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
 * A representation of the model object '<em><b>IInput Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.IInputPort#getNode <em>Node</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IInputPort#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.IWorkflowPackage#getIInputPort()
 * @model
 * @generated
 */
public interface IInputPort extends IPort
{
  /**
   * Returns the value of the '<em><b>Node</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IWorkflowNode#getInputs <em>Inputs</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Node</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Node</em>' container reference.
   * @see #setNode(IWorkflowNode)
   * @see eu.geclipse.workflow.IWorkflowPackage#getIInputPort_Node()
   * @see eu.geclipse.workflow.IWorkflowNode#getInputs
   * @model opposite="inputs" required="true"
   * @generated
   */
  IWorkflowNode getNode();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.IInputPort#getNode <em>Node</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Node</em>' container reference.
   * @see #getNode()
   * @generated
   */
  void setNode(IWorkflowNode value);

  /**
   * Returns the value of the '<em><b>Links</b></em>' reference list.
   * The list contents are of type {@link eu.geclipse.workflow.ILink}.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.ILink#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Links</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Links</em>' reference list.
   * @see eu.geclipse.workflow.IWorkflowPackage#getIInputPort_Links()
   * @see eu.geclipse.workflow.ILink#getTarget
   * @model type="eu.geclipse.workflow.ILink" opposite="target"
   * @generated
   */
  EList<ILink> getLinks();

} // IInputPort
