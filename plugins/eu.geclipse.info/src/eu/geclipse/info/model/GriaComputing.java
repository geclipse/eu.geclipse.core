/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    - UCY (www.cs.ucy.ac.cy)
 *      Nikolaos Tsioutsias
 *****************************************************************************/

package eu.geclipse.info.model;

import java.net.URI;
import java.net.URISyntaxException;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.info.glue.GlueService;

/**
 * The GriaComputing class is used to represent a gria application. A gria application is
 * described using a GlueService object.
 * @author tnikos
 * @see eu.geclipse.info.glue.GlueService
 */
public class GriaComputing extends GridGlueElement implements IGridComputing {

  /**
   * The constructor of a GriaComputing that symbolizes a gria application
   * @param parent the parent container
   * @param glueService a GlueService with the information for a gria application.
   */
  public GriaComputing( final IGridContainer parent,
                        final GlueService glueService ) {
    super( parent, glueService );
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */
  public URI getURI() {
    URI uri = null;
    try {
      uri = new URI( getGlueService().uri );
    } catch ( URISyntaxException uriExc ) {
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
    // This is not a physical machine, but the application name
    return null;
  }
  
  /**
   * Returns the glueservice that was passed in the constructor
   * @return a GlueService object or <code>null</code>
   */
  public GlueService getGlueService() {
    return ( GlueService ) getGlueElement();
  }
  
  /**
   * Get the name of the computing
   */
  @Override
  public String getName() {
    return getGlueService().name;
  }
}
