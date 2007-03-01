/**
 * <copyright>
 * </copyright>
 *
 * $Id: RangeValueTypeImpl.java,v 1.1 2007/01/25 15:26:29 emstamou Exp $
 */
package eu.geclipse.jsdl.impl;

import eu.geclipse.jsdl.BoundaryType;
import eu.geclipse.jsdl.ExactType;
import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.RangeType;
import eu.geclipse.jsdl.RangeValueType;

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
 *   <li>{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl#getUpperBoundedRange <em>Upper Bounded Range</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl#getLowerBoundedRange <em>Lower Bounded Range</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl#getExact <em>Exact</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl#getRange <em>Range</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeValueTypeImpl extends EObjectImpl implements RangeValueType {
	/**
	 * The cached value of the '{@link #getUpperBoundedRange() <em>Upper Bounded Range</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBoundedRange()
	 * @generated
	 * @ordered
	 */
	protected BoundaryType upperBoundedRange = null;

	/**
	 * The cached value of the '{@link #getLowerBoundedRange() <em>Lower Bounded Range</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBoundedRange()
	 * @generated
	 * @ordered
	 */
	protected BoundaryType lowerBoundedRange = null;

	/**
	 * The cached value of the '{@link #getExact() <em>Exact</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExact()
	 * @generated
	 * @ordered
	 */
	protected EList exact = null;

	/**
	 * The cached value of the '{@link #getRange() <em>Range</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRange()
	 * @generated
	 * @ordered
	 */
	protected EList range = null;

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
	protected RangeValueTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return JsdlPackage.Literals.RANGE_VALUE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoundaryType getUpperBoundedRange() {
		return upperBoundedRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUpperBoundedRange(BoundaryType newUpperBoundedRange, NotificationChain msgs) {
		BoundaryType oldUpperBoundedRange = upperBoundedRange;
		upperBoundedRange = newUpperBoundedRange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE, oldUpperBoundedRange, newUpperBoundedRange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBoundedRange(BoundaryType newUpperBoundedRange) {
		if (newUpperBoundedRange != upperBoundedRange) {
			NotificationChain msgs = null;
			if (upperBoundedRange != null)
				msgs = ((InternalEObject)upperBoundedRange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE, null, msgs);
			if (newUpperBoundedRange != null)
				msgs = ((InternalEObject)newUpperBoundedRange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE, null, msgs);
			msgs = basicSetUpperBoundedRange(newUpperBoundedRange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE, newUpperBoundedRange, newUpperBoundedRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoundaryType getLowerBoundedRange() {
		return lowerBoundedRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLowerBoundedRange(BoundaryType newLowerBoundedRange, NotificationChain msgs) {
		BoundaryType oldLowerBoundedRange = lowerBoundedRange;
		lowerBoundedRange = newLowerBoundedRange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE, oldLowerBoundedRange, newLowerBoundedRange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBoundedRange(BoundaryType newLowerBoundedRange) {
		if (newLowerBoundedRange != lowerBoundedRange) {
			NotificationChain msgs = null;
			if (lowerBoundedRange != null)
				msgs = ((InternalEObject)lowerBoundedRange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE, null, msgs);
			if (newLowerBoundedRange != null)
				msgs = ((InternalEObject)newLowerBoundedRange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE, null, msgs);
			msgs = basicSetLowerBoundedRange(newLowerBoundedRange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE, newLowerBoundedRange, newLowerBoundedRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getExact() {
		if (exact == null) {
			exact = new EObjectContainmentEList(ExactType.class, this, JsdlPackage.RANGE_VALUE_TYPE__EXACT);
		}
		return exact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRange() {
		if (range == null) {
			range = new EObjectContainmentEList(RangeType.class, this, JsdlPackage.RANGE_VALUE_TYPE__RANGE);
		}
		return range;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, JsdlPackage.RANGE_VALUE_TYPE__ANY_ATTRIBUTE);
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
			case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE:
				return basicSetUpperBoundedRange(null, msgs);
			case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE:
				return basicSetLowerBoundedRange(null, msgs);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE:
				return getUpperBoundedRange();
			case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE:
				return getLowerBoundedRange();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE:
				setUpperBoundedRange((BoundaryType)newValue);
				return;
			case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE:
				setLowerBoundedRange((BoundaryType)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE:
				setUpperBoundedRange((BoundaryType)null);
				return;
			case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE:
				setLowerBoundedRange((BoundaryType)null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JsdlPackage.RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE:
				return upperBoundedRange != null;
			case JsdlPackage.RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE:
				return lowerBoundedRange != null;
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
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //RangeValueTypeImpl