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
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowNode;
import eu.geclipse.workflow.IWorkflowPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IWorkflowNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowNodeImpl#getWorkflow <em>Workflow</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowNodeImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowNodeImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowNodeImpl#isIsStart <em>Is Start</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowNodeImpl#isIsFinish <em>Is Finish</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class WorkflowNodeImpl extends WorkflowElementImpl implements IWorkflowNode
{
  /**
   * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutputs()
   * @generated
   * @ordered
   */
  protected EList<IOutputPort> outputs;

  /**
   * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputs()
   * @generated
   * @ordered
   */
  protected EList<IInputPort> inputs;

  /**
   * The default value of the '{@link #isIsStart() <em>Is Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsStart()
   * @generated
   * @ordered
   */
  protected static final boolean IS_START_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsStart() <em>Is Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsStart()
   * @generated
   * @ordered
   */
  protected boolean isStart = IS_START_EDEFAULT;

  /**
   * The default value of the '{@link #isIsFinish() <em>Is Finish</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsFinish()
   * @generated
   * @ordered
   */
  protected static final boolean IS_FINISH_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isIsFinish() <em>Is Finish</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isIsFinish()
   * @generated
   * @ordered
   */
  protected boolean isFinish = IS_FINISH_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WorkflowNodeImpl()
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
    return IWorkflowPackage.Literals.IWORKFLOW_NODE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWorkflow getWorkflow()
  {
    if (this.eContainerFeatureID != IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW) 
      return null;
    return (IWorkflow)eContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWorkflow(IWorkflow newWorkflow, NotificationChain msgs)
  {
    msgs = eBasicSetContainer( ( InternalEObject )newWorkflow,
                               IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW,
                               msgs );
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWorkflow(IWorkflow newWorkflow)
  {
    if( newWorkflow != eInternalContainer()
        || ( this.eContainerFeatureID != IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW && newWorkflow != null ) )
    {
      if (EcoreUtil.isAncestor(this, newWorkflow))
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
      NotificationChain msgs = null;
      if (eInternalContainer() != null)
        msgs = eBasicRemoveFromContainer(msgs);
      if (newWorkflow != null)
        msgs = ( ( InternalEObject )newWorkflow ).eInverseAdd( this,
                                                               IWorkflowPackage.IWORKFLOW__NODES,
                                                               IWorkflow.class,
                                                               msgs );
      msgs = basicSetWorkflow(newWorkflow, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify( new ENotificationImpl( this,
                                      Notification.SET,
                                      IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW,
                                      newWorkflow,
                                      newWorkflow ) );
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<IOutputPort> getOutputs()
  {
    if (this.outputs == null)
    {
      this.outputs = new EObjectContainmentWithInverseEList<IOutputPort>( IOutputPort.class,
                                                                          this,
                                                                          IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS,
                                                                          IWorkflowPackage.IOUTPUT_PORT__NODE );
    }
    return this.outputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<IInputPort> getInputs()
  {
    if (this.inputs == null)
    {
      this.inputs = new EObjectContainmentWithInverseEList<IInputPort>( IInputPort.class,
                                                                        this,
                                                                        IWorkflowPackage.IWORKFLOW_NODE__INPUTS,
                                                                        IWorkflowPackage.IINPUT_PORT__NODE );
    }
    return this.inputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsStart()
  {
    return this.isStart;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsStart(boolean newIsStart)
  {
    boolean oldIsStart = this.isStart;
    this.isStart = newIsStart;
    if (eNotificationRequired())
      eNotify( new ENotificationImpl( this,
                                      Notification.SET,
                                      IWorkflowPackage.IWORKFLOW_NODE__IS_START,
                                      oldIsStart,
                                      this.isStart ) );
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsFinish()
  {
    return this.isFinish;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsFinish(boolean newIsFinish)
  {
    boolean oldIsFinish = this.isFinish;
    this.isFinish = newIsFinish;
    if (eNotificationRequired())
      eNotify( new ENotificationImpl( this,
                                      Notification.SET,
                                      IWorkflowPackage.IWORKFLOW_NODE__IS_FINISH,
                                      oldIsFinish,
                                      this.isFinish ) );
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd( InternalEObject otherEnd,
                                        int featureID,
                                        NotificationChain msgs )
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        if (eInternalContainer() != null)
          msgs = eBasicRemoveFromContainer(msgs);
        return basicSetWorkflow((IWorkflow)otherEnd, msgs);
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return ( ( InternalEList<InternalEObject> )( InternalEList<?> )getOutputs() ).basicAdd( otherEnd,
                                                                                                msgs );
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return ( ( InternalEList<InternalEObject> )( InternalEList<?> )getInputs() ).basicAdd( otherEnd,
                                                                                               msgs );
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove( InternalEObject otherEnd,
                                           int featureID,
                                           NotificationChain msgs )
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return basicSetWorkflow(null, msgs);
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs)
  {
    switch (this.eContainerFeatureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return eInternalContainer().eInverseRemove( this,
                                                    IWorkflowPackage.IWORKFLOW__NODES,
                                                    IWorkflow.class,
                                                    msgs );
    }
    return super.eBasicRemoveFromContainerFeature(msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return getWorkflow();
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return getOutputs();
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return getInputs();
      case IWorkflowPackage.IWORKFLOW_NODE__IS_START:
        return isIsStart() ? Boolean.TRUE : Boolean.FALSE;
      case IWorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
        return isIsFinish() ? Boolean.TRUE : Boolean.FALSE;
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        setWorkflow((IWorkflow)newValue);
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends IOutputPort>)newValue);
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        getInputs().clear();
        getInputs().addAll((Collection<? extends IInputPort>)newValue);
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__IS_START:
        setIsStart(((Boolean)newValue).booleanValue());
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
        setIsFinish(((Boolean)newValue).booleanValue());
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        setWorkflow((IWorkflow)null);
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        getOutputs().clear();
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        getInputs().clear();
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__IS_START:
        setIsStart(IS_START_EDEFAULT);
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
        setIsFinish(IS_FINISH_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case IWorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return getWorkflow() != null;
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return this.outputs != null && !this.outputs.isEmpty();
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return this.inputs != null && !this.inputs.isEmpty();
      case IWorkflowPackage.IWORKFLOW_NODE__IS_START:
        return this.isStart != IS_START_EDEFAULT;
      case IWorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
        return this.isFinish != IS_FINISH_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (isStart: ");
    result.append(this.isStart);
    result.append(", isFinish: ");
    result.append(this.isFinish);
    result.append(')');
    return result.toString();
  }

} //IWorkflowNodeImpl
