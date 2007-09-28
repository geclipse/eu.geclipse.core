/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridTransfer;
import eu.geclipse.core.model.impl.AbstractGridContainer;

/**
 * Core implementation of the {@link IGridTransfer} interface. This
 * is a very preliminary implementation and may for sure change in
 * the future.
 */
public class GridTransfer
    extends AbstractGridContainer
    implements IGridTransfer {
  
  /**
   * The parent element of this Grid transfer.
   */
  private IGridTransfer parent;
  
  /**
   * The source element of this transfer.
   */
  private IGridElement source;
  
  /**
   * Construct a new Grid transfer with the specified parent and source.
   * 
   * @param parent The parent element of this transfer element.
   * @param source The source element that should be transfered.
   */
  protected GridTransfer( final GridTransfer parent,
                          final IGridElement source ) {
    this.parent = parent;
    this.source = source;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof IGridTransfer;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return this.source.getName();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  public IGridContainer getParent() {
    return this.parent;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getPath()
   */
  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridTransfer#getSource()
   */
  public IGridElement getSource() {
    return this.source;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridTransfer#getTarget()
   */
  public IGridContainer getTarget() {
    return ( ( IGridTransfer ) getParent() ).getTarget();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridTransfer#isAtomic()
   */
  public boolean isAtomic() {
    return this.source != null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isLazy()
   */
  public boolean isLazy() {
    return false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }
  
  protected void prepareTransfer( final IProgressMonitor monitor )
      throws GridModelException {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    localMonitor.beginTask( Messages.getString("GridTransfer.preparing_transfer_progress") + getName(), 10 ); //$NON-NLS-1$
    
    try {
    
      this.deleteAll();
      if ( this.source instanceof IGridContainer ) {
        IGridContainer container = ( IGridContainer ) this.source;
        IGridElement[] children
          = container.getChildren( new SubProgressMonitor( localMonitor, 1 ) );
        for ( IGridElement child : children ) {
          GridTransfer transfer = new GridTransfer( this, child );
          addElement( transfer );
          transfer.prepareTransfer( new SubProgressMonitor( localMonitor, 9 ) );
        }
      }
      
    } finally {
      localMonitor.done();
    }
    
  }

}
