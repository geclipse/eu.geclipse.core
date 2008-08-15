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

package eu.geclipse.traceview.preferences;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.Messages;

/**
 * Preference Settings for Trace Visualization
 */
public class PreferencesPage extends PreferencePage
  implements IWorkbenchPreferencePage
{

  IPreferenceStore store;
  Button button;

  /**
   * Creates a new PreferencePage
   */
  public PreferencesPage() {
    super();
    this.store = Activator.getDefault().getPreferenceStore();
  }

  public void init( final IWorkbench workbench ) {
    // nothing
  }

  @Override
  protected Control createContents( final Composite parent ) {
    this.setTitle( Messages.getString( "PreferencePage.Title" ) ); //$NON-NLS-1$
    Composite composite = new Composite( parent, SWT.NULL );
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    composite.setLayout( layout );
    composite.setLayoutData( layoutData );
    createEventsGroup( composite );
    createMessagesGroup( composite );
    createSettingsGroup( composite );
    return null;
  }

  private void createEventsGroup( final Composite composite ) {
    // group
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layout.numColumns = 6;
    Group eventGroup = new Group( composite, SWT.NONE );
    eventGroup.setLayout( layout );
    eventGroup.setLayoutData( layoutData );
    eventGroup.setText( Messages.getString( "PreferencePage.Events" ) ); //$NON-NLS-1$
    new EventPreferenceEditor( eventGroup,
                               Messages.getString( "PreferencePage.SendEvents" ), //$NON-NLS-1$
                               PreferenceConstants.P_SEND_EVENT );
    new EventPreferenceEditor( eventGroup,
                               Messages.getString( "PreferencePage.ReceiveEvents" ), //$NON-NLS-1$
                               PreferenceConstants.P_RECV_EVENT );
    new EventPreferenceEditor( eventGroup,
                               Messages.getString( "PreferencePage.TestEvents" ), //$NON-NLS-1$
                               PreferenceConstants.P_TEST_EVENT );
    new EventPreferenceEditor( eventGroup,
                               Messages.getString( "PreferencePage.OtherEvents" ), //$NON-NLS-1$
                               PreferenceConstants.P_OTHER_EVENT );
  }

  private void createMessagesGroup( final Composite composite ) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    Group messageGroup = new Group( composite, SWT.NONE );
    messageGroup.setLayout( layout );
    messageGroup.setLayoutData( layoutData );
    messageGroup.setText( Messages.getString( "PreferencePage.Messages" ) ); //$NON-NLS-1$
    // Label
    Label name = new Label( messageGroup, SWT.NONE );
    name.setText( Messages.getString( "PreferencePage.Message" ) ); //$NON-NLS-1$
    // ColorSelector
    ColorSelector colorSelector = new ColorSelector( messageGroup );
    colorSelector.setColorValue( PreferenceConverter.getColor( this.store,
                                                               PreferenceConstants.P_MESSAGE
                                                                   + PreferenceConstants.P_COLOR ) );
    GridData gd = new GridData();
    gd.widthHint = 32;
    gd.heightHint = 16;
    colorSelector.getButton().setLayoutData( gd );
    colorSelector.addListener( new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        if( event.getNewValue() instanceof RGB ) {
          RGB rgb = ( RGB )event.getNewValue();
          PreferenceConverter.setValue( PreferencesPage.this.store,
                                        PreferenceConstants.P_MESSAGE
                                            + PreferenceConstants.P_COLOR,
                                        rgb );
        }
      }
    } );
  }

  private void createSettingsGroup( final Composite composite ) {
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    Group settingsGroup = new Group( composite, SWT.NONE );
    settingsGroup.setLayout( layout );
    settingsGroup.setLayoutData( layoutData );
    settingsGroup.setText( Messages.getString( "PreferencePage.Settings" ) ); //$NON-NLS-1$
    // CheckBox
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.horizontalSpan = 2;
    this.button = new Button( settingsGroup, SWT.CHECK );
    this.button.setText( Messages.getString( "PreferencePage.AntiAliasing" ) ); //$NON-NLS-1$
    this.button.setSelection( this.store.getBoolean( PreferenceConstants.P_ANTI_ALIASING ) );
    this.button.setLayoutData( layoutData );
    this.button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        PreferencesPage.this.store.setValue( PreferenceConstants.P_ANTI_ALIASING,
                                             PreferencesPage.this.button.getSelection() );
      }
    } );
    // Selection Color
    Label name = new Label( settingsGroup, SWT.NONE );
    name.setText( Messages.getString( "PreferencePage.SelectionColor" ) ); //$NON-NLS-1$
    // ColorSelector
    ColorSelector colorSelector = new ColorSelector( settingsGroup );
    colorSelector.setColorValue( PreferenceConverter.getColor( this.store,
                                                               PreferenceConstants.P_SELECTION_COLOR ) );
    GridData gd = new GridData();
    gd.widthHint = 32;
    gd.heightHint = 16;
    colorSelector.getButton().setLayoutData( gd );
    colorSelector.addListener( new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        if( event.getNewValue() instanceof RGB ) {
          RGB rgb = ( RGB )event.getNewValue();
          PreferenceConverter.setValue( PreferencesPage.this.store,
                                        PreferenceConstants.P_SELECTION_COLOR,
                                        rgb );
        }
      }
    } );
  }
}
