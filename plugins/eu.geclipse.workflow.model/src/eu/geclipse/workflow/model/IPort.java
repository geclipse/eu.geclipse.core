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

import eu.geclipse.workflow.model.IWorkflowElement;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IPort</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.IPort#getFileName <em>File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.workflow.WorkflowPackage#getIPort()
 * @model abstract="true"
 * @generated
 */
public interface IPort extends IWorkflowElement
{
  /**
   * Returns the value of the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File Name</em>' attribute.
   * @see #setFileName(String)
   * @see eu.geclipse.workflow.WorkflowPackage#getIPort_FileName()
   * @model
   * @generated
   */
  String getFileName();

  /**
   * Sets the value of the '{@link eu.geclipse.workflow.IPort#getFileName <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Name</em>' attribute.
   * @see #getFileName()
   * @generated
   */
  void setFileName(String value);

} // IPort
