/**
 * <copyright>
 * </copyright>
 *
 * $Id: FileSystemTypeImpl.java,v 1.2 2007/03/01 09:15:17 emstamou Exp $
 */
package eu.geclipse.jsdl.model.impl;

import eu.geclipse.jsdl.model.FileSystemType;
import eu.geclipse.jsdl.model.FileSystemTypeEnumeration;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.RangeValueType;

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
 * An implementation of the model object '<em><b>File System Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getFileSystemType <em>File System Type</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getMountPoint <em>Mount Point</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getDiskSpace <em>Disk Space</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.impl.FileSystemTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FileSystemTypeImpl extends EObjectImpl implements FileSystemType 
{
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
   * The cached value of the '{@link #getFileSystemType() <em>File System Type</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getFileSystemType()
   * @generated
   * @ordered
   */
	protected FileSystemTypeEnumeration fileSystemType = FILE_SYSTEM_TYPE_EDEFAULT;

  /**
   * This is true if the File System Type attribute has been set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
	protected boolean fileSystemTypeESet = false;

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
   * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
	protected String description = DESCRIPTION_EDEFAULT;

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
   * The cached value of the '{@link #getMountPoint() <em>Mount Point</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getMountPoint()
   * @generated
   * @ordered
   */
	protected String mountPoint = MOUNT_POINT_EDEFAULT;

  /**
   * The cached value of the '{@link #getDiskSpace() <em>Disk Space</em>}' containment reference.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #getDiskSpace()
   * @generated
   * @ordered
   */
	protected RangeValueType diskSpace = null;

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
	protected FileSystemTypeImpl()
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
    return JsdlPackage.Literals.FILE_SYSTEM_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public FileSystemTypeEnumeration getFileSystemType()
  {
    return fileSystemType;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setFileSystemType(FileSystemTypeEnumeration newFileSystemType)
  {
    FileSystemTypeEnumeration oldFileSystemType = fileSystemType;
    fileSystemType = newFileSystemType == null ? FILE_SYSTEM_TYPE_EDEFAULT : newFileSystemType;
    boolean oldFileSystemTypeESet = fileSystemTypeESet;
    fileSystemTypeESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE, oldFileSystemType, fileSystemType, !oldFileSystemTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void unsetFileSystemType()
  {
    FileSystemTypeEnumeration oldFileSystemType = fileSystemType;
    boolean oldFileSystemTypeESet = fileSystemTypeESet;
    fileSystemType = FILE_SYSTEM_TYPE_EDEFAULT;
    fileSystemTypeESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, JsdlPackage.FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE, oldFileSystemType, FILE_SYSTEM_TYPE_EDEFAULT, oldFileSystemTypeESet));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public boolean isSetFileSystemType()
  {
    return fileSystemTypeESet;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDescription(String newDescription)
  {
    String oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION, oldDescription, description));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getMountPoint()
  {
    return mountPoint;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setMountPoint(String newMountPoint)
  {
    String oldMountPoint = mountPoint;
    mountPoint = newMountPoint;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT, oldMountPoint, mountPoint));
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public RangeValueType getDiskSpace()
  {
    return diskSpace;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public NotificationChain basicSetDiskSpace(RangeValueType newDiskSpace, NotificationChain msgs)
  {
    RangeValueType oldDiskSpace = diskSpace;
    diskSpace = newDiskSpace;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE, oldDiskSpace, newDiskSpace);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void setDiskSpace(RangeValueType newDiskSpace)
  {
    if (newDiskSpace != diskSpace)
    {
      NotificationChain msgs = null;
      if (diskSpace != null)
        msgs = ((InternalEObject)diskSpace).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE, null, msgs);
      if (newDiskSpace != null)
        msgs = ((InternalEObject)newDiskSpace).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE, null, msgs);
      msgs = basicSetDiskSpace(newDiskSpace, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE, newDiskSpace, newDiskSpace));
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
      any = new BasicFeatureMap(this, JsdlPackage.FILE_SYSTEM_TYPE__ANY);
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
      eNotify(new ENotificationImpl(this, Notification.SET, JsdlPackage.FILE_SYSTEM_TYPE__NAME, oldName, name));
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
      anyAttribute = new BasicFeatureMap(this, JsdlPackage.FILE_SYSTEM_TYPE__ANY_ATTRIBUTE);
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
      case JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE:
        return basicSetDiskSpace(null, msgs);
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY:
        return ((InternalEList)getAny()).basicRemove(otherEnd, msgs);
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE:
        return getFileSystemType();
      case JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION:
        return getDescription();
      case JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT:
        return getMountPoint();
      case JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE:
        return getDiskSpace();
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY:
        if (coreType) return getAny();
        return ((FeatureMap.Internal)getAny()).getWrapper();
      case JsdlPackage.FILE_SYSTEM_TYPE__NAME:
        return getName();
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE:
        setFileSystemType((FileSystemTypeEnumeration)newValue);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION:
        setDescription((String)newValue);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT:
        setMountPoint((String)newValue);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE:
        setDiskSpace((RangeValueType)newValue);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY:
        ((FeatureMap.Internal)getAny()).set(newValue);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__NAME:
        setName((String)newValue);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE:
        unsetFileSystemType();
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION:
        setDescription(DESCRIPTION_EDEFAULT);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT:
        setMountPoint(MOUNT_POINT_EDEFAULT);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE:
        setDiskSpace((RangeValueType)null);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY:
        getAny().clear();
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
      case JsdlPackage.FILE_SYSTEM_TYPE__FILE_SYSTEM_TYPE:
        return isSetFileSystemType();
      case JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION:
        return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
      case JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT:
        return MOUNT_POINT_EDEFAULT == null ? mountPoint != null : !MOUNT_POINT_EDEFAULT.equals(mountPoint);
      case JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE:
        return diskSpace != null;
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY:
        return any != null && !any.isEmpty();
      case JsdlPackage.FILE_SYSTEM_TYPE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case JsdlPackage.FILE_SYSTEM_TYPE__ANY_ATTRIBUTE:
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
    result.append(" (fileSystemType: ");
    if (fileSystemTypeESet) result.append(fileSystemType); else result.append("<unset>");
    result.append(", description: ");
    result.append(description);
    result.append(", mountPoint: ");
    result.append(mountPoint);
    result.append(", any: ");
    result.append(any);
    result.append(", name: ");
    result.append(name);
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //FileSystemTypeImpl