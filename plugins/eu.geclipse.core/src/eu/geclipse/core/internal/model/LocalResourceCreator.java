/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Creator for local resources like files and folders.
 */
public class LocalResourceCreator
    extends AbstractGridElementCreator {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Class)
   */
  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return LocalFile.class.isAssignableFrom( elementType )
      || LocalFolder.class.isAssignableFrom( elementType );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent ) throws ProblemException {
    
    IGridElement result = null;
    
    Object source = getSource();
    
    if ( source instanceof IProject ) {
      IProject project = ( IProject ) source;
      if ( project.getName().equals( HiddenProject.NAME ) ) {
        try {
          result = HiddenProject.getInstance( project );
        } catch ( ProblemException pExc ) {
          Activator.logException( pExc );
        }
      } else {
        result = new GridProject( project );
      }
    }
    
    else if ( source instanceof IFile ) {
      result = new LocalFile( ( IFile ) source );
    }
    
    else if ( source instanceof IFolder ) {
      result = new LocalFolder( ( IFolder ) source );
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElementCreator#internalCanCreate(java.lang.Object)
   */
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    return ( fromObject instanceof IFile ) || ( fromObject instanceof IFolder );
  }
  
}
