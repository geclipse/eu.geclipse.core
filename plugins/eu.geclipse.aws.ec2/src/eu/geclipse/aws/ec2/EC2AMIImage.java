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

package eu.geclipse.aws.ec2;

import java.util.List;

import com.xerox.amazonws.ec2.ImageDescription;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridService;

/**
 * A wrapper class masking the {@link ImageDescription} object used to display
 * AMI images inside a {@link IGridService} interface.
 * 
 * @author Moritz Post
 */
public class EC2AMIImage extends AbstractEC2GridResource {

  /** The typica class to wrap. */
  private ImageDescription imageDescription;

  /**
   * Create a new {@link EC2AMIImage} class belonging to the passed
   * {@link AWSVirtualOrganization} and encapsulating data from the
   * {@link ImageDescription}.
   * 
   * @param parent the parent in the grid project
   * @param ec2Service the {@link IAWSService} to utilize
   * @param imageDescription the AMI data to provide
   */
  public EC2AMIImage( final IGridContainer parent,
                      final EC2Service ec2Service,
                      final ImageDescription imageDescription )
  {
    super( parent, ec2Service );
    this.imageDescription = imageDescription;
  }

  public String getName() {
    return this.imageDescription.getImageId() + " (" //$NON-NLS-1$
           + this.imageDescription.getImageLocation()
           + ")"; //$NON-NLS-1$
  }

  /**
   * Retrieves the underlying image id.
   * 
   * @return the AMI id
   */
  public String getImageId() {
    return this.imageDescription.getImageId();
  }

  /**
   * Getter for the {@link #imageDescription#getImageLocation()}.
   * 
   * @return the location of the image
   */
  public String getImageLocation() {
    return this.imageDescription.getImageLocation();
  }

  /**
   * Getter for the {@link ImageDescription#getImageOwnerId()}.
   * 
   * @return the aws id of the owner of the image
   */
  public String getImageOwnerId() {
    return this.imageDescription.getImageOwnerId();
  }

  /**
   * Getter for the {@link ImageDescription#getImageState()}.
   * 
   * @return the state of the current image
   */
  public String getImageState() {
    return this.imageDescription.getImageState();
  }

  /**
   * Getter for the {@link ImageDescription#getProductCodes()}.
   * 
   * @return the product code of the image
   */
  public List<String> getProductCodes() {
    return this.imageDescription.getProductCodes();
  }

}
