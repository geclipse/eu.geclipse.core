package eu.geclipse.core.config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

public class Configuration implements IConfiguration {
  
  private Hashtable< String, String[] > table
    = new Hashtable< String, String[] >();
  
  public Configuration() {
    // empty implementation
  }
  
  public Configuration( final IConfiguration configuration ) {
    Set< String > keys = configuration.getKeys();
    for ( String key : keys ) {
      setParameters( key, configuration.getParameters( key ) );
    }
  }
    
  public void setParameter( final String key, final String value ) {
    setParameters( key, new String[] { value } );
  }
  
  public void setParameters( final String key, final String[] value ) {
    this.table.put( key, value );
  }

  public Set< String > getKeys() {
    return this.table.keySet();
  }

  public String getParameter( final String key ) {
    String[] parameters = this.table.get( key );
    return
      ( parameters != null ) && ( parameters.length == 1 )
      ? parameters[ 0 ]
      : null; 
  }
  
  public String[] getParameters( final String key ) {
    return this.table.get( key );
  }
  
  public void loadFromXML( final IFileStore store ) throws ProblemException {
    
    this.table.clear();
    
    try {
    
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating( false );
      factory.setNamespaceAware( false );

      DocumentBuilder dBuilder = factory.newDocumentBuilder();
      Document document = dBuilder.parse( store.openInputStream( EFS.NONE, null ) );
      
      NodeList configNodes = document.getElementsByTagName( "configuration" );
      if ( configNodes.getLength() == 1 ) {
        Element configNode = ( Element ) configNodes.item( 0 );
        String version = configNode.getAttribute( "version" );
        if ( "1.0".equals( version ) ) {
          NodeList parameterNodes = configNode.getElementsByTagName( "parameter" );
          for ( int i = 0 ; i < parameterNodes.getLength() ; i++ ) {
            Element parameterNode = ( Element ) parameterNodes.item( i );
            String key = parameterNode.getAttribute( "key" );
            NodeList valueNodes = parameterNode.getElementsByTagName( "value" );
            String[] values = new String[ valueNodes.getLength() ];
            for ( int j = 0 ; j < valueNodes.getLength() ; j++ ) {
              values[ j ] = valueNodes.item( j ).getTextContent();
            }
            setParameters( key, values );
          }
        }
      }
      
    } catch ( ParserConfigurationException pcExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, pcExc, Activator.PLUGIN_ID );
    } catch( SAXException saxExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, saxExc, Activator.PLUGIN_ID );
    } catch( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, ioExc, Activator.PLUGIN_ID );
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, cExc, Activator.PLUGIN_ID );
    }
    
  }
  
  public void storeToXML( final IFileStore store ) throws ProblemException {
    
    try {
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating( false );
      factory.setNamespaceAware( false );
      
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      
      Element configNode = document.createElement( "configuration" );
      configNode.setAttribute( "version", "1.0" );
      document.appendChild( configNode );
      
      Set< String > keys = getKeys();
      
      for ( String key : keys ) {
        String[] parameters = getParameters( key );
        if ( ( parameters != null ) && ( parameters.length > 0 ) ) {
          Element parNode = document.createElement( "parameter" );
          parNode.setAttribute( "key", key );
          configNode.appendChild( parNode );
          for ( String value : parameters ) {
            Element valNode = document.createElement( "value" );
            parNode.appendChild( valNode );
            valNode.appendChild( document.createTextNode( value ) );
          }
        }
      }
      
      OutputStream oStream = store.openOutputStream( EFS.NONE, null );
      
      DOMSource domSource = new DOMSource( document );
      StreamResult streamResult = new StreamResult( oStream );
      
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
      transformer.transform( domSource, streamResult );
      
      oStream.close();
      
    } catch ( ParserConfigurationException pcExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, pcExc, Activator.PLUGIN_ID );
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, cExc, Activator.PLUGIN_ID );
    } catch( TransformerConfigurationException tcExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, tcExc, Activator.PLUGIN_ID );
    } catch( TransformerException tExc ) {
      throw new ProblemException( ICoreProblems.IO_OPERATION_FAILED, tExc, Activator.PLUGIN_ID );
    } catch( IOException ioExc ) {
      // Coming from close() -> Ignoring
    }
    
  }
  
}
