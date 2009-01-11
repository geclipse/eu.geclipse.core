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

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * This {@link IGridElement} is a wrapper of an {@link GroupDescription} object
 * to represent its content inside the VO subtree.
 * 
 * @author Moritz Post
 */
public class EC2SecurityGroup extends AbstractEC2GridResource {

  /** The {@link GroupDescription} object backing up this grid element. */
  private GroupDescription groupDescription;

  /**
   * Create a new {@link EC2SecurityGroup} class belonging to the passed
   * {@link AWSVirtualOrganization} and encapsulating data from the
   * {@link GroupDescription}.
   * 
   * @param parent the parent in the grid project
   * @param ec2Service the {@link IAWSService} to utilize
   * @param groupDescription the security group to provide data
   */
  public EC2SecurityGroup( final IGridContainer parent,
                           final EC2Service ec2Service,
                           final GroupDescription groupDescription )
  {
    super( parent, ec2Service );
    this.groupDescription = groupDescription;
  }

  public String getName() {
    return this.groupDescription.getName();
  }

  /**
   * Getter for the {@link #groupDescription} field.
   * 
   * @return the groupDescription
   */
  public GroupDescription getGroupDescription() {
    return this.groupDescription;
  }

  /**
   * Setter for the {@link #groupDescription} field.
   * 
   * @param groupDescription the groupDescription to set
   */
  public void setGroupDescription( final GroupDescription groupDescription ) {
    this.groupDescription = groupDescription;
  }

}
