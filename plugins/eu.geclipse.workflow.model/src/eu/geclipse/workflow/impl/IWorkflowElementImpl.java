/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow.impl;

import eu.geclipse.workflow.IWorkflowElement;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.WorkflowPackage;

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
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.workflow.impl.IWorkflowElementImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class IWorkflowElementImpl extends EObjectImpl implements IWorkflowElement
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
  protected static final String idPrefix = "W"; //$NON-NLS-1$
  
  protected static int aCounter = 0;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IWorkflowElementImpl()
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
    return WorkflowPackage.Literals.IWORKFLOW_ELEMENT;
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
      eNotify(new ENotificationImpl(this, Notification.SET, WorkflowPackage.IWORKFLOW_ELEMENT__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * This method generates and caches an id as and when required.
   * <!-- end-user-doc -->
   */
  public String getId()
  {
    if (this.id == null)
    {
      this.id = generateId();
    }
    return this.id;
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
    String oldId = this.id;
    if (newId == null && this.id == null)
    {
      this.id = generateId();
    }
    else
    {
      this.id = newId;
    }
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, IWorkflowPackage.IWORKFLOW_ELEMENT__ID, oldId, this.id));
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
      case WorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        return getName();
      case WorkflowPackage.IWORKFLOW_ELEMENT__ID:
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
      case WorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        setName((String)newValue);
        return;
      case WorkflowPackage.IWORKFLOW_ELEMENT__ID:
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
      case WorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        setName(NAME_EDEFAULT);
        return;
      case WorkflowPackage.IWORKFLOW_ELEMENT__ID:
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
      case WorkflowPackage.IWORKFLOW_ELEMENT__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case WorkflowPackage.IWORKFLOW_ELEMENT__ID:
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
    result.append(" (name: "); //$NON-NLS-1$
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //IWorkflowElementImpl
