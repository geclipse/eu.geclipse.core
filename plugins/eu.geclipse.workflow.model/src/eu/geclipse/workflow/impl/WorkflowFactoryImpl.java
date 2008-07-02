/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>IWorkflowFactory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WorkflowFactoryImpl extends EFactoryImpl implements WorkflowFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return 
   * @generated
   */
  public static WorkflowFactory init()
  {
    try
    {
      WorkflowFactory theWorkflowFactory = (WorkflowFactory)EPackage.Registry.INSTANCE.getEFactory("http:///eu/geclipse/workflow.ecore"); //$NON-NLS-1$ 
      if (theWorkflowFactory != null)
      {
        return theWorkflowFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new WorkflowFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WorkflowFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case WorkflowPackage.ILINK: return createILink();
      case WorkflowPackage.IINPUT_PORT: return createIInputPort();
      case WorkflowPackage.IOUTPUT_PORT: return createIOutputPort();
      case WorkflowPackage.IWORKFLOW: return createIWorkflow();
      case WorkflowPackage.IWORKFLOW_JOB: return createIWorkflowJob();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ILink createILink()
  {
    ILinkImpl iLink = new ILinkImpl();
    return iLink;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IInputPort createIInputPort()
  {
    IInputPortImpl iInputPort = new IInputPortImpl();
    return iInputPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IOutputPort createIOutputPort()
  {
    IOutputPortImpl iOutputPort = new IOutputPortImpl();
    return iOutputPort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWorkflow createIWorkflow()
  {
    IWorkflowImpl iWorkflow = new IWorkflowImpl();
    return iWorkflow;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWorkflowJob createIWorkflowJob()
  {
    IWorkflowJobImpl iWorkflowJob = new IWorkflowJobImpl();
    return iWorkflowJob;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WorkflowPackage getWorkflowPackage()
  {
    return (WorkflowPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static WorkflowPackage getPackage()
  {
    return WorkflowPackage.eINSTANCE;
  }


} //WorkflowFactoryImpl
