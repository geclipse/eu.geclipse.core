/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/
package eu.geclipse.info.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridApplicationParameters;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.info.glue.GlueService;


/**
 * @author tnikos
 *
 */
public class GridGlueServiceApplication extends GridGlueElement
  implements IGridApplication
{
  private ArrayList<IGridComputing> computing = new ArrayList<IGridComputing>();
  /**
   * @param parent the parent of the element
   * @param glueService the GlueService that describes the GRIA application
   */
  public GridGlueServiceApplication( final IGridContainer parent,
                                     final GlueService glueService )
  {
    super( parent, glueService );
  }

  /**
   * Add a computing resource
   * @param myComputing an IGridComputing where this application is installed
   */
  public void addComputing(final IGridComputing myComputing)
  {
    if (myComputing != null)
      this.computing.add( myComputing );
  }
  
  public IGridComputing[] getComputing() {
    return this.computing.toArray( new IGridComputing[this.computing.size()] );
  }

  public URI getScript() {
    // TODO do nothing
    return null;
  }

  public String getTag() {
    // TODO do nothing
    return null;
  }

  public void setScript( final URI script ) {
    // do nothing
  }

  public String getHostName() {
    String str = null;
    URI myURI = null;
    try {
      myURI = new URI(this.getGlueApplication().endpoint);
      str = myURI.getHost();
    } catch( URISyntaxException e ) {
      //do nothing
    }
    return str;
  }

  public URI getURI() {
    URI uri = null;
    try {
      uri = new URI( getGlueApplication().uri );
    } catch ( URISyntaxException uriExc ) {
      // Nothing to do, just catch and return null
    }
    return uri;
  }
  
  /**
   * Returns the GlueService that was passed in the constructor
   * @return a GlueService object or <code>null</code>
   */
  public GlueService getGlueApplication() {
    return ( GlueService ) getGlueElement();
  }
  
  /**
   * Get the name of the computing
   */
  @Override
  public String getName() {
    return getGlueApplication().name;
  }

  public IGridApplicationParameters getApplicationParameters() {
    // TODO Auto-generated method stub
    return null;
  }
}