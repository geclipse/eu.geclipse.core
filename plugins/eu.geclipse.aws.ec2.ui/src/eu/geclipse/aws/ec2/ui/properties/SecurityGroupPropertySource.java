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

import org.eclipse.ui.views.properties.IPropertySource;

import com.xerox.amazonws.ec2.GroupDescription;
import com.xerox.amazonws.ec2.GroupDescription.IpPermission;

import eu.geclipse.aws.ec2.EC2SecurityGroup;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;

/**
 * An {@link IPropertySource} for {@link EC2SecurityGroup} objects.
 * 
 * @author Moritz Post
 */
public class SecurityGroupPropertySource extends AbstractPropertySource<Object>
{

  /** The source for the properties. */
  private EC2SecurityGroup ec2securityGroup;

  /** The list of properties to display. */
  private List<IProperty<Object>> propertiesList;

  /**
   * Create a new properties view with the given source of information.
   * 
   * @param sourceObject the object to fetch the properties from
   */
  public SecurityGroupPropertySource( final EC2SecurityGroup sourceObject ) {
    super( sourceObject );
    this.ec2securityGroup = sourceObject;
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

    GroupDescription groupDescription = this.ec2securityGroup.getGroupDescription();
    propertyList.add( new SimpleProperty( Messages.getString( "SecurityGroupPropertySource.property_name" ), groupDescription.getName() ) ); //$NON-NLS-1$
    propertyList.add( new SimpleProperty( Messages.getString( "SecurityGroupPropertySource.property_description" ), //$NON-NLS-1$
                                          groupDescription.getDescription() ) );
    propertyList.add( new SimpleProperty( Messages.getString( "SecurityGroupPropertySource.property_owner" ), groupDescription.getOwner() ) ); //$NON-NLS-1$

    int i = 1;
    for( IpPermission ipPerm : groupDescription.getPermissions() ) {

      StringBuilder ipRanges = new StringBuilder();

      Iterator<String> iterator = ipPerm.getIpRanges().iterator();
      while( iterator.hasNext() ) {
        String range = iterator.next();
        ipRanges.append( range );

        if( iterator.hasNext() ) {
          ipRanges.append( ", " ); //$NON-NLS-1$
        }
      }

      List<String[]> uidGroupPairs = ipPerm.getUidGroupPairs();
      for( Iterator<String[]> iterator2 = uidGroupPairs.iterator(); iterator2.hasNext(); )
      {
        String[] uidGroupPair = iterator2.next();
        ipRanges.append( uidGroupPair[ 0 ] );
        ipRanges.append( ":" ); //$NON-NLS-1$
        ipRanges.append( uidGroupPair[ 1 ] );
        if( iterator2.hasNext() ) {
          ipRanges.append( ", " ); //$NON-NLS-1$
        }
      }

      StringBuilder strBuilder = new StringBuilder();
      strBuilder.append( ipPerm.getProtocol() );
      strBuilder.append( ": " ); //$NON-NLS-1$
      strBuilder.append( ipPerm.getFromPort() );
      strBuilder.append( " - " ); //$NON-NLS-1$
      strBuilder.append( ipPerm.getToPort() );
      strBuilder.append( ", " ); //$NON-NLS-1$
      strBuilder.append( ipRanges );
      String value = strBuilder.toString();

      propertyList.add( new SimpleProperty( Messages.getString( "SecurityGroupPropertySource.property_permissions" ), //$NON-NLS-1$
                                            String.valueOf( i ),
                                            value ) );
      i++;
    }

    return propertyList;
  }
}
