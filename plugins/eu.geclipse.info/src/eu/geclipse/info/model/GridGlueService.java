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
 *    Harald Gjermundrod - added the getHostName method
 *****************************************************************************/
package eu.geclipse.info.model;

 import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IMountable;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.info.glue.GlueService;
import eu.geclipse.info.internal.Activator;

 /**
  * Implementation of the {@link eu.geclipse.core.model.IGridElement}
  * interface for a {@link GlueService}.
  */
 public class GridGlueService
 extends GridGlueElement
 implements IGridService, IMountable {
   
   private static final MountPointID LFC_MOUNT_ID
     = new MountPointID( "LCG File Catalog (LFC)", "lfn" );
   
   private static final MountPointID SRM_MOUNT_ID
     = new MountPointID( "Storage Resource Manager (SRM)", "srm" );

   /**
    * Construct a new <code>GridGlueService</code> for the specified
    * {@link GlueService}.
    * 
    * @param parent The parent of this element.
    * @param glueService The associated glue Service object.
    */
   public GridGlueService( final IGridContainer parent,
                           final GlueService glueService ) {
     super( parent, glueService );
   }


   @Override
   public String getName() {
     GlueService gs=(GlueService) getGlueElement();
     String name = gs.type + " @ "; //$NON-NLS-1$
     URI uri = null;
     try {
       uri = new URI( gs.endpoint );
       name += uri.toString();
     } catch (URISyntaxException e) {
       name += "[Unknown Endpoint]"; //$NON-NLS-1$
     }
     return name;
   }

   /*
    * (non-Javadoc)
    * 
    * @see eu.geclipse.core.model.getURI#getURI()
    */
   public URI getURI() {
     GlueService gs = ( GlueService ) getGlueElement();
     URI uri=null;
     try {
       String endpoint = validateEndpoint( gs.endpoint );
       uri = new URI( endpoint );
       
       //uri = new URI( gs.uri );
     } catch (URISyntaxException e) {
       // Nothing to do, just catch and return null;
     }
     return uri;
   }

   /*
    * (non-Javadoc)
    * 
    * @see eu.geclipse.core.model.IGridResource#getHostName()
    */
   public String getHostName() {
     String str = null;
     URI uriTmp = getURI();
     
     if ( null != uriTmp ) {
       str = uriTmp.getHost();
       
       if ( null == str )
         str = uriTmp.getScheme();
     }

     return str;
   } 
   
   /**
   * @return GlueService
   */
  public GlueService getGlueService(){
    return (GlueService)getGlueElement();
  }
  
  public MountPointID[] getMountPointIDs() {
    
    List< MountPointID > result = new ArrayList< MountPointID >();
    
    if ( isLfcService() ) {
      result.add( LFC_MOUNT_ID );
    }
    
    else if ( isSrmService() ) {
      result.add( SRM_MOUNT_ID );
    }
    
    return result.toArray( new MountPointID[ result.size() ] );
    
  }
  
  public MountPoint getMountPoint( final MountPointID mountID ) {
    
    MountPoint result = null;
    
    if ( SRM_MOUNT_ID.equals( mountID ) ) {
      
      try {
      
        IVirtualOrganization vo = getProject().getVO();
        IGridInfoService infoService = vo.getInfoService();
        IGridResource[] resources = infoService.fetchResources(
            vo,
            vo,
            GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_STORAGE ),
            true,
            IGridStorage.class,
            null );
        
        for ( IGridResource resource : resources ) {
          if ( resource instanceof GridGlueStorage ) {
            GridGlueStorage ggs = ( GridGlueStorage ) resource;
            if ( ggs.getHostName().equals( getHostName() ) ) {
              result = ggs.getSrmMountPoint( this );
            }
          }
        }
        
      } catch ( GridModelException gmExc ) {
        Activator.logException( gmExc );
      }
      
    }
    
    else if ( LFC_MOUNT_ID.equals( mountID ) ) {
      String name = String.format( "lfc @ %s", getURI().toString() );
      String suri = String.format(
          "lfn://%s:5010/grid/%s/",
          getURI(),
          getProject().getVO().getName()
      );
      try {
        URI uri = new URI( suri );
        result = new MountPoint( name, uri );
      } catch ( URISyntaxException uriExc ) {
        Activator.logException( uriExc );
      }
    }
    
    return result;
    
  }
  
  public String getType() {
    return getGlueService().type;
  }
  
  public boolean isLfcService() {
    String type = getType();
    return "lcg-file-catalog".equalsIgnoreCase( type )
      || "lcg-local-file-catalog".equalsIgnoreCase( type );
  }
  
  public boolean isSrmService() {
    return "srm".equalsIgnoreCase( getType() );
  }
  
  private String validateEndpoint( final String endpoint ) {
    String result = endpoint;
    if ( result.endsWith( ":" ) ) { //$NON-NLS-1$
      result = result.substring( 0, result.length() - 1 );
    }
    return result;
  }

}
