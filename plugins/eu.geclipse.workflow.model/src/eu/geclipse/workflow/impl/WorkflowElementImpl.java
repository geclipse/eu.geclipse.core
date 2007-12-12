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

import eu.geclipse.workflow.IWorkflowElement;
import eu.geclipse.workflow.IWorkflowPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IWorkflow Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.WorkflowElementImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class WorkflowElementImpl extends EObjectImpl implements IWorkflowElement
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final String ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated NOT
   * @ordered
   */
  protected String id;
  
  /**
   * A prefix used for the generated ids.
   */
  protected static final String idPrefix = "W";
  
  protected static int aCounter = 0;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected WorkflowElementImpl()
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
    return IWorkflowPackage.Literals.IWORKFLOW_ELEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.IWORKFLOW_ELEMENT__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * This method generates and caches an id as and when required.
   * <!-- end-user-doc -->
   */
  public String getId()
  {
    if (id == null)
    {
      id = generateId();
    }
    return id;
  }

  /**
   * This method generates a random id based on the current time.
   * @return the generated id
   */
  public synchronized String generateId()
  {
    long currentTime = System.currentTimeMillis();
    return idPrefix + currentTime + aCounter++;
  }
  
  /**
   * <!-- begin-user-doc -->
   * This method sets an id.
   * <!-- end-user-doc -->
   */
  public void setId(String newId)
  {
    String oldId = id;
    if (newId == null && id == null)
    {
      id = generateId();
    }
    else
    {
      id = newId;
    }
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.IWORKFLOW_ELEMENT__ID, oldId, id));
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
      case IWorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        return getName();
      case IWorkflowPackage.IWORKFLOW_ELEMENT__ID:
        return getId();
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
      case IWorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        setName((String)newValue);
        return;
      case IWorkflowPackage.IWORKFLOW_ELEMENT__ID:
        setId((String)newValue);
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
      case IWorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        setName(NAME_EDEFAULT);
        return;
      case IWorkflowPackage.IWORKFLOW_ELEMENT__ID:
        setId(ID_EDEFAULT);
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
      case IWorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case IWorkflowPackage.IWORKFLOW_ELEMENT__ID:
        return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
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
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //IWorkflowElementImpl
