/**
 * <copyright>
 * </copyright>
 *
 * $Id: BoundaryTypeImpl.java,v 1.1 2007/01/25 15:26:30 emstamou Exp $
 */
package eu.geclipse.jsdl.impl;

import eu.geclipse.jsdl.BoundaryType;
import eu.geclipse.jsdl.JsdlPackage;

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
 * An implementation of the model object '<em><b>Boundary Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.impl.BoundaryTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.BoundaryTypeImpl#isExclusiveBound <em>Exclusive Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.BoundaryTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BoundaryTypeImpl extends EObjectImpl implements BoundaryType {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final double VALUE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected double value = VALUE_EDEFAULT;

	/**
	 * This is true if the Value attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean valueESet = false;

	/**
	 * The default value of the '{@link #isExclusiveBound() <em>Exclusive Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExclusiveBound()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCLUSIVE_BOUND_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExclusiveBound() <em>Exclusive Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExclusiveBound()
	 * @generated
	 * @ordered
	 */
	protected boolean exclusiveBound = EXCLUSIVE_BOUND_EDEFAULT;

	/**
	 * This is true if the Exclusive Bound attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean exclusiveBoundESet = false;

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
	protected BoundaryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return JsdlPackage.Literals.BOUNDARY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(double newValue) {
		double oldValue = value;
		value = newValue;
		boolean oldValueESet = valueESet;
		valueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.BOUNDARY_TYPE__VALUE, oldValue, value, !oldValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetValue() {
		double oldValue = value;
		boolean oldValueESet = valueESet;
		value = VALUE_EDEFAULT;
		valueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.BOUNDARY_TYPE__VALUE, oldValue, VALUE_EDEFAULT, oldValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetValue() {
		return valueESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExclusiveBound() {
		return exclusiveBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExclusiveBound(boolean newExclusiveBound) {
		boolean oldExclusiveBound = exclusiveBound;
		exclusiveBound = newExclusiveBound;
		boolean oldExclusiveBoundESet = exclusiveBoundESet;
		exclusiveBoundESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.BOUNDARY_TYPE__EXCLUSIVE_BOUND, oldExclusiveBound, exclusiveBound, !oldExclusiveBoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetExclusiveBound() {
		boolean oldExclusiveBound = exclusiveBound;
		boolean oldExclusiveBoundESet = exclusiveBoundESet;
		exclusiveBound = EXCLUSIVE_BOUND_EDEFAULT;
		exclusiveBoundESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.BOUNDARY_TYPE__EXCLUSIVE_BOUND, oldExclusiveBound, EXCLUSIVE_BOUND_EDEFAULT, oldExclusiveBoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetExclusiveBound() {
		return exclusiveBoundESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, JsdlPackage.BOUNDARY_TYPE__ANY_ATTRIBUTE);
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
			case JsdlPackage.BOUNDARY_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.BOUNDARY_TYPE__VALUE:
				return new Double(getValue());
			case JsdlPackage.BOUNDARY_TYPE__EXCLUSIVE_BOUND:
				return isExclusiveBound() ? Boolean.TRUE : Boolean.FALSE;
			case JsdlPackage.BOUNDARY_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.BOUNDARY_TYPE__VALUE:
				setValue(((Double)newValue).doubleValue());
				return;
			case JsdlPackage.BOUNDARY_TYPE__EXCLUSIVE_BOUND:
				setExclusiveBound(((Boolean)newValue).booleanValue());
				return;
			case JsdlPackage.BOUNDARY_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.BOUNDARY_TYPE__VALUE:
				unsetValue();
				return;
			case JsdlPackage.BOUNDARY_TYPE__EXCLUSIVE_BOUND:
				unsetExclusiveBound();
				return;
			case JsdlPackage.BOUNDARY_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.BOUNDARY_TYPE__VALUE:
				return isSetValue();
			case JsdlPackage.BOUNDARY_TYPE__EXCLUSIVE_BOUND:
				return isSetExclusiveBound();
			case JsdlPackage.BOUNDARY_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (value: ");
		if (valueESet) result.append(value); else result.append("<unset>");
		result.append(", exclusiveBound: ");
		if (exclusiveBoundESet) result.append(exclusiveBound); else result.append("<unset>");
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //BoundaryTypeImpl