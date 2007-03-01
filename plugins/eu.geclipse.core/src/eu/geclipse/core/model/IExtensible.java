package eu.geclipse.core.model;

/**
 * This interface has to be implemented by each grid element that
 * is entended to be extended. It provides a single method that
 * returns the extension ID of the creator that was responsible for
 * the creation of that element.
 */
public interface IExtensible {
  
  /**
   * Get the ID of the extension of the grid element creator extension
   * point that was responsible for the creation of this element.
   * 
   * @return The creators extension ID.
   */
  public String getExtensionID();
  
}
