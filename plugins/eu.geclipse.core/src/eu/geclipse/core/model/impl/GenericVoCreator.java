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

package eu.geclipse.core.model.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IStorableElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Grid element creator for the {@link GenericVirtualOrganization}.
 */
public class GenericVoCreator
    implements IStorableElementCreator {
  
  /**
   * The creators extension ID.
   */
  private static final String EXTENSION_ID = "eu.geclipse.core.genericVoCreator"; //$NON-NLS-1$
  
  /**
   * The object from which to create the VO.
   */
  private Object object;
  
  private String voName;
  
  private List< IGridElementCreator > serviceCreators
  = new ArrayList< IGridElementCreator >();
  
  private List< IGridService > serviceMaintainers
    = new ArrayList< IGridService >();
  
  public void createService( final IGridElementCreator creator,
                              final URI fromURI )
      throws ProblemException {
    
    if ( ! creator.canCreate( IGridService.class ) ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED, "Cannot create a service from the specified creator" );
    }
    
    if ( ! creator.canCreate( fromURI ) ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED, "Cannot create a service from the specified URI" );
    }
    
    this.serviceCreators.add( creator );
    
  }
  
  public void maintainService( final IGridService service ) {
    this.serviceMaintainers.add( service );
  }
  
  /**
   * Apply this creators settings to the specified VO.
   * 
   * @param vo The {@link GenericVirtualOrganization} to which to
   * apply this creators settings.
   * @throws GridModelException 
   */
  public void apply( final GenericVirtualOrganization vo ) throws GridModelException {
    
    IGridElement[] children = vo.getChildren( null );
    
    for ( IGridElement child : children ) {
      if ( ( child instanceof IGridService ) && ! this.serviceMaintainers.contains( child ) ) {
        vo.delete( child );
      }
    }
    
    for ( IGridElementCreator serviceCreator : this.serviceCreators ) {
      vo.create( serviceCreator );
    }

  }

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return elementType.isAssignableFrom( GenericVirtualOrganization.class );
  }

  public boolean canCreate( final Object fromObject ) {
    return false;
  }
  
  public boolean canCreate( final IFileStore fromFileStore ) {
    IFileStore propertiesStore = fromFileStore.getChild( GenericVoProperties.NAME );
    IFileInfo propertiesInfo = propertiesStore.fetchInfo();
    boolean result = propertiesInfo.exists();
    if ( result ) {
      this.object = fromFileStore;
    }
    return result;
  }

  public IGridElement create( final IGridContainer parent ) throws GridModelException {
    GenericVirtualOrganization vo = null;
    if ( this.object == null ) {
      vo = new GenericVirtualOrganization( this );
    } else if ( this.object instanceof IFileStore ) {
      IFileStore fileStore = ( IFileStore ) this.object;
      vo = new GenericVirtualOrganization( fileStore );
    }
    return vo;
  }
  
  public String getExtensionID() {
    return EXTENSION_ID;
  }

  public Object getObject() {
    return this.object;
  }
  
  /**
   * Get the VO's name.
   * 
   * @return The unique name of the VO.
   */
  public String getVoName() {
    return this.voName;
  }
  
  /**
   * Set the VO's name.
   * 
   * @param name The unique name of the VO.
   */
  public void setVoName( final String name ) {
    this.voName = name;
  }

}
