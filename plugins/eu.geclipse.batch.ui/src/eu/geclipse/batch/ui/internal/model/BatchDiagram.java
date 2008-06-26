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

/**
 * The root of the model data structure which contains Batch Resources,
 * like Queues, Computing Element, Worker Nodes.
 */
public class BatchDiagram extends ModelElement {
  /**
   * Property ID to use when a child is added to this diagram.
   */
  public static final String CHILD_ADDED_PROP = "BatchDiagram.ChildAdded"; //$NON-NLS-1$
  /**
   * Property ID to use when children are added to this diagram.
   */
  public static final String CHILDREN_ADDED_PROP = "BatchDiagram.ChildrenAdded"; //$NON-NLS-1$
  /**
   * Property ID to use when a child is removed from this diagram.
   */
  public static final String CHILD_REMOVED_PROP = "BatchDiagram.ChildRemoved"; //$NON-NLS-1$

  private static final long serialVersionUID = 1;
  private List<BatchResource> resources = new ArrayList< BatchResource >();

  /**
   * Add a resource to this diagram.
   * @param resource a non-null resource instance.
   * @return <code>true</code>, if the resource was added, <code>false</code> otherwise.
   */
  public boolean addChild( final BatchResource resource ) {
    boolean ret = false;

    if ( resource != null && this.resources.add( resource ) ) {
      ret = true;

      firePropertyChange( CHILD_ADDED_PROP, null, resource );
    }
    return ret;
  }

  /**
   * Add a list of resources to this diagram.
   * @param reses a list of non-null resource instance.
   * @return <code>true</code>, if the resource was added, <code>false</code> otherwise.
   */
  public boolean addChildren( final List < BatchResource > reses ) {
    boolean ret = false;

    if ( reses != null && this.resources.addAll( reses ) ) {
      ret = true;
      
      firePropertyChange( CHILDREN_ADDED_PROP, null, reses );
    }
    return ret;
  }

  /**
   * Return a List of Resources in this diagram.
   * NOTE: The returned List should not be modified.
   * @return Returns the list of the resources.
   */
  public List<BatchResource> getChildren() {
    return this.resources;
  }

  /**
   * Remove a shape from this diagram.
   * @param resource a non-null resource instance;
   * @return <code>true</code, if the resource was removed, <code>false</code> otherwise
   */
  public boolean removeChild( final BatchResource resource ) {
    boolean ret = false;

    if ( null != resource && this.resources.remove( resource ) ) {
      firePropertyChange( CHILD_REMOVED_PROP, null, resource );
      ret = true;
    }

    return ret;
  }

  public int compareTo(final Object o ) {
    // TODO Auto-generated method stub
    return 0;
  }
}
