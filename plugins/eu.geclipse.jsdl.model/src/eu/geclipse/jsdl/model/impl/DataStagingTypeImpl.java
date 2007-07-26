/**
 * <copyright>
 * </copyright>
 *
 * $Id: DataStagingTypeImpl.java,v 1.2 2007/03/01 09:15:18 emstamou Exp $
 */
package eu.geclipse.jsdl.model.impl;

import eu.geclipse.jsdl.model.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.SourceTargetType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Staging Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getFileName <em>File Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getFilesystemName <em>Filesystem Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getCreationFlag <em>Creation Flag</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#isDeleteOnTermination <em>Delete On Termination</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.DataStagingTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataStagingTypeImpl extends EObjectImpl implements DataStagingType 
{
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
   * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFileName()
   * @generated
   * @ordered
   */
	protected String fileName = FILE_NAME_EDEFAULT;

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
   * The cached value of the '{@link #getFilesystemName() <em>Filesystem Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFilesystemName()
   * @generated
   * @ordered
   */
	protected String filesystemName = FILESYSTEM_NAME_EDEFAULT;

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
   * The cached value of the '{@link #getCreationFlag() <em>Creation Flag</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getCreationFlag()
   * @generated
   * @ordered
   */
	protected CreationFlagEnumeration creationFlag = CREATION_FLAG_EDEFAULT;

  /**
   * This is true if the Creation Flag attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean creationFlagESet = false;

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
   * The cached value of the '{@link #isDeleteOnTermination() <em>Delete On Termination</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isDeleteOnTermination()
   * @generated
   * @ordered
   */
	protected boolean deleteOnTermination = DELETE_ON_TERMINATION_EDEFAULT;

  /**
   * This is true if the Delete On Termination attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean deleteOnTerminationESet = false;

  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
	protected SourceTargetType source = null;

  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
	protected SourceTargetType target = null;

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
	protected DataStagingTypeImpl()
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
    return JsdlPackage.Literals.DATA_STAGING_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getFileName()
  {
    return fileName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileName(String newFileName)
  {
    String oldFileName = fileName;
    fileName = newFileName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__FILE_NAME, oldFileName, fileName));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getFilesystemName()
  {
    return filesystemName;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFilesystemName(String newFilesystemName)
  {
    String oldFilesystemName = filesystemName;
    filesystemName = newFilesystemName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME, oldFilesystemName, filesystemName));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public CreationFlagEnumeration getCreationFlag()
  {
    return creationFlag;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setCreationFlag(CreationFlagEnumeration newCreationFlag)
  {
    CreationFlagEnumeration oldCreationFlag = creationFlag;
    creationFlag = newCreationFlag == null ? CREATION_FLAG_EDEFAULT : newCreationFlag;
    boolean oldCreationFlagESet = creationFlagESet;
    creationFlagESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG, oldCreationFlag, creationFlag, !oldCreationFlagESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void unsetCreationFlag()
  {
    CreationFlagEnumeration oldCreationFlag = creationFlag;
    boolean oldCreationFlagESet = creationFlagESet;
    creationFlag = CREATION_FLAG_EDEFAULT;
    creationFlagESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG, oldCreationFlag, CREATION_FLAG_EDEFAULT, oldCreationFlagESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isSetCreationFlag()
  {
    return creationFlagESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isDeleteOnTermination()
  {
    return deleteOnTermination;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDeleteOnTermination(boolean newDeleteOnTermination)
  {
    boolean oldDeleteOnTermination = deleteOnTermination;
    deleteOnTermination = newDeleteOnTermination;
    boolean oldDeleteOnTerminationESet = deleteOnTerminationESet;
    deleteOnTerminationESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION, oldDeleteOnTermination, deleteOnTermination, !oldDeleteOnTerminationESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void unsetDeleteOnTermination()
  {
    boolean oldDeleteOnTermination = deleteOnTermination;
    boolean oldDeleteOnTerminationESet = deleteOnTerminationESet;
    deleteOnTermination = DELETE_ON_TERMINATION_EDEFAULT;
    deleteOnTerminationESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION, oldDeleteOnTermination, DELETE_ON_TERMINATION_EDEFAULT, oldDeleteOnTerminationESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isSetDeleteOnTermination()
  {
    return deleteOnTerminationESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public SourceTargetType getSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetSource(SourceTargetType newSource, NotificationChain msgs)
  {
    SourceTargetType oldSource = source;
    source = newSource;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__SOURCE, oldSource, newSource);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setSource(SourceTargetType newSource)
  {
    if (newSource != source)
    {
      NotificationChain msgs = null;
      if (source != null)
        msgs = ((InternalEObject)source).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.DATA_STAGING_TYPE__SOURCE, null, msgs);
      if (newSource != null)
        msgs = ((InternalEObject)newSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.DATA_STAGING_TYPE__SOURCE, null, msgs);
      msgs = basicSetSource(newSource, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__SOURCE, newSource, newSource));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public SourceTargetType getTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetTarget(SourceTargetType newTarget, NotificationChain msgs)
  {
    SourceTargetType oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__TARGET, oldTarget, newTarget);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setTarget(SourceTargetType newTarget)
  {
    if (newTarget != target)
    {
      NotificationChain msgs = null;
      if (target != null)
        msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.DATA_STAGING_TYPE__TARGET, null, msgs);
      if (newTarget != null)
        msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.DATA_STAGING_TYPE__TARGET, null, msgs);
      msgs = basicSetTarget(newTarget, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__TARGET, newTarget, newTarget));
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
      any = new BasicFeatureMap(this, JsdlPackage.DATA_STAGING_TYPE__ANY);
    }
    return any;
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
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.DATA_STAGING_TYPE__NAME, oldName, name));
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
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE);
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
      case JsdlPackage.DATA_STAGING_TYPE__SOURCE:
        return basicSetSource(null, msgs);
      case JsdlPackage.DATA_STAGING_TYPE__TARGET:
        return basicSetTarget(null, msgs);
      case JsdlPackage.DATA_STAGING_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.DATA_STAGING_TYPE__FILE_NAME:
        return getFileName();
      case JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME:
        return getFilesystemName();
      case JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG:
        return getCreationFlag();
      case JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION:
        return isDeleteOnTermination() ? Boolean.TRUE : Boolean.FALSE;
      case JsdlPackage.DATA_STAGING_TYPE__SOURCE:
        return getSource();
      case JsdlPackage.DATA_STAGING_TYPE__TARGET:
        return getTarget();
      case JsdlPackage.DATA_STAGING_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.DATA_STAGING_TYPE__NAME:
        return getName();
      case JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.DATA_STAGING_TYPE__FILE_NAME:
        setFileName((String)newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME:
        setFilesystemName((String)newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG:
        setCreationFlag((CreationFlagEnumeration)newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION:
        setDeleteOnTermination(((Boolean)newValue).booleanValue());
        return;
      case JsdlPackage.DATA_STAGING_TYPE__SOURCE:
        setSource((SourceTargetType)newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__TARGET:
        setTarget((SourceTargetType)newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__NAME:
        setName((String)newValue);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.DATA_STAGING_TYPE__FILE_NAME:
        setFileName(FILE_NAME_EDEFAULT);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME:
        setFilesystemName(FILESYSTEM_NAME_EDEFAULT);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG:
        unsetCreationFlag();
        return;
      case JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION:
        unsetDeleteOnTermination();
        return;
      case JsdlPackage.DATA_STAGING_TYPE__SOURCE:
        setSource((SourceTargetType)null);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__TARGET:
        setTarget((SourceTargetType)null);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.DATA_STAGING_TYPE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.DATA_STAGING_TYPE__FILE_NAME:
        return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
      case JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME:
        return FILESYSTEM_NAME_EDEFAULT == null ? filesystemName != null : !FILESYSTEM_NAME_EDEFAULT.equals(filesystemName);
      case JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG:
        return isSetCreationFlag();
      case JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION:
        return isSetDeleteOnTermination();
      case JsdlPackage.DATA_STAGING_TYPE__SOURCE:
        return source != null;
      case JsdlPackage.DATA_STAGING_TYPE__TARGET:
        return target != null;
      case JsdlPackage.DATA_STAGING_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.DATA_STAGING_TYPE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (fileName: ");
    result.append(fileName);
    result.append(", filesystemName: ");
    result.append(filesystemName);
    result.append(", creationFlag: ");
    if (creationFlagESet) result.append(creationFlag); else result.append("<unset>");
    result.append(", deleteOnTermination: ");
    if (deleteOnTerminationESet) result.append(deleteOnTermination); else result.append("<unset>");
    result.append(", any: ");
    result.append(any);
    result.append(", name: ");
    result.append(name);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //DataStagingTypeImpl