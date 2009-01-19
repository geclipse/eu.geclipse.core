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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.jsdl.parametric.internal;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.jsdl.parametric.IGeneratedJsdl;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.parametric.ParametricJsdlException;
import eu.geclipse.jsdl.xpath.XPathDocument;

/**
 * This context replaces values in DOM Document containing jsdl and pass serialization to handler  
 */
public class GenerationContext implements IGenerationContext, IGeneratedJsdl {
  private IParametricJsdlHandler handler;
  private Document currentJsdl;
  private String currentJsdlName;  
  private XPathDocument xpath;

  /**
   * @param parametricJsdl 
   * @param baseJsdl JSDL in which we will change parameter values
   * @param handler
   * @param xpathDocument 
   */
  GenerationContext( final Document baseJsdl, final IParametricJsdlHandler handler, final XPathDocument xpathDocument ) {        
    this.handler = handler;
    this.currentJsdl = baseJsdl;
    this.xpath = xpathDocument;
  }  

  @Override
  public IGenerationContext clone() {
    GenerationContext newContext = new GenerationContext( ( Document )this.currentJsdl.cloneNode( true ), this.handler, this.xpath );
    return newContext;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.IGenerationContext#setValue(java.lang.String, javax.xml.xpath.XPathExpression, java.lang.String, org.eclipse.core.runtime.SubMonitor)
   */
  public void setValue( final String xpathQuery,
                        final String value ) throws ParametricJsdlException
  {
    try {
      NodeList nodeList = this.xpath.getNodes( this.currentJsdl, xpathQuery );
      for( int index = 0; index < nodeList.getLength(); index++ ) {
        Node item = nodeList.item( index );
        item.setTextContent( value );      
      }
      updateJsdlDescription( xpathQuery, value );
    } catch( XPathExpressionException exception ){
      throw new ParametricJsdlException( "Error occured during calling XPath query" ); //$NON-NLS-1$
    }
  }

  public void storeGeneratedJsdl( final List<Integer> iterationsStack ) throws ParametricJsdlException {
    this.currentJsdlName = createIterationName( iterationsStack );
    this.handler.newJsdlGenerated( this );
  }
  
  private String createIterationName( final List<Integer> iterationsStack ) {
    StringBuilder builder = new StringBuilder();
    for( Integer iteration : iterationsStack ) {
      builder.append( String.format( "[%03d]", iteration ) ); //$NON-NLS-1$
    }    
    return builder.toString();
  }
 
  private void updateJsdlDescription( final String paramName, final String value ) throws XPathExpressionException {
    NodeList nodes = this.xpath.getNodes( this.currentJsdl.getDocumentElement(), "jsdl:JobDescription/jsdl:JobIdentification/jsdl:Description" ); //$NON-NLS-1$
    Element description = null;
    if( nodes == null 
        || nodes.getLength() == 0 ) {
      description = createXmlElement( this.currentJsdl.getDocumentElement(), "jsdl:JobDescription/jsdl:JobIdentification/jsdl:Description" ); //$NON-NLS-1$
    } else {
      description = ( Element )nodes.item( 0 );
    }
    description.setTextContent( getDescriptionString( description.getTextContent(), paramName, value ) );
  }
  
  private String getDescriptionString( final String currentDescription, final String paramName, final String value ) {
    int begin = paramName.lastIndexOf( "/" ) + 1; //$NON-NLS-1$
    String newEntry = String.format( "%s=%s", paramName.substring( begin ), value ); //$NON-NLS-1$
    return currentDescription == null || currentDescription.length() == 0 ? newEntry : currentDescription + "\n" + newEntry; //$NON-NLS-1$
  }

  public Document getDocument() {
    return this.currentJsdl;
  }

  public String getIterationName() {
    return this.currentJsdlName;
  }

  public String getParamValue( final String paramName )
    throws ParametricJsdlException
  {
    try {
      return this.xpath.getValue( this.currentJsdl, paramName );
    } catch( XPathExpressionException exception ) {
      throw new ParametricJsdlException( "Error occured during calling XPath query" ); //$NON-NLS-1$
    }
  }
  
  private Element createXmlElement( final Element parent, final String pathString ) throws XPathExpressionException {
    Element element = parent;    
    
    for( String currentNodeName : pathString.split( "/" ) ) { //$NON-NLS-1$
      NodeList nodes = this.xpath.getNodes( element, "./" + currentNodeName ); //$NON-NLS-1$
      if( nodes == null 
          || nodes.getLength() == 0 ) {
        int namespaceIndex = currentNodeName.indexOf( ":" ); //$NON-NLS-1$
        if( namespaceIndex > -1
            && namespaceIndex + 1 < currentNodeName.length() ) {
          currentNodeName = currentNodeName.substring( namespaceIndex + 1 );
        }
        Element newElement = this.currentJsdl.createElementNS( "http://schemas.ggf.org/jsdl/2005/11/jsdl", currentNodeName ); //$NON-NLS-1$
        element.appendChild( newElement );
        element = newElement;
      } else {
        element = ( Element )nodes.item( 0 );
      }
    }
    
    return element;
  }

  public IParametricJsdlHandler getHandler() {
    return this.handler;
  }
  

}