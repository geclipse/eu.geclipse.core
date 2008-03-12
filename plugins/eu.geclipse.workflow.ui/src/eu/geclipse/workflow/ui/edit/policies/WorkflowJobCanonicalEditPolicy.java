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
package eu.geclipse.workflow.ui.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.part.WorkflowDiagramUpdater;
import eu.geclipse.workflow.ui.part.WorkflowNodeDescriptor;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowJobCanonicalEditPolicy extends CanonicalEditPolicy {

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
    for( Iterator it = WorkflowDiagramUpdater.getIWorkflowJob_1001SemanticChildren( viewObject )
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
  protected boolean isOrphaned( Collection semanticChildren, final View view ) {
    int visualID = WorkflowVisualIDRegistry.getVisualID( view );
    switch( visualID ) {
      case OutputPortEditPart.VISUAL_ID:
      case InputPortEditPart.VISUAL_ID:
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
      myFeaturesToSynchronize.add( IWorkflowPackage.eINSTANCE.getIWorkflowNode_Outputs() );
      myFeaturesToSynchronize.add( IWorkflowPackage.eINSTANCE.getIWorkflowNode_Inputs() );
    }
    return myFeaturesToSynchronize;
  }
}
