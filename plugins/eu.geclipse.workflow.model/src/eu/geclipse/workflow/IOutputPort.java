/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Ashish Thandavan - initial API and implementation
 *****************************************************************************/
package eu.geclipse.workflow;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IOutput Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.IOutputPort#getNode <em>Node</em>}</li>
 *   <li>{@link eu.geclipse.workflow.IOutputPort#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.IWorkflowPackage#getIOutputPort()
 * @model
 * @generated
 */
public interface IOutputPort extends IPort
{
  /**
   * Returns the value of the '<em><b>Node</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IWorkflowNode#getOutputs <em>Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Node</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Node</em>' container reference.
   * @see #setNode(IWorkflowNode)
   * @see eu.geclipse.workflow.IWorkflowPackage#getIOutputPort_Node()
   * @see eu.geclipse.workflow.IWorkflowNode#getOutputs
   * @model opposite="outputs" required="true"
   * @generated
   */
  IWorkflowNode getNode();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.IOutputPort#getNode <em>Node</em>}' container reference.
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
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.ILink#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Links</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Links</em>' reference list.
   * @see eu.geclipse.workflow.IWorkflowPackage#getIOutputPort_Links()
   * @see eu.geclipse.workflow.ILink#getSource
   * @model type="eu.geclipse.workflow.ILink" opposite="source"
   * @generated
   */
  EList<ILink> getLinks();

} // IOutputPort
