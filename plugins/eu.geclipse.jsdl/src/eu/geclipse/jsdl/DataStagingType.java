/**
 * <copyright>
 * </copyright>
 *
 * $Id: DataStagingType.java,v 1.1 2007/01/25 15:26:29 emstamou Exp $
 */
package eu.geclipse.jsdl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Staging Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getFileName <em>File Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getFilesystemName <em>Filesystem Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getCreationFlag <em>Creation Flag</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#isDeleteOnTermination <em>Delete On Termination</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getSource <em>Source</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.DataStagingType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType()
 * @model extendedMetaData="name='DataStaging_Type' kind='elementOnly'"
 * @generated
 */
public interface DataStagingType extends EObject {
	/**
	 * Returns the value of the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Name</em>' attribute.
	 * @see #setFileName(String)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_FileName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='FileName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFileName();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);

	/**
	 * Returns the value of the '<em><b>Filesystem Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filesystem Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filesystem Name</em>' attribute.
	 * @see #setFilesystemName(String)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_FilesystemName()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName"
	 *        extendedMetaData="kind='element' name='FilesystemName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFilesystemName();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getFilesystemName <em>Filesystem Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filesystem Name</em>' attribute.
	 * @see #getFilesystemName()
	 * @generated
	 */
	void setFilesystemName(String value);

	/**
	 * Returns the value of the '<em><b>Creation Flag</b></em>' attribute.
	 * The default value is <code>"overwrite"</code>.
	 * The literals are from the enumeration {@link eu.geclipse.jsdl.CreationFlagEnumeration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Flag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Flag</em>' attribute.
	 * @see eu.geclipse.jsdl.CreationFlagEnumeration
	 * @see #isSetCreationFlag()
	 * @see #unsetCreationFlag()
	 * @see #setCreationFlag(CreationFlagEnumeration)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_CreationFlag()
	 * @model default="overwrite" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='CreationFlag' namespace='##targetNamespace'"
	 * @generated
	 */
	CreationFlagEnumeration getCreationFlag();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getCreationFlag <em>Creation Flag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Flag</em>' attribute.
	 * @see eu.geclipse.jsdl.CreationFlagEnumeration
	 * @see #isSetCreationFlag()
	 * @see #unsetCreationFlag()
	 * @see #getCreationFlag()
	 * @generated
	 */
	void setCreationFlag(CreationFlagEnumeration value);

	/**
	 * Unsets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getCreationFlag <em>Creation Flag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCreationFlag()
	 * @see #getCreationFlag()
	 * @see #setCreationFlag(CreationFlagEnumeration)
	 * @generated
	 */
	void unsetCreationFlag();

	/**
	 * Returns whether the value of the '{@link eu.geclipse.jsdl.DataStagingType#getCreationFlag <em>Creation Flag</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Creation Flag</em>' attribute is set.
	 * @see #unsetCreationFlag()
	 * @see #getCreationFlag()
	 * @see #setCreationFlag(CreationFlagEnumeration)
	 * @generated
	 */
	boolean isSetCreationFlag();

	/**
	 * Returns the value of the '<em><b>Delete On Termination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete On Termination</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete On Termination</em>' attribute.
	 * @see #isSetDeleteOnTermination()
	 * @see #unsetDeleteOnTermination()
	 * @see #setDeleteOnTermination(boolean)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_DeleteOnTermination()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='element' name='DeleteOnTermination' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isDeleteOnTermination();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#isDeleteOnTermination <em>Delete On Termination</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete On Termination</em>' attribute.
	 * @see #isSetDeleteOnTermination()
	 * @see #unsetDeleteOnTermination()
	 * @see #isDeleteOnTermination()
	 * @generated
	 */
	void setDeleteOnTermination(boolean value);

	/**
	 * Unsets the value of the '{@link eu.geclipse.jsdl.DataStagingType#isDeleteOnTermination <em>Delete On Termination</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDeleteOnTermination()
	 * @see #isDeleteOnTermination()
	 * @see #setDeleteOnTermination(boolean)
	 * @generated
	 */
	void unsetDeleteOnTermination();

	/**
	 * Returns whether the value of the '{@link eu.geclipse.jsdl.DataStagingType#isDeleteOnTermination <em>Delete On Termination</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Delete On Termination</em>' attribute is set.
	 * @see #unsetDeleteOnTermination()
	 * @see #isDeleteOnTermination()
	 * @see #setDeleteOnTermination(boolean)
	 * @generated
	 */
	boolean isSetDeleteOnTermination();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(SourceTargetType)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_Source()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Source' namespace='##targetNamespace'"
	 * @generated
	 */
	SourceTargetType getSource();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getSource <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(SourceTargetType value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(SourceTargetType)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_Target()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Target' namespace='##targetNamespace'"
	 * @generated
	 */
	SourceTargetType getTarget();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(SourceTargetType value);

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
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':6' processing='lax'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link eu.geclipse.jsdl.DataStagingType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see eu.geclipse.jsdl.JsdlPackage#getDataStagingType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':8' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // DataStagingType