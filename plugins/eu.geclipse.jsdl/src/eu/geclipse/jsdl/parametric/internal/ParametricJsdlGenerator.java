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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.jsdl.parametric.IParametricJsdlGenerator;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.parametric.ParametricGenerationCanceled;
import eu.geclipse.jsdl.parametric.ParametricJsdlException;
import eu.geclipse.jsdl.xpath.XPathDocument;


/**
 * This class gets parametric JSDL and generate a bunch of non-parametric jsdl
 */
public class ParametricJsdlGenerator implements IParametricJsdlGenerator {
  private XPathDocument xpath;
  private String parametricJsdl;

  /**
   * @param parametricJsdl
   */
  public ParametricJsdlGenerator( final String parametricJsdl ) {
    this.parametricJsdl = parametricJsdl;    
  }

  private void processSweeps( final NodeList sweeps, final IGenerationContext generationContext, final List<Integer> iterationsStack ) throws ParametricJsdlException {    
    
    try {
    iterationsStack.add( new Integer( 0 ) );
    
    for( int index = 0; index < sweeps.getLength(); index++ ) {
      List<Assignment> assignmentList = new ArrayList<Assignment>();
      Node sweepItem = sweeps.item( index );
      
      if( sweepItem instanceof Element ) {
        Element currentSweep = ( Element )sweepItem;
        
        processAssignments( this.xpath.getNodes( currentSweep, "./sweep:Assignment" ), assignmentList, generationContext ); //$NON-NLS-1$
        NodeList childSweeps = this.xpath.getNodes( currentSweep, "./sweep:Sweep" ); //$NON-NLS-1$
        List<Iterator<String>> iterators = getIterators( assignmentList );
        
        while( hasNext( iterators ) ) {
          testCanceled( generationContext );
          // we will modify copy of jsdl
          IGenerationContext newContext = generationContext.clone();
          setParamsValue( assignmentList, iterators, newContext );
          
          if( childSweeps.getLength() > 0 ) {
            processSweeps( childSweeps, newContext, iterationsStack );
          } else {            
            newContext.storeGeneratedJsdl( iterationsStack );
          }
          
          increaseCurrentIteration( iterationsStack );
        }        
      } else {
        throw new ParametricJsdlException( "<sweep:Sweep> should be instance of XML Element" ); //$NON-NLS-1$
      }
    }
    
    iterationsStack.remove( iterationsStack.size() - 1 );
    } catch( XPathExpressionException exception ) {
      throw new ParametricJsdlException( "Error occured during calling XPath query", exception ); //$NON-NLS-1$
    }
  }

  private boolean hasNext( final List<Iterator<String>> iterators ) {
    boolean hasNext = false;
    
    for( Iterator<String> iterator : iterators ) {
      if( iterator.hasNext() ) {
        hasNext = true;
        break;
      }      
    }

    return hasNext;
  }

  private List<Iterator<String>> getIterators( final List<Assignment> assignmentList )
  {
    List<Iterator<String>> iterators = new ArrayList<Iterator<String>>( assignmentList.size() );
    
    for( Assignment assignment : assignmentList ) {
      iterators.add( assignment.getFunction().iterator() );
    }
    
    return iterators;    
  }

  private void increaseCurrentIteration( final List<Integer> iterationsStack ) {
    int lastIndex = iterationsStack.size() - 1;
    Integer iteration = iterationsStack.get( lastIndex );
    iterationsStack.set( lastIndex, Integer.valueOf( iteration.intValue() + 1 ) );    
  }

  private void setParamsValue( final List<Assignment> assignmentList,
                                    final List<Iterator<String>> functionIterators,
                                    final IGenerationContext generationContext ) throws ParametricJsdlException  {
    Iterator<Iterator<String>> fIterator = functionIterators.iterator();
    Iterator<Assignment> aIterator = assignmentList.iterator();
    while( fIterator.hasNext() && aIterator.hasNext() ) {
      testCanceled( generationContext );
      Assignment assignment = aIterator.next();
      Iterator<String> functionIterator = fIterator.next();
      
      if( functionIterator.hasNext() ) {
        assignment.setParamValue( functionIterator, generationContext );
      } else {
        throw createWrongNrValueException( assignmentList );
      }
    }
  }

  private ParametricJsdlException createWrongNrValueException( final List<Assignment> assignmentList )
  {
    StringBuilder builder = new StringBuilder( "Following parameters should contain the same number of values:" ); //$NON-NLS-1$
   
    for( Assignment assignment : assignmentList ) {
      for( String query : assignment.getXPathQueries() ) {
        builder.append( "\n" ); //$NON-NLS-1$
        builder.append( query );
      }
    }

    return new ParametricJsdlException( builder.toString() );
  }

  private void processAssignments( final NodeList assignmentsList,
                                   final List<Assignment> assignmentList, final IGenerationContext context ) throws ParametricJsdlException 
  {
    try {
      for( int index = 0; index < assignmentsList.getLength(); index++ ) {
        testCanceled( context );
        Node item = assignmentsList.item( index );
        
        if( item instanceof Element ) {
          Element assignment = ( Element )item;
          
          NodeList parameters = this.xpath.getNodes( assignment, "./sweep:Parameter" ); //$NON-NLS-1$
          NodeList values = this.xpath.getNodes( assignment, "./sweepfunc:Values/sweepfunc:Value" ); //$NON-NLS-1$
          NodeList loops = this.xpath.getNodes( assignment, "./sweepfunc:Loop" ); //$NON-NLS-1$
  
          if( values.getLength() > 0
              && loops.getLength() > 0 ) {
            throw new ParametricJsdlException( "<sweepfunc:Values> and <sweepfunc:Loop> cannot be defined both at the same time in one <sweep:Assignment>" ); //$NON-NLS-1$
          }
  
          IFunction function = null;
          
          if( values.getLength() > 0 ) {
            function = new FunctionValues( values );
          } else if( loops.getLength() > 0 ) {
            function = new FunctionIntegerLoop( loops, this.xpath );
          }
          
          assignmentList.add( new Assignment( this.xpath, parameters, function ) );
                  
        }else {
          throw new ParametricJsdlException( "<sweep:Assignment> should be instance of XML Element" ); //$NON-NLS-1$
        }
      }
      
      if( assignmentList.isEmpty() ) {
        throw new ParametricJsdlException( "<sweep:Sweep> should contain at least one <sweep:Assignment> as a child." ); //$NON-NLS-1$
      }
    } catch( XPathExpressionException exception ) {
      throw new ParametricJsdlException( "Error occured during calling XPath query", exception ); //$NON-NLS-1$
    }
  }

  private Document removeSweepNodes( final Document parametricJsdlDocument ) {
    Document changedDocument = ( Document )parametricJsdlDocument.cloneNode( true );
    NodeList nodes = changedDocument.getElementsByTagName( "sweep:Sweep" ); //$NON-NLS-1$
    for( int index = 0; index < nodes.getLength(); index++ ) {
      Node node = nodes.item( index );
      changedDocument.getDocumentElement().removeChild( node );
    }
    return changedDocument;
  }
  
  public void generate( final IParametricJsdlHandler handler ) throws ParametricJsdlException, ParametricGenerationCanceled
  {
    try {
      Document parametricXml = createDomDocument();
      this.xpath = new XPathDocument( parametricXml );      
      Document baseJsdl = removeSweepNodes( parametricXml );
      IGenerationContext generationContext = new GenerationContext( baseJsdl, handler, this.xpath );
  
      NodeList sweeps = this.xpath.getNodes( parametricXml.getDocumentElement(), "./sweep:Sweep" ); //$NON-NLS-1$
      
      // first generation only for validation and gathering information
      CounterGenerationContext counter = new CounterGenerationContext( handler );
      processSweeps( sweeps, counter, new ArrayList<Integer>() );    
      handler.generationStarted( counter.getIterations(), counter.getParameters() );
      
      processSweeps( sweeps, generationContext, new ArrayList<Integer>() );
    } catch( XPathExpressionException exception ) {
      throw new ParametricJsdlException( "Error occured during calling XPath query", exception ); //$NON-NLS-1$
    } finally {
      handler.generationFinished();
    }
  }

  private Document createDomDocument() throws ParametricJsdlException {    
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware( true );      
      return factory.newDocumentBuilder().parse( new ByteArrayInputStream( this.parametricJsdl.getBytes() ) );
    } catch( Exception exception ) {
      throw new ParametricJsdlException( "Unable to parse parametric JSDL as xml file", exception ); //$NON-NLS-1$
    }    
  }

  private void testCanceled( final IGenerationContext context ) throws ParametricGenerationCanceled {
    if( context.getHandler().isCanceled() ) {
      throw new ParametricGenerationCanceled();
    }
  }

}
