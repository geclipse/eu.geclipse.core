/**
 * <copyright>
 * </copyright>
 *
 * $Id: OperatingSystemTypeType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operating System Type Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemTypeType#getOperatingSystemName <em>Operating System Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemTypeType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemTypeType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemTypeType()
 * @model extendedMetaData="name='OperatingSystemType_Type' kind='elementOnly'"
 * @generated
 */
public interface OperatingSystemTypeType extends EObject
{
  /**
   * Returns the value of the '<em><b>Operating System Name</b></em>' attribute.
   * The default value is <code>"Unknown"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operating System Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Operating System Name</em>' attribute.
   * @see eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration
   * @see #isSetOperatingSystemName()
   * @see #unsetOperatingSystemName()
   * @see #setOperatingSystemName(OperatingSystemTypeEnumeration)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemTypeType_OperatingSystemName()
   * @model default="Unknown" unique="false" unsettable="true" required="true"
   *        extendedMetaData="kind='element' name='OperatingSystemName' namespace='##targetNamespace'"
   * @generated
   */
	OperatingSystemTypeEnumeration getOperatingSystemName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.OperatingSystemTypeType#getOperatingSystemName <em>Operating System Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System Name</em>' attribute.
   * @see eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration
   * @see #isSetOperatingSystemName()
   * @see #unsetOperatingSystemName()
   * @see #getOperatingSystemName()
   * @generated
   */
	void setOperatingSystemName(OperatingSystemTypeEnumeration value);

  /**
   * Unsets the value of the '{@link eu.geclipse.jsdl.model.OperatingSystemTypeType#getOperatingSystemName <em>Operating System Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetOperatingSystemName()
   * @see #getOperatingSystemName()
   * @see #setOperatingSystemName(OperatingSystemTypeEnumeration)
   * @generated
   */
	void unsetOperatingSystemName();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.model.OperatingSystemTypeType#getOperatingSystemName <em>Operating System Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Operating System Name</em>' attribute is set.
   * @see #unsetOperatingSystemName()
   * @see #getOperatingSystemName()
   * @see #setOperatingSystemName(OperatingSystemTypeEnumeration)
   * @generated
   */
	boolean isSetOperatingSystemName();

  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemTypeType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':1' processing='lax'"
   * @generated
   */
	FeatureMap getAny();

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemTypeType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // OperatingSystemTypeType