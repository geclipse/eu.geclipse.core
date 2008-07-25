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

import eu.geclipse.jsdl.model.CPUArchitectureType;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.ProcessorArchitectureEnumeration;

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
 * An implementation of the model object '<em><b>CPU Architecture Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.impl.CPUArchitectureTypeImpl#getCPUArchitectureName <em>CPU Architecture Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.CPUArchitectureTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.CPUArchitectureTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @deprecated This class is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base.impl
 */
public class CPUArchitectureTypeImpl extends EObjectImpl implements CPUArchitectureType 
{
  /**
   * The default value of the '{@link #getCPUArchitectureName() <em>CPU Architecture Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCPUArchitectureName()
   * @generated
   * @ordered
   */
	protected static final ProcessorArchitectureEnumeration CPU_ARCHITECTURE_NAME_EDEFAULT = ProcessorArchitectureEnumeration.SPARC_LITERAL;

  /**
   * The cached value of the '{@link #getCPUArchitectureName() <em>CPU Architecture Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCPUArchitectureName()
   * @generated
   * @ordered
   */
	protected ProcessorArchitectureEnumeration cPUArchitectureName = CPU_ARCHITECTURE_NAME_EDEFAULT;

  /**
   * This is true if the CPU Architecture Name attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean cPUArchitectureNameESet;

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
	protected CPUArchitectureTypeImpl()
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
    return JsdlPackage.Literals.CPU_ARCHITECTURE_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ProcessorArchitectureEnumeration getCPUArchitectureName()
  {
    return cPUArchitectureName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCPUArchitectureName(ProcessorArchitectureEnumeration newCPUArchitectureName)
  {
    ProcessorArchitectureEnumeration oldCPUArchitectureName = cPUArchitectureName;
    cPUArchitectureName = newCPUArchitectureName == null ? CPU_ARCHITECTURE_NAME_EDEFAULT : newCPUArchitectureName;
    boolean oldCPUArchitectureNameESet = cPUArchitectureNameESet;
    cPUArchitectureNameESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME, oldCPUArchitectureName, cPUArchitectureName, !oldCPUArchitectureNameESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void unsetCPUArchitectureName()
  {
    ProcessorArchitectureEnumeration oldCPUArchitectureName = cPUArchitectureName;
    boolean oldCPUArchitectureNameESet = cPUArchitectureNameESet;
    cPUArchitectureName = CPU_ARCHITECTURE_NAME_EDEFAULT;
    cPUArchitectureNameESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME, oldCPUArchitectureName, CPU_ARCHITECTURE_NAME_EDEFAULT, oldCPUArchitectureNameESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isSetCPUArchitectureName()
  {
    return cPUArchitectureNameESet;
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
      any = new BasicFeatureMap(this, JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY);
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
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE);
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
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME:
        return getCPUArchitectureName();
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME:
        setCPUArchitectureName((ProcessorArchitectureEnumeration)newValue);
        return;
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME:
        unsetCPUArchitectureName();
        return;
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME:
        return isSetCPUArchitectureName();
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (cPUArchitectureName: ");
    if (cPUArchitectureNameESet) result.append(cPUArchitectureName); else result.append("<unset>");
    result.append(", any: ");
    result.append(any);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //CPUArchitectureTypeImpl