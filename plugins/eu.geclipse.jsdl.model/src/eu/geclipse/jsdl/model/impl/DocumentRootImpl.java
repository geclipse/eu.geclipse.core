/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *    Mathias St�mpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.impl;

import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.CPUArchitectureType;
import eu.geclipse.jsdl.model.CandidateHostsType;
import eu.geclipse.jsdl.model.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.DocumentRoot;
import eu.geclipse.jsdl.model.FileSystemType;
import eu.geclipse.jsdl.model.FileSystemTypeEnumeration;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JobIdentificationType;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.OperatingSystemType;
import eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.ProcessorArchitectureEnumeration;
import eu.geclipse.jsdl.model.RangeValueType;
import eu.geclipse.jsdl.model.ResourcesType;
import eu.geclipse.jsdl.model.SourceTargetType;

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
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getApplication <em>Application</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getApplicationName <em>Application Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getApplicationVersion <em>Application Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getCandidateHosts <em>Candidate Hosts</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getCPUArchitecture <em>CPU Architecture</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getCPUArchitectureName <em>CPU Architecture Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getCreationFlag <em>Creation Flag</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getDataStaging <em>Data Staging</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#isDeleteOnTermination <em>Delete On Termination</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getDiskSpace <em>Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#isExclusiveExecution <em>Exclusive Execution</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getFileSystem <em>File System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getFilesystemName <em>Filesystem Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getFileSystemType <em>File System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getHostName <em>Host Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualCPUCount <em>Individual CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualCPUSpeed <em>Individual CPU Speed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualCPUTime <em>Individual CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualDiskSpace <em>Individual Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualNetworkBandwidth <em>Individual Network Bandwidth</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualPhysicalMemory <em>Individual Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getIndividualVirtualMemory <em>Individual Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getJobAnnotation <em>Job Annotation</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getJobDefinition <em>Job Definition</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getJobDescription <em>Job Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getJobIdentification <em>Job Identification</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getJobName <em>Job Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getJobProject <em>Job Project</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getMountPoint <em>Mount Point</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getOperatingSystem <em>Operating System</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getOperatingSystemName <em>Operating System Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getOperatingSystemType <em>Operating System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getOperatingSystemVersion <em>Operating System Version</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getSource <em>Source</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTotalCPUCount <em>Total CPU Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTotalCPUTime <em>Total CPU Time</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTotalDiskSpace <em>Total Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTotalPhysicalMemory <em>Total Physical Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTotalResourceCount <em>Total Resource Count</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getTotalVirtualMemory <em>Total Virtual Memory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DocumentRootImpl#getURI <em>URI</em>}</li>
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
	protected EMap xMLNSPrefixMap;

  /**
   * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getXSISchemaLocation()
   * @generated
   * @ordered
   */
	protected EMap xSISchemaLocation;

  /**
   * The default value of the '{@link #getApplicationName() <em>Application Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getApplicationName()
   * @generated
   * @ordered
   */
	protected static final String APPLICATION_NAME_EDEFAULT = null;

  /**
   * The default value of the '{@link #getApplicationVersion() <em>Application Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getApplicationVersion()
   * @generated
   * @ordered
   */
	protected static final String APPLICATION_VERSION_EDEFAULT = null;

  /**
   * The default value of the '{@link #getCPUArchitectureName() <em>CPU Architecture Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCPUArchitectureName()
   * @generated
   * @ordered
   */
	protected static final ProcessorArchitectureEnumeration CPU_ARCHITECTURE_NAME_EDEFAULT = ProcessorArchitectureEnumeration.SPARC_LITERAL;

  /**
   * The default value of the '{@link #getCreationFlag() <em>Creation Flag</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCreationFlag()
   * @generated
   * @ordered
   */
	protected static final CreationFlagEnumeration CREATION_FLAG_EDEFAULT = CreationFlagEnumeration.OVERWRITE_LITERAL;

  /**
   * The default value of the '{@link #isDeleteOnTermination() <em>Delete On Termination</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isDeleteOnTermination()
   * @generated
   * @ordered
   */
	protected static final boolean DELETE_ON_TERMINATION_EDEFAULT = false;

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
   * The default value of the '{@link #isExclusiveExecution() <em>Exclusive Execution</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isExclusiveExecution()
   * @generated
   * @ordered
   */
	protected static final boolean EXCLUSIVE_EXECUTION_EDEFAULT = false;

  /**
   * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
	protected static final String FILE_NAME_EDEFAULT = null;

  /**
   * The default value of the '{@link #getFilesystemName() <em>Filesystem Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFilesystemName()
   * @generated
   * @ordered
   */
	protected static final String FILESYSTEM_NAME_EDEFAULT = null;

  /**
   * The default value of the '{@link #getFileSystemType() <em>File System Type</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFileSystemType()
   * @generated
   * @ordered
   */
	protected static final FileSystemTypeEnumeration FILE_SYSTEM_TYPE_EDEFAULT = FileSystemTypeEnumeration.SWAP_LITERAL;

  /**
   * The default value of the '{@link #getHostName() <em>Host Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getHostName()
   * @generated
   * @ordered
   */
	protected static final String HOST_NAME_EDEFAULT = null;

  /**
   * The default value of the '{@link #getJobAnnotation() <em>Job Annotation</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getJobAnnotation()
   * @generated
   * @ordered
   */
	protected static final String JOB_ANNOTATION_EDEFAULT = null;

  /**
   * The default value of the '{@link #getJobName() <em>Job Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getJobName()
   * @generated
   * @ordered
   */
	protected static final String JOB_NAME_EDEFAULT = null;

  /**
   * The default value of the '{@link #getJobProject() <em>Job Project</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getJobProject()
   * @generated
   * @ordered
   */
	protected static final String JOB_PROJECT_EDEFAULT = null;

  /**
   * The default value of the '{@link #getMountPoint() <em>Mount Point</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getMountPoint()
   * @generated
   * @ordered
   */
	protected static final String MOUNT_POINT_EDEFAULT = null;

  /**
   * The default value of the '{@link #getOperatingSystemName() <em>Operating System Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOperatingSystemName()
   * @generated
   * @ordered
   */
	protected static final OperatingSystemTypeEnumeration OPERATING_SYSTEM_NAME_EDEFAULT = OperatingSystemTypeEnumeration.UNKNOWN_LITERAL;

  /**
   * The default value of the '{@link #getOperatingSystemVersion() <em>Operating System Version</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOperatingSystemVersion()
   * @generated
   * @ordered
   */
	protected static final String OPERATING_SYSTEM_VERSION_EDEFAULT = null;

  /**
   * The default value of the '{@link #getURI() <em>URI</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getURI()
   * @generated
   * @ordered
   */
	protected static final String URI_EDEFAULT = null;

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
	protected EClass eStaticClass()
  {
    return JsdlPackage.Literals.DOCUMENT_ROOT;
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
      mixed = new BasicFeatureMap(this, JsdlPackage.DOCUMENT_ROOT__MIXED);
    }
    return mixed;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EMap getXMLNSPrefixMap()
  {
    if (xMLNSPrefixMap == null)
    {
      xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    }
    return xMLNSPrefixMap;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EMap getXSISchemaLocation()
  {
    if (xSISchemaLocation == null)
    {
      xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    }
    return xSISchemaLocation;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ApplicationType getApplication()
  {
    return (ApplicationType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetApplication(ApplicationType newApplication, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION, newApplication, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setApplication(ApplicationType newApplication)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION, newApplication);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getApplicationName()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setApplicationName(String newApplicationName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_NAME, newApplicationName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getApplicationVersion()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_VERSION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setApplicationVersion(String newApplicationVersion)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_VERSION, newApplicationVersion);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CandidateHostsType getCandidateHosts()
  {
    return (CandidateHostsType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__CANDIDATE_HOSTS, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCandidateHosts(CandidateHostsType newCandidateHosts, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__CANDIDATE_HOSTS, newCandidateHosts, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCandidateHosts(CandidateHostsType newCandidateHosts)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__CANDIDATE_HOSTS, newCandidateHosts);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CPUArchitectureType getCPUArchitecture()
  {
    return (CPUArchitectureType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCPUArchitecture(CPUArchitectureType newCPUArchitecture, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE, newCPUArchitecture, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCPUArchitecture(CPUArchitectureType newCPUArchitecture)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE, newCPUArchitecture);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ProcessorArchitectureEnumeration getCPUArchitectureName()
  {
    return (ProcessorArchitectureEnumeration)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCPUArchitectureName(ProcessorArchitectureEnumeration newCPUArchitectureName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME, newCPUArchitectureName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CreationFlagEnumeration getCreationFlag()
  {
    return (CreationFlagEnumeration)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__CREATION_FLAG, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCreationFlag(CreationFlagEnumeration newCreationFlag)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__CREATION_FLAG, newCreationFlag);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DataStagingType getDataStaging()
  {
    return (DataStagingType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__DATA_STAGING, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetDataStaging(DataStagingType newDataStaging, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__DATA_STAGING, newDataStaging, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDataStaging(DataStagingType newDataStaging)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__DATA_STAGING, newDataStaging);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isDeleteOnTermination()
  {
    return ((Boolean)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__DELETE_ON_TERMINATION, true)).booleanValue();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDeleteOnTermination(boolean newDeleteOnTermination)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__DELETE_ON_TERMINATION, new Boolean(newDeleteOnTermination));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getDescription()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDescription(String newDescription)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__DESCRIPTION, newDescription);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getDiskSpace()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__DISK_SPACE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetDiskSpace(RangeValueType newDiskSpace, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__DISK_SPACE, newDiskSpace, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDiskSpace(RangeValueType newDiskSpace)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__DISK_SPACE, newDiskSpace);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isExclusiveExecution()
  {
    return ((Boolean)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION, true)).booleanValue();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setExclusiveExecution(boolean newExclusiveExecution)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION, new Boolean(newExclusiveExecution));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getFileName()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileName(String newFileName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_NAME, newFileName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileSystemType getFileSystem()
  {
    return (FileSystemType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetFileSystem(FileSystemType newFileSystem, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM, newFileSystem, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileSystem(FileSystemType newFileSystem)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM, newFileSystem);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getFilesystemName()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__FILESYSTEM_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFilesystemName(String newFilesystemName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__FILESYSTEM_NAME, newFilesystemName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileSystemTypeEnumeration getFileSystemType()
  {
    return (FileSystemTypeEnumeration)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM_TYPE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileSystemType(FileSystemTypeEnumeration newFileSystemType)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM_TYPE, newFileSystemType);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getHostName()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__HOST_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setHostName(String newHostName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__HOST_NAME, newHostName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualCPUCount()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualCPUCount(RangeValueType newIndividualCPUCount, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT, newIndividualCPUCount, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualCPUCount(RangeValueType newIndividualCPUCount)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT, newIndividualCPUCount);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualCPUSpeed()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualCPUSpeed(RangeValueType newIndividualCPUSpeed, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED, newIndividualCPUSpeed, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualCPUSpeed(RangeValueType newIndividualCPUSpeed)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED, newIndividualCPUSpeed);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualCPUTime()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualCPUTime(RangeValueType newIndividualCPUTime, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME, newIndividualCPUTime, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualCPUTime(RangeValueType newIndividualCPUTime)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME, newIndividualCPUTime);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualDiskSpace()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualDiskSpace(RangeValueType newIndividualDiskSpace, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE, newIndividualDiskSpace, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualDiskSpace(RangeValueType newIndividualDiskSpace)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE, newIndividualDiskSpace);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualNetworkBandwidth()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualNetworkBandwidth(RangeValueType newIndividualNetworkBandwidth, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH, newIndividualNetworkBandwidth, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualNetworkBandwidth(RangeValueType newIndividualNetworkBandwidth)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH, newIndividualNetworkBandwidth);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualPhysicalMemory()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualPhysicalMemory(RangeValueType newIndividualPhysicalMemory, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY, newIndividualPhysicalMemory, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualPhysicalMemory(RangeValueType newIndividualPhysicalMemory)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY, newIndividualPhysicalMemory);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getIndividualVirtualMemory()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetIndividualVirtualMemory(RangeValueType newIndividualVirtualMemory, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY, newIndividualVirtualMemory, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setIndividualVirtualMemory(RangeValueType newIndividualVirtualMemory)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY, newIndividualVirtualMemory);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getJobAnnotation()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_ANNOTATION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setJobAnnotation(String newJobAnnotation)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_ANNOTATION, newJobAnnotation);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JobDefinitionType getJobDefinition()
  {
    return (JobDefinitionType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DEFINITION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetJobDefinition(JobDefinitionType newJobDefinition, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DEFINITION, newJobDefinition, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setJobDefinition(JobDefinitionType newJobDefinition)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DEFINITION, newJobDefinition);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JobDescriptionType getJobDescription()
  {
    return (JobDescriptionType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DESCRIPTION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetJobDescription(JobDescriptionType newJobDescription, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DESCRIPTION, newJobDescription, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setJobDescription(JobDescriptionType newJobDescription)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DESCRIPTION, newJobDescription);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JobIdentificationType getJobIdentification()
  {
    return (JobIdentificationType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_IDENTIFICATION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetJobIdentification(JobIdentificationType newJobIdentification, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_IDENTIFICATION, newJobIdentification, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setJobIdentification(JobIdentificationType newJobIdentification)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_IDENTIFICATION, newJobIdentification);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getJobName()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setJobName(String newJobName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_NAME, newJobName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getJobProject()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_PROJECT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setJobProject(String newJobProject)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__JOB_PROJECT, newJobProject);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getMountPoint()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__MOUNT_POINT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setMountPoint(String newMountPoint)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__MOUNT_POINT, newMountPoint);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemType getOperatingSystem()
  {
    return (OperatingSystemType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOperatingSystem(OperatingSystemType newOperatingSystem, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM, newOperatingSystem, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystem(OperatingSystemType newOperatingSystem)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM, newOperatingSystem);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemTypeEnumeration getOperatingSystemName()
  {
    return (OperatingSystemTypeEnumeration)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystemName(OperatingSystemTypeEnumeration newOperatingSystemName)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME, newOperatingSystemName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public OperatingSystemTypeType getOperatingSystemType()
  {
    return (OperatingSystemTypeType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOperatingSystemType(OperatingSystemTypeType newOperatingSystemType, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE, newOperatingSystemType, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystemType(OperatingSystemTypeType newOperatingSystemType)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE, newOperatingSystemType);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getOperatingSystemVersion()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOperatingSystemVersion(String newOperatingSystemVersion)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION, newOperatingSystemVersion);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ResourcesType getResources()
  {
    return (ResourcesType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__RESOURCES, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetResources(ResourcesType newResources, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__RESOURCES, newResources, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setResources(ResourcesType newResources)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__RESOURCES, newResources);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public SourceTargetType getSource()
  {
    return (SourceTargetType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__SOURCE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetSource(SourceTargetType newSource, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__SOURCE, newSource, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setSource(SourceTargetType newSource)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__SOURCE, newSource);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public SourceTargetType getTarget()
  {
    return (SourceTargetType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TARGET, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTarget(SourceTargetType newTarget, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TARGET, newTarget, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTarget(SourceTargetType newTarget)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TARGET, newTarget);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalCPUCount()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_COUNT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalCPUCount(RangeValueType newTotalCPUCount, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_COUNT, newTotalCPUCount, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalCPUCount(RangeValueType newTotalCPUCount)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_COUNT, newTotalCPUCount);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalCPUTime()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_TIME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalCPUTime(RangeValueType newTotalCPUTime, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_TIME, newTotalCPUTime, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalCPUTime(RangeValueType newTotalCPUTime)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_TIME, newTotalCPUTime);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalDiskSpace()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_DISK_SPACE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalDiskSpace(RangeValueType newTotalDiskSpace, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_DISK_SPACE, newTotalDiskSpace, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalDiskSpace(RangeValueType newTotalDiskSpace)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_DISK_SPACE, newTotalDiskSpace);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalPhysicalMemory()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalPhysicalMemory(RangeValueType newTotalPhysicalMemory, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY, newTotalPhysicalMemory, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalPhysicalMemory(RangeValueType newTotalPhysicalMemory)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY, newTotalPhysicalMemory);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalResourceCount()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalResourceCount(RangeValueType newTotalResourceCount, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT, newTotalResourceCount, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalResourceCount(RangeValueType newTotalResourceCount)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT, newTotalResourceCount);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getTotalVirtualMemory()
  {
    return (RangeValueType)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTotalVirtualMemory(RangeValueType newTotalVirtualMemory, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY, newTotalVirtualMemory, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTotalVirtualMemory(RangeValueType newTotalVirtualMemory)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY, newTotalVirtualMemory);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getURI()
  {
    return (String)getMixed().get(JsdlPackage.Literals.DOCUMENT_ROOT__URI, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setURI(String newURI)
  {
    ((FeatureMap.Internal)getMixed()).set(JsdlPackage.Literals.DOCUMENT_ROOT__URI, newURI);
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
      case JsdlPackage.DOCUMENT_ROOT__MIXED:
        return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
      case JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
      case JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION:
        return basicSetApplication(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__CANDIDATE_HOSTS:
        return basicSetCandidateHosts(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE:
        return basicSetCPUArchitecture(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__DATA_STAGING:
        return basicSetDataStaging(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__DISK_SPACE:
        return basicSetDiskSpace(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM:
        return basicSetFileSystem(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT:
        return basicSetIndividualCPUCount(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED:
        return basicSetIndividualCPUSpeed(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME:
        return basicSetIndividualCPUTime(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE:
        return basicSetIndividualDiskSpace(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH:
        return basicSetIndividualNetworkBandwidth(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY:
        return basicSetIndividualPhysicalMemory(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY:
        return basicSetIndividualVirtualMemory(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__JOB_DEFINITION:
        return basicSetJobDefinition(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__JOB_DESCRIPTION:
        return basicSetJobDescription(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__JOB_IDENTIFICATION:
        return basicSetJobIdentification(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM:
        return basicSetOperatingSystem(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE:
        return basicSetOperatingSystemType(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__RESOURCES:
        return basicSetResources(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__SOURCE:
        return basicSetSource(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TARGET:
        return basicSetTarget(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_COUNT:
        return basicSetTotalCPUCount(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_TIME:
        return basicSetTotalCPUTime(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_DISK_SPACE:
        return basicSetTotalDiskSpace(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY:
        return basicSetTotalPhysicalMemory(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT:
        return basicSetTotalResourceCount(null, msgs);
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY:
        return basicSetTotalVirtualMemory(null, msgs);
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
      case JsdlPackage.DOCUMENT_ROOT__MIXED:
        if (coreType) return getMixed();
        return ((FeatureMap.Internal)getMixed()).getWrapper();
      case JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        if (coreType) return getXMLNSPrefixMap();
        else return getXMLNSPrefixMap().map();
      case JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        if (coreType) return getXSISchemaLocation();
        else return getXSISchemaLocation().map();
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION:
        return getApplication();
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_NAME:
        return getApplicationName();
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_VERSION:
        return getApplicationVersion();
      case JsdlPackage.DOCUMENT_ROOT__CANDIDATE_HOSTS:
        return getCandidateHosts();
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE:
        return getCPUArchitecture();
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME:
        return getCPUArchitectureName();
      case JsdlPackage.DOCUMENT_ROOT__CREATION_FLAG:
        return getCreationFlag();
      case JsdlPackage.DOCUMENT_ROOT__DATA_STAGING:
        return getDataStaging();
      case JsdlPackage.DOCUMENT_ROOT__DELETE_ON_TERMINATION:
        return isDeleteOnTermination() ? Boolean.TRUE : Boolean.FALSE;
      case JsdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        return getDescription();
      case JsdlPackage.DOCUMENT_ROOT__DISK_SPACE:
        return getDiskSpace();
      case JsdlPackage.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION:
        return isExclusiveExecution() ? Boolean.TRUE : Boolean.FALSE;
      case JsdlPackage.DOCUMENT_ROOT__FILE_NAME:
        return getFileName();
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM:
        return getFileSystem();
      case JsdlPackage.DOCUMENT_ROOT__FILESYSTEM_NAME:
        return getFilesystemName();
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE:
        return getFileSystemType();
      case JsdlPackage.DOCUMENT_ROOT__HOST_NAME:
        return getHostName();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT:
        return getIndividualCPUCount();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED:
        return getIndividualCPUSpeed();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME:
        return getIndividualCPUTime();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE:
        return getIndividualDiskSpace();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH:
        return getIndividualNetworkBandwidth();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY:
        return getIndividualPhysicalMemory();
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY:
        return getIndividualVirtualMemory();
      case JsdlPackage.DOCUMENT_ROOT__JOB_ANNOTATION:
        return getJobAnnotation();
      case JsdlPackage.DOCUMENT_ROOT__JOB_DEFINITION:
        return getJobDefinition();
      case JsdlPackage.DOCUMENT_ROOT__JOB_DESCRIPTION:
        return getJobDescription();
      case JsdlPackage.DOCUMENT_ROOT__JOB_IDENTIFICATION:
        return getJobIdentification();
      case JsdlPackage.DOCUMENT_ROOT__JOB_NAME:
        return getJobName();
      case JsdlPackage.DOCUMENT_ROOT__JOB_PROJECT:
        return getJobProject();
      case JsdlPackage.DOCUMENT_ROOT__MOUNT_POINT:
        return getMountPoint();
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM:
        return getOperatingSystem();
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME:
        return getOperatingSystemName();
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE:
        return getOperatingSystemType();
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION:
        return getOperatingSystemVersion();
      case JsdlPackage.DOCUMENT_ROOT__RESOURCES:
        return getResources();
      case JsdlPackage.DOCUMENT_ROOT__SOURCE:
        return getSource();
      case JsdlPackage.DOCUMENT_ROOT__TARGET:
        return getTarget();
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_COUNT:
        return getTotalCPUCount();
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_TIME:
        return getTotalCPUTime();
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_DISK_SPACE:
        return getTotalDiskSpace();
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY:
        return getTotalPhysicalMemory();
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT:
        return getTotalResourceCount();
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY:
        return getTotalVirtualMemory();
      case JsdlPackage.DOCUMENT_ROOT__URI:
        return getURI();
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
      case JsdlPackage.DOCUMENT_ROOT__MIXED:
        ((FeatureMap.Internal)getMixed()).set(newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION:
        setApplication((ApplicationType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_NAME:
        setApplicationName((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_VERSION:
        setApplicationVersion((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CANDIDATE_HOSTS:
        setCandidateHosts((CandidateHostsType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE:
        setCPUArchitecture((CPUArchitectureType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME:
        setCPUArchitectureName((ProcessorArchitectureEnumeration)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CREATION_FLAG:
        setCreationFlag((CreationFlagEnumeration)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DATA_STAGING:
        setDataStaging((DataStagingType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DELETE_ON_TERMINATION:
        setDeleteOnTermination(((Boolean)newValue).booleanValue());
        return;
      case JsdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DISK_SPACE:
        setDiskSpace((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION:
        setExclusiveExecution(((Boolean)newValue).booleanValue());
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILE_NAME:
        setFileName((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM:
        setFileSystem((FileSystemType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILESYSTEM_NAME:
        setFilesystemName((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE:
        setFileSystemType((FileSystemTypeEnumeration)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__HOST_NAME:
        setHostName((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT:
        setIndividualCPUCount((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED:
        setIndividualCPUSpeed((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME:
        setIndividualCPUTime((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE:
        setIndividualDiskSpace((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH:
        setIndividualNetworkBandwidth((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY:
        setIndividualPhysicalMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY:
        setIndividualVirtualMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_ANNOTATION:
        setJobAnnotation((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_DEFINITION:
        setJobDefinition((JobDefinitionType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_DESCRIPTION:
        setJobDescription((JobDescriptionType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_IDENTIFICATION:
        setJobIdentification((JobIdentificationType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_NAME:
        setJobName((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_PROJECT:
        setJobProject((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__MOUNT_POINT:
        setMountPoint((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM:
        setOperatingSystem((OperatingSystemType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME:
        setOperatingSystemName((OperatingSystemTypeEnumeration)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE:
        setOperatingSystemType((OperatingSystemTypeType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION:
        setOperatingSystemVersion((String)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__RESOURCES:
        setResources((ResourcesType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__SOURCE:
        setSource((SourceTargetType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TARGET:
        setTarget((SourceTargetType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_COUNT:
        setTotalCPUCount((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_TIME:
        setTotalCPUTime((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_DISK_SPACE:
        setTotalDiskSpace((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY:
        setTotalPhysicalMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT:
        setTotalResourceCount((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY:
        setTotalVirtualMemory((RangeValueType)newValue);
        return;
      case JsdlPackage.DOCUMENT_ROOT__URI:
        setURI((String)newValue);
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
      case JsdlPackage.DOCUMENT_ROOT__MIXED:
        getMixed().clear();
        return;
      case JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        getXMLNSPrefixMap().clear();
        return;
      case JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        getXSISchemaLocation().clear();
        return;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION:
        setApplication((ApplicationType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_NAME:
        setApplicationName(APPLICATION_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_VERSION:
        setApplicationVersion(APPLICATION_VERSION_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CANDIDATE_HOSTS:
        setCandidateHosts((CandidateHostsType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE:
        setCPUArchitecture((CPUArchitectureType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME:
        setCPUArchitectureName(CPU_ARCHITECTURE_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__CREATION_FLAG:
        setCreationFlag(CREATION_FLAG_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DATA_STAGING:
        setDataStaging((DataStagingType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DELETE_ON_TERMINATION:
        setDeleteOnTermination(DELETE_ON_TERMINATION_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__DISK_SPACE:
        setDiskSpace((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION:
        setExclusiveExecution(EXCLUSIVE_EXECUTION_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILE_NAME:
        setFileName(FILE_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM:
        setFileSystem((FileSystemType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILESYSTEM_NAME:
        setFilesystemName(FILESYSTEM_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE:
        setFileSystemType(FILE_SYSTEM_TYPE_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__HOST_NAME:
        setHostName(HOST_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT:
        setIndividualCPUCount((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED:
        setIndividualCPUSpeed((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME:
        setIndividualCPUTime((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE:
        setIndividualDiskSpace((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH:
        setIndividualNetworkBandwidth((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY:
        setIndividualPhysicalMemory((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY:
        setIndividualVirtualMemory((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_ANNOTATION:
        setJobAnnotation(JOB_ANNOTATION_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_DEFINITION:
        setJobDefinition((JobDefinitionType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_DESCRIPTION:
        setJobDescription((JobDescriptionType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_IDENTIFICATION:
        setJobIdentification((JobIdentificationType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_NAME:
        setJobName(JOB_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__JOB_PROJECT:
        setJobProject(JOB_PROJECT_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__MOUNT_POINT:
        setMountPoint(MOUNT_POINT_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM:
        setOperatingSystem((OperatingSystemType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME:
        setOperatingSystemName(OPERATING_SYSTEM_NAME_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE:
        setOperatingSystemType((OperatingSystemTypeType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION:
        setOperatingSystemVersion(OPERATING_SYSTEM_VERSION_EDEFAULT);
        return;
      case JsdlPackage.DOCUMENT_ROOT__RESOURCES:
        setResources((ResourcesType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__SOURCE:
        setSource((SourceTargetType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TARGET:
        setTarget((SourceTargetType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_COUNT:
        setTotalCPUCount((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_TIME:
        setTotalCPUTime((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_DISK_SPACE:
        setTotalDiskSpace((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY:
        setTotalPhysicalMemory((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT:
        setTotalResourceCount((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY:
        setTotalVirtualMemory((RangeValueType)null);
        return;
      case JsdlPackage.DOCUMENT_ROOT__URI:
        setURI(URI_EDEFAULT);
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
      case JsdlPackage.DOCUMENT_ROOT__MIXED:
        return mixed != null && !mixed.isEmpty();
      case JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
      case JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION:
        return getApplication() != null;
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_NAME:
        return APPLICATION_NAME_EDEFAULT == null ? getApplicationName() != null : !APPLICATION_NAME_EDEFAULT.equals(getApplicationName());
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_VERSION:
        return APPLICATION_VERSION_EDEFAULT == null ? getApplicationVersion() != null : !APPLICATION_VERSION_EDEFAULT.equals(getApplicationVersion());
      case JsdlPackage.DOCUMENT_ROOT__CANDIDATE_HOSTS:
        return getCandidateHosts() != null;
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE:
        return getCPUArchitecture() != null;
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME:
        return getCPUArchitectureName() != CPU_ARCHITECTURE_NAME_EDEFAULT;
      case JsdlPackage.DOCUMENT_ROOT__CREATION_FLAG:
        return getCreationFlag() != CREATION_FLAG_EDEFAULT;
      case JsdlPackage.DOCUMENT_ROOT__DATA_STAGING:
        return getDataStaging() != null;
      case JsdlPackage.DOCUMENT_ROOT__DELETE_ON_TERMINATION:
        return isDeleteOnTermination() != DELETE_ON_TERMINATION_EDEFAULT;
      case JsdlPackage.DOCUMENT_ROOT__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
      case JsdlPackage.DOCUMENT_ROOT__DISK_SPACE:
        return getDiskSpace() != null;
      case JsdlPackage.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION:
        return isExclusiveExecution() != EXCLUSIVE_EXECUTION_EDEFAULT;
      case JsdlPackage.DOCUMENT_ROOT__FILE_NAME:
        return FILE_NAME_EDEFAULT == null ? getFileName() != null : !FILE_NAME_EDEFAULT.equals(getFileName());
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM:
        return getFileSystem() != null;
      case JsdlPackage.DOCUMENT_ROOT__FILESYSTEM_NAME:
        return FILESYSTEM_NAME_EDEFAULT == null ? getFilesystemName() != null : !FILESYSTEM_NAME_EDEFAULT.equals(getFilesystemName());
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE:
        return getFileSystemType() != FILE_SYSTEM_TYPE_EDEFAULT;
      case JsdlPackage.DOCUMENT_ROOT__HOST_NAME:
        return HOST_NAME_EDEFAULT == null ? getHostName() != null : !HOST_NAME_EDEFAULT.equals(getHostName());
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT:
        return getIndividualCPUCount() != null;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED:
        return getIndividualCPUSpeed() != null;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME:
        return getIndividualCPUTime() != null;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE:
        return getIndividualDiskSpace() != null;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH:
        return getIndividualNetworkBandwidth() != null;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY:
        return getIndividualPhysicalMemory() != null;
      case JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY:
        return getIndividualVirtualMemory() != null;
      case JsdlPackage.DOCUMENT_ROOT__JOB_ANNOTATION:
        return JOB_ANNOTATION_EDEFAULT == null ? getJobAnnotation() != null : !JOB_ANNOTATION_EDEFAULT.equals(getJobAnnotation());
      case JsdlPackage.DOCUMENT_ROOT__JOB_DEFINITION:
        return getJobDefinition() != null;
      case JsdlPackage.DOCUMENT_ROOT__JOB_DESCRIPTION:
        return getJobDescription() != null;
      case JsdlPackage.DOCUMENT_ROOT__JOB_IDENTIFICATION:
        return getJobIdentification() != null;
      case JsdlPackage.DOCUMENT_ROOT__JOB_NAME:
        return JOB_NAME_EDEFAULT == null ? getJobName() != null : !JOB_NAME_EDEFAULT.equals(getJobName());
      case JsdlPackage.DOCUMENT_ROOT__JOB_PROJECT:
        return JOB_PROJECT_EDEFAULT == null ? getJobProject() != null : !JOB_PROJECT_EDEFAULT.equals(getJobProject());
      case JsdlPackage.DOCUMENT_ROOT__MOUNT_POINT:
        return MOUNT_POINT_EDEFAULT == null ? getMountPoint() != null : !MOUNT_POINT_EDEFAULT.equals(getMountPoint());
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM:
        return getOperatingSystem() != null;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME:
        return getOperatingSystemName() != OPERATING_SYSTEM_NAME_EDEFAULT;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE:
        return getOperatingSystemType() != null;
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION:
        return OPERATING_SYSTEM_VERSION_EDEFAULT == null ? getOperatingSystemVersion() != null : !OPERATING_SYSTEM_VERSION_EDEFAULT.equals(getOperatingSystemVersion());
      case JsdlPackage.DOCUMENT_ROOT__RESOURCES:
        return getResources() != null;
      case JsdlPackage.DOCUMENT_ROOT__SOURCE:
        return getSource() != null;
      case JsdlPackage.DOCUMENT_ROOT__TARGET:
        return getTarget() != null;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_COUNT:
        return getTotalCPUCount() != null;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_CPU_TIME:
        return getTotalCPUTime() != null;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_DISK_SPACE:
        return getTotalDiskSpace() != null;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY:
        return getTotalPhysicalMemory() != null;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT:
        return getTotalResourceCount() != null;
      case JsdlPackage.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY:
        return getTotalVirtualMemory() != null;
      case JsdlPackage.DOCUMENT_ROOT__URI:
        return URI_EDEFAULT == null ? getURI() != null : !URI_EDEFAULT.equals(getURI());
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
    result.append(" (mixed: ");
    result.append(mixed);
    result.append(')');
    return result.toString();
  }

} //DocumentRootImpl