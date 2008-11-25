/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.model.qdl.IntegerRangeValueType;
import eu.geclipse.batch.model.qdl.QdlPackage;
import eu.geclipse.batch.model.qdl.QueueStatusEnumeration;
import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.batch.model.qdl.RangeValueType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Queue Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getQueueName <em>Queue Name</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getQueueType <em>Queue Type</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getQueueStatus <em>Queue Status</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#isQueueStarted <em>Queue Started</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getRunningJobs <em>Running Jobs</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getJobsInQueue <em>Jobs In Queue</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.QueueTypeImpl#getAssignedResources <em>Assigned Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QueueTypeImpl extends EObjectImpl implements QueueType
{
  /**
   * The default value of the '{@link #getQueueName() <em>Queue Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueName()
   * @generated
   * @ordered
   */
  protected static final String QUEUE_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getQueueName() <em>Queue Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueName()
   * @generated
   * @ordered
   */
  protected String queueName = QUEUE_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected static final String DESCRIPTION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected String description = DESCRIPTION_EDEFAULT;

  /**
   * The default value of the '{@link #getQueueType() <em>Queue Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueType()
   * @generated
   * @ordered
   */
  protected static final QueueTypeEnumeration QUEUE_TYPE_EDEFAULT = QueueTypeEnumeration.EXECUTION;

  /**
   * The cached value of the '{@link #getQueueType() <em>Queue Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueType()
   * @generated
   * @ordered
   */
  protected QueueTypeEnumeration queueType = QUEUE_TYPE_EDEFAULT;

  /**
   * This is true if the Queue Type attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean queueTypeESet;

  /**
   * The default value of the '{@link #getQueueStatus() <em>Queue Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueStatus()
   * @generated
   * @ordered
   */
  protected static final QueueStatusEnumeration QUEUE_STATUS_EDEFAULT = QueueStatusEnumeration.ENABLED;

  /**
   * The cached value of the '{@link #getQueueStatus() <em>Queue Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueStatus()
   * @generated
   * @ordered
   */
  protected QueueStatusEnumeration queueStatus = QUEUE_STATUS_EDEFAULT;

  /**
   * This is true if the Queue Status attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean queueStatusESet;

  /**
   * The default value of the '{@link #isQueueStarted() <em>Queue Started</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isQueueStarted()
   * @generated
   * @ordered
   */
  protected static final boolean QUEUE_STARTED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isQueueStarted() <em>Queue Started</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isQueueStarted()
   * @generated
   * @ordered
   */
  protected boolean queueStarted = QUEUE_STARTED_EDEFAULT;

  /**
   * This is true if the Queue Started attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean queueStartedESet;

  /**
   * The cached value of the '{@link #getCPUTimeLimit() <em>CPU Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCPUTimeLimit()
   * @generated
   * @ordered
   */
  protected RangeValueType cPUTimeLimit;

  /**
   * The cached value of the '{@link #getWallTimeLimit() <em>Wall Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWallTimeLimit()
   * @generated
   * @ordered
   */
  protected RangeValueType wallTimeLimit;

  /**
   * The cached value of the '{@link #getAllowedVirtualOrganizations() <em>Allowed Virtual Organizations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAllowedVirtualOrganizations()
   * @generated
   * @ordered
   */
  protected AllowedVirtualOrganizationsType allowedVirtualOrganizations;

  /**
   * The cached value of the '{@link #getPriority() <em>Priority</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPriority()
   * @generated
   * @ordered
   */
  protected IntegerRangeValueType priority;

  /**
   * The cached value of the '{@link #getRunningJobs() <em>Running Jobs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRunningJobs()
   * @generated
   * @ordered
   */
  protected IntegerRangeValueType runningJobs;

  /**
   * The cached value of the '{@link #getJobsInQueue() <em>Jobs In Queue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJobsInQueue()
   * @generated
   * @ordered
   */
  protected IntegerRangeValueType jobsInQueue;

  /**
   * The cached value of the '{@link #getAssignedResources() <em>Assigned Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssignedResources()
   * @generated
   * @ordered
   */
  protected IntegerRangeValueType assignedResources;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected QueueTypeImpl()
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
    return QdlPackage.Literals.QUEUE_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getQueueName()
  {
    return this.queueName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueName(String newQueueName)
  {
    String oldQueueName = this.queueName;
    this.queueName = newQueueName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__QUEUE_NAME, oldQueueName, this.queueName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription()
  {
    return this.description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription)
  {
    String oldDescription = this.description;
    this.description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__DESCRIPTION, oldDescription, this.description));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueTypeEnumeration getQueueType()
  {
    return this.queueType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueType(QueueTypeEnumeration newQueueType)
  {
    QueueTypeEnumeration oldQueueType = this.queueType;
    this.queueType = newQueueType == null ? QUEUE_TYPE_EDEFAULT : newQueueType;
    boolean oldQueueTypeESet = this.queueTypeESet;
    this.queueTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__QUEUE_TYPE, oldQueueType, this.queueType, !oldQueueTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetQueueType()
  {
    QueueTypeEnumeration oldQueueType = this.queueType;
    boolean oldQueueTypeESet = this.queueTypeESet;
    this.queueType = QUEUE_TYPE_EDEFAULT;
    this.queueTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, QdlPackage.QUEUE_TYPE__QUEUE_TYPE, oldQueueType, QUEUE_TYPE_EDEFAULT, oldQueueTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetQueueType()
  {
    return this.queueTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueStatusEnumeration getQueueStatus()
  {
    return this.queueStatus;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueStatus(QueueStatusEnumeration newQueueStatus)
  {
    QueueStatusEnumeration oldQueueStatus = this.queueStatus;
    this.queueStatus = newQueueStatus == null ? QUEUE_STATUS_EDEFAULT : newQueueStatus;
    boolean oldQueueStatusESet = this.queueStatusESet;
    this.queueStatusESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__QUEUE_STATUS, oldQueueStatus, this.queueStatus, !oldQueueStatusESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetQueueStatus()
  {
    QueueStatusEnumeration oldQueueStatus = this.queueStatus;
    boolean oldQueueStatusESet = this.queueStatusESet;
    this.queueStatus = QUEUE_STATUS_EDEFAULT;
    this.queueStatusESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, QdlPackage.QUEUE_TYPE__QUEUE_STATUS, oldQueueStatus, QUEUE_STATUS_EDEFAULT, oldQueueStatusESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetQueueStatus()
  {
    return this.queueStatusESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isQueueStarted()
  {
    return this.queueStarted;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueStarted(boolean newQueueStarted)
  {
    boolean oldQueueStarted = this.queueStarted;
    this.queueStarted = newQueueStarted;
    boolean oldQueueStartedESet = this.queueStartedESet;
    this.queueStartedESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__QUEUE_STARTED, oldQueueStarted, this.queueStarted, !oldQueueStartedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetQueueStarted()
  {
    boolean oldQueueStarted = this.queueStarted;
    boolean oldQueueStartedESet = this.queueStartedESet;
    this.queueStarted = QUEUE_STARTED_EDEFAULT;
    this.queueStartedESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, QdlPackage.QUEUE_TYPE__QUEUE_STARTED, oldQueueStarted, QUEUE_STARTED_EDEFAULT, oldQueueStartedESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetQueueStarted()
  {
    return this.queueStartedESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeValueType getCPUTimeLimit()
  {
    return this.cPUTimeLimit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCPUTimeLimit(RangeValueType newCPUTimeLimit, NotificationChain msgs)
  {
    RangeValueType oldCPUTimeLimit = this.cPUTimeLimit;
    this.cPUTimeLimit = newCPUTimeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT, oldCPUTimeLimit, newCPUTimeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCPUTimeLimit(RangeValueType newCPUTimeLimit)
  {
    if (newCPUTimeLimit != this.cPUTimeLimit)
    {
      NotificationChain msgs = null;
      if (this.cPUTimeLimit != null)
        msgs = ((InternalEObject)this.cPUTimeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT, null, msgs);
      if (newCPUTimeLimit != null)
        msgs = ((InternalEObject)newCPUTimeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT, null, msgs);
      msgs = basicSetCPUTimeLimit(newCPUTimeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT, newCPUTimeLimit, newCPUTimeLimit));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeValueType getWallTimeLimit()
  {
    return this.wallTimeLimit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWallTimeLimit(RangeValueType newWallTimeLimit, NotificationChain msgs)
  {
    RangeValueType oldWallTimeLimit = this.wallTimeLimit;
    this.wallTimeLimit = newWallTimeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT, oldWallTimeLimit, newWallTimeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWallTimeLimit(RangeValueType newWallTimeLimit)
  {
    if (newWallTimeLimit != this.wallTimeLimit)
    {
      NotificationChain msgs = null;
      if (this.wallTimeLimit != null)
        msgs = ((InternalEObject)this.wallTimeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT, null, msgs);
      if (newWallTimeLimit != null)
        msgs = ((InternalEObject)newWallTimeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT, null, msgs);
      msgs = basicSetWallTimeLimit(newWallTimeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT, newWallTimeLimit, newWallTimeLimit));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AllowedVirtualOrganizationsType getAllowedVirtualOrganizations()
  {
    return this.allowedVirtualOrganizations;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAllowedVirtualOrganizations(AllowedVirtualOrganizationsType newAllowedVirtualOrganizations, NotificationChain msgs)
  {
    AllowedVirtualOrganizationsType oldAllowedVirtualOrganizations = this.allowedVirtualOrganizations;
    this.allowedVirtualOrganizations = newAllowedVirtualOrganizations;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS, oldAllowedVirtualOrganizations, newAllowedVirtualOrganizations);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAllowedVirtualOrganizations(AllowedVirtualOrganizationsType newAllowedVirtualOrganizations)
  {
    if (newAllowedVirtualOrganizations != this.allowedVirtualOrganizations)
    {
      NotificationChain msgs = null;
      if (this.allowedVirtualOrganizations != null)
        msgs = ((InternalEObject)this.allowedVirtualOrganizations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS, null, msgs);
      if (newAllowedVirtualOrganizations != null)
        msgs = ((InternalEObject)newAllowedVirtualOrganizations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS, null, msgs);
      msgs = basicSetAllowedVirtualOrganizations(newAllowedVirtualOrganizations, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS, newAllowedVirtualOrganizations, newAllowedVirtualOrganizations));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getPriority()
  {
    return this.priority;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPriority(IntegerRangeValueType newPriority, NotificationChain msgs)
  {
    IntegerRangeValueType oldPriority = this.priority;
    this.priority = newPriority;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__PRIORITY, oldPriority, newPriority);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPriority(IntegerRangeValueType newPriority)
  {
    if (newPriority != this.priority)
    {
      NotificationChain msgs = null;
      if (this.priority != null)
        msgs = ((InternalEObject)this.priority).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__PRIORITY, null, msgs);
      if (newPriority != null)
        msgs = ((InternalEObject)newPriority).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__PRIORITY, null, msgs);
      msgs = basicSetPriority(newPriority, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__PRIORITY, newPriority, newPriority));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getRunningJobs()
  {
    return this.runningJobs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRunningJobs(IntegerRangeValueType newRunningJobs, NotificationChain msgs)
  {
    IntegerRangeValueType oldRunningJobs = this.runningJobs;
    this.runningJobs = newRunningJobs;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__RUNNING_JOBS, oldRunningJobs, newRunningJobs);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRunningJobs(IntegerRangeValueType newRunningJobs)
  {
    if (newRunningJobs != this.runningJobs)
    {
      NotificationChain msgs = null;
      if (this.runningJobs != null)
        msgs = ((InternalEObject)this.runningJobs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__RUNNING_JOBS, null, msgs);
      if (newRunningJobs != null)
        msgs = ((InternalEObject)newRunningJobs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__RUNNING_JOBS, null, msgs);
      msgs = basicSetRunningJobs(newRunningJobs, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__RUNNING_JOBS, newRunningJobs, newRunningJobs));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getJobsInQueue()
  {
    return this.jobsInQueue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetJobsInQueue(IntegerRangeValueType newJobsInQueue, NotificationChain msgs)
  {
    IntegerRangeValueType oldJobsInQueue = this.jobsInQueue;
    this.jobsInQueue = newJobsInQueue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE, oldJobsInQueue, newJobsInQueue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJobsInQueue(IntegerRangeValueType newJobsInQueue)
  {
    if (newJobsInQueue != this.jobsInQueue)
    {
      NotificationChain msgs = null;
      if (this.jobsInQueue != null)
        msgs = ((InternalEObject)this.jobsInQueue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE, null, msgs);
      if (newJobsInQueue != null)
        msgs = ((InternalEObject)newJobsInQueue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE, null, msgs);
      msgs = basicSetJobsInQueue(newJobsInQueue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE, newJobsInQueue, newJobsInQueue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getAssignedResources()
  {
    return this.assignedResources;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAssignedResources(IntegerRangeValueType newAssignedResources, NotificationChain msgs)
  {
    IntegerRangeValueType oldAssignedResources = this.assignedResources;
    this.assignedResources = newAssignedResources;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES, oldAssignedResources, newAssignedResources);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAssignedResources(IntegerRangeValueType newAssignedResources)
  {
    if (newAssignedResources != this.assignedResources)
    {
      NotificationChain msgs = null;
      if (this.assignedResources != null)
        msgs = ((InternalEObject)this.assignedResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES, null, msgs);
      if (newAssignedResources != null)
        msgs = ((InternalEObject)newAssignedResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES, null, msgs);
      msgs = basicSetAssignedResources(newAssignedResources, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES, newAssignedResources, newAssignedResources));
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
      case QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT:
        return basicSetCPUTimeLimit(null, msgs);
      case QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT:
        return basicSetWallTimeLimit(null, msgs);
      case QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS:
        return basicSetAllowedVirtualOrganizations(null, msgs);
      case QdlPackage.QUEUE_TYPE__PRIORITY:
        return basicSetPriority(null, msgs);
      case QdlPackage.QUEUE_TYPE__RUNNING_JOBS:
        return basicSetRunningJobs(null, msgs);
      case QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE:
        return basicSetJobsInQueue(null, msgs);
      case QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES:
        return basicSetAssignedResources(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case QdlPackage.QUEUE_TYPE__QUEUE_NAME:
        return getQueueName();
      case QdlPackage.QUEUE_TYPE__DESCRIPTION:
        return getDescription();
      case QdlPackage.QUEUE_TYPE__QUEUE_TYPE:
        return getQueueType();
      case QdlPackage.QUEUE_TYPE__QUEUE_STATUS:
        return getQueueStatus();
      case QdlPackage.QUEUE_TYPE__QUEUE_STARTED:
        return isQueueStarted() ? Boolean.TRUE : Boolean.FALSE;
      case QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT:
        return getCPUTimeLimit();
      case QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT:
        return getWallTimeLimit();
      case QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS:
        return getAllowedVirtualOrganizations();
      case QdlPackage.QUEUE_TYPE__PRIORITY:
        return getPriority();
      case QdlPackage.QUEUE_TYPE__RUNNING_JOBS:
        return getRunningJobs();
      case QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE:
        return getJobsInQueue();
      case QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES:
        return getAssignedResources();
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
      case QdlPackage.QUEUE_TYPE__QUEUE_NAME:
        setQueueName((String)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__QUEUE_TYPE:
        setQueueType((QueueTypeEnumeration)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__QUEUE_STATUS:
        setQueueStatus((QueueStatusEnumeration)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__QUEUE_STARTED:
        setQueueStarted(((Boolean)newValue).booleanValue());
        return;
      case QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT:
        setCPUTimeLimit((RangeValueType)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT:
        setWallTimeLimit((RangeValueType)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS:
        setAllowedVirtualOrganizations((AllowedVirtualOrganizationsType)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__PRIORITY:
        setPriority((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__RUNNING_JOBS:
        setRunningJobs((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE:
        setJobsInQueue((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES:
        setAssignedResources((IntegerRangeValueType)newValue);
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
      case QdlPackage.QUEUE_TYPE__QUEUE_NAME:
        setQueueName(QUEUE_NAME_EDEFAULT);
        return;
      case QdlPackage.QUEUE_TYPE__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case QdlPackage.QUEUE_TYPE__QUEUE_TYPE:
        unsetQueueType();
        return;
      case QdlPackage.QUEUE_TYPE__QUEUE_STATUS:
        unsetQueueStatus();
        return;
      case QdlPackage.QUEUE_TYPE__QUEUE_STARTED:
        unsetQueueStarted();
        return;
      case QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT:
        setCPUTimeLimit((RangeValueType)null);
        return;
      case QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT:
        setWallTimeLimit((RangeValueType)null);
        return;
      case QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS:
        setAllowedVirtualOrganizations((AllowedVirtualOrganizationsType)null);
        return;
      case QdlPackage.QUEUE_TYPE__PRIORITY:
        setPriority((IntegerRangeValueType)null);
        return;
      case QdlPackage.QUEUE_TYPE__RUNNING_JOBS:
        setRunningJobs((IntegerRangeValueType)null);
        return;
      case QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE:
        setJobsInQueue((IntegerRangeValueType)null);
        return;
      case QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES:
        setAssignedResources((IntegerRangeValueType)null);
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
      case QdlPackage.QUEUE_TYPE__QUEUE_NAME:
        return QUEUE_NAME_EDEFAULT == null ? this.queueName != null : !QUEUE_NAME_EDEFAULT.equals(this.queueName);
      case QdlPackage.QUEUE_TYPE__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? this.description != null : !DESCRIPTION_EDEFAULT.equals(this.description);
      case QdlPackage.QUEUE_TYPE__QUEUE_TYPE:
        return isSetQueueType();
      case QdlPackage.QUEUE_TYPE__QUEUE_STATUS:
        return isSetQueueStatus();
      case QdlPackage.QUEUE_TYPE__QUEUE_STARTED:
        return isSetQueueStarted();
      case QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT:
        return this.cPUTimeLimit != null;
      case QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT:
        return this.wallTimeLimit != null;
      case QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS:
        return this.allowedVirtualOrganizations != null;
      case QdlPackage.QUEUE_TYPE__PRIORITY:
        return this.priority != null;
      case QdlPackage.QUEUE_TYPE__RUNNING_JOBS:
        return this.runningJobs != null;
      case QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE:
        return this.jobsInQueue != null;
      case QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES:
        return this.assignedResources != null;
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
    result.append(" (queueName: "); //$NON-NLS-1$
    result.append(this.queueName);
    result.append(", description: "); //$NON-NLS-1$
    result.append(this.description);
    result.append(", queueType: "); //$NON-NLS-1$
    if (this.queueTypeESet) result.append(this.queueType); else result.append("<unset>"); //$NON-NLS-1$
    result.append(", queueStatus: "); //$NON-NLS-1$
    if (this.queueStatusESet) result.append(this.queueStatus); else result.append("<unset>"); //$NON-NLS-1$
    result.append(", queueStarted: "); //$NON-NLS-1$
    if (this.queueStartedESet) result.append(this.queueStarted); else result.append("<unset>"); //$NON-NLS-1$
    result.append(')');
    return result.toString();
  }

} //QueueTypeImpl
