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

package eu.geclipse.core.config;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IConfigurableElementCreator;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.project.GridProjectCreationOperation;
import eu.geclipse.core.project.GridProjectProperties;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateLoader;
import eu.geclipse.core.security.ICertificateManager;
import eu.geclipse.core.security.Security;
import eu.geclipse.core.security.ICertificateLoader.CertificateID;

/**
 * Concrete core implementation of the {@link IConfigurator} interface.
 * Implements the possibilities to configure trusted certificates, VOs and grid
 * projects. Corresponds to the information defined by extensions to the
 * <code>eu.geclipse.core.configurator</code> extension point.
 */
public class Configurator
    implements IConfigurator {
  
  /**
   * Internal class used to store the configuration parameters for a grid
   * project.
   */
  private static class ProjectConfiguration {
    
    /**
     * The project's name.
     */
    private String projectName;
    
    /**
     * The name of the project's VO.
     */
    private String voName;
    
    /**
     * The project folders.
     */
    private Hashtable< String, String > projectFolders;
      
    /**
     * Create a project configuration with the specified settings.
     * 
     * @param projectName The project's name.
     * @param voName The name of the project's VO.
     * @param projectFolders The project folders.
     */
    public ProjectConfiguration( final String projectName,
                                 final String voName,
                                 final Hashtable< String, String > projectFolders ) {
      this.projectName = projectName;
      this.voName = voName;
      this.projectFolders = projectFolders;
    }
    
    /**
     * Get the project folders.
     * 
     * @return The project folders.
     */
    public Hashtable< String, String > getProjectFolders() {
      return this.projectFolders;
    }
    
    /**
     * Get the project's name.
     * 
     * @return The project's name.
     */
    public String getProjectName() {
      return this.projectName;
    }
    
    /**
     * Get the name of the project's VO.
     * 
     * @return The name of the project's VO.
     */
    public String getVoName() {
      return this.voName;
    }
    
  }
  
  
  /**
   * Parameter used for the VO configuration to encode the VO's name.
   */
  private static final String VO_NAME_PARAMETER
    = "name"; //$NON-NLS-1$
  
  /**
   * Internal hashtable holding the list of certificate loaders and the
   * corresponding URIs.
   */
  private Hashtable< ICertificateLoader, URI[] > certificateLoaders
    = new Hashtable< ICertificateLoader, URI[] >();
    
  /**
   * Internal hashtable holding the list of VO creators and the corresponding
   * configurations.
   */
  private Hashtable< IConfigurableElementCreator, Configuration > voCreators
    = new Hashtable< IConfigurableElementCreator, Configuration >();
  
  /**
   * Internal list of project configuration.
   */
  private List< ProjectConfiguration > projectConfigs
    = new ArrayList< ProjectConfiguration >();
  
  /**
   * Standard constructor. Constructs an empty configurator. The configuration
   * can be changed by using the various add-methods.
   */
  public Configurator() {
    // empty implementation
  }
  
  /**
   * Add a specific certificate loader to this configurator.
   * 
   * @param loader The loader to be added.
   * @param repositories The repository URIs for the specified certificate
   * loader.
   */
  public void addCertificateLoader( final ICertificateLoader loader,
                                    final URI[] repositories ) {
    this.certificateLoaders.put( loader, repositories );
  }
  
  /**
   * Add a specific project configuration to this configurator.
   * 
   * @param projectName The project's name.
   * @param voName The name of the project's VO.
   * @param projectFolders The project folders.
   */
  public void addProjectConfiguration( final String projectName,
                                       final String voName,
                                       final Hashtable< String, String > projectFolders ) {
    this.projectConfigs.add( new ProjectConfiguration( projectName, voName, projectFolders ) );
  }
  
  /**
   * Add a specific VO creator to this configurator.
   * 
   * @param creator The VO creator.
   * @param voName The name of the VO.
   * @param configuration The VO configuration.
   */
  public void addVoCreator( final IConfigurableElementCreator creator,
                            final String voName,
                            final Configuration configuration ) {
    configuration.setParameter( VO_NAME_PARAMETER, voName );
    this.voCreators.put( creator, configuration );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.config.IConfigurator#configure(org.eclipse.core.runtime.IProgressMonitor)
   */
  public IStatus configure( final IProgressMonitor monitor ) throws ProblemException {
    
    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          Messages.getString("Configurator.configuration_problem"), //$NON-NLS-1$
                                          null );
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, Messages.getString("Configurator.configuration_progress"), 3 ); //$NON-NLS-1$
    
    try {

      IStatus certImportStatus = doCertificateImport( sMonitor.newChild( 1 ) );
      if ( ! certImportStatus.isOK() ) {
        result.merge( certImportStatus );
      }

      testMonitor( sMonitor );
      
      IStatus voSetupStatus = doVoSetup( sMonitor.newChild( 1 ) );
      if ( ! voSetupStatus.isOK() ) {
        result.merge( voSetupStatus );
      }

      testMonitor( sMonitor );
      
      IStatus projectSetupStatus = doProjectSetup( sMonitor.newChild( 1 ) );
      if ( ! projectSetupStatus.isOK() ) {
        result.merge( projectSetupStatus );
      }
      
    } catch ( Throwable t ) {
      sMonitor.done();
      result.add( new Status( IStatus.ERROR,
                              Activator.PLUGIN_ID,
                              Messages.getString("Configurator.configuration_error"), //$NON-NLS-1$
                              t ) );
    }
    
    return
      ( result.getChildren() == null ) || ( result.getChildren().length == 0 )
      ? Status.OK_STATUS
      : result ;
    
  }
  
  /**
   * Import the certificates.
   * 
   * @param monitor Progress monitor.
   * @return Status of the import operation.
   */
  private IStatus doCertificateImport( final SubMonitor monitor ) {
    
    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          Messages.getString("Configurator.cert_import_problem"), //$NON-NLS-1$
                                          null );
    
    if ( ! this.certificateLoaders.isEmpty() ) {
      Set< ICertificateLoader > keys = this.certificateLoaders.keySet();
      ICertificateManager certificateManager = Security.getCertificateManager();
      monitor.beginTask( Messages.getString("Configurator.cert_import_progress"), 2 * keys.size() ); //$NON-NLS-1$
      for ( ICertificateLoader loader : keys ) {
        URI[] uris = this.certificateLoaders.get( loader );
        for ( URI uri : uris ) {
          try {
            CertificateID[] certificateIDs = loader.listAvailableCertificates( uri, monitor.newChild( 1 ) );
            testMonitor( monitor );
            SubMonitor sMonitor = monitor.newChild( 1 );
            sMonitor.setWorkRemaining( certificateIDs.length );
            for ( CertificateID id : certificateIDs ) {
              try {
                X509Certificate certificate = loader.fetchCertificate( id, sMonitor.newChild( 1 ) );
                if ( certificate != null ) {
                  certificateManager.addCertificate( certificate, ICertificateManager.CertTrust.AlwaysTrusted );
                }
              } catch ( ProblemException pExc ) {
                result.merge( pExc.getStatus() );
              }
            }
            testMonitor( monitor );
          } catch ( ProblemException pExc ) {
            result.merge( pExc.getStatus() );
          }
        }
      }
    }
    
    monitor.done();
    
    return
      ( result.getChildren() == null ) || ( result.getChildren().length == 0 )
      ? Status.OK_STATUS
      : result ;
    
  }
  
  /**
   * Setup the projects.
   * 
   * @param monitor Progress monitor.
   * @return Status of the operation.
   */
  private IStatus doProjectSetup( final SubMonitor monitor ) {

    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          Messages.getString("Configurator.cert_import_problem"), //$NON-NLS-1$
                                          null );

    if ( this.projectConfigs != null ) {
      
      monitor.beginTask( Messages.getString("Configurator.create_project_progress"), this.projectConfigs.size() ); //$NON-NLS-1$
      IVoManager voManager = GridModel.getVoManager();
      
      for ( ProjectConfiguration config : this.projectConfigs ) {
        
        GridProjectProperties properties = new GridProjectProperties();
        
        properties.setProjectName( config.getProjectName() );
        properties.setProjectVo( ( IVirtualOrganization ) voManager.findChild( config.getVoName() ) );
        properties.addProjectFolders( config.getProjectFolders() );
        
        try {
          GridProjectCreationOperation op = new GridProjectCreationOperation( properties );
          op.run( monitor.newChild( 1 ) );
        } catch( ProblemException pExc ) {
          result.merge( pExc.getStatus() );
        }

        testMonitor( monitor );
        
      }
      
    }
    
    monitor.done();
    
    return
      ( result.getChildren() == null ) || ( result.getChildren().length == 0 )
      ? Status.OK_STATUS
      : result ;
    
  }
  
  /**
   * Setup the VOs.
   * 
   * @param monitor Progress monitor.
   * @return Status of the operation.
   */
  private IStatus doVoSetup( final SubMonitor monitor ) {
    
    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          Messages.getString("Configurator.vo_setup_problem"), //$NON-NLS-1$
                                          null );
    
    if ( ! this.voCreators.isEmpty() ) {
      IVoManager voManager = GridModel.getVoManager();
      Set< IConfigurableElementCreator > keySet = this.voCreators.keySet();
      monitor.beginTask( Messages.getString("Configurator.vo_setup_progress"), keySet.size() ); //$NON-NLS-1$
      for ( IConfigurableElementCreator creator : keySet ) {
        IConfiguration config = this.voCreators.get( creator );
        creator.setConfiguration( config );
        try {
          voManager.create( creator );
          monitor.worked( 1 );
        } catch( ProblemException pExc ) {
          result.merge( pExc.getStatus() );
        }
        testMonitor( monitor );
      }
    }
    
    monitor.done();
    
    return
      ( result.getChildren() == null ) || ( result.getChildren().length == 0 )
      ? Status.OK_STATUS
      : result ;
    
  }
  
  /**
   * Test if the operation was canceled. If so throw an
   * {@link OperationCanceledException}.
   * 
   * @param monitor The monitor to be tested.
   */
  private void testMonitor( final IProgressMonitor monitor ) {
    if ( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }
  
}
