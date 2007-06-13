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

import eu.geclipse.workflow.IWorkflowNode;
import eu.geclipse.workflow.IWorkflowFactory;
import eu.geclipse.workflow.IWorkflowPackage;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link eu.geclipse.workflow.IWorkflowNode} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class WorkflowNodeItemProvider extends WorkflowElementItemProvider
  implements IEditingDomainItemProvider, IStructuredItemContentProvider,
  ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource
{

  /**
   * This constructs an instance from a factory and a notifier. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public WorkflowNodeItemProvider( AdapterFactory adapterFactory ) {
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
      addIsStartPropertyDescriptor( object );
      addIsFinishPropertyDescriptor( object );
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Is Start feature. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected void addIsStartPropertyDescriptor( Object object ) {
    itemPropertyDescriptors.add( createItemPropertyDescriptor( ( ( ComposeableAdapterFactory )adapterFactory ).getRootAdapterFactory(),
                                                               getResourceLocator(),
                                                               getString( "_UI_IWorkflowNode_isStart_feature" ),
                                                               getString( "_UI_PropertyDescriptor_description",
                                                                          "_UI_IWorkflowNode_isStart_feature",
                                                                          "_UI_IWorkflowNode_type" ),
                                                               IWorkflowPackage.Literals.IWORKFLOW_NODE__IS_START,
                                                               true,
                                                               false,
                                                               false,
                                                               ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                                                               null,
                                                               null ) );
  }

  /**
   * This adds a property descriptor for the Is Finish feature. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected void addIsFinishPropertyDescriptor( Object object ) {
    itemPropertyDescriptors.add( createItemPropertyDescriptor( ( ( ComposeableAdapterFactory )adapterFactory ).getRootAdapterFactory(),
                                                               getResourceLocator(),
                                                               getString( "_UI_IWorkflowNode_isFinish_feature" ),
                                                               getString( "_UI_PropertyDescriptor_description",
                                                                          "_UI_IWorkflowNode_isFinish_feature",
                                                                          "_UI_IWorkflowNode_type" ),
                                                               IWorkflowPackage.Literals.IWORKFLOW_NODE__IS_FINISH,
                                                               true,
                                                               false,
                                                               false,
                                                               ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                                                               null,
                                                               null ) );
  }

  /**
   * This specifies how to implement {@link #getChildren} and is used to deduce
   * an appropriate feature for an
   * {@link org.eclipse.emf.edit.command.AddCommand},
   * {@link org.eclipse.emf.edit.command.RemoveCommand} or
   * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Collection<? extends EStructuralFeature> getChildrenFeatures( Object object )
  {
    if( childrenFeatures == null ) {
      super.getChildrenFeatures( object );
      childrenFeatures.add( IWorkflowPackage.Literals.IWORKFLOW_NODE__OUTPUTS );
      childrenFeatures.add( IWorkflowPackage.Literals.IWORKFLOW_NODE__INPUTS );
    }
    return childrenFeatures;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EStructuralFeature getChildFeature( Object object, Object child )
  {
    // Check the type of the specified child object and return the proper
    // feature to use for
    // adding (see {@link AddCommand}) it as a child.
    return super.getChildFeature( object, child );
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
    String label = ( ( IWorkflowNode )object ).getName();
    return label == null || label.length() == 0
                                               ? getString( "_UI_IWorkflowNode_type" )
                                               : getString( "_UI_IWorkflowNode_type" )
                                                 + " "
                                                 + label;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to
   * update any cached children and by creating a viewer notification, which it
   * passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void notifyChanged( Notification notification )
  {
    updateChildren( notification );
    switch( notification.getFeatureID( IWorkflowNode.class ) ) {
      case IWorkflowPackage.IWORKFLOW_NODE__IS_START:
      case IWorkflowPackage.IWORKFLOW_NODE__IS_FINISH:
        fireNotifyChanged( new ViewerNotification( notification,
                                                   notification.getNotifier(),
                                                   false,
                                                   true ) );
        return;
      case IWorkflowPackage.IWORKFLOW_NODE__OUTPUTS:
      case IWorkflowPackage.IWORKFLOW_NODE__INPUTS:
        fireNotifyChanged( new ViewerNotification( notification,
                                                   notification.getNotifier(),
                                                   true,
                                                   false ) );
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
    newChildDescriptors.add( createChildParameter( IWorkflowPackage.Literals.IWORKFLOW_NODE__OUTPUTS,
                                                   IWorkflowFactory.eINSTANCE.createIOutputPort() ) );
    newChildDescriptors.add( createChildParameter( IWorkflowPackage.Literals.IWORKFLOW_NODE__INPUTS,
                                                   IWorkflowFactory.eINSTANCE.createIInputPort() ) );
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
