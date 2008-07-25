/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *    Mathias St�mpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.impl;

import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.JsdlPackage;

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
 * An implementation of the model object '<em><b>Application Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.impl.ApplicationTypeImpl#getApplicationName <em>Application Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.ApplicationTypeImpl#getApplicationVersion <em>Application Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.ApplicationTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.ApplicationTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.ApplicationTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @deprecated This class is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base.impl
 */
public class ApplicationTypeImpl extends EObjectImpl implements ApplicationType 
{
  /**
   * The default value of the '{@link #getApplicationName() <em>Application Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getApplicationName()
   * @generated
   * @ordered
   */
	protected static final String APPLICATION_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getApplicationName() <em>Application Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getApplicationName()
   * @generated
   * @ordered
   */
	protected String applicationName = APPLICATION_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getApplicationVersion() <em>Application Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getApplicationVersion()
   * @generated
   * @ordered
   */
	protected static final String APPLICATION_VERSION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getApplicationVersion() <em>Application Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getApplicationVersion()
   * @generated
   * @ordered
   */
	protected String applicationVersion = APPLICATION_VERSION_EDEFAULT;

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
	protected ApplicationTypeImpl()
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
    return JsdlPackage.Literals.APPLICATION_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getApplicationName()
  {
    return applicationName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setApplicationName(String newApplicationName)
  {
    String oldApplicationName = applicationName;
    applicationName = newApplicationName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME, oldApplicationName, applicationName));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getApplicationVersion()
  {
    return applicationVersion;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setApplicationVersion(String newApplicationVersion)
  {
    String oldApplicationVersion = applicationVersion;
    applicationVersion = newApplicationVersion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION, oldApplicationVersion, applicationVersion));
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
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.APPLICATION_TYPE__DESCRIPTION, oldDescription, description));
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
      any = new BasicFeatureMap(this, JsdlPackage.APPLICATION_TYPE__ANY);
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
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.APPLICATION_TYPE__ANY_ATTRIBUTE);
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
      case JsdlPackage.APPLICATION_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME:
        return getApplicationName();
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION:
        return getApplicationVersion();
      case JsdlPackage.APPLICATION_TYPE__DESCRIPTION:
        return getDescription();
      case JsdlPackage.APPLICATION_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME:
        setApplicationName((String)newValue);
        return;
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION:
        setApplicationVersion((String)newValue);
        return;
      case JsdlPackage.APPLICATION_TYPE__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case JsdlPackage.APPLICATION_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME:
        setApplicationName(APPLICATION_NAME_EDEFAULT);
        return;
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION:
        setApplicationVersion(APPLICATION_VERSION_EDEFAULT);
        return;
      case JsdlPackage.APPLICATION_TYPE__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case JsdlPackage.APPLICATION_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME:
        return APPLICATION_NAME_EDEFAULT == null ? applicationName != null : !APPLICATION_NAME_EDEFAULT.equals(applicationName);
      case JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION:
        return APPLICATION_VERSION_EDEFAULT == null ? applicationVersion != null : !APPLICATION_VERSION_EDEFAULT.equals(applicationVersion);
      case JsdlPackage.APPLICATION_TYPE__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
      case JsdlPackage.APPLICATION_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.APPLICATION_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (applicationName: ");
    result.append(applicationName);
    result.append(", applicationVersion: ");
    result.append(applicationVersion);
    result.append(", description: ");
    result.append(description);
    result.append(", any: ");
    result.append(any);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //ApplicationTypeImpl