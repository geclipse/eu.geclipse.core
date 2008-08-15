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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.Messages;

/**
 * Preference Page for the Event Markers
 */
public class MarkerPreferencesPage extends PreferencePage
  implements IWorkbenchPreferencePage
{

  IPreferenceStore store;
  Button button;
  private Button upButton;
  private Button downButton;

  /**
   * Creates a new MarkerPreferencesPage
   */
  public MarkerPreferencesPage() {
    super();
    this.store = Activator.getDefault().getPreferenceStore();
  }

  public void init( final IWorkbench workbench ) {
    // nothing
  }

  @Override
  protected Control createContents( final Composite parent ) {
    this.setTitle( Messages.getString( "MarkerPreferencesPage.pageTitle" ) ); //$NON-NLS-1$
    Composite composite = new Composite( parent, SWT.NULL );
    GridLayout layout = new GridLayout( 2, false );
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    composite.setLayout( layout );
    composite.setLayoutData( layoutData );
    GridData gData;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    composite.setLayout( layout );
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 1;
    gData.verticalSpan = 3;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 200;
    gData.heightHint = 100;
    final Table table = new Table( composite, SWT.CHECK
                                              | SWT.BORDER
                                              | SWT.H_SCROLL
                                              | SWT.V_SCROLL );
    MarkerEntry[] eventMarkers = getEventMarkers();
    for( MarkerEntry entry : eventMarkers ) {
      TableItem item = new TableItem( table, SWT.NONE );
      item.setData( entry );
      item.setText( entry.label );
      item.setChecked( entry.checked );
    }
    table.setLayoutData( gData );
    table.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        updateButtonStates( table );
      }
    } );
    table.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        ( ( MarkerEntry )table.getSelection()[ 0 ].getData() ).checked = event.detail == SWT.CHECK;
      }
    } );
    this.upButton = new Button( composite, SWT.PUSH );
    this.upButton.setText( Messages.getString( "MarkerPreferencesPage.up" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.upButton.setLayoutData( gData );
    this.upButton.setEnabled( false );
    this.upButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        moveSelectedItem( table, -1 );
      }
    } );
    this.downButton = new Button( composite, SWT.PUSH );
    this.downButton.setText( Messages.getString( "MarkerPreferencesPage.down" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.downButton.setLayoutData( gData );
    this.downButton.setEnabled( false );
    this.downButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        moveSelectedItem( table, 1 );
      }
    } );
    return composite;
  }

  MarkerEntry[] getEventMarkers() {
    List<MarkerEntry> eventMarkers = new ArrayList<MarkerEntry>();
    for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
      .getConfigurationElementsFor( "eu.geclipse.traceview.EventMarker" ) ) { //$NON-NLS-1$
      eventMarkers.add( new MarkerEntry( configurationElement.getAttribute( "id" ), //$NON-NLS-1$
                                         configurationElement.getAttribute( "label" ), //$NON-NLS-1$
                                         true ) );
    }
    return eventMarkers.toArray( new MarkerEntry[ 0 ] );
  }

  void moveSelectedItem( final Table table, final int offset ) {
    int index = table.getSelectionIndex();
    TableItem item = table.getItem( index );
    boolean checked = item.getChecked();
    MarkerEntry entry = ( MarkerEntry )item.getData();
    table.remove( index );
    TableItem newItem = new TableItem( table, SWT.NONE, index + offset );
    newItem.setChecked( checked );
    newItem.setData( entry );
    newItem.setText( entry.label );
    table.setSelection( index + offset );
    updateButtonStates( table );
  }

  void updateButtonStates( final Table table ) {
    this.upButton.setEnabled( table.getSelectionIndex() != 0 );
    this.downButton.setEnabled( table.getSelectionIndex() != table.getItemCount() - 1 );
  }
}

class MarkerEntry {

  boolean checked;
  String id;
  String label;

  MarkerEntry( final String id, final String label, final boolean checked ) {
    this.id = id;
    this.label = label;
    this.checked = checked;
  }
}
