/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.ILink;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.WorkflowPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ILink</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.impl.ILinkImpl#getWorkflow <em>Workflow</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.ILinkImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.ILinkImpl#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkImpl extends IWorkflowElementImpl implements ILink
{
  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected IInputPort target;

  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
  protected IOutputPort source;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LinkImpl()
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
    return WorkflowPackage.Literals.ILINK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWorkflow getWorkflow()
  {
    if (eContainerFeatureID != WorkflowPackage.ILINK__WORKFLOW) return null;
    return (IWorkflow)eContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWorkflow(IWorkflow newWorkflow, NotificationChain msgs)
  {
    msgs = eBasicSetContainer((InternalEObject)newWorkflow, WorkflowPackage.ILINK__WORKFLOW, msgs);
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWorkflow(IWorkflow newWorkflow)
  {
    if (newWorkflow != eInternalContainer() || (eContainerFeatureID != WorkflowPackage.ILINK__WORKFLOW && newWorkflow != null))
    {
      if (EcoreUtil.isAncestor(this, newWorkflow))
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
      NotificationChain msgs = null;
      if (eInternalContainer() != null)
        msgs = eBasicRemoveFromContainer(msgs);
      if (newWorkflow != null)
        msgs = ((InternalEObject)newWorkflow).eInverseAdd(this, WorkflowPackage.IWORKFLOW__LINKS, IWorkflow.class, msgs);
      msgs = basicSetWorkflow(newWorkflow, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.ILINK__WORKFLOW, newWorkflow, newWorkflow));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IInputPort getTarget()
  {
    if (target != null && target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)target;
      target = (IInputPort)eResolveProxy(oldTarget);
      if (target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, WorkflowPackage.ILINK__TARGET, oldTarget, target));
      }
    }
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IInputPort basicGetTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTarget(IInputPort newTarget, NotificationChain msgs)
  {
    IInputPort oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.ILINK__TARGET, oldTarget, newTarget);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(IInputPort newTarget)
  {
    if (newTarget != target)
    {
      NotificationChain msgs = null;
      if (target != null)
        msgs = ((InternalEObject)target).eInverseRemove(this, WorkflowPackage.IINPUT_PORT__LINKS, IInputPort.class, msgs);
      if (newTarget != null)
        msgs = ((InternalEObject)newTarget).eInverseAdd(this, WorkflowPackage.IINPUT_PORT__LINKS, IInputPort.class, msgs);
      msgs = basicSetTarget(newTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.ILINK__TARGET, newTarget, newTarget));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IOutputPort getSource()
  {
    if (source != null && source.eIsProxy())
    {
      InternalEObject oldSource = (InternalEObject)source;
      source = (IOutputPort)eResolveProxy(oldSource);
      if (source != oldSource)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, WorkflowPackage.ILINK__SOURCE, oldSource, source));
      }
    }
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IOutputPort basicGetSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSource(IOutputPort newSource, NotificationChain msgs)
  {
    IOutputPort oldSource = source;
    source = newSource;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WorkflowPackage.ILINK__SOURCE, oldSource, newSource);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSource(IOutputPort newSource)
  {
    if (newSource != source)
    {
      NotificationChain msgs = null;
      if (source != null)
        msgs = ((InternalEObject)source).eInverseRemove(this, WorkflowPackage.IOUTPUT_PORT__LINKS, IOutputPort.class, msgs);
      if (newSource != null)
        msgs = ((InternalEObject)newSource).eInverseAdd(this, WorkflowPackage.IOUTPUT_PORT__LINKS, IOutputPort.class, msgs);
      msgs = basicSetSource(newSource, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.ILINK__SOURCE, newSource, newSource));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case WorkflowPackage.ILINK__WORKFLOW:
        if (eInternalContainer() != null)
          msgs = eBasicRemoveFromContainer(msgs);
        return basicSetWorkflow((IWorkflow)otherEnd, msgs);
      case WorkflowPackage.ILINK__TARGET:
        if (target != null)
          msgs = ((InternalEObject)target).eInverseRemove(this, WorkflowPackage.IINPUT_PORT__LINKS, IInputPort.class, msgs);
        return basicSetTarget((IInputPort)otherEnd, msgs);
      case WorkflowPackage.ILINK__SOURCE:
        if (source != null)
          msgs = ((InternalEObject)source).eInverseRemove(this, WorkflowPackage.IOUTPUT_PORT__LINKS, IOutputPort.class, msgs);
        return basicSetSource((IOutputPort)otherEnd, msgs);
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
      case WorkflowPackage.ILINK__WORKFLOW:
        return basicSetWorkflow(null, msgs);
      case WorkflowPackage.ILINK__TARGET:
        return basicSetTarget(null, msgs);
      case WorkflowPackage.ILINK__SOURCE:
        return basicSetSource(null, msgs);
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
      case WorkflowPackage.ILINK__WORKFLOW:
        return eInternalContainer().eInverseRemove(this, WorkflowPackage.IWORKFLOW__LINKS, IWorkflow.class, msgs);
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
      case WorkflowPackage.ILINK__WORKFLOW:
        return getWorkflow();
      case WorkflowPackage.ILINK__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
      case WorkflowPackage.ILINK__SOURCE:
        if (resolve) return getSource();
        return basicGetSource();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case WorkflowPackage.ILINK__WORKFLOW:
        setWorkflow((IWorkflow)newValue);
        return;
      case WorkflowPackage.ILINK__TARGET:
        setTarget((IInputPort)newValue);
        return;
      case WorkflowPackage.ILINK__SOURCE:
        setSource((IOutputPort)newValue);
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
      case WorkflowPackage.ILINK__WORKFLOW:
        setWorkflow((IWorkflow)null);
        return;
      case WorkflowPackage.ILINK__TARGET:
        setTarget((IInputPort)null);
        return;
      case WorkflowPackage.ILINK__SOURCE:
        setSource((IOutputPort)null);
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
      case WorkflowPackage.ILINK__WORKFLOW:
        return getWorkflow() != null;
      case WorkflowPackage.ILINK__TARGET:
        return target != null;
      case WorkflowPackage.ILINK__SOURCE:
        return source != null;
    }
    return super.eIsSet(featureID);
  }

} //ILinkImpl
