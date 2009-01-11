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
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.xerox.amazonws.ec2.AddressInfo;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpDescribeAddresses;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link WizardPage} provides the form elements to choose an elastic IP
 * which is to be associated with an ec2 instance.
 * 
 * @author Moritz Post
 */
public class SelectElasticIPWizardPage extends WizardPage {

  /** Id of this Wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizard.elasticIp"; //$NON-NLS-1$

  /** The source for AWS credentials. */
  private AWSVirtualOrganization awsVo;

  /** This widget lists all elastic IPs. */
  private Table tableElasticIPs;

  /**
   * Creates the wizard page with the awsVo providing the aws credentials and
   * the ec2Instance providing some information about the target ec2 instance.
   * 
   * @param awsVo the aws credentials source
   * @param ec2Instance the ec2 instance to associate
   */
  public SelectElasticIPWizardPage( final AWSVirtualOrganization awsVo,
                                    final EC2Instance ec2Instance )
  {
    super( SelectElasticIPWizardPage.WIZARD_PAGE_ID,
           Messages.getString("SelectElasticIPWizardPage.wizard_page_title"), //$NON-NLS-1$
           null );
    setDescription( MessageFormat.format( Messages.getString("SelectElasticIPWizardPage.wizard_page_description"), //$NON-NLS-1$
                                          ec2Instance.getInstanceId() ) );
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/service_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );

    this.awsVo = awsVo;
    setPageComplete( false );
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );

    this.tableElasticIPs = new Table( mainComp, SWT.SINGLE
                                                | SWT.FULL_SELECTION
                                                | SWT.BORDER );
    this.tableElasticIPs.setLayoutData( new GridData( SWT.FILL,
                                                      SWT.FILL,
                                                      true,
                                                      true ) );
    this.tableElasticIPs.setLinesVisible( true );
    this.tableElasticIPs.setHeaderVisible( true );
    this.tableElasticIPs.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        TableItem[] selection = SelectElasticIPWizardPage.this.tableElasticIPs.getSelection();
        if( selection.length > 0 ) {
          setPageComplete( true );
        }
      }

    } );

    TableColumn tableColumnElasticIP = new TableColumn( this.tableElasticIPs,
                                                        SWT.LEFT );
    tableColumnElasticIP.setText( Messages.getString("SelectElasticIPWizardPage.table_elastic_ip_column_elastic_ip_title") ); //$NON-NLS-1$
    TableColumn tableColumnInstanceId = new TableColumn( this.tableElasticIPs,
                                                         SWT.LEFT );
    tableColumnInstanceId.setText( Messages.getString("SelectElasticIPWizardPage.table_elastic_ip_column_instance_id_title") ); //$NON-NLS-1$

    for( int i = 0; i < this.tableElasticIPs.getColumnCount(); i++ ) {
      this.tableElasticIPs.getColumn( i ).pack();
    }

    Link refreshLink = new Link( mainComp, SWT.NONE );
    refreshLink.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, false ) );
    refreshLink.setText( Messages.getString("SelectElasticIPWizardPage.link_refresh_elastic_ips") ); //$NON-NLS-1$
    refreshLink.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        refreshElasticIPs();
        setPageComplete( false );
      }
    } );

    setControl( mainComp );

    refreshElasticIPs();

  }

  /**
   * Fills the {@link #tableElasticIPs} with the elastic IPs associated with the
   * AWS account bound to the {@link #awsVo}.
   */
  protected void refreshElasticIPs() {
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          try {
            monitor.beginTask( Messages.getString("SelectElasticIPWizardPage.monitor_title"), 2 ); //$NON-NLS-1$

            EC2Registry registry = EC2Registry.getRegistry();
            IEC2 ec2 = registry.getEC2( SelectElasticIPWizardPage.this.awsVo );
            EC2OpDescribeAddresses opDescribeAddresses = new EC2OpDescribeAddresses( ec2 );
            monitor.worked( 1 );
            new OperationExecuter().execOp( opDescribeAddresses );

            if( opDescribeAddresses.getException() != null ) {
              throw new InvocationTargetException( opDescribeAddresses.getException()
                .getCause() );
            }

            populateElasticIPTable( opDescribeAddresses.getResult() );

          } catch( ProblemException problemEx ) {
            Activator.log( "Could not obtain IEC2 instance", problemEx ); //$NON-NLS-1$
          } finally {
            monitor.done();
          }
        }
      } );

    } catch( InvocationTargetException invTargetEx ) {
      Activator.log( "An problem occured while refreshing the elastic IPs", //$NON-NLS-1$
                     invTargetEx );
    } catch( InterruptedException interruptedEx ) {
      Activator.log( "The thread was interrupted while refreshing the elastic IPs", //$NON-NLS-1$
                     interruptedEx );
    }
  }

  /**
   * Fills the {@link #tableElasticIPs} with the data contained within the
   * <code>addressInfos</code> parameter.
   * 
   * @param addressInfos the data to fill the table with
   */
  protected void populateElasticIPTable( final List<AddressInfo> addressInfos )
  {
    String none = "none"; //$NON-NLS-1$

    this.tableElasticIPs.removeAll();
    for( AddressInfo addressInfo : addressInfos ) {
      String instanceId = addressInfo.getInstanceId();
      if( instanceId.length() == 0 ) {
        instanceId = none;
      }
      String[] fields = new String[]{
        addressInfo.getPublicIp(), instanceId
      };
      TableItem tableItem = new TableItem( this.tableElasticIPs, SWT.NONE );
      tableItem.setData( addressInfo );
      tableItem.setText( fields );
    }
  }

  /**
   * A getter for the Elastic IP which is selected in the
   * {@link #tableElasticIPs}.
   * 
   * @return the selected IP address
   */
  public String getSelectedElasticIP() {
    TableItem[] selection = this.tableElasticIPs.getSelection();
    if( selection.length > 0 ) {
      AddressInfo addressInfo = ( AddressInfo )selection[ 0 ].getData();
      return addressInfo.getPublicIp();
    }
    return null;
  }
}
