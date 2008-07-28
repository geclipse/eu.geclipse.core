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
package eu.geclipse.workflow.ui.view.factories;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class LinkViewFactory extends ConnectionViewFactory {

  /**
   * @generated
   */
  @Override
  protected List<Style> createStyles( View view ) {
    List<Style> styles = new ArrayList<Style>();
    styles.add( NotationFactory.eINSTANCE.createConnectorStyle() );
    styles.add( NotationFactory.eINSTANCE.createFontStyle() );
    return styles;
  }

  /**
   * @generated
   */
  @Override
  protected void decorateView( View containerView,
                               View view,
                               IAdaptable semanticAdapter,
                               String semanticHint,
                               int index,
                               boolean persisted )
  {
    if( semanticHint == null ) {
      semanticHint = WorkflowVisualIDRegistry.getType( LinkEditPart.VISUAL_ID );
      view.setType( semanticHint );
    }
    super.decorateView( containerView,
                        view,
                        semanticAdapter,
                        semanticHint,
                        index,
                        persisted );
  }
}
