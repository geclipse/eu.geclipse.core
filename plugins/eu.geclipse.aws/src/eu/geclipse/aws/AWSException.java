/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws;

/**
 * An exception thrown in the context of the Amazon webservice infrastructure.
 * 
 * @author Moritz Post
 */
public class AWSException extends Exception {

  /** Default serial. */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new exception with the given message. Similar to
   * {@link Exception#Exception(String)}.
   * 
   * @param message the message to propagate
   */
  public AWSException( final String message ) {
    super( message );
  }

  /**
   * Create a new exception with the given message and exception. Similar to
   * {@link Exception#Exception(String, Throwable)}.
   * 
   * @param message the message to propagate
   * @param throwable the cause of the exception
   */
  public AWSException( final String message, final Throwable throwable ) {
    super( message, throwable );
  }

}
