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
package eu.geclipse.workflow.util;

import eu.geclipse.workflow.*;

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
 * @see eu.geclipse.workflow.IWorkflowPackage
 * @generated
 */
public class WorkflowSwitch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static IWorkflowPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WorkflowSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = IWorkflowPackage.eINSTANCE;
    }
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  public T doSwitch(EObject theEObject)
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
  protected T doSwitch(EClass theEClass, EObject theEObject)
  {
    if (theEClass.eContainer() == modelPackage)
    {
      return doSwitch(theEClass.getClassifierID(), theEObject);
    }
    else
    {
      List<EClass> eSuperTypes = theEClass.getESuperTypes();
      return
        eSuperTypes.isEmpty() ?
          defaultCase(theEObject) :
          doSwitch(eSuperTypes.get(0), theEObject);
    }
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case IWorkflowPackage.IPORT:
      {
        IPort iPort = (IPort)theEObject;
        T result = caseIPort(iPort);
        if (result == null) result = caseIWorkflowElement(iPort);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.ILINK:
      {
        ILink iLink = (ILink)theEObject;
        T result = caseILink(iLink);
        if (result == null) result = caseIWorkflowElement(iLink);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.IINPUT_PORT:
      {
        IInputPort iInputPort = (IInputPort)theEObject;
        T result = caseIInputPort(iInputPort);
        if (result == null) result = caseIPort(iInputPort);
        if (result == null) result = caseIWorkflowElement(iInputPort);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.IOUTPUT_PORT:
      {
        IOutputPort iOutputPort = (IOutputPort)theEObject;
        T result = caseIOutputPort(iOutputPort);
        if (result == null) result = caseIPort(iOutputPort);
        if (result == null) result = caseIWorkflowElement(iOutputPort);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.IWORKFLOW:
      {
        IWorkflow iWorkflow = (IWorkflow)theEObject;
        T result = caseIWorkflow(iWorkflow);
        if (result == null) result = caseIWorkflowElement(iWorkflow);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.IWORKFLOW_JOB:
      {
        IWorkflowJob iWorkflowJob = (IWorkflowJob)theEObject;
        T result = caseIWorkflowJob(iWorkflowJob);
        if (result == null) result = caseIWorkflowNode(iWorkflowJob);
        if (result == null) result = caseIWorkflowElement(iWorkflowJob);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.IWORKFLOW_ELEMENT:
      {
        IWorkflowElement iWorkflowElement = (IWorkflowElement)theEObject;
        T result = caseIWorkflowElement(iWorkflowElement);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case IWorkflowPackage.IWORKFLOW_NODE:
      {
        IWorkflowNode iWorkflowNode = (IWorkflowNode)theEObject;
        T result = caseIWorkflowNode(iWorkflowNode);
        if (result == null) result = caseIWorkflowElement(iWorkflowNode);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IPort</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IPort</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIPort(IPort object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>ILink</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>ILink</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseILink(ILink object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IInput Port</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IInput Port</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIInputPort(IInputPort object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IOutput Port</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IOutput Port</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIOutputPort(IOutputPort object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IWorkflow</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IWorkflow</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIWorkflow(IWorkflow object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IWorkflow Job</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IWorkflow Job</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIWorkflowJob(IWorkflowJob object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IWorkflow Element</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IWorkflow Element</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIWorkflowElement(IWorkflowElement object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>IWorkflow Node</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>IWorkflow Node</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseIWorkflowNode(IWorkflowNode object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  public T defaultCase(EObject object)
  {
    return null;
  }

} //WorkflowSwitch
