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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.internal;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;

public class CategoryContainer {

  private IGridResourceCategory category;
  private List<IGridResource> resources = new ArrayList<IGridResource>();

  public CategoryContainer( final IGridResourceCategory category ) {
    this.category = category;
    this.resources = new ArrayList<IGridResource>();
  }

  public void addResource( final IGridResource resource ) {
    this.resources.add( resource );
  }
  
  public List<IGridResource> getContainedResources(){
    return this.resources;
  }
  
  public IGridResourceCategory getCategory(){
    return this.category;
  }
  
}
