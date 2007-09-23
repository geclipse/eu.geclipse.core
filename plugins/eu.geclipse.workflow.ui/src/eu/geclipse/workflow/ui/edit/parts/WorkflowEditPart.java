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
package eu.geclipse.workflow.ui.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.ui.edit.policies.WorkflowCanonicalEditPolicy;
import eu.geclipse.workflow.ui.edit.policies.WorkflowItemSemanticEditPolicy;

/**
 * The class that connects the Figure and Model of the Workflow
 */
public class WorkflowEditPart extends DiagramEditPart {

  public final static String MODEL_ID = "Workflow"; //$NON-NLS-1$

  public static final int VISUAL_ID = 79;

  /**
   * Default constructor.
   */
  public WorkflowEditPart( View view ) {
    super( view );
  }

  /**
   * @generated
   */
  @Override
  protected void createDefaultEditPolicies() {
    super.createDefaultEditPolicies();
    installEditPolicy( EditPolicyRoles.SEMANTIC_ROLE,
                       new WorkflowItemSemanticEditPolicy() );
    installEditPolicy( EditPolicyRoles.CANONICAL_ROLE,
                       new WorkflowCanonicalEditPolicy() );
    // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
  }
}
