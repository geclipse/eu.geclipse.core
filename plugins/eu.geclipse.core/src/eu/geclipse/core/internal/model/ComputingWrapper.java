/*****************************************************************************
 * Copyright (c) 2006 - 2008 g-Eclipse Consortium 
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
 *    Harald Gjermundrod - added the getHostName method
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.reporting.ProblemException;

public class ComputingWrapper
    extends VirtualGridContainer
    implements IGridComputing, IGridContainer, IWrappedElement {
  
  private IGridComputing computing;
  
  /**
   * The argument constructor for this class.
   * @param parent The container for this storage.
   * @param computing The computing resource that is wrapped.
   */  
  public ComputingWrapper( final IGridContainer parent,
                           final IGridComputing computing ) {
    super( parent, computing.getName() );
    this.computing = computing;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  public boolean canContain( final IGridElement element ) {
    return (element instanceof IGridApplication || element instanceof ContainerMarker);
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */
 
  public URI getURI() {
    return ( ( IGridComputing ) getWrappedElement() ).getURI();
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridResource#getHostName()
   */
  public String getHostName() {
    return ( ( IGridComputing ) getWrappedElement() ).getHostName();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#getResource()
   */
  @Override
  public IResource getResource() {
    return getWrappedElement().getResource();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IWrappedElement#getWrappedElement()
   */
  public IGridElement getWrappedElement() {
    return this.computing;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return getWrappedElement().isLocal();
  }
  
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor )
      throws GridModelException {
    IStatus status = Status.OK_STATUS;
    IVirtualOrganization vo = getProject().getVO();
    if ( vo != null ) {
      IGridApplicationManager manager = vo.getApplicationManager();
      if ( manager != null ) {
        try {
          IGridApplication[] applications = manager.getApplications( this, monitor );
          if ((applications != null) && (applications.length>0)) {
          for ( IGridApplication app : applications ) {
            addElement( app );
          }
          }
            else {
              addElement( new ContainerMarker( this,
                                               ContainerMarker.MarkerType.INFO,
                                               "No matching elements found" ) ); //$NON-NLS-1$
            }
        } catch ( ProblemException pExc ) {
          throw new GridModelException( pExc.getProblem() );
        }
      }
      else {
        addElement( new ContainerMarker( this,
                                         ContainerMarker.MarkerType.INFO,
                                         "No matching elements found" ) ); //$NON-NLS-1$
      }
    }
    return status;
  }

}
