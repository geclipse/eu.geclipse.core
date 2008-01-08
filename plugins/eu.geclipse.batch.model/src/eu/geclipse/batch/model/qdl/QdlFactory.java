/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.batch.model.qdl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.batch.model.qdl.QdlPackage
 * @generated
 */
public interface QdlFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  QdlFactory eINSTANCE = eu.geclipse.batch.model.qdl.impl.QdlFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Allowed Virtual Organizations Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Allowed Virtual Organizations Type</em>'.
   * @generated
   */
  AllowedVirtualOrganizationsType createAllowedVirtualOrganizationsType();

  /**
   * Returns a new object of class '<em>Boundary Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Boundary Type</em>'.
   * @generated
   */
  BoundaryType createBoundaryType();

  /**
   * Returns a new object of class '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Document Root</em>'.
   * @generated
   */
  DocumentRoot createDocumentRoot();

  /**
   * Returns a new object of class '<em>Exact Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Exact Type</em>'.
   * @generated
   */
  ExactType createExactType();

  /**
   * Returns a new object of class '<em>Queue Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Queue Type</em>'.
   * @generated
   */
  QueueType createQueueType();

  /**
   * Returns a new object of class '<em>Range Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Range Type</em>'.
   * @generated
   */
  RangeType createRangeType();

  /**
   * Returns a new object of class '<em>Range Value Type</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Range Value Type</em>'.
   * @generated
   */
  RangeValueType createRangeValueType();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  QdlPackage getQdlPackage();

} //QdlFactory
