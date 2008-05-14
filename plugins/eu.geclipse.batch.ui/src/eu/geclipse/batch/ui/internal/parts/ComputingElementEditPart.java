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
import org.eclipse.draw2d.geometry.Rectangle;
import eu.geclipse.batch.ui.IComputingElementFigure;
import eu.geclipse.batch.ui.editors.BatchEditor;
import eu.geclipse.batch.ui.internal.ComputingElementFigure;
import eu.geclipse.batch.ui.internal.model.ComputingElement;

/**
 * Class that connects together the Figure and Model of Computing Element.
 */
public final class ComputingElementEditPart extends BatchEditPart {

  public static  Rectangle CE ;
  public BatchEditor editor;
  private boolean firstTime=false;
  /**
   * Creates the figure that represents this modeled object.
   * @return Returns the created Figure
   */
  
 /* public ComputingElementEditPart(BatchEditor editor)
  {
    this.editor=editor;
  }*/
  @Override
  protected Figure createFigure() {
    
    ComputingElementFigure cef = new ComputingElementFigure();

    return cef;
  }

  /**
   * Refreshes the visual elements (Figure) of this model.
   */
  @Override
  public void refreshVisuals() {
    
    IComputingElementFigure ceFigure = ( IComputingElementFigure )getFigure();
    ComputingElement model = ( ComputingElement )getModel();
    ceFigure.setFQDN( model.getFQDN() );
    ceFigure.setType( model.getType() );
    ceFigure.setNumWNs( model.getNumWNs() );
    ceFigure.setNumQueues( model.getNumQueues() );
    ceFigure.setNumJobs( model.getNumJobs() );
 
    try
    {  
      CE  = new Rectangle( model.getLocation(),model.getSize() );

     if(!(CE.intersects(BoxEditPart.queues)||BoxEditPart.queues.intersects(CE)))
      {
       super.refreshVisuals();
     }

     this.firstTime=true;
     
    }
    catch(Exception z)
    { 
      System.out.println(z);
    } 
    if(!this.firstTime)
   super.refreshVisuals();
    this.firstTime = true;
    
   
  }

 /**
  * Refresh the visuals when something have been changed in the model.
  * @param ev The event indicating change of the model.
  */
  @Override
  public void propertyChange( final PropertyChangeEvent ev ){
    if ( ev.getPropertyName().equals( ComputingElement.PROPERTY_FQDN )
        || ev.getPropertyName().equals( ComputingElement.PROPERTY_TYPE )
        || ev.getPropertyName().equals( ComputingElement.PROPERTY_NUM_WN )
        || ev.getPropertyName().equals( ComputingElement.PROPERTY_NUM_QUEUE )
        || ev.getPropertyName().equals( ComputingElement.PROPERTY_NUM_JOBS ) )
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
