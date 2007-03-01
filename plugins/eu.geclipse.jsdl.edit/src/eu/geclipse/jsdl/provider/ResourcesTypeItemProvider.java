/**
 * <copyright>
 * </copyright>
 *
 * $Id: ResourcesTypeItemProvider.java,v 1.1 2007/01/25 15:29:05 emstamou Exp $
 */
package eu.geclipse.jsdl.provider;


import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.ResourcesType;

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
 * This is the item provider adapter for a {@link eu.geclipse.jsdl.ResourcesType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ResourcesTypeItemProvider
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
	public ResourcesTypeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addExclusiveExecutionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Exclusive Execution feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExclusiveExecutionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ResourcesType_exclusiveExecution_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ResourcesType_exclusiveExecution_feature", "_UI_ResourcesType_type"),
				 JsdlPackage.Literals.RESOURCES_TYPE__EXCLUSIVE_EXECUTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__CANDIDATE_HOSTS);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__FILE_SYSTEM);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__OPERATING_SYSTEM);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__CPU_ARCHITECTURE);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_CPU_TIME);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_CPU_COUNT);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_DISK_SPACE);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__ANY);
			childrenFeatures.add(JsdlPackage.Literals.RESOURCES_TYPE__ANY_ATTRIBUTE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ResourcesType.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ResourcesType"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		ResourcesType resourcesType = (ResourcesType)object;
		return getString("_UI_ResourcesType_type") + " " + resourcesType.isExclusiveExecution();
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ResourcesType.class)) {
			case JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS:
			case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM:
			case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM:
			case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY:
			case JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE:
			case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_TIME:
			case JsdlPackage.RESOURCES_TYPE__TOTAL_CPU_COUNT:
			case JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY:
			case JsdlPackage.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY:
			case JsdlPackage.RESOURCES_TYPE__TOTAL_DISK_SPACE:
			case JsdlPackage.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT:
			case JsdlPackage.RESOURCES_TYPE__ANY:
			case JsdlPackage.RESOURCES_TYPE__ANY_ATTRIBUTE:
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
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__CANDIDATE_HOSTS,
				 JsdlFactory.eINSTANCE.createCandidateHostsType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__FILE_SYSTEM,
				 JsdlFactory.eINSTANCE.createFileSystemType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__OPERATING_SYSTEM,
				 JsdlFactory.eINSTANCE.createOperatingSystemType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__CPU_ARCHITECTURE,
				 JsdlFactory.eINSTANCE.createCPUArchitectureType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_CPU_TIME,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_CPU_COUNT,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_DISK_SPACE,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT,
				 JsdlFactory.eINSTANCE.createRangeValueType()));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__ARGUMENT,
					 PosixFactory.eINSTANCE.createArgumentType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__CORE_DUMP_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__CPU_TIME_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__DATA_SEGMENT_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__ENVIRONMENT,
					 PosixFactory.eINSTANCE.createEnvironmentType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__ERROR,
					 PosixFactory.eINSTANCE.createFileNameType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__EXECUTABLE,
					 PosixFactory.eINSTANCE.createFileNameType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__FILE_SIZE_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__GROUP_NAME,
					 PosixFactory.eINSTANCE.createGroupNameType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__INPUT,
					 PosixFactory.eINSTANCE.createFileNameType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__MEMORY_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__OUTPUT,
					 PosixFactory.eINSTANCE.createFileNameType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__PIPE_SIZE_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__POSIX_APPLICATION,
					 PosixFactory.eINSTANCE.createPOSIXApplicationType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__PROCESS_COUNT_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__STACK_SIZE_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__THREAD_COUNT_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__USER_NAME,
					 PosixFactory.eINSTANCE.createUserNameType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
				 FeatureMapUtil.createEntry
					(PosixPackage.Literals.DOCUMENT_ROOT__WALL_TIME_LIMIT,
					 PosixFactory.eINSTANCE.createLimitsType())));

		newChildDescriptors.add
			(createChildParameter
				(JsdlPackage.Literals.RESOURCES_TYPE__ANY,
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
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection) {
		Object childFeature = feature;
		Object childObject = child;

		if (childFeature instanceof EStructuralFeature && FeatureMapUtil.isFeatureMap((EStructuralFeature)childFeature)) {
			FeatureMap.Entry entry = (FeatureMap.Entry)childObject;
			childFeature = entry.getEStructuralFeature();
			childObject = entry.getValue();
		}

		boolean qualify =
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_VIRTUAL_MEMORY ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__INDIVIDUAL_DISK_SPACE ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_CPU_TIME ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_CPU_COUNT ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_VIRTUAL_MEMORY ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_DISK_SPACE ||
			childFeature == JsdlPackage.Literals.RESOURCES_TYPE__TOTAL_RESOURCE_COUNT ||
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

		if (qualify) {
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
	public ResourceLocator getResourceLocator() {
		return JsdlEditPlugin.INSTANCE;
	}

}
