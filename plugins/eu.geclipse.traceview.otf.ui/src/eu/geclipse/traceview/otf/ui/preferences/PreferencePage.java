/*****************************************************************************
 * Copyright (c) 2010 g-Eclipse Consortium 
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
 *    Christof Klausecker MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.otf.ui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.eventgraph.tracereader.otf.preferences.PreferenceConstants;
import eu.geclipse.eventgraph.tracereader.otf.Activator;

/**
 * Preference page for the OTF reader
 */
@SuppressWarnings("unused")
public class PreferencePage extends org.eclipse.jface.preference.PreferencePage implements IWorkbenchPreferencePage {

  IPreferenceStore store;

  /**
   * Constructs a new PreferencePage
   */
  public PreferencePage() {
    super();
    this.store = Activator.getDefault().getPreferenceStore();
  }

  /**
   * Constructs a new PreferencePage
   * 
   * @param title
   */
  public PreferencePage( final String title ) {
    super( title );
    this.store = Activator.getDefault().getPreferenceStore();
  }

  /**
   * Constructs a new PreferencePage
   * 
   * @param title
   * @param image
   */
  public PreferencePage( final String title, final ImageDescriptor image ) {
    super( title, image );
    this.store = Activator.getDefault().getPreferenceStore();
  }

  @Override
  protected Control createContents( final Composite parent ) {
    this.setTitle( "Open Trace Format (OTF) Reader Preferences" );
    Composite composite = new Composite( parent, SWT.NULL );
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    composite.setLayout( layout );
    composite.setLayoutData( layoutData );
    createSettingsGroup( composite );
    return composite;
  }

  private void createSettingsGroup( final Composite composite ) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    Group settingsGroup = new Group( composite, SWT.NONE );
    settingsGroup.setLayout( layout );
    settingsGroup.setLayoutData( layoutData );
    settingsGroup.setText( "Reader Settings:" );
    // Vector Clocks CheckBox
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.horizontalSpan = 2;
    final Button vectorClocks = new Button( settingsGroup, SWT.CHECK );
    vectorClocks.setEnabled( false );
    vectorClocks.setText( "Calculate Vector Clocks" );
    vectorClocks.setSelection( this.store.getBoolean( PreferenceConstants.calculateVectorClocks ) );
    vectorClocks.setLayoutData( layoutData );
    vectorClocks.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        PreferencePage.this.store.setValue( PreferenceConstants.calculateVectorClocks, vectorClocks.getSelection() );
      }
    } );
    // Read Functions CheckBox
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.horizontalSpan = 2;
    final Button readFunctions = new Button( settingsGroup, SWT.CHECK );
    readFunctions.setText( "Read Function Events" );
    readFunctions.setSelection( this.store.getBoolean( PreferenceConstants.readFunctions ) );
    readFunctions.setLayoutData( layoutData );
    readFunctions.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        PreferencePage.this.store.setValue( PreferenceConstants.readFunctions, readFunctions.getSelection() );
        System.out.println( readFunctions.getSelection() );
      }
    } );
  }

  @Override
  public void init( final IWorkbench workbench ) {
    // TODO Auto-generated method stub
  }
}
