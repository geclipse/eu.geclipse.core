/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.util;

import eu.geclipse.jsdl.model.base.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.base.JsdlPackage
 * @generated
 */
public class JsdlSwitch
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static JsdlPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsdlSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = JsdlPackage.eINSTANCE;
    }
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  public Object doSwitch(EObject theEObject)
  {
    return doSwitch(theEObject.eClass(), theEObject);
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  protected Object doSwitch(EClass theEClass, EObject theEObject)
  {
    if (theEClass.eContainer() == modelPackage)
    {
      return doSwitch(theEClass.getClassifierID(), theEObject);
    }
    else
    {
      List eSuperTypes = theEClass.getESuperTypes();
      return
        eSuperTypes.isEmpty() ?
          defaultCase(theEObject) :
          doSwitch((EClass)eSuperTypes.get(0), theEObject);
    }
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  protected Object doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case JsdlPackage.APPLICATION_TYPE:
      {
        ApplicationType applicationType = (ApplicationType)theEObject;
        Object result = caseApplicationType(applicationType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.BOUNDARY_TYPE:
      {
        BoundaryType boundaryType = (BoundaryType)theEObject;
        Object result = caseBoundaryType(boundaryType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.CANDIDATE_HOSTS_TYPE:
      {
        CandidateHostsType candidateHostsType = (CandidateHostsType)theEObject;
        Object result = caseCandidateHostsType(candidateHostsType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.CPU_ARCHITECTURE_TYPE:
      {
        CPUArchitectureType cpuArchitectureType = (CPUArchitectureType)theEObject;
        Object result = caseCPUArchitectureType(cpuArchitectureType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.DATA_STAGING_TYPE:
      {
        DataStagingType dataStagingType = (DataStagingType)theEObject;
        Object result = caseDataStagingType(dataStagingType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.DOCUMENT_ROOT:
      {
        DocumentRoot documentRoot = (DocumentRoot)theEObject;
        Object result = caseDocumentRoot(documentRoot);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.EXACT_TYPE:
      {
        ExactType exactType = (ExactType)theEObject;
        Object result = caseExactType(exactType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.FILE_SYSTEM_TYPE:
      {
        FileSystemType fileSystemType = (FileSystemType)theEObject;
        Object result = caseFileSystemType(fileSystemType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.JOB_DEFINITION_TYPE:
      {
        JobDefinitionType jobDefinitionType = (JobDefinitionType)theEObject;
        Object result = caseJobDefinitionType(jobDefinitionType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.JOB_DESCRIPTION_TYPE:
      {
        JobDescriptionType jobDescriptionType = (JobDescriptionType)theEObject;
        Object result = caseJobDescriptionType(jobDescriptionType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.JOB_IDENTIFICATION_TYPE:
      {
        JobIdentificationType jobIdentificationType = (JobIdentificationType)theEObject;
        Object result = caseJobIdentificationType(jobIdentificationType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.OPERATING_SYSTEM_TYPE:
      {
        OperatingSystemType operatingSystemType = (OperatingSystemType)theEObject;
        Object result = caseOperatingSystemType(operatingSystemType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE:
      {
        OperatingSystemTypeType operatingSystemTypeType = (OperatingSystemTypeType)theEObject;
        Object result = caseOperatingSystemTypeType(operatingSystemTypeType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.RANGE_TYPE:
      {
        RangeType rangeType = (RangeType)theEObject;
        Object result = caseRangeType(rangeType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.RANGE_VALUE_TYPE:
      {
        RangeValueType rangeValueType = (RangeValueType)theEObject;
        Object result = caseRangeValueType(rangeValueType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.RESOURCES_TYPE:
      {
        ResourcesType resourcesType = (ResourcesType)theEObject;
        Object result = caseResourcesType(resourcesType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case JsdlPackage.SOURCE_TARGET_TYPE:
      {
        SourceTargetType sourceTargetType = (SourceTargetType)theEObject;
        Object result = caseSourceTargetType(sourceTargetType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Application Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Application Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseApplicationType(ApplicationType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Boundary Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Boundary Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseBoundaryType(BoundaryType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Candidate Hosts Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Candidate Hosts Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseCandidateHostsType(CandidateHostsType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>CPU Architecture Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>CPU Architecture Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseCPUArchitectureType(CPUArchitectureType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Data Staging Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Data Staging Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseDataStagingType(DataStagingType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseDocumentRoot(DocumentRoot object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Exact Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Exact Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseExactType(ExactType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>File System Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>File System Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseFileSystemType(FileSystemType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Job Definition Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Job Definition Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseJobDefinitionType(JobDefinitionType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Job Description Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Job Description Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseJobDescriptionType(JobDescriptionType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Job Identification Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Job Identification Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseJobIdentificationType(JobIdentificationType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Operating System Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Operating System Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseOperatingSystemType(OperatingSystemType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Operating System Type Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Operating System Type Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseOperatingSystemTypeType(OperatingSystemTypeType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Range Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Range Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseRangeType(RangeType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Range Value Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Range Value Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseRangeValueType(RangeValueType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Resources Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Resources Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseResourcesType(ResourcesType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Source Target Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Source Target Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseSourceTargetType(SourceTargetType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  public Object defaultCase(EObject object)
  {
    return null;
  }

} //JsdlSwitch
