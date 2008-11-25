/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import eu.geclipse.batch.BatchConnectionInfo;
import eu.geclipse.batch.Extensions;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IBatchServiceDescription;
import eu.geclipse.batch.ui.BatchServiceManager;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.BatchUpdate;
import eu.geclipse.batch.ui.internal.BoxSortName;
import eu.geclipse.batch.ui.internal.BoxSortState;
import eu.geclipse.batch.ui.internal.ComputingElementAction;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.QueueDeleteAction;
import eu.geclipse.batch.ui.internal.QueueEnDisAction;
import eu.geclipse.batch.ui.internal.QueueStartStopAction;
import eu.geclipse.batch.ui.internal.SSHConnectionInfo;
import eu.geclipse.batch.ui.internal.WorkerNodeAction;
import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.parts.BatchEditPartFactory;
import eu.geclipse.batch.ui.internal.parts.BatchTreeEditPartFactory;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**
 * A graphical editor that can edit .batch files. The
 * binding between the .batch file extension and this editor is done in
 * plugin.xml
 */
public class BatchEditor extends GraphicalEditor {
  /**
   * 
   */
  public int queueByName = 0;
  /**
   * 
   */
  public int sortedQ = 0;
  /**
   * 
   */
  public int sortedN = 0;
  /**
   * 
   */
  public int workerNodeByName = 0;
  /**
   * 
   */
  public int queueByState = 0;
  /**
   * 
   */
  public int workerNodeByState = 0;

  protected BatchUpdate updateJob;
  protected BatchConnectionInfo batchInfo = null;
  private BatchDiagram diagram;
  private IBatchService batchWrapper;

  /**
   * Create a new BatchEditor instance. This is called by the Workspace.
   */
  public BatchEditor( ) {
    super();
    setEditDomain( new DefaultEditDomain( this ) );
  }

  /**
   * Configure the graphical viewer before it receives contents.
   * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
   */
  @Override
  protected void configureGraphicalViewer() {
    if ( null != this.batchInfo ) {
      super.configureGraphicalViewer();
      
      ScalableFreeformRootEditPart root = new ScalableFreeformRootEditPart();

      GraphicalViewer viewer = getGraphicalViewer();
      viewer.setEditPartFactory( new BatchEditPartFactory() );
      viewer.setRootEditPart( new ScalableFreeformRootEditPart() );
      viewer.setKeyHandler( new GraphicalViewerKeyHandler( viewer ) );
      viewer.setRootEditPart( root );
      
      // Configure the context menu provider
      BatchEditorContextMenuProvider cmProvider = new BatchEditorContextMenuProvider( viewer, getActionRegistry() );
      viewer.setContextMenu( cmProvider );
      
      getEditorSite().registerContextMenu( Activator.ID_BATCH_CONTEXT,
                                           cmProvider, 
                                           getSite().getSelectionProvider(), false );
      
      List<String> zoomLevels = new ArrayList<String>( 3 );
      zoomLevels.add( ZoomManager.FIT_ALL );
      zoomLevels.add( ZoomManager.FIT_WIDTH );
      zoomLevels.add( ZoomManager.FIT_HEIGHT );

      root.getZoomManager().setZoomLevelContributions( zoomLevels );
      IAction zoomIn = new ZoomInAction( root.getZoomManager() );
      getActionRegistry().registerAction( zoomIn );
      
      IAction zoomOut = new ZoomOutAction( root.getZoomManager() );
      getActionRegistry().registerAction( zoomOut );
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getAdapter()
   */
  @SuppressWarnings("unchecked")
  @Override
  public Object getAdapter( final Class type ) {
    Object obj = null;

    if ( ZoomManager.class == type ) {
      obj = getGraphicalViewer().getProperty( ZoomManager.class.toString() );
    } else if ( IContentOutlinePage.class == type)
      obj = new BatchOutlinePage( new TreeViewer() );
    else
      obj = super.getAdapter( type );

    return obj;
  }

  /**
   * Returns the model that this editor will edit.
   * @return Returns the Batch model.
   */
  protected BatchDiagram getModel() {
    return this.diagram;
  }

  /**
   * Set up the editor's initial content (after creation).
   *
   * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
   */
  @Override
  protected void initializeGraphicalViewer() {
    if ( null != this.batchInfo ) {
      GraphicalViewer viewer = getGraphicalViewer();
      viewer.setContents( getModel() ); // set the contents of this editor

      SSHConnectionInfo sshInfo = new SSHConnectionInfo(this.batchInfo.getAccount(), this.batchInfo.getBatchName(), 
                                                        null, null, 22);

      try {
        if ( this.batchWrapper.connectToServer( sshInfo ) ) {
        
          this.updateJob = new BatchUpdate( getSite().getShell(), this.diagram, 
                                            this.batchWrapper, this.batchInfo.getBatchName(), 
                                            this.batchInfo.getBatchType(), this.batchInfo.getUpdateInterval(), 
                                            BatchEditor.this );
          this.updateJob.startUpdate();
        }
      } catch ( ProblemException gExc ) {
          ProblemDialog.openProblem( null,
                                     Messages.getString( "BatchEditor.Connection.Error" ) , //$NON-NLS-1$
                                     null,
                                     gExc );
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
   */
  @Override
  protected void setInput( final IEditorInput input ) {
    super.setInput( input );
    boolean error = false; 
    boolean found = false;
    IFile file = ( ( IFileEditorInput )input ).getFile();
    this.batchInfo = new BatchConnectionInfo( file );
      
    try {
      this.batchInfo.load();
    } catch ( ProblemException e ) {
      error = true;
      ProblemDialog.openProblem( getSite().getShell(),
                                 Messages.getString( "BatchEditor.Error.LoadConfFileTitle" ), //$NON-NLS-1$
                                 Messages.getString( "BatchEditor.Error.LoadConfFile" ), //$NON-NLS-1$
                                 e );
    }

    // Search through the extensions to find a implementation that supports this batch service type
     if ( !error ) { 
      List< IBatchServiceDescription > services = Extensions.getRegisteredBatchServiceDescriptions(); 
      
      // Do we have an implementation to open this batch service
      for ( IBatchServiceDescription description : services ) {
        if ( description.supportsService( this.batchInfo.getBatchType() ) ) {
          try {
            this.batchWrapper = BatchServiceManager.getManager().createService( description, file.getName() );
            found = true;
            //this.batchWrapper = description.createService();
          } catch( ProblemException e ) {
            error = true;
            ProblemDialog.openProblem( getSite().getShell(),
                                       Messages.getString( "BatchEditor.Error.CreateServiceTitle" ), //$NON-NLS-1$
                                       Messages.getString( "BatchEditor.Error.CreateService" ), //$NON-NLS-1$
                                       e );
          }

          // Set the tab name
          this.setPartName( file.getName() );

          // Create the diagram
          this.diagram = new BatchDiagram();

          break;
        }
      }
    }
    if ( !found )
      this.batchInfo = null;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void createActions() {
    super.createActions();

    ActionRegistry registry = getActionRegistry();
    IAction action;

    action = new WorkerNodeAction( this, this.batchWrapper );
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );

    action = new QueueEnDisAction( this, this.batchWrapper );
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );

    action = new QueueStartStopAction( this, this.batchWrapper );
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );

    action = new QueueDeleteAction( this, this.batchWrapper );
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );

    action = new ComputingElementAction( this, this.batchWrapper ); 
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );
    
    action = new BoxSortName( this, BatchEditor.this ); 
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );
    
    
    action = new BoxSortState( this,BatchEditor.this ); 
    registry.registerAction( action );
    getSelectionActions().add( action.getId() );
  }

  @Override
  public void dispose() {
    // Stop the updater
    if ( null != BatchEditor.this.updateJob )
      BatchEditor.this.updateJob.stopUpdate();

    if ( null != this.batchWrapper )
      BatchServiceManager.getManager().destroyService( this.batchWrapper );
    
    // dispose
    super.dispose();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.ISaveablePart#isSaveOnCloseNeeded()
   */
  @Override
  public boolean isSaveOnCloseNeeded() {
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
   */
  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void doSave( final IProgressMonitor monitor ) {
    // Do nothing
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.ISaveablePart#doSaveAs()
   */
  @Override
  public void doSaveAs() {
    // Do nothing
  }

  /**
   * Creates an outline pagebook for this editor.
   */
  public class BatchOutlinePage extends ContentOutlinePage {

    /**
     * Create a new outline page for the batch editor.
     * @param viewer a viewer (TreeViewer instance) used for this outline page
     */
    public BatchOutlinePage( final EditPartViewer viewer ) {
      super( viewer );
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @SuppressWarnings("synthetic-access")
    @Override
    public void createControl( final Composite parent ) {

      if ( null != BatchEditor.this.batchInfo ){
        // Create outline viewer page
        getViewer().createControl( parent );
        // Configure outline viewer
        getViewer().setEditDomain( getEditDomain() );
        getViewer().setEditPartFactory( new BatchTreeEditPartFactory() );
        // Configure & add context menu to viewer
        BatchEditorContextMenuProvider cmProvider = 
          new BatchEditorContextMenuProvider( getViewer(), getActionRegistry() );
        
        getViewer().setContextMenu( cmProvider );
        getSite().registerContextMenu( Activator.ID_BATCH_CONTEXT,
                                       cmProvider,
                                       getSite().getSelectionProvider() );

        // Hook outline viewer
        getSelectionSynchronizer().addViewer( getViewer() );
        // Initialize outline viewer with model
        getViewer().setContents( getModel() );
      }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.part.IPage#dispose()
     */
    @SuppressWarnings("synthetic-access")
    @Override
    public void dispose() {
      // Unhook outline viewer
      getSelectionSynchronizer().removeViewer( getViewer() );

      // dispose
      super.dispose();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.part.IPage#getControl()
     */
    @Override
    public Control getControl() {
      return getViewer().getControl();
    }

    /**
     * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
     */
    @SuppressWarnings("synthetic-access")
    @Override
    public void init( final IPageSite pageSite ) {
      super.init( pageSite );
      ActionRegistry registry = getActionRegistry();
      
      IActionBars bars = pageSite.getActionBars();
      String id = ActionFactory.UNDO.getId();
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = ActionFactory.REDO.getId();
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = GEFActionConstants.ZOOM_IN;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = GEFActionConstants.ZOOM_OUT;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = WorkerNodeAction.PROPERTY_WN_ACTION_CHANGESTATUS;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = QueueEnDisAction.PROPERTY_QUEUE_ACTION_ENABLE_DISABLE;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = QueueStartStopAction.PROPERTY_QUEUE_ACTION_START_STOP;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );

      id = QueueDeleteAction.PROPERTY_QUEUE_ACTION_DELETE;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );
      
      id = ComputingElementAction.PROPERTY_COMPUTINGELEMENT_ACTION_NEWQUEUE;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );
      
      id = BoxSortName.PROPERTY_SORT_BY_NAME;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );
      
      id = BoxSortState.PROPERTY_SORT_BY_STATE;
      bars.setGlobalActionHandler( id, registry.getAction( id ) );
      
      bars.updateActionBars();
    }
  }
}