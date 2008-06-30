/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Nikolaos Tsioutsias - initial API and implementation
 *    
 *****************************************************************************/

package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.info.glue.GlueSEAccessProtocol;
import eu.geclipse.info.model.GridGlueStorage;
import eu.geclipse.ui.properties.AbstractProperty;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;

public class GridGlueStorageSource extends AbstractPropertySource<GridGlueStorage>
{
  GridGlueStorage gridGlueStorage;
  
  public GridGlueStorageSource(final GridGlueStorage gridGlueStorage)
  {
    super(gridGlueStorage);
    this.gridGlueStorage = gridGlueStorage;
  }
  
  public Object getEditableValue() {
    return this;
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridGlueStorageSource.class;
  }
  
  protected List<IProperty<GridGlueStorage>> getStaticProperties() {
    List<IProperty<GridGlueStorage>> list = new ArrayList<IProperty<GridGlueStorage>>();

    for (int i=0; i<this.gridGlueStorage.getGlueSe().glueSEAccessProtocolList.size(); i++)
    {
      list.add( createEndpoint( "Endpoint", 
                                this.gridGlueStorage.getGlueSe().glueSEAccessProtocolList.get(i).Endpoint,
                                i));
      list.add( createType( "Type", 
                                this.gridGlueStorage.getGlueSe().glueSEAccessProtocolList.get(i).Endpoint,
                                i));
      list.add( createVersion( "Version", 
                               this.gridGlueStorage.getGlueSe().glueSEAccessProtocolList.get(i).Endpoint,
                               i ));
      list.add( createPort( "Port", 
                            this.gridGlueStorage.getGlueSe().glueSEAccessProtocolList.get(i).Endpoint,
                            i));
    }
    
    return list;
  }
  
  private IProperty<GridGlueStorage> createEndpoint( final String key, final String name, final int i ) {
    return new AbstractProperty<GridGlueStorage>( key, name ) {

      @Override
      public Object getValue( final GridGlueStorage sourceObject) {
        GlueSEAccessProtocol accessProtocol = sourceObject.getGlueSe().glueSEAccessProtocolList.get( i );
        return accessProtocol.Endpoint;
      }
    };
  }
    
  private IProperty<GridGlueStorage> createVersion( final String key, final String name, final int i ) {
    return new AbstractProperty<GridGlueStorage>( key, name ) {

      @Override
      public Object getValue( final GridGlueStorage sourceObject ) {
        GlueSEAccessProtocol accessProtocol = sourceObject.getGlueSe().glueSEAccessProtocolList.get( i );
        return accessProtocol.Version;
      }
    };
  }
  
  private IProperty<GridGlueStorage> createPort( final String key, final String name, final int i ) {
    return new AbstractProperty<GridGlueStorage>( key, name ) {

      @Override
      public Object getValue( final GridGlueStorage sourceObject ) {
        GlueSEAccessProtocol accessProtocol = sourceObject.getGlueSe().glueSEAccessProtocolList.get( i );
        return accessProtocol.Port;
      }
    };
  }
  
  private IProperty<GridGlueStorage> createType( final String key, final String name, final int i ) {
    return new AbstractProperty<GridGlueStorage>( key, name ) {

      @Override
      public Object getValue( final GridGlueStorage sourceObject ) {
        GlueSEAccessProtocol accessProtocol = sourceObject.getGlueSe().glueSEAccessProtocolList.get( i );
        return accessProtocol.Type;
      }
    };
  }
}
