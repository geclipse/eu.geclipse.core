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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.StoredCombo;

public class RemoteLocationChooserPage extends AbstractLocationChooserPage {
  
  private StoredCombo combo;

  public RemoteLocationChooserPage( final CertificateLoaderSelectionPage loaderSelectionPage ) {
    super( "remoteLocationChooserPage",
           "Import Location",
           null,
           loaderSelectionPage );
    setDescription( "Choose the remote location from which you want to import certificates" );
  }
  
  public void createControl(final Composite parent ) {

    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Label label = new Label( mainComp, SWT.NONE );
    label.setText( "Remote Repository URL:" );
    gData = new GridData();
    label.setLayoutData( gData );
    
    this.combo = new StoredCombo( mainComp, SWT.NONE );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.combo.setLayoutData( gData );
    
    this.combo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }
    } );
    
    validatePage();
    setControl( mainComp );
    
  }
  
  @Override
  public URI getSelectedLocation() {
    URI result = null;
    String text = this.combo.getText();
    try {
      result = new URI( text );
    } catch ( URISyntaxException uriExc ) {
      // Nothing to do here, errors are caught in validatePage()
    }
    return result;
  }
  
  @Override
  public void setVisible( final boolean visible ) {
    if ( visible ) {
      populateLocationCombo();
    }
    super.setVisible( visible );
  }
  
  protected void validatePage() {
    
    String errorMessage = null;
    String location = this.combo.getText();
    
    if ( ( location != null ) && ( location.length() != 0 ) ) {
      try {
        new URL( location );
      } catch ( MalformedURLException urlExc ) {
        errorMessage = String.format( "Invalid URL -> %1$s", urlExc.getLocalizedMessage() );
      }
    } else {
      errorMessage = "Please specify a valid URL";
    }
    
    setErrorMessage( errorMessage );
    setPageComplete( errorMessage == null );
    
  }
  
  private void populateLocationCombo() {
    
    ICaCertificateLoader loader = getLoader();
    
    if ( loader != null ) {
      
      IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
      String preferenceID = loader.getClass().toString() + "_remote_import_locations";
      this.combo.setPreferences( preferenceStore, preferenceID );
      
      URI[] locations = loader.getPredefinedRemoteLocations();
      if ( locations != null ) {
        for ( URI location : locations ) {
          String lString = location.toString();
          if ( ( lString.trim().length() > 0 ) && ( this.combo.indexOf( lString ) == -1 ) ) {
            if ( ( this.combo.getText() == null ) || ( this.combo.getText().length() == 0 ) ) {
              this.combo.setText( lString );
            }
            this.combo.add( lString );
          }
        }
      }
      
    }
    
  }

}
