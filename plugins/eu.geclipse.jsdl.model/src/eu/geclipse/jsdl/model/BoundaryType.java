/**
 * <copyright>
 * </copyright>
 *
 * $Id: BoundaryType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boundary Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.BoundaryType#getValue <em>Value</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.BoundaryType#isExclusiveBound <em>Exclusive Bound</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.BoundaryType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getBoundaryType()
 * @model extendedMetaData="name='Boundary_Type' kind='simple'"
 * @generated
 */
public interface BoundaryType extends EObject
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getBoundaryType_Value()
   * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
   *        extendedMetaData="name=':0' kind='simple'"
   * @generated
   */
	double getValue();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.BoundaryType#getValue <em>Value</em>}' attribute.
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
   * Unsets the value of the '{@link eu.geclipse.jsdl.model.BoundaryType#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetValue()
   * @see #getValue()
   * @see #setValue(double)
   * @generated
   */
	void unsetValue();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.model.BoundaryType#getValue <em>Value</em>}' attribute is set.
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
   * Returns the value of the '<em><b>Exclusive Bound</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclusive Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Exclusive Bound</em>' attribute.
   * @see #isSetExclusiveBound()
   * @see #unsetExclusiveBound()
   * @see #setExclusiveBound(boolean)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getBoundaryType_ExclusiveBound()
   * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
   *        extendedMetaData="kind='attribute' name='exclusiveBound'"
   * @generated
   */
	boolean isExclusiveBound();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.BoundaryType#isExclusiveBound <em>Exclusive Bound</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exclusive Bound</em>' attribute.
   * @see #isSetExclusiveBound()
   * @see #unsetExclusiveBound()
   * @see #isExclusiveBound()
   * @generated
   */
	void setExclusiveBound(boolean value);

  /**
   * Unsets the value of the '{@link eu.geclipse.jsdl.model.BoundaryType#isExclusiveBound <em>Exclusive Bound</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetExclusiveBound()
   * @see #isExclusiveBound()
   * @see #setExclusiveBound(boolean)
   * @generated
   */
	void unsetExclusiveBound();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.model.BoundaryType#isExclusiveBound <em>Exclusive Bound</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Exclusive Bound</em>' attribute is set.
   * @see #unsetExclusiveBound()
   * @see #isExclusiveBound()
   * @see #setExclusiveBound(boolean)
   * @generated
   */
	boolean isSetExclusiveBound();

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getBoundaryType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // BoundaryType