/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.util;

import eu.geclipse.jsdl.model.base.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.base.JsdlPackage
 * @generated
 */
public class JsdlAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static JsdlPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsdlAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = JsdlPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch the delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected JsdlSwitch modelSwitch =
    new JsdlSwitch()
    {
      public Object caseApplicationType(ApplicationType object)
      {
        return createApplicationTypeAdapter();
      }
      public Object caseBoundaryType(BoundaryType object)
      {
        return createBoundaryTypeAdapter();
      }
      public Object caseCandidateHostsType(CandidateHostsType object)
      {
        return createCandidateHostsTypeAdapter();
      }
      public Object caseCPUArchitectureType(CPUArchitectureType object)
      {
        return createCPUArchitectureTypeAdapter();
      }
      public Object caseDataStagingType(DataStagingType object)
      {
        return createDataStagingTypeAdapter();
      }
      public Object caseDocumentRoot(DocumentRoot object)
      {
        return createDocumentRootAdapter();
      }
      public Object caseExactType(ExactType object)
      {
        return createExactTypeAdapter();
      }
      public Object caseFileSystemType(FileSystemType object)
      {
        return createFileSystemTypeAdapter();
      }
      public Object caseJobDefinitionType(JobDefinitionType object)
      {
        return createJobDefinitionTypeAdapter();
      }
      public Object caseJobDescriptionType(JobDescriptionType object)
      {
        return createJobDescriptionTypeAdapter();
      }
      public Object caseJobIdentificationType(JobIdentificationType object)
      {
        return createJobIdentificationTypeAdapter();
      }
      public Object caseOperatingSystemType(OperatingSystemType object)
      {
        return createOperatingSystemTypeAdapter();
      }
      public Object caseOperatingSystemTypeType(OperatingSystemTypeType object)
      {
        return createOperatingSystemTypeTypeAdapter();
      }
      public Object caseRangeType(RangeType object)
      {
        return createRangeTypeAdapter();
      }
      public Object caseRangeValueType(RangeValueType object)
      {
        return createRangeValueTypeAdapter();
      }
      public Object caseResourcesType(ResourcesType object)
      {
        return createResourcesTypeAdapter();
      }
      public Object caseSourceTargetType(SourceTargetType object)
      {
        return createSourceTargetTypeAdapter();
      }
      public Object defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  public Adapter createAdapter(Notifier target)
  {
    return (Adapter)modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.ApplicationType <em>Application Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.ApplicationType
   * @generated
   */
  public Adapter createApplicationTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.BoundaryType <em>Boundary Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.BoundaryType
   * @generated
   */
  public Adapter createBoundaryTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.CandidateHostsType <em>Candidate Hosts Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.CandidateHostsType
   * @generated
   */
  public Adapter createCandidateHostsTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.CPUArchitectureType <em>CPU Architecture Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.CPUArchitectureType
   * @generated
   */
  public Adapter createCPUArchitectureTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.DataStagingType <em>Data Staging Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.DataStagingType
   * @generated
   */
  public Adapter createDataStagingTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.DocumentRoot
   * @generated
   */
  public Adapter createDocumentRootAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.ExactType <em>Exact Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.ExactType
   * @generated
   */
  public Adapter createExactTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.FileSystemType <em>File System Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.FileSystemType
   * @generated
   */
  public Adapter createFileSystemTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.JobDefinitionType <em>Job Definition Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.JobDefinitionType
   * @generated
   */
  public Adapter createJobDefinitionTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.JobDescriptionType <em>Job Description Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.JobDescriptionType
   * @generated
   */
  public Adapter createJobDescriptionTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.JobIdentificationType <em>Job Identification Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.JobIdentificationType
   * @generated
   */
  public Adapter createJobIdentificationTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.OperatingSystemType <em>Operating System Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.OperatingSystemType
   * @generated
   */
  public Adapter createOperatingSystemTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.OperatingSystemTypeType <em>Operating System Type Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.OperatingSystemTypeType
   * @generated
   */
  public Adapter createOperatingSystemTypeTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.RangeType <em>Range Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.RangeType
   * @generated
   */
  public Adapter createRangeTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.RangeValueType <em>Range Value Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.RangeValueType
   * @generated
   */
  public Adapter createRangeValueTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.ResourcesType <em>Resources Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.ResourcesType
   * @generated
   */
  public Adapter createResourcesTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.base.SourceTargetType <em>Source Target Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.base.SourceTargetType
   * @generated
   */
  public Adapter createSourceTargetTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //JsdlAdapterFactory
