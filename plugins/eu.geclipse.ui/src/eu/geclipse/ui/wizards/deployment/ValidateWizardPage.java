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
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.StoredCombo;


/**the page for inputing the validate script
 * @author tao-j
 *
 */
public class ValidateWizardPage extends WizardPage {

  
  private static Composite composite;
  private static String INPUT_EXE_ID = "executable_file"; //$NON-NLS-1$
  StoredCombo buttontext;
  //private static String TAR_SUFFIX = "tar"; //$NON-NLS-1$
  private Button filebutton;
  private URI scripturi = null;

 
  protected ValidateWizardPage( final String pageName ) {
  super( pageName );
  this.setDescription( "Choose the validate script file" ); //$NON-NLS-1$
  this.setTitle( "Application validate" ); //$NON-NLS-1$
}

  public void createControl( final Composite parent ) {
    composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 1, false ) );
    IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
    URL openFileIcon = Activator.getDefault()
    .getBundle()
    .getEntry( "icons/obj16/open_file.gif" ); //$NON-NLS-1$
    Image openFileImage = ImageDescriptor.createFromURL( openFileIcon )
    .createImage();

    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    composite.setLayout( gLayout );
    
    Label inputLabel = new Label( composite, GridData.HORIZONTAL_ALIGN_BEGINNING
                                  | GridData.VERTICAL_ALIGN_CENTER );
    inputLabel.setText("Script file"); //$NON-NLS-1$
    GridData gridData;
    gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    inputLabel.setLayoutData( gridData );
    
    this.buttontext = new StoredCombo( composite, SWT.DROP_DOWN );
    this.buttontext.setPreferences( prefs, INPUT_EXE_ID );
    this.buttontext.setText( "" ); //$NON-NLS-1$
    this.buttontext.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent event ) {
        if( ValidateWizardPage.this.buttontext.getText()
          .equals( "" ) ) { //$NON-NLS-1$
          removescript();
        } else {
          setscript();
        }
      }
    } );
    
    
    
   gridData = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.buttontext.setLayoutData( gridData );
    
    
    // Button - browsing for executable file
    this.filebutton = new Button( composite, SWT.PUSH );
    this.filebutton.setImage( openFileImage );
    gridData = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.filebutton.setLayoutData( gridData );
    
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
  
  void setscript() {
    String input = this.buttontext.getText(); 
    try {
      this.scripturi = new URI (input);
    } catch( URISyntaxException e ) {
      this.setErrorMessage( "The file path is incorrect" ); //$NON-NLS-1$
    }
 
    this.setPageComplete(true);
    this.setErrorMessage( null );
  }
  
  void removescript() {
    this.scripturi = null;
    this.setPageComplete(false);
    this.setErrorMessage( Messages.getString( "Validate.script_empty" ) ); //$NON-NLS-1$
  }
  
  protected void initContents() {
    this.filebutton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
       URI[] urls = GridFileDialog.openFileDialog( getShell(), GridFileDialog.STYLE_ALLOW_ONLY_FILES );
       if( ( urls != null ) && ( urls.length > 0 ) ) {
         ValidateWizardPage.this.buttontext.setText( urls[ 0 ].toString() );
       } else {
         ValidateWizardPage.this.buttontext.setText( "" ); //$NON-NLS-1$
       }
       updatePagebuttonComplete(urls);
      }
  } );
  
  }
  
  protected void updatePagebuttonComplete(final URI[] urls) {
    this.setPageComplete(false);
    if ( urls == null ) {
      this.setMessage( null );
      this.setErrorMessage( Messages.getString( "Validate.script_empty" ) ); //$NON-NLS-1$
      return;
    }
    
    this.scripturi = urls[0];
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
