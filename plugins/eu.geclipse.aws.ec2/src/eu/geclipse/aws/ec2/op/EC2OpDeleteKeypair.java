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

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} removes a keypair from EC2.
 * 
 * @author Moritz Post
 */
public class EC2OpDeleteKeypair implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** The keypair group to remove. */
  private String keypair;

  /**
   * Creates a new {@link EC2OpDeleteKeypair} operation, using the IEC2 as the
   * interaction service, to delete the passed keypair.
   * 
   * @param ec2 the service to interact with
   * @param keypair the security group to delete
   */
  public EC2OpDeleteKeypair( final IEC2 ec2, final String keypair ) {
    this.keypair = keypair;
    this.ec2Service = ec2;
  }

  public Exception getException() {
    return this.exception;
  }

  public Object getResult() {
    return null;
  }

  public void run() {
    this.exception = null;
    try {
      this.ec2Service.deleteKeypair( this.keypair );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

}
