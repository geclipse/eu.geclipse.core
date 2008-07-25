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

package eu.geclipse.jsdl.model.impl;

import eu.geclipse.jsdl.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 * @deprecated This class is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base.impl
 */
public class JsdlFactoryImpl extends EFactoryImpl implements JsdlFactory 
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static JsdlFactory init()
  {
    try
    {
      JsdlFactory theJsdlFactory = (JsdlFactory)EPackage.Registry.INSTANCE.getEFactory("http://schemas.ggf.org/jsdl/2005/11/jsdl"); 
      if (theJsdlFactory != null)
      {
        return theJsdlFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new JsdlFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JsdlFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case JsdlPackage.APPLICATION_TYPE: return createApplicationType();
      case JsdlPackage.BOUNDARY_TYPE: return createBoundaryType();
      case JsdlPackage.CANDIDATE_HOSTS_TYPE: return createCandidateHostsType();
      case JsdlPackage.CPU_ARCHITECTURE_TYPE: return createCPUArchitectureType();
      case JsdlPackage.DATA_STAGING_TYPE: return createDataStagingType();
      case JsdlPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case JsdlPackage.EXACT_TYPE: return createExactType();
      case JsdlPackage.FILE_SYSTEM_TYPE: return createFileSystemType();
      case JsdlPackage.JOB_DEFINITION_TYPE: return createJobDefinitionType();
      case JsdlPackage.JOB_DESCRIPTION_TYPE: return createJobDescriptionType();
      case JsdlPackage.JOB_IDENTIFICATION_TYPE: return createJobIdentificationType();
      case JsdlPackage.OPERATING_SYSTEM_TYPE: return createOperatingSystemType();
      case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE: return createOperatingSystemTypeType();
      case JsdlPackage.RANGE_TYPE: return createRangeType();
      case JsdlPackage.RANGE_VALUE_TYPE: return createRangeValueType();
      case JsdlPackage.RESOURCES_TYPE: return createResourcesType();
      case JsdlPackage.SOURCE_TARGET_TYPE: return createSourceTargetType();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case JsdlPackage.CREATION_FLAG_ENUMERATION:
        return createCreationFlagEnumerationFromString(eDataType, initialValue);
      case JsdlPackage.FILE_SYSTEM_TYPE_ENUMERATION:
        return createFileSystemTypeEnumerationFromString(eDataType, initialValue);
      case JsdlPackage.OPERATING_SYSTEM_TYPE_ENUMERATION:
        return createOperatingSystemTypeEnumerationFromString(eDataType, initialValue);
      case JsdlPackage.PROCESSOR_ARCHITECTURE_ENUMERATION:
        return createProcessorArchitectureEnumerationFromString(eDataType, initialValue);
      case JsdlPackage.CREATION_FLAG_ENUMERATION_OBJECT:
        return createCreationFlagEnumerationObjectFromString(eDataType, initialValue);
      case JsdlPackage.DESCRIPTION_TYPE:
        return createDescriptionTypeFromString(eDataType, initialValue);
      case JsdlPackage.FILE_SYSTEM_TYPE_ENUMERATION_OBJECT:
        return createFileSystemTypeEnumerationObjectFromString(eDataType, initialValue);
      case JsdlPackage.OPERATING_SYSTEM_TYPE_ENUMERATION_OBJECT:
        return createOperatingSystemTypeEnumerationObjectFromString(eDataType, initialValue);
      case JsdlPackage.PROCESSOR_ARCHITECTURE_ENUMERATION_OBJECT:
        return createProcessorArchitectureEnumerationObjectFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case JsdlPackage.CREATION_FLAG_ENUMERATION:
        return convertCreationFlagEnumerationToString(eDataType, instanceValue);
      case JsdlPackage.FILE_SYSTEM_TYPE_ENUMERATION:
        return convertFileSystemTypeEnumerationToString(eDataType, instanceValue);
      case JsdlPackage.OPERATING_SYSTEM_TYPE_ENUMERATION:
        return convertOperatingSystemTypeEnumerationToString(eDataType, instanceValue);
      case JsdlPackage.PROCESSOR_ARCHITECTURE_ENUMERATION:
        return convertProcessorArchitectureEnumerationToString(eDataType, instanceValue);
      case JsdlPackage.CREATION_FLAG_ENUMERATION_OBJECT:
        return convertCreationFlagEnumerationObjectToString(eDataType, instanceValue);
      case JsdlPackage.DESCRIPTION_TYPE:
        return convertDescriptionTypeToString(eDataType, instanceValue);
      case JsdlPackage.FILE_SYSTEM_TYPE_ENUMERATION_OBJECT:
        return convertFileSystemTypeEnumerationObjectToString(eDataType, instanceValue);
      case JsdlPackage.OPERATING_SYSTEM_TYPE_ENUMERATION_OBJECT:
        return convertOperatingSystemTypeEnumerationObjectToString(eDataType, instanceValue);
      case JsdlPackage.PROCESSOR_ARCHITECTURE_ENUMERATION_OBJECT:
        return convertProcessorArchitectureEnumerationObjectToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ApplicationType createApplicationType()
  {
    ApplicationTypeImpl applicationType = new ApplicationTypeImpl();
    return applicationType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public BoundaryType createBoundaryType()
  {
    BoundaryTypeImpl boundaryType = new BoundaryTypeImpl();
    return boundaryType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CandidateHostsType createCandidateHostsType()
  {
    CandidateHostsTypeImpl candidateHostsType = new CandidateHostsTypeImpl();
    return candidateHostsType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CPUArchitectureType createCPUArchitectureType()
  {
    CPUArchitectureTypeImpl cpuArchitectureType = new CPUArchitectureTypeImpl();
    return cpuArchitectureType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DataStagingType createDataStagingType()
  {
    DataStagingTypeImpl dataStagingType = new DataStagingTypeImpl();
    return dataStagingType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DocumentRoot createDocumentRoot()
  {
    DocumentRootImpl documentRoot = new DocumentRootImpl();
    return documentRoot;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ExactType createExactType()
  {
    ExactTypeImpl exactType = new ExactTypeImpl();
    return exactType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileSystemType createFileSystemType()
  {
    FileSystemTypeImpl fileSystemType = new FileSystemTypeImpl();
    return fileSystemType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JobDefinitionType createJobDefinitionType()
  {
    JobDefinitionTypeImpl jobDefinitionType = new JobDefinitionTypeImpl();
    return jobDefinitionType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JobDescriptionType createJobDescriptionType()
  {
    JobDescriptionTypeImpl jobDescriptionType = new JobDescriptionTypeImpl();
    return jobDescriptionType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JobIdentificationType createJobIdentificationType()
  {
    JobIdentificationTypeImpl jobIdentificationType = new JobIdentificationTypeImpl();
    return jobIdentificationType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemType createOperatingSystemType()
  {
    OperatingSystemTypeImpl operatingSystemType = new OperatingSystemTypeImpl();
    return operatingSystemType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemTypeType createOperatingSystemTypeType()
  {
    OperatingSystemTypeTypeImpl operatingSystemTypeType = new OperatingSystemTypeTypeImpl();
    return operatingSystemTypeType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeType createRangeType()
  {
    RangeTypeImpl rangeType = new RangeTypeImpl();
    return rangeType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType createRangeValueType()
  {
    RangeValueTypeImpl rangeValueType = new RangeValueTypeImpl();
    return rangeValueType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ResourcesType createResourcesType()
  {
    ResourcesTypeImpl resourcesType = new ResourcesTypeImpl();
    return resourcesType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public SourceTargetType createSourceTargetType()
  {
    SourceTargetTypeImpl sourceTargetType = new SourceTargetTypeImpl();
    return sourceTargetType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CreationFlagEnumeration createCreationFlagEnumerationFromString(EDataType eDataType, String initialValue)
  {
    CreationFlagEnumeration result = CreationFlagEnumeration.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertCreationFlagEnumerationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileSystemTypeEnumeration createFileSystemTypeEnumerationFromString(EDataType eDataType, String initialValue)
  {
    FileSystemTypeEnumeration result = FileSystemTypeEnumeration.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertFileSystemTypeEnumerationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemTypeEnumeration createOperatingSystemTypeEnumerationFromString(EDataType eDataType, String initialValue)
  {
    OperatingSystemTypeEnumeration result = OperatingSystemTypeEnumeration.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertOperatingSystemTypeEnumerationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ProcessorArchitectureEnumeration createProcessorArchitectureEnumerationFromString(EDataType eDataType, String initialValue)
  {
    ProcessorArchitectureEnumeration result = ProcessorArchitectureEnumeration.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertProcessorArchitectureEnumerationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CreationFlagEnumeration createCreationFlagEnumerationObjectFromString(EDataType eDataType, String initialValue)
  {
    return createCreationFlagEnumerationFromString(JsdlPackage.Literals.CREATION_FLAG_ENUMERATION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertCreationFlagEnumerationObjectToString(EDataType eDataType, Object instanceValue)
  {
    return convertCreationFlagEnumerationToString(JsdlPackage.Literals.CREATION_FLAG_ENUMERATION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String createDescriptionTypeFromString(EDataType eDataType, String initialValue)
  {
    return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertDescriptionTypeToString(EDataType eDataType, Object instanceValue)
  {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileSystemTypeEnumeration createFileSystemTypeEnumerationObjectFromString(EDataType eDataType, String initialValue)
  {
    return createFileSystemTypeEnumerationFromString(JsdlPackage.Literals.FILE_SYSTEM_TYPE_ENUMERATION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertFileSystemTypeEnumerationObjectToString(EDataType eDataType, Object instanceValue)
  {
    return convertFileSystemTypeEnumerationToString(JsdlPackage.Literals.FILE_SYSTEM_TYPE_ENUMERATION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemTypeEnumeration createOperatingSystemTypeEnumerationObjectFromString(EDataType eDataType, String initialValue)
  {
    return createOperatingSystemTypeEnumerationFromString(JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_ENUMERATION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertOperatingSystemTypeEnumerationObjectToString(EDataType eDataType, Object instanceValue)
  {
    return convertOperatingSystemTypeEnumerationToString(JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_ENUMERATION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ProcessorArchitectureEnumeration createProcessorArchitectureEnumerationObjectFromString(EDataType eDataType, String initialValue)
  {
    return createProcessorArchitectureEnumerationFromString(JsdlPackage.Literals.PROCESSOR_ARCHITECTURE_ENUMERATION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String convertProcessorArchitectureEnumerationObjectToString(EDataType eDataType, Object instanceValue)
  {
    return convertProcessorArchitectureEnumerationToString(JsdlPackage.Literals.PROCESSOR_ARCHITECTURE_ENUMERATION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JsdlPackage getJsdlPackage()
  {
    return (JsdlPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
	public static JsdlPackage getPackage()
  {
    return JsdlPackage.eINSTANCE;
  }

} //JsdlFactoryImpl
