/**
 * <copyright>
 * </copyright>
 *
 * $Id: POSIXApplicationTypeItemProvider.java,v 1.2 2007/03/01 09:16:02 emstamou Exp $
 */
package eu.geclipse.jsdl.posix.provider;


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
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.provider.JsdlEditPlugin;

/**
 * This is the item provider adapter for a {@link eu.geclipse.jsdl.posix.POSIXApplicationType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class POSIXApplicationTypeItemProvider
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
	public POSIXApplicationTypeItemProvider(AdapterFactory adapterFactory)
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

      addNamePropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Name feature.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void addNamePropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_POSIXApplicationType_name_feature"),
         getString("_UI_PropertyDescriptor_description", "_UI_POSIXApplicationType_name_feature", "_UI_POSIXApplicationType_type"),
         PosixPackage.Literals.POSIX_APPLICATION_TYPE__NAME,
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
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__EXECUTABLE);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__ARGUMENT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__INPUT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__OUTPUT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__ERROR);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__ENVIRONMENT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__MEMORY_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__USER_NAME);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__GROUP_NAME);
      childrenFeatures.add(PosixPackage.Literals.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE);
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
   * This returns POSIXApplicationType.gif.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/POSIXApplicationType"));
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getText(Object object)
  {
    String label = ((POSIXApplicationType)object).getName();
    return label == null || label.length() == 0 ?
      getString("_UI_POSIXApplicationType_type") :
      getString("_UI_POSIXApplicationType_type") + " " + label;
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

    switch (notification.getFeatureID(POSIXApplicationType.class))
    {
      case PosixPackage.POSIX_APPLICATION_TYPE__NAME:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
      case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:
      case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:
      case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:
      case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:
      case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:
      case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY:
      case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:
      case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT:
      case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME:
      case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME:
      case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:
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
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__EXECUTABLE,
         PosixFactory.eINSTANCE.createFileNameType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__ARGUMENT,
         PosixFactory.eINSTANCE.createArgumentType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__INPUT,
         PosixFactory.eINSTANCE.createFileNameType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__OUTPUT,
         PosixFactory.eINSTANCE.createFileNameType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__ERROR,
         PosixFactory.eINSTANCE.createFileNameType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY,
         PosixFactory.eINSTANCE.createDirectoryNameType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__ENVIRONMENT,
         PosixFactory.eINSTANCE.createEnvironmentType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__MEMORY_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT,
         PosixFactory.eINSTANCE.createLimitsType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__USER_NAME,
         PosixFactory.eINSTANCE.createUserNameType()));

    newChildDescriptors.add
      (createChildParameter
        (PosixPackage.Literals.POSIX_APPLICATION_TYPE__GROUP_NAME,
         PosixFactory.eINSTANCE.createGroupNameType()));
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
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__EXECUTABLE ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__INPUT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__OUTPUT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__ERROR ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__MEMORY_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT ||
      childFeature == PosixPackage.Literals.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT;

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
