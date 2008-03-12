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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

/**
 * @generated
 */
public abstract class WorkflowAbstractNavigatorItem extends PlatformObject {

  /**
   * @generated
   */
  static {
    final Class[] supportedTypes = new Class[]{
      ITabbedPropertySheetPageContributor.class
    };
    final ITabbedPropertySheetPageContributor propertySheetPageContributor = new ITabbedPropertySheetPageContributor()
    {

      public String getContributorId() {
        return "eu.geclipse.workflow.ui"; //$NON-NLS-1$
      }
    };
    Platform.getAdapterManager().registerAdapters( new IAdapterFactory() {

      public Object getAdapter( Object adaptableObject, Class adapterType ) {
        if( adaptableObject instanceof eu.geclipse.workflow.ui.navigator.WorkflowAbstractNavigatorItem
            && adapterType == ITabbedPropertySheetPageContributor.class )
        {
          return propertySheetPageContributor;
        }
        return null;
      }

      public Class[] getAdapterList() {
        return supportedTypes;
      }
    }, eu.geclipse.workflow.ui.navigator.WorkflowAbstractNavigatorItem.class );
  }
  /**
   * @generated
   */
  private Object myParent;

  /**
   * @generated
   */
  protected WorkflowAbstractNavigatorItem( Object parent ) {
    myParent = parent;
  }

  /**
   * @generated
   */
  public Object getParent() {
    return myParent;
  }
}
