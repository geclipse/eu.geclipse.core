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

package eu.geclipse.jsdl.model.posix.util;

import eu.geclipse.jsdl.model.posix.*;

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
 * @see eu.geclipse.jsdl.model.posix.PosixPackage
 * @generated
 */
public class PosixSwitch 
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected static PosixPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public PosixSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = PosixPackage.eINSTANCE;
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
      case PosixPackage.ARGUMENT_TYPE:
      {
        ArgumentType argumentType = (ArgumentType)theEObject;
        Object result = caseArgumentType(argumentType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.DIRECTORY_NAME_TYPE:
      {
        DirectoryNameType directoryNameType = (DirectoryNameType)theEObject;
        Object result = caseDirectoryNameType(directoryNameType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.DOCUMENT_ROOT:
      {
        DocumentRoot documentRoot = (DocumentRoot)theEObject;
        Object result = caseDocumentRoot(documentRoot);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.ENVIRONMENT_TYPE:
      {
        EnvironmentType environmentType = (EnvironmentType)theEObject;
        Object result = caseEnvironmentType(environmentType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.FILE_NAME_TYPE:
      {
        FileNameType fileNameType = (FileNameType)theEObject;
        Object result = caseFileNameType(fileNameType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.GROUP_NAME_TYPE:
      {
        GroupNameType groupNameType = (GroupNameType)theEObject;
        Object result = caseGroupNameType(groupNameType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.LIMITS_TYPE:
      {
        LimitsType limitsType = (LimitsType)theEObject;
        Object result = caseLimitsType(limitsType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.POSIX_APPLICATION_TYPE:
      {
        POSIXApplicationType posixApplicationType = (POSIXApplicationType)theEObject;
        Object result = casePOSIXApplicationType(posixApplicationType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case PosixPackage.USER_NAME_TYPE:
      {
        UserNameType userNameType = (UserNameType)theEObject;
        Object result = caseUserNameType(userNameType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Argument Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Argument Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseArgumentType(ArgumentType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Directory Name Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Directory Name Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseDirectoryNameType(DirectoryNameType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Document Root</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseDocumentRoot(DocumentRoot object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Environment Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Environment Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseEnvironmentType(EnvironmentType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>File Name Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>File Name Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseFileNameType(FileNameType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Group Name Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Group Name Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseGroupNameType(GroupNameType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Limits Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Limits Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseLimitsType(LimitsType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>POSIX Application Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>POSIX Application Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object casePOSIXApplicationType(POSIXApplicationType object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>User Name Type</em>'.
   * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>User Name Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
	public Object caseUserNameType(UserNameType object)
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
	public Object defaultCase(EObject object)
  {
    return null;
  }

} //PosixSwitch
