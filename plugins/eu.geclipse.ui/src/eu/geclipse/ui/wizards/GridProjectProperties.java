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

public class GridProjectProperties {
  
  private String projectName;
  
  private IPath projectLocation;
  
  private IVirtualOrganization projectVo;
  
  private IProject[] referencesProjects;
  
  private Hashtable< String, String > projectFolders
    = new Hashtable< String, String >();

  public String getProjectName() {
    return this.projectName;
  }
  
  public void setProjectName( final String projectName ) {
    this.projectName = projectName;
  }
  
  public IPath getProjectLocation() {
    return this.projectLocation;
  }
  
  public void setProjectLocation( final IPath projectLocation ) {
    this.projectLocation = projectLocation;
  }
  
  public IVirtualOrganization getProjectVo() {
    return this.projectVo;
  }
  
  public void setProjectVo( final IVirtualOrganization vo ) {
    this.projectVo = vo;
  }
  
  public IProject[] getReferencesProjects() {
    return this.referencesProjects;
  }

  public void setReferencesProjects( final IProject[] referencesProjects ) {
    this.referencesProjects = referencesProjects;
  }
  
  public void addProjectFolder( final String id, final String label ) {
    this.projectFolders.put( id, label );
  }
  
  public void addProjectFolders( final Hashtable< String, String > folders ) {
    for ( String key : folders.keySet() ) {
      this.projectFolders.put( key, folders.get( key ) );
    }
  }
  
  public Hashtable< String, String > getProjectFolders() {
    return this.projectFolders;
  }

}
