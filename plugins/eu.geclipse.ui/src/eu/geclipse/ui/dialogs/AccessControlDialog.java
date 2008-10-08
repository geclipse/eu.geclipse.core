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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import eu.geclipse.core.accesscontrol.IACL;
import eu.geclipse.core.accesscontrol.IACLEntry;
import eu.geclipse.core.model.IProtectable;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.comparators.TableColumnComparator;
import eu.geclipse.ui.listeners.TableColumnListener;
import eu.geclipse.ui.providers.ACLEntryLabelProvider;


/**
 * A dialog for managing the access permissions of grid elements
 * implementing {@link IProtectable}.
 * <p>
 * Editing multiple ACLs is supported only if:
 * <ul>
 * <li>for all of them <code>canSaveWholeACL() == true</code> (saving single
 *     entries at a time would be too risky)</li>
 * <li>all of them belong to objects of the same type, resp. are instances
 *     of the same class</li>
 * </ul>
 * 
 * @author agarcia
 */
public class AccessControlDialog extends Dialog {
  
  boolean canSaveWholeACL;
  
  /** The list of ACLs to manage */
  List< IACL > aclList;
  
  /** The list of ACL entries to display in the dialog */
  private List< IACLEntry > entriesList;
  
  private TableViewer tableViewer;
  
  /**
   * Constructs the dialog used for access control management.
   * 
   * @param acls the list of {@link IACL}s which should be managed.
   * @param canSaveWholeACL if true then the ACL supports being saved as a whole.
   * @param parentShell the parent shell for this (modal) dialog.
   */
  public AccessControlDialog( final List< IACL > acls,
                              final boolean canSaveWholeACL,
                              final Shell parentShell )
  {
    super( parentShell );
    
    setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
                   | SWT.RESIZE | SWT.MIN | SWT.MAX );
    
    this.canSaveWholeACL = canSaveWholeACL;
    this.aclList = acls;
    this.entriesList = new ArrayList< IACLEntry >();
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public Control createDialogArea( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    mainComp.setLayoutData( gData );
    
    Group aclGroup = new Group( mainComp, SWT.NONE );
    aclGroup.setLayout( new GridLayout( 2, false ) );
    aclGroup.setText( Messages.getString("AccessControlDialog.group_title") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.heightHint = 200;
    aclGroup.setLayoutData( gData );
    
    int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE;    
    Table aclEntriesTable = new Table( aclGroup, style );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true);
    aclEntriesTable.setLayoutData( gData );
    aclEntriesTable.setHeaderVisible( true );
    
    TableColumn policyColumn = new TableColumn( aclEntriesTable, SWT.LEAD );
    policyColumn.setText( Messages.getString("AccessControlDialog.policy_column_title") ); //$NON-NLS-1$
    policyColumn.setToolTipText( Messages.getString("AccessControlDialog.policy_tooltip") ); //$NON-NLS-1$
    policyColumn.setWidth( 120 );
    policyColumn.setAlignment( SWT.LEAD );
    
    TableColumn capabilityColumn = new TableColumn( aclEntriesTable, SWT.LEAD );
    capabilityColumn.setText( Messages.getString("AccessControlDialog.capability_column_title") ); //$NON-NLS-1$
    capabilityColumn.setToolTipText( Messages.getString("AccessControlDialog.capability_tooltip") ); //$NON-NLS-1$
    capabilityColumn.setWidth( 100 );
    capabilityColumn.setAlignment( SWT.LEAD );
    
    TableColumn actorTypeColumn = new TableColumn( aclEntriesTable, SWT.LEAD );
    actorTypeColumn.setText( Messages.getString("AccessControlDialog.actor_type_column_title") ); //$NON-NLS-1$
    actorTypeColumn.setToolTipText( Messages.getString("AccessControlDialog.actor_type_tooltip") ); //$NON-NLS-1$
    actorTypeColumn.setWidth( 130 );
    actorTypeColumn.setAlignment( SWT.LEAD );
    
    TableColumn actorIDColumn = new TableColumn( aclEntriesTable, SWT.LEAD );
    actorIDColumn.setText( Messages.getString("AccessControlDialog.actor_ID_column_title") ); //$NON-NLS-1$
    actorIDColumn.setToolTipText( Messages.getString("AccessControlDialog.actor_ID_tooltip") ); //$NON-NLS-1$
    actorIDColumn.setWidth( 300 );
    actorIDColumn.setAlignment( SWT.LEAD );
    
    TableColumn actorCAColumn = new TableColumn( aclEntriesTable, SWT.LEAD );
    actorCAColumn.setText( Messages.getString("AccessControlDialog.actor_CA_column_title") ); //$NON-NLS-1$
    actorCAColumn.setToolTipText( Messages.getString("AccessControlDialog.actor_CA_tooltip") ); //$NON-NLS-1$
    actorCAColumn.setWidth( 150 );
    actorCAColumn.setAlignment( SWT.LEAD );
    
    this.tableViewer = new TableViewer( aclEntriesTable );
    this.tableViewer.setContentProvider( new ArrayContentProvider() );
    this.tableViewer.setLabelProvider( new ACLEntryLabelProvider() );
    
    // Add listener for column sorting
    TableColumnListener columnListener = new TableColumnListener( this.tableViewer );
    for ( TableColumn column : aclEntriesTable.getColumns() ) {
      column.addSelectionListener( columnListener );
    }
    
    // Initially sort by the capability column, ascending
    aclEntriesTable.setSortColumn( capabilityColumn );
    aclEntriesTable.setSortDirection( SWT.UP );
    // Set also the capability column as fall back sorting column
    this.tableViewer.setComparator( new TableColumnComparator( capabilityColumn ) );
    
    Composite buttons = new Composite( aclGroup, SWT.NULL );
    gData = new GridData( SWT.CENTER, SWT.BEGINNING, false, false );
    gData.horizontalSpan = 1;
    buttons.setLayoutData( gData );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    buttons.setLayout( gLayout );
    
    Button addEntryButton = new Button( buttons, SWT.PUSH );
    addEntryButton.setText( Messages.getString("AccessControlDialog.add_button_text") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    addEntryButton.setLayoutData( gData );
    addEntryButton.addSelectionListener( new SelectionAdapter() {
      
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        IACLEntry newEntry = AccessControlDialog.this.aclList.get( 0 ).getEmptyEntry();
        editEntry( newEntry );
      }
      
    } );
    
    Button editEntryButton = new Button( buttons, SWT.PUSH );
    editEntryButton.setText( Messages.getString("AccessControlDialog.edit_button_text") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    editEntryButton.setLayoutData( gData );
    editEntryButton.addSelectionListener( new SelectionAdapter() {
      
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        editEntry( getSelectedEntry() );
      }
      
    } );
    
    Button removeEntryButton = new Button( buttons, SWT.PUSH );
    removeEntryButton.setText( Messages.getString("AccessControlDialog.remove_button_text") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    removeEntryButton.setLayoutData( gData );
    removeEntryButton.addSelectionListener( new SelectionAdapter() {
      
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        removeEntry();
      }
    
    } );
    
    // Fetch the entries to initialise the table
    initialize();
    
    if ( this.entriesList != null ) {
      this.tableViewer.setInput( this.entriesList );
    }
    
    return mainComp;
  }
  
  /**
   * Initialises the entries which should be displayed and managed in the
   * dialog.
   */
  private void initialize() {
    
    // TODO add Progress monitor!!
    
    IACL acl = this.aclList.get( 0 );
    if ( acl != null ) {
      try {
        for ( IACLEntry entry : acl.getEntries( null ) ) {
          this.entriesList.add( entry );
        }
      } catch ( ProblemException pe ) {
        // TODO: handle exception
      }
    }
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    shell.setText( Messages.getString("AccessControlDialog.dialog_title") ); //$NON-NLS-1$
  }
  
  /**
   * Create the dialog buttons. We override the superclass' method to be
   * able to modify the 'OK' button label if the ACL can be saved as a whole,
   * and presenting only a 'Done' button if the entries are saved by the
   * sub-dialog {@link AccessControlRuleDialog}.
   */
  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    
    // We just change the label of the OK button
    String buttonText = null;
    if ( this.canSaveWholeACL ) {
      buttonText = Messages.getString("AccessControlDialog.save_button_text"); //$NON-NLS-1$
    } else {
      buttonText = Messages.getString("AccessControlDialog.done_button_text"); //$NON-NLS-1$
    }
    
    createButton( parent, IDialogConstants.OK_ID,
                  buttonText, true );
    
    // Avoid having a Cancel button if there is nothing to cancel
    if ( this.canSaveWholeACL ) {
      createButton( parent, IDialogConstants.CANCEL_ID,
                    IDialogConstants.CANCEL_LABEL, false );
    }
  
  }
  
  /**
   * Returns the currently selected ACL entry.
   * 
   * @return the {@link IACLEntry} that is currently selected in the table control.
   */
  IACLEntry getSelectedEntry() {
    IACLEntry entry = null;
    IStructuredSelection selection
      = ( IStructuredSelection ) this.tableViewer.getSelection();
    Object obj = selection.getFirstElement();
    if ( obj instanceof  IACLEntry ) {
      entry = ( IACLEntry ) obj;
    }
    return entry;
  }
  
  /**
   * Helper method to edit an entry.
   * 
   * @param entry the ACL entry to edit.
   */
  void editEntry( final IACLEntry entry ) {

    AccessControlRuleDialog dialog
      = new AccessControlRuleDialog( entry,
                                     ! this.canSaveWholeACL,
                                     this.getShell() );

    int result = dialog.open();
    switch ( result ) {
      case Window.OK :
        //save();
        break;
      case Window.CANCEL :
        //undo();
        break;
      default :
        break;
    }
  }
  
  /**
   * Helper method for removing the entry selected in the table.
   */
  void removeEntry() {
    
    /*
     * We only ask for confirmation if the changes cannot be cancelled,
     * i.e. if single ACL entries have to be saved separately.
     */
    boolean confirm = true;
    if ( ! this.canSaveWholeACL ) {
      confirm = MessageDialog.openConfirm( this.getShell(),
                  "Remove rule",
                  "You are going to remove this access control rule. Continue?" );
    }
    
    // TODO add Progress monitor!!
    
    if ( confirm ) {
      
      IACLEntry entry = getSelectedEntry();
      
      try {
        // This ACLEntry may belong to several ACLs
        for ( IACL acl : this.aclList ) {
          acl.removeEntry( entry, null );
        }
        this.entriesList.remove( entry );
      } catch ( ProblemException pe ) {
        ProblemDialog.openProblem( this.getShell(),
                                   "Error deleting rule",
                                   "The selected rule could not be deleted",
                                   pe );
      }
    }
  }

}
