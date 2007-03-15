/**
 * <copyright>
 * </copyright>
 *
 * $Id: ResourcesTypeImpl.java,v 1.2 2007/03/01 09:15:18 emstamou Exp $
 */
package eu.geclipse.jsdl.impl;

import eu.geclipse.jsdl.CPUArchitectureType;
import eu.geclipse.jsdl.CandidateHostsType;
import eu.geclipse.jsdl.FileSystemType;
import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.OperatingSystemType;
import eu.geclipse.jsdl.RangeValueType;
import eu.geclipse.jsdl.ResourcesType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resources Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getCandidateHosts <em>Candidate Hosts</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getFileSystem <em>File System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#isExclusiveExecution <em>Exclusive Execution</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getOperatingSystem <em>Operating System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getCPUArchitecture <em>CPU Architecture</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualCPUSpeed <em>Individual CPU Speed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualCPUTime <em>Individual CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualCPUCount <em>Individual CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getIndividualDiskSpace <em>Individual Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getTotalCPUTime <em>Total CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getTotalCPUCount <em>Total CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getTotalPhysicalMemory <em>Total Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getTotalVirtualMemory <em>Total Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getTotalDiskSpace <em>Total Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getTotalResourceCount <em>Total Resource Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.impl.ResourcesTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourcesTypeImpl extends EObjectImpl implements ResourcesType 
{
  /**
   * The cached value of the '{@link #getCandidateHosts() <em>Candidate Hosts</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCandidateHosts()
   * @generated
   * @ordered
   */
	protected CandidateHostsType candidateHosts = null;

  /**
   * The cached value of the '{@link #getFileSystem() <em>File System</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFileSystem()
   * @generated
   * @ordered
   */
	protected EList fileSystem = null;

  /**
   * The default value of the '{@link #isExclusiveExecution() <em>Exclusive Execution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isExclusiveExecution()
   * @generated
   * @ordered
   */
	protected static final boolean EXCLUSIVE_EXECUTION_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExclusiveExecution() <em>Exclusive Execution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isExclusiveExecution()
   * @generated
   * @ordered
   */
	protected boolean exclusiveExecution = EXCLUSIVE_EXECUTION_EDEFAULT;

  /**
   * This is true if the Exclusive Execution attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean exclusiveExecutionESet = false;

  /**
   * The cached value of the '{@link #getOperatingSystem() <em>Operating System</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOperatingSystem()
   * @generated
   * @ordered
   */
	protected OperatingSystemType operatingSystem = null;

  /**
   * The cached value of the '{@link #getCPUArchitecture() <em>CPU Architecture</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCPUArchitecture()
   * @generated
   * @ordered
   */
	protected CPUArchitectureType cPUArchitecture = null;

  /**
   * The cached value of the '{@link #getIndividualCPUSpeed() <em>Individual CPU Speed</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualCPUSpeed()
   * @generated
   * @ordered
   */
	protected RangeValueType individualCPUSpeed = null;

  /**
   * The cached value of the '{@link #getIndividualCPUTime() <em>Individual CPU Time</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualCPUTime()
   * @generated
   * @ordered
   */
	protected RangeValueType individualCPUTime = null;

  /**
   * The cached value of the '{@link #getIndividualCPUCount() <em>Individual CPU Count</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualCPUCount()
   * @generated
   * @ordered
   */
	protected RangeValueType individualCPUCount = null;

  /**
   * The cached value of the '{@link #getIndividualNetworkBandwidth() <em>Individual Network Bandwidth</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualNetworkBandwidth()
   * @generated
   * @ordered
   */
	protected RangeValueType individualNetworkBandwidth = null;

  /**
   * The cached value of the '{@link #getIndividualPhysicalMemory() <em>Individual Physical Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualPhysicalMemory()
   * @generated
   * @ordered
   */
	protected RangeValueType individualPhysicalMemory = null;

  /**
   * The cached value of the '{@link #getIndividualVirtualMemory() <em>Individual Virtual Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualVirtualMemory()
   * @generated
   * @ordered
   */
	protected RangeValueType individualVirtualMemory = null;

  /**
   * The cached value of the '{@link #getIndividualDiskSpace() <em>Individual Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getIndividualDiskSpace()
   * @generated
   * @ordered
   */
	protected RangeValueType individualDiskSpace = null;

  /**
   * The cached value of the '{@link #getTotalCPUTime() <em>Total CPU Time</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalCPUTime()
   * @generated
   * @ordered
   */
	protected RangeValueType totalCPUTime = null;

  /**
   * The cached value of the '{@link #getTotalCPUCount() <em>Total CPU Count</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalCPUCount()
   * @generated
   * @ordered
   */
	protected RangeValueType totalCPUCount = null;

  /**
   * The cached value of the '{@link #getTotalPhysicalMemory() <em>Total Physical Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalPhysicalMemory()
   * @generated
   * @ordered
   */
	protected RangeValueType totalPhysicalMemory = null;

  /**
   * The cached value of the '{@link #getTotalVirtualMemory() <em>Total Virtual Memory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalVirtualMemory()
   * @generated
   * @ordered
   */
	protected RangeValueType totalVirtualMemory = null;

  /**
   * The cached value of the '{@link #getTotalDiskSpace() <em>Total Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalDiskSpace()
   * @generated
   * @ordered
   */
	protected RangeValueType totalDiskSpace = null;

  /**
   * The cached value of the '{@link #getTotalResourceCount() <em>Total Resource Count</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTotalResourceCount()
   * @generated
   * @ordered
   */
	protected RangeValueType totalResourceCount = null;

  /**
   * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getAny()
   * @generated
   * @ordered
   */
	protected FeatureMap any = null;

  /**
   * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getAnyAttribute()
   * @generated
   * @ordered
   */
	protected FeatureMap anyAttribute = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected ResourcesTypeImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected EClass eStaticClass()
  {
    return JsdlPackage.Literals.RESOURCES_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CandidateHostsType getCandidateHosts()
  {
    return candidateHosts;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCandidateHosts(CandidateHostsType newCandidateHosts, NotificationChain msgs)
  {
    CandidateHostsType oldCandidateHosts = candidateHosts;
    candidateHosts = newCandidateHosts;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS, oldCandidateHosts, newCandidateHosts);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCandidateHosts(CandidateHostsType newCandidateHosts)
  {
    if (newCandidateHosts != candidateHosts)
    {
      NotificationChain msgs = null;
      if (candidateHosts != null)
        msgs = ((InternalEObject)candidateHosts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS, null, msgs);
      if (newCandidateHosts != null)
        msgs = ((InternalEObject)newCandidateHosts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS, null, msgs);
      msgs = basicSetCandidateHosts(newCandidateHosts, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS, newCandidateHosts, newCandidateHosts));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EList getFileSystem()
  {
    if (fileSystem == null)
    {
      fileSystem = new EObjectContainmentEList(FileSystemType.class, this, JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM);
    }
    return fileSystem;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isExclusiveExecution()
  {
    return exclusiveExecution;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setExclusiveExecution(boolean newExclusiveExecution)
  {
    boolean oldExclusiveExecution = exclusiveExecution;
    exclusiveExecution = newExclusiveExecution;
    boolean oldExclusiveExecutionESet = exclusiveExecutionESet;
    exclusiveExecutionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION, oldExclusiveExecution, exclusiveExecution, !oldExclusiveExecutionESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void unsetExclusiveExecution()
  {
    boolean oldExclusiveExecution = exclusiveExecution;
    boolean oldExclusiveExecutionESet = exclusiveExecutionESet;
    exclusiveExecution = EXCLUSIVE_EXECUTION_EDEFAULT;
    exclusiveExecutionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION, oldExclusiveExecution, EXCLUSIVE_EXECUTION_EDEFAULT, oldExclusiveExecutionESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isSetExclusiveExecution()
  {
    return exclusiveExecutionESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemType getOperatingSystem()
  {
    return operatingSystem;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOperatingSystem(OperatingSystemType newOperatingSystem, NotificationChain msgs)
  {
    OperatingSystemType oldOperatingSystem = operatingSystem;
    operatingSystem = newOperatingSystem;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM, oldOperatingSystem, newOperatingSystem);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystem(OperatingSystemType newOperatingSystem)
  {
    if (newOperatingSystem != operatingSystem)
    {
      NotificationChain msgs = null;
      if (operatingSystem != null)
        msgs = ((InternalEObject)operatingSystem).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM, null, msgs);
      if (newOperatingSystem != null)
        msgs = ((InternalEObject)newOperatingSystem).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM, null, msgs);
      msgs = basicSetOperatingSystem(newOperatingSystem, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM, newOperatingSystem, newOperatingSystem));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CPUArchitectureType getCPUArchitecture()
  {
    return cPUArchitecture;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCPUArchitecture(CPUArchitectureType newCPUArchitecture, NotificationChain msgs)
  {
    CPUArchitectureType oldCPUArchitecture = cPUArchitecture;
    cPUArchitecture = newCPUArchitecture;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE, oldCPUArchitecture, newCPUArchitecture);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCPUArchitecture(CPUArchitectureType newCPUArchitecture)
  {
    if (newCPUArchitecture != cPUArchitecture)
    {
      NotificationChain msgs = null;
      if (cPUArchitecture != null)
        msgs = ((InternalEObject)cPUArchitecture).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE, null, msgs);
      if (newCPUArchitecture != null)
        msgs = ((InternalEObject)newCPUArchitecture).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE, null, msgs);
      msgs = basicSetCPUArchitecture(newCPUArchitecture, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE, newCPUArchitecture, newCPUArchitecture));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualCPUSpeed()
  {
    return individualCPUSpeed;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualCPUSpeed(RangeValueType newIndividualCPUSpeed, NotificationChain msgs)
  {
    RangeValueType oldIndividualCPUSpeed = individualCPUSpeed;
    individualCPUSpeed = newIndividualCPUSpeed;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED, oldIndividualCPUSpeed, newIndividualCPUSpeed);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualCPUSpeed(RangeValueType newIndividualCPUSpeed)
  {
    if (newIndividualCPUSpeed != individualCPUSpeed)
    {
      NotificationChain msgs = null;
      if (individualCPUSpeed != null)
        msgs = ((InternalEObject)individualCPUSpeed).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED, null, msgs);
      if (newIndividualCPUSpeed != null)
        msgs = ((InternalEObject)newIndividualCPUSpeed).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED, null, msgs);
      msgs = basicSetIndividualCPUSpeed(newIndividualCPUSpeed, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED, newIndividualCPUSpeed, newIndividualCPUSpeed));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualCPUTime()
  {
    return individualCPUTime;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualCPUTime(RangeValueType newIndividualCPUTime, NotificationChain msgs)
  {
    RangeValueType oldIndividualCPUTime = individualCPUTime;
    individualCPUTime = newIndividualCPUTime;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME, oldIndividualCPUTime, newIndividualCPUTime);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualCPUTime(RangeValueType newIndividualCPUTime)
  {
    if (newIndividualCPUTime != individualCPUTime)
    {
      NotificationChain msgs = null;
      if (individualCPUTime != null)
        msgs = ((InternalEObject)individualCPUTime).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME, null, msgs);
      if (newIndividualCPUTime != null)
        msgs = ((InternalEObject)newIndividualCPUTime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME, null, msgs);
      msgs = basicSetIndividualCPUTime(newIndividualCPUTime, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME, newIndividualCPUTime, newIndividualCPUTime));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualCPUCount()
  {
    return individualCPUCount;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualCPUCount(RangeValueType newIndividualCPUCount, NotificationChain msgs)
  {
    RangeValueType oldIndividualCPUCount = individualCPUCount;
    individualCPUCount = newIndividualCPUCount;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT, oldIndividualCPUCount, newIndividualCPUCount);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualCPUCount(RangeValueType newIndividualCPUCount)
  {
    if (newIndividualCPUCount != individualCPUCount)
    {
      NotificationChain msgs = null;
      if (individualCPUCount != null)
        msgs = ((InternalEObject)individualCPUCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT, null, msgs);
      if (newIndividualCPUCount != null)
        msgs = ((InternalEObject)newIndividualCPUCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT, null, msgs);
      msgs = basicSetIndividualCPUCount(newIndividualCPUCount, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT, newIndividualCPUCount, newIndividualCPUCount));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualNetworkBandwidth()
  {
    return individualNetworkBandwidth;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualNetworkBandwidth(RangeValueType newIndividualNetworkBandwidth, NotificationChain msgs)
  {
    RangeValueType oldIndividualNetworkBandwidth = individualNetworkBandwidth;
    individualNetworkBandwidth = newIndividualNetworkBandwidth;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH, oldIndividualNetworkBandwidth, newIndividualNetworkBandwidth);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualNetworkBandwidth(RangeValueType newIndividualNetworkBandwidth)
  {
    if (newIndividualNetworkBandwidth != individualNetworkBandwidth)
    {
      NotificationChain msgs = null;
      if (individualNetworkBandwidth != null)
        msgs = ((InternalEObject)individualNetworkBandwidth).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH, null, msgs);
      if (newIndividualNetworkBandwidth != null)
        msgs = ((InternalEObject)newIndividualNetworkBandwidth).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH, null, msgs);
      msgs = basicSetIndividualNetworkBandwidth(newIndividualNetworkBandwidth, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH, newIndividualNetworkBandwidth, newIndividualNetworkBandwidth));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualPhysicalMemory()
  {
    return individualPhysicalMemory;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualPhysicalMemory(RangeValueType newIndividualPhysicalMemory, NotificationChain msgs)
  {
    RangeValueType oldIndividualPhysicalMemory = individualPhysicalMemory;
    individualPhysicalMemory = newIndividualPhysicalMemory;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY, oldIndividualPhysicalMemory, newIndividualPhysicalMemory);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualPhysicalMemory(RangeValueType newIndividualPhysicalMemory)
  {
    if (newIndividualPhysicalMemory != individualPhysicalMemory)
    {
      NotificationChain msgs = null;
      if (individualPhysicalMemory != null)
        msgs = ((InternalEObject)individualPhysicalMemory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY, null, msgs);
      if (newIndividualPhysicalMemory != null)
        msgs = ((InternalEObject)newIndividualPhysicalMemory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY, null, msgs);
      msgs = basicSetIndividualPhysicalMemory(newIndividualPhysicalMemory, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY, newIndividualPhysicalMemory, newIndividualPhysicalMemory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualVirtualMemory()
  {
    return individualVirtualMemory;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualVirtualMemory(RangeValueType newIndividualVirtualMemory, NotificationChain msgs)
  {
    RangeValueType oldIndividualVirtualMemory = individualVirtualMemory;
    individualVirtualMemory = newIndividualVirtualMemory;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY, oldIndividualVirtualMemory, newIndividualVirtualMemory);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualVirtualMemory(RangeValueType newIndividualVirtualMemory)
  {
    if (newIndividualVirtualMemory != individualVirtualMemory)
    {
      NotificationChain msgs = null;
      if (individualVirtualMemory != null)
        msgs = ((InternalEObject)individualVirtualMemory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY, null, msgs);
      if (newIndividualVirtualMemory != null)
        msgs = ((InternalEObject)newIndividualVirtualMemory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY, null, msgs);
      msgs = basicSetIndividualVirtualMemory(newIndividualVirtualMemory, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY, newIndividualVirtualMemory, newIndividualVirtualMemory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualDiskSpace()
  {
    return individualDiskSpace;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualDiskSpace(RangeValueType newIndividualDiskSpace, NotificationChain msgs)
  {
    RangeValueType oldIndividualDiskSpace = individualDiskSpace;
    individualDiskSpace = newIndividualDiskSpace;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE, oldIndividualDiskSpace, newIndividualDiskSpace);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualDiskSpace(RangeValueType newIndividualDiskSpace)
  {
    if (newIndividualDiskSpace != individualDiskSpace)
    {
      NotificationChain msgs = null;
      if (individualDiskSpace != null)
        msgs = ((InternalEObject)individualDiskSpace).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE, null, msgs);
      if (newIndividualDiskSpace != null)
        msgs = ((InternalEObject)newIndividualDiskSpace).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE, null, msgs);
      msgs = basicSetIndividualDiskSpace(newIndividualDiskSpace, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE, newIndividualDiskSpace, newIndividualDiskSpace));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalCPUTime()
  {
    return totalCPUTime;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalCPUTime(RangeValueType newTotalCPUTime, NotificationChain msgs)
  {
    RangeValueType oldTotalCPUTime = totalCPUTime;
    totalCPUTime = newTotalCPUTime;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME, oldTotalCPUTime, newTotalCPUTime);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalCPUTime(RangeValueType newTotalCPUTime)
  {
    if (newTotalCPUTime != totalCPUTime)
    {
      NotificationChain msgs = null;
      if (totalCPUTime != null)
        msgs = ((InternalEObject)totalCPUTime).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME, null, msgs);
      if (newTotalCPUTime != null)
        msgs = ((InternalEObject)newTotalCPUTime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME, null, msgs);
      msgs = basicSetTotalCPUTime(newTotalCPUTime, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME, newTotalCPUTime, newTotalCPUTime));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalCPUCount()
  {
    return totalCPUCount;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalCPUCount(RangeValueType newTotalCPUCount, NotificationChain msgs)
  {
    RangeValueType oldTotalCPUCount = totalCPUCount;
    totalCPUCount = newTotalCPUCount;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT, oldTotalCPUCount, newTotalCPUCount);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalCPUCount(RangeValueType newTotalCPUCount)
  {
    if (newTotalCPUCount != totalCPUCount)
    {
      NotificationChain msgs = null;
      if (totalCPUCount != null)
        msgs = ((InternalEObject)totalCPUCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT, null, msgs);
      if (newTotalCPUCount != null)
        msgs = ((InternalEObject)newTotalCPUCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT, null, msgs);
      msgs = basicSetTotalCPUCount(newTotalCPUCount, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT, newTotalCPUCount, newTotalCPUCount));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalPhysicalMemory()
  {
    return totalPhysicalMemory;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalPhysicalMemory(RangeValueType newTotalPhysicalMemory, NotificationChain msgs)
  {
    RangeValueType oldTotalPhysicalMemory = totalPhysicalMemory;
    totalPhysicalMemory = newTotalPhysicalMemory;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY, oldTotalPhysicalMemory, newTotalPhysicalMemory);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalPhysicalMemory(RangeValueType newTotalPhysicalMemory)
  {
    if (newTotalPhysicalMemory != totalPhysicalMemory)
    {
      NotificationChain msgs = null;
      if (totalPhysicalMemory != null)
        msgs = ((InternalEObject)totalPhysicalMemory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY, null, msgs);
      if (newTotalPhysicalMemory != null)
        msgs = ((InternalEObject)newTotalPhysicalMemory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY, null, msgs);
      msgs = basicSetTotalPhysicalMemory(newTotalPhysicalMemory, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY, newTotalPhysicalMemory, newTotalPhysicalMemory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalVirtualMemory()
  {
    return totalVirtualMemory;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalVirtualMemory(RangeValueType newTotalVirtualMemory, NotificationChain msgs)
  {
    RangeValueType oldTotalVirtualMemory = totalVirtualMemory;
    totalVirtualMemory = newTotalVirtualMemory;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY, oldTotalVirtualMemory, newTotalVirtualMemory);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalVirtualMemory(RangeValueType newTotalVirtualMemory)
  {
    if (newTotalVirtualMemory != totalVirtualMemory)
    {
      NotificationChain msgs = null;
      if (totalVirtualMemory != null)
        msgs = ((InternalEObject)totalVirtualMemory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY, null, msgs);
      if (newTotalVirtualMemory != null)
        msgs = ((InternalEObject)newTotalVirtualMemory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY, null, msgs);
      msgs = basicSetTotalVirtualMemory(newTotalVirtualMemory, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY, newTotalVirtualMemory, newTotalVirtualMemory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalDiskSpace()
  {
    return totalDiskSpace;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalDiskSpace(RangeValueType newTotalDiskSpace, NotificationChain msgs)
  {
    RangeValueType oldTotalDiskSpace = totalDiskSpace;
    totalDiskSpace = newTotalDiskSpace;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE, oldTotalDiskSpace, newTotalDiskSpace);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalDiskSpace(RangeValueType newTotalDiskSpace)
  {
    if (newTotalDiskSpace != totalDiskSpace)
    {
      NotificationChain msgs = null;
      if (totalDiskSpace != null)
        msgs = ((InternalEObject)totalDiskSpace).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE, null, msgs);
      if (newTotalDiskSpace != null)
        msgs = ((InternalEObject)newTotalDiskSpace).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE, null, msgs);
      msgs = basicSetTotalDiskSpace(newTotalDiskSpace, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE, newTotalDiskSpace, newTotalDiskSpace));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalResourceCount()
  {
    return totalResourceCount;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalResourceCount(RangeValueType newTotalResourceCount, NotificationChain msgs)
  {
    RangeValueType oldTotalResourceCount = totalResourceCount;
    totalResourceCount = newTotalResourceCount;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT, oldTotalResourceCount, newTotalResourceCount);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalResourceCount(RangeValueType newTotalResourceCount)
  {
    if (newTotalResourceCount != totalResourceCount)
    {
      NotificationChain msgs = null;
      if (totalResourceCount != null)
        msgs = ((InternalEObject)totalResourceCount).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT, null, msgs);
      if (newTotalResourceCount != null)
        msgs = ((InternalEObject)newTotalResourceCount).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT, null, msgs);
      msgs = basicSetTotalResourceCount(newTotalResourceCount, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT, newTotalResourceCount, newTotalResourceCount));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FeatureMap getAny()
  {
    if (any == null)
    {
      any = new BasicFeatureMap(this, JsdlPackage.RESOURCES_TYPE__ANY);
    }
    return any;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FeatureMap getAnyAttribute()
  {
    if (anyAttribute == null)
    {
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE);
    }
    return anyAttribute;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS:
        return basicSetCandidateHosts(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM:
        return ((InternalEList)getFileSystem()).basicRemove(otherEnd, msgs);
      case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM:
        return basicSetOperatingSystem(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:
        return basicSetCPUArchitecture(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED:
        return basicSetIndividualCPUSpeed(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME:
        return basicSetIndividualCPUTime(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT:
        return basicSetIndividualCPUCount(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH:
        return basicSetIndividualNetworkBandwidth(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY:
        return basicSetIndividualPhysicalMemory(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY:
        return basicSetIndividualVirtualMemory(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE:
        return basicSetIndividualDiskSpace(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME:
        return basicSetTotalCPUTime(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT:
        return basicSetTotalCPUCount(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY:
        return basicSetTotalPhysicalMemory(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY:
        return basicSetTotalVirtualMemory(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE:
        return basicSetTotalDiskSpace(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT:
        return basicSetTotalResourceCount(null, msgs);
      case JsdlPackage.RESOURCES_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE:
        return ((InternalEList)getAnyAttribute()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS:
        return getCandidateHosts();
      case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM:
        return getFileSystem();
      case JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION:
        return isExclusiveExecution() ? Boolean.TRUE : Boolean.FALSE;
      case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM:
        return getOperatingSystem();
      case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:
        return getCPUArchitecture();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED:
        return getIndividualCPUSpeed();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME:
        return getIndividualCPUTime();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT:
        return getIndividualCPUCount();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH:
        return getIndividualNetworkBandwidth();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY:
        return getIndividualPhysicalMemory();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY:
        return getIndividualVirtualMemory();
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE:
        return getIndividualDiskSpace();
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME:
        return getTotalCPUTime();
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT:
        return getTotalCPUCount();
      case JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY:
        return getTotalPhysicalMemory();
      case JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY:
        return getTotalVirtualMemory();
      case JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE:
        return getTotalDiskSpace();
      case JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT:
        return getTotalResourceCount();
      case JsdlPackage.RESOURCES_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE:
        if (coreType) return getAnyAttribute();
        return ((FeatureMap.Internal)getAnyAttribute()).getWrapper();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS:
        setCandidateHosts((CandidateHostsType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM:
        getFileSystem().clear();
        getFileSystem().addAll((Collection)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION:
        setExclusiveExecution(((Boolean)newValue).booleanValue());
        return;
      case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM:
        setOperatingSystem((OperatingSystemType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:
        setCPUArchitecture((CPUArchitectureType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED:
        setIndividualCPUSpeed((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME:
        setIndividualCPUTime((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT:
        setIndividualCPUCount((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH:
        setIndividualNetworkBandwidth((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY:
        setIndividualPhysicalMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY:
        setIndividualVirtualMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE:
        setIndividualDiskSpace((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME:
        setTotalCPUTime((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT:
        setTotalCPUCount((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY:
        setTotalPhysicalMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY:
        setTotalVirtualMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE:
        setTotalDiskSpace((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT:
        setTotalResourceCount((RangeValueType)newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE:
        ((FeatureMap.Internal)getAnyAttribute()).set(newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS:
        setCandidateHosts((CandidateHostsType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM:
        getFileSystem().clear();
        return;
      case JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION:
        unsetExclusiveExecution();
        return;
      case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM:
        setOperatingSystem((OperatingSystemType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:
        setCPUArchitecture((CPUArchitectureType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED:
        setIndividualCPUSpeed((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME:
        setIndividualCPUTime((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT:
        setIndividualCPUCount((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH:
        setIndividualNetworkBandwidth((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY:
        setIndividualPhysicalMemory((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY:
        setIndividualVirtualMemory((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE:
        setIndividualDiskSpace((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME:
        setTotalCPUTime((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT:
        setTotalCPUCount((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY:
        setTotalPhysicalMemory((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY:
        setTotalVirtualMemory((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE:
        setTotalDiskSpace((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT:
        setTotalResourceCount((RangeValueType)null);
        return;
      case JsdlPackage.RESOURCES_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE:
        getAnyAttribute().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS:
        return candidateHosts != null;
      case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM:
        return fileSystem != null && !fileSystem.isEmpty();
      case JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION:
        return isSetExclusiveExecution();
      case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM:
        return operatingSystem != null;
      case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:
        return cPUArchitecture != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED:
        return individualCPUSpeed != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME:
        return individualCPUTime != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT:
        return individualCPUCount != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH:
        return individualNetworkBandwidth != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY:
        return individualPhysicalMemory != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY:
        return individualVirtualMemory != null;
      case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE:
        return individualDiskSpace != null;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME:
        return totalCPUTime != null;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT:
        return totalCPUCount != null;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY:
        return totalPhysicalMemory != null;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY:
        return totalVirtualMemory != null;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE:
        return totalDiskSpace != null;
      case JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT:
        return totalResourceCount != null;
      case JsdlPackage.RESOURCES_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE:
        return anyAttribute != null && !anyAttribute.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (exclusiveExecution: ");
    if (exclusiveExecutionESet) result.append(exclusiveExecution); else result.append("<unset>");
    result.append(", any: ");
    result.append(any);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //ResourcesTypeImpl