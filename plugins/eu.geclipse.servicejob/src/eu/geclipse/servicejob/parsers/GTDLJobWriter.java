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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.servicejob.model.tests.job.SubmittableServiceJobResult;


/**
 * GTDL writer for service jobs.
 */
public class GTDLJobWriter {
  
  /**
   * standard location
   */
  public static final String SKELETON_LOCATION = "xml/GTDL_no_Output.xml"; //$NON-NLS-1$

  /**
   * Adds a service job results from a list to specified file. If there exists a
   * service job with the same id, then a previous status entry of this job is
   * overwritten with the new entry. 
   * 
   * @param file gtdl file
   * @param newResults list of results to save
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws TransformerFactoryConfigurationError
   * @throws TransformerException
   */
  public static void addTestResults( final File file,
                                     final List<SubmittableServiceJobResult> newResults )
    throws ParserConfigurationException, SAXException, IOException,
    TransformerFactoryConfigurationError, TransformerException
  {
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    for( SubmittableServiceJobResult result : newResults ) {
      Node nodeToDelete = null;
      NodeList resultsList = document.getElementsByTagName( "result"); //$NON-NLS-1$
      for( int i = 0; i < resultsList.getLength(); i++ ) {
        String rawDataString = ((Element)resultsList.item( i ))
          .getElementsByTagName( GTDLParser.OUTPUT_RESULT_DATA ).item( 0 ).getTextContent();
        
        DocumentBuilderFactory factoryDOM2 = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder2 = factoryDOM2.newDocumentBuilder();
        Document rawDataDocument = builder2.parse( new ByteArrayInputStream( rawDataString.getBytes()) );
        Node jobIDNode = rawDataDocument.getElementsByTagName( "Name" ).item( 0 ); //$NON-NLS-1$
        if ( jobIDNode != null && jobIDNode.getTextContent().equals( result.getJobIDString() ) ) {
          nodeToDelete = resultsList.item( i );
        }
      }
      Element newElement = document.createElement( GTDLParser.OUTPUT_RESULT_ELEMENT );
      Element resourceElement = document.createElement( GTDLParser.OUTPUT_RESULT_RESOURCE );
      resourceElement.setTextContent( result.getResourceName() );
      Element updateElement = document.createElement( GTDLParser.OUTPUT_RESULT_DATE ); 
      updateElement.setTextContent( DateFormat.getDateTimeInstance()
        .format( result.getRunDate() ) );
      Element testNameElement = document.createElement( GTDLParser.OUTPUT_RESULT_TEST ); 
      testNameElement.setTextContent( result.getSubTestName() );
      Element outputResultDataElement = document.createElement( GTDLParser.OUTPUT_RESULT_DATA ); 
      outputResultDataElement.setTextContent( result.getResultRawData() );
      Element statusEnumElement = document.createElement(GTDLParser.OUTPUT_RESULT_ENUM);
      statusEnumElement.setTextContent( result.getResultEnum() );
      Element summaryElement = document.createElement( GTDLParser.OUTPUT_RESULT_SUMMARY );
      summaryElement.setTextContent( result.getResultSummary() );
      Element typeElement = document.createElement( GTDLParser.OUTPUT_RESULT_TYPE );
      typeElement.setTextContent( result.getResultType() );
      
      newElement.appendChild( resourceElement );
      newElement.appendChild( updateElement );
      newElement.appendChild( testNameElement );
      newElement.appendChild( outputResultDataElement );
      newElement.appendChild( statusEnumElement );
      newElement.appendChild( summaryElement );
      newElement.appendChild( typeElement );
      if ( nodeToDelete != null ) {
        document.getFirstChild().replaceChild( newElement, nodeToDelete );
      } else {
        document.getFirstChild().appendChild( newElement );
      }
      Transformer transformer = TransformerFactory.newInstance()
        .newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" ); //$NON-NLS-1$
      // TODO - set indentation amount!
      Source source = new DOMSource( document );
      Result result1 = new StreamResult( file );
      transformer.transform( source, result1 );
    }
  }
  
}
