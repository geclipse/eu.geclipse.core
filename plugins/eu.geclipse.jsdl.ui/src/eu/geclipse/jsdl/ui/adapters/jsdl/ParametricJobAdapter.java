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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.ContainmentUpdatingFeatureMapEntry;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;

import eu.geclipse.jsdl.model.base.ApplicationType;
import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.ExactType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JobIdentificationType;
import eu.geclipse.jsdl.model.base.RangeType;
import eu.geclipse.jsdl.model.base.RangeValueType;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.model.functions.ExceptionType;
import eu.geclipse.jsdl.model.functions.FunctionsFactory;
import eu.geclipse.jsdl.model.functions.FunctionsPackage;
import eu.geclipse.jsdl.model.functions.LoopType;
import eu.geclipse.jsdl.model.functions.ValuesType;
import eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepType;

public class ParametricJobAdapter extends JsdlAdaptersFactory {

  private JobDescriptionType jobDescriptionType;
  private boolean isNotifyAllowed;
  private boolean adapterRefreshed;

  /**
   * Constructs a new <code> {@link DataStageTypeAdapter} </code>
   * 
   * @param jobDefinitionRoot . The root element of a JSDL document (
   *          {@link JobDefinitionType}).
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
        result.add( "/*//jsdl:JobName" );
      }
      if( jobIdentification.getDescription() != null ) {
        result.add( "/*//jsdl:JobIdentification/jsdl:Description" );
      }
      if( jobIdentification.getJobAnnotation() != null ) {
        // this element is deprecated and may be removed in future versions of
        // JSDL
        int annotationsCount = jobIdentification.getJobAnnotation().size();
        if( annotationsCount > 1 ) {
          for( int i = 0; i < annotationsCount; i++ ) {
            result.add( "/*//jsdl:JobAnnotation[" + ( i + 1 ) + "]" );
          }
        } else if( annotationsCount == 1 ) {
          result.add( "/*//jsdl:JobAnnotation" );
        }
      }
      if( jobIdentification.getJobProject() != null ) {
        int projectCount = jobIdentification.getJobProject().size();
        if( projectCount > 1 ) {
          for( int i = 0; i < projectCount; i++ ) {
            result.add( "/*//jsdl:JobProject[" + ( i + 1 ) + "]" );
          }
        } else if( projectCount == 1 ) {
          result.add( "/*//jsdl:JobProject" );
        }
      }
    }
    // Application element
    ApplicationType application = this.jobDescriptionType.getApplication();
    if( application != null ) {
      if( application.getApplicationName() != null ) {
        result.add( "/*//jsdl:ApplicationName" );
      }
      if( application.getApplicationVersion() != null ) {
        result.add( "/*//jsdl:ApplicationVersion" );
      }
      if( application.getDescription() != null ) {
        result.add( "/*//jsdl:Application/jsdl:Description" );
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
                result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Argument["
                            + ( j + 1 )
                            + "]" );
              }
            } else if( argumentsCount == 1 ) {
              result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Argument" );
            }
          }
          if( posixApp.getExecutable() != null ) {
            result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Executable" );
          }
          if( posixApp.getInput() != null ) {
            result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Input" );
          }
          if( posixApp.getOutput() != null ) {
            result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Output" );
          }
          if( posixApp.getError() != null ) {
            result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Error" );
          }
          if( posixApp.getEnvironment() != null ) {
            int envCount = posixApp.getEnvironment().size();
            if( envCount > 1 ) {
              for( int j = 0; j < envCount; j++ ) {
                result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Environment["
                            + ( j + 1 )
                            + "]" );
              }
            } else if( envCount == 1 ) {
              result.add( "/*//jsdl-posix:POSIXApplication/jsdl-posix:Environment" );
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
        int i = 1;
        for( DataStagingType dataStaging : dataStagingList ) {
          result.addAll( parseDataStagingElement( dataStaging,
                                                  "/*//jsdl:DataStaging["
                                                      + i
                                                      + "]" ) );
          i++;
        }
      } else if( dataStagingCount == 1 ) {
        DataStagingType data = dataStagingList.get( 0 );
        result.addAll( parseDataStagingElement( data, "/*//jsdl:DataStaging" ) );
      }
    }
    // Resources element
    if( this.jobDescriptionType.getResources() != null ) {
      result.addAll( parseResources( this.jobDescriptionType.getResources() ) );
    }
    return result;
  }

  private List<String> parseResources( final ResourcesType resources ) {
    List<String> result = new ArrayList<String>();
    String baseString = "/*//jsdl:Resources/";
    if( resources.getCandidateHosts() != null ) {
      String baseCandidate = baseString + "jsdl:CandidateHosts/";
      if( resources.getCandidateHosts().getHostName().size() > 1 ) {
        int i = 1;
        for( Object host : resources.getCandidateHosts().getHostName() ) {
          result.add( baseCandidate + "jsdl:HostName[" + i + "]" );
          i++;
        }
      } else {
        result.add( baseCandidate + "jsdl:HostName" );
      }
    }
    // if (resources.getFileSystem() != null){
    // String baseFileSystem = baseString + "jsdl:CandidateHosts/";
    // if( resources.getCandidateHosts().getHostName().size() > 1 ) {
    // int i = 1;
    // for( Object host : resources.getCandidateHosts().getHostName() ) {
    // result.add( baseCandidate + "jsdl:HostName[" + i + "]" );
    // i++;
    // }
    // } else {
    // result.add( baseCandidate + "jsdl:HostName" );
    // }
    // }
    if( resources.isSetExclusiveExecution() ) {
      result.add( baseString + "jsdl:ExclusiveExecution" );
    }
    if( resources.getOperatingSystem() != null ) {
      String baseOS = baseString + "jsdl:OperatingSystem/";
      if( resources.getOperatingSystem().getOperatingSystemType() != null ) {
        result.add( baseOS
                    + "jsdl:OperatingSystemType/jsdl:OperatingStystemName" );
      }
      if( resources.getOperatingSystem().getOperatingSystemVersion() != null ) {
        result.add( baseOS + "jsdl:OperatingSystemVersion" );
      }
      if( resources.getOperatingSystem().getDescription() != null ) {
        result.add( baseOS + "jsdl:Description" );
      }
      if( resources.getCPUArchitecture() != null ) {
        result.add( baseString
                    + "jsdl:CPUArchitecture/jsdl:CPUArchitectureName" );
      }
    }
    result.addAll( parseRangeValueType( resources.getIndividualCPUSpeed(),
                                        baseString + "jsdl:IndividualCPUSpeed/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualCPUTime(),
                                        baseString + "jsdl:IndividualCPUTime/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualCPUCount(),
                                        baseString + "jsdl:IndividualCPUCount/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualNetworkBandwidth(),
                                        baseString
                                            + "jsdl:IndividualNetworkBandwidth/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualPhysicalMemory(),
                                        baseString
                                            + "jsdl:IndividualPhysicalMemory/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualVirtualMemory(),
                                        baseString
                                            + "jsdl:IndividualVirtualMemory/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualCPUTime(),
                                        baseString + "jsdl:IndividualCPUTime/" ) );
    result.addAll( parseRangeValueType( resources.getIndividualDiskSpace(),
                                        baseString
                                            + "jsdl:IndividualDiskSpace/" ) );
    result.addAll( parseRangeValueType( resources.getTotalCPUTime(),
                                        baseString + "jsdl:TotalCPUTime/" ) );
    result.addAll( parseRangeValueType( resources.getTotalCPUCount(),
                                        baseString + "jsdl:TotalCPUCount/" ) );
    result.addAll( parseRangeValueType( resources.getTotalPhysicalMemory(),
                                        baseString
                                            + "jsdl:TotalPhysicalMemory/" ) );
    result.addAll( parseRangeValueType( resources.getTotalVirtualMemory(),
                                        baseString + "jsdl:TotalVirtualMemory/" ) );
    result.addAll( parseRangeValueType( resources.getTotalDiskSpace(),
                                        baseString + "jsdl:TotalDiskSpace/" ) );
    result.addAll( parseRangeValueType( resources.getTotalResourceCount(),
                                        baseString + "jsdl:TotalResourceCount/" ) );
    return result;
  }

  private List<String> parseRangeValueType( final RangeValueType rangeType,
                                            final String baseString )
  {
    List<String> result = new ArrayList<String>();
    if( rangeType != null ) {
      if( rangeType.getUpperBound() != null ) {
        result.add( baseString + "jsdl:UpperBoundedRange" );
        if( rangeType.getUpperBound().isSetExclusiveBound() ) {
          result.add( baseString + "jsdl:UpperBoundedRange@exclusiveBound" );
        }
      }
      if( rangeType.getLowerBound() != null ) {
        result.add( baseString + "jsdl:UpperLowerRange" );
        if( rangeType.getLowerBound().isSetExclusiveBound() ) {
          result.add( baseString + "jsdl:UpperLowerRange@exclusiveBound" );
        }
      }
      if( rangeType.getExact() != null ) {
        if( rangeType.getExact().size() > 1 ) {
          int i = 1;
          for( Object obj : rangeType.getExact() ) {
            String baseExact = baseString + "jsdl:Exact[" + i + "]";
            result.add( baseExact );
            if( ( ( ExactType )obj ).isSetEpsilon() ) {
              result.add( baseExact + "@epsilon" );
            }
            i++;
          }
        } else {
          result.add( baseString + "jsdl:Exact" );
          if( ( ( ExactType )rangeType.getExact().get( 0 ) ).isSetEpsilon() ) {
            result.add( baseString + "jsdl:Exact@epsilon" );
          }
        }
      }
      if( rangeType.getRange() != null ) {
        if( rangeType.getRange().size() > 1 ) {
          int i = 1;
          for( Object obj : rangeType.getRange() ) {
            result.addAll( parseRangeType( ( RangeType )obj, baseString
                                                             + "jsdl:Range["
                                                             + i
                                                             + "]/" ) );
          }
        } else {
          result.addAll( parseRangeType( ( RangeType )rangeType.getRange()
            .get( 0 ), baseString + "jsdl:Range/" ) );
        }
      }
    }
    return result;
  }

  private List<String> parseRangeType( final RangeType range,
                                       final String baseString )
  {
    List<String> result = new ArrayList<String>();
    if( range.getLowerBound() != null ) {
      result.add( baseString + "jsdl:LowerBound" );
      if( range.getLowerBound().isSetExclusiveBound() ) {
        result.add( baseString + "jsdl:LowerBound@ExclusiveBound" );
      }
    }
    if( range.getUpperBound() != null ) {
      result.add( baseString + "jsdl:UpperBound" );
      if( range.getUpperBound().isSetExclusiveBound() ) {
        result.add( baseString + "jsdl:UpperBound@ExclusiveBound" );
      }
    }
    return result;
  }

  private List<String> parseDataStagingElement( final DataStagingType dataStaging,
                                                final String baseString )
  {
    List<String> result = new ArrayList<String>();
    if( dataStaging.getFileName() != null
        && !dataStaging.getFileName().equals( "" ) )
    {
      result.add( baseString + "/jsdl:FileName" );
    }
    if( dataStaging.getFilesystemName() != null
        && !dataStaging.getFilesystemName().equals( "" ) )
    {
      result.add( baseString + "/jsdl:FilesystemName" );
    }
    if( dataStaging.isSetCreationFlag() ) {
      result.add( baseString + "/jsdl:CreationFlag" );
    }
    if( dataStaging.isSetDeleteOnTermination() ) {
      result.add( baseString + "/jsdl:DeleteOnTermination" );
    }
    if( dataStaging.getSource() != null ) {
      if( dataStaging.getSource().getURI() != null
          && !dataStaging.getSource().getURI().equals( "" ) )
      {
        result.add( baseString + "/jsdl:Source/jsdl:URI" );
      }
    }
    if( dataStaging.getTarget() != null ) {
      if( dataStaging.getTarget().getURI() != null
          && !dataStaging.getTarget().getURI().equals( "" ) )
      {
        result.add( baseString + "/jsdl:Target/jsdl:URI" );
      }
    }
    return result;
  }

  public List<String> getValuesForParameter( final String paramName,
                                             final List<SweepType> sweepList )
  {
    List<String> result = new ArrayList<String>();
    SweepType sweep = findSweepElement( paramName, sweepList );
    if( sweep != null ) {
      AssignmentType assignment = null;
      for( int j = 0; j < sweep.getAssignment().size(); j++ ) {
        if( ( ( AssignmentType )sweep.getAssignment().get( j ) ).getParameter()
          .contains( paramName ) )
        {
          assignment = ( AssignmentType )sweep.getAssignment().get( j );
          break;
        }
      }
      if( assignment != null ) {
        if( assignment.getFunction() instanceof ValuesType ) {
          ValuesType values = ( ValuesType )assignment.getFunction();
          if( values != null ) {
            for( int i = 0; i < values.getValue().size(); i++ ) {
              result.add( ( String )values.getValue().get( i ) );
            }
          }
        }
        Iterator iterator = assignment.getFunctionGroup().iterator();
        while( iterator.hasNext() ) {
          Object obj = iterator.next();
          if( obj instanceof ContainmentUpdatingFeatureMapEntry ) {
            ContainmentUpdatingFeatureMapEntry loopF = ( ContainmentUpdatingFeatureMapEntry )obj;
            if( loopF.getValue() instanceof LoopType ) {
              LoopType loop = ( LoopType )loopF.getValue();
              List<BigInteger> list = new ArrayList<BigInteger>();
              for( ExceptionType exc : ( ExceptionType[] )loop.getException()
                .toArray( new ExceptionType[ 0 ] ) )
              {
                list.add( exc.getValue() );
              }
              result.add( createLOOPString( loop.getStart(),
                                            loop.getEnd(),
                                            loop.getStep(),
                                            list ) );
            }
          }
        }
      }
    }
    return result;
  }

  public SweepType findSweepElement( final String name,
                                     final List<SweepType> sweepList )
  {
    SweepType refSweep = null;
    for( SweepType sweep : sweepList ) {
      int k = 0;
      EList list = sweep.getAssignment();
      for( int i = 0; i < list.size(); i++ ) {
        Object el = list.get( i );
        if( el instanceof AssignmentType ) {
          AssignmentType assignment = ( AssignmentType )el;
          EList paramList = assignment.getParameter();
          for( int j = 0; j < paramList.size(); j++ ) {
            String name1 = ( String )paramList.get( j );
            if( name1.equals( name ) ) {
              refSweep = sweep;
            }
          }
        }
      }
    }
    return refSweep;
  }

  /**
   * Method to create user-friendly string representation of loop function for
   * given values.
   * 
   * @param start start value of the loop
   * @param end end value of the loop
   * @param step step value for the loop
   * @param exceptionsValues list of values, which should be excluded from
   *          generated loop values
   * @return string which should be presented to user (e.g. in editor). It has
   *         the following structure: "LOOP( start = startValue, end = endValue,
   *         step = stepValue) \ { exception1, exception2, ... }"
   */
  public String createLOOPString( final BigInteger startValue,
                                  final BigInteger endValue,
                                  final BigInteger stepValue,
                                  final List<BigInteger> exceptionsValues )
  {
    String result = "LOOP";
    result = result + "( start = " + startValue.toString() + "; ";
    result = result + "end = " + endValue.toString() + "; ";
    result = result + "step = " + stepValue.toString() + " )";
    if( exceptionsValues != null & exceptionsValues.size() > 0 ) {
      result = result + " \\ { ";
      for( BigInteger exc : exceptionsValues ) {
        result = result + exc.toString() + "; ";
      }
      result = result.substring( 0, result.length() - 2 );
      result = result + " }";
    }
    parseLOOPStringForEnd( result );
    parseLOOPStringForStep( result );
    return result;
  }

  public BigInteger parseLOOPStringForStart( final String loopString ) {
    BigInteger result = null;
    String[] splited = loopString.split( "start\\s*=\\s*" );
    if( splited.length > 1 ) {
      result = new BigInteger( splited[ 1 ].split( "\\s*;" )[ 0 ] );
    }
    return result;
  }

  public BigInteger parseLOOPStringForEnd( final String loopString ) {
    BigInteger result = null;
    String[] splited = loopString.split( "end\\s*=\\s*" );
    if( splited.length > 1 ) {
      result = new BigInteger( splited[ 1 ].split( "\\s*;" )[ 0 ] );
    }
    return result;
  }

  public BigInteger parseLOOPStringForStep( final String loopString ) {
    BigInteger result = null;
    String[] splited = loopString.split( "step\\s*=\\s*" );
    if( splited.length > 1 ) {
      result = new BigInteger( splited[ 1 ].split( "\\s*\\)" )[ 0 ] );
    }
    return result;
  }

  public List<ExceptionType> parseLOOPStringForExceptions( final String loopString )
  {
    List<ExceptionType> result = new ArrayList<ExceptionType>();
    FunctionsPackage pak = FunctionsPackageImpl.eINSTANCE;
    FunctionsFactory factory = pak.getFunctionsFactory();
    String[] splited = loopString.split( "\\s*\\\\\\s*\\{\\s*" );
    if( splited.length > 1 ) {
      splited = splited[ 1 ].split( "\\s*;\\s*" );
      for( String split : splited ) {
        try {
          if( split.endsWith( " }" ) ) {
            split = split.split( "\\s*\\}" )[ 0 ];
          }
          ExceptionType exc = factory.createExceptionType();
          exc.setValue( new BigInteger( split ) );
          result.add( exc );
        } catch( NumberFormatException nubmerExc ) {
          // ignore
        }
      }
    }
    return result;
  }
}
