/******************************************************************************
 * Copyright (c) 2007- 2008 g-Eclipse consortium 
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.model.impl.ServiceJobResult;

/**
 * Parser for certificate test XML (results)
 */
public class GTDLParser {

  /**
   * Name of language used when creating XML schema
   */
  public static final String SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$
  /**
   * Path to location of the XML schema
   */
  public static final String SCHEMA_LOCATION = "xml/GTDL_schema.xsd"; //$NON-NLS-1$
  /**
   * Input element name from GTDL XML
   */
  public static final String INPUT_ELEMENT = "input"; //$NON-NLS-1$
  /**
   * Plug-in id element name (in input element) from GTDL XML
   */
  public static final String PLUGIN_ELEMENT = "pluginId"; //$NON-NLS-1$
  /**
   * Test input element name (in input element) from GTDL XML
   */
  public static final String INPUT_DATA_ELEMENT = "inputData"; //$NON-NLS-1$
  /**
   * Result element name (in Output element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_ELEMENT = "result"; //$NON-NLS-1$
  /**
   * Resource element name (in Result element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_RESOURCE = "resource"; //$NON-NLS-1$
  /**
   * Date element name (in Result element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_DATE = "date"; //$NON-NLS-1$
  /**
   * Simple test element name (in Result element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_TEST = "simpleTest"; //$NON-NLS-1$
  /**
   * Result data element name (in Result element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_DATA = "resultData"; //$NON-NLS-1$
  /**
   * Result summary element name (in Result element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_SUMMARY = "resultSummary"; //$NON-NLS-1$
  /**
   * BES status (test enumeration result) element name (in Result element) from
   * GTDL XML
   */
  public static final String OUTPUT_RESULT_ENUM = "BESStatusType"; //$NON-NLS-1$
  /**
   * Result type element name (in Result element) from GTDL XML
   */
  public static final String OUTPUT_RESULT_TYPE = "resultType"; //$NON-NLS-1$
  /**
   * Tested resources list element name (in input element) from GTDL XML
   */
  public static final String INPUT_RESOURCES_LIST = "testedResources"; //$NON-NLS-1$
  
  /**
   * Tested resource element form tested resources list in GTDL XML.
   */
  public static final String INPUT_RESOURCE = "resource"; //$NON-NLS-1$

  /**
   * Method to check if file of type GTDL is compatible with XML schema
   * definition.
   * 
   * @param xmlFile XML file to parse
   * @throws SAXException in case XML or XML schema file could not be parsed
   * @throws IOException in case there are problems with reading XML or XML
   *             schema file
   */
  public static void validateFile( final File xmlFile )
    throws SAXException, IOException
  {
    SchemaFactory factory = SchemaFactory.newInstance( SCHEMA_LANGUAGE );
    Path resultPath = new Path( SCHEMA_LOCATION );
    URL fileURL = FileLocator.find( Platform.getBundle( Activator.PLUGIN_ID ),
                                    resultPath,
                                    null );
    fileURL = FileLocator.toFileURL( fileURL );
    String temp = fileURL.toString();
    temp = temp.substring( temp.indexOf( fileURL.getProtocol() )
                           + fileURL.getProtocol().length()
                           + 1, temp.length() );
    resultPath = new Path( temp );
    File schemaLocation = resultPath.toFile();
    Schema schema;
    schema = factory.newSchema( schemaLocation );
    Validator validator = schema.newValidator();
    Source source = new StreamSource( xmlFile );
    validator.validate( source );
  }

  /**
   * Gets plug-in id from GTDL file (text content of test > input > pluginId
   * element)
   * 
   * @param file GTDL file to parse
   * @return plug-in id (responsible for handling test data from GTDL file)
   * @throws ParserConfigurationException in case parser could not be
   *             initialized
   * @throws SAXException in case file could not be parsed
   * @throws IOException in case there were problems when reading XML file
   */
  public static String getPluginId( final File file )
    throws ParserConfigurationException, SAXException, IOException
  {
    String result = ""; //$NON-NLS-1$
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    NodeList resultsXML = document.getElementsByTagName( INPUT_ELEMENT );
    for( int i = 0; i < resultsXML.getLength(); i++ ) {
      Element singleResult = ( Element )resultsXML.item( i );
      result = ( ( Element )singleResult.getElementsByTagName( PLUGIN_ELEMENT )
        .item( 0 ) ).getTextContent();
    }
    return result;
  }

  /**
   * Gets test initialization data from GTDL file (text content of test > input >
   * inputData element)
   * 
   * @param file GTDL file to parse
   * @return plug-in id (responsible for handling test data from GTDL file)
   * @throws ParserConfigurationException in case parser could not be
   *             initialized
   * @throws SAXException in case file could not be parsed
   * @throws IOException in case there were problems when reading XML file
   */
  public static String getInputTestData( final File file )
    throws ParserConfigurationException, SAXException, IOException
  {
    String result = ""; //$NON-NLS-1$
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    NodeList resultsXML = document.getElementsByTagName( INPUT_ELEMENT );
    for( int i = 0; i < resultsXML.getLength(); i++ ) {
      Element singleResult = ( Element )resultsXML.item( i );
      result = ( ( Element )singleResult.getElementsByTagName( INPUT_DATA_ELEMENT )
        .item( 0 ) ).getTextContent();
    }
    return result;
  }

  /**
   * Parses GTDL (grid test description language) file for test results. List of
   * results that were found and parsed is returned. List elements are instances
   * of {@link ServiceJobResult} - which means that they have String taken
   * directly from XML as a test's result. Only plug-in calling this method
   * knows what to do with this String (available also as a InputStream -
   * {@link IServiceJob#getInputStreamForResult(IServiceJobResult)}). This all
   * means that data returned by this method has to modified by plug-in that
   * called this method!
   * 
   * @param file XML file (GTDL)
   * @return List of {@link ServiceJobResult} representing data in XML file
   * @throws ParserConfigurationException in case parser could not be
   *             initialized
   * @throws SAXException in case file could not be parsed
   * @throws IOException in case there were problems when reading XML file
   * @throws DOMException in case parser could not read text content of XML's
   *             elements
   * @throws ParseException in case {@link DateFormat} class could not parse
   *             date contained in XML
   */
  public static List<ServiceJobResult> getTestResults( final File file )
    throws ParserConfigurationException, SAXException, IOException,
    DOMException, ParseException
  {
    List<ServiceJobResult> result = new ArrayList<ServiceJobResult>();
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    NodeList resultsXML = document.getElementsByTagName( OUTPUT_RESULT_ELEMENT );
    for( int i = 0; i < resultsXML.getLength(); i++ ) {
      Element singleResult = ( Element )resultsXML.item( i );
      Date date = DateFormat.getDateTimeInstance()
        .parse( ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_DATE )
          .item( 0 ) ).getTextContent() );
      result.add( new ServiceJobResult( date,
                                      ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_RESOURCE )
                                        .item( 0 ) ).getTextContent(),
                                      ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_TEST )
                                        .item( 0 ) ).getTextContent(),
                                      ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_DATA )
                                        .item( 0 ) ).getTextContent(),
                                      ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_SUMMARY )
                                        .item( 0 ) ).getTextContent(),
                                      ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_TYPE )
                                        .item( 0 ) ).getTextContent(),
                                      ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_ENUM )
                                        .item( 0 ) ).getTextContent() ) );
    }
    return result;
  }

  /**
   * Method to access list of tested resources names form input part of GTDL file.
   * @param file GTDL file to parse
   * @return List of names of resources that this test should test
   * @throws ParserConfigurationException 
   * @throws SAXException
   * @throws IOException
   */
  public static List<String> getTestedResources( final File file ) throws ParserConfigurationException, SAXException, IOException {
    List<String> result = new ArrayList<String>();
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    NodeList resultsXML = document.getElementsByTagName( INPUT_RESOURCES_LIST );
    for( int i = 0; i < resultsXML.getLength(); i++ ) {
      NodeList resources = ((Element)resultsXML.item( i )).getElementsByTagName( INPUT_RESOURCE );
      for (int j = 0; j < resources.getLength(); j++){
        result.add( (( Element )resources.item( j )).getTextContent());
      }
    }
    return result;
  }
}
