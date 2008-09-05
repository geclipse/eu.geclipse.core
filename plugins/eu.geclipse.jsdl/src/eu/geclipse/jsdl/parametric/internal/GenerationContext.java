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

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;

/**
 * This context replaces values in DOM Document containing jsdl and pass serialization to handler  
 */
public class GenerationContext implements IGenerationContext {
  private IParametricJsdlHandler handler;  
  private Document currentJsdl;

  public GenerationContext( final Document baseJsdl, final IParametricJsdlHandler handler ) {        
    this.handler = handler;
    this.currentJsdl = baseJsdl;
  }  

  @Override
  public IGenerationContext clone() {
    return new GenerationContext( ( Document )currentJsdl.cloneNode( true ), handler );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.IGenerationContext#setValue(java.lang.String, javax.xml.xpath.XPathExpression, java.lang.String, org.eclipse.core.runtime.SubMonitor)
   */
  public void setValue( final String paramName, final XPathExpression paramXPath, final String value, final SubMonitor subMonitor ) throws ProblemException {
    try {
      NodeList nodeList = ( NodeList )paramXPath.evaluate( currentJsdl,
                                                               XPathConstants.NODESET );
      
      for( int index = 0; index < nodeList.getLength(); index++ ) {
        Node item = nodeList.item( index );
        
        // TODO mariusz check if substituted node is text node
        item.setTextContent( value );
      }      
      
      // TODO mariusz check if paramName.toString() return correct xPath query
      handler.paramSubstituted( paramName, value, subMonitor );
    } catch( XPathExpressionException exception ) {
      // TODO mariusz Auto-generated catch block
      exception.printStackTrace();
    }
    
  }

  public void storeGeneratedJsdl( final List<Integer> iterationsStack, final SubMonitor subMonitor ) throws ProblemException {
    this.handler.newJsdlGenerated( currentJsdl, iterationsStack, subMonitor );
  }
  
}