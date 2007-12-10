/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Mathias Stuempert - initial API and implementation Pawel Wolniewicz Katarzyna
 * Bylec
 ******************************************************************************/
package eu.geclipse.jsdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;

import eu.geclipse.core.CoreProblems;
import eu.geclipse.core.GridException;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.jsdl.internal.JsdlAdaptersPlugin;
import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.BoundaryType;
import eu.geclipse.jsdl.model.CPUArchitectureType;
import eu.geclipse.jsdl.model.CandidateHostsType;
import eu.geclipse.jsdl.model.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.DocumentRoot;
import eu.geclipse.jsdl.model.ExactType;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JobIdentificationType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.OperatingSystemType;
import eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.ProcessorArchitectureEnumeration;
import eu.geclipse.jsdl.model.RangeType;
import eu.geclipse.jsdl.model.RangeValueType;
import eu.geclipse.jsdl.model.ResourcesType;
import eu.geclipse.jsdl.model.SourceTargetType;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.impl.POSIXApplicationTypeImpl;
import eu.geclipse.jsdl.model.util.JsdlResourceFactoryImpl;

/**
 * Concrete implementation of an {@link IGridJobDescription} for the JSDL
 * language.
 */
public class JSDLJobDescription extends ResourceGridContainer
  implements IGridJobDescription
{

  private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$
  /**
   * This caches an instance of the model package. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   */
  protected JsdlPackage jsdlPackage = JsdlPackage.eINSTANCE;
  protected PosixPackage posixPackage = PosixPackage.eINSTANCE;
  /**
   * This caches an instance of the model factory. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   */
  protected JsdlFactory jsdlFactory = this.jsdlPackage.getJsdlFactory();
  protected PosixFactory posixFactory = this.posixPackage.getPosixFactory();
  private Resource resource = null;
  // This are declarations for each element.
  // The root element is jobDefinition.
  private JobDefinitionType jobDefinition;
  private JobDescriptionType jobDescription;
  private JobIdentificationType jobIdentification;
  private DocumentRoot documentRoot;

  /**
   * Create a new JSDL job description from the specified {@link IFile}.
   * 
   * @param file The file from which to create the description.
   */
  public JSDLJobDescription( final IFile file ) {
    super( file );
    try {
      if( file.getContents().read() != -1 ) {
        loadModel( file );
      }
    } catch( CoreException e ) {
      // TODO katis - error handling
    } catch( IOException e ) {
      // TODO katis - error handling
    }
  }

  /**
   * @param file
   */
  public void loadModel( final IFile file ) {
    String filePath = file.getFullPath().toString();
    URI uri = URI.createPlatformResourceURI( filePath, false );
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
      JsdlAdaptersPlugin.logException( ioEx );
    }
  }

  /**
   * Method to access root element of an EMF document represented by this
   * object.
   * 
   * @return root element of a JSDL document
   */
  public DocumentRoot getRoot() {
    return this.documentRoot;
  }

  /**
   * Method to set
   * 
   * @param root
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
   * Saves file to JSDL model.
   * 
   * @param file file to be saved
   */
  public void save( final IFile file ) {
    writeModelToFile( this.documentRoot, file );
  }

  /**
   * Get the content of the corresponding JSDL file.
   * 
   * @return The content of the file as a String.
   * @throws IOException If an error occurs when loading the content.
   */
  public String getJSDLString() throws IOException {
    String jsdl = null;
    File jsdlFile = this.getPath().toFile();
    FileInputStream jsdlStream = null;
    jsdlStream = new FileInputStream( jsdlFile );
    Reader reader = new InputStreamReader( jsdlStream );
    BufferedReader jsdlReader = new BufferedReader( reader );
    String line;
    while( null != ( line = jsdlReader.readLine() ) ) {
      jsdl += line;
    }
    return jsdl;
  }

  // This method can be called to write the JSDL file to the project.
  // It get's as parameters the JobDefinition and the file name.
  // You can get the file name from the wizard.
  @SuppressWarnings("unchecked")
  private void writeModelToFile( final EObject jsdlRoot, final IFile file ) {
    // Here you have to get the File's path...
    // This is where i couldn't do it , but with the
    // wizard this is easy to do.
    String filePath = file.getFullPath().toString();
    URI fileURI = URI.createPlatformResourceURI( filePath, false );
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
    Map options = new HashMap();
    options.put( XMLResource.OPTION_ENCODING, UTF_8 );
    try {
      this.resource.save( options );
    } catch( IOException ioEx ) {
      JsdlAdaptersPlugin.logException( ioEx );
    }
  }

  protected void addContent() {
    // Using the Factory create all other elements.
    this.jobIdentification = this.jsdlFactory.createJobIdentificationType();
    this.jobDescription = this.jsdlFactory.createJobDescriptionType();
    this.jobDefinition = this.jsdlFactory.createJobDefinitionType();
    // Associate child element with parent element.
    // Remember that JobDescription is contained within JobDefinition
    // and that JobIdentification is contained within JobDescription.
    this.jobDefinition.setJobDescription( this.jobDescription );
    this.jobDescription.setJobIdentification( this.jobIdentification );
  }

  /**
   * Method to create document root of JSDL file. This method should be called
   * once, after JSDL is created.
   */
  public void createRoot() {
    this.documentRoot = this.jsdlFactory.createDocumentRoot();
    this.jobDefinition = this.jsdlFactory.createJobDefinitionType();
    this.documentRoot.setJobDefinition( this.jobDefinition );
  }

  /**
   * Method to add job description element to JSDL. This method must not be
   * called before calling {@link JSDLJobDescription#createRoot()}
   */
  public void addJobDescription() {
    this.jobDescription = this.jsdlFactory.createJobDescriptionType();
    this.jobDefinition.setJobDescription( this.jobDescription );
  }

  /**
   * Method to add job identification element to JSDL. This method must not be
   * called before calling {@link JSDLJobDescription#jobIdentification}
   * 
   * @param jobName
   * @param description
   */
  public void addJobIdentification( final String jobName,
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
   * Method to add application element to JSDL. This method must no be called
   * before calling {@link JSDLJobDescription#jobDescription}
   */
  public void addApplication() {
    ApplicationType app = this.jsdlFactory.createApplicationType();
    this.jobDescription.setApplication( app );
  }

  /**
   * Method to add POSIX-specific information to created JSDL. This method must
   * not be called before calling {@link JSDLJobDescription#addApplication()}
   * 
   * @param applicationName
   * @param executableFile
   * @param stdin
   * @param stdinName
   * @param stdout
   * @param outName
   * @param err
   * @param errName
   */
  @SuppressWarnings("unchecked")
  public void addPOSIXApplicationDetails( final String applicationName,
                                          final String executableFile,
                                          final String stdin,
                                          final String stdinName,
                                          final String stdout,
                                          final String outName,
                                          final String err,
                                          final String errName )
  {
    this.jobDescription.getApplication().setApplicationName( applicationName );
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
    if (err != null){
      FileNameType errorFile = this.posixFactory.createFileNameType();
      errorFile.setValue( errName );
      posixApp.setError( errorFile );
    }
    ApplicationType app = this.jobDescription.getApplication();
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
    if (err != null){
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
   * Method to add data staging element to JSDL with source element added to it
   * as one of its children
   * 
   * @param name
   * @param path
   */
  @SuppressWarnings("unchecked")
  public void setOutDataStaging( final String name, final String path ) {
    DataStagingType dataIn = this.jsdlFactory.createDataStagingType();
    dataIn.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
    dataIn.setDeleteOnTermination( true );
    dataIn.setFileName( name );
    SourceTargetType sourceDataIn = this.jsdlFactory.createSourceTargetType();
    sourceDataIn.setURI( path );
    dataIn.setTarget( sourceDataIn );
    this.jobDescription.getDataStaging().add( dataIn );
  }

  /**
   * Method to add data staging element to JSDL with target element added to it
   * as one of its children
   * 
   * @param name
   * @param path
   */
  @SuppressWarnings("unchecked")
  public void setInDataStaging( final String name, final String path ) {
    DataStagingType dataOut = this.jsdlFactory.createDataStagingType();
    dataOut.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
    dataOut.setDeleteOnTermination( true );
    dataOut.setFileName( name );
    SourceTargetType sourceDataOut = this.jsdlFactory.createSourceTargetType();
    sourceDataOut.setURI( path );
    dataOut.setSource( sourceDataOut );
    this.jobDescription.getDataStaging().add( dataOut );
  }

  protected DocumentRoot getDocumentRoot() {
    if( this.documentRoot == null ) {
      this.loadModel( ( IFile )this.getResource() );
    }
    return this.documentRoot;
  }

  protected POSIXApplicationType getPosixApplication() {
    POSIXApplicationType result = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null
        && getJobDescription( dRoot ).getApplication() != null )
    {
      // dRoot.getJobDefinition().getJobDescription().getApplication().getAny().get(
      // eClass.getEStructuralFeature( "pOSIXApplication" ), true );
      FeatureMap anyMap = getJobDescription( dRoot ).getApplication().getAny();
      for( int i = 0; i < anyMap.size(); i++ ) {
        if( anyMap.getValue( i ) instanceof POSIXApplicationTypeImpl ) {
          result = ( POSIXApplicationTypeImpl )anyMap.getValue( i );
        }
      }
    }
    return result;
  }

  /**
   * Accesses value of jsdl-posix:Executable element in jsdl-posix:Application
   * 
   * @return value of element jsdl-posix:Executable or null if not defined
   */
  public String getExecutable() {
    String result = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getExecutable() != null ) {
      result = posixApp.getExecutable().getValue();
    }
    return result;
  }

  /**
   * Accesses value of jsdl:Description element
   * 
   * @return value of jsdl:Description element, or null if not defined
   */
  public String getDescription() {
    String result = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null
        && getJobDescription( dRoot ).getJobIdentification() != null )
    {
      result = getJobDescription( dRoot ).getJobIdentification()
        .getDescription();
    }
    return result;
  }

  /**
   * Returns list of values of jsdl-posix:Argument elements in
   * jsdl-posix:Application
   * 
   * @return list of values of jsdl-posix:Argument or null if no
   *         jsdl-posix:Argument elements are defined
   */
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

  /**
   * @return required CPU architecture
   */
  public String getCpuArchitectureName() {
    String architectureNameString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null
        && getJobDescription( dRoot ).getResources() != null
        && getJobDescription( dRoot ).getResources().getCPUArchitecture() != null
        && getJobDescription( dRoot ).getResources()
          .getCPUArchitecture()
          .getCPUArchitectureName() != null )
    {
      architectureNameString = getJobDescription( dRoot ).getResources()
        .getCPUArchitecture()
        .getCPUArchitectureName()
        .getName();
    }
    return architectureNameString;
  }

  /**
   * @return name of required filesystem
   */
  public String getFilesystemType() {
    String typeString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null && dRoot.getFileSystemType() != null ) {
      typeString = dRoot.getFileSystemType().getName();
    }
    return typeString;
  }

  /**
   * @return requirements for filesystem mount point
   */
  public String getFilesystemMountPoint() {
    String mountPointString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null ) {
      mountPointString = getDocumentRoot().getMountPoint();
    }
    return mountPointString;
  }

  /**
   * @return requirements for diskspace
   */
  public RangeValueType getFilesystemDiskSpace() {
    RangeValueType diskSpaceValue = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null && dRoot.getFileSystem() != null ) {
      diskSpaceValue = dRoot.getFileSystem().getDiskSpace();
    }
    return diskSpaceValue;
  }

  /**
   * @return name of required operating system
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
        && getJobDescription( dRoot ).getResources()
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
   * @return version of required operating system
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
   * Adds sequence of jsdl-posix:Argument elements as a children of
   * jsdl-posix:Application
   * 
   * @param argName name of the argument
   * @param argValues list of values of the argument
   */
  @SuppressWarnings("unchecked")
  public void addArgumentForPosixApplication( final String argName,
                                              final ArrayList<String> argValues )
  {
    POSIXApplicationType posixApp = getPosixApplication();
    ArgumentType arg;
    if( argValues != null && !argValues.isEmpty() ) {
      if( posixApp != null ) {
        EList argumentList = posixApp.getArgument();
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
   * Adds a jsdl-posix:Argument element as a child of jsdl-posix:Application
   * 
   * @param argName name of the argument to add
   */
  @SuppressWarnings("unchecked")
  public void addArgument( final String argName ) {
    POSIXApplicationType posixApp = getPosixApplication();
    ArgumentType arg;
    if( posixApp != null ) {
      EList argumentList = posixApp.getArgument();
      if( argName != null && !argName.equals( "" ) ) { //$NON-NLS-1$
        arg = this.posixFactory.createArgumentType();
        arg.setValue( argName );
        argumentList.add( arg );
      }
    }
  }

  public String getStdInputFileName() {
    String inputString = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getInput() != null ) {
      inputString = posixApp.getInput().getValue();
    }
    return inputString;
  }

  public String getStdOutputFileName() {
    String outputString = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getOutput() != null ) {
      outputString = posixApp.getOutput().getValue();
    }
    return outputString;
  }

  /**
   * @return list of possible OS
   */
  public static List<String> getOSTypes() {
    ArrayList<String> result = new ArrayList<String>();
    for( Object value : OperatingSystemTypeEnumeration.VALUES ) {
      result.add( value.toString() );
    }
    return result;
  }

  /**
   * @return list of possible CPU architectures
   */
  public static List<String> getCPUArchitectures() {
    ArrayList<String> result = new ArrayList<String>();
    for( Object value : ProcessorArchitectureEnumeration.VALUES ) {
      result.add( value.toString() );
    }
    return result;
  }

  /**
   * @param cpu - required cpu architecture
   */
  public void setCPUArchitecture( final String cpu ) {
    final DocumentRoot dRoot = getDocumentRoot();
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
      final CPUArchitectureType cpuArch = this.jsdlFactory.createCPUArchitectureType();
      cpuArch.setCPUArchitectureName( ProcessorArchitectureEnumeration.getByName( cpu ) );
      resources.setCPUArchitecture( cpuArch );
    }
  }

  /**
   * @param start - minimum number of cpu
   * @param end - maximum number of cpu
   * @param exclusive - treat above range as closed (valid value can be also
   *            start or end)
   */
  @SuppressWarnings("unchecked")
  public void setTotalCPUCount( final double start,
                                final double end,
                                final boolean exclusive )
  {
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
      RangeValueType rangeValue = resources.getTotalCPUCount();
      if( rangeValue == null ) {
        rangeValue = this.jsdlFactory.createRangeValueType();
        resources.setTotalCPUCount( rangeValue );
      }
      RangeType range = this.jsdlFactory.createRangeType();
      BoundaryType bl = this.jsdlFactory.createBoundaryType();
      bl.setExclusiveBound( exclusive );
      bl.setValue( start );
      range.setLowerBound( bl );
      bl = this.jsdlFactory.createBoundaryType();
      bl.setExclusiveBound( exclusive );
      bl.setValue( end );
      range.setUpperBound( bl );
      rangeValue.getRange().add( range );
    }
  }

  /**
   * @param hostsList - list of hosts, which are candidate to run jub
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
   * @param osList - list of operating systems, on which job can run
   */
  public void setOS( final String osList ) {
    final DocumentRoot dRoot = getDocumentRoot();
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
      OperatingSystemType osType = resources.getOperatingSystem();
      if( osType == null ) {
        osType = this.jsdlFactory.createOperatingSystemType();
        resources.setOperatingSystem( osType );
      }
      final OperatingSystemTypeType osInstance = this.jsdlFactory.createOperatingSystemTypeType();
      osInstance.setOperatingSystemName( OperatingSystemTypeEnumeration.getByName( osList ) );
      osType.setOperatingSystemType( osInstance );
    }
  }

  /**
   * @param start - minimum CPU speed
   * @param end - maximum CPU speed
   * @param exclusive - should above range be trated as closed (start / end
   *            value is valid value)
   */
  @SuppressWarnings("unchecked")
  public void setInidividialCPUSpeedRange( final double start,
                                           final double end,
                                           final boolean exclusive )
  {
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
      RangeValueType rangeValue = resources.getIndividualCPUSpeed();
      if( rangeValue == null ) {
        rangeValue = this.jsdlFactory.createRangeValueType();
        resources.setIndividualCPUSpeed( rangeValue );
      }
      RangeType range = this.jsdlFactory.createRangeType();
      BoundaryType bl = this.jsdlFactory.createBoundaryType();
      bl.setExclusiveBound( exclusive );
      bl.setValue( start );
      range.setLowerBound( bl );
      bl = this.jsdlFactory.createBoundaryType();
      bl.setExclusiveBound( exclusive );
      bl.setValue( end );
      range.setUpperBound( bl );
      rangeValue.getRange().add( range );
    }
  }

  /**
   * @param start - minimum amount of memory
   * @param end - maximum amount of memory
   * @param exclusive - should above range be closed (then start and end value
   *            is valid value)
   */
  @SuppressWarnings("unchecked")
  public void setTotalPhysicalMemory( final double start,
                                      final double end,
                                      final boolean exclusive )
  {
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
      RangeValueType rangeValue = resources.getTotalPhysicalMemory();
      if( rangeValue == null ) {
        rangeValue = this.jsdlFactory.createRangeValueType();
        resources.setTotalPhysicalMemory( rangeValue );
      }
      RangeType range = this.jsdlFactory.createRangeType();
      BoundaryType bl = this.jsdlFactory.createBoundaryType();
      bl.setExclusiveBound( exclusive );
      bl.setValue( start );
      range.setLowerBound( bl );
      bl = this.jsdlFactory.createBoundaryType();
      bl.setExclusiveBound( exclusive );
      bl.setValue( end );
      range.setUpperBound( bl );
      rangeValue.getRange().add( range );
    }
  }

  /**
   * Set required CPU to: value +/- epsilon
   * 
   * @param value - CPU speed
   * @param epsilon - value of which CPU speed can be bigger or smaller
   */
  @SuppressWarnings("unchecked")
  public void setIndividualCPUSpeedValue( final double value,
                                          final double epsilon )
  {
    final DocumentRoot dRoot = getDocumentRoot();
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
      RangeValueType rangeValue = resources.getIndividualCPUSpeed();
      if( rangeValue == null ) {
        rangeValue = this.jsdlFactory.createRangeValueType();
        resources.setIndividualCPUSpeed( rangeValue );
      }
      final ExactType exact = this.jsdlFactory.createExactType();
      exact.setValue( value );
      exact.setEpsilon( epsilon );
      rangeValue.getExact().add( exact );
    }
  }

  /**
   * Set valid CPU count to: value +/- epsilon
   * 
   * @param value - how many cpu are required to start job
   * @param epsilon - how much cpu can differs from value
   */
  @SuppressWarnings("unchecked")
  public void setTotalCPUCountValue( final double value, final double epsilon )
  {
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
      RangeValueType rangeValue = resources.getTotalCPUCount();
      if( rangeValue == null ) {
        rangeValue = this.jsdlFactory.createRangeValueType();
        resources.setTotalCPUCount( rangeValue );
      }
      ExactType exact = this.jsdlFactory.createExactType();
      exact.setValue( value );
      exact.setEpsilon( epsilon );
      rangeValue.getExact().add( exact );
    }
  }

  /**
   * Set valid memory amount to: value +/- epsilon
   * 
   * @param value
   * @param epsilon
   */
  @SuppressWarnings("unchecked")
  public void setTotalPhysicalMemoryValue( final double value,
                                           final double epsilon )
  {
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
      RangeValueType rangeValue = resources.getTotalPhysicalMemory();
      if( rangeValue == null ) {
        rangeValue = this.jsdlFactory.createRangeValueType();
        resources.setTotalPhysicalMemory( rangeValue );
      }
      ExactType exact = this.jsdlFactory.createExactType();
      exact.setValue( value );
      exact.setEpsilon( epsilon );
      rangeValue.getExact().add( exact );
    }
  }

  // helping methods
  // ////////////////
  private JobDescriptionType getJobDescription( final DocumentRoot dRoot ) {
    JobDescriptionType result = null;
    if( dRoot != null && dRoot.getJobDefinition() != null ) {
      result = dRoot.getJobDefinition().getJobDescription();
    }
    return result;
  }

  /**
   * Method to access data staging in for this JSDL document
   * 
   * @return List of {@link DataStagingType}
   */
  @SuppressWarnings("unchecked")
  public List<DataStagingType> getDataStagingIn() {
    List<DataStagingType> result = new ArrayList<DataStagingType>();
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null ) {
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
   * @return pairs (filename on CE, source-uri) for staged-in files
   */
  @SuppressWarnings("unchecked")
  public Map<String, String> getDataStagingInStrings() {
    Map<String, String> result = new HashMap<String, String>();
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null ) {
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
   * @return pairs (filename on CE, target-uri) for staged-out files
   */
  public Map<String, String> getDataStagingOutStrings() {
    Map<String, String> result = new HashMap<String, String>();
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null ) {
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
   * Method to access data staging out for this JSDL document
   * 
   * @return List of {@link DataStagingType}
   */
  @SuppressWarnings("unchecked")
  public List<DataStagingType> getDataStagingOut() {
    List<DataStagingType> result = new ArrayList<DataStagingType>();
    DocumentRoot dRoot = getDocumentRoot();
    if( getJobDescription( dRoot ) != null ) {
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
   * The same as {@link JSDLJobDescription#getDataStagingIn()}, but returns
   * only those files which are local to user's computer
   * 
   * @return List of {@link DataStagingType}
   */
  public List<DataStagingType> getLocalDataStagingIn() {
    List<DataStagingType> result = new ArrayList<DataStagingType>();
    for( DataStagingType dataType : getDataStagingIn() ) {
      try {
        java.net.URI testURI = new java.net.URI( dataType.getSource().getURI() );
        // if( testURI != null && testURI.getQuery() != null ) {
        // if( testURI.getQuery().indexOf( "geclslave=file" ) != -1 ) {
        // result.add( dataType );
        // }
        // }
        if( testURI != null ) {
          if( testURI.getScheme().equals( "file" ) ) { //$NON-NLS-1$
            result.add( dataType );
          }
        }
      } catch( URISyntaxException e ) {
        // TODO katis
      }
    }
    return result;
  }

  /**
   * If there is an executable file that's supposed to be stage in - this method
   * will return it
   * 
   * @return {@link DataStagingType} representing file to execute. If command to
   *         execute is local to execution machine - it will return null
   */
  public DataStagingType getStdInputDataType() {
    DataStagingType result = null;
    if( getStdInputFileName() != null ) {
      for( DataStagingType dataType : getDataStagingIn() ) {
        if( dataType.getFileName().equals( getStdInputFileName() ) ) {
          result = dataType;
        }
      }
    }
    return result;
  }

  public void removeDataStaging() {
    this.jobDescription.getDataStaging()
      .removeAll( this.jobDescription.getDataStaging() );
  }

  public void addDataStagingType( final DataStagingType data ) {
    this.jobDescription.getDataStaging().add( data );
  }
  
  private java.net.URI findStagingAbsoluteUri( final String filename,
                                               final boolean stageOut )
    throws GridException
  {
    java.net.URI uri = null;
    Map<String, String> stagingsMap = stageOut
                                              ? getDataStagingOutStrings()
                                              : getDataStagingInStrings();
    try {
      if( stagingsMap != null ) {
        String currentFilename = filename;
        // if current uri is local, then check again if it's staged-out
        for( String currentUriString = stagingsMap.get( currentFilename );
              uri == null && currentUriString != null; 
              currentUriString = stagingsMap.get( currentFilename ) )
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
      throw new GridException( CoreProblems.MALFORMED_URL,
                               exception,
                               String.format( Messages.getString( "errWrongUri" ), filename ) ); //$NON-NLS-1$
    }
    return uri;
  }
  
  public java.net.URI getStdOutputUri() throws GridException {
    java.net.URI uri = null;
    String stdOutputFileName = getStdOutputFileName();
    if( stdOutputFileName != null ) {
      uri = findStagingAbsoluteUri( stdOutputFileName, true );
    }
    return uri;
  }

  public java.net.URI getStdInputUri() throws GridException {
    java.net.URI uri = null;
    String stdInputFileName = getStdInputFileName();
    if( stdInputFileName != null ) {
      uri = findStagingAbsoluteUri( stdInputFileName, false );
    }
    return uri;
  }

  public String getStdErrorFileName() {
    String errorFilename = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if( posixApp != null && posixApp.getError() != null ) {
      errorFilename = posixApp.getError().getValue();
    }
    return errorFilename;
  }

  public java.net.URI getStdErrorUri() throws GridException {
    java.net.URI uri = null;
    String stdErrFileName = getStdErrorFileName();
    if( stdErrFileName != null ) {
      uri = findStagingAbsoluteUri( stdErrFileName, true );
    }
    return uri;
  }

  public void setApplicationName( String applicationName ) {
    this.jobDescription.getApplication().setApplicationName( applicationName );
    
  }
  
}
