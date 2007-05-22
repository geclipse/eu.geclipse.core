package eu.geclipse.info.glue;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.info.Activator;
import java.lang.reflect.Method;

/**
 * @author George Tsouloupas
 */
public abstract class GlueIndex implements java.io.Serializable {

  private static GlueIndex glueIndexInstance;

  protected abstract String getTag();
  
  //Used for invalidating the cache 
  public String voListString;

  /**
   * @return the singleton instance to the Glue information datastructure
   */
  public static GlueIndex getInstance() {
    if( glueIndexInstance == null ) {
      try {
        glueIndexInstance = loadInstance();
      } catch( IOException e ) {
        glueIndexInstance = new GlueIndex() {

          private static final long serialVersionUID = 1L;

          @Override
          protected String getTag()
          {
            return "basis"; //$NON-NLS-1$
          }
        };
      }
    }
    return glueIndexInstance;
  }

  public static void drop(){
    glueIndexInstance=null;
    serializeInstance();
  }
  
  private static IPath getGridInfoLocation() {
    Activator activator= eu.geclipse.info.Activator.getDefault();
    IPath location = null;
    if(activator!=null){
      location = activator.getStateLocation();
      if( !location.hasTrailingSeparator() ) {
        location = location.addTrailingSeparator();
      }
      location = location.append( ".gridinfo" ); //$NON-NLS-1$
    }
    return location;
  }

  /**
   * Serialize the contents of the Glue data-structure to disk
   */
  public static void serializeInstance() {
    try {
      IPath serPath = getGridInfoLocation();
      FileOutputStream fos = new FileOutputStream( serPath.toFile() );
      ObjectOutputStream oos = new ObjectOutputStream( fos );
      oos.writeObject( GlueIndex.getInstance() );
      fos.close();
    } catch( FileNotFoundException e ) {
      e.printStackTrace();
    } catch( IOException e ) {
      e.printStackTrace();
    }
  }

  private static GlueIndex loadInstance() throws IOException {
    IPath serPath = getGridInfoLocation();
    GlueIndex gi=null;
    if(serPath!=null){
      try {
        FileInputStream fis;
        fis = new FileInputStream( serPath.toFile() );
        ObjectInputStream ois = new ObjectInputStream( fis );
        gi= ( GlueIndex )ois.readObject();
      } catch( ClassNotFoundException e ) {
        e.printStackTrace();
      }
    }else{
      throw new IOException("Could not load cache."); //$NON-NLS-1$
    }
    return gi;
  }

  /**
   * @param objectName String representing the name of the Glue Object 
   * such as "GlueSite", "GlueCE", "GlueSE" ...
   * @param key the unique identifier for the Object to be retrieved
   * @return The Glue object with the specified key, null otherwise
   */
  public AbstractGlueTable get( String objectName, String key ) {
    Class[] c = new Class[ 1 ];
    c[ 0 ] = String.class;
    Method m;
    try {
      m = GlueIndex.class.getMethod( "get" + objectName, c ); //$NON-NLS-1$
      Object[] o = new Object[ 1 ];
      o[ 0 ] = key;
      return ( AbstractGlueTable )m.invoke( getInstance(), o );
    } catch( SecurityException e ) {
      e.printStackTrace();
    } catch( NoSuchMethodException e ) {
      e.printStackTrace();
    } catch( IllegalArgumentException e ) {
      e.printStackTrace();
    } catch( IllegalAccessException e ) {
      e.printStackTrace();
    } catch( InvocationTargetException e ) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @param objectName String representing the name of the Glue Object 
   * such as "GlueSite", "GlueCE", "GlueSE" ...
   * @return a list of all Glue objects of the provided type
   */
  public ArrayList<AbstractGlueTable> getList( String objectName ) {
    ArrayList<AbstractGlueTable> agtList = new ArrayList<AbstractGlueTable>();
    try {
      String fieldName = objectName.substring( 0, 1 ).toLowerCase()
                         + objectName.substring( 1 );
      Field f = GlueIndex.class.getField( fieldName );
      Object o = f.get( this );
      Hashtable<String, AbstractGlueTable> ht=( Hashtable<String, AbstractGlueTable>)o;
      Enumeration<AbstractGlueTable> enAgt = ht.elements();
      while( enAgt.hasMoreElements() ) {
        agtList.add( enAgt.nextElement() );
      }
      // System.out.println(ht);
      //TODO remove printstacktrace
    } catch( SecurityException e ) {
      e.printStackTrace();
    } catch( NoSuchFieldException e ) {
      e.printStackTrace();
    } catch( IllegalArgumentException e ) {
      e.printStackTrace();
    } catch( IllegalAccessException e ) {
      e.printStackTrace();
    }
    return agtList;
  }
  @SuppressWarnings("nls")
  private static final String[] tableList = {
    "GlueHost",
    "GlueSite",
    "GlueSE",
    "GlueSL",
    "GlueCluster",
    "GlueSubCluster",
    "GlueCE",
    "GlueBatchJob",
    "GlueBatchQueue",
    "GlueBatchSystem",
    "GlueCEAccessControlBaseRule",
    "GlueCEContactString",
    "GlueCESEBind",
    "GlueCEVOView",
    "GlueCEVOViewAccessControlBaseRule",
    "GlueHostLocalFileSystem",
    "GlueHostNetworkAdapter",
    "GlueHostPoolAccount",
    "GlueHostProcess",
    "GlueHostRemoteFileSystem",
    "GlueHostRole",
    "GlueSA",
    "GlueSAAccessControlBaseRule",
    "GlueSEAccessProtocol",
    "GlueSEAccessProtocolCapability",
    "GlueSEAccessProtocolSupportedSecurity",
    "GlueSEControlProtocol",
    "GlueSEControlProtocolCapability",
    "GlueService",
    "GlueServiceAccessControlRule",
    "GlueServiceAssociation",
    "GlueServiceData",
    "GlueServiceOwner",
    "GlueServiceStatus",
    "GlueSiteInfo",
    "GlueSiteSponsor",
    "GlueSubClusterLocation",
    "GlueSubClusterSoftwareRunTimeEnvironment",
    "GlueVO"
  };
  
  /**
   * @return A list of all possible Glue object types
   */
  public static String[] getObjectTypeList() {
    return tableList;
  }
  
  public Hashtable<String, GlueHost> glueHost = new Hashtable<String, GlueHost>();
  public Hashtable<String, GlueSite> glueSite = new Hashtable<String, GlueSite>();
  public Hashtable<String, GlueSE> glueSE = new Hashtable<String, GlueSE>();
  public Hashtable<String, GlueSL> glueSL = new Hashtable<String, GlueSL>();
  public Hashtable<String, GlueCluster> glueCluster = new Hashtable<String, GlueCluster>();
  public Hashtable<String, GlueSubCluster> glueSubCluster = new Hashtable<String, GlueSubCluster>();
  public Hashtable<String, GlueCE> glueCE = new Hashtable<String, GlueCE>();
  public Hashtable<String, GlueBatchJob> glueBatchJob = new Hashtable<String, GlueBatchJob>();
  public Hashtable<String, GlueBatchQueue> glueBatchQueue = new Hashtable<String, GlueBatchQueue>();
  public Hashtable<String, GlueBatchSystem> glueBatchSystem = new Hashtable<String, GlueBatchSystem>();
  public Hashtable<String, GlueCEAccessControlBaseRule> glueCEAccessControlBaseRule = new Hashtable<String, GlueCEAccessControlBaseRule>();
  public Hashtable<String, GlueCEContactString> glueCEContactString = new Hashtable<String, GlueCEContactString>();
  public Hashtable<String, GlueCESEBind> glueCESEBind = new Hashtable<String, GlueCESEBind>();
  public Hashtable<String, GlueCEVOView> glueCEVOView = new Hashtable<String, GlueCEVOView>();
  public Hashtable<String, GlueCEVOViewAccessControlBaseRule> glueCEVOViewAccessControlBaseRule = new Hashtable<String, GlueCEVOViewAccessControlBaseRule>();
  public Hashtable<String, GlueHostLocalFileSystem> glueHostLocalFileSystem = new Hashtable<String, GlueHostLocalFileSystem>();
  public Hashtable<String, GlueHostNetworkAdapter> glueHostNetworkAdapter = new Hashtable<String, GlueHostNetworkAdapter>();
  public Hashtable<String, GlueHostPoolAccount> glueHostPoolAccount = new Hashtable<String, GlueHostPoolAccount>();
  public Hashtable<String, GlueHostProcess> glueHostProcess = new Hashtable<String, GlueHostProcess>();
  public Hashtable<String, GlueHostRemoteFileSystem> glueHostRemoteFileSystem = new Hashtable<String, GlueHostRemoteFileSystem>();
  public Hashtable<String, GlueHostRole> glueHostRole = new Hashtable<String, GlueHostRole>();
  public Hashtable<String, GlueSA> glueSA = new Hashtable<String, GlueSA>();
  public Hashtable<String, GlueSAAccessControlBaseRule> glueSAAccessControlBaseRule = new Hashtable<String, GlueSAAccessControlBaseRule>();
  public Hashtable<String, GlueSEAccessProtocol> glueSEAccessProtocol = new Hashtable<String, GlueSEAccessProtocol>();
  public Hashtable<String, GlueSEAccessProtocolCapability> glueSEAccessProtocolCapability = new Hashtable<String, GlueSEAccessProtocolCapability>();
  public Hashtable<String, GlueSEAccessProtocolSupportedSecurity> glueSEAccessProtocolSupportedSecurity = new Hashtable<String, GlueSEAccessProtocolSupportedSecurity>();
  public Hashtable<String, GlueSEControlProtocol> glueSEControlProtocol = new Hashtable<String, GlueSEControlProtocol>();
  public Hashtable<String, GlueSEControlProtocolCapability> glueSEControlProtocolCapability = new Hashtable<String, GlueSEControlProtocolCapability>();
  public Hashtable<String, GlueService> glueService = new Hashtable<String, GlueService>();
  public Hashtable<String, GlueServiceAccessControlRule> glueServiceAccessControlRule = new Hashtable<String, GlueServiceAccessControlRule>();
  public Hashtable<String, GlueServiceAssociation> glueServiceAssociation = new Hashtable<String, GlueServiceAssociation>();
  public Hashtable<String, GlueServiceData> glueServiceData = new Hashtable<String, GlueServiceData>();
  public Hashtable<String, GlueServiceOwner> glueServiceOwner = new Hashtable<String, GlueServiceOwner>();
  public Hashtable<String, GlueServiceStatus> glueServiceStatus = new Hashtable<String, GlueServiceStatus>();
  public Hashtable<String, GlueSiteInfo> glueSiteInfo = new Hashtable<String, GlueSiteInfo>();
  public Hashtable<String, GlueSiteSponsor> glueSiteSponsor = new Hashtable<String, GlueSiteSponsor>();
  public Hashtable<String, GlueSubClusterLocation> glueSubClusterLocation = new Hashtable<String, GlueSubClusterLocation>();
  public Hashtable<String, GlueSubClusterSoftwareRunTimeEnvironment> glueSubClusterSoftwareRunTimeEnvironment = new Hashtable<String, GlueSubClusterSoftwareRunTimeEnvironment>();
  public Hashtable<String, GlueVO> glueVO = new Hashtable<String, GlueVO>();
  public Hashtable<String, AbstractGlueTable> fullIndex = new Hashtable<String, AbstractGlueTable>();

  private void putInFullIndex( final String key, AbstractGlueTable agt ) {
    String newKey = agt.getClass().getName() + key;
    AbstractGlueTable previous = fullIndex.put( newKey, agt );
    if( previous != null ) {
      //System.out.println( "Duplicate:\n   " + previous + "\n   " + agt ); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }
  

  /**
   * @param key
   * @return object
   */
  public GlueHost getGlueHost( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHost out = glueHost.get( key );
    if( out == null ) {
      out = new GlueHost();
      out.fresh = true;
      out.setID( key );
      glueHost.put( key, out );
      putInFullIndex( key, out );
    } else {
      // //out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSite getGlueSite( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSite out = glueSite.get( key );
    if( out == null ) {
      out = new GlueSite();
      out.fresh = true;
      out.setID( key );
      glueSite.put( key, out );
      putInFullIndex( key, out );
    } else {
      // //out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSE getGlueSE( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSE out = glueSE.get( key );
    if( out == null ) {
      out = new GlueSE();
      out.fresh = true;
      out.setID( key );
      glueSE.put( key, out );
      putInFullIndex( key, out );
    } else {
      // //out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSL getGlueSL( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSL out = glueSL.get( key );
    if( out == null ) {
      out = new GlueSL();
      out.fresh = true;
      out.setID( key );
      glueSL.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueCluster getGlueCluster( String key ) {
    if( key == null ) {
      return null;
    }
    GlueCluster out = glueCluster.get( key );
    if( out == null ) {
      out = new GlueCluster();
      out.fresh = true;
      out.setID( key );
      glueCluster.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSubCluster getGlueSubCluster( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSubCluster out = glueSubCluster.get( key );
    if( out == null ) {
      out = new GlueSubCluster();
      out.fresh = true;
      out.setID( key );
      glueSubCluster.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueCE getGlueCE( String key ) {
    if( key == null ) {
      return null;
    }
    GlueCE out = glueCE.get( key );
    if( out == null ) {
      out = new GlueCE();
      out.fresh = true;
      out.setID( key );
      glueCE.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueBatchJob getGlueBatchJob( String key ) {
    if( key == null ) {
      return null;
    }
    GlueBatchJob out = glueBatchJob.get( key );
    if( out == null ) {
      out = new GlueBatchJob();
      out.fresh = true;
      out.setID( key );
      glueBatchJob.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueBatchQueue getGlueBatchQueue( String key ) {
    if( key == null ) {
      return null;
    }
    GlueBatchQueue out = glueBatchQueue.get( key );
    if( out == null ) {
      out = new GlueBatchQueue();
      out.fresh = true;
      out.setID( key );
      glueBatchQueue.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueBatchSystem getGlueBatchSystem( String key ) {
    if( key == null ) {
      return null;
    }
    GlueBatchSystem out = glueBatchSystem.get( key );
    if( out == null ) {
      out = new GlueBatchSystem();
      out.fresh = true;
      out.setID( key );
      glueBatchSystem.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueCEAccessControlBaseRule getGlueCEAccessControlBaseRule( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueCEAccessControlBaseRule out = glueCEAccessControlBaseRule.get( key );
    if( out == null ) {
      out = new GlueCEAccessControlBaseRule();
      out.fresh = true;
      out.setID( key );
      glueCEAccessControlBaseRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueCEContactString getGlueCEContactString( String key ) {
    if( key == null ) {
      return null;
    }
    GlueCEContactString out = glueCEContactString.get( key );
    if( out == null ) {
      out = new GlueCEContactString();
      out.fresh = true;
      out.setID( key );
      glueCEContactString.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueCESEBind getGlueCESEBind( String key ) {
    if( key == null ) {
      return null;
    }
    GlueCESEBind out = glueCESEBind.get( key );
    if( out == null ) {
      out = new GlueCESEBind();
      out.fresh = true;
      out.setID( key );
      glueCESEBind.put( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  } 

  /**
   * @param key
   * @return object
   */
  public GlueCEVOView getGlueCEVOView( String key ) {
    if( key == null ) {
      return null;
    }
    GlueCEVOView out = glueCEVOView.get( key );
    if( out == null ) {
      out = new GlueCEVOView();
      out.fresh = true;
      out.setID( key );
      glueCEVOView.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueCEVOViewAccessControlBaseRule getGlueCEVOViewAccessControlBaseRule( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueCEVOViewAccessControlBaseRule out = glueCEVOViewAccessControlBaseRule.get( key );
    if( out == null ) {
      out = new GlueCEVOViewAccessControlBaseRule();
      out.fresh = true;
      out.setID( key );
      glueCEVOViewAccessControlBaseRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueHostLocalFileSystem getGlueHostLocalFileSystem( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostLocalFileSystem out = glueHostLocalFileSystem.get( key );
    if( out == null ) {
      out = new GlueHostLocalFileSystem();
      out.fresh = true;
      out.setID( key );
      glueHostLocalFileSystem.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueHostNetworkAdapter getGlueHostNetworkAdapter( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostNetworkAdapter out = glueHostNetworkAdapter.get( key );
    if( out == null ) {
      out = new GlueHostNetworkAdapter();
      out.fresh = true;
      out.setID( key );
      glueHostNetworkAdapter.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueHostPoolAccount getGlueHostPoolAccount( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostPoolAccount out = glueHostPoolAccount.get( key );
    if( out == null ) {
      out = new GlueHostPoolAccount();
      out.fresh = true;
      out.setID( key );
      glueHostPoolAccount.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueHostProcess getGlueHostProcess( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostProcess out = glueHostProcess.get( key );
    if( out == null ) {
      out = new GlueHostProcess();
      out.fresh = true;
      out.setID( key );
      glueHostProcess.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueHostRemoteFileSystem getGlueHostRemoteFileSystem( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostRemoteFileSystem out = glueHostRemoteFileSystem.get( key );
    if( out == null ) {
      out = new GlueHostRemoteFileSystem();
      out.fresh = true;
      out.setID( key );
      glueHostRemoteFileSystem.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueHostRole getGlueHostRole( String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostRole out = glueHostRole.get( key );
    if( out == null ) {
      out = new GlueHostRole();
      out.fresh = true;
      out.setID( key );
      glueHostRole.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSA getGlueSA( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSA out = glueSA.get( key );
    if( out == null ) {
      out = new GlueSA();
      out.fresh = true;
      out.setID( key );
      glueSA.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSAAccessControlBaseRule getGlueSAAccessControlBaseRule( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSAAccessControlBaseRule out = glueSAAccessControlBaseRule.get( key );
    if( out == null ) {
      out = new GlueSAAccessControlBaseRule();
      out.fresh = true;
      out.setID( key );
      glueSAAccessControlBaseRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSEAccessProtocol getGlueSEAccessProtocol( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSEAccessProtocol out = glueSEAccessProtocol.get( key );
    if( out == null ) {
      out = new GlueSEAccessProtocol();
      out.fresh = true;
      out.setID( key );
      glueSEAccessProtocol.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSEAccessProtocolCapability getGlueSEAccessProtocolCapability( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSEAccessProtocolCapability out = glueSEAccessProtocolCapability.get( key );
    if( out == null ) {
      out = new GlueSEAccessProtocolCapability();
      out.fresh = true;
      out.setID( key );
      glueSEAccessProtocolCapability.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSEAccessProtocolSupportedSecurity getGlueSEAccessProtocolSupportedSecurity( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSEAccessProtocolSupportedSecurity out = glueSEAccessProtocolSupportedSecurity.get( key );
    if( out == null ) {
      out = new GlueSEAccessProtocolSupportedSecurity();
      out.fresh = true;
      out.setID( key );
      glueSEAccessProtocolSupportedSecurity.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSEControlProtocol getGlueSEControlProtocol( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSEControlProtocol out = glueSEControlProtocol.get( key );
    if( out == null ) {
      out = new GlueSEControlProtocol();
      out.fresh = true;
      out.setID( key );
      glueSEControlProtocol.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSEControlProtocolCapability getGlueSEControlProtocolCapability( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSEControlProtocolCapability out = glueSEControlProtocolCapability.get( key );
    if( out == null ) {
      out = new GlueSEControlProtocolCapability();
      out.fresh = true;
      out.setID( key );
      glueSEControlProtocolCapability.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueService getGlueService( String key ) {
    if( key == null ) {
      return null;
    }
    GlueService out = glueService.get( key );
    if( out == null ) {
      out = new GlueService();
      out.fresh = true;
      out.setID( key );
      glueService.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueServiceAccessControlRule getGlueServiceAccessControlRule( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueServiceAccessControlRule out = glueServiceAccessControlRule.get( key );
    if( out == null ) {
      out = new GlueServiceAccessControlRule();
      out.fresh = true;
      out.setID( key );
      glueServiceAccessControlRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueServiceAssociation getGlueServiceAssociation( String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceAssociation out = glueServiceAssociation.get( key );
    if( out == null ) {
      out = new GlueServiceAssociation();
      out.fresh = true;
      out.setID( key );
      glueServiceAssociation.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueServiceData getGlueServiceData( String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceData out = glueServiceData.get( key );
    if( out == null ) {
      out = new GlueServiceData();
      out.fresh = true;
      out.setID( key );
      glueServiceData.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueServiceOwner getGlueServiceOwner( String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceOwner out = glueServiceOwner.get( key );
    if( out == null ) {
      out = new GlueServiceOwner();
      out.fresh = true;
      out.setID( key );
      glueServiceOwner.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueServiceStatus getGlueServiceStatus( String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceStatus out = glueServiceStatus.get( key );
    if( out == null ) {
      out = new GlueServiceStatus();
      out.fresh = true;
      out.setID( key );
      glueServiceStatus.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSiteInfo getGlueSiteInfo( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSiteInfo out = glueSiteInfo.get( key );
    if( out == null ) {
      out = new GlueSiteInfo();
      out.fresh = true;
      out.setID( key );
      glueSiteInfo.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSiteSponsor getGlueSiteSponsor( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSiteSponsor out = glueSiteSponsor.get( key );
    if( out == null ) {
      out = new GlueSiteSponsor();
      out.fresh = true;
      out.setID( key );
      glueSiteSponsor.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSubClusterLocation getGlueSubClusterLocation( String key ) {
    if( key == null ) {
      return null;
    }
    GlueSubClusterLocation out = glueSubClusterLocation.get( key );
    if( out == null ) {
      out = new GlueSubClusterLocation();
      out.fresh = true;
      out.setID( key );
      glueSubClusterLocation.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueSubClusterSoftwareRunTimeEnvironment getGlueSubClusterSoftwareRunTimeEnvironment( String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSubClusterSoftwareRunTimeEnvironment out = glueSubClusterSoftwareRunTimeEnvironment.get( key );
    if( out == null ) {
      out = new GlueSubClusterSoftwareRunTimeEnvironment();
      out.fresh = true;
      out.setID( key );
      glueSubClusterSoftwareRunTimeEnvironment.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  /**
   * @param key
   * @return object
   */
  public GlueVO getGlueVO( String key ) {
    if( key == null ) {
      return null;
    }
    GlueVO out = glueVO.get( key );
    if( out == null ) {
      out = new GlueVO();
      out.fresh = true;
      out.setID( key );
      glueVO.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }


}
