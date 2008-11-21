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

public class Configurator
    implements IConfigurator {
  
  private static class ProjectConfiguration {
    
    private String projectName;
    
    private String voName;
    
    private Hashtable< String, String > projectFolders;
      
    public ProjectConfiguration( final String projectName,
                                 final String voName,
                                 final Hashtable< String, String > projectFolders ) {
      this.projectName = projectName;
      this.voName = voName;
      this.projectFolders = projectFolders;
    }
    
    public Hashtable< String, String > getProjectFolders() {
      return this.projectFolders;
    }
    
    public String getProjectName() {
      return this.projectName;
    }
    
    public String getVoName() {
      return this.voName;
    }
    
  }
  
  private Hashtable< ICertificateLoader, URI[] > certificateLoaders
    = new Hashtable< ICertificateLoader, URI[] >();
    
  private Hashtable< IConfigurableElementCreator, Configuration > voCreators
    = new Hashtable< IConfigurableElementCreator, Configuration >();
  
  private List< ProjectConfiguration > projectConfigs
    = new ArrayList< ProjectConfiguration >();
  
  public Configurator() {
    // empty implementation
  }
  
  public void addCertificateLoader( final ICertificateLoader loader,
                                    final URI[] repositories ) {
    this.certificateLoaders.put( loader, repositories );
  }
  
  public void addProjectConfiguration( final String projectName,
                                       final String voName,
                                       final Hashtable< String, String > projectFolders ) {
    this.projectConfigs.add( new ProjectConfiguration( projectName, voName, projectFolders ) );
  }
  
  public void addVoCreator( final IConfigurableElementCreator creator,
                            final String voName,
                            final Configuration configuration ) {
    configuration.setParameter( "name", voName );
    this.voCreators.put( creator, configuration );
  }
  
  public IStatus configure( final IProgressMonitor monitor ) throws ProblemException {
    
    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          "Problems occured during configuration process.",
                                          null );
    
    SubMonitor sMonitor = SubMonitor.convert( monitor, "Configuring g-Eclipse", 3 );
    
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
                              "An unexpected error happened during the configuration process.",
                              t ) );
    }
    
    return
      ( result.getChildren() == null ) || ( result.getChildren().length == 0 )
      ? Status.OK_STATUS
      : result ;
    
  }
  
  private IStatus doCertificateImport( final SubMonitor monitor ) {
    
    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          "Problems with certificate import.",
                                          null );
    
    if ( ! this.certificateLoaders.isEmpty() ) {
      Set< ICertificateLoader > keys = this.certificateLoaders.keySet();
      ICertificateManager certificateManager = Security.getCertificateManager();
      monitor.beginTask( "Importing certificates", 2 * keys.size() );
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
  
  private IStatus doProjectSetup( final SubMonitor monitor ) {

    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          "Problems with certificate import.",
                                          null );

    if ( this.projectConfigs != null ) {
      
      monitor.beginTask( "Creating projects", projectConfigs.size() );
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
  
  private IStatus doVoSetup( final SubMonitor monitor ) {
    
    MultiStatus result = new MultiStatus( Activator.PLUGIN_ID,
                                          0,
                                          "Problems with VO setup.",
                                          null );
    
    if ( ! this.voCreators.isEmpty() ) {
      IVoManager voManager = GridModel.getVoManager();
      Set< IConfigurableElementCreator > keySet = this.voCreators.keySet();
      monitor.beginTask( "Setting-up VOs", keySet.size() );
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
  
  private void testMonitor( final IProgressMonitor monitor ) {
    if ( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }
  
}
