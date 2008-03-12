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

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.info.glue.GlueSA;
import eu.geclipse.info.glue.GlueSE;
import eu.geclipse.info.glue.GlueSEAccessProtocol;

/**
 * Implementation of the {@link eu.geclipse.core.model.IGridElement}
 * interface for a {@link GlueSE}.
 */
public class GridGlueStorage
    extends GridGlueElement
    implements IGridStorage {

  /**
   * Construct a new <code>GridGlueStorage</code> for the specified
   * {@link GlueSE}.
   * 
   * @param parent The parent of this element.
   * @param glueSE The associated glue SE object.
   */
  public GridGlueStorage( final IGridContainer parent,
                          final GlueSE glueSE ) {
    super( parent, glueSE );
  }
  
  public URI[] getAccessTokens() {
    
    ArrayList<URI> uriList=new ArrayList<URI>();
    IVirtualOrganization vo = getVo();

    for( GlueSA sa : getGlueSe().glueSAList ) {
      if( ( vo == null ) || GlueQuery.saSupportsVO( sa, vo.getName() )){
        try {
          String host = getGlueSe().UniqueID;
          List<GlueSEAccessProtocol> list=getGlueSe().glueSEAccessProtocolList;
          if ( ( list != null ) && !list.isEmpty() ) {
            int protocolCount=list.size();
            for( int i = 0; i < protocolCount; i++ ) {
              GlueSEAccessProtocol ap=getGlueSe().glueSEAccessProtocolList.get( i );
              String scheme=null;
              if(ap.Type.startsWith( "srm" ) ){ //$NON-NLS-1$
                String[] vNumbers=ap.Version.split( "\\." ); //$NON-NLS-1$
                StringBuilder sBuilder=new StringBuilder("srm-v"); //$NON-NLS-1$
                for( String n : vNumbers ) {
                  sBuilder.append( n );
                }
                scheme=sBuilder.toString();
              }else{
                scheme=ap.Type;
              }
              
              String newPath =  sa.Path.endsWith( "/" ) ? sa.Path : sa.Path + "/"; //$NON-NLS-1$ //$NON-NLS-2$
              uriList.add(new URI( scheme, null, host, 
                                   ap.Port.intValue(), newPath,
                                   null, null ));
            }
          }        
        } catch( RuntimeException e ) {
          // Just catch and do nothing here
        } catch( URISyntaxException e ) {
          // Just catch and do nothing here
        }
      }
    }
    URI[] result = new URI[uriList.size()];
    int i=0;
    for( URI uri : uriList ) {
      result[i]=uri;
      ++i;
    }
    return result;
    
  }
  
  /**
   * Convenience method for getting the glue SE.
   * 
   * @return The associated {@link GlueSE} object.
   */
  public GlueSE getGlueSe() {
    return ( GlueSE ) getGlueElement();
  }
  
  @Override
  public String getName() {
    GlueSE se = getGlueSe();
    return "SE @ " + se.UniqueID; //$NON-NLS-1$
  }
  
  protected IVirtualOrganization getVo() {
    
    IVirtualOrganization result = null;
    IGridProject project = getProject();
    
    if ( project != null ) {
      result = project.getVO();
    } else {
      IGridContainer parent = getParent();
      while ( parent != null ) {
        if ( parent instanceof IVirtualOrganization ) {
          result = ( IVirtualOrganization ) parent;
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
    } catch (URISyntaxException e) {
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
    
    if ( null != uri ) {
      str = uri.getHost();

      if ( null == str )
        str = uri.getPath();
    }
    
    return str;
  }  
}
