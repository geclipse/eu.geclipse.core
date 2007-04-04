/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     PSNC - Mariusz Wojtysiak
 *           
 *****************************************************************************/
package eu.geclipse.ui.properties;

import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.voms.vo.VomsVirtualOrganization;


/**
 * Properties for {@link IVirtualOrganization}
 *
 */
 class VOPropertySource extends AbstractPropertySource {
  IVirtualOrganization virtualOrganization;
  
  /**
   * @param virtualOrganization - vo for which properties be displayed
   */
  public VOPropertySource( final IVirtualOrganization virtualOrganization ) {
    super();
    this.virtualOrganization = virtualOrganization;
  }

  @Override
  protected IProperty[] createProperties()
  {
    IProperty[] properties = new IProperty[] {
      createName(),
      createType()      
    };
    
    VomsVirtualOrganization voms = getVomsVO();
    
    if( voms != null ) {
      IProperty[] vomsProperties = new VomsPropertySource( voms ).getProperties();
      properties = joinProperties( properties, vomsProperties );
    }

    return properties;
  }
  
  VomsVirtualOrganization getVomsVO() {
    VomsVirtualOrganization voms = null;
    if( this.virtualOrganization instanceof VomsVirtualOrganization ) {
      voms = ( VomsVirtualOrganization )this.virtualOrganization;
    } else if( this.virtualOrganization instanceof IWrappedElement ) {
      IWrappedElement wrapper = ( IWrappedElement ) this.virtualOrganization;      
      
      if( wrapper.getWrappedElement() instanceof VomsVirtualOrganization ) {
        voms = ( VomsVirtualOrganization ) wrapper.getWrappedElement();
      }
    }  
    
    return voms;
  }
  
  private IProperty createName() {
    return new AbstractProperty( Messages.getString("VOPropertySource.propertyName"),   //$NON-NLS-1$
                                 Messages.getString("VOPropertySource.categoryGeneral") ) { //$NON-NLS-1$
      public String getValue() {
        return VOPropertySource.this.virtualOrganization.getName();
      }
    };
  }
    
  private IProperty createType() {
    return new AbstractProperty( Messages.getString("VOPropertySource.propertyType"),   //$NON-NLS-1$ 
                                 Messages.getString("VOPropertySource.categoryGeneral") ) { //$NON-NLS-1$
      public String getValue() {
        return VOPropertySource.this.virtualOrganization.getTypeName();
      }
    }; 
  }
  
}
