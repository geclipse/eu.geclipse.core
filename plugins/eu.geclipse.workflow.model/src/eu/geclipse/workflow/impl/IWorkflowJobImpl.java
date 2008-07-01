/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.WorkflowPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IWorkflow Job</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowJobImpl#getJobDescription <em>Job Description</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowJobImpl#getJobDescriptionFileName <em>Job Description File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IWorkflowJobImpl extends IWorkflowNodeImpl implements IWorkflowJob
{
  /**
   * The default value of the '{@link #getJobDescription() <em>Job Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobDescription()
   * @generated
   * @ordered
   */
  protected static final String JOB_DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getJobDescription() <em>Job Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobDescription()
   * @generated
   * @ordered
   */
  protected String jobDescription = JOB_DESCRIPTION_EDEFAULT;

  /**
   * The default value of the '{@link #getJobDescriptionFileName() <em>Job Description File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobDescriptionFileName()
   * @generated
   * @ordered
   */
  protected static final String JOB_DESCRIPTION_FILE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getJobDescriptionFileName() <em>Job Description File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobDescriptionFileName()
   * @generated
   * @ordered
   */
  protected String jobDescriptionFileName = JOB_DESCRIPTION_FILE_NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IWorkflowJobImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return WorkflowPackage.Literals.IWORKFLOW_JOB;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getJobDescription()
  {
    return jobDescription;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJobDescription(String newJobDescription)
  {
    String oldJobDescription = jobDescription;
    jobDescription = newJobDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION, oldJobDescription, jobDescription));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getJobDescriptionFileName()
  {
    return jobDescriptionFileName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJobDescriptionFileName(String newJobDescriptionFileName)
  {
    String oldJobDescriptionFileName = jobDescriptionFileName;
    jobDescriptionFileName = newJobDescriptionFileName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME, oldJobDescriptionFileName, jobDescriptionFileName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        return getJobDescription();
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        return getJobDescriptionFileName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        setJobDescription((String)newValue);
        return;
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        setJobDescriptionFileName((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        setJobDescription(JOB_DESCRIPTION_EDEFAULT);
        return;
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        setJobDescriptionFileName(JOB_DESCRIPTION_FILE_NAME_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        return JOB_DESCRIPTION_EDEFAULT == null ? jobDescription != null : !JOB_DESCRIPTION_EDEFAULT.equals(jobDescription);
      case WorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        return JOB_DESCRIPTION_FILE_NAME_EDEFAULT == null ? jobDescriptionFileName != null : !JOB_DESCRIPTION_FILE_NAME_EDEFAULT.equals(jobDescriptionFileName);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (jobDescription: "); //$NON-NLS-1$
    result.append(jobDescription);
    result.append(", jobDescriptionFileName: "); //$NON-NLS-1$
    result.append(jobDescriptionFileName);
    result.append(')');
    return result.toString();
  }

} //IWorkflowJobImpl
