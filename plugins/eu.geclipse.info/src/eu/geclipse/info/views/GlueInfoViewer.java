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

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.info.Activator;
import eu.geclipse.info.InfoServiceFactory;
import eu.geclipse.info.IGlueStoreChangeListerner;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueCE;
import eu.geclipse.info.glue.GlueCEAccessControlBaseRule;
import eu.geclipse.info.glue.GlueIndex;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.info.model.IExtentedGridInfoService;

/**
 * @author George Tsouloupas
 */
public class GlueInfoViewer extends ViewPart
implements ISelectionProvider, IGlueStoreChangeListerner, IGridModelListener {

  private String currentVO = null;
  private DrillDownAdapter drillDownAdapter;
  private Action actionSetSourceRGMA;
  private Action actionSetSourceBDII;

  Action doubleClickAction;
  Job fetchJob;
  TreeViewer viewer;
  
  private boolean SHOW_VO_LIST=false;
  private Combo comboVOList;
  private Label label = null;

  class TreeObject implements IAdaptable {

    private String prefix;
    private String name;
    private TreeParent parent;

    /**
     * @param name
     * @param prefix
     */
    public TreeObject( final String name, final String prefix ) {
      this.name = name;
      this.prefix = prefix;
    }

    /**
     * @return The name.
     */
    public String getName() {
      return this.name;
    }

    /**
     * @param parent
     */
    public void setParent( final TreeParent parent ) {
      this.parent = parent;
    }

    /**
     * @return The parent.
     */
    public TreeParent getParent() {
      return this.parent;
    }

    @Override
    public String toString() {
      return ( ( this.prefix != null ) ? this.prefix + ": ":"" ) + getName(); //$NON-NLS-1$  //$NON-NLS-2$
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter( final Class key ) {
      return null;
    }
  }
  class TreeParent extends TreeObject {

    AbstractGlueTable agt;
    
    private ArrayList<AbstractGlueTable> agtList;
    private ArrayList<String[]> queries;
    private String[] query;
    //String prefix; 
    

    /**
     * @param agt
     * @param prefix
     */
    public TreeParent( final AbstractGlueTable agt,final String prefix ) {
      super( agt.getID(),prefix );
      this.agt = agt;
    }

    /**
     * @param name
     * @param query
     * @param agtList
     */
    public TreeParent( final String name,final String[] query,
                       final ArrayList<AbstractGlueTable> agtList )
    {
      super( name,null );
      this.agtList = agtList;
      this.query=query;
    }

    /**
     * @param name
     * @param agtList
     */
    public TreeParent( final String name,
                       final ArrayList<AbstractGlueTable> agtList )
    {
      super( name,null  );
      this.agtList = agtList;
    }

    /**
     * @param queries
     */
    public TreeParent( final ArrayList<String[]> queries) {
      super( "",null  ); //$NON-NLS-1$
      this.queries = queries;
    }
    
    /**
     * @return Returns an array of the children
     */
    @SuppressWarnings("unchecked")
    public TreeObject[] getChildren() {
      ArrayList<TreeObject> toList = new ArrayList<TreeObject>();
      if ( this.queries != null ) {
        for ( String[] s : this.queries ) {
          toList.add( new TreeParent( s[ 1 ],s, null ) );
        }
      } 
      if ( this.query != null ) {
        this.agtList = GlueQuery.getGlueTable( this.query[ 0 ], this.query[ 2 ], getCurrentVO() );
      } 
      if ( this.agt != null ) {
        String fieldName;
        Field[] fields = this.agt.getClass().getFields();
        for( int i = 0; i < fields.length; i++ ) {
          Field field = fields[ i ];
          Object value;
          try {
            fieldName=field.getName();
            value = field.get( this.agt );
            if( fieldName.endsWith( "List" ) ) { //$NON-NLS-1$
              ArrayList<AbstractGlueTable> list = ( ArrayList<AbstractGlueTable> ) value;
              TreeObject to = new TreeParent( fieldName, list );
              toList.add( to );
            } else if ( value instanceof AbstractGlueTable ) { 
              AbstractGlueTable agt1 = ( AbstractGlueTable ) value;
              toList.add( new TreeParent(agt1,agt1.tableName) );
            } else if ( !( ( fieldName.equals( "byRefOnly" ) || fieldName.equals( "key" )  //$NON-NLS-1$  //$NON-NLS-2$
                || fieldName.equals( "keyName" ) || fieldName.equals( "tableName" ) //$NON-NLS-1$  //$NON-NLS-2$
                || fieldName.equals( "glueIndex" ) || fieldName.equals( "glueService" ))) ){//$NON-NLS-1$//$NON-NLS-2$
                String s = fieldName + " = " //$NON-NLS-1$
                + ( ( value == null || value == "" ) //$NON-NLS-1$
                    ? "Not available from the information service" : value.toString() ); //$NON-NLS-1$
                TreeObject to = new TreeObject( s,null );
                toList.add( to );
              
            }
          } catch( IllegalArgumentException e ) {
            Activator.logException( e );
          } catch( IllegalAccessException e ) {
            Activator.logException( e );
          }
        }
      }
      if ( this.agtList != null ) {
        for ( AbstractGlueTable table : this.agtList ) {
          if ( table.getID() != null ) {
            if ( !table.byRefOnly ){
              toList.add( new TreeParent( table, null ) );
            }
          }
        }
      }
//      if(toList.size()<1){
//        TreeObject[] nodat=new TreeObject[1];
//        nodat[0]=new TreeObject("no data");
//        return nodat;
//      }
      //System.out.println("getChilren "+this.getName()+" "+toList.size());

      TreeObject[] treeObjectArray=toList.toArray( new TreeObject[ toList.size() ] );
      return treeObjectArray;
    }

    public boolean hasChildren() {
      boolean hc = true;
      //hc=getChildren().length>0;
      return hc;
      // return children.size() > 0;
    }

  }
  
  class ViewContentProvider
  implements IStructuredContentProvider, ITreeContentProvider
  {

    private TreeParent glueRoot;

    public void inputChanged( final Viewer v,
                              final Object oldInput,
                              final Object newInput )
    {
      // empty implementation
    }

    public void dispose() {
      // empty implementation
    }

    public Object[] getElements( final Object parent ) {
      Object[] result;
      if( parent.equals( getViewSite() ) ) {
        if( this.glueRoot == null ) {
          initialize();
        }
        result = getChildren( this.glueRoot );
      } else {
        result = getChildren( parent );
      }
      return result;
    }

    public Object getParent( final Object child ) {
      Object result = null;
      if( child instanceof TreeObject ) {
        result = ( ( TreeObject )child ).getParent();
      }
      return result;
    }

    public Object[] getChildren( final Object parent ) {
      Object[] result;
      if( parent instanceof TreeParent ) {
        result = ( ( TreeParent )parent ).getChildren();
      } else {
        result = new Object[ 0 ];
      }
      return result;
    }

    public boolean hasChildren( final Object parent ) {
      boolean result = false;
      if( parent instanceof TreeParent ) {
        result = ( ( TreeParent )parent ).hasChildren();
      }
      return result;
    }

    private void initialize() {
      buildTopLevel();
    }

    private void buildTopLevel() {
      ArrayList<String[]> list = new ArrayList<String[]>();
      String[] queryArray;
      queryArray = new String[ 3 ];
      queryArray[ 0 ] = "GlueSite"; //$NON-NLS-1$
      queryArray[ 1 ] = "Sites"; //$NON-NLS-1$
      queryArray[ 2 ] = "GlueSite"; //$NON-NLS-1$
      list.add( queryArray );
      queryArray = new String[ 3 ];
      queryArray[ 0 ] = "GlueCE"; //$NON-NLS-1$
      queryArray[ 1 ] = "Computing Elements"; //$NON-NLS-1$
      queryArray[ 2 ] = "GlueCE"; //$NON-NLS-1$
      list.add( queryArray );
      queryArray = new String[ 3 ];
      queryArray[ 0 ] = "GlueSE"; //$NON-NLS-1$
      queryArray[ 1 ] = "Storage Elements"; //$NON-NLS-1$
      queryArray[ 2 ] = "GlueSE"; //$NON-NLS-1$
      list.add( queryArray );
      queryArray = new String[ 3 ];
      queryArray[ 0 ] = "GlueService"; //$NON-NLS-1$
      queryArray[ 1 ] = "Gria Services"; //$NON-NLS-1$
      queryArray[ 2] = "GriaService"; //$NON-NLS-1$
      list.add( queryArray );
      this.glueRoot = new TreeParent( list );
    }
  }
  class ViewLabelProvider extends LabelProvider {

    @Override
    public String getText( final Object obj ) {
      String result;
      if( obj instanceof AbstractGlueTable ) {
        result = ( ( AbstractGlueTable )obj ).getID();
      } else {
        result = obj.toString();
      }
      return result;
    }

    @Override
    public Image getImage( final Object obj ) {
      String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
      if( obj instanceof TreeParent ) {
        imageKey = ISharedImages.IMG_OBJ_FOLDER;
      }
      return PlatformUI.getWorkbench().getSharedImages().getImage( imageKey );
    }
  }
  class NameSorter extends ViewerSorter {
    @Override
    public int compare(final Viewer myViewer, final Object p1, final Object p2)
    {
      
      int result = 0;
      
      if ((p1 instanceof TreeParent && !(p2 instanceof TreeParent)) 
          || (p2 instanceof TreeParent && !(p1 instanceof TreeParent)))
      {
        if ((p1 instanceof TreeParent))
          result = -1;
        else if ((p2 instanceof TreeParent)) 
          result = 1;
        else
          result = super.compare( myViewer, p1, p2 );
      }
      else 
        result = super.compare( myViewer, p1, p2 );
      
      return result;
    }
    
  }

  /**
   * The constructor.
   */
  public GlueInfoViewer() {
    
    Thread t=new Thread(){
      //wait for BDII and register listener
      //TODO this should not require another thread, is there another way?
      
      @Override
      
      public void run() {
        
        ArrayList<IExtentedGridInfoService> infoServicesArray = InfoServiceFactory.getAllExistingInfoService();
        for (int i=0; i<infoServicesArray.size(); i++)
        {
          IExtentedGridInfoService infoService = infoServicesArray.get( i );
          while (infoService != null && infoService.getStore()==null)
          {
            try {
              sleep(1000);
            } catch( InterruptedException e ) {
              //ignore interrupted exception
            }
          }
          
          if (infoService != null && infoService.getStore()!=null)
            infoService.getStore().addListener( GlueInfoViewer.this, null );
           
        }
      }
      
    };
    t.start();
    
    this.fetchJob = new Job( " Retrieving Information" ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        GlueIndex.drop(); // Clear the glue index.
        Status status = new Status( IStatus.ERROR,
                                    "eu.geclipse.glite.info", //$NON-NLS-1$
                                    "BDII fetch from " //$NON-NLS-1$
                                        + " Failed" ); //$NON-NLS-1$
        ArrayList<IExtentedGridInfoService> infoServicesArray = null;
        infoServicesArray = InfoServiceFactory.getAllExistingInfoService();
        
        // Get the number of projects. The number is used in the monitor.
        int gridProjectNumbers = 0;
        IGridElement[] projectElements;
        try {
          projectElements = GridModel.getRoot().getChildren( null );
          if (projectElements!= null) {
            gridProjectNumbers = projectElements.length;
          }
        } catch( GridModelException e ) {
          // Do nothing
        }
        
        monitor.beginTask( "Retrieving information", gridProjectNumbers * 10 ); //$NON-NLS-1$
        
        // Get the information from the info systems to file the glue view.
        for (int i=0; infoServicesArray!= null && i<infoServicesArray.size(); i++)
        {
          IExtentedGridInfoService infoService = infoServicesArray.get( i );
          if (infoService != null)
          {
            infoService.scheduleFetch(monitor);
          }
        }
        
        // Notify the listeners that the info has changed.
        for (int i=0; infoServicesArray != null && i<infoServicesArray.size(); i++)
        {
          IExtentedGridInfoService infoService = infoServicesArray.get( i );
          if (infoService != null && infoService.getStore() != null)
          {
            infoService.getStore().notifyListeners( null );
          }
        }
        
        monitor.done();
        status = new Status( IStatus.OK,
                             "eu.geclipse.glite.info", //$NON-NLS-1$
                             "BDII data fetched successfully." ); //$NON-NLS-1$
        return status;
      }
    };
  }
  
  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl( final Composite parent )
  {
    GridLayout parentLayout = new GridLayout( 3, false );
    parentLayout.marginTop = 0;
    parentLayout.marginBottom = parentLayout.marginTop;
    parentLayout.marginLeft = parentLayout.marginTop;
    parentLayout.marginRight = parentLayout.marginTop;
    parentLayout.marginHeight = 0;
    parentLayout.marginWidth = 0;
    // parentLayout.horizontalSpacing = 0;
    // parentLayout.verticalSpacing = 0;
    parent.setLayout( parentLayout );
    GridData gData;

    if(this.SHOW_VO_LIST){
      this.label = new Label( parent, SWT.NONE );
      this.label.setText( "VO:" );  //$NON-NLS-1$
      gData = new GridData();
      this.label.setLayoutData( gData );

      this.comboVOList = new Combo( parent, SWT.NONE );
      gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.widthHint = 150;
      gData.verticalAlignment = GridData.CENTER;
      this.comboVOList.setLayoutData( gData );
    }


    this.viewer = new TreeViewer( parent, SWT.MULTI
                                  | SWT.H_SCROLL
                                  | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.horizontalSpan = 3;
    this.viewer.getTree().setLayoutData( gData );
    this.drillDownAdapter = new DrillDownAdapter( this.viewer );
    this.viewer.setContentProvider( new ViewContentProvider() );
    this.viewer.setLabelProvider( new ViewLabelProvider() );
    this.viewer.setSorter( new NameSorter() );
    this.viewer.setInput( getViewSite() );
    this.viewer.setComparer( new GlueInfoComparator() );
 
    if(this.SHOW_VO_LIST){
    // Label filler = new Label(parent, SWT.NONE);

      this.comboVOList.addSelectionListener( new SelectionAdapter() {
        @Override
        public void widgetDefaultSelected( final SelectionEvent e )
        {
          updateVOList();
        }

        @SuppressWarnings("synthetic-access")
        @Override
        public void widgetSelected( final SelectionEvent arg0 )
        {
          setCurrentVO( GlueInfoViewer.this.comboVOList.getText() );
          GlueInfoViewer.this.viewer.refresh();
        }
      } );
    }
    addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent arg0 ) {
        IStructuredSelection selection = ( IStructuredSelection ) arg0.getSelection();
      }
    } );
    makeActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();
    
    GridModel.getRoot().addGridModelListener( this );
    setCurrentVO( "none" );  //$NON-NLS-1$
  }

  void updateVOList() {
    ArrayList<AbstractGlueTable> agtList = GlueQuery.getGlueTable( "GlueCEAccessControlBaseRule", "GlueCEAccessControlBaseRule", null ); //$NON-NLS-1$
    ArrayList<String> vos = new ArrayList<String>();
    int i = 0;
    for( AbstractGlueTable table : agtList ) {
      GlueCEAccessControlBaseRule rule = ( GlueCEAccessControlBaseRule )table;
      String[] s = rule.Value.split( ":" );  //$NON-NLS-1$
      if( s[ 0 ].equals( "VO" ) ) {  //$NON-NLS-1$
        if( s[ 1 ] != null ) {
          vos.add( s[ 1 ] );
        }
      }
    }
    String[] voArray = new String[ vos.size() ];
    i = 0;
    for( String voString : vos ) {
      voArray[ i++ ] = voString;
    }
    Arrays.sort( voArray );

    this.comboVOList.setItems( voArray );
  }

  @SuppressWarnings("unused")
  private void hookContextMenu() {
    MenuManager menuMgr = new MenuManager( "Information Source" );  //$NON-NLS-1$
    menuMgr.setRemoveAllWhenShown( true );
    menuMgr.addMenuListener( new IMenuListener() {

      public void menuAboutToShow( final IMenuManager manager ) {
        GlueInfoViewer.this.fillContextMenu( manager );
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
    // manager.add( actionSetSourceRGMA );
    // manager.add( new Separator() );
    manager.add( this.actionSetSourceBDII );
  }

  void fillContextMenu( final IMenuManager manager ) {
    ISelection selection = this.viewer.getSelection();
    Object obj = ( ( IStructuredSelection )selection ).getFirstElement();
    if ( obj instanceof TreeParent ){
      TreeParent tp = ( TreeParent ) obj;
      if ( tp.agt instanceof GlueCE ) {
        //manager.add( this.benchmarkAction );
        manager.add( new Separator() );
        
      }
    }

    this.drillDownAdapter.addNavigationActions( manager );
    // Other plug-ins can contribute there actions here
    manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
  }

  private void fillLocalToolBar( final IToolBarManager manager ) {
    // manager.add( actionSetSourceRGMA );
    // manager.add( actionSetSourceBDII );
    // manager.add( new Separator() );
    
    IAction refreshAction = new Action() {
      @Override
      public void run() {
        
        GlueInfoViewer.this.fetchJob.schedule();
      }
    };
    refreshAction.setToolTipText( "Refresh" );  //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/elcl16/refresh_nav.gif" ); //$NON-NLS-1$
    refreshAction.setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    manager.add( refreshAction );
    this.drillDownAdapter.addNavigationActions( manager );
  }

  private void makeActions() {
    this.actionSetSourceRGMA = new Action() {

      @Override
      public void run() {
        // if( glueStore != null ) {
        // glueStore.removeListener( ( IGlueStoreChangeListerner
        // )viewer.getContentProvider(),
        // "GlueSite" ); //$NON-NLS-1$
        // }
        // glueStore = new RGMAStore();
        // glueStore.addListener( ( IGlueStoreChangeListerner
        // )viewer.getContentProvider(),
        // "GlueSite" ); //$NON-NLS-1$
        // ArrayList<GlueSite> siteList=new ArrayList<GlueSite>();
        //        
        // ArrayList<AbstractGlueTable> agtList=GlueQuery.getGlueTable(
        // "GlueSite", null );
        // for( AbstractGlueTable table : agtList ) {
        // siteList.add( (GlueSite) table );
        // }
        // getGrid3DView().setSiteList( siteList );
      }
    };
    this.actionSetSourceRGMA.setText( "RGMA" ); //$NON-NLS-1$
    this.actionSetSourceRGMA.setToolTipText( "Set Datasource to RGMA" ); //$NON-NLS-1$
    this.actionSetSourceRGMA.setImageDescriptor( PlatformUI.getWorkbench() 
                                            .getSharedImages()
                                            .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
    this.actionSetSourceBDII = new Action() {
      @Override
      public void run() {
        //
      }
    };
    
    this.actionSetSourceBDII.setText( "BDII" ); //$NON-NLS-1$
    this.actionSetSourceBDII.setToolTipText( "Set Datasource to BDII" ); //$NON-NLS-1$
    this.actionSetSourceBDII.setImageDescriptor( PlatformUI.getWorkbench()
                                            .getSharedImages()
                                            .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
     

    this.doubleClickAction = new Action() {
      @Override
      public void run() {
        ISelection selection = GlueInfoViewer.this.viewer.getSelection();
        Object obj = ( ( IStructuredSelection )selection ).getFirstElement();
      }
     
    };
    
  }

  private void hookDoubleClickAction() {
    this.viewer.addDoubleClickListener( new IDoubleClickListener() {

      public void doubleClick( final DoubleClickEvent event ) {
        GlueInfoViewer.this.doubleClickAction.run();
      }
    } );
  }

  /*
  MetricsView getMetricsView() {
    if ( this.metricsView == null ) {
      IWorkbenchPage activePage = PlatformUI.getWorkbench()
      .getActiveWorkbenchWindow()
      .getActivePage();
      this.metricsView = ( MetricsView )activePage.findView( "eu.geclipse.gridbench.views.MetricsView" ); //$NON-NLS-1$
    }
    return this.metricsView;
  }
  */

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    this.viewer.getControl().setFocus();
  }

  /**
   * @return Returns the current VO
   */
  public String getCurrentVO() {
    return this.currentVO;
  }

  /**
   * @param currentVO
   */
  public void setCurrentVO( final String currentVO ) {
    this.currentVO = currentVO;
  }

  public void addSelectionChangedListener( final ISelectionChangedListener arg0 ) {
    this.viewer.addSelectionChangedListener( arg0 );
  }

  public ISelection getSelection() {
    this.viewer.getSelection();
    return null;
  }

  public void removeSelectionChangedListener( final ISelectionChangedListener arg0 )
  {
    this.viewer.removeSelectionChangedListener( arg0 );
  }

  public void setSelection( final ISelection arg0 ) {
    this.viewer.setSelection( arg0 );
  }

  public void infoChanged( final ArrayList<AbstractGlueTable> modifiedGlueEntries ) {
    getSite().getShell().getDisplay().syncExec( new Runnable( ){
      @SuppressWarnings("synthetic-access")
      public void run() {
        if ( GlueInfoViewer.this.SHOW_VO_LIST ){
          updateVOList();
        }
        
        // Get the expanded nodes
        Object[] currentExpanded = GlueInfoViewer.this.viewer.getExpandedElements();
        // Refresh the tree with the new model
        GlueInfoViewer.this.viewer.refresh();
        // Remember the expanded status of the tree.
        GlueInfoViewer.this.viewer.setExpandedElements(currentExpanded);
      }
    } );
  }
  
  @SuppressWarnings("unused")
  private void showMessage( final String message ) {
    MessageDialog.openInformation( this.viewer.getControl().getShell(),
                                   "Grid Information View",  //$NON-NLS-1$
                                   message );
  }

  public void gridModelChanged( final IGridModelEvent event ) {
    int type=event.getType();    
    switch( type ) {
      case IGridModelEvent.ELEMENTS_ADDED:
      case IGridModelEvent.ELEMENTS_REMOVED:
      case IGridModelEvent.PROJECT_CLOSED:
      case IGridModelEvent.PROJECT_OPENED:

        for( IGridElement gridElement : event.getElements() ) {
          if(gridElement instanceof IGridProject){
            this.fetchJob.schedule(); // Getting the information from the info services.
            break;
          }
        }
        break;
      default:
        break;
    }
  }
}