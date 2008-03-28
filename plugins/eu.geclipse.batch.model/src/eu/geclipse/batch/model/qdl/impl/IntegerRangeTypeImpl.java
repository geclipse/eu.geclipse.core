/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
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
    return integerLowerBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIntegerLowerBound(IntegerBoundaryType newIntegerLowerBound, NotificationChain msgs)
  {
    IntegerBoundaryType oldIntegerLowerBound = integerLowerBound;
    integerLowerBound = newIntegerLowerBound;
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
    if (newIntegerLowerBound != integerLowerBound)
    {
      NotificationChain msgs = null;
      if (integerLowerBound != null)
        msgs = ((InternalEObject)integerLowerBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND, null, msgs);
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
    return integerUpperBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetIntegerUpperBound(IntegerBoundaryType newIntegerUpperBound, NotificationChain msgs)
  {
    IntegerBoundaryType oldIntegerUpperBound = integerUpperBound;
    integerUpperBound = newIntegerUpperBound;
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
    if (newIntegerUpperBound != integerUpperBound)
    {
      NotificationChain msgs = null;
      if (integerUpperBound != null)
        msgs = ((InternalEObject)integerUpperBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND, null, msgs);
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
    if (anyAttribute == null)
    {
      anyAttribute = new BasicFeatureMap(this, QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE);
    }
    return anyAttribute;
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
        return integerLowerBound != null;
      case QdlPackage.INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND:
        return integerUpperBound != null;
      case QdlPackage.INTEGER_RANGE_TYPE__ANY_ATTRIBUTE:
        return anyAttribute != null && !anyAttribute.isEmpty();
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
    result.append(" (anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //IntegerRangeTypeImpl
