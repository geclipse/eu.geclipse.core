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

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.EC2ElasticIPAddress;
import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2Keypair;
import eu.geclipse.aws.ec2.EC2SecurityGroup;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IPropertiesFactory;

/**
 * This properties factory provides all the {@link IPropertySource}s used
 * throughout the EC2 infrastructure.
 * 
 * @author Moritz Post
 */
public class EC2PropertiesFactory implements IPropertiesFactory {

  /** The list of {@link IPropertySource}s this factory supports. */
  private ArrayList<AbstractPropertySource<?>> sourcesList;

  public List<AbstractPropertySource<?>> getPropertySources( final Object sourceObject )
  {
    if( this.sourcesList == null ) {
      this.sourcesList = new ArrayList<AbstractPropertySource<?>>();

      if( sourceObject instanceof EC2AMIImage ) {
        this.sourcesList.add( new AMIImagePropertySource( ( EC2AMIImage )sourceObject ) );
      }

      if( sourceObject instanceof EC2Instance ) {
        this.sourcesList.add( new EC2InstancePropertySource( ( EC2Instance )sourceObject ) );
      }
      if( sourceObject instanceof EC2Keypair ) {
        this.sourcesList.add( new KeypairPropertySource( ( EC2Keypair )sourceObject ) );
      }
      if( sourceObject instanceof EC2SecurityGroup ) {
        this.sourcesList.add( new SecurityGroupPropertySource( ( EC2SecurityGroup )sourceObject ) );
      }
      if( sourceObject instanceof EC2ElasticIPAddress ) {
        this.sourcesList.add( new ElasticIPPropertySource( ( EC2ElasticIPAddress )sourceObject ) );
      }
    }
    return this.sourcesList;

  }
}
