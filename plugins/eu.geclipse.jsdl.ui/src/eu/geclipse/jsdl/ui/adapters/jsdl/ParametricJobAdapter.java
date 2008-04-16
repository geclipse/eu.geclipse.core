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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.adapters.jsdl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import eu.geclipse.jsdl.model.base.ApplicationType;
import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JobIdentificationType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;

public class ParametricJobAdapter extends JsdlAdaptersFactory {

  private JobDescriptionType jobDescriptionType;
  private boolean isNotifyAllowed;
  private boolean adapterRefreshed;

  /**
   * Constructs a new <code> {@link DataStageTypeAdapter} </code>
   * 
   * @param jobDefinitionRoot . The root element of a JSDL document ({@link JobDefinitionType}).
   */
  public ParametricJobAdapter( final JobDefinitionType jobDefinitionRoot ) {
    getTypeForAdapter( jobDefinitionRoot );
  } // End Constructor

  protected void contentChanged() {
    if( this.isNotifyAllowed ) {
      fireNotifyChanged( null );
    }
  } // End void contenctChanged()

  /*
   * Get the DataStage Type Element from the root Jsdl Element.
   */
  private void getTypeForAdapter( final JobDefinitionType jobDefinitionRoot ) {
    this.jobDescriptionType = jobDefinitionRoot.getJobDescription();
  } // End void getTypeforAdapter()

  /**
   * Allows to set the adapter's content on demand and not through the
   * constructor.
   * 
   * @param jobDefinitionRoot The root element of a JSDL document.
   */
  public void setContent( final JobDefinitionType jobDefinitionRoot ) {
    this.adapterRefreshed = true;
    getTypeForAdapter( jobDefinitionRoot );
  } // End void setContent()

  public List<String> getElementsList() {
    List<String> result = new ArrayList<String>();
    // job identification element
    JobIdentificationType jobIdentification = this.jobDescriptionType.getJobIdentification();
    if( jobIdentification != null ) {
      if( jobIdentification.getJobName() != null ) {
        result.add( "JobName" );
      }
      if( jobIdentification.getDescription() != null ) {
        result.add( "JobIdentification/Description" );
      }
      if( jobIdentification.getJobAnnotation() != null ) {
        // this element is deprecated and may be removed in future versions of
        // JSDL
        int annotationsCount = jobIdentification.getJobAnnotation().size();
        if( annotationsCount > 1 ) {
          for( int i = 0; i < annotationsCount; i++ ) {
            result.add( "JobAnnotation[" + ( i + 1 ) + "]" );
          }
        } else if( annotationsCount == 1 ) {
          result.add( "JobAnnotation" );
        }
      }
      if( jobIdentification.getJobProject() != null ) {
        int projectCount = jobIdentification.getJobProject().size();
        if( projectCount > 1 ) {
          for( int i = 0; i < projectCount; i++ ) {
            result.add( "JobProject[" + ( i + 1 ) + "]" );
          }
        } else if( projectCount == 1 ) {
          result.add( "JobProject" );
        }
      }
    }
    // Application element
    ApplicationType application = this.jobDescriptionType.getApplication();
    if( application != null ) {
      if( application.getApplicationName() != null ) {
        result.add( "ApplicationName" );
      }
      if( application.getApplicationVersion() != null ) {
        result.add( "ApplicationVersion" );
      }
      if( application.getDescription() != null ) {
        result.add( "Application/Description" );
      }
      // check for POSIX
      FeatureMap map = application.getAny();
      for( int i = 0; i < map.size(); i++ ) {
        Entry entry = map.get( i );
        if( entry.getValue() instanceof POSIXApplicationType ) {
          POSIXApplicationType posixApp = ( POSIXApplicationType )entry.getValue();
          if( posixApp.getArgument() != null ) {
            int argumentsCount = posixApp.getArgument().size();
            if( argumentsCount > 1 ) {
              for( int j = 0; j < argumentsCount; j++ ) {
                result.add( "POSIXApplication/Argument[" + ( j + 1 ) + "]" );
              }
            } else if( argumentsCount == 1 ) {
              result.add( "POSIXApplication/Argument" );
            }
          }
          if( posixApp.getExecutable() != null ) {
            result.add( "POSIXApplication/Executable" );
          }
          if( posixApp.getInput() != null ) {
            result.add( "POSIXApplication/Input" );
          }
          if( posixApp.getOutput() != null ) {
            result.add( "POSIXApplication/Output" );
          }
          if( posixApp.getError() != null ) {
            result.add( "POSIXApplication/Error" );
          }
          if( posixApp.getEnvironment() != null ) {
            int envCount = posixApp.getEnvironment().size();
            if( envCount > 1 ) {
              for( int j = 0; j < envCount; j++ ) {
                result.add( "POSIXApplication/Environment[" + ( j + 1 ) + "]" );
              }
            } else if( envCount == 1 ) {
              result.add( "POSIXApplication/Environment" );
            }
          }
        }
      }
    }
    // Data staging element
    EList<DataStagingType> dataStagingList = this.jobDescriptionType.getDataStaging();
    if( dataStagingList != null ) {
      int dataStagingCount = dataStagingList.size();
      if( dataStagingCount > 1 ) {
        for( DataStagingType dataStaging : dataStagingList ) {
        } 
      } else if (dataStagingCount == 1){
        DataStagingType data = dataStagingList.get( 0 );
      }
    }
    // Resources element
    return result;
  }
}
