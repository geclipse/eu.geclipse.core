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
package eu.geclipse.workflow.ui.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import eu.geclipse.workflow.ui.navigator.WorkflowNavigatorGroup;
import eu.geclipse.workflow.ui.part.WorkflowDiagramEditorPlugin;

/**
 * @generated
 */
public class WorkflowSheetLabelProvider extends DecoratingLabelProvider {

  /**
   * @generated
   */
  public WorkflowSheetLabelProvider() {
    super( new AdapterFactoryLabelProvider( WorkflowDiagramEditorPlugin.getInstance()
             .getItemProvidersAdapterFactory() ),
           null );
  }

  /**
   * @generated
   */
  @Override
  public String getText( Object element ) {
    Object selected = unwrap( element );
    if( selected instanceof WorkflowNavigatorGroup ) {
      return ( ( WorkflowNavigatorGroup )selected ).getGroupName();
    }
    return super.getText( selected );
  }

  /**
   * @generated
   */
  @Override
  public Image getImage( Object element ) {
    return super.getImage( unwrap( element ) );
  }

  /**
   * @generated
   */
  private Object unwrap( Object element ) {
    if( element instanceof IStructuredSelection ) {
      return unwrap( ( ( IStructuredSelection )element ).getFirstElement() );
    }
    if( element instanceof EditPart ) {
      return unwrapEditPart( ( EditPart )element );
    }
    if( element instanceof IAdaptable ) {
      View view = ( View )( ( IAdaptable )element ).getAdapter( View.class );
      if( view != null ) {
        return unwrapView( view );
      }
    }
    return element;
  }

  /**
   * @generated
   */
  private Object unwrapEditPart( EditPart p ) {
    if( p.getModel() instanceof View ) {
      return unwrapView( ( View )p.getModel() );
    }
    return p.getModel();
  }

  /**
   * @generated
   */
  private Object unwrapView( View view ) {
    return view.getElement() == null
                                    ? view
                                    : view.getElement();
  }
}
