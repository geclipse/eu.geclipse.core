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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.xerox.amazonws.ec2.AvailabilityZone;
import com.xerox.amazonws.ec2.InstanceType;
import com.xerox.amazonws.ec2.KeyPairInfo;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.launch.IEC2LaunchConfigurationConstants;
import eu.geclipse.aws.ec2.op.EC2OpDescribeAvailabilityZones;
import eu.geclipse.aws.ec2.op.EC2OpDescribeKeypairs;
import eu.geclipse.aws.ec2.op.IOperation;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.op.OperationSet;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.ec2.ui.wizards.SecurityGroupSelectionWizard;

/**
 * This Tab shows the main configuration directives from an {@link EC2AMIImage}.
 * The properties displayed are:
 * <ul>
 * <li> {@link IEC2LaunchConfigurationConstants#AMI_ID}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#INSTANCE_TYPE}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#KEY_NAME}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#MAX_COUNT}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#MIN_COUNT}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#SECURITY_GROUP}</li>
 * <li> {@link IEC2LaunchConfigurationConstants#ZONE}</li>
 * </ul>
 * Additionally the tab allows to load some preset values right from the EC2
 * infrastructure. For this to work, the user has to provide his access
 * credentials.
 * 
 * @author Moritz Post
 */
public class EC2MainTab extends AbstractLaunchConfigurationTab
  implements KeyListener, SelectionListener
{

  /** The {@link Text} widget for the AMI id. */
  private Text amiIdText;

  /** The {@link Text} widget for the minimum number of AMIs to launch. */
  private Text minCountText;

  /** The {@link Text} widget for the maximum number of AMIs to launch. */
  private Text maxCountText;

  /** The {@link Combo} widget for the name of the keypair to start the AMI with. */
  private Combo keyNameCombo;

  /**
   * The {@link Combo} widget to select the availability zone to start the AMI
   * in.
   */
  private Combo zoneCombo;

  /** The launch configuration from which die dialog was initialized. */
  private ILaunchConfiguration launchConfiguration;

  /** The currently selected instance type. */
  private Combo comboInstanceType;

  /** The list to display the security groups in. */
  private org.eclipse.swt.widgets.List listSecurityGroups;

  /** A {@link Text} widget to hold the aws access key. */
  private Text awsAccessIdText;

  public void createControl( final Composite parent ) {
    GridData gData;

    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );

    // image group
    Group awsGroup = new Group( mainComp, SWT.NONE );
    awsGroup.setLayout( new GridLayout( 2, false ) );
    awsGroup.setText( Messages.getString("EC2MainTab.group_aws_credentials") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false, 2, 0 );
    awsGroup.setLayoutData( gData );

    // text field for aws access id
    Label awsAccessIdlabel = new Label( awsGroup, SWT.LEFT );
    awsAccessIdlabel.setText( Messages.getString("EC2MainTab.label_aws_access_id") ); //$NON-NLS-1$
    awsAccessIdlabel.setLayoutData( new GridData() );

    this.awsAccessIdText = new Text( awsGroup, SWT.LEFT
                                               | SWT.SINGLE
                                               | SWT.BORDER );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    this.awsAccessIdText.setLayoutData( gData );
    this.awsAccessIdText.addKeyListener( this );

    // image group
    Group amiGroup = new Group( mainComp, SWT.NONE );
    amiGroup.setLayout( new GridLayout( 6, false ) );
    amiGroup.setText( Messages.getString( "EC2MainTab.group_ami_title" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 2;
    amiGroup.setLayoutData( gData );

    // ami id text
    Label amiIdLabel = new Label( amiGroup, SWT.LEFT );
    amiIdLabel.setText( Messages.getString( "EC2MainTab.label_ami_id" ) ); //$NON-NLS-1$
    amiIdLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_ami_id" ) ); //$NON-NLS-1$
    amiIdLabel.setLayoutData( new GridData() );

    this.amiIdText = new Text( amiGroup, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.amiIdText.setLayoutData( gData );
    this.amiIdText.addKeyListener( this );

    // min count
    Label minCountLabel = new Label( amiGroup, SWT.RIGHT );
    minCountLabel.setText( Messages.getString( "EC2MainTab.label_min" ) ); //$NON-NLS-1$
    minCountLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_min" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.END, SWT.CENTER, false, false );
    gData.horizontalIndent = 5;
    minCountLabel.setLayoutData( gData );

    this.minCountText = new Text( amiGroup, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.minCountText.setText( Messages.getString( "EC2MainTab.text_min_default" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.END, SWT.CENTER, false, false );
    gData.widthHint = 30;
    this.minCountText.setLayoutData( gData );
    this.minCountText.addKeyListener( this );

    // max count
    Label maxCountLabel = new Label( amiGroup, SWT.RIGHT );
    maxCountLabel.setText( Messages.getString( "EC2MainTab.label_max" ) ); //$NON-NLS-1$
    maxCountLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_max" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.END, SWT.CENTER, false, false );
    maxCountLabel.setLayoutData( gData );

    this.maxCountText = new Text( amiGroup, SWT.LEFT | SWT.SINGLE | SWT.BORDER );
    this.maxCountText.setText( Messages.getString( "EC2MainTab.text_max_default" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.END, SWT.CENTER, false, false );
    gData.widthHint = 30;
    this.maxCountText.setLayoutData( gData );
    this.maxCountText.addKeyListener( this );

    // key name
    Label keyNameLabel = new Label( amiGroup, SWT.LEFT );
    keyNameLabel.setText( Messages.getString( "EC2MainTab.label_key_name" ) ); //$NON-NLS-1$
    keyNameLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_key_name" ) ); //$NON-NLS-1$
    gData = new GridData();
    keyNameLabel.setLayoutData( gData );

    this.keyNameCombo = new Combo( amiGroup, SWT.LEFT
                                             | SWT.SINGLE
                                             | SWT.BORDER
                                             | SWT.DROP_DOWN );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 5;
    this.keyNameCombo.setLayoutData( gData );
    this.keyNameCombo.addKeyListener( this );
    this.keyNameCombo.addSelectionListener( this );

    // image group
    Group imageGroup = new Group( mainComp, SWT.NONE );
    imageGroup.setLayout( new GridLayout( 2, false ) );
    imageGroup.setText( Messages.getString( "EC2MainTab.group_image_title" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 2;
    imageGroup.setLayoutData( gData );

    // Instance Type
    Label instanceTypeLabel = new Label( imageGroup, SWT.LEFT );
    instanceTypeLabel.setText( Messages.getString( "EC2MainTab.label_instance_type" ) ); //$NON-NLS-1$
    instanceTypeLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_instance_type" ) ); //$NON-NLS-1$
    gData = new GridData();
    instanceTypeLabel.setLayoutData( gData );

    this.comboInstanceType = new Combo( imageGroup, SWT.LEFT
                                                    | SWT.READ_ONLY
                                                    | SWT.SINGLE
                                                    | SWT.BORDER
                                                    | SWT.DROP_DOWN );
    this.comboInstanceType.addSelectionListener( this );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.comboInstanceType.setLayoutData( gData );

    String[] instanceTypesReadable = new String[]{
      Messages.getString( "EC2MainTab.instance_type_small" ), //$NON-NLS-1$
      Messages.getString( "EC2MainTab.instance_type_large" ), //$NON-NLS-1$
      Messages.getString( "EC2MainTab.instance_type_extra_large" ), //$NON-NLS-1$
      Messages.getString( "EC2MainTab.instance_type_high_cpu_medium" ), //$NON-NLS-1$
      Messages.getString( "EC2MainTab.instance_type_high_cpu_large" ) //$NON-NLS-1$
    };

    InstanceType[] instanceTypes = InstanceType.values();

    for( int i = 0; i < instanceTypes.length; i++ ) {
      instanceTypesReadable[ i ] = instanceTypesReadable[ i ] + " (" //$NON-NLS-1$
                                   + instanceTypes[ i ].getTypeId()
                                   + ")"; //$NON-NLS-1$
    }

    this.comboInstanceType.setItems( instanceTypesReadable );

    // security Group
    Label securityGroupLabel = new Label( imageGroup, SWT.LEFT );
    securityGroupLabel.setText( Messages.getString( "EC2MainTab.label_security_group" ) ); //$NON-NLS-1$
    securityGroupLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_security_group" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.LEFT, SWT.TOP, false, false );
    securityGroupLabel.setLayoutData( gData );

    Composite secGroup = new Composite( imageGroup, SWT.NONE );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    secGroup.setLayoutData( gData );
    GridLayout gridLayout = new GridLayout( 2, false );
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 0;
    secGroup.setLayout( gridLayout );

    this.listSecurityGroups = new org.eclipse.swt.widgets.List( secGroup,
                                                                SWT.BORDER
                                                                    | SWT.SINGLE
                                                                    | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.heightHint = 100;
    gData.grabExcessHorizontalSpace = true;
    this.listSecurityGroups.setLayoutData( gData );

    Button buttonEditSecGr = new Button( secGroup, SWT.NONE );
    buttonEditSecGr.setText( Messages.getString( "EC2MainTab.button_security_group_edit" ) ); //$NON-NLS-1$
    buttonEditSecGr.setLayoutData( new GridData( SWT.CENTER,
                                                 SWT.TOP,
                                                 false,
                                                 false ) );
    buttonEditSecGr.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        String awsAccessId = EC2MainTab.this.awsAccessIdText.getText().trim();
        SecurityGroupSelectionWizard securityGroupSelectionWizard = new SecurityGroupSelectionWizard( awsAccessId );

        List<String> groupsList = Arrays.asList( EC2MainTab.this.listSecurityGroups.getItems() );
        securityGroupSelectionWizard.setSecurityGroups( groupsList );
        WizardDialog wizardDialog = new WizardDialog( getShell(),
                                                      securityGroupSelectionWizard );
        int code = wizardDialog.open();

        if( code == Window.OK ) {
          List<String> secGroups = securityGroupSelectionWizard.getSecurityGroups();
          EC2MainTab.this.listSecurityGroups.removeAll();

          for( String secGroup : secGroups ) {
            EC2MainTab.this.listSecurityGroups.add( secGroup );
          }
          updateLaunchConfigurationDialog();
        }
      }
    } );

    // zone
    Label zoneLabel = new Label( imageGroup, SWT.LEFT );
    zoneLabel.setText( Messages.getString( "EC2MainTab.label_zone" ) ); //$NON-NLS-1$
    zoneLabel.setToolTipText( Messages.getString( "EC2MainTab.tooltip_zone" ) ); //$NON-NLS-1$
    gData = new GridData();
    zoneLabel.setLayoutData( gData );

    this.zoneCombo = new Combo( imageGroup, SWT.LEFT
                                            | SWT.SINGLE
                                            | SWT.BORDER
                                            | SWT.DROP_DOWN );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.zoneCombo.setLayoutData( gData );
    this.zoneCombo.addKeyListener( this );
    this.zoneCombo.addSelectionListener( this );

    // populate link
    Link linkPopulate = new Link( mainComp, SWT.NONE );
    linkPopulate.setText( Messages.getString( "EC2MainTab.link_populate_from_ec2" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.RIGHT, SWT.CENTER, true, false, 2, 0 );
    linkPopulate.setLayoutData( gData );
    linkPopulate.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        EC2Registry ec2Registry = EC2Registry.getRegistry();
        IEC2 ec2 = ec2Registry.getEC2( EC2MainTab.this.awsAccessIdText.getText()
          .trim() );
        populateFromEC2( ec2 );
      }

    } );
    setControl( mainComp );
  }

  public String getName() {
    return Messages.getString( "EC2MainTab.tab_title" ); //$NON-NLS-1$
  }

  public void initializeFrom( final ILaunchConfiguration configuration ) {
    this.launchConfiguration = configuration;

    String emptyString = ""; //$NON-NLS-1$
    String oneString = "1"; //$NON-NLS-1$
    try {
      this.amiIdText.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.AMI_ID,
                                                          emptyString ) );
      this.awsAccessIdText.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.AWS_ACCESS_ID,
                                                                emptyString ) );
      this.minCountText.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.MIN_COUNT,
                                                             oneString ) );
      this.maxCountText.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.MAX_COUNT,
                                                             oneString ) );
      this.keyNameCombo.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.KEY_NAME,
                                                             emptyString ) );
      List<?> securityGroupsList = configuration.getAttribute( IEC2LaunchConfigurationConstants.SECURITY_GROUP,
                                                               new ArrayList<String>() );
      String[] securityGroupsArray = new String[ securityGroupsList.size() ];
      int j = 0;
      for( Object object : securityGroupsList ) {
        securityGroupsArray[ j ] = object.toString();
        j++;
      }
      this.listSecurityGroups.setItems( securityGroupsArray );
      this.zoneCombo.setText( configuration.getAttribute( IEC2LaunchConfigurationConstants.ZONE,
                                                          emptyString ) );
      String instanceTypeString = configuration.getAttribute( IEC2LaunchConfigurationConstants.INSTANCE_TYPE,
                                                              emptyString );

      // find index of selected instance type
      InstanceType[] instanceTypes = InstanceType.values();

      int instanceTypeIndex = 0;
      for( int i = 0; i < instanceTypes.length; i++ ) {
        if( instanceTypes[ i ].getTypeId().equals( instanceTypeString ) ) {
          instanceTypeIndex = i;
          break;
        }
      }

      this.comboInstanceType.select( instanceTypeIndex );

    } catch( CoreException coreEx ) {
      Activator.log( "Could not prepopulate launch dialog", coreEx ); //$NON-NLS-1$
    }

  }

  public void performApply( final ILaunchConfigurationWorkingCopy configuration )
  {
    configuration.setAttribute( IEC2LaunchConfigurationConstants.AMI_ID,
                                this.amiIdText.getText().trim() );
    configuration.setAttribute( IEC2LaunchConfigurationConstants.AWS_ACCESS_ID,
                                this.awsAccessIdText.getText().trim() );
    configuration.setAttribute( IEC2LaunchConfigurationConstants.MIN_COUNT,
                                this.minCountText.getText().trim() );
    configuration.setAttribute( IEC2LaunchConfigurationConstants.MAX_COUNT,
                                this.maxCountText.getText().trim() );
    configuration.setAttribute( IEC2LaunchConfigurationConstants.KEY_NAME,
                                this.keyNameCombo.getText().trim() );
    String[] secGroups = this.listSecurityGroups.getItems();
    List<String> secGroupsList = Arrays.asList( secGroups );
    configuration.setAttribute( IEC2LaunchConfigurationConstants.SECURITY_GROUP,
                                secGroupsList );
    configuration.setAttribute( IEC2LaunchConfigurationConstants.ZONE,
                                this.zoneCombo.getText().trim() );
    int selectionIndex = this.comboInstanceType.getSelectionIndex();
    InstanceType[] instanceTypes = InstanceType.values();
    configuration.setAttribute( IEC2LaunchConfigurationConstants.INSTANCE_TYPE,
                                instanceTypes[ selectionIndex ].getTypeId() );
  }

  public void setDefaults( final ILaunchConfigurationWorkingCopy configuration )
  {
    // nothing to do here
  }

  /**
   * Connects with the EC2 service and fetches the data to prepopulate the input
   * fields.
   * 
   * @param ec2 the {@link IEC2} instance to use for EC2 interaction
   */
  private void populateFromEC2( final IEC2 ec2 ) {
    try {

      ILaunchConfigurationDialog lcd = getLaunchConfigurationDialog();
      lcd.run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          monitor.beginTask( Messages.getString( "EC2MainTab.monitor_get_ec2_presets_title" ), //$NON-NLS-1$
                             2 );

          EC2OpDescribeAvailabilityZones opDescZones = new EC2OpDescribeAvailabilityZones( ec2 );
          EC2OpDescribeKeypairs opDescKeypairs = new EC2OpDescribeKeypairs( ec2 );
          OperationSet operationSet = new OperationSet();
          operationSet.addOp( opDescZones );
          operationSet.addOp( opDescKeypairs );
          OperationExecuter operationExecuter = new OperationExecuter();
          operationExecuter.execOpGroup( operationSet );
          monitor.worked( 1 );

          for( IOperation op : operationSet.getOps() ) {
            if( op.getException() != null ) {
              monitor.done();
              throw new InvocationTargetException( op.getException() );
            }
          }

          // setup availability zones
          String zoneString = EC2MainTab.this.zoneCombo.getText().trim();
          EC2MainTab.this.zoneCombo.removeAll();
          List<AvailabilityZone> resultZones = opDescZones.getResult();
          int i = 0;
          int select = -1;
          for( AvailabilityZone zone : resultZones ) {
            EC2MainTab.this.zoneCombo.add( zone.getName() );
            if( zone.getName().equals( zoneString ) ) {
              select = i;
            }
            i++;
          }
          if( select == -1 ) {
            if( zoneString.length() != 0 ) {
              EC2MainTab.this.zoneCombo.add( zoneString, 0 );
            }
            select = 0;
          }
          EC2MainTab.this.zoneCombo.select( select );

          // setup keypairs
          String keyNameString = EC2MainTab.this.keyNameCombo.getText().trim();
          EC2MainTab.this.keyNameCombo.removeAll();
          List<KeyPairInfo> resultKeypairs = opDescKeypairs.getResult();
          i = 0;
          select = -1;
          for( KeyPairInfo keyPairInfo : resultKeypairs ) {
            EC2MainTab.this.keyNameCombo.add( keyPairInfo.getKeyName() );
            if( keyPairInfo.getKeyName().equals( keyNameString ) ) {
              select = i;
            }
            i++;
          }
          if( select == -1 ) {
            if( keyNameString.length() != 0 ) {
              EC2MainTab.this.keyNameCombo.add( keyNameString, 0 );
            }
            select = 0;
          }
          EC2MainTab.this.keyNameCombo.select( select );

          monitor.done();
        }
      } );
    } catch( Exception ex ) {
      Activator.log( "Could not populate fields in Main EC2 Launch Tab", ex ); //$NON-NLS-1$
    }
  }

  public void keyPressed( final KeyEvent e ) {
    // nothing to do here
  }

  public void keyReleased( final KeyEvent e ) {
    try {
      performApply( this.launchConfiguration.getWorkingCopy() );
      updateLaunchConfigurationDialog();
    } catch( CoreException coreEx ) {
      Activator.log( "Problems applying the launch configuration", coreEx ); //$NON-NLS-1$
    }
  }

  @Override
  public boolean isValid( final ILaunchConfiguration launchConfig ) {
    String error = null;

    if( this.awsAccessIdText.getText().trim().length() == 0 ) {
      error = Messages.getString("EC2MainTab.error_aws_access_id_required"); //$NON-NLS-1$
    }
    if( this.amiIdText.getText().trim().length() == 0 ) {
      error = Messages.getString( "EC2MainTab.error_validation_no_ami" ); //$NON-NLS-1$
    }
    int minNum = 0;
    try {
      minNum = Integer.parseInt( this.minCountText.getText() );
    } catch( NumberFormatException e ) {
      error = Messages.getString( "EC2MainTab.error_validation_no_valid_min" ); //$NON-NLS-1$
    }
    if( error == null && minNum <= 0 ) {
      error = Messages.getString( "EC2MainTab.error_validation_min_smaller_than_1" ); //$NON-NLS-1$
    }
    int maxNum = 0;
    try {
      maxNum = Integer.parseInt( this.maxCountText.getText() );
    } catch( NumberFormatException e ) {
      error = Messages.getString( "EC2MainTab.error_validation_no_valid_max" ); //$NON-NLS-1$
    }
    if( error == null && maxNum <= 0 ) {
      error = Messages.getString( "EC2MainTab.error_validation_max_smaller_than_1" ); //$NON-NLS-1$
    }

    if( error == null && minNum > maxNum ) {
      error = Messages.getString( "EC2MainTab.error_validation_min_larger_than_max" ); //$NON-NLS-1$
    }

    if( error != null ) {
      setErrorMessage( error );
      return false;
    }
    setErrorMessage( null );
    return true;
  }

  public void widgetDefaultSelected( final SelectionEvent e ) {
    // nothing to do here
  }

  public void widgetSelected( final SelectionEvent e ) {
    updateLaunchConfigurationDialog();
    try {
      performApply( this.launchConfiguration.getWorkingCopy() );
    } catch( CoreException coreEx ) {
      Activator.log( "Problems applying the launch configuration", coreEx ); //$NON-NLS-1$
    }
  }
}
