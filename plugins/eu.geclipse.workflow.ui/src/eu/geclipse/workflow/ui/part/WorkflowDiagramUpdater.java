/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.ILink;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.IWorkflowNode;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * @generated
 */
public class WorkflowDiagramUpdater {

  /**
   * @generated
   */
  public static List getSemanticChildren( View view ) {
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return getIWorkflowJob_1001SemanticChildren( view );
      case WorkflowEditPart.VISUAL_ID:
        return getIWorkflow_79SemanticChildren( view );
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIWorkflowJob_1001SemanticChildren( View view ) {
    if( !view.isSetElement() ) {
      return Collections.EMPTY_LIST;
    }
    IWorkflowJob modelElement = ( IWorkflowJob )view.getElement();
    List result = new LinkedList();
    for( Iterator it = modelElement.getOutputs().iterator(); it.hasNext(); ) {
      IOutputPort childElement = ( IOutputPort )it.next();
      int visualID = WorkflowVisualIDRegistry.getNodeVisualID( view,
                                                               childElement );
      if( visualID == OutputPortEditPart.VISUAL_ID ) {
        result.add( new WorkflowNodeDescriptor( childElement, visualID ) );
        continue;
      }
    }
    for( Iterator it = modelElement.getInputs().iterator(); it.hasNext(); ) {
      IInputPort childElement = ( IInputPort )it.next();
      int visualID = WorkflowVisualIDRegistry.getNodeVisualID( view,
                                                               childElement );
      if( visualID == InputPortEditPart.VISUAL_ID ) {
        result.add( new WorkflowNodeDescriptor( childElement, visualID ) );
        continue;
      }
    }
    return result;
  }

  /**
   * @generated
   */
  public static List getIWorkflow_79SemanticChildren( View view ) {
    if( !view.isSetElement() ) {
      return Collections.EMPTY_LIST;
    }
    IWorkflow modelElement = ( IWorkflow )view.getElement();
    List result = new LinkedList();
    for( Iterator it = modelElement.getNodes().iterator(); it.hasNext(); ) {
      IWorkflowNode childElement = ( IWorkflowNode )it.next();
      int visualID = WorkflowVisualIDRegistry.getNodeVisualID( view,
                                                               childElement );
      if( visualID == WorkflowJobEditPart.VISUAL_ID ) {
        result.add( new WorkflowNodeDescriptor( childElement, visualID ) );
        continue;
      }
    }
    return result;
  }

  /**
   * @generated
   */
  public static List getContainedLinks( View view ) {
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowEditPart.VISUAL_ID:
        return getIWorkflow_79ContainedLinks( view );
      case WorkflowJobEditPart.VISUAL_ID:
        return getIWorkflowJob_1001ContainedLinks( view );
      case OutputPortEditPart.VISUAL_ID:
        return getIOutputPort_2001ContainedLinks( view );
      case InputPortEditPart.VISUAL_ID:
        return getIInputPort_2002ContainedLinks( view );
      case LinkEditPart.VISUAL_ID:
        return getILink_3001ContainedLinks( view );
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIncomingLinks( View view ) {
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return getIWorkflowJob_1001IncomingLinks( view );
      case OutputPortEditPart.VISUAL_ID:
        return getIOutputPort_2001IncomingLinks( view );
      case InputPortEditPart.VISUAL_ID:
        return getIInputPort_2002IncomingLinks( view );
      case LinkEditPart.VISUAL_ID:
        return getILink_3001IncomingLinks( view );
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getOutgoingLinks( View view ) {
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return getIWorkflowJob_1001OutgoingLinks( view );
      case OutputPortEditPart.VISUAL_ID:
        return getIOutputPort_2001OutgoingLinks( view );
      case InputPortEditPart.VISUAL_ID:
        return getIInputPort_2002OutgoingLinks( view );
      case LinkEditPart.VISUAL_ID:
        return getILink_3001OutgoingLinks( view );
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIWorkflow_79ContainedLinks( View view ) {
    IWorkflow modelElement = ( IWorkflow )view.getElement();
    List result = new LinkedList();
    result.addAll( getContainedTypeModelFacetLinks_ILink_3001( modelElement ) );
    return result;
  }

  /**
   * @generated
   */
  public static List getIWorkflowJob_1001ContainedLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIOutputPort_2001ContainedLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIInputPort_2002ContainedLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getILink_3001ContainedLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIWorkflowJob_1001IncomingLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIOutputPort_2001IncomingLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIInputPort_2002IncomingLinks( View view ) {
    IInputPort modelElement = ( IInputPort )view.getElement();
    Map crossReferences = EcoreUtil.CrossReferencer.find( view.eResource()
      .getResourceSet()
      .getResources() );
    List result = new LinkedList();
    result.addAll( getIncomingTypeModelFacetLinks_ILink_3001( modelElement,
                                                              crossReferences ) );
    return result;
  }

  /**
   * @generated
   */
  public static List getILink_3001IncomingLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIWorkflowJob_1001OutgoingLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getIOutputPort_2001OutgoingLinks( View view ) {
    IOutputPort modelElement = ( IOutputPort )view.getElement();
    List result = new LinkedList();
    result.addAll( getOutgoingTypeModelFacetLinks_ILink_3001( modelElement ) );
    return result;
  }

  /**
   * @generated
   */
  public static List getIInputPort_2002OutgoingLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  public static List getILink_3001OutgoingLinks( View view ) {
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  private static Collection getContainedTypeModelFacetLinks_ILink_3001( IWorkflow container )
  {
    Collection result = new LinkedList();
    for( Iterator links = container.getLinks().iterator(); links.hasNext(); ) {
      Object linkObject = links.next();
      if( false == linkObject instanceof ILink ) {
        continue;
      }
      ILink link = ( ILink )linkObject;
      if( LinkEditPart.VISUAL_ID != WorkflowVisualIDRegistry.getLinkWithClassVisualID( link ) )
      {
        continue;
      }
      IInputPort dst = link.getTarget();
      IOutputPort src = link.getSource();
      result.add( new WorkflowLinkDescriptor( src,
                                              dst,
                                              link,
                                              WorkflowElementTypes.ILink_3001,
                                              LinkEditPart.VISUAL_ID ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private static Collection getIncomingTypeModelFacetLinks_ILink_3001( IInputPort target,
                                                                       Map crossReferences )
  {
    Collection result = new LinkedList();
    Collection settings = ( Collection )crossReferences.get( target );
    for( Iterator it = settings.iterator(); it.hasNext(); ) {
      EStructuralFeature.Setting setting = ( EStructuralFeature.Setting )it.next();
      if( setting.getEStructuralFeature() != IWorkflowPackage.eINSTANCE.getILink_Target()
          || false == setting.getEObject() instanceof ILink )
      {
        continue;
      }
      ILink link = ( ILink )setting.getEObject();
      if( LinkEditPart.VISUAL_ID != WorkflowVisualIDRegistry.getLinkWithClassVisualID( link ) )
      {
        continue;
      }
      IOutputPort src = link.getSource();
      result.add( new WorkflowLinkDescriptor( src,
                                              target,
                                              link,
                                              WorkflowElementTypes.ILink_3001,
                                              LinkEditPart.VISUAL_ID ) );
    }
    return result;
  }

  /**
   * @generated
   */
  private static Collection getOutgoingTypeModelFacetLinks_ILink_3001( IOutputPort source )
  {
    IWorkflow container = null;
    // Find container element for the link.
    // Climb up by containment hierarchy starting from the source
    // and return the first element that is instance of the container class.
    for( EObject element = source; element != null && container == null; element = element.eContainer() )
    {
      if( element instanceof IWorkflow ) {
        container = ( IWorkflow )element;
      }
    }
    if( container == null ) {
      return Collections.EMPTY_LIST;
    }
    Collection result = new LinkedList();
    for( Iterator links = container.getLinks().iterator(); links.hasNext(); ) {
      Object linkObject = links.next();
      if( false == linkObject instanceof ILink ) {
        continue;
      }
      ILink link = ( ILink )linkObject;
      if( LinkEditPart.VISUAL_ID != WorkflowVisualIDRegistry.getLinkWithClassVisualID( link ) )
      {
        continue;
      }
      IInputPort dst = link.getTarget();
      IOutputPort src = link.getSource();
      if( src != source ) {
        continue;
      }
      result.add( new WorkflowLinkDescriptor( src,
                                              dst,
                                              link,
                                              WorkflowElementTypes.ILink_3001,
                                              LinkEditPart.VISUAL_ID ) );
    }
    return result;
  }
}
