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

package eu.geclipse.jsdl;

import org.eclipse.core.resources.IFile;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Creator for JSDL job descriptions. Creates
 * {@link JSDLJobDescription}s from files with file extension ".jsdl". 
 */
public class JSDLJobDescriptionCreator
    extends AbstractGridElementCreator {
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent ) throws ProblemException {
    IGridElement result = null;
    IFile file = ( IFile ) getSource();
    if ( file != null ) {
      result = new JSDLJobDescription( file );
    }
    return result;
  }
  
}
