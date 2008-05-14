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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.reporting.ProblemException;

public class QueryContainer
    extends VirtualGridContainer
    implements IQueryInputProvider {
  
  private IQueryInputProvider inputProvider;
  
  private List< IQueryFilter > filters
    = new ArrayList< IQueryFilter >();
  
  private boolean queryAsChildren;
  
  private List< IGridElement > permanentChildren
    = new ArrayList< IGridElement >();
  
  public QueryContainer( final IGridContainer parent,
                         final String name,
                         final IQueryInputProvider inputProvider ) {
    super( parent, name );
    this.inputProvider = inputProvider;
    this.queryAsChildren = true;
  }
  
  public QueryContainer( final QueryContainer parent,
                         final String name ) {
    this( parent, name, parent );
    parent.permanentChildren.add( this );
  }
  
  public void addFilter( final IQueryFilter filter ) {
    this.filters.add( filter );
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return ( true );
  }
  
  public boolean getQueryAsChildren() {
    return this.queryAsChildren;
  }

  public boolean isLocal() {
    return true;
  }
  
  public void setQueryAsChildren( final boolean b ) {
    this.queryAsChildren = b;
  }
  
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor )
      throws GridModelException {
    
    for ( IGridElement permantenChild : this.permanentChildren ) {
      addElement( permantenChild );
    }

    if ( this.queryAsChildren ) {
    
      IGridElement[] query = null;
      
      try {
        query = query( monitor );
      } catch ( ProblemException pExc ) {
        throw new GridModelException( pExc.getProblem() );
      }
  
      // Add the children to the container
      if ( ( query != null ) && ( query.length > 0 ) ) {
        lock();
        try {
          for ( IGridElement child : query ) {
            addElement( child );
          }
        } finally {
          unlock();
        }
      } else {
        addElement( new ContainerMarker( this,
                                         ContainerMarker.MarkerType.INFO,
                                         "No matching elements found" ) );
      }
      
    }

    return Status.OK_STATUS;

  }

  protected IGridElement[] query( final IProgressMonitor monitor )
      throws ProblemException {
    IGridElement[] input = getInput( monitor );
    for ( IQueryFilter filter : this.filters ) {
      input = filter.filter( input );
    }
    return input;
  }

  public IGridElement[] getInput( final IProgressMonitor monitor )
      throws ProblemException {
    return this.inputProvider instanceof QueryContainer
    ? ( ( QueryContainer ) this.inputProvider ).query( monitor )
        : this.inputProvider.getInput( monitor );
  }
  
}
