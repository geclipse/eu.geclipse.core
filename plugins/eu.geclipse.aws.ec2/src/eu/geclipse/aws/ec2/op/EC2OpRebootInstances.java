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

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} is used to reboot a running EC2 instance.
 * 
 * @author Moritz Post
 */
public class EC2OpRebootInstances implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** A list of instances to reboot. */
  private List<String> instances;

  /**
   * Create a new {@link EC2OpRebootInstances} operation with the EC2 acting as
   * the ec2 gateways and the list of instances to reboot.
   * 
   * @param ec2Service the gateway to EC2
   * @param instances the instances to reboot
   */
  public EC2OpRebootInstances( final IEC2 ec2Service,
                               final List<String> instances )
  {
    this.ec2Service = ec2Service;
    this.instances = instances;
  }

  public Exception getException() {
    return this.exception;
  }

  public Object getResult() {
    // the operation does not produce a result
    return null;
  }

  public void run() {
    this.exception = null;
    try {
      this.ec2Service.rebootInstances( this.instances );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

}
