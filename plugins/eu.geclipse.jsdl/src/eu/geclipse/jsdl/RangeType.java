/**
 * <copyright>
 * </copyright>
 *
 * $Id: RangeType.java,v 1.1 2007/01/25 15:26:29 emstamou Exp $
 */
package eu.geclipse.jsdl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.RangeType#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.RangeType#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.RangeType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.JsdlPackage#getRangeType()
 * @model extendedMetaData="name='Range_Type' kind='elementOnly'"
 * @generated
 */
public interface RangeType extends EObject {
	/**
	 * Returns the value of the '<em><b>Lower Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Bound</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound</em>' containment reference.
	 * @see #setLowerBound(BoundaryType)
	 * @see eu.geclipse.jsdl.JsdlPackage#getRangeType_LowerBound()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='LowerBound' namespace='##targetNamespace'"
	 * @generated
	 */
	BoundaryType getLowerBound();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.RangeType#getLowerBound <em>Lower Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' containment reference.
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(BoundaryType value);

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' containment reference.
	 * @see #setUpperBound(BoundaryType)
	 * @see eu.geclipse.jsdl.JsdlPackage#getRangeType_UpperBound()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='UpperBound' namespace='##targetNamespace'"
	 * @generated
	 */
	BoundaryType getUpperBound();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.RangeType#getUpperBound <em>Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' containment reference.
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(BoundaryType value);

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see eu.geclipse.jsdl.JsdlPackage#getRangeType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // RangeType