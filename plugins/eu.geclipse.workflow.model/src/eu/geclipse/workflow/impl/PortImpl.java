/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IPort;
import eu.geclipse.workflow.WorkflowPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IPort</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class PortImpl extends IWorkflowElementImpl implements IPort
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PortImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return WorkflowPackage.Literals.IPORT;
  }

} //IPortImpl
