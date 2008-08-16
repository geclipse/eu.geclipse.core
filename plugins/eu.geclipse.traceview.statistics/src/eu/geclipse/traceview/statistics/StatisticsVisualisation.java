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

package eu.geclipse.traceview.statistics;

import java.util.ArrayList;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.TraceVisualization;
import eu.geclipse.traceview.statistics.chartbuilder.AbstractChartBuilder;
import eu.geclipse.traceview.statistics.providers.IStatistics;

/**
 * @author ck
 */
public class StatisticsVisualisation extends TraceVisualization {

  protected int x = 0;
  protected int y = 0;
  protected IDeviceRenderer render = null;
  protected Chart chart = null;
  protected GeneratedChartState state = null;
  private Image cachedImage = null;
  private ITrace trace = null;
  private Composite composite = null;


  /**
   * @param parent
   * @param style
   * @param viewSite
   * @param trace
   */
  public StatisticsVisualisation( final Composite parent,
                                  final int style,
                                  final IViewSite viewSite,
                                  final ITrace trace )
  {
    super( parent, style );
    this.trace = trace;
    // this
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    layout.numColumns = 4;
    this.setLayout( layout );
    this.setLayoutData( layoutData );
    // selection
    layout = new GridLayout();
    layoutData = new GridData( SWT.FILL, SWT.FILL, false, true );
    layoutData.horizontalSpan = 1;
    Group selectionGroup = new Group( this, SWT.NONE );
    selectionGroup.setText( "Statistics" );
    selectionGroup.setLayout( layout );
    selectionGroup.setLayoutData( layoutData );
    // treeviewer
    final TreeViewer treeViewer = new TreeViewer( selectionGroup );
    layout = new GridLayout();
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    treeViewer.getTree().setLayout( layout );
    treeViewer.getTree().setLayoutData( layoutData );
    treeViewer.setContentProvider( new TreeNodeContentProvider() );
    treeViewer.setLabelProvider( new StatisticsVisualisationLabelProvider() );
    populateTree( treeViewer );
    // chart
    Group chartGroup = new Group( this, SWT.NONE );
    layout = new GridLayout();
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    chartGroup.setLayout( layout );
    chartGroup.setLayoutData( layoutData );
    chartGroup.setText( "Chart" );
    layoutData.horizontalSpan = 3;
    // description
    Group description = new Group( this, SWT.NONE );
    layout = new GridLayout();
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    layoutData.heightHint = 60;
    layoutData.horizontalSpan = 4;
    description.setLayout( layout );
    description.setLayoutData( layoutData );
    description.setText( "Description" );
    final Text text = new Text( description, SWT.MULTI
                                             | SWT.V_SCROLL
                                             | SWT.WRAP );
    text.setEditable( false );
    text.setBackground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    text.setLayoutData( layoutData );
    text.setText( "Select a statistic to display." );
    // init
    try {
      PluginSettings ps = PluginSettings.instance();
      this.render = ps.getDevice( "dv.SWT" ); //$NON-NLS-1$
    } catch( ChartException exception ) {
      Activator.logException( exception );
    }
    // Scrolled Composite
    Label l = new Label( chartGroup, SWT.NONE );
    final ScrolledComposite sc = new ScrolledComposite( chartGroup,
                                                        SWT.H_SCROLL
                                                            | SWT.V_SCROLL
                                                            | SWT.BORDER );
    sc.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    layout = new GridLayout();
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    layoutData.verticalIndent = -18; // TODO cleaner solution or test if its ok
    // on other platforms
    sc.setLayout( layout );
    sc.setLayoutData( layoutData );
    // Composite
    this.composite = new Composite( sc, SWT.NONE );
    this.composite.setBackground( getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    layout = new GridLayout();
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.composite.setLayout( layout );
    this.composite.setLayoutData( layoutData );
    sc.setContent( this.composite );
    // Job job = new Job( "" ) {
    //
    // @Override
    // protected IStatus run( final IProgressMonitor monitor ) {
    // if( StatisticsVisualisation.this.p instanceof StackedChartBuilder ) {
    // StackedChartBuilder s = ( StackedChartBuilder
    // )StatisticsVisualisation.this.p;
    // for( int i = 0; i < s.calculation.length; i++ ) {
    // s.calculation[ i ] = Math.random() * 10000;
    // s.communication[ i ] = Math.random() * 10000;
    // }
    // if( !StatisticsVisualisation.this.isDisposed() ) {
    // Display.getDefault().asyncExec( new Runnable() {
    //
    // @Override
    // public void run() {
    // StatisticsVisualisation.this.buildChart();
    // StatisticsVisualisation.this.c.redraw();
    // }
    // } );
    // }
    // }
    // this.schedule( 1000 );
    // return Status.OK_STATUS;
    // }
    // };
    // job.schedule();
    sc.addListener( SWT.Resize, new Listener() {

      public void handleEvent( final Event e ) {
        if( StatisticsVisualisation.this.chart != null ) {
          int maxX = Math.max( StatisticsVisualisation.this.x,
                               sc.getClientArea().width );
          int maxY = Math.max( StatisticsVisualisation.this.y,
                               sc.getClientArea().height );
          StatisticsVisualisation.this.composite.setSize( maxX, maxY );
          buildChart();
          //
        }
      }
    } );
    this.composite.addPaintListener( new PaintListener() {

      public void paintControl( final PaintEvent e ) {
        paint( e );
      }
    } );
    this.composite.addDisposeListener( new DisposeListener() {

      public void widgetDisposed( final DisposeEvent e ) {
        disposeWidget( e );
      }
    } );
    treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent event ) {
        if( treeViewer.getSelection() instanceof IStructuredSelection ) {
          IStructuredSelection structuredSelection = ( IStructuredSelection )treeViewer.getSelection();
          if( structuredSelection.getFirstElement() instanceof TreeNode ) {
            TreeNode treeNode = ( TreeNode )structuredSelection.getFirstElement();
            if( treeNode.getValue() instanceof IStatistics ) {
              IStatistics statistics = ( IStatistics )treeNode.getValue();
              text.setText( statistics.getDescription() );
            }
          }
        }
      }
    } );
    treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent event ) {
        ISelection selection = treeViewer.getSelection();
        if( selection != null && selection instanceof IStructuredSelection ) {
          IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
          TreeNode treeNode = ( TreeNode )structuredSelection.getFirstElement();
          if( treeNode != null
              && treeNode.getValue() instanceof AbstractChartBuilder )
          {
            AbstractChartBuilder abstractChartBuilder = ( AbstractChartBuilder )treeNode.getValue();
            IStatistics statistics = ( IStatistics )treeNode.getParent()
              .getValue();
            text.setText( statistics.getDescription() );
            statistics.initialize();
            abstractChartBuilder.setTitle( statistics.getTitle() );
            abstractChartBuilder.setXSeries( statistics.getXSeries() );
            abstractChartBuilder.setYSeries( statistics.getYSeries() );
            abstractChartBuilder.setZSeries( statistics.getZSeries() );
            abstractChartBuilder.build();
            StatisticsVisualisation.this.setChart( abstractChartBuilder.getChart() );
            StatisticsVisualisation.this.x = abstractChartBuilder.minWidth();
            StatisticsVisualisation.this.y = abstractChartBuilder.minHeight();
            int maxX = Math.max( StatisticsVisualisation.this.x,
                                 sc.getClientArea().width );
            int maxY = Math.max( StatisticsVisualisation.this.y,
                                 sc.getClientArea().height );
            StatisticsVisualisation.this.composite.setSize( maxX, maxY );
            StatisticsVisualisation.this.composite.redraw();
          }
        }
      }
    } );
  }

  private void populateTree( final TreeViewer treeViewer ) {
    ArrayList<TreeNode> root = new ArrayList<TreeNode>();

    // category
/*    for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
        .getConfigurationElementsFor( "eu.geclipse.traceview.statistics.Category" ) ) { //$NON-NLS-1$
      String name = configurationElement.getAttribute( "name" );
      String id = configurationElement.getAttribute( "id" );

    }*/

    for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
      .getConfigurationElementsFor( "eu.geclipse.traceview.statistics.Statistics" ) ) { //$NON-NLS-1$
      IStatistics statistics;
      try {
        statistics = ( IStatistics )configurationElement.createExecutableExtension( "class" ); //$NON-NLS-1$
        statistics.setTrace( this.trace );
        TreeNode treeNode = new TreeNode( statistics );
        ArrayList<TreeNode> leaf = new ArrayList<TreeNode>();
        for( IConfigurationElement configurationElement2 : Platform.getExtensionRegistry()
          .getConfigurationElementsFor( "eu.geclipse.traceview.statistics.Visualizations" ) ) { //$NON-NLS-1$
          if( ( statistics.xAxis() == configurationElement2.getAttribute( "x" ) || //$NON-NLS-1$
          statistics.xAxis().equals( configurationElement2.getAttribute( "x" ) ) )//$NON-NLS-1$
              && ( statistics.yAxis() == configurationElement2.getAttribute( "y" ) || //$NON-NLS-1$
              statistics.yAxis()
                .equals( configurationElement2.getAttribute( "y" ) ) ) //$NON-NLS-1$
              && ( statistics.zAxis() == configurationElement2.getAttribute( "z" ) || statistics.zAxis()
                .equals( configurationElement2.getAttribute( "z" ) ) ) ) //$NON-NLS-1$
          {
            AbstractChartBuilder abstractChartBuilder = ( AbstractChartBuilder )configurationElement2.createExecutableExtension( "class" ); //$NON-NLS-1$
            TreeNode child = new TreeNode( abstractChartBuilder );
            child.setParent( treeNode );
            leaf.add( child );
          }
        }
        treeNode.setChildren( leaf.toArray( new TreeNode[ 0 ] ) );
        root.add( treeNode );
      } catch( CoreException e ) {
        // TODO log
      }
    }
    treeViewer.setInput( root.toArray( new TreeNode[ 0 ] ) );
    treeViewer.expandAll();
  }

  protected void paint( final PaintEvent e ) {
    Composite co = ( Composite )e.getSource();
    final Rectangle rect = co.getClientArea();
    if( this.cachedImage == null && this.chart != null ) {
      buildChart();
      drawToCachedImage( rect );
    }
    if( this.cachedImage != null )
      e.gc.drawImage( this.cachedImage,
                      0,
                      0,
                      this.cachedImage.getBounds().width,
                      this.cachedImage.getBounds().height,
                      0,
                      0,
                      rect.width,
                      rect.height );
  }

  protected void drawToCachedImage( final Rectangle size ) {
    GC gc = null;
    try {
      if( this.cachedImage != null )
        this.cachedImage.dispose();
      this.cachedImage = new Image( Display.getCurrent(),
                                    size.width,
                                    size.height );
      gc = new GC( this.cachedImage );
      this.render.setProperty( IDeviceRenderer.GRAPHICS_CONTEXT, gc );
      Generator gr = Generator.instance();
      gr.render( this.render, this.state );
    } catch( ChartException exception ) {
      Activator.logException( exception );
    } finally {
      if( gc != null )
        gc.dispose();
    }
  }

  protected void disposeWidget( final DisposeEvent e ) {
    if( this.cachedImage != null )
      this.cachedImage.dispose();
  }

  protected void buildChart() {
    Point size = this.composite.getSize();
    Bounds bo = BoundsImpl.create( 0, 0, size.x, size.y );
    int resolution = this.render.getDisplayServer().getDpiResolution();
    bo.scale( 72d / resolution );
    try {
      Generator gr = Generator.instance();
      this.state = gr.build( this.render.getDisplayServer(),
                             this.chart,
                             bo,
                             null,
                             null,
                             null );
    } catch( ChartException exception ) {
      Activator.logException( exception );
    }
    this.cachedImage = null;
  }

  @Override
  public IContributionItem[] getToolBarItems() {
    // TODO Auto-generated method stub
    return new IContributionItem[ 0 ];
  }

  @Override
  public ITrace getTrace() {
    return this.trace;
  }

  protected void setChart( final Chart chart ) {
    if( this.cachedImage != null )
      this.cachedImage.dispose();
    this.cachedImage = null;
    this.chart = chart;
  }
}
