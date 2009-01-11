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
import java.util.List;

import org.eclipse.ui.views.properties.IPropertySource;

import com.xerox.amazonws.ec2.KeyPairInfo;

import eu.geclipse.aws.ec2.EC2Keypair;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;

/**
 * An {@link IPropertySource} for {@link EC2Keypair} objects.
 * 
 * @author Moritz Post
 */
public class KeypairPropertySource extends AbstractPropertySource<Object> {

  /** The source for the properties. */
  private EC2Keypair ec2Keypair;

  /** The list of properties to display. */
  private List<IProperty<Object>> propertiesList;

  /**
   * Create a new properties view with the given source of information.
   * 
   * @param sourceObject the object to fetch the properties from
   */
  public KeypairPropertySource( final EC2Keypair sourceObject ) {
    super( sourceObject );
    this.ec2Keypair = sourceObject;
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

    KeyPairInfo keyPairInfo = this.ec2Keypair.getKeyPairInfo();
    propertyList.add( new SimpleProperty( Messages.getString( "KeypairPropertySource.property_name" ), keyPairInfo.getKeyName() ) ); //$NON-NLS-1$
    propertyList.add( new SimpleProperty( Messages.getString( "KeypairPropertySource.property_fingerprint" ), //$NON-NLS-1$
                                          keyPairInfo.getKeyFingerprint() ) );

    return propertyList;
  }
}
