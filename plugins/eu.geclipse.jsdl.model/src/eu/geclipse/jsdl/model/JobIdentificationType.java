/**
 * <copyright>
 * </copyright>
 *
 * $Id: JobIdentificationType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Job Identification Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.JobIdentificationType#getJobName <em>Job Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobIdentificationType#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobIdentificationType#getJobAnnotation <em>Job Annotation</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobIdentificationType#getJobProject <em>Job Project</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobIdentificationType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobIdentificationType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType()
 * @model extendedMetaData="name='JobIdentification_Type' kind='elementOnly'"
 * @generated
 */
public interface JobIdentificationType extends EObject
{
  /**
   * Returns the value of the '<em><b>Job Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Job Name</em>' attribute.
   * @see #setJobName(String)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType_JobName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='JobName' namespace='##targetNamespace'"
   * @generated
   */
	String getJobName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.JobIdentificationType#getJobName <em>Job Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Name</em>' attribute.
   * @see #getJobName()
   * @generated
   */
	void setJobName(String value);

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType_Description()
   * @model unique="false" dataType="eu.geclipse.jsdl.model.DescriptionType"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
	String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.JobIdentificationType#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
	void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Job Annotation</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Annotation</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Job Annotation</em>' attribute list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType_JobAnnotation()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='JobAnnotation' namespace='##targetNamespace'"
   * @generated
   */
	EList getJobAnnotation();

  /**
   * Returns the value of the '<em><b>Job Project</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Project</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Job Project</em>' attribute list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType_JobProject()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='JobProject' namespace='##targetNamespace'"
   * @generated
   */
	EList getJobProject();

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':4' processing='lax'"
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobIdentificationType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':5' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // JobIdentificationType