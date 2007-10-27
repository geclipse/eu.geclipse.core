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
 *    Pawel Wolniewicz 
 *    Mariusz Wojtysiak
 *****************************************************************************/
package eu.geclipse.core.jobs;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.impl.AbstractGridJobCreator;
import eu.geclipse.jsdl.JSDLJobDescription;

/**
 * GridJobCreator create grid job. This is middleware independent. Grid job
 * contains of GridJobID, IGridJobDescription and GridJobStatus. And those three
 * components can be middleware dependent.
 */
public class GridJobCreator extends AbstractGridJobCreator {

  private static final String TMPJOBFILE = ".job.tmp"; //$NON-NLS-1$

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Class)
   */
  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return IGridJob.class.isAssignableFrom( elementType );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent )
    throws GridModelException
  {
    IResource resource = ( IResource )getObject();
    if( resource == null ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED );
    }
    if( !( resource instanceof IFolder ) ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED );
    }
    return new GridJob( ( IFolder )resource );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.impl.AbstractGridJobCreator#internalCanCreate(eu.geclipse.core.model.IGridJobDescription)
   */
  @Override
  protected boolean internalCanCreate( final IGridJobDescription description ) {
    return ( description instanceof JSDLJobDescription );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.impl.AbstractGridElementCreator#internalCanCreate(java.lang.Object)
   */
  @Override
  protected boolean internalCanCreate( final Object object ) {
    return object instanceof IFolder
                                    ? GridJob.canCreate( ( IFolder )object )
                                    : false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobCreator#create(eu.geclipse.core.model.IGridContainer,
   *      eu.geclipse.core.model.IGridJobID)
   */
  public void create( final IGridContainer parent, final IGridJobID id )
    throws GridModelException
  {
    if( !( id instanceof GridJobID ) ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    null,
                                    "Cannot create GridJob from job id not implementing GriJobID " );
    }
    IGridJobDescription description = getDescription();
    IFolder jobFolder = findJobFileName( description,
                                         ( IFolder )parent.getResource() );
    IPath fullPath = jobFolder.getFullPath();
    IPath tmpPath = fullPath;
    // IPath tmpPath = fullPath.addFileExtension( "tmp.job" );
    try {
      // create temporary job folder, to prevent adding new GridJob to project
      // IPath stateLocation = Activator.getDefault().getStateLocation();
      IFolder tmpJobFolder = ( ( IFolder )parent.getResource() ).getFolder( tmpPath.lastSegment() );
      tmpJobFolder.delete( true, null );
      tmpJobFolder.create( true, true, null );
      GridJob.createJobStructure( tmpJobFolder, ( GridJobID )id, description );
      this.canCreate( tmpJobFolder );
      parent.create( this );// .refresh( null );
      // move folder to its final location. This will add job to the project
      // tmpJobFolder.move( fullPath, true, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc,
                                    "Problem while creating job folder "
                                        + jobFolder.getName() );
    }
  }

  /**
   * @param baseName
   * @param folder
   * @return
   */
  private IFolder findJobFileName( final IGridJobDescription description,
                                   final IFolder folder )
  {
    String baseName = description.getPath().removeFileExtension().lastSegment();
    // IFolder folder = ( IFolder )parent.getResource();
    String name = "." + baseName + ".job"; //$NON-NLS-1$ //$NON-NLS-2$
    IFolder jobFolder = folder.getFolder( name );
    // IFile jobFile = folder.getFile( name );
    int jobNum = 0;
    while( jobFolder.exists() ) {
      jobNum++;
      name = "." + baseName + "[" + jobNum + "].job"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      jobFolder = folder.getFolder( name );
    }
    return jobFolder;
  }
}
