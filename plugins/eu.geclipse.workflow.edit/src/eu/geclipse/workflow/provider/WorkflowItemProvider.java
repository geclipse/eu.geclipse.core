/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.provider;

import eu.geclipse.workflow.model.IWorkflow;
import eu.geclipse.workflow.model.IWorkflowFactory;
import eu.geclipse.workflow.model.IWorkflowPackage;

import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link eu.geclipse.workflow.model.IWorkflow} object. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class WorkflowItemProvider extends WorkflowElementItemProvider
  implements IEditingDomainItemProvider, IStructuredItemContentProvider,
  ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource
{

  /**
   * This constructs an instance from a factory and a notifier. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public WorkflowItemProvider( AdapterFactory adapterFactory ) {
    super( adapterFactory );
  }

  /**
   * This returns the property descriptors for the adapted class. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors( Object object ) {
    if( this.itemPropertyDescriptors == null ) {
      super.getPropertyDescriptors( object );
    }
    return this.itemPropertyDescriptors;
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
    if( this.childrenFeatures == null ) {
      super.getChildrenFeatures( object );
      this.childrenFeatures.add( IWorkflowPackage.Literals.IWORKFLOW__NODES );
      this.childrenFeatures.add( IWorkflowPackage.Literals.IWORKFLOW__LINKS );
    }
    return this.childrenFeatures;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EStructuralFeature getChildFeature( Object object, Object child ) {
    // Check the type of the specified child object and return the proper
    // feature to use for
    // adding (see {@link AddCommand}) it as a child.
    return super.getChildFeature( object, child );
  }

  /**
   * This returns IWorkflow.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object getImage( Object object ) {
    return overlayImage( object,
                         getResourceLocator().getImage( "full/obj16/Workflow" ) ); //$NON-NLS-1$
  }

  /**
   * This returns the label text for the adapted class. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String getText( Object object ) {
    String label = ( ( IWorkflow )object ).getName();
    return label == null || label.length() == 0
                                               ? getString( "_UI_IWorkflow_type" ) //$NON-NLS-1$
                                               : getString( "_UI_IWorkflow_type" ) //$NON-NLS-1$
                                                 + " " //$NON-NLS-1$
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
  public void notifyChanged( Notification notification ) {
    updateChildren( notification );
    switch( notification.getFeatureID( IWorkflow.class ) ) {
      case IWorkflowPackage.IWORKFLOW__NODES:
      case IWorkflowPackage.IWORKFLOW__LINKS:
        fireNotifyChanged( new ViewerNotification( notification,
                                                   notification.getNotifier(),
                                                   true,
                                                   false ) );
        return;
    }
    super.notifyChanged( notification );
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
   * describing the children that can be created under this object. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors( Collection<Object> newChildDescriptors,
                                             Object object )
  {
    super.collectNewChildDescriptors( newChildDescriptors, object );
    newChildDescriptors.add( createChildParameter( IWorkflowPackage.Literals.IWORKFLOW__NODES,
                                                   IWorkflowFactory.eINSTANCE.createIWorkflowJob() ) );
    newChildDescriptors.add( createChildParameter( IWorkflowPackage.Literals.IWORKFLOW__LINKS,
                                                   IWorkflowFactory.eINSTANCE.createILink() ) );
  }

  /**
   * Return the resource locator for this item provider's resources.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator() {
    return WorkflowEditPlugin.INSTANCE;
  }
}
