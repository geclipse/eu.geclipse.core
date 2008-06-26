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
 * A representation of the model object '<em><b>Range Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeType#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeType#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.RangeType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeType()
 * @model extendedMetaData="name='Range_Type' kind='elementOnly'"
 * @generated
 */
public interface RangeType extends EObject
{
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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeType_LowerBound()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='LowerBound' namespace='##targetNamespace'"
   * @generated
   */
  BoundaryType getLowerBound();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.RangeType#getLowerBound <em>Lower Bound</em>}' containment reference.
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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeType_UpperBound()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='UpperBound' namespace='##targetNamespace'"
   * @generated
   */
  BoundaryType getUpperBound();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.RangeType#getUpperBound <em>Upper Bound</em>}' containment reference.
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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getRangeType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
  FeatureMap getAnyAttribute();

} // RangeType
