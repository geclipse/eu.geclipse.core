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

import eu.geclipse.core.model.IGridConnection;


/**
 * Properties for {@link IGridConnection}
 *
 */
public class ConnectionPropertySource extends AbstractPropertySource {
  protected IGridConnection gridConnection;
  
  /**
   * @param gridConnection - connection, for which properties will be shown
   */
  public ConnectionPropertySource( final IGridConnection gridConnection ) {
    super();

    this.gridConnection = gridConnection;
  }
    
  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#createPropertyDescriptors()
   */
  @Override
  protected IProperty[] createProperties()
  {
    return new IProperty[] {
      createPropConnType(),
      createPropHost(),
      createPropPath(),
      createPropPort()      
      };
  }
  
  private IProperty createPropConnType() {
    return new AbstractProperty( Messages.getString("ConnectionPropertySource.propertyConnectionType"), null ) { //$NON-NLS-1$
      public String getValue() {
        String typeString = null;
        if( ConnectionPropertySource.this.gridConnection.getURI().getScheme() != null ) {
          typeString = ConnectionPropertySource.this.gridConnection.getURI().getScheme().toString();
        }
        return typeString;
      }
    };
  }
  
  private IProperty createPropHost() {
    return new AbstractProperty( Messages.getString("ConnectionPropertySource.propertyHost"), null ) { //$NON-NLS-1$
      public String getValue() {
        return ConnectionPropertySource.this.gridConnection.getURI().getHost();
      }
    };
  }
  
  private IProperty createPropPath() {
    return new AbstractProperty( Messages.getString("ConnectionPropertySource.propertyPath"), null ) { //$NON-NLS-1$
      public String getValue() {
        return ConnectionPropertySource.this.gridConnection.getURI().getPath();
      }
    };
  }

  private IProperty createPropPort() {
    return new AbstractProperty( Messages.getString("ConnectionPropertySource.propertyPort"), null ) { //$NON-NLS-1$
      public String getValue() {
        String portString = null;
        
        if( ConnectionPropertySource.this.gridConnection.getURI().getPort() != -1 ) {
          portString = String.valueOf( ConnectionPropertySource.this.gridConnection.getURI().getPort() );
        }
        
        return portString;
      }
    };
  }  
}
