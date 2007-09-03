/**
 * <copyright>
 * </copyright>
 *
 * $Id: OperatingSystemTypeTypeItemProvider.java,v 1.2 2007/03/01 09:16:01 emstamou Exp $
 */
package eu.geclipse.jsdl.provider;


import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;

/**
 * This is the item provider adapter for a {@link eu.geclipse.jsdl.OperatingSystemTypeType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OperatingSystemTypeTypeItemProvider
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
	public OperatingSystemTypeTypeItemProvider(AdapterFactory adapterFactory)
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

      addOperatingSystemNamePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
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
         getString("_UI_OperatingSystemTypeType_operatingSystemName_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_OperatingSystemTypeType_operatingSystemName_feature", "_UI_OperatingSystemTypeType_type"),
         JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME,
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
      childrenFeatures.add(JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY);
      childrenFeatures.add(JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE);
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
   * This returns OperatingSystemTypeType.gif.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/OperatingSystemTypeType"));
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getText(Object object)
  {
    OperatingSystemTypeEnumeration labelValue = ((OperatingSystemTypeType)object).getOperatingSystemName();
    String label = labelValue == null ? null : labelValue.toString();
    return label == null || label.length() == 0 ?
      getString("_UI_OperatingSystemTypeType_type") :
      getString("_UI_OperatingSystemTypeType_type") + " " + label;
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

    switch (notification.getFeatureID(OperatingSystemTypeType.class))
    {
      case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__OPERATING_SYSTEM_NAME:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY:
      case JsdlPackage.OPERATING_SYSTEM_TYPE_TYPE__ANY_ATTRIBUTE:
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
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__ARGUMENT,
           PosixFactory.eINSTANCE.createArgumentType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__ENVIRONMENT,
           PosixFactory.eINSTANCE.createEnvironmentType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__ERROR,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__GROUP_NAME,
           PosixFactory.eINSTANCE.createGroupNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__INPUT,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__POSIX_APPLICATION,
           PosixFactory.eINSTANCE.createPOSIXApplicationType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__USER_NAME,
           PosixFactory.eINSTANCE.createUserNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__WORKING_DIRECTORY,
           PosixFactory.eINSTANCE.createDirectoryNameType())));
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
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__ERROR ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__INPUT ||
      childFeature == PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT;

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
