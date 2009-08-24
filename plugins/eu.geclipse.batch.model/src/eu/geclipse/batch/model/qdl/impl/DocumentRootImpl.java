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
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.batch.model.qdl.IntegerRangeValueType;
import eu.geclipse.batch.model.qdl.QdlPackage;
import eu.geclipse.batch.model.qdl.QueueStatusEnumeration;
import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.batch.model.qdl.RangeValueType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getAssignedResources <em>Assigned Resources</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getJobsInQueue <em>Jobs In Queue</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getQueue <em>Queue</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#isQueueStarted <em>Queue Started</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getQueueStatus <em>Queue Status</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getQueueType <em>Queue Type</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getRunningJobs <em>Running Jobs</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getVOName <em>VO Name</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.impl.DocumentRootImpl#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot
{
  /**
   * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMixed()
   * @generated
   * @ordered
   */
  protected FeatureMap mixed;

  /**
   * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXMLNSPrefixMap()
   * @generated
   * @ordered
   */
  protected EMap<String, String> xMLNSPrefixMap;

  /**
   * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXSISchemaLocation()
   * @generated
   * @ordered
   */
  protected EMap<String, String> xSISchemaLocation;

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
   * The default value of the '{@link #isQueueStarted() <em>Queue Started</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isQueueStarted()
   * @generated
   * @ordered
   */
  protected static final boolean QUEUE_STARTED_EDEFAULT = false;

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
   * The default value of the '{@link #getQueueType() <em>Queue Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQueueType()
   * @generated
   * @ordered
   */
  protected static final QueueTypeEnumeration QUEUE_TYPE_EDEFAULT = QueueTypeEnumeration.EXECUTION;

  /**
   * The default value of the '{@link #getVOName() <em>VO Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVOName()
   * @generated
   * @ordered
   */
  protected static final String VO_NAME_EDEFAULT = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DocumentRootImpl()
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
    return QdlPackage.Literals.DOCUMENT_ROOT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getMixed()
  {
    if (mixed == null)
    {
      mixed = new BasicFeatureMap(this, QdlPackage.DOCUMENT_ROOT__MIXED);
    }
    return mixed;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EMap<String, String> getXMLNSPrefixMap()
  {
    if (xMLNSPrefixMap == null)
    {
      xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, QdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    }
    return xMLNSPrefixMap;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EMap<String, String> getXSISchemaLocation()
  {
    if (xSISchemaLocation == null)
    {
      xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, QdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    }
    return xSISchemaLocation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AllowedVirtualOrganizationsType getAllowedVirtualOrganizations()
  {
    return (AllowedVirtualOrganizationsType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAllowedVirtualOrganizations(AllowedVirtualOrganizationsType newAllowedVirtualOrganizations, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS, newAllowedVirtualOrganizations, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAllowedVirtualOrganizations(AllowedVirtualOrganizationsType newAllowedVirtualOrganizations)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS, newAllowedVirtualOrganizations);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getAssignedResources()
  {
    return (IntegerRangeValueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__ASSIGNED_RESOURCES, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAssignedResources(IntegerRangeValueType newAssignedResources, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__ASSIGNED_RESOURCES, newAssignedResources, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAssignedResources(IntegerRangeValueType newAssignedResources)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__ASSIGNED_RESOURCES, newAssignedResources);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeValueType getCPUTimeLimit()
  {
    return (RangeValueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetCPUTimeLimit(RangeValueType newCPUTimeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT, newCPUTimeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCPUTimeLimit(RangeValueType newCPUTimeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT, newCPUTimeLimit);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDescription()
  {
    return (String)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(String newDescription)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, newDescription);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getJobsInQueue()
  {
    return (IntegerRangeValueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__JOBS_IN_QUEUE, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetJobsInQueue(IntegerRangeValueType newJobsInQueue, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__JOBS_IN_QUEUE, newJobsInQueue, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setJobsInQueue(IntegerRangeValueType newJobsInQueue)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__JOBS_IN_QUEUE, newJobsInQueue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getPriority()
  {
    return (IntegerRangeValueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__PRIORITY, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetPriority(IntegerRangeValueType newPriority, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__PRIORITY, newPriority, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPriority(IntegerRangeValueType newPriority)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__PRIORITY, newPriority);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueType getQueue()
  {
    return (QueueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetQueue(QueueType newQueue, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE, newQueue, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueue(QueueType newQueue)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE, newQueue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isQueueStarted()
  {
    return ((Boolean)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE_STARTED, true)).booleanValue();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueStarted(boolean newQueueStarted)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE_STARTED, new Boolean(newQueueStarted));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueStatusEnumeration getQueueStatus()
  {
    return (QueueStatusEnumeration)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE_STATUS, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueStatus(QueueStatusEnumeration newQueueStatus)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE_STATUS, newQueueStatus);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueTypeEnumeration getQueueType()
  {
    return (QueueTypeEnumeration)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE_TYPE, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQueueType(QueueTypeEnumeration newQueueType)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__QUEUE_TYPE, newQueueType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IntegerRangeValueType getRunningJobs()
  {
    return (IntegerRangeValueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__RUNNING_JOBS, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRunningJobs(IntegerRangeValueType newRunningJobs, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__RUNNING_JOBS, newRunningJobs, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRunningJobs(IntegerRangeValueType newRunningJobs)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__RUNNING_JOBS, newRunningJobs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getVOName()
  {
    return (String)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__VO_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setVOName(String newVOName)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__VO_NAME, newVOName);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeValueType getWallTimeLimit()
  {
    return (RangeValueType)getMixed().get(QdlPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetWallTimeLimit(RangeValueType newWallTimeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(QdlPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT, newWallTimeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWallTimeLimit(RangeValueType newWallTimeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(QdlPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT, newWallTimeLimit);
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
      case QdlPackage.DOCUMENT_ROOT__MIXED:
        return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
      case QdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
      case QdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
      case QdlPackage.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS:
        return basicSetAllowedVirtualOrganizations(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__ASSIGNED_RESOURCES:
        return basicSetAssignedResources(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        return basicSetCPUTimeLimit(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__JOBS_IN_QUEUE:
        return basicSetJobsInQueue(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__PRIORITY:
        return basicSetPriority(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__QUEUE:
        return basicSetQueue(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__RUNNING_JOBS:
        return basicSetRunningJobs(null, msgs);
      case QdlPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        return basicSetWallTimeLimit(null, msgs);
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
      case QdlPackage.DOCUMENT_ROOT__MIXED:
        if (coreType) return getMixed();
        return ((FeatureMap.Internal)getMixed()).getWrapper();
      case QdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        if (coreType) return getXMLNSPrefixMap();
        else return getXMLNSPrefixMap().map();
      case QdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        if (coreType) return getXSISchemaLocation();
        else return getXSISchemaLocation().map();
      case QdlPackage.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS:
        return getAllowedVirtualOrganizations();
      case QdlPackage.DOCUMENT_ROOT__ASSIGNED_RESOURCES:
        return getAssignedResources();
      case QdlPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        return getCPUTimeLimit();
      case QdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        return getDescription();
      case QdlPackage.DOCUMENT_ROOT__JOBS_IN_QUEUE:
        return getJobsInQueue();
      case QdlPackage.DOCUMENT_ROOT__PRIORITY:
        return getPriority();
      case QdlPackage.DOCUMENT_ROOT__QUEUE:
        return getQueue();
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STARTED:
        return isQueueStarted() ? Boolean.TRUE : Boolean.FALSE;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STATUS:
        return getQueueStatus();
      case QdlPackage.DOCUMENT_ROOT__QUEUE_TYPE:
        return getQueueType();
      case QdlPackage.DOCUMENT_ROOT__RUNNING_JOBS:
        return getRunningJobs();
      case QdlPackage.DOCUMENT_ROOT__VO_NAME:
        return getVOName();
      case QdlPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        return getWallTimeLimit();
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
      case QdlPackage.DOCUMENT_ROOT__MIXED:
        ((FeatureMap.Internal)getMixed()).set(newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS:
        setAllowedVirtualOrganizations((AllowedVirtualOrganizationsType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__ASSIGNED_RESOURCES:
        setAssignedResources((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        setCPUTimeLimit((RangeValueType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__JOBS_IN_QUEUE:
        setJobsInQueue((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__PRIORITY:
        setPriority((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE:
        setQueue((QueueType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STARTED:
        setQueueStarted(((Boolean)newValue).booleanValue());
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STATUS:
        setQueueStatus((QueueStatusEnumeration)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_TYPE:
        setQueueType((QueueTypeEnumeration)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__RUNNING_JOBS:
        setRunningJobs((IntegerRangeValueType)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__VO_NAME:
        setVOName((String)newValue);
        return;
      case QdlPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        setWallTimeLimit((RangeValueType)newValue);
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
      case QdlPackage.DOCUMENT_ROOT__MIXED:
        getMixed().clear();
        return;
      case QdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        getXMLNSPrefixMap().clear();
        return;
      case QdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        getXSISchemaLocation().clear();
        return;
      case QdlPackage.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS:
        setAllowedVirtualOrganizations((AllowedVirtualOrganizationsType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__ASSIGNED_RESOURCES:
        setAssignedResources((IntegerRangeValueType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        setCPUTimeLimit((RangeValueType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case QdlPackage.DOCUMENT_ROOT__JOBS_IN_QUEUE:
        setJobsInQueue((IntegerRangeValueType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__PRIORITY:
        setPriority((IntegerRangeValueType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE:
        setQueue((QueueType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STARTED:
        setQueueStarted(QUEUE_STARTED_EDEFAULT);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STATUS:
        setQueueStatus(QUEUE_STATUS_EDEFAULT);
        return;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_TYPE:
        setQueueType(QUEUE_TYPE_EDEFAULT);
        return;
      case QdlPackage.DOCUMENT_ROOT__RUNNING_JOBS:
        setRunningJobs((IntegerRangeValueType)null);
        return;
      case QdlPackage.DOCUMENT_ROOT__VO_NAME:
        setVOName(VO_NAME_EDEFAULT);
        return;
      case QdlPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        setWallTimeLimit((RangeValueType)null);
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
      case QdlPackage.DOCUMENT_ROOT__MIXED:
        return mixed != null && !mixed.isEmpty();
      case QdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
      case QdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
      case QdlPackage.DOCUMENT_ROOT__ALLOWED_VIRTUAL_ORGANIZATIONS:
        return getAllowedVirtualOrganizations() != null;
      case QdlPackage.DOCUMENT_ROOT__ASSIGNED_RESOURCES:
        return getAssignedResources() != null;
      case QdlPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        return getCPUTimeLimit() != null;
      case QdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
      case QdlPackage.DOCUMENT_ROOT__JOBS_IN_QUEUE:
        return getJobsInQueue() != null;
      case QdlPackage.DOCUMENT_ROOT__PRIORITY:
        return getPriority() != null;
      case QdlPackage.DOCUMENT_ROOT__QUEUE:
        return getQueue() != null;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STARTED:
        return isQueueStarted() != QUEUE_STARTED_EDEFAULT;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_STATUS:
        return getQueueStatus() != QUEUE_STATUS_EDEFAULT;
      case QdlPackage.DOCUMENT_ROOT__QUEUE_TYPE:
        return getQueueType() != QUEUE_TYPE_EDEFAULT;
      case QdlPackage.DOCUMENT_ROOT__RUNNING_JOBS:
        return getRunningJobs() != null;
      case QdlPackage.DOCUMENT_ROOT__VO_NAME:
        return VO_NAME_EDEFAULT == null ? getVOName() != null : !VO_NAME_EDEFAULT.equals(getVOName());
      case QdlPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        return getWallTimeLimit() != null;
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
    result.append(" (mixed: ");
    result.append(mixed);
    result.append(')');
    return result.toString();
  }

} //DocumentRootImpl
