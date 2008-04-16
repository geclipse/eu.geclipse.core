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
import eu.geclipse.batch.ui.IWorkerNodeFigure;
import eu.geclipse.batch.ui.internal.WorkerNodeFigure;
import eu.geclipse.batch.ui.internal.model.WorkerNode;

/**
 * Class that connects together the Figure and Model of a Worker Node.
 */
public final class WorkerNodeEditPart extends BatchEditPart {

  /**
   * Creates the figure that represents this modeled object.
   * @return Returns the created Figure
   */
  @Override
  protected Figure createFigure() {
    WorkerNodeFigure wnf = new WorkerNodeFigure();

    return wnf;
  }

  /**
   * Refreshes the visual elements (Figure) of this model.
   */
  @Override
  public void refreshVisuals() {
    int numJobs;
    IWorkerNodeFigure wnFigure = ( IWorkerNodeFigure )getFigure();
    WorkerNode model = ( WorkerNode )getModel();

    if ( null != model.getJobIds() )
      numJobs = model.getJobIds().size();
    else
      numJobs = 0;

    wnFigure.setFQDN( model.getFQDN() );
    wnFigure.setState( model.getState(), numJobs );
    super.refreshVisuals();
  }

  /**
   * Refresh the visuals when something have been changed in the model.
   * @param ev The event indicating change of the model.
   */
   @Override
  public void propertyChange( final PropertyChangeEvent ev ) {
    if ( ev.getPropertyName().equals( WorkerNode.PROPERTY_FQDN )
         || ev.getPropertyName().equals( WorkerNode.PROPERTY_STATE )
         || ev.getPropertyName().equals( WorkerNode.PROPERTY_TOTAL_WN_JOBS ) )
       // Due to multiple threads accessing GEF which is not thread safe
       this.display.syncExec( new Runnable() {
         public void run() {
           refreshVisuals();
         }
       }
       );

       //refreshVisuals();
     else
       super.propertyChange( ev );
   }
}
