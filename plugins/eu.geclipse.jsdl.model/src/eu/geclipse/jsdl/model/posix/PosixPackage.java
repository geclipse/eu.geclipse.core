/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *    Mathias St�mpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.posix;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see eu.geclipse.jsdl.model.posix.PosixFactory
 * @model kind="package"
 * @generated
 */
public interface PosixPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNAME = "model.posix";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_URI = "http://schemas.ggf.org/jsdl/2005/11/jsdl-posix";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	String eNS_PREFIX = "jsdl-posix";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	PosixPackage eINSTANCE = eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl.init();

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.ArgumentTypeImpl <em>Argument Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.ArgumentTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getArgumentType()
   * @generated
   */
	int ARGUMENT_TYPE = 0;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ARGUMENT_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Filesystem Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ARGUMENT_TYPE__FILESYSTEM_NAME = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ARGUMENT_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Argument Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ARGUMENT_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl <em>Directory Name Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getDirectoryNameType()
   * @generated
   */
	int DIRECTORY_NAME_TYPE = 1;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DIRECTORY_NAME_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Filesystem Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DIRECTORY_NAME_TYPE__FILESYSTEM_NAME = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Directory Name Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DIRECTORY_NAME_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.DocumentRootImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getDocumentRoot()
   * @generated
   */
	int DOCUMENT_ROOT = 2;

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
   * The feature id for the '<em><b>Argument</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__ARGUMENT = 3;

  /**
   * The feature id for the '<em><b>Core Dump Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__CORE_DUMP_LIMIT = 4;

  /**
   * The feature id for the '<em><b>CPU Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__CPU_TIME_LIMIT = 5;

  /**
   * The feature id for the '<em><b>Data Segment Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__DATA_SEGMENT_LIMIT = 6;

  /**
   * The feature id for the '<em><b>Environment</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__ENVIRONMENT = 7;

  /**
   * The feature id for the '<em><b>Error</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__ERROR = 8;

  /**
   * The feature id for the '<em><b>Executable</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__EXECUTABLE = 9;

  /**
   * The feature id for the '<em><b>File Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__FILE_SIZE_LIMIT = 10;

  /**
   * The feature id for the '<em><b>Group Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__GROUP_NAME = 11;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__INPUT = 12;

  /**
   * The feature id for the '<em><b>Locked Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT = 13;

  /**
   * The feature id for the '<em><b>Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__MEMORY_LIMIT = 14;

  /**
   * The feature id for the '<em><b>Open Descriptors Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT = 15;

  /**
   * The feature id for the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__OUTPUT = 16;

  /**
   * The feature id for the '<em><b>Pipe Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__PIPE_SIZE_LIMIT = 17;

  /**
   * The feature id for the '<em><b>POSIX Application</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__POSIX_APPLICATION = 18;

  /**
   * The feature id for the '<em><b>Process Count Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__PROCESS_COUNT_LIMIT = 19;

  /**
   * The feature id for the '<em><b>Stack Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__STACK_SIZE_LIMIT = 20;

  /**
   * The feature id for the '<em><b>Thread Count Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__THREAD_COUNT_LIMIT = 21;

  /**
   * The feature id for the '<em><b>User Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__USER_NAME = 22;

  /**
   * The feature id for the '<em><b>Virtual Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT = 23;

  /**
   * The feature id for the '<em><b>Wall Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__WALL_TIME_LIMIT = 24;

  /**
   * The feature id for the '<em><b>Working Directory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT__WORKING_DIRECTORY = 25;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int DOCUMENT_ROOT_FEATURE_COUNT = 26;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.EnvironmentTypeImpl <em>Environment Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.EnvironmentTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getEnvironmentType()
   * @generated
   */
	int ENVIRONMENT_TYPE = 3;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ENVIRONMENT_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Filesystem Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ENVIRONMENT_TYPE__FILESYSTEM_NAME = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ENVIRONMENT_TYPE__NAME = 2;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ENVIRONMENT_TYPE__ANY_ATTRIBUTE = 3;

  /**
   * The number of structural features of the '<em>Environment Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int ENVIRONMENT_TYPE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.FileNameTypeImpl <em>File Name Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.FileNameTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getFileNameType()
   * @generated
   */
	int FILE_NAME_TYPE = 4;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_NAME_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Filesystem Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_NAME_TYPE__FILESYSTEM_NAME = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_NAME_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>File Name Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int FILE_NAME_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.GroupNameTypeImpl <em>Group Name Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.GroupNameTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getGroupNameType()
   * @generated
   */
	int GROUP_NAME_TYPE = 5;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int GROUP_NAME_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int GROUP_NAME_TYPE__ANY_ATTRIBUTE = 1;

  /**
   * The number of structural features of the '<em>Group Name Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int GROUP_NAME_TYPE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.LimitsTypeImpl <em>Limits Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.LimitsTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getLimitsType()
   * @generated
   */
	int LIMITS_TYPE = 6;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int LIMITS_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int LIMITS_TYPE__ANY_ATTRIBUTE = 1;

  /**
   * The number of structural features of the '<em>Limits Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int LIMITS_TYPE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl <em>POSIX Application Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getPOSIXApplicationType()
   * @generated
   */
	int POSIX_APPLICATION_TYPE = 7;

  /**
   * The feature id for the '<em><b>Executable</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__EXECUTABLE = 0;

  /**
   * The feature id for the '<em><b>Argument</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__ARGUMENT = 1;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__INPUT = 2;

  /**
   * The feature id for the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__OUTPUT = 3;

  /**
   * The feature id for the '<em><b>Error</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__ERROR = 4;

  /**
   * The feature id for the '<em><b>Working Directory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__WORKING_DIRECTORY = 5;

  /**
   * The feature id for the '<em><b>Environment</b></em>' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__ENVIRONMENT = 6;

  /**
   * The feature id for the '<em><b>Wall Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT = 7;

  /**
   * The feature id for the '<em><b>File Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT = 8;

  /**
   * The feature id for the '<em><b>Core Dump Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT = 9;

  /**
   * The feature id for the '<em><b>Data Segment Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT = 10;

  /**
   * The feature id for the '<em><b>Locked Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT = 11;

  /**
   * The feature id for the '<em><b>Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__MEMORY_LIMIT = 12;

  /**
   * The feature id for the '<em><b>Open Descriptors Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT = 13;

  /**
   * The feature id for the '<em><b>Pipe Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT = 14;

  /**
   * The feature id for the '<em><b>Stack Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT = 15;

  /**
   * The feature id for the '<em><b>CPU Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT = 16;

  /**
   * The feature id for the '<em><b>Process Count Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT = 17;

  /**
   * The feature id for the '<em><b>Virtual Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT = 18;

  /**
   * The feature id for the '<em><b>Thread Count Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT = 19;

  /**
   * The feature id for the '<em><b>User Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__USER_NAME = 20;

  /**
   * The feature id for the '<em><b>Group Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__GROUP_NAME = 21;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__NAME = 22;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE = 23;

  /**
   * The number of structural features of the '<em>POSIX Application Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int POSIX_APPLICATION_TYPE_FEATURE_COUNT = 24;

  /**
   * The meta object id for the '{@link eu.geclipse.jsdl.model.posix.impl.UserNameTypeImpl <em>User Name Type</em>}' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see eu.geclipse.jsdl.model.posix.impl.UserNameTypeImpl
   * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getUserNameType()
   * @generated
   */
	int USER_NAME_TYPE = 8;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int USER_NAME_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int USER_NAME_TYPE__ANY_ATTRIBUTE = 1;

  /**
   * The number of structural features of the '<em>User Name Type</em>' class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	int USER_NAME_TYPE_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.ArgumentType <em>Argument Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Argument Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.ArgumentType
   * @generated
   */
	EClass getArgumentType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.ArgumentType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.ArgumentType#getValue()
   * @see #getArgumentType()
   * @generated
   */
	EAttribute getArgumentType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.ArgumentType#getFilesystemName <em>Filesystem Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Filesystem Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.ArgumentType#getFilesystemName()
   * @see #getArgumentType()
   * @generated
   */
	EAttribute getArgumentType_FilesystemName();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.ArgumentType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.ArgumentType#getAnyAttribute()
   * @see #getArgumentType()
   * @generated
   */
	EAttribute getArgumentType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.DirectoryNameType <em>Directory Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Directory Name Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.DirectoryNameType
   * @generated
   */
	EClass getDirectoryNameType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.DirectoryNameType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.DirectoryNameType#getValue()
   * @see #getDirectoryNameType()
   * @generated
   */
	EAttribute getDirectoryNameType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.DirectoryNameType#getFilesystemName <em>Filesystem Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Filesystem Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.DirectoryNameType#getFilesystemName()
   * @see #getDirectoryNameType()
   * @generated
   */
	EAttribute getDirectoryNameType_FilesystemName();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.DirectoryNameType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.DirectoryNameType#getAnyAttribute()
   * @see #getDirectoryNameType()
   * @generated
   */
	EAttribute getDirectoryNameType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot
   * @generated
   */
	EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
	EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getArgument <em>Argument</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Argument</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getArgument()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Argument();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getCoreDumpLimit <em>Core Dump Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Core Dump Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getCoreDumpLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_CoreDumpLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getCPUTimeLimit <em>CPU Time Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>CPU Time Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getCPUTimeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_CPUTimeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getDataSegmentLimit <em>Data Segment Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Data Segment Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getDataSegmentLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_DataSegmentLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getEnvironment <em>Environment</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Environment</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getEnvironment()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Environment();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getError <em>Error</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Error</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getError()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Error();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getExecutable <em>Executable</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Executable</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getExecutable()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Executable();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getFileSizeLimit <em>File Size Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>File Size Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getFileSizeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_FileSizeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getGroupName <em>Group Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Group Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getGroupName()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_GroupName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Input</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getInput()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Input();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getLockedMemoryLimit <em>Locked Memory Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Locked Memory Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getLockedMemoryLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_LockedMemoryLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getMemoryLimit <em>Memory Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Memory Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getMemoryLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_MemoryLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getOpenDescriptorsLimit <em>Open Descriptors Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Open Descriptors Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getOpenDescriptorsLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_OpenDescriptorsLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getOutput <em>Output</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Output</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getOutput()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_Output();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getPipeSizeLimit <em>Pipe Size Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Pipe Size Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getPipeSizeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_PipeSizeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getPOSIXApplication <em>POSIX Application</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>POSIX Application</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getPOSIXApplication()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_POSIXApplication();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getProcessCountLimit <em>Process Count Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Process Count Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getProcessCountLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_ProcessCountLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getStackSizeLimit <em>Stack Size Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Stack Size Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getStackSizeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_StackSizeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getThreadCountLimit <em>Thread Count Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Thread Count Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getThreadCountLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_ThreadCountLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getUserName <em>User Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>User Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getUserName()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_UserName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getVirtualMemoryLimit <em>Virtual Memory Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Virtual Memory Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getVirtualMemoryLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_VirtualMemoryLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getWallTimeLimit <em>Wall Time Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Wall Time Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getWallTimeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_WallTimeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.DocumentRoot#getWorkingDirectory <em>Working Directory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Working Directory</em>'.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot#getWorkingDirectory()
   * @see #getDocumentRoot()
   * @generated
   */
	EReference getDocumentRoot_WorkingDirectory();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.EnvironmentType <em>Environment Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Environment Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.EnvironmentType
   * @generated
   */
	EClass getEnvironmentType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.EnvironmentType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.EnvironmentType#getValue()
   * @see #getEnvironmentType()
   * @generated
   */
	EAttribute getEnvironmentType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.EnvironmentType#getFilesystemName <em>Filesystem Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Filesystem Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.EnvironmentType#getFilesystemName()
   * @see #getEnvironmentType()
   * @generated
   */
	EAttribute getEnvironmentType_FilesystemName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.EnvironmentType#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.EnvironmentType#getName()
   * @see #getEnvironmentType()
   * @generated
   */
	EAttribute getEnvironmentType_Name();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.EnvironmentType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.EnvironmentType#getAnyAttribute()
   * @see #getEnvironmentType()
   * @generated
   */
	EAttribute getEnvironmentType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.FileNameType <em>File Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>File Name Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.FileNameType
   * @generated
   */
	EClass getFileNameType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.FileNameType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.FileNameType#getValue()
   * @see #getFileNameType()
   * @generated
   */
	EAttribute getFileNameType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.FileNameType#getFilesystemName <em>Filesystem Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Filesystem Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.FileNameType#getFilesystemName()
   * @see #getFileNameType()
   * @generated
   */
	EAttribute getFileNameType_FilesystemName();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.FileNameType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.FileNameType#getAnyAttribute()
   * @see #getFileNameType()
   * @generated
   */
	EAttribute getFileNameType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.GroupNameType <em>Group Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Group Name Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.GroupNameType
   * @generated
   */
	EClass getGroupNameType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.GroupNameType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.GroupNameType#getValue()
   * @see #getGroupNameType()
   * @generated
   */
	EAttribute getGroupNameType_Value();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.GroupNameType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.GroupNameType#getAnyAttribute()
   * @see #getGroupNameType()
   * @generated
   */
	EAttribute getGroupNameType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.LimitsType <em>Limits Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>Limits Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.LimitsType
   * @generated
   */
	EClass getLimitsType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.LimitsType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.LimitsType#getValue()
   * @see #getLimitsType()
   * @generated
   */
	EAttribute getLimitsType_Value();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.LimitsType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.LimitsType#getAnyAttribute()
   * @see #getLimitsType()
   * @generated
   */
	EAttribute getLimitsType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType <em>POSIX Application Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>POSIX Application Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType
   * @generated
   */
	EClass getPOSIXApplicationType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getExecutable <em>Executable</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Executable</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getExecutable()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_Executable();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getArgument <em>Argument</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Argument</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getArgument()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_Argument();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Input</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getInput()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_Input();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOutput <em>Output</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Output</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOutput()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_Output();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getError <em>Error</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Error</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getError()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_Error();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWorkingDirectory <em>Working Directory</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Working Directory</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWorkingDirectory()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_WorkingDirectory();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getEnvironment <em>Environment</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Environment</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getEnvironment()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_Environment();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWallTimeLimit <em>Wall Time Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Wall Time Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWallTimeLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_WallTimeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getFileSizeLimit <em>File Size Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>File Size Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getFileSizeLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_FileSizeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCoreDumpLimit <em>Core Dump Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Core Dump Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCoreDumpLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_CoreDumpLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getDataSegmentLimit <em>Data Segment Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Data Segment Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getDataSegmentLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_DataSegmentLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getLockedMemoryLimit <em>Locked Memory Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Locked Memory Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getLockedMemoryLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_LockedMemoryLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getMemoryLimit <em>Memory Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Memory Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getMemoryLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_MemoryLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOpenDescriptorsLimit <em>Open Descriptors Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Open Descriptors Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOpenDescriptorsLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_OpenDescriptorsLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getPipeSizeLimit <em>Pipe Size Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Pipe Size Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getPipeSizeLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_PipeSizeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getStackSizeLimit <em>Stack Size Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Stack Size Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getStackSizeLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_StackSizeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCPUTimeLimit <em>CPU Time Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>CPU Time Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCPUTimeLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_CPUTimeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getProcessCountLimit <em>Process Count Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Process Count Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getProcessCountLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_ProcessCountLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getVirtualMemoryLimit <em>Virtual Memory Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Virtual Memory Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getVirtualMemoryLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_VirtualMemoryLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getThreadCountLimit <em>Thread Count Limit</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Thread Count Limit</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getThreadCountLimit()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_ThreadCountLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getUserName <em>User Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>User Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getUserName()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_UserName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getGroupName <em>Group Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Group Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getGroupName()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EReference getPOSIXApplicationType_GroupName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getName()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EAttribute getPOSIXApplicationType_Name();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType#getAnyAttribute()
   * @see #getPOSIXApplicationType()
   * @generated
   */
	EAttribute getPOSIXApplicationType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.jsdl.model.posix.UserNameType <em>User Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for class '<em>User Name Type</em>'.
   * @see eu.geclipse.jsdl.model.posix.UserNameType
   * @generated
   */
	EClass getUserNameType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.jsdl.model.posix.UserNameType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.jsdl.model.posix.UserNameType#getValue()
   * @see #getUserNameType()
   * @generated
   */
	EAttribute getUserNameType_Value();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.jsdl.model.posix.UserNameType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.jsdl.model.posix.UserNameType#getAnyAttribute()
   * @see #getUserNameType()
   * @generated
   */
	EAttribute getUserNameType_AnyAttribute();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
	PosixFactory getPosixFactory();

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
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.ArgumentTypeImpl <em>Argument Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.ArgumentTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getArgumentType()
     * @generated
     */
		EClass ARGUMENT_TYPE = eINSTANCE.getArgumentType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ARGUMENT_TYPE__VALUE = eINSTANCE.getArgumentType_Value();

    /**
     * The meta object literal for the '<em><b>Filesystem Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ARGUMENT_TYPE__FILESYSTEM_NAME = eINSTANCE.getArgumentType_FilesystemName();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ARGUMENT_TYPE__ANY_ATTRIBUTE = eINSTANCE.getArgumentType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl <em>Directory Name Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.DirectoryNameTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getDirectoryNameType()
     * @generated
     */
		EClass DIRECTORY_NAME_TYPE = eINSTANCE.getDirectoryNameType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DIRECTORY_NAME_TYPE__VALUE = eINSTANCE.getDirectoryNameType_Value();

    /**
     * The meta object literal for the '<em><b>Filesystem Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DIRECTORY_NAME_TYPE__FILESYSTEM_NAME = eINSTANCE.getDirectoryNameType_FilesystemName();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE = eINSTANCE.getDirectoryNameType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.DocumentRootImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getDocumentRoot()
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
     * The meta object literal for the '<em><b>Argument</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__ARGUMENT = eINSTANCE.getDocumentRoot_Argument();

    /**
     * The meta object literal for the '<em><b>Core Dump Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__CORE_DUMP_LIMIT = eINSTANCE.getDocumentRoot_CoreDumpLimit();

    /**
     * The meta object literal for the '<em><b>CPU Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__CPU_TIME_LIMIT = eINSTANCE.getDocumentRoot_CPUTimeLimit();

    /**
     * The meta object literal for the '<em><b>Data Segment Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__DATA_SEGMENT_LIMIT = eINSTANCE.getDocumentRoot_DataSegmentLimit();

    /**
     * The meta object literal for the '<em><b>Environment</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__ENVIRONMENT = eINSTANCE.getDocumentRoot_Environment();

    /**
     * The meta object literal for the '<em><b>Error</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__ERROR = eINSTANCE.getDocumentRoot_Error();

    /**
     * The meta object literal for the '<em><b>Executable</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__EXECUTABLE = eINSTANCE.getDocumentRoot_Executable();

    /**
     * The meta object literal for the '<em><b>File Size Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__FILE_SIZE_LIMIT = eINSTANCE.getDocumentRoot_FileSizeLimit();

    /**
     * The meta object literal for the '<em><b>Group Name</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__GROUP_NAME = eINSTANCE.getDocumentRoot_GroupName();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__INPUT = eINSTANCE.getDocumentRoot_Input();

    /**
     * The meta object literal for the '<em><b>Locked Memory Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT = eINSTANCE.getDocumentRoot_LockedMemoryLimit();

    /**
     * The meta object literal for the '<em><b>Memory Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__MEMORY_LIMIT = eINSTANCE.getDocumentRoot_MemoryLimit();

    /**
     * The meta object literal for the '<em><b>Open Descriptors Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT = eINSTANCE.getDocumentRoot_OpenDescriptorsLimit();

    /**
     * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__OUTPUT = eINSTANCE.getDocumentRoot_Output();

    /**
     * The meta object literal for the '<em><b>Pipe Size Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__PIPE_SIZE_LIMIT = eINSTANCE.getDocumentRoot_PipeSizeLimit();

    /**
     * The meta object literal for the '<em><b>POSIX Application</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__POSIX_APPLICATION = eINSTANCE.getDocumentRoot_POSIXApplication();

    /**
     * The meta object literal for the '<em><b>Process Count Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__PROCESS_COUNT_LIMIT = eINSTANCE.getDocumentRoot_ProcessCountLimit();

    /**
     * The meta object literal for the '<em><b>Stack Size Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__STACK_SIZE_LIMIT = eINSTANCE.getDocumentRoot_StackSizeLimit();

    /**
     * The meta object literal for the '<em><b>Thread Count Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__THREAD_COUNT_LIMIT = eINSTANCE.getDocumentRoot_ThreadCountLimit();

    /**
     * The meta object literal for the '<em><b>User Name</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__USER_NAME = eINSTANCE.getDocumentRoot_UserName();

    /**
     * The meta object literal for the '<em><b>Virtual Memory Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT = eINSTANCE.getDocumentRoot_VirtualMemoryLimit();

    /**
     * The meta object literal for the '<em><b>Wall Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__WALL_TIME_LIMIT = eINSTANCE.getDocumentRoot_WallTimeLimit();

    /**
     * The meta object literal for the '<em><b>Working Directory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference DOCUMENT_ROOT__WORKING_DIRECTORY = eINSTANCE.getDocumentRoot_WorkingDirectory();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.EnvironmentTypeImpl <em>Environment Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.EnvironmentTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getEnvironmentType()
     * @generated
     */
		EClass ENVIRONMENT_TYPE = eINSTANCE.getEnvironmentType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ENVIRONMENT_TYPE__VALUE = eINSTANCE.getEnvironmentType_Value();

    /**
     * The meta object literal for the '<em><b>Filesystem Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ENVIRONMENT_TYPE__FILESYSTEM_NAME = eINSTANCE.getEnvironmentType_FilesystemName();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ENVIRONMENT_TYPE__NAME = eINSTANCE.getEnvironmentType_Name();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute ENVIRONMENT_TYPE__ANY_ATTRIBUTE = eINSTANCE.getEnvironmentType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.FileNameTypeImpl <em>File Name Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.FileNameTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getFileNameType()
     * @generated
     */
		EClass FILE_NAME_TYPE = eINSTANCE.getFileNameType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_NAME_TYPE__VALUE = eINSTANCE.getFileNameType_Value();

    /**
     * The meta object literal for the '<em><b>Filesystem Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_NAME_TYPE__FILESYSTEM_NAME = eINSTANCE.getFileNameType_FilesystemName();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute FILE_NAME_TYPE__ANY_ATTRIBUTE = eINSTANCE.getFileNameType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.GroupNameTypeImpl <em>Group Name Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.GroupNameTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getGroupNameType()
     * @generated
     */
		EClass GROUP_NAME_TYPE = eINSTANCE.getGroupNameType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute GROUP_NAME_TYPE__VALUE = eINSTANCE.getGroupNameType_Value();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute GROUP_NAME_TYPE__ANY_ATTRIBUTE = eINSTANCE.getGroupNameType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.LimitsTypeImpl <em>Limits Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.LimitsTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getLimitsType()
     * @generated
     */
		EClass LIMITS_TYPE = eINSTANCE.getLimitsType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute LIMITS_TYPE__VALUE = eINSTANCE.getLimitsType_Value();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute LIMITS_TYPE__ANY_ATTRIBUTE = eINSTANCE.getLimitsType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl <em>POSIX Application Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getPOSIXApplicationType()
     * @generated
     */
		EClass POSIX_APPLICATION_TYPE = eINSTANCE.getPOSIXApplicationType();

    /**
     * The meta object literal for the '<em><b>Executable</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__EXECUTABLE = eINSTANCE.getPOSIXApplicationType_Executable();

    /**
     * The meta object literal for the '<em><b>Argument</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__ARGUMENT = eINSTANCE.getPOSIXApplicationType_Argument();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__INPUT = eINSTANCE.getPOSIXApplicationType_Input();

    /**
     * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__OUTPUT = eINSTANCE.getPOSIXApplicationType_Output();

    /**
     * The meta object literal for the '<em><b>Error</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__ERROR = eINSTANCE.getPOSIXApplicationType_Error();

    /**
     * The meta object literal for the '<em><b>Working Directory</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__WORKING_DIRECTORY = eINSTANCE.getPOSIXApplicationType_WorkingDirectory();

    /**
     * The meta object literal for the '<em><b>Environment</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__ENVIRONMENT = eINSTANCE.getPOSIXApplicationType_Environment();

    /**
     * The meta object literal for the '<em><b>Wall Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT = eINSTANCE.getPOSIXApplicationType_WallTimeLimit();

    /**
     * The meta object literal for the '<em><b>File Size Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT = eINSTANCE.getPOSIXApplicationType_FileSizeLimit();

    /**
     * The meta object literal for the '<em><b>Core Dump Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT = eINSTANCE.getPOSIXApplicationType_CoreDumpLimit();

    /**
     * The meta object literal for the '<em><b>Data Segment Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT = eINSTANCE.getPOSIXApplicationType_DataSegmentLimit();

    /**
     * The meta object literal for the '<em><b>Locked Memory Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT = eINSTANCE.getPOSIXApplicationType_LockedMemoryLimit();

    /**
     * The meta object literal for the '<em><b>Memory Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__MEMORY_LIMIT = eINSTANCE.getPOSIXApplicationType_MemoryLimit();

    /**
     * The meta object literal for the '<em><b>Open Descriptors Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT = eINSTANCE.getPOSIXApplicationType_OpenDescriptorsLimit();

    /**
     * The meta object literal for the '<em><b>Pipe Size Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT = eINSTANCE.getPOSIXApplicationType_PipeSizeLimit();

    /**
     * The meta object literal for the '<em><b>Stack Size Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT = eINSTANCE.getPOSIXApplicationType_StackSizeLimit();

    /**
     * The meta object literal for the '<em><b>CPU Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT = eINSTANCE.getPOSIXApplicationType_CPUTimeLimit();

    /**
     * The meta object literal for the '<em><b>Process Count Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT = eINSTANCE.getPOSIXApplicationType_ProcessCountLimit();

    /**
     * The meta object literal for the '<em><b>Virtual Memory Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT = eINSTANCE.getPOSIXApplicationType_VirtualMemoryLimit();

    /**
     * The meta object literal for the '<em><b>Thread Count Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT = eINSTANCE.getPOSIXApplicationType_ThreadCountLimit();

    /**
     * The meta object literal for the '<em><b>User Name</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__USER_NAME = eINSTANCE.getPOSIXApplicationType_UserName();

    /**
     * The meta object literal for the '<em><b>Group Name</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EReference POSIX_APPLICATION_TYPE__GROUP_NAME = eINSTANCE.getPOSIXApplicationType_GroupName();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute POSIX_APPLICATION_TYPE__NAME = eINSTANCE.getPOSIXApplicationType_Name();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE = eINSTANCE.getPOSIXApplicationType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.jsdl.model.posix.impl.UserNameTypeImpl <em>User Name Type</em>}' class.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @see eu.geclipse.jsdl.model.posix.impl.UserNameTypeImpl
     * @see eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl#getUserNameType()
     * @generated
     */
		EClass USER_NAME_TYPE = eINSTANCE.getUserNameType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute USER_NAME_TYPE__VALUE = eINSTANCE.getUserNameType_Value();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
     * @generated
     */
		EAttribute USER_NAME_TYPE__ANY_ATTRIBUTE = eINSTANCE.getUserNameType_AnyAttribute();

	}

} //PosixPackage
