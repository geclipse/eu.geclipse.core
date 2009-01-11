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

import com.xerox.amazonws.ec2.AddressInfo;

import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.core.model.IGridContainer;

/**
 * This class is a container to display the information of an
 * {@link AddressInfo}.
 * 
 * @author Moritz Post
 */
public class EC2ElasticIPAddress extends AbstractEC2GridResource {

  /** The source of information. */
  private AddressInfo addressInfo;

  /**
   * Creates a new instance of the {@link EC2ElasticIPAddress} from the data
   * given in the {@link AddressInfo} parameter.
   * 
   * @param parent the parent in the grid project
   * @param parentInGridModel the service object acting as the grid element
   *          parent
   * @param addressInfo the source of information
   */
  public EC2ElasticIPAddress( final IGridContainer parent,
                              final EC2Service parentInGridModel,
                              final AddressInfo addressInfo )
  {
    super( parent, parentInGridModel );
    this.addressInfo = addressInfo;
  }

  public String getName() {
    StringBuffer strBuf = new StringBuffer();
    strBuf.append( this.addressInfo.getPublicIp() );

    if( this.addressInfo.getInstanceId().trim().length() > 0 ) {
      strBuf.append( " - " ); //$NON-NLS-1$
      strBuf.append( this.addressInfo.getInstanceId() );
    }
    return strBuf.toString();
  }

  /**
   * A getter for the {@link #addressInfo} field.
   * 
   * @return the addressInfo
   */
  public AddressInfo getAddressInfo() {
    return this.addressInfo;
  }
}
