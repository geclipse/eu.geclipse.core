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

package eu.geclipse.jsdl.model.posix.impl;

import eu.geclipse.jsdl.model.posix.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PosixFactoryImpl extends EFactoryImpl implements PosixFactory 
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static PosixFactory init()
  {
    try
    {
      PosixFactory thePosixFactory = (PosixFactory)EPackage.Registry.INSTANCE.getEFactory("http://schemas.ggf.org/jsdl/2005/11/jsdl-posix"); 
      if (thePosixFactory != null)
      {
        return thePosixFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new PosixFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public PosixFactoryImpl()
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
      case PosixPackage.ARGUMENT_TYPE: return createArgumentType();
      case PosixPackage.DIRECTORY_NAME_TYPE: return createDirectoryNameType();
      case PosixPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case PosixPackage.ENVIRONMENT_TYPE: return createEnvironmentType();
      case PosixPackage.FILE_NAME_TYPE: return createFileNameType();
      case PosixPackage.GROUP_NAME_TYPE: return createGroupNameType();
      case PosixPackage.LIMITS_TYPE: return createLimitsType();
      case PosixPackage.POSIX_APPLICATION_TYPE: return createPOSIXApplicationType();
      case PosixPackage.USER_NAME_TYPE: return createUserNameType();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ArgumentType createArgumentType()
  {
    ArgumentTypeImpl argumentType = new ArgumentTypeImpl();
    return argumentType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DirectoryNameType createDirectoryNameType()
  {
    DirectoryNameTypeImpl directoryNameType = new DirectoryNameTypeImpl();
    return directoryNameType;
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
	public EnvironmentType createEnvironmentType()
  {
    EnvironmentTypeImpl environmentType = new EnvironmentTypeImpl();
    return environmentType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType createFileNameType()
  {
    FileNameTypeImpl fileNameType = new FileNameTypeImpl();
    return fileNameType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public GroupNameType createGroupNameType()
  {
    GroupNameTypeImpl groupNameType = new GroupNameTypeImpl();
    return groupNameType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType createLimitsType()
  {
    LimitsTypeImpl limitsType = new LimitsTypeImpl();
    return limitsType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public POSIXApplicationType createPOSIXApplicationType()
  {
    POSIXApplicationTypeImpl posixApplicationType = new POSIXApplicationTypeImpl();
    return posixApplicationType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public UserNameType createUserNameType()
  {
    UserNameTypeImpl userNameType = new UserNameTypeImpl();
    return userNameType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public PosixPackage getPosixPackage()
  {
    return (PosixPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
	public static PosixPackage getPackage()
  {
    return PosixPackage.eINSTANCE;
  }

} //PosixFactoryImpl
