package eu.geclipse.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.IVirtualOrganization;

public class GridProjectProperties {
  
  private String projectName;
  
  private IPath projectLocation;
  
  private IVirtualOrganization projectVo;
  
  private IProject[] referencesProjects;

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
  
}
