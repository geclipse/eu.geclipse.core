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
import java.beans.PropertyChangeListener;
import java.util.List;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.model.Connection;
import eu.geclipse.batch.ui.internal.model.ModelElement;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.internal.model.Box;
/**
 * EditPart used for BatchResource instances.
 */
public abstract class BatchEditPart extends AbstractGraphicalEditPart
  implements PropertyChangeListener, NodeEditPart
{
  protected Display display;

  private ConnectionAnchor anchor;


  /**
   * Upon activation, attach to the model element as a property change listener.
   */
  @Override
  public void activate() {
    if ( !isActive() ) {
      super.activate();
      ( ( ModelElement )getModel() ).addPropertyChangeListener( this );
      this.display = this.getRoot().getViewer().getControl().getDisplay();
    }
  }


  /**
   * Upon deactivation, detach from the model element as a property change
   * listener.
   */
  @Override
  public void deactivate() {
    if ( isActive() ) {
      super.deactivate();
      ( ( ModelElement )getModel() ).removePropertyChangeListener( this );
    }
  }

  /**
   * Create the edit policies.
   */
  @Override
  protected void createEditPolicies() {
    // Do nothing
  }

  /**
   * Create the Figure.
   */
  @Override
  protected abstract IFigure createFigure() ;

  /**
   * Return the model as a BatchResource.
   * @return The model as a BatchResource.
   */
  private BatchResource getCastedModel() {
    return ( BatchResource )getModel();
  }

  /**
   * Return the connection anchors of this model.
   * @return The connection anchors.
   */
  protected ConnectionAnchor getConnectionAnchor() {
    if( null == this.anchor ) {
      if ( getModel() instanceof WorkerNode )
        this.anchor = new EllipseAnchor( getFigure() );
      else if ( getModel() instanceof Queue )
        this.anchor = new EllipseAnchor( getFigure() );
      else if ( getModel() instanceof Box )
       {this.anchor = new EllipseAnchor( getFigure() );}
      else if ( getModel() instanceof ComputingElement )
        {this.anchor = new ChopboxAnchor( getFigure() );}
      else {
        // if BatchResource gets extended the conditions above must be updated
        throw new IllegalArgumentException( 
                                     Messages.getString( "BatchEditPart.Error.UnexpectedModel" ) ); //$NON-NLS-1$
      }
    }
    return this.anchor;
  }

  /**
   * Returns all the connections which has this model as a source.
   * @return A List of the <code>Connection</code>.
   */
  @Override
  protected List<Connection> getModelSourceConnections() {
    return getCastedModel().getSourceConnections();
  }

  /**
   * Returns all the connections which has this model as a target.
   * @return A List of the <code>Connection</code>.
   */
  @Override
  protected List<Connection> getModelTargetConnections() {
    return getCastedModel().getTargetConnections();
  }

  public ConnectionAnchor getSourceConnectionAnchor( final ConnectionEditPart connection )
  {
    return getConnectionAnchor();
  }

  public ConnectionAnchor getSourceConnectionAnchor( final Request request ) {
    return getConnectionAnchor();
  }

  public ConnectionAnchor getTargetConnectionAnchor( final ConnectionEditPart connection ) {
    return getConnectionAnchor();
  }

  public ConnectionAnchor getTargetConnectionAnchor( final Request request ) {
    return getConnectionAnchor();
  }

  public void propertyChange( final PropertyChangeEvent evt ) {
    String prop = evt.getPropertyName();
    if ( BatchResource.SIZE_PROP.equals( prop ) || BatchResource.LOCATION_PROP.equals( prop ) ) {
      refreshVisuals();
    } else if ( BatchResource.SOURCE_CONNECTIONS_PROP.equals( prop ) ) {
      // Due to multiple threads accessing GEF which is not thread safe
      this.display.syncExec( new Runnable() {  
        @SuppressWarnings("synthetic-access")
        public void run() {  
          refreshSourceConnections();
              
        }  
      } 
      ); 
    } else if ( BatchResource.TARGET_CONNECTIONS_PROP.equals( prop ) ) {
      // Due to multiple threads accessing GEF which is not thread safe
      this.display.syncExec( new Runnable() {  
        @SuppressWarnings("synthetic-access")
        public void run() {  
          refreshTargetConnections();
          
        }  
      } 
      ); 
    }
  }

  /**
   * Refreshes the visual, i.e. the Figure.
   */
  @Override
  protected void refreshVisuals() {

    
    Rectangle bounds = new Rectangle( getCastedModel().getLocation(),
                                      getCastedModel().getSize() );
  
    
    ( ( GraphicalEditPart )getParent() ).setLayoutConstraint( this, getFigure(), bounds );
  }
}