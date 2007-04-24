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

package eu.geclipse.ui.internal.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import eu.geclipse.core.Preferences;

/**
 * Preference page for the g-Eclipse related network settings.
 */
public class NetworkPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
  
  /**
   * UI control to edit the connection timeout setting.
   */
  protected Text connectionTimeoutText;
  
  /**
   * UI control to edit the proxy enabled setting.
   */
  protected Button proxyEnabledButton;
  
  /**
   * UI control to edit the proxy host setting.
   */
  protected Text proxyHostText;
  
  /**
   * UI control to edit the proxy port setting.
   */
  protected Text proxyPortText;
  
  /**
   * UI control to edit the proxy authentication required setting.
   */
  protected Button proxyAuthRequiredButton;
  
  /**
   * UI control to edit the proxy authentication login setting.
   */
  protected Text proxyAuthLoginText;
  
  /**
   * UI control to edit the proxy authentication password setting.
   */
  protected Text proxyAuthPwText;
  
  /**
   * Construct a new network preference page.
   */
  public NetworkPreferencePage() {
    super();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    // Nothing to do
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
  @Override
  public boolean performOk() {
    
    checkState();
    
    if ( isValid() ) {
    
      int connectionTimeout = Integer.parseInt( this.connectionTimeoutText.getText() );
      boolean proxyEnabled = this.proxyEnabledButton.getSelection();
      String proxyHost = this.proxyHostText.getText();
      int proxyPort;
      try {
        proxyPort = Integer.valueOf( this.proxyPortText.getText() ).intValue();
      } catch ( NumberFormatException nfExc ) {
        proxyPort = 0;
      }
      boolean proxyAuthRequired = this.proxyAuthRequiredButton.getSelection();
      String proxyAuthLogin = this.proxyAuthLoginText.getText();
      String proxyAuthPw = this.proxyAuthPwText.getText();

      Preferences.setConnectionTimeout( connectionTimeout );
      Preferences.setProxyEnabled( proxyEnabled );
      Preferences.setProxyHost( proxyHost );
      Preferences.setProxyPort( proxyPort );
      Preferences.setProxyAuthenticationRequired( proxyAuthRequired );
      Preferences.setProxyAuthenticationLogin( proxyAuthLogin );
      Preferences.setProxyAuthenticationPassword( proxyAuthPw );
      
      Preferences.save();
      
    }
    
    return super.performOk();
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
   */
  @Override
  protected void performDefaults() {
    
    int connectionTimeout = Preferences.getDefaultConnectionTimeout();
    boolean proxyEnable = Preferences.getDefaultProxyEnabled();
    String proxyHost = Preferences.getDefaultProxyHost();
    int proxyPort = Preferences.getDefaultProxyPort();
    boolean proxyAuthRequired = Preferences.getProxyAuthenticationRequired();
    String proxyAuthLogin = Preferences.getProxyAuthenticationLogin();
    String proxyAuthPw = Preferences.getProxyAuthenticationPassword();
    
    this.connectionTimeoutText.setText( String.valueOf( connectionTimeout ) );
    this.proxyEnabledButton.setSelection( proxyEnable );
    this.proxyHostText.setText( proxyHost );
    this.proxyPortText.setText( isValidPort( proxyPort ) ? String.valueOf( proxyPort ) : "" ); //$NON-NLS-1$
    this.proxyAuthRequiredButton.setSelection( proxyAuthRequired );
    this.proxyAuthLoginText.setText( proxyAuthLogin );
    this.proxyAuthPwText.setText( proxyAuthPw );
    
    updateUI();
    super.performDefaults();
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    
    initializeDialogUnits( parent );
    GridData gData;
    
    Group connectionGroup = new Group( parent, SWT.SHADOW_NONE );
    connectionGroup.setLayout( new GridLayout( 2, false ) );
    connectionGroup.setText( Messages.getString("NetworkPreferencePage.connection_group_title") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.verticalAlignment = GridData.BEGINNING;
    connectionGroup.setLayoutData( gData );
    
    Label connectionTimeoutLabel = new Label( connectionGroup, SWT.NONE );
    connectionTimeoutLabel.setText( Messages.getString("NetworkPreferencePage.connection_timeout_label") ); //$NON-NLS-1$
    gData = new GridData();
    connectionTimeoutLabel.setLayoutData( gData );
    
    this.connectionTimeoutText = new Text( connectionGroup, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.connectionTimeoutText.setLayoutData( gData );
    
    Group proxyGroup = new Group( parent, SWT.SHADOW_NONE );
    proxyGroup.setLayout( new GridLayout( 2, false ) );
    proxyGroup.setText( Messages.getString( "NetworkPreferencePage.proxy_group_title" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.verticalAlignment = GridData.BEGINNING;
    proxyGroup.setLayoutData( gData );
    
    this.proxyEnabledButton = new Button( proxyGroup, SWT.CHECK );
    this.proxyEnabledButton.setText( Messages.getString( "NetworkPreferencePage.proxy_enable_button" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    this.proxyEnabledButton.setLayoutData( gData );
    
    Label proxyServerLabel = new Label( proxyGroup, SWT.NONE );
    proxyServerLabel.setText( Messages.getString( "NetworkPreferencePage.proxy_server_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    proxyServerLabel.setLayoutData( gData );
    
    this.proxyHostText = new Text( proxyGroup, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.proxyHostText.setLayoutData( gData );
    
    Label proxyPortLabel = new Label( proxyGroup, SWT.NONE );
    proxyPortLabel.setText( Messages.getString( "NetworkPreferencePage.proxy_port_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    proxyPortLabel.setLayoutData( gData );
    
    this.proxyPortText = new Text( proxyGroup, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.proxyPortText.setLayoutData( gData );
    
    this.proxyAuthRequiredButton = new Button( proxyGroup, SWT.CHECK );
    this.proxyAuthRequiredButton.setText( Messages.getString("NetworkPreferencePage.proxy_auth_required_button") ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    this.proxyAuthRequiredButton.setLayoutData( gData );
    
    Label proxyAuthLoginLabel = new Label( proxyGroup, SWT.NONE );
    proxyAuthLoginLabel.setText( Messages.getString("NetworkPreferencePage.proxy_auth_login_text") ); //$NON-NLS-1$
    gData = new GridData();
    proxyAuthLoginLabel.setLayoutData( gData );
    
    this.proxyAuthLoginText = new Text( proxyGroup, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.proxyAuthLoginText.setLayoutData( gData );
    
    Label proxyAuthPwLabel = new Label( proxyGroup, SWT.NONE );
    proxyAuthPwLabel.setText( Messages.getString("NetworkPreferencePage.proxy_auth_pw_text") ); //$NON-NLS-1$
    gData = new GridData();
    proxyAuthPwLabel.setLayoutData( gData );
    
    this.proxyAuthPwText = new Text( proxyGroup, SWT.BORDER | SWT.PASSWORD );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.proxyAuthPwText.setLayoutData( gData );
    
    Label spacer = new Label( parent, SWT.NONE );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessVerticalSpace = true;
    spacer.setLayoutData(gData);
    
    this.connectionTimeoutText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        checkState();
      }
    } );
    this.proxyEnabledButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        updateUI();
      }
    } );
    this.proxyEnabledButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        checkState();
      }
    } );
    this.proxyHostText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        checkState();
      }
    } );
    this.proxyPortText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        checkState();
      }
    } );
    this.proxyAuthRequiredButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        updateUI();
      }
    } );
    this.proxyAuthRequiredButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        checkState();
      }
    } );
    this.proxyAuthLoginText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        checkState();
      }
    } );
    this.proxyAuthPwText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        checkState();
      }
    } );
    
    
    initializeValues();
    checkState();
    
    return parent;
    
  }
  
  /**
   * Initialize the controls with the values from the core Preferences.
   * 
   * @see Preferences
   */
  protected void initializeValues() {
    
    int connectionTimeout = Preferences.getConnectionTimeout();
    boolean proxyEnable = Preferences.getProxyEnabled();
    String proxyHost = Preferences.getProxyHost();
    int proxyPort = Preferences.getProxyPort();
    boolean proxyAuthRequired = Preferences.getProxyAuthenticationRequired();
    String proxyAuthLogin = Preferences.getProxyAuthenticationLogin();
    String proxyAuthPw = Preferences.getProxyAuthenticationPassword();
    
    this.connectionTimeoutText.setText( String.valueOf( connectionTimeout ) );
    this.proxyEnabledButton.setSelection( proxyEnable );
    this.proxyHostText.setText( proxyHost );
    this.proxyPortText.setText( isValidPort( proxyPort ) ? String.valueOf( proxyPort ) : "" ); //$NON-NLS-1$
    this.proxyAuthRequiredButton.setSelection( proxyAuthRequired );
    this.proxyAuthLoginText.setText( proxyAuthLogin );
    this.proxyAuthPwText.setText( proxyAuthPw );
    
    updateUI();
    
  }
  
  /**
   * Update the UI controls, i.e. set the enabled flag of the controls according
   * to their values.
   */
  protected void updateUI() {
    boolean proxyEnabled = this.proxyEnabledButton.getSelection();
    boolean proxyAuthRequired = this.proxyAuthRequiredButton.getSelection();
    this.proxyHostText.setEnabled( proxyEnabled );
    this.proxyPortText.setEnabled( proxyEnabled );
    this.proxyAuthRequiredButton.setEnabled( proxyEnabled );
    this.proxyAuthLoginText.setEnabled( proxyEnabled & proxyAuthRequired );
    this.proxyAuthPwText.setEnabled( proxyEnabled & proxyAuthRequired );
  }
  
  /**
   * validate the settings of the UI controls.
   */
  protected void checkState() {
    String errorMessage = null;
    if ( this.proxyEnabledButton.getSelection() ) {
      if ( this.proxyHostText.getText().length() == 0 ) {
        errorMessage = Messages.getString( "NetworkPreferencePage.no_server_error" ); //$NON-NLS-1$
      } else if ( !isValidPort() ) {
        errorMessage = Messages.getString( "NetworkPreferencePage.wrong_port_error" ); //$NON-NLS-1$
      } else if ( this.proxyAuthRequiredButton.getSelection() ) {
        if ( this.proxyAuthLoginText.getText().length() == 0 ) {
          errorMessage = Messages.getString("NetworkPreferencePage.login_empty_error"); //$NON-NLS-1$
        } else if ( this.proxyAuthPwText.getText().length() == 0) {
          errorMessage = Messages.getString("NetworkPreferencePage.pw_empty_error"); //$NON-NLS-1$
        }
      }
    }
    try {
      Integer.parseInt( this.connectionTimeoutText.getText() );
    } catch ( NumberFormatException nfExc ) {
      errorMessage = Messages.getString("NetworkPreferencePage.connection_timeout_value_error"); //$NON-NLS-1$
    }
    setErrorMessage( errorMessage );
    setValid( errorMessage == null );
  }
  
  /**
   * Test if the port specified in the corresponding UI element is valid.
   * 
   * @return True if the port is valid.
   */
  protected boolean isValidPort() {
    boolean valid = false;
    try {
      int proxyPort = Integer.valueOf( this.proxyPortText.getText() ).intValue();
      valid = isValidPort( proxyPort );
    } catch ( NumberFormatException nfExc ) {
      // Nothing to do here since port is simply invalid
    }
    return valid;
  }
  
  /**
   * Check if the specified port is valid.
   * 
   * @param port The integer to be tested.
   * @return True if the port is larger than 0 and smaller or equal than 65535.
   */
  protected boolean isValidPort( final int port ) {
    return ( port > 0 ) && ( port <= 65535 ); 
  }
 
}
