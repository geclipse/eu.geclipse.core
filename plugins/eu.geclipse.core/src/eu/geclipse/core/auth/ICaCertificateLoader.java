package eu.geclipse.core.auth;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.GridException;

public interface ICaCertificateLoader {
  
  public ICaCertificate getCertificate( final URI uri,
                                        final String certID,
                                        final IProgressMonitor monitor )
    throws GridException;

  public String[] getCertificateList( final URI uri,
                                      final IProgressMonitor monitor )
    throws GridException;
  
  public URI[] getPredefinedRemoteLocations();

}
