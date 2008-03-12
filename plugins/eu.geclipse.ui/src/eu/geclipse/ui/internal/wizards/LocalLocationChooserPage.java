/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.ui.internal.wizards;

import java.io.File;
import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.ui.widgets.StoredCombo;

public class LocalLocationChooserPage extends AbstractLocationChooserPage {
  
  protected StoredCombo combo;
  
  public LocalLocationChooserPage( final CertificateLoaderSelectionPage loaderSelectionPage ) {
    super( "localLocationChooserPage",
           "Import Location",
           null,
           loaderSelectionPage );
    setDescription( "Choose the local location from which you want to import certificates" );
  }

  public void createControl( final Composite parent ) {
    
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );

    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Label label = new Label( mainComp, SWT.NONE );
    label.setText( "&Local File or Directory:" );
    gData = new GridData();
    gData.horizontalSpan = 2;
    label.setLayoutData( gData );
    
    this.combo = new StoredCombo( mainComp, SWT.NONE );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.combo.setLayoutData( gData );
    
    Button button = new Button( mainComp, SWT.PUSH );
    button.setImage( fileImage );
    gData = new GridData();
    button.setLayoutData( gData );
    
    this.combo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }
    } );
    
    button.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        String file = openDirectoryDialog();
        if ( file != null ) {
          LocalLocationChooserPage.this.combo.setText( file );
        }
      }
    } );
    
    validatePage();
    setControl( mainComp );
    
  }
  
  @Override
  public URI getSelectedLocation() {
    String pathString = this.combo.getText();
    IPath path = new Path( pathString );
    IFileSystem localFileSystem = EFS.getLocalFileSystem();
    IFileStore store = localFileSystem.getStore( path );
    return store.toURI();
  }
  
  protected String openDirectoryDialog() {
    DirectoryDialog dialog = new DirectoryDialog( getShell(), SWT.OPEN );
    dialog.setText( "Select PEM file directory" );
    String dir = dialog.open();
    return dir;
  }
  
  protected void validatePage() {
    
    String errorMessage = null;
    String location = this.combo.getText();
    
    if ( ( location != null ) && ( location.length() != 0 ) ) {
      File file = new File( location );
      if ( ! file.exists() ) {
        errorMessage = "No file or directory found at the specified location";
      }
    } else {
      errorMessage = "Please specify a valid file or directory";
    }
    
    setErrorMessage( errorMessage );
    setPageComplete( errorMessage == null );
    
  }

}
