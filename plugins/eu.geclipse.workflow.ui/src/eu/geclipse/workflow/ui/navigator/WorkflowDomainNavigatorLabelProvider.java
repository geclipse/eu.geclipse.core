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
package eu.geclipse.workflow.ui.navigator;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;

/**
 * @generated
 */
public class WorkflowDomainNavigatorLabelProvider
  implements ICommonLabelProvider
{

  /**
   * @generated
   */
  private AdapterFactoryLabelProvider myAdapterFactoryLabelProvider = new AdapterFactoryLabelProvider( WorkflowDiagramEditorPlugin.getInstance()
    .getItemProvidersAdapterFactory() );

  /**
   * @generated
   */
  public void init( ICommonContentExtensionSite aConfig ) {
  }

  /**
   * @generated
   */
  public Image getImage( Object element ) {
    if( element instanceof WorkflowDomainNavigatorItem ) {
      return myAdapterFactoryLabelProvider.getImage( ( ( WorkflowDomainNavigatorItem )element ).getEObject() );
    }
    return null;
  }

  /**
   * @generated
   */
  public String getText( Object element ) {
    if( element instanceof WorkflowDomainNavigatorItem ) {
      return myAdapterFactoryLabelProvider.getText( ( ( WorkflowDomainNavigatorItem )element ).getEObject() );
    }
    return null;
  }

  /**
   * @generated
   */
  public void addListener( ILabelProviderListener listener ) {
    myAdapterFactoryLabelProvider.addListener( listener );
  }

  /**
   * @generated
   */
  public void dispose() {
    myAdapterFactoryLabelProvider.dispose();
  }

  /**
   * @generated
   */
  public boolean isLabelProperty( Object element, String property ) {
    return myAdapterFactoryLabelProvider.isLabelProperty( element, property );
  }

  /**
   * @generated
   */
  public void removeListener( ILabelProviderListener listener ) {
    myAdapterFactoryLabelProvider.removeListener( listener );
  }

  /**
   * @generated
   */
  public void restoreState( IMemento aMemento ) {
  }

  /**
   * @generated
   */
  public void saveState( IMemento aMemento ) {
  }

  /**
   * @generated
   */
  public String getDescription( Object anElement ) {
    return null;
  }
}
