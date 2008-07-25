/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.info.glue.GlueSA;
import eu.geclipse.info.glue.GlueSE;
import eu.geclipse.info.glue.GlueSEAccessProtocol;


/**
 * Implementation of the {@link eu.geclipse.core.model.IGridElement} interface
 * for a {@link GlueSE}.
 */
public class GridGlueStorage extends GridGlueElement implements IGridStorage {

  /**
   * Construct a new <code>GridGlueStorage</code> for the specified
   * {@link GlueSE}.
   * 
   * @param parent The parent of this element.
   * @param glueSE The associated glue SE object.
   */
  public GridGlueStorage( final IGridContainer parent, final GlueSE glueSE ) {
    super( parent, glueSE );
  }


  /**
   * Convenience method for getting the glue SE.
   * 
   * @return The associated {@link GlueSE} object.
   */
  public GlueSE getGlueSe() {
    return ( GlueSE )getGlueElement();
  }

  @Override
  public String getName() {
    GlueSE se = getGlueSe();
    return "SE @ " + se.UniqueID; //$NON-NLS-1$
  }

  protected IVirtualOrganization getVo() {
    IVirtualOrganization result = null;
    IGridProject project = getProject();
    if( project != null ) {
      result = project.getVO();
    } else {
      IGridContainer parent = getParent();
      while( parent != null ) {
        if( parent instanceof IVirtualOrganization ) {
          result = ( IVirtualOrganization )parent;
          break;
        }
        parent = parent.getParent();
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */
  public URI getURI() {
    URI uri = null;
    try {
      uri = new URI( getGlueSe().UniqueID );
    } catch( URISyntaxException e ) {
      // Nothing to do, just catch and return null
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
    URI uri = getURI();
    if( null != uri ) {
      str = uri.getHost();
      if( null == str )
        str = uri.getPath();
    }
    return str;
  }

  public MountPoint getMountPoint( final MountPointID mountID ) {
    
    MountPoint result = null;
    
    List< GlueSEAccessProtocol > apList = getGlueSe().glueSEAccessProtocolList;
    
    if( apList != null ) {
      for ( GlueSEAccessProtocol ap : apList ) {
        String uid = getAccessProtocolUID( ap );
        if ( mountID.getUID().equals( uid ) ) {
          result = getMountPoint( ap );
          break;
        }
      }
    }
    
    return result;
    
  }

  public MountPointID[] getMountPointIDs() {

    List< MountPointID > mountIDs = new ArrayList< MountPointID >();
    List< GlueSEAccessProtocol > apList = getGlueSe().glueSEAccessProtocolList;

    if( apList != null ) {
      for ( GlueSEAccessProtocol ap : apList ) {
        String uid = getAccessProtocolUID( ap );
        String scheme = getAccessProtocolScheme( ap );
        MountPointID mountID = new MountPointID( uid, scheme );
        mountIDs.add( mountID );
      }
    }

    return mountIDs.toArray( new MountPointID[ mountIDs.size() ] );
    
  }
  
  protected MountPoint getSrmMountPoint( final GridGlueService srmService ) {
    
    MountPoint result = null;
    
    ArrayList< GlueSEAccessProtocol > apList = getGlueSe().glueSEAccessProtocolList;
    if( apList != null ) {
      for ( GlueSEAccessProtocol ap : apList ) {
        String scheme = getAccessProtocolScheme( ap );
        if ( "srm".equals( scheme ) ) { //$NON-NLS-1$
          result = getMountPoint( ap );
          break;
        }
      }
    }
    
    return result;
    
  }
  
  private String getAccessProtocolUID( final GlueSEAccessProtocol ap ) {
    String scheme = getAccessProtocolScheme( ap );
    return String.format( "%s:%d", scheme, ap.Port ); //$NON-NLS-1$
  }
  
  private String getAccessProtocolScheme( final GlueSEAccessProtocol ap ) {
    return ap.Type.toLowerCase();
  }
  
  private MountPoint getMountPoint( final GlueSEAccessProtocol ap ) {
    
    MountPoint result = null;
    
    String scheme = getAccessProtocolScheme( ap );
    String host = getGlueSe().UniqueID;
    int port = ap.Port.intValue();
    String path = getMountPath();
    
    try {
      URI uri = new URI( scheme, null, host, port, path, null, null );
      String name = String.format( "%s @ %s.%d", scheme, host, port ); //$NON-NLS-1$
      result = new MountPoint( name, uri );
    } catch ( URISyntaxException uriExc ) {
      // Just catch and ignore
    }
    
    return result;
    
  }
  
  private String getMountPath() {
   
    String path = null;
    IVirtualOrganization vo = getVo();

    for ( GlueSA sa : getGlueSe().glueSAList ) {
      if( ( vo == null ) || GlueQuery.saSupportsVO( sa, vo.getName() ) ) {
        path = sa.Path;
        if ( ! path.endsWith( "/" ) ) { //$NON-NLS-1$
          path += "/"; //$NON-NLS-1$
        }
        break;
      }
    }
    
    return path;
    
  }

}
