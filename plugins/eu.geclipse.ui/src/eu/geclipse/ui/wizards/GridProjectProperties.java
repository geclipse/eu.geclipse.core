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

package eu.geclipse.ui.wizards;

import java.util.Hashtable;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IVirtualOrganization;

/**
 * Class which contains the Properties of a Grid Project. 
 * Propoerties are Name, Location, VirtualOrganisation, ... 
 */
public class GridProjectProperties {
  
  private String projectName;
  
  private IPath projectLocation;
  
  private IVirtualOrganization projectVo;
  
  private IProject[] referencesProjects;
  
  private Hashtable< String, String > projectFolders
    = new Hashtable< String, String >();

  /**
   * return project name
   * @return
   */
  public String getProjectName() {
    return this.projectName;
  }
  
  /**
   * set the ProjectName()
   * @param projectName
   */
  public void setProjectName( final String projectName ) {
    this.projectName = projectName;
  }
  
  /**
   * set ProjectLocation
   * @return
   */
  public IPath getProjectLocation() {
    return this.projectLocation;
  }
  
  /**
   * get ProjectLocation
   * @param projectLocation
   */
  public void setProjectLocation( final IPath projectLocation ) {
    this.projectLocation = projectLocation;
  }
  
  /**
   * get Virtual Organization of the Project
   * @return
   */
  public IVirtualOrganization getProjectVo() {
    return this.projectVo;
  }
  
  /**
   * set the Virtual Organization of the Project 
   * @param vo
   */
  public void setProjectVo( final IVirtualOrganization vo ) {
    this.projectVo = vo;
  }
  
  /**
   * Get the list of reference projects
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
   * Set the list of references projects.
   * 
   * @param referencesProjects The referenced projects.
   */
  public void setReferencesProjects( final IProject[] referencesProjects ) {
    this.referencesProjects = referencesProjects;
  }
  
  /**
   * add a folder to the projects structure
   * 
   * @param id
   * @param label
   */
  public void addProjectFolder( final String id, final String label ) {
    this.projectFolders.put( id, label );
  }
  
  /** 
   * adds a list of project folders
   * 
   * @param folders
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
