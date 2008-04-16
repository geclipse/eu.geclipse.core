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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.Messages;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;

public class ProblemReportDialog extends TitleAreaDialog {
  
  private static final int SEND_ID = 0x01;
  
  private static final int SAVE_ID = 0x02;
  
  private static final int COPY_ID = 0x03;
  
  private IProblem problem;
  
  private Text reportText;
  
  public ProblemReportDialog( final Shell parentShell, final IProblem problem ) {
    super( parentShell );
    this.problem = problem;
    setShellStyle( SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
    URL imgURL = Activator.getDefault().getBundle()
                   .getResource( "icons/extras/problem_report_dlg.gif" ); //$NON-NLS-1$
    ImageDescriptor imgDesc = ImageDescriptor.createFromURL( imgURL );
    setTitleImage( imgDesc.createImage() );
  }
  
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
  
  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    createButton( parent, IDialogConstants.CLIENT_ID + SEND_ID, "Send", true );
    createButton( parent, IDialogConstants.CLIENT_ID + SAVE_ID, "Save", false );
    createButton( parent, IDialogConstants.CLIENT_ID + COPY_ID, "Copy", false );
    createButton( parent, IDialogConstants.CLOSE_ID, IDialogConstants.CLOSE_LABEL, false );
  }
  
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    Label topRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    topRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    mainComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    
    Label reportLabel = new Label( mainComp, SWT.NONE );
    reportLabel.setText( "Problem Report:" );
    reportLabel.setLayoutData( new GridData() );
    
    this.reportText = new Text( mainComp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY );
    ProblemReport factory = new ProblemReport( this.problem );
    this.reportText.setText( factory.createReport() );
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.widthHint = 500;
    gData.heightHint = 400;
    this.reportText.setLayoutData( gData );
    
    Label bottomRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    bottomRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    setTitle( "Problem Reporting Dialog" );
    setMessage( "A problem report was created, choose one of the options by pressing the appropriate button." );
    
    return mainComp;
        
  }
  
  protected void closePressed() {
    setReturnCode( OK );
    close();
  }
  
  protected void copyPressed() {
    
    Transfer[] dataTypes = new Transfer[] {
        TextTransfer.getInstance()
    };
    Object[] data = new Object[] {
        this.reportText.getText()
    };
    Clipboard clipboard = new Clipboard( getShell().getDisplay() );
    clipboard.setContents( data, dataTypes );
    clipboard.dispose();
    
    MessageDialog.openInformation( getShell(), "Report copied", "The problem report was copied to the system clipboard." );
    
  }
  
  protected void savePressed() {
    
    GridFileDialog dialog = new GridFileDialog( getShell(), GridFileDialog.STYLE_ALLOW_ONLY_FILES );
    
    if ( dialog.open() == Window.OK ) {
      
      IFileStore[] fsList = dialog.getSelectedFileStores();
      
      if ( ( fsList != null ) && ( fsList.length > 0 ) ) {
       
        try {
          saveReport( fsList[ 0 ], this.reportText.getText() );
          MessageDialog.openInformation( getShell(), "Report saved", "The problem report was saved to " + fsList[ 0 ].toString() );
        } catch ( CoreException cExc ) {
        
          ProblemDialog.openProblem( getShell(),
              "Save failed",
              "Unable to save report",
              cExc );
          
        } catch ( IOException ioExc ) {
          
          ProblemDialog.openProblem( getShell(),
              "Save failed",
              "Unable to save report",
              ioExc );
          
        }
        
      }
      
    }
    
  }
  
  protected void sendPressed() {
    
    try {
      
      URL link = getMailToLink( this.reportText.getText() );
      IWorkbenchBrowserSupport browserSupport
        = PlatformUI.getWorkbench().getBrowserSupport();
      IWebBrowser externalBrowser
        = browserSupport.getExternalBrowser();
      externalBrowser.openURL( link );
      MessageDialog.openInformation(
          getShell(),
          "Report send",
          "The problem report was send to your mail client. Please note that this is an OS specific functionality that may fail. So please make sure that the report arrived at your mail client." );
      
    } catch ( PartInitException piExc ) {
      
      ProblemDialog.openProblem( getShell(),
          "Send failed",
          "Unable to send report to " + this.problem.getMailTo(),
          piExc );
      
    } catch ( MalformedURLException murlExc ) {
      
      ProblemDialog.openProblem( getShell(),
          "Send failed",
          "Unable to send report to " + this.problem.getMailTo(),
          murlExc );
      
    }
    
  }
  
  private URL getMailToLink( final String report )
      throws MalformedURLException {
    return new URL(
        "mailto:" + this.problem.getMailTo()
        + "?subject=Problem Report: " + this.problem.getDescription()
        + "&body=" + report
    );
  }
  
  private void saveReport( final IFileStore store, final String report )
      throws CoreException, IOException {
    OutputStream oStream = store.openOutputStream( EFS.NONE, null );
    oStream.write( report.getBytes() );
    oStream.close();
  }
  
}
