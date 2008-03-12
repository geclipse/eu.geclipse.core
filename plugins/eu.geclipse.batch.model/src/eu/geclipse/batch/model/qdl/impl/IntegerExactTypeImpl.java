/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.IntegerExactType;
import eu.geclipse.batch.model.qdl.QdlPackage;

import java.math.BigInteger;

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
 * An implementation of the model object '<em><b>Integer Exact Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl#getEpsilon <em>Epsilon</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IntegerExactTypeImpl extends EObjectImpl implements IntegerExactType
{
  /**
   * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected static final int VALUE_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValue()
   * @generated
   * @ordered
   */
  protected int value = VALUE_EDEFAULT;

  /**
   * This is true if the Value attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean valueESet;

  /**
   * The default value of the '{@link #getEpsilon() <em>Epsilon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEpsilon()
   * @generated
   * @ordered
   */
  protected static final int EPSILON_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getEpsilon() <em>Epsilon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEpsilon()
   * @generated
   * @ordered
   */
  protected int epsilon = EPSILON_EDEFAULT;

  /**
   * This is true if the Epsilon attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean epsilonESet;

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
  protected IntegerExactTypeImpl()
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
    return QdlPackage.Literals.INTEGER_EXACT_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setValue(int newValue)
  {
    int oldValue = value;
    value = newValue;
    boolean oldValueESet = valueESet;
    valueESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.INTEGER_EXACT_TYPE__VALUE, oldValue, value, !oldValueESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetValue()
  {
    int oldValue = value;
    boolean oldValueESet = valueESet;
    value = VALUE_EDEFAULT;
    valueESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, QdlPackage.INTEGER_EXACT_TYPE__VALUE, oldValue, VALUE_EDEFAULT, oldValueESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetValue()
  {
    return valueESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getEpsilon()
  {
    return epsilon;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEpsilon(int newEpsilon)
  {
    int oldEpsilon = epsilon;
    epsilon = newEpsilon;
    boolean oldEpsilonESet = epsilonESet;
    epsilonESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.INTEGER_EXACT_TYPE__EPSILON, oldEpsilon, epsilon, !oldEpsilonESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetEpsilon()
  {
    int oldEpsilon = epsilon;
    boolean oldEpsilonESet = epsilonESet;
    epsilon = EPSILON_EDEFAULT;
    epsilonESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, QdlPackage.INTEGER_EXACT_TYPE__EPSILON, oldEpsilon, EPSILON_EDEFAULT, oldEpsilonESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetEpsilon()
  {
    return epsilonESet;
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
      anyAttribute = new BasicFeatureMap(this, QdlPackage.INTEGER_EXACT_TYPE__ANY_ATTRIBUTE);
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
      case QdlPackage.INTEGER_EXACT_TYPE__ANY_ATTRIBUTE:
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
      case QdlPackage.INTEGER_EXACT_TYPE__VALUE:
        return new Integer(getValue());
      case QdlPackage.INTEGER_EXACT_TYPE__EPSILON:
        return new Integer(getEpsilon());
      case QdlPackage.INTEGER_EXACT_TYPE__ANY_ATTRIBUTE:
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
      case QdlPackage.INTEGER_EXACT_TYPE__VALUE:
        setValue(((Integer)newValue).intValue());
        return;
      case QdlPackage.INTEGER_EXACT_TYPE__EPSILON:
        setEpsilon(((Integer)newValue).intValue());
        return;
      case QdlPackage.INTEGER_EXACT_TYPE__ANY_ATTRIBUTE:
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
      case QdlPackage.INTEGER_EXACT_TYPE__VALUE:
        unsetValue();
        return;
      case QdlPackage.INTEGER_EXACT_TYPE__EPSILON:
        unsetEpsilon();
        return;
      case QdlPackage.INTEGER_EXACT_TYPE__ANY_ATTRIBUTE:
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
      case QdlPackage.INTEGER_EXACT_TYPE__VALUE:
        return isSetValue();
      case QdlPackage.INTEGER_EXACT_TYPE__EPSILON:
        return isSetEpsilon();
      case QdlPackage.INTEGER_EXACT_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (value: ");
    if (valueESet) result.append(value); else result.append("<unset>");
    result.append(", epsilon: ");
    if (epsilonESet) result.append(epsilon); else result.append("<unset>");
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //IntegerExactTypeImpl
