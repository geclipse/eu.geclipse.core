package eu.geclipse.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Returns the localised messages for this package.
 */
public class Messages {
  private static final String BUNDLE_NAME = "eu.geclipse.core.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // not instanceable
  }

  /**
   * Returns a localised version of a message.
   * 
   * @param key key for the message.
   * @return the localised string.
   */
  public static String getString( final String key ) {
    String message;
    try {
      message = RESOURCE_BUNDLE.getString( key );
    } catch( final MissingResourceException exception ) {
      message = '!' + key + '!';
    }
    return message;
  }
}
