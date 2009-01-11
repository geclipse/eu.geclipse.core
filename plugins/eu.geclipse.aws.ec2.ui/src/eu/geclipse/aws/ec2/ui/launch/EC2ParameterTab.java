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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.launch;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.aws.ec2.launch.IEC2LaunchConfigurationConstants;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * The parameter tab takes user defined data to send as a parameter to a
 * launching AMI. The user data can either be in {@link String} form or as a
 * binary file (max. 16K).
 * 
 * @author Moritz Post
 */
public class EC2ParameterTab extends AbstractLaunchConfigurationTab
  implements Listener, ModifyListener
{

  /** The maximum file size for the user data file in EC2. */
  private static final int USER_DATA_FILE_SIZE_LIMIT = 16000;

  /**
   * The widget to hold special user configuration parameter for the AMI
   * instance.
   */
  private Text userDataText;

  /** {@link StoredCombo} for the file path of the user data file. */
  private StoredCombo userDataFileCombo;

  /**
   * A {@link Composite} holding the widgets used to input a user data file
   * path.
   */
  private Composite userDataFileComp;

  /**
   * A {@link Composite} holding the widgets used to input the user data via
   * text.
   */
  private Composite userDataStringComp;

  /** The radio button for user data in text form. */
  private Button userDataStringRadio;

  /** The radio button for user data in binary file form. */
  private Button userDataFileRadio;

  /** The button opening the {@link FileDialog} to select the user data file. */
  private Button userDataFileButton;

  /** The launch configuration from which die dialog was initialized. */
  private ILaunchConfiguration launchConfiguration;

  public void createControl( final Composite parent ) {

    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );

    // machine group
    Group machineGroup = new Group( mainComp, SWT.NONE );
    machineGroup.setLayout( new GridLayout( 1, false ) );
    machineGroup.setText( Messages.getString( "EC2ParameterTab.group_machine_title" ) ); //$NON-NLS-1$
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    machineGroup.setLayoutData( gData );

    // user data string
    this.userDataStringRadio = new Button( machineGroup, SWT.RADIO );
    this.userDataStringRadio.setText( Messages.getString( "EC2ParameterTab.label_user_data_via_text" ) ); //$NON-NLS-1$
    this.userDataStringRadio.setLayoutData( new GridData( SWT.LEFT,
                                                          SWT.CENTER,
                                                          true,
                                                          false ) );
    this.userDataStringRadio.addListener( SWT.Selection, this );
    this.userDataStringRadio.setSelection( true );

    this.userDataStringComp = new Composite( machineGroup, SWT.NONE );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.horizontalIndent = EC2LaunchConfigurationTabGroup.RADIO_INDENT;
    this.userDataStringComp.setLayoutData( gData );
    this.userDataStringComp.setLayout( new GridLayout( 2, false ) );

    Label userDataLabel = new Label( this.userDataStringComp, SWT.LEFT );
    userDataLabel.setText( Messages.getString( "EC2ParameterTab.label_user_data" ) ); //$NON-NLS-1$
    userDataLabel.setToolTipText( Messages.getString( "EC2ParameterTab.tooltip_user_data" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.LEFT, SWT.TOP, false, false );
    userDataLabel.setLayoutData( gData );

    this.userDataText = new Text( this.userDataStringComp, SWT.LEFT
                                                           | SWT.MULTI
                                                           | SWT.BORDER
                                                           | SWT.WRAP
                                                           | SWT.V_SCROLL );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.heightHint = 60;
    this.userDataText.setLayoutData( gData );
    this.userDataText.addModifyListener( this );

    // user data file
    this.userDataFileRadio = new Button( machineGroup, SWT.RADIO );
    this.userDataFileRadio.setText( Messages.getString( "EC2ParameterTab.label_user_data_via_file" ) ); //$NON-NLS-1$
    this.userDataFileRadio.setLayoutData( new GridData( SWT.LEFT,
                                                        SWT.CENTER,
                                                        true,
                                                        false ) );
    this.userDataFileRadio.addListener( SWT.Selection, this );

    this.userDataFileComp = new Composite( machineGroup, SWT.NONE );
    gData = new GridData( SWT.FILL, SWT.LEFT, true, false );
    gData.horizontalIndent = EC2LaunchConfigurationTabGroup.RADIO_INDENT;
    this.userDataFileComp.setLayoutData( gData );
    this.userDataFileComp.setLayout( new GridLayout( 3, false ) );

    Label userDataFileLabel = new Label( this.userDataFileComp, SWT.LEFT );
    userDataFileLabel.setText( Messages.getString( "EC2ParameterTab.label_user_data_file" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.LEFT, SWT.CENTER, false, false );
    userDataFileLabel.setLayoutData( gData );

    this.userDataFileCombo = new StoredCombo( this.userDataFileComp, SWT.LEFT );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    this.userDataFileCombo.setLayoutData( gData );
    this.userDataFileCombo.addModifyListener( this );

    this.userDataFileButton = new Button( this.userDataFileComp, SWT.CENTER );
    this.userDataFileButton.setText( Messages.getString( "EC2ParameterTab.button_browse_file" ) ); //$NON-NLS-1$
    this.userDataFileButton.addListener( SWT.Selection, this );
    setEnabled( this.userDataFileComp, false );

    setControl( mainComp );
  }

  public String getName() {
    return Messages.getString("EC2ParameterTab.tab_title"); //$NON-NLS-1$
  }

  public void initializeFrom( final ILaunchConfiguration configuration ) {
    this.launchConfiguration = configuration;
    try {
      this.userDataText.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.USER_DATA,
                                                             "" ) ); //$NON-NLS-1$
    } catch( CoreException coreEx ) {
      Activator.log( "Could not prepopulate launch dialog", coreEx ); //$NON-NLS-1$
    }
  }

  public void performApply( final ILaunchConfigurationWorkingCopy configuration )
  {
    if( this.userDataStringRadio.getSelection() ) {
      configuration.setAttribute( IEC2LaunchConfigurationConstants.USER_DATA,
                                  this.userDataText.getText() );
    } else if( this.userDataFileRadio.getSelection() ) {
      configuration.setAttribute( IEC2LaunchConfigurationConstants.USER_DATA_FILE_PATH,
                                  this.userDataFileCombo.getText() );
    }
  }

  public void setDefaults( final ILaunchConfigurationWorkingCopy configuration )
  {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isValid( final ILaunchConfiguration launchConfig ) {
    String error = null;

    String userDataFilePath = this.userDataFileCombo.getText().trim();
    if( userDataFilePath.length() > 0 && this.userDataFileRadio.getSelection() )
    {
      File userDataFile = new File( userDataFilePath );

      if( !userDataFile.exists() ) {
        error = Messages.getString( "EC2ParameterTab.error_no_user_data_file" ); //$NON-NLS-1$
      } else if( userDataFile.length() > EC2ParameterTab.USER_DATA_FILE_SIZE_LIMIT )
      {
        error = Messages.getString( "EC2ParameterTab.error_user_data_file_to_big" ); //$NON-NLS-1$
      }
    }

    if( error != null ) {
      setErrorMessage( error );
      return false;
    }
    setErrorMessage( null );
    return true;
  }

  /**
   * Enables or disables a control. If the control is a composite all the
   * children are enabled/disable as well.
   * 
   * @param control the control to modify
   * @param enabled the status to apply
   */
  private void setEnabled( final Control control, final boolean enabled ) {
    if( control instanceof Composite ) {
      Control[] children = ( ( Composite )control ).getChildren();
      for( Control childControl : children ) {
        setEnabled( childControl, enabled );
      }
    }
    control.setEnabled( enabled );
  }

  public void handleEvent( final Event event ) {
    if( event.widget == this.userDataStringRadio ) {
      setEnabled( this.userDataStringComp, true );
      setEnabled( this.userDataFileComp, false );
    } else if( event.widget == this.userDataFileRadio ) {
      setEnabled( this.userDataStringComp, false );
      setEnabled( this.userDataFileComp, true );
    } else if( event.widget == this.userDataFileButton ) {
      FileDialog fileDialog = new FileDialog( getShell(), SWT.OPEN | SWT.SINGLE );
      fileDialog.setText( Messages.getString( "EC2ParameterTab.dialog_title_select_file_user_data" ) ); //$NON-NLS-1$
      String selectedFile = fileDialog.open();
      EC2ParameterTab.this.userDataFileCombo.setText( selectedFile );
    }
  }

  public void modifyText( final ModifyEvent e ) {
    try {
      performApply( this.launchConfiguration.getWorkingCopy() );
      updateLaunchConfigurationDialog();
    } catch( CoreException coreEx ) {
      Activator.log( "Problems applying the launch configuration", coreEx ); //$NON-NLS-1$
    }
  }
}
