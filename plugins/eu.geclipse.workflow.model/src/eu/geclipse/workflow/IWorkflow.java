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
package eu.geclipse.workflow;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IWorkflow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.IWorkflow#getNodes <em>Nodes</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IWorkflow#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.IWorkflowPackage#getIWorkflow()
 * @model
 * @generated
 */
public interface IWorkflow extends IWorkflowElement
{
  /**
   * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.workflow.IWorkflowNode}.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IWorkflowNode#getWorkflow <em>Workflow</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Nodes</em>' containment reference list.
   * @see eu.geclipse.workflow.IWorkflowPackage#getIWorkflow_Nodes()
   * @see eu.geclipse.workflow.IWorkflowNode#getWorkflow
   * @model type="eu.geclipse.workflow.IWorkflowNode" opposite="workflow" containment="true"
   * @generated
   */
  EList<IWorkflowNode> getNodes();

  /**
   * Returns the value of the '<em><b>Links</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.workflow.ILink}.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.ILink#getWorkflow <em>Workflow</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Links</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Links</em>' containment reference list.
   * @see eu.geclipse.workflow.IWorkflowPackage#getIWorkflow_Links()
   * @see eu.geclipse.workflow.ILink#getWorkflow
   * @model type="eu.geclipse.workflow.ILink" opposite="workflow" containment="true"
   * @generated
   */
  EList<ILink> getLinks();

} // IWorkflow
