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
 *      - Nikolaos Tsioutsias (tnikos@yahoo.com)
 *
 *****************************************************************************/
package eu.geclipse.info.glue;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ArrayList;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.info.Activator;
import java.lang.reflect.Method;

/**
 * 
 * @author George Tsouloupas
 *
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
  public Hashtable<String, GlueCEAccessControlBaseRule> glueCEAccessControlBaseRule
    = new Hashtable<String, GlueCEAccessControlBaseRule>();

  /**
   * 
   */
  public Hashtable<String, GlueCEContactString> glueCEContactString
    = new Hashtable<String, GlueCEContactString>();

  /**
   * 
   */
  public Hashtable<String, GlueCESEBind> glueCESEBind
    = new Hashtable<String, GlueCESEBind>();

  /**
   * 
   */
  public Hashtable<String, GlueCEVOView> glueCEVOView
    = new Hashtable<String, GlueCEVOView>();

  /**
   * 
   */
  public Hashtable<String, GlueCEVOViewAccessControlBaseRule> glueCEVOViewAccessControlBaseRule
    = new Hashtable<String, GlueCEVOViewAccessControlBaseRule>();

  /**
   * 
   */
  public Hashtable<String, GlueHostLocalFileSystem> glueHostLocalFileSystem
    = new Hashtable<String, GlueHostLocalFileSystem>();

  /**
   * 
   */
  public Hashtable<String, GlueHostNetworkAdapter> glueHostNetworkAdapter
    = new Hashtable<String, GlueHostNetworkAdapter>();

  /**
   * 
   */
  public Hashtable<String, GlueHostPoolAccount> glueHostPoolAccount
    = new Hashtable<String, GlueHostPoolAccount>();

  /**
   * 
   */
  public Hashtable<String, GlueHostProcess> glueHostProcess
    = new Hashtable<String, GlueHostProcess>();

  /**
   * 
   */
  public Hashtable<String, GlueHostRemoteFileSystem> glueHostRemoteFileSystem
    = new Hashtable<String, GlueHostRemoteFileSystem>();

  /**
   * 
   */
  public Hashtable<String, GlueHostRole> glueHostRole
    = new Hashtable<String, GlueHostRole>();

  /**
   * 
   */
  public Hashtable<String, GlueSA> glueSA
    = new Hashtable<String, GlueSA>();

  /**
   * 
   */
  public Hashtable<String, GlueSAAccessControlBaseRule> glueSAAccessControlBaseRule
    = new Hashtable<String, GlueSAAccessControlBaseRule>();

  /**
   * 
   */
  public Hashtable<String, GlueSEAccessProtocol> glueSEAccessProtocol
    = new Hashtable<String, GlueSEAccessProtocol>();

  /**
   * 
   */
  public Hashtable<String, GlueSEAccessProtocolCapability> glueSEAccessProtocolCapability
    = new Hashtable<String, GlueSEAccessProtocolCapability>();

  /**
   * 
   */
  public Hashtable<String, GlueSEAccessProtocolSupportedSecurity> glueSEAccessProtocolSupportedSecurity
    = new Hashtable<String, GlueSEAccessProtocolSupportedSecurity>();

  /**
   * 
   */
  public Hashtable<String, GlueSEControlProtocol> glueSEControlProtocol
    = new Hashtable<String, GlueSEControlProtocol>();

  /**
   * 
   */
  public Hashtable<String, GlueSEControlProtocolCapability> glueSEControlProtocolCapability
    = new Hashtable<String, GlueSEControlProtocolCapability>();

  /**
   * 
   */
  public Hashtable<String, GlueService> glueService
    = new Hashtable<String, GlueService>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceAccessControlRule> glueServiceAccessControlRule
    = new Hashtable<String, GlueServiceAccessControlRule>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceAssociation> glueServiceAssociation
    = new Hashtable<String, GlueServiceAssociation>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceData> glueServiceData
    = new Hashtable<String, GlueServiceData>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceOwner> glueServiceOwner
    = new Hashtable<String, GlueServiceOwner>();

  /**
   * 
   */
  public Hashtable<String, GlueServiceStatus> glueServiceStatus
    = new Hashtable<String, GlueServiceStatus>();

  /**
   * 
   */
  public Hashtable<String, GlueSiteInfo> glueSiteInfo
    = new Hashtable<String, GlueSiteInfo>();

  /**
   * 
   */
  public Hashtable<String, GlueSiteSponsor> glueSiteSponsor
    = new Hashtable<String, GlueSiteSponsor>();

  /**
   * 
   */
  public Hashtable<String, GlueSubClusterLocation> glueSubClusterLocation
    = new Hashtable<String, GlueSubClusterLocation>();

  /**
   * 
   */
  public Hashtable<String, GlueSubClusterSoftwareRunTimeEnvironment> glueSubClusterSoftwareRunTimeEnvironment
    = new Hashtable<String, GlueSubClusterSoftwareRunTimeEnvironment>();

  /**
   * 
   */
  public Hashtable<String, GlueVO> glueVO
    = new Hashtable<String, GlueVO>();

  /**
   * 
   */
  public Hashtable<String, AbstractGlueTable> fullIndex
    = new Hashtable<String, AbstractGlueTable>();

  
  protected abstract String getTag();
  

  /**
   * @return the singleton instance to the Glue information datastructure
   */
  public static GlueIndex getInstance() {
    boolean errorFound = false;
    
    if( glueIndexInstance == null ) {
      
      IPath serPath = getGridInfoLocation();
      if(serPath!=null && serPath.toFile().length() > 0)
      {
        try {
          glueIndexInstance = loadInstance();
        } catch( IOException e ) {
            errorFound = true;
        }
      }
      
      if (glueIndexInstance == null || errorFound)
      {
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

  /**
   * Delete the file where the glue information is stored and set 
   * glueIndexInstance to null.
   */
  public static void drop(){
    
    glueIndexInstance=null;
   
    IPath serPath = getGridInfoLocation();
    serPath.toFile().delete(); 
    
    //serializeInstance(); Commented out for the bug 204787
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
    /* Commented out so that the GLUE file will not be saved when 
     * eclipse is exited.
    try {
      IPath serPath = getGridInfoLocation();
      FileOutputStream fos = new FileOutputStream( serPath.toFile() );
      ObjectOutputStream oos = new ObjectOutputStream( fos );
      oos.writeObject( GlueIndex.getInstance() );
      fos.close();
    } catch( FileNotFoundException e ) {
      Activator.logException( e );
    } catch( IOException e ) {
      Activator.logException( e );
    }
    */
  }

  /**
   * Delete the file where the glue infomation is stored. 
   */
  public static void dropCachePersistenceFile(){
    IPath serPath = getGridInfoLocation();
    serPath.toFile().delete();
  }
  
  @SuppressWarnings("unused")
  private static GlueIndex loadInstance() throws IOException {
    IPath serPath = getGridInfoLocation();
    GlueIndex gi=null;
    
    /* Commented out so that the file of the glue will not be read
       when g-eclipse starts. 
     
    if(serPath!=null){
      try {
        FileInputStream fis;
        fis = new FileInputStream( serPath.toFile() );
        ObjectInputStream ois = new ObjectInputStream( fis );
        gi= ( GlueIndex )ois.readObject();
      } catch( ClassNotFoundException e ) {
        Activator.logException( e );
      } catch( Exception exception ) {
        Activator.logException( exception );
        throw new IOException("Could not load cache."); //$NON-NLS-1$
      }
    }else{
      throw new IOException("Could not load cache."); //$NON-NLS-1$
    }
    */
    return gi;
  }

  /**
   * @param objectName String representing the name of the Glue Object 
   * such as "GlueSite", "GlueCE", "GlueSE" ...
   * @param key the unique identifier for the Object to be retrieved
   * @param onlyIfExists 
   * @return The Glue object with the specified key, null otherwise
   */
  @SuppressWarnings("unchecked")
  public AbstractGlueTable get( final String objectName, final String key, final boolean onlyIfExists ) {
    AbstractGlueTable result = null;
    
    Class<?>[] c = new Class<?>[ 1 ];
    c[ 0 ] = String.class;
    Method m;
    
    try {
      boolean returnIt=true;
      if(onlyIfExists){
        String fieldName = objectName.substring( 0, 1 ).toLowerCase()
        + objectName.substring( 1 );
        Hashtable<String, String> ht=(Hashtable<String, String>) this.getClass().getField( fieldName ).get( this );
        returnIt=ht.containsKey( key );
      }
      if(returnIt){
        m = GlueIndex.class.getMethod( "get" + objectName, c ); //$NON-NLS-1$
        Object[] o = new Object[ 1 ];
        o[ 0 ] = key;
        result =  ( AbstractGlueTable )m.invoke( getInstance(), o );
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

      Activator.logException( e );
    }
    return result;
  }

  /**
   * @param objectName String representing the name of the Glue Object 
   * such as "GlueSite", "GlueCE", "GlueSE" ...
   * @return a list of all Glue objects of the provided type
   */
  @SuppressWarnings("unchecked")
  public ArrayList<AbstractGlueTable> getList( final String objectName ) {
    ArrayList<AbstractGlueTable> agtList = new ArrayList<AbstractGlueTable>();
    try {
      String fieldName = objectName.substring( 0, 1 ).toLowerCase()
                         + objectName.substring( 1 );
      Field f = GlueIndex.class.getField( fieldName );
      Object o = f.get( this );
      if (o instanceof Hashtable) {
        Hashtable<String, AbstractGlueTable> ht=( Hashtable<String, AbstractGlueTable> )o;
        Enumeration<AbstractGlueTable> enAgt = ht.elements();
        while( enAgt.hasMoreElements() ) {
          agtList.add( enAgt.nextElement() );
        }
      }
    } catch( SecurityException e ) {
      Activator.logException( e );
    } catch( NoSuchFieldException e ) {
      Activator.logException( e );
    } catch( IllegalArgumentException e ) {
      Activator.logException( e );
    } catch( IllegalAccessException e ) {
      Activator.logException( e );
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
    //String newKey = agt.getClass().getName() + key;
    //AbstractGlueTable previous = this.fullIndex.put( newKey, agt );
  }
  
  /**
   * Get the GlueHost
   * @param key the name of the GlueHost
   * @return the GlueHost or null.
   */
  public GlueHost getGlueHost( final String key ) {
    GlueHost result = null;
    
    if( key != null ) {
      
      result = this.glueHost.get( key );
      if( result == null ) {
        result = new GlueHost();
  
        result.setID( key );
        this.glueHost.put( key, result );
        putInFullIndex( key, result );
      }
    }
    
    return result;
  }

  /**
   * Get the Glue Site.
   * @param key the name of the glue site
   * @return the GlueSite or null
   */
  public GlueSite getGlueSite( final String key ) {
    GlueSite result = null;
    
    if( key != null ) 
    {      
      result = this.glueSite.get( key );
      if( result == null ) {
        result = new GlueSite();
  
        result.setID( key );
        this.glueSite.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the Glue Storage Element
   * @param key the name of the GlueSE
   * @return the GlueSE or null
   */
  public GlueSE getGlueSE( final String key ) {
    GlueSE result = null;
    
    if( key != null ) {
       
      result = this.glueSE.get( key );
      if( result == null ) {
        result = new GlueSE();
  
        result.setID( key );
        this.glueSE.put( key, result );
        putInFullIndex( key, result );
      }
    }
    
    return result;
  }

  /**
   * Get the GlueSL
   * @param key the name of the GlueSL
   * @return the GlueSL or null
   */
  public GlueSL getGlueSL( final String key ) {
    GlueSL result = null;
    
    if( key != null ) {
      
      result = this.glueSL.get( key );
      if( result == null ) {
        result = new GlueSL();
  
        result.setID( key );
        this.glueSL.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueCluster
   * @param key the name of the GlueCluster
   * @return the GlueCluster or null
   */
  public GlueCluster getGlueCluster( final String key ) {
    GlueCluster result = null;
    
    if( key != null ) {
      
      result = this.glueCluster.get( key );
      if( result == null ) {
        result = new GlueCluster();
  
        result.setID( key );
        this.glueCluster.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSubCluster
   * @param key the name of the GlueSubCluster
   * @return the GlueSubCluster or null
   */
  public GlueSubCluster getGlueSubCluster( final String key ) {
    GlueSubCluster result = null;
    
    if( key != null ) {
      
      result = this.glueSubCluster.get( key );
      if( result == null ) {
        result = new GlueSubCluster();
  
        result.setID( key );
        this.glueSubCluster.put( key, result );
        putInFullIndex( key, result );
      }
    }
    
    return result;
  }

  /**
   * Get the GlueCE
   * @param key the name of the GlueCE
   * @return the GlueCE or null
   */
  public GlueCE getGlueCE( final String key ) {
    GlueCE result = null;
    
    if( key != null ) {
      
      result = this.glueCE.get( key );
      if( result == null ) {
        result = new GlueCE();
  
        result.setID( key );
        this.glueCE.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueBatchJob
   * @param key the name of the GlueBatchJob
   * @return the GlueBatchJob or null
   */
  public GlueBatchJob getGlueBatchJob( final String key ) {
    GlueBatchJob result = null;
    
    if( key != null ) {
      
      result = this.glueBatchJob.get( key );
      if( result == null ) {
        result = new GlueBatchJob();
  
        result.setID( key );
        this.glueBatchJob.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueBatchQueue
   * @param key the name of the GlueBatchQueue
   * @return the GlueBatchQueue or null.
   */
  public GlueBatchQueue getGlueBatchQueue( final String key ) {
    GlueBatchQueue result = null;
    
    if( key != null ) {
      
      result = this.glueBatchQueue.get( key );
      if( result == null ) {
        result = new GlueBatchQueue();
  
        result.setID( key );
        this.glueBatchQueue.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueBatchSystem
   * @param key the name of the GlueBatchSystem
   * @return the GlueBatchSystem or null
   */
  public GlueBatchSystem getGlueBatchSystem( final String key ) {
    GlueBatchSystem result = null;
    
    if( key != null ) {
      
      result = this.glueBatchSystem.get( key );
      if( result == null ) {
        result = new GlueBatchSystem();
  
        result.setID( key );
        this.glueBatchSystem.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueCEAccessControlBaseRule
   * @param key the name of the GlueCEAccessControlBaseRule
   * @return the GlueCEAccessControlBaseRule or null
   */
  public GlueCEAccessControlBaseRule getGlueCEAccessControlBaseRule( final String key )
  {
    GlueCEAccessControlBaseRule result = null;
    
    if( key != null ) {
      
      result = this.glueCEAccessControlBaseRule.get( key );
      if( result == null ) {
        result = new GlueCEAccessControlBaseRule();
  
        result.setID( key );
        this.glueCEAccessControlBaseRule.put( key, result );
        putInFullIndex( key, result );
      }
    }
    
    return result;
  }

  /**
   * Get the GlueCEContactString
   * @param key the name of the GlueCEContactString
   * @return the GlueCEContactString or null
   */
  public GlueCEContactString getGlueCEContactString( final String key ) {
    GlueCEContactString result = null;
    
    if( key != null ) {
    
      result = this.glueCEContactString.get( key );
      if( result == null ) {
        result = new GlueCEContactString();
  
        result.setID( key );
        this.glueCEContactString.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueCESEBind
   * @param key the name of the GlueCESEBind to get
   * @return the GlueCESEBind or null
   */
  public GlueCESEBind getGlueCESEBind( final String key ) {
    GlueCESEBind result = null;
    
    if( key != null ) {
      result = this.glueCESEBind.get( key );
      if( result == null ) {
        result = new GlueCESEBind();
  
        result.setID( key );
        this.glueCESEBind.put( key, result );
      }
    }
    return result;
  } 

  /**
   * Get the GlueCEVOView
   * @param key the name of the GlueCEVOView to get
   * @return the GlueCEVOView or null.
   */
  public GlueCEVOView getGlueCEVOView( final String key ) {
    GlueCEVOView result = null;
    
    if( key != null ) {
      
      result = this.glueCEVOView.get( key );
      if( result == null ) {
        result = new GlueCEVOView();
  
        result.setID( key );
        this.glueCEVOView.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueCEVOViewAccessControlBaseRule
   * @param key the name of the GlueCEVOViewAccessControlBaseRule to get
   * @return the GlueCEVOViewAccessControlBaseRule or null
   */
  public GlueCEVOViewAccessControlBaseRule getGlueCEVOViewAccessControlBaseRule( final String key )
  {
    GlueCEVOViewAccessControlBaseRule result = null;
    
    if( key != null ) {
      
      result = this.glueCEVOViewAccessControlBaseRule.get( key );
      if( result == null ) {
        result = new GlueCEVOViewAccessControlBaseRule();
  
        result.setID( key );
        this.glueCEVOViewAccessControlBaseRule.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueHostLocalFileSystem
   * @param key the name of the GlueHostLocalFileSystem to get
   * @return the GlueHostLocalFileSystem or null
   */
  public GlueHostLocalFileSystem getGlueHostLocalFileSystem( final String key ) {
    GlueHostLocalFileSystem result = null;
    
    if( key != null ) {
      
      result = this.glueHostLocalFileSystem.get( key );
      if( result == null ) {
        result = new GlueHostLocalFileSystem();
  
        result.setID( key );
        this.glueHostLocalFileSystem.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueHostNetworkAdapter
   * @param key the name of the GlueHostNetworkAdapter to get
   * @return GlueHostNetworkAdapter or null
   */
  public GlueHostNetworkAdapter getGlueHostNetworkAdapter( final String key ) {
    GlueHostNetworkAdapter result = null;
    
    if( key != null ) {
      
      result = this.glueHostNetworkAdapter.get( key );
      if( result == null ) {
        result = new GlueHostNetworkAdapter();
  
        result.setID( key );
        this.glueHostNetworkAdapter.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueHostPoolAccount
   * @param key the name of the GlueHostPoolAccount
   * @return the GlueHostPoolAccount or null
   */
  public GlueHostPoolAccount getGlueHostPoolAccount( final String key ) {
    GlueHostPoolAccount result = null;
    if( key != null ) {
      result = this.glueHostPoolAccount.get( key );
      if( result == null ) {
        result = new GlueHostPoolAccount();
  
        result.setID( key );
        this.glueHostPoolAccount.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueHostProcess
   * @param key the name of the GlueHostProcess to get
   * @return the GlueHostProcess or null
   */
  public GlueHostProcess getGlueHostProcess( final String key ) {
    GlueHostProcess result = null;
    
    if( key != null ) {
      
      result = this.glueHostProcess.get( key );
      if( result == null ) {
        result = new GlueHostProcess();
  
        result.setID( key );
        this.glueHostProcess.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueHostRemoteFileSystem
   * @param key the name of the GlueHostRemoteFileSystem to get
   * @return the GlueHostRemoteFileSystem or null
   */
  public GlueHostRemoteFileSystem getGlueHostRemoteFileSystem( final String key ) {
    GlueHostRemoteFileSystem result = null;
    if( key != null ) {
      
      result = this.glueHostRemoteFileSystem.get( key );
      if( result == null ) {
        result = new GlueHostRemoteFileSystem();
  
        result.setID( key );
        this.glueHostRemoteFileSystem.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueHostRole
   * @param key the name of the GlueHostRole to get
   * @return the GlueHostRole or null
   */
  public GlueHostRole getGlueHostRole( final String key ) {
    GlueHostRole result = null;
    
    if( key != null ) {
      
      result = this.glueHostRole.get( key );
      if( result == null ) {
        result = new GlueHostRole();
  
        result.setID( key );
        this.glueHostRole.put( key, result );
        putInFullIndex( key, result );
      }
    }
    
    return result;
  }

  /**
   * Get the GlueSA
   * @param key the name of the GlueSA to get
   * @return the GlueSA or null
   */
  public GlueSA getGlueSA( final String key ) {
    GlueSA result = null;
    
    if( key != null ) {
      
      result = this.glueSA.get( key );
      if( result == null ) {
        result = new GlueSA();
  
        result.setID( key );
        this.glueSA.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSAAccessControlBaseRule
   * @param key the name of the GlueSAAccessControlBaseRule
   * @return the GlueSAAccessControlBaseRule or null
   */
  public GlueSAAccessControlBaseRule getGlueSAAccessControlBaseRule( final String key )
  {
    GlueSAAccessControlBaseRule result = null;
    
    if( key != null ) {
      
      result = this.glueSAAccessControlBaseRule.get( key );
      if( result == null ) {
        result = new GlueSAAccessControlBaseRule();
  
        result.setID( key );
        this.glueSAAccessControlBaseRule.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSEAccessProtocol
   * @param key the name of the GlueSEAccessProtocol to get
   * @return the GlueSEAccessProtocol or null
   */
  public GlueSEAccessProtocol getGlueSEAccessProtocol( final String key ) {
    GlueSEAccessProtocol result = null;
    
    if( key != null ) {
    
      result = this.glueSEAccessProtocol.get( key );
      if( result == null ) {
        result = new GlueSEAccessProtocol();
  
        result.setID( key );
        this.glueSEAccessProtocol.put( key, result );
        putInFullIndex( key, result );
      }
    }
    
    return result;
  }

  /**
   * Get the GlueSEAccessProtocolCapability
   * @param key the name of the GlueSEAccessProtocolCapability to get
   * @return the GlueSEAccessProtocolCapability or null
   */
  public GlueSEAccessProtocolCapability getGlueSEAccessProtocolCapability( final String key )
  {
    GlueSEAccessProtocolCapability result = null;
    
    if( key != null ) {
      
      result = this.glueSEAccessProtocolCapability.get( key );
      if( result == null ) {
        result = new GlueSEAccessProtocolCapability();
  
        result.setID( key );
        this.glueSEAccessProtocolCapability.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSEAccessProtocolSupportedSecurity
   * @param key the name of the GlueSEAccessProtocolSupportedSecurity to get
   * @return the GlueSEAccessProtocolSupportedSecurity or null
   */
  public GlueSEAccessProtocolSupportedSecurity getGlueSEAccessProtocolSupportedSecurity( final String key )
  {
    GlueSEAccessProtocolSupportedSecurity result = null;
    
    if( key != null ) {
      
      result = this.glueSEAccessProtocolSupportedSecurity.get( key );
      if( result == null ) {
        result = new GlueSEAccessProtocolSupportedSecurity();
  
        result.setID( key );
        this.glueSEAccessProtocolSupportedSecurity.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSEControlProtocol
   * @param key the name of the GlueSEControlProtocol
   * @return the GlueSEControlProtocol or null
   */
  public GlueSEControlProtocol getGlueSEControlProtocol( final String key ) {
    GlueSEControlProtocol result = null;
    
    if( key != null ) {
      
      result = this.glueSEControlProtocol.get( key );
      if( result == null ) {
        result = new GlueSEControlProtocol();
  
        result.setID( key );
        this.glueSEControlProtocol.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSEControlProtocolCapability
   * @param key the name of the GlueSEControlProtocolCapability to get
   * @return the GlueSEControlProtocolCapability or null
   */
  public GlueSEControlProtocolCapability getGlueSEControlProtocolCapability( final String key )
  {
    GlueSEControlProtocolCapability result = null;
    
    if( key != null ) {
      
      result = this.glueSEControlProtocolCapability.get( key );
      if( result == null ) {
        result = new GlueSEControlProtocolCapability();
  
        result.setID( key );
        this.glueSEControlProtocolCapability.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueService
   * @param key the name of the GlueService to get
   * @return the GlueService or null
   */
  public GlueService getGlueService( final String key ) {
    GlueService result = null;
    
    if( key != null ) {
    
      result = this.glueService.get( key );
      if( result == null ) {
        result = new GlueService();
  
        result.setID( key );
        this.glueService.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueServiceAccessControlRule
   * @param key the name of the GlueServiceAccessControlRule to get
   * @return the GlueServiceAccessControlRule or null
   */
  public GlueServiceAccessControlRule getGlueServiceAccessControlRule( final String key )
  {
    GlueServiceAccessControlRule result = null;
    
    if( key != null ) {
    
      result = this.glueServiceAccessControlRule.get( key );
      if( result == null ) {
        result = new GlueServiceAccessControlRule();
  
        result.setID( key );
        this.glueServiceAccessControlRule.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueServiceAssociation
   * @param key the name of the GlueServiceAssociation to get
   * @return the GlueServiceAssociation or null
   */
  public GlueServiceAssociation getGlueServiceAssociation( final String key ) {
    GlueServiceAssociation result = null;
    
    if( key != null ) {
      
      result = this.glueServiceAssociation.get( key );
      if( result == null ) {
        result = new GlueServiceAssociation();
  
        result.setID( key );
        this.glueServiceAssociation.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueServiceData
   * @param key the name of the GlueServiceData to get
   * @return the GlueServiceData or null
   */
  public GlueServiceData getGlueServiceData( final String key ) {
    GlueServiceData result = null;
    
    if( key != null ) {
      
      result = this.glueServiceData.get( key );
      if( result == null ) {
        result = new GlueServiceData();
  
        result.setID( key );
        this.glueServiceData.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueServiceOwner
   * @param key the name of the GlueServiceOwner to get
   * @return  the GlueServiceOwner or null
   */
  public GlueServiceOwner getGlueServiceOwner( final String key ) {
    GlueServiceOwner result = null;
    
    if( key != null ) {
      
      result = this.glueServiceOwner.get( key );
      if( result == null ) {
        result = new GlueServiceOwner();
  
        result.setID( key );
        this.glueServiceOwner.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueServiceStatus
   * @param key the name of the GlueServiceStatus to get
   * @return the GlueServiceStatus or null
   */
  public GlueServiceStatus getGlueServiceStatus( final String key ) {
    GlueServiceStatus result = null;
    
    if( key != null ) {
      
      result = this.glueServiceStatus.get( key );
      if( result == null ) {
        result = new GlueServiceStatus();
  
        result.setID( key );
        this.glueServiceStatus.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSiteInfo
   * @param key the name of the GlueSiteInfo
   * @return the GlueSiteInfo or null
   */
  public GlueSiteInfo getGlueSiteInfo( final String key ) {
    GlueSiteInfo result = null;
    
    if( key != null ) {
    
      result = this.glueSiteInfo.get( key );
      if( result == null ) {
        result = new GlueSiteInfo();
  
        result.setID( key );
        this.glueSiteInfo.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSiteSponsor
   * @param key the name of the GlueSiteSponsor
   * @return the GlueSiteSponsor or null
   */
  public GlueSiteSponsor getGlueSiteSponsor( final String key ) {
    GlueSiteSponsor result = null;
    
    if( key != null ) {
      
      result = this.glueSiteSponsor.get( key );
      if( result == null ) {
        result = new GlueSiteSponsor();
  
        result.setID( key );
        this.glueSiteSponsor.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSubClusterLocation
   * @param key the name of the GlueSubClusterLocation to get
   * @return the GlueSubClusterLocation or null
   */
  public GlueSubClusterLocation getGlueSubClusterLocation( final String key ) {
    GlueSubClusterLocation result = null;
    
    if( key != null ) {
      
      result = this.glueSubClusterLocation.get( key );
      if( result == null ) {
        result = new GlueSubClusterLocation();
  
        result.setID( key );
        this.glueSubClusterLocation.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueSubClusterSoftwareRunTimeEnvironment
   * @param key the name of the GlueSubClusterSoftwareRunTimeEnvironment
   * @return the GlueSubClusterSoftwareRunTimeEnvironment or null
   */
  public GlueSubClusterSoftwareRunTimeEnvironment getGlueSubClusterSoftwareRunTimeEnvironment( final String key )
  {
    GlueSubClusterSoftwareRunTimeEnvironment result = null;
    
    if( key != null ) {
      
      result = this.glueSubClusterSoftwareRunTimeEnvironment.get( key );
      if( result == null ) {
        result = new GlueSubClusterSoftwareRunTimeEnvironment();
  
        result.setID( key );
        this.glueSubClusterSoftwareRunTimeEnvironment.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }

  /**
   * Get the GlueVO
   * @param key the name of the GlueVO to get
   * @return the GlueVO or null
   */
  public GlueVO getGlueVO( final String key ) {
    GlueVO result = null;
    
    if( key != null ) {
      
      result = this.glueVO.get( key );
      if( result == null ) {
        result = new GlueVO();
  
        result.setID( key );
        this.glueVO.put( key, result );
        putInFullIndex( key, result );
      }
    }
    return result;
  }
}
