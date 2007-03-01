package eu.geclipse.core.model;


/**
 * A <code>IStorableElement</code> defines methods to save
 * an element to an <code>OutputStream</code> and to load it
 * from an <code>InputStream</code>. That makes that element
 * persistent.
 */

public interface IStorableElement extends IGridElement {
  
  /**
   * Load the properties of that element from the specified
   * input stream. That may cause that some predefined properties
   * may be overwritten with the loaded properties.
   * 
   * @param from The <code>java.lang.InputStream</code> from
   * which to load the properties.
   * @throws GridModelException If an error occures while loading
   * this element. This is mainly due to <code>IOException</code>s.
   */
  public void load() throws GridModelException;
  
  /**
   * Write the properties of that element to the specified
   * output stream.
   * 
   * @param to The <code>java.lang.OutputStream</code> to which
   * the element's properties should be written.
   * @throws GridModelException If an error occures while saving
   * this element. This is mainly due to <code>IOException</code>s.
   */
  public void save() throws GridModelException;
  
}
