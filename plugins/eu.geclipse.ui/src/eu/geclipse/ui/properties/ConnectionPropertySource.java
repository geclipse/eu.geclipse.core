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

import java.util.ArrayList;
import java.util.List;
import eu.geclipse.core.model.IGridConnection;


/**
 * Properties for {@link IGridConnection}
 */
public class ConnectionPropertySource
  extends AbstractPropertySource<IGridConnection>
{
  static private List<IProperty<IGridConnection>> staticProperties;

  /**
   * @param gridConnection - connection, for which properties will be shown
   */
  public ConnectionPropertySource( final IGridConnection gridConnection ) {
    super( gridConnection );
    if( gridConnection.getURI() != null ) {
      addChildSource( new URIPropertySource( gridConnection.getURI() ) );
    }
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return ConnectionPropertySource.class;
  }

  @Override
  protected List<IProperty<IGridConnection>> getStaticProperties()
  {
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
  }

  static private List<IProperty<IGridConnection>> createProperties() {
    List<IProperty<IGridConnection>> propertiesList = new ArrayList<IProperty<IGridConnection>>( 5 );
    return propertiesList;
  }
}
