/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.impl;

import eu.geclipse.jsdl.model.base.JobIdentificationType;
import eu.geclipse.jsdl.model.base.JsdlPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Job Identification Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.JobIdentificationTypeImpl#getJobName <em>Job Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.JobIdentificationTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.JobIdentificationTypeImpl#getJobAnnotation <em>Job Annotation</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.JobIdentificationTypeImpl#getJobProject <em>Job Project</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.JobIdentificationTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.JobIdentificationTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JobIdentificationTypeImpl extends EObjectImpl implements JobIdentificationType
{
  /**
   * The default value of the '{@link #getJobName() <em>Job Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobName()
   * @generated
   * @ordered
   */
  protected static final String JOB_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getJobName() <em>Job Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobName()
   * @generated
   * @ordered
   */
  protected String jobName = JOB_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * The cached value of the '{@link #getJobAnnotation() <em>Job Annotation</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobAnnotation()
   * @generated
   * @ordered
   */
  protected EList jobAnnotation;

  /**
   * The cached value of the '{@link #getJobProject() <em>Job Project</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobProject()
   * @generated
   * @ordered
   */
  protected EList jobProject;

  /**
   * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAny()
   * @generated
   * @ordered
   */
  protected FeatureMap any;

  /**
   * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnyAttribute()
   * @generated
   * @ordered
   */
  protected FeatureMap anyAttribute;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JobIdentificationTypeImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EClass eStaticClass()
  {
    return JsdlPackage.Literals.JOB_IDENTIFICATION_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getJobName()
  {
    return jobName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJobName(String newJobName)
  {
    String oldJobName = jobName;
    jobName = newJobName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME, oldJobName, jobName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription)
  {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getJobAnnotation()
  {
    if (jobAnnotation == null)
    {
      jobAnnotation = new EDataTypeEList(String.class, this, JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION);
    }
    return jobAnnotation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getJobProject()
  {
    if (jobProject == null)
    {
      jobProject = new EDataTypeEList(String.class, this, JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT);
    }
    return jobProject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getAny()
  {
    if (any == null)
    {
      any = new BasicFeatureMap(this, JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY);
    }
    return any;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getAnyAttribute()
  {
    if (anyAttribute == null)
    {
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE);
    }
    return anyAttribute;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE:
        return ((InternalEList)getAnyAttribute()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME:
        return getJobName();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION:
        return getDescription();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION:
        return getJobAnnotation();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT:
        return getJobProject();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE:
        if (coreType) return getAnyAttribute();
        return ((FeatureMap.Internal)getAnyAttribute()).getWrapper();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME:
        setJobName((String)newValue);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION:
        getJobAnnotation().clear();
        getJobAnnotation().addAll((Collection)newValue);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT:
        getJobProject().clear();
        getJobProject().addAll((Collection)newValue);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE:
        ((FeatureMap.Internal)getAnyAttribute()).set(newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME:
        setJobName(JOB_NAME_EDEFAULT);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION:
        getJobAnnotation().clear();
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT:
        getJobProject().clear();
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE:
        getAnyAttribute().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME:
        return JOB_NAME_EDEFAULT == null ? jobName != null : !JOB_NAME_EDEFAULT.equals(jobName);
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION:
        return jobAnnotation != null && !jobAnnotation.isEmpty();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT:
        return jobProject != null && !jobProject.isEmpty();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE:
        return anyAttribute != null && !anyAttribute.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (jobName: ");
    result.append(jobName);
    result.append(", description: ");
    result.append(description);
    result.append(", jobAnnotation: ");
    result.append(jobAnnotation);
    result.append(", jobProject: ");
    result.append(jobProject);
    result.append(", any: ");
    result.append(any);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //JobIdentificationTypeImpl
