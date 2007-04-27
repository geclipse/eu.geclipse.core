/**
 * <copyright>
 * </copyright>
 *
 * $Id: DocumentRootImpl.java,v 1.2 2007/03/01 09:15:17 emstamou Exp $
 */
package eu.geclipse.jsdl.posix.impl;

import eu.geclipse.jsdl.posix.ArgumentType;
import eu.geclipse.jsdl.posix.DirectoryNameType;
import eu.geclipse.jsdl.posix.DocumentRoot;
import eu.geclipse.jsdl.posix.EnvironmentType;
import eu.geclipse.jsdl.posix.FileNameType;
import eu.geclipse.jsdl.posix.GroupNameType;
import eu.geclipse.jsdl.posix.LimitsType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;
import eu.geclipse.jsdl.posix.PosixPackage;
import eu.geclipse.jsdl.posix.UserNameType;

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
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getArgument <em>Argument</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getCoreDumpLimit <em>Core Dump Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getDataSegmentLimit <em>Data Segment Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getEnvironment <em>Environment</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getError <em>Error</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getExecutable <em>Executable</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getFileSizeLimit <em>File Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getInput <em>Input</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getLockedMemoryLimit <em>Locked Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getMemoryLimit <em>Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getOpenDescriptorsLimit <em>Open Descriptors Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getOutput <em>Output</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getPipeSizeLimit <em>Pipe Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getPOSIXApplication <em>POSIX Application</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getProcessCountLimit <em>Process Count Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getStackSizeLimit <em>Stack Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getThreadCountLimit <em>Thread Count Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getVirtualMemoryLimit <em>Virtual Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.posix.impl.DocumentRootImpl#getWorkingDirectory <em>Working Directory</em>}</li>
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
	protected FeatureMap mixed = null;

  /**
   * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getXMLNSPrefixMap()
   * @generated
   * @ordered
   */
	protected EMap xMLNSPrefixMap = null;

  /**
   * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getXSISchemaLocation()
   * @generated
   * @ordered
   */
	protected EMap xSISchemaLocation = null;

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
    return PosixPackage.Literals.DOCUMENT_ROOT;
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
      mixed = new BasicFeatureMap(this, PosixPackage.DOCUMENT_ROOT__MIXED);
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
      xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, PosixPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
      xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, PosixPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    }
    return xSISchemaLocation;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ArgumentType getArgument()
  {
    return (ArgumentType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__ARGUMENT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetArgument(ArgumentType newArgument, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__ARGUMENT, newArgument, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setArgument(ArgumentType newArgument)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__ARGUMENT, newArgument);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getCoreDumpLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCoreDumpLimit(LimitsType newCoreDumpLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT, newCoreDumpLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCoreDumpLimit(LimitsType newCoreDumpLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT, newCoreDumpLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getCPUTimeLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCPUTimeLimit(LimitsType newCPUTimeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT, newCPUTimeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCPUTimeLimit(LimitsType newCPUTimeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT, newCPUTimeLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getDataSegmentLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetDataSegmentLimit(LimitsType newDataSegmentLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT, newDataSegmentLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDataSegmentLimit(LimitsType newDataSegmentLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT, newDataSegmentLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EnvironmentType getEnvironment()
  {
    return (EnvironmentType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__ENVIRONMENT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetEnvironment(EnvironmentType newEnvironment, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__ENVIRONMENT, newEnvironment, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setEnvironment(EnvironmentType newEnvironment)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__ENVIRONMENT, newEnvironment);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getError()
  {
    return (FileNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__ERROR, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetError(FileNameType newError, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__ERROR, newError, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setError(FileNameType newError)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__ERROR, newError);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getExecutable()
  {
    return (FileNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetExecutable(FileNameType newExecutable, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE, newExecutable, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setExecutable(FileNameType newExecutable)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE, newExecutable);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getFileSizeLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetFileSizeLimit(LimitsType newFileSizeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT, newFileSizeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileSizeLimit(LimitsType newFileSizeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT, newFileSizeLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public GroupNameType getGroupName()
  {
    return (GroupNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__GROUP_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetGroupName(GroupNameType newGroupName, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__GROUP_NAME, newGroupName, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setGroupName(GroupNameType newGroupName)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__GROUP_NAME, newGroupName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getInput()
  {
    return (FileNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__INPUT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetInput(FileNameType newInput, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__INPUT, newInput, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setInput(FileNameType newInput)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__INPUT, newInput);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getLockedMemoryLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetLockedMemoryLimit(LimitsType newLockedMemoryLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT, newLockedMemoryLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setLockedMemoryLimit(LimitsType newLockedMemoryLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT, newLockedMemoryLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getMemoryLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetMemoryLimit(LimitsType newMemoryLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT, newMemoryLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setMemoryLimit(LimitsType newMemoryLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT, newMemoryLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getOpenDescriptorsLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOpenDescriptorsLimit(LimitsType newOpenDescriptorsLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT, newOpenDescriptorsLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOpenDescriptorsLimit(LimitsType newOpenDescriptorsLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT, newOpenDescriptorsLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getOutput()
  {
    return (FileNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOutput(FileNameType newOutput, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT, newOutput, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOutput(FileNameType newOutput)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT, newOutput);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getPipeSizeLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetPipeSizeLimit(LimitsType newPipeSizeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT, newPipeSizeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setPipeSizeLimit(LimitsType newPipeSizeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT, newPipeSizeLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public POSIXApplicationType getPOSIXApplication()
  {
    return (POSIXApplicationType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__POSIX_APPLICATION, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetPOSIXApplication(POSIXApplicationType newPOSIXApplication, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__POSIX_APPLICATION, newPOSIXApplication, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setPOSIXApplication(POSIXApplicationType newPOSIXApplication)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__POSIX_APPLICATION, newPOSIXApplication);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getProcessCountLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetProcessCountLimit(LimitsType newProcessCountLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT, newProcessCountLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setProcessCountLimit(LimitsType newProcessCountLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT, newProcessCountLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getStackSizeLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetStackSizeLimit(LimitsType newStackSizeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT, newStackSizeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setStackSizeLimit(LimitsType newStackSizeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT, newStackSizeLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getThreadCountLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetThreadCountLimit(LimitsType newThreadCountLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT, newThreadCountLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setThreadCountLimit(LimitsType newThreadCountLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT, newThreadCountLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public UserNameType getUserName()
  {
    return (UserNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__USER_NAME, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetUserName(UserNameType newUserName, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__USER_NAME, newUserName, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setUserName(UserNameType newUserName)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__USER_NAME, newUserName);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getVirtualMemoryLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetVirtualMemoryLimit(LimitsType newVirtualMemoryLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT, newVirtualMemoryLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setVirtualMemoryLimit(LimitsType newVirtualMemoryLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT, newVirtualMemoryLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getWallTimeLimit()
  {
    return (LimitsType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetWallTimeLimit(LimitsType newWallTimeLimit, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT, newWallTimeLimit, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setWallTimeLimit(LimitsType newWallTimeLimit)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT, newWallTimeLimit);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DirectoryNameType getWorkingDirectory()
  {
    return (DirectoryNameType)getMixed().get(PosixPackage.Literals.DOCUMENT_ROOT__WORKING_DIRECTORY, true);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetWorkingDirectory(DirectoryNameType newWorkingDirectory, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(PosixPackage.Literals.DOCUMENT_ROOT__WORKING_DIRECTORY, newWorkingDirectory, msgs);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setWorkingDirectory(DirectoryNameType newWorkingDirectory)
  {
    ((FeatureMap.Internal)getMixed()).set(PosixPackage.Literals.DOCUMENT_ROOT__WORKING_DIRECTORY, newWorkingDirectory);
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
      case PosixPackage.DOCUMENT_ROOT__MIXED:
        return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
      case PosixPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
      case PosixPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
      case PosixPackage.DOCUMENT_ROOT__ARGUMENT:
        return basicSetArgument(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__CORE_DUMP_LIMIT:
        return basicSetCoreDumpLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        return basicSetCPUTimeLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT:
        return basicSetDataSegmentLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__ENVIRONMENT:
        return basicSetEnvironment(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__ERROR:
        return basicSetError(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__EXECUTABLE:
        return basicSetExecutable(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__FILE_SIZE_LIMIT:
        return basicSetFileSizeLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__GROUP_NAME:
        return basicSetGroupName(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__INPUT:
        return basicSetInput(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT:
        return basicSetLockedMemoryLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__MEMORY_LIMIT:
        return basicSetMemoryLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT:
        return basicSetOpenDescriptorsLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__OUTPUT:
        return basicSetOutput(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__PIPE_SIZE_LIMIT:
        return basicSetPipeSizeLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION:
        return basicSetPOSIXApplication(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT:
        return basicSetProcessCountLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__STACK_SIZE_LIMIT:
        return basicSetStackSizeLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__THREAD_COUNT_LIMIT:
        return basicSetThreadCountLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__USER_NAME:
        return basicSetUserName(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT:
        return basicSetVirtualMemoryLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        return basicSetWallTimeLimit(null, msgs);
      case PosixPackage.DOCUMENT_ROOT__WORKING_DIRECTORY:
        return basicSetWorkingDirectory(null, msgs);
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
      case PosixPackage.DOCUMENT_ROOT__MIXED:
        if (coreType) return getMixed();
        return ((FeatureMap.Internal)getMixed()).getWrapper();
      case PosixPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        if (coreType) return getXMLNSPrefixMap();
        else return getXMLNSPrefixMap().map();
      case PosixPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        if (coreType) return getXSISchemaLocation();
        else return getXSISchemaLocation().map();
      case PosixPackage.DOCUMENT_ROOT__ARGUMENT:
        return getArgument();
      case PosixPackage.DOCUMENT_ROOT__CORE_DUMP_LIMIT:
        return getCoreDumpLimit();
      case PosixPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        return getCPUTimeLimit();
      case PosixPackage.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT:
        return getDataSegmentLimit();
      case PosixPackage.DOCUMENT_ROOT__ENVIRONMENT:
        return getEnvironment();
      case PosixPackage.DOCUMENT_ROOT__ERROR:
        return getError();
      case PosixPackage.DOCUMENT_ROOT__EXECUTABLE:
        return getExecutable();
      case PosixPackage.DOCUMENT_ROOT__FILE_SIZE_LIMIT:
        return getFileSizeLimit();
      case PosixPackage.DOCUMENT_ROOT__GROUP_NAME:
        return getGroupName();
      case PosixPackage.DOCUMENT_ROOT__INPUT:
        return getInput();
      case PosixPackage.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT:
        return getLockedMemoryLimit();
      case PosixPackage.DOCUMENT_ROOT__MEMORY_LIMIT:
        return getMemoryLimit();
      case PosixPackage.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT:
        return getOpenDescriptorsLimit();
      case PosixPackage.DOCUMENT_ROOT__OUTPUT:
        return getOutput();
      case PosixPackage.DOCUMENT_ROOT__PIPE_SIZE_LIMIT:
        return getPipeSizeLimit();
      case PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION:
        return getPOSIXApplication();
      case PosixPackage.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT:
        return getProcessCountLimit();
      case PosixPackage.DOCUMENT_ROOT__STACK_SIZE_LIMIT:
        return getStackSizeLimit();
      case PosixPackage.DOCUMENT_ROOT__THREAD_COUNT_LIMIT:
        return getThreadCountLimit();
      case PosixPackage.DOCUMENT_ROOT__USER_NAME:
        return getUserName();
      case PosixPackage.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT:
        return getVirtualMemoryLimit();
      case PosixPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        return getWallTimeLimit();
      case PosixPackage.DOCUMENT_ROOT__WORKING_DIRECTORY:
        return getWorkingDirectory();
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
      case PosixPackage.DOCUMENT_ROOT__MIXED:
        ((FeatureMap.Internal)getMixed()).set(newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__ARGUMENT:
        setArgument((ArgumentType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__CORE_DUMP_LIMIT:
        setCoreDumpLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        setCPUTimeLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT:
        setDataSegmentLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__ENVIRONMENT:
        setEnvironment((EnvironmentType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__ERROR:
        setError((FileNameType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__EXECUTABLE:
        setExecutable((FileNameType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__FILE_SIZE_LIMIT:
        setFileSizeLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__GROUP_NAME:
        setGroupName((GroupNameType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__INPUT:
        setInput((FileNameType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT:
        setLockedMemoryLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__MEMORY_LIMIT:
        setMemoryLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT:
        setOpenDescriptorsLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__OUTPUT:
        setOutput((FileNameType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__PIPE_SIZE_LIMIT:
        setPipeSizeLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION:
        setPOSIXApplication((POSIXApplicationType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT:
        setProcessCountLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__STACK_SIZE_LIMIT:
        setStackSizeLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__THREAD_COUNT_LIMIT:
        setThreadCountLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__USER_NAME:
        setUserName((UserNameType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT:
        setVirtualMemoryLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        setWallTimeLimit((LimitsType)newValue);
        return;
      case PosixPackage.DOCUMENT_ROOT__WORKING_DIRECTORY:
        setWorkingDirectory((DirectoryNameType)newValue);
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
      case PosixPackage.DOCUMENT_ROOT__MIXED:
        getMixed().clear();
        return;
      case PosixPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        getXMLNSPrefixMap().clear();
        return;
      case PosixPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        getXSISchemaLocation().clear();
        return;
      case PosixPackage.DOCUMENT_ROOT__ARGUMENT:
        setArgument((ArgumentType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__CORE_DUMP_LIMIT:
        setCoreDumpLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        setCPUTimeLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT:
        setDataSegmentLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__ENVIRONMENT:
        setEnvironment((EnvironmentType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__ERROR:
        setError((FileNameType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__EXECUTABLE:
        setExecutable((FileNameType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__FILE_SIZE_LIMIT:
        setFileSizeLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__GROUP_NAME:
        setGroupName((GroupNameType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__INPUT:
        setInput((FileNameType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT:
        setLockedMemoryLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__MEMORY_LIMIT:
        setMemoryLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT:
        setOpenDescriptorsLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__OUTPUT:
        setOutput((FileNameType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__PIPE_SIZE_LIMIT:
        setPipeSizeLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION:
        setPOSIXApplication((POSIXApplicationType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT:
        setProcessCountLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__STACK_SIZE_LIMIT:
        setStackSizeLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__THREAD_COUNT_LIMIT:
        setThreadCountLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__USER_NAME:
        setUserName((UserNameType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT:
        setVirtualMemoryLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        setWallTimeLimit((LimitsType)null);
        return;
      case PosixPackage.DOCUMENT_ROOT__WORKING_DIRECTORY:
        setWorkingDirectory((DirectoryNameType)null);
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
      case PosixPackage.DOCUMENT_ROOT__MIXED:
        return mixed != null && !mixed.isEmpty();
      case PosixPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
      case PosixPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
      case PosixPackage.DOCUMENT_ROOT__ARGUMENT:
        return getArgument() != null;
      case PosixPackage.DOCUMENT_ROOT__CORE_DUMP_LIMIT:
        return getCoreDumpLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__CPU_TIME_LIMIT:
        return getCPUTimeLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT:
        return getDataSegmentLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__ENVIRONMENT:
        return getEnvironment() != null;
      case PosixPackage.DOCUMENT_ROOT__ERROR:
        return getError() != null;
      case PosixPackage.DOCUMENT_ROOT__EXECUTABLE:
        return getExecutable() != null;
      case PosixPackage.DOCUMENT_ROOT__FILE_SIZE_LIMIT:
        return getFileSizeLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__GROUP_NAME:
        return getGroupName() != null;
      case PosixPackage.DOCUMENT_ROOT__INPUT:
        return getInput() != null;
      case PosixPackage.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT:
        return getLockedMemoryLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__MEMORY_LIMIT:
        return getMemoryLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT:
        return getOpenDescriptorsLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__OUTPUT:
        return getOutput() != null;
      case PosixPackage.DOCUMENT_ROOT__PIPE_SIZE_LIMIT:
        return getPipeSizeLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION:
        return getPOSIXApplication() != null;
      case PosixPackage.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT:
        return getProcessCountLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__STACK_SIZE_LIMIT:
        return getStackSizeLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__THREAD_COUNT_LIMIT:
        return getThreadCountLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__USER_NAME:
        return getUserName() != null;
      case PosixPackage.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT:
        return getVirtualMemoryLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__WALL_TIME_LIMIT:
        return getWallTimeLimit() != null;
      case PosixPackage.DOCUMENT_ROOT__WORKING_DIRECTORY:
        return getWorkingDirectory() != null;
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