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

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.voms.vo.VomsVirtualOrganization;
import eu.geclipse.voms.vo.VomsVoProperties;


/**
 * Properties for {@link VomsVirtualOrganization}
 *
 */
public class VomsPropertySource extends AbstractPropertySource {
  
  VomsVirtualOrganization voms;

  /**
   * @param voms - voms which properties will be displayed
   */
  VomsPropertySource( final VomsVirtualOrganization voms ) {
    super();
    this.voms = voms;
  }

  @Override
  protected IProperty[] createProperties()
  {    
    return new IProperty[] {
      createHost(),
      createHostDN(),
      createPort()
    };
  }
    
  private IProperty createHost() {
    return new AbstractProperty( Messages.getString("VomsPropertySource.propertyHost"),     //$NON-NLS-1$
                                 Messages.getString("VomsPropertySource.categoryHost") ) {  //$NON-NLS-1$

      public String getValue() {
        String valueString = null;
        
        try {
          VomsVoProperties vomsProperties = VomsPropertySource.this.voms.getProperties();
          
          if( vomsProperties != null ) {
            valueString = vomsProperties.getHost();
          }
        } catch( GridModelException e ) {
          valueString = e.getMessage();
        }
        
        return valueString;
      }
      
    };
  }
  
  private IProperty createHostDN() {
    return new AbstractProperty( Messages.getString("VomsPropertySource.propertyHostDN"),   //$NON-NLS-1$ 
                                 Messages.getString("VomsPropertySource.categoryHost") ) {  //$NON-NLS-1$

      public String getValue() {
        String valueString = null;
        
        try {
          VomsVoProperties vomsProperties = VomsPropertySource.this.voms.getProperties();
          
          if( vomsProperties != null ) {
            valueString = vomsProperties.getHostDN();
          }
        } catch( GridModelException e ) {
          valueString = e.getMessage();
        }
        
        return valueString;
      }
    };
  }
  
  private IProperty createPort() {
    return new AbstractProperty( Messages.getString("VomsPropertySource.propertyPort"),     //$NON-NLS-1$
                                 Messages.getString("VomsPropertySource.categoryHost") ) {  //$NON-NLS-1$

      public String getValue() {
        String valueString = null;
        
        try {
          VomsVoProperties vomsProperties = VomsPropertySource.this.voms.getProperties();
          
          if( vomsProperties != null ) {
            valueString = String.valueOf( vomsProperties.getPort() );
          }
        } catch( GridModelException e ) {
          valueString = e.getMessage();
        }
        
        return valueString;
      }
    };
  }
  
}
