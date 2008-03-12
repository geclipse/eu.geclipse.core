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
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.batch.ui.internal.model.Connection;
import eu.geclipse.batch.ui.internal.model.ModelElement;

/**
 * Edit part for Connection model elements.
 */
public class ConnectionEditPart extends AbstractConnectionEditPart
implements PropertyChangeListener {
  protected Display display;

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
   * Sets the edit policy for the connection.
   */
  @Override
  protected void createEditPolicies() {
    // Makes the connection show a feedback, when selected by the user.
    installEditPolicy( EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy() );
  }

  /**
   * Creates the connection figure.
   * @return Returns the figure.
   */
  @Override
  protected IFigure createFigure() {
    PolylineConnection connection = ( PolylineConnection )super.createFigure();
    connection.setTargetDecoration( new PolygonDecoration() );
    connection.setLineStyle( getCastedModel().getLineStyle() );
    return connection;
  }

  private Connection getCastedModel() {
    return ( Connection )getModel();
  }

  /**
   * A property change event has been fired.
   * @param event The fired event.
   */
  public void propertyChange( final PropertyChangeEvent event ) {
    String property = event.getPropertyName();
    if ( Connection.LINESTYLE_PROP.equals( property ) ) {
      ( ( PolylineConnection )getFigure() ).setLineStyle( getCastedModel().getLineStyle() );
    }
  }
}