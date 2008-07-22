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
package eu.geclipse.servicejob.parsers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.Activator;

/**
 * Helper class for writing to GTDL file.
 */
public class GTDLWriter {

  /**
   * Path (within plug-in location) to GTDL skeleton (file containing GTDL XML
   * structure).
   */
  public static final String SKELETON_LOCATION = "xml/GTDL_no_Output.xml"; //$NON-NLS-1$

  /**
   * Method that takes empty GTDL skeleton, files it out with plug-in specific
   * content (plug-in id and initial test's data) and transforms it to input
   * stream. Method transforms GTDL skeleton file (file containing GTDL XML
   * structure)
   * 
   * @param plugInID id of a plug-in that provides test's data
   * @param testedResources list of names of resources to test
   * @param testInputData initial test data (will be set as a text content of
   *            input > pluginData element)
   * @return input stream of a GTDL file filled up with plug-in specific data
   * @throws IOException in case there were problems with accessing the GTDL
   *             skeleton file or with input streams
   * @throws ParserConfigurationException in case parser could not be
   *             initialized
   * @throws SAXException in case GTDL skeleton file could not be parsed
   * @throws TransformerException in case there were problems transforming
   *             skeleton source to input stream
   */
  public static InputStream getInitialInputStream( final String plugInID,
                                                   final List<String> testedResources,
                                                   final InputStream testInputData )
    throws IOException, ParserConfigurationException, SAXException,
    TransformerException
  {
    InputStream result = null;
    Path resultPath = new Path( SKELETON_LOCATION );
    URL fileURL = FileLocator.find( Platform.getBundle( Activator.PLUGIN_ID ),
                                    resultPath,
                                    null );
    fileURL = FileLocator.toFileURL( fileURL );
    String temp = fileURL.toString();
    temp = temp.substring( temp.indexOf( fileURL.getProtocol() )
                           + fileURL.getProtocol().length()
                           + 1, temp.length() );
    resultPath = new Path( temp );
    File skeletonFile = resultPath.toFile();
    InputStream stream = new FileInputStream( skeletonFile );
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factoryDOM.newDocumentBuilder();
    Document doc = builder.parse( stream );
    NodeList pluginList = doc.getElementsByTagName( GTDLParser.PLUGIN_ELEMENT );
    NodeList dataList = doc.getElementsByTagName( GTDLParser.INPUT_DATA_ELEMENT );
    NodeList resourcesList = doc.getElementsByTagName( GTDLParser.INPUT_RESOURCES_LIST );
    Element pluginElement = null;
    Element inputDataElement = null;
    Element resourcesElement = null;
    if( pluginList != null
        && pluginList.getLength() != 0
        && dataList != null
        && dataList.getLength() != 0
        && resourcesList != null
        && resourcesList.getLength() != 0 )
    {
      pluginElement = ( Element )pluginList.item( 0 );
      inputDataElement = ( Element )dataList.item( 0 );
      resourcesElement = ( Element )resourcesList.item( 0 );
    }
    if( pluginElement != null
        && inputDataElement != null
        && resourcesElement != null )
    {
      pluginElement.setTextContent( plugInID );
      BufferedReader in = new BufferedReader( new InputStreamReader( testInputData ) );
      StringBuffer buffer = new StringBuffer();
      String line;
      while( ( line = in.readLine() ) != null ) {
        buffer.append( line );
      }
      inputDataElement.setTextContent( buffer.toString() );
      for( String resourceName : testedResources ) {
        Element resourceElement = doc.createElement( GTDLParser.INPUT_RESOURCE );
        resourceElement.setTextContent( resourceName );
        resourcesElement.appendChild( resourceElement );
      }
    }
    // transform XML document to output stream
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Transformer transformer = tFactory.newTransformer();
    transformer.setOutputProperty( OutputKeys.INDENT, "yes" ); //$NON-NLS-1$
    transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4" ); //$NON-NLS-1$ //$NON-NLS-2$
    DOMSource source = new DOMSource( doc );
    ByteArrayOutputStream arrStream = new ByteArrayOutputStream();
    StreamResult streamResult = new StreamResult( arrStream );
    transformer.transform( source, streamResult );
    // transform output stream to input stream (input is small, so we can use
    // buffered streams)
    arrStream = ( ByteArrayOutputStream )streamResult.getOutputStream();
    result = new ByteArrayInputStream( arrStream.toByteArray() );
    return result;
  }

  /**
   * Method to put test result into GTDL file (to add new element output >
   * result)
   * 
   * @param file GTDL file to write to
   * @param newResults results which will be serialized to GTDL file
   * @throws ParserConfigurationException in case parser could not be
   *             initialized
   * @throws SAXException in case XML file could not be parsed
   * @throws IOException in case there were I/O problems with XML file
   * @throws TransformerFactoryConfigurationError in case XML transformer could
   *             not be initialized
   * @throws TransformerException in case results could not be transformed to
   *             XML file
   */
  public static void addTestResults( final File file,
                                     final List<IServiceJobResult> newResults )
    throws ParserConfigurationException, SAXException, IOException,
    TransformerFactoryConfigurationError, TransformerException
  {
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    for( IServiceJobResult result : newResults ) {
      Element newElement = document.createElement( GTDLParser.OUTPUT_RESULT_ELEMENT );
      Element resourceElement = document.createElement( GTDLParser.OUTPUT_RESULT_RESOURCE );
      resourceElement.setTextContent( result.getResourceName() );
      Element updateElement = document.createElement( GTDLParser.OUTPUT_RESULT_DATE );
      updateElement.setTextContent( DateFormat.getDateTimeInstance()
        .format( result.getRunDate() ) );
      Element testNameElement = document.createElement( GTDLParser.OUTPUT_RESULT_TEST );
      testNameElement.setTextContent( result.getSubTestName() );
      Element notAfterElement = document.createElement( GTDLParser.OUTPUT_RESULT_DATA );
      notAfterElement.setTextContent( result.getResultRawData() );
      Element statusEnumElement = document.createElement( GTDLParser.OUTPUT_RESULT_ENUM );
      statusEnumElement.setTextContent( result.getResultEnum() );
      Element summaryElement = document.createElement( GTDLParser.OUTPUT_RESULT_SUMMARY );
      summaryElement.setTextContent( result.getResultSummary() );
      Element typeElement = document.createElement( GTDLParser.OUTPUT_RESULT_TYPE );
      typeElement.setTextContent( result.getResultType() );
      newElement.appendChild( resourceElement );
      newElement.appendChild( updateElement );
      newElement.appendChild( testNameElement );
      newElement.appendChild( notAfterElement );
      newElement.appendChild( statusEnumElement );
      newElement.appendChild( summaryElement );
      newElement.appendChild( typeElement );
      document.getFirstChild().appendChild( newElement );
      Transformer transformer = TransformerFactory.newInstance()
        .newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" ); //$NON-NLS-1$
      transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4" ); //$NON-NLS-1$ //$NON-NLS-2$
      Source source = new DOMSource( document );
      Result result1 = new StreamResult( file );
      transformer.transform( source, result1 );
    }
  }
}
