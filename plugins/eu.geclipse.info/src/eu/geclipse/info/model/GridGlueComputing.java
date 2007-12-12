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
 *****************************************************************************/

package eu.geclipse.info.model;

import java.net.URI;
import java.net.URISyntaxException;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.info.glue.GlueCE;

/**
 * Implementation of the {@link eu.geclipse.core.model.IGridElement}
 * interface for a {@link GlueCE}.
 */
public class GridGlueComputing
    extends GridGlueElement
    implements IGridComputing {
  
  /**
   * Construct a new <code>GridGlueComputing</code> for the specified
   * {@link GlueCE}.
   * 
   * @param parent The parent of this element.
   * @param glueCE The associated glue CE object.
   */
  public GridGlueComputing( final IGridContainer parent,
                            final GlueCE glueCE ) {
    super( parent, glueCE );
  }
  
  /**
   * Convenience method for getting the glue CE.
   * 
   * @return The associated {@link GlueCE} object.
   */
  public GlueCE getGlueCe() {
    return ( GlueCE ) getGlueElement();
  }
  
  @Override
  public String getName() {
    GlueCE ce = getGlueCe();
    return "CE @ " + ce.UniqueID; //$NON-NLS-1$
  }

  public URI getURI() {
    URI uri = null;
    try {
      uri = new URI( getGlueCe().UniqueID );
    } catch ( URISyntaxException uriExc ) {
      // Nothing to do, just catch and return null
    }
    return uri;
  }
  
}
