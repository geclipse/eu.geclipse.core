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

import eu.geclipse.core.model.IGridJob;


/**
 * Property source for {@link IGridJob}
 */
public class GridJobSource extends AbstractPropertySource<IGridJob> {

  static private List<IProperty<IGridJob>> staticProperties;

  /**
   * @param gridJob - job for which properties will be shown
   */
  public GridJobSource( final IGridJob gridJob ) {
    super( gridJob );
    if( gridJob.getJobDescription() != null ) {
      addChildSource( new GridJobDescSource( gridJob.getJobDescription() ) );
    }
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridJobSource.class;
  }

  @Override
  protected List<IProperty<IGridJob>> getStaticProperties()
  {
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
  }

  static private List<IProperty<IGridJob>> createProperties() {
    List<IProperty<IGridJob>> propertiesList = new ArrayList<IProperty<IGridJob>>( 1 );
    propertiesList.add( createName() );
    propertiesList.add( createId() );
    propertiesList.add( createStatus() );
    return propertiesList;
  }

  static private IProperty<IGridJob> createName() {
    return new AbstractProperty<IGridJob>( Messages.getString( "JobPropertySource.propertyName" ), //$NON-NLS-1$
                                           Messages.getString( "JobPropertySource.categoryGeneral" ) ) //$NON-NLS-1$
    {

      @Override
      public Object getValue( final IGridJob sourceObject )
      {
        return sourceObject.getName();
      }
    };
  }

  static private IProperty<IGridJob> createId() {
    return new AbstractProperty<IGridJob>( Messages.getString( "JobPropertySource.propertyId" ), //$NON-NLS-1$
                                           Messages.getString( "JobPropertySource.categoryGeneral" ) ) //$NON-NLS-1$
    {

      @Override
      public Object getValue( final IGridJob gridJob )
      {
        String value = null;
        if( gridJob.getID() != null ) {
          value = gridJob.getID().getJobID();
        }
        return value;
      }
    };
  }

  static private IProperty<IGridJob> createStatus() {
    return new AbstractProperty<IGridJob>( Messages.getString( "JobPropertySource.propertyStatus" ), //$NON-NLS-1$
                                           Messages.getString( "JobPropertySource.categoryGeneral" ) ) //$NON-NLS-1$
    {

      @Override
      public Object getValue( final IGridJob gridJob )
      {
        String value = null;
        if( gridJob.getJobStatus() != null ) {
          value = gridJob.getJobStatus().getName();
        }
        return value;
      }
    };
  }
}
