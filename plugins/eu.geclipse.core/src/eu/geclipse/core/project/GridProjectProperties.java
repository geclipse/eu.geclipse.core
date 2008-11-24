/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

import java.util.Hashtable;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IVirtualOrganization;

/**
 * Class which contains the Properties of a Grid Project. 
 * Properties are Name, Location, VirtualOrganisation, ... 
 */
public class GridProjectProperties {
  
private String projectName;
  
  private IPath projectLocation;
  
  private IVirtualOrganization projectVo;
  
  private IProject[] referencesProjects;
  
  private Hashtable< String, String > projectFolders
    = new Hashtable< String, String >();

  /**
   * Get the project's name.
   * 
   * @return The project's name.
   */
  public String getProjectName() {
    return this.projectName;
  }
  
  /**
   * Set the project's name.
   * 
   * @param projectName The project's name.
   */
  public void setProjectName( final String projectName ) {
    this.projectName = projectName;
  }
  
  /**
   * Get the project's location within the workspace.
   * 
   * @return The project's workspace location.
   */
  public IPath getProjectLocation() {
    return this.projectLocation;
  }
  
  /**
   * Set the project's location within the workspace.
   * 
   * @param projectLocation The project's workspace location.
   */
  public void setProjectLocation( final IPath projectLocation ) {
    this.projectLocation = projectLocation;
  }
  
  /**
   * Get the project's {@link IVirtualOrganization}.
   * 
   * @return The project's {@link IVirtualOrganization}.
   */
  public IVirtualOrganization getProjectVo() {
    return this.projectVo;
  }
  
  /**
   * Set the project's {@link IVirtualOrganization}.
   * 
   * @param vo The project's {@link IVirtualOrganization}.
   */
  public void setProjectVo( final IVirtualOrganization vo ) {
    this.projectVo = vo;
  }
  
  /**
   * Get the list of referenced projects.
   * 
   * @return The referenced projects or <code>null</code>.
   */
  public IProject[] getReferencesProjects() {
    
    IProject[] result = null;
    
    if ( ( this.referencesProjects != null ) && ( this.referencesProjects.length > 0 ) ) {
      result = new IProject[ this.referencesProjects.length ];
      System.arraycopy( this.referencesProjects, 0, result, 0, result.length );
    }
    
    return result;
    
  }

  /**
   * Set the list of referenced projects.
   * 
   * @param referencesProjects The referenced projects.
   */
  public void setReferencesProjects( final IProject[] referencesProjects ) {
    this.referencesProjects = referencesProjects;
  }
  
  /**
   * Add a project folder to the project structure.
   * 
   * @param id The id of the project folder.
   * @param label The label of the project folder.
   */
  public void addProjectFolder( final String id, final String label ) {
    this.projectFolders.put( id, label );
  }
  
  /** 
   * Adds a list of project folders to the project structure.
   * 
   * @param folders A {@link Hashtable} containing pairs of ids and labels
   * of the project folders.
   */
  public void addProjectFolders( final Hashtable< String, String > folders ) {
    for ( String key : folders.keySet() ) {
      this.projectFolders.put( key, folders.get( key ) );
    }
  }
  
  /** 
   * Get the list of project folders.
   *  
   * @return The list of project folders.
   */
  public Hashtable< String, String > getProjectFolders() {
    return new Hashtable< String, String >( this.projectFolders );
  }
  
}
