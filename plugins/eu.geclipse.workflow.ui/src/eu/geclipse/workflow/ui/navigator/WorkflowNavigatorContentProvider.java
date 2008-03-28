/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.part.Messages;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowNavigatorContentProvider implements ICommonContentProvider
{

  /**
   * @generated
   */
  private static final Object[] EMPTY_ARRAY = new Object[ 0 ];
  /**
   * @generated
   */
  private Viewer myViewer;
  /**
   * @generated
   */
  private AdapterFactoryEditingDomain myEditingDomain;
  /**
   * @generated
   */
  private WorkspaceSynchronizer myWorkspaceSynchronizer;
  /**
   * @generated
   */
  private Runnable myViewerRefreshRunnable;

  /**
   * @generated
   */
  public WorkflowNavigatorContentProvider() {
    TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();
    myEditingDomain = ( AdapterFactoryEditingDomain )editingDomain;
    myEditingDomain.setResourceToReadOnlyMap( new HashMap() {

      public Object get( Object key ) {
        if( !containsKey( key ) ) {
          put( key, Boolean.TRUE );
        }
        return super.get( key );
      }
    } );
    myViewerRefreshRunnable = new Runnable() {

      public void run() {
        if( myViewer != null ) {
          myViewer.refresh();
        }
      }
    };
    myWorkspaceSynchronizer = new WorkspaceSynchronizer( editingDomain,
                                                         new WorkspaceSynchronizer.Delegate()
                                                         {

                                                           public void dispose()
                                                           {
                                                           }

                                                           public boolean handleResourceChanged( final Resource resource )
                                                           {
                                                             for( Iterator it = myEditingDomain.getResourceSet()
                                                               .getResources()
                                                               .iterator(); it.hasNext(); )
                                                             {
                                                               Resource nextResource = ( Resource )it.next();
                                                               nextResource.unload();
                                                             }
                                                             if( myViewer != null )
                                                             {
                                                               myViewer.getControl()
                                                                 .getDisplay()
                                                                 .asyncExec( myViewerRefreshRunnable );
                                                             }
                                                             return true;
                                                           }

                                                           public boolean handleResourceDeleted( Resource resource )
                                                           {
                                                             for( Iterator it = myEditingDomain.getResourceSet()
                                                               .getResources()
                                                               .iterator(); it.hasNext(); )
                                                             {
                                                               Resource nextResource = ( Resource )it.next();
                                                               nextResource.unload();
                                                             }
                                                             if( myViewer != null )
                                                             {
                                                               myViewer.getControl()
                                                                 .getDisplay()
                                                                 .asyncExec( myViewerRefreshRunnable );
                                                             }
                                                             return true;
                                                           }

                                                           public boolean handleResourceMoved( Resource resource,
                                                                                               final URI newURI )
                                                           {
                                                             for( Iterator it = myEditingDomain.getResourceSet()
                                                               .getResources()
                                                               .iterator(); it.hasNext(); )
                                                             {
                                                               Resource nextResource = ( Resource )it.next();
                                                               nextResource.unload();
                                                             }
                                                             if( myViewer != null )
                                                             {
                                                               myViewer.getControl()
                                                                 .getDisplay()
                                                                 .asyncExec( myViewerRefreshRunnable );
                                                             }
                                                             return true;
                                                           }
                                                         } );
  }

  /**
   * @generated
   */
  public void dispose() {
    myWorkspaceSynchronizer.dispose();
    myWorkspaceSynchronizer = null;
    myViewerRefreshRunnable = null;
    for( Iterator it = myEditingDomain.getResourceSet()
      .getResources()
      .iterator(); it.hasNext(); )
    {
      Resource resource = ( Resource )it.next();
      resource.unload();
    }
    ( ( TransactionalEditingDomain )myEditingDomain ).dispose();
    myEditingDomain = null;
  }

  /**
   * @generated
   */
  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    myViewer = viewer;
  }

  /**
   * @generated
   */
  public Object[] getElements( Object inputElement ) {
    return getChildren( inputElement );
  }

  /**
   * @generated
   */
  public void restoreState( IMemento aMemento ) {
    //
  }

  /**
   * @generated
   */
  public void saveState( IMemento aMemento ) {
    //
  }

  /**
   * @generated
   */
  public void init( ICommonContentExtensionSite aConfig ) {
    //
  }

  /**
   * @generated
   */
  public Object[] getChildren( Object parentElement ) {
    if( parentElement instanceof IFile ) {
      IFile file = ( IFile )parentElement;
      URI fileURI = URI.createPlatformResourceURI( file.getFullPath()
        .toString(), true );
      Resource resource = myEditingDomain.getResourceSet()
        .getResource( fileURI, true );
      Collection result = new ArrayList();
      result.addAll( createNavigatorItems( selectViewsByType( resource.getContents(),
                                                              WorkflowEditPart.MODEL_ID ),
                                           file,
                                           false ) );
      return result.toArray();
    }
    if( parentElement instanceof WorkflowNavigatorGroup ) {
      WorkflowNavigatorGroup group = ( WorkflowNavigatorGroup )parentElement;
      return group.getChildren();
    }
    if( parentElement instanceof WorkflowNavigatorItem ) {
      WorkflowNavigatorItem navigatorItem = ( WorkflowNavigatorItem )parentElement;
      if( navigatorItem.isLeaf() || !isOwnView( navigatorItem.getView() ) ) {
        return EMPTY_ARRAY;
      }
      return getViewChildren( navigatorItem.getView(), parentElement );
    }
    return EMPTY_ARRAY;
  }

  /**
   * @generated NOT
   */
  private Object[] getViewChildren( View view, Object parentElement ) {
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowEditPart.VISUAL_ID: {
        Collection result = new ArrayList();
        WorkflowNavigatorGroup links = new WorkflowNavigatorGroup( Messages.getString("NavigatorGroupName_IWorkflow_79_links"),
                                                                   "icons/obj16/LinksNavigatorGroup.gif", parentElement ); //$NON-NLS-1$
        Collection connectedViews = getChildrenByType( Collections.singleton( view ),
                                                       WorkflowJobEditPart.VISUAL_ID );
        result.addAll( createNavigatorItems( connectedViews,
                                             parentElement,
                                             false ) );
        connectedViews = getDiagramLinksByType( Collections.singleton( view ),
                                                LinkEditPart.VISUAL_ID );
        links.addChildren( createNavigatorItems( connectedViews, links, false ) );
        if( !links.isEmpty() ) {
          result.add( links );
        }
        return result.toArray();
      }
      case WorkflowJobEditPart.VISUAL_ID: {
        Collection result = new ArrayList();
        Collection connectedViews = getChildrenByType( Collections.singleton( view ),
                                                       OutputPortEditPart.VISUAL_ID );
        result.addAll( createNavigatorItems( connectedViews,
                                             parentElement,
                                             false ) );
        connectedViews = getChildrenByType( Collections.singleton( view ),
                                            InputPortEditPart.VISUAL_ID );
        result.addAll( createNavigatorItems( connectedViews,
                                             parentElement,
                                             false ) );
        return result.toArray();
      }
      case OutputPortEditPart.VISUAL_ID: {
        Collection result = new ArrayList();
        WorkflowNavigatorGroup outgoinglinks = new WorkflowNavigatorGroup( Messages.getString("NavigatorGroupName_IOutputPort_2001_outgoinglinks"),
                                                                           "icons/obj16/OutputLinks.gif", parentElement ); //$NON-NLS-1$
        Collection connectedViews = getOutgoingLinksByType( Collections.singleton( view ),
                                                            LinkEditPart.VISUAL_ID );
        outgoinglinks.addChildren( createNavigatorItems( connectedViews,
                                                         outgoinglinks,
                                                         true ) );
        if( !outgoinglinks.isEmpty() ) {
          result.add( outgoinglinks );
        }
        return result.toArray();
      }
      case InputPortEditPart.VISUAL_ID: {
        Collection result = new ArrayList();
        WorkflowNavigatorGroup incominglinks = new WorkflowNavigatorGroup( Messages.getString("NavigatorGroupName_IInputPort_2002_incominglinks"),
                                                                           "icons/obj16/InputLinks.gif", parentElement ); //$NON-NLS-1$
        Collection connectedViews = getIncomingLinksByType( Collections.singleton( view ),
                                                            LinkEditPart.VISUAL_ID );
        incominglinks.addChildren( createNavigatorItems( connectedViews,
                                                         incominglinks,
                                                         true ) );
        if( !incominglinks.isEmpty() ) {
          result.add( incominglinks );
        }
        return result.toArray();
      }
      case LinkEditPart.VISUAL_ID: {
        Collection result = new ArrayList();
        WorkflowNavigatorGroup target = new WorkflowNavigatorGroup( Messages.getString("NavigatorGroupName_ILink_3001_target"),
                                                                    "icons/obj16/LinkTarget.gif", parentElement ); //$NON-NLS-1$
        WorkflowNavigatorGroup source = new WorkflowNavigatorGroup( Messages.getString("NavigatorGroupName_ILink_3001_source"),
                                                                    "icons/obj16/LinkSource.gif", parentElement ); //$NON-NLS-1$
        Collection connectedViews = getLinksTargetByType( Collections.singleton( view ),
                                                          InputPortEditPart.VISUAL_ID );
        target.addChildren( createNavigatorItems( connectedViews, target, true ) );
        connectedViews = getLinksSourceByType( Collections.singleton( view ),
                                               OutputPortEditPart.VISUAL_ID );
        source.addChildren( createNavigatorItems( connectedViews, source, true ) );
        if( !target.isEmpty() ) {
          result.add( target );
        }
        if( !source.isEmpty() ) {
          result.add( source );
        }
        return result.toArray();
      }
    }
    return EMPTY_ARRAY;
  }

  /**
   * @generated
   */
  private Collection getLinksSourceByType( Collection edges, int visualID ) {
    Collection result = new ArrayList();
    String type = WorkflowVisualIDRegistry.getType( visualID );
    for( Iterator it = edges.iterator(); it.hasNext(); ) {
      Edge nextEdge = ( Edge )it.next();
      View nextEdgeSource = nextEdge.getSource();
      if( type.equals( nextEdgeSource.getType() ) && isOwnView( nextEdgeSource ) )
      {
        result.add( nextEdgeSource );
      }
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection getLinksTargetByType( Collection edges, int visualID ) {
    Collection result = new ArrayList();
    String type = WorkflowVisualIDRegistry.getType( visualID );
    for( Iterator it = edges.iterator(); it.hasNext(); ) {
      Edge nextEdge = ( Edge )it.next();
      View nextEdgeTarget = nextEdge.getTarget();
      if( type.equals( nextEdgeTarget.getType() ) && isOwnView( nextEdgeTarget ) )
      {
        result.add( nextEdgeTarget );
      }
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection getOutgoingLinksByType( Collection nodes, int visualID ) {
    Collection result = new ArrayList();
    String type = WorkflowVisualIDRegistry.getType( visualID );
    for( Iterator it = nodes.iterator(); it.hasNext(); ) {
      View nextNode = ( View )it.next();
      result.addAll( selectViewsByType( nextNode.getSourceEdges(), type ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection getIncomingLinksByType( Collection nodes, int visualID ) {
    Collection result = new ArrayList();
    String type = WorkflowVisualIDRegistry.getType( visualID );
    for( Iterator it = nodes.iterator(); it.hasNext(); ) {
      View nextNode = ( View )it.next();
      result.addAll( selectViewsByType( nextNode.getTargetEdges(), type ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection getChildrenByType( Collection nodes, int visualID ) {
    Collection result = new ArrayList();
    String type = WorkflowVisualIDRegistry.getType( visualID );
    for( Iterator it = nodes.iterator(); it.hasNext(); ) {
      View nextNode = ( View )it.next();
      result.addAll( selectViewsByType( nextNode.getChildren(), type ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection getDiagramLinksByType( Collection diagrams, int visualID )
  {
    Collection result = new ArrayList();
    String type = WorkflowVisualIDRegistry.getType( visualID );
    for( Iterator it = diagrams.iterator(); it.hasNext(); ) {
      Diagram nextDiagram = ( Diagram )it.next();
      result.addAll( selectViewsByType( nextDiagram.getEdges(), type ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection selectViewsByType( Collection views, String type ) {
    Collection result = new ArrayList();
    for( Iterator it = views.iterator(); it.hasNext(); ) {
      View nextView = ( View )it.next();
      if( type.equals( nextView.getType() ) && isOwnView( nextView ) ) {
        result.add( nextView );
      }
    }
    return result;
  }

  /**
   * @generated
   */
  private boolean isOwnView( View view ) {
    return WorkflowEditPart.MODEL_ID.equals( WorkflowVisualIDRegistry.getModelID( view ) );
  }

  /**
   * @generated
   */
  private Collection createNavigatorItems( Collection views,
                                           Object parent,
                                           boolean isLeafs )
  {
    Collection result = new ArrayList();
    for( Iterator it = views.iterator(); it.hasNext(); ) {
      result.add( new WorkflowNavigatorItem( ( View )it.next(), parent, isLeafs ) );
    }
    return result;
  }

  /**
   * @generated
   */
  public Object getParent( Object element ) {
    if( element instanceof WorkflowAbstractNavigatorItem ) {
      WorkflowAbstractNavigatorItem abstractNavigatorItem = ( WorkflowAbstractNavigatorItem )element;
      return abstractNavigatorItem.getParent();
    }
    return null;
  }

  /**
   * @generated
   */
  public boolean hasChildren( Object element ) {
    return element instanceof IFile || getChildren( element ).length > 0;
  }
}
