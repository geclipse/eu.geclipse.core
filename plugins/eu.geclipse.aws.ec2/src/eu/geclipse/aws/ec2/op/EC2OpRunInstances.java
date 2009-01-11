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

/**
 * 
 */
package eu.geclipse.aws.ec2.op;

import com.xerox.amazonws.ec2.ReservationDescription;

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} launches an AMI Instance.
 * 
 * @author Moritz Post
 */
public class EC2OpRunInstances implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The {@link AMILaunchConfiguration} to use when running the AMI. */
  private AMILaunchConfiguration launchConfig;

  /** The {@link ReservationDescription} produced by this {@link IOperation}. */
  private ReservationDescription result;

  /**
   * The exception which might have been thrown when invoking the
   * {@link IOperation}.
   */
  private Exception exception;

  /**
   * Creates a {@link EC2OpRunInstances} with the provided
   * {@link AMILaunchConfiguration} as the AMI details source.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param launchConfig the {@link AMILaunchConfiguration} to use initially
   */
  public EC2OpRunInstances( final IEC2 ec2Service,
                            final AMILaunchConfiguration launchConfig )
  {
    this.ec2Service = ec2Service;
    this.launchConfig = launchConfig;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.runInstances( this.launchConfig );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public Exception getException() {
    return this.exception;
  }

  public ReservationDescription getResult() {
    return this.result;
  }

  /**
   * A getter for the {@link AMILaunchConfiguration}.
   * 
   * @return the launchConfig
   */
  public AMILaunchConfiguration getLaunchConfig() {
    return this.launchConfig;
  }

  /**
   * A setter for the {@link AMILaunchConfiguration}.
   * 
   * @param launchConfig the launchConfig to set
   */
  public void setLaunchConfig( final AMILaunchConfiguration launchConfig ) {
    this.launchConfig = launchConfig;
  }

}
