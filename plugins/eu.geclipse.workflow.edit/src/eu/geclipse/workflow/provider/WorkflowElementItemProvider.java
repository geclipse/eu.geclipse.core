/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.provider;

import eu.geclipse.workflow.IWorkflowElement;
import eu.geclipse.workflow.IWorkflowPackage;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link eu.geclipse.workflow.IWorkflowElement} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class WorkflowElementItemProvider extends ItemProviderAdapter
  implements IEditingDomainItemProvider, IStructuredItemContentProvider,
  ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource
{

  /**
   * This constructs an instance from a factory and a notifier. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public WorkflowElementItemProvider( AdapterFactory adapterFactory ) {
    super( adapterFactory );
  }

  /**
   * This returns the property descriptors for the adapted class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors( Object object )
  {
    if( itemPropertyDescriptors == null ) {
      super.getPropertyDescriptors( object );
      addNamePropertyDescriptor( object );
      addIdPropertyDescriptor( object );
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Name feature. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected void addNamePropertyDescriptor( Object object ) {
    itemPropertyDescriptors.add( createItemPropertyDescriptor( ( ( ComposeableAdapterFactory )adapterFactory ).getRootAdapterFactory(),
                                                               getResourceLocator(),
                                                               getString( "_UI_IWorkflowElement_name_feature" ),
                                                               getString( "_UI_PropertyDescriptor_description",
                                                                          "_UI_IWorkflowElement_name_feature",
                                                                          "_UI_IWorkflowElement_type" ),
                                                               IWorkflowPackage.Literals.IWORKFLOW_ELEMENT__NAME,
                                                               true,
                                                               false,
                                                               false,
                                                               ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                                                               null,
                                                               null ) );
  }

  /**
   * This adds a property descriptor for the Id feature. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   */
  protected void addIdPropertyDescriptor( Object object ) {
    itemPropertyDescriptors.add( createItemPropertyDescriptor( ( ( ComposeableAdapterFactory )adapterFactory ).getRootAdapterFactory(),
                                                               getResourceLocator(),
                                                               getString( "_UI_IWorkflowElement_id_feature" ),
                                                               getString( "_UI_PropertyDescriptor_description",
                                                                          "_UI_IWorkflowElement_id_feature",
                                                                          "_UI_IWorkflowElement_type" ),
                                                               IWorkflowPackage.Literals.IWORKFLOW_ELEMENT__ID,
                                                               true,
                                                               false,
                                                               false,
                                                               ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                                                               null,
                                                               null ) );
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String getText( Object object )
  {
    String label = ( ( IWorkflowElement )object ).getName();
    return label == null || label.length() == 0
                                               ? getString( "_UI_IWorkflowElement_type" )
                                               : getString( "_UI_IWorkflowElement_type" )
                                                 + " "
                                                 + label;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void notifyChanged( Notification notification )
  {
    updateChildren( notification );
    switch( notification.getFeatureID( IWorkflowElement.class ) ) {
      case IWorkflowPackage.IWORKFLOW_ELEMENT__NAME:
      case IWorkflowPackage.IWORKFLOW_ELEMENT__ID:
        fireNotifyChanged( new ViewerNotification( notification,
                                                   notification.getNotifier(),
                                                   false,
                                                   true ) );
        return;
    }
    super.notifyChanged( notification );
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors( Collection<Object> newChildDescriptors,
                                             Object object )
  {
    super.collectNewChildDescriptors( newChildDescriptors, object );
  }

  /**
   * Return the resource locator for this item provider's resources.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator()
  {
    return WorkflowEditPlugin.INSTANCE;
  }
}
