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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.IntegerBoundaryType;
import eu.geclipse.batch.model.qdl.IntegerRangeType;
import eu.geclipse.batch.model.qdl.QdlPackage;

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
 * An implementation of the model object '<em><b>Integer Range Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl#getIntegerLowerBound <em>Integer Lower Bound</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl#getIntegerUpperBound <em>Integer Upper Bound</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IntegerRangeTypeImpl extends EObjectImpl implements IntegerRangeType
{
  /**
   * The cached value of the '{@link #getIntegerLowerBound() <em>Integer Lower Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIntegerLowerBound()
   * @generated
   * @ordered
   */
  protected IntegerBoundaryType integerLowerBound;

  /**
   * The cached value of the '{@link #getIntegerUpperBound() <em>Integer Upper Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getIntegerUpperBound()
   * @generated
   * @ordered
   */
  protected IntegerBoundaryType integerUpperBound;

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
  protected IntegerRangeTypeImpl()
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
    return QdlPackage.Literals.INTEGER_RANGE_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerBoundaryType getIntegerLowerBound()
  {
    return this.integerLowerBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIntegerLowerBound(IntegerBoundaryType newIntegerLowerBound, NotificationChain msgs)
  {
    IntegerBoundaryType oldIntegerLowerBound = this.integerLowerBound;
    this.integerLowerBound = newIntegerLowerBound;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND, oldIntegerLowerBound, newIntegerLowerBound);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIntegerLowerBound(IntegerBoundaryType newIntegerLowerBound)
  {
    if (newIntegerLowerBound != this.integerLowerBound)
    {
      NotificationChain msgs = null;
      if (this.integerLowerBound != null)
        msgs = ((InternalEObject)this.integerLowerBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND, null, msgs);
      if (newIntegerLowerBound != null)
        msgs = ((InternalEObject)newIntegerLowerBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND, null, msgs);
      msgs = basicSetIntegerLowerBound(newIntegerLowerBound, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND, newIntegerLowerBound, newIntegerLowerBound));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerBoundaryType getIntegerUpperBound()
  {
    return this.integerUpperBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIntegerUpperBound(IntegerBoundaryType newIntegerUpperBound, NotificationChain msgs)
  {
    IntegerBoundaryType oldIntegerUpperBound = this.integerUpperBound;
    this.integerUpperBound = newIntegerUpperBound;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND, oldIntegerUpperBound, newIntegerUpperBound);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIntegerUpperBound(IntegerBoundaryType newIntegerUpperBound)
  {
    if (newIntegerUpperBound != this.integerUpperBound)
    {
      NotificationChain msgs = null;
      if (this.integerUpperBound != null)
        msgs = ((InternalEObject)this.integerUpperBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND, null, msgs);
      if (newIntegerUpperBound != null)
        msgs = ((InternalEObject)newIntegerUpperBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND, null, msgs);
      msgs = basicSetIntegerUpperBound(newIntegerUpperBound, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND, newIntegerUpperBound, newIntegerUpperBound));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getAnyAttribute()
  {
    if (this.anyAttribute == null)
    {
      this.anyAttribute = new BasicFeatureMap(this, QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE);
    }
    return this.anyAttribute;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND:
        return basicSetIntegerLowerBound(null, msgs);
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND:
        return basicSetIntegerUpperBound(null, msgs);
      case QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE:
        return ((InternalEList<?>)getAnyAttribute()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND:
        return getIntegerLowerBound();
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND:
        return getIntegerUpperBound();
      case QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE:
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
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND:
        setIntegerLowerBound((IntegerBoundaryType)newValue);
        return;
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND:
        setIntegerUpperBound((IntegerBoundaryType)newValue);
        return;
      case QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE:
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
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND:
        setIntegerLowerBound((IntegerBoundaryType)null);
        return;
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND:
        setIntegerUpperBound((IntegerBoundaryType)null);
        return;
      case QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE:
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
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND:
        return this.integerLowerBound != null;
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND:
        return this.integerUpperBound != null;
      case QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE:
        return this.anyAttribute != null && !this.anyAttribute.isEmpty();
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
    result.append(" (anyAttribute: "); //$NON-NLS-1$
    result.append(this.anyAttribute);
    result.append(')');
    return result.toString();
  }

} //IntegerRangeTypeImpl
