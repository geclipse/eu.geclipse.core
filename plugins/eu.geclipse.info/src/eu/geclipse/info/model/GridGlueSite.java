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
 *    Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/

package eu.geclipse.info.model;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueCE;
import eu.geclipse.info.glue.GlueSite;

/**
 * @author tnikos
 *
 */
public class GridGlueSite implements IGridResource {

  /**
   * The parent of this element.
   */
  private IGridContainer parent;
  
  /**
   * The associated glue table.
   */
  private GlueSite glueTable;
  
  /**
   * Create a GridGlueSite
   * @param parent The parent of this element.
   * @param glueSite The associated glue site object.
   */
  public GridGlueSite( final IGridContainer parent,
                       final GlueSite glueSite ) {
    this.parent = parent;
    this.glueTable = glueSite;
  }
  
  /**
   * Convenience method for getting the glue Site.
   * 
   * @return The associated {@link GlueCE} object.
   */
  public GlueSite getGlueSite() {
    return ( GlueSite ) getGlueElement();
  }
  
  /**
   * Get the associated glue object.
   * 
   * @return The associated glue object.
   */
  public AbstractGlueTable getGlueElement() {
    return this.glueTable;
  }
  
  public void dispose() {
    // do nothing
  }

  public IFileStore getFileStore() {
    IFileStore result = null;
    if ( getParent()!= null )
      result = getParent().getFileStore().getChild( getName() );
    
    return result;
  }

  public String getName() {
    String result = getGlueSite().getDisplayName();
    return result;
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    IPath result = null;
    if ( getParent()!= null )
      result = getParent().getPath().append( getName() );
    
    return result;
  }

  public IGridProject getProject() {
    IGridProject result = null;
    
    if ( getParent()!= null )
      result = getParent().getProject();
    
    return result;
  }

  public IResource getResource() {
    return null;
  }

  public boolean isHidden() {
    return false;
  }

  public boolean isLocal() {
    return false;
  }

  public boolean isVirtual() {
    return false;
  }

  public String getHostName() {
    String result = getGlueSite().getDisplayName();
    return result;
  }

  public URI getURI() {
    return null;
  }

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    return null;
  }
}
