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
package eu.geclipse.batch.ui.internal.model;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * A command to resize and/or move a batch resource.
 * The command can be undone or redone.
 */
public class BatchSetConstraintCommand extends Command {
  /** 
   * Stores the new size and location. 
   */
  private final Rectangle newBounds;
  /** 
   * Stores the old size and location. 
   */
  private Rectangle oldBounds;
  /** 
   * A request to move/resize an edit part.
   */
  private final ChangeBoundsRequest request;

  /** 
   * BatchResource to manipulate. 
   */
  private final BatchResource resource;

  /**
   * Create a command that can resize and/or move a shape.
   * @param resource the resource to manipulate
   * @param req the move and resize request
   * @param newBounds the new size and location
   * @throws IllegalArgumentException if any of the parameters is null
   */
  public BatchSetConstraintCommand( final BatchResource resource, final ChangeBoundsRequest req, 
                                    final Rectangle newBounds ) {
    if ( null == resource || null == req || null == newBounds ) {
        throw new IllegalArgumentException();
    }
    this.resource = resource;
    this.request = req;
    this.newBounds = newBounds.getCopy();
    setLabel( "move / resize" ); //$NON-NLS-1$
  }

  /**
   * Test if the move/resize can be executed.
   */
  @Override
  public boolean canExecute() {
    Object type = this.request.getType();
    // Make sure the Request is of a type we support:
    return (RequestConstants.REQ_MOVE.equals(type)
            || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
            || RequestConstants.REQ_RESIZE.equals(type)
            || RequestConstants.REQ_RESIZE_CHILDREN.equals(type));
  }

  /**
   * Execute the move/resize
   */
  @Override
  public void execute() {
    this.oldBounds = new Rectangle( this.resource.getLocation(), this.resource.getSize() );
    redo();
  }

  /**
   * Redo the move/resize
   */
  @Override
  public void redo() {
    this.resource.setSize( this.newBounds.getSize() );
    this.resource.setLocation( this.newBounds.getLocation() );
  }

  /**
   * Undo the move/resize
   */
  @Override
  public void undo() {
    this.resource.setSize( this.oldBounds.getSize() );
    this.resource.setLocation( this.oldBounds.getLocation() );
  }
}
