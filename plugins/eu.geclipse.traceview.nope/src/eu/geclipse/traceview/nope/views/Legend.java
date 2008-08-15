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

package eu.geclipse.traceview.nope.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;

import eu.geclipse.traceview.nope.preferences.PreferenceConstants;
import eu.geclipse.traceview.nope.ui.EventSubTypePreferenceEditor;

/**
 * Legend for Nope Events
 */
public class Legend extends ViewPart {

  @Override
  public void createPartControl( final Composite parent ) {
    GridData gd;
    ScrolledComposite scrolledComposite = new ScrolledComposite( parent,
                                                                 SWT.H_SCROLL
                                                                     | SWT.V_SCROLL );
    GridLayout layout = new GridLayout();
    scrolledComposite.setLayout( new GridLayout() );
    gd = new GridData();
    scrolledComposite.setLayoutData( gd );
    Composite composite = new Composite( scrolledComposite, SWT.NONE );
    layout = new GridLayout();
    layout.marginLeft = 10;
    layout.marginRight = 10;
    layout.marginTop = 10;
    layout.marginBottom = 10;
    layout.numColumns = 4;
    composite.setLayout( layout );
    gd = new GridData();
    composite.setLayoutData( gd );
    for( int i = 0; i < PreferenceConstants.Names.length; i++ ) {
      new EventSubTypePreferenceEditor( composite,
                                        PreferenceConstants.Names[ i ] );
    }
    scrolledComposite.setContent( composite );
    scrolledComposite.setExpandHorizontal( true );
    scrolledComposite.setExpandVertical( true );
    scrolledComposite.setMinSize( composite.computeSize( SWT.DEFAULT,
                                                         SWT.DEFAULT ) );
  }

  @Override
  public void setFocus() {
    // do nothing
  }
}