/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.jobs;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.ui.dialogs.gexplorer.GridFileDialog;

/**
 * Wizard page that allows user to specify stdIn, stdOut and stdErr files for
 * job to be run on the Grid
 * 
 * @author katis
 */
public class FilesInputNewJobWizardPage extends WizardPage {

  Text stdin;
  Text stdout;
  Text stderr;
  Text stdInName;
  /**
   * Button to add files to {@link FilesInputNewJobWizardPage#stdin}
   */
  private Button chooseButton;

  /**
   * Creates new FilesNewJobWizardPage
   * 
   * @param pageName name of this page
   */
  protected FilesInputNewJobWizardPage( final String pageName ) {
    super( pageName );
    setDescription( Messages.getString( "FilesInputNewJobWizardPage.page_description" ) ); //$NON-NLS-1$
    setTitle( Messages.getString( "FilesInputNewJobWizardPage.page_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    initializeDialogUnits( parent );
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout layout = new GridLayout( 4, false );
    layout.verticalSpacing = 10;
    layout.horizontalSpacing = 12;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    mainComp.setLayout( layout );
    GridData gData;
    Label stdinLabel = new Label( mainComp, SWT.LEAD );
    gData = new GridData( GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stdinLabel.setLayoutData( gData );
    stdinLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_label" ) ); //$NON-NLS-1$
    this.stdInName = new Text (mainComp, SWT.NONE | SWT.BORDER);
    gData = new GridData( GridData.FILL_HORIZONTAL
                          | GridData.GRAB_HORIZONTAL
                          | GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_CENTER );
    this.stdInName.setText( "name" );
    this.stdInName.setLayoutData( gData );
    // chosen only by picking up file in GridDialog
    this.stdin = new Text( mainComp, SWT.NONE | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL
                          | GridData.GRAB_HORIZONTAL
                          | GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_CENTER );
    this.stdin.setLayoutData( gData );
    this.stdin.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ); //$NON-NLS-1$
    this.stdin.setEnabled( false );
    this.chooseButton = new Button( mainComp, SWT.PUSH );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    this.chooseButton.setImage( fileImage );
    gData = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                          | GridData.VERTICAL_ALIGN_FILL
                          | GridData.VERTICAL_ALIGN_CENTER );
    this.chooseButton.setLayoutData( gData );
    Label stdoutLabel = new Label( mainComp, SWT.NONE );
    gData = new GridData( GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stdoutLabel.setLayoutData( gData );
    stdoutLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stdout_label" ) ); //$NON-NLS-1$
    this.stdout = new Text( mainComp, SWT.NONE | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL
                          | GridData.GRAB_HORIZONTAL
                          | GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_CENTER );
     gData.horizontalSpan = 2;
    this.stdout.setLayoutData( gData );
    this.stdout.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ); //$NON-NLS-1$
    this.stdout.setEnabled( false );
    Button outButton = new Button( mainComp, SWT.PUSH );
    outButton.setImage( fileImage );
    gData = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                          | GridData.VERTICAL_ALIGN_FILL
                          | GridData.VERTICAL_ALIGN_CENTER );
//    gData.horizontalSpan = 2;
    outButton.setLayoutData( gData );
    Label stderrLabel = new Label( mainComp, SWT.NONE );
    gData = new GridData( GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stderrLabel.setLayoutData( gData );
    stderrLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stderr_label" ) ); //$NON-NLS-1$
    this.stderr = new Text( mainComp, SWT.NONE | SWT.BORDER );
    gData = new GridData( GridData.VERTICAL_ALIGN_CENTER
                          | GridData.HORIZONTAL_ALIGN_CENTER
                          | GridData.GRAB_HORIZONTAL
                          | GridData.FILL_HORIZONTAL );
     gData.horizontalSpan = 2;
    this.stderr.setLayoutData( gData );
    this.stderr.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ); //$NON-NLS-1$
    this.stderr.setEnabled( false );
    Button errButton = new Button( mainComp, SWT.PUSH );
    errButton.setImage( fileImage );
    gData = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                          | GridData.VERTICAL_ALIGN_FILL
                          | GridData.VERTICAL_ALIGN_CENTER );
    errButton.setLayoutData( gData );
    this.chooseButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog dialog = new GridFileDialog( getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          FilesInputNewJobWizardPage.this.stdin.setText( filename );
        }
      }
    } );
    outButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog dialog = new GridFileDialog( getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          FilesInputNewJobWizardPage.this.stdout.setText( filename );
        }
      }
    } );
    errButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog dialog = new GridFileDialog( getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          FilesInputNewJobWizardPage.this.stderr.setText( filename );
        }
      }
    } );
    setControl( mainComp );
  }

  /**
   * Method to access text form {@link FilesInputNewJobWizardPage#stderr}
   * 
   * @return text kept in {@link FilesInputNewJobWizardPage#stderr}
   */
  public String getStderr() {
    return this.stderr.getText();
  }

  /**
   * Method to access text form {@link FilesInputNewJobWizardPage#stdin}
   * 
   * @return text kept in {@link FilesInputNewJobWizardPage#stdin}
   */
  public String getStdin() {
    return this.stdin.getText();
  }

  /**
   * Method to access text form {@link FilesInputNewJobWizardPage#stdout}
   * 
   * @return text kept in {@link FilesInputNewJobWizardPage#stdout}
   */
  public String getStdout() {
    return this.stdout.getText();
  }

  public String getStdinName() {
    return this.stdInName.getText();
  }
}
