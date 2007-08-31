package eu.geclipse.core.auth;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the {@link PasswordManager} class.
 */
public class PasswordManager_Test {
  
  private static final String PWUID = "pwuid"; //$NON-NLS-1$
  
  private static final String PW_1 = "Some&%$Cryptic=)&Password'*)(&%";   //$NON-NLS-1$

  private static final String PW_2 = "thisissimple";   //$NON-NLS-1$

  /**
   * Tests the {@link PasswordManager#getPassword(String)}-method by
   * registering a password and afterwards retrieving this password and
   * comparing it with the original one.
   */
  @Test
  public void testGetPassword() {
    PasswordManager.registerPassword( PWUID, PW_1 );
    String pw = PasswordManager.getPassword( PWUID );
    Assert.assertSame( pw, PW_1 );
  }

  /**
   * Test the {@link PasswordManager#registerPassword(String, String)}-method
   * by registering a password, registering another password, retrieving the
   * password and afterwards comparing the password with the second registered.
   */
  @Test
  public void testRegisterPassword() {
    PasswordManager.registerPassword( PWUID, PW_1 );
    PasswordManager.registerPassword( PWUID, PW_2 );
    String pw = PasswordManager.getPassword( PWUID );
    Assert.assertSame( pw, PW_2 );
  }

  /**
   * Test the {@link PasswordManager#erasePassword(String)}-method by
   * registering a password, retrieving this password and verifying that it was
   * registered successfully, erasing the password and verifying that it is
   * <code>null</code> afterwards.
   */
  @Test
  public void testErasePassword() {
    PasswordManager.registerPassword( PWUID, PW_1 );
    String pw = PasswordManager.getPassword( PWUID );
    Assert.assertSame( pw, PW_1 );
    PasswordManager.erasePassword( PWUID );
    pw = PasswordManager.getPassword( PWUID );
    Assert.assertNull( pw );
  }
}
