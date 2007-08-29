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
 *****************************************************************************/package eu.geclipse.info.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridStorage;
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

    for( GlueSA sa : getGlueSe().glueSAList ) {
      if(GlueQuery.saSupportsVO( sa, this.getProject().getVO().getName() )){
        try {
          String host = getName();
          List list=getGlueSe().glueSEAccessProtocolList;
          if ( ( list != null ) && !list.isEmpty() ) {
            int protocolCount=list.size();
            for( int i = 0; i < protocolCount; i++ ) {
              GlueSEAccessProtocol ap=getGlueSe().glueSEAccessProtocolList.get( i );
              String scheme=null;
              if(ap.Type.startsWith( "srm" ) ){
                String[] vNumbers=ap.Version.split( "\\." );
                StringBuilder sBuilder=new StringBuilder("srm-v");
                for( String n : vNumbers ) {
                  sBuilder.append( n );
                }
                scheme=sBuilder.toString();
              }else{
                scheme=ap.Type;
              }
              
              String newPath =  sa.Path.endsWith( "/" ) ? sa.Path : sa.Path + "/";
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
  
}
