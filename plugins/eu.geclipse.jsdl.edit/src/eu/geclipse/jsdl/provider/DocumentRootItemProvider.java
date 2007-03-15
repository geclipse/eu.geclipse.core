/**
 * <copyright>
 * </copyright>
 *
 * $Id: DocumentRootItemProvider.java,v 1.2 2007/03/01 09:16:01 emstamou Exp $
 */
package eu.geclipse.jsdl.provider;


import eu.geclipse.jsdl.CreationFlagEnumeration;
import eu.geclipse.jsdl.DocumentRoot;
import eu.geclipse.jsdl.FileSystemTypeEnumeration;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.ProcessorArchitectureEnumeration;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link eu.geclipse.jsdl.DocumentRoot} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DocumentRootItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public DocumentRootItemProvider(AdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public List getPropertyDescriptors(Object object)
  {
    if (itemPropertyDescriptors == null)
    {
      super.getPropertyDescriptors(object);

      addApplicationNamePropertyDescriptor(object);
      addApplicationVersionPropertyDescriptor(object);
      addCPUArchitectureNamePropertyDescriptor(object);
      addCreationFlagPropertyDescriptor(object);
      addDeleteOnTerminationPropertyDescriptor(object);
      addDescriptionPropertyDescriptor(object);
      addExclusiveExecutionPropertyDescriptor(object);
      addFileNamePropertyDescriptor(object);
      addFilesystemNamePropertyDescriptor(object);
      addFileSystemTypePropertyDescriptor(object);
      addHostNamePropertyDescriptor(object);
      addJobAnnotationPropertyDescriptor(object);
      addJobNamePropertyDescriptor(object);
      addJobProjectPropertyDescriptor(object);
      addMountPointPropertyDescriptor(object);
      addOperatingSystemNamePropertyDescriptor(object);
      addOperatingSystemVersionPropertyDescriptor(object);
      addURIPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Application Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addApplicationNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_applicationName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_applicationName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Application Version feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addApplicationVersionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_applicationVersion_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_applicationVersion_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_VERSION,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the CPU Architecture Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addCPUArchitectureNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_cPUArchitectureName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_cPUArchitectureName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Creation Flag feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addCreationFlagPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_creationFlag_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_creationFlag_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__CREATION_FLAG,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Delete On Termination feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addDeleteOnTerminationPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_deleteOnTermination_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_deleteOnTermination_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__DELETE_ON_TERMINATION,
         true,
         false,
         false,
         ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Description feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addDescriptionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_description_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_description_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__DESCRIPTION,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Exclusive Execution feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addExclusiveExecutionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_exclusiveExecution_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_exclusiveExecution_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION,
         true,
         false,
         false,
         ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the File Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addFileNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_fileName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_fileName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__FILE_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Filesystem Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addFilesystemNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_filesystemName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_filesystemName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__FILESYSTEM_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the File System Type feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addFileSystemTypePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_fileSystemType_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_fileSystemType_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM_TYPE,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Host Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addHostNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_hostName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_hostName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__HOST_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Job Annotation feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addJobAnnotationPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_jobAnnotation_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_jobAnnotation_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__JOB_ANNOTATION,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Job Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addJobNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_jobName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_jobName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__JOB_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Job Project feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addJobProjectPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_jobProject_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_jobProject_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__JOB_PROJECT,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Mount Point feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addMountPointPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_mountPoint_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_mountPoint_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__MOUNT_POINT,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Operating System Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addOperatingSystemNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_operatingSystemName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_operatingSystemName_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the Operating System Version feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addOperatingSystemVersionPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_operatingSystemVersion_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_operatingSystemVersion_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This adds a property descriptor for the URI feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addURIPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_DocumentRoot_uRI_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_DocumentRoot_uRI_feature", "_UI_DocumentRoot_type"),
         JsdlPackage.Literals.DOCUMENT_ROOT__URI,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
   * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Collection getChildrenFeatures(Object object)
  {
    if (childrenFeatures == null)
    {
      super.getChildrenFeatures(object);
      childrenFeatures.add(JsdlPackage.Literals.DOCUMENT_ROOT__MIXED);
      childrenFeatures.add(JsdlPackage.Literals.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
      childrenFeatures.add(JsdlPackage.Literals.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected EStructuralFeature getChildFeature(Object object, Object child)
  {
    // Check the type of the specified child object and return the proper feature to use for
    // adding (see {@link AddCommand}) it as a child.

    return super.getChildFeature(object, child);
  }

  /**
   * This returns DocumentRoot.gif.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/DocumentRoot"));
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getText(Object object)
  {
    String label = ((DocumentRoot)object).getApplicationName();
    return label == null || label.length() == 0 ?
      getString("_UI_DocumentRoot_type") :
      getString("_UI_DocumentRoot_type") + " " + label;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(DocumentRoot.class))
    {
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_NAME:
      case JsdlPackage.DOCUMENT_ROOT__APPLICATION_VERSION:
      case JsdlPackage.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME:
      case JsdlPackage.DOCUMENT_ROOT__CREATION_FLAG:
      case JsdlPackage.DOCUMENT_ROOT__DELETE_ON_TERMINATION:
      case JsdlPackage.DOCUMENT_ROOT__DESCRIPTION:
      case JsdlPackage.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION:
      case JsdlPackage.DOCUMENT_ROOT__FILE_NAME:
      case JsdlPackage.DOCUMENT_ROOT__FILESYSTEM_NAME:
      case JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE:
      case JsdlPackage.DOCUMENT_ROOT__HOST_NAME:
      case JsdlPackage.DOCUMENT_ROOT__JOB_ANNOTATION:
      case JsdlPackage.DOCUMENT_ROOT__JOB_NAME:
      case JsdlPackage.DOCUMENT_ROOT__JOB_PROJECT:
      case JsdlPackage.DOCUMENT_ROOT__MOUNT_POINT:
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME:
      case JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION:
      case JsdlPackage.DOCUMENT_ROOT__URI:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case JsdlPackage.DOCUMENT_ROOT__MIXED:
      case JsdlPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
      case JsdlPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
   * describing all of the children that can be created under this object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__COMMENT,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__TEXT,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION,
           JsdlFactory.eINSTANCE.createApplicationType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_NAME,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__APPLICATION_VERSION,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__CANDIDATE_HOSTS,
           JsdlFactory.eINSTANCE.createCandidateHostsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE,
           JsdlFactory.eINSTANCE.createCPUArchitectureType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__CPU_ARCHITECTURE_NAME,
           ProcessorArchitectureEnumeration.SPARC_LITERAL)));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__CREATION_FLAG,
           CreationFlagEnumeration.OVERWRITE_LITERAL)));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__DATA_STAGING,
           JsdlFactory.eINSTANCE.createDataStagingType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__DELETE_ON_TERMINATION,
           XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.BOOLEAN, "false"))));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__DESCRIPTION,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__DISK_SPACE,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__EXCLUSIVE_EXECUTION,
           XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.BOOLEAN, "false"))));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__FILE_NAME,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM,
           JsdlFactory.eINSTANCE.createFileSystemType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__FILESYSTEM_NAME,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__FILE_SYSTEM_TYPE,
           FileSystemTypeEnumeration.SWAP_LITERAL)));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__HOST_NAME,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__JOB_ANNOTATION,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DEFINITION,
           JsdlFactory.eINSTANCE.createJobDefinitionType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__JOB_DESCRIPTION,
           JsdlFactory.eINSTANCE.createJobDescriptionType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__JOB_IDENTIFICATION,
           JsdlFactory.eINSTANCE.createJobIdentificationType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__JOB_NAME,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__JOB_PROJECT,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__MOUNT_POINT,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM,
           JsdlFactory.eINSTANCE.createOperatingSystemType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_NAME,
           OperatingSystemTypeEnumeration.UNKNOWN_LITERAL)));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_TYPE,
           JsdlFactory.eINSTANCE.createOperatingSystemTypeType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION,
           "")));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__RESOURCES,
           JsdlFactory.eINSTANCE.createResourcesType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__SOURCE,
           JsdlFactory.eINSTANCE.createSourceTargetType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TARGET,
           JsdlFactory.eINSTANCE.createSourceTargetType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_COUNT,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_TIME,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_DISK_SPACE,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY,
           JsdlFactory.eINSTANCE.createRangeValueType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.DOCUMENT_ROOT__MIXED,
         FeatureMapUtil.createEntry
          (JsdlPackage.Literals.DOCUMENT_ROOT__URI,
           "")));
  }

  /**
   * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection)
  {
    Object childFeature = feature;
    Object childObject = child;

    if (childFeature instanceof EStructuralFeature && FeatureMapUtil.isFeatureMap((EStructuralFeature)childFeature))
    {
      FeatureMap.Entry entry = (FeatureMap.Entry)childObject;
      childFeature = entry.getEStructuralFeature();
      childObject = entry.getValue();
    }

    boolean qualify =
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__DISK_SPACE ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_COUNT ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_CPU_TIME ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_DISK_SPACE ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_NETWORK_BANDWIDTH ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_PHYSICAL_MEMORY ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__INDIVIDUAL_VIRTUAL_MEMORY ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_COUNT ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_CPU_TIME ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_DISK_SPACE ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_PHYSICAL_MEMORY ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_RESOURCE_COUNT ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TOTAL_VIRTUAL_MEMORY ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__SOURCE ||
      childFeature == JsdlPackage.Literals.DOCUMENT_ROOT__TARGET;

    if (qualify)
    {
      return getString
        ("_UI_CreateChild_text2",
         new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
    }
    return super.getCreateChildText(owner, feature, child, selection);
  }

  /**
   * Return the resource locator for this item provider's resources.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public ResourceLocator getResourceLocator()
  {
    return JsdlEditPlugin.INSTANCE;
  }

}
