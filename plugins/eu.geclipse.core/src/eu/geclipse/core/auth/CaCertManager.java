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
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.GridException;
import eu.geclipse.core.internal.Activator;

/**
 * This class manages all CA certificates that are currently loaded in the
 * workspace. It also provides methods for retrieving new certificates from
 * local or remote repositories. The {@link #getCaCertLocation()} method
 * can be used to initialise external packages like the org.globus.
 * 
 * @author stuempert-m
 */
public class CaCertManager implements IContentChangeNotifier {
  
  /**
   * The singleton.
   */
  static private CaCertManager singleton = null;
  
  /**
   * The list holds the currently managed certificates.
   */
  private Hashtable< String, ICaCertificate > certs = new Hashtable< String, ICaCertificate >();
  
  /**
   * This list holds the currently registered IContentChangeListeners. 
   */
  private ListenerList ccListeners = new ListenerList();
  
  /**
   * Private constructor. The created manager is initialised with all
   * certificates that could be found in the certificate location.
   */
  private CaCertManager() {
    update();
    fireContentChanged();
  }
  
  /**
   * This methods can be used to get a <code>CaCertManager</code>. If no manager
   * was yet requested the singleton is instantiated once.
   * 
   * @return The singleton.
   */
  static public CaCertManager getManager() {
    if ( singleton == null ) {
      singleton = new CaCertManager();
    }
    return singleton;
  }
  
  /**
   * Add a certificate to the list of managed certificates.
   * 
   * @param certificate The cterificate to be added.
   */
  public void addCertificate( final ICaCertificate certificate ) {
    if ( internalAddCertificate( certificate ) ) {
      fireContentChanged();
    }
  }
  
  /**
   * Add a list of certificates to the managed certificates.
   * 
   * @param certificates The certifcates to be added.
   */
  public void addCertificates( final ICaCertificate[] certificates ) {
    boolean changed = false;
    for ( ICaCertificate certificate : certificates ) {
      changed |= internalAddCertificate( certificate );
    }
    if ( changed ) {
      fireContentChanged();
    }
  }
  
  /**
   * Get a copy of the internal list of all currently managed certificates.
   * 
   * @return A list containing all currently available certificates.
   */
  public ICaCertificate[] getCertificates() {
    return this.certs.values().toArray( new ICaCertificate[0] );
  }
  
  /**
   * Get the certificate with the specified ID or null if not found.
   * 
   * @param id The unique ID of the certificate.
   * @return The certificate with the specified ID or null if no such
   * certificate was found.
   */
  public ICaCertificate getCertificate( final String id ) {
    return this.certs.get( id );
  }
  
  /**
   * Deletes the certificate with the specified id from the manager.
   * Deleting a certificate means also deleting the corresponding file
   * from disk. If the certificate is once deleted it should not
   * longer be referenced or used outside the <code>CaCertManager</code>.
   * 
   * @param id The ID of the certificate that should be deleted.
   * If no such certificate was found nothing will be done.
   */
  public void deleteCertificate( final String id ) {
    ICaCertificate certificate = getCertificate( id );
    if ( certificate != null ) {
      if ( internalDeleteCertificate( certificate ) ) {
        fireContentChanged();
      }
    }
  }
  
  /**
   * Delete the certificates with the specified ids. The certificates
   * are removed from the list of certificates and are deleted afterwards. 
   * 
   * @param ids The ids of the certificates to be deleted.
   */
  public void deleteCertificates( final String[] ids ) {
    boolean changed = false;
    for ( String id : ids ) {
      ICaCertificate certificate = getCertificate( id );
      changed |= internalDeleteCertificate( certificate );
    }
    if ( changed ) {
      fireContentChanged();
    }
  }
  
  /**
   * Get the path to the location where the certificates are stored. This
   * method can be used for instance to initialise the CoG-jglobus with
   * <code>CoGProperties.setCaCertLocations(String)</code>.
   * 
   * @return An absolute IPath that describes the location of the certificates in
   * the filesystem.
   */
  public IPath getCaCertLocation() {
    IPath location = Activator.getDefault().getStateLocation();
    if ( !location.hasTrailingSeparator() ) {
      location = location.addTrailingSeparator();
    }
    location = location.append( ".certs" ); //$NON-NLS-1$
    File file = location.toFile();
    if ( !file.exists() ) {
      file.mkdir();
    }
    return location;
  }

  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeNotifier#addContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void addContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.add( listener );
  }

  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeNotifier#removeContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void removeContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.remove( listener );
  }
  
  /**
   * Update this manager.
   */
  private void update() {
    
    this.certs.clear();
    IPath certLocation = getCaCertLocation();
    File certDir = certLocation.toFile();
    
    if ( certDir.isDirectory() ) {
      
      String[] certFiles = certDir.list();
      
      if ( ( certFiles != null ) && ( certFiles.length > 0 ) ) {
        
        ExtensionManager extManager = new ExtensionManager();
        List< Object > loaders
          = extManager.getExecutableExtensions(
              Extensions.CA_CERT_LOADER_POINT,
              Extensions.CA_CERT_LOADER_ELEMENT,
              Extensions.CA_CERT_LOADER_CLASS_ATTRIBUTE );
     
        for ( int i = 0 ; i < certFiles.length ; i++ ) {
          final IPath certPath = certLocation.append( certFiles[i] );
          for ( Object o : loaders ) {
            ICaCertificateLoader loader = ( ICaCertificateLoader ) o;
            try {
              ICaCertificate certificate = loader.getCertificate( certPath );
              if ( certificate != null ) {
                internalAddCertificate( certificate );
                break;
              }
            } catch ( GridException gExc ) {
              Activator.logException( gExc );
            }
          }
        }
      }
    }
    
  }

  /**
   * Notify all registered IContentChangeListeners about content changes.
   */
  private void fireContentChanged() {
    Object[] list = this.ccListeners.getListeners();
    for ( int i = 0 ; i < list.length ; i++ ) {
      if ( list[i] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener ) list[i];
        listener.contentChanged( this );
      }
    }
  }
  
  private boolean internalAddCertificate( final ICaCertificate certificate ) {
    
    boolean result = false;
    
    if ( certificate != null ) {
    
      String id = certificate.getID();
      
      ICaCertificate oldCertificate = getCertificate( id );
      if ( oldCertificate != null ) {
        internalDeleteCertificate( oldCertificate );
      }

      try {
        certificate.write( getCaCertLocation() );
        this.certs.put( id, certificate );
        result = true;
      } catch ( IOException ioExc ) {
        // TODO mathias throw GridException here
      }
      
    }
    
    return result;
    
  }
  
  private boolean internalDeleteCertificate( final ICaCertificate certificate ) {
    
    boolean result = false;
    
    if ( certificate != null ) {
      String id = certificate.getID();
      this.certs.remove( id );
      certificate.delete( getCaCertLocation() );
      result = true;
    }
    
    return result;
    
  }
   
}
