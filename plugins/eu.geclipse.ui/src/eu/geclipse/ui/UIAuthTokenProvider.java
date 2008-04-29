/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.CheatSheetListener;
import org.eclipse.ui.cheatsheets.ICheatSheetEvent;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import eu.geclipse.core.auth.AuthTokenRequest;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.CoreAuthTokenProvider;
import eu.geclipse.core.auth.IAuthTokenProvider;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

/**
 * The <code>UIAuthTokenProvider</code> is the main point where Plugins should request their
 * authentication tokens. It should be used instead of the <code>AuthenticationTokenManager</code>
 * whenever possible. It provides methods to request any token or tokens of a special type. It also
 * takes responsibility for the user interactions with respect to new token wizards, question
 * dialogs and error dialogs. Therefore it makes the request for a new token very easy.
 */
public class UIAuthTokenProvider extends CheatSheetListener implements IAuthTokenProvider {

  /**
   * Runnable implementation that is executed in the UI thread in
   * order to retrieve an existing token or to create a new token.
   * This has to run in the UI thread in order to allow the popup
   * of error dialogs or token wizards. 
   */
  private class Runner implements Runnable {
    
    /**
     * The token description for which to retrieve a token.
     */
    AuthTokenRequest request;
    
    /**
     * The token that was found or created.
     */
    IAuthenticationToken token;
    
    /**
     * Construct a new Runner used to find a token for the specified
     * token description.
     * 
     * @param request The {@link AuthTokenRequest} for
     * which to find an {@link IAuthenticationToken}.
     */
    public Runner( final AuthTokenRequest request ) {
      this.request = request;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
      
      Throwable t = null;
      this.token = null;
      CoreAuthTokenProvider cProvider = new CoreAuthTokenProvider();
      
      try {
        this.token = cProvider.requestToken( this.request );
      } catch ( Exception e ) {
        t = e;
      }
      
      if ( ( this.token == null ) && ( t == null ) ) {
        
        // No token could be found, so create one
        
        String title = this.request.getRequester();
        if ( title == null ) {
          title = Messages.getString( "UIAuthTokenProvider.req_token_title" ); //$NON-NLS-1$
        }
        
        String message = this.request.getPurpose();
        if ( message == null ) {
          message = Messages.getString( "UIAuthTokenProvider.new_token_question" ); //$NON-NLS-1$
        }
        
        boolean result = MessageDialog.openQuestion( UIAuthTokenProvider.this.shell, title, message );
        
        if( result ) {
          IAuthenticationTokenDescription description = this.request.getDescription();
          String tokenWizardId = description.getWizardId();
          if ( showNewTokenWizard( tokenWizardId, false, description ) ) {
            this.token = cProvider.requestToken( this.request );
          }
        }
        
      }
      
      if( this.token != null ) {
        // Check if the token is both valid and active
        try {
          if( !this.token.isValid() ) {
            validateToken( this.token );
          }
          if( !this.token.isActive() ) {
            activateToken( this.token );
          }
        } catch( InvocationTargetException itExc ) {
          t = itExc.getCause();
          if( t == null ) {
            t = itExc;
          }
        } catch( InterruptedException intExc ) {
          t = intExc;
        }
      }
      
      if ( t != null ) {
        ProblemDialog.openProblem( UIAuthTokenProvider.this.shell,
                                   Messages.getString("UIAuthTokenProvider.token_activation_error_title"), //$NON-NLS-1$
                                   Messages.getString("UIAuthTokenProvider.token_activation_error_message"), //$NON-NLS-1$
                                   t );
        this.token = null;
      }
      
    }
    
  }
  
  /**
   * A manager used for cheat sheet automation.
   */
  protected static ICheatSheetManager cheatSheetManager;
  
  /**
   * Key for the auth token wizard.
   */
  private static final String WIZARD_PAGE_NAME = "pagename"; //$NON-NLS-1$

  /**
   * The <code>Shell</code> that is used to create dialogs, error dialogs...
   */
  protected Shell shell;
  
  /**
   * The display used to synchronously run the token creation process.
   */
  protected Display display;
  
  /**
   * Standard constructor for the <code>UIAuthTokenProvider</code>.
   */
  public UIAuthTokenProvider() {
    this( null );
  }

  /**
   * Construct a new <code>UIAuthTokenProvider</code>.
   * 
   * @param shell The shell that is used to create wizards and dialogs.
   */
  public UIAuthTokenProvider( final Shell shell ) {
    IWorkbench workbench = PlatformUI.getWorkbench();
    this.display = workbench.getDisplay();
    this.shell = shell;
    if ( shell == null ) {
      IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
      if ( window != null ) { 
        this.shell = window.getShell();
      }
    }
  }

  /**
   * Request any authentication token. This is the same as
   * <code>requestToken(null)</code>.
   * 
   * @return Any token that could be found.
   * @see #requestToken(AuthTokenRequest)
   */
  public IAuthenticationToken requestToken() {
    return requestToken( null );
  }

  /**
   * Request an authentication token that fits the specified description. At the
   * moment the token has only to fit the supported type of the description. So
   * if a <code>GridProxy</code> is requested an empty
   * <code>GridProxyDescription</code> should be passed to this method.
   * <p>
   * Internally this method queries the <code>AuthenticationTokenManager</code>.
   * The first step therefore is always to look for the default token. If the
   * default token is of the specified type it is returned. The second step is
   * to look for all currently registered tokens. If one of these fits it is
   * returned. If no token could be found up to here the new token wizard is
   * launched to create a new token that fits the description. The newly created
   * token is afterwards added to the token managers managed tokens.
   * <p>
   * If the found token is not the default token a message box will pop up to
   * ask if the token should be set as default token. If the found token is not
   * valid or not active it is validated and respectively activated. If
   * something wents wrong during this process an error message will pop up.
   * 
   * @param request An {@link AuthTokenRequest} that
   *          describes the token that is requested.
   * @return A token of the type that is described by the specified description
   *         or null if no such token could be found or created.
   */
  public IAuthenticationToken requestToken( final AuthTokenRequest request ) {
    Runner runner = new Runner( request );
    this.display.syncExec( runner );
    return runner.token;
  }
  
  /**
   * Show the new token wizard. If the specified description is not null the wizard will
   * be started with the wizard page belonging to the specified description. Otherwise it
   * will be started with the token type page as starting page where the user can choose
   * the type of the he wants to create.
   * 
   * @param tokenWizardId The ID of the token type that should be created or null.
   * @param description Token description passed to the token specific wizard pages in
   * order to allow initialisation for a predefined token type.
   * @return True if the token dialog was closed with status {@link Window#OK}.
   */
  public boolean showNewTokenWizard( final String tokenWizardId,
                                     final boolean forceWizardId,
                                     final IAuthenticationTokenDescription description ) {
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newtoken_wiz.gif" ); //$NON-NLS-1$

    Wizard wizard =  new Wizard() {
      @Override
      public boolean performFinish() {
        return false;
      }
  
      @Override
      public void addPages() {
        List<String> filterList = null;
        if (tokenWizardId != null) {
          filterList = new LinkedList<String>();
          filterList.add( tokenWizardId );
        }
        ExtPointWizardSelectionListPage page = new ExtPointWizardSelectionListPage(
            WIZARD_PAGE_NAME,
            Extensions.AUTH_TOKEN_UI_POINT,
            filterList,
            forceWizardId,
            Messages.getString( "UIAuthTokenProvider.wizard_first_page_title" ), //$NON-NLS-1$
            Messages.getString( "UIAuthTokenProvider.wizard_first_page_description" ), //$NON-NLS-1$
            Messages.getString( "UIAuthTokenProvider.noTokenCreator" ) ); //$NON-NLS-1$
//        page.setPreselectedId( tokenWizardId, true );
        page.setInitData( description );
        page.setCheatSheetManager(cheatSheetManager);
        addPage( page );
      }
    };

    wizard.setNeedsProgressMonitor( true );
    wizard.setForcePreviousAndNextButtons( true );
    wizard.setWindowTitle( Messages.getString("UIAuthTokenProvider.wizard_title") ); //$NON-NLS-1$
    wizard.setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    WizardDialog dialog = new WizardDialog( this.shell, wizard );
    return dialog.open() == Window.OK;
  }

  /**
   * Validates the specified token. This method does the validation in a
   * separate thread and provides a progress monitor for the validation process.
   * 
   * @param token The token to be validated.
   * @throws InvocationTargetException Thrown if an exception occurs in the
   *           validation thread.
   * @throws InterruptedException Thrown if the validation thread is
   *           interrupted.
   */
  protected void validateToken( final IAuthenticationToken token )
    throws InvocationTargetException, InterruptedException
  {
    ProgressMonitorDialog progMon = new ProgressMonitorDialog( this.shell );
    progMon.run( false, false, new IRunnableWithProgress() {

      public void run( final IProgressMonitor monitor )
        throws InvocationTargetException, InterruptedException
      {
        try {
          token.validate( monitor );
        } catch( AuthenticationException authExc ) {
          throw new InvocationTargetException( authExc );
        }
      }
    } );
  }

  /**
   * Activate the specified token. This method does the activation in a separate
   * thread and provides a progress monitor for the activation process.
   * 
   * @param token The token to be activated.
   * @throws InvocationTargetException Thrown if an exception occurs in the
   *           activation thread.
   * @throws InterruptedException Thrown if the activation thread is
   *           interrupted.
   */
  protected void activateToken( final IAuthenticationToken token )
    throws InvocationTargetException, InterruptedException
  {
    ProgressMonitorDialog progMon = new ProgressMonitorDialog( this.shell );
    progMon.run( false, false, new IRunnableWithProgress() {

      public void run( final IProgressMonitor monitor )
        throws InvocationTargetException, InterruptedException
      {
        try {
          token.setActive( true, monitor );
        } catch( AuthenticationException authExc ) {
          throw new InvocationTargetException( authExc );
        }
      }
    } );
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.cheatsheets.CheatSheetListener#cheatSheetEvent(org.eclipse.ui.cheatsheets.ICheatSheetEvent)
   */
  @Override
  public void cheatSheetEvent( final ICheatSheetEvent event ) {
    cheatSheetManager = event.getCheatSheetManager();
    if ( cheatSheetManager.getData( "startingPageName" ) == null ) { //$NON-NLS-1$
      cheatSheetManager.setData( "startingPageName", "none" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }
}
