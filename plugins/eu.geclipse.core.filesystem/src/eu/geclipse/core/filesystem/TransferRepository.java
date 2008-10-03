/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.filesystem.internal.Activator;

/**
 * Class representing repository for storing informations about all transfer
 * operations.
 */
public class TransferRepository {

  private static TransferRepository singleton;

  /**
   * Getter of the singleton of this class
   * @return singleton of repository class
   */
  public static TransferRepository getTransferRepository() {
    if( singleton == null ) {
      singleton = new TransferRepository();
    }
    return singleton;
  }

  /**
   * Saves information about this transfer to the repository
   * 
   * @param source
   * @param destination
   * @param operationSpecificData
   * @param monitor
   */
  public void save( final TransferInformation op ) {
    // TODO save
    try {
      TransferRepositoryWriter.saveOperation( op, getRepoFile() );
    } catch( ParserConfigurationException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( TransformerFactoryConfigurationError e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( TransformerException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    }
  }

  /**
   * Deletes information about specified transfer from the repository
   * 
   * @param source IFileStore
   * @param destination
   */
  public void delete( final Integer transferId ) {
    // TODO delete from repo file
    try {
      TransferRepositoryWriter.delete( getRepoFile(), transferId );
    } catch( ParserConfigurationException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( TransformerFactoryConfigurationError e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( TransformerException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    }
  }

  public List<TransferInformation> getOperations() {
    List<TransferInformation> operations = null;
    // TODO iterate over XML file and get all unfinished operations
    // Create transfer operations from it using stored informations (src, dst,
    // data) and add to list
    try {
      operations = TransferRepositoryParser.getOperations( getRepoFile() );
    } catch( ParserConfigurationException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( CoreException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    } catch( URISyntaxException e ) {
      // TODO Auto-generated catch block
      Activator.logException( e );
    }
    return operations;
  }

  private File getRepoFile() {
    IPath path = Activator.getDefault().getStateLocation();
    File file = path.append( "transferRepository.xml" ).toFile();
    if( !file.exists() ) {
      try {
        file.createNewFile();
        FileOutputStream stream = new FileOutputStream( file );
        String contents = "<root>\n</root>";
        stream.write( contents.getBytes() );
        stream.close();
      } catch( IOException e ) {
        // TODO Auto-generated catch block
        Activator.logException( e );
      }
    }
    return file;
  }
  private static class TransferRepositoryParser {

    public static List<TransferInformation> getOperations( final File file )
      throws ParserConfigurationException, SAXException, IOException,
      CoreException, URISyntaxException
    {
      List<TransferInformation> result = new ArrayList<TransferInformation>();
      DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factoryDOM.newDocumentBuilder();
      Document document = builder.parse( file );
      NodeList resultsXML = document.getElementsByTagName( "transfer" );
      for( int i = 0; i < resultsXML.getLength(); i++ ) {
        Element singleResult = ( Element )resultsXML.item( i );
        int id = Integer.valueOf( ( ( Element )singleResult.getElementsByTagName( "id" )
          .item( 0 ) ).getTextContent() );
        String data = ( ( Element )singleResult.getElementsByTagName( "data" )
          .item( 0 ) ).getTextContent();
        String sourceURI = ( ( Element )singleResult.getElementsByTagName( "source" )
          .item( 0 ) ).getTextContent();
        String destinationURI = ( ( Element )singleResult.getElementsByTagName( "destination" )
          .item( 0 ) ).getTextContent();
        long lenght = Long.valueOf( ( ( Element )singleResult.getElementsByTagName( "size" )
            .item( 0 ) ).getTextContent() );
        IFileStore source = EFS.getStore( new URI( sourceURI ) );
        IFileStore destination = EFS.getStore( new URI( destinationURI ) );
        result.add( new TransferInformation( id, source, destination, data, lenght ) );
      }
      return result;
    }
  }
  private static class TransferRepositoryWriter {

    public static void saveOperation( final TransferInformation op,
                                      final File file )
      throws ParserConfigurationException, SAXException, IOException,
      TransformerFactoryConfigurationError, TransformerException
    {
      DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder;
      builder = factoryDOM.newDocumentBuilder();
      Document document = builder.parse( file );
      NodeList resultsList = document.getElementsByTagName( "transfer" ); //$NON-NLS-1$
      Element newTransfer = document.createElement( "transfer" );
      Element transferIdElement = document.createElement( "id" );
      transferIdElement.setTextContent( op.getId().toString() );
      Element sourceElement = document.createElement( "source" );
      sourceElement.setTextContent( op.getSource().toURI().toString() );
      Element destinationElement = document.createElement( "destination" );
      destinationElement.setTextContent( op.getDestination().toURI().toString() );
      Element dataElement = document.createElement( "data" );
      dataElement.setTextContent( op.getData() );
      Element sizeElement = document.createElement( "size" );
      sizeElement.setTextContent( String.valueOf( op.getSize() ) );
      newTransfer.appendChild( transferIdElement );
      newTransfer.appendChild( sourceElement );
      newTransfer.appendChild( destinationElement );
      newTransfer.appendChild( dataElement );
      newTransfer.appendChild( sizeElement ); 
      
      document.getFirstChild().appendChild( newTransfer );
      Transformer transformer = TransformerFactory.newInstance()
        .newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" ); //$NON-NLS-1$
      Source source = new DOMSource( document );
      Result result1 = new StreamResult( file );
      transformer.transform( source, result1 );
    }

    public static void delete( final File file, final Integer transferId )
      throws ParserConfigurationException, SAXException, IOException,
      TransformerFactoryConfigurationError, TransformerException
    {
      DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder;
      builder = factoryDOM.newDocumentBuilder();
      Document document = builder.parse( file );
      Node nodeToDelete = null;
      NodeList resultsList = document.getElementsByTagName( "transfer" ); //$NON-NLS-1$
      for( int i = 0; i < resultsList.getLength(); i++ ) {
        Integer id = Integer.valueOf( ( ( Element )resultsList.item( i ) ).getElementsByTagName( "id" )
          .item( 0 )
          .getTextContent() );
        if( id.compareTo( transferId ) == 0 ) {
          nodeToDelete = resultsList.item( i );
        }
      }
      if( nodeToDelete != null ) {
        document.getFirstChild().removeChild( nodeToDelete );
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
}
