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

package eu.geclipse.core.auth;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.auth.Messages;
import eu.geclipse.core.internal.auth.PEMCertificate;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Certificate loader for {@link PEMCertificate}s.
 */
public class PEMCertificateLoader
    implements ICaCertificateLoader {
  
  public ICaCertificate getCertificate( final IPath path )
      throws ProblemException {
    try {
      return PEMCertificate.readFromFile( path );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM, ioExc, Activator.PLUGIN_ID );
    }
  }

  public ICaCertificate getCertificate( final URI uri, final String certID, final IProgressMonitor monitor )
      throws ProblemException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
    
    lMonitor.beginTask( String.format( Messages.getString("PEMCertificateLoader.load_cert_task"), certID ), 1 ); //$NON-NLS-1$
    
    PEMCertificate result = null;
    
    try {
      File parent = new File( uri );
      File file = new File( parent, certID );
      IPath filePath = new Path( file.getPath() );
      result = PEMCertificate.readFromFile( filePath );
      lMonitor.worked( 1 );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM, ioExc, Activator.PLUGIN_ID );
    } finally {
      lMonitor.done();
    }
    
    return result;
    
  }

  public String[] getCertificateList( final URI uri, final IProgressMonitor monitor )
      throws ProblemException {
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
