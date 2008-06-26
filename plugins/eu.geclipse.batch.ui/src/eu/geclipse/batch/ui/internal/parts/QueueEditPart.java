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

import java.beans.PropertyChangeEvent;
import org.eclipse.draw2d.Figure;
import eu.geclipse.batch.ui.IQueueFigure;
import eu.geclipse.batch.ui.internal.QueueFigure;
import eu.geclipse.batch.ui.internal.model.Queue;

/**
 * Class that connects together the Figure and Model of a Queue.
 */
public final class QueueEditPart extends BatchEditPart {
  /**
   * Creates the figure that represents this modeled object.
   * @return Returns the created Figure
   */
  @Override
  protected Figure createFigure() {
    QueueFigure wnf = new QueueFigure();

    return wnf;
  }

  /**
   * Refreshes the visual elements (Figure) of this model.
   */
  @Override
  public void refreshVisuals() {
    IQueueFigure queueFigure = ( IQueueFigure )getFigure();
    Queue model = ( Queue )getModel();
    queueFigure.setQueueName( model.getQueneName() );
    queueFigure.setState( model.getState(), model.getRunState() );
    queueFigure.setRunState( model.getRunState(), model.getState() );
    super.refreshVisuals();
  }

  /**
   * Refresh the visuals when something have been changed in the model.
   * @param ev The event indicating change of the model.
   */
  @Override
  public void propertyChange( final PropertyChangeEvent ev ) {
    if ( ev.getPropertyName().equals( Queue.PROPERTY_NAME )
        || ev.getPropertyName().equals( Queue.PROPERTY_STATE ) 
        || ev.getPropertyName().equals( Queue.PROPERTY_RUN_STATE ) )
      // Due to multiple threads accessing GEF which is not thread safe
      this.display.syncExec( new Runnable() {  
        public void run() {  
            refreshVisuals(); 
        }  
      } 
      ); 
    else
      super.propertyChange( ev );
  }



}
