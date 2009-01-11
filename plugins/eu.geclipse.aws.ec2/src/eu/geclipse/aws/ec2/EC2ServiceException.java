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

package eu.geclipse.aws.ec2;

import eu.geclipse.aws.AWSException;

/**
 * This exception deals with any failures occurring within the context of the
 * Amazon Ec2 Webservices.
 * 
 * @author Moritz Post
 */
public class EC2ServiceException extends AWSException {

  /** The serial of this {@link EC2ServiceException}. */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new {@link EC2ServiceException} with the message given.
   * 
   * @param message the message describing the exception
   */
  public EC2ServiceException( final String message ) {
    super( message );
  }

  /**
   * Create a new {@link EC2ServiceException} with the message and exception
   * describing the {@link Exception}.
   * 
   * @param message the message to describe the exception
   * @param throwable the cause of the exception
   */
  public EC2ServiceException( final String message, final Throwable throwable )
  {
    super( message, throwable );
  }

}
