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

package eu.geclipse.traceview.views;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceVisProvider;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.Messages;

class DropDown implements IMenuCreator {

  TraceView view;
  String id;

  DropDown( final TraceView view, final String id ) {
    this.view = view;
    this.id = id;
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public Menu getMenu( final Control parent ) {
    Menu menu = new Menu( parent );
    fill( menu );
    return menu;
  }

  public Menu getMenu( final Menu parent ) {
    Menu m = new Menu( parent );
    MenuItem item = new MenuItem( m, SWT.PUSH );
    item.setText( Messages.getString( "DropDown.switchVisualization" ) ); //$NON-NLS-1$
    return m;
  }

  void fill( final Menu menu ) {
    ITrace trace = this.view.getTrace();
    for( final IConfigurationElement configurationElement : Platform.getExtensionRegistry()
      .getConfigurationElementsFor( "eu.geclipse.traceview.TraceVisualization" ) ) { //$NON-NLS-1$
      try {
        ITraceVisProvider provider = ( ITraceVisProvider )configurationElement.createExecutableExtension( "class" ); //$NON-NLS-1$
        if( provider.supports( trace ) ) {
          MenuItem item = new MenuItem( menu, SWT.CHECK );
          item.setText( configurationElement.getAttribute( "label" ) ); //$NON-NLS-1$
          item.setImage( provider.getImage() );
          if( configurationElement.getAttribute( "id" ).equals( this.id ) ) { //$NON-NLS-1$
            item.setSelection( true );
          }
          item.addSelectionListener( new SelectionAdapter() {

            @Override
            public void widgetSelected( final SelectionEvent e ) {
              DropDown.this.view.traceVisPage.changeToVisualisation( configurationElement.getAttribute( "id" ) ); //$NON-NLS-1$
            }
          } );
        }
      } catch( CoreException exception ) {
        Activator.logException( exception );
      }
    }
  }
}
