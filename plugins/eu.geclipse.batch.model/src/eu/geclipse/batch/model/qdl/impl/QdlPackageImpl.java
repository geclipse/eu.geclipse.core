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
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.model.qdl.BoundaryType;
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.batch.model.qdl.ExactType;
import eu.geclipse.batch.model.qdl.QdlFactory;
import eu.geclipse.batch.model.qdl.QdlPackage;
import eu.geclipse.batch.model.qdl.QueueStatusEnumeration;
import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.batch.model.qdl.RangeType;
import eu.geclipse.batch.model.qdl.RangeValueType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class QdlPackageImpl extends EPackageImpl implements QdlPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass allowedVirtualOrganizationsTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass boundaryTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass documentRootEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass exactTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass queueTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rangeTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rangeValueTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum queueStatusEnumerationEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum queueTypeEnumerationEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType descriptionTypeEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType queueStatusEnumerationObjectEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType queueTypeEnumerationObjectEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see eu.geclipse.batch.model.qdl.QdlPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private QdlPackageImpl()
  {
    super(eNS_URI, QdlFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this
   * model, and for any others upon which it depends.  Simple
   * dependencies are satisfied by calling this method on all
   * dependent packages before doing anything else.  This method drives
   * initialization for interdependent packages directly, in parallel
   * with this package, itself.
   * <p>Of this package and its interdependencies, all packages which
   * have not yet been registered by their URI values are first created
   * and registered.  The packages are then initialized in two steps:
   * meta-model objects for all of the packages are created before any
   * are initialized, since one package's meta-model objects may refer to
   * those of another.
   * <p>Invocation of this method will not affect any packages that have
   * already been initialized.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static QdlPackage init()
  {
    if (isInited) return (QdlPackage)EPackage.Registry.INSTANCE.getEPackage(QdlPackage.eNS_URI);

    // Obtain or create and register package
    QdlPackageImpl theQdlPackage = (QdlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof QdlPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new QdlPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theQdlPackage.createPackageContents();

    // Initialize created meta-data
    theQdlPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theQdlPackage.freeze();

    return theQdlPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAllowedVirtualOrganizationsType()
  {
    return allowedVirtualOrganizationsTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAllowedVirtualOrganizationsType_VOName()
  {
    return (EAttribute)allowedVirtualOrganizationsTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBoundaryType()
  {
    return boundaryTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBoundaryType_Value()
  {
    return (EAttribute)boundaryTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBoundaryType_ExclusiveBound()
  {
    return (EAttribute)boundaryTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBoundaryType_AnyAttribute()
  {
    return (EAttribute)boundaryTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDocumentRoot()
  {
    return documentRootEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_Mixed()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_XMLNSPrefixMap()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_XSISchemaLocation()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_AllowedVirtualOrganizations()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_AssignedResources()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_CPUTimeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_Description()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_JobsInQueue()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_Priority()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_Queue()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_QueueStatus()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_QueueType()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_RunningJobs()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDocumentRoot_VOName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDocumentRoot_WallTimeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExactType()
  {
    return exactTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExactType_Value()
  {
    return (EAttribute)exactTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExactType_Epsilon()
  {
    return (EAttribute)exactTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExactType_AnyAttribute()
  {
    return (EAttribute)exactTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getQueueType()
  {
    return queueTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getQueueType_QueueName()
  {
    return (EAttribute)queueTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getQueueType_Description()
  {
    return (EAttribute)queueTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getQueueType_QueueType()
  {
    return (EAttribute)queueTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getQueueType_QueueStatus()
  {
    return (EAttribute)queueTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_CPUTimeLimit()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_WallTimeLimit()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_AllowedVirtualOrganizations()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_Priority()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_RunningJobs()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_JobsInQueue()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQueueType_AssignedResources()
  {
    return (EReference)queueTypeEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRangeType()
  {
    return rangeTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeType_LowerBound()
  {
    return (EReference)rangeTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeType_UpperBound()
  {
    return (EReference)rangeTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRangeType_AnyAttribute()
  {
    return (EAttribute)rangeTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRangeValueType()
  {
    return rangeValueTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeValueType_UpperBoundedRange()
  {
    return (EReference)rangeValueTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeValueType_LowerBoundedRange()
  {
    return (EReference)rangeValueTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeValueType_Exact()
  {
    return (EReference)rangeValueTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRangeValueType_Range()
  {
    return (EReference)rangeValueTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRangeValueType_AnyAttribute()
  {
    return (EAttribute)rangeValueTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getQueueStatusEnumeration()
  {
    return queueStatusEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getQueueTypeEnumeration()
  {
    return queueTypeEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getDescriptionType()
  {
    return descriptionTypeEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getQueueStatusEnumerationObject()
  {
    return queueStatusEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getQueueTypeEnumerationObject()
  {
    return queueTypeEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QdlFactory getQdlFactory()
  {
    return (QdlFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    allowedVirtualOrganizationsTypeEClass = createEClass(ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE);
    createEAttribute(allowedVirtualOrganizationsTypeEClass, ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME);

    boundaryTypeEClass = createEClass(BOUNDARY_TYPE);
    createEAttribute(boundaryTypeEClass, BOUNDARY_TYPE__VALUE);
    createEAttribute(boundaryTypeEClass, BOUNDARY_TYPE__EXCLUSIVE_BOUND);
    createEAttribute(boundaryTypeEClass, BOUNDARY_TYPE__ANY_ATTRIBUTE);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ASSIGNED_RESOURCES);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CPU_TIME_LIMIT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__DESCRIPTION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__JOBS_IN_QUEUE);
    createEReference(documentRootEClass, DOCUMENT_ROOT__PRIORITY);
    createEReference(documentRootEClass, DOCUMENT_ROOT__QUEUE);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__QUEUE_STATUS);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__QUEUE_TYPE);
    createEReference(documentRootEClass, DOCUMENT_ROOT__RUNNING_JOBS);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__VO_NAME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__WALL_TIME_LIMIT);

    exactTypeEClass = createEClass(EXACT_TYPE);
    createEAttribute(exactTypeEClass, EXACT_TYPE__VALUE);
    createEAttribute(exactTypeEClass, EXACT_TYPE__EPSILON);
    createEAttribute(exactTypeEClass, EXACT_TYPE__ANY_ATTRIBUTE);

    queueTypeEClass = createEClass(QUEUE_TYPE);
    createEAttribute(queueTypeEClass, QUEUE_TYPE__QUEUE_NAME);
    createEAttribute(queueTypeEClass, QUEUE_TYPE__DESCRIPTION);
    createEAttribute(queueTypeEClass, QUEUE_TYPE__QUEUE_TYPE);
    createEAttribute(queueTypeEClass, QUEUE_TYPE__QUEUE_STATUS);
    createEReference(queueTypeEClass, QUEUE_TYPE__CPU_TIME_LIMIT);
    createEReference(queueTypeEClass, QUEUE_TYPE__WALL_TIME_LIMIT);
    createEReference(queueTypeEClass, QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS);
    createEReference(queueTypeEClass, QUEUE_TYPE__PRIORITY);
    createEReference(queueTypeEClass, QUEUE_TYPE__RUNNING_JOBS);
    createEReference(queueTypeEClass, QUEUE_TYPE__JOBS_IN_QUEUE);
    createEReference(queueTypeEClass, QUEUE_TYPE__ASSIGNED_RESOURCES);

    rangeTypeEClass = createEClass(RANGE_TYPE);
    createEReference(rangeTypeEClass, RANGE_TYPE__LOWER_BOUND);
    createEReference(rangeTypeEClass, RANGE_TYPE__UPPER_BOUND);
    createEAttribute(rangeTypeEClass, RANGE_TYPE__ANY_ATTRIBUTE);

    rangeValueTypeEClass = createEClass(RANGE_VALUE_TYPE);
    createEReference(rangeValueTypeEClass, RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE);
    createEReference(rangeValueTypeEClass, RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE);
    createEReference(rangeValueTypeEClass, RANGE_VALUE_TYPE__EXACT);
    createEReference(rangeValueTypeEClass, RANGE_VALUE_TYPE__RANGE);
    createEAttribute(rangeValueTypeEClass, RANGE_VALUE_TYPE__ANY_ATTRIBUTE);

    // Create enums
    queueStatusEnumerationEEnum = createEEnum(QUEUE_STATUS_ENUMERATION);
    queueTypeEnumerationEEnum = createEEnum(QUEUE_TYPE_ENUMERATION);

    // Create data types
    descriptionTypeEDataType = createEDataType(DESCRIPTION_TYPE);
    queueStatusEnumerationObjectEDataType = createEDataType(QUEUE_STATUS_ENUMERATION_OBJECT);
    queueTypeEnumerationObjectEDataType = createEDataType(QUEUE_TYPE_ENUMERATION_OBJECT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(allowedVirtualOrganizationsTypeEClass, AllowedVirtualOrganizationsType.class, "AllowedVirtualOrganizationsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAllowedVirtualOrganizationsType_VOName(), theXMLTypePackage.getString(), "vOName", null, 1, -1, AllowedVirtualOrganizationsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(boundaryTypeEClass, BoundaryType.class, "BoundaryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBoundaryType_Value(), theXMLTypePackage.getDouble(), "value", null, 0, 1, BoundaryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundaryType_ExclusiveBound(), theXMLTypePackage.getBoolean(), "exclusiveBound", null, 0, 1, BoundaryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundaryType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, BoundaryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_AllowedVirtualOrganizations(), this.getAllowedVirtualOrganizationsType(), null, "allowedVirtualOrganizations", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_AssignedResources(), this.getRangeValueType(), null, "assignedResources", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_CPUTimeLimit(), this.getRangeValueType(), null, "cPUTimeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_Description(), this.getDescriptionType(), "description", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_JobsInQueue(), this.getRangeValueType(), null, "jobsInQueue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Priority(), this.getRangeValueType(), null, "priority", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Queue(), this.getQueueType(), null, "queue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_QueueStatus(), this.getQueueStatusEnumeration(), "queueStatus", "Enabled", 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_QueueType(), this.getQueueTypeEnumeration(), "queueType", "Execution", 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_RunningJobs(), this.getRangeValueType(), null, "runningJobs", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_VOName(), theXMLTypePackage.getString(), "vOName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_WallTimeLimit(), this.getRangeValueType(), null, "wallTimeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(exactTypeEClass, ExactType.class, "ExactType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExactType_Value(), theXMLTypePackage.getDouble(), "value", null, 0, 1, ExactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExactType_Epsilon(), theXMLTypePackage.getDouble(), "epsilon", null, 0, 1, ExactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExactType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ExactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(queueTypeEClass, QueueType.class, "QueueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getQueueType_QueueName(), theXMLTypePackage.getString(), "queueName", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getQueueType_Description(), this.getDescriptionType(), "description", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getQueueType_QueueType(), this.getQueueTypeEnumeration(), "queueType", "Execution", 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getQueueType_QueueStatus(), this.getQueueStatusEnumeration(), "queueStatus", "Enabled", 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_CPUTimeLimit(), this.getRangeValueType(), null, "cPUTimeLimit", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_WallTimeLimit(), this.getRangeValueType(), null, "wallTimeLimit", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_AllowedVirtualOrganizations(), this.getAllowedVirtualOrganizationsType(), null, "allowedVirtualOrganizations", null, 0, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_Priority(), this.getRangeValueType(), null, "priority", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_RunningJobs(), this.getRangeValueType(), null, "runningJobs", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_JobsInQueue(), this.getRangeValueType(), null, "jobsInQueue", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getQueueType_AssignedResources(), this.getRangeValueType(), null, "assignedResources", null, 1, 1, QueueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(rangeTypeEClass, RangeType.class, "RangeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRangeType_LowerBound(), this.getBoundaryType(), null, "lowerBound", null, 1, 1, RangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRangeType_UpperBound(), this.getBoundaryType(), null, "upperBound", null, 1, 1, RangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRangeType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, RangeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(rangeValueTypeEClass, RangeValueType.class, "RangeValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRangeValueType_UpperBoundedRange(), this.getBoundaryType(), null, "upperBoundedRange", null, 0, 1, RangeValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRangeValueType_LowerBoundedRange(), this.getBoundaryType(), null, "lowerBoundedRange", null, 0, 1, RangeValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRangeValueType_Exact(), this.getExactType(), null, "exact", null, 0, -1, RangeValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRangeValueType_Range(), this.getRangeType(), null, "range", null, 0, -1, RangeValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getRangeValueType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, RangeValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(queueStatusEnumerationEEnum, QueueStatusEnumeration.class, "QueueStatusEnumeration");
    addEEnumLiteral(queueStatusEnumerationEEnum, QueueStatusEnumeration.ENABLED);
    addEEnumLiteral(queueStatusEnumerationEEnum, QueueStatusEnumeration.DISABLED);

    initEEnum(queueTypeEnumerationEEnum, QueueTypeEnumeration.class, "QueueTypeEnumeration");
    addEEnumLiteral(queueTypeEnumerationEEnum, QueueTypeEnumeration.EXECUTION);
    addEEnumLiteral(queueTypeEnumerationEEnum, QueueTypeEnumeration.ROUTE);

    // Initialize data types
    initEDataType(descriptionTypeEDataType, String.class, "DescriptionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(queueStatusEnumerationObjectEDataType, QueueStatusEnumeration.class, "QueueStatusEnumerationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
    initEDataType(queueTypeEnumerationObjectEDataType, QueueTypeEnumeration.class, "QueueTypeEnumerationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createExtendedMetaDataAnnotations()
  {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
    addAnnotation
      (allowedVirtualOrganizationsTypeEClass, 
       source, 
       new String[] 
       {
       "name", "AllowedVirtualOrganizationsType",
       "kind", "elementOnly"
       });
    addAnnotation
      (getAllowedVirtualOrganizationsType_VOName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "VOName",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (boundaryTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Boundary_Type",
       "kind", "simple"
       });
    addAnnotation
      (getBoundaryType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });
    addAnnotation
      (getBoundaryType_ExclusiveBound(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "exclusiveBound"
       });
    addAnnotation
      (getBoundaryType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });
    addAnnotation
      (descriptionTypeEDataType, 
       source, 
       new String[] 
       {
       "name", "Description_Type",
       "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
       });
    addAnnotation
      (documentRootEClass, 
       source, 
       new String[] 
       {
       "name", "",
       "kind", "mixed"
       });
    addAnnotation
      (getDocumentRoot_Mixed(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "name", ":mixed"
       });
    addAnnotation
      (getDocumentRoot_XMLNSPrefixMap(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "xmlns:prefix"
       });
    addAnnotation
      (getDocumentRoot_XSISchemaLocation(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "xsi:schemaLocation"
       });
    addAnnotation
      (getDocumentRoot_AllowedVirtualOrganizations(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "AllowedVirtualOrganizations",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_AssignedResources(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "AssignedResources",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_CPUTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUTimeLimit",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_Description(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Description",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_JobsInQueue(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobsInQueue",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_Priority(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Priority",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_Queue(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Queue",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_QueueStatus(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "QueueStatus",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_QueueType(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "QueueType",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_RunningJobs(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "RunningJobs",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_VOName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "VOName",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getDocumentRoot_WallTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "WallTimeLimit",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (exactTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Exact_Type",
       "kind", "simple"
       });
    addAnnotation
      (getExactType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });
    addAnnotation
      (getExactType_Epsilon(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "epsilon"
       });
    addAnnotation
      (getExactType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });
    addAnnotation
      (queueStatusEnumerationEEnum, 
       source, 
       new String[] 
       {
       "name", "QueueStatusEnumeration"
       });
    addAnnotation
      (queueStatusEnumerationObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "QueueStatusEnumeration:Object",
       "baseType", "QueueStatusEnumeration"
       });
    addAnnotation
      (queueTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Queue_Type",
       "kind", "elementOnly"
       });
    addAnnotation
      (getQueueType_QueueName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "QueueName",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_Description(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Description",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_QueueType(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "QueueType",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_QueueStatus(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "QueueStatus",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_CPUTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUTimeLimit",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_WallTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "WallTimeLimit",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_AllowedVirtualOrganizations(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "AllowedVirtualOrganizations",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_Priority(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Priority",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_RunningJobs(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "RunningJobs",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_JobsInQueue(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobsInQueue",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getQueueType_AssignedResources(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "AssignedResources",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (queueTypeEnumerationEEnum, 
       source, 
       new String[] 
       {
       "name", "QueueTypeEnumeration"
       });
    addAnnotation
      (queueTypeEnumerationObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "QueueTypeEnumeration:Object",
       "baseType", "QueueTypeEnumeration"
       });
    addAnnotation
      (rangeTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Range_Type",
       "kind", "elementOnly"
       });
    addAnnotation
      (getRangeType_LowerBound(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "LowerBound",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getRangeType_UpperBound(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "UpperBound",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getRangeType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });
    addAnnotation
      (rangeValueTypeEClass, 
       source, 
       new String[] 
       {
       "name", "RangeValue_Type",
       "kind", "elementOnly"
       });
    addAnnotation
      (getRangeValueType_UpperBoundedRange(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "UpperBoundedRange",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getRangeValueType_LowerBoundedRange(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "LowerBoundedRange",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getRangeValueType_Exact(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Exact",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getRangeValueType_Range(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Range",
       "namespace", "##targetNamespace"
       });
    addAnnotation
      (getRangeValueType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":4",
       "processing", "lax"
       });
  }

} //QdlPackageImpl
