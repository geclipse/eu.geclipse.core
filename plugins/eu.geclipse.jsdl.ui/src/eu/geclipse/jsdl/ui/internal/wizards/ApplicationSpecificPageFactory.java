/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.wizards;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.wizards.nodes.SpecificWizardPart;
import eu.geclipse.jsdl.ui.wizards.specific.ApplicationSpecificLastPage;
import eu.geclipse.jsdl.ui.wizards.specific.ApplicationSpecificPage;
import eu.geclipse.jsdl.ui.wizards.specific.IApplicationSpecificPage;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Class to generate instances of {@link IApplicationSpecificPage}
 */
public class ApplicationSpecificPageFactory {

  /**
   * Name of language used when creating XML schema
   */
  public static final String SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$
  /**
   * Path to location of the XML schema
   */
  public static final String SCHEMA_LOCATION = "xml/ui_definition.xsd"; //$NON-NLS-1$

  /**
   * Method to validate XML file against XML schema definition
   * 
   * @param xmlFile File to validate.
   * @throws SAXException if validation was not successful
   */
  public static void validateFile( final File xmlFile ) throws SAXException {
    SchemaFactory factory = SchemaFactory.newInstance( SCHEMA_LANGUAGE );
    Path result = new Path( SCHEMA_LOCATION );
    URL fileURL = FileLocator.find( Platform.getBundle( Activator.PLUGIN_ID ),
                                    result,
                                    null );
    try {
      fileURL = FileLocator.toFileURL( fileURL );
    } catch( IOException ioException ) {
      ProblemException exception = new ProblemException( ICoreProblems.NET_MALFORMED_URL,
                                                         ioException,
                                                         Activator.PLUGIN_ID );
      ProblemDialog.openProblem( PlatformUI.getWorkbench()
                                   .getActiveWorkbenchWindow()
                                   .getShell(),
                                 Messages.getString( "ApplicationSpecificPageFactory.XML_problems_title" ), //$NON-NLS-1$
                                 Messages.getString( "ApplicationSpecificPageFactory.XML_problems_text" ), //$NON-NLS-1$
                                 exception );
    } catch( NullPointerException nullExc ) {
      Activator.logException( nullExc );
    }
    String temp = fileURL.toString();
    temp = temp.substring( temp.indexOf( fileURL.getProtocol() )
                           + fileURL.getProtocol().length()
                           + 1, temp.length() );
    result = new Path( temp );
    File schemaLocation = result.toFile();
    Schema schema;
    try {
      schema = factory.newSchema( schemaLocation );
      Validator validator = schema.newValidator();
      Source source = new StreamSource( xmlFile );
      validator.validate( source );
    } catch( IOException ioException ) {
      ProblemException exception = new ProblemException( Activator.XML_ASP_PARSING_PROBLEM_ID,
                                                         ioException,
                                                         Activator.PLUGIN_ID );
      ProblemDialog.openProblem( PlatformUI.getWorkbench()
                                   .getActiveWorkbenchWindow()
                                   .getShell(),
                                 Messages.getString( "ApplicationSpecificPageFactory.XML_problems_title" ), //$NON-NLS-1$
                                 Messages.getString( "ApplicationSpecificPageFactory.XML_problems_text" ), //$NON-NLS-1$
                                 exception );
    }
  }

  /**
   * Method used to extract information about pages from XML file and create
   * those wizard pages
   * 
   * @param path to XML file
   * @param node node of XML file to parse
   * @return list of wizard pages contained in {@link SpecificWizardPart}
   * @throws SAXException when there is problem with XML
   * @throws ParserConfigurationException when XML parser is not configured well
   * @throws IOException when there are problems with reading the file
   */
  public static List<WizardPage> getPagesFromXML( final Path path,
                                                  final IWizardNode node )
    throws SAXException, ParserConfigurationException, IOException
  {
    List<WizardPage> result = new ArrayList<WizardPage>();
    File file = new File( path.toString() );
    validateFile( file );
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factoryDOM.newDocumentBuilder();
    Document document = builder.parse( file );
    NodeList pageElements = document.getElementsByTagName( "page" ); //$NON-NLS-1$
    // very important thing is that
    // last page needs special treatment
    // ... so first I create pages... but the last page is different!!!
    for( int i = 0; i < pageElements.getLength() - 1; i++ ) {
      result.add( new ApplicationSpecificPage( "", //$NON-NLS-1$
                                               ( Element )( pageElements.item( i ) ) ) );
    }
    // and now - create last page!
    result.add( new ApplicationSpecificLastPage( ( "" ), //$NON-NLS-1$
                                                 ( Element )( pageElements.item( pageElements.getLength() - 1 ) ),
                                                 node ) );
    return result;
  }
}
