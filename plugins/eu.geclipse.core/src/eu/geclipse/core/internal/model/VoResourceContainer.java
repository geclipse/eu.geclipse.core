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

package eu.geclipse.core.internal.model;

import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;

/**
 * This class represents a virtual grid container that holds
 * VO-specific information. This information is either fetched
 * from the {@link IGridInfoService} of the specified VO or from
 * the VO itself. The type of information the container holds is
 * determined by the type of the container
 * (i.e. {@link ResourceType}). 
 */
public class VoResourceContainer
    extends VirtualGridContainer {
  
  /**
   * This field determines the type of information that is contained
   * in this container.
   */
  public static enum ResourceType {
    
    /**
     * Computing element type.
     */
    Computing,
    
    /**
     * Storage element type. 
     */
    Storage,
    
    /**
     * Service type. 
     */
    Service
    
  }
  
  /**
   * The type of this container.
   */
  private ResourceType type;
  
  private IVirtualOrganization vo;
  
  /**
   * Construct a new <code>VoResourceContainer</code> of the
   * specified type and for the specified VO.
   * 
   * @param parent The VO this container belongs to.
   * 
   * @param type The type of this container.
   */
  protected VoResourceContainer( final IGridContainer parent,
                                 final IVirtualOrganization vo,
                                 final ResourceType type ) {
    super( parent, getName( type ) );
    this.vo = vo;
    this.type = type;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    boolean result = false;
    if ( this.type == ResourceType.Computing ) {
      result = element instanceof IGridComputing;
    } else if ( this.type == ResourceType.Service ) {
      result = element instanceof IGridService;
    } else if ( this.type == ResourceType.Storage ) {
      result = element instanceof IGridStorage;
    }
    return result;
  }
  
  /**
   * Get the type of this container.
   * 
   * @return The type of this container. This determines the
   * information that is contained in the container.
   */
  public ResourceType getType() {
    return this.type;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#isLocal()
   */
  public boolean isLocal() {
    return false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#isVirtual()
   */
  @Override
  public boolean isVirtual() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridContainer#fetchChildren()
   */
  @Override
  protected boolean fetchChildren( final IProgressMonitor monitor )
      throws GridModelException {
    
    IGridElement[] children = null;
    
    // Fetch the information
    IGridInfoService infoService = getVo().getInfoService();
    /*if ( infoService != null ) {
      if ( this.type == ResourceType.Service ) {
        children = infoService.fetchServices( this, this.vo, monitor );
      } else if ( this.type == ResourceType.Computing ) {
        children = infoService.fetchComputing( this, this.vo, monitor );
      } else if ( this.type == ResourceType.Storage ) {
        children = infoService.fetchStorage( this, this.vo, monitor );
      } 
    } else */if ( this.type == ResourceType.Service ) {
      children = getVo().getServices();
    } else if ( this.type == ResourceType.Computing ) {
      children = getVo().getComputing();
    } else if ( this.type == ResourceType.Storage ) {
      children = getVo().getStorage();
    }
    
    // Add children to the container
    if ( children != null ) {
      lock();
      try {
        for ( IGridElement child : children ) {
          addElement( child );
        }
      } finally {
        unlock();
      }
    }
    
    return true;
    
  }
  
  /**
   * Convenience method to get the VO.
   * 
   * @return The casted parent of this container.
   */
  protected IVirtualOrganization getVo() {
    return this.vo;
  }
  
  /**
   * Generate a name out of the type.
   * 
   * @param type The type.
   * @return A name that denotes the type.
   */
  private static String getName( final ResourceType type ) {
    // TODO mathias internationalization
    String name = null;
    switch ( type ) {
      case Computing:
        name = "Computing"; //$NON-NLS-1$
        break;
      case Storage:
        name = "Storage"; //$NON-NLS-1$
        break;
      case Service:
        name = "Services"; //$NON-NLS-1$
        break;
    }
    return name;
  }
  
}
