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

package eu.geclipse.traceview.views.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceVisProvider;
import eu.geclipse.traceview.TraceVisualization;
import eu.geclipse.traceview.views.TraceView;

/**
 * Contains a Trace Visualization
 */
public class TraceVisPage extends Composite {

  protected IContributionItem[] items = new IContributionItem[ 0 ];
  private TraceVisualization visualization;
  private ITrace trace;
  private IViewSite viewSite;
  private TraceView traceView;
  private String visualisationID;

  /**
   * Creates a new TraceVisPage
   * 
   * @param cTabFolder - the folder which will contain the Visualization
   * @param style - the style
   * @param viewSite - the ViewSite which
   * @param traceView - the trace view the page is opened in
   * @param trace - the Trace to display
   */
  public TraceVisPage( final CTabFolder cTabFolder,
                       final int style,
                       final IViewSite viewSite,
                       final TraceView traceView,
                       final ITrace trace )
  {
    super( cTabFolder, style );
    this.traceView = traceView;
    this.viewSite = viewSite;
    this.trace = trace;
    GridLayout gridLayout = new GridLayout();
    GridData gridData = new GridData();
    this.setLayout( gridLayout );
    this.setLayoutData( gridData );
    CTabItem cTabItem = new CTabItem( cTabFolder, SWT.CLOSE );
    cTabItem.setControl( this );
    cTabItem.addDisposeListener( new DisposeListener() {

      public void widgetDisposed( final DisposeEvent e ) {
        dispose();
      }
    } );
    cTabItem.setText( this.trace.getName() );
    cTabFolder.setSelection( cTabItem );
    // TODO add preference entry
    changeToVisualisation( "eu.geclipse.traceview.logicalgraph.LogicalGraphProvider" ); //$NON-NLS-1$
  }

  /**
   * Returns the visualization id
   * 
   * @return id
   */
  public String getVisualisationID() {
    return this.visualisationID;
  }

  /**
   * Changes to the specified visualization
   * 
   * @param id
   */
  public void changeToVisualisation( final String id ) {
    if( id != null ) {
      this.visualisationID = id;
      for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
        .getConfigurationElementsFor( "eu.geclipse.traceview.TraceVisualization" ) ) { //$NON-NLS-1$^
        if( id.equals( configurationElement.getAttribute( "id" ) ) ) { //$NON-NLS-1$
          // remove items from action bar
          this.traceView.getViewSite()
            .getActionBars()
            .getToolBarManager()
            .removeAll();
          this.traceView.getViewSite()
            .getActionBars()
            .getToolBarManager()
            .update( true );
          // dispose visualization
          if( this.visualization != null && !this.visualization.isDisposed() ) {
            this.visualization.dispose();
          }
          // create new visualization
          try {
            ITraceVisProvider provider = ( ITraceVisProvider )configurationElement.createExecutableExtension( "class" ); //$NON-NLS-1$
            this.visualization = provider.getTraceVis( this,
                                                       SWT.NONE,
                                                       this.viewSite,
                                                       this.trace );
            this.items = this.visualization.getToolBarItems();
          } catch( CoreException coreException ) {
            // nothing
          }
          // add visualization specific items to bar
          if( this.items != null ) {
            for( IContributionItem item : this.items ) {
              this.traceView.getViewSite()
                .getActionBars()
                .getToolBarManager()
                .add( item );
            }
          }
          // add standard bar
          this.traceView.createToolbar( id );
          this.viewSite.getActionBars().getToolBarManager().update( true );
          this.layout( true );
        }
      }
    }
  }

  /**
   * Returns the items for that will contribute to the Menubar
   * 
   * @return Menubar Items
   */
  public IContributionItem[] getItems() {
    return this.items;
  }

  @Override
  public void dispose() {
    for( IContributionItem item : this.items ) {
      this.viewSite.getActionBars().getToolBarManager().remove( item );
    }
    this.viewSite.getActionBars().getToolBarManager().update( true );
    this.layout( true );
    super.dispose();
  }

  /**
   * Returns the Trace
   * 
   * @return trace
   */
  public ITrace getTrace() {
    return this.trace;
  }

  /**
   * Redraws the visualisation
   */
  public void redrawVisualisation() {
    if( this.visualization != null && !this.visualization.isDisposed() ) {
      this.visualization.redraw();
    }
  }

  public void printTrace( final GC gc ) {
    this.visualization.printTrace(gc);
  }

  public TraceVisualization getVisualization() {
    return this.visualization;
  }
}
