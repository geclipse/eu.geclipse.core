/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.part.WorkflowDiagramUpdater;
import eu.geclipse.workflow.ui.part.WorkflowLinkDescriptor;
import eu.geclipse.workflow.ui.part.WorkflowNodeDescriptor;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowCanonicalEditPolicy extends CanonicalConnectionEditPolicy {

  /**
   * @generated
   */
  Set myFeaturesToSynchronize;

  /**
   * @generated
   */
  @Override
  protected List getSemanticChildrenList() {
    View viewObject = ( View )getHost().getModel();
    List result = new LinkedList();
    for( Iterator it = WorkflowDiagramUpdater.getIWorkflow_79SemanticChildren( viewObject )
      .iterator(); it.hasNext(); )
    {
      result.add( ( ( WorkflowNodeDescriptor )it.next() ).getModelElement() );
    }
    return result;
  }

  /**
   * @generated
   */
  @Override
  protected boolean shouldDeleteView( View view ) {
    return true;
  }

  /**
   * @generated
   */
  @Override
  protected boolean isOrphaned( Collection semanticChildren, final View view ) {
    int visualID = WorkflowVisualIDRegistry.getVisualID( view );
    switch( visualID ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return !semanticChildren.contains( view.getElement() )
               || visualID != WorkflowVisualIDRegistry.getNodeVisualID( ( View )getHost().getModel(),
                                                                        view.getElement() );
    }
    return false;
  }

  /**
   * @generated
   */
  @Override
  protected String getDefaultFactoryHint() {
    return null;
  }

  /**
   * @generated
   */
  @Override
  protected Set getFeaturesToSynchronize() {
    if( myFeaturesToSynchronize == null ) {
      myFeaturesToSynchronize = new HashSet();
      myFeaturesToSynchronize.add( IWorkflowPackage.eINSTANCE.getIWorkflow_Nodes() );
    }
    return myFeaturesToSynchronize;
  }

  /**
   * @generated
   */
  @Override
  protected List getSemanticConnectionsList() {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  protected EObject getSourceElement( EObject relationship ) {
    return null;
  }

  /**
   * @generated
   */
  @Override
  protected EObject getTargetElement( EObject relationship ) {
    return null;
  }

  /**
   * @generated
   */
  @Override
  protected boolean shouldIncludeConnection( Edge connector, Collection children )
  {
    return false;
  }

  /**
   * @generated
   */
  @Override
  protected void refreshSemantic() {
    List createdViews = new LinkedList();
    createdViews.addAll( refreshSemanticChildren() );
    List createdConnectionViews = new LinkedList();
    createdConnectionViews.addAll( refreshSemanticConnections() );
    createdConnectionViews.addAll( refreshConnections() );
    if( createdViews.size() > 1 ) {
      // perform a layout of the container
      DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand( host().getEditingDomain(),
                                                                   createdViews,
                                                                   host() );
      executeCommand( new ICommandProxy( layoutCmd ) );
    }
    createdViews.addAll( createdConnectionViews );
    makeViewsImmutable( createdViews );
  }

  /**
   * @generated
   */
  private Diagram getDiagram() {
    return ( ( View )getHost().getModel() ).getDiagram();
  }

  /**
   * @generated
   */
  private Collection refreshConnections() {
    Map domain2NotationMap = new HashMap();
    Collection linkDescriptors = collectAllLinks( getDiagram(),
                                                  domain2NotationMap );
    Collection existingLinks = new LinkedList( getDiagram().getEdges() );
    for( Iterator linksIterator = existingLinks.iterator(); linksIterator.hasNext(); )
    {
      Edge nextDiagramLink = ( Edge )linksIterator.next();
      EObject diagramLinkObject = nextDiagramLink.getElement();
      EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
      EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
      int diagramLinkVisualID = WorkflowVisualIDRegistry.getVisualID( nextDiagramLink );
      for( Iterator LinkDescriptorsIterator = linkDescriptors.iterator(); LinkDescriptorsIterator.hasNext(); )
      {
        WorkflowLinkDescriptor nextLinkDescriptor = ( WorkflowLinkDescriptor )LinkDescriptorsIterator.next();
        if( diagramLinkObject == nextLinkDescriptor.getModelElement()
            && diagramLinkSrc == nextLinkDescriptor.getSource()
            && diagramLinkDst == nextLinkDescriptor.getDestination()
            && diagramLinkVisualID == nextLinkDescriptor.getVisualID() )
        {
          linksIterator.remove();
          LinkDescriptorsIterator.remove();
        }
      }
    }
    deleteViews( existingLinks.iterator() );
    return createConnections( linkDescriptors, domain2NotationMap );
  }

  /**
   * @generated
   */
  private Collection collectAllLinks( View view, Map domain2NotationMap ) {
    Collection result = new LinkedList();
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowEditPart.VISUAL_ID: {
        domain2NotationMap.put( view.getElement(), view );
        result.addAll( WorkflowDiagramUpdater.getIWorkflow_79ContainedLinks( view ) );
        break;
      }
      case WorkflowJobEditPart.VISUAL_ID: {
        domain2NotationMap.put( view.getElement(), view );
        result.addAll( WorkflowDiagramUpdater.getIWorkflowJob_1001ContainedLinks( view ) );
        break;
      }
      case OutputPortEditPart.VISUAL_ID: {
        domain2NotationMap.put( view.getElement(), view );
        result.addAll( WorkflowDiagramUpdater.getIOutputPort_2001ContainedLinks( view ) );
        break;
      }
      case InputPortEditPart.VISUAL_ID: {
        domain2NotationMap.put( view.getElement(), view );
        result.addAll( WorkflowDiagramUpdater.getIInputPort_2002ContainedLinks( view ) );
        break;
      }
      case LinkEditPart.VISUAL_ID: {
        domain2NotationMap.put( view.getElement(), view );
        result.addAll( WorkflowDiagramUpdater.getILink_3001ContainedLinks( view ) );
        break;
      }
    }
    for( Iterator children = view.getChildren().iterator(); children.hasNext(); )
    {
      result.addAll( collectAllLinks( ( View )children.next(),
                                      domain2NotationMap ) );
    }
    for( Iterator edges = view.getSourceEdges().iterator(); edges.hasNext(); ) {
      result.addAll( collectAllLinks( ( View )edges.next(), domain2NotationMap ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private Collection createConnections( Collection linkDescriptors,
                                        Map domain2NotationMap )
  {
    List adapters = new LinkedList();
    for( Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator.hasNext(); )
    {
      final WorkflowLinkDescriptor nextLinkDescriptor = ( WorkflowLinkDescriptor )linkDescriptorsIterator.next();
      EditPart sourceEditPart = getEditPart( nextLinkDescriptor.getSource(),
                                             domain2NotationMap );
      EditPart targetEditPart = getEditPart( nextLinkDescriptor.getDestination(),
                                             domain2NotationMap );
      if( sourceEditPart == null || targetEditPart == null ) {
        continue;
      }
      CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor( nextLinkDescriptor.getSemanticAdapter(),
                                                                                                                                  null,
                                                                                                                                  ViewUtil.APPEND,
                                                                                                                                  false,
                                                                                                                                  ( ( IGraphicalEditPart )getHost() ).getDiagramPreferencesHint() );
      CreateConnectionViewRequest ccr = new CreateConnectionViewRequest( descriptor );
      ccr.setType( RequestConstants.REQ_CONNECTION_START );
      ccr.setSourceEditPart( sourceEditPart );
      sourceEditPart.getCommand( ccr );
      ccr.setTargetEditPart( targetEditPart );
      ccr.setType( RequestConstants.REQ_CONNECTION_END );
      Command cmd = targetEditPart.getCommand( ccr );
      if( cmd != null && cmd.canExecute() ) {
        executeCommand( cmd );
        IAdaptable viewAdapter = ( IAdaptable )ccr.getNewObject();
        if( viewAdapter != null ) {
          adapters.add( viewAdapter );
        }
      }
    }
    return adapters;
  }

  /**
   * @generated
   */
  private EditPart getEditPart( EObject domainModelElement,
                                Map domain2NotationMap )
  {
    View view = ( View )domain2NotationMap.get( domainModelElement );
    if( view != null ) {
      return ( EditPart )getHost().getViewer().getEditPartRegistry().get( view );
    }
    return null;
  }
}
