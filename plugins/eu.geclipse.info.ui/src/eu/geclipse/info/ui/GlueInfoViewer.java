/******************************************************************************
 * Copyright (c) 2007,2008 g-Eclipse consortium
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
 *      - Nikolaos Tsioutsias
 *****************************************************************************/
package eu.geclipse.info.ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.info.InfoServiceFactory;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueCE;
import eu.geclipse.info.glue.GlueCEAccessControlBaseRule;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.info.ui.internal.Activator;
import eu.geclipse.info.ui.internal.Messages;
import eu.geclipse.info.model.FetchJob;
import eu.geclipse.info.model.InfoTopTreeCategory;
import eu.geclipse.info.model.InfoTopTreeElement;
import eu.geclipse.info.model.IExtentedGridInfoService;
import eu.geclipse.info.model.IGlueStoreChangeListerner;
/**
 * @author George Tsouloupas
 */
public class GlueInfoViewer extends ViewPart
implements ISelectionProvider, IGlueStoreChangeListerner {

  Action doubleClickAction;
  FetchJob fetchJob;
  TreeViewer viewer;
  boolean showOnlyFilledInfoElements = false;
  
  private String currentVO = null;
  private DrillDownAdapter drillDownAdapter;
  
  private boolean SHOW_VO_LIST=false;
  private Combo comboVOList;
  private Label label = null;
  
  

  /**
   * @author George Tsouloupas
   *
   */
  public class TreeObject implements IAdaptable {

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
    @SuppressWarnings("unchecked")
    public Object getAdapter( final Class key ) {
      return null;
    }
  }
  /**
   * @author George Tsouloupas
   *
   */
  public class TreeParent extends TreeObject {

    private AbstractGlueTable agt;
    
    private ArrayList<AbstractGlueTable> agtList;
    private ArrayList<InfoTopTreeElement> queries;
    private InfoTopTreeElement query;
    

    /**
     * @param agt
     * @param prefix
     */
    public TreeParent( final AbstractGlueTable agt,final String prefix ) {
      super( agt.getDisplayName(),prefix );
      this.setAgt( agt );
    }

    /**
     * @param name
     * @param query
     * @param agtList
     */
    public TreeParent( final String name, final InfoTopTreeElement query,
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
    public TreeParent( final ArrayList<InfoTopTreeElement> queries) {
      super( "",null  ); //$NON-NLS-1$
      this.queries = queries;
    }
    
    /**
     * Gets the query
     * @return a GlueInfoTopTreeElement object or null
     */
    public InfoTopTreeElement getQuery()
    {
      return this.query;
    }
    
    /**
     * @return Returns an array of the children
     */
    @SuppressWarnings("unchecked")
    public TreeObject[] getChildren() {
      ArrayList<TreeObject> toList = new ArrayList<TreeObject>();
      
      // Create the tree parents that will be the top level tree elements
      if ( this.queries != null ) {
        for ( InfoTopTreeElement s : this.queries ) {
          toList.add( new TreeParent( s.getDisplayName(), s, null ) );
        }
      } 
      if ( this.query != null ) {
        this.agtList = new ArrayList<AbstractGlueTable>();
        for (int i=0; i<this.query.getGlueInfoTopTreeCategory().size(); i++)
        {
          String myObjectTableName = this.query.getGlueInfoTopTreeCategory().get( i ).getObjectTableName();
          String myGlueObjectName = this.query.getGlueInfoTopTreeCategory().get( i ).getGlueObjectName();

          this.agtList.addAll( GlueQuery.getGlueTable( myGlueObjectName, 
                                                       myObjectTableName, 
                                                       getCurrentVO() ) );
        }
      } 
      if ( this.getAgt() != null ) {
        String fieldName;
        Field[] fields = this.getAgt().getClass().getFields();
        for( int i = 0; i < fields.length; i++ ) {
          Field field = fields[ i ];
          Object value;
          try {
            fieldName=field.getName();
            value = field.get( this.getAgt() );
            if( fieldName.endsWith( "List" ) ) { //$NON-NLS-1$
              ArrayList<AbstractGlueTable> list  = ( ArrayList<AbstractGlueTable> ) value;
              TreeObject to = new TreeParent( fieldName, list );
              toList.add( to );
            } else if ( value instanceof AbstractGlueTable ) { 
              AbstractGlueTable agt1 = ( AbstractGlueTable ) value;
              toList.add( new TreeParent(agt1,agt1.tableName) );
            } else if ( !( ( fieldName.equals( "byRefOnly" ) //$NON-NLS-1$ 
                || fieldName.equals( "key" )  //$NON-NLS-1$
                || fieldName.equals( "keyName" ) || fieldName.equals( "tableName" ) //$NON-NLS-1$  //$NON-NLS-2$
                || fieldName.equals( "glueIndex" ) || fieldName.equals( "glueService" ) //$NON-NLS-1$ //$NON-NLS-2$
                || fieldName.equals( "displayName" ))) ){//$NON-NLS-1$
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

      TreeObject[] treeObjectArray=toList.toArray( new TreeObject[ toList.size() ] );
      return treeObjectArray;
    }

    /**
     * Always returns true
     * @return always true
     */
    public boolean hasChildren() {
      boolean  hc = true;
      return hc;
    }

    /**
     * @param agt an AbstractGlueTable
     */
    public void setAgt( final AbstractGlueTable agt ) {
      this.agt = agt;
    }

    /**
     * @return an AbstractGlueTable or null
     */
    public AbstractGlueTable getAgt() {
      return this.agt;
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
    

    private void addObjectTablename(final HashSet<InfoTopTreeElement> uniqueList,
                                    final InfoTopTreeElement otherElement)
    {
      if (uniqueList != null && otherElement != null)
      {
        Iterator<InfoTopTreeElement> it = uniqueList.iterator();
        while (it.hasNext()) {
          InfoTopTreeElement currentElement = it.next();
          if ( currentElement != null 
              && currentElement.getGlueInfoTopTreeCategory() != null
              && otherElement.getGlueInfoTopTreeCategory() != null
              && currentElement.equals( otherElement ))
          {
            ArrayList<InfoTopTreeCategory> currentGlueInfoTopTreeCategory = 
              currentElement.getGlueInfoTopTreeCategory();
            for (int i=0; i < otherElement.getGlueInfoTopTreeCategory().size(); i++)
            {
              if (! (currentGlueInfoTopTreeCategory.contains( otherElement.getGlueInfoTopTreeCategory().get( i ) ) ) )
              {
                currentGlueInfoTopTreeCategory.add( otherElement.getGlueInfoTopTreeCategory().get( i ) );
              }
            }
          }
        }
      }
    }
    
    private void buildTopLevel() {
      ArrayList<InfoTopTreeElement> list = new ArrayList<InfoTopTreeElement>();
      HashSet<InfoTopTreeElement> uniqueList = new HashSet<InfoTopTreeElement>();
      
      // We build the top level elements according to the existing projects
      IGridElement[] projectElements;
      try {
        projectElements = GridModel.getRoot().getChildren( null );
        for( IGridElement element : projectElements ) {
          IGridProject igp=(IGridProject)element;
          if(igp.isOpen() && igp.getVO()!=null){
            IGridInfoService infoService = igp.getVO().getInfoService();
            if ( infoService != null && infoService instanceof IExtentedGridInfoService) {
              ArrayList<InfoTopTreeElement> result = ((IExtentedGridInfoService)infoService).getTopTreeElements();
              for (int i=0; i<result.size(); i++)
              {
                InfoTopTreeElement currentElement = result.get( i );
                if (!uniqueList.contains( currentElement ))
                  uniqueList.add( currentElement );
                else
                  addObjectTablename(uniqueList, currentElement);
                  
              }
            }
          }
        }
          
      } catch( GridModelException e ) {
        Activator.logException( e );
      }
      
      Iterator<InfoTopTreeElement> it = uniqueList.iterator();
      while (it.hasNext()) {
        list.add( it.next() );
      }
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
      @Override
      public void run() {
        
        ArrayList<IGridInfoService> infoServicesArray = InfoServiceFactory.getAllExistingInfoService();
        for (int i=0; i<infoServicesArray.size(); i++)
        {
          if ( infoServicesArray.get( i ) instanceof IExtentedGridInfoService)
          {
            IExtentedGridInfoService infoService = ( IExtentedGridInfoService )infoServicesArray.get( i );
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
      }
      
    };
    t.start();
    
    this.fetchJob = FetchJob.getInstance(" Retrieving Information"); //$NON-NLS-1$
    this.showOnlyFilledInfoElements = false;
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
    
    // The viewerFilter that is used consists of 2 filters. One to show all or only filled info
    // elements and another to show all/glite/gria elements.
    ViewerFilter[] initialFilters = new ViewerFilter[2];    
    initialFilters[0] = new InfoViewerFilter();
    initialFilters[1] = new InfoViewerFilter();
    this.viewer.setFilters( initialFilters );
    
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
    hookContextMenu();
    contributeToActionBars();
    
    setCurrentVO( "none" );  //$NON-NLS-1$
  }

  void updateVOList() {
    ArrayList<AbstractGlueTable> agtList = 
      GlueQuery.getGlueTable( "GlueCEAccessControlBaseRule", //$NON-NLS-1$
                              "GlueCEAccessControlBaseRule", null ); //$NON-NLS-1$
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
    
    // Create a new action for every filter that exists.
    ArrayList<InfoViewerFilter> infoFilterArray = new ArrayList<InfoViewerFilter>();
    InfoViewerFilter infoFilter = null;
    IExtensionRegistry myRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = myRegistry.getExtensionPoint( "eu.geclipse.info.infoViewerFilter"); //$NON-NLS-1$
    IExtension[] extensions = extensionPoint.getExtensions();
    
    for (int i = 0; i < extensions.length; i++)
    {
      IExtension extension = extensions[i];
 
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for( IConfigurationElement element : elements ) {
        
        try {
          infoFilter = (InfoViewerFilter) element.createExecutableExtension( "class" ); //$NON-NLS-1$
          if (infoFilter != null)
          {
            infoFilterArray.add( infoFilter );
          }
        } catch( CoreException e ) {
          // do nothing
        }
      }
    }
    
    // Add the default Filter
    FilterAction filterAction = new FilterAction(null, this.viewer); 
    filterAction.setText( "Show everything" ); //$NON-NLS-1$
    filterAction.setToolTipText( "Show everything" ); //$NON-NLS-1$
    filterAction.setImageDescriptor( PlatformUI.getWorkbench()
                                     .getSharedImages()
                                     .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
    manager.add( filterAction );
    
    // Create an action for each filter and show it in the view
    for (int i=0; i<infoFilterArray.size(); i++)
    {
      filterAction = new FilterAction(infoFilterArray.get( i ), this.viewer);
      
      filterAction.setText( infoFilterArray.get( i ).getText());
      filterAction.setToolTipText( infoFilterArray.get( i ).getText() );
      filterAction.setImageDescriptor( PlatformUI.getWorkbench()
                                       .getSharedImages()
                                       .getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK ) );
      manager.add( filterAction );
    }
    
  }

  void fillContextMenu( final IMenuManager manager ) {
    ISelection selection = this.viewer.getSelection();
    Object obj = ( ( IStructuredSelection )selection ).getFirstElement();
    if ( obj instanceof TreeParent ){
      TreeParent tp = ( TreeParent ) obj;
      if ( tp.getAgt() instanceof GlueCE ) {
        manager.add( new Separator() );
        
      }
    }

    this.drillDownAdapter.addNavigationActions( manager );
    // Other plug-ins can contribute there actions here
    manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
  }

  private void fillLocalToolBar( final IToolBarManager manager ) {
    
    IAction refreshAction = new Action() {
      @Override
      public void run() {
        
        GlueInfoViewer.this.fetchJob.schedule();
      }
    };
    refreshAction.setToolTipText( Messages.getString( "GlueInfoView.refresh" ) );  //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/elcl16/refresh_nav.gif" ); //$NON-NLS-1$
    refreshAction.setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    manager.add( refreshAction );
    
    IAction showFilledInfoElementsAction = new Action() {
      @Override
      public void run() {
        URL imageURL = null;
        
        if (GlueInfoViewer.this.showOnlyFilledInfoElements == true)
        {
          GlueInfoViewer.this.showOnlyFilledInfoElements = false;
          this.setToolTipText( Messages.getString( "GlueInfoView.showOnlyFilledInfoElements" ) ); //$NON-NLS-1$
          imageURL = Activator.getDefault().getBundle().getEntry( "icons/elcl16/hide.png" ); //$NON-NLS-1$
          this.setImageDescriptor( ImageDescriptor.createFromURL( imageURL ) );
         
          ViewerFilter[] myViewerFilter = GlueInfoViewer.this.viewer.getFilters();
          myViewerFilter[0] = new InfoViewerFilter();
          GlueInfoViewer.this.viewer.setFilters( myViewerFilter );
        }
        else
        {
          GlueInfoViewer.this.showOnlyFilledInfoElements = true;
          this.setToolTipText( Messages.getString( "GlueInfoView.showAllInfoElements" )); //$NON-NLS-1$
          imageURL = Activator.getDefault().getBundle().getEntry( "icons/elcl16/show_all.png" ); //$NON-NLS-1$
          this.setImageDescriptor( ImageDescriptor.createFromURL( imageURL ) );
          
          ViewerFilter[] myViewerFilter = GlueInfoViewer.this.viewer.getFilters();
          myViewerFilter[0] = new InfoViewFilterShowFilledElements();
          GlueInfoViewer.this.viewer.setFilters( myViewerFilter );
        }
        
        GlueInfoViewer.this.viewer.refresh();
      }
    };
    imgUrl = Activator.getDefault().getBundle().getEntry( "icons/elcl16/hide.png" ); //$NON-NLS-1$
    showFilledInfoElementsAction.setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    showFilledInfoElementsAction.setToolTipText( 
                                    Messages.getString( "GlueInfoView.showOnlyFilledInfoElements" ) ); //$NON-NLS-1$
    manager.add( showFilledInfoElementsAction );
    
    this.drillDownAdapter.addNavigationActions( manager );
  }

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
        GlueInfoViewer.this.viewer.setContentProvider( new ViewContentProvider() );
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
}