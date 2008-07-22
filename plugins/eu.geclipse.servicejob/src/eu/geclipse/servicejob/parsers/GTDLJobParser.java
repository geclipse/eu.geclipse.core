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
 *    Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.servicejob.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.model.impl.ServiceJobResult;
import eu.geclipse.servicejob.model.tests.job.SubmittableServiceJobResult;


/**
 * General parser for the service job status file.
 */
public class GTDLJobParser {
  
  /**
   * Path to location of the XML schema
   */
  public static final String SCHEMA_LOCATION = "xml/GTDL_schema.xsd"; //$NON-NLS-1$
  
  /**
   * XML node - result
   */
  public static final String OUTPUT_RESULT_ELEMENT = "result"; //$NON-NLS-1$
  
  /**
   * XML node - resource 
   */
  public static final String OUTPUT_RESULT_RESOURCE = "resource"; //$NON-NLS-1$
  
  /**
   * XML node - date
   */
  public static final String OUTPUT_RESULT_DATE = "date"; //$NON-NLS-1$
  
  /**
   * XML node - simple test name
   */
  public static final String OUTPUT_RESULT_TEST = "simpleTest"; //$NON-NLS-1$
  
  /**
   * XML node - result data
   */
  public static final String OUTPUT_RESULT_DATA = "resultData"; //$NON-NLS-1$
  
  /**
   * XML node - summary
   */
  public static final String OUTPUT_RESULT_SUMMARY = "resultSummary"; //$NON-NLS-1$
  
  /**
   * XML node - bes status
   */
  public static final String OUTPUT_RESULT_ENUM = "BESStatusType"; //$NON-NLS-1$
  
  /**
   * result type
   */
  public static final String OUTPUT_RESULT_TYPE = "resultType"; //$NON-NLS-1$

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
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws DOMException
   * @throws ParseException
   */
  public static List<SubmittableServiceJobResult> getTestResults( final File file )
    throws ParserConfigurationException, SAXException, IOException,
    DOMException, ParseException
  {
    List<SubmittableServiceJobResult> result = new ArrayList<SubmittableServiceJobResult>();
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    NodeList resultsXML = document.getElementsByTagName( OUTPUT_RESULT_ELEMENT );
    for( int i = 0; i < resultsXML.getLength(); i++ ) {
      Node node = resultsXML.item( i );
      Element singleResult = ( Element )node;
      Date date = DateFormat.getDateTimeInstance()
        .parse( ( ( Element )singleResult.getElementsByTagName( OUTPUT_RESULT_DATE )
          .item( 0 ) ).getTextContent() );
      
      SubmittableServiceJobResult testResult = 
        new SubmittableServiceJobResult( date,
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
                             .item( 0 ) ).getTextContent() );
      result.add( testResult );
    }
    return result;
  }
  
  /**
   * Method to parse part of XML file in which tested resources are described. 
   * @param stream input stream of XML to parse
   * @return list of resources 
   */
  public static List<String> parseGeneralPartForResources( final InputStream stream ) {
    List<String> result = new ArrayList<String>();
    try {
      DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factoryDOM.newDocumentBuilder();
      Document document = builder.parse( stream );
      NodeList resultsXML = document.getElementsByTagName( "resourceList" ); //$NON-NLS-1$
      for( int i = 0; i < resultsXML.getLength(); i++ ) {
        Element element = ( Element )resultsXML.item( i );
        result.add( element.getChildNodes().item( 0 ).getNodeValue().trim() );
      }
    } catch( ParserConfigurationException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO katis
      Activator.logException( e );
    }
    return result;
  }
  
}

  
