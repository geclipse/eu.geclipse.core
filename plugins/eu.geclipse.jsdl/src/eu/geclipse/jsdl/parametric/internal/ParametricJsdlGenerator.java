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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.parametric.IParametricJsdlGenerator;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.xpath.XPathDocument;


/**
 * This class gets parametric JSDL and generate a bunch of non-parametric jsdl
 */
public class ParametricJsdlGenerator implements IParametricJsdlGenerator {
  private XPathDocument xpath;
  private JSDLJobDescription parametricJsdl;

  public ParametricJsdlGenerator( final JSDLJobDescription parametricJsdl ) {
    this.parametricJsdl = parametricJsdl;
    this.xpath = new XPathDocument( parametricJsdl.getXml() );
  }

  private void processSweeps( final NodeList sweeps, final IGenerationContext generationContext, final List<Integer> iterationsStack, final SubMonitor monitor ) throws ProblemException {    

    iterationsStack.add( new Integer( 0 ) );
    
    for( int index = 0; index < sweeps.getLength(); index++ ) {
      List<Assignment> assignmentList = new ArrayList<Assignment>();
      Node sweepItem = sweeps.item( index );
      
      if( sweepItem instanceof Element ) {
        Element currentSweep = ( Element )sweepItem;
        
        processAssignments( this.xpath.getNodes( currentSweep, "./sweep:Assignment" ), assignmentList );
        NodeList childSweeps = this.xpath.getNodes( currentSweep, "./sweep:Sweep" );
        
        // TODO mariusz Throw exception when maxIterations is 0 (not values defined)        
        List<Iterator<String>> iterators = getIterators( assignmentList );
        
        while( hasNext( iterators ) ) {
          testCancelled( monitor );
          IGenerationContext newContext = generationContext.clone();
          // we will modify copy of jsdl
          setParamsValue( assignmentList, iterators, newContext, monitor );   
          
          if( childSweeps.getLength() > 0 ) {
            processSweeps( childSweeps, newContext, iterationsStack, monitor );
          } else {            
            newContext.storeGeneratedJsdl( iterationsStack, monitor );
            monitor.worked( 1 );
          }
          
          increaseCurrentIteration( iterationsStack );
        }        
      } // TODO mariusz throw exc: expected sweep with children
    }
    
    iterationsStack.remove( iterationsStack.size() - 1 );
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
                                    final IGenerationContext generationContext, final SubMonitor monitor ) throws ProblemException  {
    Iterator<Iterator<String>> fIterator = functionIterators.iterator();
    Iterator<Assignment> aIterator = assignmentList.iterator();
    while( fIterator.hasNext() && aIterator.hasNext() ) {
      testCancelled( monitor );
      Assignment assignment = aIterator.next();
      Iterator<String> functionIterator = fIterator.next();
      assignment.setParamValue( functionIterator, generationContext, monitor );
    }
  }

  private void processAssignments( final NodeList assignmentsList,
                                   final List<Assignment> assignmentList ) throws ProblemException
  {
    for( int index = 0; index < assignmentsList.getLength(); index++ ) {
      Node item = assignmentsList.item( index );
      
      if( item instanceof Element ) {
        Element assignment = ( Element )item;
        
        NodeList parameters = this.xpath.getNodes( assignment, "./sweep:Parameter" );
        NodeList values = this.xpath.getNodes( assignment, "./sweepfunc:Values/sweepfunc:Value" );
        NodeList loops = this.xpath.getNodes( assignment, "./sweepfunc:Loop" );
        
        // TODO mariusz check: values and loops cannot be defined both
        
        IFunction function = null;
        
        if( values.getLength() > 0 ) {
          function = new FunctionValues( values );
        } else if( loops.getLength() > 0 ) {
          function = new FunctionIntegerLoop( loops, this.xpath );
        }
        
        assignmentList.add( new Assignment( this.xpath, parameters, function ) );
                
      } // TODO mariusz throw exception: children expected
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

  // TODO mariusz remove that method:
  private void printXml( final Document document ) {
    try {
    document.normalizeDocument();    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.transform( new DOMSource( document ), new StreamResult( outputStream ) );
    System.out.println( outputStream.toString() );
    } catch ( Exception e) {
      Activator.logException( e );
    }
    
  }
  
  private NamespaceContext getNamespaceContext() {
    
    return new NamespaceContext() {
// TODO mariusz parse source jsdl document add here all prefixes and namespaces (user may use these prefixes)

      public String getNamespaceURI( final String prefix ) {
        String namespace = XMLConstants.NULL_NS_URI;
        if ("jsdl".equals(prefix)) {  //$NON-NLS-1$
          namespace = "http://schemas.ggf.org/jsdl/2005/11/jsdl"; //$NON-NLS-1$
        }
        else if( "jsdl-posix".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://schemas.ggf.org/jsdl/2005/11/jsdl-posix"; //$NON-NLS-1$
        }
        else if( "sweep".equals( prefix ) ) {  //$NON-NLS-1$
          namespace = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep" ; //$NON-NLS-1$
        }
        else if( "sweepfunc".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep/functions" ; //$NON-NLS-1$
        }
        else if ("xml".equals(prefix)) { //$NON-NLS-1$
          namespace = XMLConstants.XML_NS_URI;
        }        
        return namespace;
      }

      public String getPrefix( final String namespaceURI ) {
        throw new UnsupportedOperationException();
      }

      @SuppressWarnings("unchecked")
      public Iterator getPrefixes( final String namespaceURI ) {
        throw new UnsupportedOperationException();
      }};
  }

  public void generate( final IParametricJsdlHandler handler,
                        final IProgressMonitor monitor )
  {
    // TODO mariusz handle progress monitor
    SubMonitor subMonitor = SubMonitor.convert( monitor );
    
    try {
      Document parametricXml = this.parametricJsdl.getXml();
      Document baseJsdl = removeSweepNodes( parametricXml );
      IGenerationContext generationContext = new GenerationContext( this.parametricJsdl, baseJsdl, handler, this.xpath );

      NodeList sweeps = this.xpath.getNodes( parametricXml.getDocumentElement(), "./sweep:Sweep" );
      
      // first generation only for validation and gathering information
      CounterGenerationContext counter = new CounterGenerationContext();      
      processSweeps( sweeps, counter, new ArrayList<Integer>(), subMonitor.newChild( 0 ) );    
      handler.generationStarted( counter.getIterations(), counter.getParameters() );
      
      subMonitor.subTask( "Generating JSDLs" );
      subMonitor.setWorkRemaining( counter.getIterations() );
      processSweeps( sweeps, generationContext, new ArrayList<Integer>(), subMonitor );
      handler.generationFinished();
    } catch ( ProblemException exc ) {
      // TODO mariusz 
      Activator.logException( exc );
    }    
  }

  private void testCancelled( final IProgressMonitor monitor ) {
    if( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }

}
