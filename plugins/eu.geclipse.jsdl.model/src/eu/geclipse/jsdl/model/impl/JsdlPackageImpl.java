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
 *    Mathias Stümpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.impl;


import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.BoundaryType;
import eu.geclipse.jsdl.model.CPUArchitectureType;
import eu.geclipse.jsdl.model.CandidateHostsType;
import eu.geclipse.jsdl.model.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.DocumentRoot;
import eu.geclipse.jsdl.model.ExactType;
import eu.geclipse.jsdl.model.FileSystemType;
import eu.geclipse.jsdl.model.FileSystemTypeEnumeration;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JobIdentificationType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.OperatingSystemType;
import eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.ProcessorArchitectureEnumeration;
import eu.geclipse.jsdl.model.RangeType;
import eu.geclipse.jsdl.model.RangeValueType;
import eu.geclipse.jsdl.model.ResourcesType;
import eu.geclipse.jsdl.model.SourceTargetType;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.impl.PosixPackageImpl;


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
public class JsdlPackageImpl extends EPackageImpl implements JsdlPackage 
{
  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass applicationTypeEClass = null;

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
	private EClass candidateHostsTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass cpuArchitectureTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass dataStagingTypeEClass = null;

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
	private EClass fileSystemTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass jobDefinitionTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass jobDescriptionTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass jobIdentificationTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass operatingSystemTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass operatingSystemTypeTypeEClass = null;

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
	private EClass resourcesTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass sourceTargetTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EEnum creationFlagEnumerationEEnum = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EEnum fileSystemTypeEnumerationEEnum = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EEnum operatingSystemTypeEnumerationEEnum = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EEnum processorArchitectureEnumerationEEnum = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EDataType creationFlagEnumerationObjectEDataType = null;

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
	private EDataType fileSystemTypeEnumerationObjectEDataType = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EDataType operatingSystemTypeEnumerationObjectEDataType = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EDataType processorArchitectureEnumerationObjectEDataType = null;

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
   * @see eu.geclipse.jsdl.model.JsdlPackage#eNS_URI
   * @see #init()
   * @generated
   */
	private JsdlPackageImpl()
  {
    super(eNS_URI, JsdlFactory.eINSTANCE);
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
	public static JsdlPackage init()
  {
    if (isInited) return (JsdlPackage)EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI);

    // Obtain or create and register package
    JsdlPackageImpl theJsdlPackage = (JsdlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof JsdlPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new JsdlPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Obtain or create and register interdependencies
    PosixPackageImpl thePosixPackage = (PosixPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI) instanceof PosixPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI) : PosixPackage.eINSTANCE);

    // Create package meta-data objects
    theJsdlPackage.createPackageContents();
    thePosixPackage.createPackageContents();

    // Initialize created meta-data
    theJsdlPackage.initializePackageContents();
    thePosixPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theJsdlPackage.freeze();

    return theJsdlPackage;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getApplicationType()
  {
    return applicationTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getApplicationType_ApplicationName()
  {
    return (EAttribute)applicationTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getApplicationType_ApplicationVersion()
  {
    return (EAttribute)applicationTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getApplicationType_Description()
  {
    return (EAttribute)applicationTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getApplicationType_Any()
  {
    return (EAttribute)applicationTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getApplicationType_AnyAttribute()
  {
    return (EAttribute)applicationTypeEClass.getEStructuralFeatures().get(4);
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
	public EClass getCandidateHostsType()
  {
    return candidateHostsTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getCandidateHostsType_HostName()
  {
    return (EAttribute)candidateHostsTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getCPUArchitectureType()
  {
    return cpuArchitectureTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getCPUArchitectureType_CPUArchitectureName()
  {
    return (EAttribute)cpuArchitectureTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getCPUArchitectureType_Any()
  {
    return (EAttribute)cpuArchitectureTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getCPUArchitectureType_AnyAttribute()
  {
    return (EAttribute)cpuArchitectureTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getDataStagingType()
  {
    return dataStagingTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_FileName()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_FilesystemName()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_CreationFlag()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_DeleteOnTermination()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDataStagingType_Source()
  {
    return (EReference)dataStagingTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDataStagingType_Target()
  {
    return (EReference)dataStagingTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_Any()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_Name()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDataStagingType_AnyAttribute()
  {
    return (EAttribute)dataStagingTypeEClass.getEStructuralFeatures().get(8);
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
	public EReference getDocumentRoot_Application()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_ApplicationName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_ApplicationVersion()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_CandidateHosts()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_CPUArchitecture()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_CPUArchitectureName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_CreationFlag()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_DataStaging()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_DeleteOnTermination()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_Description()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_DiskSpace()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_ExclusiveExecution()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_FileName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_FileSystem()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_FilesystemName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_FileSystemType()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_HostName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualCPUCount()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualCPUSpeed()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualCPUTime()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualDiskSpace()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualNetworkBandwidth()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualPhysicalMemory()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_IndividualVirtualMemory()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_JobAnnotation()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(27);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_JobDefinition()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_JobDescription()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_JobIdentification()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_JobName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(31);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_JobProject()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(32);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_MountPoint()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(33);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_OperatingSystem()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(34);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_OperatingSystemName()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(35);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_OperatingSystemType()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(36);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_OperatingSystemVersion()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(37);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Resources()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(38);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Source()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(39);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Target()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(40);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_TotalCPUCount()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(41);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_TotalCPUTime()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(42);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_TotalDiskSpace()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(43);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_TotalPhysicalMemory()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(44);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_TotalResourceCount()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(45);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_TotalVirtualMemory()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(46);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_URI()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(47);
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
	public EClass getFileSystemType()
  {
    return fileSystemTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileSystemType_FileSystemType()
  {
    return (EAttribute)fileSystemTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileSystemType_Description()
  {
    return (EAttribute)fileSystemTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileSystemType_MountPoint()
  {
    return (EAttribute)fileSystemTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getFileSystemType_DiskSpace()
  {
    return (EReference)fileSystemTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileSystemType_Any()
  {
    return (EAttribute)fileSystemTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileSystemType_Name()
  {
    return (EAttribute)fileSystemTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileSystemType_AnyAttribute()
  {
    return (EAttribute)fileSystemTypeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getJobDefinitionType()
  {
    return jobDefinitionTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getJobDefinitionType_JobDescription()
  {
    return (EReference)jobDefinitionTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobDefinitionType_Any()
  {
    return (EAttribute)jobDefinitionTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobDefinitionType_Id()
  {
    return (EAttribute)jobDefinitionTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobDefinitionType_AnyAttribute()
  {
    return (EAttribute)jobDefinitionTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getJobDescriptionType()
  {
    return jobDescriptionTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getJobDescriptionType_JobIdentification()
  {
    return (EReference)jobDescriptionTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getJobDescriptionType_Application()
  {
    return (EReference)jobDescriptionTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getJobDescriptionType_Resources()
  {
    return (EReference)jobDescriptionTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getJobDescriptionType_DataStaging()
  {
    return (EReference)jobDescriptionTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobDescriptionType_Any()
  {
    return (EAttribute)jobDescriptionTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobDescriptionType_AnyAttribute()
  {
    return (EAttribute)jobDescriptionTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getJobIdentificationType()
  {
    return jobIdentificationTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobIdentificationType_JobName()
  {
    return (EAttribute)jobIdentificationTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobIdentificationType_Description()
  {
    return (EAttribute)jobIdentificationTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobIdentificationType_JobAnnotation()
  {
    return (EAttribute)jobIdentificationTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobIdentificationType_JobProject()
  {
    return (EAttribute)jobIdentificationTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobIdentificationType_Any()
  {
    return (EAttribute)jobIdentificationTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getJobIdentificationType_AnyAttribute()
  {
    return (EAttribute)jobIdentificationTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getOperatingSystemType()
  {
    return operatingSystemTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getOperatingSystemType_OperatingSystemType()
  {
    return (EReference)operatingSystemTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemType_OperatingSystemVersion()
  {
    return (EAttribute)operatingSystemTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemType_Description()
  {
    return (EAttribute)operatingSystemTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemType_Any()
  {
    return (EAttribute)operatingSystemTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemType_AnyAttribute()
  {
    return (EAttribute)operatingSystemTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getOperatingSystemTypeType()
  {
    return operatingSystemTypeTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemTypeType_OperatingSystemName()
  {
    return (EAttribute)operatingSystemTypeTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemTypeType_Any()
  {
    return (EAttribute)operatingSystemTypeTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getOperatingSystemTypeType_AnyAttribute()
  {
    return (EAttribute)operatingSystemTypeTypeEClass.getEStructuralFeatures().get(2);
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
	public EClass getResourcesType()
  {
    return resourcesTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_CandidateHosts()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_FileSystem()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getResourcesType_ExclusiveExecution()
  {
    return (EAttribute)resourcesTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_OperatingSystem()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_CPUArchitecture()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualCPUSpeed()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualCPUTime()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualCPUCount()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualNetworkBandwidth()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualPhysicalMemory()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualVirtualMemory()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_IndividualDiskSpace()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_TotalCPUTime()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_TotalCPUCount()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_TotalPhysicalMemory()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_TotalVirtualMemory()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_TotalDiskSpace()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getResourcesType_TotalResourceCount()
  {
    return (EReference)resourcesTypeEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getResourcesType_Any()
  {
    return (EAttribute)resourcesTypeEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getResourcesType_AnyAttribute()
  {
    return (EAttribute)resourcesTypeEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getSourceTargetType()
  {
    return sourceTargetTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getSourceTargetType_URI()
  {
    return (EAttribute)sourceTargetTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getSourceTargetType_Any()
  {
    return (EAttribute)sourceTargetTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getSourceTargetType_AnyAttribute()
  {
    return (EAttribute)sourceTargetTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EEnum getCreationFlagEnumeration()
  {
    return creationFlagEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EEnum getFileSystemTypeEnumeration()
  {
    return fileSystemTypeEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EEnum getOperatingSystemTypeEnumeration()
  {
    return operatingSystemTypeEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EEnum getProcessorArchitectureEnumeration()
  {
    return processorArchitectureEnumerationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EDataType getCreationFlagEnumerationObject()
  {
    return creationFlagEnumerationObjectEDataType;
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
	public EDataType getFileSystemTypeEnumerationObject()
  {
    return fileSystemTypeEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EDataType getOperatingSystemTypeEnumerationObject()
  {
    return operatingSystemTypeEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EDataType getProcessorArchitectureEnumerationObject()
  {
    return processorArchitectureEnumerationObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JsdlFactory getJsdlFactory()
  {
    return (JsdlFactory)getEFactoryInstance();
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
    applicationTypeEClass = createEClass(APPLICATION_TYPE);
    createEAttribute(applicationTypeEClass, APPLICATION_TYPE__APPLICATION_NAME);
    createEAttribute(applicationTypeEClass, APPLICATION_TYPE__APPLICATION_VERSION);
    createEAttribute(applicationTypeEClass, APPLICATION_TYPE__DESCRIPTION);
    createEAttribute(applicationTypeEClass, APPLICATION_TYPE__ANY);
    createEAttribute(applicationTypeEClass, APPLICATION_TYPE__ANY_ATTRIBUTE);

    boundaryTypeEClass = createEClass(BOUNDARY_TYPE);
    createEAttribute(boundaryTypeEClass, BOUNDARY_TYPE__VALUE);
    createEAttribute(boundaryTypeEClass, BOUNDARY_TYPE__EXCLUSIVE_BOUND);
    createEAttribute(boundaryTypeEClass, BOUNDARY_TYPE__ANY_ATTRIBUTE);

    candidateHostsTypeEClass = createEClass(CANDIDATE_HOSTS_TYPE);
    createEAttribute(candidateHostsTypeEClass, CANDIDATE_HOSTS_TYPE__HOST_NAME);

    cpuArchitectureTypeEClass = createEClass(CPU_ARCHITECTURE_TYPE);
    createEAttribute(cpuArchitectureTypeEClass, CPU_ARCHITECTURE_TYPE__CPU_ARCHITECTURE_NAME);
    createEAttribute(cpuArchitectureTypeEClass, CPU_ARCHITECTURE_TYPE__ANY);
    createEAttribute(cpuArchitectureTypeEClass, CPU_ARCHITECTURE_TYPE__ANY_ATTRIBUTE);

    dataStagingTypeEClass = createEClass(DATA_STAGING_TYPE);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__FILE_NAME);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__FILESYSTEM_NAME);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__CREATION_FLAG);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__DELETE_ON_TERMINATION);
    createEReference(dataStagingTypeEClass, DATA_STAGING_TYPE__SOURCE);
    createEReference(dataStagingTypeEClass, DATA_STAGING_TYPE__TARGET);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__ANY);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__NAME);
    createEAttribute(dataStagingTypeEClass, DATA_STAGING_TYPE__ANY_ATTRIBUTE);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__APPLICATION);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__APPLICATION_NAME);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__APPLICATION_VERSION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CANDIDATE_HOSTS);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CPU_ARCHITECTURE);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__CREATION_FLAG);
    createEReference(documentRootEClass, DOCUMENT_ROOT__DATA_STAGING);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__DELETE_ON_TERMINATION);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__DESCRIPTION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__DISK_SPACE);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__EXCLUSIVE_EXECUTION);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__FILE_NAME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__FILE_SYSTEM);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__FILESYSTEM_NAME);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__FILE_SYSTEM_TYPE);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__HOST_NAME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__JOB_ANNOTATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__JOB_DEFINITION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__JOB_DESCRIPTION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__JOB_IDENTIFICATION);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__JOB_NAME);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__JOB_PROJECT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MOUNT_POINT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__OPERATING_SYSTEM);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__OPERATING_SYSTEM_NAME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__RESOURCES);
    createEReference(documentRootEClass, DOCUMENT_ROOT__SOURCE);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TARGET);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TOTAL_CPU_COUNT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TOTAL_CPU_TIME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TOTAL_DISK_SPACE);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__URI);

    exactTypeEClass = createEClass(EXACT_TYPE);
    createEAttribute(exactTypeEClass, EXACT_TYPE__VALUE);
    createEAttribute(exactTypeEClass, EXACT_TYPE__EPSILON);
    createEAttribute(exactTypeEClass, EXACT_TYPE__ANY_ATTRIBUTE);

    fileSystemTypeEClass = createEClass(FILE_SYSTEM_TYPE);
    createEAttribute(fileSystemTypeEClass, FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE);
    createEAttribute(fileSystemTypeEClass, FILE_SYSTEM_TYPE__DESCRIPTION);
    createEAttribute(fileSystemTypeEClass, FILE_SYSTEM_TYPE__MOUNT_POINT);
    createEReference(fileSystemTypeEClass, FILE_SYSTEM_TYPE__DISK_SPACE);
    createEAttribute(fileSystemTypeEClass, FILE_SYSTEM_TYPE__ANY);
    createEAttribute(fileSystemTypeEClass, FILE_SYSTEM_TYPE__NAME);
    createEAttribute(fileSystemTypeEClass, FILE_SYSTEM_TYPE__ANY_ATTRIBUTE);

    jobDefinitionTypeEClass = createEClass(JOB_DEFINITION_TYPE);
    createEReference(jobDefinitionTypeEClass, JOB_DEFINITION_TYPE__JOB_DESCRIPTION);
    createEAttribute(jobDefinitionTypeEClass, JOB_DEFINITION_TYPE__ANY);
    createEAttribute(jobDefinitionTypeEClass, JOB_DEFINITION_TYPE__ID);
    createEAttribute(jobDefinitionTypeEClass, JOB_DEFINITION_TYPE__ANY_ATTRIBUTE);

    jobDescriptionTypeEClass = createEClass(JOB_DESCRIPTION_TYPE);
    createEReference(jobDescriptionTypeEClass, JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION);
    createEReference(jobDescriptionTypeEClass, JOB_DESCRIPTION_TYPE__APPLICATION);
    createEReference(jobDescriptionTypeEClass, JOB_DESCRIPTION_TYPE__RESOURCES);
    createEReference(jobDescriptionTypeEClass, JOB_DESCRIPTION_TYPE__DATA_STAGING);
    createEAttribute(jobDescriptionTypeEClass, JOB_DESCRIPTION_TYPE__ANY);
    createEAttribute(jobDescriptionTypeEClass, JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE);

    jobIdentificationTypeEClass = createEClass(JOB_IDENTIFICATION_TYPE);
    createEAttribute(jobIdentificationTypeEClass, JOB_IDENTIFICATION_TYPE__JOB_NAME);
    createEAttribute(jobIdentificationTypeEClass, JOB_IDENTIFICATION_TYPE__DESCRIPTION);
    createEAttribute(jobIdentificationTypeEClass, JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION);
    createEAttribute(jobIdentificationTypeEClass, JOB_IDENTIFICATION_TYPE__JOB_PROJECT);
    createEAttribute(jobIdentificationTypeEClass, JOB_IDENTIFICATION_TYPE__ANY);
    createEAttribute(jobIdentificationTypeEClass, JOB_IDENTIFICATION_TYPE__ANY_ATTRIBUTE);

    operatingSystemTypeEClass = createEClass(OPERATING_SYSTEM_TYPE);
    createEReference(operatingSystemTypeEClass, OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_TYPE);
    createEAttribute(operatingSystemTypeEClass, OPERATING_SYSTEM_TYPE__OPERATING_SYSTEM_VERSION);
    createEAttribute(operatingSystemTypeEClass, OPERATING_SYSTEM_TYPE__DESCRIPTION);
    createEAttribute(operatingSystemTypeEClass, OPERATING_SYSTEM_TYPE__ANY);
    createEAttribute(operatingSystemTypeEClass, OPERATING_SYSTEM_TYPE__ANY_ATTRIBUTE);

    operatingSystemTypeTypeEClass = createEClass(OPERATING_SYSTEM_TYPE_TYPE);
    createEAttribute(operatingSystemTypeTypeEClass, OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME);
    createEAttribute(operatingSystemTypeTypeEClass, OPERATING_SYSTEM_TYPE_TYPE__ANY);
    createEAttribute(operatingSystemTypeTypeEClass, OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE);

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

    resourcesTypeEClass = createEClass(RESOURCES_TYPE);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__CANDIDATE_HOSTS);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__FILE_SYSTEM);
    createEAttribute(resourcesTypeEClass, RESOURCES_TYPE__EXCLUSIVE_EXECUTION);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__OPERATING_SYSTEM);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__CPU_ARCHITECTURE);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_CPU_TIME);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__TOTAL_CPU_TIME);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__TOTAL_CPU_COUNT);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__TOTAL_DISK_SPACE);
    createEReference(resourcesTypeEClass, RESOURCES_TYPE__TOTAL_RESOURCE_COUNT);
    createEAttribute(resourcesTypeEClass, RESOURCES_TYPE__ANY);
    createEAttribute(resourcesTypeEClass, RESOURCES_TYPE__ANY_ATTRIBUTE);

    sourceTargetTypeEClass = createEClass(SOURCE_TARGET_TYPE);
    createEAttribute(sourceTargetTypeEClass, SOURCE_TARGET_TYPE__URI);
    createEAttribute(sourceTargetTypeEClass, SOURCE_TARGET_TYPE__ANY);
    createEAttribute(sourceTargetTypeEClass, SOURCE_TARGET_TYPE__ANY_ATTRIBUTE);

    // Create enums
    creationFlagEnumerationEEnum = createEEnum(CREATION_FLAG_ENUMERATION);
    fileSystemTypeEnumerationEEnum = createEEnum(FILE_SYSTEM_TYPE_ENUMERATION);
    operatingSystemTypeEnumerationEEnum = createEEnum(OPERATING_SYSTEM_TYPE_ENUMERATION);
    processorArchitectureEnumerationEEnum = createEEnum(PROCESSOR_ARCHITECTURE_ENUMERATION);

    // Create data types
    creationFlagEnumerationObjectEDataType = createEDataType(CREATION_FLAG_ENUMERATION_OBJECT);
    descriptionTypeEDataType = createEDataType(DESCRIPTION_TYPE);
    fileSystemTypeEnumerationObjectEDataType = createEDataType(FILE_SYSTEM_TYPE_ENUMERATION_OBJECT);
    operatingSystemTypeEnumerationObjectEDataType = createEDataType(OPERATING_SYSTEM_TYPE_ENUMERATION_OBJECT);
    processorArchitectureEnumerationObjectEDataType = createEDataType(PROCESSOR_ARCHITECTURE_ENUMERATION_OBJECT);
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

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(applicationTypeEClass, ApplicationType.class, "ApplicationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getApplicationType_ApplicationName(), theXMLTypePackage.getString(), "applicationName", null, 0, 1, ApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getApplicationType_ApplicationVersion(), theXMLTypePackage.getString(), "applicationVersion", null, 0, 1, ApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getApplicationType_Description(), this.getDescriptionType(), "description", null, 0, 1, ApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getApplicationType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, ApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getApplicationType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(boundaryTypeEClass, BoundaryType.class, "BoundaryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBoundaryType_Value(), theXMLTypePackage.getDouble(), "value", null, 0, 1, BoundaryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundaryType_ExclusiveBound(), theXMLTypePackage.getBoolean(), "exclusiveBound", null, 0, 1, BoundaryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBoundaryType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, BoundaryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(candidateHostsTypeEClass, CandidateHostsType.class, "CandidateHostsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCandidateHostsType_HostName(), theXMLTypePackage.getString(), "hostName", null, 1, -1, CandidateHostsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(cpuArchitectureTypeEClass, CPUArchitectureType.class, "CPUArchitectureType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCPUArchitectureType_CPUArchitectureName(), this.getProcessorArchitectureEnumeration(), "cPUArchitectureName", "sparc", 1, 1, CPUArchitectureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCPUArchitectureType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, CPUArchitectureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCPUArchitectureType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, CPUArchitectureType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(dataStagingTypeEClass, DataStagingType.class, "DataStagingType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDataStagingType_FileName(), theXMLTypePackage.getString(), "fileName", null, 1, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDataStagingType_FilesystemName(), theXMLTypePackage.getNCName(), "filesystemName", null, 0, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDataStagingType_CreationFlag(), this.getCreationFlagEnumeration(), "creationFlag", "overwrite", 1, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDataStagingType_DeleteOnTermination(), theXMLTypePackage.getBoolean(), "deleteOnTermination", null, 0, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDataStagingType_Source(), this.getSourceTargetType(), null, "source", null, 0, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDataStagingType_Target(), this.getSourceTargetType(), null, "target", null, 0, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDataStagingType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDataStagingType_Name(), theXMLTypePackage.getNCName(), "name", null, 0, 1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDataStagingType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, DataStagingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Application(), this.getApplicationType(), null, "application", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_ApplicationName(), theXMLTypePackage.getString(), "applicationName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_ApplicationVersion(), theXMLTypePackage.getString(), "applicationVersion", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_CandidateHosts(), this.getCandidateHostsType(), null, "candidateHosts", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_CPUArchitecture(), this.getCPUArchitectureType(), null, "cPUArchitecture", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_CPUArchitectureName(), this.getProcessorArchitectureEnumeration(), "cPUArchitectureName", "sparc", 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_CreationFlag(), this.getCreationFlagEnumeration(), "creationFlag", "overwrite", 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_DataStaging(), this.getDataStagingType(), null, "dataStaging", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_DeleteOnTermination(), theXMLTypePackage.getBoolean(), "deleteOnTermination", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_Description(), this.getDescriptionType(), "description", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_DiskSpace(), this.getRangeValueType(), null, "diskSpace", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_ExclusiveExecution(), theXMLTypePackage.getBoolean(), "exclusiveExecution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_FileName(), theXMLTypePackage.getString(), "fileName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_FileSystem(), this.getFileSystemType(), null, "fileSystem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_FilesystemName(), theXMLTypePackage.getNCName(), "filesystemName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_FileSystemType(), this.getFileSystemTypeEnumeration(), "fileSystemType", "swap", 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_HostName(), theXMLTypePackage.getString(), "hostName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualCPUCount(), this.getRangeValueType(), null, "individualCPUCount", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualCPUSpeed(), this.getRangeValueType(), null, "individualCPUSpeed", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualCPUTime(), this.getRangeValueType(), null, "individualCPUTime", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualDiskSpace(), this.getRangeValueType(), null, "individualDiskSpace", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualNetworkBandwidth(), this.getRangeValueType(), null, "individualNetworkBandwidth", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualPhysicalMemory(), this.getRangeValueType(), null, "individualPhysicalMemory", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_IndividualVirtualMemory(), this.getRangeValueType(), null, "individualVirtualMemory", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_JobAnnotation(), theXMLTypePackage.getString(), "jobAnnotation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_JobDefinition(), this.getJobDefinitionType(), null, "jobDefinition", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_JobDescription(), this.getJobDescriptionType(), null, "jobDescription", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_JobIdentification(), this.getJobIdentificationType(), null, "jobIdentification", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_JobName(), theXMLTypePackage.getString(), "jobName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_JobProject(), theXMLTypePackage.getString(), "jobProject", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_MountPoint(), theXMLTypePackage.getString(), "mountPoint", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_OperatingSystem(), this.getOperatingSystemType(), null, "operatingSystem", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_OperatingSystemName(), this.getOperatingSystemTypeEnumeration(), "operatingSystemName", "Unknown", 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_OperatingSystemType(), this.getOperatingSystemTypeType(), null, "operatingSystemType", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_OperatingSystemVersion(), theXMLTypePackage.getString(), "operatingSystemVersion", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Resources(), this.getResourcesType(), null, "resources", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Source(), this.getSourceTargetType(), null, "source", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Target(), this.getSourceTargetType(), null, "target", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_TotalCPUCount(), this.getRangeValueType(), null, "totalCPUCount", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_TotalCPUTime(), this.getRangeValueType(), null, "totalCPUTime", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_TotalDiskSpace(), this.getRangeValueType(), null, "totalDiskSpace", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_TotalPhysicalMemory(), this.getRangeValueType(), null, "totalPhysicalMemory", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_TotalResourceCount(), this.getRangeValueType(), null, "totalResourceCount", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_TotalVirtualMemory(), this.getRangeValueType(), null, "totalVirtualMemory", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEAttribute(getDocumentRoot_URI(), theXMLTypePackage.getAnyURI(), "uRI", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(exactTypeEClass, ExactType.class, "ExactType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExactType_Value(), theXMLTypePackage.getDouble(), "value", null, 0, 1, ExactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExactType_Epsilon(), theXMLTypePackage.getDouble(), "epsilon", null, 0, 1, ExactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExactType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ExactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fileSystemTypeEClass, FileSystemType.class, "FileSystemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFileSystemType_FileSystemType(), this.getFileSystemTypeEnumeration(), "fileSystemType", "swap", 0, 1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileSystemType_Description(), this.getDescriptionType(), "description", null, 0, 1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileSystemType_MountPoint(), theXMLTypePackage.getString(), "mountPoint", null, 0, 1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getFileSystemType_DiskSpace(), this.getRangeValueType(), null, "diskSpace", null, 0, 1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileSystemType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileSystemType_Name(), theXMLTypePackage.getNCName(), "name", null, 1, 1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileSystemType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, FileSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jobDefinitionTypeEClass, JobDefinitionType.class, "JobDefinitionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJobDefinitionType_JobDescription(), this.getJobDescriptionType(), null, "jobDescription", null, 1, 1, JobDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobDefinitionType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, JobDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobDefinitionType_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, JobDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobDefinitionType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, JobDefinitionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jobDescriptionTypeEClass, JobDescriptionType.class, "JobDescriptionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getJobDescriptionType_JobIdentification(), this.getJobIdentificationType(), null, "jobIdentification", null, 0, 1, JobDescriptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJobDescriptionType_Application(), this.getApplicationType(), null, "application", null, 0, 1, JobDescriptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJobDescriptionType_Resources(), this.getResourcesType(), null, "resources", null, 0, 1, JobDescriptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getJobDescriptionType_DataStaging(), this.getDataStagingType(), null, "dataStaging", null, 0, -1, JobDescriptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobDescriptionType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, JobDescriptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobDescriptionType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, JobDescriptionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jobIdentificationTypeEClass, JobIdentificationType.class, "JobIdentificationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getJobIdentificationType_JobName(), theXMLTypePackage.getString(), "jobName", null, 0, 1, JobIdentificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobIdentificationType_Description(), this.getDescriptionType(), "description", null, 0, 1, JobIdentificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobIdentificationType_JobAnnotation(), theXMLTypePackage.getString(), "jobAnnotation", null, 0, -1, JobIdentificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobIdentificationType_JobProject(), theXMLTypePackage.getString(), "jobProject", null, 0, -1, JobIdentificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobIdentificationType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, JobIdentificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getJobIdentificationType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, JobIdentificationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(operatingSystemTypeEClass, OperatingSystemType.class, "OperatingSystemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getOperatingSystemType_OperatingSystemType(), this.getOperatingSystemTypeType(), null, "operatingSystemType", null, 0, 1, OperatingSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOperatingSystemType_OperatingSystemVersion(), theXMLTypePackage.getString(), "operatingSystemVersion", null, 0, 1, OperatingSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOperatingSystemType_Description(), this.getDescriptionType(), "description", null, 0, 1, OperatingSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOperatingSystemType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, OperatingSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOperatingSystemType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, OperatingSystemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(operatingSystemTypeTypeEClass, OperatingSystemTypeType.class, "OperatingSystemTypeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getOperatingSystemTypeType_OperatingSystemName(), this.getOperatingSystemTypeEnumeration(), "operatingSystemName", "Unknown", 1, 1, OperatingSystemTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOperatingSystemTypeType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, OperatingSystemTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getOperatingSystemTypeType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, OperatingSystemTypeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

    initEClass(resourcesTypeEClass, ResourcesType.class, "ResourcesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getResourcesType_CandidateHosts(), this.getCandidateHostsType(), null, "candidateHosts", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_FileSystem(), this.getFileSystemType(), null, "fileSystem", null, 0, -1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getResourcesType_ExclusiveExecution(), theXMLTypePackage.getBoolean(), "exclusiveExecution", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_OperatingSystem(), this.getOperatingSystemType(), null, "operatingSystem", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_CPUArchitecture(), this.getCPUArchitectureType(), null, "cPUArchitecture", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualCPUSpeed(), this.getRangeValueType(), null, "individualCPUSpeed", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualCPUTime(), this.getRangeValueType(), null, "individualCPUTime", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualCPUCount(), this.getRangeValueType(), null, "individualCPUCount", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualNetworkBandwidth(), this.getRangeValueType(), null, "individualNetworkBandwidth", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualPhysicalMemory(), this.getRangeValueType(), null, "individualPhysicalMemory", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualVirtualMemory(), this.getRangeValueType(), null, "individualVirtualMemory", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_IndividualDiskSpace(), this.getRangeValueType(), null, "individualDiskSpace", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_TotalCPUTime(), this.getRangeValueType(), null, "totalCPUTime", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_TotalCPUCount(), this.getRangeValueType(), null, "totalCPUCount", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_TotalPhysicalMemory(), this.getRangeValueType(), null, "totalPhysicalMemory", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_TotalVirtualMemory(), this.getRangeValueType(), null, "totalVirtualMemory", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_TotalDiskSpace(), this.getRangeValueType(), null, "totalDiskSpace", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getResourcesType_TotalResourceCount(), this.getRangeValueType(), null, "totalResourceCount", null, 0, 1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getResourcesType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getResourcesType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ResourcesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(sourceTargetTypeEClass, SourceTargetType.class, "SourceTargetType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSourceTargetType_URI(), theXMLTypePackage.getAnyURI(), "uRI", null, 0, 1, SourceTargetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSourceTargetType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, SourceTargetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSourceTargetType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, SourceTargetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(creationFlagEnumerationEEnum, CreationFlagEnumeration.class, "CreationFlagEnumeration");
    addEEnumLiteral(creationFlagEnumerationEEnum, CreationFlagEnumeration.OVERWRITE_LITERAL);
    addEEnumLiteral(creationFlagEnumerationEEnum, CreationFlagEnumeration.APPEND_LITERAL);
    addEEnumLiteral(creationFlagEnumerationEEnum, CreationFlagEnumeration.DONT_OVERWRITE_LITERAL);

    initEEnum(fileSystemTypeEnumerationEEnum, FileSystemTypeEnumeration.class, "FileSystemTypeEnumeration");
    addEEnumLiteral(fileSystemTypeEnumerationEEnum, FileSystemTypeEnumeration.SWAP_LITERAL);
    addEEnumLiteral(fileSystemTypeEnumerationEEnum, FileSystemTypeEnumeration.TEMPORARY_LITERAL);
    addEEnumLiteral(fileSystemTypeEnumerationEEnum, FileSystemTypeEnumeration.SPOOL_LITERAL);
    addEEnumLiteral(fileSystemTypeEnumerationEEnum, FileSystemTypeEnumeration.NORMAL_LITERAL);

    initEEnum(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.class, "OperatingSystemTypeEnumeration");
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.UNKNOWN_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.MACOS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.ATTUNIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.DGUX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.DECNT_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.TRU64_UNIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OPEN_VMS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.HPUX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.AIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.MVS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OS400_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OS2_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.JAVA_VM_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.MSDOS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WIN_3X_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WIN95_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WIN98_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WINNT_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WINCE_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.NCR3000_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.NET_WARE_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OSF_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.DCOS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.RELIANT_UNIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.SCO_UNIX_WARE_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.SCO_OPEN_SERVER_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.SEQUENT_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.IRIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.SOLARIS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.SUN_OS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.U6000_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.ASERIES_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.TANDEM_NSK_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.TANDEM_NT_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.BS2000_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.LINUX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.LYNX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.XENIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.VM_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.INTERACTIVE_UNIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.BSDUNIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.FREE_BSD_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.NET_BSD_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.GNU_HURD_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OS9_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.MACH_KERNEL_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.INFERNO_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.QNX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.EPOC_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.IX_WORKS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.VX_WORKS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.MI_NT_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.BE_OS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.HPMPE_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.NEXT_STEP_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.PALM_PILOT_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.RHAPSODY_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WINDOWS2000_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.DEDICATED_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OS390_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.VSE_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.TPF_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WINDOWS_RME_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.CALDERA_OPEN_UNIX_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OPEN_BSD_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.NOT_APPLICABLE_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.WINDOWS_XP_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.ZOS_LITERAL);
    addEEnumLiteral(operatingSystemTypeEnumerationEEnum, OperatingSystemTypeEnumeration.OTHER_LITERAL);

    initEEnum(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.class, "ProcessorArchitectureEnumeration");
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.SPARC_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.POWERPC_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.X86_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.X8632_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.X8664_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.PARISC_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.MIPS_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.IA64_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.ARM_LITERAL);
    addEEnumLiteral(processorArchitectureEnumerationEEnum, ProcessorArchitectureEnumeration.OTHER_LITERAL);

    // Initialize data types
    initEDataType(creationFlagEnumerationObjectEDataType, CreationFlagEnumeration.class, "CreationFlagEnumerationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
    initEDataType(descriptionTypeEDataType, String.class, "DescriptionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
    initEDataType(fileSystemTypeEnumerationObjectEDataType, FileSystemTypeEnumeration.class, "FileSystemTypeEnumerationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
    initEDataType(operatingSystemTypeEnumerationObjectEDataType, OperatingSystemTypeEnumeration.class, "OperatingSystemTypeEnumerationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
    initEDataType(processorArchitectureEnumerationObjectEDataType, ProcessorArchitectureEnumeration.class, "ProcessorArchitectureEnumerationObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
      (applicationTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Application_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getApplicationType_ApplicationName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ApplicationName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getApplicationType_ApplicationVersion(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ApplicationVersion",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getApplicationType_Description(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Description",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getApplicationType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":3",
       "processing", "lax"
       });		
    addAnnotation
      (getApplicationType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":4",
       "processing", "lax"
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
      (candidateHostsTypeEClass, 
       source, 
       new String[] 
       {
       "name", "CandidateHosts_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getCandidateHostsType_HostName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "HostName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (cpuArchitectureTypeEClass, 
       source, 
       new String[] 
       {
       "name", "CPUArchitecture_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getCPUArchitectureType_CPUArchitectureName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUArchitectureName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getCPUArchitectureType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });		
    addAnnotation
      (getCPUArchitectureType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });		
    addAnnotation
      (creationFlagEnumerationEEnum, 
       source, 
       new String[] 
       {
       "name", "CreationFlagEnumeration"
       });		
    addAnnotation
      (creationFlagEnumerationObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "CreationFlagEnumeration:Object",
       "baseType", "CreationFlagEnumeration"
       });		
    addAnnotation
      (dataStagingTypeEClass, 
       source, 
       new String[] 
       {
       "name", "DataStaging_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getDataStagingType_FileName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDataStagingType_FilesystemName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FilesystemName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDataStagingType_CreationFlag(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CreationFlag",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDataStagingType_DeleteOnTermination(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DeleteOnTermination",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDataStagingType_Source(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Source",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDataStagingType_Target(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Target",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDataStagingType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":6",
       "processing", "lax"
       });		
    addAnnotation
      (getDataStagingType_Name(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getDataStagingType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":8",
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
      (getDocumentRoot_Application(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Application",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_ApplicationName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ApplicationName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_ApplicationVersion(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ApplicationVersion",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_CandidateHosts(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CandidateHosts",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_CPUArchitecture(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUArchitecture",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_CPUArchitectureName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUArchitectureName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_CreationFlag(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CreationFlag",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_DataStaging(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DataStaging",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_DeleteOnTermination(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DeleteOnTermination",
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
      (getDocumentRoot_DiskSpace(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DiskSpace",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_ExclusiveExecution(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ExclusiveExecution",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_FileName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_FileSystem(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileSystem",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_FilesystemName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FilesystemName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_FileSystemType(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileSystemType",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_HostName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "HostName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualCPUCount(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualCPUCount",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualCPUSpeed(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualCPUSpeed",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualCPUTime(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualCPUTime",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualDiskSpace(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualDiskSpace",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualNetworkBandwidth(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualNetworkBandwidth",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualPhysicalMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualPhysicalMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_IndividualVirtualMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualVirtualMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_JobAnnotation(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobAnnotation",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_JobDefinition(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobDefinition",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_JobDescription(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobDescription",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_JobIdentification(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobIdentification",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_JobName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_JobProject(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobProject",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_MountPoint(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "MountPoint",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_OperatingSystem(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystem",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_OperatingSystemName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystemName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_OperatingSystemType(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystemType",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_OperatingSystemVersion(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystemVersion",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Resources(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Resources",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Source(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Source",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Target(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Target",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_TotalCPUCount(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalCPUCount",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_TotalCPUTime(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalCPUTime",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_TotalDiskSpace(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalDiskSpace",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_TotalPhysicalMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalPhysicalMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_TotalResourceCount(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalResourceCount",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_TotalVirtualMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalVirtualMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_URI(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "URI",
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
      (fileSystemTypeEClass, 
       source, 
       new String[] 
       {
       "name", "FileSystem_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getFileSystemType_FileSystemType(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileSystemType",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getFileSystemType_Description(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Description",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getFileSystemType_MountPoint(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "MountPoint",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getFileSystemType_DiskSpace(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DiskSpace",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getFileSystemType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":4",
       "processing", "lax"
       });		
    addAnnotation
      (getFileSystemType_Name(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getFileSystemType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":6",
       "processing", "lax"
       });		
    addAnnotation
      (fileSystemTypeEnumerationEEnum, 
       source, 
       new String[] 
       {
       "name", "FileSystemTypeEnumeration"
       });		
    addAnnotation
      (fileSystemTypeEnumerationObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "FileSystemTypeEnumeration:Object",
       "baseType", "FileSystemTypeEnumeration"
       });		
    addAnnotation
      (jobDefinitionTypeEClass, 
       source, 
       new String[] 
       {
       "name", "JobDefinition_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getJobDefinitionType_JobDescription(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobDescription",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobDefinitionType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });		
    addAnnotation
      (getJobDefinitionType_Id(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "id"
       });		
    addAnnotation
      (getJobDefinitionType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":3",
       "processing", "lax"
       });		
    addAnnotation
      (jobDescriptionTypeEClass, 
       source, 
       new String[] 
       {
       "name", "JobDescription_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getJobDescriptionType_JobIdentification(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobIdentification",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobDescriptionType_Application(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Application",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobDescriptionType_Resources(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Resources",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobDescriptionType_DataStaging(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DataStaging",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobDescriptionType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":4",
       "processing", "lax"
       });		
    addAnnotation
      (getJobDescriptionType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":5",
       "processing", "lax"
       });		
    addAnnotation
      (jobIdentificationTypeEClass, 
       source, 
       new String[] 
       {
       "name", "JobIdentification_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getJobIdentificationType_JobName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobIdentificationType_Description(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Description",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobIdentificationType_JobAnnotation(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobAnnotation",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobIdentificationType_JobProject(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "JobProject",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getJobIdentificationType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":4",
       "processing", "lax"
       });		
    addAnnotation
      (getJobIdentificationType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":5",
       "processing", "lax"
       });		
    addAnnotation
      (operatingSystemTypeEClass, 
       source, 
       new String[] 
       {
       "name", "OperatingSystem_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getOperatingSystemType_OperatingSystemType(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystemType",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getOperatingSystemType_OperatingSystemVersion(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystemVersion",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getOperatingSystemType_Description(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Description",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getOperatingSystemType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":3",
       "processing", "lax"
       });		
    addAnnotation
      (getOperatingSystemType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":4",
       "processing", "lax"
       });		
    addAnnotation
      (operatingSystemTypeEnumerationEEnum, 
       source, 
       new String[] 
       {
       "name", "OperatingSystemTypeEnumeration"
       });		
    addAnnotation
      (operatingSystemTypeEnumerationObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "OperatingSystemTypeEnumeration:Object",
       "baseType", "OperatingSystemTypeEnumeration"
       });		
    addAnnotation
      (operatingSystemTypeTypeEClass, 
       source, 
       new String[] 
       {
       "name", "OperatingSystemType_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getOperatingSystemTypeType_OperatingSystemName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystemName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getOperatingSystemTypeType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });		
    addAnnotation
      (getOperatingSystemTypeType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });		
    addAnnotation
      (processorArchitectureEnumerationEEnum, 
       source, 
       new String[] 
       {
       "name", "ProcessorArchitectureEnumeration"
       });		
    addAnnotation
      (processorArchitectureEnumerationObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "ProcessorArchitectureEnumeration:Object",
       "baseType", "ProcessorArchitectureEnumeration"
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
    addAnnotation
      (resourcesTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Resources_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getResourcesType_CandidateHosts(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CandidateHosts",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_FileSystem(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileSystem",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_ExclusiveExecution(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ExclusiveExecution",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_OperatingSystem(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OperatingSystem",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_CPUArchitecture(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUArchitecture",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualCPUSpeed(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualCPUSpeed",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualCPUTime(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualCPUTime",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualCPUCount(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualCPUCount",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualNetworkBandwidth(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualNetworkBandwidth",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualPhysicalMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualPhysicalMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualVirtualMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualVirtualMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_IndividualDiskSpace(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "IndividualDiskSpace",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_TotalCPUTime(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalCPUTime",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_TotalCPUCount(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalCPUCount",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_TotalPhysicalMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalPhysicalMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_TotalVirtualMemory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalVirtualMemory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_TotalDiskSpace(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalDiskSpace",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_TotalResourceCount(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "TotalResourceCount",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getResourcesType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":18",
       "processing", "lax"
       });		
    addAnnotation
      (getResourcesType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":19",
       "processing", "lax"
       });		
    addAnnotation
      (sourceTargetTypeEClass, 
       source, 
       new String[] 
       {
       "name", "SourceTarget_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getSourceTargetType_URI(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "URI",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getSourceTargetType_Any(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });		
    addAnnotation
      (getSourceTargetType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });
  }

} //JsdlPackageImpl
