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
package eu.geclipse.batch.model.qdl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Queue Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getQueueName <em>Queue Name</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getQueueType <em>Queue Type</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getQueueStatus <em>Queue Status</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#isQueueStarted <em>Queue Started</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getPriority <em>Priority</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getRunningJobs <em>Running Jobs</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getJobsInQueue <em>Jobs In Queue</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.QueueType#getAssignedResources <em>Assigned Resources</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType()
 * @model extendedMetaData="name='Queue_Type' kind='elementOnly'"
 * @generated
 */
public interface QueueType extends EObject
{
  /**
   * Returns the value of the '<em><b>Queue Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Queue Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Queue Name</em>' attribute.
   * @see #setQueueName(String)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_QueueName()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='element' name='QueueName' namespace='##targetNamespace'"
   * @generated
   */
  String getQueueName();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueName <em>Queue Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Name</em>' attribute.
   * @see #getQueueName()
   * @generated
   */
  void setQueueName(String value);

  /**
   * Returns the value of the '<em><b>Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Description</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Description</em>' attribute.
   * @see #setDescription(String)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_Description()
   * @model dataType="eu.geclipse.batch.model.qdl.DescriptionType" required="true"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

  /**
   * Returns the value of the '<em><b>Queue Type</b></em>' attribute.
   * The default value is <code>"Execution"</code>.
   * The literals are from the enumeration {@link eu.geclipse.batch.model.qdl.QueueTypeEnumeration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Queue Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Queue Type</em>' attribute.
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @see #isSetQueueType()
   * @see #unsetQueueType()
   * @see #setQueueType(QueueTypeEnumeration)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_QueueType()
   * @model default="Execution" unsettable="true" required="true"
   *        extendedMetaData="kind='element' name='QueueType' namespace='##targetNamespace'"
   * @generated
   */
  QueueTypeEnumeration getQueueType();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueType <em>Queue Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Type</em>' attribute.
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @see #isSetQueueType()
   * @see #unsetQueueType()
   * @see #getQueueType()
   * @generated
   */
  void setQueueType(QueueTypeEnumeration value);

  /**
   * Unsets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueType <em>Queue Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetQueueType()
   * @see #getQueueType()
   * @see #setQueueType(QueueTypeEnumeration)
   * @generated
   */
  void unsetQueueType();

  /**
   * Returns whether the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueType <em>Queue Type</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Queue Type</em>' attribute is set.
   * @see #unsetQueueType()
   * @see #getQueueType()
   * @see #setQueueType(QueueTypeEnumeration)
   * @generated
   */
  boolean isSetQueueType();

  /**
   * Returns the value of the '<em><b>Queue Status</b></em>' attribute.
   * The default value is <code>"Enabled"</code>.
   * The literals are from the enumeration {@link eu.geclipse.batch.model.qdl.QueueStatusEnumeration}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Queue Status</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Queue Status</em>' attribute.
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @see #isSetQueueStatus()
   * @see #unsetQueueStatus()
   * @see #setQueueStatus(QueueStatusEnumeration)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_QueueStatus()
   * @model default="Enabled" unsettable="true" required="true"
   *        extendedMetaData="kind='element' name='QueueStatus' namespace='##targetNamespace'"
   * @generated
   */
  QueueStatusEnumeration getQueueStatus();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueStatus <em>Queue Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Status</em>' attribute.
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @see #isSetQueueStatus()
   * @see #unsetQueueStatus()
   * @see #getQueueStatus()
   * @generated
   */
  void setQueueStatus(QueueStatusEnumeration value);

  /**
   * Unsets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueStatus <em>Queue Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetQueueStatus()
   * @see #getQueueStatus()
   * @see #setQueueStatus(QueueStatusEnumeration)
   * @generated
   */
  void unsetQueueStatus();

  /**
   * Returns whether the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getQueueStatus <em>Queue Status</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Queue Status</em>' attribute is set.
   * @see #unsetQueueStatus()
   * @see #getQueueStatus()
   * @see #setQueueStatus(QueueStatusEnumeration)
   * @generated
   */
  boolean isSetQueueStatus();

  /**
   * Returns the value of the '<em><b>Queue Started</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Queue Started</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Queue Started</em>' attribute.
   * @see #isSetQueueStarted()
   * @see #unsetQueueStarted()
   * @see #setQueueStarted(boolean)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_QueueStarted()
   * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
   *        extendedMetaData="kind='element' name='QueueStarted' namespace='##targetNamespace'"
   * @generated
   */
  boolean isQueueStarted();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#isQueueStarted <em>Queue Started</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Started</em>' attribute.
   * @see #isSetQueueStarted()
   * @see #unsetQueueStarted()
   * @see #isQueueStarted()
   * @generated
   */
  void setQueueStarted(boolean value);

  /**
   * Unsets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#isQueueStarted <em>Queue Started</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetQueueStarted()
   * @see #isQueueStarted()
   * @see #setQueueStarted(boolean)
   * @generated
   */
  void unsetQueueStarted();

  /**
   * Returns whether the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#isQueueStarted <em>Queue Started</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Queue Started</em>' attribute is set.
   * @see #unsetQueueStarted()
   * @see #isQueueStarted()
   * @see #setQueueStarted(boolean)
   * @generated
   */
  boolean isSetQueueStarted();

  /**
   * Returns the value of the '<em><b>CPU Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>CPU Time Limit</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>CPU Time Limit</em>' containment reference.
   * @see #setCPUTimeLimit(RangeValueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_CPUTimeLimit()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='CPUTimeLimit' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getCPUTimeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getCPUTimeLimit <em>CPU Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Time Limit</em>' containment reference.
   * @see #getCPUTimeLimit()
   * @generated
   */
  void setCPUTimeLimit(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Wall Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Wall Time Limit</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Wall Time Limit</em>' containment reference.
   * @see #setWallTimeLimit(RangeValueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_WallTimeLimit()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='WallTimeLimit' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getWallTimeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getWallTimeLimit <em>Wall Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Wall Time Limit</em>' containment reference.
   * @see #getWallTimeLimit()
   * @generated
   */
  void setWallTimeLimit(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Allowed Virtual Organizations</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Allowed Virtual Organizations</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Allowed Virtual Organizations</em>' containment reference.
   * @see #setAllowedVirtualOrganizations(AllowedVirtualOrganizationsType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_AllowedVirtualOrganizations()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='AllowedVirtualOrganizations' namespace='##targetNamespace'"
   * @generated
   */
  AllowedVirtualOrganizationsType getAllowedVirtualOrganizations();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Allowed Virtual Organizations</em>' containment reference.
   * @see #getAllowedVirtualOrganizations()
   * @generated
   */
  void setAllowedVirtualOrganizations(AllowedVirtualOrganizationsType value);

  /**
   * Returns the value of the '<em><b>Priority</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Priority</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Priority</em>' containment reference.
   * @see #setPriority(IntegerRangeValueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_Priority()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='Priority' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getPriority();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getPriority <em>Priority</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Priority</em>' containment reference.
   * @see #getPriority()
   * @generated
   */
  void setPriority(IntegerRangeValueType value);

  /**
   * Returns the value of the '<em><b>Running Jobs</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Running Jobs</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Running Jobs</em>' containment reference.
   * @see #setRunningJobs(IntegerRangeValueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_RunningJobs()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='RunningJobs' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getRunningJobs();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getRunningJobs <em>Running Jobs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Running Jobs</em>' containment reference.
   * @see #getRunningJobs()
   * @generated
   */
  void setRunningJobs(IntegerRangeValueType value);

  /**
   * Returns the value of the '<em><b>Jobs In Queue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Jobs In Queue</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Jobs In Queue</em>' containment reference.
   * @see #setJobsInQueue(IntegerRangeValueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_JobsInQueue()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='JobsInQueue' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getJobsInQueue();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getJobsInQueue <em>Jobs In Queue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Jobs In Queue</em>' containment reference.
   * @see #getJobsInQueue()
   * @generated
   */
  void setJobsInQueue(IntegerRangeValueType value);

  /**
   * Returns the value of the '<em><b>Assigned Resources</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assigned Resources</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assigned Resources</em>' containment reference.
   * @see #setAssignedResources(IntegerRangeValueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueType_AssignedResources()
   * @model containment="true" required="true"
   *        extendedMetaData="kind='element' name='AssignedResources' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getAssignedResources();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.QueueType#getAssignedResources <em>Assigned Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assigned Resources</em>' containment reference.
   * @see #getAssignedResources()
   * @generated
   */
  void setAssignedResources(IntegerRangeValueType value);

} // QueueType
