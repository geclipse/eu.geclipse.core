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

import com.xerox.amazonws.ec2.KeyPairInfo;

import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * This class encapsulates an {@link KeyPairInfo} so that it can be displayed in
 * an {@link AWSVirtualOrganization} tree.
 * 
 * @author Moritz Post
 */
public class EC2Keypair extends AbstractEC2GridResource {

  /**
   * The {@link KeyPairInfo} that contains the information for this
   * {@link EC2Keypair}.
   */
  private KeyPairInfo keyPairInfo;

  /**
   * Create a new {@link EC2Keypair} with the passed {@link EC2Service} as the
   * parent and the {@link KeyPairInfo} as the information source for this
   * {@link IGridElement}.
   * 
   * @param parent the parent in the grid project
   * @param ec2Service the service object acting as the grid element parent
   * @param keyPairInfo the source of information to display in this grid
   *          element
   */
  public EC2Keypair( final IGridContainer parent,
                     final EC2Service ec2Service,
                     final KeyPairInfo keyPairInfo )
  {
    super( parent, ec2Service );
    this.keyPairInfo = keyPairInfo;
  }

  public String getName() {
    return this.keyPairInfo.getKeyName();
  }

  /**
   * A getter for the {@link #keyPairInfo} field.
   * 
   * @return the keyPairInfo
   */
  public KeyPairInfo getKeyPairInfo() {
    return this.keyPairInfo;
  }

}
