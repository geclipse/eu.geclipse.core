/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Katarzyna Bylec - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.impl.AbstractGridContainer;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.model.base.ApplicationType;
import eu.geclipse.jsdl.model.base.CandidateHostsType;
import eu.geclipse.jsdl.model.base.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.DocumentRoot;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JobIdentificationType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.model.base.SourceTargetType;
import eu.geclipse.jsdl.model.base.util.JsdlResourceFactoryImpl;
import eu.geclipse.jsdl.model.functions.ExceptionType;
import eu.geclipse.jsdl.model.functions.FunctionsFactory;
import eu.geclipse.jsdl.model.functions.FunctionsPackage;
import eu.geclipse.jsdl.model.functions.LoopType;
import eu.geclipse.jsdl.model.functions.ValuesType;
import eu.geclipse.jsdl.model.functions.impl.FunctionsPackageImpl;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl;
import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepFactory;
import eu.geclipse.jsdl.model.sweep.SweepPackage;
import eu.geclipse.jsdl.model.sweep.SweepType;
import eu.geclipse.jsdl.model.sweep.impl.SweepPackageImpl;
import eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl;

/**
 * JSDLJobDescription object for use outside g-Eclipse model. This class bases
 * only on EMF's model representing JSDL's XML definition. For creating instance
 * of this class a java.io.File with JSDL content has to exist on disk.
 */
public class JSDLJobDescriptionModel extends AbstractGridContainer
  implements IGridJobDescription
{

  private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$
  protected JsdlPackage jsdlPackage = JsdlPackage.eINSTANCE;
  protected PosixPackage posixPackage = PosixPackage.eINSTANCE;
  protected PosixFactory posixFactory = this.posixPackage.getPosixFactory();
  protected JsdlFactory jsdlFactory = this.jsdlPackage.getJsdlFactory();
  private File fileResource;
  private Resource resource = null;
  private DocumentRoot documentRoot;
  private JobDefinitionType jobDefinition;
  private JobDescriptionType jobDescription;
  private JobIdentificationType jobIdentification;

  /**
   * @param file
   * @throws IOWrappedException
   */
  public JSDLJobDescriptionModel( final File file ) throws IOWrappedException {
    this.fileResource = file;
    updateModelFromFile();
  }

  public String getDescription() {
    String result = null;
    if( getJobDescription() != null
        && getJobDescription().getJobIdentification() != null )
    {
      result = getJobDescription().getJobIdentification().getDescription();
    }
    return result;
  }

  public String getExecutable() {
    String result = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getExecutable() != null ) {
      result = posixApp.getExecutable().getValue();
    }
    return result;
  }

  public List<String> getExecutableArguments() {
    ArrayList<String> result = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && !posixApp.getArgument().isEmpty() ) {
      result = new ArrayList<String>();
      for( Object arg : posixApp.getArgument() ) {
        result.add( ( ( ArgumentType )arg ).getValue() );
      }
    }
    return result;
  }

  public String getStdErrorFileName() {
    String errorFilename = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getError() != null ) {
      errorFilename = posixApp.getError().getValue();
    }
    return errorFilename;
  }

  public URI getStdErrorUri() throws ProblemException {
    java.net.URI uri = null;
    String stdErrFileName = getStdErrorFileName();
    if( stdErrFileName != null ) {
      uri = findStagingAbsoluteUri( stdErrFileName, true );
    }
    return uri;
  }

  public String getStdInputFileName() {
    String inputString = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getInput() != null ) {
      inputString = posixApp.getInput().getValue();
    }
    return inputString;
  }

  public URI getStdInputUri() throws ProblemException {
    java.net.URI uri = null;
    String stdInputFileName = getStdInputFileName();
    if( stdInputFileName != null ) {
      uri = findStagingAbsoluteUri( stdInputFileName, false );
    }
    return uri;
  }

  public String getStdOutputFileName() {
    String result = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getOutput() != null ) {
      result = posixApp.getOutput().getValue();
    }
    return result;
  }

  public URI getStdOutputUri() throws ProblemException {
    java.net.URI uri = null;
    String stdOutputFileName = getStdOutputFileName();
    if( stdOutputFileName != null ) {
      uri = findStagingAbsoluteUri( stdOutputFileName, true );
    }
    return uri;
  }

  public boolean isLazy() {
    return false;
  }

  public IFileStore getFileStore() {
    return null;
  }

  public String getName() {
    return this.fileResource.getName();
  }

  // ??? nie można wołać getProject() - zapętli się!
  public IGridContainer getParent() {
    // TODO katis - ???
    return null;
  }

  public IPath getPath() {
    return null;
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }

  // non-interface methods
  /**
   * Updates the EMF JSDL model so that it matches the file content.
   * 
   * @param file XML file with JSDL content.
   * @throws IOWrappedException
   */
  public void updateModelFromFile() throws IOWrappedException {
    if( this.fileResource.length() != 0 ) {
      String filePath = this.fileResource.getAbsolutePath();
      org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createFileURI( filePath );
      ResourceSet resourceSet = new ResourceSetImpl();
      Resource resourceA = resourceSet.createResource( uri );
      XMLMapImpl xmlmap = new XMLMapImpl();
      xmlmap.setNoNamespacePackage( JsdlPackage.eINSTANCE );
      Map<String, Object> options = new HashMap<String, Object>();
      options.put( XMLResource.OPTION_XML_MAP, xmlmap );
      options.put( XMLResource.OPTION_ENCODING, "UTF8" ); //$NON-NLS-1$
      try {
        resourceA.load( options );
        this.documentRoot = ( DocumentRoot )resourceA.getContents().get( 0 );
        this.jobDefinition = this.documentRoot.getJobDefinition();
        this.jobDescription = this.jobDefinition.getJobDescription();
        this.jobIdentification = this.documentRoot.getJobIdentification();
      } catch( IOException ioEx ) {
        if( ioEx instanceof IOWrappedException ) {
          IOWrappedException ioWEx = ( IOWrappedException )ioEx;
          throw ioWEx;
        }
        // TODO katis - other Exceptions - also throw them?
      }
    }
  }

  /**
   * This method is to set EMF document's root element. If given root is empty
   * it creates JobDefinition and JobDescription JSDL elements.
   * 
   * @param root EMF document root to set as a JSDL root element
   */
  public void setRoot( final DocumentRoot root ) {
    this.documentRoot = root;
    if( root.getJobDefinition() != null ) {
      this.jobDefinition = root.getJobDefinition();
      if( this.jobDefinition.getJobDescription() != null ) {
        this.jobDescription = this.jobDefinition.getJobDescription();
      }
    }
  }

  /**
   * Reads content of EMF document and transforms it into a String object.
   * 
   * @return String representation of EMF document's content. This String is
   *         formated - e.g. it is divided into lines.
   * @throws IOException when there was a problem while reading the file
   */
  public String getJSDLString() throws IOException {
    FileInputStream inputStream = new FileInputStream( this.fileResource );
    BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
    char[] buffer = new char[ 1024 ];
    int bytesRead = 0;
    StringBuilder builder = new StringBuilder();
    do {
      bytesRead = reader.read( buffer );
      if( bytesRead > 0 ) {
        builder.append( buffer, 0, bytesRead );
      }
    } while( bytesRead > 0 );
    return builder.toString();
  }

  /**
   * This method re-sets JSDL content. It creates empty parent JSDL elements -
   * JobDefinition, JobDescription and JobIdentification. If JSDL had those
   * elements before calling this method - they will be overwritten with new
   * ones (and their content - children elements - will be lost).
   */
  public void setUpBasicJSDLStructure() {
    // Using the Factory create all other elements.
    this.documentRoot = this.jsdlFactory.createDocumentRoot();
    this.jobIdentification = this.jsdlFactory.createJobIdentificationType();
    this.jobDescription = this.jsdlFactory.createJobDescriptionType();
    this.jobDefinition = this.jsdlFactory.createJobDefinitionType();
    // Associate child element with parent element.
    // Remember that JobDescription is contained within JobDefinition
    // and that JobIdentification is contained within JobDescription.
    this.jobDefinition.setJobDescription( this.jobDescription );
    this.jobDescription.setJobIdentification( this.jobIdentification );
    this.documentRoot.setJobDefinition( this.jobDefinition );
  }

  /**
   * This method re-sets JSDL JobIdentification element (creates new empty
   * element). If JSDL had this element before calling this method - it will be
   * overwritten with new one (and its content - children elements - will be
   * lost). <br>
   * Note: if no JobDescription is set this method will throw
   * NullPointerException
   * 
   * @param jobName name of the job
   * @param description the description of the job
   */
  public void setJobIdentification( final String jobName,
                                    final String description )
  {
    JobIdentificationType identyfication = this.jsdlFactory.createJobIdentificationType();
    if( !"".equals( description ) ) { //$NON-NLS-1$
      identyfication.setDescription( description );
    } else {
      identyfication.setDescription( "default description" ); //$NON-NLS-1$
    }
    if( !"".equals( jobName ) ) { //$NON-NLS-1$
      identyfication.setJobName( jobName );
    } else {
      identyfication.setJobName( "default job name" ); //$NON-NLS-1$
    }
    this.jobDescription.setJobIdentification( identyfication );
  }

  /**
   * This method is for set content of POSIXApplication element. For each
   * standard input, output and error file it creates also corresponding data
   * stagings entries.
   * 
   * @param applicationName name of the POSIXApplication (value of "name"
   *          attribute in POSIXApplication element)
   * @param executableFile value of Executable element
   * @param stdin standard input file's URI (used in data staging element)
   * @param stdinName standard input file's name (used in POSIX Input element
   *          and as a file name in data staging entry)
   * @param stdout standard output file's URI (used in data staging element)
   * @param outName standard output file's name (used in POSIX Output element
   *          and as a file name in data staging entry)
   * @param err standard error file's URI (used in data staging element)
   * @param errName standard error file's name (used in POSIX Error element and
   *          as a file name in data staging entry)
   */
  @SuppressWarnings("unchecked")
  public void setPOSIXApplicationDetails( final String applicationName,
                                          final String executableFile,
                                          final String stdin,
                                          final String stdinName,
                                          final String stdout,
                                          final String outName,
                                          final String err,
                                          final String errName )
  {
    setApplicationName( applicationName );
    POSIXApplicationType posixApp = this.posixFactory.createPOSIXApplicationType();
    FileNameType execFile = this.posixFactory.createFileNameType();
    execFile.setValue( executableFile );
    posixApp.setExecutable( execFile );
    if( stdin != null ) {
      FileNameType inputFile = this.posixFactory.createFileNameType();
      inputFile.setValue( stdinName );
      posixApp.setInput( inputFile );
    }
    if( stdout != null ) {
      FileNameType outputFile = this.posixFactory.createFileNameType();
      outputFile.setValue( outName );
      posixApp.setOutput( outputFile );
    }
    if( err != null ) {
      FileNameType errorFile = this.posixFactory.createFileNameType();
      errorFile.setValue( errName );
      posixApp.setError( errorFile );
    }
    ApplicationType app = this.jobDescription.getApplication();
    if( app == null ) {
      app = this.jsdlFactory.createApplicationType();
    }
    EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( this.posixPackage );
    Entry e = FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "pOSIXApplication" ), //$NON-NLS-1$
                                          posixApp );
    app.getAny().add( e );
    // when adding files for input and output - add them also to data staging
    if( stdin != null ) {
      DataStagingType dataIn = this.jsdlFactory.createDataStagingType();
      dataIn.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
      dataIn.setDeleteOnTermination( false );
      dataIn.setFileName( stdinName );
      SourceTargetType sourceDataIn = this.jsdlFactory.createSourceTargetType();
      sourceDataIn.setURI( stdin );
      dataIn.setSource( sourceDataIn );
      this.jobDescription.getDataStaging().add( dataIn );
    }
    if( stdout != null ) {
      DataStagingType dataOut = this.jsdlFactory.createDataStagingType();
      dataOut.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
      dataOut.setDeleteOnTermination( false );
      dataOut.setFileName( outName );
      SourceTargetType sourceDataOut = this.jsdlFactory.createSourceTargetType();
      sourceDataOut.setURI( stdout );
      dataOut.setTarget( sourceDataOut );
      this.jobDescription.getDataStaging().add( dataOut );
    }
    if( err != null ) {
      DataStagingType dataErr = this.jsdlFactory.createDataStagingType();
      dataErr.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
      dataErr.setDeleteOnTermination( false );
      dataErr.setFileName( errName );
      SourceTargetType sourceDataErr = this.jsdlFactory.createSourceTargetType();
      sourceDataErr.setURI( err );
      dataErr.setTarget( sourceDataErr );
      this.jobDescription.getDataStaging().add( dataErr );
    }
  }

  /**
   * Adds JSDL DataStaging element for staged out resources.
   * 
   * @param name name of the resource
   * @param path URI representing resource's location
   */
  @SuppressWarnings("unchecked")
  public void addDataStagingOut( final String name, final String path ) {
    DataStagingType dataOut = this.jsdlFactory.createDataStagingType();
    dataOut.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
    dataOut.setDeleteOnTermination( true );
    dataOut.setFileName( name );
    SourceTargetType sourceDataIn = this.jsdlFactory.createSourceTargetType();
    sourceDataIn.setURI( path );
    dataOut.setTarget( sourceDataIn );
    this.jobDescription.getDataStaging().add( dataOut );
  }

  /**
   * Sets JSDL DataStaging element for staged in resources.
   * 
   * @param name name of the resource
   * @param path URI representing resource's location
   */
  @SuppressWarnings("unchecked")
  public void addDataStagingIn( final String name, final String path ) {
    DataStagingType dataIn = this.jsdlFactory.createDataStagingType();
    dataIn.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
    dataIn.setDeleteOnTermination( true );
    dataIn.setFileName( name );
    SourceTargetType sourceDataOut = this.jsdlFactory.createSourceTargetType();
    sourceDataOut.setURI( path );
    dataIn.setSource( sourceDataOut );
    this.jobDescription.getDataStaging().add( dataIn );
  }

  /**
   * Method to access POSIXApplication element.
   * 
   * @return POSIXApplcation element with all its children or <code>null</code>
   *         if this element is not set in JSDL document.
   */
  public POSIXApplicationType getPosixApplication() {
    POSIXApplicationType result = null;
    if( getJobDescription() != null
        && getJobDescription().getApplication() != null )
    {
      FeatureMap anyMap = getJobDescription().getApplication().getAny();
      for( int i = 0; i < anyMap.size(); i++ ) {
        if( anyMap.getValue( i ) instanceof POSIXApplicationTypeImpl ) {
          result = ( POSIXApplicationTypeImpl )anyMap.getValue( i );
        }
      }
    }
    return result;
  }

  private JobDescriptionType getJobDescription() {
    JobDescriptionType result = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null && dRoot.getJobDefinition() != null ) {
      result = dRoot.getJobDefinition().getJobDescription();
    }
    return result;
  }

  /**
   * Method to access CPUArchitecture name. This is String value or Resources
   * child element.
   * 
   * @return content of CPUArchitecture element
   */
  public String getCpuArchitectureName() {
    String architectureNameString = null;
    if( getJobDescription() != null
        && getJobDescription().getResources() != null
        && getJobDescription().getResources().getCPUArchitecture() != null
        && getJobDescription().getResources()
          .getCPUArchitecture()
          .getCPUArchitectureName() != null )
    {
      architectureNameString = getJobDescription().getResources()
        .getCPUArchitecture()
        .getCPUArchitectureName()
        .getName();
    }
    return architectureNameString;
  }

  /**
   * Method to access OperatingSystem name. This is String value or Resources
   * child element.
   * 
   * @return content of OperatingSystem > OperatingSystemType >
   *         OperatingSystemName element
   */
  public String getOSTypeName() {
    String nameString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null
        && dRoot.getJobDefinition() != null
        && dRoot.getJobDefinition().getJobDescription() != null
        && dRoot.getJobDefinition().getJobDescription().getResources() != null
        && dRoot.getJobDefinition()
          .getJobDescription()
          .getResources()
          .getOperatingSystem() != null
        && getJobDescription().getResources()
          .getOperatingSystem()
          .getOperatingSystemType() != null
        && dRoot.getJobDefinition()
          .getJobDescription()
          .getResources()
          .getOperatingSystem()
          .getOperatingSystemType()
          .getOperatingSystemName() != null )
    {
      nameString = dRoot.getJobDefinition()
        .getJobDescription()
        .getResources()
        .getOperatingSystem()
        .getOperatingSystemType()
        .getOperatingSystemName()
        .getName();
    }
    return nameString;
  }

  /**
   * Method to access OperatingSystem version. This is String value or Resources
   * child element.
   * 
   * @return content of OperatingSystem > OperatingSystemVersion element
   */
  public String getOSVersion() {
    String versionString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null
        && dRoot.getJobDefinition() != null
        && dRoot.getJobDefinition().getJobDescription() != null
        && dRoot.getJobDefinition().getJobDescription().getResources() != null
        && dRoot.getJobDefinition()
          .getJobDescription()
          .getResources()
          .getOperatingSystem() != null )
    {
      versionString = dRoot.getJobDefinition()
        .getJobDescription()
        .getResources()
        .getOperatingSystem()
        .getOperatingSystemVersion();
    }
    return versionString;
  }

  /**
   * Method to add Argument element to POSIXApplication element. Has no effect
   * if POSIXApplication element was not set.
   * 
   * @param argName name of the argument
   * @param argValues list of argument's values
   */
  @SuppressWarnings("unchecked")
  public void addArgumentForPosixApplication( final String argName,
                                              final ArrayList<String> argValues )
  {
    POSIXApplicationType posixApp = getPosixApplication();
    ArgumentType arg;
    if( argValues != null && !argValues.isEmpty() ) {
      if( posixApp != null ) {
        EList<ArgumentType> argumentList = posixApp.getArgument();
        if( argName != null && !argName.equals( "" ) ) { //$NON-NLS-1$
          arg = this.posixFactory.createArgumentType();
          arg.setValue( argName );
          argumentList.add( arg );
        }
        for( String value : argValues ) {
          if( !value.equals( "" ) ) { //$NON-NLS-1$
            arg = this.posixFactory.createArgumentType();
            arg.setValue( value );
            argumentList.add( arg );
          }
        }
      }
    }
  }

  /**
   * Method to set CandidateHost element of JSDL's Resources. If no Resources
   * element was set - it will be created. Has no effect if JobDescription
   * element was not set.
   * 
   * @param hostsList list of hosts names
   */
  @SuppressWarnings("unchecked")
  public void addCandidateHosts( final List<String> hostsList ) {
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null
        && dRoot.getJobDefinition() != null
        && dRoot.getJobDefinition().getJobDescription() != null )
    {
      ResourcesType resources = dRoot.getJobDefinition()
        .getJobDescription()
        .getResources();
      if( resources == null ) {
        resources = this.jsdlFactory.createResourcesType();
        dRoot.getJobDefinition().getJobDescription().setResources( resources );
      }
      CandidateHostsType candidateHosts = resources.getCandidateHosts();
      if( candidateHosts == null ) {
        candidateHosts = this.jsdlFactory.createCandidateHostsType();
        resources.setCandidateHosts( candidateHosts );
      }
      for( String hostName : hostsList ) {
        candidateHosts.getHostName().add( hostName );
      }
    }
  }

  /**
   * Returns list of those DataStagings entries that have Source element set
   * (data stagings in)
   * 
   * @return list of DataStagings object with Source element set
   */
  @SuppressWarnings("unchecked")
  public List<DataStagingType> getDataStagingIn() {
    List<DataStagingType> result = new ArrayList<DataStagingType>();
    if( getJobDescription() != null ) {
      List<DataStagingType> temp = this.jobDescription.getDataStaging();
      if( temp != null ) {
        for( DataStagingType dataType : temp ) {
          if( dataType.getSource() != null ) {
            result.add( dataType );
          }
        }
      }
    }
    return result;
  }

  /**
   * Returns list of DataStagings entries that have Source element set (data
   * stagings in) in form of Strings map - mapping file name to its location
   * 
   * @return Map with data staging in file's name as a key and its location as a
   *         value
   */
  @SuppressWarnings("unchecked")
  public Map<String, String> getDataStagingInStrings() {
    Map<String, String> result = new HashMap<String, String>();
    if( getJobDescription() != null ) {
      List<DataStagingType> temp = this.jobDescription.getDataStaging();
      if( temp != null ) {
        for( DataStagingType dataType : temp ) {
          if( dataType.getSource() != null ) {
            result.put( dataType.getFileName(), dataType.getSource().getURI() );
          }
        }
      }
    }
    return result;
  }

  /**
   * Returns list of DataStagings entries that have Target element set (data
   * stagings out) in form of Strings map - mapping file name to its location
   * 
   * @return Map with data staging out file's name as a key and its location as
   *         a value
   */
  @SuppressWarnings("unchecked")
  public Map<String, String> getDataStagingOutStrings() {
    Map<String, String> result = new HashMap<String, String>();
    if( getJobDescription() != null ) {
      List<DataStagingType> temp = this.jobDescription.getDataStaging();
      if( temp != null ) {
        for( DataStagingType dataType : temp ) {
          if( dataType.getTarget() != null ) {
            result.put( dataType.getFileName(), dataType.getTarget().getURI() );
          }
        }
      }
    }
    return result;
  }

  /**
   * Returns list of those DataStagings entries that have Target element set
   * (data stgaings out)
   * 
   * @return list of DataStagings object with Target element set
   */
  @SuppressWarnings("unchecked")
  public List<DataStagingType> getDataStagingOut() {
    List<DataStagingType> result = new ArrayList<DataStagingType>();
    if( getJobDescription() != null ) {
      List<DataStagingType> temp = this.jobDescription.getDataStaging();
      if( temp != null ) {
        for( DataStagingType dataType : temp ) {
          if( dataType.getTarget() != null ) {
            result.add( dataType );
          }
        }
      }
    }
    return result;
  }

  /**
   * Method to remove all DataStagings elements from JSDL.
   */
  @SuppressWarnings("unchecked")
  public void removeDataStaging() {
    this.jobDescription.getDataStaging()
      .removeAll( this.jobDescription.getDataStaging() );
  }

  /**
   * Method to set "name" attribute of POSIXApplication element. Make sure that
   * POSIXApplication is set.
   * 
   * @param applicationName name of the application
   */
  public void setApplicationName( final String applicationName ) {
    createJobDescriptionCond();
    if( this.jobDescription.getApplication() == null ) {
      ApplicationType app = this.jsdlFactory.createApplicationType();
      this.jobDescription.setApplication( app );
    }
    this.jobDescription.getApplication().setApplicationName( applicationName );
  }

  /**
   * Method to access "name" attribute of the POSIXApplication element. Make
   * sure that POSIXApplication is set.
   * 
   * @return value of "name" attribute of POSIXApplication element.
   */
  public String getApplicationName() {
    String applicationName = null;
    JobDescriptionType description = getJobDescription();
    if( description != null ) {
      ApplicationType application = description.getApplication();
      applicationName = application.getApplicationName();
    }
    return applicationName;
  }

  /**
   * Method to access list of candidate hosts - that is content of CandidateHost
   * element of JSDL Resources entry.
   * 
   * @return List with names of candidate hosts as set in JSDL document
   */
  @SuppressWarnings("unchecked")
  public List<String> getCandidateHostsNames() {
    List<String> result = new ArrayList<String>();
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null
        && dRoot.getJobDefinition() != null
        && dRoot.getJobDefinition().getJobDescription() != null
        && dRoot.getJobDefinition().getJobDescription().getResources() != null
        && dRoot.getJobDefinition()
          .getJobDescription()
          .getResources()
          .getCandidateHosts() != null )
    {
      EList list = dRoot.getJobDefinition()
        .getJobDescription()
        .getResources()
        .getCandidateHosts()
        .getHostName();
      Iterator iterator = list.iterator();
      while( iterator.hasNext() ) {
        result.add( ( String )iterator.next() );
      }
    }
    return result;
  }

  /**
   * Method to check if any sweep element is present in this document. If so -
   * this means that JSDL is defined as a parametric document.
   * 
   * @return <code>true</code> if sweep element was defined, <code>false</code>
   *         otherwise
   */
  public boolean isParametric() {
    boolean parametric = false;
    if( this.jobDefinition != null ) {
      FeatureMap featureMap = this.jobDefinition.getAny();
      for( Entry entry : featureMap ) {
        if( entry.getValue() instanceof SweepTypeImpl ) {
          parametric = true;
          break;
        }
      }
    }
    return parametric;
  }

  /**
   * Method to clear (remove) Target elements from data stagings out entries.
   */
  @SuppressWarnings("unchecked")
  public void removeTargetsFromOutStaging() {
    if( getJobDescription() != null ) {
      List<DataStagingType> temp = this.jobDescription.getDataStaging();
      if( temp != null ) {
        for( DataStagingType dataType : temp ) {
          if( dataType.getTarget() != null ) {
            SourceTargetType target = this.jsdlFactory.createSourceTargetType();
            target.setURI( "" ); //$NON-NLS-1$
            dataType.setTarget( target );
          }
        }
      }
    }
  }

  /**
   * Adds a Argument element as a child of POSIXApplication element. Has no
   * effect if POSIXApplication was not set.
   * 
   * @param argName name of the argument to add
   */
  @SuppressWarnings("unchecked")
  public void addArgument( final String argName ) {
    POSIXApplicationType posixApp = getPosixApplication();
    ArgumentType arg;
    if( posixApp == null ) {
      try {
        posixApp = this.posixFactory.createPOSIXApplicationType();
        ApplicationType app = this.jobDescription.getApplication();
        if( app != null ) {
          EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( this.posixPackage );
          Entry e = FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "pOSIXApplication" ), //$NON-NLS-1$
                                                posixApp );
          app.getAny().add( e );
          posixApp = getPosixApplication();
        }
      } catch( Exception exc ) {
        // ignore
      }
    }
    if( posixApp != null ) {
      EList<ArgumentType> argumentList = posixApp.getArgument();
      if( argName != null && !argName.equals( "" ) ) { //$NON-NLS-1$
        arg = this.posixFactory.createArgumentType();
        arg.setValue( argName );
        argumentList.add( arg );
      }
    }
  }

  /**
   * Method to add parameter definition of a sweep changing with referenced
   * element (sweep on the same level). If JSDL is not parametric (no sweep
   * defined yet) or referenced sweep cannot be found - this method has no
   * effect.
   * 
   * @param refParamName name (XPath expression) of a sweeped element with which
   *          newly created sweep should change
   * @param sweepParamName name of JSDL element (XPath expression) for which new
   *          sweep will be created
   * @return newly created sweep element
   */
  public SweepType addSweepOnTheSameLevel( final String refParamName,
                                           final String sweepParamName )
  {
    SweepType result = null;
    if( isParametric() ) {
      // find ref sweep
      SweepType refSweep = findSweepWithParam( refParamName );
      if( refSweep != null ) {
        result = refSweep;
        addAssignment( result, sweepParamName );
        // add assignment to refSweep
      }
    }
    return result;
  }

  /**
   * Method to add parameter definition of a sweep changing for each change of
   * referenced element (inner sweep loop). If JSDL is not parametric (no sweep
   * defined yet) or referenced sweep cannot be found - this method has no
   * effect.
   * 
   * @param refParamName name (XPath expression) of a sweeped element for each
   *          change of which newly created sweep should change
   * @param sweepParamName name of JSDL element (XPath expression) for which new
   *          sweep will be created
   * @return newly created sweep element
   */
  public SweepType addInnerSweep( final String refParamName,
                                  final String sweepParamName )
  {
    SweepType result = null;
    if( isParametric() ) {
      // find ref sweep
      SweepType refSweep = findSweepWithParam( refParamName );
      if( refSweep != null ) {
        // create new sweep as its child
        result = createSweepType();
        refSweep.getSweep().add( result );
        // add assignment
        addAssignment( result, sweepParamName );
      }
    }
    return result;
  }

  /**
   * Method to add parameter definition of a sweep changing independently from
   * referenced element (values will be sweep after all values of referenced
   * parameter were substituted). </br>If JSDL is not parametric, referenced
   * element cannot be found or this method was called with <code>null</code> as
   * a refParamName - sweep on a root level will be created. This means that
   * JSDL will became parametric.
   * 
   * @param refParamName name (XPath expression) of a sweeped element from which
   *          newly created parameter should be independent
   * @param sweepParamName name of JSDL element (XPath expression) for which new
   *          sweep will be created
   * @return newly created sweep element
   */
  public SweepType addIndependentSweep( final String refParamName,
                                        final String sweepParamName )
  {
    SweepType result = null;
    boolean flag = false;
    if( isParametric() && refParamName != null ) {
      // find ref sweep
      SweepType refSweep = findSweepWithParam( refParamName );
      if( refSweep != null ) {
        flag = true;
        // create new sweep as child of refSweep parent
        result = createSweepType();
        if( refSweep.eContainer() instanceof JobDefinitionType ) {
          EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( SweepPackageImpl.eINSTANCE );
          Entry e = FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "sweep" ), //$NON-NLS-1$
                                                result );
          ( ( JobDefinitionType )refSweep.eContainer() ).getAny().add( e );
        } else if( refSweep.eContainer() instanceof SweepType ) {
          ( ( SweepType )refSweep.eContainer() ).getSweep().add( result );
        }
        // add assignment
        addAssignment( result, sweepParamName );
      }
    }
    if( !flag ) {
      // create rootSweep
      result = createSweepType();
      EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( SweepPackageImpl.eINSTANCE );
      Entry e = FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "sweep" ), //$NON-NLS-1$
                                            result );
      createJobDefinitionCond();
      this.jobDefinition.getAny().add( e );
      addAssignment( result, sweepParamName );
    }
    return result;
  }

  /**
   * Method for setting function defining sweep parameter values by enumeration
   * (listing them).
   * 
   * @param paramName name of a parameter (JSDL element, XPath expression) for
   *          which function is defined
   * @param values enumeration values of function
   */
  public void setEnumFunction( final String paramName, final List<String> values )
  {
    // find sweep
    AssignmentType assignment = findAssignmentType( paramName );
    // add values
    if( values != null && values.size() > 0 ) {
      FunctionsPackage pak = FunctionsPackageImpl.eINSTANCE;
      FunctionsFactory factory = pak.getFunctionsFactory();
      ValuesType valuesType = factory.createValuesType();
      for( String value : values ) {
        valuesType.getValue().add( value );
      }
      EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( pak );
      Entry e = FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "values" ), //$NON-NLS-1$
                                            valuesType );
      assignment.getFunctionGroup().add( e );
    } else {
      assignment.getFunctionGroup().clear();
    }
  }

  /**
   * Method for setting function defining sweep parameter values by integer
   * loop.
   * 
   * @param paramName name of a parameter (JSDL element, XPath expression) for
   *          which function is defined
   * @param start starting value of loop
   * @param end ending value of loop
   * @param step changing step of loop
   * @param exceptions values which will be excluded from values generated by
   *          this loop
   */
  @SuppressWarnings("unchecked")
  public void setLoopFunction( final String paramName,
                               final BigInteger start,
                               final BigInteger end,
                               final BigInteger step,
                               final List<BigInteger> exceptions )
  {
    // find sweep
    AssignmentType assignment = findAssignmentType( paramName );
    // add loop
    FunctionsPackage pak = FunctionsPackageImpl.eINSTANCE;
    FunctionsFactory factory = pak.getFunctionsFactory();
    LoopType loopType = factory.createLoopType();
    loopType.setStart( start );
    loopType.setEnd( end );
    loopType.setStep( step );
    if( exceptions != null ) {
      for( BigInteger except : exceptions ) {
        ExceptionType exc = factory.createExceptionType();
        exc.setValue( except );
        loopType.getException().add( exc );
      }
    }
    EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( pak );
    Entry e = FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "loop" ), //$NON-NLS-1$
                                          loopType );
    assignment.getFunctionGroup().add( e );
  }

  /**
   * Given EMF model representation of JSDL this method serialises it to File
   * (through EMF resources)
   * 
   * @param jsdlRoot EMF object representing JSDL document root. This is handler
   *          to JSDL content in terms of EMF.
   */
  protected void writeModelToFile( final EObject jsdlRoot ) {
    // Here you have to get the File's path...
    // This is where i couldn't do it , but with the
    // wizard this is easy to do.
    String filePath = this.fileResource.getAbsolutePath().toString();
    org.eclipse.emf.common.util.URI fileURI = org.eclipse.emf.common.util.URI.createFileURI( filePath );
    // Create resource set.
    ResourceSet resourceSet = new ResourceSetImpl();
    Registry factoryRegistry = resourceSet.getResourceFactoryRegistry();
    Map<String, Object> map = factoryRegistry.getExtensionToFactoryMap();
    map.put( "jsdl", //$NON-NLS-1$
             new JsdlResourceFactoryImpl() );
    this.resource = resourceSet.createResource( fileURI );
    if( jsdlRoot != null ) {
      this.resource.getContents().add( jsdlRoot );
    }
    // Setting XML encoding.. This could be changed later.
    Map<String, String> options = new HashMap<String, String>();
    options.put( XMLResource.OPTION_ENCODING, UTF_8 );
    try {
      this.resource.save( options );
    } catch( IOException ioEx ) {
      Activator.logException( ioEx );
    }
  }

  protected DocumentRoot getDocumentRoot() {
    if( this.documentRoot == null ) {
      try {
        updateModelFromFile();
      } catch( IOWrappedException e ) {
        // TODO Auto-generated catch block
        Activator.logException( e );
      }
    }
    return this.documentRoot;
  }

  private void createDocumentRootCond() {
    if( this.documentRoot == null ) {
      this.documentRoot = this.jsdlFactory.createDocumentRoot();
    }
  }

  private void createJobDefinitionCond() {
    createDocumentRootCond();
    if( this.documentRoot.getJobDefinition() == null ) {
      this.jobDefinition = this.jsdlFactory.createJobDefinitionType();
      this.documentRoot.setJobDefinition( this.jobDefinition );
    }
  }

  private void createJobDescriptionCond() {
    createJobDefinitionCond();
    if( this.jobDefinition.getJobDescription() == null ) {
      this.jobDescription = this.jsdlFactory.createJobDescriptionType();
      this.jobDefinition.setJobDescription( this.jobDescription );
    }
  }

  private void createJobIdentificationCond() {
    createJobDescriptionCond();
    if( this.jobDescription.getJobIdentification() == null ) {
      this.jobIdentification = this.jsdlFactory.createJobIdentificationType();
      this.jobDescription.setJobIdentification( this.jobIdentification );
    }
  }

  private java.net.URI findStagingAbsoluteUri( final String filename,
                                               final boolean stageOut )
    throws ProblemException
  {
    java.net.URI uri = null;
    Map<String, String> stagingsMap = stageOut
                                              ? getDataStagingOutStrings()
                                              : getDataStagingInStrings();
    try {
      if( stagingsMap != null ) {
        String currentFilename = filename;
        // if current uri is local, then check again if it's staged-out
        for( String currentUriString = stagingsMap.get( currentFilename ); uri == null
                                                                           && currentUriString != null; currentUriString = stagingsMap.get( currentFilename ) )
        {
          java.net.URI currentUri = new java.net.URI( currentUriString );
          String pathString = currentUri.getPath();
          if( pathString != null ) {
            IPath path = new Path( pathString );
            if( path.isAbsolute() ) {
              uri = currentUri;
            }
          }
          stagingsMap.remove( currentFilename ); // prevent endless iteration
          currentFilename = currentUriString;
        }
      }
    } catch( URISyntaxException exception ) {
      throw new ProblemException( "eu.geclipse.core.problem.net.malformedURL", //$NON-NLS-1$
                                  exception,
                                  String.format( Messages.getString( "errWrongUri" ), filename ) ); //$NON-NLS-1$
    }
    return uri;
  }

  private AssignmentType findAssignmentType( final String paramName ) {
    AssignmentType result = null;
    EList aList = findSweepWithParam( paramName ).getAssignment();
    for( int i = 0; i < aList.size(); i++ ) {
      EList pList = ( ( AssignmentType )aList.get( i ) ).getParameter();
      for( int j = 0; j < pList.size(); j++ ) {
        if( ( ( String )pList.get( j ) ).equalsIgnoreCase( paramName ) ) {
          result = ( AssignmentType )aList.get( i );
          break;
        }
      }
    }
    return result;
  }

  private SweepType createSweepType() {
    SweepType result = null;
    SweepPackage pak = SweepPackageImpl.eINSTANCE;
    SweepFactory factory = pak.getSweepFactory();
    result = factory.createSweepType();
    return result;
  }

  private SweepType findSweepWithParam( final String paramName ) {
    SweepType result = null;
    List<SweepType> resultSweep = new ArrayList<SweepType>();
    if( isParametric() ) {
      FeatureMap featureMap = this.jobDefinition.getAny();
      for( Entry entry : featureMap ) {
        if( entry.getValue() instanceof SweepTypeImpl ) {
          resultSweep.addAll( getSweepChildren( ( SweepType )entry.getValue() ) );
        }
      }
      for( SweepType sweep : resultSweep ) {
        EList list = sweep.getAssignment();
        for( int i = 0; i < list.size(); i++ ) {
          EList aList = ( ( AssignmentType )list.get( i ) ).getParameter();
          for( int j = 0; j < aList.size(); j++ ) {
            if( ( ( String )aList.get( j ) ).equalsIgnoreCase( paramName ) ) {
              result = sweep;
              break;
            }
          }
        }
      }
    }
    return result;
  }

  private AssignmentType addAssignment( final SweepType parentSweep,
                                        final String param )
  {
    AssignmentType assignment;
    SweepPackage pak = SweepPackageImpl.eINSTANCE;
    SweepFactory factory = pak.getSweepFactory();
    assignment = factory.createAssignmentType();
    assignment.getParameter().add( param );
    parentSweep.getAssignment().add( assignment );
    return assignment;
  }

  private List<SweepType> getSweepChildren( final SweepType rootSweep ) {
    List<SweepType> result = new ArrayList<SweepType>();
    EList list = rootSweep.getSweep();
    for( int i = 0; i < list.size(); i++ ) {
      result.addAll( getSweepChildren( ( SweepType )list.get( i ) ) );
    }
    result.add( rootSweep );
    return result;
  }
}
