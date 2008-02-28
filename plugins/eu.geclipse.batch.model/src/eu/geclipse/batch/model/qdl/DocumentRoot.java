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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.model.qdl;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getAssignedResources <em>Assigned Resources</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getJobsInQueue <em>Jobs In Queue</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getPriority <em>Priority</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueue <em>Queue</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#isQueueStarted <em>Queue Started</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueueStatus <em>Queue Status</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueueType <em>Queue Type</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getRunningJobs <em>Running Jobs</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getVOName <em>VO Name</em>}</li>
 *   <li>{@link eu.geclipse.batch.model.qdl.DocumentRoot#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject
{
  /**
   * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mixed</em>' attribute list.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_Mixed()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' name=':mixed'"
   * @generated
   */
  FeatureMap getMixed();

  /**
   * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>XMLNS Prefix Map</em>' map.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_XMLNSPrefixMap()
   * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
   *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
   * @generated
   */
  EMap<String, String> getXMLNSPrefixMap();

  /**
   * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
   * The key is of type {@link java.lang.String},
   * and the value is of type {@link java.lang.String},
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>XSI Schema Location</em>' map.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_XSISchemaLocation()
   * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
   *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
   * @generated
   */
  EMap<String, String> getXSISchemaLocation();

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_AllowedVirtualOrganizations()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='AllowedVirtualOrganizations' namespace='##targetNamespace'"
   * @generated
   */
  AllowedVirtualOrganizationsType getAllowedVirtualOrganizations();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getAllowedVirtualOrganizations <em>Allowed Virtual Organizations</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Allowed Virtual Organizations</em>' containment reference.
   * @see #getAllowedVirtualOrganizations()
   * @generated
   */
  void setAllowedVirtualOrganizations(AllowedVirtualOrganizationsType value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_AssignedResources()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='AssignedResources' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getAssignedResources();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getAssignedResources <em>Assigned Resources</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Assigned Resources</em>' containment reference.
   * @see #getAssignedResources()
   * @generated
   */
  void setAssignedResources(IntegerRangeValueType value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_CPUTimeLimit()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='CPUTimeLimit' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getCPUTimeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getCPUTimeLimit <em>CPU Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Time Limit</em>' containment reference.
   * @see #getCPUTimeLimit()
   * @generated
   */
  void setCPUTimeLimit(RangeValueType value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_Description()
   * @model unique="false" dataType="eu.geclipse.batch.model.qdl.DescriptionType" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Description' namespace='##targetNamespace'"
   * @generated
   */
  String getDescription();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getDescription <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Description</em>' attribute.
   * @see #getDescription()
   * @generated
   */
  void setDescription(String value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_JobsInQueue()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='JobsInQueue' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getJobsInQueue();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getJobsInQueue <em>Jobs In Queue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Jobs In Queue</em>' containment reference.
   * @see #getJobsInQueue()
   * @generated
   */
  void setJobsInQueue(IntegerRangeValueType value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_Priority()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Priority' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getPriority();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getPriority <em>Priority</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Priority</em>' containment reference.
   * @see #getPriority()
   * @generated
   */
  void setPriority(IntegerRangeValueType value);

  /**
   * Returns the value of the '<em><b>Queue</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Queue</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Queue</em>' containment reference.
   * @see #setQueue(QueueType)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_Queue()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Queue' namespace='##targetNamespace'"
   * @generated
   */
  QueueType getQueue();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueue <em>Queue</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue</em>' containment reference.
   * @see #getQueue()
   * @generated
   */
  void setQueue(QueueType value);

  /**
   * Returns the value of the '<em><b>Queue Started</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Queue Started</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Queue Started</em>' attribute.
   * @see #setQueueStarted(boolean)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_QueueStarted()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='QueueStarted' namespace='##targetNamespace'"
   * @generated
   */
  boolean isQueueStarted();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#isQueueStarted <em>Queue Started</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Started</em>' attribute.
   * @see #isQueueStarted()
   * @generated
   */
  void setQueueStarted(boolean value);

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
   * @see #setQueueStatus(QueueStatusEnumeration)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_QueueStatus()
   * @model default="Enabled" unique="false" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='QueueStatus' namespace='##targetNamespace'"
   * @generated
   */
  QueueStatusEnumeration getQueueStatus();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueueStatus <em>Queue Status</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Status</em>' attribute.
   * @see eu.geclipse.batch.model.qdl.QueueStatusEnumeration
   * @see #getQueueStatus()
   * @generated
   */
  void setQueueStatus(QueueStatusEnumeration value);

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
   * @see #setQueueType(QueueTypeEnumeration)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_QueueType()
   * @model default="Execution" unique="false" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='QueueType' namespace='##targetNamespace'"
   * @generated
   */
  QueueTypeEnumeration getQueueType();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getQueueType <em>Queue Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Queue Type</em>' attribute.
   * @see eu.geclipse.batch.model.qdl.QueueTypeEnumeration
   * @see #getQueueType()
   * @generated
   */
  void setQueueType(QueueTypeEnumeration value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_RunningJobs()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='RunningJobs' namespace='##targetNamespace'"
   * @generated
   */
  IntegerRangeValueType getRunningJobs();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getRunningJobs <em>Running Jobs</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Running Jobs</em>' containment reference.
   * @see #getRunningJobs()
   * @generated
   */
  void setRunningJobs(IntegerRangeValueType value);

  /**
   * Returns the value of the '<em><b>VO Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>VO Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>VO Name</em>' attribute.
   * @see #setVOName(String)
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_VOName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='VOName' namespace='##targetNamespace'"
   * @generated
   */
  String getVOName();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getVOName <em>VO Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>VO Name</em>' attribute.
   * @see #getVOName()
   * @generated
   */
  void setVOName(String value);

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
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getDocumentRoot_WallTimeLimit()
   * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='WallTimeLimit' namespace='##targetNamespace'"
   * @generated
   */
  RangeValueType getWallTimeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.batch.model.qdl.DocumentRoot#getWallTimeLimit <em>Wall Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Wall Time Limit</em>' containment reference.
   * @see #getWallTimeLimit()
   * @generated
   */
  void setWallTimeLimit(RangeValueType value);

} // DocumentRoot
