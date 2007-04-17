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
	  return (gs.Endpoint!=null)?gs.Endpoint:gs.getID()+" (endpoint missing)";
  }


  public URI getURI() {
	  GlueService gs=(GlueService) getGlueElement();
	  URI uri=null;
	  try {
		  uri=new URI(gs.Endpoint);
	  } catch (URISyntaxException e) {
		  uri=null;
	  }
	  return uri;
  }

  public GlueService getGlueService(){
	  return (GlueService)getGlueElement();
  }
}
