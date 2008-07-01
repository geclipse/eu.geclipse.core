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
 * A representation of the model object '<em><b>IWorkflow Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.IWorkflowNode#getWorkflow <em>Workflow</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IWorkflowNode#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IWorkflowNode#getInputs <em>Inputs</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IWorkflowNode#isIsStart <em>Is Start</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IWorkflowNode#isIsFinish <em>Is Finish</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.WorkflowPackage#getIWorkflowNode()
 * @model abstract="true"
 * @generated
 */
public interface IWorkflowNode extends IWorkflowElement
{
  /**
   * Returns the value of the '<em><b>Workflow</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IWorkflow#getNodes <em>Nodes</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Workflow</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Workflow</em>' container reference.
   * @see #setWorkflow(IWorkflow)
   * @see eu.geclipse.workflow.WorkflowPackage#getIWorkflowNode_Workflow()
   * @see eu.geclipse.workflow.IWorkflow#getNodes
   * @model opposite="nodes" required="true" transient="false"
   * @generated
   */
  IWorkflow getWorkflow();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.IWorkflowNode#getWorkflow <em>Workflow</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Workflow</em>' container reference.
   * @see #getWorkflow()
   * @generated
   */
  void setWorkflow(IWorkflow value);

  /**
   * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.workflow.IOutputPort}.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IOutputPort#getNode <em>Node</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Outputs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Outputs</em>' containment reference list.
   * @see eu.geclipse.workflow.WorkflowPackage#getIWorkflowNode_Outputs()
   * @see eu.geclipse.workflow.IOutputPort#getNode
   * @model opposite="node" containment="true" required="true"
   * @generated
   */
  EList<IOutputPort> getOutputs();

  /**
   * Returns the value of the '<em><b>Inputs</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.workflow.IInputPort}.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IInputPort#getNode <em>Node</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Inputs</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Inputs</em>' containment reference list.
   * @see eu.geclipse.workflow.WorkflowPackage#getIWorkflowNode_Inputs()
   * @see eu.geclipse.workflow.IInputPort#getNode
   * @model opposite="node" containment="true" required="true"
   * @generated
   */
  EList<IInputPort> getInputs();

  /**
   * Returns the value of the '<em><b>Is Start</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Start</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Is Start</em>' attribute.
   * @see #setIsStart(boolean)
   * @see eu.geclipse.workflow.WorkflowPackage#getIWorkflowNode_IsStart()
   * @model default="false" required="true"
   * @generated
   */
  boolean isIsStart();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.IWorkflowNode#isIsStart <em>Is Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Is Start</em>' attribute.
   * @see #isIsStart()
   * @generated
   */
  void setIsStart(boolean value);

  /**
   * Returns the value of the '<em><b>Is Finish</b></em>' attribute.
   * The default value is <code>"false"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Finish</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Is Finish</em>' attribute.
   * @see #setIsFinish(boolean)
   * @see eu.geclipse.workflow.WorkflowPackage#getIWorkflowNode_IsFinish()
   * @model default="false" required="true"
   * @generated
   */
  boolean isIsFinish();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.IWorkflowNode#isIsFinish <em>Is Finish</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Is Finish</em>' attribute.
   * @see #isIsFinish()
   * @generated
   */
  void setIsFinish(boolean value);

} // IWorkflowNode
