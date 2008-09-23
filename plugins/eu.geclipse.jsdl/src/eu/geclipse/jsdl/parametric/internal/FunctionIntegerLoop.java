package eu.geclipse.jsdl.parametric.internal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.xpath.XPathDocument;


/**
 * Function, which can assign next integer values to parameter over the loop.
 * This function accept also exceptions.
 */
class FunctionIntegerLoop implements IFunction {  
  int start;
  int end;
  int step;
  Set<Integer> exceptions;

  FunctionIntegerLoop( final NodeList nodes, final XPathDocument xpath ) throws ProblemException {    
    if( nodes.getLength() == 1 ) {
      Node item = nodes.item( 0 );      
      this.start = getIntAttribute( item, "sweepfunc:start", true, 0 ); //$NON-NLS-1$
      this.end = getIntAttribute( item, "sweepfunc:end", true, 0 ); //$NON-NLS-1$
      this.step = getIntAttribute( item, "sweepfunc:step", false, 1 ); //$NON-NLS-1$
      
      if( this.step == 0 ) {
        String msg = Messages.FunctionIntegerLoop_errStepIsZero;
        throw new ProblemException( "eu.geclipse.jsdl.problem.sweepExtensionSyntaxError", msg, Activator.PLUGIN_ID ); //$NON-NLS-1$
      }
      
      this.exceptions = readExceptions( item, xpath );
    } else {      
      String msg = String.format( Messages.FunctionIntegerLoop_errOnlyOneLoopIsAllowed, Integer.valueOf( nodes.getLength() ) );
      throw new ProblemException( "eu.geclipse.jsdl.problem.sweepExtensionSyntaxError", msg, Activator.PLUGIN_ID );       //$NON-NLS-1$
    }
  }
  
  private int getIntAttribute( final Node item, final String attrName, final boolean mandatory, final int defaultValue ) throws ProblemException {
    int value = defaultValue;
    String textContent = null;
    
    try {
      NamedNodeMap attributes = item.getAttributes();
      Node attr = attributes.getNamedItem( attrName );
      if( attr != null ) {
        textContent = attr.getTextContent();
        value = Integer.parseInt( textContent );
      } else if( mandatory ) {
        String msg = String.format( Messages.FunctionIntegerLoop_errAttrIsMissing, attrName );
        throw new ProblemException( "eu.geclipse.jsdl.problem.sweepExtensionSyntaxError", msg, Activator.PLUGIN_ID ); //$NON-NLS-1$
      }
    } catch( NumberFormatException exception ) {
      String msg = String.format( Messages.FunctionIntegerLoop_errIntegerExpected, attrName, textContent == null ? "" : textContent ); //$NON-NLS-1$
      throw new ProblemException( "eu.geclipse.jsdl.problem.sweepExtensionSyntaxError", msg, exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    }
    
    return value;
  }

  private Set<Integer> readExceptions( final Node loopNode,
                                       final XPathDocument xpath )
    throws ProblemException
  {
    Set<Integer> exceptionsSet = new HashSet<Integer>();
    if( loopNode instanceof Element ) {
      Element loopElement = ( Element )loopNode;
      NodeList nodeList = xpath.getNodes( loopElement, "./sweepfunc:Exception" ); //$NON-NLS-1$
      for( int index = 0; index < nodeList.getLength(); index++ ) {
        Node exceptionNode = nodeList.item( index );
        exceptionsSet.add( Integer.valueOf( getIntAttribute( exceptionNode, "sweepfunc:value", false, 0 ) ) ); //$NON-NLS-1$
      }
    }
    return exceptionsSet;
  }
  
  public Iterator<String> iterator() {
    return new LoopIterator();
  }

  
  class LoopIterator implements Iterator<String> {
    private int currentValue = FunctionIntegerLoop.this.start - FunctionIntegerLoop.this.step;

    public boolean hasNext() {
      int value = this.currentValue + FunctionIntegerLoop.this.step;
      
      while( FunctionIntegerLoop.this.exceptions.contains( Integer.valueOf( value ) ) && value < FunctionIntegerLoop.this.end ) {
        value += FunctionIntegerLoop.this.step;
      }
      
      return value <= FunctionIntegerLoop.this.end;
    }

    public String next() {      
      do{
        this.currentValue += FunctionIntegerLoop.this.step;        
      } while( FunctionIntegerLoop.this.exceptions.contains( Integer.valueOf( this.currentValue ) ) && this.currentValue < FunctionIntegerLoop.this.end );
      
      return Integer.toString( this.currentValue );
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }    
  }
}
