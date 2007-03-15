/**
 * <copyright>
 * </copyright>
 *
 * $Id: JsdlPackage.java,v 1.3 2007/03/07 10:41:40 nickl Exp $
 */
package eu.geclipse.jsdl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.JsdlFactory
 * @model kind="package"
 * @generated
 */
public interface JsdlPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNAME = "jsdl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_URI = "http://schemas.ggf.org/jsdl/2005/11/jsdl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_PREFIX = "jsdl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	JsdlPackage eINSTANCE = eu.geclipse.jsdl.impl.JsdlPackageImpl.init();

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.ApplicationTypeImpl <em>Application Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.ApplicationTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getApplicationType()
   * @generated
   */
	int APPLICATION_TYPE = 0;

  /**
   * The feature id for the '<em><b>Application Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int APPLICATION_TYPE__APPLICATION_NAME = 0;

  /**
   * The feature id for the '<em><b>Application Version</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int APPLICATION_TYPE__APPLICATION_VERSION = 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int APPLICATION_TYPE__DESCRIPTION = 2;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int APPLICATION_TYPE__ANY = 3;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int APPLICATION_TYPE__ANY_ATTRIBUTE = 4;

  /**
   * The number of structural features of the '<em>Application Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int APPLICATION_TYPE_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.BoundaryTypeImpl <em>Boundary Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.BoundaryTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getBoundaryType()
   * @generated
   */
	int BOUNDARY_TYPE = 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int BOUNDARY_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Exclusive Bound</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int BOUNDARY_TYPE__EXCLUSIVE_BOUND = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int BOUNDARY_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Boundary Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int BOUNDARY_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.CandidateHostsTypeImpl <em>Candidate Hosts Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.CandidateHostsTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCandidateHostsType()
   * @generated
   */
	int CANDIDATE_HOSTS_TYPE = 2;

  /**
   * The feature id for the '<em><b>Host Name</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CANDIDATE_HOSTS_TYPE__HOST_NAME = 0;

  /**
   * The number of structural features of the '<em>Candidate Hosts Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CANDIDATE_HOSTS_TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.CPUArchitectureTypeImpl <em>CPU Architecture Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.CPUArchitectureTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCPUArchitectureType()
   * @generated
   */
	int CPU_ARCHITECTURE_TYPE = 3;

  /**
   * The feature id for the '<em><b>CPU Architecture Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME = 0;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CPU_ARCHITECTURE_TYPE__ANY = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>CPU Architecture Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int CPU_ARCHITECTURE_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.DataStagingTypeImpl <em>Data Staging Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.DataStagingTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getDataStagingType()
   * @generated
   */
	int DATA_STAGING_TYPE = 4;

  /**
   * The feature id for the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__FILE_NAME = 0;

  /**
   * The feature id for the '<em><b>Filesystem Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__FILESYSTEM_NAME = 1;

  /**
   * The feature id for the '<em><b>Creation Flag</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__CREATION_FLAG = 2;

  /**
   * The feature id for the '<em><b>Delete On Termination</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__DELETE_ON_TERMINATION = 3;

  /**
   * The feature id for the '<em><b>Source</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__SOURCE = 4;

  /**
   * The feature id for the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__TARGET = 5;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__ANY = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__NAME = 7;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE__ANY_ATTRIBUTE = 8;

  /**
   * The number of structural features of the '<em>Data Staging Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DATA_STAGING_TYPE_FEATURE_COUNT = 9;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.DocumentRootImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getDocumentRoot()
   * @generated
   */
	int DOCUMENT_ROOT = 5;

  /**
   * The feature id for the '<em><b>Mixed</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__MIXED = 0;

  /**
   * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

  /**
   * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

  /**
   * The feature id for the '<em><b>Application</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__APPLICATION = 3;

  /**
   * The feature id for the '<em><b>Application Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__APPLICATION_NAME = 4;

  /**
   * The feature id for the '<em><b>Application Version</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__APPLICATION_VERSION = 5;

  /**
   * The feature id for the '<em><b>Candidate Hosts</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__CANDIDATE_HOSTS = 6;

  /**
   * The feature id for the '<em><b>CPU Architecture</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__CPU_ARCHITECTURE = 7;

  /**
   * The feature id for the '<em><b>CPU Architecture Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME = 8;

  /**
   * The feature id for the '<em><b>Creation Flag</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__CREATION_FLAG = 9;

  /**
   * The feature id for the '<em><b>Data Staging</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__DATA_STAGING = 10;

  /**
   * The feature id for the '<em><b>Delete On Termination</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__DELETE_ON_TERMINATION = 11;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__DESCRIPTION = 12;

  /**
   * The feature id for the '<em><b>Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__DISK_SPACE = 13;

  /**
   * The feature id for the '<em><b>Exclusive Execution</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__EXCLUSIVE_EXECUTION = 14;

  /**
   * The feature id for the '<em><b>File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__FILE_NAME = 15;

  /**
   * The feature id for the '<em><b>File System</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__FILE_SYSTEM = 16;

  /**
   * The feature id for the '<em><b>Filesystem Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__FILESYSTEM_NAME = 17;

  /**
   * The feature id for the '<em><b>File System Type</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__FILE_SYSTEM_TYPE = 18;

  /**
   * The feature id for the '<em><b>Host Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__HOST_NAME = 19;

  /**
   * The feature id for the '<em><b>Individual CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT = 20;

  /**
   * The feature id for the '<em><b>Individual CPU Speed</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED = 21;

  /**
   * The feature id for the '<em><b>Individual CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME = 22;

  /**
   * The feature id for the '<em><b>Individual Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE = 23;

  /**
   * The feature id for the '<em><b>Individual Network Bandwidth</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH = 24;

  /**
   * The feature id for the '<em><b>Individual Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY = 25;

  /**
   * The feature id for the '<em><b>Individual Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY = 26;

  /**
   * The feature id for the '<em><b>Job Annotation</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__JOB_ANNOTATION = 27;

  /**
   * The feature id for the '<em><b>Job Definition</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__JOB_DEFINITION = 28;

  /**
   * The feature id for the '<em><b>Job Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__JOB_DESCRIPTION = 29;

  /**
   * The feature id for the '<em><b>Job Identification</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__JOB_IDENTIFICATION = 30;

  /**
   * The feature id for the '<em><b>Job Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__JOB_NAME = 31;

  /**
   * The feature id for the '<em><b>Job Project</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__JOB_PROJECT = 32;

  /**
   * The feature id for the '<em><b>Mount Point</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__MOUNT_POINT = 33;

  /**
   * The feature id for the '<em><b>Operating System</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__OPERATING_SYSTEM = 34;

  /**
   * The feature id for the '<em><b>Operating System Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__OPERATING_SYSTEM_NAME = 35;

  /**
   * The feature id for the '<em><b>Operating System Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE = 36;

  /**
   * The feature id for the '<em><b>Operating System Version</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION = 37;

  /**
   * The feature id for the '<em><b>Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__RESOURCES = 38;

  /**
   * The feature id for the '<em><b>Source</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__SOURCE = 39;

  /**
   * The feature id for the '<em><b>Target</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TARGET = 40;

  /**
   * The feature id for the '<em><b>Total CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TOTAL_CPU_COUNT = 41;

  /**
   * The feature id for the '<em><b>Total CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TOTAL_CPU_TIME = 42;

  /**
   * The feature id for the '<em><b>Total Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TOTAL_DISK_SPACE = 43;

  /**
   * The feature id for the '<em><b>Total Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY = 44;

  /**
   * The feature id for the '<em><b>Total Resource Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT = 45;

  /**
   * The feature id for the '<em><b>Total Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY = 46;

  /**
   * The feature id for the '<em><b>URI</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__URI = 47;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT_FEATURE_COUNT = 48;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.ExactTypeImpl <em>Exact Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.ExactTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getExactType()
   * @generated
   */
	int EXACT_TYPE = 6;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int EXACT_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Epsilon</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int EXACT_TYPE__EPSILON = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int EXACT_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Exact Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int EXACT_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.FileSystemTypeImpl <em>File System Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.FileSystemTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getFileSystemType()
   * @generated
   */
	int FILE_SYSTEM_TYPE = 7;

  /**
   * The feature id for the '<em><b>File System Type</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Mount Point</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__MOUNT_POINT = 2;

  /**
   * The feature id for the '<em><b>Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__DISK_SPACE = 3;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__ANY = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__NAME = 5;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE__ANY_ATTRIBUTE = 6;

  /**
   * The number of structural features of the '<em>File System Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_SYSTEM_TYPE_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.JobDefinitionTypeImpl <em>Job Definition Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.JobDefinitionTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getJobDefinitionType()
   * @generated
   */
	int JOB_DEFINITION_TYPE = 8;

  /**
   * The feature id for the '<em><b>Job Description</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DEFINITION_TYPE__JOB_DESCRIPTION = 0;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DEFINITION_TYPE__ANY = 1;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DEFINITION_TYPE__ID = 2;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DEFINITION_TYPE__ANY_ATTRIBUTE = 3;

  /**
   * The number of structural features of the '<em>Job Definition Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DEFINITION_TYPE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl <em>Job Description Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.JobDescriptionTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getJobDescriptionType()
   * @generated
   */
	int JOB_DESCRIPTION_TYPE = 9;

  /**
   * The feature id for the '<em><b>Job Identification</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION = 0;

  /**
   * The feature id for the '<em><b>Application</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE__APPLICATION = 1;

  /**
   * The feature id for the '<em><b>Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE__RESOURCES = 2;

  /**
   * The feature id for the '<em><b>Data Staging</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE__DATA_STAGING = 3;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE__ANY = 4;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE = 5;

  /**
   * The number of structural features of the '<em>Job Description Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_DESCRIPTION_TYPE_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.JobIdentificationTypeImpl <em>Job Identification Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.JobIdentificationTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getJobIdentificationType()
   * @generated
   */
	int JOB_IDENTIFICATION_TYPE = 10;

  /**
   * The feature id for the '<em><b>Job Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE__JOB_NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Job Annotation</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION = 2;

  /**
   * The feature id for the '<em><b>Job Project</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE__JOB_PROJECT = 3;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE__ANY = 4;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE = 5;

  /**
   * The number of structural features of the '<em>Job Identification Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int JOB_IDENTIFICATION_TYPE_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.OperatingSystemTypeImpl <em>Operating System Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.OperatingSystemTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemType()
   * @generated
   */
	int OPERATING_SYSTEM_TYPE = 11;

  /**
   * The feature id for the '<em><b>Operating System Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE = 0;

  /**
   * The feature id for the '<em><b>Operating System Version</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION = 1;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE__DESCRIPTION = 2;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE__ANY = 3;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE = 4;

  /**
   * The number of structural features of the '<em>Operating System Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl <em>Operating System Type Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemTypeType()
   * @generated
   */
	int OPERATING_SYSTEM_TYPE_TYPE = 12;

  /**
   * The feature id for the '<em><b>Operating System Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME = 0;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE_TYPE__ANY = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Operating System Type Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int OPERATING_SYSTEM_TYPE_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.RangeTypeImpl <em>Range Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.RangeTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getRangeType()
   * @generated
   */
	int RANGE_TYPE = 13;

  /**
   * The feature id for the '<em><b>Lower Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_TYPE__LOWER_BOUND = 0;

  /**
   * The feature id for the '<em><b>Upper Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_TYPE__UPPER_BOUND = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Range Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl <em>Range Value Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.RangeValueTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getRangeValueType()
   * @generated
   */
	int RANGE_VALUE_TYPE = 14;

  /**
   * The feature id for the '<em><b>Upper Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE = 0;

  /**
   * The feature id for the '<em><b>Lower Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE = 1;

  /**
   * The feature id for the '<em><b>Exact</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_VALUE_TYPE__EXACT = 2;

  /**
   * The feature id for the '<em><b>Range</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_VALUE_TYPE__RANGE = 3;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_VALUE_TYPE__ANY_ATTRIBUTE = 4;

  /**
   * The number of structural features of the '<em>Range Value Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RANGE_VALUE_TYPE_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl <em>Resources Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.ResourcesTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getResourcesType()
   * @generated
   */
	int RESOURCES_TYPE = 15;

  /**
   * The feature id for the '<em><b>Candidate Hosts</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__CANDIDATE_HOSTS = 0;

  /**
   * The feature id for the '<em><b>File System</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__FILE_SYSTEM = 1;

  /**
   * The feature id for the '<em><b>Exclusive Execution</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__EXCLUSIVE_EXECUTION = 2;

  /**
   * The feature id for the '<em><b>Operating System</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__OPERATING_SYSTEM = 3;

  /**
   * The feature id for the '<em><b>CPU Architecture</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__CPU_ARCHITECTURE = 4;

  /**
   * The feature id for the '<em><b>Individual CPU Speed</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED = 5;

  /**
   * The feature id for the '<em><b>Individual CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_CPU_TIME = 6;

  /**
   * The feature id for the '<em><b>Individual CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT = 7;

  /**
   * The feature id for the '<em><b>Individual Network Bandwidth</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH = 8;

  /**
   * The feature id for the '<em><b>Individual Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY = 9;

  /**
   * The feature id for the '<em><b>Individual Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY = 10;

  /**
   * The feature id for the '<em><b>Individual Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE = 11;

  /**
   * The feature id for the '<em><b>Total CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__TOTAL_CPU_TIME = 12;

  /**
   * The feature id for the '<em><b>Total CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__TOTAL_CPU_COUNT = 13;

  /**
   * The feature id for the '<em><b>Total Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY = 14;

  /**
   * The feature id for the '<em><b>Total Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY = 15;

  /**
   * The feature id for the '<em><b>Total Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__TOTAL_DISK_SPACE = 16;

  /**
   * The feature id for the '<em><b>Total Resource Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__TOTAL_RESOURCE_COUNT = 17;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__ANY = 18;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE__ANY_ATTRIBUTE = 19;

  /**
   * The number of structural features of the '<em>Resources Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int RESOURCES_TYPE_FEATURE_COUNT = 20;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.impl.SourceTargetTypeImpl <em>Source Target Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.impl.SourceTargetTypeImpl
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getSourceTargetType()
   * @generated
   */
	int SOURCE_TARGET_TYPE = 16;

  /**
   * The feature id for the '<em><b>URI</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int SOURCE_TARGET_TYPE__URI = 0;

  /**
   * The feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int SOURCE_TARGET_TYPE__ANY = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int SOURCE_TARGET_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Source Target Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int SOURCE_TARGET_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.CreationFlagEnumeration <em>Creation Flag Enumeration</em>}' enum.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.CreationFlagEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCreationFlagEnumeration()
   * @generated
   */
	int CREATION_FLAG_ENUMERATION = 17;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.FileSystemTypeEnumeration <em>File System Type Enumeration</em>}' enum.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.FileSystemTypeEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getFileSystemTypeEnumeration()
   * @generated
   */
	int FILE_SYSTEM_TYPE_ENUMERATION = 18;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.OperatingSystemTypeEnumeration <em>Operating System Type Enumeration</em>}' enum.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.OperatingSystemTypeEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemTypeEnumeration()
   * @generated
   */
	int OPERATING_SYSTEM_TYPE_ENUMERATION = 19;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.ProcessorArchitectureEnumeration <em>Processor Architecture Enumeration</em>}' enum.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getProcessorArchitectureEnumeration()
   * @generated
   */
	int PROCESSOR_ARCHITECTURE_ENUMERATION = 20;

  /**
   * The meta object id for the '<em>Creation Flag Enumeration Object</em>' data type.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.CreationFlagEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCreationFlagEnumerationObject()
   * @generated
   */
	int CREATION_FLAG_ENUMERATION_OBJECT = 21;

  /**
   * The meta object id for the '<em>Description Type</em>' data type.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see java.lang.String
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getDescriptionType()
   * @generated
   */
	int DESCRIPTION_TYPE = 22;

  /**
   * The meta object id for the '<em>File System Type Enumeration Object</em>' data type.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.FileSystemTypeEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getFileSystemTypeEnumerationObject()
   * @generated
   */
	int FILE_SYSTEM_TYPE_ENUMERATION_OBJECT = 23;

  /**
   * The meta object id for the '<em>Operating System Type Enumeration Object</em>' data type.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.OperatingSystemTypeEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemTypeEnumerationObject()
   * @generated
   */
	int OPERATING_SYSTEM_TYPE_ENUMERATION_OBJECT = 24;

  /**
   * The meta object id for the '<em>Processor Architecture Enumeration Object</em>' data type.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
   * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getProcessorArchitectureEnumerationObject()
   * @generated
   */
	int PROCESSOR_ARCHITECTURE_ENUMERATION_OBJECT = 25;


  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.ApplicationType <em>Application Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Application Type</em>'.
   * @see eu.geclipse.jsdl.ApplicationType
   * @generated
   */
	EClass getApplicationType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.ApplicationType#getApplicationName <em>Application Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application Name</em>'.
   * @see eu.geclipse.jsdl.ApplicationType#getApplicationName()
   * @see #getApplicationType()
   * @generated
   */
	EAttribute getApplicationType_ApplicationName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.ApplicationType#getApplicationVersion <em>Application Version</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application Version</em>'.
   * @see eu.geclipse.jsdl.ApplicationType#getApplicationVersion()
   * @see #getApplicationType()
   * @generated
   */
	EAttribute getApplicationType_ApplicationVersion();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.ApplicationType#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.jsdl.ApplicationType#getDescription()
   * @see #getApplicationType()
   * @generated
   */
	EAttribute getApplicationType_Description();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.ApplicationType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.ApplicationType#getAny()
   * @see #getApplicationType()
   * @generated
   */
	EAttribute getApplicationType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.ApplicationType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.ApplicationType#getAnyAttribute()
   * @see #getApplicationType()
   * @generated
   */
	EAttribute getApplicationType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.BoundaryType <em>Boundary Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boundary Type</em>'.
   * @see eu.geclipse.jsdl.BoundaryType
   * @generated
   */
	EClass getBoundaryType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.BoundaryType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.BoundaryType#getValue()
   * @see #getBoundaryType()
   * @generated
   */
	EAttribute getBoundaryType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.BoundaryType#isExclusiveBound <em>Exclusive Bound</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exclusive Bound</em>'.
   * @see eu.geclipse.jsdl.BoundaryType#isExclusiveBound()
   * @see #getBoundaryType()
   * @generated
   */
	EAttribute getBoundaryType_ExclusiveBound();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.BoundaryType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.BoundaryType#getAnyAttribute()
   * @see #getBoundaryType()
   * @generated
   */
	EAttribute getBoundaryType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.CandidateHostsType <em>Candidate Hosts Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Candidate Hosts Type</em>'.
   * @see eu.geclipse.jsdl.CandidateHostsType
   * @generated
   */
	EClass getCandidateHostsType();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.CandidateHostsType#getHostName <em>Host Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Host Name</em>'.
   * @see eu.geclipse.jsdl.CandidateHostsType#getHostName()
   * @see #getCandidateHostsType()
   * @generated
   */
	EAttribute getCandidateHostsType_HostName();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.CPUArchitectureType <em>CPU Architecture Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>CPU Architecture Type</em>'.
   * @see eu.geclipse.jsdl.CPUArchitectureType
   * @generated
   */
	EClass getCPUArchitectureType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.CPUArchitectureType#getCPUArchitectureName <em>CPU Architecture Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>CPU Architecture Name</em>'.
   * @see eu.geclipse.jsdl.CPUArchitectureType#getCPUArchitectureName()
   * @see #getCPUArchitectureType()
   * @generated
   */
	EAttribute getCPUArchitectureType_CPUArchitectureName();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.CPUArchitectureType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.CPUArchitectureType#getAny()
   * @see #getCPUArchitectureType()
   * @generated
   */
	EAttribute getCPUArchitectureType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.CPUArchitectureType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.CPUArchitectureType#getAnyAttribute()
   * @see #getCPUArchitectureType()
   * @generated
   */
	EAttribute getCPUArchitectureType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.DataStagingType <em>Data Staging Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Data Staging Type</em>'.
   * @see eu.geclipse.jsdl.DataStagingType
   * @generated
   */
	EClass getDataStagingType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DataStagingType#getFileName <em>File Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File Name</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getFileName()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_FileName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DataStagingType#getFilesystemName <em>Filesystem Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Filesystem Name</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getFilesystemName()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_FilesystemName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DataStagingType#getCreationFlag <em>Creation Flag</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Creation Flag</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getCreationFlag()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_CreationFlag();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DataStagingType#isDeleteOnTermination <em>Delete On Termination</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Delete On Termination</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#isDeleteOnTermination()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_DeleteOnTermination();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DataStagingType#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Source</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getSource()
   * @see #getDataStagingType()
   * @generated
   */
	EReference getDataStagingType_Source();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DataStagingType#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Target</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getTarget()
   * @see #getDataStagingType()
   * @generated
   */
	EReference getDataStagingType_Target();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.DataStagingType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getAny()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_Any();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DataStagingType#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getName()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_Name();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.DataStagingType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.DataStagingType#getAnyAttribute()
   * @see #getDataStagingType()
   * @generated
   */
	EAttribute getDataStagingType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot
   * @generated
   */
	EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getApplication <em>Application</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Application</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getApplication()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Application();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getApplicationName <em>Application Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getApplicationName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_ApplicationName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getApplicationVersion <em>Application Version</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Application Version</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getApplicationVersion()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_ApplicationVersion();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getCandidateHosts <em>Candidate Hosts</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Candidate Hosts</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getCandidateHosts()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_CandidateHosts();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getCPUArchitecture <em>CPU Architecture</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>CPU Architecture</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getCPUArchitecture()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_CPUArchitecture();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getCPUArchitectureName <em>CPU Architecture Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>CPU Architecture Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getCPUArchitectureName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_CPUArchitectureName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getCreationFlag <em>Creation Flag</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Creation Flag</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getCreationFlag()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_CreationFlag();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getDataStaging <em>Data Staging</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Data Staging</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getDataStaging()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_DataStaging();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#isDeleteOnTermination <em>Delete On Termination</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Delete On Termination</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#isDeleteOnTermination()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_DeleteOnTermination();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getDescription()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_Description();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getDiskSpace <em>Disk Space</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Disk Space</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getDiskSpace()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_DiskSpace();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#isExclusiveExecution <em>Exclusive Execution</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exclusive Execution</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#isExclusiveExecution()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_ExclusiveExecution();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getFileName <em>File Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getFileName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_FileName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getFileSystem <em>File System</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>File System</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getFileSystem()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_FileSystem();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getFilesystemName <em>Filesystem Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Filesystem Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getFilesystemName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_FilesystemName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getFileSystemType <em>File System Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File System Type</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getFileSystemType()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_FileSystemType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getHostName <em>Host Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Host Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getHostName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_HostName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualCPUCount <em>Individual CPU Count</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual CPU Count</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualCPUCount()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualCPUCount();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualCPUSpeed <em>Individual CPU Speed</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual CPU Speed</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualCPUSpeed()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualCPUSpeed();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualCPUTime <em>Individual CPU Time</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual CPU Time</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualCPUTime()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualCPUTime();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualDiskSpace <em>Individual Disk Space</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Disk Space</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualDiskSpace()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualDiskSpace();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Network Bandwidth</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualNetworkBandwidth()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualNetworkBandwidth();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Physical Memory</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualPhysicalMemory()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualPhysicalMemory();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Virtual Memory</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getIndividualVirtualMemory()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_IndividualVirtualMemory();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getJobAnnotation <em>Job Annotation</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Job Annotation</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getJobAnnotation()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_JobAnnotation();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getJobDefinition <em>Job Definition</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Job Definition</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getJobDefinition()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_JobDefinition();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getJobDescription <em>Job Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Job Description</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getJobDescription()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_JobDescription();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getJobIdentification <em>Job Identification</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Job Identification</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getJobIdentification()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_JobIdentification();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getJobName <em>Job Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Job Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getJobName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_JobName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getJobProject <em>Job Project</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Job Project</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getJobProject()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_JobProject();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getMountPoint <em>Mount Point</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mount Point</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getMountPoint()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_MountPoint();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getOperatingSystem <em>Operating System</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operating System</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getOperatingSystem()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_OperatingSystem();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getOperatingSystemName <em>Operating System Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operating System Name</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getOperatingSystemName()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_OperatingSystemName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getOperatingSystemType <em>Operating System Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operating System Type</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getOperatingSystemType()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_OperatingSystemType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getOperatingSystemVersion <em>Operating System Version</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operating System Version</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getOperatingSystemVersion()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_OperatingSystemVersion();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getResources <em>Resources</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Resources</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getResources()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Resources();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Source</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getSource()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Source();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Target</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTarget()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Target();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTotalCPUCount <em>Total CPU Count</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total CPU Count</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTotalCPUCount()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_TotalCPUCount();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTotalCPUTime <em>Total CPU Time</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total CPU Time</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTotalCPUTime()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_TotalCPUTime();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTotalDiskSpace <em>Total Disk Space</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Disk Space</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTotalDiskSpace()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_TotalDiskSpace();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTotalPhysicalMemory <em>Total Physical Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Physical Memory</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTotalPhysicalMemory()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_TotalPhysicalMemory();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTotalResourceCount <em>Total Resource Count</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Resource Count</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTotalResourceCount()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_TotalResourceCount();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.DocumentRoot#getTotalVirtualMemory <em>Total Virtual Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Virtual Memory</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getTotalVirtualMemory()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_TotalVirtualMemory();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.DocumentRoot#getURI <em>URI</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>URI</em>'.
   * @see eu.geclipse.jsdl.DocumentRoot#getURI()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_URI();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.ExactType <em>Exact Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Exact Type</em>'.
   * @see eu.geclipse.jsdl.ExactType
   * @generated
   */
	EClass getExactType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.ExactType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.ExactType#getValue()
   * @see #getExactType()
   * @generated
   */
	EAttribute getExactType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.ExactType#getEpsilon <em>Epsilon</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Epsilon</em>'.
   * @see eu.geclipse.jsdl.ExactType#getEpsilon()
   * @see #getExactType()
   * @generated
   */
	EAttribute getExactType_Epsilon();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.ExactType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.ExactType#getAnyAttribute()
   * @see #getExactType()
   * @generated
   */
	EAttribute getExactType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.FileSystemType <em>File System Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>File System Type</em>'.
   * @see eu.geclipse.jsdl.FileSystemType
   * @generated
   */
	EClass getFileSystemType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.FileSystemType#getFileSystemType <em>File System Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>File System Type</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getFileSystemType()
   * @see #getFileSystemType()
   * @generated
   */
	EAttribute getFileSystemType_FileSystemType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.FileSystemType#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getDescription()
   * @see #getFileSystemType()
   * @generated
   */
	EAttribute getFileSystemType_Description();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.FileSystemType#getMountPoint <em>Mount Point</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Mount Point</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getMountPoint()
   * @see #getFileSystemType()
   * @generated
   */
	EAttribute getFileSystemType_MountPoint();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.FileSystemType#getDiskSpace <em>Disk Space</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Disk Space</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getDiskSpace()
   * @see #getFileSystemType()
   * @generated
   */
	EReference getFileSystemType_DiskSpace();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.FileSystemType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getAny()
   * @see #getFileSystemType()
   * @generated
   */
	EAttribute getFileSystemType_Any();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.FileSystemType#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getName()
   * @see #getFileSystemType()
   * @generated
   */
	EAttribute getFileSystemType_Name();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.FileSystemType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.FileSystemType#getAnyAttribute()
   * @see #getFileSystemType()
   * @generated
   */
	EAttribute getFileSystemType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.JobDefinitionType <em>Job Definition Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Job Definition Type</em>'.
   * @see eu.geclipse.jsdl.JobDefinitionType
   * @generated
   */
	EClass getJobDefinitionType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.JobDefinitionType#getJobDescription <em>Job Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Job Description</em>'.
   * @see eu.geclipse.jsdl.JobDefinitionType#getJobDescription()
   * @see #getJobDefinitionType()
   * @generated
   */
	EReference getJobDefinitionType_JobDescription();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobDefinitionType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.JobDefinitionType#getAny()
   * @see #getJobDefinitionType()
   * @generated
   */
	EAttribute getJobDefinitionType_Any();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.JobDefinitionType#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see eu.geclipse.jsdl.JobDefinitionType#getId()
   * @see #getJobDefinitionType()
   * @generated
   */
	EAttribute getJobDefinitionType_Id();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobDefinitionType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.JobDefinitionType#getAnyAttribute()
   * @see #getJobDefinitionType()
   * @generated
   */
	EAttribute getJobDefinitionType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.JobDescriptionType <em>Job Description Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Job Description Type</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType
   * @generated
   */
	EClass getJobDescriptionType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.JobDescriptionType#getJobIdentification <em>Job Identification</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Job Identification</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType#getJobIdentification()
   * @see #getJobDescriptionType()
   * @generated
   */
	EReference getJobDescriptionType_JobIdentification();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.JobDescriptionType#getApplication <em>Application</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Application</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType#getApplication()
   * @see #getJobDescriptionType()
   * @generated
   */
	EReference getJobDescriptionType_Application();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.JobDescriptionType#getResources <em>Resources</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Resources</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType#getResources()
   * @see #getJobDescriptionType()
   * @generated
   */
	EReference getJobDescriptionType_Resources();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.JobDescriptionType#getDataStaging <em>Data Staging</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Data Staging</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType#getDataStaging()
   * @see #getJobDescriptionType()
   * @generated
   */
	EReference getJobDescriptionType_DataStaging();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobDescriptionType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType#getAny()
   * @see #getJobDescriptionType()
   * @generated
   */
	EAttribute getJobDescriptionType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobDescriptionType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.JobDescriptionType#getAnyAttribute()
   * @see #getJobDescriptionType()
   * @generated
   */
	EAttribute getJobDescriptionType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.JobIdentificationType <em>Job Identification Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Job Identification Type</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType
   * @generated
   */
	EClass getJobIdentificationType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.JobIdentificationType#getJobName <em>Job Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Job Name</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType#getJobName()
   * @see #getJobIdentificationType()
   * @generated
   */
	EAttribute getJobIdentificationType_JobName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.JobIdentificationType#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType#getDescription()
   * @see #getJobIdentificationType()
   * @generated
   */
	EAttribute getJobIdentificationType_Description();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobIdentificationType#getJobAnnotation <em>Job Annotation</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Job Annotation</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType#getJobAnnotation()
   * @see #getJobIdentificationType()
   * @generated
   */
	EAttribute getJobIdentificationType_JobAnnotation();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobIdentificationType#getJobProject <em>Job Project</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Job Project</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType#getJobProject()
   * @see #getJobIdentificationType()
   * @generated
   */
	EAttribute getJobIdentificationType_JobProject();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobIdentificationType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType#getAny()
   * @see #getJobIdentificationType()
   * @generated
   */
	EAttribute getJobIdentificationType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.JobIdentificationType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.JobIdentificationType#getAnyAttribute()
   * @see #getJobIdentificationType()
   * @generated
   */
	EAttribute getJobIdentificationType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.OperatingSystemType <em>Operating System Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Operating System Type</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemType
   * @generated
   */
	EClass getOperatingSystemType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.OperatingSystemType#getOperatingSystemType <em>Operating System Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operating System Type</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemType#getOperatingSystemType()
   * @see #getOperatingSystemType()
   * @generated
   */
	EReference getOperatingSystemType_OperatingSystemType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.OperatingSystemType#getOperatingSystemVersion <em>Operating System Version</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operating System Version</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemType#getOperatingSystemVersion()
   * @see #getOperatingSystemType()
   * @generated
   */
	EAttribute getOperatingSystemType_OperatingSystemVersion();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.OperatingSystemType#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemType#getDescription()
   * @see #getOperatingSystemType()
   * @generated
   */
	EAttribute getOperatingSystemType_Description();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.OperatingSystemType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemType#getAny()
   * @see #getOperatingSystemType()
   * @generated
   */
	EAttribute getOperatingSystemType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.OperatingSystemType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemType#getAnyAttribute()
   * @see #getOperatingSystemType()
   * @generated
   */
	EAttribute getOperatingSystemType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.OperatingSystemTypeType <em>Operating System Type Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Operating System Type Type</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemTypeType
   * @generated
   */
	EClass getOperatingSystemTypeType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.OperatingSystemTypeType#getOperatingSystemName <em>Operating System Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Operating System Name</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemTypeType#getOperatingSystemName()
   * @see #getOperatingSystemTypeType()
   * @generated
   */
	EAttribute getOperatingSystemTypeType_OperatingSystemName();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.OperatingSystemTypeType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemTypeType#getAny()
   * @see #getOperatingSystemTypeType()
   * @generated
   */
	EAttribute getOperatingSystemTypeType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.OperatingSystemTypeType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemTypeType#getAnyAttribute()
   * @see #getOperatingSystemTypeType()
   * @generated
   */
	EAttribute getOperatingSystemTypeType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.RangeType <em>Range Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Range Type</em>'.
   * @see eu.geclipse.jsdl.RangeType
   * @generated
   */
	EClass getRangeType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.RangeType#getLowerBound <em>Lower Bound</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower Bound</em>'.
   * @see eu.geclipse.jsdl.RangeType#getLowerBound()
   * @see #getRangeType()
   * @generated
   */
	EReference getRangeType_LowerBound();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.RangeType#getUpperBound <em>Upper Bound</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Upper Bound</em>'.
   * @see eu.geclipse.jsdl.RangeType#getUpperBound()
   * @see #getRangeType()
   * @generated
   */
	EReference getRangeType_UpperBound();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.RangeType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.RangeType#getAnyAttribute()
   * @see #getRangeType()
   * @generated
   */
	EAttribute getRangeType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.RangeValueType <em>Range Value Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Range Value Type</em>'.
   * @see eu.geclipse.jsdl.RangeValueType
   * @generated
   */
	EClass getRangeValueType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.RangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Upper Bounded Range</em>'.
   * @see eu.geclipse.jsdl.RangeValueType#getUpperBoundedRange()
   * @see #getRangeValueType()
   * @generated
   */
	EReference getRangeValueType_UpperBoundedRange();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.RangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower Bounded Range</em>'.
   * @see eu.geclipse.jsdl.RangeValueType#getLowerBoundedRange()
   * @see #getRangeValueType()
   * @generated
   */
	EReference getRangeValueType_LowerBoundedRange();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.RangeValueType#getExact <em>Exact</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exact</em>'.
   * @see eu.geclipse.jsdl.RangeValueType#getExact()
   * @see #getRangeValueType()
   * @generated
   */
	EReference getRangeValueType_Exact();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.RangeValueType#getRange <em>Range</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Range</em>'.
   * @see eu.geclipse.jsdl.RangeValueType#getRange()
   * @see #getRangeValueType()
   * @generated
   */
	EReference getRangeValueType_Range();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.RangeValueType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.RangeValueType#getAnyAttribute()
   * @see #getRangeValueType()
   * @generated
   */
	EAttribute getRangeValueType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.ResourcesType <em>Resources Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Resources Type</em>'.
   * @see eu.geclipse.jsdl.ResourcesType
   * @generated
   */
	EClass getResourcesType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getCandidateHosts <em>Candidate Hosts</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Candidate Hosts</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getCandidateHosts()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_CandidateHosts();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.ResourcesType#getFileSystem <em>File System</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>File System</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getFileSystem()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_FileSystem();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.ResourcesType#isExclusiveExecution <em>Exclusive Execution</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exclusive Execution</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#isExclusiveExecution()
   * @see #getResourcesType()
   * @generated
   */
	EAttribute getResourcesType_ExclusiveExecution();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getOperatingSystem <em>Operating System</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Operating System</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getOperatingSystem()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_OperatingSystem();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getCPUArchitecture <em>CPU Architecture</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>CPU Architecture</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getCPUArchitecture()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_CPUArchitecture();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualCPUSpeed <em>Individual CPU Speed</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual CPU Speed</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualCPUSpeed()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualCPUSpeed();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualCPUTime <em>Individual CPU Time</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual CPU Time</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualCPUTime()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualCPUTime();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualCPUCount <em>Individual CPU Count</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual CPU Count</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualCPUCount()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualCPUCount();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Network Bandwidth</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualNetworkBandwidth()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualNetworkBandwidth();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Physical Memory</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualPhysicalMemory()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualPhysicalMemory();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Virtual Memory</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualVirtualMemory()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualVirtualMemory();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getIndividualDiskSpace <em>Individual Disk Space</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Individual Disk Space</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getIndividualDiskSpace()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_IndividualDiskSpace();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getTotalCPUTime <em>Total CPU Time</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total CPU Time</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getTotalCPUTime()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_TotalCPUTime();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getTotalCPUCount <em>Total CPU Count</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total CPU Count</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getTotalCPUCount()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_TotalCPUCount();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getTotalPhysicalMemory <em>Total Physical Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Physical Memory</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getTotalPhysicalMemory()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_TotalPhysicalMemory();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getTotalVirtualMemory <em>Total Virtual Memory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Virtual Memory</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getTotalVirtualMemory()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_TotalVirtualMemory();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getTotalDiskSpace <em>Total Disk Space</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Disk Space</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getTotalDiskSpace()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_TotalDiskSpace();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.ResourcesType#getTotalResourceCount <em>Total Resource Count</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Total Resource Count</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getTotalResourceCount()
   * @see #getResourcesType()
   * @generated
   */
	EReference getResourcesType_TotalResourceCount();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.ResourcesType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getAny()
   * @see #getResourcesType()
   * @generated
   */
	EAttribute getResourcesType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.ResourcesType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.ResourcesType#getAnyAttribute()
   * @see #getResourcesType()
   * @generated
   */
	EAttribute getResourcesType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.SourceTargetType <em>Source Target Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Source Target Type</em>'.
   * @see eu.geclipse.jsdl.SourceTargetType
   * @generated
   */
	EClass getSourceTargetType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.SourceTargetType#getURI <em>URI</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>URI</em>'.
   * @see eu.geclipse.jsdl.SourceTargetType#getURI()
   * @see #getSourceTargetType()
   * @generated
   */
	EAttribute getSourceTargetType_URI();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.SourceTargetType#getAny <em>Any</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any</em>'.
   * @see eu.geclipse.jsdl.SourceTargetType#getAny()
   * @see #getSourceTargetType()
   * @generated
   */
	EAttribute getSourceTargetType_Any();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.SourceTargetType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.SourceTargetType#getAnyAttribute()
   * @see #getSourceTargetType()
   * @generated
   */
	EAttribute getSourceTargetType_AnyAttribute();

  /**
   * Returns the meta object for enum '{@link eu.geclipse.jsdl.CreationFlagEnumeration <em>Creation Flag Enumeration</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Creation Flag Enumeration</em>'.
   * @see eu.geclipse.jsdl.CreationFlagEnumeration
   * @generated
   */
	EEnum getCreationFlagEnumeration();

  /**
   * Returns the meta object for enum '{@link eu.geclipse.jsdl.FileSystemTypeEnumeration <em>File System Type Enumeration</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for enum '<em>File System Type Enumeration</em>'.
   * @see eu.geclipse.jsdl.FileSystemTypeEnumeration
   * @generated
   */
	EEnum getFileSystemTypeEnumeration();

  /**
   * Returns the meta object for enum '{@link eu.geclipse.jsdl.OperatingSystemTypeEnumeration <em>Operating System Type Enumeration</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Operating System Type Enumeration</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemTypeEnumeration
   * @generated
   */
	EEnum getOperatingSystemTypeEnumeration();

  /**
   * Returns the meta object for enum '{@link eu.geclipse.jsdl.ProcessorArchitectureEnumeration <em>Processor Architecture Enumeration</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Processor Architecture Enumeration</em>'.
   * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
   * @generated
   */
	EEnum getProcessorArchitectureEnumeration();

  /**
   * Returns the meta object for data type '{@link eu.geclipse.jsdl.CreationFlagEnumeration <em>Creation Flag Enumeration Object</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Creation Flag Enumeration Object</em>'.
   * @see eu.geclipse.jsdl.CreationFlagEnumeration
   * @model instanceClass="eu.geclipse.jsdl.CreationFlagEnumeration"
   *        extendedMetaData="name='CreationFlagEnumeration:Object' baseType='CreationFlagEnumeration'" 
   * @generated
   */
	EDataType getCreationFlagEnumerationObject();

  /**
   * Returns the meta object for data type '{@link java.lang.String <em>Description Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Description Type</em>'.
   * @see java.lang.String
   * @model instanceClass="java.lang.String"
   *        extendedMetaData="name='Description_Type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
   * @generated
   */
	EDataType getDescriptionType();

  /**
   * Returns the meta object for data type '{@link eu.geclipse.jsdl.FileSystemTypeEnumeration <em>File System Type Enumeration Object</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for data type '<em>File System Type Enumeration Object</em>'.
   * @see eu.geclipse.jsdl.FileSystemTypeEnumeration
   * @model instanceClass="eu.geclipse.jsdl.FileSystemTypeEnumeration"
   *        extendedMetaData="name='FileSystemTypeEnumeration:Object' baseType='FileSystemTypeEnumeration'" 
   * @generated
   */
	EDataType getFileSystemTypeEnumerationObject();

  /**
   * Returns the meta object for data type '{@link eu.geclipse.jsdl.OperatingSystemTypeEnumeration <em>Operating System Type Enumeration Object</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Operating System Type Enumeration Object</em>'.
   * @see eu.geclipse.jsdl.OperatingSystemTypeEnumeration
   * @model instanceClass="eu.geclipse.jsdl.OperatingSystemTypeEnumeration"
   *        extendedMetaData="name='OperatingSystemTypeEnumeration:Object' baseType='OperatingSystemTypeEnumeration'" 
   * @generated
   */
	EDataType getOperatingSystemTypeEnumerationObject();

  /**
   * Returns the meta object for data type '{@link eu.geclipse.jsdl.ProcessorArchitectureEnumeration <em>Processor Architecture Enumeration Object</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Processor Architecture Enumeration Object</em>'.
   * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
   * @model instanceClass="eu.geclipse.jsdl.ProcessorArchitectureEnumeration"
   *        extendedMetaData="name='ProcessorArchitectureEnumeration:Object' baseType='ProcessorArchitectureEnumeration'" 
   * @generated
   */
	EDataType getProcessorArchitectureEnumerationObject();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
	JsdlFactory getJsdlFactory();

  /**
   * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
   * @generated
   */
	interface Literals 
	{
    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.ApplicationTypeImpl <em>Application Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.ApplicationTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getApplicationType()
     * @generated
     */
		EClass APPLICATION_TYPE = eINSTANCE.getApplicationType();

    /**
     * The meta object literal for the '<em><b>Application Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute APPLICATION_TYPE__APPLICATION_NAME = eINSTANCE.getApplicationType_ApplicationName();

    /**
     * The meta object literal for the '<em><b>Application Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute APPLICATION_TYPE__APPLICATION_VERSION = eINSTANCE.getApplicationType_ApplicationVersion();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute APPLICATION_TYPE__DESCRIPTION = eINSTANCE.getApplicationType_Description();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute APPLICATION_TYPE__ANY = eINSTANCE.getApplicationType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute APPLICATION_TYPE__ANY_ATTRIBUTE = eINSTANCE.getApplicationType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.BoundaryTypeImpl <em>Boundary Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.BoundaryTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getBoundaryType()
     * @generated
     */
		EClass BOUNDARY_TYPE = eINSTANCE.getBoundaryType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute BOUNDARY_TYPE__VALUE = eINSTANCE.getBoundaryType_Value();

    /**
     * The meta object literal for the '<em><b>Exclusive Bound</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute BOUNDARY_TYPE__EXCLUSIVE_BOUND = eINSTANCE.getBoundaryType_ExclusiveBound();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute BOUNDARY_TYPE__ANY_ATTRIBUTE = eINSTANCE.getBoundaryType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.CandidateHostsTypeImpl <em>Candidate Hosts Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.CandidateHostsTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCandidateHostsType()
     * @generated
     */
		EClass CANDIDATE_HOSTS_TYPE = eINSTANCE.getCandidateHostsType();

    /**
     * The meta object literal for the '<em><b>Host Name</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute CANDIDATE_HOSTS_TYPE__HOST_NAME = eINSTANCE.getCandidateHostsType_HostName();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.CPUArchitectureTypeImpl <em>CPU Architecture Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.CPUArchitectureTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCPUArchitectureType()
     * @generated
     */
		EClass CPU_ARCHITECTURE_TYPE = eINSTANCE.getCPUArchitectureType();

    /**
     * The meta object literal for the '<em><b>CPU Architecture Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME = eINSTANCE.getCPUArchitectureType_CPUArchitectureName();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute CPU_ARCHITECTURE_TYPE__ANY = eINSTANCE.getCPUArchitectureType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getCPUArchitectureType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.DataStagingTypeImpl <em>Data Staging Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.DataStagingTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getDataStagingType()
     * @generated
     */
		EClass DATA_STAGING_TYPE = eINSTANCE.getDataStagingType();

    /**
     * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__FILE_NAME = eINSTANCE.getDataStagingType_FileName();

    /**
     * The meta object literal for the '<em><b>Filesystem Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__FILESYSTEM_NAME = eINSTANCE.getDataStagingType_FilesystemName();

    /**
     * The meta object literal for the '<em><b>Creation Flag</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__CREATION_FLAG = eINSTANCE.getDataStagingType_CreationFlag();

    /**
     * The meta object literal for the '<em><b>Delete On Termination</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__DELETE_ON_TERMINATION = eINSTANCE.getDataStagingType_DeleteOnTermination();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DATA_STAGING_TYPE__SOURCE = eINSTANCE.getDataStagingType_Source();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DATA_STAGING_TYPE__TARGET = eINSTANCE.getDataStagingType_Target();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__ANY = eINSTANCE.getDataStagingType_Any();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__NAME = eINSTANCE.getDataStagingType_Name();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DATA_STAGING_TYPE__ANY_ATTRIBUTE = eINSTANCE.getDataStagingType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.DocumentRootImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getDocumentRoot()
     * @generated
     */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

    /**
     * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

    /**
     * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

    /**
     * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

    /**
     * The meta object literal for the '<em><b>Application</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__APPLICATION = eINSTANCE.getDocumentRoot_Application();

    /**
     * The meta object literal for the '<em><b>Application Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__APPLICATION_NAME = eINSTANCE.getDocumentRoot_ApplicationName();

    /**
     * The meta object literal for the '<em><b>Application Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__APPLICATION_VERSION = eINSTANCE.getDocumentRoot_ApplicationVersion();

    /**
     * The meta object literal for the '<em><b>Candidate Hosts</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__CANDIDATE_HOSTS = eINSTANCE.getDocumentRoot_CandidateHosts();

    /**
     * The meta object literal for the '<em><b>CPU Architecture</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__CPU_ARCHITECTURE = eINSTANCE.getDocumentRoot_CPUArchitecture();

    /**
     * The meta object literal for the '<em><b>CPU Architecture Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME = eINSTANCE.getDocumentRoot_CPUArchitectureName();

    /**
     * The meta object literal for the '<em><b>Creation Flag</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__CREATION_FLAG = eINSTANCE.getDocumentRoot_CreationFlag();

    /**
     * The meta object literal for the '<em><b>Data Staging</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__DATA_STAGING = eINSTANCE.getDocumentRoot_DataStaging();

    /**
     * The meta object literal for the '<em><b>Delete On Termination</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__DELETE_ON_TERMINATION = eINSTANCE.getDocumentRoot_DeleteOnTermination();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__DESCRIPTION = eINSTANCE.getDocumentRoot_Description();

    /**
     * The meta object literal for the '<em><b>Disk Space</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__DISK_SPACE = eINSTANCE.getDocumentRoot_DiskSpace();

    /**
     * The meta object literal for the '<em><b>Exclusive Execution</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__EXCLUSIVE_EXECUTION = eINSTANCE.getDocumentRoot_ExclusiveExecution();

    /**
     * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__FILE_NAME = eINSTANCE.getDocumentRoot_FileName();

    /**
     * The meta object literal for the '<em><b>File System</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__FILE_SYSTEM = eINSTANCE.getDocumentRoot_FileSystem();

    /**
     * The meta object literal for the '<em><b>Filesystem Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__FILESYSTEM_NAME = eINSTANCE.getDocumentRoot_FilesystemName();

    /**
     * The meta object literal for the '<em><b>File System Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__FILE_SYSTEM_TYPE = eINSTANCE.getDocumentRoot_FileSystemType();

    /**
     * The meta object literal for the '<em><b>Host Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__HOST_NAME = eINSTANCE.getDocumentRoot_HostName();

    /**
     * The meta object literal for the '<em><b>Individual CPU Count</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT = eINSTANCE.getDocumentRoot_IndividualCPUCount();

    /**
     * The meta object literal for the '<em><b>Individual CPU Speed</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED = eINSTANCE.getDocumentRoot_IndividualCPUSpeed();

    /**
     * The meta object literal for the '<em><b>Individual CPU Time</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME = eINSTANCE.getDocumentRoot_IndividualCPUTime();

    /**
     * The meta object literal for the '<em><b>Individual Disk Space</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE = eINSTANCE.getDocumentRoot_IndividualDiskSpace();

    /**
     * The meta object literal for the '<em><b>Individual Network Bandwidth</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH = eINSTANCE.getDocumentRoot_IndividualNetworkBandwidth();

    /**
     * The meta object literal for the '<em><b>Individual Physical Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY = eINSTANCE.getDocumentRoot_IndividualPhysicalMemory();

    /**
     * The meta object literal for the '<em><b>Individual Virtual Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY = eINSTANCE.getDocumentRoot_IndividualVirtualMemory();

    /**
     * The meta object literal for the '<em><b>Job Annotation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__JOB_ANNOTATION = eINSTANCE.getDocumentRoot_JobAnnotation();

    /**
     * The meta object literal for the '<em><b>Job Definition</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__JOB_DEFINITION = eINSTANCE.getDocumentRoot_JobDefinition();

    /**
     * The meta object literal for the '<em><b>Job Description</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__JOB_DESCRIPTION = eINSTANCE.getDocumentRoot_JobDescription();

    /**
     * The meta object literal for the '<em><b>Job Identification</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__JOB_IDENTIFICATION = eINSTANCE.getDocumentRoot_JobIdentification();

    /**
     * The meta object literal for the '<em><b>Job Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__JOB_NAME = eINSTANCE.getDocumentRoot_JobName();

    /**
     * The meta object literal for the '<em><b>Job Project</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__JOB_PROJECT = eINSTANCE.getDocumentRoot_JobProject();

    /**
     * The meta object literal for the '<em><b>Mount Point</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__MOUNT_POINT = eINSTANCE.getDocumentRoot_MountPoint();

    /**
     * The meta object literal for the '<em><b>Operating System</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__OPERATING_SYSTEM = eINSTANCE.getDocumentRoot_OperatingSystem();

    /**
     * The meta object literal for the '<em><b>Operating System Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__OPERATING_SYSTEM_NAME = eINSTANCE.getDocumentRoot_OperatingSystemName();

    /**
     * The meta object literal for the '<em><b>Operating System Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE = eINSTANCE.getDocumentRoot_OperatingSystemType();

    /**
     * The meta object literal for the '<em><b>Operating System Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION = eINSTANCE.getDocumentRoot_OperatingSystemVersion();

    /**
     * The meta object literal for the '<em><b>Resources</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__RESOURCES = eINSTANCE.getDocumentRoot_Resources();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__SOURCE = eINSTANCE.getDocumentRoot_Source();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TARGET = eINSTANCE.getDocumentRoot_Target();

    /**
     * The meta object literal for the '<em><b>Total CPU Count</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TOTAL_CPU_COUNT = eINSTANCE.getDocumentRoot_TotalCPUCount();

    /**
     * The meta object literal for the '<em><b>Total CPU Time</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TOTAL_CPU_TIME = eINSTANCE.getDocumentRoot_TotalCPUTime();

    /**
     * The meta object literal for the '<em><b>Total Disk Space</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TOTAL_DISK_SPACE = eINSTANCE.getDocumentRoot_TotalDiskSpace();

    /**
     * The meta object literal for the '<em><b>Total Physical Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY = eINSTANCE.getDocumentRoot_TotalPhysicalMemory();

    /**
     * The meta object literal for the '<em><b>Total Resource Count</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT = eINSTANCE.getDocumentRoot_TotalResourceCount();

    /**
     * The meta object literal for the '<em><b>Total Virtual Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY = eINSTANCE.getDocumentRoot_TotalVirtualMemory();

    /**
     * The meta object literal for the '<em><b>URI</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DOCUMENT_ROOT__URI = eINSTANCE.getDocumentRoot_URI();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.ExactTypeImpl <em>Exact Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.ExactTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getExactType()
     * @generated
     */
		EClass EXACT_TYPE = eINSTANCE.getExactType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute EXACT_TYPE__VALUE = eINSTANCE.getExactType_Value();

    /**
     * The meta object literal for the '<em><b>Epsilon</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute EXACT_TYPE__EPSILON = eINSTANCE.getExactType_Epsilon();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute EXACT_TYPE__ANY_ATTRIBUTE = eINSTANCE.getExactType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.FileSystemTypeImpl <em>File System Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.FileSystemTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getFileSystemType()
     * @generated
     */
		EClass FILE_SYSTEM_TYPE = eINSTANCE.getFileSystemType();

    /**
     * The meta object literal for the '<em><b>File System Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE = eINSTANCE.getFileSystemType_FileSystemType();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_SYSTEM_TYPE__DESCRIPTION = eINSTANCE.getFileSystemType_Description();

    /**
     * The meta object literal for the '<em><b>Mount Point</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_SYSTEM_TYPE__MOUNT_POINT = eINSTANCE.getFileSystemType_MountPoint();

    /**
     * The meta object literal for the '<em><b>Disk Space</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference FILE_SYSTEM_TYPE__DISK_SPACE = eINSTANCE.getFileSystemType_DiskSpace();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_SYSTEM_TYPE__ANY = eINSTANCE.getFileSystemType_Any();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_SYSTEM_TYPE__NAME = eINSTANCE.getFileSystemType_Name();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_SYSTEM_TYPE__ANY_ATTRIBUTE = eINSTANCE.getFileSystemType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.JobDefinitionTypeImpl <em>Job Definition Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.JobDefinitionTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getJobDefinitionType()
     * @generated
     */
		EClass JOB_DEFINITION_TYPE = eINSTANCE.getJobDefinitionType();

    /**
     * The meta object literal for the '<em><b>Job Description</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference JOB_DEFINITION_TYPE__JOB_DESCRIPTION = eINSTANCE.getJobDefinitionType_JobDescription();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_DEFINITION_TYPE__ANY = eINSTANCE.getJobDefinitionType_Any();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_DEFINITION_TYPE__ID = eINSTANCE.getJobDefinitionType_Id();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_DEFINITION_TYPE__ANY_ATTRIBUTE = eINSTANCE.getJobDefinitionType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.JobDescriptionTypeImpl <em>Job Description Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.JobDescriptionTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getJobDescriptionType()
     * @generated
     */
		EClass JOB_DESCRIPTION_TYPE = eINSTANCE.getJobDescriptionType();

    /**
     * The meta object literal for the '<em><b>Job Identification</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION = eINSTANCE.getJobDescriptionType_JobIdentification();

    /**
     * The meta object literal for the '<em><b>Application</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference JOB_DESCRIPTION_TYPE__APPLICATION = eINSTANCE.getJobDescriptionType_Application();

    /**
     * The meta object literal for the '<em><b>Resources</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference JOB_DESCRIPTION_TYPE__RESOURCES = eINSTANCE.getJobDescriptionType_Resources();

    /**
     * The meta object literal for the '<em><b>Data Staging</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference JOB_DESCRIPTION_TYPE__DATA_STAGING = eINSTANCE.getJobDescriptionType_DataStaging();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_DESCRIPTION_TYPE__ANY = eINSTANCE.getJobDescriptionType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE = eINSTANCE.getJobDescriptionType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.JobIdentificationTypeImpl <em>Job Identification Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.JobIdentificationTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getJobIdentificationType()
     * @generated
     */
		EClass JOB_IDENTIFICATION_TYPE = eINSTANCE.getJobIdentificationType();

    /**
     * The meta object literal for the '<em><b>Job Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_IDENTIFICATION_TYPE__JOB_NAME = eINSTANCE.getJobIdentificationType_JobName();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_IDENTIFICATION_TYPE__DESCRIPTION = eINSTANCE.getJobIdentificationType_Description();

    /**
     * The meta object literal for the '<em><b>Job Annotation</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION = eINSTANCE.getJobIdentificationType_JobAnnotation();

    /**
     * The meta object literal for the '<em><b>Job Project</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_IDENTIFICATION_TYPE__JOB_PROJECT = eINSTANCE.getJobIdentificationType_JobProject();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_IDENTIFICATION_TYPE__ANY = eINSTANCE.getJobIdentificationType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE = eINSTANCE.getJobIdentificationType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.OperatingSystemTypeImpl <em>Operating System Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.OperatingSystemTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemType()
     * @generated
     */
		EClass OPERATING_SYSTEM_TYPE = eINSTANCE.getOperatingSystemType();

    /**
     * The meta object literal for the '<em><b>Operating System Type</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE = eINSTANCE.getOperatingSystemType_OperatingSystemType();

    /**
     * The meta object literal for the '<em><b>Operating System Version</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION = eINSTANCE.getOperatingSystemType_OperatingSystemVersion();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE__DESCRIPTION = eINSTANCE.getOperatingSystemType_Description();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE__ANY = eINSTANCE.getOperatingSystemType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE = eINSTANCE.getOperatingSystemType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl <em>Operating System Type Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.OperatingSystemTypeTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemTypeType()
     * @generated
     */
		EClass OPERATING_SYSTEM_TYPE_TYPE = eINSTANCE.getOperatingSystemTypeType();

    /**
     * The meta object literal for the '<em><b>Operating System Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME = eINSTANCE.getOperatingSystemTypeType_OperatingSystemName();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE_TYPE__ANY = eINSTANCE.getOperatingSystemTypeType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getOperatingSystemTypeType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.RangeTypeImpl <em>Range Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.RangeTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getRangeType()
     * @generated
     */
		EClass RANGE_TYPE = eINSTANCE.getRangeType();

    /**
     * The meta object literal for the '<em><b>Lower Bound</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RANGE_TYPE__LOWER_BOUND = eINSTANCE.getRangeType_LowerBound();

    /**
     * The meta object literal for the '<em><b>Upper Bound</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RANGE_TYPE__UPPER_BOUND = eINSTANCE.getRangeType_UpperBound();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute RANGE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getRangeType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.RangeValueTypeImpl <em>Range Value Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.RangeValueTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getRangeValueType()
     * @generated
     */
		EClass RANGE_VALUE_TYPE = eINSTANCE.getRangeValueType();

    /**
     * The meta object literal for the '<em><b>Upper Bounded Range</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE = eINSTANCE.getRangeValueType_UpperBoundedRange();

    /**
     * The meta object literal for the '<em><b>Lower Bounded Range</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE = eINSTANCE.getRangeValueType_LowerBoundedRange();

    /**
     * The meta object literal for the '<em><b>Exact</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RANGE_VALUE_TYPE__EXACT = eINSTANCE.getRangeValueType_Exact();

    /**
     * The meta object literal for the '<em><b>Range</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RANGE_VALUE_TYPE__RANGE = eINSTANCE.getRangeValueType_Range();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute RANGE_VALUE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getRangeValueType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl <em>Resources Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.ResourcesTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getResourcesType()
     * @generated
     */
		EClass RESOURCES_TYPE = eINSTANCE.getResourcesType();

    /**
     * The meta object literal for the '<em><b>Candidate Hosts</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__CANDIDATE_HOSTS = eINSTANCE.getResourcesType_CandidateHosts();

    /**
     * The meta object literal for the '<em><b>File System</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__FILE_SYSTEM = eINSTANCE.getResourcesType_FileSystem();

    /**
     * The meta object literal for the '<em><b>Exclusive Execution</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute RESOURCES_TYPE__EXCLUSIVE_EXECUTION = eINSTANCE.getResourcesType_ExclusiveExecution();

    /**
     * The meta object literal for the '<em><b>Operating System</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__OPERATING_SYSTEM = eINSTANCE.getResourcesType_OperatingSystem();

    /**
     * The meta object literal for the '<em><b>CPU Architecture</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__CPU_ARCHITECTURE = eINSTANCE.getResourcesType_CPUArchitecture();

    /**
     * The meta object literal for the '<em><b>Individual CPU Speed</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED = eINSTANCE.getResourcesType_IndividualCPUSpeed();

    /**
     * The meta object literal for the '<em><b>Individual CPU Time</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_CPU_TIME = eINSTANCE.getResourcesType_IndividualCPUTime();

    /**
     * The meta object literal for the '<em><b>Individual CPU Count</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT = eINSTANCE.getResourcesType_IndividualCPUCount();

    /**
     * The meta object literal for the '<em><b>Individual Network Bandwidth</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH = eINSTANCE.getResourcesType_IndividualNetworkBandwidth();

    /**
     * The meta object literal for the '<em><b>Individual Physical Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY = eINSTANCE.getResourcesType_IndividualPhysicalMemory();

    /**
     * The meta object literal for the '<em><b>Individual Virtual Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY = eINSTANCE.getResourcesType_IndividualVirtualMemory();

    /**
     * The meta object literal for the '<em><b>Individual Disk Space</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE = eINSTANCE.getResourcesType_IndividualDiskSpace();

    /**
     * The meta object literal for the '<em><b>Total CPU Time</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__TOTAL_CPU_TIME = eINSTANCE.getResourcesType_TotalCPUTime();

    /**
     * The meta object literal for the '<em><b>Total CPU Count</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__TOTAL_CPU_COUNT = eINSTANCE.getResourcesType_TotalCPUCount();

    /**
     * The meta object literal for the '<em><b>Total Physical Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY = eINSTANCE.getResourcesType_TotalPhysicalMemory();

    /**
     * The meta object literal for the '<em><b>Total Virtual Memory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY = eINSTANCE.getResourcesType_TotalVirtualMemory();

    /**
     * The meta object literal for the '<em><b>Total Disk Space</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__TOTAL_DISK_SPACE = eINSTANCE.getResourcesType_TotalDiskSpace();

    /**
     * The meta object literal for the '<em><b>Total Resource Count</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference RESOURCES_TYPE__TOTAL_RESOURCE_COUNT = eINSTANCE.getResourcesType_TotalResourceCount();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute RESOURCES_TYPE__ANY = eINSTANCE.getResourcesType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute RESOURCES_TYPE__ANY_ATTRIBUTE = eINSTANCE.getResourcesType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.impl.SourceTargetTypeImpl <em>Source Target Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.impl.SourceTargetTypeImpl
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getSourceTargetType()
     * @generated
     */
		EClass SOURCE_TARGET_TYPE = eINSTANCE.getSourceTargetType();

    /**
     * The meta object literal for the '<em><b>URI</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute SOURCE_TARGET_TYPE__URI = eINSTANCE.getSourceTargetType_URI();

    /**
     * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute SOURCE_TARGET_TYPE__ANY = eINSTANCE.getSourceTargetType_Any();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute SOURCE_TARGET_TYPE__ANY_ATTRIBUTE = eINSTANCE.getSourceTargetType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.CreationFlagEnumeration <em>Creation Flag Enumeration</em>}' enum.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.CreationFlagEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCreationFlagEnumeration()
     * @generated
     */
		EEnum CREATION_FLAG_ENUMERATION = eINSTANCE.getCreationFlagEnumeration();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.FileSystemTypeEnumeration <em>File System Type Enumeration</em>}' enum.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.FileSystemTypeEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getFileSystemTypeEnumeration()
     * @generated
     */
		EEnum FILE_SYSTEM_TYPE_ENUMERATION = eINSTANCE.getFileSystemTypeEnumeration();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.OperatingSystemTypeEnumeration <em>Operating System Type Enumeration</em>}' enum.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.OperatingSystemTypeEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemTypeEnumeration()
     * @generated
     */
		EEnum OPERATING_SYSTEM_TYPE_ENUMERATION = eINSTANCE.getOperatingSystemTypeEnumeration();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.ProcessorArchitectureEnumeration <em>Processor Architecture Enumeration</em>}' enum.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getProcessorArchitectureEnumeration()
     * @generated
     */
		EEnum PROCESSOR_ARCHITECTURE_ENUMERATION = eINSTANCE.getProcessorArchitectureEnumeration();

    /**
     * The meta object literal for the '<em>Creation Flag Enumeration Object</em>' data type.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.CreationFlagEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getCreationFlagEnumerationObject()
     * @generated
     */
		EDataType CREATION_FLAG_ENUMERATION_OBJECT = eINSTANCE.getCreationFlagEnumerationObject();

    /**
     * The meta object literal for the '<em>Description Type</em>' data type.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see java.lang.String
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getDescriptionType()
     * @generated
     */
		EDataType DESCRIPTION_TYPE = eINSTANCE.getDescriptionType();

    /**
     * The meta object literal for the '<em>File System Type Enumeration Object</em>' data type.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.FileSystemTypeEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getFileSystemTypeEnumerationObject()
     * @generated
     */
		EDataType FILE_SYSTEM_TYPE_ENUMERATION_OBJECT = eINSTANCE.getFileSystemTypeEnumerationObject();

    /**
     * The meta object literal for the '<em>Operating System Type Enumeration Object</em>' data type.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.OperatingSystemTypeEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getOperatingSystemTypeEnumerationObject()
     * @generated
     */
		EDataType OPERATING_SYSTEM_TYPE_ENUMERATION_OBJECT = eINSTANCE.getOperatingSystemTypeEnumerationObject();

    /**
     * The meta object literal for the '<em>Processor Architecture Enumeration Object</em>' data type.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
     * @see eu.geclipse.jsdl.impl.JsdlPackageImpl#getProcessorArchitectureEnumerationObject()
     * @generated
     */
		EDataType PROCESSOR_ARCHITECTURE_ENUMERATION_OBJECT = eINSTANCE.getProcessorArchitectureEnumerationObject();

	}

} //JsdlPackage
