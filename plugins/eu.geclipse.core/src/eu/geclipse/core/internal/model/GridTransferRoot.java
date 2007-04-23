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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * Root element for Grid transfers. This
 * is a very preliminary implementation and may for sure change in
 * the future.
 */
public class GridTransferRoot
    extends GridTransfer {
  
  private static final String TRANSFER_QUALIFIER = "transfer@"; //$NON-NLS-1$
  
  /**
   * The name of this element.
   */
  private String name;
  
  /**
   * The target of this transfer.
   */
  private IGridContainer target;
  
  /**
   * Construct a new transfer root with the specified sources and target.
   * 
   * @param sources The elements that should be transfered.
   * @param target The target to where to transfer the elements.
   */
  public GridTransferRoot( final IGridElement[] sources,
                           final IGridContainer target ) {
    super( null, null );
    this.target = target;
    this.name = TRANSFER_QUALIFIER + System.currentTimeMillis();
    
    for ( IGridElement element : sources ) {
      try {
        GridTransfer transfer = new GridTransfer( this, element );
        addElement( transfer );
      } catch( GridModelException gmExc ) {
        // Should never happen. If it does we just log it
        Activator.logException( gmExc );
      }
    }
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  @Override
  public IFileStore getFileStore() {
    return TransferManager.getTransferManagerStore().getChild( getName() );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  @Override
  public IGridContainer getParent() {
    return TransferManager.getManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.GridTransfer#getTarget()
   */
  @Override
  public IGridContainer getTarget() {
    return this.target;
  }
  
  /**
   * Start the transfer process. Empty implementation for the moment.
   */
  public void startTransfer() {
    // empty implementation
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.GridTransfer#prepareTransfer(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected void prepareTransfer( final IProgressMonitor monitor )
      throws GridModelException {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    localMonitor.beginTask( Messages.getString("GridTransferRoot.preparing_transfer_progress"), getChildCount() ); //$NON-NLS-1$
    
    try {
      
      IGridElement[] children = getChildren( null );
      for ( IGridElement child : children ) {
        GridTransfer transfer = ( GridTransfer ) child;
        transfer.prepareTransfer( new SubProgressMonitor( localMonitor, 1 ) );
      }
      
    } finally {
      localMonitor.done();
    }
    
  }

}
