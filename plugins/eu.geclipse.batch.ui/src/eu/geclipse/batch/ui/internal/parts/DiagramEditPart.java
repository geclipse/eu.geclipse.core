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

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.BatchSetConstraintCommand;
import eu.geclipse.batch.ui.internal.model.ModelElement;

/**
 * This edit part server as the main diagram container, the white area where
 * everything else is in. Also responsible for the container's layout (the way
 * the container rearranges is contents) and the container's capabilities (edit
 * policies).
 */
public class DiagramEditPart extends AbstractGraphicalEditPart
  implements PropertyChangeListener
{

  protected Display display;

  /**
   * Return the "true" type of the model
   * 
   * @return the model as <code>BatchDiagram</code>
   */
  private BatchDiagram getCastedModel() {
    return ( BatchDiagram )getModel();
  }

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
   * Creates the edit policy for this diagram container.
   */
  @Override
  protected void createEditPolicies() {
    // Disallows the removal of this edit part from its parent
    installEditPolicy( EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy() );
    // Handles constraint changes (e.g. moving and/or resizing) of model
    // elements
    installEditPolicy( EditPolicy.LAYOUT_ROLE, new BatchXYLayoutEditPolicy() );
  }

  /**
   * Creates a figure.
   * 
   * @return Returns the created figure.
   */
  @Override
  protected IFigure createFigure() {
    Figure fig = new FreeformLayer();
    fig.setBorder( new MarginBorder( 3 ) );
    fig.setLayoutManager( new FreeformLayout() );
    // Create the static router for the connection layer
    ConnectionLayer connLayer = ( ConnectionLayer )getLayer( LayerConstants.CONNECTION_LAYER );
    connLayer.setConnectionRouter( new ShortestPathConnectionRouter( fig ) );
    return fig;
  }

  /**
   * Returns a list of all the figures in this diagram.
   * 
   * @return Returns a List of the children <code>BatchResource</code>.
   */
  @Override
  protected List<BatchResource> getModelChildren() {
    return getCastedModel().getChildren();
  }

  /**
   * Handle a property change event
   * 
   * @param evt The event.
   */
  public void propertyChange( final PropertyChangeEvent evt ) {
    String prop = evt.getPropertyName();
    if ( BatchDiagram.CHILD_ADDED_PROP.equals( prop )
        || BatchDiagram.CHILDREN_ADDED_PROP.equals( prop )
        || BatchDiagram.CHILD_REMOVED_PROP.equals( prop ) ) {
      this.display.syncExec( new Runnable() {

        @SuppressWarnings("synthetic-access")
        public void run() {
          refreshChildren();
        }
      } );
    }
  }
  /**
   * EditPolicy for the Figure used by this edit part. Children of
   * XYLayoutEditPolicy can be used in Figures with XYLayout.
   */
  protected static class BatchXYLayoutEditPolicy extends XYLayoutEditPolicy {

    /**
     * Sets the constrains for moving the figures within the component.
     * 
     * @param request The request.
     * @param child The object.
     * @param constraint The constraint.
     * @return Returns the <code>Command</code>
     */
    @Override
    protected Command createChangeConstraintCommand( final ChangeBoundsRequest request, final EditPart child,
                                                     final Object constraint )
    {
      Command command = null;
      if ( child instanceof BatchEditPart && constraint instanceof Rectangle ) {
        // return a command that can move and/or resize a Shape
        command = new BatchSetConstraintCommand( ( BatchResource )child.getModel(), request, ( Rectangle )constraint );
      } else
        command = super.createChangeConstraintCommand( request, child, constraint );
      return command;
    }

    /**
     * Not needed
     * 
     * @return Returns <code>null</code>
     */
    @Override
    protected Command createChangeConstraintCommand( final EditPart child, final Object constraint )
    {
      // Not needed
      return null;
    }

    /**
     * Not needed
     * 
     * @return Returns <code>null</code>
     */
    @Override
    protected Command getCreateCommand( final CreateRequest request ) {
      // Not needed
      return null;
    }
  }
}
