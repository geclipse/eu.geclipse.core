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
 *          - Mateusz Pabis
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.connection.managers.local;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Wizard Page used in NewConnectionWizard for creating new connection to local file system
 * @author katis
 *
 */
public class LocalConnectionWizardPage extends WizardPage implements ModifyListener {

  private static final String separator = System.getProperty( "file.separator" ); //$NON-NLS-1$
  
  private static String KEY_MNTPOINS_ID = "key_mntpoins"; //$NON-NLS-1$

  private StoredCombo mntPointCombo;
  
  protected LocalConnectionWizardPage( final String pageName ) {
    super( pageName );
    setTitle( Messages.getString("LocalConnectionWizardPage.page_title") ); //$NON-NLS-1$
    setDescription( Messages.getString("LocalConnectionWizardPage.page_description") ); //$NON-NLS-1$
  }

  URI finish() {
    URI result= null;
    String mnt = this.mntPointCombo.getText();
    try {
      if ( separator .equals( "\\" )){ //$NON-NLS-1$
        mnt = mnt.replaceAll( "\\\\", "/" ); //$NON-NLS-1$ //$NON-NLS-2$
        mnt = mnt.replaceAll( " ", "%20" ); //$NON-NLS-1$ //$NON-NLS-2$
      }
      result = new URI("file:" + mnt); //$NON-NLS-1$
    } catch( NumberFormatException numberFormatExc ) {
       eu.geclipse.ui.internal.Activator.logException( numberFormatExc );
    } catch( URISyntaxException uriSyntaxExc ) {
      eu.geclipse.ui.internal.Activator.logException( uriSyntaxExc );
    }
    return result;
  }
  
  @Override
  public boolean isPageComplete() {
    boolean result = false;
    String errorMessage = null;
    if (this.mntPointCombo.getText().equals( "" )){ //$NON-NLS-1$
      result = false;
      errorMessage = Messages.getString("LocalConnectionWizardPage.empty_mount_error"); //$NON-NLS-1$
    }
    else{
      if( !this.mntPointCombo.getText().endsWith( separator ) ) 
      {
        result = false;
        errorMessage = Messages.getString("LocalConnectionWizardPage.dir_path_end_error") + separator; //$NON-NLS-1$
      }
      else{
        result = true;
      }
    }
    setErrorMessage( errorMessage );
    return result;
  }

  public void createControl( final Composite parent ) {
    IPreferenceStore prefStore = eu.geclipse.ui.internal.Activator.getDefault().getPreferenceStore();
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    GridData gData = new GridData();
    Label mntPointLabel = new Label(mainComp, SWT.LEFT);
    mntPointLabel.setText( Messages.getString("LocalConnectionWizardPage.mount_point_label") ); //$NON-NLS-1$
    gData.horizontalAlignment = GridData.BEGINNING;
    mntPointLabel.setLayoutData( gData );
    
    gData = new GridData(GridData.FILL_HORIZONTAL);
    this.mntPointCombo = new StoredCombo( mainComp, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    // host.setText( "test" );
    this.mntPointCombo.setPreferences( prefStore, KEY_MNTPOINS_ID );
    this.mntPointCombo.setLayoutData( gData );
    this.mntPointCombo.setToolTipText( Messages.getString("LocalConnectionWizardPage.mount_point_tooltip") ); //$NON-NLS-1$
    this.mntPointCombo.addModifyListener( this );
    
    setControl( mainComp );
    
  }

  public void modifyText( final ModifyEvent e ) {
    getContainer().updateButtons();
    getContainer().updateMessage();
  }  
}
