/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridResourceContainer;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Internal implementation of the {@link IGridResourceContainer} interface.
 */
public class ResourceCategoryContainer
    extends VirtualGridContainer
    implements IGridResourceContainer {
  
  /**
   * The container's category.
   */
  private IGridResourceCategory category;
  
  /**
   * List of permanent children of the container, i.e. child resource
   * containers.
   */
  private List< IGridElement > permanentChildren
    = new ArrayList< IGridElement >();
  
  /**
   * Create a new resource category container.
   * 
   * @param parent The parent container of this container.
   * @param category The category corresponding to this container.
   */
  protected ResourceCategoryContainer( final IGridContainer parent,
                                       final IGridResourceCategory category ) {
    super( parent, category.getName() );
    this.category = category;
    if ( parent instanceof ResourceCategoryContainer ) {
      ( ( ResourceCategoryContainer ) parent ).permanentChildren.add( this );
    }
  }
  
  /**
   * Add the specified {@link ResourceCategoryContainer} as a permanent child
   * to this container. To remove a child container use
   * {@link #delete(IGridElement)}.
   * 
   * @param child The new child of this container.
   * @throws ProblemException If the child could not be added.
   */
  public void addChild( final ResourceCategoryContainer child )
      throws ProblemException {
    addElement( child );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return ( element instanceof IGridResource )
      || ( element instanceof ResourceCategoryContainer )
      || ( element instanceof ContainerMarker );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridResourceContainer#getCategory()
   */
  public IGridResourceCategory getCategory() {
    return this.category;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor )
      throws ProblemException {
  
    for ( IGridElement permantenChild : this.permanentChildren ) {
      addElement( permantenChild );
    }
    
    if ( this.category.isActive() ) {

      IGridResource[] resources
        = getProject().getVO().getAvailableResources( this.category, true, monitor);

      if ( ( resources != null ) && ( resources.length > 0 ) ) {
        lock();
        try {
          for ( IGridElement resource : resources ) {
            addElement( resource );
          }
        } finally {
          unlock();
        }
      } else {
        addElement( new ContainerMarker( this,
                                         ContainerMarker.MarkerType.INFO,
                                         Messages.getString("ResourceCategoryContainer.no_matching_elements_error") ) ); //$NON-NLS-1$
      }
    }
    
    return Status.OK_STATUS;
  }

}
