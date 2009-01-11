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

package eu.geclipse.aws.ec2.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.GroupDescription.IpPermission;

import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.EC2SecurityGroup;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups;
import eu.geclipse.aws.ec2.op.EC2OpRevokeSecurityGroup;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link WizardPage} provides the form elements to modify the access
 * permissions of an EC2 security group.
 * 
 * @author Moritz Post
 */
public class SecurityGroupWizardPage extends WizardPage {

  /** Id of this Wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizard.securityGroup"; //$NON-NLS-1$

  /**
   * The security group containing the {@link GroupDescription}.
   */
  private EC2SecurityGroup securityGroup;

  /**
   * The table holding the permissions of the currently selected security group.
   */
  private Table permTable;

  /**
   * Creates a new security group wizard page and initiates the title,
   * description and image icon.
   * 
   * @param securityGroup the group to modify
   * @param awsAccessId the access id to identify the current user
   */
  public SecurityGroupWizardPage( final EC2SecurityGroup securityGroup ) {
    super( SecurityGroupWizardPage.WIZARD_PAGE_ID,
           Messages.getString( "SecurityGroupWizardPage.page_title" ) + securityGroup.getName(), //$NON-NLS-1$
           null );
    setDescription( Messages.getString( "SecurityGroupWizardPage.page_description" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/security_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );

    this.securityGroup = securityGroup;
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    mainComp.setLayoutData( gData );
    GridLayout gridLayout = new GridLayout( 2, false );
    mainComp.setLayout( gridLayout );

    this.permTable = new Table( mainComp, SWT.SINGLE
                                          | SWT.H_SCROLL
                                          | SWT.V_SCROLL
                                          | SWT.FULL_SELECTION
                                          | SWT.BORDER );
    this.permTable.setHeaderVisible( true );
    this.permTable.setLinesVisible( true );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.permTable.setLayoutData( gData );

    TableColumn tableColumnProtocol = new TableColumn( this.permTable, SWT.LEFT );
    tableColumnProtocol.setText( Messages.getString( "SecurityGroupWizardPage.tab_label_protcol" ) ); //$NON-NLS-1$
    tableColumnProtocol.setWidth( 80 );

    TableColumn tableColumnFrom = new TableColumn( this.permTable, SWT.LEFT );
    tableColumnFrom.setText( Messages.getString( "SecurityGroupWizardPage.tab_label_from" ) ); //$NON-NLS-1$
    tableColumnFrom.setWidth( 60 );

    TableColumn tableColumnTo = new TableColumn( this.permTable, SWT.LEFT );
    tableColumnTo.setText( Messages.getString( "SecurityGroupWizardPage.tab_label_to" ) ); //$NON-NLS-1$
    tableColumnTo.setWidth( 60 );

    TableColumn tableColumnSource = new TableColumn( this.permTable, SWT.LEFT );
    tableColumnSource.setText( Messages.getString( "SecurityGroupWizardPage.tab_label_source" ) ); //$NON-NLS-1$
    tableColumnSource.setWidth( 130 );

    Composite buttonComp = new Composite( mainComp, SWT.NONE );
    gData = new GridData( SWT.FILL, SWT.FILL, false, true );
    buttonComp.setLayoutData( gData );
    gridLayout = new GridLayout( 1, true );
    buttonComp.setLayout( gridLayout );

    Button addCidrButton = new Button( buttonComp, SWT.NONE );
    addCidrButton.setText( Messages.getString( "SecurityGroupWizardPage.button_add_cidr" ) ); //$NON-NLS-1$
    addCidrButton.setLayoutData( new GridData( SWT.FILL,
                                               SWT.CENTER,
                                               true,
                                               false ) );
    addCidrButton.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        GroupDescription groupDescription = SecurityGroupWizardPage.this.securityGroup.getGroupDescription();

        EC2Service ec2Service = SecurityGroupWizardPage.this.securityGroup.getEC2Service();
        AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )ec2Service.getParent();
        String awsAccessId = null;
        try {
          awsAccessId = awsVo.getProperties().getAwsAccessId();
        } catch( ProblemException problemEx ) {
          Activator.log( "Could not read aws vo properties", problemEx ); //$NON-NLS-1$
        }
        CIDRPermissionWizard cidrWizard = new CIDRPermissionWizard( groupDescription,
                                                                    awsAccessId );

        WizardDialog wizardDialog = new WizardDialog( getShell(), cidrWizard );
        int wizardStatus = wizardDialog.open();
        if( wizardStatus == Window.OK ) {
          refreshPermissionsTable();
        }
      }

    } );

    Button addGroupButton = new Button( buttonComp, SWT.NONE );
    addGroupButton.setText( Messages.getString( "SecurityGroupWizardPage.button_add_group" ) ); //$NON-NLS-1$
    addGroupButton.setLayoutData( new GridData( SWT.FILL,
                                                SWT.CENTER,
                                                true,
                                                false ) );
    addGroupButton.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        GroupDescription groupDescription = SecurityGroupWizardPage.this.securityGroup.getGroupDescription();

        EC2Service ec2Service = SecurityGroupWizardPage.this.securityGroup.getEC2Service();
        AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )ec2Service.getParent();
        String awsAccessId = null;
        try {
          awsAccessId = awsVo.getProperties().getAwsAccessId();
        } catch( ProblemException problemEx ) {
          Activator.log( "Could not read aws vo properties", problemEx ); //$NON-NLS-1$
        }
        GroupPermissionWizard groupPermWizard = new GroupPermissionWizard( groupDescription,
                                                                           awsAccessId );

        WizardDialog wizardDialog = new WizardDialog( getShell(),
                                                      groupPermWizard );
        int wizardStatus = wizardDialog.open();
        if( wizardStatus == Window.OK ) {
          refreshPermissionsTable();
        }
      }

    } );

    Button deleteButton = new Button( buttonComp, SWT.NONE );
    deleteButton.setText( Messages.getString( "SecurityGroupWizardPage.button_delete" ) ); //$NON-NLS-1$
    deleteButton.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        deleteSelectedPermission();
      }

    } );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.verticalIndent = 10;
    deleteButton.setLayoutData( gData );

    Link refreshLink = new Link( mainComp, SWT.NONE );
    refreshLink.setText( Messages.getString( "SecurityGroupWizardPage.link_refresh_permissions" ) ); //$NON-NLS-1$
    refreshLink.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        refreshPermissionsTable();
      }
    } );
    gData = new GridData( SWT.LEFT, SWT.CENTER, true, false );
    refreshLink.setLayoutData( gData );

    refreshPermissionsTable();

    setControl( mainComp );
  }

  /**
   * Removes the selected permission the security group.
   */
  protected void deleteSelectedPermission() {
    TableItem[] tableSelection = this.permTable.getSelection();
    if( tableSelection.length > 0 ) {
      final IpPermission ipPermission = ( IpPermission )tableSelection[ 0 ].getData();

      try {
        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            String groupName = SecurityGroupWizardPage.this.securityGroup.getGroupDescription()
              .getName();
            monitor.beginTask( Messages.getString( "SecurityGroupWizardPage.task_remove_permissions" ) //$NON-NLS-1$
                                   + groupName,
                               2 );
            try {
              // obtain the awsAccessId
              String awsAccessId = getAwsAccessId( SecurityGroupWizardPage.this.securityGroup );
              IEC2 ec2 = EC2Registry.getRegistry().getEC2( awsAccessId );
              if( ipPermission.getUidGroupPairs().size() > 0 ) {
                List<String[]> uidGroupPairs = ipPermission.getUidGroupPairs();
                String[] uid = uidGroupPairs.get( 0 );

                // create operation
                EC2OpRevokeSecurityGroup opRevoke = new EC2OpRevokeSecurityGroup( ec2,
                                                                                  SecurityGroupWizardPage.this.securityGroup.getGroupDescription()
                                                                                    .getName(),
                                                                                  uid[ 1 ],
                                                                                  uid[ 0 ] );
                new OperationExecuter().execOp( opRevoke );
              } else {
                EC2OpRevokeSecurityGroup opRevoke = new EC2OpRevokeSecurityGroup( ec2,
                                                                                  SecurityGroupWizardPage.this.securityGroup.getGroupDescription()
                                                                                    .getName(),
                                                                                  ipPermission.getIpRanges()
                                                                                    .get( 0 ),
                                                                                  ipPermission.getProtocol(),
                                                                                  ipPermission.getFromPort(),
                                                                                  ipPermission.getToPort() );
                new OperationExecuter().execOp( opRevoke );
              }
              monitor.worked( 1 );
              refreshPermissionsTable();
              monitor.worked( 2 );
            } finally {
              monitor.done();
            }
          }
        } );

      } catch( InvocationTargetException invTargetEx ) {
        Activator.log( "An problem occured while revoking security group access", //$NON-NLS-1$
                       invTargetEx );
      } catch( InterruptedException interruptedEx ) {
        Activator.log( "The thread was interrupted while revoking security group access", //$NON-NLS-1$
                       interruptedEx );
      }
    }
  }

  /**
   * Gets the aws access id from the security group via the ec2service, the aws
   * vo and the aws vo properties.
   * 
   * @param securityGroup the security group via which to obtain the aws access
   *          id
   * @return the aws access id associated with the aws vo
   */
  private String getAwsAccessId( final EC2SecurityGroup securityGroup ) {
    EC2Service ec2Service = securityGroup.getEC2Service();
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )ec2Service.getParent();
    String awsAccessId = null;
    try {
      awsAccessId = awsVo.getProperties().getAwsAccessId();
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not obtain aws vo properties", problemEx ); //$NON-NLS-1$
    }
    return awsAccessId;
  }

  /**
   * Refreshed the permission table from the EC2 system.
   */
  protected void refreshPermissionsTable() {
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          GroupDescription groupDescription = SecurityGroupWizardPage.this.securityGroup.getGroupDescription();
          monitor.beginTask( Messages.getString( "SecurityGroupWizardPage.task_fetch_group_permissions" ) //$NON-NLS-1$
                                 + groupDescription.getName(),
                             2 );
          try {
            String awsAccessId = null;
            awsAccessId = getAwsAccessId( SecurityGroupWizardPage.this.securityGroup );
            IEC2 ec2 = null;
            ec2 = EC2Registry.getRegistry().getEC2( awsAccessId );

            ArrayList<String> securityGroups = new ArrayList<String>();
            securityGroups.add( groupDescription.getName() );
            EC2OpDescribeSecurityGroups opDescribeSecurityGroups = new EC2OpDescribeSecurityGroups( ec2,
                                                                                                    securityGroups );
            new OperationExecuter().execOp( opDescribeSecurityGroups );
            monitor.worked( 1 );
            List<GroupDescription> secGrpList = opDescribeSecurityGroups.getResult();
            GroupDescription updatedGroupDescription = secGrpList.get( 0 );

            IGridContainer parent = SecurityGroupWizardPage.this.securityGroup.getParent();
            EC2Service ec2Service = SecurityGroupWizardPage.this.securityGroup.getEC2Service();
            SecurityGroupWizardPage.this.securityGroup = new EC2SecurityGroup( parent,
                                                                               ec2Service,
                                                                               updatedGroupDescription );

            updatePermTable();
            monitor.worked( 2 );
          } finally {
            monitor.done();
          }
        }
      } );

    } catch( InvocationTargetException invTargetEx ) {
      Activator.log( "An problem occured while reading security group access", //$NON-NLS-1$
                     invTargetEx );
    } catch( InterruptedException interruptedEx ) {
      Activator.log( "The thread was interrupted while reading security group access", //$NON-NLS-1$
                     interruptedEx );
    }
  }

  /**
   * Reads the data from the {@link #groupDescription} and display it in the
   * {@link #permTable}.
   */
  private void updatePermTable() {

    this.permTable.removeAll();

    if( this.securityGroup.getGroupDescription() != null ) {

      List<IpPermission> permissions = this.securityGroup.getGroupDescription()
        .getPermissions();

      for( IpPermission ipPerm : permissions ) {
        TableItem tableItem = new TableItem( this.permTable, SWT.NONE );
        tableItem.setData( ipPerm );

        String source = ""; //$NON-NLS-1$

        List<String> ipRanges = ipPerm.getIpRanges();
        for( Iterator<String> iterator = ipRanges.iterator(); iterator.hasNext(); )
        {
          source += iterator.next();
          if( iterator.hasNext() ) {
            source += ", "; //$NON-NLS-1$
          }
        }

        List<String[]> uidGroupPairs = ipPerm.getUidGroupPairs();
        for( Iterator<String[]> iterator = uidGroupPairs.iterator(); iterator.hasNext(); )
        {
          String[] uidGroupPair = iterator.next();
          source += uidGroupPair[ 0 ] + ":" + uidGroupPair[ 1 ]; //$NON-NLS-1$
          if( iterator.hasNext() ) {
            source += ", "; //$NON-NLS-1$
          }
        }

        String[] ColumnValues = new String[]{
          ipPerm.getProtocol(),
          String.valueOf( ipPerm.getFromPort() ),
          String.valueOf( ipPerm.getToPort() ),
          source
        };
        tableItem.setText( ColumnValues );
      }
    }
  }
}
