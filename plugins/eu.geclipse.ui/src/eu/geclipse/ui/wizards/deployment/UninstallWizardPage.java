/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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
 *    Jie Tao -- initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.wizards.deployment;

import java.net.URI;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.ui.dialogs.GridFileDialog;


/**get the script
 * @author tao-j
 *
 */
public class UninstallWizardPage extends WizardPage {

  private static Composite composite;
  //private static String TAR_SUFFIX = "tar"; //$NON-NLS-1$
  private Button filebutton;
  private URI scripturi = null;
  private Label seletedinfo;
  private Label buttontext;
  
  protected UninstallWizardPage( final String pageName ) {
  super( pageName );
  this.setDescription( "Choose the uninstall script file" ); //$NON-NLS-1$
  this.setTitle( "Application Uninstall" ); //$NON-NLS-1$
}

  public void createControl( final Composite parent ) {
    composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 1, false ) );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileimage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    this.filebutton = new Button(composite,SWT.PUSH);
    this.buttontext = new Label ( composite, SWT.NONE );
    this.buttontext.setText( "Select a script file for remove the deployed software" ); //$NON-NLS-1$
    this.filebutton.setImage( fileimage );
    this.seletedinfo = new Label ( composite, SWT.NONE );
    this.seletedinfo.setText( "The selected script is:  here is the position" ); //$NON-NLS-1$
    this.seletedinfo.setVisible( false );
    GridData gridData;
    gridData = new GridData(GridData.GRAB_HORIZONTAL);
    gridData.horizontalAlignment = GridData.BEGINNING;
    gridData.grabExcessHorizontalSpace = true;
    //gridData.minimumWidth = 700;
    this.seletedinfo.setLayoutData( gridData );
    this.setControl( composite );
    //initContents();
  }
  
  @Override
  public void setVisible( final boolean visible ) {
    if( visible ) {
      this.initContents();
    }
    super.setVisible( visible );
  }
  
  protected void initContents() {
    this.filebutton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
       URI[] urls = GridFileDialog.openFileDialog( getShell(), GridFileDialog.STYLE_ALLOW_ONLY_FILES );
       updatePagebuttonComplete(urls);
      }
  } );
  
  }
  
 /* public void widgetDefaultSelected( final SelectionEvent e ) {
    // empty implementation
    
  }*/

  /*public void widgetSelected( final SelectionEvent e ) {
    if( e.getSource().equals( this.filebutton )) {
      if (this.filebutton.getSelection()){
      URI[] urls = GridFileDialog.openFileDialog( getShell(), GridFileDialog.STYLE_ALLOW_ONLY_FILES );
      //getSourceTree().collapseAll();
      updatePagebuttonComplete(urls);
  }
    }
  }*/

 
  protected void updatePagebuttonComplete(final URI[] urls) {
    this.setPageComplete(false);
    if ( urls == null ) {
      this.setMessage( null );
      this.setErrorMessage( Messages.getString( "Uninstall.script_empty" ) ); //$NON-NLS-1$
      this.seletedinfo.setVisible( false );
      return;
    }
    
    this.scripturi = urls[0];
    this.seletedinfo.setText( "The selected script is: " + (new Path (this.scripturi.getPath())).lastSegment() ); //$NON-NLS-1$
    this.seletedinfo.setVisible( true );
    this.setPageComplete( true );
    this.setErrorMessage( null );
  }
  
 
  /** the selected script
   * @return URI
   */
  public URI getScript() {
    return this.scripturi;
  }
 
}
