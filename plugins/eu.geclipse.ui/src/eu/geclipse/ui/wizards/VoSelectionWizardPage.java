/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - switch to use a CheckboxTableViewer
 *                      - make it a IGridModelListener
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PreferencesUtil;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 * A wizard page allowing to select a VO from the list of VOs registered
 * in the VO manager. It also allows to edit the VOs on the fly by opening
 * the VO preferences page.
 */
public class VoSelectionWizardPage extends WizardPage
    implements IGridModelListener
{
  
  CheckboxTableViewer tableViewer;
  java.util.List< IVirtualOrganization > voList;
  Class< ? > voType;
  boolean allowMultiSelection;
  
  private Text infoText;
  
  private final String PREFERENCE_PAGE_ID = "eu.geclipse.ui.internal.preference.VOPreferencePage"; //$NON-NLS-1$
  
  
  public VoSelectionWizardPage( final boolean allowMultiSelection ) {
    this( allowMultiSelection, null );
  }
  
  public VoSelectionWizardPage( final boolean allowMultiSelection,
                                final Class< ? > voType ) {
    super( "eu.geclipse.ui.voSelectionWizardPage", //$NON-NLS-1$
           "VO Selection Page",
           null );
    setDescription( "Specify the VO that should be used" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/vo_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    this.allowMultiSelection = allowMultiSelection;
    this.voType = voType;
    this.voList = new ArrayList< IVirtualOrganization >();
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    mainComp.setLayoutData( gData );
    
    Group voGroup = new Group( mainComp, SWT.NONE );
    voGroup.setLayout( new GridLayout( 2, false ) );
    voGroup.setText( "&Available VOs" );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.heightHint = 120;
    voGroup.setLayoutData( gData );
    
    int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.CHECK;    
    Table voTable = new Table( voGroup, style );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true);
    voTable.setLayoutData( gData );
    voTable.setHeaderVisible( false );
    
    this.tableViewer = new CheckboxTableViewer( voTable );
    this.tableViewer.setContentProvider( new ArrayContentProvider() );
    this.tableViewer.setLabelProvider( new LabelProvider() {
      @Override
      public String getText( final Object element ) {
        return ( (IVirtualOrganization) element ).getName();
      }
    } );
    
    this.tableViewer.setComparator( new ViewerComparator() {
      @Override
      public int compare( final Viewer viewer, final Object vo1, final Object vo2 ) {
        String name1 = ( (IVirtualOrganization) vo1 ).getName();
        String name2 = ( (IVirtualOrganization) vo2 ).getName();
        // Sort by VO name
        return name1.compareTo( name2 );
      }
    } );

    // Filter the VOs that can be used to create this type of auth-token
    this.tableViewer.addFilter( new ViewerFilter() {
      @Override
      public boolean select( final Viewer viewer,
                             final Object parentElement,
                             final Object element )
      {
        boolean ret = false;

        Class< ? > type = VoSelectionWizardPage.this.voType;
        if ( element instanceof IVirtualOrganization ) {
          IVirtualOrganization vo = (IVirtualOrganization) element;
          if ( ( type == null ) || type.isAssignableFrom( vo.getClass() ) ) {
            ret = true;
          }
        }
        return ret;
      }
    } );
    
    this.tableViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        showSelectedInfo();
      }
    } );
    
    this.tableViewer.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        
        // If allowMultiSelection is not set, then implement a radio-button behavior
        if ( ! VoSelectionWizardPage.this.allowMultiSelection ) {
          Object element = event.getElement();
          if ( element instanceof IVirtualOrganization ) {
            IVirtualOrganization vo = ( IVirtualOrganization ) element;
            if ( event.getChecked() ) {
              VoSelectionWizardPage.this.tableViewer.setCheckedElements( new Object[] { vo } );
              VoSelectionWizardPage.this.tableViewer.refresh();
            }
          }
        }
        
        setPageComplete( getSelectedVos() != null );
      }
    } );
    
    updateVoList();
    this.tableViewer.setInput( this.voList );
    
    // Listen on new VOs, this will update the tableViewer
    GridModel.getVoManager().addGridModelListener( this );
    
    Button editVOsButton = new Button( voGroup, SWT.PUSH );
    editVOsButton.setText( "Edit &VOs..." );
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    gData.verticalAlignment = GridData.BEGINNING;
    editVOsButton.setLayoutData( gData );
    editVOsButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        PreferenceDialog dialog
          = PreferencesUtil.createPreferenceDialogOn( getShell(),
                VoSelectionWizardPage.this.PREFERENCE_PAGE_ID,
                null,
                null );
        
        /*
         * Even if the user pressed 'Cancel' VOs might have been
         * added/deleted, so ignore the dialog's return value.
         */
        dialog.open();
      }
    } );
    
    Group infoGroup = new Group( mainComp, SWT.NONE );
    infoGroup.setLayout( new GridLayout( 1, false ) );
    infoGroup.setText( "&VO Info" );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.heightHint = 120;
    infoGroup.setLayoutData( gData );
    
    this.infoText = new Text( infoGroup, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
    this.infoText.setEditable( false );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.infoText.setLayoutData( gData );
    
    setInitialSelection();
    showSelectedInfo();
    setPageComplete( getSelectedVos() != null );
    
    setControl( mainComp );
    
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelListener#gridModelChanged(eu.geclipse.core.model.IGridModelEvent)
   */
  public void gridModelChanged( final IGridModelEvent event ) {
    IGridElement[] elements = event.getElements();
    
    // We are a listener of the VoManager only
    assert elements[ 0 ] instanceof IVirtualOrganization
      : "VoSelectionWizardPage expects events whose source is an IVirtualOrganization."; //$NON-NLS-1$
    
    switch ( event.getType() ) {
      case IGridModelEvent.ELEMENTS_ADDED:
        updateVoList();
        this.tableViewer.setInput( this.voList );
        // Each event contains a single element
        setSelection( elements[ 0 ] );
        break;
      case IGridModelEvent.ELEMENTS_REMOVED:
        updateVoList();
        this.tableViewer.setInput( this.voList );
        // If the selected VO has been deleted select the default one
        IVirtualOrganization[] selectedVOs = getSelectedVos();
        if ( selectedVOs == null ) {
          setInitialSelection();
        }
        break;
      default:
        // Do nothing in other cases
    }
    setPageComplete( getSelectedVos() != null );        
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    GridModel.getVoManager().removeGridModelListener( this );
    super.dispose();
  }
  
  /**
   * Get the list of checked VOs
   * 
   * @return the array of VOs checked in the viewer
   */
  public IVirtualOrganization[] getSelectedVos() {
    
    java.util.List< IVirtualOrganization > selectedVos
      = new ArrayList< IVirtualOrganization >();

    for ( Object vo : this.tableViewer.getCheckedElements() ) {
      selectedVos.add( ( IVirtualOrganization ) vo );
    }
    
    if ( selectedVos.size() == 0 ) {
      setErrorMessage( "No valid VO is selected" );
    } else {
      setErrorMessage( null );
    }
    
    return
      selectedVos.size() == 0
        ? null
        : selectedVos.toArray( new IVirtualOrganization[ selectedVos.size() ] );
  }
  
  /**
   * Select the given VOs by setting the checkboxes
   * 
   * @param vos an array of VOs to check on the viewer
   */
  public void setSelectedVos( final IGridElement[] vos ) {
    if ( vos == null || vos.length == 0 ) {
      return;
    }
    
    if ( VoSelectionWizardPage.this.allowMultiSelection ) {
      this.tableViewer.setCheckedElements( vos );
    } else {
      assert vos.length == 1
        : "VoSelectionWizardPage got more than one VO to select while allowMultiSelection=false."; //$NON-NLS-1$
      this.tableViewer.setChecked( vos[ 0 ], true );
    }
    this.tableViewer.refresh();
  }
  
  protected void showSelectedInfo() {
    int index = this.tableViewer.getTable().getSelectionIndex();
    if ( index >= 0 ) {
      IVirtualOrganization vo
        = (IVirtualOrganization) this.tableViewer.getElementAt( index );
      try {
        String text = ""; //$NON-NLS-1$
        text += "Name:\n\t" + vo.getName() + '\n';
        text += "Type:\n\t" + vo.getTypeName() + '\n';
        IGridInfoService infoService = vo.getInfoService();
        if ( infoService != null ) {
          text += "\nInformation Service:\n\tName:\n\t\t" + infoService.getName();
          text += "\n\tURI:\n\t\t" + infoService.getURI().toString(); 
        }

        //
        // This forces an Infosystem query for every click on a VO!! 
        //   Don't show non-static info here!
        /*
        IGridService[] services = vo.getServices();
        text += "\n\nOther Services:";
        if ( ( services != null ) && ( services.length > 1 ) ) {
          for ( IGridService service : services ) {
            if ( service != infoService ) {
              text += "\n\tName:\n\t\t" + service.getName();
              URI uri = service.getURI();
              if ( uri != null ) {
                text += "\n\tURI:\n\t\t" + service.getURI().toString();
              } else {
                text += "\n\tURI:\n\t\tN/A";
              }
            }
          }
        } else {
          text += "\n\tNone";
        }
        */
        this.infoText.setText( text );
      } catch ( ProblemException pExc ) {
        ProblemDialog.openProblem( getShell(),
                                   "VO info problem",
                                   "Unable to query services for VO " + vo.getName(),
                                   pExc );
      }
    } else {
      this.infoText.setText( "" ); //$NON-NLS-1$
    }
    // This is needed if the text is changed while the dialog is not focused
    this.infoText.redraw();
  }
  
  /**
   * Load the list of VOs from the manager.
   */
  private void updateVoList() {
    this.voList.clear();
    try {
      IGridElement[] vos = GridModel.getVoManager().getChildren( null );
      for ( IGridElement vo : vos ) {
        this.voList.add( ( IVirtualOrganization ) vo );
      }
    } catch ( ProblemException pExc ) {
      ProblemDialog.openProblem( getShell(),
                                 "VO list problem",
                                 "Unable to query registered VOs",
                                 pExc );
    }
  }
  
  /**
   * Set the specified VO as selected (clicked and checked) in the viewer.
   * 
   * @param vo the VO to set as checked/selected
   */
  private void setSelection( final IGridElement vo ) {
    
    // Do nothing if there is no VO to select
    if ( vo == null || ! ( vo instanceof IVirtualOrganization ) ) {
      return;
    }
    
    // If allowMultiSelection is not set we implement a radio-button behavior
    if ( VoSelectionWizardPage.this.allowMultiSelection ) {
      this.tableViewer.setChecked( vo, true );
    } else {
      this.tableViewer.setCheckedElements( new Object[] { vo } );
    }
    this.tableViewer.refresh();
    
    StructuredSelection sel = new StructuredSelection( vo );
    this.tableViewer.setSelection( sel, true );
  }
  
  /**
   * Determines which VO to select initially in the viewer.
   */
  private void setInitialSelection() {
    IGridElement defaultVo = GridModel.getVoManager().getDefault();
    
    if ( defaultVo == null ) {
      // No default VO, take the first entry if any, null otherwise
      defaultVo = ( IGridElement ) this.tableViewer.getElementAt( 0 );
    } else {
      // Check if the default VO is of the requested voType
      boolean show = true;
      for ( ViewerFilter filter: this.tableViewer.getFilters() ) {
        show &= filter.select( this.tableViewer, this.voList, defaultVo );
      }
      // If it is not, then select the first of the list if any, null otherwise
      if ( ! show ) {
        defaultVo = ( IGridElement ) this.tableViewer.getElementAt( 0 );
      }
    }
    
    setSelection( defaultVo );
  }

}
