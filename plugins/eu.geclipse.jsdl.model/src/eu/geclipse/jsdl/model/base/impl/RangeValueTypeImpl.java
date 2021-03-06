/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.impl;

import eu.geclipse.jsdl.model.base.BoundaryType;
import eu.geclipse.jsdl.model.base.ExactType;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.RangeType;
import eu.geclipse.jsdl.model.base.RangeValueType;

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
 * An implementation of the model object '<em><b>Range Value Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.RangeValueTypeImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.RangeValueTypeImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.RangeValueTypeImpl#getExact <em>Exact</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.RangeValueTypeImpl#getRange <em>Range</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.impl.RangeValueTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeValueTypeImpl extends EObjectImpl implements RangeValueType
{
  /**
   * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUpperBound()
   * @generated
   * @ordered
   */
  protected BoundaryType upperBound;

  /**
   * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLowerBound()
   * @generated
   * @ordered
   */
  protected BoundaryType lowerBound;

  /**
   * The cached value of the '{@link #getExact() <em>Exact</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExact()
   * @generated
   * @ordered
   */
  protected EList exact;

  /**
   * The cached value of the '{@link #getRange() <em>Range</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRange()
   * @generated
   * @ordered
   */
  protected EList range;

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
  protected RangeValueTypeImpl()
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
    return JsdlPackage.Literals.RANGE_VALUE_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BoundaryType getUpperBound()
  {
    return upperBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetUpperBound(BoundaryType newUpperBound, NotificationChain msgs)
  {
    BoundaryType oldUpperBound = upperBound;
    upperBound = newUpperBound;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND, oldUpperBound, newUpperBound);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUpperBound(BoundaryType newUpperBound)
  {
    if (newUpperBound != upperBound)
    {
      NotificationChain msgs = null;
      if (upperBound != null)
        msgs = ((InternalEObject)upperBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND, null, msgs);
      if (newUpperBound != null)
        msgs = ((InternalEObject)newUpperBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND, null, msgs);
      msgs = basicSetUpperBound(newUpperBound, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND, newUpperBound, newUpperBound));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BoundaryType getLowerBound()
  {
    return lowerBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLowerBound(BoundaryType newLowerBound, NotificationChain msgs)
  {
    BoundaryType oldLowerBound = lowerBound;
    lowerBound = newLowerBound;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND, oldLowerBound, newLowerBound);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLowerBound(BoundaryType newLowerBound)
  {
    if (newLowerBound != lowerBound)
    {
      NotificationChain msgs = null;
      if (lowerBound != null)
        msgs = ((InternalEObject)lowerBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND, null, msgs);
      if (newLowerBound != null)
        msgs = ((InternalEObject)newLowerBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND, null, msgs);
      msgs = basicSetLowerBound(newLowerBound, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND, newLowerBound, newLowerBound));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getExact()
  {
    if (exact == null)
    {
      exact = new EObjectContainmentEList(ExactType.class, this, JsdlPackage.RANGE_VALUE_TYPE__EXACT);
    }
    return exact;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getRange()
  {
    if (range == null)
    {
      range = new EObjectContainmentEList(RangeType.class, this, JsdlPackage.RANGE_VALUE_TYPE__RANGE);
    }
    return range;
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
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE);
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
      case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND:
        return basicSetUpperBound(null, msgs);
      case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND:
        return basicSetLowerBound(null, msgs);
      case JsdlPackage.RANGE_VALUE_TYPE__EXACT:
        return ((InternalEList)getExact()).basicRemove(otherEnd, msgs);
      case JsdlPackage.RANGE_VALUE_TYPE__RANGE:
        return ((InternalEList)getRange()).basicRemove(otherEnd, msgs);
      case JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND:
        return getUpperBound();
      case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND:
        return getLowerBound();
      case JsdlPackage.RANGE_VALUE_TYPE__EXACT:
        return getExact();
      case JsdlPackage.RANGE_VALUE_TYPE__RANGE:
        return getRange();
      case JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND:
        setUpperBound((BoundaryType)newValue);
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND:
        setLowerBound((BoundaryType)newValue);
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__EXACT:
        getExact().clear();
        getExact().addAll((Collection)newValue);
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__RANGE:
        getRange().clear();
        getRange().addAll((Collection)newValue);
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND:
        setUpperBound((BoundaryType)null);
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND:
        setLowerBound((BoundaryType)null);
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__EXACT:
        getExact().clear();
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__RANGE:
        getRange().clear();
        return;
      case JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUND:
        return upperBound != null;
      case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUND:
        return lowerBound != null;
      case JsdlPackage.RANGE_VALUE_TYPE__EXACT:
        return exact != null && !exact.isEmpty();
      case JsdlPackage.RANGE_VALUE_TYPE__RANGE:
        return range != null && !range.isEmpty();
      case JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //RangeValueTypeImpl
