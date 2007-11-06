package eu.geclipse.jsdl;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Messages for jsdl package
 * @author mariusz
 *
 */
public class Messages {

  private static final String BUNDLE_NAME = "eu.geclipse.jsdl.messages"; //$NON-NLS-1$
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

  private Messages() {
    // empty constructor
  }

  /**
   * @param key
   * @return String
   */
  public static String getString( final String key ) {
    String string = null;
    try {
      string = RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException e ) {
      string = '!' + key + '!';
    }
    return string;
  }
}
