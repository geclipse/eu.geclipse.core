/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.batch.BatchQueueDescription;
import eu.geclipse.ui.properties.AbstractProperty;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;


/**
 * @author nickl
 *
 */
public class BatchQueueDescriptionPropertySource extends AbstractPropertySource<BatchQueueDescription>
{
  
  static private List< IProperty< BatchQueueDescription > > staticProperties;

  /**
   * @param queueDescription
   */
  public BatchQueueDescriptionPropertySource( final BatchQueueDescription queueDescription )
  {
    super( queueDescription );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#getPropertySourceClass()
   */
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass() {
    return BatchQueueDescriptionPropertySource.class;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#getStaticProperties()
   */
  @Override
  protected List< IProperty< BatchQueueDescription > > getStaticProperties() {
    
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
  }
  
  
  static private List< IProperty< BatchQueueDescription > > createProperties() {
    
    List< IProperty< BatchQueueDescription > > propertiesList = 
      new ArrayList< IProperty< BatchQueueDescription > >( 1 );
    
    propertiesList.add( createAllowedVOs() );
    propertiesList.add( createMaxWallTime() );
    propertiesList.add( createMaxCPUTime() );
    propertiesList.add( createQueueStatus() );
    propertiesList.add( createQueueType() );
    propertiesList.add( createQueueName() );    
    
    return propertiesList;
    
  }
  
  
  static private IProperty< BatchQueueDescription > createQueueName() {
    return new AbstractProperty< BatchQueueDescription >( 
        Messages.getString( "BatchQueueDescriptionPropertySource.propertyQueueName" ),  //$NON-NLS-1$
        null, false ) {      
      @Override
      public Object getValue( final BatchQueueDescription source ) {
        if ( null == source.getQueueName() ){
                
         // try {
            source.load(source.getResource().getFullPath().toString());
         // } catch( GridModelException e ) {
            // Ignore for now
         // }
        }
        return source.getQueueName();
      }
    };
  }
  
  
  static private IProperty< BatchQueueDescription > createQueueType() {
    return new AbstractProperty< BatchQueueDescription >( 
        Messages.getString( "BatchQueueDescriptionPropertySource.propertyQueueType" ),  //$NON-NLS-1$
        null, false ) {      
      @Override
      public Object getValue( final BatchQueueDescription source ) {
        if ( null == source.getQueueType() ){
                
         // try {
            source.load(source.getResource().getFullPath().toString());
         // } catch( GridModelException e ) {
            // Ignore for now
         // }
        }
        return source.getQueueType();
      }
    };
  }
  
  
  static private IProperty< BatchQueueDescription > createQueueStatus() {
    return new AbstractProperty< BatchQueueDescription >( 
        Messages.getString( "BatchQueueDescriptionPropertySource.propertyQueueStatus" ),  //$NON-NLS-1$
        null, false ) {      
      @Override
      public Object getValue( final BatchQueueDescription source ) {
        if ( null == source.getQueueStatus() ){
                
        //  try {
            source.load(source.getResource().getFullPath().toString());
        //  } catch( GridModelException e ) {
            // Ignore for now
        //  }
        }
        return source.getQueueStatus();
      }
    };
  }
  
  static private IProperty< BatchQueueDescription > createMaxCPUTime() {
    return new AbstractProperty< BatchQueueDescription >( 
        Messages.getString( "BatchQueueDescriptionPropertySource.propertyQueueCPUTime" ),  //$NON-NLS-1$
        null, false ) {      
      @Override
      public Object getValue( final BatchQueueDescription source ) {
        if ( null == source.getQueueMaxCPUTime() ){
                
        //  try {
            source.load(source.getResource().getFullPath().toString());
        //  } catch( GridModelException e ) {
            // Ignore for now
        //  }
        }
        return new Double(source.getQueueMaxCPUTimeValue());
      }
    };
  }
  
  
  static private IProperty< BatchQueueDescription > createMaxWallTime() {
    return new AbstractProperty< BatchQueueDescription >( 
        Messages.getString( "BatchQueueDescriptionPropertySource.propertyQueueWallTime" ),  //$NON-NLS-1$
        null, false ) {      
      @Override
      public Object getValue( final BatchQueueDescription source ) {
        if ( null == source.getQueueMaxWallTime() ){
                
         // try {
            source.load(source.getResource().getFullPath().toString());
         // } catch( GridModelException e ) {
            // Ignore for now
          //}
        }
        return new Double(source.getQueueMaxCPUTimeValue());
      }
    };
  }
  
  
  static private IProperty< BatchQueueDescription > createAllowedVOs() {
    return new AbstractProperty< BatchQueueDescription >( 
        Messages.getString( "BatchQueueDescriptionPropertySource.propertyQueueVOs" ),  //$NON-NLS-1$
        null, false ) {
      @Override
      public Object getValue( final BatchQueueDescription source ) {
        if ( 0 == source.getAllowedVirtualOrganizations().size() ){
                
          //try {
            source.load(source.getResource().getFullPath().toString());
         // } catch( GridModelException e ) {
            // Ignore for now
         // }
        }
        return source.getAllowedVirtualOrganizations();
      }
    };
  }
  
  
  
} // end class BatchQueueDescriptionSource
