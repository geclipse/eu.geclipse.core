/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.BatchResource;

/**
 * Factory that maps model elements to TreeEditParts. TreeEditParts are used in
 * the outline view of the BatchEditor.
 */
public class BatchTreeEditPartFactory implements EditPartFactory {

  /**
   * Create the edit part.
   * @param context The context.
   * @param model The mode.
   * @return Returns the EditPart.
   */
  public EditPart createEditPart( final EditPart context, final Object model ) {
    EditPart editPart = null;

    if ( model instanceof BatchResource ) {
      editPart = new BatchTreeEditPart( ( BatchResource )model );
    }
    else if ( model instanceof BatchDiagram ) {
      editPart = new DiagramTreeEditPart( ( BatchDiagram )model );
    }

    return editPart; 
  }
}
