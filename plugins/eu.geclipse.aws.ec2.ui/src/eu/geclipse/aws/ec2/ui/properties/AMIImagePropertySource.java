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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;

/**
 * This properties source provides all the available information about an Amazon
 * Machine Image. The source of information is The {@link EC2AMIImage}.
 * 
 * @author Moritz Post
 */
public class AMIImagePropertySource extends AbstractPropertySource<Object> {

  /** The list of properties to display. */
  private List<IProperty<Object>> propertiesList;

  /** The source of information to display. */
  private EC2AMIImage ec2AmiImage;

  /**
   * Create a new properties view with the given source of information.
   * 
   * @param sourceObject the {@link EC2AMIImage} to fetch the properties from
   */
  public AMIImagePropertySource( final EC2AMIImage sourceObject ) {
    super( sourceObject );
    this.ec2AmiImage = sourceObject;
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

    propertyList.add( new SimpleProperty( Messages.getString( "AMIImagePropertySource.property_image_id" ), //$NON-NLS-1$
                                          this.ec2AmiImage.getImageId() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "AMIImagePropertySource.property_location" ), //$NON-NLS-1$
                                          this.ec2AmiImage.getImageLocation() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "AMIImagePropertySource.property_owner" ), //$NON-NLS-1$
                                          this.ec2AmiImage.getImageOwnerId() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "AMIImagePropertySource.property_state" ), //$NON-NLS-1$
                                          this.ec2AmiImage.getImageState() ) );
    StringBuilder strBuilder = new StringBuilder();
    List<String> productCodes = this.ec2AmiImage.getProductCodes();

    for( Iterator<String> iterator = productCodes.iterator(); iterator.hasNext(); )
    {
      String productCode = iterator.next();
      strBuilder.append( productCode );
      if( iterator.hasNext() ) {
        strBuilder.append( ", " ); //$NON-NLS-1$
      }
    }

    propertyList.add( new SimpleProperty( Messages.getString( "AMIImagePropertySource.property_product_code" ), strBuilder.toString() ) ); //$NON-NLS-1$
    return propertyList;
  }
}
