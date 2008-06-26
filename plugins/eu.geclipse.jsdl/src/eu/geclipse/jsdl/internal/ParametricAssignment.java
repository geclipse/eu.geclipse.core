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
package eu.geclipse.jsdl.internal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 */
public class ParametricAssignment {

  private NodeList parameters;
  private NodeList values;
  private List<XPathExpression> xPathExpressions;

  /**
   * @param xpathEngine 
   * @param parameters
   * @param values
   */
  public ParametricAssignment( final XPath xpathEngine,
                               final NodeList parameters,
                               final NodeList values )
  {
    this.parameters = parameters;
    this.values = values;
    initXpathExpressions( xpathEngine );
  }

  private void initXpathExpressions( final XPath xpathEngine ) {
    this.xPathExpressions = new ArrayList<XPathExpression>( this.parameters.getLength() );
    try {
      for( int index = 0; index < this.parameters.getLength(); index++ ) {
        Node item = this.parameters.item( index );
        String textContent = item.getTextContent();
        // TODO mariusz check if text content != null
        // TODO mariusz check: should has no children
        this.xPathExpressions.add( xpathEngine.compile( textContent ) );
      }
    } catch( XPathExpressionException exception ) {
      // TODO mariusz Auto-generated catch block
      exception.printStackTrace();
    }
  }

  /**
   * @return number of iterations, which should be done in this assignment
   */
  public int getValuesCount() {
    return this.values.getLength();
  }

  /**
   * @param currentJsdl jsdl in which parameter value should be subtituted
   * @param iteration which value should be subtituted
   */
  public void substituteParams( final Document currentJsdl, final int iteration )
  {
    try {
      for( int index = 0; index < this.parameters.getLength(); index++ ) {
        XPathExpression pathExpression = this.xPathExpressions.get( index );
        NodeList nodeList = ( NodeList )pathExpression.evaluate( currentJsdl,
                                                                 XPathConstants.NODESET );
        
        substituteNodesValues( nodeList, iteration );
      }
    } catch( XPathExpressionException exception ) {
      // TODO mariusz Auto-generated catch block
      exception.printStackTrace();
    }
  }

  private void substituteNodesValues( final NodeList nodeList, final int iteration ) {
    Node node = this.values.item( iteration );
    
    // TODO mariusz check if node "value" is text
    
    String stringValue = node.getTextContent();
    
    for( int index = 0; index < nodeList.getLength(); index++ ) {
      Node item = nodeList.item( index );
      
      // TODO mariusz check if substituted node is text node
      item.setTextContent( stringValue );
    }
    
  }
}
