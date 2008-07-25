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

package eu.geclipse.jsdl.model.util;

import eu.geclipse.jsdl.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.JsdlPackage
 * @generated
 * @deprecated This class is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base.util
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
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.ApplicationType <em>Application Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.ApplicationType
   * @generated
   */
	public Adapter createApplicationTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.BoundaryType <em>Boundary Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.BoundaryType
   * @generated
   */
	public Adapter createBoundaryTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.CandidateHostsType <em>Candidate Hosts Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.CandidateHostsType
   * @generated
   */
	public Adapter createCandidateHostsTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.CPUArchitectureType <em>CPU Architecture Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.CPUArchitectureType
   * @generated
   */
	public Adapter createCPUArchitectureTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.DataStagingType <em>Data Staging Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.DataStagingType
   * @generated
   */
	public Adapter createDataStagingTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.DocumentRoot
   * @generated
   */
	public Adapter createDocumentRootAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.ExactType <em>Exact Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.ExactType
   * @generated
   */
	public Adapter createExactTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.FileSystemType <em>File System Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.FileSystemType
   * @generated
   */
	public Adapter createFileSystemTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.JobDefinitionType <em>Job Definition Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.JobDefinitionType
   * @generated
   */
	public Adapter createJobDefinitionTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.JobDescriptionType <em>Job Description Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.JobDescriptionType
   * @generated
   */
	public Adapter createJobDescriptionTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.JobIdentificationType <em>Job Identification Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.JobIdentificationType
   * @generated
   */
	public Adapter createJobIdentificationTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.OperatingSystemType <em>Operating System Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.OperatingSystemType
   * @generated
   */
	public Adapter createOperatingSystemTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.OperatingSystemTypeType <em>Operating System Type Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.OperatingSystemTypeType
   * @generated
   */
	public Adapter createOperatingSystemTypeTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.RangeType <em>Range Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.RangeType
   * @generated
   */
	public Adapter createRangeTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.RangeValueType <em>Range Value Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.RangeValueType
   * @generated
   */
	public Adapter createRangeValueTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.ResourcesType <em>Resources Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.ResourcesType
   * @generated
   */
	public Adapter createResourcesTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.SourceTargetType <em>Source Target Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.SourceTargetType
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
