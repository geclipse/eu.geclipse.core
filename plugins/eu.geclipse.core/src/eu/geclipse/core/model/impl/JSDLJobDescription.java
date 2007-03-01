package eu.geclipse.core.model.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.eclipse.core.resources.IFile;
import eu.geclipse.core.model.IGridJobDescription;


public class JSDLJobDescription
    extends AbstractGridContainer
    implements IGridJobDescription {

  
   
  protected JSDLJobDescription( final IFile file ) {
    super( file );
  }

  public String getJSDLString( ) throws IOException {
    String jsdl = null;
    File jsdlFile = this.getPath().toFile();
    FileInputStream jsdlStream = null;
    jsdlStream = new FileInputStream(jsdlFile); 
    Reader reader = new InputStreamReader( jsdlStream );
    BufferedReader jsdlReader = new BufferedReader( reader );
    String line;
    while( null != ( line = jsdlReader.readLine() ) ) {
      jsdl += line;
    }
    return jsdl;
  }

  public boolean isLazy() {
    return false;
  }
  
  public boolean isLocal() {
    return true;
  }

  public boolean isVirtual() {
    return false;
  }
  
}
