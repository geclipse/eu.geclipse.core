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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.GridElementCreatorWizard;

/**
 * This class represents a preference page that is contributed to the
 * PreferenceConstants dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public class VoPreferencePage
    extends PreferencePage
    implements IWorkbenchPreferencePage, IContentChangeListener {
  
  class VoContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      IGridElement[] result = null;
      if ( inputElement instanceof IVoManager ) {
        IVoManager manager = ( IVoManager ) inputElement;
        result = manager.getChildren( null );
        Arrays.sort( result, new Comparator< IGridElement >() {
          public int compare( final IGridElement vo1,
                              final IGridElement vo2 ) {
            return vo1.getName().compareTo( vo2.getName() );
          }
        } );
      }
      return result;
    }

    public void dispose() {
      // empty implementation
    }

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput ) {
      // empty implementation
    }
    
  }
  
  class VoLabelProvider extends LabelProvider implements ITableLabelProvider {

    public Image getColumnImage( final Object element, final int columnIndex ) {
      return null;
    }

    public String getColumnText( final Object element,
                                 final int columnIndex ) {
      String text = null;
      if ( element instanceof IVirtualOrganization ) {
        switch ( columnIndex ) {
          case 0:
            text = ( ( IVirtualOrganization ) element ).getName();
            break;
          case 1:
            text = ( ( IVirtualOrganization ) element ).getTypeName();
            break;
        }
      }
      return text;
    }
    
  }

  /**
   * The tabl viewer used to display the VOs.
   */
  private CheckboxTableViewer voViewer;
  
  /**
   * The button that triggers the creation of a new VO.
   */
  private Button addButton;
  
  /**
   * The button that triggers the deletion of an existing VO.
   */
  private Button removeButton;
  
  /**
   * The button that triggers the editing of an existing VO.
   */
  private Button editButton;
  
  /**
   * Standard constructor for the VO preference page.
   */
  public VoPreferencePage() {
    super();
    setDescription( Messages.getString("VoPreferencePage.description") ); //$NON-NLS-1$
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    GridModel.getRoot().getVoManager().removeContentChangeListener( this );
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    
    initializeDialogUnits( parent );
    noDefaultAndApplyButton();
    
    GridData gData;
    GridLayout layout= new GridLayout( 2, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    parent.setLayout( layout );
    
    Label voLabel = new Label( parent, SWT.NONE );
    voLabel.setText( Messages.getString("VoPreferencePage.vo_list_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    voLabel.setLayoutData( gData );
    
    Table voTable = new Table( parent, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION );
    voTable.setHeaderVisible( true );
    voTable.setLinesVisible( true );
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 1;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 200;
    gData.heightHint = 100;
    voTable.setLayoutData( gData );
    voTable.addMouseListener( new MouseAdapter() {
      @Override
      public void mouseDoubleClick( final MouseEvent e ) {
        editSelectedVO();
      }
    } );
    voTable.addKeyListener( new KeyAdapter() {
      @Override
      public void keyPressed( final KeyEvent event ) {
        if (event.character == SWT.DEL && event.stateMask == 0) {
          removeSelectedVOs();
        }
      }
    }); 
    
    TableColumn nameColumn = new TableColumn( voTable, SWT.LEFT );
    nameColumn.setText( "Name" );
    nameColumn.setWidth( 100 );
    TableColumn typeColumn = new TableColumn( voTable, SWT.CENTER );
    typeColumn.setText( "Type" );
    typeColumn.setWidth( 100 );
    
    final IVoManager manager = GridModel.getRoot().getVoManager();
    this.voViewer = new CheckboxTableViewer( voTable );
    this.voViewer.setLabelProvider( new VoLabelProvider() );
    this.voViewer.setContentProvider( new VoContentProvider() );
    this.voViewer.setInput( manager );
    this.voViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons();
      }
    } );
    manager.addContentChangeListener( this );
    IVirtualOrganization defaultVo
      = ( IVirtualOrganization ) manager.getDefault();
    if ( defaultVo != null ) {
      this.voViewer.setCheckedElements( new Object[] { defaultVo } );
    }
    this.voViewer.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        Object element = event.getElement();
        if ( element instanceof IVirtualOrganization ) {
          IVirtualOrganization vo = ( IVirtualOrganization ) element;
          try {
            if ( !event.getChecked() ) {
              manager.setDefault( null );
            }
            manager.setDefault( vo );
          } catch ( GridModelException gmExc ) {
            // TODO mathias
            Activator.logException( gmExc );
          }
        }
      }
    } );
    
    Composite buttons = new Composite( parent, SWT.NULL );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    gData.horizontalSpan = 1;
    buttons.setLayoutData( gData );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    buttons.setLayout( gLayout );
    
    this.addButton = new Button( buttons, SWT.PUSH );
    this.addButton.setText( "Add..." ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addButton.setLayoutData( gData );
    this.editButton = new Button( buttons, SWT.PUSH );
    this.editButton.setText( "Edit..." ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.editButton.setLayoutData( gData );
    this.removeButton = new Button( buttons, SWT.PUSH );
    this.removeButton.setText( "Remove" ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.removeButton.setLayoutData( gData );
    
    Label separator = new Label(buttons, SWT.NONE);
    separator.setVisible(false);
    gData = new GridData();
    gData.horizontalAlignment= GridData.FILL;
    gData.heightHint= 4;
    separator.setLayoutData(gData);
    
    this.addButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        addNewVO();
      }
    } );
    this.editButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        editSelectedVO();
      }
    } );
    this.removeButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        removeSelectedVOs();
      }
    } );
    
    updateButtons();
    
    /*
    this.voDescriptors.add( new VODescriptor( "geclipse", //$NON-NLS-1$
                                         "dgrid-voms.fzk.de", //$NON-NLS-1$
                                         15009,
                                         "/O=GermanGrid/OU=FZK/CN=host/dgrid-voms.fzk.de", //$NON-NLS-1$
                                         ldap://bdii101.grid.ucy.ac.cy:2170
                                         "http://dgi.d-grid.de/fileadmin/user_upload/documents/DGI-FG1.4/files/dgrid-voms.fzk.de" ) ); //$NON-NLS-1$
                                         https://www.d-grid.de/fileadmin/user_upload/documents/DGI-FG1.4/files/dgrid-voms.fzk.de
    */

    return parent;

  }
  
  /**
   * Get the currently selected VO.
   * 
   * @return The VO that is currently selected in the table control.
   */
  public IVirtualOrganization getSelectedVo() {
    IVirtualOrganization selectedVo = null;
    IStructuredSelection selection
      = ( IStructuredSelection ) this.voViewer.getSelection();
    Object obj = selection.getFirstElement();
    if ( obj instanceof  IVirtualOrganization ) {
      selectedVo = ( IVirtualOrganization ) obj;
    }
    return selectedVo;
  }
  
  /**
   * Get the currently selected VOs.
   * 
   * @return The VOs that are currently selected in the table control.
   */
  public List< IVirtualOrganization > getSelectedVos() {
    IStructuredSelection selection 
      = ( IStructuredSelection ) this.voViewer.getSelection();
    List< ? > selectionList = selection.toList();
    List< IVirtualOrganization > result = new ArrayList< IVirtualOrganization >();
    for( Object element : selectionList ) {
      if ( element instanceof IVirtualOrganization ) {
        IVirtualOrganization vo = ( IVirtualOrganization ) element;
        result.add( vo );
      }
    }
    return result;
  }
  
  /**
   * Trigger the new VO wizard to pop up in order to create
   * a new VO.
   */
  public void addNewVO() {
    GridElementCreatorWizard wizard
      = new GridElementCreatorWizard( IVirtualOrganization.class );
    WizardDialog dialog = new WizardDialog( this.getShell(),
                                            wizard );
    wizard.setNeedsProgressMonitor( true );
    wizard.setFirstPageComboLabel( "Type of the new Virtual Organization:" );
    wizard.setFirstPageDescription( "Create a new Virtual Organization of the selected type." );
    wizard.setFirstPageTitle( "Create a new VO" );
    wizard.setWindowTitle( "Create a new VO" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/authtokenwizard.gif" ); //$NON-NLS-1$
    wizard.setFirstPageImage( ImageDescriptor.createFromURL( imgUrl ) );
    dialog.open();
  }
  
  /**
   * Trigger the new VO wizard to pop up in order to edit an
   * existing VO.
   */
  public void editSelectedVO() {
    IVirtualOrganization selectedVo = getSelectedVo();
    if ( selectedVo != null ) {
      GridElementCreatorWizard wizard
        = new GridElementCreatorWizard( IVirtualOrganization.class );
      wizard.setNeedsProgressMonitor( true );
      wizard.setFirstPageComboLabel( "Type of the new Virtual Organization:" );
      wizard.setFirstPageDescription( "Create a new Virtual Organization of the selected type." );
      wizard.setFirstPageTitle( "Create a new VO" );
      wizard.setInitialData( selectedVo );
      wizard.setWindowTitle( "Edit VO" );
      URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/authtokenwizard.gif" ); //$NON-NLS-1$
      wizard.setFirstPageImage( ImageDescriptor.createFromURL( imgUrl ) );
      WizardDialog dialog = new WizardDialog( this.getShell(),
                                              wizard );
      dialog.open();
    }
  }
  
  /**
   * Remove all VOs that are currently selected in the table control.
   */
  public void removeSelectedVOs() {
    List< IVirtualOrganization > vos = getSelectedVos();
    if ( !vos.isEmpty() ) {
      boolean confirm = !MessageDialog.openConfirm( getShell(),
                                                    "Delete VOs",
                                                    "Do you really want to delete the selected VOs?" );
      if ( !confirm ) {
        IVoManager manager = GridModel.getRoot().getVoManager();
        for ( IVirtualOrganization vo : vos ) {
          manager.delete( vo );
        }
        updateButtons();
      }
    }
  }
  
  /**
   * Update the enabled state of the button controls.
   */
  protected void updateButtons() {
    
    /*java.util.List< IVoDescription > registeredVODescriptions
      = Extensions.getRegisteredVODescriptions();
    boolean managersAvailable
      = ( registeredVODescriptions != null ) && ( registeredVODescriptions.size() > 0 );
    */
    ISelection selection = this.voViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    
    this.addButton.setEnabled( true ); // TODO mathias
    this.removeButton.setEnabled( selectionAvailable );
    this.editButton.setEnabled( selectionAvailable );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeListener#contentChanged(org.eclipse.compare.IContentChangeNotifier)
   */
  public void contentChanged( final IContentChangeNotifier source ) {
    this.voViewer.refresh();
    IVoManager manager = GridModel.getRoot().getVoManager();
    IGridElement defaultVo = manager.getDefault();
    if ( defaultVo != null ) {
      this.voViewer.setCheckedElements( new Object[] { defaultVo } );
    }
    updateButtons();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
  @Override
  public boolean performOk() {
    try {
      GridModel.getRoot().getVoManager().saveElements();
    } catch( GridModelException gmExc ) {
      // TODO mathias
      Activator.logException( gmExc );
    }
    return super.performOk();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
   */
  @Override
  protected void performDefaults() {
    try {
      GridModel.getRoot().getVoManager().loadElements();
    } catch( GridModelException gmExc ) {
      // TODO mathias
      Activator.logException( gmExc );
    }
    super.performDefaults();
  }
  
}
