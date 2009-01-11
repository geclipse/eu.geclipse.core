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

package eu.geclipse.aws.ec2.op;

import java.util.List;

import com.xerox.amazonws.ec2.ImageDescription;

/**
 * This base class provides some common functionality for classes wanting to
 * fetch the list or available AMIs.
 * 
 * @author Moritz Post
 */
public abstract class AbstractEC2OpDescribeImages implements IOperation {

  /** The resulting list of AMIs */
  private List<ImageDescription> result;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  abstract public void run();

  public List<ImageDescription> getResult() {
    return this.result;
  }

  public Exception getException() {
    return this.exception;
  }

  /**
   * A setter for {@link #result}.
   * 
   * @param describeImagesByOwner the param to set
   */
  protected void setResult( final List<ImageDescription> describeImagesByOwner )
  {
    this.result = describeImagesByOwner;
  }

  /**
   * A setter for {@link #exception}.
   * 
   * @param ex the exception to set
   */
  protected void setException( final Exception ex ) {
    this.exception = ex;
  }
}
