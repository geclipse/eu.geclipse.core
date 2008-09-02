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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.model.IVoLoader;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Wizard page for selection VOs to be imported.
 */
public class VoChooserPage extends WizardPage {
  
  protected CheckboxTableViewer viewer;

  private static class VoFilter extends ViewerFilter {
    
    private String filterText;
    
    /**
     * This filters's default constructor.
     */
    public VoFilter() {
      super();
    }
    
    /**
     * Set the filter's text that is used to filter the view's content.
     * 
     * @param text The text that has to match the beginning of the VO name.
     */
    public void setFilterText( final String text ) {
      this.filterText = text;
    }

    @Override
    public boolean select( final Viewer viewer,
                           final Object parentElement,
                           final Object element ) {
      return ( this.filterText == null )
        || ( this.filterText.trim().length() == 0)
        || ( ( String ) element ).toLowerCase().startsWith( this.filterText.toLowerCase() );
    }
    
  }
  
  
  protected Text filterText;
  
  protected VoFilter voFilter;

  protected List< Object > selection = new ArrayList< Object >();
  
  private VoImportLocationChooserPage locationChooserPage;
  

  /**
   * Construct a new chooser page.
   * 
   * @param locationChooserPage The location chooser page of the same wizard.
   */
  public VoChooserPage( final VoImportLocationChooserPage locationChooserPage ) {
    super( "voChooserPage", //$NON-NLS-1$
           Messages.getString("VoChooserPage.title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("VoChooserPage.description") ); //$NON-NLS-1$
    this.locationChooserPage = locationChooserPage;
  }

  public void createControl( final Composite parent ) {

    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    this.filterText = new Text( mainComp, SWT.BORDER );
    this.filterText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    this.filterText.setText( Messages.getString("VoChooserPage.initial_filter_text") ); //$NON-NLS-1$
    this.filterText.selectAll();
    
    Table table = new Table( mainComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    table.setLayoutData( gData );
    
    this.viewer = new CheckboxTableViewer( table );
    this.viewer.setContentProvider( new ArrayContentProvider() );
    this.voFilter = new VoFilter();
    this.viewer.addFilter( this.voFilter );
    
    Composite buttonComp = new Composite( mainComp, SWT.NULL );
    buttonComp.setLayout( new GridLayout( 3, false ) );
    gData = new GridData();
    buttonComp.setLayoutData( gData );
    
    Button selectAllButton = new Button( buttonComp, SWT.PUSH );
    selectAllButton.setText( Messages.getString("VoChooserPage.select_all_button") ); //$NON-NLS-1$
    gData = new GridData();
    selectAllButton.setLayoutData( gData );
    
    Button deselectAllButton = new Button( buttonComp, SWT.PUSH );
    deselectAllButton.setText( Messages.getString("VoChooserPage.deselect_all_button") ); //$NON-NLS-1$
    gData = new GridData();
    deselectAllButton.setLayoutData( gData );
    
    Button revertButton = new Button( buttonComp, SWT.PUSH );
    revertButton.setText( Messages.getString("VoChooserPage.revert_selection_button") ); //$NON-NLS-1$
    gData = new GridData();
    revertButton.setLayoutData( gData );
    
    this.viewer.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        handleCheckStateChanged( event );
      }
    } );
    
    this.filterText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        VoChooserPage.this.voFilter.setFilterText( VoChooserPage.this.filterText.getText() );
        VoChooserPage.this.viewer.refresh();
        VoChooserPage.this.viewer.setCheckedElements( VoChooserPage.this.selection.toArray( new Object[ VoChooserPage.this.selection.size() ] ) );
      }
    } );
    
    selectAllButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        selectAll();
      }
    } );
    
    deselectAllButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        deselectAll();
      }
    } );
    
    revertButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        revertSelection();
      }
    } );
    
    setControl( mainComp );
    
  }
  
  /**
   * Get all selected VOs.
   * 
   * @return A list containing the names of all selected VOs.
   */
  public String[] getSelectedVos() {
    
    String[] result = null;
    
    result = new String[ this.selection.size() ];
    for ( int i = 0 ; i < this.selection.size() ; i++ ) {
      result[ i ] = ( String ) this.selection.get( i );
    }
    
    return result;
    
  }
  
  @Override
  public void setVisible( final boolean visible ) {
    super.setVisible( visible );
    if ( visible ) {
      loadVoList();
      this.filterText.setFocus();
    }
  }
  
  protected void handleCheckStateChanged( final CheckStateChangedEvent event ) {
    Object element = event.getElement();
    if ( event.getChecked() ) {
      addToSelection( new Object[] { element } );
    } else if ( ! event.getChecked() ) {
      removeFromSelection( new Object[] { element } );
    }
  }
  
  protected void selectAll() {
    this.viewer.setAllChecked( true );
    addToSelection( this.viewer.getCheckedElements() );
  }
  
  protected void deselectAll() {
    removeFromSelection( this.viewer.getCheckedElements() );
    this.viewer.setAllChecked( false );
  }
  
  protected void revertSelection() {
    TableItem[] items = this.viewer.getTable().getItems();
    for ( TableItem item : items ) {
      String element = ( String ) item.getData();
      boolean state = this.viewer.getChecked( element );
      this.viewer.setChecked( element, ! state );
      if ( state ) {
        removeFromSelection( new Object[] { element } );
      } else {
        addToSelection( new Object[] { element } );
      }
    }
  }
  
  private void addToSelection( final Object[] elements ) {
    for ( Object element : elements ) {
      if ( ! this.selection.contains( element ) ) {
        this.selection.add( element );
      }
    }
  }
  
  private void removeFromSelection( final Object[] elements ) {
    for ( Object element : elements ) {
      if ( this.selection.contains( element ) ) {
        this.selection.remove( element );
      }
    }
  }
  
  private void loadVoList() {
    
    final IVoLoader loader = this.locationChooserPage.getLoader();
    final URI uri = this.locationChooserPage.getSelectedLocation();
    
    try {
      getContainer().run( false, true, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {
          try {
            String[] voArray = loader.getVoList( uri, monitor );
            List< String > voList = new ArrayList< String >();
            for ( String vo : voArray ) {
              if ( ! voList.contains( vo ) ) {
                voList.add( vo );
              }
            }
            VoChooserPage.this.viewer.setInput( voList );
          } catch ( ProblemException pExc ) {
            throw new InvocationTargetException( pExc );
          }
        }
      } );
    } catch ( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause();
      ProblemDialog.openProblem( getShell(),
                                 Messages.getString("VoChooserPage.import_failed_title"), //$NON-NLS-1$
                                 Messages.getString("VoChooserPage.import_failed_error"), //$NON-NLS-1$
                                 cause );
      setErrorMessage( cause.getLocalizedMessage() );
    } catch ( InterruptedException intExc ) {
      // Do nothing on user interrupt
    }
    
  }

}
