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

package eu.geclipse.jsdl.model.posix;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.posix.PosixPackage
 * @generated
 */
public interface PosixFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	PosixFactory eINSTANCE = eu.geclipse.jsdl.model.posix.impl.PosixFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Argument Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Argument Type</em>'.
   * @generated
   */
	ArgumentType createArgumentType();

  /**
   * Returns a new object of class '<em>Directory Name Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Directory Name Type</em>'.
   * @generated
   */
	DirectoryNameType createDirectoryNameType();

  /**
   * Returns a new object of class '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Document Root</em>'.
   * @generated
   */
	DocumentRoot createDocumentRoot();

  /**
   * Returns a new object of class '<em>Environment Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Environment Type</em>'.
   * @generated
   */
	EnvironmentType createEnvironmentType();

  /**
   * Returns a new object of class '<em>File Name Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>File Name Type</em>'.
   * @generated
   */
	FileNameType createFileNameType();

  /**
   * Returns a new object of class '<em>Group Name Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Group Name Type</em>'.
   * @generated
   */
	GroupNameType createGroupNameType();

  /**
   * Returns a new object of class '<em>Limits Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Limits Type</em>'.
   * @generated
   */
	LimitsType createLimitsType();

  /**
   * Returns a new object of class '<em>POSIX Application Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>POSIX Application Type</em>'.
   * @generated
   */
	POSIXApplicationType createPOSIXApplicationType();

  /**
   * Returns a new object of class '<em>User Name Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>User Name Type</em>'.
   * @generated
   */
	UserNameType createUserNameType();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
	PosixPackage getPosixPackage();

} //PosixFactory
