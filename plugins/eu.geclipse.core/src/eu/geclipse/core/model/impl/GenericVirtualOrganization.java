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

package eu.geclipse.core.model.impl;

import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
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
  
  public String getTypeName() {
    return VO_TYPE_NAME;
  }

  public boolean isLazy() {
    return false;
  }

  public String getName() {
    return this.name;
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
