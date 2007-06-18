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
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.IWorkflowPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * An implementation of the model object '<em><b>IWorkflowJob</b></em>'.
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowJobImpl#getJobDescription <em>Job Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkflowJobImpl extends WorkflowNodeImpl implements IWorkflowJob
{
  /**
   * The default value of the '{@link #getJobDescription() <em>Job Description</em>}' attribute.
   * @see #getJobDescription()
   * @generated
   * @ordered
   */
  protected static final String JOB_DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getJobDescription() <em>Job Description</em>}' attribute.
   * @see #getJobDescription()
   * @generated
   * @ordered
   */
  protected String jobDescription = JOB_DESCRIPTION_EDEFAULT;

  /**
   * @generated
   */
  protected WorkflowJobImpl()
  {
    super();
  }

  /**
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return IWorkflowPackage.Literals.IWORKFLOW_JOB;
  }

  /**
   * @generated
   */
  public String getJobDescription()
  {
    return jobDescription;
  }

  /**
   * @generated
   */
  public void setJobDescription(String newJobDescription)
  {
    String oldJobDescription = jobDescription;
    jobDescription = newJobDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION, oldJobDescription, jobDescription));
  }

  /**
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        return getJobDescription();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        setJobDescription((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        setJobDescription(JOB_DESCRIPTION_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        return JOB_DESCRIPTION_EDEFAULT == null ? jobDescription != null : !JOB_DESCRIPTION_EDEFAULT.equals(jobDescription);
    }
    return super.eIsSet(featureID);
  }

  /**
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (jobDescription: ");
    result.append(jobDescription);
    result.append(')');
    return result.toString();
  }

}