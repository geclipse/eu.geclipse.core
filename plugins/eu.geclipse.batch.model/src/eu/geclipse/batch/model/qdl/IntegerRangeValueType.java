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
package eu.geclipse.batch.model.qdl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Range Value Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getExact <em>Exact</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getRange <em>Range</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeValueType()
 * @model extendedMetaData="name='Integer_RangeValue_Type' kind='elementOnly'"
 * @generated
 */
public interface IntegerRangeValueType extends EObject
{
  /**
   * Returns the value of the '<em><b>Upper Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Upper Bounded Range</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Upper Bounded Range</em>' containment reference.
   * @see #setUpperBoundedRange(IntegerBoundaryType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeValueType_UpperBoundedRange()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='UpperBoundedRange' namespace='##targetNamespace'"
   * @generated
   */
  IntegerBoundaryType getUpperBoundedRange();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Upper Bounded Range</em>' containment reference.
   * @see #getUpperBoundedRange()
   * @generated
   */
  void setUpperBoundedRange(IntegerBoundaryType value);

  /**
   * Returns the value of the '<em><b>Lower Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lower Bounded Range</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lower Bounded Range</em>' containment reference.
   * @see #setLowerBoundedRange(IntegerBoundaryType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeValueType_LowerBoundedRange()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='LowerBoundedRange' namespace='##targetNamespace'"
   * @generated
   */
  IntegerBoundaryType getLowerBoundedRange();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lower Bounded Range</em>' containment reference.
   * @see #getLowerBoundedRange()
   * @generated
   */
  void setLowerBoundedRange(IntegerBoundaryType value);

  /**
   * Returns the value of the '<em><b>Exact</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.batch.model.qdl.IntegerExactType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exact</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exact</em>' containment reference list.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeValueType_Exact()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Exact' namespace='##targetNamespace'"
   * @generated
   */
  EList<IntegerExactType> getExact();

  /**
   * Returns the value of the '<em><b>Range</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.batch.model.qdl.IntegerRangeType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Range</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Range</em>' containment reference list.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeValueType_Range()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Range' namespace='##targetNamespace'"
   * @generated
   */
  EList<IntegerRangeType> getRange();

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeValueType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
   * @generated
   */
  FeatureMap getAnyAttribute();

} // IntegerRangeValueType
