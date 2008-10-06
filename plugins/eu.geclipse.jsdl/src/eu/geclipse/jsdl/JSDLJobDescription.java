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
 *    - Mathias Stuempert - initial API and implementation
 *    - Katarzyna Bylec (katis@man.poznan.pl)
 *    - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *****************************************************************************/
package eu.geclipse.jsdl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.DocumentRoot;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;

/**
 * Concrete implementation of an {@link IGridJobDescription} for the JSDL
 * language.
 */
public class JSDLJobDescription extends ResourceGridContainer
  implements IGridJobDescription
{

  private JSDLJobDescriptionModel internalJSDL;
  static {
    GridModel.addGridModelListener( createGridModelListener() );
  }

  /**
   * Create a new JSDL job description from the specified {@link IFile}.
   * 
   * @param file The file from which to create the description.
   */
  public JSDLJobDescription( final IFile file ) {
    super( file );
    try {
      this.internalJSDL = new JSDLJobDescriptionModel( new File( file.getLocation()
        .toOSString() ) );
    } catch( IOWrappedException e ) {
      // TODO katis
      e.printStackTrace();
    }
  }

  private static IGridModelListener createGridModelListener() {
    return new IGridModelListener() {

      public void gridModelChanged( final IGridModelEvent event ) {
        switch( event.getType() ) {
          case IGridModelEvent.ELEMENTS_CHANGED:
            if( event.getElements().length > 0 ) {
              IGridElement element = event.getElements()[ 0 ];
              if( element instanceof JSDLJobDescription ) {
                onJsdlChanged( event.getElements() );
              }
            }
          break;
        }
      }

      private void onJsdlChanged( final IGridElement[] elements ) {
        for( IGridElement gridElement : elements ) {
          if( gridElement instanceof JSDLJobDescription ) {
            JSDLJobDescription jsdlDescription = ( JSDLJobDescription )gridElement;
            try {
              jsdlDescription.updateModelFromResource();
            } catch( IOWrappedException e ) {
              // TODO Auto-generated catch block
              Activator.logException( e );
            }
          }
        }
      }
    };
  }

  /**
   * Updates the EMF JSDL model so that it matches the file content.
   * 
   * @throws IOWrappedException
   */
  void updateModelFromResource() throws IOWrappedException {
    this.internalJSDL.updateModelFromFile();
  }

  /**
   * Method to access root element of an EMF document represented by this
   * object.
   * 
   * @return root element of a JSDL document
   */
  public DocumentRoot getDocumentRoot() {
    return this.internalJSDL.getDocumentRoot();
  }

  /**
   * This method is to set EMF document's root element. If given root is empty
   * it creates JobDefinition and JobDescription JSDL elements.
   * 
   * @param root EMF document root to set as a JSDL root element
   */
  public void setRoot( final DocumentRoot root ) {
    this.internalJSDL.setRoot( root );
  }

  /**
   * Given EMF model representation of JSDL this method serialises it to File
   * (through EMF resources)
   * 
   * @param jsdlRoot EMF object representing JSDL document root. This is handler
   *          to JSDL content in terms of EMF.
   */
  public void save() {
    writeModelToFile( this.internalJSDL.getDocumentRoot() );
  }

  /**
   * Get the content of the corresponding JSDL file.
   * 
   * @return The content of the file as a String.
   * @throws IOException If an error occurs when loading the content.
   * @throws CoreException in case content of resource associated with JSDL is
   *           not accessible
   */
  public String getJSDLString() throws IOException, CoreException {
    return this.internalJSDL.getJSDLString();
  }

  // This method can be called to write the JSDL file to the project.
  // It get's as parameters the JobDefinition and the file name.
  // You can get the file name from the wizard.
  @SuppressWarnings("unchecked")
  private void writeModelToFile( final EObject jsdlRoot ) {
    this.internalJSDL.writeModelToFile( jsdlRoot );
  }

  /**
   * This method re-sets JSDL content. It creates empty parent JSDL elements -
   * JobDefinition, JobDescription and JobIdentification. If JSDL had those
   * elements before calling this method - they will be overwritten with new
   * ones (and their content - children elements - will be lost).
   */
  public void setUpBasicJSDLStructure() {
    this.internalJSDL.setUpBasicJSDLStructure();
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
    this.internalJSDL.setJobIdentification( jobName, description );
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
    this.internalJSDL.setPOSIXApplicationDetails( applicationName,
                                                  executableFile,
                                                  stdin,
                                                  stdinName,
                                                  stdout,
                                                  outName,
                                                  err,
                                                  errName );
  }

  /**
   * Adds JSDL DataStaging element for staged out resources.
   * 
   * @param name name of the resource
   * @param path URI representing resource's location
   */
  public void addDataStagingOut( final String name, final String path ) {
    this.internalJSDL.addDataStagingOut( name, path );
  }

  /**
   * Sets JSDL DataStaging element for staged in resources.
   * 
   * @param name name of the resource
   * @param path URI representing resource's location
   */
  @SuppressWarnings("unchecked")
  public void addDataStagingIn( final String name, final String path ) {
    this.internalJSDL.addDataStagingIn( name, path );
  }

  protected POSIXApplicationType getPosixApplication() {
    return this.internalJSDL.getPosixApplication();
  }

  public String getExecutable() {
    return this.internalJSDL.getExecutable();
  }

  public String getDescription() {
    return this.internalJSDL.getDescription();
  }

  public List<String> getExecutableArguments() {
    return this.internalJSDL.getExecutableArguments();
  }

  /**
   * Method to access CPUArchitecture name. This is String value or Resources
   * child element.
   * 
   * @return content of CPUArchitecture element
   */
  public String getCpuArchitectureName() {
    return this.internalJSDL.getCpuArchitectureName();
  }

  /**
   * Method to access OperatingSystem name. This is String value or Resources
   * child element.
   * 
   * @return content of OperatingSystem > OperatingSystemType >
   *         OperatingSystemName element
   */
  public String getOSTypeName() {
    return this.internalJSDL.getOSTypeName();
  }

  /**
   * Method to access OperatingSystem version. This is String value or Resources
   * child element.
   * 
   * @return content of OperatingSystem > OperatingSystemVersion element
   */
  public String getOSVersion() {
    return this.internalJSDL.getOSVersion();
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
    this.internalJSDL.addArgumentForPosixApplication( argName, argValues );
  }

  /**
   * Adds a Argument element as a child of POSIXApplication element. Has no
   * effect if POSIXApplication was not set.
   * 
   * @param argName name of the argument to add
   */
  @SuppressWarnings("unchecked")
  public void addArgument( final String argName ) {
    this.internalJSDL.addArgument( argName );
  }

  public String getStdInputFileName() {
    return this.internalJSDL.getStdInputFileName();
  }

  public String getStdOutputFileName() {
    return this.internalJSDL.getStdOutputFileName();
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
    this.internalJSDL.addCandidateHosts( hostsList );
  }

  /**
   * Returns list of those DataStagings entries that have Source element set
   * (data stagings in)
   * 
   * @return list of DataStagings object with Source element set
   */
  @SuppressWarnings("unchecked")
  public List<DataStagingType> getDataStagingIn() {
    return this.internalJSDL.getDataStagingIn();
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
    return this.internalJSDL.getDataStagingInStrings();
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
    return this.internalJSDL.getDataStagingOutStrings();
  }

  /**
   * Returns list of those DataStagings entries that have Target element set
   * (data stgaings out)
   * 
   * @return list of DataStagings object with Target element set
   */
  @SuppressWarnings("unchecked")
  public List<DataStagingType> getDataStagingOut() {
    return this.internalJSDL.getDataStagingOut();
  }

  /**
   * Method to remove all DataStagings elements from JSDL.
   */
  public void removeDataStaging() {
    this.internalJSDL.removeDataStaging();
  }

  public java.net.URI getStdOutputUri() throws ProblemException {
    return this.internalJSDL.getStdOutputUri();
  }

  public java.net.URI getStdInputUri() throws ProblemException {
    return this.internalJSDL.getStdInputUri();
  }

  public String getStdErrorFileName() {
    return this.internalJSDL.getStdErrorFileName();
  }

  public java.net.URI getStdErrorUri() throws ProblemException {
    return this.internalJSDL.getStdErrorUri();
  }

  /**
   * Method to set "name" attribute of POSIXApplication element. Make sure that
   * POSIXApplication is set.
   * 
   * @param applicationName name of the application
   */
  public void setApplicationName( final String applicationName ) {
    this.internalJSDL.setApplicationName( applicationName );
  }

  /**
   * Method to access "name" attribute of the POSIXApplication element. Make
   * sure that POSIXApplication is set.
   * 
   * @return value of "name" attribute of POSIXApplication element.
   */
  public String getApplicationName() {
    return this.internalJSDL.getApplicationName();
  }

  /**
   * Returns EMF XML document as a DOM XML Document
   * 
   * @return DOM Document representing EMF's XML content
   */
  public Document getXml() {
    Document doc = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware( true );
      doc = factory.newDocumentBuilder()
        .parse( ( ( IFile )getResource() ).getContents() );
    } catch( SAXException exception ) {
      Activator.logException( exception );
    } catch( IOException exception ) {
      Activator.logException( exception );
    } catch( ParserConfigurationException exception ) {
      Activator.logException( exception );
    } catch( CoreException exception ) {
      Activator.logException( exception );
    }
    return doc;
  }

  /**
   * Method to access list of candidate hosts.
   * 
   * @return List of Strings with names of candidate hosts
   */
  public List<String> getCandidateHostsNames() {
    return this.internalJSDL.getCandidateHostsNames();
  }

  /**
   * @return true if this job has parametric extension (sweep)
   */
  public boolean isParametric() {
    return this.internalJSDL.isParametric();
  }

  public void removeTargetsFromOutStaging() {
    this.internalJSDL.removeTargetsFromOutStaging();
  }
}
