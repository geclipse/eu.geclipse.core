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

import com.xerox.amazonws.ec2.TerminatingInstanceDescription;

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} terminates a list of given instances. It changes
 * their state, which can be obtained from an {@link EC2OpDescribeInstances},
 * to "shutting-down".
 * 
 * @author Moritz Post
 */
public class EC2OpTerminateInstances implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The list of instances to terminate. */
  private List<String> instanceList;

  /**
   * The exception which might have been thrown when invoking this
   * {@link IOperation}.
   */
  private Exception exception;

  /** A {@link List} of the terminating instances. */
  private List<TerminatingInstanceDescription> result;

  /**
   * Creates a new {@link EC2OpTerminateInstances} with the provided
   * instanceList as the instances to terminate.
   * 
   * @param ec2Service the {@link IEC2} to interface with
   * @param instanceList the list of instance ids to terminate
   */
  public EC2OpTerminateInstances( final IEC2 ec2Service,
                                  final List<String> instanceList )
  {
    {
      this.ec2Service = ec2Service;
      this.instanceList = instanceList;
    }
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.terminateInstances( this.instanceList );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public Exception getException() {
    return this.exception;
  }

  public List<TerminatingInstanceDescription> getResult() {
    return this.result;
  }

}
