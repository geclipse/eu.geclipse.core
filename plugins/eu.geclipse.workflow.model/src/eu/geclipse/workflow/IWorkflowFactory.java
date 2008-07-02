/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.workflow.IWorkflowPackage
 * @generated
 */
public interface IWorkflowFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  IWorkflowFactory eINSTANCE = eu.geclipse.workflow.impl.WorkflowFactoryImpl.init();

  /**
   * Returns a new object of class '<em>ILink</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>ILink</em>'.
   * @generated
   */
  ILink createILink();

  /**
   * Returns a new object of class '<em>IInput Port</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>IInput Port</em>'.
   * @generated
   */
  IInputPort createIInputPort();

  /**
   * Returns a new object of class '<em>IOutput Port</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>IOutput Port</em>'.
   * @generated
   */
  IOutputPort createIOutputPort();

  /**
   * Returns a new object of class '<em>IWorkflow</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>IWorkflow</em>'.
   * @generated
   */
  IWorkflow createIWorkflow();

  /**
   * Returns a new object of class '<em>IWorkflow Job</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>IWorkflow Job</em>'.
   * @generated
   */
  IWorkflowJob createIWorkflowJob();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  IWorkflowPackage getWorkflowPackage();

} //WorkflowFactory
