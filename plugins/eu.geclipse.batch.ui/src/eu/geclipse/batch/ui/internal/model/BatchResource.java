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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.graphics.Image;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import eu.geclipse.batch.BatchJobManager;

/**
 * Base class for the various model classes that models a batch resource.
 */
public abstract class BatchResource extends ModelElement {

  /**
   * Property ID to use when the location of this batch resource is modified.
   */
  public static final String LOCATION_PROP = "BatchResource.Location"; //$NON-NLS-1$
  /**
   * Property ID to use then the size of this batch resource is modified.
   */
  public static final String SIZE_PROP = "BatchResource.Size"; //$NON-NLS-1$
  /**
   * Property ID to use when the list of outgoing connections is modified.
   */
  public static final String SOURCE_CONNECTIONS_PROP = "BatchResource.SourceConn"; //$NON-NLS-1$
  /**
   * Property ID to use when the list of incoming connections is modified.
   */
  public static final String TARGET_CONNECTIONS_PROP = "BatchResource.TargetConn"; //$NON-NLS-1$
  
  private static final long serialVersionUID = 1;

  /**
   * The manager that keeps track of all the jobs in the batch service
   */
  protected BatchJobManager jobManager; 

  private Point location = new Point( 0, 0 );
  private Dimension size = new Dimension( 50, 50 );
  private List<Connection> sourceConnections = new ArrayList<Connection>();
  private List<Connection> targetConnections = new ArrayList<Connection>();

  /**
   * The default constructor
   * 
   * @param jobManager The manager of all the jobs residing in this batch service
   */
  public BatchResource( final BatchJobManager jobManager ) {
    this.jobManager = jobManager;
  }
  
  /**
   * Add an incoming or outgoing connection to this shape.
   * @param conn a non-null connection instance
   * @throws IllegalArgumentException if the connection is null or has not distinct endpoints
   */
  public void addConnection( final Connection conn ) {
    if ( null == conn || conn.getSource() == conn.getTarget() ) {
      throw new IllegalArgumentException();
    }
    if ( this == conn.getSource() ) {
      this.sourceConnections.add( conn );
      firePropertyChange( SOURCE_CONNECTIONS_PROP, null, conn );
    } else if ( this == conn.getTarget() ) {
      this.targetConnections.add( conn );
      firePropertyChange( TARGET_CONNECTIONS_PROP, null, conn );
    }
  }

  /**
   * Return a pictogram (small icon) describing this model element.
   * Children should override this method and return an appropriate Image.
   * @return a 16x16 Image or null
   */
  public abstract Image getIcon();

  /**
   * Return the Location of this shape.
   * @return a non-null location instance
   */
  public Point getLocation() {
    return this.location.getCopy();
  }

  /**
   * Return the Size of this shape.
   * @return a non-null Dimension instance
   */
  public Dimension getSize() {
    return this.size.getCopy();
  }

  /**
   * Return a List of outgoing Connections.
   * @return Returns a <code>List</code> of source connections.
   */
  public List<Connection> getSourceConnections() {
    return new ArrayList<Connection>( this.sourceConnections );
  }

  /**
   * Return a List of incoming Connections.
   * @return Returns a <code>List</code> of target connections.
   */
  public List<Connection> getTargetConnections() {
    return new ArrayList<Connection>( this.targetConnections );
  }

  /**
   * Remove an incoming or outgoing connection from this batch resource.
   * @param conn a non-null connection instance.
   * @throws IllegalArgumentException if the parameter is <code>null</code>.
   */
  public void removeConnection( final Connection conn ) {
    if ( null == conn ) {
      throw new IllegalArgumentException();
    }

    if ( this == conn.getSource() ) {
      this.sourceConnections.remove( conn );
      firePropertyChange( SOURCE_CONNECTIONS_PROP, null, conn );
    } else if ( this == conn.getTarget() ) {
      this.targetConnections.remove( conn );
      firePropertyChange( TARGET_CONNECTIONS_PROP, null, conn );
    }
  }

  /**
   * Set the Location of this batch resource.
   * @param newLocation a non-null Point instance.
   * @throws IllegalArgumentException if the parameter is <code>null</code>.
   */
  public void setLocation( final Point newLocation ) {
    if ( null == newLocation ) {
      throw new IllegalArgumentException();
    }
    this.location.setLocation( newLocation );
    firePropertyChange( LOCATION_PROP, null, this.location );
  }

  /**
   * Set the Size of this batch resource.
   * @param newSize The new size.
   */
  public void setSize( final Dimension newSize ) {
    if ( null != newSize ) {
      this.size.setSize( newSize );
      firePropertyChange( SIZE_PROP, null, this.size );
    }
  }

  /**
   * Returns the job manager for this batch resource. 
   * 
   * @return returns the manager {@link BatchJobManager} of this batch 
   * resource. 
   */
  public BatchJobManager getJobManager() {
    return this.jobManager;
  }
  
  /**
   * Returns a string describing the model element, to be used for the outline 
   * view. 
   * @return A string describing the model element.
   */
  public abstract String getOutlineString();
}
