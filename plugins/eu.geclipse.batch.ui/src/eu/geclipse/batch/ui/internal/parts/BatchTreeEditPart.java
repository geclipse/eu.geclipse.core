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
import org.eclipse.swt.graphics.Image;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.ModelElement;

/**
 * TreeEditPart used for BatchResource instances. This is used in the Outline 
 * View of the BatchEditor.
 */
public class BatchTreeEditPart extends AbstractTreeEditPart
  implements PropertyChangeListener
{
  /**
   * Create a new instance of this edit part using the given model element.
   *
   * @param model a non-null BatchResource instance
   */
  BatchTreeEditPart( final BatchResource model ) {
    super( model );
  }

  /**
   * Upon activation, attach to the model element as a property change listener.
   */
  @Override
  public void activate() {
    if ( !isActive() ) {
      super.activate();
      ( ( ModelElement )getModel() ).addPropertyChangeListener( this );
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

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
   */
  @Override
  protected void createEditPolicies() {
    // Do nothing
  }

  private BatchResource getCastedModel() {
    return ( BatchResource )getModel();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
   */
  @Override
  protected Image getImage() {
    return getCastedModel().getIcon();
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
   */
  @Override
  protected String getText() {
    return getCastedModel().getOutlineString();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  public void propertyChange( final PropertyChangeEvent evt ) {
    // No code needed   
  }
}