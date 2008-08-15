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
import eu.geclipse.traceview.internal.Activator;

/**
 * Editor of Nope Event Legend Preferences
 */
public class EventPreferenceEditor {

  IPreferenceStore store;
  String preference;
  String label;
  Button drawButton;
  Button button;
  Combo combo;

  /**
   * Creates a new EventSubTypePreferenceEditor
   * 
   * @param composite
   * @param label
   * @param preference
   */
  public EventPreferenceEditor( final Composite composite,
                                final String label,
                                final String preference )
  {
    this.label = label;
    this.preference = preference;
    this.store = Activator.getDefault().getPreferenceStore();
    // Label
    Label name = new Label( composite, SWT.NONE );
    name.setText( label );
    // ColorSelector
    ColorSelector colorSelector = new ColorSelector( composite );
    colorSelector.setColorValue( PreferenceConverter.getColor( this.store,
                                                               preference
                                                                   + PreferenceConstants.P_COLOR ) );
    GridData gd = new GridData();
    gd.widthHint = 32;
    gd.heightHint = 16;
    colorSelector.getButton().setLayoutData( gd );
    colorSelector.addListener( new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handleColorPropertyChangeEvent( event );
      }
    } );
    // CheckBox
    this.drawButton = new Button( composite, SWT.CHECK );
    this.drawButton.setSelection( this.store.getBoolean( preference
                                                         + PreferenceConstants.P_DRAW ) );
    this.drawButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleDrawPropertyChangeEvent();
      }
    } );
    // ColorSelector
    colorSelector = new ColorSelector( composite );
    colorSelector.setColorValue( PreferenceConverter.getColor( this.store,
                                                               preference
                                                                   + PreferenceConstants.P_FILL_COLOR ) );
    gd = new GridData();
    gd.widthHint = 32;
    gd.heightHint = 16;
    colorSelector.getButton().setLayoutData( gd );
    colorSelector.addListener( new IPropertyChangeListener() {

      public void propertyChange( final PropertyChangeEvent event ) {
        handleFillColorPropertyChangeEvent( event );
      }
    } );
    // CheckBox
    this.button = new Button( composite, SWT.CHECK );
    this.button.setSelection( this.store.getBoolean( preference
                                                     + PreferenceConstants.P_FILL ) );
    this.button.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleFillPropertyChangeEvent();
      }
    } );
    // Type Combo
    this.combo = new Combo( composite, SWT.READ_ONLY );
    // int selection = this.store.getInt( this.name + PreferenceConstants.shape
    // );
    int selection = 0;
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
  }

  protected void handleDrawPropertyChangeEvent() {
    this.store.setValue( this.preference + PreferenceConstants.P_DRAW,
                         this.drawButton.getSelection() );
  }

  protected void handleFillPropertyChangeEvent() {
    this.store.setValue( this.preference + PreferenceConstants.P_FILL,
                         this.button.getSelection() );
  }

  protected void handleColorPropertyChangeEvent( final PropertyChangeEvent event )
  {
    if( event.getNewValue() instanceof RGB ) {
      RGB rgb = ( RGB )event.getNewValue();
      PreferenceConverter.setValue( this.store,
                                    this.preference
                                        + PreferenceConstants.P_COLOR,
                                    rgb );
    }
  }

  protected void handleFillColorPropertyChangeEvent( final PropertyChangeEvent event )
  {
    if( event.getNewValue() instanceof RGB ) {
      RGB rgb = ( RGB )event.getNewValue();
      PreferenceConverter.setValue( this.store,
                                    this.preference
                                        + PreferenceConstants.P_FILL_COLOR,
                                    rgb );
    }
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
    this.store.setValue( this.preference + PreferenceConstants.P_SHAPE, value );
  }
}
