/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

package eu.geclipse.ui.internal.dialogs;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * Dialog for reporting {@link IProblem}s. The dialog supports sending, saving
 * and copying the problem report to the system clipboard.
 */
public class ProblemReportDialog extends TitleAreaDialog {
  
  /**
   * ID of the send button.
   */
  private static final int SEND_ID = 0x01;
  
  /**
   * ID of the save button.
   */
  private static final int SAVE_ID = 0x02;
  
  /**
   * ID of the copy button.
   */
  private static final int COPY_ID = 0x03;
  
  /**
   * The problem to be reported.
   */
  private IProblem problem;
  
  /**
   * Text field for the email address.
   */
  private Text mailtoText;
  
  /**
   * The text holding the problem report.
   */
  private Text reportText;
  
  /**
   * Create a new problem report dialog for the specified problem.
   * 
   * @param parentShell The dialog's parent {@link Shell}.
   * @param problem The {@link IProblem} to be reported.
   */
  public ProblemReportDialog( final Shell parentShell, final IProblem problem ) {
    super( parentShell );
    this.problem = problem;
    setShellStyle( SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
    URL imgURL = Activator.getDefault().getBundle()
                   .getResource( "icons/extras/problem_report_dlg.gif" ); //$NON-NLS-1$
    ImageDescriptor imgDesc = ImageDescriptor.createFromURL( imgURL );
    setTitleImage( imgDesc.createImage() );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
   */
  @Override
  protected void buttonPressed( final int buttonId ) {
    if ( buttonId == IDialogConstants.CLIENT_ID + SEND_ID ) {
      sendPressed();
    } else if ( buttonId == IDialogConstants.CLIENT_ID + SAVE_ID ) {
      savePressed();
    } else if ( buttonId == IDialogConstants.CLIENT_ID + COPY_ID ) {
      copyPressed();
    } else if ( buttonId == IDialogConstants.CLOSE_ID ) {
      closePressed();
    } else {
      super.buttonPressed( buttonId );
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    createButton( parent, IDialogConstants.CLIENT_ID + SEND_ID, "Send", true );
    createButton( parent, IDialogConstants.CLIENT_ID + SAVE_ID, "Save", false );
    createButton( parent, IDialogConstants.CLIENT_ID + COPY_ID, "Copy", false );
    createButton( parent, IDialogConstants.CLOSE_ID, IDialogConstants.CLOSE_LABEL, false );
    updateUI();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    Label topRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    topRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    mainComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    
    Label mailtoLabel = new Label( mainComp, SWT.NONE );
    mailtoLabel.setText( "Email address:" );
    mailtoLabel.setLayoutData( new GridData() );
    
    this.mailtoText = new Text( mainComp, SWT.BORDER );
    String mailto = this.problem.getMailTo();
    this.mailtoText.setText( mailto == null ? "" : mailto ); //$NON-NLS-1$
    this.mailtoText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    
    Label reportLabel = new Label( mainComp, SWT.NONE );
    reportLabel.setText( "Problem Report:" );
    reportLabel.setLayoutData( new GridData() );
    
    this.reportText = new Text( mainComp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    ProblemReport factory = new ProblemReport( this.problem );
    this.reportText.setText( factory.createReport() );
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.widthHint = 500;
    gData.heightHint = 400;
    this.reportText.setLayoutData( gData );
    
    Label bottomRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    bottomRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    this.mailtoText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    setTitle( "Problem Reporting Dialog" );
    setMessage( "A problem report was created, choose one of the options by pressing the appropriate button." );
    
    updateUI();
    
    return mainComp;
        
  }
  
  /**
   * Called when the close button is pressed.
   */
  protected void closePressed() {
    setReturnCode( OK );
    close();
  }
  
  /**
   * Called when the copy button is pressed.
   */
  protected void copyPressed() {
    
    try {
    
      Transfer[] dataTypes = new Transfer[] {
          TextTransfer.getInstance()
      };
      Object[] data = new Object[] {
          this.reportText.getText()
      };
      Clipboard clipboard = new Clipboard( getShell().getDisplay() );
      clipboard.setContents( data, dataTypes );
      clipboard.dispose();
      
      setMessage(
          "The problem report was copied to the system clipboard",
          IMessageProvider.INFORMATION );
      
    } catch ( Throwable t ) {
      setErrorMessage( String.format( "Copy failed: %s", t.getLocalizedMessage() ) );
    }
    
  }
  
  /**
   * Called when the save button is pressed.
   */
  protected void savePressed() {
    
    try {
    
      GridFileDialog dialog = new GridFileDialog( getShell(), GridFileDialog.STYLE_ALLOW_ONLY_FILES );
      
      if ( dialog.open() == Window.OK ) {
        
        IFileStore[] fsList = dialog.getSelectedFileStores();
        
        if ( ( fsList != null ) && ( fsList.length > 0 ) ) {
         
          saveReport( fsList[ 0 ], this.reportText.getText() );
          setMessage(
              String.format( "The problem report saved to %s", fsList[ 0 ].toString() ),
              IMessageProvider.INFORMATION );
          
        }
        
      }
      
    } catch ( Throwable t ) {
      setErrorMessage( String.format( "Save failed: %s", t.getLocalizedMessage() ) );
    }
    
  }
  
  /**
   * Called when the send button is pressed.
   */
  protected void sendPressed() {
    
    try {
      
      String mailto = this.mailtoText.getText();
      if ( ( mailto == null ) || ( mailto.length() == 0 ) ) {
        throw new IllegalArgumentException( "The mailto address is empty" );
      }
      
      URL link = getMailToLink( this.reportText.getText(), mailto );
      IWorkbenchBrowserSupport browserSupport
        = PlatformUI.getWorkbench().getBrowserSupport();
      IWebBrowser externalBrowser
        = browserSupport.getExternalBrowser();
      externalBrowser.openURL( link );
      setMessage(
          String.format( "An attempt was made to send to problem report to %s", mailto ),
          IMessageProvider.INFORMATION );
      
    } catch ( Throwable t ) {
      setErrorMessage( String.format( "Send failed: %s", t.getLocalizedMessage() ) );
    }
    
  }
  
  protected void updateUI() {
    Button sendButton = getButton( IDialogConstants.CLIENT_ID + SEND_ID );
    if ( sendButton != null ) {
      String mailto = this.mailtoText.getText();
      sendButton.setEnabled( ( mailto != null ) && ( mailto.length() > 0 ) );
    }
  }
  
  /**
   * Get the mailto link for the send action.
   * 
   * @param report The report to be send.
   * @return The mailto link as {@link URL}.
   * @throws MalformedURLException If the {@link URL} is malformed.
   */
  private URL getMailToLink( final String report, final String address )
      throws MalformedURLException {
    if ( ( address == null ) || ( address.length() == 0 ) ) {
      throw new IllegalArgumentException( "The mailto address is empty" );
    }
    return new URL(
        "mailto:" + address
        + "?subject=Problem Report: " + this.problem.getDescription()
        + "&body=" + report
    );
  }
  
  /**
   * Save the report to the specified {@link IFileStore}.
   * 
   * @param store The {@link IFileStore} to which to save the report.
   * @param report The report to be saved.
   * @throws CoreException If the output stream could not be opened.
   * @throws IOException If writing to the output stream or closing the
   * stream failed.
   */
  private void saveReport( final IFileStore store, final String report )
      throws CoreException, IOException {
    OutputStream oStream = store.openOutputStream( EFS.NONE, null );
    oStream.write( report.getBytes() );
    oStream.close();
  }
  
}
