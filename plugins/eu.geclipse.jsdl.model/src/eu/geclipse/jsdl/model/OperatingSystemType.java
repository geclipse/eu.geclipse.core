/**
 * <copyright>
 * </copyright>
 *
 * $Id: OperatingSystemType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operating System Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemType#getOperatingSystemType <em>Operating System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemType#getOperatingSystemVersion <em>Operating System Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemType#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.OperatingSystemType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemType()
 * @model extendedMetaData="name='OperatingSystem_Type' kind='elementOnly'"
 * @generated
 */
public interface OperatingSystemType extends EObject
{
  /**
   * Returns the value of the '<em><b>Operating System Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operating System Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Operating System Type</em>' containment reference.
   * @see #setOperatingSystemType(OperatingSystemTypeType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemType_OperatingSystemType()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='OperatingSystemType' namespace='##targetNamespace'"
   * @generated
   */
	OperatingSystemTypeType getOperatingSystemType();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.OperatingSystemType#getOperatingSystemType <em>Operating System Type</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System Type</em>' containment reference.
   * @see #getOperatingSystemType()
   * @generated
   */
	void setOperatingSystemType(OperatingSystemTypeType value);

  /**
   * Returns the value of the '<em><b>Operating System Version</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operating System Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Operating System Version</em>' attribute.
   * @see #setOperatingSystemVersion(String)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemType_OperatingSystemVersion()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='OperatingSystemVersion' namespace='##targetNamespace'"
   * @generated
   */
	String getOperatingSystemVersion();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.OperatingSystemType#getOperatingSystemVersion <em>Operating System Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System Version</em>' attribute.
   * @see #getOperatingSystemVersion()
   * @generated
   */
	void setOperatingSystemVersion(String value);

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemType_Description()
   * @model unique="false" dataType="eu.geclipse.jsdl.model.DescriptionType"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
	String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.OperatingSystemType#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
	void setDescription(String value);

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':3' processing='lax'"
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getOperatingSystemType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // OperatingSystemType