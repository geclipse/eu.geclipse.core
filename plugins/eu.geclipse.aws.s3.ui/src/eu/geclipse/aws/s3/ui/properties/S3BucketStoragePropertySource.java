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

package eu.geclipse.aws.s3.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.aws.s3.S3BucketStorage;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;

/**
 * This property source provides the readable details about a S3 Bucket.
 * 
 * @author Moritz Post
 */
public class S3BucketStoragePropertySource
  extends AbstractPropertySource<Object>
{

  /** The list to display. */
  private List<IProperty<Object>> propertiesList;

  /** The bucket we use as information source. */
  private S3BucketStorage s3BucketStorage;

  /**
   * Create a new {@link S3BucketStoragePropertySource}.
   * 
   * @param sourceObject the object from which to pull the information
   */
  public S3BucketStoragePropertySource( final S3BucketStorage sourceObject ) {
    super( sourceObject );
    this.s3BucketStorage = sourceObject;
  }

  /**
   * Returns a list of properties to by displayed on the eclipse properties
   * view.
   * 
   * @return the list to display
   */
  private List<IProperty<Object>> getProperties() {
    ArrayList<IProperty<Object>> propertyList = new ArrayList<IProperty<Object>>();

    propertyList.add( new SimpleProperty( Messages.getString("S3BucketStoragePropertySource.property_name"), //$NON-NLS-1$
                                          this.s3BucketStorage.getName() ) );
    propertyList.add( new SimpleProperty( Messages.getString("S3BucketStoragePropertySource.property_host_name"), //$NON-NLS-1$
                                          this.s3BucketStorage.getHostName() ) );
    propertyList.add( new SimpleProperty( Messages.getString("S3BucketStoragePropertySource.property_uri"), this.s3BucketStorage.getURI() //$NON-NLS-1$
      .toString() ) );
    return propertyList;
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

}
