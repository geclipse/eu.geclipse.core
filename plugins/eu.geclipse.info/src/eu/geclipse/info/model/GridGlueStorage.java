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
import java.util.List;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridStorage;
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
    
    URI[] result = null;
    //TODO: now we get the first Storage area only,
    //      also we should check the GlueSAAccessControlBaseRule
    
    String path=getGlueSe().glueSAList.get(0).Path;
    
    try {
      String host = getName();
      Object field
        = getGlueSe().getFieldByName( "glueSEAccessProtocolList" );
      List< GlueSEAccessProtocol > list
        = ( List< GlueSEAccessProtocol > ) field;
      if ( ( list != null ) && !list.isEmpty() ) {
        result = new URI[ list.size() ];
        for ( int i = 0 ; i < list.size() ; i++ ) {
          String scheme
            = ( String ) list.get( i ).getFieldByName( "Type" );
          Long port
            = ( Long ) list.get( i ).getFieldByName( "Port" );
          
          //TODO: There seems to be a problem with  DPM/GridFTP
          //I set the path to null otherwise it just doesn't work
          //path=null;
          result[ i ] = new URI( scheme, null, host, port.intValue(), path, null, null );
          
        }
      }
    } catch( RuntimeException e ) {
      // Just catch and do nothing here
    } catch( IllegalAccessException e ) {
      // Just catch and do nothing here
    } catch( NoSuchFieldException e ) {
      // Just catch and do nothing here
    } catch( URISyntaxException e ) {
      // Just catch and do nothing here
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
