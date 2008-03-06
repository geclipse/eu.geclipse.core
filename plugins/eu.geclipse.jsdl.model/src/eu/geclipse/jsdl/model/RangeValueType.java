/**
 * <copyright>
 * </copyright>
 *
 * $Id: RangeValueType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Value Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.RangeValueType#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.RangeValueType#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.RangeValueType#getExact <em>Exact</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.RangeValueType#getRange <em>Range</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.RangeValueType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getRangeValueType()
 * @model extendedMetaData="name='RangeValue_Type' kind='elementOnly'"
 * @generated
 */
public interface RangeValueType extends EObject
{
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getRangeValueType_UpperBound()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='UpperBoundedRange' namespace='##targetNamespace'"
   * @generated
   */
  BoundaryType getUpperBound();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.RangeValueType#getUpperBound <em>Upper Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Upper Bound</em>' containment reference.
   * @see #getUpperBound()
   * @generated
   */
  void setUpperBound(BoundaryType value);

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getRangeValueType_LowerBound()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='LowerBoundedRange' namespace='##targetNamespace'"
   * @generated
   */
  BoundaryType getLowerBound();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.RangeValueType#getLowerBound <em>Lower Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lower Bound</em>' containment reference.
   * @see #getLowerBound()
   * @generated
   */
  void setLowerBound(BoundaryType value);

  /**
   * Returns the value of the '<em><b>Exact</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.ExactType}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exact</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Exact</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getRangeValueType_Exact()
   * @model type="eu.geclipse.jsdl.model.ExactType" containment="true"
   *        extendedMetaData="kind='element' name='Exact' namespace='##targetNamespace'"
   * @generated
   */
	EList getExact();

  /**
   * Returns the value of the '<em><b>Range</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.RangeType}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Range</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Range</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getRangeValueType_Range()
   * @model type="eu.geclipse.jsdl.model.RangeType" containment="true"
   *        extendedMetaData="kind='element' name='Range' namespace='##targetNamespace'"
   * @generated
   */
	EList getRange();

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getRangeValueType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // RangeValueType