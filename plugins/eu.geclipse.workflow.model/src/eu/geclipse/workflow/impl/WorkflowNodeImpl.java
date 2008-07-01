/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowNode;
import eu.geclipse.workflow.WorkflowPackage;

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
 * An implementation of the model object '<em><b>IWorkflow Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl#getWorkflow <em>Workflow</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl#isIsStart <em>Is Start</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl#isIsFinish <em>Is Finish</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class WorkflowNodeImpl extends IWorkflowElementImpl implements IWorkflowNode
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
    return WorkflowPackage.Literals.IWORKFLOW_NODE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWorkflow getWorkflow()
  {
    if (eContainerFeatureID != WorkflowPackage.IWORKFLOW_NODE__WORKFLOW) return null;
    return (IWorkflow)eContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWorkflow(IWorkflow newWorkflow, NotificationChain msgs)
  {
    msgs = eBasicSetContainer((InternalEObject)newWorkflow, WorkflowPackage.IWORKFLOW_NODE__WORKFLOW, msgs);
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWorkflow(IWorkflow newWorkflow)
  {
    if (newWorkflow != eInternalContainer() || (eContainerFeatureID != WorkflowPackage.IWORKFLOW_NODE__WORKFLOW && newWorkflow != null))
    {
      if (EcoreUtil.isAncestor(this, newWorkflow))
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
      NotificationChain msgs = null;
      if (eInternalContainer() != null)
        msgs = eBasicRemoveFromContainer(msgs);
      if (newWorkflow != null)
        msgs = ((InternalEObject)newWorkflow).eInverseAdd(this, WorkflowPackage.IWORKFLOW__NODES, IWorkflow.class, msgs);
      msgs = basicSetWorkflow(newWorkflow, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.IWORKFLOW_NODE__WORKFLOW, newWorkflow, newWorkflow));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<IOutputPort> getOutputs()
  {
    if (outputs == null)
    {
      outputs = new EObjectContainmentWithInverseEList<IOutputPort>(IOutputPort.class, this, WorkflowPackage.IWORKFLOW_NODE__OUTPUTS, WorkflowPackage.IOUTPUT_PORT__NODE);
    }
    return outputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<IInputPort> getInputs()
  {
    if (inputs == null)
    {
      inputs = new EObjectContainmentWithInverseEList<IInputPort>(IInputPort.class, this, WorkflowPackage.IWORKFLOW_NODE__INPUTS, WorkflowPackage.IINPUT_PORT__NODE);
    }
    return inputs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsStart()
  {
    return isStart;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsStart(boolean newIsStart)
  {
    boolean oldIsStart = isStart;
    isStart = newIsStart;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.IWORKFLOW_NODE__IS_START, oldIsStart, isStart));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isIsFinish()
  {
    return isFinish;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setIsFinish(boolean newIsFinish)
  {
    boolean oldIsFinish = isFinish;
    isFinish = newIsFinish;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.IWORKFLOW_NODE__IS_FINISH, oldIsFinish, isFinish));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        if (eInternalContainer() != null)
          msgs = eBasicRemoveFromContainer(msgs);
        return basicSetWorkflow((IWorkflow)otherEnd, msgs);
      case WorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputs()).basicAdd(otherEnd, msgs);
      case WorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return ((InternalEList<InternalEObject>)(InternalEList<?>)getInputs()).basicAdd(otherEnd, msgs);
    }
    return super.eInverseAdd(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return basicSetWorkflow(null, msgs);
      case WorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
      case WorkflowPackage.IWORKFLOW_NODE__INPUTS:
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
    switch (eContainerFeatureID)
    {
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return eInternalContainer().eInverseRemove(this, WorkflowPackage.IWORKFLOW__NODES, IWorkflow.class, msgs);
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
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return getWorkflow();
      case WorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return getOutputs();
      case WorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return getInputs();
      case WorkflowPackage.IWORKFLOW_NODE__IS_START:
        return isIsStart() ? Boolean.TRUE : Boolean.FALSE;
      case WorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
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
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        setWorkflow((IWorkflow)newValue);
        return;
      case WorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        getOutputs().clear();
        getOutputs().addAll((Collection<? extends IOutputPort>)newValue);
        return;
      case WorkflowPackage.IWORKFLOW_NODE__INPUTS:
        getInputs().clear();
        getInputs().addAll((Collection<? extends IInputPort>)newValue);
        return;
      case WorkflowPackage.IWORKFLOW_NODE__IS_START:
        setIsStart(((Boolean)newValue).booleanValue());
        return;
      case WorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
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
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        setWorkflow((IWorkflow)null);
        return;
      case WorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        getOutputs().clear();
        return;
      case WorkflowPackage.IWORKFLOW_NODE__INPUTS:
        getInputs().clear();
        return;
      case WorkflowPackage.IWORKFLOW_NODE__IS_START:
        setIsStart(IS_START_EDEFAULT);
        return;
      case WorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
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
      case WorkflowPackage.IWORKFLOW_NODE__WORKFLOW:
        return getWorkflow() != null;
      case WorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
        return outputs != null && !outputs.isEmpty();
      case WorkflowPackage.IWORKFLOW_NODE__INPUTS:
        return inputs != null && !inputs.isEmpty();
      case WorkflowPackage.IWORKFLOW_NODE__IS_START:
        return isStart != IS_START_EDEFAULT;
      case WorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
        return isFinish != IS_FINISH_EDEFAULT;
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
    result.append(" (isStart: "); //$NON-NLS-1$
    result.append(isStart);
    result.append(", isFinish: "); //$NON-NLS-1$
    result.append(isFinish);
    result.append(')');
    return result.toString();
  }

} //IWorkflowNodeImpl
