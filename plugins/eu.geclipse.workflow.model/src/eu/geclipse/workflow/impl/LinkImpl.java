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
import eu.geclipse.workflow.ILink;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowPackage;

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
 *   <li>{@link eu.geclipse.workflow.impl.LinkImpl#getWorkflow <em>Workflow</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.LinkImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.LinkImpl#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkImpl extends WorkflowElementImpl implements ILink
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
    return IWorkflowPackage.Literals.ILINK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IWorkflow getWorkflow()
  {
    if (this.eContainerFeatureID != IWorkflowPackage.ILINK__WORKFLOW) return null;
    return (IWorkflow)eContainer();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWorkflow(IWorkflow newWorkflow, NotificationChain msgs)
  {
    msgs = eBasicSetContainer((InternalEObject)newWorkflow, IWorkflowPackage.ILINK__WORKFLOW, msgs);
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWorkflow(IWorkflow newWorkflow)
  {
    if (newWorkflow != eInternalContainer() || (this.eContainerFeatureID != IWorkflowPackage.ILINK__WORKFLOW && newWorkflow != null))
    {
      if (EcoreUtil.isAncestor(this, newWorkflow))
        throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
      NotificationChain msgs = null;
      if (eInternalContainer() != null)
        msgs = eBasicRemoveFromContainer(msgs);
      if (newWorkflow != null)
        msgs = ((InternalEObject)newWorkflow).eInverseAdd(this, IWorkflowPackage.IWORKFLOW__LINKS, IWorkflow.class, msgs);
      msgs = basicSetWorkflow(newWorkflow, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.ILINK__WORKFLOW, newWorkflow, newWorkflow));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IInputPort getTarget()
  {
    if (this.target != null && this.target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)this.target;
      this.target = (IInputPort)eResolveProxy(oldTarget);
      if (this.target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, IWorkflowPackage.ILINK__TARGET, oldTarget, this.target));
      }
    }
    return this.target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IInputPort basicGetTarget()
  {
    return this.target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTarget(IInputPort newTarget, NotificationChain msgs)
  {
    IInputPort oldTarget = this.target;
    this.target = newTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IWorkflowPackage.ILINK__TARGET, oldTarget, newTarget);
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
    if (newTarget != this.target)
    {
      NotificationChain msgs = null;
      if (this.target != null)
        msgs = ((InternalEObject)this.target).eInverseRemove(this, IWorkflowPackage.IINPUT_PORT__LINKS, IInputPort.class, msgs);
      if (newTarget != null)
        msgs = ((InternalEObject)newTarget).eInverseAdd(this, IWorkflowPackage.IINPUT_PORT__LINKS, IInputPort.class, msgs);
      msgs = basicSetTarget(newTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.ILINK__TARGET, newTarget, newTarget));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IOutputPort getSource()
  {
    if (this.source != null && this.source.eIsProxy())
    {
      InternalEObject oldSource = (InternalEObject)this.source;
      this.source = (IOutputPort)eResolveProxy(oldSource);
      if (this.source != oldSource)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, IWorkflowPackage.ILINK__SOURCE, oldSource, this.source));
      }
    }
    return this.source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IOutputPort basicGetSource()
  {
    return this.source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSource(IOutputPort newSource, NotificationChain msgs)
  {
    IOutputPort oldSource = this.source;
    this.source = newSource;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IWorkflowPackage.ILINK__SOURCE, oldSource, newSource);
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
    if (newSource != this.source)
    {
      NotificationChain msgs = null;
      if (this.source != null)
        msgs = ((InternalEObject)this.source).eInverseRemove(this, IWorkflowPackage.IOUTPUT_PORT__LINKS, IOutputPort.class, msgs);
      if (newSource != null)
        msgs = ((InternalEObject)newSource).eInverseAdd(this, IWorkflowPackage.IOUTPUT_PORT__LINKS, IOutputPort.class, msgs);
      msgs = basicSetSource(newSource, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.ILINK__SOURCE, newSource, newSource));
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
      case IWorkflowPackage.ILINK__WORKFLOW:
        if (eInternalContainer() != null)
          msgs = eBasicRemoveFromContainer(msgs);
        return basicSetWorkflow((IWorkflow)otherEnd, msgs);
      case IWorkflowPackage.ILINK__TARGET:
        if (this.target != null)
          msgs = ((InternalEObject)this.target).eInverseRemove(this, IWorkflowPackage.IINPUT_PORT__LINKS, IInputPort.class, msgs);
        return basicSetTarget((IInputPort)otherEnd, msgs);
      case IWorkflowPackage.ILINK__SOURCE:
        if (this.source != null)
          msgs = ((InternalEObject)this.source).eInverseRemove(this, IWorkflowPackage.IOUTPUT_PORT__LINKS, IOutputPort.class, msgs);
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
      case IWorkflowPackage.ILINK__WORKFLOW:
        return basicSetWorkflow(null, msgs);
      case IWorkflowPackage.ILINK__TARGET:
        return basicSetTarget(null, msgs);
      case IWorkflowPackage.ILINK__SOURCE:
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
    switch (this.eContainerFeatureID)
    {
      case IWorkflowPackage.ILINK__WORKFLOW:
        return eInternalContainer().eInverseRemove(this, IWorkflowPackage.IWORKFLOW__LINKS, IWorkflow.class, msgs);
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
      case IWorkflowPackage.ILINK__WORKFLOW:
        return getWorkflow();
      case IWorkflowPackage.ILINK__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
      case IWorkflowPackage.ILINK__SOURCE:
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
      case IWorkflowPackage.ILINK__WORKFLOW:
        setWorkflow((IWorkflow)newValue);
        return;
      case IWorkflowPackage.ILINK__TARGET:
        setTarget((IInputPort)newValue);
        return;
      case IWorkflowPackage.ILINK__SOURCE:
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
      case IWorkflowPackage.ILINK__WORKFLOW:
        setWorkflow((IWorkflow)null);
        return;
      case IWorkflowPackage.ILINK__TARGET:
        setTarget((IInputPort)null);
        return;
      case IWorkflowPackage.ILINK__SOURCE:
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
      case IWorkflowPackage.ILINK__WORKFLOW:
        return getWorkflow() != null;
      case IWorkflowPackage.ILINK__TARGET:
        return this.target != null;
      case IWorkflowPackage.ILINK__SOURCE:
        return this.source != null;
    }
    return super.eIsSet(featureID);
  }

} //ILinkImpl
