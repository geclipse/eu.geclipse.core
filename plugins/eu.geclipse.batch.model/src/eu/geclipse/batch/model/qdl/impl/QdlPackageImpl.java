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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.model.qdl.BoundaryType;
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.batch.model.qdl.ExactType;
import eu.geclipse.batch.model.qdl.IntegerBoundaryType;
import eu.geclipse.batch.model.qdl.IntegerExactType;
import eu.geclipse.batch.model.qdl.IntegerRangeType;
import eu.geclipse.batch.model.qdl.IntegerRangeValueType;
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
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class QdlPackageImpl extends EPackageImpl implements QdlPackage {

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass allowedVirtualOrganizationsTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass boundaryTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass documentRootEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass exactTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass integerBoundaryTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass integerExactTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass integerRangeTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass integerRangeValueTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass queueTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass rangeTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EClass rangeValueTypeEClass = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EEnum queueStatusEnumerationEEnum = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EEnum queueTypeEnumerationEEnum = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EDataType descriptionTypeEDataType = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EDataType queueStatusEnumerationObjectEDataType = null;
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private EDataType queueTypeEnumerationObjectEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
   * package package URI value.
   * <p>
   * Note: the correct way to create the package is via the static factory
   * method {@link #init init()}, which also performs initialization of the
   * package, or returns the registered package, if one already exists. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see eu.geclipse.batch.model.qdl.QdlPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private QdlPackageImpl() {
    super( eNS_URI, QdlFactory.eINSTANCE );
  }
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and
   * for any others upon which it depends. Simple dependencies are satisfied by
   * calling this method on all dependent packages before doing anything else.
   * This method drives initialization for interdependent packages directly, in
   * parallel with this package, itself.
   * <p>
   * Of this package and its interdependencies, all packages which have not yet
   * been registered by their URI values are first created and registered. The
   * packages are then initialized in two steps: meta-model objects for all of
   * the packages are created before any are initialized, since one package's
   * meta-model objects may refer to those of another.
   * <p>
   * Invocation of this method will not affect any packages that have already
   * been initialized. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static QdlPackage init() {
    if( isInited )
      return ( QdlPackage )EPackage.Registry.INSTANCE.getEPackage( QdlPackage.eNS_URI );
    // Obtain or create and register package
    QdlPackageImpl theQdlPackage = ( QdlPackageImpl )( EPackage.Registry.INSTANCE.getEPackage( eNS_URI ) instanceof QdlPackageImpl
                                                                                                                                  ? EPackage.Registry.INSTANCE.getEPackage( eNS_URI )
                                                                                                                                  : new QdlPackageImpl() );
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
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getAllowedVirtualOrganizationsType() {
    return this.allowedVirtualOrganizationsTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getAllowedVirtualOrganizationsType_VOName() {
    return ( EAttribute )this.allowedVirtualOrganizationsTypeEClass.getEStructuralFeatures()
      .get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getBoundaryType() {
    return this.boundaryTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getBoundaryType_Value() {
    return ( EAttribute )this.boundaryTypeEClass.getEStructuralFeatures().get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getBoundaryType_ExclusiveBound() {
    return ( EAttribute )this.boundaryTypeEClass.getEStructuralFeatures().get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getBoundaryType_AnyAttribute() {
    return ( EAttribute )this.boundaryTypeEClass.getEStructuralFeatures().get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getDocumentRoot() {
    return this.documentRootEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getDocumentRoot_Mixed() {
    return ( EAttribute )this.documentRootEClass.getEStructuralFeatures().get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_XMLNSPrefixMap() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_XSISchemaLocation() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_AllowedVirtualOrganizations() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 3 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_AssignedResources() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 4 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_CPUTimeLimit() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 5 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getDocumentRoot_Description() {
    return ( EAttribute )this.documentRootEClass.getEStructuralFeatures().get( 6 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_JobsInQueue() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 7 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_Priority() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 8 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_Queue() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 9 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getDocumentRoot_QueueStarted() {
    return ( EAttribute )this.documentRootEClass.getEStructuralFeatures().get( 10 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getDocumentRoot_QueueStatus() {
    return ( EAttribute )this.documentRootEClass.getEStructuralFeatures().get( 11 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getDocumentRoot_QueueType() {
    return ( EAttribute )this.documentRootEClass.getEStructuralFeatures().get( 12 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_RunningJobs() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 13 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getDocumentRoot_VOName() {
    return ( EAttribute )this.documentRootEClass.getEStructuralFeatures().get( 14 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getDocumentRoot_WallTimeLimit() {
    return ( EReference )this.documentRootEClass.getEStructuralFeatures().get( 15 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getExactType() {
    return this.exactTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getExactType_Value() {
    return ( EAttribute )this.exactTypeEClass.getEStructuralFeatures().get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getExactType_Epsilon() {
    return ( EAttribute )this.exactTypeEClass.getEStructuralFeatures().get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getExactType_AnyAttribute() {
    return ( EAttribute )this.exactTypeEClass.getEStructuralFeatures().get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getIntegerBoundaryType() {
    return this.integerBoundaryTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerBoundaryType_Value() {
    return ( EAttribute )this.integerBoundaryTypeEClass.getEStructuralFeatures()
      .get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerBoundaryType_ExclusiveBound() {
    return ( EAttribute )this.integerBoundaryTypeEClass.getEStructuralFeatures()
      .get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerBoundaryType_AnyAttribute() {
    return ( EAttribute )this.integerBoundaryTypeEClass.getEStructuralFeatures()
      .get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getIntegerExactType() {
    return this.integerExactTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerExactType_Value() {
    return ( EAttribute )this.integerExactTypeEClass.getEStructuralFeatures()
      .get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerExactType_Epsilon() {
    return ( EAttribute )this.integerExactTypeEClass.getEStructuralFeatures()
      .get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerExactType_AnyAttribute() {
    return ( EAttribute )this.integerExactTypeEClass.getEStructuralFeatures()
      .get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getIntegerRangeType() {
    return this.integerRangeTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getIntegerRangeType_IntegerLowerBound() {
    return ( EReference )this.integerRangeTypeEClass.getEStructuralFeatures()
      .get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getIntegerRangeType_IntegerUpperBound() {
    return ( EReference )this.integerRangeTypeEClass.getEStructuralFeatures()
      .get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerRangeType_AnyAttribute() {
    return ( EAttribute )this.integerRangeTypeEClass.getEStructuralFeatures()
      .get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getIntegerRangeValueType() {
    return this.integerRangeValueTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getIntegerRangeValueType_UpperBoundedRange() {
    return ( EReference )this.integerRangeValueTypeEClass.getEStructuralFeatures()
      .get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getIntegerRangeValueType_LowerBoundedRange() {
    return ( EReference )this.integerRangeValueTypeEClass.getEStructuralFeatures()
      .get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getIntegerRangeValueType_Exact() {
    return ( EReference )this.integerRangeValueTypeEClass.getEStructuralFeatures()
      .get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getIntegerRangeValueType_Range() {
    return ( EReference )this.integerRangeValueTypeEClass.getEStructuralFeatures()
      .get( 3 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getIntegerRangeValueType_AnyAttribute() {
    return ( EAttribute )this.integerRangeValueTypeEClass.getEStructuralFeatures()
      .get( 4 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getQueueType() {
    return this.queueTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getQueueType_QueueName() {
    return ( EAttribute )this.queueTypeEClass.getEStructuralFeatures().get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getQueueType_Description() {
    return ( EAttribute )this.queueTypeEClass.getEStructuralFeatures().get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getQueueType_QueueType() {
    return ( EAttribute )this.queueTypeEClass.getEStructuralFeatures().get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getQueueType_QueueStatus() {
    return ( EAttribute )this.queueTypeEClass.getEStructuralFeatures().get( 3 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getQueueType_QueueStarted() {
    return ( EAttribute )this.queueTypeEClass.getEStructuralFeatures().get( 4 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_CPUTimeLimit() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 5 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_WallTimeLimit() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 6 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_AllowedVirtualOrganizations() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 7 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_Priority() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 8 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_RunningJobs() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 9 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_JobsInQueue() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 10 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getQueueType_AssignedResources() {
    return ( EReference )this.queueTypeEClass.getEStructuralFeatures().get( 11 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getRangeType() {
    return this.rangeTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getRangeType_LowerBound() {
    return ( EReference )this.rangeTypeEClass.getEStructuralFeatures().get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getRangeType_UpperBound() {
    return ( EReference )this.rangeTypeEClass.getEStructuralFeatures().get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getRangeType_AnyAttribute() {
    return ( EAttribute )this.rangeTypeEClass.getEStructuralFeatures().get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EClass getRangeValueType() {
    return this.rangeValueTypeEClass;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getRangeValueType_UpperBoundedRange() {
    return ( EReference )this.rangeValueTypeEClass.getEStructuralFeatures().get( 0 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getRangeValueType_LowerBoundedRange() {
    return ( EReference )this.rangeValueTypeEClass.getEStructuralFeatures().get( 1 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getRangeValueType_Exact() {
    return ( EReference )this.rangeValueTypeEClass.getEStructuralFeatures().get( 2 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EReference getRangeValueType_Range() {
    return ( EReference )this.rangeValueTypeEClass.getEStructuralFeatures().get( 3 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EAttribute getRangeValueType_AnyAttribute() {
    return ( EAttribute )this.rangeValueTypeEClass.getEStructuralFeatures().get( 4 );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EEnum getQueueStatusEnumeration() {
    return this.queueStatusEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EEnum getQueueTypeEnumeration() {
    return this.queueTypeEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EDataType getDescriptionType() {
    return this.descriptionTypeEDataType;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EDataType getQueueStatusEnumerationObject() {
    return this.queueStatusEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public EDataType getQueueTypeEnumerationObject() {
    return this.queueTypeEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public QdlFactory getQdlFactory() {
    return ( QdlFactory )getEFactoryInstance();
  }
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package. This method is guarded to
   * have no affect on any invocation but its first. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   */
  public void createPackageContents() {
    if( this.isCreated )
      return;
    this.isCreated = true;
    // Create classes and their features
    this.allowedVirtualOrganizationsTypeEClass = createEClass( ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE );
    createEAttribute( this.allowedVirtualOrganizationsTypeEClass,
                      ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE__VO_NAME );
    this.boundaryTypeEClass = createEClass( BOUNDARY_TYPE );
    createEAttribute( this.boundaryTypeEClass, BOUNDARY_TYPE__VALUE );
    createEAttribute( this.boundaryTypeEClass, BOUNDARY_TYPE__EXCLUSIVE_BOUND );
    createEAttribute( this.boundaryTypeEClass, BOUNDARY_TYPE__ANY_ATTRIBUTE );
    this.documentRootEClass = createEClass( DOCUMENT_ROOT );
    createEAttribute( this.documentRootEClass, DOCUMENT_ROOT__MIXED );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION );
    createEReference( this.documentRootEClass,
                      DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__ASSIGNED_RESOURCES );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__CPU_TIME_LIMIT );
    createEAttribute( this.documentRootEClass, DOCUMENT_ROOT__DESCRIPTION );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__JOBS_IN_QUEUE );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__PRIORITY );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__QUEUE );
    createEAttribute( this.documentRootEClass, DOCUMENT_ROOT__QUEUE_STARTED );
    createEAttribute( this.documentRootEClass, DOCUMENT_ROOT__QUEUE_STATUS );
    createEAttribute( this.documentRootEClass, DOCUMENT_ROOT__QUEUE_TYPE );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__RUNNING_JOBS );
    createEAttribute( this.documentRootEClass, DOCUMENT_ROOT__VO_NAME );
    createEReference( this.documentRootEClass, DOCUMENT_ROOT__WALL_TIME_LIMIT );
    this.exactTypeEClass = createEClass( EXACT_TYPE );
    createEAttribute( this.exactTypeEClass, EXACT_TYPE__VALUE );
    createEAttribute( this.exactTypeEClass, EXACT_TYPE__EPSILON );
    createEAttribute( this.exactTypeEClass, EXACT_TYPE__ANY_ATTRIBUTE );
    this.integerBoundaryTypeEClass = createEClass( INTEGER_BOUNDARY_TYPE );
    createEAttribute( this.integerBoundaryTypeEClass, INTEGER_BOUNDARY_TYPE__VALUE );
    createEAttribute( this.integerBoundaryTypeEClass,
                      INTEGER_BOUNDARY_TYPE__EXCLUSIVE_BOUND );
    createEAttribute( this.integerBoundaryTypeEClass,
                      INTEGER_BOUNDARY_TYPE__ANY_ATTRIBUTE );
    this.integerExactTypeEClass = createEClass( INTEGER_EXACT_TYPE );
    createEAttribute( this.integerExactTypeEClass, INTEGER_EXACT_TYPE__VALUE );
    createEAttribute( this.integerExactTypeEClass, INTEGER_EXACT_TYPE__EPSILON );
    createEAttribute( this.integerExactTypeEClass, INTEGER_EXACT_TYPE__ANY_ATTRIBUTE );
    this.integerRangeTypeEClass = createEClass( INTEGER_RANGE_TYPE );
    createEReference( this.integerRangeTypeEClass,
                      INTEGER_RANGE_TYPE__INTEGER_LOWER_BOUND );
    createEReference( this.integerRangeTypeEClass,
                      INTEGER_RANGE_TYPE__INTEGER_UPPER_BOUND );
    createEAttribute( this.integerRangeTypeEClass, INTEGER_RANGE_TYPE__ANY_ATTRIBUTE );
    this.integerRangeValueTypeEClass = createEClass( INTEGER_RANGE_VALUE_TYPE );
    createEReference( this.integerRangeValueTypeEClass,
                      INTEGER_RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE );
    createEReference( this.integerRangeValueTypeEClass,
                      INTEGER_RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE );
    createEReference( this.integerRangeValueTypeEClass,
                      INTEGER_RANGE_VALUE_TYPE__EXACT );
    createEReference( this.integerRangeValueTypeEClass,
                      INTEGER_RANGE_VALUE_TYPE__RANGE );
    createEAttribute( this.integerRangeValueTypeEClass,
                      INTEGER_RANGE_VALUE_TYPE__ANY_ATTRIBUTE );
    this.queueTypeEClass = createEClass( QUEUE_TYPE );
    createEAttribute( this.queueTypeEClass, QUEUE_TYPE__QUEUE_NAME );
    createEAttribute( this.queueTypeEClass, QUEUE_TYPE__DESCRIPTION );
    createEAttribute( this.queueTypeEClass, QUEUE_TYPE__QUEUE_TYPE );
    createEAttribute( this.queueTypeEClass, QUEUE_TYPE__QUEUE_STATUS );
    createEAttribute( this.queueTypeEClass, QUEUE_TYPE__QUEUE_STARTED );
    createEReference( this.queueTypeEClass, QUEUE_TYPE__CPU_TIME_LIMIT );
    createEReference( this.queueTypeEClass, QUEUE_TYPE__WALL_TIME_LIMIT );
    createEReference( this.queueTypeEClass,
                      QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS );
    createEReference( this.queueTypeEClass, QUEUE_TYPE__PRIORITY );
    createEReference( this.queueTypeEClass, QUEUE_TYPE__RUNNING_JOBS );
    createEReference( this.queueTypeEClass, QUEUE_TYPE__JOBS_IN_QUEUE );
    createEReference( this.queueTypeEClass, QUEUE_TYPE__ASSIGNED_RESOURCES );
    this.rangeTypeEClass = createEClass( RANGE_TYPE );
    createEReference( this.rangeTypeEClass, RANGE_TYPE__LOWER_BOUND );
    createEReference( this.rangeTypeEClass, RANGE_TYPE__UPPER_BOUND );
    createEAttribute( this.rangeTypeEClass, RANGE_TYPE__ANY_ATTRIBUTE );
    this.rangeValueTypeEClass = createEClass( RANGE_VALUE_TYPE );
    createEReference( this.rangeValueTypeEClass,
                      RANGE_VALUE_TYPE__UPPER_BOUNDED_RANGE );
    createEReference( this.rangeValueTypeEClass,
                      RANGE_VALUE_TYPE__LOWER_BOUNDED_RANGE );
    createEReference( this.rangeValueTypeEClass, RANGE_VALUE_TYPE__EXACT );
    createEReference( this.rangeValueTypeEClass, RANGE_VALUE_TYPE__RANGE );
    createEAttribute( this.rangeValueTypeEClass, RANGE_VALUE_TYPE__ANY_ATTRIBUTE );
    // Create enums
    this.queueStatusEnumerationEEnum = createEEnum( QUEUE_STATUS_ENUMERATION );
    this.queueTypeEnumerationEEnum = createEEnum( QUEUE_TYPE_ENUMERATION );
    // Create data types
    this.descriptionTypeEDataType = createEDataType( DESCRIPTION_TYPE );
    this.queueStatusEnumerationObjectEDataType = createEDataType( QUEUE_STATUS_ENUMERATION_OBJECT );
    this.queueTypeEnumerationObjectEDataType = createEDataType( QUEUE_TYPE_ENUMERATION_OBJECT );
  }
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model. This method
   * is guarded to have no affect on any invocation but its first. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void initializePackageContents() {
    if( this.isInitialized )
      return;
    this.isInitialized = true;
    // Initialize package
    setName( eNAME );
    setNsPrefix( eNS_PREFIX );
    setNsURI( eNS_URI );
    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = ( XMLTypePackage )EPackage.Registry.INSTANCE.getEPackage( XMLTypePackage.eNS_URI );
    // Create type parameters
    // Set bounds for type parameters
    // Add supertypes to classes
    // Initialize classes and features; add operations and parameters
    initEClass( this.allowedVirtualOrganizationsTypeEClass,
                AllowedVirtualOrganizationsType.class,
                "AllowedVirtualOrganizationsType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getAllowedVirtualOrganizationsType_VOName(),
                    theXMLTypePackage.getString(),
                    "vOName", //$NON-NLS-1$
                    null,
                    1,
                    -1,
                    AllowedVirtualOrganizationsType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.boundaryTypeEClass,
                BoundaryType.class,
                "BoundaryType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getBoundaryType_Value(),
                    theXMLTypePackage.getDouble(),
                    "value", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    BoundaryType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getBoundaryType_ExclusiveBound(),
                    theXMLTypePackage.getBoolean(),
                    "exclusiveBound", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    BoundaryType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getBoundaryType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    BoundaryType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getDocumentRoot_Mixed(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "mixed", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    null,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_XMLNSPrefixMap(),
                    this.ecorePackage.getEStringToStringMapEntry(),
                    null,
                    "xMLNSPrefixMap", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    null,
                    IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_XSISchemaLocation(),
                    this.ecorePackage.getEStringToStringMapEntry(),
                    null,
                    "xSISchemaLocation", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    null,
                    IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_AllowedVirtualOrganizations(),
                    this.getAllowedVirtualOrganizationsType(),
                    null,
                    "allowedVirtualOrganizations", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_AssignedResources(),
                    this.getIntegerRangeValueType(),
                    null,
                    "assignedResources", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_CPUTimeLimit(),
                    this.getRangeValueType(),
                    null,
                    "cPUTimeLimit", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getDocumentRoot_Description(),
                    this.getDescriptionType(),
                    "description", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_JobsInQueue(),
                    this.getIntegerRangeValueType(),
                    null,
                    "jobsInQueue", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_Priority(),
                    this.getIntegerRangeValueType(),
                    null,
                    "priority", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_Queue(),
                    this.getQueueType(),
                    null,
                    "queue", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getDocumentRoot_QueueStarted(),
                    theXMLTypePackage.getBoolean(),
                    "queueStarted", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getDocumentRoot_QueueStatus(),
                    this.getQueueStatusEnumeration(),
                    "queueStatus", //$NON-NLS-1$
                    "Enabled", //$NON-NLS-1$
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getDocumentRoot_QueueType(),
                    this.getQueueTypeEnumeration(),
                    "queueType", //$NON-NLS-1$
                    "Execution", //$NON-NLS-1$
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_RunningJobs(),
                    this.getIntegerRangeValueType(),
                    null,
                    "runningJobs", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getDocumentRoot_VOName(),
                    theXMLTypePackage.getString(),
                    "vOName", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEReference( getDocumentRoot_WallTimeLimit(),
                    this.getRangeValueType(),
                    null,
                    "wallTimeLimit", //$NON-NLS-1$
                    null,
                    0,
                    -2,
                    null,
                    IS_TRANSIENT,
                    IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.exactTypeEClass,
                ExactType.class,
                "ExactType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getExactType_Value(),
                    theXMLTypePackage.getDouble(),
                    "value", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    ExactType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getExactType_Epsilon(),
                    theXMLTypePackage.getDouble(),
                    "epsilon", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    ExactType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getExactType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    ExactType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.integerBoundaryTypeEClass,
                IntegerBoundaryType.class,
                "IntegerBoundaryType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getIntegerBoundaryType_Value(),
                    theXMLTypePackage.getInt(),
                    "value", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    IntegerBoundaryType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getIntegerBoundaryType_ExclusiveBound(),
                    theXMLTypePackage.getInt(),
                    "exclusiveBound", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    IntegerBoundaryType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getIntegerBoundaryType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    IntegerBoundaryType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.integerExactTypeEClass,
                IntegerExactType.class,
                "IntegerExactType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getIntegerExactType_Value(),
                    theXMLTypePackage.getInt(),
                    "value", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    IntegerExactType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getIntegerExactType_Epsilon(),
                    theXMLTypePackage.getInt(),
                    "epsilon", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    IntegerExactType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getIntegerExactType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    IntegerExactType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.integerRangeTypeEClass,
                IntegerRangeType.class,
                "IntegerRangeType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEReference( getIntegerRangeType_IntegerLowerBound(),
                    this.getIntegerBoundaryType(),
                    null,
                    "integerLowerBound", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    IntegerRangeType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getIntegerRangeType_IntegerUpperBound(),
                    this.getIntegerBoundaryType(),
                    null,
                    "integerUpperBound", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    IntegerRangeType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getIntegerRangeType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    IntegerRangeType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.integerRangeValueTypeEClass,
                IntegerRangeValueType.class,
                "IntegerRangeValueType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEReference( getIntegerRangeValueType_UpperBoundedRange(),
                    this.getIntegerBoundaryType(),
                    null,
                    "upperBoundedRange", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    IntegerRangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getIntegerRangeValueType_LowerBoundedRange(),
                    this.getIntegerBoundaryType(),
                    null,
                    "lowerBoundedRange", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    IntegerRangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getIntegerRangeValueType_Exact(),
                    this.getIntegerExactType(),
                    null,
                    "exact", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    IntegerRangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getIntegerRangeValueType_Range(),
                    this.getIntegerRangeType(),
                    null,
                    "range", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    IntegerRangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getIntegerRangeValueType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    IntegerRangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.queueTypeEClass,
                QueueType.class,
                "QueueType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEAttribute( getQueueType_QueueName(),
                    theXMLTypePackage.getString(),
                    "queueName", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getQueueType_Description(),
                    this.getDescriptionType(),
                    "description", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getQueueType_QueueType(),
                    this.getQueueTypeEnumeration(),
                    "queueType", //$NON-NLS-1$
                    "Execution", //$NON-NLS-1$
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getQueueType_QueueStatus(),
                    this.getQueueStatusEnumeration(),
                    "queueStatus", //$NON-NLS-1$
                    "Enabled", //$NON-NLS-1$
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getQueueType_QueueStarted(),
                    theXMLTypePackage.getBoolean(),
                    "queueStarted", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_UNSETTABLE,
                    !IS_ID,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_CPUTimeLimit(),
                    this.getRangeValueType(),
                    null,
                    "cPUTimeLimit", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_WallTimeLimit(),
                    this.getRangeValueType(),
                    null,
                    "wallTimeLimit", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_AllowedVirtualOrganizations(),
                    this.getAllowedVirtualOrganizationsType(),
                    null,
                    "allowedVirtualOrganizations", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_Priority(),
                    this.getIntegerRangeValueType(),
                    null,
                    "priority", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_RunningJobs(),
                    this.getIntegerRangeValueType(),
                    null,
                    "runningJobs", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_JobsInQueue(),
                    this.getIntegerRangeValueType(),
                    null,
                    "jobsInQueue", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getQueueType_AssignedResources(),
                    this.getIntegerRangeValueType(),
                    null,
                    "assignedResources", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    QueueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.rangeTypeEClass,
                RangeType.class,
                "RangeType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEReference( getRangeType_LowerBound(),
                    this.getBoundaryType(),
                    null,
                    "lowerBound", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    RangeType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getRangeType_UpperBound(),
                    this.getBoundaryType(),
                    null,
                    "upperBound", //$NON-NLS-1$
                    null,
                    1,
                    1,
                    RangeType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getRangeType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    RangeType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEClass( this.rangeValueTypeEClass,
                RangeValueType.class,
                "RangeValueType", //$NON-NLS-1$
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS );
    initEReference( getRangeValueType_UpperBoundedRange(),
                    this.getBoundaryType(),
                    null,
                    "upperBoundedRange", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    RangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getRangeValueType_LowerBoundedRange(),
                    this.getBoundaryType(),
                    null,
                    "lowerBoundedRange", //$NON-NLS-1$
                    null,
                    0,
                    1,
                    RangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getRangeValueType_Exact(),
                    this.getExactType(),
                    null,
                    "exact", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    RangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEReference( getRangeValueType_Range(),
                    this.getRangeType(),
                    null,
                    "range", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    RangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    IS_COMPOSITE,
                    !IS_RESOLVE_PROXIES,
                    !IS_UNSETTABLE,
                    IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    initEAttribute( getRangeValueType_AnyAttribute(),
                    this.ecorePackage.getEFeatureMapEntry(),
                    "anyAttribute", //$NON-NLS-1$
                    null,
                    0,
                    -1,
                    RangeValueType.class,
                    !IS_TRANSIENT,
                    !IS_VOLATILE,
                    IS_CHANGEABLE,
                    !IS_UNSETTABLE,
                    !IS_ID,
                    !IS_UNIQUE,
                    !IS_DERIVED,
                    IS_ORDERED );
    // Initialize enums and add enum literals
    initEEnum( this.queueStatusEnumerationEEnum,
               QueueStatusEnumeration.class,
               "QueueStatusEnumeration" ); //$NON-NLS-1$
    addEEnumLiteral( this.queueStatusEnumerationEEnum,
                     QueueStatusEnumeration.ENABLED );
    addEEnumLiteral( this.queueStatusEnumerationEEnum,
                     QueueStatusEnumeration.DISABLED );
    initEEnum( this.queueTypeEnumerationEEnum,
               QueueTypeEnumeration.class,
               "QueueTypeEnumeration" ); //$NON-NLS-1$
    addEEnumLiteral( this.queueTypeEnumerationEEnum, QueueTypeEnumeration.EXECUTION );
    addEEnumLiteral( this.queueTypeEnumerationEEnum, QueueTypeEnumeration.ROUTE );
    // Initialize data types
    initEDataType( this.descriptionTypeEDataType,
                   String.class,
                   "DescriptionType", //$NON-NLS-1$
                   IS_SERIALIZABLE,
                   !IS_GENERATED_INSTANCE_CLASS );
    initEDataType( this.queueStatusEnumerationObjectEDataType,
                   QueueStatusEnumeration.class,
                   "QueueStatusEnumerationObject", //$NON-NLS-1$
                   IS_SERIALIZABLE,
                   IS_GENERATED_INSTANCE_CLASS );
    initEDataType( this.queueTypeEnumerationObjectEDataType,
                   QueueTypeEnumeration.class,
                   "QueueTypeEnumerationObject", //$NON-NLS-1$
                   IS_SERIALIZABLE,
                   IS_GENERATED_INSTANCE_CLASS );
    // Create resource
    createResource( eNS_URI );
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
  protected void createExtendedMetaDataAnnotations() {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$
    addAnnotation( this.allowedVirtualOrganizationsTypeEClass, source, new String[]{
      "name", "AllowedVirtualOrganizationsType", "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getAllowedVirtualOrganizationsType_VOName(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "VOName", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( this.boundaryTypeEClass, source, new String[]{
      "name", "Boundary_Type", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getBoundaryType_Value(), source, new String[]{
      "name", ":0", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getBoundaryType_ExclusiveBound(), source, new String[]{
      "kind", "attribute", "name", "exclusiveBound" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getBoundaryType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":2", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
    addAnnotation( this.descriptionTypeEDataType, source, new String[]{
      "name", //$NON-NLS-1$
      "Description_Type", //$NON-NLS-1$
      "baseType", //$NON-NLS-1$
      "http://www.eclipse.org/emf/2003/XMLType#string" //$NON-NLS-1$
    } );
    addAnnotation( this.documentRootEClass, source, new String[]{
      "name", "", "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getDocumentRoot_Mixed(), source, new String[]{
      "kind", "elementWildcard", "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getDocumentRoot_XMLNSPrefixMap(), source, new String[]{
      "kind", "attribute", "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getDocumentRoot_XSISchemaLocation(), source, new String[]{
      "kind", "attribute", "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getDocumentRoot_AllowedVirtualOrganizations(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "AllowedVirtualOrganizations", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( getDocumentRoot_AssignedResources(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "AssignedResources", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_CPUTimeLimit(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "CPUTimeLimit", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_Description(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "Description", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_JobsInQueue(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "JobsInQueue", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_Priority(), source, new String[]{
      "kind", "element", "name", "Priority", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getDocumentRoot_Queue(), source, new String[]{
      "kind", "element", "name", "Queue", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getDocumentRoot_QueueStarted(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "QueueStarted", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_QueueStatus(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "QueueStatus", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_QueueType(), source, new String[]{
      "kind", "element", "name", "QueueType", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getDocumentRoot_RunningJobs(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "RunningJobs", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getDocumentRoot_VOName(), source, new String[]{
      "kind", "element", "name", "VOName", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getDocumentRoot_WallTimeLimit(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "WallTimeLimit", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( this.exactTypeEClass, source, new String[]{
      "name", "Exact_Type", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getExactType_Value(), source, new String[]{
      "name", ":0", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getExactType_Epsilon(), source, new String[]{
      "kind", "attribute", "name", "epsilon" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getExactType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":2", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
    addAnnotation( this.integerBoundaryTypeEClass, source, new String[]{
      "name", "Integer_Boundary_Type", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerBoundaryType_Value(), source, new String[]{
      "name", ":0", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerBoundaryType_ExclusiveBound(),
                   source,
                   new String[]{
                     "kind", "attribute", "name", "exclusiveBound" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                   } );
    addAnnotation( getIntegerBoundaryType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":2", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
    addAnnotation( this.integerExactTypeEClass, source, new String[]{
      "name", "Integer_Exact_Type", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerExactType_Value(), source, new String[]{
      "name", ":0", "kind", "simple" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerExactType_Epsilon(), source, new String[]{
      "kind", "attribute", "name", "epsilon" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerExactType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":2", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
    addAnnotation( this.integerRangeTypeEClass, source, new String[]{
      "name", "Integer_Range_Type", "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerRangeType_IntegerLowerBound(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "Integer_LowerBound", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( getIntegerRangeType_IntegerUpperBound(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "Integer_UpperBound", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( getIntegerRangeType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":2", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
    addAnnotation( this.integerRangeValueTypeEClass, source, new String[]{
      "name", "Integer_RangeValue_Type", "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getIntegerRangeValueType_UpperBoundedRange(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "UpperBoundedRange", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( getIntegerRangeValueType_LowerBoundedRange(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "LowerBoundedRange", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( getIntegerRangeValueType_Exact(), source, new String[]{
      "kind", "element", "name", "Exact", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getIntegerRangeValueType_Range(), source, new String[]{
      "kind", "element", "name", "Range", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getIntegerRangeValueType_AnyAttribute(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "attributeWildcard", //$NON-NLS-1$
                     "wildcards", //$NON-NLS-1$
                     "##other", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     ":4", //$NON-NLS-1$
                     "processing", //$NON-NLS-1$
                     "lax" //$NON-NLS-1$
                   } );
    addAnnotation( this.queueStatusEnumerationEEnum, source, new String[]{
      "name", "QueueStatusEnumeration" //$NON-NLS-1$ //$NON-NLS-2$
    } );
    addAnnotation( this.queueStatusEnumerationObjectEDataType, source, new String[]{
      "name", //$NON-NLS-1$
      "QueueStatusEnumeration:Object", //$NON-NLS-1$
      "baseType", //$NON-NLS-1$
      "QueueStatusEnumeration" //$NON-NLS-1$
    } );
    addAnnotation( this.queueTypeEClass, source, new String[]{
      "name", "Queue_Type", "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getQueueType_QueueName(), source, new String[]{
      "kind", "element", "name", "QueueName", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getQueueType_Description(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "Description", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_QueueType(), source, new String[]{
      "kind", "element", "name", "QueueType", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getQueueType_QueueStatus(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "QueueStatus", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_QueueStarted(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "QueueStarted", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_CPUTimeLimit(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "CPUTimeLimit", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_WallTimeLimit(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "WallTimeLimit", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_AllowedVirtualOrganizations(),
                   source,
                   new String[]{
                     "kind", //$NON-NLS-1$
                     "element", //$NON-NLS-1$
                     "name", //$NON-NLS-1$
                     "AllowedVirtualOrganizations", //$NON-NLS-1$
                     "namespace", //$NON-NLS-1$
                     "##targetNamespace" //$NON-NLS-1$
                   } );
    addAnnotation( getQueueType_Priority(), source, new String[]{
      "kind", "element", "name", "Priority", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getQueueType_RunningJobs(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "RunningJobs", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_JobsInQueue(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "JobsInQueue", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getQueueType_AssignedResources(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "AssignedResources", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( this.queueTypeEnumerationEEnum, source, new String[]{
      "name", "QueueTypeEnumeration" //$NON-NLS-1$ //$NON-NLS-2$
    } );
    addAnnotation( this.queueTypeEnumerationObjectEDataType, source, new String[]{
      "name", "QueueTypeEnumeration:Object", "baseType", "QueueTypeEnumeration" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( this.rangeTypeEClass, source, new String[]{
      "name", "Range_Type", "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getRangeType_LowerBound(), source, new String[]{
      "kind", "element", "name", "LowerBound", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getRangeType_UpperBound(), source, new String[]{
      "kind", "element", "name", "UpperBound", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getRangeType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":2", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
    addAnnotation( this.rangeValueTypeEClass, source, new String[]{
      "name", "RangeValue_Type", "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    } );
    addAnnotation( getRangeValueType_UpperBoundedRange(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "UpperBoundedRange", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getRangeValueType_LowerBoundedRange(), source, new String[]{
      "kind", //$NON-NLS-1$
      "element", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      "LowerBoundedRange", //$NON-NLS-1$
      "namespace", //$NON-NLS-1$
      "##targetNamespace" //$NON-NLS-1$
    } );
    addAnnotation( getRangeValueType_Exact(), source, new String[]{
      "kind", "element", "name", "Exact", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getRangeValueType_Range(), source, new String[]{
      "kind", "element", "name", "Range", "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    } );
    addAnnotation( getRangeValueType_AnyAttribute(), source, new String[]{
      "kind", //$NON-NLS-1$
      "attributeWildcard", //$NON-NLS-1$
      "wildcards", //$NON-NLS-1$
      "##other", //$NON-NLS-1$
      "name", //$NON-NLS-1$
      ":4", //$NON-NLS-1$
      "processing", //$NON-NLS-1$
      "lax" //$NON-NLS-1$
    } );
  }
} //QdlPackageImpl
