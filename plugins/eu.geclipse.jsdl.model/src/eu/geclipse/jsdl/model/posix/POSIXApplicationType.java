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
 *    Mathias Stümpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.posix;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>POSIX Application Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getExecutable <em>Executable</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getArgument <em>Argument</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getInput <em>Input</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOutput <em>Output</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getError <em>Error</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWorkingDirectory <em>Working Directory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getEnvironment <em>Environment</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getFileSizeLimit <em>File Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCoreDumpLimit <em>Core Dump Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getDataSegmentLimit <em>Data Segment Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getLockedMemoryLimit <em>Locked Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getMemoryLimit <em>Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOpenDescriptorsLimit <em>Open Descriptors Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getPipeSizeLimit <em>Pipe Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getStackSizeLimit <em>Stack Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getProcessCountLimit <em>Process Count Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getVirtualMemoryLimit <em>Virtual Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getThreadCountLimit <em>Thread Count Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getUserName <em>User Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType()
 * @model extendedMetaData="name='POSIXApplication_Type' kind='elementOnly'"
 * @generated
 */
public interface POSIXApplicationType extends EObject
{
  /**
   * Returns the value of the '<em><b>Executable</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Executable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Executable</em>' containment reference.
   * @see #setExecutable(FileNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Executable()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Executable' namespace='##targetNamespace'"
   * @generated
   */
	FileNameType getExecutable();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getExecutable <em>Executable</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Executable</em>' containment reference.
   * @see #getExecutable()
   * @generated
   */
	void setExecutable(FileNameType value);

  /**
   * Returns the value of the '<em><b>Argument</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.posix.ArgumentType}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Argument</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Argument</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Argument()
   * @model type="eu.geclipse.jsdl.model.posix.ArgumentType" containment="true"
   *        extendedMetaData="kind='element' name='Argument' namespace='##targetNamespace'"
   * @generated
   */
	EList getArgument();

  /**
   * Returns the value of the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Input</em>' containment reference.
   * @see #setInput(FileNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Input()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Input' namespace='##targetNamespace'"
   * @generated
   */
	FileNameType getInput();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getInput <em>Input</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Input</em>' containment reference.
   * @see #getInput()
   * @generated
   */
	void setInput(FileNameType value);

  /**
   * Returns the value of the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Output</em>' containment reference.
   * @see #setOutput(FileNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Output()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Output' namespace='##targetNamespace'"
   * @generated
   */
	FileNameType getOutput();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOutput <em>Output</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Output</em>' containment reference.
   * @see #getOutput()
   * @generated
   */
	void setOutput(FileNameType value);

  /**
   * Returns the value of the '<em><b>Error</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Error</em>' containment reference.
   * @see #setError(FileNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Error()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='Error' namespace='##targetNamespace'"
   * @generated
   */
	FileNameType getError();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getError <em>Error</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Error</em>' containment reference.
   * @see #getError()
   * @generated
   */
	void setError(FileNameType value);

  /**
   * Returns the value of the '<em><b>Working Directory</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Working Directory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Working Directory</em>' containment reference.
   * @see #setWorkingDirectory(DirectoryNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_WorkingDirectory()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='WorkingDirectory' namespace='##targetNamespace'"
   * @generated
   */
	DirectoryNameType getWorkingDirectory();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWorkingDirectory <em>Working Directory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Working Directory</em>' containment reference.
   * @see #getWorkingDirectory()
   * @generated
   */
	void setWorkingDirectory(DirectoryNameType value);

  /**
   * Returns the value of the '<em><b>Environment</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.posix.EnvironmentType}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Environment</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Environment</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Environment()
   * @model type="eu.geclipse.jsdl.model.posix.EnvironmentType" containment="true"
   *        extendedMetaData="kind='element' name='Environment' namespace='##targetNamespace'"
   * @generated
   */
	EList getEnvironment();

  /**
   * Returns the value of the '<em><b>Wall Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wall Time Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Wall Time Limit</em>' containment reference.
   * @see #setWallTimeLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_WallTimeLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='WallTimeLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getWallTimeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getWallTimeLimit <em>Wall Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Wall Time Limit</em>' containment reference.
   * @see #getWallTimeLimit()
   * @generated
   */
	void setWallTimeLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>File Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Size Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>File Size Limit</em>' containment reference.
   * @see #setFileSizeLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_FileSizeLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='FileSizeLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getFileSizeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getFileSizeLimit <em>File Size Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>File Size Limit</em>' containment reference.
   * @see #getFileSizeLimit()
   * @generated
   */
	void setFileSizeLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Core Dump Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Core Dump Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Core Dump Limit</em>' containment reference.
   * @see #setCoreDumpLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_CoreDumpLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='CoreDumpLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getCoreDumpLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCoreDumpLimit <em>Core Dump Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Core Dump Limit</em>' containment reference.
   * @see #getCoreDumpLimit()
   * @generated
   */
	void setCoreDumpLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Data Segment Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Segment Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Data Segment Limit</em>' containment reference.
   * @see #setDataSegmentLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_DataSegmentLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='DataSegmentLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getDataSegmentLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getDataSegmentLimit <em>Data Segment Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Data Segment Limit</em>' containment reference.
   * @see #getDataSegmentLimit()
   * @generated
   */
	void setDataSegmentLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Locked Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locked Memory Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Locked Memory Limit</em>' containment reference.
   * @see #setLockedMemoryLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_LockedMemoryLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='LockedMemoryLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getLockedMemoryLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getLockedMemoryLimit <em>Locked Memory Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Locked Memory Limit</em>' containment reference.
   * @see #getLockedMemoryLimit()
   * @generated
   */
	void setLockedMemoryLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Memory Limit</em>' containment reference.
   * @see #setMemoryLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_MemoryLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='MemoryLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getMemoryLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getMemoryLimit <em>Memory Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Memory Limit</em>' containment reference.
   * @see #getMemoryLimit()
   * @generated
   */
	void setMemoryLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Open Descriptors Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open Descriptors Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Open Descriptors Limit</em>' containment reference.
   * @see #setOpenDescriptorsLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_OpenDescriptorsLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='OpenDescriptorsLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getOpenDescriptorsLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getOpenDescriptorsLimit <em>Open Descriptors Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Open Descriptors Limit</em>' containment reference.
   * @see #getOpenDescriptorsLimit()
   * @generated
   */
	void setOpenDescriptorsLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Pipe Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pipe Size Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Pipe Size Limit</em>' containment reference.
   * @see #setPipeSizeLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_PipeSizeLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='PipeSizeLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getPipeSizeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getPipeSizeLimit <em>Pipe Size Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Pipe Size Limit</em>' containment reference.
   * @see #getPipeSizeLimit()
   * @generated
   */
	void setPipeSizeLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Stack Size Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stack Size Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Stack Size Limit</em>' containment reference.
   * @see #setStackSizeLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_StackSizeLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='StackSizeLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getStackSizeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getStackSizeLimit <em>Stack Size Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Stack Size Limit</em>' containment reference.
   * @see #getStackSizeLimit()
   * @generated
   */
	void setStackSizeLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>CPU Time Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CPU Time Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>CPU Time Limit</em>' containment reference.
   * @see #setCPUTimeLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_CPUTimeLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='CPUTimeLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getCPUTimeLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getCPUTimeLimit <em>CPU Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Time Limit</em>' containment reference.
   * @see #getCPUTimeLimit()
   * @generated
   */
	void setCPUTimeLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Process Count Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Count Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Process Count Limit</em>' containment reference.
   * @see #setProcessCountLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_ProcessCountLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='ProcessCountLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getProcessCountLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getProcessCountLimit <em>Process Count Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Process Count Limit</em>' containment reference.
   * @see #getProcessCountLimit()
   * @generated
   */
	void setProcessCountLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Virtual Memory Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual Memory Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Virtual Memory Limit</em>' containment reference.
   * @see #setVirtualMemoryLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_VirtualMemoryLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='VirtualMemoryLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getVirtualMemoryLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getVirtualMemoryLimit <em>Virtual Memory Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Virtual Memory Limit</em>' containment reference.
   * @see #getVirtualMemoryLimit()
   * @generated
   */
	void setVirtualMemoryLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>Thread Count Limit</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thread Count Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Thread Count Limit</em>' containment reference.
   * @see #setThreadCountLimit(LimitsType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_ThreadCountLimit()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='ThreadCountLimit' namespace='##targetNamespace'"
   * @generated
   */
	LimitsType getThreadCountLimit();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getThreadCountLimit <em>Thread Count Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Thread Count Limit</em>' containment reference.
   * @see #getThreadCountLimit()
   * @generated
   */
	void setThreadCountLimit(LimitsType value);

  /**
   * Returns the value of the '<em><b>User Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>User Name</em>' containment reference.
   * @see #setUserName(UserNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_UserName()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='UserName' namespace='##targetNamespace'"
   * @generated
   */
	UserNameType getUserName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getUserName <em>User Name</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>User Name</em>' containment reference.
   * @see #getUserName()
   * @generated
   */
	void setUserName(UserNameType value);

  /**
   * Returns the value of the '<em><b>Group Name</b></em>' containment reference.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Group Name</em>' containment reference.
   * @see #setGroupName(GroupNameType)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_GroupName()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='GroupName' namespace='##targetNamespace'"
   * @generated
   */
	GroupNameType getGroupName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getGroupName <em>Group Name</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Group Name</em>' containment reference.
   * @see #getGroupName()
   * @generated
   */
	void setGroupName(GroupNameType value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_Name()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName"
   *        extendedMetaData="kind='attribute' name='name'"
   * @generated
   */
	String getName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.posix.POSIXApplicationType#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
	void setName(String value);

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
   * @see eu.geclipse.jsdl.model.posix.PosixPackage#getPOSIXApplicationType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':23' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // POSIXApplicationType