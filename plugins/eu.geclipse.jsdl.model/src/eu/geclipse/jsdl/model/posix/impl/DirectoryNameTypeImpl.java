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

package eu.geclipse.jsdl.model.posix.impl;

import eu.geclipse.jsdl.model.posix.DirectoryNameType;
import eu.geclipse.jsdl.model.posix.PosixPackage;

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
 * An implementation of the model object '<em><b>Directory Name Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl#getFilesystemName <em>Filesystem Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DirectoryNameTypeImpl extends EObjectImpl implements DirectoryNameType 
{
  /**
   * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
	protected static final String VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
	protected String value = VALUE_EDEFAULT;

  /**
   * The default value of the '{@link #getFilesystemName() <em>Filesystem Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFilesystemName()
   * @generated
   * @ordered
   */
	protected static final String FILESYSTEM_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFilesystemName() <em>Filesystem Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFilesystemName()
   * @generated
   * @ordered
   */
	protected String filesystemName = FILESYSTEM_NAME_EDEFAULT;

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
	protected DirectoryNameTypeImpl()
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
    return PosixPackage.Literals.DIRECTORY_NAME_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setValue(String newValue)
  {
    String oldValue = value;
    value = newValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.DIRECTORY_NAME_TYPE__VALUE, oldValue, value));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getFilesystemName()
  {
    return filesystemName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFilesystemName(String newFilesystemName)
  {
    String oldFilesystemName = filesystemName;
    filesystemName = newFilesystemName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.DIRECTORY_NAME_TYPE__FILESYSTEM_NAME, oldFilesystemName, filesystemName));
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
      anyAttribute = new BasicFeatureMap(this, PosixPackage.DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE);
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
      case PosixPackage.DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.DIRECTORY_NAME_TYPE__VALUE:
        return getValue();
      case PosixPackage.DIRECTORY_NAME_TYPE__FILESYSTEM_NAME:
        return getFilesystemName();
      case PosixPackage.DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.DIRECTORY_NAME_TYPE__VALUE:
        setValue((String)newValue);
        return;
      case PosixPackage.DIRECTORY_NAME_TYPE__FILESYSTEM_NAME:
        setFilesystemName((String)newValue);
        return;
      case PosixPackage.DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.DIRECTORY_NAME_TYPE__VALUE:
        setValue(VALUE_EDEFAULT);
        return;
      case PosixPackage.DIRECTORY_NAME_TYPE__FILESYSTEM_NAME:
        setFilesystemName(FILESYSTEM_NAME_EDEFAULT);
        return;
      case PosixPackage.DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.DIRECTORY_NAME_TYPE__VALUE:
        return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
      case PosixPackage.DIRECTORY_NAME_TYPE__FILESYSTEM_NAME:
        return FILESYSTEM_NAME_EDEFAULT == null ? filesystemName != null : !FILESYSTEM_NAME_EDEFAULT.equals(filesystemName);
      case PosixPackage.DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (value: ");
    result.append(value);
    result.append(", filesystemName: ");
    result.append(filesystemName);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //DirectoryNameTypeImpl