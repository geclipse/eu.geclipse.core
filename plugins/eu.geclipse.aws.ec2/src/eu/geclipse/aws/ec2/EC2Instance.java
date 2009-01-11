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

import java.util.Calendar;

import com.xerox.amazonws.ec2.ReservationDescription.Instance;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;

/**
 * An {@link IGridElement} implementation representing an EC2 Instance and
 * wrapping {@link Instance}.
 * 
 * @author Moritz Post
 */
public class EC2Instance extends AbstractEC2GridResource
  implements IGridComputing
{

  /** The instance to wrap. */
  private Instance instance;

  /**
   * Create a new {@link EC2Instance} with the given vo as the parent and
   * wrapping the instance provided.
   * 
   * @param parent the parent in the grid project
   * @param ec2Service the {@link IAWSService} which this grid element utilizes
   * @param instance the actual {@link Instance} to wrap
   */
  public EC2Instance( final IGridContainer parent,
                      final EC2Service ec2Service,
                      final Instance instance )
  {
    super( parent, ec2Service );
    this.instance = instance;
  }

  @Override
  public String getHostName() {
    return this.instance.getDnsName();
  }

  public String getName() {
    StringBuilder strBuilder = new StringBuilder( this.instance.getInstanceId() );
    strBuilder.append( " (" ); //$NON-NLS-1$
    strBuilder.append( this.instance.getImageId() );
    strBuilder.append( ") - " ); //$NON-NLS-1$
    strBuilder.append( this.instance.getState() );
    return strBuilder.toString();
  }

  /**
   * Getter for the instance id of the embedded {@link Instance} object.
   * 
   * @return the instance id
   */
  public String getInstanceId() {
    return this.instance.getInstanceId();
  }

  /**
   * Getter for the {@link #instance#getAvailabilityZone()}.
   * 
   * @return the availability zone of this instance
   */
  public String getAvailabilityZone() {
    return this.instance.getAvailabilityZone();
  }

  /**
   * Getter for the {@link #instance#getDnsName()}.
   * 
   * @return the public dns name of the instance
   */
  public String getDnsName() {
    return this.instance.getDnsName();
  }

  /**
   * Getter for the {@link #instance#getImageId()}.
   * 
   * @return the id of image this instance is based on
   */
  public String getImageId() {
    return this.instance.getImageId();
  }

  /**
   * Getter for the {@link #instance#getInstanceType()()}. This describes the
   * hardware size of the vm.
   * 
   * @return the type of the instance
   */
  public String getInstanceType() {
    return this.instance.getInstanceType().getTypeId();
  }

  /**
   * Getter for the {@link #instance#getKernelId()}.
   * 
   * @return the if of the kernel used
   */
  public String getKernelId() {
    return this.instance.getKernelId();
  }

  /**
   * Getter for the {@link #instance#getKeyName()}.
   * 
   * @return the ssh key to use
   */
  public String getKeyName() {
    return this.instance.getKeyName();
  }

  /**
   * Getter for the {@link #instance#getLaunchTime()}.
   * 
   * @return the launch time
   */
  public Calendar getLaunchTime() {
    return this.instance.getLaunchTime();
  }

  /**
   * Getter for the {@link #instance#getPrivateDnsName()}
   * 
   * @return the private dns name
   */
  public String getPrivateDnsName() {
    return this.instance.getPrivateDnsName();
  }

  /**
   * Getter for the {@link #instance#getRamdiskId()}
   * 
   * @return the ram disk id
   */
  public String getRamdiskId() {
    return this.instance.getRamdiskId();
  }

  /**
   * Getter for the {@link #instance#getReason()}
   * 
   * @return the reason
   */
  public String getReason() {
    return this.instance.getReason();
  }

  /**
   * Getter for the {@link #instance#getState()}
   * 
   * @return the state of the vm in {@link String} form.
   */
  public String getState() {
    return this.instance.getState();
  }

  /**
   * Getter for the {@link #instance#getStateCode()}.
   * 
   * @return the state of the instance in numeric form
   */
  public int getStateCode() {
    return this.instance.getStateCode();
  }

}
