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

package eu.geclipse.traceview.internal;

import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

class MarkerSelectionMenuCreator implements IMenuCreator {
  private AbstractGraphVisualization graphVis;

  MarkerSelectionMenuCreator( AbstractGraphVisualization graphVis ) {
    this.graphVis = graphVis; 
  }

  public void dispose() {
  }

  public Menu getMenu( final Control parent ) {
    Menu menu = new Menu( parent );
    fill( menu );
    return menu;
  }

  public Menu getMenu( final Menu parent ) {
    Menu m = new Menu( parent );
    MenuItem item = new MenuItem( m, SWT.PUSH );
    item.setText( Messages.getString("MarkerSelectionMenuCreator.toggleMarkers") ); //$NON-NLS-1$
    return m;
  }

  void fill( final Menu menu ) {
    for (final EventMarkerEntry entry : graphVis.eventMarkers.getEventMarkerEntries() ) {
      MenuItem item = new MenuItem( menu, SWT.CHECK );
      item.setText( entry.label );
//      item.setImage( provider.getImage() );
      item.setSelection( entry.enabled );
      item.addSelectionListener( new SelectionAdapter() {
        @Override
        public void widgetSelected( final SelectionEvent e ) {
          entry.enabled ^= true;
          graphVis.eventMarkers.buildEventMarkersList();
          graphVis.redraw();
        }
      } );
    }
  }
}
