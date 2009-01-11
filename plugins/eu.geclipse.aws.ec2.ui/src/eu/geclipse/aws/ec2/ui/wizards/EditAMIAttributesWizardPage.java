/*****************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * Copyright (c) 2008 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.xerox.amazonws.ec2.DescribeImageAttributeResult;
import com.xerox.amazonws.ec2.ImageListAttribute;
import com.xerox.amazonws.ec2.ImageListAttributeItem;
import com.xerox.amazonws.ec2.LaunchPermissionAttribute;
import com.xerox.amazonws.ec2.ProductCodesAttribute;
import com.xerox.amazonws.ec2.ImageAttribute.ImageAttributeType;
import com.xerox.amazonws.ec2.ImageListAttribute.ImageListAttributeItemType;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpDescribeImageAttributes;
import eu.geclipse.aws.ec2.op.EC2OpModifyImageAttribute;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.op.OperationSet;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This {@link WizardPage} presents the form elements to edit the attributes of
 * an AMI. This means to present a list of attributes with their corresponding
 * values.
 * 
 * @author Moritz Post
 */
public class EditAMIAttributesWizardPage extends WizardPage {

  /** An identifier for to give public access to an image. */
  private static final String GROUP_PERMISSION_ALL = "all"; //$NON-NLS-1$

  /** The id of this wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizards.editAMIAttributesWizardPage"; //$NON-NLS-1$

  /** The aws vo the selected ami is bound to. */
  private AWSVirtualOrganization awsVo;

  /** The AMI under modification. */
  private EC2AMIImage ami;

  /** The table holding the list of attributes. */
  private Table tableAttrib;

  /** The launch permission attributes of the AMI. */
  private DescribeImageAttributeResult attributesLaunchPerm;

  /** The product codes attributes of the AMI. */
  private DescribeImageAttributeResult attributesProductCodes;

  /** The button to remove a selected launch permission from the list. */
  private Button buttonRemLaunchPerm;

  /**
   * The button to make an AMI public by adding the appropriate launch
   * permission.
   */
  private Button buttonMakeAMIPublic;

  /**
   * Create a new {@link EditAMIAttributesWizardPage} by setting the parent aws
   * vo and the ami under modification.
   * 
   * @param awsVo the parent vo
   * @param ami the AMI to whos attributes to edit
   */
  public EditAMIAttributesWizardPage( final AWSVirtualOrganization awsVo,
                                      final EC2AMIImage ami )
  {
    super( EditAMIAttributesWizardPage.WIZARD_PAGE_ID,
           Messages.getString("EditAMIAttributesWizardPage.page_title"), //$NON-NLS-1$
           null );
    setDescription( MessageFormat.format( Messages.getString("EditAMIAttributesWizardPage.page_description"), //$NON-NLS-1$
                                          ami.getImageId() ) );
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/service_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );

    this.awsVo = awsVo;
    this.ami = ami;
  }

  public void createControl( final Composite parent ) {

    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    mainComp.setLayout( new GridLayout( 2, false ) );

    this.tableAttrib = new Table( mainComp, SWT.SINGLE
                                            | SWT.FULL_SELECTION
                                            | SWT.MULTI
                                            | SWT.H_SCROLL
                                            | SWT.V_SCROLL
                                            | SWT.BORDER );
    this.tableAttrib.setLayoutData( new GridData( SWT.FILL,
                                                  SWT.FILL,
                                                  true,
                                                  true ) );
    this.tableAttrib.setLinesVisible( true );
    this.tableAttrib.setHeaderVisible( true );
    this.tableAttrib.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent e ) {
        updateRemoveButtonStatus();
      }

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // nothing to do here
      }
    } );

    // column attribute
    TableColumn tableColumnAttrib = new TableColumn( this.tableAttrib, SWT.LEAD );
    tableColumnAttrib.setText( Messages.getString("EditAMIAttributesWizardPage.table_attrib_column_attribute") ); //$NON-NLS-1$

    // column identifier
    TableColumn tableColumnIdentifier = new TableColumn( this.tableAttrib,
                                                         SWT.LEAD );
    tableColumnIdentifier.setText( Messages.getString("EditAMIAttributesWizardPage.table_attrib_column_identifier") ); //$NON-NLS-1$

    // column value
    TableColumn tableColumnValue = new TableColumn( this.tableAttrib, SWT.LEAD );
    tableColumnValue.setText( Messages.getString("EditAMIAttributesWizardPage.table_attrib_column_value") ); //$NON-NLS-1$

    for( int i = 0; i < this.tableAttrib.getColumnCount(); i++ ) {
      this.tableAttrib.getColumn( i ).pack();
    }

    Composite buttonComp = new Composite( mainComp, SWT.NONE );
    buttonComp.setLayoutData( new GridData( SWT.CENTER, SWT.FILL, false, true ) );
    buttonComp.setLayout( new GridLayout( 1, false ) );

    Button buttonAddLaunchPermission = new Button( buttonComp, SWT.PUSH );
    buttonAddLaunchPermission.setLayoutData( new GridData( SWT.FILL,
                                                           SWT.CENTER,
                                                           true,
                                                           false ) );
    buttonAddLaunchPermission.setText( Messages.getString("EditAMIAttributesWizardPage.button_add_launch_permission") ); //$NON-NLS-1$
    buttonAddLaunchPermission.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        WizardDialog wizardDialog = new WizardDialog( getContainer().getShell(),
                                                      new AddAttributeWizard( EditAMIAttributesWizardPage.this.awsVo,
                                                                              EditAMIAttributesWizardPage.this.ami ) );

        int wizardReturnCode = wizardDialog.open();
        if( wizardReturnCode == Window.OK ) {
          refreshAttributesTable();
        }
      }

    } );

    this.buttonRemLaunchPerm = new Button( buttonComp, SWT.PUSH );
    this.buttonRemLaunchPerm.setLayoutData( new GridData( SWT.FILL,
                                                          SWT.CENTER,
                                                          true,
                                                          false ) );
    this.buttonRemLaunchPerm.setText( Messages.getString("EditAMIAttributesWizardPage.button_remove_launch_permission") ); //$NON-NLS-1$
    this.buttonRemLaunchPerm.setEnabled( false );
    this.buttonRemLaunchPerm.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        removeAttributes();
      }

    } );

    this.buttonMakeAMIPublic = new Button( buttonComp, SWT.PUSH );
    this.buttonMakeAMIPublic.setLayoutData( new GridData( SWT.FILL,
                                                          SWT.CENTER,
                                                          true,
                                                          false,
                                                          0,
                                                          12 ) );
    this.buttonMakeAMIPublic.setText( Messages.getString("EditAMIAttributesWizardPage.button_make_ami_public") ); //$NON-NLS-1$
    this.buttonMakeAMIPublic.setEnabled( true );
    this.buttonMakeAMIPublic.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {

        boolean confirmation = MessageDialog.openConfirm( getContainer().getShell(),
                                                          MessageFormat.format( Messages.getString("EditAMIAttributesWizardPage.dialog_ami_public_title"), //$NON-NLS-1$
                                                                                EditAMIAttributesWizardPage.this.ami.getImageId() ),
                                                          MessageFormat.format( Messages.getString("EditAMIAttributesWizardPage.dialog_ami_public_description"), //$NON-NLS-1$
                                                                                EditAMIAttributesWizardPage.this.ami.getImageId() ) );
        if( confirmation ) {
          makeAMIPublic();
        }
      }

    } );

    Button buttonAddProductCode = new Button( buttonComp, SWT.PUSH );
    buttonAddProductCode.setLayoutData( new GridData( SWT.FILL,
                                                      SWT.CENTER,
                                                      true,
                                                      false ) );
    buttonAddProductCode.setText( Messages.getString("EditAMIAttributesWizardPage.button_add_product_code") ); //$NON-NLS-1$
    buttonAddProductCode.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        addProductCode();
      }
    } );

    Link refreshLink = new Link( mainComp, SWT.NONE );
    refreshLink.setText( Messages.getString("EditAMIAttributesWizardPage.link_refresh_attributes") ); //$NON-NLS-1$
    refreshLink.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        refreshAttributesTable();
      }
    } );
    refreshLink.setLayoutData( new GridData( SWT.LEFT,
                                             SWT.CENTER,
                                             true,
                                             false,
                                             12,
                                             0 ) );

    refreshAttributesTable();

    setControl( mainComp );
  }

  /**
   * Adds a product, which is queried from the user, to the {@link #ami} under
   * modification.
   */
  protected void addProductCode() {
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          try {
            monitor.beginTask( Messages.getString("EditAMIAttributesWizardPage.task_adding_product_code"), 2 ); //$NON-NLS-1$
            EC2Registry registry = EC2Registry.getRegistry();
            IEC2 ec2;
            try {
              InputDialog inputDialog = new InputDialog( getContainer().getShell(),
                                                         Messages.getString("EditAMIAttributesWizardPage.dialog_add_product_id_title"), //$NON-NLS-1$
                                                         Messages.getString("EditAMIAttributesWizardPage.dialog_add_product_id_description"), //$NON-NLS-1$
                                                         null,
                                                         new IInputValidator() {

                                                           public String isValid( final String newText )
                                                           {
                                                             if( newText.trim()
                                                               .length() == 0 )
                                                             {
                                                               return Messages.getString("EditAMIAttributesWizardPage.dialog_add_product_id_error_product_id_needed"); //$NON-NLS-1$
                                                             }
                                                             return null;
                                                           }
                                                         } );
              inputDialog.open();

              if( inputDialog.getReturnCode() == Window.OK ) {
                ec2 = registry.getEC2( EditAMIAttributesWizardPage.this.awsVo );
                ProductCodesAttribute attribute = new ProductCodesAttribute();
                attribute.addImageListAttributeItem( ImageListAttributeItemType.productCode,
                                                     inputDialog.getValue()
                                                       .trim() );

                EC2OpModifyImageAttribute op = new EC2OpModifyImageAttribute( ec2,
                                                                              EditAMIAttributesWizardPage.this.ami.getImageId(),
                                                                              attribute,
                                                                              ImageListAttributeOperationType.add );

                new OperationExecuter().execOp( op );

                if( op.getException() != null ) {
                  throw new InvocationTargetException( op.getException() );
                }
              }
            } catch( ProblemException problemEx ) {
              throw new InvocationTargetException( problemEx );
            }
            monitor.worked( 1 );
          } finally {
            monitor.done();
          }
        }
      } );
    } catch( Exception ex ) {
      Activator.log( "An problem occured while adding a product code", ex ); //$NON-NLS-1$
      // process any errors
      final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                     ex.getCause()
                                                                       .getLocalizedMessage(),
                                                                     ex,
                                                                     Activator.PLUGIN_ID );

      Display display = PlatformUI.getWorkbench().getDisplay();
      display.asyncExec( new Runnable() {

        public void run() {
          IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
          ProblemDialog.openProblem( workbenchWindow.getShell(),
                                     Messages.getString("EditAMIAttributesWizardPage.problem_add_product_code_title"), //$NON-NLS-1$
                                     Messages.getString("EditAMIAttributesWizardPage.problem_add_product_code_description"), //$NON-NLS-1$
                                     exception );
        }
      } );
    }
    refreshAttributesTable();

  }

  /**
   * Grants any AWS user access to this AMI by setting the launch permission
   * "group" to all.
   */
  protected void makeAMIPublic() {
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          try {
            monitor.beginTask( Messages.getString("EditAMIAttributesWizardPage.task_make_ami_public"), 2 ); //$NON-NLS-1$
            EC2Registry registry = EC2Registry.getRegistry();
            IEC2 ec2;
            try {
              ec2 = registry.getEC2( EditAMIAttributesWizardPage.this.awsVo );

              LaunchPermissionAttribute attribute = new LaunchPermissionAttribute();

              attribute.addImageListAttributeItem( ImageListAttributeItemType.group,
                                                   EditAMIAttributesWizardPage.GROUP_PERMISSION_ALL );

              EC2OpModifyImageAttribute op = new EC2OpModifyImageAttribute( ec2,
                                                                            EditAMIAttributesWizardPage.this.ami.getImageId(),
                                                                            attribute,
                                                                            ImageListAttributeOperationType.add );

              new OperationExecuter().execOp( op );

              if( op.getException() != null ) {
                throw new InvocationTargetException( op.getException() );
              }

              refreshAttributesTable();
            } catch( ProblemException problemEx ) {
              throw new InvocationTargetException( problemEx );
            }
            monitor.worked( 1 );
          } finally {
            monitor.done();
          }
        }
      } );
    } catch( Exception ex ) {
      Activator.log( "An problem occured while adding public group permissions", ex ); //$NON-NLS-1$
      // process any errors
      final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                     ex.getCause()
                                                                       .getLocalizedMessage(),
                                                                     ex,
                                                                     Activator.PLUGIN_ID );

      Display display = PlatformUI.getWorkbench().getDisplay();
      display.asyncExec( new Runnable() {

        public void run() {
          IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
          ProblemDialog.openProblem( workbenchWindow.getShell(),
                                     Messages.getString("EditAMIAttributesWizardPage.problem_add_group_permission_title"), //$NON-NLS-1$
                                     Messages.getString("EditAMIAttributesWizardPage.problem_add_group_permission_description"), //$NON-NLS-1$
                                     exception );
        }
      } );
    }
  }

  /**
   * Removes the attributes which are selected in the {@link #tableAttrib} table
   * from the list of attributes for this AMI. Only launch permissions are
   * currently supported.
   */
  protected void removeAttributes() {
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          try {
            monitor.beginTask( Messages.getString("EditAMIAttributesWizardPage.task_removing_permissions"), 2 ); //$NON-NLS-1$
            EC2Registry registry = EC2Registry.getRegistry();
            IEC2 ec2;
            try {
              ec2 = registry.getEC2( EditAMIAttributesWizardPage.this.awsVo );
            } catch( ProblemException problemEx ) {
              throw new InvocationTargetException( problemEx );
            }
            monitor.worked( 1 );

            LaunchPermissionAttribute attribute = new LaunchPermissionAttribute();
            TableItem[] selection = EditAMIAttributesWizardPage.this.tableAttrib.getSelection();
            for( TableItem tableItem : selection ) {

              attribute.addImageListAttributeItem( ImageListAttributeItemType.valueOf( tableItem.getText( 1 ) ),
                                                   tableItem.getText( 2 ) );
            }

            EC2OpModifyImageAttribute op = new EC2OpModifyImageAttribute( ec2,
                                                                          EditAMIAttributesWizardPage.this.ami.getImageId(),
                                                                          attribute,
                                                                          ImageListAttributeOperationType.remove );

            new OperationExecuter().execOp( op );

            if( op.getException() != null ) {
              throw new InvocationTargetException( op.getException() );
            }

            refreshAttributesTable();

          } finally {
            monitor.done();
          }
        }
      } );
    } catch( Exception ex ) {
      Activator.log( "An problem occured while removing permissions", ex ); //$NON-NLS-1$
      // process any errors
      final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                     ex.getCause()
                                                                       .getLocalizedMessage(),
                                                                     ex,
                                                                     Activator.PLUGIN_ID );

      Display display = PlatformUI.getWorkbench().getDisplay();
      display.asyncExec( new Runnable() {

        public void run() {
          IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow();
          ProblemDialog.openProblem( workbenchWindow.getShell(),
                                     Messages.getString("EditAMIAttributesWizardPage.problem_remove_permission_title"), //$NON-NLS-1$
                                     Messages.getString("EditAMIAttributesWizardPage.problem_remove_permission_description"), //$NON-NLS-1$
                                     exception );
        }
      } );
    }
  }

  /**
   * Refreshes the content of the {@link #tableAttrib} table by fetching the
   * current attributes from the EC2 infrastructure.
   */
  protected void refreshAttributesTable() {
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          monitor.beginTask( MessageFormat.format( Messages.getString("EditAMIAttributesWizardPage.task_fetching_attributes"), //$NON-NLS-1$
                                                   EditAMIAttributesWizardPage.this.ami.getImageId() ),
                             2 );
          try {
            IEC2 ec2 = null;
            ec2 = EC2Registry.getRegistry()
              .getEC2( EditAMIAttributesWizardPage.this.awsVo );

            EC2OpDescribeImageAttributes opLaunchPerms = new EC2OpDescribeImageAttributes( ec2,
                                                                                           EditAMIAttributesWizardPage.this.ami.getImageId(),
                                                                                           ImageAttributeType.launchPermission );

            EC2OpDescribeImageAttributes opProdCodes = new EC2OpDescribeImageAttributes( ec2,
                                                                                         EditAMIAttributesWizardPage.this.ami.getImageId(),
                                                                                         ImageAttributeType.productCodes );

            OperationSet operationSet = new OperationSet();
            operationSet.addOp( opLaunchPerms );
            operationSet.addOp( opProdCodes );

            new OperationExecuter().execOpGroup( operationSet );
            monitor.worked( 1 );

            if( opLaunchPerms.getException() != null ) {
              throw new InvocationTargetException( opLaunchPerms.getException()
                .getCause() );
            }
            if( opProdCodes.getException() != null ) {
              throw new InvocationTargetException( opProdCodes.getException()
                .getCause() );
            }

            EditAMIAttributesWizardPage.this.attributesLaunchPerm = opLaunchPerms.getResult();
            EditAMIAttributesWizardPage.this.attributesProductCodes = opProdCodes.getResult();

            updateAttribTable();

            monitor.worked( 2 );
          } catch( ProblemException problemEx ) {
            new InvocationTargetException( problemEx,
                                           "Could not obtain EC2 instance" ); //$NON-NLS-1$
          } finally {
            monitor.done();
          }
        }

        private void updateAttribTable() {

          EditAMIAttributesWizardPage.this.tableAttrib.removeAll();

          ImageListAttribute attribLaunchPerm = EditAMIAttributesWizardPage.this.attributesLaunchPerm.getImageListAttribute();
          ImageListAttribute attribProductCode = EditAMIAttributesWizardPage.this.attributesProductCodes.getImageListAttribute();

          getContainer().getShell().getDisplay().asyncExec( new Runnable() {

            public void run() {
              EditAMIAttributesWizardPage.this.buttonMakeAMIPublic.setEnabled( true );
            }
          } );

          // launch perm
          for( ImageListAttributeItem imageListAttributeItem : attribLaunchPerm.getImageListAttributeItems() )
          {
            TableItem tableItem = new TableItem( EditAMIAttributesWizardPage.this.tableAttrib,
                                                 SWT.NONE );

            String attribute = attribLaunchPerm.getType().toString();
            String permissionType = imageListAttributeItem.getType().toString();
            String attribValue = imageListAttributeItem.getValue();

            if( permissionType.equals( ImageListAttributeItemType.group.toString() )
                && attribValue.equals( EditAMIAttributesWizardPage.GROUP_PERMISSION_ALL ) )
            {
              getContainer().getShell().getDisplay().asyncExec( new Runnable() {

                public void run() {
                  EditAMIAttributesWizardPage.this.buttonMakeAMIPublic.setEnabled( false );
                }
              } );
            }

            tableItem.setText( new String[]{
              attribute, permissionType, attribValue
            } );
          }

          // product code
          for( ImageListAttributeItem imageListAttributeItem : attribProductCode.getImageListAttributeItems() )
          {
            TableItem tableItem = new TableItem( EditAMIAttributesWizardPage.this.tableAttrib,
                                                 SWT.NONE );
            tableItem.setText( new String[]{
              attribLaunchPerm.getType().toString(),
              imageListAttributeItem.getType().toString(),
              imageListAttributeItem.getValue()
            } );
          }
          for( int i = 0; i < EditAMIAttributesWizardPage.this.tableAttrib.getColumnCount(); i++ )
          {
            EditAMIAttributesWizardPage.this.tableAttrib.getColumn( i ).pack();
          }
          getContainer().getShell().getDisplay().asyncExec( new Runnable() {

            public void run() {
              updateRemoveButtonStatus();
            }
          } );
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
   * Sets the enablement state of the {@link #buttonRemLaunchPerm} depending of
   * the selection in the {@link #tableAttrib}.
   */
  private void updateRemoveButtonStatus() {
    TableItem[] selection = EditAMIAttributesWizardPage.this.tableAttrib.getSelection();

    if( selection.length > 0 ) {
      EditAMIAttributesWizardPage.this.buttonRemLaunchPerm.setEnabled( true );
    } else {
      EditAMIAttributesWizardPage.this.buttonRemLaunchPerm.setEnabled( false );
    }
  }
}
