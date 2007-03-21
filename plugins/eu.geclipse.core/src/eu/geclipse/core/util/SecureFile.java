/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Ariel Garcia - initial implementation
 *****************************************************************************/

package eu.geclipse.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URI;

/**
 * This class is an extension of java.io.File providing creation of 
 * files and folders with secure permissions, if supported by the 
 * underlying OS! Sadly there is no way to manage file/folder permissions
 * in Java <= 5 or in Eclipse itself. This class should go away with
 * the use of Java 6...
 * 
 * @author ariel
 * @see    java.io.File
 */
public class SecureFile extends File {
    
  /**
   * Creates a new <code>SecureFile</code> instance by converting the
   * given pathname string into an abstract pathname. Never forget to
   * actually _create_ the file with one of the create*File() methods,
   * or call setSecure() if the file already exists, before writing
   * data inside with a FileOutputStream!
   * 
   * @see   java.io.File#File(String)
   * @param pathname A pathname string
   */
  public SecureFile( String pathname ) {
    super( pathname );
  }

  /**
   * Creates a new <code>SecureFile</code> instance from a parent
   * pathname string and a child pathname string. Never forget to
   * actually _create_ the file with one of the create*File() methods,
   * or call setSecure() if the file already exists, before writing
   * data inside with a FileOutputStream!
   * 
   * @see   java.io.File#File(String, String)
   * @param parent The parent abstract pathname
   * @param child  The child pathname string
   */
  public SecureFile( String parent, String child ) {
    super( parent, child );
  }

  /**
   * Creates a new <code>SecureFile</code> instance from a parent abstract
   * pathname and a child pathname string. Never forget to
   * actually _create_ the file with one of the create*File() methods,
   * or call setSecure() if the file already exists, before writing
   * data inside with a FileOutputStream!
   * 
   * @see   java.io.File#File(File, String)
   * @param parent The parent abstract pathname
   * @param child  The child pathname string
   */
  public SecureFile( File parent, String child ) {
    super( parent, child );
  }

  /**
   * Creates a new <code>SecureFile</code> instance by converting the
   * given URI into an abstract pathname. Never forget to
   * actually _create_ the file with one of the create*File() methods,
   * or call setSecure() if the file already exists, before writing
   * data inside with a FileOutputStream!
   * 
   * @see   java.io.File#File(URI)
   * @param uri A URI
   */
  public SecureFile( URI uri ) {
    super( uri );
  }


  /**
   * Private method for setting the secure permissions
   */
  private static boolean protect( String path ) {
    int retVal = 0;

    // TODO: implement this for Windows. This is insecure, but keeps
    //       the current behaviour until it is implemented!
    if ( System.getProperty( "os.name" ).contains( "Windows" ) ) {
      return true;
    }
    
    /*
     * UNIX specific, using a library call could be more efficient but
     *   less portable, at least exec'ing chmod should work on all unixes...
     */
    String mode = "600";
    SecureFile f = new SecureFile( path );
    if ( f.isDirectory() ) {
      mode = "700";
    }
    String[] cmd = { "chmod", mode, path };
    String[] env = { "PATH=/bin;/usr/bin;/usr/local/bin" };
    Runtime rt = Runtime.getRuntime();
    Process proc = null;
    try {
      proc = rt.exec( cmd, env );
    } catch (Exception ex) {
      // TODO: replace console logging with appropriate (g)eclipse tools
      System.out.println( "Failed to set secure permissions:"
                          + "Exception running chmod:" + ex.toString() );
      return false;
    }

    // Output?
    InputStream eStr = proc.getErrorStream();
    BufferedReader eB = new BufferedReader( new InputStreamReader( eStr ) );
    InputStream oStr = proc.getInputStream();
    BufferedReader oB = new BufferedReader( new InputStreamReader( oStr ) );

    // Error?
    try {
      retVal = proc.waitFor();
    } catch ( InterruptedException ie ) {
      // Something went wrong
      retVal = 1;
      System.out.println( "Failed to set secure permissions:"
                          + "Exception running chmod:" + ie.toString() );
    }
    if ( retVal != 0 ) {
      System.out.println( "Failed to set secure permissions: " );
      String line = null;
      try {
        while ( (line = eB.readLine()) != null ) {
          System.out.println( line );
        }
      } catch ( IOException ioe ) {
        //
      }
      return false;
    }

    return true;
  }

  
  /**
   * Set secure permissions on the existing file named by this abstract pathname.
   *
   * @return <code>true</code> if the named file could be set to secure
   *         permissions; <code>false</code> if the file didn't exist
   * @throws IOException if setting the permissions failed
   */
  public boolean setSecure() throws IOException {
    String filePath = getPath();
    boolean ret = protect( filePath );
    if ( ret != true ) {
      throw new IOException( "Could not set secure permissions on file" + filePath );
    }
    return ret;
  }

  /**
   * Tests whether the file named by this abstract pathname has secure 
   * permissions.
   *
   * @return <code>true</code> if the named file exists and has secure
   *         permissions; <code>false</code> otherwise
   */
  public boolean isSecure() {
    String filePath = getPath();
    // TODO: implement this. Right now we just return false, so
    //       setSecure() will be called in any case.
    return false;
  }

  /**
   * Atomically creates a new, empty, protected file named by this abstract pathname if
   * and only if a file with this name does not yet exist.
   *
   * @return <code>true</code> if the named file does not exist and was
   *         successfully created with secure permissions; <code>false</code>
   *         if the named file already exists
   * @throws IOException if setting the permissions failed
   */
  public boolean createNewFile() throws IOException {
    if ( super.createNewFile() == false ) {
      return false;
    }

    String filePath = getPath();
    boolean ret = protect( filePath );
    if ( ret != true ) {
      // CreateNewFile succeded, but setting the secure permissions didn't...
      File dfile = new File( filePath );
      dfile.delete();
      // TODO: check this method's policy regarding return value and
      //       exception throwing in the superclass...
      throw new IOException( "Could not set secure permissions on file" + filePath );
    }
    return ret;
  }

  /**
   * Creates a protected empty file in the default temporary-file directory,
   * using the given prefix and suffix to generate its name. Invoking this
   * method is equivalent to invoking
   *   <code>{@link #createTempFile(java.lang.String, java.lang.String, java.io.File)
   *   createTempFile(prefix,&nbsp;suffix,&nbsp;null)}</code>.
   * 
   * @see    java.io.File#createTempFile(String, String)
   * @return An abstract pathname denoting a new empty protected file
   */
  public static SecureFile createTempFile(String prefix, String suffix)
    throws IOException {
    
    return createTempFile(prefix, suffix, null);
  }

  /**
   * Creates a protected empty file in the specified directory, using the
   * given prefix and suffix to generate its name.
   * 
   * @see    java.io.File#createTempFile(String, String, File)
   * @return An abstract pathname denoting a new empty protected file
   */
  public static SecureFile createTempFile(String prefix, String suffix, File directory)
    throws IOException {
    
    File file = File.createTempFile(prefix, suffix, directory);
    String filePath = file.getPath();
    
    SecureFile secFile = new SecureFile( filePath );
    
    if ( protect( filePath ) != true ) {
      throw new IOException( "Could not set secure permissions on file" + filePath);
    }
    return secFile;
  }

  /**
   * Renames the file denoted by this abstract pathname.
   * 
   * @see    java.io.File#renameTo(File)
   * @return <code>true</code> if and only if the renaming succeeded;
   *         <code>false</code> otherwise
   */
  public boolean renameTo( File dest ) {
    return super.renameTo( dest ) && protect( getPath() );
  }

  /**
   * Creates the secure directory named by this abstract pathname.
   * 
   * @see    java.io.File#mkdir()
   * @return <code>true</code> if and only if the directory was
   *         created with secure permissions; <code>false</code> otherwise
   */
  public boolean mkdir() {
    return super.mkdir() && protect( getPath() );
  }

  /**
   * Creates the secure directory named by this abstract pathname, 
   * including any necessary but nonexistent parent directories.
   * 
   * @see    java.io.File#mkdirs()
   * @return <code>true</code> if and only if the directory was created
   *         with secure permissions, along with all necessary parent
   *         directories; <code>false</code> otherwise
   */
  public boolean mkdirs() {
    return super.mkdirs() && protect( getPath() );
  }

  /** Declare some serialVersionUID */
  private static final long serialVersionUID = 101010123456789010L;

}
