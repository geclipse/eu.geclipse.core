/**
 * <copyright>
 * </copyright>
 *
 * $Id: POSIXApplicationTypeImpl.java,v 1.2 2007/03/01 09:15:17 emstamou Exp $
 */
package eu.geclipse.jsdl.model.posix.impl;

import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.DirectoryNameType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.GroupNameType;
import eu.geclipse.jsdl.model.posix.LimitsType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.UserNameType;

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
 * An implementation of the model object '<em><b>POSIX Application Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getExecutable <em>Executable</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getArgument <em>Argument</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getInput <em>Input</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getOutput <em>Output</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getError <em>Error</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getWorkingDirectory <em>Working Directory</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getEnvironment <em>Environment</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getWallTimeLimit <em>Wall Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getFileSizeLimit <em>File Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getCoreDumpLimit <em>Core Dump Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getDataSegmentLimit <em>Data Segment Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getLockedMemoryLimit <em>Locked Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getMemoryLimit <em>Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getOpenDescriptorsLimit <em>Open Descriptors Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getPipeSizeLimit <em>Pipe Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getStackSizeLimit <em>Stack Size Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getCPUTimeLimit <em>CPU Time Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getProcessCountLimit <em>Process Count Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getVirtualMemoryLimit <em>Virtual Memory Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getThreadCountLimit <em>Thread Count Limit</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class POSIXApplicationTypeImpl extends EObjectImpl implements POSIXApplicationType 
{
  /**
   * The cached value of the '{@link #getExecutable() <em>Executable</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getExecutable()
   * @generated
   * @ordered
   */
	protected FileNameType executable = null;

  /**
   * The cached value of the '{@link #getArgument() <em>Argument</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getArgument()
   * @generated
   * @ordered
   */
	protected EList argument = null;

  /**
   * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getInput()
   * @generated
   * @ordered
   */
	protected FileNameType input = null;

  /**
   * The cached value of the '{@link #getOutput() <em>Output</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOutput()
   * @generated
   * @ordered
   */
	protected FileNameType output = null;

  /**
   * The cached value of the '{@link #getError() <em>Error</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getError()
   * @generated
   * @ordered
   */
	protected FileNameType error = null;

  /**
   * The cached value of the '{@link #getWorkingDirectory() <em>Working Directory</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getWorkingDirectory()
   * @generated
   * @ordered
   */
	protected DirectoryNameType workingDirectory = null;

  /**
   * The cached value of the '{@link #getEnvironment() <em>Environment</em>}' containment reference list.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getEnvironment()
   * @generated
   * @ordered
   */
	protected EList environment = null;

  /**
   * The cached value of the '{@link #getWallTimeLimit() <em>Wall Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getWallTimeLimit()
   * @generated
   * @ordered
   */
	protected LimitsType wallTimeLimit = null;

  /**
   * The cached value of the '{@link #getFileSizeLimit() <em>File Size Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFileSizeLimit()
   * @generated
   * @ordered
   */
	protected LimitsType fileSizeLimit = null;

  /**
   * The cached value of the '{@link #getCoreDumpLimit() <em>Core Dump Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCoreDumpLimit()
   * @generated
   * @ordered
   */
	protected LimitsType coreDumpLimit = null;

  /**
   * The cached value of the '{@link #getDataSegmentLimit() <em>Data Segment Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getDataSegmentLimit()
   * @generated
   * @ordered
   */
	protected LimitsType dataSegmentLimit = null;

  /**
   * The cached value of the '{@link #getLockedMemoryLimit() <em>Locked Memory Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getLockedMemoryLimit()
   * @generated
   * @ordered
   */
	protected LimitsType lockedMemoryLimit = null;

  /**
   * The cached value of the '{@link #getMemoryLimit() <em>Memory Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getMemoryLimit()
   * @generated
   * @ordered
   */
	protected LimitsType memoryLimit = null;

  /**
   * The cached value of the '{@link #getOpenDescriptorsLimit() <em>Open Descriptors Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getOpenDescriptorsLimit()
   * @generated
   * @ordered
   */
	protected LimitsType openDescriptorsLimit = null;

  /**
   * The cached value of the '{@link #getPipeSizeLimit() <em>Pipe Size Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getPipeSizeLimit()
   * @generated
   * @ordered
   */
	protected LimitsType pipeSizeLimit = null;

  /**
   * The cached value of the '{@link #getStackSizeLimit() <em>Stack Size Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getStackSizeLimit()
   * @generated
   * @ordered
   */
	protected LimitsType stackSizeLimit = null;

  /**
   * The cached value of the '{@link #getCPUTimeLimit() <em>CPU Time Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCPUTimeLimit()
   * @generated
   * @ordered
   */
	protected LimitsType cPUTimeLimit = null;

  /**
   * The cached value of the '{@link #getProcessCountLimit() <em>Process Count Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getProcessCountLimit()
   * @generated
   * @ordered
   */
	protected LimitsType processCountLimit = null;

  /**
   * The cached value of the '{@link #getVirtualMemoryLimit() <em>Virtual Memory Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getVirtualMemoryLimit()
   * @generated
   * @ordered
   */
	protected LimitsType virtualMemoryLimit = null;

  /**
   * The cached value of the '{@link #getThreadCountLimit() <em>Thread Count Limit</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getThreadCountLimit()
   * @generated
   * @ordered
   */
	protected LimitsType threadCountLimit = null;

  /**
   * The cached value of the '{@link #getUserName() <em>User Name</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getUserName()
   * @generated
   * @ordered
   */
	protected UserNameType userName = null;

  /**
   * The cached value of the '{@link #getGroupName() <em>Group Name</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getGroupName()
   * @generated
   * @ordered
   */
	protected GroupNameType groupName = null;

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
	protected POSIXApplicationTypeImpl()
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
    return PosixPackage.Literals.POSIX_APPLICATION_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getExecutable()
  {
    return executable;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetExecutable(FileNameType newExecutable, NotificationChain msgs)
  {
    FileNameType oldExecutable = executable;
    executable = newExecutable;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE, oldExecutable, newExecutable);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setExecutable(FileNameType newExecutable)
  {
    if (newExecutable != executable)
    {
      NotificationChain msgs = null;
      if (executable != null)
        msgs = ((InternalEObject)executable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE, null, msgs);
      if (newExecutable != null)
        msgs = ((InternalEObject)newExecutable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE, null, msgs);
      msgs = basicSetExecutable(newExecutable, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE, newExecutable, newExecutable));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EList getArgument()
  {
    if (argument == null)
    {
      argument = new EObjectContainmentEList(ArgumentType.class, this, PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT);
    }
    return argument;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getInput()
  {
    return input;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetInput(FileNameType newInput, NotificationChain msgs)
  {
    FileNameType oldInput = input;
    input = newInput;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__INPUT, oldInput, newInput);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setInput(FileNameType newInput)
  {
    if (newInput != input)
    {
      NotificationChain msgs = null;
      if (input != null)
        msgs = ((InternalEObject)input).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__INPUT, null, msgs);
      if (newInput != null)
        msgs = ((InternalEObject)newInput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__INPUT, null, msgs);
      msgs = basicSetInput(newInput, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__INPUT, newInput, newInput));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getOutput()
  {
    return output;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOutput(FileNameType newOutput, NotificationChain msgs)
  {
    FileNameType oldOutput = output;
    output = newOutput;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT, oldOutput, newOutput);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOutput(FileNameType newOutput)
  {
    if (newOutput != output)
    {
      NotificationChain msgs = null;
      if (output != null)
        msgs = ((InternalEObject)output).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT, null, msgs);
      if (newOutput != null)
        msgs = ((InternalEObject)newOutput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT, null, msgs);
      msgs = basicSetOutput(newOutput, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT, newOutput, newOutput));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileNameType getError()
  {
    return error;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetError(FileNameType newError, NotificationChain msgs)
  {
    FileNameType oldError = error;
    error = newError;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__ERROR, oldError, newError);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setError(FileNameType newError)
  {
    if (newError != error)
    {
      NotificationChain msgs = null;
      if (error != null)
        msgs = ((InternalEObject)error).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__ERROR, null, msgs);
      if (newError != null)
        msgs = ((InternalEObject)newError).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__ERROR, null, msgs);
      msgs = basicSetError(newError, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__ERROR, newError, newError));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DirectoryNameType getWorkingDirectory()
  {
    return workingDirectory;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetWorkingDirectory(DirectoryNameType newWorkingDirectory, NotificationChain msgs)
  {
    DirectoryNameType oldWorkingDirectory = workingDirectory;
    workingDirectory = newWorkingDirectory;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY, oldWorkingDirectory, newWorkingDirectory);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setWorkingDirectory(DirectoryNameType newWorkingDirectory)
  {
    if (newWorkingDirectory != workingDirectory)
    {
      NotificationChain msgs = null;
      if (workingDirectory != null)
        msgs = ((InternalEObject)workingDirectory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY, null, msgs);
      if (newWorkingDirectory != null)
        msgs = ((InternalEObject)newWorkingDirectory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY, null, msgs);
      msgs = basicSetWorkingDirectory(newWorkingDirectory, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY, newWorkingDirectory, newWorkingDirectory));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EList getEnvironment()
  {
    if (environment == null)
    {
      environment = new EObjectContainmentEList(EnvironmentType.class, this, PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT);
    }
    return environment;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getWallTimeLimit()
  {
    return wallTimeLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetWallTimeLimit(LimitsType newWallTimeLimit, NotificationChain msgs)
  {
    LimitsType oldWallTimeLimit = wallTimeLimit;
    wallTimeLimit = newWallTimeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT, oldWallTimeLimit, newWallTimeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setWallTimeLimit(LimitsType newWallTimeLimit)
  {
    if (newWallTimeLimit != wallTimeLimit)
    {
      NotificationChain msgs = null;
      if (wallTimeLimit != null)
        msgs = ((InternalEObject)wallTimeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT, null, msgs);
      if (newWallTimeLimit != null)
        msgs = ((InternalEObject)newWallTimeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT, null, msgs);
      msgs = basicSetWallTimeLimit(newWallTimeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT, newWallTimeLimit, newWallTimeLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getFileSizeLimit()
  {
    return fileSizeLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetFileSizeLimit(LimitsType newFileSizeLimit, NotificationChain msgs)
  {
    LimitsType oldFileSizeLimit = fileSizeLimit;
    fileSizeLimit = newFileSizeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT, oldFileSizeLimit, newFileSizeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileSizeLimit(LimitsType newFileSizeLimit)
  {
    if (newFileSizeLimit != fileSizeLimit)
    {
      NotificationChain msgs = null;
      if (fileSizeLimit != null)
        msgs = ((InternalEObject)fileSizeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT, null, msgs);
      if (newFileSizeLimit != null)
        msgs = ((InternalEObject)newFileSizeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT, null, msgs);
      msgs = basicSetFileSizeLimit(newFileSizeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT, newFileSizeLimit, newFileSizeLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getCoreDumpLimit()
  {
    return coreDumpLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCoreDumpLimit(LimitsType newCoreDumpLimit, NotificationChain msgs)
  {
    LimitsType oldCoreDumpLimit = coreDumpLimit;
    coreDumpLimit = newCoreDumpLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT, oldCoreDumpLimit, newCoreDumpLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCoreDumpLimit(LimitsType newCoreDumpLimit)
  {
    if (newCoreDumpLimit != coreDumpLimit)
    {
      NotificationChain msgs = null;
      if (coreDumpLimit != null)
        msgs = ((InternalEObject)coreDumpLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT, null, msgs);
      if (newCoreDumpLimit != null)
        msgs = ((InternalEObject)newCoreDumpLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT, null, msgs);
      msgs = basicSetCoreDumpLimit(newCoreDumpLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT, newCoreDumpLimit, newCoreDumpLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getDataSegmentLimit()
  {
    return dataSegmentLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetDataSegmentLimit(LimitsType newDataSegmentLimit, NotificationChain msgs)
  {
    LimitsType oldDataSegmentLimit = dataSegmentLimit;
    dataSegmentLimit = newDataSegmentLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT, oldDataSegmentLimit, newDataSegmentLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDataSegmentLimit(LimitsType newDataSegmentLimit)
  {
    if (newDataSegmentLimit != dataSegmentLimit)
    {
      NotificationChain msgs = null;
      if (dataSegmentLimit != null)
        msgs = ((InternalEObject)dataSegmentLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT, null, msgs);
      if (newDataSegmentLimit != null)
        msgs = ((InternalEObject)newDataSegmentLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT, null, msgs);
      msgs = basicSetDataSegmentLimit(newDataSegmentLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT, newDataSegmentLimit, newDataSegmentLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getLockedMemoryLimit()
  {
    return lockedMemoryLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetLockedMemoryLimit(LimitsType newLockedMemoryLimit, NotificationChain msgs)
  {
    LimitsType oldLockedMemoryLimit = lockedMemoryLimit;
    lockedMemoryLimit = newLockedMemoryLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT, oldLockedMemoryLimit, newLockedMemoryLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setLockedMemoryLimit(LimitsType newLockedMemoryLimit)
  {
    if (newLockedMemoryLimit != lockedMemoryLimit)
    {
      NotificationChain msgs = null;
      if (lockedMemoryLimit != null)
        msgs = ((InternalEObject)lockedMemoryLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT, null, msgs);
      if (newLockedMemoryLimit != null)
        msgs = ((InternalEObject)newLockedMemoryLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT, null, msgs);
      msgs = basicSetLockedMemoryLimit(newLockedMemoryLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT, newLockedMemoryLimit, newLockedMemoryLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getMemoryLimit()
  {
    return memoryLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetMemoryLimit(LimitsType newMemoryLimit, NotificationChain msgs)
  {
    LimitsType oldMemoryLimit = memoryLimit;
    memoryLimit = newMemoryLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT, oldMemoryLimit, newMemoryLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setMemoryLimit(LimitsType newMemoryLimit)
  {
    if (newMemoryLimit != memoryLimit)
    {
      NotificationChain msgs = null;
      if (memoryLimit != null)
        msgs = ((InternalEObject)memoryLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT, null, msgs);
      if (newMemoryLimit != null)
        msgs = ((InternalEObject)newMemoryLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT, null, msgs);
      msgs = basicSetMemoryLimit(newMemoryLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT, newMemoryLimit, newMemoryLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getOpenDescriptorsLimit()
  {
    return openDescriptorsLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetOpenDescriptorsLimit(LimitsType newOpenDescriptorsLimit, NotificationChain msgs)
  {
    LimitsType oldOpenDescriptorsLimit = openDescriptorsLimit;
    openDescriptorsLimit = newOpenDescriptorsLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT, oldOpenDescriptorsLimit, newOpenDescriptorsLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setOpenDescriptorsLimit(LimitsType newOpenDescriptorsLimit)
  {
    if (newOpenDescriptorsLimit != openDescriptorsLimit)
    {
      NotificationChain msgs = null;
      if (openDescriptorsLimit != null)
        msgs = ((InternalEObject)openDescriptorsLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT, null, msgs);
      if (newOpenDescriptorsLimit != null)
        msgs = ((InternalEObject)newOpenDescriptorsLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT, null, msgs);
      msgs = basicSetOpenDescriptorsLimit(newOpenDescriptorsLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT, newOpenDescriptorsLimit, newOpenDescriptorsLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getPipeSizeLimit()
  {
    return pipeSizeLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetPipeSizeLimit(LimitsType newPipeSizeLimit, NotificationChain msgs)
  {
    LimitsType oldPipeSizeLimit = pipeSizeLimit;
    pipeSizeLimit = newPipeSizeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT, oldPipeSizeLimit, newPipeSizeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setPipeSizeLimit(LimitsType newPipeSizeLimit)
  {
    if (newPipeSizeLimit != pipeSizeLimit)
    {
      NotificationChain msgs = null;
      if (pipeSizeLimit != null)
        msgs = ((InternalEObject)pipeSizeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT, null, msgs);
      if (newPipeSizeLimit != null)
        msgs = ((InternalEObject)newPipeSizeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT, null, msgs);
      msgs = basicSetPipeSizeLimit(newPipeSizeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT, newPipeSizeLimit, newPipeSizeLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getStackSizeLimit()
  {
    return stackSizeLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetStackSizeLimit(LimitsType newStackSizeLimit, NotificationChain msgs)
  {
    LimitsType oldStackSizeLimit = stackSizeLimit;
    stackSizeLimit = newStackSizeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT, oldStackSizeLimit, newStackSizeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setStackSizeLimit(LimitsType newStackSizeLimit)
  {
    if (newStackSizeLimit != stackSizeLimit)
    {
      NotificationChain msgs = null;
      if (stackSizeLimit != null)
        msgs = ((InternalEObject)stackSizeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT, null, msgs);
      if (newStackSizeLimit != null)
        msgs = ((InternalEObject)newStackSizeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT, null, msgs);
      msgs = basicSetStackSizeLimit(newStackSizeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT, newStackSizeLimit, newStackSizeLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getCPUTimeLimit()
  {
    return cPUTimeLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetCPUTimeLimit(LimitsType newCPUTimeLimit, NotificationChain msgs)
  {
    LimitsType oldCPUTimeLimit = cPUTimeLimit;
    cPUTimeLimit = newCPUTimeLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT, oldCPUTimeLimit, newCPUTimeLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCPUTimeLimit(LimitsType newCPUTimeLimit)
  {
    if (newCPUTimeLimit != cPUTimeLimit)
    {
      NotificationChain msgs = null;
      if (cPUTimeLimit != null)
        msgs = ((InternalEObject)cPUTimeLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT, null, msgs);
      if (newCPUTimeLimit != null)
        msgs = ((InternalEObject)newCPUTimeLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT, null, msgs);
      msgs = basicSetCPUTimeLimit(newCPUTimeLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT, newCPUTimeLimit, newCPUTimeLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getProcessCountLimit()
  {
    return processCountLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetProcessCountLimit(LimitsType newProcessCountLimit, NotificationChain msgs)
  {
    LimitsType oldProcessCountLimit = processCountLimit;
    processCountLimit = newProcessCountLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT, oldProcessCountLimit, newProcessCountLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setProcessCountLimit(LimitsType newProcessCountLimit)
  {
    if (newProcessCountLimit != processCountLimit)
    {
      NotificationChain msgs = null;
      if (processCountLimit != null)
        msgs = ((InternalEObject)processCountLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT, null, msgs);
      if (newProcessCountLimit != null)
        msgs = ((InternalEObject)newProcessCountLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT, null, msgs);
      msgs = basicSetProcessCountLimit(newProcessCountLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT, newProcessCountLimit, newProcessCountLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getVirtualMemoryLimit()
  {
    return virtualMemoryLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetVirtualMemoryLimit(LimitsType newVirtualMemoryLimit, NotificationChain msgs)
  {
    LimitsType oldVirtualMemoryLimit = virtualMemoryLimit;
    virtualMemoryLimit = newVirtualMemoryLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT, oldVirtualMemoryLimit, newVirtualMemoryLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setVirtualMemoryLimit(LimitsType newVirtualMemoryLimit)
  {
    if (newVirtualMemoryLimit != virtualMemoryLimit)
    {
      NotificationChain msgs = null;
      if (virtualMemoryLimit != null)
        msgs = ((InternalEObject)virtualMemoryLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT, null, msgs);
      if (newVirtualMemoryLimit != null)
        msgs = ((InternalEObject)newVirtualMemoryLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT, null, msgs);
      msgs = basicSetVirtualMemoryLimit(newVirtualMemoryLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT, newVirtualMemoryLimit, newVirtualMemoryLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public LimitsType getThreadCountLimit()
  {
    return threadCountLimit;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetThreadCountLimit(LimitsType newThreadCountLimit, NotificationChain msgs)
  {
    LimitsType oldThreadCountLimit = threadCountLimit;
    threadCountLimit = newThreadCountLimit;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT, oldThreadCountLimit, newThreadCountLimit);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setThreadCountLimit(LimitsType newThreadCountLimit)
  {
    if (newThreadCountLimit != threadCountLimit)
    {
      NotificationChain msgs = null;
      if (threadCountLimit != null)
        msgs = ((InternalEObject)threadCountLimit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT, null, msgs);
      if (newThreadCountLimit != null)
        msgs = ((InternalEObject)newThreadCountLimit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT, null, msgs);
      msgs = basicSetThreadCountLimit(newThreadCountLimit, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT, newThreadCountLimit, newThreadCountLimit));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public UserNameType getUserName()
  {
    return userName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetUserName(UserNameType newUserName, NotificationChain msgs)
  {
    UserNameType oldUserName = userName;
    userName = newUserName;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME, oldUserName, newUserName);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setUserName(UserNameType newUserName)
  {
    if (newUserName != userName)
    {
      NotificationChain msgs = null;
      if (userName != null)
        msgs = ((InternalEObject)userName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME, null, msgs);
      if (newUserName != null)
        msgs = ((InternalEObject)newUserName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME, null, msgs);
      msgs = basicSetUserName(newUserName, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME, newUserName, newUserName));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public GroupNameType getGroupName()
  {
    return groupName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetGroupName(GroupNameType newGroupName, NotificationChain msgs)
  {
    GroupNameType oldGroupName = groupName;
    groupName = newGroupName;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME, oldGroupName, newGroupName);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setGroupName(GroupNameType newGroupName)
  {
    if (newGroupName != groupName)
    {
      NotificationChain msgs = null;
      if (groupName != null)
        msgs = ((InternalEObject)groupName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME, null, msgs);
      if (newGroupName != null)
        msgs = ((InternalEObject)newGroupName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME, null, msgs);
      msgs = basicSetGroupName(newGroupName, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME, newGroupName, newGroupName));
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
      eNotify(new ENotificationImpl(this, Notification.SET, PosixPackage.POSIX_APPLICATION_TYPE__NAME, oldName, name));
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
      anyAttribute = new BasicFeatureMap(this, PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE);
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
      case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:
        return basicSetExecutable(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:
        return ((InternalEList)getArgument()).basicRemove(otherEnd, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:
        return basicSetInput(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:
        return basicSetOutput(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:
        return basicSetError(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY:
        return basicSetWorkingDirectory(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:
        return ((InternalEList)getEnvironment()).basicRemove(otherEnd, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:
        return basicSetWallTimeLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:
        return basicSetFileSizeLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT:
        return basicSetCoreDumpLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT:
        return basicSetDataSegmentLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT:
        return basicSetLockedMemoryLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT:
        return basicSetMemoryLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT:
        return basicSetOpenDescriptorsLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT:
        return basicSetPipeSizeLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT:
        return basicSetStackSizeLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT:
        return basicSetCPUTimeLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT:
        return basicSetProcessCountLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT:
        return basicSetVirtualMemoryLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT:
        return basicSetThreadCountLimit(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME:
        return basicSetUserName(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME:
        return basicSetGroupName(null, msgs);
      case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:
        return getExecutable();
      case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:
        return getArgument();
      case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:
        return getInput();
      case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:
        return getOutput();
      case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:
        return getError();
      case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY:
        return getWorkingDirectory();
      case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:
        return getEnvironment();
      case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:
        return getWallTimeLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:
        return getFileSizeLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT:
        return getCoreDumpLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT:
        return getDataSegmentLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT:
        return getLockedMemoryLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT:
        return getMemoryLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT:
        return getOpenDescriptorsLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT:
        return getPipeSizeLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT:
        return getStackSizeLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT:
        return getCPUTimeLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT:
        return getProcessCountLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT:
        return getVirtualMemoryLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT:
        return getThreadCountLimit();
      case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME:
        return getUserName();
      case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME:
        return getGroupName();
      case PosixPackage.POSIX_APPLICATION_TYPE__NAME:
        return getName();
      case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:
        setExecutable((FileNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:
        getArgument().clear();
        getArgument().addAll((Collection)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:
        setInput((FileNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:
        setOutput((FileNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:
        setError((FileNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY:
        setWorkingDirectory((DirectoryNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:
        getEnvironment().clear();
        getEnvironment().addAll((Collection)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:
        setWallTimeLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:
        setFileSizeLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT:
        setCoreDumpLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT:
        setDataSegmentLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT:
        setLockedMemoryLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT:
        setMemoryLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT:
        setOpenDescriptorsLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT:
        setPipeSizeLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT:
        setStackSizeLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT:
        setCPUTimeLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT:
        setProcessCountLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT:
        setVirtualMemoryLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT:
        setThreadCountLimit((LimitsType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME:
        setUserName((UserNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME:
        setGroupName((GroupNameType)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__NAME:
        setName((String)newValue);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:
        setExecutable((FileNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:
        getArgument().clear();
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:
        setInput((FileNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:
        setOutput((FileNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:
        setError((FileNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY:
        setWorkingDirectory((DirectoryNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:
        getEnvironment().clear();
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:
        setWallTimeLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:
        setFileSizeLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT:
        setCoreDumpLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT:
        setDataSegmentLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT:
        setLockedMemoryLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT:
        setMemoryLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT:
        setOpenDescriptorsLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT:
        setPipeSizeLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT:
        setStackSizeLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT:
        setCPUTimeLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT:
        setProcessCountLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT:
        setVirtualMemoryLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT:
        setThreadCountLimit((LimitsType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME:
        setUserName((UserNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME:
        setGroupName((GroupNameType)null);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:
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
      case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:
        return executable != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:
        return argument != null && !argument.isEmpty();
      case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:
        return input != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:
        return output != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:
        return error != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY:
        return workingDirectory != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:
        return environment != null && !environment.isEmpty();
      case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:
        return wallTimeLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:
        return fileSizeLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT:
        return coreDumpLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT:
        return dataSegmentLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT:
        return lockedMemoryLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT:
        return memoryLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT:
        return openDescriptorsLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT:
        return pipeSizeLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT:
        return stackSizeLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT:
        return cPUTimeLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT:
        return processCountLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT:
        return virtualMemoryLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT:
        return threadCountLimit != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME:
        return userName != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME:
        return groupName != null;
      case PosixPackage.POSIX_APPLICATION_TYPE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (name: ");
    result.append(name);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //POSIXApplicationTypeImpl