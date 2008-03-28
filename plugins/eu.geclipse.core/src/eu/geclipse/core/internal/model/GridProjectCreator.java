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

import org.eclipse.core.resources.IProject;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

/**
 * Implementation of the {@link IGridElementCreator} interface
 * for projects in the workspace. These projects have not
 * to be grid projects.
 */
public class GridProjectCreator
    extends AbstractGridElementCreator {

  public boolean canCreate( final Class< ? extends IGridElement > elementType ) {
    return IGridProject.class.isAssignableFrom( elementType );
  }
  
  public IGridElement create( final IGridContainer parent ) {
    
    IGridProject result = null;
    IProject project = ( IProject ) getObject();
    String name = project.getName();
    
    if ( name.equals( HiddenProject.NAME ) ) {
      try {
        result = HiddenProject.getInstance( project );
      } catch ( GridModelException gmExc ) {
        Activator.logException( gmExc );
      }
    } else {
      result = new GridProject( project );
    }
    
    return result;
    
  }
  
  @Override
  public boolean internalCanCreate( final Object object ) {
    return ( object instanceof IProject );
  }

}
