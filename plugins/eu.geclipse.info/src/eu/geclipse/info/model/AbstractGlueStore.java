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

import java.util.ArrayList;

import eu.geclipse.core.model.IVirtualOrganization;

/**
 * This is an abstract class that stores all the glue elements. The elements are
 * fetched by the info services.
 * @author tnikos
 */
public abstract class AbstractGlueStore{

  /**
   * The constructor of the GlueStore. The method setVoList is called.
   * @param voList An ArrayList of IVirtualOrganization
   */
  public AbstractGlueStore( final ArrayList<IVirtualOrganization> voList ) {
    setVoList( voList );
  }

  /**
   * This is an abstract method that must be implemented 
   * @param voList
   */
  public abstract void setVoList( final ArrayList<IVirtualOrganization> voList );
  
}