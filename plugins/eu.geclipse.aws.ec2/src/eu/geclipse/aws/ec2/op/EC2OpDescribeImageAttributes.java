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

import com.xerox.amazonws.ec2.DescribeImageAttributeResult;
import com.xerox.amazonws.ec2.ImageAttribute.ImageAttributeType;

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} lists all attributes given by the
 * {@link ImageAttributeType} and associated with the passed image id.
 * 
 * @author Moritz Post
 */
public class EC2OpDescribeImageAttributes implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The image to fetch the attributes for. */
  private String imageId;

  /** The kind of attributes to fetch. */
  private ImageAttributeType type;

  /** The result of this operation. */
  private DescribeImageAttributeResult result;

  /** The {@link Exception} which might have been thrown. */
  private Exception exception;

  /**
   * Create a new {@link IOperation} with the image id as the targeted AMI.
   * 
   * @param ec2 the gateway to the EC2 infrastructure
   * @param imageId the image to fetch attributes for
   * @param type the kind of information to fetch
   */
  public EC2OpDescribeImageAttributes( final IEC2 ec2,
                                       final String imageId,
                                       final ImageAttributeType type )
  {
    this.ec2Service = ec2;
    this.imageId = imageId;
    this.type = type;
  }

  public Exception getException() {
    return this.exception;
  }

  public DescribeImageAttributeResult getResult() {
    return this.result;
  }

  public void run() {
    this.result = null;
    this.exception = null;
    try {
      this.result = this.ec2Service.describeImageAttributes( this.imageId,
                                                             this.type );
    } catch( Exception ex ) {
      this.exception = ex;
    }
  }

}
