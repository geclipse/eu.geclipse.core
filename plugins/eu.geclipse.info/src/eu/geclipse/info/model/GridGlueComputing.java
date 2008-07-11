/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.AbstractGridContainer;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueCE;


/**
 * Implementation of the {@link eu.geclipse.core.model.IGridElement}
 * interface for a {@link GlueCE}.
 */
public class GridGlueComputing
    extends AbstractGridContainer
    implements IGridComputing {
  
  /**
   * The parent of this element.
   */
  private IGridContainer parent;
  
  /**
   * The associated glue table.
   */
  private GlueCE glueTable;
  
  /**
   * Construct a new <code>GridGlueComputing</code> for the specified
   * {@link GlueCE}.
   * 
   * @param parent The parent of this element.
   * @param glueCE The associated glue CE object.
   */
  public GridGlueComputing( final IGridContainer parent,
                               final GlueCE glueCE ) {
    this.parent = parent;
    this.glueTable = glueCE;
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return IGridApplication.class.isAssignableFrom( element.getClass() )
      || ContainerMarker.class.isAssignableFrom( element.getClass() );
  }
  
  /**
   * Convenience method for getting the glue CE.
   * 
   * @return The associated {@link GlueCE} object.
   */
  public GlueCE getGlueCe() {
    return ( GlueCE ) getGlueElement();
  }
  
  /**
   * Get this <code>GridGlueElement</code>'s associated
   * {@link AbstractGlueTable} object.
   * 
   * @return The associated glue object.
   */
  public AbstractGlueTable getGlueElement() {
    return this.glueTable;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }
  
  public String getHostName() {
    String str = null;
    URI uri = getURI();
    
    if ( null != uri ) {
      str = uri.getHost();

      if ( null == str )
        str = uri.getScheme();
    }
    
    return str;
  }
  
  public String getName() {
    GlueCE ce = getGlueCe();
    return "CE @ " + ce.UniqueID; //$NON-NLS-1$
  }
  
  public IGridContainer getParent() {
    return this.parent;
  }
  
  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }
  
  public IResource getResource() {
    return null;
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
  
  public boolean isLazy() {
    return true;
  }
  
  public boolean isLocal() {
    return false;
  }
  
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor )
      throws ProblemException {
    
    IStatus status = Status.OK_STATUS;
    
    IVirtualOrganization vo = getProject().getVO();
    IGridInfoService infoService = vo.getInfoService();
    
    IGridResourceCategory category
      = GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_APPLICATIONS );
    IGridResource[] applications
      = infoService.fetchResources( this, vo, category, false, IGridApplication.class, monitor );
    
    if ( ( applications != null ) && ( applications.length > 0 ) ) {
      for ( IGridResource app : applications ) {
        addElement( app );
      }
    } else {
      addElement( new ContainerMarker( this,
          ContainerMarker.MarkerType.INFO,
          "No matching elements found" ) ); //$NON-NLS-1$
    }
      
    return status;
    
  }

}