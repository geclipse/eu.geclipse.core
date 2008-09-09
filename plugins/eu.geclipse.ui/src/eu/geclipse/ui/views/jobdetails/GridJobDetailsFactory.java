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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.jobdetails;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridJob;

/**
 *
 */
public class GridJobDetailsFactory implements IJobDetailsFactory {

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.views.jobdetails.IJobDetailsFactory#getDetails(eu.geclipse.core.model.IGridJob,
   *      eu.geclipse.ui.views.jobdetails.JobDetailSectionsManager)
   */
  public List<IJobDetail> getDetails( final IGridJob gridJob,
                                      final JobDetailSectionsManager sectionManager )
  {
    List<IJobDetail> details = new ArrayList<IJobDetail>( 20 );
    details.add( createName( sectionManager ) );
    details.add( createIdentifier( sectionManager ) );
    details.add( createStatus( sectionManager ) );
    details.add( createReason( sectionManager ) );
    details.add( createStatusUpdatedTime( sectionManager ) );
    details.add( createExecutable( sectionManager ) );
    details.add( createExecArgs( sectionManager ) );
    details.add( createInput( sectionManager ) );
    details.add( createOutput( sectionManager ) );
    details.add( createDescription( sectionManager ) );
    return details;
  }

  private IJobDetail createName( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionGeneral(),
                              Messages.GridJobDetailsFactory_name )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        return gridJob.getJobName();
      }
    };
  }

  private IJobDetail createIdentifier( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionGeneral(),
                              Messages.GridJobDetailsFactory_id )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getID() != null ) {
          value = gridJob.getID().getJobID();
        }
        return value;
      }
    };
  }

  private IJobDetail createStatus( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionGeneral(),
                              Messages.GridJobDetailsFactory_status )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobStatus() != null ) {
          value = gridJob.getJobStatus().getName();
        }
        return value;
      }
    };
  }

  private IJobDetail createReason( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionGeneral(),
                              Messages.GridJobDetailsFactory_reason )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobStatus() != null
            && gridJob.getJobStatus().getReason() != null
            && gridJob.getJobStatus().getReason().length() > 0 ) {
          value = gridJob.getJobStatus().getReason();
        }
        return value;
      }
    };
  }

  private IJobDetail createStatusUpdatedTime( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionGeneral(),
                              Messages.GridJobDetailsFactory_lastUpdateTime )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobStatus() != null
            && gridJob.getJobStatus().getLastUpdateTime() != null )
        {
          value = DateFormat.getDateTimeInstance()
            .format( gridJob.getJobStatus().getLastUpdateTime() );
        }
        return value;
      }
    };
  }

  private IJobDetail createExecutable( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionApplication(),
                              Messages.GridJobDetailsFactory_executable )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobDescription() != null ) {
          value = gridJob.getJobDescription().getExecutable();
        }
        return value;
      }
    };
  }

  private IJobDetail createExecArgs( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionApplication(),
                              Messages.GridJobDetailsFactory_arguments )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String valueString = null;
        if( gridJob.getJobDescription() != null
            && gridJob.getJobDescription().getExecutableArguments() != null )
        {
          StringBuilder stringBuilder = new StringBuilder();
          List<String> argList = gridJob.getJobDescription()
            .getExecutableArguments();
          for( String argString : argList ) {
            if( stringBuilder.length() > 0 ) {
              stringBuilder.append( ", " ); //$NON-NLS-1$
            }
            stringBuilder.append( argString );
          }
          valueString = stringBuilder.toString();
        }
        return valueString;
      }
    };
  }

  private IJobDetail createInput( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionApplication(),
                              Messages.GridJobDetailsFactory_input )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobDescription() != null ) {
          value = gridJob.getJobDescription().getStdInputFileName();
        }
        return value;
      }
    };
  }

  private IJobDetail createOutput( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionApplication(),
                              Messages.GridJobDetailsFactory_output )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobDescription() != null ) {
          value = gridJob.getJobDescription().getStdOutputFileName();
        }
        return value;
      }
    };
  }
  
  private IJobDetail createDescription( final JobDetailSectionsManager sectionManager )
  {
    return new JobTextDetail( sectionManager.getSectionApplication(),
                              Messages.GridJobDetailsFactory_description )
    {

      @Override
      protected String getValue( final IGridJob gridJob ) {
        String value = null;
        if( gridJob.getJobDescription() != null ) {
          value = gridJob.getJobDescription().getDescription();
        }
        return value;
      }
    };
  }

}
