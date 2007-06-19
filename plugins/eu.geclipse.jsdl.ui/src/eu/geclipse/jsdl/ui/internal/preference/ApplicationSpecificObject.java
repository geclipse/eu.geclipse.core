package eu.geclipse.jsdl.ui.internal.preference;

import org.eclipse.core.runtime.IPath;


public class ApplicationSpecificObject {

  private String appName;
  
  private String appPath;
  
  private IPath xmlPath;
  
  private int id;
  
  
  public ApplicationSpecificObject( int id, String appName, String path, IPath xmlPath ) {
    this.id = id;
    this.appName = appName;
    this.appPath = path;
    this.xmlPath = xmlPath;
    
  }


  
  public String getAppName() {
    return appName;
  }


  
  public void setAppName( String appName ) {
    this.appName = appName;
  }


  
  public String getAppPath() {
    return appPath;
  }


  
  public void setAppPath( String appPath ) {
    this.appPath = appPath;
  }


  
  public IPath getXmlPath() {
    return xmlPath;
  }


  
  public void setXmlPath( IPath xmlPath ) {
    this.xmlPath = xmlPath;
  }



  public int getId() {
    return this.id;
  }
}
