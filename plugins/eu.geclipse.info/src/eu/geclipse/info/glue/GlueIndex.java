/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *
 *****************************************************************************/
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
  
  private static GlueIndex glueIndexInstance;

  //Used for invalidating the cache 
  
  /**
   * 
   */
  public String voListString;

  /**
   * 
   */
  public Hashtable<String, GlueHost> glueHost = new Hashtable<String, GlueHost>();

  /**
   * 
   */
  public Hashtable<String, GlueSite> glueSite = new Hashtable<String, GlueSite>();

  /**
   * 
   */
  public Hashtable<String, GlueSE> glueSE = new Hashtable<String, GlueSE>();

  /**
   * 
   */
  public Hashtable<String, GlueSL> glueSL = new Hashtable<String, GlueSL>();

  /**
   * 
   */
  public Hashtable<String, GlueCluster> glueCluster = new Hashtable<String, GlueCluster>();

  /**
   * 
   */
  public Hashtable<String, GlueSubCluster> glueSubCluster = new Hashtable<String, GlueSubCluster>();

  /**
   * 
   */
  public Hashtable<String, GlueCE> glueCE = new Hashtable<String, GlueCE>();

  /**
   * 
   */
  public Hashtable<String, GlueBatchJob> glueBatchJob = new Hashtable<String, GlueBatchJob>();

  /**
   * 
   */
  public Hashtable<String, GlueBatchQueue> glueBatchQueue = new Hashtable<String, GlueBatchQueue>();

  /**
   * 
   */
  public Hashtable<String, GlueBatchSystem> glueBatchSystem = new Hashtable<String, GlueBatchSystem>();

  /**
   * 
   */
  public Hashtable<String, GlueCEAccessControlBaseRule> glueCEAccessControlBaseRule = new Hashtable<String, GlueCEAccessControlBaseRule>();

  /**
   * 
   */
  public Hashtable<String, GlueCEContactString> glueCEContactString = new Hashtable<String, GlueCEContactString>();

  /**
   * 
   */
  public Hashtable<String, GlueCESEBind> glueCESEBind = new Hashtable<String, GlueCESEBind>();

  /**
   * 
   */
  public Hashtable<String, GlueCEVOView> glueCEVOView = new Hashtable<String, GlueCEVOView>();

  /**
   * 
   */
  public Hashtable<String, GlueCEVOViewAccessControlBaseRule> glueCEVOViewAccessControlBaseRule = new Hashtable<String, GlueCEVOViewAccessControlBaseRule>();

  /**
   * 
   */
  public Hashtable<String, GlueHostLocalFileSystem> glueHostLocalFileSystem = new Hashtable<String, GlueHostLocalFileSystem>();

  /**
   * 
   */
  public Hashtable<String, GlueHostNetworkAdapter> glueHostNetworkAdapter = new Hashtable<String, GlueHostNetworkAdapter>();

  /**
   * 
   */
  public Hashtable<String, GlueHostPoolAccount> glueHostPoolAccount = new Hashtable<String, GlueHostPoolAccount>();

  /**
   * 
   */
  public Hashtable<String, GlueHostProcess> glueHostProcess = new Hashtable<String, GlueHostProcess>();

  /**
   * 
   */
  public Hashtable<String, GlueHostRemoteFileSystem> glueHostRemoteFileSystem = new Hashtable<String, GlueHostRemoteFileSystem>();

  /**
   * 
   */
  public Hashtable<String, GlueHostRole> glueHostRole = new Hashtable<String, GlueHostRole>();

  /**
   * 
   */
  public Hashtable<String, GlueSA> glueSA = new Hashtable<String, GlueSA>();

  /**
   * 
   */
  public Hashtable<String, GlueSAAccessControlBaseRule> glueSAAccessControlBaseRule = new Hashtable<String, GlueSAAccessControlBaseRule>();

  /**
   * 
   */
  public Hashtable<String, GlueSEAccessProtocol> glueSEAccessProtocol = new Hashtable<String, GlueSEAccessProtocol>();

  /**
   * 
   */
  public Hashtable<String, GlueSEAccessProtocolCapability> glueSEAccessProtocolCapability = new Hashtable<String, GlueSEAccessProtocolCapability>();

  /**
   * 
   */
  public Hashtable<String, GlueSEAccessProtocolSupportedSecurity> glueSEAccessProtocolSupportedSecurity = new Hashtable<String, GlueSEAccessProtocolSupportedSecurity>();

  /**
   * 
   */
  public Hashtable<String, GlueSEControlProtocol> glueSEControlProtocol = new Hashtable<String, GlueSEControlProtocol>();

  /**
   * 
   */
  public Hashtable<String, GlueSEControlProtocolCapability> glueSEControlProtocolCapability = new Hashtable<String, GlueSEControlProtocolCapability>();

  /**
   * 
   */
  public Hashtable<String, GlueService> glueService = new Hashtable<String, GlueService>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceAccessControlRule> glueServiceAccessControlRule = new Hashtable<String, GlueServiceAccessControlRule>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceAssociation> glueServiceAssociation = new Hashtable<String, GlueServiceAssociation>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceData> glueServiceData = new Hashtable<String, GlueServiceData>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceOwner> glueServiceOwner = new Hashtable<String, GlueServiceOwner>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceStatus> glueServiceStatus = new Hashtable<String, GlueServiceStatus>();

  /**
   * 
   */
  public Hashtable<String, GlueSiteInfo> glueSiteInfo = new Hashtable<String, GlueSiteInfo>();

  /**
   * 
   */
  public Hashtable<String, GlueSiteSponsor> glueSiteSponsor = new Hashtable<String, GlueSiteSponsor>();

  /**
   * 
   */
  public Hashtable<String, GlueSubClusterLocation> glueSubClusterLocation = new Hashtable<String, GlueSubClusterLocation>();

  /**
   * 
   */
  public Hashtable<String, GlueSubClusterSoftwareRunTimeEnvironment> glueSubClusterSoftwareRunTimeEnvironment = new Hashtable<String, GlueSubClusterSoftwareRunTimeEnvironment>();

  /**
   * 
   */
  public Hashtable<String, GlueVO> glueVO = new Hashtable<String, GlueVO>();

  /**
   * 
   */
  public Hashtable<String, AbstractGlueTable> fullIndex = new Hashtable<String, AbstractGlueTable>();

  
  protected abstract String getTag();
  

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

  public static void dropCachePersistenceFile(){
    IPath serPath = getGridInfoLocation();
    serPath.toFile().delete();
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
      } catch( Exception exception ) {
        Activator.logException( exception );
        throw new IOException("Could not load cache."); //$NON-NLS-1$
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
   * @param onlyIfExists 
   * @return The Glue object with the specified key, null otherwise
   */
  public AbstractGlueTable get( final String objectName, final String key, final boolean onlyIfExists ) {
    Class[] c = new Class[ 1 ];
    c[ 0 ] = String.class;
    Method m;
    
    try {
      boolean returnIt=true;
      if(onlyIfExists){
        String fieldName = objectName.substring( 0, 1 ).toLowerCase()
        + objectName.substring( 1 );
        Hashtable ht=(Hashtable)this.getClass().getField( fieldName ).get( this );
        returnIt=ht.containsKey( key );
      }
      if(returnIt){
        m = GlueIndex.class.getMethod( "get" + objectName, c ); //$NON-NLS-1$
        Object[] o = new Object[ 1 ];
        o[ 0 ] = key;
        return ( AbstractGlueTable )m.invoke( getInstance(), o );
      }
    } catch( SecurityException e ) {
      //
    } catch( NoSuchMethodException e ) {
      //
    } catch( IllegalArgumentException e ) {
      //;
    } catch( IllegalAccessException e ) {
      //
    } catch( InvocationTargetException e ) {
      //
    } catch( NoSuchFieldException e ) {

      e.printStackTrace();
    }
    return null;
  }

  /**
   * @param objectName String representing the name of the Glue Object 
   * such as "GlueSite", "GlueCE", "GlueSE" ...
   * @return a list of all Glue objects of the provided type
   */
  public ArrayList<AbstractGlueTable> getList( final String objectName ) {
    ArrayList<AbstractGlueTable> agtList = new ArrayList<AbstractGlueTable>();
    try {
      String fieldName = objectName.substring( 0, 1 ).toLowerCase()
                         + objectName.substring( 1 );
      Field f = GlueIndex.class.getField( fieldName );
      Object o = f.get( this );
      Hashtable<String, AbstractGlueTable> ht=( Hashtable<String, AbstractGlueTable> )o;
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
  
  /**
   * @return A list of all possible Glue object types
   */
  public static String[] getObjectTypeList() {
    return tableList;
  }
  

  private void putInFullIndex( final String key,final AbstractGlueTable agt ) {
    String newKey = agt.getClass().getName() + key;
    AbstractGlueTable previous = this.fullIndex.put( newKey, agt );
    if( previous != null ) {
      //System.out.println( "Duplicate:\n   " + previous + "\n   " + agt ); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }
  

  public GlueHost getGlueHost( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHost out = this.glueHost.get( key );
    if( out == null ) {
      out = new GlueHost();

      out.setID( key );
      this.glueHost.put( key, out );
      putInFullIndex( key, out );
    } else {
      // //out.fresh=false;
    }
    return out;
  }

  public GlueSite getGlueSite( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSite out = this.glueSite.get( key );
    if( out == null ) {
      out = new GlueSite();

      out.setID( key );
      this.glueSite.put( key, out );
      putInFullIndex( key, out );
    } else {
      // //out.fresh=false;
    }
    return out;
  }

  public GlueSE getGlueSE( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSE out = this.glueSE.get( key );
    if( out == null ) {
      out = new GlueSE();

      out.setID( key );
      this.glueSE.put( key, out );
      putInFullIndex( key, out );
    } else {
      // //out.fresh=false;
    }
    return out;
  }

  public GlueSL getGlueSL( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSL out = this.glueSL.get( key );
    if( out == null ) {
      out = new GlueSL();

      out.setID( key );
      this.glueSL.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueCluster getGlueCluster( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueCluster out = this.glueCluster.get( key );
    if( out == null ) {
      out = new GlueCluster();

      out.setID( key );
      this.glueCluster.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSubCluster getGlueSubCluster( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSubCluster out = this.glueSubCluster.get( key );
    if( out == null ) {
      out = new GlueSubCluster();

      out.setID( key );
      this.glueSubCluster.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueCE getGlueCE( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueCE out = this.glueCE.get( key );
    if( out == null ) {
      out = new GlueCE();

      out.setID( key );
      this.glueCE.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueBatchJob getGlueBatchJob( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueBatchJob out = this.glueBatchJob.get( key );
    if( out == null ) {
      out = new GlueBatchJob();

      out.setID( key );
      this.glueBatchJob.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueBatchQueue getGlueBatchQueue( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueBatchQueue out = this.glueBatchQueue.get( key );
    if( out == null ) {
      out = new GlueBatchQueue();

      out.setID( key );
      this.glueBatchQueue.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueBatchSystem getGlueBatchSystem( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueBatchSystem out = this.glueBatchSystem.get( key );
    if( out == null ) {
      out = new GlueBatchSystem();

      out.setID( key );
      this.glueBatchSystem.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueCEAccessControlBaseRule getGlueCEAccessControlBaseRule( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueCEAccessControlBaseRule out = this.glueCEAccessControlBaseRule.get( key );
    if( out == null ) {
      out = new GlueCEAccessControlBaseRule();

      out.setID( key );
      this.glueCEAccessControlBaseRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueCEContactString getGlueCEContactString( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueCEContactString out = this.glueCEContactString.get( key );
    if( out == null ) {
      out = new GlueCEContactString();

      out.setID( key );
      this.glueCEContactString.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueCESEBind getGlueCESEBind( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueCESEBind out = this.glueCESEBind.get( key );
    if( out == null ) {
      out = new GlueCESEBind();

      out.setID( key );
      this.glueCESEBind.put( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  } 

  public GlueCEVOView getGlueCEVOView( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueCEVOView out = this.glueCEVOView.get( key );
    if( out == null ) {
      out = new GlueCEVOView();

      out.setID( key );
      this.glueCEVOView.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueCEVOViewAccessControlBaseRule getGlueCEVOViewAccessControlBaseRule( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueCEVOViewAccessControlBaseRule out = this.glueCEVOViewAccessControlBaseRule.get( key );
    if( out == null ) {
      out = new GlueCEVOViewAccessControlBaseRule();

      out.setID( key );
      this.glueCEVOViewAccessControlBaseRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueHostLocalFileSystem getGlueHostLocalFileSystem( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostLocalFileSystem out = this.glueHostLocalFileSystem.get( key );
    if( out == null ) {
      out = new GlueHostLocalFileSystem();

      out.setID( key );
      this.glueHostLocalFileSystem.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueHostNetworkAdapter getGlueHostNetworkAdapter( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostNetworkAdapter out = this.glueHostNetworkAdapter.get( key );
    if( out == null ) {
      out = new GlueHostNetworkAdapter();

      out.setID( key );
      this.glueHostNetworkAdapter.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueHostPoolAccount getGlueHostPoolAccount( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostPoolAccount out = this.glueHostPoolAccount.get( key );
    if( out == null ) {
      out = new GlueHostPoolAccount();

      out.setID( key );
      this.glueHostPoolAccount.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueHostProcess getGlueHostProcess( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostProcess out = this.glueHostProcess.get( key );
    if( out == null ) {
      out = new GlueHostProcess();

      out.setID( key );
      this.glueHostProcess.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueHostRemoteFileSystem getGlueHostRemoteFileSystem( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostRemoteFileSystem out = this.glueHostRemoteFileSystem.get( key );
    if( out == null ) {
      out = new GlueHostRemoteFileSystem();

      out.setID( key );
      this.glueHostRemoteFileSystem.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueHostRole getGlueHostRole( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueHostRole out = this.glueHostRole.get( key );
    if( out == null ) {
      out = new GlueHostRole();

      out.setID( key );
      this.glueHostRole.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSA getGlueSA( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSA out = this.glueSA.get( key );
    if( out == null ) {
      out = new GlueSA();

      out.setID( key );
      this.glueSA.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSAAccessControlBaseRule getGlueSAAccessControlBaseRule( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSAAccessControlBaseRule out = this.glueSAAccessControlBaseRule.get( key );
    if( out == null ) {
      out = new GlueSAAccessControlBaseRule();

      out.setID( key );
      this.glueSAAccessControlBaseRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSEAccessProtocol getGlueSEAccessProtocol( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSEAccessProtocol out = this.glueSEAccessProtocol.get( key );
    if( out == null ) {
      out = new GlueSEAccessProtocol();

      out.setID( key );
      this.glueSEAccessProtocol.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSEAccessProtocolCapability getGlueSEAccessProtocolCapability( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSEAccessProtocolCapability out = this.glueSEAccessProtocolCapability.get( key );
    if( out == null ) {
      out = new GlueSEAccessProtocolCapability();

      out.setID( key );
      this.glueSEAccessProtocolCapability.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSEAccessProtocolSupportedSecurity getGlueSEAccessProtocolSupportedSecurity( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSEAccessProtocolSupportedSecurity out = this.glueSEAccessProtocolSupportedSecurity.get( key );
    if( out == null ) {
      out = new GlueSEAccessProtocolSupportedSecurity();

      out.setID( key );
      this.glueSEAccessProtocolSupportedSecurity.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSEControlProtocol getGlueSEControlProtocol( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSEControlProtocol out = this.glueSEControlProtocol.get( key );
    if( out == null ) {
      out = new GlueSEControlProtocol();

      out.setID( key );
      this.glueSEControlProtocol.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSEControlProtocolCapability getGlueSEControlProtocolCapability( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSEControlProtocolCapability out = this.glueSEControlProtocolCapability.get( key );
    if( out == null ) {
      out = new GlueSEControlProtocolCapability();

      out.setID( key );
      this.glueSEControlProtocolCapability.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueService getGlueService( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueService out = this.glueService.get( key );
    if( out == null ) {
      out = new GlueService();

      out.setID( key );
      this.glueService.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueServiceAccessControlRule getGlueServiceAccessControlRule( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueServiceAccessControlRule out = this.glueServiceAccessControlRule.get( key );
    if( out == null ) {
      out = new GlueServiceAccessControlRule();

      out.setID( key );
      this.glueServiceAccessControlRule.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueServiceAssociation getGlueServiceAssociation( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceAssociation out = this.glueServiceAssociation.get( key );
    if( out == null ) {
      out = new GlueServiceAssociation();

      out.setID( key );
      this.glueServiceAssociation.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueServiceData getGlueServiceData( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceData out = this.glueServiceData.get( key );
    if( out == null ) {
      out = new GlueServiceData();

      out.setID( key );
      this.glueServiceData.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueServiceOwner getGlueServiceOwner( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceOwner out = this.glueServiceOwner.get( key );
    if( out == null ) {
      out = new GlueServiceOwner();

      out.setID( key );
      this.glueServiceOwner.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueServiceStatus getGlueServiceStatus( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueServiceStatus out = this.glueServiceStatus.get( key );
    if( out == null ) {
      out = new GlueServiceStatus();

      out.setID( key );
      this.glueServiceStatus.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSiteInfo getGlueSiteInfo( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSiteInfo out = this.glueSiteInfo.get( key );
    if( out == null ) {
      out = new GlueSiteInfo();

      out.setID( key );
      this.glueSiteInfo.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSiteSponsor getGlueSiteSponsor( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSiteSponsor out = this.glueSiteSponsor.get( key );
    if( out == null ) {
      out = new GlueSiteSponsor();

      out.setID( key );
      this.glueSiteSponsor.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSubClusterLocation getGlueSubClusterLocation( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueSubClusterLocation out = this.glueSubClusterLocation.get( key );
    if( out == null ) {
      out = new GlueSubClusterLocation();

      out.setID( key );
      this.glueSubClusterLocation.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueSubClusterSoftwareRunTimeEnvironment getGlueSubClusterSoftwareRunTimeEnvironment( final String key )
  {
    if( key == null ) {
      return null;
    }
    GlueSubClusterSoftwareRunTimeEnvironment out = this.glueSubClusterSoftwareRunTimeEnvironment.get( key );
    if( out == null ) {
      out = new GlueSubClusterSoftwareRunTimeEnvironment();

      out.setID( key );
      this.glueSubClusterSoftwareRunTimeEnvironment.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }

  public GlueVO getGlueVO( final String key ) {
    if( key == null ) {
      return null;
    }
    GlueVO out = this.glueVO.get( key );
    if( out == null ) {
      out = new GlueVO();

      out.setID( key );
      this.glueVO.put( key, out );
      putInFullIndex( key, out );
    } else {
      // out.fresh=false;
    }
    return out;
  }
}
