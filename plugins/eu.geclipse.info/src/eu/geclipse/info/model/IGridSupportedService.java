package eu.geclipse.info.model;


/**
 * @author tnikos
 *
 */
public interface IGridSupportedService {
  
  /**
   * Returns the service type that is supported
   * @return A string with the type of the service
   */
  public String getType();
  
  /**
   * Gets the versions of the service type that are supported. If null is returned it is
   * be ignored.
   * @return An array of String
   */
  public String[] getVersion();
}
