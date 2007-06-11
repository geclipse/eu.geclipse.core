/**
 * <copyright>
 * </copyright>
 *
 * $Id: OperatingSystemTypeImpl.java,v 1.2 2007/03/01 09:15:18 emstamou Exp $
 */
package eu.geclipse.jsdl.model.impl;

import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.OperatingSystemType;
import eu.geclipse.jsdl.model.OperatingSystemTypeType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operating System Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.impl.OperatingSystemTypeImpl#getOperatingSystemType <em>Operating System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.OperatingSystemTypeImpl#getOperatingSystemVersion <em>Operating System Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.OperatingSystemTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.OperatingSystemTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.OperatingSystemTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperatingSystemTypeImpl extends EObjectImpl implements OperatingSystemType 
{
  /**
   * The cached value of the '{@link #getOperatingSystemType() <em>Operating System Type</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOperatingSystemType()
   * @generated
   * @ordered
   */
	protected OperatingSystemTypeType operatingSystemType = null;

  /**
   * The default value of the '{@link #getOperatingSystemVersion() <em>Operating System Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOperatingSystemVersion()
   * @generated
   * @ordered
   */
	protected static final String OPERATING_SYSTEM_VERSION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOperatingSystemVersion() <em>Operating System Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOperatingSystemVersion()
   * @generated
   * @ordered
   */
	protected String operatingSystemVersion = OPERATING_SYSTEM_VERSION_EDEFAULT;

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
	protected OperatingSystemTypeImpl()
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
    return JsdlPackage.Literals.OPERATING_SYSTEM_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemTypeType getOperatingSystemType()
  {
    return operatingSystemType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOperatingSystemType(OperatingSystemTypeType newOperatingSystemType, NotificationChain msgs)
  {
    OperatingSystemTypeType oldOperatingSystemType = operatingSystemType;
    operatingSystemType = newOperatingSystemType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE, oldOperatingSystemType, newOperatingSystemType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystemType(OperatingSystemTypeType newOperatingSystemType)
  {
    if (newOperatingSystemType != operatingSystemType)
    {
      NotificationChain msgs = null;
      if (operatingSystemType != null)
        msgs = ((InternalEObject)operatingSystemType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE, null, msgs);
      if (newOperatingSystemType != null)
        msgs = ((InternalEObject)newOperatingSystemType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE, null, msgs);
      msgs = basicSetOperatingSystemType(newOperatingSystemType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE, newOperatingSystemType, newOperatingSystemType));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getOperatingSystemVersion()
  {
    return operatingSystemVersion;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystemVersion(String newOperatingSystemVersion)
  {
    String oldOperatingSystemVersion = operatingSystemVersion;
    operatingSystemVersion = newOperatingSystemVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION, oldOperatingSystemVersion, operatingSystemVersion));
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
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.OPERATING_SYSTEM_TYPE__DESCRIPTION, oldDescription, description));
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
      any = new BasicFeatureMap(this, JsdlPackage.OPERATING_SYSTEM_TYPE__ANY);
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
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE);
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
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE:
        return basicSetOperatingSystemType(null, msgs);
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE:
        return getOperatingSystemType();
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION:
        return getOperatingSystemVersion();
      case JsdlPackage.OPERATING_SYSTEM_TYPE__DESCRIPTION:
        return getDescription();
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE:
        setOperatingSystemType((OperatingSystemTypeType)newValue);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION:
        setOperatingSystemVersion((String)newValue);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE:
        setOperatingSystemType((OperatingSystemTypeType)null);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION:
        setOperatingSystemVersion(OPERATING_SYSTEM_VERSION_EDEFAULT);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE:
        return operatingSystemType != null;
      case JsdlPackage.OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION:
        return OPERATING_SYSTEM_VERSION_EDEFAULT == null ? operatingSystemVersion != null : !OPERATING_SYSTEM_VERSION_EDEFAULT.equals(operatingSystemVersion);
      case JsdlPackage.OPERATING_SYSTEM_TYPE__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (operatingSystemVersion: ");
    result.append(operatingSystemVersion);
    result.append(", description: ");
    result.append(description);
    result.append(", any: ");
    result.append(any);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //OperatingSystemTypeImpl