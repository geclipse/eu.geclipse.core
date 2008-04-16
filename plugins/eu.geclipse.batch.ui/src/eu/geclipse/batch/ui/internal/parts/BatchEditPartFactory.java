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

import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPart;

import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.model.Connection;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;

import eu.geclipse.batch.ui.internal.model.Box;

/**
 *  Factory for creating BatchEditPart.
 */
public class BatchEditPartFactory implements EditPartFactory {
  /**
   * Creates the edit part.
   * @param context The context.
   * @param modelElement The element
   * @return Returns the EditPart.
   */
  public EditPart createEditPart( final EditPart context, final Object modelElement ) {
    // Get EditPart for model element
    EditPart part = getPartForElement( modelElement );
    // Store model element in EditPart
    part.setModel( modelElement );

    return part;
   }

  /**
   * Maps an object to an EditPart.
   * @param modelElement The element
   * @return Returns the EditPart.
   * @throws RuntimeException if no match was found.
   */
  private EditPart getPartForElement( final Object modelElement ) {
    EditPart part = null;

    if ( modelElement instanceof BatchDiagram ) {
      part = new DiagramEditPart();
    }
    else if ( modelElement instanceof ComputingElement ) {
      part = new ComputingElementEditPart();
    }
    else if ( modelElement instanceof WorkerNode ) {
      part = new WorkerNodeEditPart();
    }
    else if ( modelElement instanceof Queue ) {
      part = new QueueEditPart();
    }
    else if ( modelElement instanceof Connection ) {
      part = new ConnectionEditPart();
    }
    else if ( modelElement instanceof Box ) {
      part = new BoxEditPart();
    }
    else {
      throw new RuntimeException( "Can't create part for model element: " //$NON-NLS-1$
                                  + ( ( modelElement != null ) 
                                      ? modelElement.getClass().getName() : "null" ) ); //$NON-NLS-1$
    }

    return part;
  }
}
