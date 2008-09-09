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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.reporting.ProblemException;

class Assignment {

  private NodeList parameters;  
  private List<XPathExpression> xPathExpressions;
  private IFunction function;

  /**
   * @param xpathEngine 
   * @param parameters which values will be substituted
   * @param function which describes values for parameters over iterations
   */
  public Assignment( final XPath xpathEngine,
                               final NodeList parameters,
                               final IFunction function )
  {
    this.parameters = parameters;
    this.function = function;
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
   * @param currentJsdl jsdl in which parameter value should be subtituted
   * @param functionIterator iterator, which return next value from function associated with with assignment
   * @param generationContext 
   * @param monitor progress monitor
   * @throws ProblemException 
   */
  public void setParamValue( final Iterator<String> functionIterator,
                             final IGenerationContext generationContext,
                             final SubMonitor monitor ) throws ProblemException
  {
    String value = functionIterator.next();

    for( int index = 0; index < this.parameters.getLength(); index++ ) {
      // TODO mariusz check if value is a Text node
      // TODO mariusz check progress monitor
      XPathExpression pathExpression = this.xPathExpressions.get( index );
      String paramName = this.parameters.item( index ).getTextContent();
      generationContext.setValue( paramName, pathExpression, value, monitor );
    }
  }
  
  public IFunction getFunction() {
    return this.function;
  }
}
