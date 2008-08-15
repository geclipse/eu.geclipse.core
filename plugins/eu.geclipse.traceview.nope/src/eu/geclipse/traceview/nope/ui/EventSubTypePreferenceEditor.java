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

package eu.geclipse.traceview.nope.ui;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.nope.Activator;
import eu.geclipse.traceview.nope.preferences.PreferenceConstants;

/**
 * Editor of Nope Event Legend Preferences
 */
public class EventSubTypePreferenceEditor {

  IPreferenceStore store;
  String name;
  Button button;
  Combo combo;

  /**
   * Creates a new EventSubTypePreferenceEditor
   * 
   * @param composite
   * @param name
   */
  public EventSubTypePreferenceEditor( final Composite composite,
                                       final String name )
  {
    this.name = name;
    this.store = Activator.getDefault().getPreferenceStore();
    // Label
    Label label = new Label( composite, SWT.NONE );
    label.setText( name );
    // ColorSelector
    ColorSelector colorSelector = new ColorSelector( composite );
    colorSelector.setColorValue( PreferenceConverter.getColor( this.store,
                                                               name
                                                                   + PreferenceConstants.color ) );
    GridData gd = new GridData();
    gd.widthHint = 32;
    gd.heightHint = 16;
    colorSelector.getButton().setLayoutData( gd );
    colorSelector.addListener( new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handlePropertyChangeEvent( event );
      }
    } );
    // Combo
    this.combo = new Combo( composite, SWT.READ_ONLY );
    int selection = this.store.getInt( this.name + PreferenceConstants.shape );
    switch( selection ) {
      case IEventMarker.No_Event:
        selection = 0;
      break;
      case IEventMarker.Rectangle_Event:
        selection = 1;
      break;
      case IEventMarker.Ellipse_Event:
        selection = 2;
      break;
      case IEventMarker.Cross_Event:
        selection = 3;
      break;
      case IEventMarker.Triangle_Event:
        selection = 4;
      break;
      case IEventMarker.Diamond_Event:
        selection = 5;
      break;
      default:
        selection = 0;
    }
    this.combo.setItems( new String[]{
      "None", //$NON-NLS-1$
      "Rectangle", //$NON-NLS-1$
      "Circle", //$NON-NLS-1$
      "Cross", //$NON-NLS-1$
      "Triangle", //$NON-NLS-1$
      "Diamond" //$NON-NLS-1$
    } );
    this.combo.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleSelectionEvent2();
      }
    } );
    this.combo.select( selection );
    // CheckBox
    this.button = new Button( composite, SWT.CHECK );
    this.button.setSelection( this.store.getBoolean( name
                                                     + PreferenceConstants.enabled ) );
    this.button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleSelectionEvent();
      }
    } );
  }

  protected void handlePropertyChangeEvent( final PropertyChangeEvent event ) {
    if( event.getNewValue() instanceof RGB ) {
      RGB rgb = ( RGB )event.getNewValue();
      PreferenceConverter.setValue( this.store,
                                    this.name + PreferenceConstants.color,
                                    rgb );
    }
  }

  protected void handleSelectionEvent() {
    this.store.setValue( this.name + PreferenceConstants.enabled,
                         this.button.getSelection() );
  }

  protected void handleSelectionEvent2() {
    int value = 0;
    switch( this.combo.getSelectionIndex() ) {
      case 0:
        value = IEventMarker.No_Event;
      break;
      case 1:
        value = IEventMarker.Rectangle_Event;
      break;
      case 2:
        value = IEventMarker.Ellipse_Event;
      break;
      case 3:
        value = IEventMarker.Cross_Event;
      break;
      case 4:
        value = IEventMarker.Triangle_Event;
      break;
      case 5:
        value = IEventMarker.Diamond_Event;
      break;
      default:
        value = -1;
    }
    this.store.setValue( this.name + PreferenceConstants.shape, value );
  }
}
