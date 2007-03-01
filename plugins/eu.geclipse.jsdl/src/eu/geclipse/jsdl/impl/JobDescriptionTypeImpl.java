/**
 * <copyright>
 * </copyright>
 *
 * $Id: JobDescriptionTypeImpl.java,v 1.1 2007/01/25 15:26:30 emstamou Exp $
 */
package eu.geclipse.jsdl.impl;

import eu.geclipse.jsdl.ApplicationType;
import eu.geclipse.jsdl.DataStagingType;
import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JobIdentificationType;
import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.ResourcesType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Job Description Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl#getJobIdentification <em>Job Identification</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl#getDataStaging <em>Data Staging</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JobDescriptionTypeImpl extends EObjectImpl implements JobDescriptionType {
	/**
	 * The cached value of the '{@link #getJobIdentification() <em>Job Identification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJobIdentification()
	 * @generated
	 * @ordered
	 */
	protected JobIdentificationType jobIdentification = null;

	/**
	 * The cached value of the '{@link #getApplication() <em>Application</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplication()
	 * @generated
	 * @ordered
	 */
	protected ApplicationType application = null;

	/**
	 * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
	protected ResourcesType resources = null;

	/**
	 * The cached value of the '{@link #getDataStaging() <em>Data Staging</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataStaging()
	 * @generated
	 * @ordered
	 */
	protected EList dataStaging = null;

	/**
	 * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAny()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap any = null;

	/**
	 * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnyAttribute()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap anyAttribute = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JobDescriptionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return JsdlPackage.Literals.JOB_DESCRIPTION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JobIdentificationType getJobIdentification() {
		return jobIdentification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJobIdentification(JobIdentificationType newJobIdentification, NotificationChain msgs) {
		JobIdentificationType oldJobIdentification = jobIdentification;
		jobIdentification = newJobIdentification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION, oldJobIdentification, newJobIdentification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobIdentification(JobIdentificationType newJobIdentification) {
		if (newJobIdentification != jobIdentification) {
			NotificationChain msgs = null;
			if (jobIdentification != null)
				msgs = ((InternalEObject)jobIdentification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION, null, msgs);
			if (newJobIdentification != null)
				msgs = ((InternalEObject)newJobIdentification).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION, null, msgs);
			msgs = basicSetJobIdentification(newJobIdentification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION, newJobIdentification, newJobIdentification));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationType getApplication() {
		return application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetApplication(ApplicationType newApplication, NotificationChain msgs) {
		ApplicationType oldApplication = application;
		application = newApplication;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION, oldApplication, newApplication);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setApplication(ApplicationType newApplication) {
		if (newApplication != application) {
			NotificationChain msgs = null;
			if (application != null)
				msgs = ((InternalEObject)application).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION, null, msgs);
			if (newApplication != null)
				msgs = ((InternalEObject)newApplication).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION, null, msgs);
			msgs = basicSetApplication(newApplication, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION, newApplication, newApplication));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcesType getResources() {
		return resources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResources(ResourcesType newResources, NotificationChain msgs) {
		ResourcesType oldResources = resources;
		resources = newResources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES, oldResources, newResources);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResources(ResourcesType newResources) {
		if (newResources != resources) {
			NotificationChain msgs = null;
			if (resources != null)
				msgs = ((InternalEObject)resources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES, null, msgs);
			if (newResources != null)
				msgs = ((InternalEObject)newResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES, null, msgs);
			msgs = basicSetResources(newResources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES, newResources, newResources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDataStaging() {
		if (dataStaging == null) {
			dataStaging = new EObjectContainmentEList(DataStagingType.class, this, JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING);
		}
		return dataStaging;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAny() {
		if (any == null) {
			any = new BasicFeatureMap(this, JsdlPackage.JOB_DESCRIPTION_TYPE__ANY);
		}
		return any;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE);
		}
		return anyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION:
				return basicSetJobIdentification(null, msgs);
			case JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION:
				return basicSetApplication(null, msgs);
			case JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES:
				return basicSetResources(null, msgs);
			case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:
				return ((InternalEList)getDataStaging()).basicRemove(otherEnd, msgs);
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY:
				return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE:
				return ((InternalEList)getAnyAttribute()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION:
				return getJobIdentification();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION:
				return getApplication();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES:
				return getResources();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:
				return getDataStaging();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY:
				if (coreType) return getAny();
				return ((FeatureMap.Internal)getAny()).getWrapper();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE:
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION:
				setJobIdentification((JobIdentificationType)newValue);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION:
				setApplication((ApplicationType)newValue);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES:
				setResources((ResourcesType)newValue);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:
				getDataStaging().clear();
				getDataStaging().addAll((Collection)newValue);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY:
				((FeatureMap.Internal)getAny()).set(newValue);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE:
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION:
				setJobIdentification((JobIdentificationType)null);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION:
				setApplication((ApplicationType)null);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES:
				setResources((ResourcesType)null);
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:
				getDataStaging().clear();
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY:
				getAny().clear();
				return;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE:
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION:
				return jobIdentification != null;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION:
				return application != null;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES:
				return resources != null;
			case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:
				return dataStaging != null && !dataStaging.isEmpty();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY:
				return any != null && !any.isEmpty();
			case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE:
				return anyAttribute != null && !anyAttribute.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (any: ");
		result.append(any);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //JobDescriptionTypeImpl