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

import eu.geclipse.core.model.IGridJob;


/**
 * Properties for IGridJob
 * 
 * @author Mariusz Wojtysiak
 */
public class JobPropertySource extends AbstractPropertySource {

  protected IGridJob gridJob;

  /**
   * @param gridJob - object, which properties will be displayed
   */
  public JobPropertySource( final IGridJob gridJob ) {
    super();
    this.gridJob = gridJob;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.properties.AbstractPropertySource#createPropertyDescriptors()
   */
  @Override
  protected IProperty[] createProperties()
  {
    IProperty[] result = new IProperty[]{
      createPropertyName(),
      createPropertyId(),      
      createPropertyStatus()
    };
    if( this.gridJob.getJobDescription() != null ) {
      JobDescPropertySource jobDescPropertySource = new JobDescPropertySource( this.gridJob.getJobDescription() );
      result = joinProperties( result, jobDescPropertySource.getProperties() );
    }
    return result;
  }

  private IProperty createPropertyName() {
    return new AbstractProperty( Messages.getString( "JobPropertySource.propertyName" ), //$NON-NLS-1$
                                 Messages.getString( "JobPropertySource.categoryGeneral" ) ) { //$NON-NLS-1$

      public String getValue() {
        return JobPropertySource.this.gridJob.getName();
      }
    };
  }

  private IProperty createPropertyId() {
    return new AbstractProperty( Messages.getString( "JobPropertySource.propertyId" ), //$NON-NLS-1$
                                 Messages.getString( "JobPropertySource.categoryGeneral" ) ) { //$NON-NLS-1$

      public String getValue() {
        String valueString = null;
        if( JobPropertySource.this.gridJob.getID() != null ) {
          valueString = JobPropertySource.this.gridJob.getID().getJobID();
        }
        return valueString;
      }
    };
  }

  private IProperty createPropertyStatus() {
    return new AbstractProperty( Messages.getString( "JobPropertySource.propertyStatus" ), //$NON-NLS-1$
                                 Messages.getString( "JobPropertySource.categoryGeneral" ) ) { //$NON-NLS-1$

      public String getValue() {
        String valueString = null;
        if( JobPropertySource.this.gridJob.getJobStatus() != null ) {
          valueString = JobPropertySource.this.gridJob.getJobStatus().getName();
        }
        return valueString;
      }
    };
  }
}
