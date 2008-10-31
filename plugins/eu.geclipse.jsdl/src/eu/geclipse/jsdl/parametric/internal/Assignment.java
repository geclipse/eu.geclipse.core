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

import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.xpath.XPathDocument;

class Assignment {
  private List<String> xPathQueries;
  private IFunction function;

  /**
   * @param pathDocument 
   * @param parameters which values will be substituted
   * @param function which describes values for parameters over iterations
   * @throws ProblemException 
   */
  public Assignment( final XPathDocument pathDocument,
                               final NodeList parameters,
                               final IFunction function ) throws ProblemException
  {    
    this.function = function;
    initXpathExpressions( parameters );
    
    if( function == null ) {      
        String msg = String.format( Messages.Assignment_errParameterWithoutFunction, this.xPathQueries.isEmpty() ? "" : this.xPathQueries.get( 0 ) ); //$NON-NLS-1$
        throw new ProblemException( "eu.geclipse.jsdl.problem.sweepExtensionSyntaxError", msg, Activator.PLUGIN_ID );                  //$NON-NLS-1$
    }
  }

  private void initXpathExpressions( final NodeList parameters ) throws ProblemException {
    this.xPathQueries = new ArrayList<String>( parameters.getLength() );
      for( int index = 0; index < parameters.getLength(); index++ ) {
        Node item = parameters.item( index );
        String textContent = item.getTextContent();
        
        if( textContent == null
            || textContent.length() == 0 ) {
          String msg = Messages.Assignment_errParameterEmpty;
          throw new ProblemException( "eu.geclipse.jsdl.problem.sweepExtensionSyntaxError", msg, Activator.PLUGIN_ID ); //$NON-NLS-1$
        }
        this.xPathQueries.add( textContent );
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
    for( String xpathQuery : this.xPathQueries ) {
      generationContext.setValue( xpathQuery, value, monitor );
    }
  }
  
  public IFunction getFunction() {
    return this.function;
  }
  
  
  public List<String> getXPathQueries() {
    return xPathQueries;
  }
}
