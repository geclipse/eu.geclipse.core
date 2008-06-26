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
package eu.geclipse.batch.ui.internal.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.BatchJobManager;
import eu.geclipse.batch.ui.internal.Messages;

public class Box extends BatchResource {

  public static final String CHILD_ADDED_PROP = "BatchDiagram.ChildAdded"; //$NON-NLS-1$
  public static final String CHILDREN_ADDED_PROP = "BatchDiagram.ChildrenAdded"; //$NON-NLS-1$
  public static final String CHILD_REMOVED_PROP = "BatchDiagram.ChildRemoved"; //$NON-NLS-1$
  private String name;
  private final Image BoxIcon = Activator.getDefault()
    .getImageRegistry()
    .get( Activator.IMG_COMPUTING_ELEMENT );
  private List<BatchResource> resources = new ArrayList<BatchResource>();
  private boolean IsNodes = false;


  public Box( final BatchJobManager jobManager ) {
    super( jobManager );
  }

  @Override
  public void addPropertyChangeListener( final PropertyChangeListener listener )
  {
    this.pcsDelegate.addPropertyChangeListener( listener );
  }

  @Override
  public void removePropertyChangeListener( final PropertyChangeListener listener )
  {
    this.pcsDelegate.removePropertyChangeListener( listener );
  }

  /**
   * Set the name of the Box.
   * <p>Set the name of the Box </p>
   * @param The name of the Box.
   * 
   */
  public void setName( final String name ) {
    this.name = name;
  }

  public boolean addChild( final BatchResource resource ) {
    boolean ret = false;
    if( resource != null && this.resources.add( resource ) ) {
      ret = true;
      firePropertyChange( CHILD_ADDED_PROP, null, resource );
    }
    return ret;
  }

  public boolean addChildren( final List<BatchResource> reses ) {
    boolean ret = false;
    if( reses != null && this.resources.addAll( reses ) ) {
      ret = true;
      firePropertyChange( CHILDREN_ADDED_PROP, null, reses );
    }
    return ret;
  }
  
  /**
   * Set true if this box is a nodes box
   *  @param True if is a node box.
  **/
  public void setIsNodes( final boolean v ) {
    this.IsNodes = v;
  }

  /**
   * Returns if this Box is a node box.
   * <p>Return true if this box is a node box or false </p>
   * @return Return true if this box is a node box or else false .
   */
  public boolean getIsNodes() {
    return this.IsNodes;
  }
  

  

  public boolean removeChild( final BatchResource resource ) {
    boolean ret = false;
    if( null != resource && this.resources.remove( resource ) ) {
      firePropertyChange( CHILD_REMOVED_PROP, null, resource );
      ret = true;
    }
    return ret;
  }

  public boolean removeChildren( final List<BatchResource> reses ) {
    boolean ret = false;
    if( null != reses && this.resources.removeAll( reses ) ) {
      firePropertyChange( CHILD_REMOVED_PROP, null, reses );
      ret = true;
    }
    return ret;
  }

  public List<BatchResource> getChildren() {
    return this.resources;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public Image getIcon() {
    return this.BoxIcon;
  }

  @Override
  public String getOutlineString() {
    return Messages.getString( "BoxElement" ) + this.name; //$NON-NLS-1$
  }

  public int compareTo( final Object o ) {
    // TODO Auto-generated method stub
    return 0;
  }
}
