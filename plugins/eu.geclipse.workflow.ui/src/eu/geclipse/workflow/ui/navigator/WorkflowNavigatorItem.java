/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class WorkflowNavigatorItem extends WorkflowAbstractNavigatorItem {

  /**
   * @generated
   */
  static {
    final Class[] supportedTypes = new Class[]{
      View.class, EObject.class
    };
    Platform.getAdapterManager().registerAdapters( new IAdapterFactory() {

      public Object getAdapter( Object adaptableObject, Class adapterType ) {
        if( adaptableObject instanceof eu.geclipse.workflow.ui.navigator.WorkflowNavigatorItem
            && ( adapterType == View.class || adapterType == EObject.class ) )
        {
          return ( ( eu.geclipse.workflow.ui.navigator.WorkflowNavigatorItem )adaptableObject ).getView();
        }
        return null;
      }

      public Class[] getAdapterList() {
        return supportedTypes;
      }
    },
                                                   eu.geclipse.workflow.ui.navigator.WorkflowNavigatorItem.class );
  }
  /**
   * @generated
   */
  private View myView;
  /**
   * @generated
   */
  private boolean myLeaf = false;

  /**
   * @generated
   */
  public WorkflowNavigatorItem( View view, Object parent, boolean isLeaf ) {
    super( parent );
    myView = view;
    myLeaf = isLeaf;
  }

  /**
   * @generated
   */
  public View getView() {
    return myView;
  }

  /**
   * @generated
   */
  public boolean isLeaf() {
    return myLeaf;
  }

  /**
   * @generated
   */
  @Override
  public boolean equals( Object obj ) {
    if( obj instanceof eu.geclipse.workflow.ui.navigator.WorkflowNavigatorItem )
    {
      return EcoreUtil.getURI( getView() )
        .equals( EcoreUtil.getURI( ( ( eu.geclipse.workflow.ui.navigator.WorkflowNavigatorItem )obj ).getView() ) );
    }
    return super.equals( obj );
  }

  /**
   * @generated
   */
  @Override
  public int hashCode() {
    return EcoreUtil.getURI( getView() ).hashCode();
  }
}
