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

package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchSite;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.dialogs.FileOverwriteDialog;


public class TransformAction extends Action {
  
  protected IGridElementCreator creator;
  
  protected IGridJobDescription[] input;
  
  private IWorkbenchSite site;
  
  public TransformAction( final String title,
                          final IGridElementCreator creator,
                          final IGridJobDescription[] input,
                          final IWorkbenchSite site) {
    super( title );
    this.creator = creator;
    this.input = input;
    this.site = site;
  }
  
  @Override
  public void run() {
    
 for (IGridJobDescription description : this.input ){
      
      IFile file = getFile( description );
      
      if ( file.exists() ){
        FileOverwriteDialog dialog = new FileOverwriteDialog( this.site.getShell(), file.getName() );
        if (dialog.open() == SWT.YES ){        
          try {
            file.delete( true, null );
            
            WorkspaceJob job = new WorkspaceJob( "transformer" ) { //$NON-NLS-1$
              @Override
              public IStatus runInWorkspace( final IProgressMonitor monitor )
                  throws CoreException {
                return transform( TransformAction.this.creator, TransformAction.this.input, monitor );
              }
            };
            job.setUser( true );
            job.schedule();
          } catch( CoreException e ) {
            Activator.logException( e );
        }
      }
    }
 }
    

     
  }
  
  protected IStatus transform( final IGridElementCreator creator,
                               final IGridJobDescription[] input,
                               final IProgressMonitor monitor ) {

    IStatus result = Status.OK_STATUS;
    List< IStatus > errors = new ArrayList< IStatus >();
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    lMonitor.beginTask( "Transforming job descriptions", input.length );
    
    try {
      for ( IGridJobDescription description : input ) {
        lMonitor.subTask( description.getName() );
        IStatus status = transform( creator, description );
        if ( ! status.isOK() ) {
          errors.add( status );
        }
        lMonitor.worked( 1 );
      }
    } finally {
      lMonitor.done();
    }
    
    if ( ( errors != null ) && ( errors.size() > 0 ) ) {
      if ( errors.size() == 1 ) {
        result = errors.get( 0 );
      } else {
        result = new MultiStatus(
            Activator.PLUGIN_ID,
            0,
            errors.toArray( new IStatus[ errors.size() ] ),
            "Transformation problems",
            null
        );
      }
    }
    
    return result;
  }
  
  protected IStatus transform2( final IGridElementCreator creator,
                               final IGridJobDescription[] input,
                               final IProgressMonitor monitor ) {

    IStatus result = Status.OK_STATUS;
    List< IStatus > errors = new ArrayList< IStatus >();
    
    for (IGridJobDescription description : input ){
      
      IFile file = getFile( description );
      
      if ( file.exists() ){
        FileOverwriteDialog dialog = new FileOverwriteDialog( this.site.getShell(), file.getName() );
        if (dialog.open() == SWT.YES){        
          try {
            file.delete( true, null );
            
            IProgressMonitor lMonitor
            = monitor == null
            ? new NullProgressMonitor()
            : monitor;
            
          lMonitor.beginTask( "Transforming job descriptions", input.length ); //$NON-NLS-1$
          
          try {
            for ( IGridJobDescription descr : input ) {
              
              lMonitor.subTask( descr.getName() );
              IStatus status = transform( creator, descr );
              if ( ! status.isOK() ) {
                errors.add( status );
              }
              lMonitor.worked( 1 );
            }
          } finally {
            lMonitor.done();
          }
          
          if ( ( errors != null ) && ( errors.size() > 0 ) ) {
            if ( errors.size() == 1 ) {
              result = errors.get( 0 );
            } else {
              result = new MultiStatus(
                  Activator.PLUGIN_ID,
                  0,
                  errors.toArray( new IStatus[ errors.size() ] ),
                  "Transformation problems", //$NON-NLS-1$
                  null
              );
            }
          }
            
          } catch( CoreException e ) {
            Activator.logException( e );
          }
        }
        else {
          result = new Status(
                              IStatus.OK,
                              Activator.PLUGIN_ID,
                              String.format( "Transformation canceleled for %s", file.getName() )); //$NON-NLS-1$
        }
      }
    }
    
    
    
    return result;
    
  }
  
  private IStatus transform( final IGridElementCreator creator,
                             final IGridJobDescription input ) {
    
    IStatus result = Status.OK_STATUS;
    
    creator.setSource( input );    
    IGridContainer parent = input.getParent();

    try {
      creator.create( parent );
    } catch ( ProblemException pExc ) {
      result = new Status(
                          IStatus.ERROR,
                          Activator.PLUGIN_ID,
                          String.format( "Transformation failed for %s", input.getName() ), //$NON-NLS-1$
                          pExc
      );
    }
    
    return result;
    
  }
  
  private IFile getFile( final IGridJobDescription inputDescr ){
    
    IFile returnFile = null;
    JSDLJobDescription jsdl = null;
    
    if ( inputDescr instanceof JSDLJobDescription ){
      jsdl = (JSDLJobDescription) inputDescr;
    }
    IPath jdlName = new Path( jsdl.getName() ).removeFileExtension().addFileExtension( "jdl" ); //$NON-NLS-1$
    IFile jdlFile = ( ( IContainer ) jsdl.getParent().getResource() ).getFile( jdlName );
    
    returnFile = jdlFile;
    
    return returnFile;
  }

}
