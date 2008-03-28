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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.model.qdl;

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
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeValueType#getExact <em>Exact</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeValueType#getRange <em>Range</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeValueType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeValueType()
 * @model extendedMetaData="name='RangeValue_Type' kind='elementOnly'"
 * @generated
 */
public interface RangeValueType extends EObject
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
   * @see #setUpperBoundedRange(BoundaryType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeValueType_UpperBoundedRange()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='UpperBoundedRange' namespace='##targetNamespace'"
   * @generated
   */
  BoundaryType getUpperBoundedRange();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.RangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Upper Bounded Range</em>' containment reference.
   * @see #getUpperBoundedRange()
   * @generated
   */
  void setUpperBoundedRange(BoundaryType value);

  /**
   * Returns the value of the '<em><b>Lower Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Lower Bounded Range</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lower Bounded Range</em>' containment reference.
   * @see #setLowerBoundedRange(BoundaryType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeValueType_LowerBoundedRange()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='LowerBoundedRange' namespace='##targetNamespace'"
   * @generated
   */
  BoundaryType getLowerBoundedRange();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.RangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lower Bounded Range</em>' containment reference.
   * @see #getLowerBoundedRange()
   * @generated
   */
  void setLowerBoundedRange(BoundaryType value);

  /**
   * Returns the value of the '<em><b>Exact</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.batch.model.qdl.ExactType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exact</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exact</em>' containment reference list.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeValueType_Exact()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Exact' namespace='##targetNamespace'"
   * @generated
   */
  EList<ExactType> getExact();

  /**
   * Returns the value of the '<em><b>Range</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.batch.model.qdl.RangeType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Range</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Range</em>' containment reference list.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeValueType_Range()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Range' namespace='##targetNamespace'"
   * @generated
   */
  EList<RangeType> getRange();

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeValueType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
   * @generated
   */
  FeatureMap getAnyAttribute();

} // RangeValueType
