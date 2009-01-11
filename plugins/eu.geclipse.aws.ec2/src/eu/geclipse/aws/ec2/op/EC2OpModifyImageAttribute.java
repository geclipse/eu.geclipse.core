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

import com.xerox.amazonws.ec2.ImageListAttribute;
import com.xerox.amazonws.ec2.Jec2.ImageListAttributeOperationType;

import eu.geclipse.aws.ec2.IEC2;

/**
 * This {@link IOperation} modifies the attributes of an image. This means to
 * grant or revoke access permissions to foreign users.
 * 
 * @author Moritz Post
 */
public class EC2OpModifyImageAttribute implements IOperation {

  /** The {@link IEC2} to obtain data from. */
  private final IEC2 ec2Service;

  /** The exception containing any problems during the operation. */
  private Exception exception;

  /** The id of the image to modify its attributes. */
  private String imageId;

  /** The attributes to modify on the image. */
  private ImageListAttribute attribute;

  /**
   * What type of operation should be performed on the image attributes.
   * Currently <code>add</code> and <code>remove</code> are supported.
   */
  private ImageListAttributeOperationType operationType;

  /**
   * Creates a new {@link EC2OpModifyImageAttribute} object using the provided
   * EC2 as its interfacing instance.
   * 
   * @param ec2 the gateway to the EC2 service
   * @param imageId the id of the image to modify
   * @param attribute the attribute to set
   * @param operationType the operation to perform
   */
  public EC2OpModifyImageAttribute( final IEC2 ec2,
                                    final String imageId,
                                    final ImageListAttribute attribute,
                                    final ImageListAttributeOperationType operationType )
  {
    this.ec2Service = ec2;
    this.imageId = imageId;
    this.attribute = attribute;
    this.operationType = operationType;
  }

  public Exception getException() {
    return this.exception;
  }

  /**
   * This {@link IOperation} does not return a result.
   */
  public Object getResult() {
    return null;
  }

  public void run() {
    this.exception = null;
    try {
      this.ec2Service.modifyImageAttribute( this.imageId,
                                            this.attribute,
                                            this.operationType );
    } catch( Exception ex ) {
      this.exception = ex;
    }

  }

}
