/**
 * <copyright>
 * </copyright>
 *
 * $Id: ResourcesType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resources Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getCandidateHosts <em>Candidate Hosts</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getFileSystem <em>File System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#isExclusiveExecution <em>Exclusive Execution</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getOperatingSystem <em>Operating System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getCPUArchitecture <em>CPU Architecture</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualCPUSpeed <em>Individual CPU Speed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualCPUTime <em>Individual CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualCPUCount <em>Individual CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualDiskSpace <em>Individual Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getTotalCPUTime <em>Total CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getTotalCPUCount <em>Total CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getTotalPhysicalMemory <em>Total Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getTotalVirtualMemory <em>Total Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getTotalDiskSpace <em>Total Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getTotalResourceCount <em>Total Resource Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.ResourcesType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType()
 * @model extendedMetaData="name='Resources_Type' kind='elementOnly'"
 * @generated
 * @deprecated This interface is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base
 */
public interface ResourcesType extends EObject
{
  /**
   * Returns the value of the '<em><b>Candidate Hosts</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Candidate Hosts</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Candidate Hosts</em>' containment reference.
   * @see #setCandidateHosts(CandidateHostsType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_CandidateHosts()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='CandidateHosts' namespace='##targetNamespace'"
   * @generated
   */
	CandidateHostsType getCandidateHosts();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getCandidateHosts <em>Candidate Hosts</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Candidate Hosts</em>' containment reference.
   * @see #getCandidateHosts()
   * @generated
   */
	void setCandidateHosts(CandidateHostsType value);

  /**
   * Returns the value of the '<em><b>File System</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.FileSystemType}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File System</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>File System</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_FileSystem()
   * @model type="eu.geclipse.jsdl.model.FileSystemType" containment="true"
   *        extendedMetaData="kind='element' name='FileSystem' namespace='##targetNamespace'"
   * @generated
   */
	EList getFileSystem();

  /**
   * Returns the value of the '<em><b>Exclusive Execution</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclusive Execution</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Exclusive Execution</em>' attribute.
   * @see #isSetExclusiveExecution()
   * @see #unsetExclusiveExecution()
   * @see #setExclusiveExecution(boolean)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_ExclusiveExecution()
   * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
   *        extendedMetaData="kind='element' name='ExclusiveExecution' namespace='##targetNamespace'"
   * @generated
   */
	boolean isExclusiveExecution();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#isExclusiveExecution <em>Exclusive Execution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Exclusive Execution</em>' attribute.
   * @see #isSetExclusiveExecution()
   * @see #unsetExclusiveExecution()
   * @see #isExclusiveExecution()
   * @generated
   */
	void setExclusiveExecution(boolean value);

  /**
   * Unsets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#isExclusiveExecution <em>Exclusive Execution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetExclusiveExecution()
   * @see #isExclusiveExecution()
   * @see #setExclusiveExecution(boolean)
   * @generated
   */
	void unsetExclusiveExecution();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#isExclusiveExecution <em>Exclusive Execution</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>Exclusive Execution</em>' attribute is set.
   * @see #unsetExclusiveExecution()
   * @see #isExclusiveExecution()
   * @see #setExclusiveExecution(boolean)
   * @generated
   */
	boolean isSetExclusiveExecution();

  /**
   * Returns the value of the '<em><b>Operating System</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operating System</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Operating System</em>' containment reference.
   * @see #setOperatingSystem(OperatingSystemType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_OperatingSystem()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='OperatingSystem' namespace='##targetNamespace'"
   * @generated
   */
	OperatingSystemType getOperatingSystem();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getOperatingSystem <em>Operating System</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Operating System</em>' containment reference.
   * @see #getOperatingSystem()
   * @generated
   */
	void setOperatingSystem(OperatingSystemType value);

  /**
   * Returns the value of the '<em><b>CPU Architecture</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CPU Architecture</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>CPU Architecture</em>' containment reference.
   * @see #setCPUArchitecture(CPUArchitectureType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_CPUArchitecture()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='CPUArchitecture' namespace='##targetNamespace'"
   * @generated
   */
	CPUArchitectureType getCPUArchitecture();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getCPUArchitecture <em>CPU Architecture</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Architecture</em>' containment reference.
   * @see #getCPUArchitecture()
   * @generated
   */
	void setCPUArchitecture(CPUArchitectureType value);

  /**
   * Returns the value of the '<em><b>Individual CPU Speed</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual CPU Speed</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual CPU Speed</em>' containment reference.
   * @see #setIndividualCPUSpeed(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualCPUSpeed()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualCPUSpeed' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualCPUSpeed();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualCPUSpeed <em>Individual CPU Speed</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual CPU Speed</em>' containment reference.
   * @see #getIndividualCPUSpeed()
   * @generated
   */
	void setIndividualCPUSpeed(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual CPU Time</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual CPU Time</em>' containment reference.
   * @see #setIndividualCPUTime(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualCPUTime()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualCPUTime' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualCPUTime();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualCPUTime <em>Individual CPU Time</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual CPU Time</em>' containment reference.
   * @see #getIndividualCPUTime()
   * @generated
   */
	void setIndividualCPUTime(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual CPU Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual CPU Count</em>' containment reference.
   * @see #setIndividualCPUCount(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualCPUCount()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualCPUCount' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualCPUCount();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualCPUCount <em>Individual CPU Count</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual CPU Count</em>' containment reference.
   * @see #getIndividualCPUCount()
   * @generated
   */
	void setIndividualCPUCount(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Network Bandwidth</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual Network Bandwidth</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Network Bandwidth</em>' containment reference.
   * @see #setIndividualNetworkBandwidth(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualNetworkBandwidth()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualNetworkBandwidth' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualNetworkBandwidth();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Network Bandwidth</em>' containment reference.
   * @see #getIndividualNetworkBandwidth()
   * @generated
   */
	void setIndividualNetworkBandwidth(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual Physical Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Physical Memory</em>' containment reference.
   * @see #setIndividualPhysicalMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualPhysicalMemory()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualPhysicalMemory' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualPhysicalMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Physical Memory</em>' containment reference.
   * @see #getIndividualPhysicalMemory()
   * @generated
   */
	void setIndividualPhysicalMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual Virtual Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Virtual Memory</em>' containment reference.
   * @see #setIndividualVirtualMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualVirtualMemory()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualVirtualMemory' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualVirtualMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Virtual Memory</em>' containment reference.
   * @see #getIndividualVirtualMemory()
   * @generated
   */
	void setIndividualVirtualMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Individual Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Individual Disk Space</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Individual Disk Space</em>' containment reference.
   * @see #setIndividualDiskSpace(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_IndividualDiskSpace()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='IndividualDiskSpace' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getIndividualDiskSpace();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getIndividualDiskSpace <em>Individual Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Individual Disk Space</em>' containment reference.
   * @see #getIndividualDiskSpace()
   * @generated
   */
	void setIndividualDiskSpace(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total CPU Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total CPU Time</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total CPU Time</em>' containment reference.
   * @see #setTotalCPUTime(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_TotalCPUTime()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='TotalCPUTime' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getTotalCPUTime();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getTotalCPUTime <em>Total CPU Time</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total CPU Time</em>' containment reference.
   * @see #getTotalCPUTime()
   * @generated
   */
	void setTotalCPUTime(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total CPU Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total CPU Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total CPU Count</em>' containment reference.
   * @see #setTotalCPUCount(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_TotalCPUCount()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='TotalCPUCount' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getTotalCPUCount();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getTotalCPUCount <em>Total CPU Count</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total CPU Count</em>' containment reference.
   * @see #getTotalCPUCount()
   * @generated
   */
	void setTotalCPUCount(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Physical Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Physical Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total Physical Memory</em>' containment reference.
   * @see #setTotalPhysicalMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_TotalPhysicalMemory()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='TotalPhysicalMemory' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getTotalPhysicalMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getTotalPhysicalMemory <em>Total Physical Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Physical Memory</em>' containment reference.
   * @see #getTotalPhysicalMemory()
   * @generated
   */
	void setTotalPhysicalMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Virtual Memory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Virtual Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total Virtual Memory</em>' containment reference.
   * @see #setTotalVirtualMemory(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_TotalVirtualMemory()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='TotalVirtualMemory' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getTotalVirtualMemory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getTotalVirtualMemory <em>Total Virtual Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Virtual Memory</em>' containment reference.
   * @see #getTotalVirtualMemory()
   * @generated
   */
	void setTotalVirtualMemory(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Disk Space</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Disk Space</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total Disk Space</em>' containment reference.
   * @see #setTotalDiskSpace(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_TotalDiskSpace()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='TotalDiskSpace' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getTotalDiskSpace();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getTotalDiskSpace <em>Total Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Disk Space</em>' containment reference.
   * @see #getTotalDiskSpace()
   * @generated
   */
	void setTotalDiskSpace(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Total Resource Count</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Resource Count</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Total Resource Count</em>' containment reference.
   * @see #setTotalResourceCount(RangeValueType)
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_TotalResourceCount()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='TotalResourceCount' namespace='##targetNamespace'"
   * @generated
   */
	RangeValueType getTotalResourceCount();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.ResourcesType#getTotalResourceCount <em>Total Resource Count</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Total Resource Count</em>' containment reference.
   * @see #getTotalResourceCount()
   * @generated
   */
	void setTotalResourceCount(RangeValueType value);

  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':18' processing='lax'"
   * @generated
   */
	FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Any Attribute</em>' attribute list.
   * @see eu.geclipse.jsdl.model.JsdlPackage#getResourcesType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':19' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // ResourcesType