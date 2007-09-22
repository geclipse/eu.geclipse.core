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

package eu.geclipse.core.project;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * The project nature of a grid project.
 * 
 * @author stuempert-m
 */
public class GridProjectNature implements IProjectNature {
  
  /**
   * The project this project nature belongs to.
   */
  private IProject project;

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#configure()
   */
  public void configure() throws CoreException {
    addBuilder( GridProjectBuilder.getID() );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#deconfigure()
   */
  public void deconfigure() throws CoreException {
    removeBuilder( GridProjectBuilder.getID() );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#getProject()
   */
  public IProject getProject() {
    return this.project;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
   */
  public void setProject( final IProject project ) {
    this.project = project;
  }
  
  /**
   * The ID of this project nature.
   * 
   * @return A unique ID used to refer to this project nature.
   */
  public static String getID() {
    return GridProjectNature.class.getName();
  }
  
  /**
   * Add the builder with the specified name to this nature.
   * 
   * @param builderName The name of the builder to be added.
   * 
   * @throws CoreException If an exception occurs when trying to
   * add the specified builder.
   */
  private void addBuilder( final String builderName ) throws CoreException {
    
    IProject proj = getProject();
    IProjectDescription desc = proj.getDescription();
    ICommand[] commands = desc.getBuildSpec();
    
    boolean found = false;
    for ( int i = 0 ; ( i < commands.length ) && !found ; i++ ) {
      if ( commands[ i ].getBuilderName().equals( builderName ) ) {
        found = true;
      }
    }
    
    if ( !found ) {
      ICommand command = desc.newCommand();
      command.setBuilderName( builderName );
      ICommand[] newCommands = new ICommand[ commands.length + 1 ];
      System.arraycopy( commands, 0, newCommands, 1, commands.length );
      newCommands[ 0 ] = command;
      desc.setBuildSpec( newCommands );
      proj.setDescription( desc, new NullProgressMonitor() );
    }
    
  }
  
  /**
   * Remove the builder with the specified name from this project nature.
   * 
   * @param builderName The name of the builder to be removed.
   * 
   * @throws CoreException If an exception occurs while removing the
   * specified builder.
   */
  private void removeBuilder( final String builderName ) throws CoreException {
    
    IProject proj = getProject();
    IProjectDescription desc = proj.getDescription();
    ICommand[] commands = desc.getBuildSpec();
    
    boolean found = false;
    int index = 0;
    for ( ; ( index < commands.length ) && !found ; index++ ) {
      if ( commands[ index ].getBuilderName().equals( builderName ) ) {
        found = true;
      }
    }
    
    if ( found ) {
      ICommand[] newCommands = new ICommand[ commands.length - 1 ];
      System.arraycopy( commands, 0, newCommands, 0, index - 1 );
      System.arraycopy( commands, index, newCommands, index - 1, commands.length - index );
      desc.setBuildSpec( newCommands );
      proj.setDescription( desc, new NullProgressMonitor() );
    }
    
  }
  
}
