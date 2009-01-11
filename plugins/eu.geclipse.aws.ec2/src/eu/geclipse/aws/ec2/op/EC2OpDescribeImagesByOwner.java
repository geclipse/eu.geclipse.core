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

import java.util.Arrays;
import java.util.List;

import eu.geclipse.aws.ec2.EC2;
import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} implementation uses the {@link EC2} to send a query
 * to the Amazon Webservice. It fetches all amazon machine images owned by the
 * current user.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeImagesByOwner extends AbstractEC2OpDescribeImages {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The list of owners whos AMIs to fetch. */
  private List<String> ownerList;

  /**
   * Creates a new {@link EC2OpDescribeImagesByOwner} with the given owners as
   * parameter.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param ownerArray the list of people whos AMIs to fetch
   */
  public EC2OpDescribeImagesByOwner( final IEC2 ec2Service,
                                     final String[] ownerArray )
  {
    this.ec2Service = ec2Service;
    this.ownerList = Arrays.asList( ownerArray );
  }

  /**
   * Creates a new {@link EC2OpDescribeImagesByOwner} with the given owners as
   * parameter.
   * 
   * @param ec2Service the {@link IEC2} to obtain data from
   * @param ownerList the list of people whos AMIs to fetch
   */
  public EC2OpDescribeImagesByOwner( final IEC2 ec2Service,
                                     final List<String> ownerList )
  {
    this.ec2Service = ec2Service;
    this.ownerList = ownerList;
  }

  @Override
  public void run() {
    setResult( null );
    setException( null );
    try {
      setResult( this.ec2Service.describeImagesByOwner( this.ownerList ) );
    } catch( Exception ex ) {
      setException( ex );
    }

  }

}
