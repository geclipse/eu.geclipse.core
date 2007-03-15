/**
 * <copyright>
 * </copyright>
 *
 * $Id: JobDefinitionType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Job Definition Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.JobDefinitionType#getJobDescription <em>Job Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.JobDefinitionType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.JobDefinitionType#getId <em>Id</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.JobDefinitionType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.JsdlPackage#getJobDefinitionType()
 * @model extendedMetaData="name='JobDefinition_Type' kind='elementOnly'"
 * @generated
 */
public interface JobDefinitionType extends EObject
{
  /**
   * Returns the value of the '<em><b>Job Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Description</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Job Description</em>' containment reference.
   * @see #setJobDescription(JobDescriptionType)
   * @see eu.geclipse.jsdl.JsdlPackage#getJobDefinitionType_JobDescription()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='JobDescription' namespace='##targetNamespace'"
   * @generated
   */
	JobDescriptionType getJobDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.JobDefinitionType#getJobDescription <em>Job Description</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Description</em>' containment reference.
   * @see #getJobDescription()
   * @generated
   */
	void setJobDescription(JobDescriptionType value);

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
   * @see eu.geclipse.jsdl.JsdlPackage#getJobDefinitionType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':1' processing='lax'"
   * @generated
   */
	FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see eu.geclipse.jsdl.JsdlPackage#getJobDefinitionType_Id()
   * @model unique="false" id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
   *        extendedMetaData="kind='attribute' name='id'"
   * @generated
   */
	String getId();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.JobDefinitionType#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
	void setId(String value);

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
   * @see eu.geclipse.jsdl.JsdlPackage#getJobDefinitionType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':3' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // JobDefinitionType