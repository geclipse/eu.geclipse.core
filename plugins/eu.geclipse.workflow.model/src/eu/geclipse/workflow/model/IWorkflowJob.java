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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IWorkflow Job</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.model.IWorkflowJob#getJobDescription <em>Job Description</em>}</li>
 *   <li>{@link eu.geclipse.workflow.model.IWorkflowJob#getJobDescriptionFileName <em>Job Description File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.model.IWorkflowPackage#getIWorkflowJob()
 * @model
 * @generated
 */
public interface IWorkflowJob extends IWorkflowNode
{
  /**
   * Returns the value of the '<em><b>Job Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Job Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Job Description</em>' attribute.
   * @see #setJobDescription(String)
   * @see eu.geclipse.workflow.model.IWorkflowPackage#getIWorkflowJob_JobDescription()
   * @model required="true"
   * @generated
   */
  String getJobDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.model.IWorkflowJob#getJobDescription <em>Job Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Description</em>' attribute.
   * @see #getJobDescription()
   * @generated
   */
  void setJobDescription(String value);

  /**
   * Returns the value of the '<em><b>Job Description File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Job Description File Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Job Description File Name</em>' attribute.
   * @see #setJobDescriptionFileName(String)
   * @see eu.geclipse.workflow.model.IWorkflowPackage#getIWorkflowJob_JobDescriptionFileName()
   * @model required="true"
   * @generated
   */
  String getJobDescriptionFileName();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.model.IWorkflowJob#getJobDescriptionFileName <em>Job Description File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Description File Name</em>' attribute.
   * @see #getJobDescriptionFileName()
   * @generated
   */
  void setJobDescriptionFileName(String value);

} // IWorkflowJob
