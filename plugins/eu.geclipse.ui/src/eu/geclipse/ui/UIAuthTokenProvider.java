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

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.auth.AuthTokenRequest;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.CoreAuthTokenProvider;
import eu.geclipse.core.auth.IAuthTokenProvider;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.core.reporting.ProblemException;
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
    
    ProblemException exc;

    private CoreAuthTokenProvider cProvider;
    
    /**
     * Construct a new Runner used to find a token for the specified
     * token description.
     * 
     * @param request The {@link AuthTokenRequest} for
     * which to find an {@link IAuthenticationToken}.
     * @param cProvider 
     */
    public Runner( final AuthTokenRequest request, final CoreAuthTokenProvider cProvider ) {
      this.request = request;
      this.cProvider = cProvider;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {      
        
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
          this.token = this.cProvider.requestToken( this.request );
        } else {
          this.exc = new ProblemException( ICoreProblems.AUTH_TOKEN_REQUEST_CANCELED, Activator.PLUGIN_ID );
        }
      } else {
        this.exc = new ProblemException( ICoreProblems.AUTH_TOKEN_REQUEST_CANCELED, Activator.PLUGIN_ID );
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
   * @throws ProblemException If the token request was canceled by the user.
   * @see #requestToken(AuthTokenRequest)
   */
  public IAuthenticationToken requestToken() throws ProblemException {
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
   * @throws ProblemException If the token request was canceled by the user.
   */
  public IAuthenticationToken requestToken( final AuthTokenRequest request ) throws ProblemException {
    IAuthenticationToken token = null;
    Throwable t = null;    
    CoreAuthTokenProvider cProvider = new CoreAuthTokenProvider();
    
    try {
      token = cProvider.requestToken( request );
    } catch ( Exception e ) {
      t = e;
    }
    
    if ( ( token == null ) && ( t == null ) ) {
      Runner runner = new Runner( request, cProvider );
      runInUIThread( runner );
      if ( runner.exc != null ) {
        throw runner.exc;
      }
      token = runner.token;
    }
    
    if( token != null ) {
      // Check if the token is both valid and active
      try {
        if( !token.isValid() ) {
          validateToken( token );
        }
        if( !token.isActive() ) {
          activateToken( token );
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
    }
    
    return token;    
  }
  
  /**
   * Show the new token wizard. If the specified description is not null the wizard will
   * be started with the wizard page belonging to the specified description. Otherwise it
   * will be started with the token type page as starting page where the user can choose
   * the type of the he wants to create.
   * 
   * @param tokenWizardId The ID of the token type that should be created or null.
   * @param forceWizardId 
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
    final Exception[] exc = new Exception[1]; 
    Runnable runnable = new Runnable() {

      public void run() {
        ProgressMonitorDialog progMon = new ProgressMonitorDialog( UIAuthTokenProvider.this.shell );
        try {
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
        } catch( InvocationTargetException exception ) {
          exc[0] = exception;
        } catch( InterruptedException exception ) {
          exc[0] = exception;
        }        
      }};
      
      runInUIThread( runnable );
      
      if( exc[0] instanceof InvocationTargetException ) {
        throw (InvocationTargetException)exc[0];
      } else if( exc[0] instanceof InterruptedException ) {
        throw (InterruptedException)exc[0];
      }
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
    final Exception[] exc = new Exception[1];
    
    Runnable uiRunnable = new Runnable() {

      public void run() {
        ProgressMonitorDialog progMon = new ProgressMonitorDialog( UIAuthTokenProvider.this.shell );
        try {
          progMon.run( false, false, new IRunnableWithProgress() {

            public void run( final IProgressMonitor monitor ) throws InvocationTargetException          
            {
              try {
                token.setActive( true, monitor );
              } catch( AuthenticationException authExc ) {
                throw new InvocationTargetException( authExc );
              }
            }
          } );
        } catch( InvocationTargetException exception ) {
          exc[0] = exception;
        } catch( InterruptedException exception ) {
          exc[0] = exception;
        }
      }};
      
    runInUIThread( uiRunnable );
    if( exc[0] instanceof InvocationTargetException ) {
      throw (InvocationTargetException)exc[0];
    } else if( exc[0] instanceof InterruptedException ) {
      throw (InterruptedException)exc[0];
    }
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
  
  /**
   * Method calling {@link Runnable#run()} in UI thread. In the future this
   * method will have deadlock detection
   * 
   * @param runnable
   */
  private void runInUIThread( final Runnable runnable ) {
    this.display.syncExec( runnable );
  }
}
