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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

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

/**
 * Concrete implementation of the {@link IConfiguration} interface.
 * Configuration parameters are stored using a hashtable. This implementation
 * provides a standard way of loading and saving a specific configuration from
 * and to XML files.
 */
public class Configuration implements IConfiguration {
  
  private static final String CONFIGURATION_ELEMENT
    = "configuration"; //$NON-NLS-1$
  
  private static final String VERSION_ATTRIBUTE
    = "version"; //$NON-NLS-1$
  
  private static final String VERSION_10
    = "1.0"; //$NON-NLS-1$
  
  private static final String PARAMETER_ELEMENT
    = "parameter"; //$NON-NLS-1$
  
  private static final String KEY_ATTRIBUTE
    = "key"; //$NON-NLS-1$
  
  private static final String VALUE_ELEMENT
    = "value"; //$NON-NLS-1$
  
  /**
   * Internal hashtable holding the configuration parameters.
   */
  private Hashtable< String, String[] > table
    = new Hashtable< String, String[] >();
  
  /**
   * Standard constructor for an empty configuration.
   */
  public Configuration() {
    // empty implementation
  }
  
  /**
   * Copy constructor.
   * 
   * @param configuration The configuration to be copied.
   */
  public Configuration( final IConfiguration configuration ) {
    Set< String > keys = configuration.getKeys();
    for ( String key : keys ) {
      setParameters( key, configuration.getParameters( key ) );
    }
  }
    
  /**
   * Sets a single-value parameter.
   * 
   * @param key The parameter's key.
   * @param value The parameter's value.
   */
  public void setParameter( final String key, final String value ) {
    setParameters( key, new String[] { value } );
  }
  
  /**
   * Sets a multi-value parameter.
   * 
   * @param key The parameter's key.
   * @param value The parameter's value.
   */
  public void setParameters( final String key, final String[] value ) {
    this.table.put( key, value );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.config.IConfiguration#getKeys()
   */
  public Set< String > getKeys() {
    return this.table.keySet();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.config.IConfiguration#getParameter(java.lang.String)
   */
  public String getParameter( final String key ) {
    String[] parameters = this.table.get( key );
    return
      ( parameters != null ) && ( parameters.length == 1 )
      ? parameters[ 0 ]
      : null; 
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.config.IConfiguration#getParameters(java.lang.String)
   */
  public String[] getParameters( final String key ) {
    return this.table.get( key );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.config.IConfiguration#loadFromXML(org.eclipse.core.filesystem.IFileStore)
   */
  public void loadFromXML( final IFileStore store ) throws ProblemException {
    
    this.table.clear();
    
    try {
    
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating( false );
      factory.setNamespaceAware( false );

      DocumentBuilder dBuilder = factory.newDocumentBuilder();
      Document document = dBuilder.parse( store.openInputStream( EFS.NONE, null ) );
      
      NodeList configNodes = document.getElementsByTagName( CONFIGURATION_ELEMENT );
      if ( configNodes.getLength() == 1 ) {
        Element configNode = ( Element ) configNodes.item( 0 );
        String version = configNode.getAttribute( VERSION_ATTRIBUTE );
        if ( VERSION_10.equals( version ) ) {
          NodeList parameterNodes = configNode.getElementsByTagName( PARAMETER_ELEMENT );
          for ( int i = 0 ; i < parameterNodes.getLength() ; i++ ) {
            Element parameterNode = ( Element ) parameterNodes.item( i );
            String key = parameterNode.getAttribute( KEY_ATTRIBUTE );
            NodeList valueNodes = parameterNode.getElementsByTagName( VALUE_ELEMENT );
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
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.config.IConfiguration#storeToXML(org.eclipse.core.filesystem.IFileStore)
   */
  public void storeToXML( final IFileStore store ) throws ProblemException {
    
    try {
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating( false );
      factory.setNamespaceAware( false );
      
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      
      Element configNode = document.createElement( CONFIGURATION_ELEMENT );
      configNode.setAttribute( VERSION_ATTRIBUTE, VERSION_10 );
      document.appendChild( configNode );
      
      Set< String > keys = getKeys();
      
      for ( String key : keys ) {
        String[] parameters = getParameters( key );
        if ( ( parameters != null ) && ( parameters.length > 0 ) ) {
          Element parNode = document.createElement( PARAMETER_ELEMENT );
          parNode.setAttribute( KEY_ATTRIBUTE, key );
          configNode.appendChild( parNode );
          for ( String value : parameters ) {
            Element valNode = document.createElement( VALUE_ELEMENT );
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
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" ); //$NON-NLS-1$
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
