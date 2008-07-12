/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.batch.BatchConnectionInfo;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.properties.AbstractProperty;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;


/**
 * @author harald
 */
public class BatchPropertySource extends AbstractPropertySource< BatchConnectionInfo >
{
  static private List< IProperty< BatchConnectionInfo > > staticProperties;

  /**
   * @param connectionInfo - batch service which properties will be displayed
   */
  BatchPropertySource( final BatchConnectionInfo connectionInfo ) {
    super( connectionInfo );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return BatchPropertySource.class;
  }

  @Override
  protected List< IProperty< BatchConnectionInfo > > getStaticProperties()
  {
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
  }

  static private List< IProperty< BatchConnectionInfo > > createProperties() {
    List< IProperty< BatchConnectionInfo > > propertiesList = new ArrayList< IProperty< BatchConnectionInfo > >( 1 );
    propertiesList.add( createHostName() );
    propertiesList.add( createAccountName() );
    propertiesList.add( createBatchType() );
    propertiesList.add( createUpdateInterval() );
    return propertiesList;
  }

  static private IProperty< BatchConnectionInfo > createHostName() {
    return new AbstractProperty< BatchConnectionInfo >( 
        Messages.getString( "BatchPropertySource.propertyHostName" ),  //$NON-NLS-1$
        null, false ) {

      @Override
      public Object getValue( final BatchConnectionInfo source ) {
        if ( null == source.getBatchType() )
          try {
            source.load();
          } catch ( ProblemException e ) {
            // Ignore for now
          }
        return source.getBatchName();
      }
    };
  }
  
  static private IProperty<BatchConnectionInfo> createAccountName() {
    return new AbstractProperty<BatchConnectionInfo>( 
        Messages.getString( "BatchPropertySource.propertyAccountName" ),  //$NON-NLS-1$
        null, false ) {

      @Override
      public Object getValue( final BatchConnectionInfo source ) {
        if ( null == source.getBatchType() )
          try {
            source.load();
          } catch ( ProblemException e ) {
            // Ignore for now
          }
        return source.getAccount();
      }
    };
  }

  static private IProperty<BatchConnectionInfo> createBatchType() {
    return new AbstractProperty<BatchConnectionInfo>( 
        Messages.getString( "BatchPropertySource.propertyBatchType" ),  //$NON-NLS-1$
        null, false ) {

      @Override
      public Object getValue( final BatchConnectionInfo source ) {
        if ( null == source.getBatchType() )
          try {
            source.load();
          } catch ( ProblemException e ) {
            // Ignore for now
          }
        return source.getBatchType();
      }
    };
  }
  
  static private IProperty<BatchConnectionInfo> createUpdateInterval() {
    return new AbstractProperty<BatchConnectionInfo>( 
        Messages.getString( "BatchPropertySource.propertyUpdateInterval" ),  //$NON-NLS-1$
        null,  false ) {

      @Override
      public Object getValue( final BatchConnectionInfo source ) {
        if ( null == source.getBatchType() )
          try {
            source.load();
          } catch ( ProblemException e ) {
            // Ignore for now
          }
        return Integer.toString( source.getUpdateInterval() );
      }
    };
  }
}
