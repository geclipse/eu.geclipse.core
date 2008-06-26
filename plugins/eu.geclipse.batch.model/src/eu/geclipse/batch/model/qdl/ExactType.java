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
 * A representation of the model object '<em><b>Exact Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.ExactType#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.ExactType#getEpsilon <em>Epsilon</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.ExactType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getExactType()
 * @model extendedMetaData="name='Exact_Type' kind='simple'"
 * @generated
 */
public interface ExactType extends EObject
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #isSetValue()
   * @see #unsetValue()
   * @see #setValue(double)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getExactType_Value()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
   *        extendedMetaData="name=':0' kind='simple'"
   * @generated
   */
  double getValue();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.ExactType#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #isSetValue()
   * @see #unsetValue()
   * @see #getValue()
   * @generated
   */
  void setValue(double value);

  /**
   * Unsets the value of the '{@link eu.geclipse.batch.model.qdl.ExactType#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetValue()
   * @see #getValue()
   * @see #setValue(double)
   * @generated
   */
  void unsetValue();

  /**
   * Returns whether the value of the '{@link eu.geclipse.batch.model.qdl.ExactType#getValue <em>Value</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Value</em>' attribute is set.
   * @see #unsetValue()
   * @see #getValue()
   * @see #setValue(double)
   * @generated
   */
  boolean isSetValue();

  /**
   * Returns the value of the '<em><b>Epsilon</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Epsilon</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Epsilon</em>' attribute.
   * @see #isSetEpsilon()
   * @see #unsetEpsilon()
   * @see #setEpsilon(double)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getExactType_Epsilon()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
   *        extendedMetaData="kind='attribute' name='epsilon'"
   * @generated
   */
  double getEpsilon();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.ExactType#getEpsilon <em>Epsilon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Epsilon</em>' attribute.
   * @see #isSetEpsilon()
   * @see #unsetEpsilon()
   * @see #getEpsilon()
   * @generated
   */
  void setEpsilon(double value);

  /**
   * Unsets the value of the '{@link eu.geclipse.batch.model.qdl.ExactType#getEpsilon <em>Epsilon</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetEpsilon()
   * @see #getEpsilon()
   * @see #setEpsilon(double)
   * @generated
   */
  void unsetEpsilon();

  /**
   * Returns whether the value of the '{@link eu.geclipse.batch.model.qdl.ExactType#getEpsilon <em>Epsilon</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Epsilon</em>' attribute is set.
   * @see #unsetEpsilon()
   * @see #getEpsilon()
   * @see #setEpsilon(double)
   * @generated
   */
  boolean isSetEpsilon();

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getExactType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
  FeatureMap getAnyAttribute();

} // ExactType
