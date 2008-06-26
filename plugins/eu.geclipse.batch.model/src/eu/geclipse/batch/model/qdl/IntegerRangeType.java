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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Range Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerLowerBound <em>Integer Lower Bound</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerUpperBound <em>Integer Upper Bound</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeType()
 * @model extendedMetaData="name='Integer_Range_Type' kind='elementOnly'"
 * @generated
 */
public interface IntegerRangeType extends EObject
{
  /**
   * Returns the value of the '<em><b>Integer Lower Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Integer Lower Bound</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Integer Lower Bound</em>' containment reference.
   * @see #setIntegerLowerBound(IntegerBoundaryType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeType_IntegerLowerBound()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='Integer_LowerBound' namespace='##targetNamespace'"
   * @generated
   */
  IntegerBoundaryType getIntegerLowerBound();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerLowerBound <em>Integer Lower Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Integer Lower Bound</em>' containment reference.
   * @see #getIntegerLowerBound()
   * @generated
   */
  void setIntegerLowerBound(IntegerBoundaryType value);

  /**
   * Returns the value of the '<em><b>Integer Upper Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Integer Upper Bound</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Integer Upper Bound</em>' containment reference.
   * @see #setIntegerUpperBound(IntegerBoundaryType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeType_IntegerUpperBound()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='Integer_UpperBound' namespace='##targetNamespace'"
   * @generated
   */
  IntegerBoundaryType getIntegerUpperBound();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerUpperBound <em>Integer Upper Bound</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Integer Upper Bound</em>' containment reference.
   * @see #getIntegerUpperBound()
   * @generated
   */
  void setIntegerUpperBound(IntegerBoundaryType value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getIntegerRangeType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
  FeatureMap getAnyAttribute();

} // IntegerRangeType
