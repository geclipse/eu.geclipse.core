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
package eu.geclipse.workflow.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IOutputPort</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.model.IOutputPort#getNode <em>Node</em>}</li>
 *   <li>{@link eu.geclipse.workflow.model.IOutputPort#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.WorkflowPackage#getIOutputPort()
 * @model
 * @generated
 */
public interface IOutputPort extends IPort
{
  /**
   * Returns the value of the '<em><b>Node</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.model.IWorkflowNode#getOutputs <em>Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Node</em>' container reference.
   * @see #setNode(IWorkflowNode)
   * @see eu.geclipse.workflow.WorkflowPackage#getIOutputPort_Node()
   * @see eu.geclipse.workflow.model.IWorkflowNode#getOutputs
   * @model opposite="outputs" required="true" transient="false"
   * @generated
   */
  IWorkflowNode getNode();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.model.IOutputPort#getNode <em>Node</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Node</em>' container reference.
   * @see #getNode()
   * @generated
   */
  void setNode(IWorkflowNode value);

  /**
   * Returns the value of the '<em><b>Links</b></em>' reference list.
   * The list contents are of type {@link eu.geclipse.workflow.model.ILink}.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.model.ILink#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Links</em>' reference list.
   * @see eu.geclipse.workflow.WorkflowPackage#getIOutputPort_Links()
   * @see eu.geclipse.workflow.model.ILink#getSource
   * @model opposite="source"
   * @generated
   */
  EList<ILink> getLinks();

} // IOutputPort
