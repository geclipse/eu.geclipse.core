/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getApplication <em>Application</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getApplicationName <em>Application Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getApplicationVersion <em>Application Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCandidateHosts <em>Candidate Hosts</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCPUArchitecture <em>CPU Architecture</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCPUArchitectureName <em>CPU Architecture Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCreationFlag <em>Creation Flag</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getDataStaging <em>Data Staging</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#isDeleteOnTermination <em>Delete On Termination</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getDiskSpace <em>Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#isExclusiveExecution <em>Exclusive Execution</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFileName <em>File Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFileSystem <em>File System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFilesystemName <em>Filesystem Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFileSystemType <em>File System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getHostName <em>Host Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualCPUCount <em>Individual CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualCPUSpeed <em>Individual CPU Speed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualCPUTime <em>Individual CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualDiskSpace <em>Individual Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobAnnotation <em>Job Annotation</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobDefinition <em>Job Definition</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobDescription <em>Job Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobIdentification <em>Job Identification</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobName <em>Job Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobProject <em>Job Project</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getMountPoint <em>Mount Point</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystem <em>Operating System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystemName <em>Operating System Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystemType <em>Operating System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystemVersion <em>Operating System Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getResources <em>Resources</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getSource <em>Source</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalCPUCount <em>Total CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalCPUTime <em>Total CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalDiskSpace <em>Total Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalPhysicalMemory <em>Total Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalResourceCount <em>Total Resource Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalVirtualMemory <em>Total Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.base.DocumentRoot#getURI <em>URI</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject
{
  /**
   * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mixed</em>' attribute list.
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_Mixed()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' name=':mixed'"
   * @generated
   */
  FeatureMap getMixed();

  /**
   * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>XMLNS Prefix Map</em>' map.
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_XMLNSPrefixMap()
   * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
   *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
   * @generated
   */
  EMap getXMLNSPrefixMap();

  /**
   * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>XSI Schema Location</em>' map.
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_XSISchemaLocation()
   * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
   *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
   * @generated
   */
  EMap getXSISchemaLocation();

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_Application()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Application' namespace='##targetNamespace'"
   * @generated
   */
  ApplicationType getApplication();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getApplication <em>Application</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application</em>' containment reference.
   * @see #getApplication()
   * @generated
   */
  void setApplication(ApplicationType value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_ApplicationName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='ApplicationName' namespace='##targetNamespace'"
   * @generated
   */
  String getApplicationName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getApplicationName <em>Application Name</em>}' attribute.
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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_ApplicationVersion()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='ApplicationVersion' namespace='##targetNamespace'"
   * @generated
   */
  String getApplicationVersion();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getApplicationVersion <em>Application Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Application Version</em>' attribute.
   * @see #getApplicationVersion()
   * @generated
   */
  void setApplicationVersion(String value);

  /**
   * Returns the value of the '<em><b>Candidate Hosts</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Candidate Hosts</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Candidate Hosts</em>' containment reference.
   * @see #setCandidateHosts(CandidateHostsType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_CandidateHosts()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='CandidateHosts' namespace='##targetNamespace'"
   * @generated
   */
  CandidateHostsType getCandidateHosts();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCandidateHosts <em>Candidate Hosts</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Candidate Hosts</em>' containment reference.
   * @see #getCandidateHosts()
   * @generated
   */
  void setCandidateHosts(CandidateHostsType value);

  /**
   * Returns the value of the '<em><b>CPU Architecture</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>CPU Architecture</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>CPU Architecture</em>' containment reference.
   * @see #setCPUArchitecture(CPUArchitectureType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_CPUArchitecture()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='CPUArchitecture' namespace='##targetNamespace'"
   * @generated
   */
  CPUArchitectureType getCPUArchitecture();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCPUArchitecture <em>CPU Architecture</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Architecture</em>' containment reference.
   * @see #getCPUArchitecture()
   * @generated
   */
  void setCPUArchitecture(CPUArchitectureType value);

  /**
   * Returns the value of the '<em><b>CPU Architecture Name</b></em>' attribute.
   * The default value is <code>"sparc"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.model.base.ProcessorArchitectureEnumeration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>CPU Architecture Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>CPU Architecture Name</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.ProcessorArchitectureEnumeration
   * @see #setCPUArchitectureName(ProcessorArchitectureEnumeration)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_CPUArchitectureName()
   * @model default="sparc" unique="false" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='CPUArchitectureName' namespace='##targetNamespace'"
   * @generated
   */
  ProcessorArchitectureEnumeration getCPUArchitectureName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCPUArchitectureName <em>CPU Architecture Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Architecture Name</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.ProcessorArchitectureEnumeration
   * @see #getCPUArchitectureName()
   * @generated
   */
  void setCPUArchitectureName(ProcessorArchitectureEnumeration value);

  /**
   * Returns the value of the '<em><b>Creation Flag</b></em>' attribute.
   * The default value is <code>"overwrite"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.model.base.CreationFlagEnumeration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Creation Flag</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Creation Flag</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.CreationFlagEnumeration
   * @see #setCreationFlag(CreationFlagEnumeration)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_CreationFlag()
   * @model default="overwrite" unique="false" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='CreationFlag' namespace='##targetNamespace'"
   * @generated
   */
  CreationFlagEnumeration getCreationFlag();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getCreationFlag <em>Creation Flag</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Creation Flag</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.CreationFlagEnumeration
   * @see #getCreationFlag()
   * @generated
   */
  void setCreationFlag(CreationFlagEnumeration value);

  /**
   * Returns the value of the '<em><b>Data Staging</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Data Staging</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Data Staging</em>' containment reference.
   * @see #setDataStaging(DataStagingType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_DataStaging()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='DataStaging' namespace='##targetNamespace'"
   * @generated
   */
  DataStagingType getDataStaging();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getDataStaging <em>Data Staging</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Data Staging</em>' containment reference.
   * @see #getDataStaging()
   * @generated
   */
  void setDataStaging(DataStagingType value);

  /**
   * Returns the value of the '<em><b>Delete On Termination</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Delete On Termination</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Delete On Termination</em>' attribute.
   * @see #setDeleteOnTermination(boolean)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_DeleteOnTermination()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='DeleteOnTermination' namespace='##targetNamespace'"
   * @generated
   */
  boolean isDeleteOnTermination();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#isDeleteOnTermination <em>Delete On Termination</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Delete On Termination</em>' attribute.
   * @see #isDeleteOnTermination()
   * @generated
   */
  void setDeleteOnTermination(boolean value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_Description()
   * @model unique="false" dataType="eu.geclipse.jsdl.model.base.DescriptionType" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_DiskSpace()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='DiskSpace' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getDiskSpace();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getDiskSpace <em>Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Disk Space</em>' containment reference.
   * @see #getDiskSpace()
   * @generated
   */
  void setDiskSpace(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Exclusive Execution</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exclusive Execution</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exclusive Execution</em>' attribute.
   * @see #setExclusiveExecution(boolean)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_ExclusiveExecution()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='ExclusiveExecution' namespace='##targetNamespace'"
   * @generated
   */
  boolean isExclusiveExecution();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#isExclusiveExecution <em>Exclusive Execution</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exclusive Execution</em>' attribute.
   * @see #isExclusiveExecution()
   * @generated
   */
  void setExclusiveExecution(boolean value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_FileName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='FileName' namespace='##targetNamespace'"
   * @generated
   */
  String getFileName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFileName <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Name</em>' attribute.
   * @see #getFileName()
   * @generated
   */
  void setFileName(String value);

  /**
   * Returns the value of the '<em><b>File System</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File System</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File System</em>' containment reference.
   * @see #setFileSystem(FileSystemType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_FileSystem()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='FileSystem' namespace='##targetNamespace'"
   * @generated
   */
  FileSystemType getFileSystem();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFileSystem <em>File System</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File System</em>' containment reference.
   * @see #getFileSystem()
   * @generated
   */
  void setFileSystem(FileSystemType value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_FilesystemName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='FilesystemName' namespace='##targetNamespace'"
   * @generated
   */
  String getFilesystemName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFilesystemName <em>Filesystem Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Filesystem Name</em>' attribute.
   * @see #getFilesystemName()
   * @generated
   */
  void setFilesystemName(String value);

  /**
   * Returns the value of the '<em><b>File System Type</b></em>' attribute.
   * The default value is <code>"swap"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.model.base.FileSystemTypeEnumeration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>File System Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>File System Type</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.FileSystemTypeEnumeration
   * @see #setFileSystemType(FileSystemTypeEnumeration)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_FileSystemType()
   * @model default="swap" unique="false" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='FileSystemType' namespace='##targetNamespace'"
   * @generated
   */
  FileSystemTypeEnumeration getFileSystemType();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getFileSystemType <em>File System Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>File System Type</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.FileSystemTypeEnumeration
   * @see #getFileSystemType()
   * @generated
   */
  void setFileSystemType(FileSystemTypeEnumeration value);

  /**
   * Returns the value of the '<em><b>Host Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Host Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Host Name</em>' attribute.
   * @see #setHostName(String)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_HostName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='HostName' namespace='##targetNamespace'"
   * @generated
   */
  String getHostName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getHostName <em>Host Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Host Name</em>' attribute.
   * @see #getHostName()
   * @generated
   */
  void setHostName(String value);

  /**
   * Returns the value of the '<em><b>Individual CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual CPU Count</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual CPU Count</em>' containment reference.
   * @see #setIndividualCPUCount(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualCPUCount()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualCPUCount' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualCPUCount();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualCPUCount <em>Individual CPU Count</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual CPU Count</em>' containment reference.
   * @see #getIndividualCPUCount()
   * @generated
   */
  void setIndividualCPUCount(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual CPU Speed</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual CPU Speed</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual CPU Speed</em>' containment reference.
   * @see #setIndividualCPUSpeed(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualCPUSpeed()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualCPUSpeed' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualCPUSpeed();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualCPUSpeed <em>Individual CPU Speed</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual CPU Speed</em>' containment reference.
   * @see #getIndividualCPUSpeed()
   * @generated
   */
  void setIndividualCPUSpeed(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual CPU Time</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual CPU Time</em>' containment reference.
   * @see #setIndividualCPUTime(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualCPUTime()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualCPUTime' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualCPUTime();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualCPUTime <em>Individual CPU Time</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual CPU Time</em>' containment reference.
   * @see #getIndividualCPUTime()
   * @generated
   */
  void setIndividualCPUTime(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual Disk Space</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Disk Space</em>' containment reference.
   * @see #setIndividualDiskSpace(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualDiskSpace()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualDiskSpace' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualDiskSpace();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualDiskSpace <em>Individual Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Disk Space</em>' containment reference.
   * @see #getIndividualDiskSpace()
   * @generated
   */
  void setIndividualDiskSpace(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Network Bandwidth</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual Network Bandwidth</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Network Bandwidth</em>' containment reference.
   * @see #setIndividualNetworkBandwidth(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualNetworkBandwidth()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualNetworkBandwidth' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualNetworkBandwidth();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Network Bandwidth</em>' containment reference.
   * @see #getIndividualNetworkBandwidth()
   * @generated
   */
  void setIndividualNetworkBandwidth(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual Physical Memory</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Physical Memory</em>' containment reference.
   * @see #setIndividualPhysicalMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualPhysicalMemory()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualPhysicalMemory' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualPhysicalMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Physical Memory</em>' containment reference.
   * @see #getIndividualPhysicalMemory()
   * @generated
   */
  void setIndividualPhysicalMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Individual Virtual Memory</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Virtual Memory</em>' containment reference.
   * @see #setIndividualVirtualMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_IndividualVirtualMemory()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='IndividualVirtualMemory' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getIndividualVirtualMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Virtual Memory</em>' containment reference.
   * @see #getIndividualVirtualMemory()
   * @generated
   */
  void setIndividualVirtualMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Job Annotation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Job Annotation</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Job Annotation</em>' attribute.
   * @see #setJobAnnotation(String)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_JobAnnotation()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobAnnotation' namespace='##targetNamespace'"
   * @generated
   */
  String getJobAnnotation();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobAnnotation <em>Job Annotation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Annotation</em>' attribute.
   * @see #getJobAnnotation()
   * @generated
   */
  void setJobAnnotation(String value);

  /**
   * Returns the value of the '<em><b>Job Definition</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Job Definition</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Job Definition</em>' containment reference.
   * @see #setJobDefinition(JobDefinitionType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_JobDefinition()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobDefinition' namespace='##targetNamespace'"
   * @generated
   */
  JobDefinitionType getJobDefinition();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobDefinition <em>Job Definition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Definition</em>' containment reference.
   * @see #getJobDefinition()
   * @generated
   */
  void setJobDefinition(JobDefinitionType value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_JobDescription()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobDescription' namespace='##targetNamespace'"
   * @generated
   */
  JobDescriptionType getJobDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobDescription <em>Job Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Description</em>' containment reference.
   * @see #getJobDescription()
   * @generated
   */
  void setJobDescription(JobDescriptionType value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_JobIdentification()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobIdentification' namespace='##targetNamespace'"
   * @generated
   */
  JobIdentificationType getJobIdentification();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobIdentification <em>Job Identification</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Identification</em>' containment reference.
   * @see #getJobIdentification()
   * @generated
   */
  void setJobIdentification(JobIdentificationType value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_JobName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobName' namespace='##targetNamespace'"
   * @generated
   */
  String getJobName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobName <em>Job Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Name</em>' attribute.
   * @see #getJobName()
   * @generated
   */
  void setJobName(String value);

  /**
   * Returns the value of the '<em><b>Job Project</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Job Project</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Job Project</em>' attribute.
   * @see #setJobProject(String)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_JobProject()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobProject' namespace='##targetNamespace'"
   * @generated
   */
  String getJobProject();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getJobProject <em>Job Project</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Job Project</em>' attribute.
   * @see #getJobProject()
   * @generated
   */
  void setJobProject(String value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_MountPoint()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='MountPoint' namespace='##targetNamespace'"
   * @generated
   */
  String getMountPoint();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getMountPoint <em>Mount Point</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Mount Point</em>' attribute.
   * @see #getMountPoint()
   * @generated
   */
  void setMountPoint(String value);

  /**
   * Returns the value of the '<em><b>Operating System</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operating System</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operating System</em>' containment reference.
   * @see #setOperatingSystem(OperatingSystemType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_OperatingSystem()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='OperatingSystem' namespace='##targetNamespace'"
   * @generated
   */
  OperatingSystemType getOperatingSystem();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystem <em>Operating System</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System</em>' containment reference.
   * @see #getOperatingSystem()
   * @generated
   */
  void setOperatingSystem(OperatingSystemType value);

  /**
   * Returns the value of the '<em><b>Operating System Name</b></em>' attribute.
   * The default value is <code>"Unknown"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.model.base.OperatingSystemTypeEnumeration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operating System Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operating System Name</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.OperatingSystemTypeEnumeration
   * @see #setOperatingSystemName(OperatingSystemTypeEnumeration)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_OperatingSystemName()
   * @model default="Unknown" unique="false" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='OperatingSystemName' namespace='##targetNamespace'"
   * @generated
   */
  OperatingSystemTypeEnumeration getOperatingSystemName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystemName <em>Operating System Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System Name</em>' attribute.
   * @see eu.geclipse.jsdl.model.base.OperatingSystemTypeEnumeration
   * @see #getOperatingSystemName()
   * @generated
   */
  void setOperatingSystemName(OperatingSystemTypeEnumeration value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_OperatingSystemType()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='OperatingSystemType' namespace='##targetNamespace'"
   * @generated
   */
  OperatingSystemTypeType getOperatingSystemType();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystemType <em>Operating System Type</em>}' containment reference.
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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_OperatingSystemVersion()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='OperatingSystemVersion' namespace='##targetNamespace'"
   * @generated
   */
  String getOperatingSystemVersion();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getOperatingSystemVersion <em>Operating System Version</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System Version</em>' attribute.
   * @see #getOperatingSystemVersion()
   * @generated
   */
  void setOperatingSystemVersion(String value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_Resources()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Resources' namespace='##targetNamespace'"
   * @generated
   */
  ResourcesType getResources();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getResources <em>Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resources</em>' containment reference.
   * @see #getResources()
   * @generated
   */
  void setResources(ResourcesType value);

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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_Source()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Source' namespace='##targetNamespace'"
   * @generated
   */
  SourceTargetType getSource();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getSource <em>Source</em>}' containment reference.
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
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_Target()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Target' namespace='##targetNamespace'"
   * @generated
   */
  SourceTargetType getTarget();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTarget <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' containment reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(SourceTargetType value);

  /**
   * Returns the value of the '<em><b>Total CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Total CPU Count</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Total CPU Count</em>' containment reference.
   * @see #setTotalCPUCount(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_TotalCPUCount()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TotalCPUCount' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getTotalCPUCount();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalCPUCount <em>Total CPU Count</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total CPU Count</em>' containment reference.
   * @see #getTotalCPUCount()
   * @generated
   */
  void setTotalCPUCount(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Total CPU Time</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Total CPU Time</em>' containment reference.
   * @see #setTotalCPUTime(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_TotalCPUTime()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TotalCPUTime' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getTotalCPUTime();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalCPUTime <em>Total CPU Time</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total CPU Time</em>' containment reference.
   * @see #getTotalCPUTime()
   * @generated
   */
  void setTotalCPUTime(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Total Disk Space</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Total Disk Space</em>' containment reference.
   * @see #setTotalDiskSpace(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_TotalDiskSpace()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TotalDiskSpace' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getTotalDiskSpace();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalDiskSpace <em>Total Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Disk Space</em>' containment reference.
   * @see #getTotalDiskSpace()
   * @generated
   */
  void setTotalDiskSpace(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Total Physical Memory</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Total Physical Memory</em>' containment reference.
   * @see #setTotalPhysicalMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_TotalPhysicalMemory()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TotalPhysicalMemory' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getTotalPhysicalMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalPhysicalMemory <em>Total Physical Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Physical Memory</em>' containment reference.
   * @see #getTotalPhysicalMemory()
   * @generated
   */
  void setTotalPhysicalMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Resource Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Total Resource Count</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Total Resource Count</em>' containment reference.
   * @see #setTotalResourceCount(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_TotalResourceCount()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TotalResourceCount' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getTotalResourceCount();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalResourceCount <em>Total Resource Count</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Resource Count</em>' containment reference.
   * @see #getTotalResourceCount()
   * @generated
   */
  void setTotalResourceCount(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Total Virtual Memory</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Total Virtual Memory</em>' containment reference.
   * @see #setTotalVirtualMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_TotalVirtualMemory()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='TotalVirtualMemory' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getTotalVirtualMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getTotalVirtualMemory <em>Total Virtual Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Virtual Memory</em>' containment reference.
   * @see #getTotalVirtualMemory()
   * @generated
   */
  void setTotalVirtualMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>URI</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>URI</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>URI</em>' attribute.
   * @see #setURI(String)
   * @see eu.geclipse.jsdl.model.base.JsdlPackage#getDocumentRoot_URI()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='URI' namespace='##targetNamespace'"
   * @generated
   */
  String getURI();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.base.DocumentRoot#getURI <em>URI</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>URI</em>' attribute.
   * @see #getURI()
   * @generated
   */
  void setURI(String value);

} // DocumentRoot
