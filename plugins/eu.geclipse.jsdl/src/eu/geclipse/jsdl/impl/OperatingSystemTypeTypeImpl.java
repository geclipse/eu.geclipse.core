/**
 * <copyright>
 * </copyright>
 *
 * $Id: OperatingSystemTypeTypeImpl.java,v 1.1 2007/01/25 15:26:30 emstamou Exp $
 */
package eu.geclipse.jsdl.impl;

import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.OperatingSystemTypeType;

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
 * An implementation of the model object '<em><b>Operating System Type Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl#getOperatingSystemName <em>Operating System Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperatingSystemTypeTypeImpl extends EObjectImpl implements OperatingSystemTypeType {
	/**
	 * The default value of the '{@link #getOperatingSystemName() <em>Operating System Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperatingSystemName()
	 * @generated
	 * @ordered
	 */
	protected static final OperatingSystemTypeEnumeration OPERATING_SYSTEM_NAME_EDEFAULT = OperatingSystemTypeEnumeration.UNKNOWN_LITERAL;

	/**
	 * The cached value of the '{@link #getOperatingSystemName() <em>Operating System Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperatingSystemName()
	 * @generated
	 * @ordered
	 */
	protected OperatingSystemTypeEnumeration operatingSystemName = OPERATING_SYSTEM_NAME_EDEFAULT;

	/**
	 * This is true if the Operating System Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean operatingSystemNameESet = false;

	/**
	 * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAny()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap any = null;

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
	protected OperatingSystemTypeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperatingSystemTypeEnumeration getOperatingSystemName() {
		return operatingSystemName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperatingSystemName(OperatingSystemTypeEnumeration newOperatingSystemName) {
		OperatingSystemTypeEnumeration oldOperatingSystemName = operatingSystemName;
		operatingSystemName = newOperatingSystemName == null ? OPERATING_SYSTEM_NAME_EDEFAULT : newOperatingSystemName;
		boolean oldOperatingSystemNameESet = operatingSystemNameESet;
		operatingSystemNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME, oldOperatingSystemName, operatingSystemName, !oldOperatingSystemNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOperatingSystemName() {
		OperatingSystemTypeEnumeration oldOperatingSystemName = operatingSystemName;
		boolean oldOperatingSystemNameESet = operatingSystemNameESet;
		operatingSystemName = OPERATING_SYSTEM_NAME_EDEFAULT;
		operatingSystemNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME, oldOperatingSystemName, OPERATING_SYSTEM_NAME_EDEFAULT, oldOperatingSystemNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOperatingSystemName() {
		return operatingSystemNameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAny() {
		if (any == null) {
			any = new BasicFeatureMap(this, JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY);
		}
		return any;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE);
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
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY:
				return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME:
				return getOperatingSystemName();
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY:
				if (coreType) return getAny();
				return ((FeatureMap.Internal)getAny()).getWrapper();
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME:
				setOperatingSystemName((OperatingSystemTypeEnumeration)newValue);
				return;
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY:
				((FeatureMap.Internal)getAny()).set(newValue);
				return;
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME:
				unsetOperatingSystemName();
				return;
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY:
				getAny().clear();
				return;
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE:
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
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME:
				return isSetOperatingSystemName();
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY:
				return any != null && !any.isEmpty();
			case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (operatingSystemName: ");
		if (operatingSystemNameESet) result.append(operatingSystemName); else result.append("<unset>");
		result.append(", any: ");
		result.append(any);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //OperatingSystemTypeTypeImpl