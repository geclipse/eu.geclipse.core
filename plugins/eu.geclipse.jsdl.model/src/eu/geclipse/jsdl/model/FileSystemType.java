/**
 * <copyright>
 * </copyright>
 *
 * $Id: FileSystemType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File System Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getFileSystemType <em>File System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getMountPoint <em>Mount Point</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getDiskSpace <em>Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.FileSystemType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType()
 * @model extendedMetaData="name='FileSystem_Type' kind='elementOnly'"
 * @generated
 * @deprecated This interface is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base
 */
public interface FileSystemType extends EObject
{
  /**
   * Returns the value of the '<em><b>File System Type</b></em>' attribute.
   * The default value is <code>"swap"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.model.FileSystemTypeEnumeration}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File System Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>File System Type</em>' attribute.
   * @see eu.geclipse.jsdl.model.FileSystemTypeEnumeration
   * @see #isSetFileSystemType()
   * @see #unsetFileSystemType()
   * @see #setFileSystemType(FileSystemTypeEnumeration)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_FileSystemType()
   * @model default="swap" unique="false" unsettable="true"
   *        extendedMetaData="kind='element' name='FileSystemType' namespace='##targetNamespace'"
   * @generated
   */
	FileSystemTypeEnumeration getFileSystemType();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getFileSystemType <em>File System Type</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>File System Type</em>' attribute.
   * @see eu.geclipse.jsdl.model.FileSystemTypeEnumeration
   * @see #isSetFileSystemType()
   * @see #unsetFileSystemType()
   * @see #getFileSystemType()
   * @generated
   */
	void setFileSystemType(FileSystemTypeEnumeration value);

  /**
   * Unsets the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getFileSystemType <em>File System Type</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetFileSystemType()
   * @see #getFileSystemType()
   * @see #setFileSystemType(FileSystemTypeEnumeration)
   * @generated
   */
	void unsetFileSystemType();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getFileSystemType <em>File System Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>File System Type</em>' attribute is set.
   * @see #unsetFileSystemType()
   * @see #getFileSystemType()
   * @see #setFileSystemType(FileSystemTypeEnumeration)
   * @generated
   */
	boolean isSetFileSystemType();

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_Description()
   * @model unique="false" dataType="eu.geclipse.jsdl.model.DescriptionType"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
	String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
	void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Mount Point</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mount Point</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Mount Point</em>' attribute.
   * @see #setMountPoint(String)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_MountPoint()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='MountPoint' namespace='##targetNamespace'"
   * @generated
   */
	String getMountPoint();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getMountPoint <em>Mount Point</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mount Point</em>' attribute.
   * @see #getMountPoint()
   * @generated
   */
	void setMountPoint(String value);

  /**
   * Returns the value of the '<em><b>Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disk Space</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Disk Space</em>' containment reference.
   * @see #setDiskSpace(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_DiskSpace()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='DiskSpace' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getDiskSpace();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getDiskSpace <em>Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Disk Space</em>' containment reference.
   * @see #getDiskSpace()
   * @generated
   */
	void setDiskSpace(RangeValueType value);

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':4' processing='lax'"
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_Name()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName" required="true"
   *        extendedMetaData="kind='attribute' name='name'"
   * @generated
   */
	String getName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.FileSystemType#getName <em>Name</em>}' attribute.
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
   * @see eu.geclipse.jsdl.model.JsdlPackage#getFileSystemType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':6' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // FileSystemType