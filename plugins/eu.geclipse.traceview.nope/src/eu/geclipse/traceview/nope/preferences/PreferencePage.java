/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.nope.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
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

import eu.geclipse.traceview.nope.Activator;

/**
 * Preference page for the Nope TraceReader
 *
 */
public class PreferencePage extends org.eclipse.jface.preference.PreferencePage
  implements IWorkbenchPreferencePage {

  IPreferenceStore store;
  Button vectorClocks;

  /**
   * Creates a new preference page
   */
  public PreferencePage() {
    super();
    this.store = Activator.getDefault().getPreferenceStore();
  }

  @Override
  protected Control createContents( final Composite parent ) {
    this.setTitle( Messages.getString("PreferencePage.pageTitle") ); //$NON-NLS-1$
    Composite composite = new Composite( parent, SWT.NULL );
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    composite.setLayout( layout );
    composite.setLayoutData( layoutData );
    createSettingsGroup( composite );
    return null;
  }

  private void createSettingsGroup( final Composite composite ) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    Group settingsGroup = new Group( composite, SWT.NONE );
    settingsGroup.setLayout( layout );
    settingsGroup.setLayoutData( layoutData );
    settingsGroup.setText( Messages.getString("PreferencePage.readerSettings") ); //$NON-NLS-1$
    // CheckBox
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.horizontalSpan = 2;
    this.vectorClocks = new Button( settingsGroup, SWT.CHECK );
    this.vectorClocks.setText( Messages.getString("PreferencePage.calcVectorClocks") ); //$NON-NLS-1$
    this.vectorClocks.setSelection( this.store.getBoolean( PreferenceConstants.vectorClocks ) );
    this.vectorClocks.setLayoutData( layoutData );
    this.vectorClocks.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        PreferencePage.this.store.setValue( PreferenceConstants.vectorClocks,
                                            PreferencePage.this.vectorClocks.getSelection() );
      }
    } );
  }

  public void init( final IWorkbench workbench ) {
    // TODO Auto-generated method stub
  }
}
