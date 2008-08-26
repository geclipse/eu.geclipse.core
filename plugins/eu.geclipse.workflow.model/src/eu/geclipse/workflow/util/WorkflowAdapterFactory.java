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

import eu.geclipse.workflow.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * @see eu.geclipse.workflow.model.IWorkflowPackage
 * @generated
 */
public class WorkflowAdapterFactory extends AdapterFactoryImpl {

  /**
   * The cached model package.
   * @generated
   */
  protected static IWorkflowPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * @generated
   */
  public WorkflowAdapterFactory() {
    if( modelPackage == null ) {
      modelPackage = IWorkflowPackage.eINSTANCE;
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
  @Override
  public boolean isFactoryForType( Object object ) {
    if( object == modelPackage ) {
      return true;
    }
    if( object instanceof EObject ) {
      return ( ( EObject )object ).eClass().getEPackage() == modelPackage;
    }
    return false;
  }
  /**
   * The switch the delegates to the <code>createXXX</code> methods.
   * @generated
   */
  protected WorkflowSwitch<Adapter> modelSwitch = new WorkflowSwitch<Adapter>()
  {

    @Override
    public Adapter caseIPort( IPort object ) {
      return createIPortAdapter();
    }

    @Override
    public Adapter caseILink( ILink object ) {
      return createILinkAdapter();
    }

    @Override
    public Adapter caseIInputPort( IInputPort object ) {
      return createIInputPortAdapter();
    }

    @Override
    public Adapter caseIOutputPort( IOutputPort object ) {
      return createIOutputPortAdapter();
    }

    @Override
    public Adapter caseIWorkflow( IWorkflow object ) {
      return createIWorkflowAdapter();
    }

    @Override
    public Adapter caseIWorkflowJob( IWorkflowJob object ) {
      return createIWorkflowJobAdapter();
    }

    @Override
    public Adapter caseIWorkflowElement( IWorkflowElement object ) {
      return createIWorkflowElementAdapter();
    }

    @Override
    public Adapter caseIWorkflowNode( IWorkflowNode object ) {
      return createIWorkflowNodeAdapter();
    }

    @Override
    public Adapter defaultCase( EObject object ) {
      return createEObjectAdapter();
    }
  };

  /**
   * Creates an adapter for the <code>target</code>.
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter( Notifier target ) {
    return this.modelSwitch.doSwitch( ( EObject )target );
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IPort <em>IPort</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IPort
   * @generated
   */
  public Adapter createIPortAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.ILink <em>ILink</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.ILink
   * @generated
   */
  public Adapter createILinkAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IInputPort <em>IInput Port</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IInputPort
   * @generated
   */
  public Adapter createIInputPortAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IOutputPort <em>IOutput Port</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IOutputPort
   * @generated
   */
  public Adapter createIOutputPortAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IWorkflow <em>IWorkflow</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IWorkflow
   * @generated
   */
  public Adapter createIWorkflowAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IWorkflowJob <em>IWorkflow Job</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IWorkflowJob
   * @generated
   */
  public Adapter createIWorkflowJobAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IWorkflowElement <em>IWorkflow Element</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IWorkflowElement
   * @generated
   */
  public Adapter createIWorkflowElementAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link eu.geclipse.workflow.model.IWorkflowNode <em>IWorkflow Node</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see eu.geclipse.workflow.model.IWorkflowNode
   * @generated
   */
  public Adapter createIWorkflowNodeAdapter() {
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
  public Adapter createEObjectAdapter() {
    return null;
  }
} //WorkflowAdapterFactory
