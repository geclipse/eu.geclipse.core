/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.model.qdl;

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
 * @see eu.geclipse.batch.model.qdl.QdlFactory
 * @model kind="package"
 * @generated
 */
public interface QdlPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "qdl";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/geclipse/qdl";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "qdl";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  QdlPackage eINSTANCE = eu.geclipse.batch.model.qdl.impl.QdlPackageImpl.init();

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.AllowedVirtualOrganizationsTypeImpl <em>Allowed Virtual Organizations Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.AllowedVirtualOrganizationsTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getAllowedVirtualOrganizationsType()
   * @generated
   */
  int ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE = 0;

  /**
   * The feature id for the '<em><b>VO Name</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME = 0;

  /**
   * The number of structural features of the '<em>Allowed Virtual Organizations Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.BoundaryTypeImpl <em>Boundary Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.BoundaryTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getBoundaryType()
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
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl <em>Document Root</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.DocumentRootImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getDocumentRoot()
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
   * The feature id for the '<em><b>Allowed Virtual Organizations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS = 3;

  /**
   * The feature id for the '<em><b>Assigned Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__ASSIGNED_RESOURCES = 4;

  /**
   * The feature id for the '<em><b>CPU Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__CPU_TIME_LIMIT = 5;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__DESCRIPTION = 6;

  /**
   * The feature id for the '<em><b>Jobs In Queue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__JOBS_IN_QUEUE = 7;

  /**
   * The feature id for the '<em><b>Priority</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__PRIORITY = 8;

  /**
   * The feature id for the '<em><b>Queue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__QUEUE = 9;

  /**
   * The feature id for the '<em><b>Queue Started</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__QUEUE_STARTED = 10;

  /**
   * The feature id for the '<em><b>Queue Status</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__QUEUE_STATUS = 11;

  /**
   * The feature id for the '<em><b>Queue Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__QUEUE_TYPE = 12;

  /**
   * The feature id for the '<em><b>Running Jobs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__RUNNING_JOBS = 13;

  /**
   * The feature id for the '<em><b>VO Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__VO_NAME = 14;

  /**
   * The feature id for the '<em><b>Wall Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT__WALL_TIME_LIMIT = 15;

  /**
   * The number of structural features of the '<em>Document Root</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_ROOT_FEATURE_COUNT = 16;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.ExactTypeImpl <em>Exact Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.ExactTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getExactType()
   * @generated
   */
  int EXACT_TYPE = 3;

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
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerBoundaryTypeImpl <em>Integer Boundary Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.IntegerBoundaryTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerBoundaryType()
   * @generated
   */
  int INTEGER_BOUNDARY_TYPE = 4;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_BOUNDARY_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Exclusive Bound</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_BOUNDARY_TYPE__EXCLUSIVE_BOUND = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_BOUNDARY_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Integer Boundary Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_BOUNDARY_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl <em>Integer Exact Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerExactType()
   * @generated
   */
  int INTEGER_EXACT_TYPE = 5;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_EXACT_TYPE__VALUE = 0;

  /**
   * The feature id for the '<em><b>Epsilon</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_EXACT_TYPE__EPSILON = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_EXACT_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Integer Exact Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_EXACT_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl <em>Integer Range Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerRangeType()
   * @generated
   */
  int INTEGER_RANGE_TYPE = 6;

  /**
   * The feature id for the '<em><b>Integer Lower Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND = 0;

  /**
   * The feature id for the '<em><b>Integer Upper Bound</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND = 1;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_TYPE__ANY_ATTRIBUTE = 2;

  /**
   * The number of structural features of the '<em>Integer Range Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_TYPE_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeValueTypeImpl <em>Integer Range Value Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.IntegerRangeValueTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerRangeValueType()
   * @generated
   */
  int INTEGER_RANGE_VALUE_TYPE = 7;

  /**
   * The feature id for the '<em><b>Upper Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE = 0;

  /**
   * The feature id for the '<em><b>Lower Bounded Range</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE = 1;

  /**
   * The feature id for the '<em><b>Exact</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_VALUE_TYPE__EXACT = 2;

  /**
   * The feature id for the '<em><b>Range</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_VALUE_TYPE__RANGE = 3;

  /**
   * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_VALUE_TYPE__ANY_ATTRIBUTE = 4;

  /**
   * The number of structural features of the '<em>Integer Range Value Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTEGER_RANGE_VALUE_TYPE_FEATURE_COUNT = 5;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl <em>Queue Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.QueueTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueType()
   * @generated
   */
  int QUEUE_TYPE = 8;

  /**
   * The feature id for the '<em><b>Queue Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__QUEUE_NAME = 0;

  /**
   * The feature id for the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__DESCRIPTION = 1;

  /**
   * The feature id for the '<em><b>Queue Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__QUEUE_TYPE = 2;

  /**
   * The feature id for the '<em><b>Queue Status</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__QUEUE_STATUS = 3;

  /**
   * The feature id for the '<em><b>Queue Started</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__QUEUE_STARTED = 4;

  /**
   * The feature id for the '<em><b>CPU Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__CPU_TIME_LIMIT = 5;

  /**
   * The feature id for the '<em><b>Wall Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__WALL_TIME_LIMIT = 6;

  /**
   * The feature id for the '<em><b>Allowed Virtual Organizations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS = 7;

  /**
   * The feature id for the '<em><b>Priority</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__PRIORITY = 8;

  /**
   * The feature id for the '<em><b>Running Jobs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__RUNNING_JOBS = 9;

  /**
   * The feature id for the '<em><b>Jobs In Queue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__JOBS_IN_QUEUE = 10;

  /**
   * The feature id for the '<em><b>Assigned Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE__ASSIGNED_RESOURCES = 11;

  /**
   * The number of structural features of the '<em>Queue Type</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int QUEUE_TYPE_FEATURE_COUNT = 12;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.RangeTypeImpl <em>Range Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.RangeTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getRangeType()
   * @generated
   */
  int RANGE_TYPE = 9;

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
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.impl.RangeValueTypeImpl <em>Range Value Type</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.impl.RangeValueTypeImpl
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getRangeValueType()
   * @generated
   */
  int RANGE_VALUE_TYPE = 10;

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
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.QueueStatusEnumeration <em>Queue Status Enumeration</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueStatusEnumeration()
   * @generated
   */
  int QUEUE_STATUS_ENUMERATION = 11;

  /**
   * The meta object id for the '{@link eu.geclipse.batch.model.qdl.QueueTypeEnumeration <em>Queue Type Enumeration</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueTypeEnumeration()
   * @generated
   */
  int QUEUE_TYPE_ENUMERATION = 12;

  /**
   * The meta object id for the '<em>Description Type</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see java.lang.String
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getDescriptionType()
   * @generated
   */
  int DESCRIPTION_TYPE = 13;

  /**
   * The meta object id for the '<em>Queue Status Enumeration Object</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueStatusEnumerationObject()
   * @generated
   */
  int QUEUE_STATUS_ENUMERATION_OBJECT = 14;

  /**
   * The meta object id for the '<em>Queue Type Enumeration Object</em>' data type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueTypeEnumerationObject()
   * @generated
   */
  int QUEUE_TYPE_ENUMERATION_OBJECT = 15;


  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType <em>Allowed Virtual Organizations Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Allowed Virtual Organizations Type</em>'.
   * @see eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType
   * @generated
   */
  EClass getAllowedVirtualOrganizationsType();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType#getVOName <em>VO Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>VO Name</em>'.
   * @see eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType#getVOName()
   * @see #getAllowedVirtualOrganizationsType()
   * @generated
   */
  EAttribute getAllowedVirtualOrganizationsType_VOName();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.BoundaryType <em>Boundary Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Boundary Type</em>'.
   * @see eu.geclipse.batch.model.qdl.BoundaryType
   * @generated
   */
  EClass getBoundaryType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.BoundaryType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.batch.model.qdl.BoundaryType#getValue()
   * @see #getBoundaryType()
   * @generated
   */
  EAttribute getBoundaryType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.BoundaryType#isExclusiveBound <em>Exclusive Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exclusive Bound</em>'.
   * @see eu.geclipse.batch.model.qdl.BoundaryType#isExclusiveBound()
   * @see #getBoundaryType()
   * @generated
   */
  EAttribute getBoundaryType_ExclusiveBound();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.BoundaryType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.BoundaryType#getAnyAttribute()
   * @see #getBoundaryType()
   * @generated
   */
  EAttribute getBoundaryType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Root</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot
   * @generated
   */
  EClass getDocumentRoot();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getMixed <em>Mixed</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Mixed</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getMixed()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Mixed();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getXMLNSPrefixMap()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XMLNSPrefixMap();

  /**
   * Returns the meta object for the map '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the map '<em>XSI Schema Location</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getXSISchemaLocation()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_XSISchemaLocation();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Allowed Virtual Organizations</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getAllowedVirtualOrganizations()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_AllowedVirtualOrganizations();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getAssignedResources <em>Assigned Resources</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Assigned Resources</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getAssignedResources()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_AssignedResources();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getCPUTimeLimit <em>CPU Time Limit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>CPU Time Limit</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getCPUTimeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_CPUTimeLimit();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getDescription()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_Description();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getJobsInQueue <em>Jobs In Queue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Jobs In Queue</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getJobsInQueue()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_JobsInQueue();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getPriority <em>Priority</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Priority</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getPriority()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Priority();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueue <em>Queue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Queue</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getQueue()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_Queue();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.DocumentRoot#isQueueStarted <em>Queue Started</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Started</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#isQueueStarted()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_QueueStarted();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueueStatus <em>Queue Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Status</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getQueueStatus()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_QueueStatus();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueueType <em>Queue Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Type</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getQueueType()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_QueueType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getRunningJobs <em>Running Jobs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Running Jobs</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getRunningJobs()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_RunningJobs();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getVOName <em>VO Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>VO Name</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getVOName()
   * @see #getDocumentRoot()
   * @generated
   */
  EAttribute getDocumentRoot_VOName();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getWallTimeLimit <em>Wall Time Limit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Wall Time Limit</em>'.
   * @see eu.geclipse.batch.model.qdl.DocumentRoot#getWallTimeLimit()
   * @see #getDocumentRoot()
   * @generated
   */
  EReference getDocumentRoot_WallTimeLimit();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.ExactType <em>Exact Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Exact Type</em>'.
   * @see eu.geclipse.batch.model.qdl.ExactType
   * @generated
   */
  EClass getExactType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.ExactType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.batch.model.qdl.ExactType#getValue()
   * @see #getExactType()
   * @generated
   */
  EAttribute getExactType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.ExactType#getEpsilon <em>Epsilon</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Epsilon</em>'.
   * @see eu.geclipse.batch.model.qdl.ExactType#getEpsilon()
   * @see #getExactType()
   * @generated
   */
  EAttribute getExactType_Epsilon();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.ExactType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.ExactType#getAnyAttribute()
   * @see #getExactType()
   * @generated
   */
  EAttribute getExactType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.IntegerBoundaryType <em>Integer Boundary Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer Boundary Type</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerBoundaryType
   * @generated
   */
  EClass getIntegerBoundaryType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.IntegerBoundaryType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerBoundaryType#getValue()
   * @see #getIntegerBoundaryType()
   * @generated
   */
  EAttribute getIntegerBoundaryType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.IntegerBoundaryType#getExclusiveBound <em>Exclusive Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Exclusive Bound</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerBoundaryType#getExclusiveBound()
   * @see #getIntegerBoundaryType()
   * @generated
   */
  EAttribute getIntegerBoundaryType_ExclusiveBound();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.IntegerBoundaryType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerBoundaryType#getAnyAttribute()
   * @see #getIntegerBoundaryType()
   * @generated
   */
  EAttribute getIntegerBoundaryType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.IntegerExactType <em>Integer Exact Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer Exact Type</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerExactType
   * @generated
   */
  EClass getIntegerExactType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.IntegerExactType#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerExactType#getValue()
   * @see #getIntegerExactType()
   * @generated
   */
  EAttribute getIntegerExactType_Value();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.IntegerExactType#getEpsilon <em>Epsilon</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Epsilon</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerExactType#getEpsilon()
   * @see #getIntegerExactType()
   * @generated
   */
  EAttribute getIntegerExactType_Epsilon();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.IntegerExactType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerExactType#getAnyAttribute()
   * @see #getIntegerExactType()
   * @generated
   */
  EAttribute getIntegerExactType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.IntegerRangeType <em>Integer Range Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer Range Type</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeType
   * @generated
   */
  EClass getIntegerRangeType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerLowerBound <em>Integer Lower Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Integer Lower Bound</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerLowerBound()
   * @see #getIntegerRangeType()
   * @generated
   */
  EReference getIntegerRangeType_IntegerLowerBound();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerUpperBound <em>Integer Upper Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Integer Upper Bound</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeType#getIntegerUpperBound()
   * @see #getIntegerRangeType()
   * @generated
   */
  EReference getIntegerRangeType_IntegerUpperBound();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.IntegerRangeType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeType#getAnyAttribute()
   * @see #getIntegerRangeType()
   * @generated
   */
  EAttribute getIntegerRangeType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType <em>Integer Range Value Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Integer Range Value Type</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeValueType
   * @generated
   */
  EClass getIntegerRangeValueType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Upper Bounded Range</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeValueType#getUpperBoundedRange()
   * @see #getIntegerRangeValueType()
   * @generated
   */
  EReference getIntegerRangeValueType_UpperBoundedRange();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower Bounded Range</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeValueType#getLowerBoundedRange()
   * @see #getIntegerRangeValueType()
   * @generated
   */
  EReference getIntegerRangeValueType_LowerBoundedRange();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getExact <em>Exact</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exact</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeValueType#getExact()
   * @see #getIntegerRangeValueType()
   * @generated
   */
  EReference getIntegerRangeValueType_Exact();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getRange <em>Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Range</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeValueType#getRange()
   * @see #getIntegerRangeValueType()
   * @generated
   */
  EReference getIntegerRangeValueType_Range();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.IntegerRangeValueType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.IntegerRangeValueType#getAnyAttribute()
   * @see #getIntegerRangeValueType()
   * @generated
   */
  EAttribute getIntegerRangeValueType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.QueueType <em>Queue Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Queue Type</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType
   * @generated
   */
  EClass getQueueType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueName <em>Queue Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Name</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getQueueName()
   * @see #getQueueType()
   * @generated
   */
  EAttribute getQueueType_QueueName();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.QueueType#getDescription <em>Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Description</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getDescription()
   * @see #getQueueType()
   * @generated
   */
  EAttribute getQueueType_Description();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueType <em>Queue Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Type</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getQueueType()
   * @see #getQueueType()
   * @generated
   */
  EAttribute getQueueType_QueueType();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueStatus <em>Queue Status</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Status</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getQueueStatus()
   * @see #getQueueType()
   * @generated
   */
  EAttribute getQueueType_QueueStatus();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.batch.model.qdl.QueueType#isQueueStarted <em>Queue Started</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Queue Started</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#isQueueStarted()
   * @see #getQueueType()
   * @generated
   */
  EAttribute getQueueType_QueueStarted();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getCPUTimeLimit <em>CPU Time Limit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>CPU Time Limit</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getCPUTimeLimit()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_CPUTimeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getWallTimeLimit <em>Wall Time Limit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Wall Time Limit</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getWallTimeLimit()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_WallTimeLimit();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Allowed Virtual Organizations</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getAllowedVirtualOrganizations()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_AllowedVirtualOrganizations();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getPriority <em>Priority</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Priority</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getPriority()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_Priority();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getRunningJobs <em>Running Jobs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Running Jobs</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getRunningJobs()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_RunningJobs();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getJobsInQueue <em>Jobs In Queue</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Jobs In Queue</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getJobsInQueue()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_JobsInQueue();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.QueueType#getAssignedResources <em>Assigned Resources</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Assigned Resources</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueType#getAssignedResources()
   * @see #getQueueType()
   * @generated
   */
  EReference getQueueType_AssignedResources();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.RangeType <em>Range Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Range Type</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeType
   * @generated
   */
  EClass getRangeType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.RangeType#getLowerBound <em>Lower Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower Bound</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeType#getLowerBound()
   * @see #getRangeType()
   * @generated
   */
  EReference getRangeType_LowerBound();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.RangeType#getUpperBound <em>Upper Bound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Upper Bound</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeType#getUpperBound()
   * @see #getRangeType()
   * @generated
   */
  EReference getRangeType_UpperBound();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.RangeType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeType#getAnyAttribute()
   * @see #getRangeType()
   * @generated
   */
  EAttribute getRangeType_AnyAttribute();

  /**
   * Returns the meta object for class '{@link eu.geclipse.batch.model.qdl.RangeValueType <em>Range Value Type</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Range Value Type</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeValueType
   * @generated
   */
  EClass getRangeValueType();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.RangeValueType#getUpperBoundedRange <em>Upper Bounded Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Upper Bounded Range</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeValueType#getUpperBoundedRange()
   * @see #getRangeValueType()
   * @generated
   */
  EReference getRangeValueType_UpperBoundedRange();

  /**
   * Returns the meta object for the containment reference '{@link eu.geclipse.batch.model.qdl.RangeValueType#getLowerBoundedRange <em>Lower Bounded Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Lower Bounded Range</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeValueType#getLowerBoundedRange()
   * @see #getRangeValueType()
   * @generated
   */
  EReference getRangeValueType_LowerBoundedRange();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.batch.model.qdl.RangeValueType#getExact <em>Exact</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Exact</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeValueType#getExact()
   * @see #getRangeValueType()
   * @generated
   */
  EReference getRangeValueType_Exact();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.batch.model.qdl.RangeValueType#getRange <em>Range</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Range</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeValueType#getRange()
   * @see #getRangeValueType()
   * @generated
   */
  EReference getRangeValueType_Range();

  /**
   * Returns the meta object for the attribute list '{@link eu.geclipse.batch.model.qdl.RangeValueType#getAnyAttribute <em>Any Attribute</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Any Attribute</em>'.
   * @see eu.geclipse.batch.model.qdl.RangeValueType#getAnyAttribute()
   * @see #getRangeValueType()
   * @generated
   */
  EAttribute getRangeValueType_AnyAttribute();

  /**
   * Returns the meta object for enum '{@link eu.geclipse.batch.model.qdl.QueueStatusEnumeration <em>Queue Status Enumeration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Queue Status Enumeration</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @generated
   */
  EEnum getQueueStatusEnumeration();

  /**
   * Returns the meta object for enum '{@link eu.geclipse.batch.model.qdl.QueueTypeEnumeration <em>Queue Type Enumeration</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Queue Type Enumeration</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @generated
   */
  EEnum getQueueTypeEnumeration();

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
   * Returns the meta object for data type '{@link eu.geclipse.batch.model.qdl.QueueStatusEnumeration <em>Queue Status Enumeration Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Queue Status Enumeration Object</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @model instanceClass="eu.geclipse.batch.model.qdl.QueueStatusEnumeration"
   *        extendedMetaData="name='QueueStatusEnumeration:Object' baseType='QueueStatusEnumeration'"
   * @generated
   */
  EDataType getQueueStatusEnumerationObject();

  /**
   * Returns the meta object for data type '{@link eu.geclipse.batch.model.qdl.QueueTypeEnumeration <em>Queue Type Enumeration Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Queue Type Enumeration Object</em>'.
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @model instanceClass="eu.geclipse.batch.model.qdl.QueueTypeEnumeration"
   *        extendedMetaData="name='QueueTypeEnumeration:Object' baseType='QueueTypeEnumeration'"
   * @generated
   */
  EDataType getQueueTypeEnumerationObject();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  QdlFactory getQdlFactory();

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
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.AllowedVirtualOrganizationsTypeImpl <em>Allowed Virtual Organizations Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.AllowedVirtualOrganizationsTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getAllowedVirtualOrganizationsType()
     * @generated
     */
    EClass ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE = eINSTANCE.getAllowedVirtualOrganizationsType();

    /**
     * The meta object literal for the '<em><b>VO Name</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME = eINSTANCE.getAllowedVirtualOrganizationsType_VOName();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.BoundaryTypeImpl <em>Boundary Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.BoundaryTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getBoundaryType()
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
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.DocumentRootImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getDocumentRoot()
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
     * The meta object literal for the '<em><b>Allowed Virtual Organizations</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS = eINSTANCE.getDocumentRoot_AllowedVirtualOrganizations();

    /**
     * The meta object literal for the '<em><b>Assigned Resources</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__ASSIGNED_RESOURCES = eINSTANCE.getDocumentRoot_AssignedResources();

    /**
     * The meta object literal for the '<em><b>CPU Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__CPU_TIME_LIMIT = eINSTANCE.getDocumentRoot_CPUTimeLimit();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__DESCRIPTION = eINSTANCE.getDocumentRoot_Description();

    /**
     * The meta object literal for the '<em><b>Jobs In Queue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__JOBS_IN_QUEUE = eINSTANCE.getDocumentRoot_JobsInQueue();

    /**
     * The meta object literal for the '<em><b>Priority</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__PRIORITY = eINSTANCE.getDocumentRoot_Priority();

    /**
     * The meta object literal for the '<em><b>Queue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__QUEUE = eINSTANCE.getDocumentRoot_Queue();

    /**
     * The meta object literal for the '<em><b>Queue Started</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__QUEUE_STARTED = eINSTANCE.getDocumentRoot_QueueStarted();

    /**
     * The meta object literal for the '<em><b>Queue Status</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__QUEUE_STATUS = eINSTANCE.getDocumentRoot_QueueStatus();

    /**
     * The meta object literal for the '<em><b>Queue Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__QUEUE_TYPE = eINSTANCE.getDocumentRoot_QueueType();

    /**
     * The meta object literal for the '<em><b>Running Jobs</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__RUNNING_JOBS = eINSTANCE.getDocumentRoot_RunningJobs();

    /**
     * The meta object literal for the '<em><b>VO Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_ROOT__VO_NAME = eINSTANCE.getDocumentRoot_VOName();

    /**
     * The meta object literal for the '<em><b>Wall Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DOCUMENT_ROOT__WALL_TIME_LIMIT = eINSTANCE.getDocumentRoot_WallTimeLimit();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.ExactTypeImpl <em>Exact Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.ExactTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getExactType()
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
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerBoundaryTypeImpl <em>Integer Boundary Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.IntegerBoundaryTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerBoundaryType()
     * @generated
     */
    EClass INTEGER_BOUNDARY_TYPE = eINSTANCE.getIntegerBoundaryType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_BOUNDARY_TYPE__VALUE = eINSTANCE.getIntegerBoundaryType_Value();

    /**
     * The meta object literal for the '<em><b>Exclusive Bound</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_BOUNDARY_TYPE__EXCLUSIVE_BOUND = eINSTANCE.getIntegerBoundaryType_ExclusiveBound();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_BOUNDARY_TYPE__ANY_ATTRIBUTE = eINSTANCE.getIntegerBoundaryType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl <em>Integer Exact Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.IntegerExactTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerExactType()
     * @generated
     */
    EClass INTEGER_EXACT_TYPE = eINSTANCE.getIntegerExactType();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_EXACT_TYPE__VALUE = eINSTANCE.getIntegerExactType_Value();

    /**
     * The meta object literal for the '<em><b>Epsilon</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_EXACT_TYPE__EPSILON = eINSTANCE.getIntegerExactType_Epsilon();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_EXACT_TYPE__ANY_ATTRIBUTE = eINSTANCE.getIntegerExactType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl <em>Integer Range Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.IntegerRangeTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerRangeType()
     * @generated
     */
    EClass INTEGER_RANGE_TYPE = eINSTANCE.getIntegerRangeType();

    /**
     * The meta object literal for the '<em><b>Integer Lower Bound</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND = eINSTANCE.getIntegerRangeType_IntegerLowerBound();

    /**
     * The meta object literal for the '<em><b>Integer Upper Bound</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND = eINSTANCE.getIntegerRangeType_IntegerUpperBound();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_RANGE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getIntegerRangeType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.IntegerRangeValueTypeImpl <em>Integer Range Value Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.IntegerRangeValueTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getIntegerRangeValueType()
     * @generated
     */
    EClass INTEGER_RANGE_VALUE_TYPE = eINSTANCE.getIntegerRangeValueType();

    /**
     * The meta object literal for the '<em><b>Upper Bounded Range</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE = eINSTANCE.getIntegerRangeValueType_UpperBoundedRange();

    /**
     * The meta object literal for the '<em><b>Lower Bounded Range</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE = eINSTANCE.getIntegerRangeValueType_LowerBoundedRange();

    /**
     * The meta object literal for the '<em><b>Exact</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_RANGE_VALUE_TYPE__EXACT = eINSTANCE.getIntegerRangeValueType_Exact();

    /**
     * The meta object literal for the '<em><b>Range</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTEGER_RANGE_VALUE_TYPE__RANGE = eINSTANCE.getIntegerRangeValueType_Range();

    /**
     * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTEGER_RANGE_VALUE_TYPE__ANY_ATTRIBUTE = eINSTANCE.getIntegerRangeValueType_AnyAttribute();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl <em>Queue Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.QueueTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueType()
     * @generated
     */
    EClass QUEUE_TYPE = eINSTANCE.getQueueType();

    /**
     * The meta object literal for the '<em><b>Queue Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute QUEUE_TYPE__QUEUE_NAME = eINSTANCE.getQueueType_QueueName();

    /**
     * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute QUEUE_TYPE__DESCRIPTION = eINSTANCE.getQueueType_Description();

    /**
     * The meta object literal for the '<em><b>Queue Type</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute QUEUE_TYPE__QUEUE_TYPE = eINSTANCE.getQueueType_QueueType();

    /**
     * The meta object literal for the '<em><b>Queue Status</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute QUEUE_TYPE__QUEUE_STATUS = eINSTANCE.getQueueType_QueueStatus();

    /**
     * The meta object literal for the '<em><b>Queue Started</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute QUEUE_TYPE__QUEUE_STARTED = eINSTANCE.getQueueType_QueueStarted();

    /**
     * The meta object literal for the '<em><b>CPU Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__CPU_TIME_LIMIT = eINSTANCE.getQueueType_CPUTimeLimit();

    /**
     * The meta object literal for the '<em><b>Wall Time Limit</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__WALL_TIME_LIMIT = eINSTANCE.getQueueType_WallTimeLimit();

    /**
     * The meta object literal for the '<em><b>Allowed Virtual Organizations</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS = eINSTANCE.getQueueType_AllowedVirtualOrganizations();

    /**
     * The meta object literal for the '<em><b>Priority</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__PRIORITY = eINSTANCE.getQueueType_Priority();

    /**
     * The meta object literal for the '<em><b>Running Jobs</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__RUNNING_JOBS = eINSTANCE.getQueueType_RunningJobs();

    /**
     * The meta object literal for the '<em><b>Jobs In Queue</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__JOBS_IN_QUEUE = eINSTANCE.getQueueType_JobsInQueue();

    /**
     * The meta object literal for the '<em><b>Assigned Resources</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference QUEUE_TYPE__ASSIGNED_RESOURCES = eINSTANCE.getQueueType_AssignedResources();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.RangeTypeImpl <em>Range Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.RangeTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getRangeType()
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
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.impl.RangeValueTypeImpl <em>Range Value Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.impl.RangeValueTypeImpl
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getRangeValueType()
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
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.QueueStatusEnumeration <em>Queue Status Enumeration</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueStatusEnumeration()
     * @generated
     */
    EEnum QUEUE_STATUS_ENUMERATION = eINSTANCE.getQueueStatusEnumeration();

    /**
     * The meta object literal for the '{@link eu.geclipse.batch.model.qdl.QueueTypeEnumeration <em>Queue Type Enumeration</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueTypeEnumeration()
     * @generated
     */
    EEnum QUEUE_TYPE_ENUMERATION = eINSTANCE.getQueueTypeEnumeration();

    /**
     * The meta object literal for the '<em>Description Type</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.lang.String
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getDescriptionType()
     * @generated
     */
    EDataType DESCRIPTION_TYPE = eINSTANCE.getDescriptionType();

    /**
     * The meta object literal for the '<em>Queue Status Enumeration Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueStatusEnumerationObject()
     * @generated
     */
    EDataType QUEUE_STATUS_ENUMERATION_OBJECT = eINSTANCE.getQueueStatusEnumerationObject();

    /**
     * The meta object literal for the '<em>Queue Type Enumeration Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
     * @see eu.geclipse.batch.model.qdl.impl.QdlPackageImpl#getQueueTypeEnumerationObject()
     * @generated
     */
    EDataType QUEUE_TYPE_ENUMERATION_OBJECT = eINSTANCE.getQueueTypeEnumerationObject();

  }

} //QdlPackage
