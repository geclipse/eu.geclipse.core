/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *    Pawel Wolniewicz
 *    Katarzyna Bylec
 *****************************************************************************/
package eu.geclipse.core.model.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.jsdl.ApplicationType;
import eu.geclipse.jsdl.CreationFlagEnumeration;
import eu.geclipse.jsdl.DataStagingType;
import eu.geclipse.jsdl.DocumentRoot;
import eu.geclipse.jsdl.JobDefinitionType;
import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JobIdentificationType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;
import eu.geclipse.jsdl.RangeValueType;
import eu.geclipse.jsdl.SourceTargetType;
import eu.geclipse.jsdl.posix.ArgumentType;
import eu.geclipse.jsdl.posix.FileNameType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;
import eu.geclipse.jsdl.posix.PosixFactory;
import eu.geclipse.jsdl.posix.PosixPackage;
import eu.geclipse.jsdl.posix.impl.POSIXApplicationTypeImpl;
import eu.geclipse.jsdl.util.JsdlResourceFactoryImpl;

/**
 * Concrete implementation of an {@link IGridJobDescription} for the JSDL
 * language.
 */
public class JSDLJobDescription
    extends ResourceGridContainer
    implements IGridJobDescription {

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
  }


  /**
   * 
   * @param file
   */
  public void loadModel( final IFile file ) {
    String filePath = file.getFullPath().toString();
    URI uri = URI.createPlatformResourceURI( filePath );
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
      this.documentRoot.getJobDefinition();
    } catch( IOException e ) {
      Activator.logException( e );
    }
  }

  /**
   * Saves file to JSDL model.
   * @param file file to be saved
   */
  public void save( final IFile file ) {
    writeModelToFile( this.documentRoot, file );
  }

  /**
   * Get the content of the corresponding JSDL file.
   * 
   * @return The content of the file as a String.
   * @throws IOException If an error occures when loading the content.
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
  private void writeModelToFile( final EObject jsdlRoot, final IFile file )
  {
    try {
      // Here you have to get the File's path...
      // This is where i couldn't do it , but with the
      // wizard this is easy to do.
      String filePath = file.getFullPath().toString();
      URI fileURI = URI.createPlatformResourceURI( filePath );
      // Create resource set.
      ResourceSet resourceSet = new ResourceSetImpl();
      
      resourceSet.getResourceFactoryRegistry()
        .getExtensionToFactoryMap()
        .put( "jsdl", //$NON-NLS-1$
         new JsdlResourceFactoryImpl() );
      
      this.resource = resourceSet.createResource( fileURI );
      if( jsdlRoot != null ) {
        this.resource.getContents().add( jsdlRoot );
      }
      // Setting XML encoding.. This could be changed later.
      Map options = new HashMap();
      options.put( XMLResource.OPTION_ENCODING, "UTF-8" ); //$NON-NLS-1$
      this.resource.save( options );
    }
    // Catch exceptions.
    catch( Exception exception ) {
      Activator.logException( exception );
    }
  }

  protected void addContent() {
    // Using the Factory create all other elements.
    this.jobIdentification = this.jsdlFactory.createJobIdentificationType();
    this.jobDescription = this.jsdlFactory.createJobDescriptionType();
    this.jobDefinition = this.jsdlFactory.createJobDefinitionType();
    // Associate child element with parent element.
    // Remeber that JobDescription is contained within JobDefinition
    // and that JobIdentification is contained within JobDescription.
    this.jobDefinition.setJobDescription( this.jobDescription );
    this.jobDescription.setJobIdentification( this.jobIdentification );
  }

  /**
   * Method to create document root of JSDL file. This method should be called once, after JSDL is created.
   */
  public void createRoot() {
    this.documentRoot = this.jsdlFactory.createDocumentRoot();
    this.jobDefinition = this.jsdlFactory.createJobDefinitionType();
    this.documentRoot.setJobDefinition( this.jobDefinition );
  }

  /**
   * Method to add job description element to JSDL. This method must not be called before calling {@link JSDLJobDescription#createRoot()}
   */
  public void addJobDescription() {
    this.jobDescription = this.jsdlFactory.createJobDescriptionType();
    this.jobDefinition.setJobDescription( this.jobDescription );
  }

  /**
   * Method to add job identyfication element to JSDL. This method must not be called before calling {@link JSDLJobDescription#jobIdentification}
   * @param jobName
   * @param description
   */
  public void addJobIdentification( final String jobName, final String description ) {
    JobIdentificationType identyfication = this.jsdlFactory.createJobIdentificationType();
    if( !description.equals( "" ) ) { //$NON-NLS-1$
      identyfication.setDescription( description );
    } else {
      identyfication.setDescription( "default description" ); //$NON-NLS-1$
    }
    if( !jobName.equals( "" ) ) { //$NON-NLS-1$
      identyfication.setJobName( jobName );
    } else {
      identyfication.setJobName( "default job name" ); //$NON-NLS-1$
    }
    this.jobDescription.setJobIdentification( identyfication );
  }

  /**
   * Method to add application element to JSDL. This method must no be called before calling {@link JSDLJobDescription#jobDescription}
   */
  public void addApplication() {
    ApplicationType app = this.jsdlFactory.createApplicationType();
    this.jobDescription.setApplication( app );
  }

  /**
   * Method to add POSIX-specific information to created JSDL. This method must not be called before calling {@link JSDLJobDescription#addApplication()}
   * @param applicationName
   * @param executableFile
   * @param stdin
   * @param stdinName 
   * @param stdout 
   */
  @SuppressWarnings("unchecked")
  public void addPOSIXApplicationDetails( final String applicationName,
                                          final String executableFile,
                                          final String stdin, final String stdinName, final String stdout )
  {
    this.jobDescription.getApplication().setApplicationName( applicationName );
    POSIXApplicationType posixApp = this.posixFactory.createPOSIXApplicationType();
    FileNameType execFile = this.posixFactory.createFileNameType();
    execFile.setValue( executableFile );
    posixApp.setExecutable( execFile );
    if( stdin != null ) {
      FileNameType inputFile = this.posixFactory.createFileNameType();
      inputFile.setValue( stdin );
      posixApp.setInput( inputFile );
    }
    if( stdout != null ) {
      FileNameType outputFile = this.posixFactory.createFileNameType();
      outputFile.setValue( stdout );
      posixApp.setOutput( outputFile );
    }
    ApplicationType app = this.jobDescription.getApplication();
    EClass eClass = ExtendedMetaData.INSTANCE.getDocumentRoot( this.posixPackage );
    Entry e = org.eclipse.emf.ecore.util.FeatureMapUtil.createEntry( eClass.getEStructuralFeature( "pOSIXApplication" ), //$NON-NLS-1$
                                                                     posixApp );
    app.getAny().add( e );
    // when adding files for input and output - add them also to data staging
    if( stdin != null ) {
      DataStagingType dataIn = this.jsdlFactory.createDataStagingType();
      dataIn.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
      dataIn.setDeleteOnTermination( true );
      dataIn.setFileName( stdinName );
      SourceTargetType sourceDataIn = this.jsdlFactory.createSourceTargetType();
      sourceDataIn.setURI( stdin );
      dataIn.setSource( sourceDataIn );
      this.jobDescription.getDataStaging().add( dataIn );
    }
    if( stdout != null ) {
      DataStagingType dataOut = this.jsdlFactory.createDataStagingType();
      dataOut.setCreationFlag( CreationFlagEnumeration.OVERWRITE_LITERAL );
      dataOut.setDeleteOnTermination( true );
      dataOut.setFileName( stdin );
      SourceTargetType sourceDataOut = this.jsdlFactory.createSourceTargetType();
      sourceDataOut.setURI( stdout );
      dataOut.setTarget( sourceDataOut );
      this.jobDescription.getDataStaging().add( dataOut );
    }
  }

  /**
   * Method to add data staging element to JSDL with source element added to it as one of its children
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
    dataIn.setSource( sourceDataIn );
    this.jobDescription.getDataStaging().add( dataIn );
  }

  /**
   * Method to add data staging element to JSDL with target element added to it as one of its children
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
    dataOut.setTarget( sourceDataOut );
    this.jobDescription.getDataStaging().add( dataOut );
  }
  
  protected DocumentRoot getDocumentRoot(){
    if ( this.documentRoot == null ){
      this.loadModel( (IFile) this.getResource() );
    }
    return this.documentRoot;
  }
  
  protected POSIXApplicationType getPosixApplication(){
    POSIXApplicationType result = null;
    DocumentRoot dRoot = getDocumentRoot();
    if ( dRoot != null && dRoot.getJobDefinition() != null && dRoot.getJobDefinition().getJobDescription() != null && dRoot.getJobDefinition().getJobDescription().getApplication() != null){
//      dRoot.getJobDefinition().getJobDescription().getApplication().getAny().get( eClass.getEStructuralFeature( "pOSIXApplication" ), true );
      FeatureMap anyMap = dRoot.getJobDefinition().getJobDescription().getApplication().getAny();
      for (int i = 0; i < anyMap.size(); i++ ){
        if (anyMap.getValue( i ) instanceof POSIXApplicationTypeImpl){
          result = ( POSIXApplicationTypeImpl ) anyMap.getValue( i );
        }
      }    
    }
    return result;
  }
  
  /**
   * Accesses value of jsdl-posix:Executable element in jsdl-posix:Application
   * @return value of element jsdl-posix:Executable or null if not defined
   */
  public String getExecutable(){
    String result = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if (posixApp != null){
      result = posixApp.getExecutable().getValue();
    }
    return result;
  }
  
  /**
   * Accesses value of jsdl:Description element
   * @return value of jsdl:Description element, or null if not defined
   */
  public String getDescription(){
    String result = null;
    DocumentRoot dRoot = getDocumentRoot();
    if ( dRoot != null && dRoot.getJobDefinition() != null && dRoot.getJobDefinition().getJobDescription() != null && dRoot.getJobDefinition().getJobDescription().getJobIdentification() != null ){
              result = dRoot.getJobDefinition().getJobDescription().getJobIdentification().getDescription();
    }
    return result;
  }
  
  /**
   * Retruns list of values of jsdl-posix:Argument elements in jsdl-posix:Application
   * @return list of values of jsdl-posix:Argument or null if no jsdl-posix:Argument elements are defined
   */
  public List<String> getExecutableArguments(){
    ArrayList<String> result = null;
    POSIXApplicationType posixApp = getPosixApplication();
    if (posixApp != null && ! posixApp.getArgument().isEmpty()){
      result = new ArrayList<String>();
      for (Object arg: posixApp.getArgument()){
        result.add( ( ( ArgumentType )arg ).getValue() );
      }
    }
    return result;
  }
  
  /**
   * @return required cpu
   */
  public String getCpuArchitectureName() {
    String architectureNameString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null
        && dRoot.getCPUArchitecture() != null
        && dRoot.getCPUArchitecture().getCPUArchitectureName() != null )
    {
      architectureNameString = dRoot.getCPUArchitecture()
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
    if( dRoot != null && dRoot.getOperatingSystemName() != null ) {
      dRoot.getOperatingSystemName().getName();
    }
    return nameString;
  }

  /**
   * @return version of required operating system
   */
  public String getOSVersion() {
    String versionString = null;
    DocumentRoot dRoot = getDocumentRoot();
    if( dRoot != null ) {
      versionString = dRoot.getOperatingSystemVersion();
    }
    return versionString;
  }
  
  /**
   * Adds sequence of jsdl-posix:Argument elements as a children of jsdl-posix:Application
   * @param argName name of the argument
   * @param argValues list of values of the argument
   */
  @SuppressWarnings("unchecked")
  public void addArgumentForPosixApplication( final String argName, final ArrayList<String> argValues ){
    POSIXApplicationType posixApp = getPosixApplication();
    ArgumentType arg;
    if ( posixApp != null ){
      EList argumentList = posixApp.getArgument();
      arg = this.posixFactory.createArgumentType();
      arg.setValue( argName );
      argumentList.add( arg );
      for ( String value: argValues ){
        arg = this.posixFactory.createArgumentType();
        arg.setValue( value );
        argumentList.add( arg );
      }
    }
  }

  public String getInput() {
    String inputString = null;
    POSIXApplicationType posixApp = getPosixApplication();
    
    if( posixApp != null
        && posixApp.getInput() != null ) {      
      inputString = posixApp.getInput().getValue();
    }
    
    return inputString;
  }

  public String getOutput() {
    String outputString= null;
    POSIXApplicationType posixApp = getPosixApplication();
    
    if( posixApp != null
        && posixApp.getOutput() != null ) {
      outputString = posixApp.getOutput().getValue();
    }
    
    return outputString;  
  }
  
}
