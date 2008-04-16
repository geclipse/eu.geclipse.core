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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import eu.geclipse.batch.ui.IBoxFigure;
import eu.geclipse.batch.ui.internal.BoxFigure;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.Box;

final public class BoxEditPart extends BatchEditPart {

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
    super.refreshVisuals();
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
      installEditPolicy( EditPolicy.LAYOUT_ROLE, new BatchXYLayoutEditPolicy() );
    } catch( Exception z ) {
    }
  }

  private BatchResource getCastedModel() {
    return ( BatchResource )getModel();
  }

  public Box getMod() {
    return ( Box )getModel();
  }

  @Override
  public void propertyChange( final PropertyChangeEvent ev ) {
    // if (ev.getPropertyName().equals(B.CHILD_REMOVED_PROP)) refreshChildren();
    this.display.syncExec( new Runnable() {

      public void run() {
        refreshVisuals();
      }
    } );
  }
  protected static class BatchXYLayoutEditPolicy extends FlowLayoutEditPolicy {

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
