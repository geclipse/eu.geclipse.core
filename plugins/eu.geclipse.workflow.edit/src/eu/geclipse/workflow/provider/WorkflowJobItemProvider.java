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

import eu.geclipse.workflow.IWorkflowJob;
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
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link eu.geclipse.workflow.IWorkflowJob} object. 
 * 
 * @generated
 */
public class WorkflowJobItemProvider extends WorkflowNodeItemProvider
  implements IEditingDomainItemProvider, IStructuredItemContentProvider,
  ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource
{

  /**
   * This constructs an instance from a factory and a notifier. 
   * 
   * @generated
   */
  public WorkflowJobItemProvider( AdapterFactory adapterFactory ) {
    super( adapterFactory );
  }

  /**
   * This returns the property descriptors for the adapted class. 
   * 
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors( Object object )
  {
    if( itemPropertyDescriptors == null ) {
      super.getPropertyDescriptors( object );
      addJobDescriptionPropertyDescriptor( object );
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Job Description feature. 
   * 
   * @generated NOT
   */
  protected void addJobDescriptionPropertyDescriptor( Object object ) {
    itemPropertyDescriptors.add( createItemPropertyDescriptor( ( ( ComposeableAdapterFactory )adapterFactory ).getRootAdapterFactory(),
                                                               getResourceLocator(),
                                                               getString( "_UI_IWorkflowJob_jobDescription_feature" ), //$NON-NLS-1$
                                                               getString( "_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                                                          "_UI_IWorkflowJob_jobDescription_feature", //$NON-NLS-1$
                                                                          "_UI_IWorkflowJob_type" ), //$NON-NLS-1$
                                                               IWorkflowPackage.Literals.IWORKFLOW_JOB__JOB_DESCRIPTION,
                                                               true,
                                                               true,
                                                               false,
                                                               ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                                                               null,
                                                               null ) );
  }

  /**
   * This adds a property descriptor for the Job Description File Name feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addJobDescriptionFileNamePropertyDescriptor(Object object)
  {
    this.itemPropertyDescriptors.add
      (createItemPropertyDescriptor
        (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
         getResourceLocator(),
         getString("_UI_IWorkflowJob_jobDescriptionFileName_feature"), //$NON-NLS-1$
         getString("_UI_PropertyDescriptor_description", "_UI_IWorkflowJob_jobDescriptionFileName_feature", "_UI_IWorkflowJob_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
         IWorkflowPackage.Literals.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME,
         true,
         false,
         false,
         ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
         null,
         null));
  }

  /**
   * This returns IWorkflowJob.gif. 
   * 
   * @generated
   */
  @Override
  public Object getImage( Object object )
  {
    return overlayImage( object, getResourceLocator().getImage( "full/obj16/WorkflowJob" ) ); //$NON-NLS-1$
  }

  /**
   * This returns the label text for the adapted class. 
   * 
   * @generated
   */
  @Override
  public String getText( Object object )
  {
    String label = ( ( IWorkflowJob )object ).getName();
    return label == null || label.length() == 0
                                               ? getString( "_UI_IWorkflowJob_type" ) //$NON-NLS-1$
                                               : getString( "_UI_IWorkflowJob_type" ) //$NON-NLS-1$
                                                 + " " //$NON-NLS-1$
                                                 + label;
  }


  /**
   * This handles model notifications by calling {@link #updateChildren} to
   * update any cached children and by creating a viewer notification, which it
   * passes to {@link #fireNotifyChanged}. 
   * 
   * @generated
   */
  @Override
  public void notifyChanged( Notification notification )
  {
    updateChildren( notification );
    switch( notification.getFeatureID( IWorkflowJob.class ) ) {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        fireNotifyChanged( new ViewerNotification( notification,
                                                   notification.getNotifier(),
                                                   true,
                                                   true ) );
        return;
    }
    super.notifyChanged( notification );
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * 
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
   * @generated
   */
  @Override
  public ResourceLocator getResourceLocator()
  {
    return WorkflowEditPlugin.INSTANCE;
  }
}
