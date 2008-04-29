/**
 * <copyright>
 * </copyright>
 *
 * $Id: ApplicationType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.ApplicationType#getApplicationName <em>Application Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ApplicationType#getApplicationVersion <em>Application Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ApplicationType#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ApplicationType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ApplicationType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getApplicationType()
 * @model extendedMetaData="name='Application_Type' kind='elementOnly'"
 * @generated
 */
public interface ApplicationType extends EObject
{
  /**
   * Returns the value of the '<em><b>Application Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Application Name</em>' attribute.
   * @see #setApplicationName(String)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getApplicationType_ApplicationName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='ApplicationName' namespace='##targetNamespace'"
   * @generated
   */
	String getApplicationName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ApplicationType#getApplicationName <em>Application Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application Name</em>' attribute.
   * @see #getApplicationName()
   * @generated
   */
	void setApplicationName(String value);

  /**
   * Returns the value of the '<em><b>Application Version</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Application Version</em>' attribute.
   * @see #setApplicationVersion(String)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getApplicationType_ApplicationVersion()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='ApplicationVersion' namespace='##targetNamespace'"
   * @generated
   */
	String getApplicationVersion();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ApplicationType#getApplicationVersion <em>Application Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application Version</em>' attribute.
   * @see #getApplicationVersion()
   * @generated
   */
	void setApplicationVersion(String value);

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getApplicationType_Description()
   * @model unique="false" dataType="eu.geclipse.jsdl.model.DescriptionType"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
	String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ApplicationType#getDescription <em>Description</em>}' attribute.
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getApplicationType_Any()
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getApplicationType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // ApplicationType