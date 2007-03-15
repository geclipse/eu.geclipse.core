/**
 * <copyright>
 * </copyright>
 *
 * $Id: ExactType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl;

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
 *   <li>{@link eu.geclipse.jsdl.ExactType#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.ExactType#getEpsilon <em>Epsilon</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.ExactType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.JsdlPackage#getExactType()
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
   * @see eu.geclipse.jsdl.JsdlPackage#getExactType_Value()
   * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
   *        extendedMetaData="name=':0' kind='simple'"
   * @generated
   */
	double getValue();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.ExactType#getValue <em>Value</em>}' attribute.
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
   * Unsets the value of the '{@link eu.geclipse.jsdl.ExactType#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetValue()
   * @see #getValue()
   * @see #setValue(double)
   * @generated
   */
	void unsetValue();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.ExactType#getValue <em>Value</em>}' attribute is set.
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
   * @see eu.geclipse.jsdl.JsdlPackage#getExactType_Epsilon()
   * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
   *        extendedMetaData="kind='attribute' name='epsilon'"
   * @generated
   */
	double getEpsilon();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.ExactType#getEpsilon <em>Epsilon</em>}' attribute.
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
   * Unsets the value of the '{@link eu.geclipse.jsdl.ExactType#getEpsilon <em>Epsilon</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetEpsilon()
   * @see #getEpsilon()
   * @see #setEpsilon(double)
   * @generated
   */
	void unsetEpsilon();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.ExactType#getEpsilon <em>Epsilon</em>}' attribute is set.
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
   * @see eu.geclipse.jsdl.JsdlPackage#getExactType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // ExactType