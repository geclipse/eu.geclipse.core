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

 import eu.geclipse.core.model.IGridContainer;
 import eu.geclipse.core.model.IGridService;
import eu.geclipse.info.glue.GlueService;

 /**
  * Implementation of the {@link eu.geclipse.core.model.IGridElement}
  * interface for a {@link GlueService}.
  */
 public class GridGlueService
 extends GridGlueElement
 implements IGridService {

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
  
  private String validateEndpoint( final String endpoint ) {
    String result = endpoint;
    if ( result.endsWith( ":" ) ) { //$NON-NLS-1$
      result = result.substring( 0, result.length() - 1 );
    }
    return result;
  }
  
}
