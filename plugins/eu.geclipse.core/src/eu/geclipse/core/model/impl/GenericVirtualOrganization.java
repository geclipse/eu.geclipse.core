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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IVirtualOrganization;

/**
 * This is the g-Eclipse default implementation for the
 * {@link IVirtualOrganization} interface. It is used whenever
 * no middleware dependent VO is available. This be
 * the case if the user has not installed a middleware feature that
 * comes with an own VO implementation. 
 */
public class GenericVirtualOrganization
    extends AbstractVirtualOrganization {
  
  /**
   * The type name of this VO implementation
   */
  private static final String VO_TYPE_NAME = "Generic VO"; //$NON-NLS-1$
  
  private String name;
  
  GenericVirtualOrganization( final GenericVoCreator creator ) {
    try {
      apply( creator );
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
  }
  
  GenericVirtualOrganization( final IFileStore fileStore ) {
    this.name = fileStore.getName();
    try {
      load();
    } catch( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractVirtualOrganization#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return
      super.canContain( element )
      || ( element instanceof GenericVoProperties );
  }
  
  @Override
  public boolean equals( final Object o ) {
    boolean result = false;
    if ( o instanceof GenericVirtualOrganization ) {
      GenericVirtualOrganization vo = ( GenericVirtualOrganization ) o;
      result = getName().equals( vo.getName() );
    }
    return result;
  }
  
  /*
   * If equals() is overridden hashCode() must be also, equal objects
   * must have equal hashes.
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
  
  @Override
  public IGridService[] getServices( final IProgressMonitor monitor )
      throws GridModelException {
    
    List< IGridService > results = new ArrayList< IGridService >();
    
    IGridService[] services = super.getServices( monitor );
    if ( services != null ) {
      for ( IGridService service : services ) {
        results.add( service );
      }
    }
    
    IGridElement[] children = getChildren( null );
    if ( children != null ) {
      for ( IGridElement child : children ) {
        if ( child instanceof IGridService ) {
          results.add( ( IGridService ) child );
        }
      }
    }
    
    return results.toArray( new IGridService[ results.size() ] );
    
  }
  
  public String getTypeName() {
    return VO_TYPE_NAME;
  }

  public boolean isLazy() {
    return false;
  }

  public String getName() {
    return this.name;
  }
  
  @Override
  public void save() throws GridModelException {
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridService ) {
        // Do nothing, services are handled by the GenericVoProperties
      } else if ( child instanceof IStorableElement ) {
        ( ( IStorableElement ) child ).save();
      } else {
        saveChild( child );
      }
    }
  }
  
  /**
   * Apply the settings of the specified {@link GenericVoCreator} to
   * this VO.
   * 
   * @param creator The creator to be applied to this VO.
   * @throws GridModelException 
   */
  protected void apply( final GenericVoCreator creator ) throws GridModelException {
    
    this.name = creator.getVoName();
    
    GenericVoProperties properties = new GenericVoProperties( this );
    addElement( properties );
    
    creator.apply( this );
    
  }
  
  @Override
  protected IGridElement loadChild( final String childName ) {
    
    IGridElement child = null;
    
    try {
    
      if ( childName.equals( GenericVoProperties.NAME ) ) {
        GenericVoProperties properties = new GenericVoProperties( this );
        properties.load();
        child = properties;
      }
    
    } catch( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
    
    return child;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getWizardId()
   */
  public String getWizardId() {
    return "eu.geclipse.ui.wizards.GenericVoWizard"; //$NON-NLS-1$
  }
  
}
