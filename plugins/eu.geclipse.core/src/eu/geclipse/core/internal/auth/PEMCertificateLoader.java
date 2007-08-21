package eu.geclipse.core.internal.auth;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.CoreProblems;
import eu.geclipse.core.GridException;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.core.auth.ICaCertificateLoader;

public class PEMCertificateLoader
    implements ICaCertificateLoader {
  
  public ICaCertificate getCertificate( final IPath path )
      throws GridException {
    try {
      return PEMCertificate.readFromFile( path );
    } catch ( IOException ioExc ) {
      throw new GridException( CoreProblems.UNSPECIFIED_IO_PROBLEM, ioExc );
    }
  }

  public ICaCertificate getCertificate( final URI uri, final String certID, final IProgressMonitor monitor )
      throws GridException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
    
    lMonitor.beginTask( "Loading certificate " + certID + "...", 1 );
    
    PEMCertificate result = null;
    
    try {
      File parent = new File( uri );
      File file = new File( parent, certID );
      IPath filePath = new Path( file.getPath() );
      result = PEMCertificate.readFromFile( filePath );
      lMonitor.worked( 1 );
    } catch ( IOException ioExc ) {
      throw new GridException( CoreProblems.UNSPECIFIED_IO_PROBLEM, ioExc );
    } finally {
      lMonitor.done();
    }
    
    return result;
    
  }

  public String[] getCertificateList( final URI uri, final IProgressMonitor monitor )
      throws GridException {
    File dirFile = new File( uri );
    String[] result = dirFile.list( new FilenameFilter() {
      public boolean accept( final File dir, final String name ) {
        IPath path = new Path( name );
        return PEMCertificate.CERT_FILE_EXTENSION.equals( path.getFileExtension() )
          || PEMCertificate.PEM_FILE_EXTENSION.equals( path.getFileExtension() );
      }
    } );
    return result;
  }

  public URI[] getPredefinedRemoteLocations() {
    return null;
  }

}
