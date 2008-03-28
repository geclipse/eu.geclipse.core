/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.model.qdl.QdlPackage;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Allowed Virtual Organizations Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.AllowedVirtualOrganizationsTypeImpl#getVOName <em>VO Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AllowedVirtualOrganizationsTypeImpl extends EObjectImpl implements AllowedVirtualOrganizationsType
{
  /**
   * The cached value of the '{@link #getVOName() <em>VO Name</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVOName()
   * @generated
   * @ordered
   */
  protected EList<String> vOName;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AllowedVirtualOrganizationsTypeImpl()
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
    return QdlPackage.Literals.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getVOName()
  {
    if (vOName == null)
    {
      vOName = new EDataTypeEList<String>(String.class, this, QdlPackage.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME);
    }
    return vOName;
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
      case QdlPackage.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME:
        return getVOName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case QdlPackage.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME:
        getVOName().clear();
        getVOName().addAll((Collection<? extends String>)newValue);
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
      case QdlPackage.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME:
        getVOName().clear();
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
      case QdlPackage.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME:
        return vOName != null && !vOName.isEmpty();
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
    result.append(" (vOName: ");
    result.append(vOName);
    result.append(')');
    return result.toString();
  }

} //AllowedVirtualOrganizationsTypeImpl
