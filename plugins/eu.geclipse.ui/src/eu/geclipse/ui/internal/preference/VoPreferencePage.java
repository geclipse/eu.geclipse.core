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
import org.eclipse.jface.wizard.Wizard;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.comparators.TableColumnComparator;
import eu.geclipse.ui.internal.listeners.TableColumnListener;
import eu.geclipse.ui.internal.wizards.VoImportWizard;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

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
    implements IWorkbenchPreferencePage, IGridModelListener {
  
  /**
   * Content provider for the {@link VoPreferencePage}.
   */
  class VoContentProvider
      implements IStructuredContentProvider {
    
    /**
     * The shell of the input.
     */
    private Shell shell;

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements( final Object inputElement ) {
      IGridElement[] result = null;
      if ( inputElement instanceof IVoManager ) {
        IVoManager manager = ( IVoManager ) inputElement;
        try {
          result = manager.getChildren( null );
          Arrays.sort( result, new Comparator< IGridElement >() {
            public int compare( final IGridElement vo1,
                                final IGridElement vo2 ) {
              return vo1.getName().compareTo( vo2.getName() );
            }
          } );
        } catch ( GridModelException gmExc ) {
          if ( this.shell != null ) {
            NewProblemDialog.openProblem( this.shell,
                                          Messages.getString("VoPreferencePage.content_provider_problem"), //$NON-NLS-1$
                                          Messages.getString("VoPreferencePage.query_vo_failed"), //$NON-NLS-1$
                                          gmExc );
          } else {
            Activator.logException( gmExc );
          }
        }
      }
      return result;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
      // empty implementation
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput ) {
      if ( viewer != null ) {
        this.shell = viewer.getControl().getShell();
      }
    }
    
  }
  
  /**
   * Label provider for the {@link VoPreferencePage}.
   */
  class VoLabelProvider
      extends LabelProvider
      implements ITableLabelProvider {

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage( final Object element, final int columnIndex ) {
      return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
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
   * The table viewer used to display the VOs.
   *   Package visibility to avoid warning about synthetic accessor
   *   performance issue.
   */
  CheckboxTableViewer voViewer;
  
  /**
   * The button that triggers the creation of a new VO.
   */
  private Button addButton;
  
  /**
   * The button that triggers the opening of the VO import wizard. 
   */
  private Button importButton;
  
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
    GridModel.getVoManager().removeGridModelListener( this );
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
        IVirtualOrganization selectedVo = getSelectedVo();
        if ( selectedVo != null ) {
          editVO( selectedVo );
        }
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
    nameColumn.setText( Messages.getString("VoPreferencePage.name_column_text") ); //$NON-NLS-1$
    nameColumn.setWidth( 100 );
    TableColumn typeColumn = new TableColumn( voTable, SWT.CENTER );
    typeColumn.setText( Messages.getString("VoPreferencePage.type_column_text") ); //$NON-NLS-1$
    typeColumn.setWidth( 100 );
    
    final IVoManager manager = GridModel.getVoManager();
    this.voViewer = new CheckboxTableViewer( voTable );
    this.voViewer.setLabelProvider( new VoLabelProvider() );
    this.voViewer.setContentProvider( new VoContentProvider() );
    
    TableColumnListener columnListener = new TableColumnListener( this.voViewer );
    nameColumn.addSelectionListener( columnListener );
    typeColumn.addSelectionListener( columnListener );
    
    // Initially we sort the VOs by name, ascending
    voTable.setSortColumn( nameColumn );
    voTable.setSortDirection( SWT.UP );
    this.voViewer.setComparator( new TableColumnComparator( this.voViewer, nameColumn ) );
    
    this.voViewer.setInput( manager );
    this.voViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons();
      }
    } );
    manager.addGridModelListener( this );
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
            } else {
              // We want a radio-button behavior, as there is only one default VO
              VoPreferencePage.this.voViewer.setCheckedElements( new Object[] { vo } );
              VoPreferencePage.this.voViewer.refresh();
              manager.setDefault( vo );
            }
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
    this.addButton.setText( Messages.getString("VoPreferencePage.add_button") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addButton.setLayoutData( gData );
    this.importButton = new Button( buttons, SWT.PUSH );
    this.importButton.setText( Messages.getString("VoPreferencePage.import_button") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.importButton.setLayoutData( gData );
    this.editButton = new Button( buttons, SWT.PUSH );
    this.editButton.setText( Messages.getString("VoPreferencePage.edit_button") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.editButton.setLayoutData( gData );
    this.removeButton = new Button( buttons, SWT.PUSH );
    this.removeButton.setText( Messages.getString("VoPreferencePage.remove_button") ); //$NON-NLS-1$
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
        editVO( null );
      }
    } );
    this.importButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        importVOs();
      }
    } );
    this.editButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        IVirtualOrganization selectedVo = getSelectedVo();
        if ( selectedVo != null ) {
          editVO( selectedVo );
        }
      }
    } );
    this.removeButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        removeSelectedVOs();
      }
    } );
    
    updateButtons();
    
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
   * Trigger the new VO wizard to pop up in order to create a new VO if vo
   * parameter is null or edit existing vo if vo parameter is specified.
   * 
   * @param vo To {@link IVirtualOrganization} to be edited. 
   */
  protected void editVO( final IVirtualOrganization vo ) {
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/vo_wiz.gif" ); //$NON-NLS-1$
    Wizard wizard = new Wizard() {
      @Override
      public void addPages() {
        ExtPointWizardSelectionListPage page = new ExtPointWizardSelectionListPage(
            "pagename", //$NON-NLS-1$
            "eu.geclipse.ui.newVoWizards", //$NON-NLS-1$
            Messages.getString("VoPreferencePage.create_new_vo"), //$NON-NLS-1$
            Messages.getString("VoPreferencePage.create_new_vo_long"), //$NON-NLS-1$
            Messages.getString("VoPreferencePage.no_vo_providers") ); //$NON-NLS-1$
        page.setInitData( vo );
        if ( vo != null ) {
          page.setPreselectedId( vo.getWizardId(), true );
        }
        addPage( page );
      }
      
      @Override
      public boolean performFinish() {
        return false;
      }
    };
    wizard.setForcePreviousAndNextButtons( true );
    wizard.setNeedsProgressMonitor( true );
    wizard.setWindowTitle( Messages.getString("VoPreferencePage.create_new_vo") ); //$NON-NLS-1$
    wizard.setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    WizardDialog dialog = new WizardDialog( this.getShell(), wizard );
    dialog.open();
  }
  
  protected void importVOs() {
    VoImportWizard wizard = new VoImportWizard();
    WizardDialog dialog = new WizardDialog( getShell(), wizard );
    dialog.open();
  }
  
  /**
   * Remove all VOs that are currently selected in the table control.
   */
  protected void removeSelectedVOs() {
    List< IVirtualOrganization > vos = getSelectedVos();
    if ( !vos.isEmpty() ) {
      boolean confirm = !MessageDialog.openConfirm( getShell(),
                                                    Messages.getString("VoPreferencePage.delete_vos"), //$NON-NLS-1$
                                                    Messages.getString("VoPreferencePage.really_delete_vos") ); //$NON-NLS-1$
      if ( !confirm ) {
        IGridElement[] projectElements = {};
        
        // Collect a list of children of the GridRoot
        try {
          projectElements = GridModel.getRoot().getChildren( null );
        } catch ( GridModelException gme ) {
          // GridRoot is not a lazy container
        }
        
        IVoManager manager = GridModel.getVoManager();
        IGridProject igp = null;
        
        for ( IVirtualOrganization vo : vos ) {
          // Check if the given VO is used by some GridProject on the WS
          boolean used = false;
          for( IGridElement element : projectElements ) {
            igp = (IGridProject) element;
            if ( igp.isGridProject() && ( vo == igp.getVO() ) ) {
              used = true;
              break;
            }
          }
          
          if ( used ) {
            MessageDialog.openError( this.getShell(),
                                     Messages.getString("VoPreferencePage.error"), //$NON-NLS-1$
                                     String.format( Messages.getString("VoPreferencePage.vo_in_use"), //$NON-NLS-1$
                                                    vo.getName(),
                                                    igp.getName() ) );
          } else {
            try {
              manager.delete( vo );
            } catch( GridModelException gmExc ) {
              NewProblemDialog.openProblem( this.getShell(),
                                            Messages.getString("VoPreferencePage.error"), //$NON-NLS-1$
                                            Messages.getString("VoPreferencePage.delete_vo_failed") + " " + vo.getName(), //$NON-NLS-1$ //$NON-NLS-2$
                                            gmExc,
                                            null );
            }
          }
        }
        updateButtons();
      }
    }
  }
  
  /**
   * Update the enabled state of the button controls.
   */
  protected void updateButtons() {
    
    ISelection selection = this.voViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    
    this.addButton.setEnabled( true );
    this.importButton.setEnabled( true );
    this.removeButton.setEnabled( selectionAvailable );
    this.editButton.setEnabled( selectionAvailable );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeListener#contentChanged(org.eclipse.compare.IContentChangeNotifier)
   */
  public void gridModelChanged( final IGridModelEvent source ) {
    this.voViewer.refresh();
    IVoManager manager = GridModel.getVoManager();
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
      GridModel.getVoManager().saveElements();
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
      GridModel.getVoManager().loadElements();
    } catch( GridModelException gmExc ) {
      // TODO mathias
      Activator.logException( gmExc );
    }
    super.performDefaults();
  }
  
}
