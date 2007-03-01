/**
 * <copyright>
 * </copyright>
 *
 * $Id: PosixFactory.java,v 1.1 2007/01/25 15:26:28 emstamou Exp $
 */
package eu.geclipse.jsdl.posix;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.posix.PosixPackage
 * @generated
 */
public interface PosixFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PosixFactory eINSTANCE = eu.geclipse.jsdl.posix.impl.PosixFactoryImpl.init();

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
