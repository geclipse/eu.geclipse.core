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

package eu.geclipse.aws.ec2.ui.properties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;

/**
 * This class acts as a source for a properties view displaying information
 * about an {@link EC2Instance}.
 * 
 * @author Moritz Post
 */
public class EC2InstancePropertySource extends AbstractPropertySource<Object> {

  /** The list to display. */
  private List<IProperty<Object>> propertiesList;

  /** The {@link EC2Instance} to obtain the properties from. */
  private EC2Instance ec2Instance;

  /**
   * Create a new {@link EC2InstancePropertySource} with the given
   * {@link EC2Instance} as the source of information.
   * 
   * @param sourceObject the source object to obtain properties from
   */
  public EC2InstancePropertySource( final EC2Instance sourceObject ) {
    super( sourceObject );
    this.ec2Instance = sourceObject;
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return this.getClass();
  }

  @Override
  protected List<IProperty<Object>> getStaticProperties() {
    if( this.propertiesList == null ) {
      this.propertiesList = getProperties();
    }

    return this.propertiesList;
  }

  /**
   * Returns a list of properties to by displayed on the eclipse properties
   * view.
   * 
   * @return the list to display
   */
  private List<IProperty<Object>> getProperties() {

    ArrayList<IProperty<Object>> propertyList = new ArrayList<IProperty<Object>>();

    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_instance_id" ), //$NON-NLS-1$
                                          this.ec2Instance.getInstanceId() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_image_id" ), //$NON-NLS-1$
                                          this.ec2Instance.getImageId() ) );

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat( Messages.getString( "EC2InstancePropertySource.property_launch_time_format" ) ); //$NON-NLS-1$
    String formatedDate = simpleDateFormat.format( this.ec2Instance.getLaunchTime()
      .getTime() );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_launch_time" ), formatedDate ) ); //$NON-NLS-1$

    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_dns_name" ), //$NON-NLS-1$
                                          this.ec2Instance.getDnsName() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_private_dns_name" ), //$NON-NLS-1$
                                          this.ec2Instance.getPrivateDnsName() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_availability_zone" ), //$NON-NLS-1$
                                          this.ec2Instance.getAvailabilityZone() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_key_name" ), //$NON-NLS-1$
                                          this.ec2Instance.getKeyName() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_instance_type" ), //$NON-NLS-1$
                                          this.ec2Instance.getInstanceType() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_kernel_id" ), //$NON-NLS-1$
                                          this.ec2Instance.getKernelId() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_ramdisk_id" ), //$NON-NLS-1$
                                          this.ec2Instance.getRamdiskId() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_state" ), //$NON-NLS-1$
                                          this.ec2Instance.getState() + " (" //$NON-NLS-1$
                                              + this.ec2Instance.getStateCode()
                                              + ")" ) ); //$NON-NLS-1$
    propertyList.add( new SimpleProperty( Messages.getString( "EC2InstancePropertySource.property_reason" ), //$NON-NLS-1$
                                          this.ec2Instance.getReason() ) );

    return propertyList;
  }
}
