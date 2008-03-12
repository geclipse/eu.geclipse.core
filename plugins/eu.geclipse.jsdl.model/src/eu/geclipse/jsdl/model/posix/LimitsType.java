/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *    Mathias Stümpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.posix;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Limits Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.posix.LimitsType#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.LimitsType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.posix.PosixPackage#getLimitsType()
 * @model extendedMetaData="name='Limits_Type' kind='simple'"
 * @generated
 */
public interface LimitsType extends EObject
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
   * @see #setValue(BigInteger)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getLimitsType_Value()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
   *        extendedMetaData="name=':0' kind='simple'"
   * @generated
   */
	BigInteger getValue();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.LimitsType#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
	void setValue(BigInteger value);

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
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getLimitsType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':1' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // LimitsType