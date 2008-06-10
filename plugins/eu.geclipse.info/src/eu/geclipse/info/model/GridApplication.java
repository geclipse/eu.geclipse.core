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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nikolaos Tsioutsias
 *
 *****************************************************************************/
package eu.geclipse.info.model;

import java.net.URI;
import java.util.ArrayList;

import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.info.glue.GlueLocation;
import eu.geclipse.info.glue.GlueSubClusterSoftwareRunTimeEnvironment;


public class GridApplication extends GridGlueElement implements IGridApplication {

  private URI script = null;
  
  private ArrayList<IGridComputing> computing = new ArrayList<IGridComputing>();
  
  public GridApplication( final IGridContainer parent,
                          final GlueLocation software ) {
    super( parent, software );
  }
  
  public void addComputing(final IGridComputing computing)
  {
    if (computing != null)
      this.computing.add( computing );
  }
  
  public IGridComputing[] getComputing() {
    return this.computing.toArray( new IGridComputing[this.computing.size()] );
  }

  public URI getScript() {
    return this.script;
  }

  public String getTag() {
    String tag = null;
    if ( getGlueElement() != null && getGlueElement() instanceof GlueLocation )
      tag = ( ( GlueLocation ) getGlueElement() ).locationPath;
    
    return tag;
  }

  public void setScript( final URI script ) {
    this.script = script;
    
  }

  public String getHostName() {
    String result = null;
    if ( getGlueElement() != null && getGlueElement() instanceof GlueLocation )
      result = ( ( GlueLocation ) getGlueElement() ).subCluster.UniqueID;
    
    return result;
  }

  public URI getURI() {
    // TODO Auto-generated method stub
    return null;
  }

}
