package eu.geclipse.ui.internal.dialogs.ConfigureFilters;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class Messages {
  private static final String BUNDLE_NAME = "eu.geclipse.ui.internal.dialogs.ConfigureFilters.messages"; //$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle(BUNDLE_NAME);

  private Messages() {
    // empty implementation
  }

  /**
   * Returns a localised version of a message.
   * 
   * @param key key for the message.
   * @return the localised string.
   */
  public static String getString( final String key ) {
    String resultString = '!' + key + '!'; 
    try {
      resultString = RESOURCE_BUNDLE.getString( key );
    } catch( MissingResourceException mrEx ) {
      // Nothing to do here
    }
    return resultString;
  }
  
}
