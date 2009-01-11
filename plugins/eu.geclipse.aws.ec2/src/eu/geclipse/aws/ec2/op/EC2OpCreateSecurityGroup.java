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
 * This {@link IOperation} creates a new security group.
 * 
 * @author Moritz Post
 */
public class EC2OpCreateSecurityGroup implements IOperation {

  /** The name of the security group to create. */
  private String securityGroupName;

  /** The description of the security group to create. */
  private String securityGroupDescription;

  /** The {@link Exception} which might have been thrown. */
  private Exception exception;

  /**
   * The {@link IEC2} instance to use for interfacing with the ec2
   * infrastructure.
   */
  private IEC2 ec2;

  /**
   * Creates a new instance of the {@link EC2OpCreateSecurityGroup} with the
   * given security group details and using the provided {@link IEC2} to
   * interface with the EC2 infrastructure.
   * 
   * @param ec2
   * @param securityGroupName
   * @param securityGroupDescription
   */
  public EC2OpCreateSecurityGroup( final IEC2 ec2,
                                   final String securityGroupName,
                                   final String securityGroupDescription )
  {
    this.ec2 = ec2;
    this.securityGroupName = securityGroupName;
    this.securityGroupDescription = securityGroupDescription;
  }

  public void run() {
    this.exception = null;
    try {
      this.ec2.createSecurityGroup( this.securityGroupName,
                                    this.securityGroupDescription );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

  public Exception getException() {
    return this.exception;
  }

  /**
   * This operation does not return a result.
   */
  public Object getResult() {
    // no return value for this operation
    return null;
  }

}
