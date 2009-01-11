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
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpDescribeSecurityGroups;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This wizard page presents the form elements to choose the selected security
 * groups from the list of available security groups.
 * 
 * @author Moritz Post
 */
public class SecurityGroupSelectionWizardPage extends WizardPage {

  /** Id of this Wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizard.securityGroupSelection"; //$NON-NLS-1$

  /** The SWT {@link List} of available security groups. */
  private List listSecGrAvailable;

  /** The SWT {@link List} of selected security groups. */
  private List listSecGrSelected;

  /**
   * The list of available security groups backing up the
   * {@link #listSecGrAvailable}.
   */
  private ArrayList<String> availableGroups;

  /**
   * The list of selected security groups backing up the
   * {@link #listSecGrSelected}.
   */
  private ArrayList<String> selectedGroups;

  /** The access id to identify the current aws account */
  private String awsAccessId;

  /**
   * Creates the wizard page and sets up the title, description and wizard
   * image.
   * 
   * @param awsAccessId
   */
  protected SecurityGroupSelectionWizardPage( final String awsAccessId ) {
    super( SecurityGroupSelectionWizardPage.WIZARD_PAGE_ID,
           Messages.getString( "SecurityGroupSelectionWizardPage.page_title" ), //$NON-NLS-1$
           null );
    setDescription( Messages.getString( "SecurityGroupSelectionWizardPage.page_description" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/security_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );

    this.awsAccessId = awsAccessId;
    this.availableGroups = new ArrayList<String>();
    this.selectedGroups = new ArrayList<String>();
  }

  /**
   * Add the particular group to the list of selected security groups.
   * 
   * @param groupToAdd the group to add
   */
  private void addGroup( final String groupToAdd ) {
    if( this.availableGroups.contains( groupToAdd ) ) {
      this.selectedGroups.add( groupToAdd );
      this.availableGroups.remove( groupToAdd );
    }
  }

  /**
   * Add each element in the security groups array to the list of selected
   * security groups.
   * 
   * @param groupsToAdd the array of groups to add
   */
  protected void addGroup( final String[] groupsToAdd ) {
    for( String group : groupsToAdd ) {
      addGroup( group );
    }
    updateFromModel();
  }

  /**
   * Remove the given group from the list of selected security groups.
   * 
   * @param groupToRemove the group to remove
   */
  private void removeGroup( final String groupToRemove ) {
    if( this.selectedGroups.contains( groupToRemove ) ) {
      this.availableGroups.add( groupToRemove );
      this.selectedGroups.remove( groupToRemove );
    }
    updateFromModel();
  }

  /**
   * Removes all the elements in the passed security groups array from the list
   * of selected security groups.
   * 
   * @param groupsToRemove the array of groups to remove
   */
  private void removeGroup( final String[] groupsToRemove ) {
    for( String group : groupsToRemove ) {
      removeGroup( group );
    }
  }

  /**
   * Inserts all the elements in the passed {@link java.util.List} into the list
   * of selected security groups.
   * 
   * @param groups the security groups to add
   */
  public void initialiseSelectedGroups( final java.util.List<String> groups ) {
    for( String group : groups ) {
      this.selectedGroups.add( group );
    }
  }

  /**
   * Inserts all the elements in the passed {@link java.util.List} into the list
   * of available security groups. If one of the elements is already contained
   * within the list of selected elements it is not inserted into the list of
   * available security groups.
   * 
   * @param groupsList the security groups to add
   */
  protected void initialiseAvailableGroups( final java.util.List<String> groupsList )
  {
    // clear available
    this.availableGroups.clear();

    // add to available when not in selected
    for( String group : groupsList ) {
      if( !this.selectedGroups.contains( group ) ) {
        this.availableGroups.add( group );
      }
    }

    // remove from available when not in passed groupsList
    for( String group : this.selectedGroups ) {
      if( !groupsList.contains( group ) ) {
        this.selectedGroups.remove( group );
      }
    }
    updateFromModel();
  }

  /**
   * Updates the SWT {@link List} widgets {@link #listSecGrAvailable} and
   * {@link #listSecGrSelected} from the underlying model {@link java.util.List}s
   * {@link #availableGroups} and {@link #selectedGroups}.
   */
  protected void updateFromModel() {
    Collections.sort( this.availableGroups );
    this.listSecGrAvailable.removeAll();
    for( String group : this.availableGroups ) {
      this.listSecGrAvailable.add( group );
    }

    Collections.sort( this.selectedGroups );
    this.listSecGrSelected.removeAll();
    for( String group : this.selectedGroups ) {
      this.listSecGrSelected.add( group );
    }
  }

  /**
   * A getter for the {@link #selectedGroups}
   * 
   * @return the currently selected security groups
   */
  public java.util.List<String> getSelectedSecurityGroups() {
    return this.selectedGroups;
  }

  public void createControl( final Composite parent ) {

    Composite secGroup = new Composite( parent, SWT.NONE );
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    secGroup.setLayoutData( gData );
    GridLayout gridLayout = new GridLayout( 3, false );
    secGroup.setLayout( gridLayout );

    Label labelSecGroupAvailable = new Label( secGroup, SWT.NONE );
    labelSecGroupAvailable.setText( Messages.getString( "SecurityGroupSelectionWizardPage.label_available" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    labelSecGroupAvailable.setLayoutData( gData );

    Label labelSecGroupSelected = new Label( secGroup, SWT.NONE );
    labelSecGroupSelected.setText( Messages.getString( "SecurityGroupSelectionWizardPage.label_selected" ) ); //$NON-NLS-1$
    gData = new GridData();
    labelSecGroupSelected.setLayoutData( gData );

    this.listSecGrAvailable = new List( secGroup, SWT.BORDER
                                                  | SWT.MULTI
                                                  | SWT.V_SCROLL );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.listSecGrAvailable.setLayoutData( gData );
    this.listSecGrAvailable.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( final SelectionEvent e ) {
        addGroup( SecurityGroupSelectionWizardPage.this.listSecGrAvailable.getSelection() );
      }

      public void widgetSelected( final SelectionEvent e ) {
        // nothing to do
      }
    } );

    Composite secGroupButComp = new Composite( secGroup, SWT.NONE );
    secGroupButComp.setLayout( new GridLayout( 1, true ) );
    secGroupButComp.setLayoutData( new GridData( GridData.FILL_VERTICAL ) );

    Button buttonAddSecGr = new Button( secGroupButComp, SWT.NONE );
    buttonAddSecGr.setText( Messages.getString( "SecurityGroupSelectionWizardPage.button_add_group" ) ); //$NON-NLS-1$
    buttonAddSecGr.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        addGroup( SecurityGroupSelectionWizardPage.this.listSecGrAvailable.getSelection() );
      }
    } );

    Button buttonRemSecGr = new Button( secGroupButComp, SWT.NONE );
    buttonRemSecGr.setText( Messages.getString( "SecurityGroupSelectionWizardPage.button_remove_group" ) ); //$NON-NLS-1$
    buttonRemSecGr.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        removeGroup( SecurityGroupSelectionWizardPage.this.listSecGrSelected.getSelection() );
      }
    } );

    this.listSecGrSelected = new List( secGroup, SWT.BORDER
                                                 | SWT.MULTI
                                                 | SWT.V_SCROLL );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.listSecGrSelected.setLayoutData( gData );
    this.listSecGrSelected.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( final SelectionEvent e ) {
        removeGroup( SecurityGroupSelectionWizardPage.this.listSecGrSelected.getSelection() );
      }

      public void widgetSelected( final SelectionEvent e ) {
        // nothing to do
      }
    } );

    // populate link
    Link linkPopulate = new Link( secGroup, SWT.NONE );
    linkPopulate.setText( Messages.getString( "SecurityGroupSelectionWizardPage.link_populate_from_ec2" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.LEFT, SWT.CENTER, true, false );
    linkPopulate.setLayoutData( gData );
    linkPopulate.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        EC2Registry ec2Registry = EC2Registry.getRegistry();
        IEC2 ec2 = ec2Registry.getEC2( SecurityGroupSelectionWizardPage.this.awsAccessId );
        populateFromEC2( ec2 );
      }

    } );

    setControl( secGroup );

    EC2Registry ec2Registry = EC2Registry.getRegistry();
    IEC2 ec2 = ec2Registry.getEC2( SecurityGroupSelectionWizardPage.this.awsAccessId );

    populateFromEC2( ec2 );

    updateFromModel();
  }

  /**
   * Connects with the EC2 service and fetches the data to prepopulate the
   * {@link #availableGroups} {@link List}.
   * 
   * @param ec2 the {@link IEC2} instance to use for EC2 interaction
   */
  private void populateFromEC2( final IEC2 ec2 ) {
    try {

      getContainer().run( false, false, new IRunnableWithProgress() {

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
        {
          try {

            monitor.beginTask( Messages.getString("SecurityGroupSelectionWizardPage.task_title"), 2 ); //$NON-NLS-1$

            EC2Registry ec2Registry = EC2Registry.getRegistry();
            IEC2 ec2 = ec2Registry.getEC2( SecurityGroupSelectionWizardPage.this.awsAccessId );
            EC2OpDescribeSecurityGroups opDescSec = new EC2OpDescribeSecurityGroups( ec2 );
            OperationExecuter operationExecuter = new OperationExecuter();
            operationExecuter.execOp( opDescSec );
            monitor.worked( 1 );

            if( opDescSec.getException() != null ) {
              throw new InvocationTargetException( opDescSec.getException() );
            }
            java.util.List<GroupDescription> result = opDescSec.getResult();
            ArrayList<String> groupsList = new ArrayList<String>( result.size() );

            for( GroupDescription groupDescription : result ) {
              groupsList.add( groupDescription.getName() );
            }
            initialiseAvailableGroups( groupsList );
          } finally {
            monitor.done();
          }
        }
      } );
    } catch( Exception ex ) {
      ProblemDialog.openProblem( getContainer().getShell(),
                                 Messages.getString( "SecurityGroupSelectionWizardPage.problem_title" ), //$NON-NLS-1$
                                 Messages.getString( "SecurityGroupSelectionWizardPage.problem_description" ), //$NON-NLS-1$
                                 ex.getCause() );
      Activator.log( "Could not populate fields in Main EC2 Launch Tab", ex );//$NON-NLS-1$
    }
  }

}
