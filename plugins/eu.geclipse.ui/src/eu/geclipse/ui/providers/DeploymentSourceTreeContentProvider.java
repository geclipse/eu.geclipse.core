/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Yifan Zhou - initial API and implementation
 ******************************************************************************/
package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;

/**
 * @author Yifan Zhou
 */
public class DeploymentSourceTreeContentProvider
  implements ITreeContentProvider
{

  /**
   * Get all referenced projects.
   */
  private IGridElement[] referencedProjects;

  /**
   * Create a content provider.
   * 
   * @param referencedProjects All referenced projects.
   */
  public DeploymentSourceTreeContentProvider( final IGridElement[] referencedProjects )
  {
    this.referencedProjects = referencedProjects;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren( Object
   *      parentElement )
   */
  public Object[] getChildren( final Object parentElement ) {
    IGridElement[] children = null;
    try {
      if( ( ( IGridElement )parentElement ) instanceof IGridContainer ) {
        children = ( ( IGridContainer )parentElement ).getChildren( null );
      }
    } catch( GridModelException e ) {
      Activator.logException( e );
    }
    return ( children == null )
                               ? new IGridElement[ 0 ]
                               : children;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent( Object
   *      element )
   */
  public Object getParent( final Object element ) {
    Object parent = null;
    if( element instanceof IGridElement ) {
      parent = ( ( IGridElement )element ).getParent();
    }
    return parent;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren( Object
   *      element )
   */
  public boolean hasChildren( final Object element ) {
    return ( ( ( IGridElement )element ) instanceof IGridContainer )
                                                                    ? this.getChildren( element ).length > 0
                                                                    : false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
   *      Object inputElement )
   */
  public Object[] getElements( final Object inputElement ) {
    return this.referencedProjects;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  public void dispose() {
    // empty implementation
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged( final Viewer
   *      viewer, final Object oldInput, final Object newInput)
   */
  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput )
  {
    // empty implementation
  }
}
