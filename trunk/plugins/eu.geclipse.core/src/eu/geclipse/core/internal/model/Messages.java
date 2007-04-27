package eu.geclipse.core.internal.model;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Auto-generated class.
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.core.internal.model.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // empty implementation
  }

  /**
   * Auto-generated method.
   * 
   * @param key The key.
   * @return The value.
   */
  public static String getString( final String key ) {
    String result = '!' + key + '!';
    try {
      result = RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException e ) {
      // Nothing to do;
    }
    return result;
  }
  
}
