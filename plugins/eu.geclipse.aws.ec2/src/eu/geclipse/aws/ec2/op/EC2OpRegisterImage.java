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
 * This {@link IOperation} registers an AMI image by providing a path to an
 * image manifest, e.g. <code>bucketName/image.manifest.xml</code>.
 * 
 * @author Moritz Post
 */
public class EC2OpRegisterImage implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private IEC2 ec2Service;

  /** Any exception which came up during the inquiry. */
  private Exception exception;

  /** The AMI ID of the newly registered image. */
  private String result;

  /**
   * The bucket path to the AMI manifest. E.g.:
   * <code>bucketName/image.manifest.xml</code>
   */
  private String bucketPath;

  /**
   * Creates a new {@link IOperation} with ec2 acting as the gateway and the
   * bucketPath as the path to the AMI to register.
   * 
   * @param ec2 the gateway to EC2
   * @param bucketPath the path to the AMI
   */
  public EC2OpRegisterImage( final IEC2 ec2, final String bucketPath ) {
    this.ec2Service = ec2;
    this.bucketPath = bucketPath;
  }

  public Exception getException() {
    return this.exception;
  }

  public String getResult() {
    return this.result;
  }

  public void run() {
    this.exception = null;
    try {
      this.result = this.ec2Service.registerImage( this.bucketPath );
    } catch( Exception ex ) {
      this.exception = ex;
    }

  }

}
