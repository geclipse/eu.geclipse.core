package eu.geclipse.jsdl.parametric.internal;

import java.util.Iterator;

import org.w3c.dom.NodeList;


class FunctionValues implements IFunction {
  NodeList values;

  public FunctionValues( final NodeList values ) {
    super();
    this.values = values;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.internal.IFunction#getIterations()
   */
  public int getIterations() {
    return this.values.getLength();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.internal.IFunction#getValue(int)
   */
  public String getValue( final int iteration ) {
    return this.values.item( iteration ).getTextContent();
  }
  
  public Iterator<String> iterator() {
    return new ValuesIterator();
  }
  
  class ValuesIterator implements Iterator<String> {
    private int iteration = -1;

    public boolean hasNext() {
      return this.iteration + 1 < FunctionValues.this.values.getLength();
    }

    public String next() {
      return FunctionValues.this.values.item( ++this.iteration ).getTextContent();
    }

    public void remove() {
      throw new UnsupportedOperationException();      
    }    
  }

    
}
