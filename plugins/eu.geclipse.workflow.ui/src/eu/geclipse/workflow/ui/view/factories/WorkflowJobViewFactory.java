/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.view.factories;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobNameEditPart;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowJobViewFactory extends AbstractShapeViewFactory {

  /**
   * @generated
   */
  protected List createStyles( View view ) {
    List styles = new ArrayList();
    styles.add( NotationFactory.eINSTANCE.createShapeStyle() );
    return styles;
  }

  /**
   * @generated
   */
  protected void decorateView( View containerView,
                               View view,
                               IAdaptable semanticAdapter,
                               String semanticHint,
                               int index,
                               boolean persisted )
  {
    if( semanticHint == null ) {
      semanticHint = WorkflowVisualIDRegistry.getType( WorkflowJobEditPart.VISUAL_ID );
      view.setType( semanticHint );
    }
    super.decorateView( containerView,
                        view,
                        semanticAdapter,
                        semanticHint,
                        index,
                        persisted );
    if( !WorkflowEditPart.MODEL_ID.equals( WorkflowVisualIDRegistry.getModelID( containerView ) ) )
    {
      EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
      shortcutAnnotation.setSource( "Shortcut" ); //$NON-NLS-1$
      shortcutAnnotation.getDetails()
        .put( "modelID", WorkflowEditPart.MODEL_ID ); //$NON-NLS-1$
      view.getEAnnotations().add( shortcutAnnotation );
    }
    IAdaptable eObjectAdapter = null;
    EObject eObject = ( EObject )semanticAdapter.getAdapter( EObject.class );
    if( eObject != null ) {
      eObjectAdapter = new EObjectAdapter( eObject );
    }
    getViewService().createNode( eObjectAdapter,
                                 view,
                                 WorkflowVisualIDRegistry.getType( WorkflowJobNameEditPart.VISUAL_ID ),
                                 ViewUtil.APPEND,
                                 true,
                                 getPreferencesHint() );
  }
}
