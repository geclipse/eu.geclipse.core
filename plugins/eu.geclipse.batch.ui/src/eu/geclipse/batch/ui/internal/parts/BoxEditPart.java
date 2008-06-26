/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ 
 *    Contributor(s): UCY (http://www.cs.ucy.ac.cy) 
 *        - Kyriakos Katsaris (kykatsar@gmail.com)
 ******************************************************************************/
package eu.geclipse.batch.ui.internal.parts;

import java.beans.PropertyChangeEvent;
import java.util.List;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import eu.geclipse.batch.ui.IBoxFigure;
import eu.geclipse.batch.ui.internal.BoxFigure;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.Box;
import eu.geclipse.batch.ui.internal.model.ComputingElement;

final public class BoxEditPart extends BatchEditPart  {
  
  
 
  public static Rectangle nodes ;
  public static  Rectangle queues ; 

  private int[] firstArea = new int[2]; //[0] for Queues , [1] for Nodes 
  private Dimension[] firstDimension = new Dimension[2]; //[0] for Queues , [1] for Nodes
  private boolean firstTime=false; 
  private boolean[] firstTimeArea = {false,false}; //[0] for Queues , [1] for Nodes
  private int areaQueues=0;
  private int areaNodes=0;
  
 
  @Override
  protected Figure createFigure() {
    BoxFigure box = new BoxFigure();
 
    return box;
  }

  @Override
  public void refreshVisuals() {
 
    IBoxFigure ceFigure = ( IBoxFigure )getFigure();
    Box model = ( Box )getModel();
    ceFigure.setName( model.getName() );
   try
   {
    
     queues = new Rectangle( model.getLocation(),model.getSize() );
     
    if (model.getIsNodes())
    {
      
      Dimension firstNodes = new Dimension (model.getSize());
      this.areaNodes= firstNodes.getArea();
      if(!this.firstTimeArea[1]){
       this.firstArea[1] = this.areaNodes;
       this.firstTimeArea[1]=true;
       this.firstDimension[1] =model.getSize();
       }
 
    
      
      if (model.getLocation()!=null && model.getLocation()!=null)
      {
        nodes = new Rectangle( model.getLocation(),model.getSize() );

      }

    }
    
    
   
     if (!model.getIsNodes())
    { 
       Dimension firstQueues = new Dimension (model.getSize());
          this.areaQueues= firstQueues.getArea();
          if(!this.firstTimeArea[0] ){
           this.firstArea[0] = this.areaQueues;
           this.firstTimeArea[0]=true;
           this.firstDimension[0] =model.getSize();
           }

        
           if(!(nodes.intersects(queues)||ComputingElementEditPart.CE.intersects( queues )) )
           {
             super.refreshVisuals();
           }
    }
    else if(!(queues.intersects(ComputingElementEditPart.CE)))
    {
     super.refreshVisuals();
   
    }
    
     if(this.areaQueues<this.firstArea[0] )
     {
      
       resizeAble(this.firstDimension[0],model);
       

     } 
    if(this.areaNodes<this.firstArea[1])
     {
      resizeAble(this.firstDimension[1],model);

     }

   }
   catch(Exception z)
   { 
     //no code needed
     System.out.println();
   } 
   
   if(!this.firstTime){
  super.refreshVisuals();}
   this.firstTime = true;
    
  }
  
  
  
  private void resizeAble(final Dimension initial,final Box model)
  {
      Rectangle rect = new Rectangle (model.getLocation(),initial);
      
      ( ( GraphicalEditPart )getParent() ).setLayoutConstraint( this, getFigure(), rect ); 
  }
  
  
 
  @Override
  public void activate() {
    if( !isActive() ) {
      super.activate();
    }
  }

  @Override
  protected List<BatchResource> getModelChildren() {
    return ( ( Box )getModel() ).getChildren();
  }

  @Override
  protected void createEditPolicies() {
    // Disallows the removal of this edit part from its parent
    installEditPolicy( EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy() );
    // Handles constraint changes (e.g. moving and/or resizing) of model
    // elements
    try {
      installEditPolicy( EditPolicy.LAYOUT_ROLE, new BatchLayoutEditPolicy() );
    } catch( Exception z ) { //no code needed
    }
  }

  private BatchResource getCastedModel() {
    return ( BatchResource )getModel();
  }

  public Box getMod() {
    return ( Box )getModel();
  }

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
  
 
  
  
  
  
  protected static class BatchLayoutEditPolicy extends FlowLayoutEditPolicy {

    /**
     * Sets the constrains for moving the figures within the component.
     * 
     * @param request The request.
     * @param child The object.
     * @param constraint The constraint.
     * @return Returns the <code>Command</code>
     */
    @Override
    protected Command createAddCommand( final EditPart child,
                                        final EditPart after )
    {

    return null;
    }

    @Override
    protected Command getCreateCommand( final CreateRequest request ) {
      return null;
    }

    @Override
    protected Command createMoveChildCommand( final EditPart child,
                                              final EditPart after )
    {
      return null;
    }
  }





 
}
