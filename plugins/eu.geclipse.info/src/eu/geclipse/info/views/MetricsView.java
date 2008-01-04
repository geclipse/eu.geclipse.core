/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.info.views;

import java.util.ArrayList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 *
 */
public class MetricsView extends ViewPart {
 
  private TableViewer viewer;
  private Action viewResult;
  private Action addToChart;
  private Action doubleClickAction;

  private ArrayList<TableColumn> metricColumns;
  //private Hashtable<String, Integer> metricColumnAssociation=new Hashtable<String, Integer>();
  
  class ViewContentProvider implements IStructuredContentProvider {
    
    //private ArrayList<Benchmark> benchmarks;

    /**
     * @return Returns a list of benchmarks 
     */
    //public ArrayList<Benchmark> getBenchmarkList(){
    public ArrayList<?> getBenchmarkList(){
      /*
      if ( this.benchmarks == null ){
        this.benchmarks = new ArrayList<Benchmark>();
      }
      return this.benchmarks;
      */
      return new ArrayList();
    }
    
    public void inputChanged( final Viewer v, final Object oldInput, final Object newInput ) {
      // No code yet
    }

    public void dispose() {
      // No code yet
    }

    public Object[] getElements( final Object parent ) {
//      ArrayList<GlueCE> ces = GlueQuery.getComputingElements( null );
//      return ( GlueCE[] )ces.toArray( new GlueCE[ ces.size() ] );
       return getBenchmarkList().toArray();
    }
  }
  
  class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

    public String getColumnText( final Object obj, final int index ) {
      String out = "n/a";
      /*
      Benchmark b=(Benchmark) obj;
      switch( index ) {
        case 0:
          out=b.getLocation().getResource( 0 ).toString();
          break;
        case 1:
          out=b.getName();
          break;
        case 2:
          out=b.getDate().toString();
          break;
        default:
          int metricIndex=index-3;
          if ( metricIndex<metricColumns.size() ) {
            out=b.getMetric( metricColumns.get( metricIndex ).getText() ).getValue();
          }
        break;
      }
      */
      return getText( out );
    }

    public Image getColumnImage( final Object obj, final int index ) {
      return getImage( obj );
    }

    @Override
    public Image getImage( final Object obj ) {
      return null;
    }
    
  }
  
  class NameSorter extends ViewerSorter {
  }

  /**
   * The constructor.
   */
  public MetricsView() {
    // No Code needed
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   * @param parent The parent composit. 
   */
  @Override
  public void createPartControl( final Composite parent ) {
    this.viewer = new TableViewer( parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
    this.viewer.setContentProvider( new ViewContentProvider() );
    this.viewer.setLabelProvider( new ViewLabelProvider() );
    this.viewer.setSorter( new NameSorter() );
    this.viewer.setInput( getViewSite() );
    Table table = this.viewer.getTable();
    table.setHeaderVisible( true );
    
    TableColumn ceColumn = new TableColumn( table, SWT.LEFT );
    ceColumn.setText( "CE" ); //$NON-NLS-1$
    ceColumn.setWidth( 100 );
    
    TableColumn bmkColumn = new TableColumn( table, SWT.LEFT );
    bmkColumn.setText( "Benchmark" ); //$NON-NLS-1$
    bmkColumn.setWidth( 100 );
    
    TableColumn dateColumn = new TableColumn( table, SWT.LEFT );
    dateColumn.setText( "Date" ); //$NON-NLS-1$
    dateColumn.setWidth( 100 );
    
    this.metricColumns = new ArrayList<TableColumn>();
    
    makeActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();
  }

  private synchronized void addMetricColumn( final String name ){
    TableColumn newColumn=new TableColumn( this.viewer.getTable(), SWT.LEFT );
    newColumn.setText( name );
    newColumn.setWidth( 100 );

    //metricColumnAssociation.put( name, metricColumns.size() );
    this.metricColumns.add( this.metricColumns.size(), newColumn );
  }
  
  private void hookContextMenu() {
    MenuManager menuMgr = new MenuManager( "#PopupMenu" );
    menuMgr.setRemoveAllWhenShown( true );
    menuMgr.addMenuListener( new IMenuListener() {

      public void menuAboutToShow( final IMenuManager manager ) {
        MetricsView.this.fillContextMenu( manager );
      }
    } );

    Menu menu = menuMgr.createContextMenu( this.viewer.getControl() );
    this.viewer.getControl().setMenu( menu );
    getSite().registerContextMenu( menuMgr, this.viewer );
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalPullDown( bars.getMenuManager() );
    fillLocalToolBar( bars.getToolBarManager() );
  }

  private void fillLocalPullDown( final IMenuManager manager ) {
    manager.add( this.viewResult );
    manager.add( new Separator() );
    manager.add( this.addToChart );
  }

  private void fillContextMenu( final IMenuManager manager ) {
    manager.add( this.viewResult );
    manager.add( this.addToChart );
    // Other plug-ins can contribute there actions here
    manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
  }

  private void fillLocalToolBar( final IToolBarManager manager ) {
    manager.add( this.viewResult );
    manager.add( this.addToChart );
  }

  private void makeActions() {
    this.viewResult = new Action() {

      @Override
      public void run() {
        //TODO Implement viewResult
      }
    };
    this.viewResult.setText( "View Result" );
    this.viewResult.setToolTipText( "Result details" );
    this.viewResult.setImageDescriptor( PlatformUI.getWorkbench()
      .getSharedImages()
      .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
    this.addToChart = new Action() {

      @Override
      public void run() {
        //TODO Implement addToChart
      }
    };
    this.addToChart.setText( "Add To Chart..." );
    this.addToChart.setToolTipText( "Add to a new or existing chart." );
    this.addToChart.setImageDescriptor( PlatformUI.getWorkbench()
      .getSharedImages()
      .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
    doubleClickAction = new Action() {

      @Override
      public void run() {
        ISelection selection = viewer.getSelection();
        Object obj = ( ( IStructuredSelection )selection ).getFirstElement();
        showMessage( "Result " + obj.toString()+" double-clicked" );
      }
    };
  }

  private void hookDoubleClickAction() {
    this.viewer.addDoubleClickListener( new IDoubleClickListener() {

      public void doubleClick( final DoubleClickEvent event ) {
        doubleClickAction.run();
      }
    } );
  }

  private void showMessage( final String message ) {
    MessageDialog.openInformation( this.viewer.getControl().getShell(),
                                   "Metrics View",
                                   message );
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    this.viewer.getControl().setFocus();
  }
  
  /**
   * @param benchmark
   */
  /*
  public void addBenchmark( final Benchmark benchmark ){
    ArrayList<Benchmark> bmks=((ViewContentProvider) this.viewer.getContentProvider()).getBenchmarkList();
    bmks.add( benchmark );
    for(int i=0;i<benchmark.getMetricCount();i++){
      Metric m=benchmark.getMetric( i );
      boolean found = false;
      for ( int j = 0; j < this.metricColumns.size() && !found; ++j ) {
        found = this.metricColumns.get( j ).getText().equals( m.getName() );
      }
      if( !found ){
        addMetricColumn( m.getName() );
      }
    }
    this.viewer.add( benchmark );
  }
  */
}