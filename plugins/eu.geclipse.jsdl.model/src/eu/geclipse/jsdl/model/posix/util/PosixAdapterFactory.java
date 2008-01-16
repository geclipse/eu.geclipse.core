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

package eu.geclipse.jsdl.model.posix.util;

import eu.geclipse.jsdl.model.posix.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.posix.PosixPackage
 * @generated
 */
public class PosixAdapterFactory extends AdapterFactoryImpl 
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected static PosixPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public PosixAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = PosixPackage.eINSTANCE;
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
	protected PosixSwitch modelSwitch =
    new PosixSwitch()
    {
      public Object caseArgumentType(ArgumentType object)
      {
        return createArgumentTypeAdapter();
      }
      public Object caseDirectoryNameType(DirectoryNameType object)
      {
        return createDirectoryNameTypeAdapter();
      }
      public Object caseDocumentRoot(DocumentRoot object)
      {
        return createDocumentRootAdapter();
      }
      public Object caseEnvironmentType(EnvironmentType object)
      {
        return createEnvironmentTypeAdapter();
      }
      public Object caseFileNameType(FileNameType object)
      {
        return createFileNameTypeAdapter();
      }
      public Object caseGroupNameType(GroupNameType object)
      {
        return createGroupNameTypeAdapter();
      }
      public Object caseLimitsType(LimitsType object)
      {
        return createLimitsTypeAdapter();
      }
      public Object casePOSIXApplicationType(POSIXApplicationType object)
      {
        return createPOSIXApplicationTypeAdapter();
      }
      public Object caseUserNameType(UserNameType object)
      {
        return createUserNameTypeAdapter();
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
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.ArgumentType <em>Argument Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.ArgumentType
   * @generated
   */
	public Adapter createArgumentTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.DirectoryNameType <em>Directory Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.DirectoryNameType
   * @generated
   */
	public Adapter createDirectoryNameTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.DocumentRoot <em>Document Root</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.DocumentRoot
   * @generated
   */
	public Adapter createDocumentRootAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.EnvironmentType <em>Environment Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.EnvironmentType
   * @generated
   */
	public Adapter createEnvironmentTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.FileNameType <em>File Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.FileNameType
   * @generated
   */
	public Adapter createFileNameTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.GroupNameType <em>Group Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.GroupNameType
   * @generated
   */
	public Adapter createGroupNameTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.LimitsType <em>Limits Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.LimitsType
   * @generated
   */
	public Adapter createLimitsTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType <em>POSIX Application Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.POSIXApplicationType
   * @generated
   */
	public Adapter createPOSIXApplicationTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.jsdl.model.posix.UserNameType <em>User Name Type</em>}'.
   * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.jsdl.model.posix.UserNameType
   * @generated
   */
	public Adapter createUserNameTypeAdapter()
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

} //PosixAdapterFactory
