/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import org.eclipse.core.resources.IFile;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractFileElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Creator for batch service connection descriptions. Creates
 * {@link BatchQueueDescription}s from files with file extension ".qdl". 
 */
public class BatchQueueDescriptionCreator extends AbstractFileElementCreator {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractFileElementCreator#internalCanCreate(java.lang.String)
   */
  @Override
  protected boolean internalCanCreate( final String fileExtension ) {
    return "qdl".equalsIgnoreCase( fileExtension ); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Class)
   */
  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return IGridBatchQueueDescription.class.isAssignableFrom( elementType );
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent ) throws ProblemException
  {
    IGridElement result = null;
    IFile file = ( IFile ) getSource();
    if ( file != null ) {
      result = new BatchQueueDescription( file );
    }
    return result;
  }
}
