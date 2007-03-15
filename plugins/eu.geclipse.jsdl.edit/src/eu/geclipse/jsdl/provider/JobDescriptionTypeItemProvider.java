/**
 * <copyright>
 * </copyright>
 *
 * $Id: JobDescriptionTypeItemProvider.java,v 1.2 2007/03/01 09:16:01 emstamou Exp $
 */
package eu.geclipse.jsdl.provider;


import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;

import eu.geclipse.jsdl.posix.PosixFactory;
import eu.geclipse.jsdl.posix.PosixPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link eu.geclipse.jsdl.JobDescriptionType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class JobDescriptionTypeItemProvider
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
	public JobDescriptionTypeItemProvider(AdapterFactory adapterFactory)
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

    }
    return itemPropertyDescriptors;
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
      childrenFeatures.add(JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION);
      childrenFeatures.add(JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__APPLICATION);
      childrenFeatures.add(JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__RESOURCES);
      childrenFeatures.add(JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__DATA_STAGING);
      childrenFeatures.add(JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY);
      childrenFeatures.add(JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE);
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
   * This returns JobDescriptionType.gif.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/JobDescriptionType"));
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public String getText(Object object)
  {
    return getString("_UI_JobDescriptionType_type");
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

    switch (notification.getFeatureID(JobDescriptionType.class))
    {
      case JsdlPackage.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION:
      case JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION:
      case JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES:
      case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:
      case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY:
      case JsdlPackage.JOB_DESCRIPTION_TYPE__ANY_ATTRIBUTE:
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
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__JOB_IDENTIFICATION,
         JsdlFactory.eINSTANCE.createJobIdentificationType()));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__APPLICATION,
         JsdlFactory.eINSTANCE.createApplicationType()));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__RESOURCES,
         JsdlFactory.eINSTANCE.createResourcesType()));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__DATA_STAGING,
         JsdlFactory.eINSTANCE.createDataStagingType()));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__ARGUMENT,
           PosixFactory.eINSTANCE.createArgumentType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__ENVIRONMENT,
           PosixFactory.eINSTANCE.createEnvironmentType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__ERROR,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__GROUP_NAME,
           PosixFactory.eINSTANCE.createGroupNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__INPUT,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT,
           PosixFactory.eINSTANCE.createFileNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__POSIX_APPLICATION,
           PosixFactory.eINSTANCE.createPOSIXApplicationType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__USER_NAME,
           PosixFactory.eINSTANCE.createUserNameType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
         FeatureMapUtil.createEntry
          (PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT,
           PosixFactory.eINSTANCE.createLimitsType())));

    newChildDescriptors.add
      (createChildParameter
        (JsdlPackage.Literals.JOB_DESCRIPTION_TYPE__ANY,
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
