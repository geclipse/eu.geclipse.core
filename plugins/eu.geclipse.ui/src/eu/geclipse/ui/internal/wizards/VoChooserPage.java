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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import eu.geclipse.core.model.IVoLoader;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;


public class VoChooserPage extends WizardPage {
  
protected CheckboxTableViewer viewer;
  
  private VoImportLocationChooserPage locationChooserPage;
  
  public VoChooserPage( final VoImportLocationChooserPage locationChooserPage ) {
    super( "voChooserPage",
           "Choose VOs",
           null );
    setDescription( "Choose the VOs you would like to import" );
    this.locationChooserPage = locationChooserPage;
  }

  public void createControl( final Composite parent ) {

    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Table table = new Table( mainComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    table.setLayoutData( gData );
    
    this.viewer = new CheckboxTableViewer( table );
    this.viewer.setContentProvider( new ArrayContentProvider() );
    
    Composite buttonComp = new Composite( mainComp, SWT.NULL );
    buttonComp.setLayout( new GridLayout( 3, false ) );
    gData = new GridData();
    buttonComp.setLayoutData( gData );
    
    Button selectAllButton = new Button( buttonComp, SWT.PUSH );
    selectAllButton.setText( "&Select All" );
    gData = new GridData();
    selectAllButton.setLayoutData( gData );
    
    Button deselectAllButton = new Button( buttonComp, SWT.PUSH );
    deselectAllButton.setText( "&Deselect All" );
    gData = new GridData();
    deselectAllButton.setLayoutData( gData );
    
    Button revertButton = new Button( buttonComp, SWT.PUSH );
    revertButton.setText( "&Revert Selection" );
    gData = new GridData();
    revertButton.setLayoutData( gData );
    
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
  
  public String[] getSelectedVos() {
    
    String[] result = null;
    
    Object[] checkedElements = this.viewer.getCheckedElements();
    if ( checkedElements != null ) {
      result = new String[ checkedElements.length ];
      for ( int i = 0 ; i < checkedElements.length ; i++ ) {
        result[ i ] = ( String ) checkedElements[ i ];
      }
    }
    
    return result;
    
  }
  
  @Override
  public void setVisible( final boolean visible ) {
    super.setVisible( visible );
    if ( visible ) {
      loadVoList();
    }
  }
  
  protected void selectAll() {
    this.viewer.setAllChecked( true );
  }
  
  protected void deselectAll() {
    this.viewer.setAllChecked( false );
  }
  
  protected void revertSelection() {
    String[] elements = ( String[] ) this.viewer.getInput();
    for ( String element : elements ) {
      boolean state = this.viewer.getChecked( element );
      this.viewer.setChecked( element, ! state );
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
            String[] certificateList = loader.getVoList( uri, monitor );
            VoChooserPage.this.viewer.setInput( certificateList );
          } catch ( ProblemException pExc ) {
            throw new InvocationTargetException( pExc );
          }
        }
      } );
    } catch ( InvocationTargetException itExc ) {
      Throwable cause = itExc.getCause();
      ProblemDialog.openProblem( getShell(),
                                 "Import Failed",
                                 "Unable to load VO list",
                                 cause );
      setErrorMessage( cause.getLocalizedMessage() );
    } catch ( InterruptedException intExc ) {
      // Do nothing on user interrupt
    }
    
  }

}
