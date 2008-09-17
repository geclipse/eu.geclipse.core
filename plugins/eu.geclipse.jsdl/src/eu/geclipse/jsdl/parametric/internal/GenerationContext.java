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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.parametric.IGeneratedJsdl;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;

/**
 * This context replaces values in DOM Document containing jsdl and pass serialization to handler  
 */
public class GenerationContext implements IGenerationContext, IGeneratedJsdl {
  private JSDLJobDescription parametricJsdl;
  private IParametricJsdlHandler handler;
  private Document currentJsdl;
  private String currentJsdlName;
  private Map<String,XPathExpression> xpathMap;
  private XPath xpathEngine;

  /**
   * @param baseJsdl JSDL in which we will change parameter values
   * @param handler
   */
  public GenerationContext( final JSDLJobDescription parametricJsdl, final Document baseJsdl, final IParametricJsdlHandler handler, final XPath xpathEngine ) {        
    this.handler = handler;
    this.currentJsdl = baseJsdl;
    this.parametricJsdl = parametricJsdl;
    this.xpathEngine = xpathEngine;
  }  

  @Override
  public IGenerationContext clone() {
    GenerationContext newContext = new GenerationContext( this.parametricJsdl, ( Document )this.currentJsdl.cloneNode( true ), this.handler, this.xpathEngine );
    newContext.xpathMap = this.xpathMap;    
    return newContext;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.IGenerationContext#setValue(java.lang.String, javax.xml.xpath.XPathExpression, java.lang.String, org.eclipse.core.runtime.SubMonitor)
   */
  public void setValue( final String paramName, final XPathExpression paramXPath, final String value, final SubMonitor subMonitor ) throws ProblemException {
    try {
      NodeList nodeList = ( NodeList )paramXPath.evaluate( this.currentJsdl,
                                                               XPathConstants.NODESET );
      
      for( int index = 0; index < nodeList.getLength(); index++ ) {
        Node item = nodeList.item( index );

        // TODO mariusz check if substituted node is text node
        item.setTextContent( value );
      }
      
      updateJsdlDescription( paramName, value );
      
      // TODO mariusz check if paramName.toString() return correct xPath query
      this.handler.paramSubstituted( paramName, value, subMonitor );
    } catch( XPathExpressionException exception ) {
      // TODO mariusz Auto-generated catch block
      exception.printStackTrace();
    }
    
  }

  public void storeGeneratedJsdl( final List<Integer> iterationsStack, final SubMonitor subMonitor ) throws ProblemException {
    currentJsdlName = createIterationName( iterationsStack );
    this.handler.newJsdlGenerated( this, subMonitor );
  }
  
  private String createIterationName( final List<Integer> iterationsStack ) {
    StringBuilder builder = new StringBuilder();
    for( Integer iteration : iterationsStack ) {
      builder.append( String.format( "[%03d]", iteration ) ); //$NON-NLS-1$
    }    
    return builder.toString();
  }
 
  private void updateJsdlDescription( final String paramName, final String value ) {
    Element jobDescription = getElement( this.currentJsdl.getDocumentElement(), "jsdl:JobDescription" );   //$NON-NLS-1$
    Element jobIdentification = getElement( jobDescription, "jsdl:JobIdentification" ); //$NON-NLS-1$
    Element description = getElement( jobIdentification, "jsdl:Description" ); //$NON-NLS-1$    
    description.setTextContent( getDescriptionString( description.getTextContent(), paramName, value ) );
  }
  
  private String getDescriptionString( final String currentDescription, final String paramName, final String value ) {
    int begin = paramName.lastIndexOf( "/" ) + 1; //$NON-NLS-1$
    String newEntry = String.format( "%s=%s", paramName.substring( begin ), value ); //$NON-NLS-1$
    return currentDescription == null || currentDescription.length() == 0 ? newEntry : currentDescription + "\n" + newEntry; //$NON-NLS-1$
  }

  /**
   * Get element with given name, which is direct child of parent. If element doesn't exists, then create new one.
   * @param parent
   * @param childName
   * @return
   */
  private Element getElement( final Element parent, final String childName ) {
    Element element = null;    
    NodeList nodes = parent.getChildNodes();
    
    for( int index = 0; index < nodes.getLength(); index++ ) {
      Node child = nodes.item( index );
      
      if( child.getNodeName().equals( childName ) ) {
        element = ( Element )child;
      }
    }
    
    if( element == null ) {
      element = this.currentJsdl.createElement( childName );
      parent.appendChild( element );
    }
    
    return element;
  }

  public Document getDocument() {
    return this.currentJsdl;
  }

  public String getIterationName() {
    return this.currentJsdlName;
  }

  public String getParamValue( final String paramName ) throws ProblemException {
    String value = null;
    Map<String, XPathExpression> map = getXpathMap();
    XPathExpression xpath = map.get( paramName );
    
    if( xpath == null ) {
      try {
        xpath = this.xpathEngine.compile( paramName );
        map.put( paramName, xpath );
      } catch( XPathExpressionException exception ) {
        // TODO mariusz Auto-generated catch block
        exception.printStackTrace();
      }
    }

    try {
      value = xpath.evaluate( this.currentJsdl );
    } catch( XPathExpressionException exception ) {
      // TODO mariusz Auto-generated catch block
      exception.printStackTrace();
    }
    
    return value;
  }
  
  private Map<String, XPathExpression> getXpathMap() {
    if( xpathMap == null ) {
      xpathMap = new HashMap<String,XPathExpression>();
    }
    return xpathMap;    
  }  
  

}