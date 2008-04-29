/**
 * <copyright>
 * </copyright>
 *
 * $Id: JobDescriptionType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Job Description Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.JobDescriptionType#getJobIdentification <em>Job Identification</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobDescriptionType#getApplication <em>Application</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobDescriptionType#getResources <em>Resources</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobDescriptionType#getDataStaging <em>Data Staging</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobDescriptionType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.JobDescriptionType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType()
 * @model extendedMetaData="name='JobDescription_Type' kind='elementOnly'"
 * @generated
 */
public interface JobDescriptionType extends EObject
{
  /**
   * Returns the value of the '<em><b>Job Identification</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Identification</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Job Identification</em>' containment reference.
   * @see #setJobIdentification(JobIdentificationType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType_JobIdentification()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='JobIdentification' namespace='##targetNamespace'"
   * @generated
   */
	JobIdentificationType getJobIdentification();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.JobDescriptionType#getJobIdentification <em>Job Identification</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Identification</em>' containment reference.
   * @see #getJobIdentification()
   * @generated
   */
	void setJobIdentification(JobIdentificationType value);

  /**
   * Returns the value of the '<em><b>Application</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Application</em>' containment reference.
   * @see #setApplication(ApplicationType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType_Application()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Application' namespace='##targetNamespace'"
   * @generated
   */
	ApplicationType getApplication();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.JobDescriptionType#getApplication <em>Application</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application</em>' containment reference.
   * @see #getApplication()
   * @generated
   */
	void setApplication(ApplicationType value);

  /**
   * Returns the value of the '<em><b>Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Resources</em>' containment reference.
   * @see #setResources(ResourcesType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType_Resources()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Resources' namespace='##targetNamespace'"
   * @generated
   */
	ResourcesType getResources();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.JobDescriptionType#getResources <em>Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resources</em>' containment reference.
   * @see #getResources()
   * @generated
   */
	void setResources(ResourcesType value);

  /**
   * Returns the value of the '<em><b>Data Staging</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.DataStagingType}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Staging</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Data Staging</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType_DataStaging()
   * @model type="eu.geclipse.jsdl.model.DataStagingType" containment="true"
   *        extendedMetaData="kind='element' name='DataStaging' namespace='##targetNamespace'"
   * @generated
   */
	EList getDataStaging();

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType_Any()
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getJobDescriptionType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':5' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // JobDescriptionType