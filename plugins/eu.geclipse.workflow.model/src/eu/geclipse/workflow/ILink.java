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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ILink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.ILink#getWorkflow <em>Workflow</em>}</li>
 *   <li>{@link eu.geclipse.workflow.ILink#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.workflow.ILink#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.IWorkflowPackage#getILink()
 * @model
 * @generated
 */
public interface ILink extends IWorkflowElement
{
  /**
   * Returns the value of the '<em><b>Workflow</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IWorkflow#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Workflow</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Workflow</em>' container reference.
   * @see #setWorkflow(IWorkflow)
   * @see eu.geclipse.workflow.IWorkflowPackage#getILink_Workflow()
   * @see eu.geclipse.workflow.IWorkflow#getLinks
   * @model opposite="links" required="true"
   * @generated
   */
  IWorkflow getWorkflow();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.ILink#getWorkflow <em>Workflow</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Workflow</em>' container reference.
   * @see #getWorkflow()
   * @generated
   */
  void setWorkflow(IWorkflow value);

  /**
   * Returns the value of the '<em><b>Target</b></em>' reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IInputPort#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' reference.
   * @see #setTarget(IInputPort)
   * @see eu.geclipse.workflow.IWorkflowPackage#getILink_Target()
   * @see eu.geclipse.workflow.IInputPort#getLinks
   * @model opposite="links" required="true"
   * @generated
   */
  IInputPort getTarget();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.ILink#getTarget <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(IInputPort value);

  /**
   * Returns the value of the '<em><b>Source</b></em>' reference.
   * It is bidirectional and its opposite is '{@link eu.geclipse.workflow.IOutputPort#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Source</em>' reference.
   * @see #setSource(IOutputPort)
   * @see eu.geclipse.workflow.IWorkflowPackage#getILink_Source()
   * @see eu.geclipse.workflow.IOutputPort#getLinks
   * @model opposite="links" required="true"
   * @generated
   */
  IOutputPort getSource();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.ILink#getSource <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Source</em>' reference.
   * @see #getSource()
   * @generated
   */
  void setSource(IOutputPort value);

} // ILink
