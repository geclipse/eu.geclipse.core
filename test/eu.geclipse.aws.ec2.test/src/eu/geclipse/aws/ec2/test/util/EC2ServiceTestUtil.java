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

package eu.geclipse.aws.ec2.test.util;

import java.util.List;

import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.aws.ec2.service.EC2ServiceCreator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * A utility class able to provide an {@link EC2ServiceCreator} and an
 * {@link EC2Service}.
 * 
 * @author Moritz Post
 */
public class EC2ServiceTestUtil {

  /** A name used for the {@link EC2Service}. */
  public static final String EC2_SERVICE_NAME = "EC2 Service"; //$NON-NLS-1$

  /** A default url for the Amazon AWS EC2 service. */
  public static final String EC2_URL = "https://ec2.amazonaws.com/"; //$NON-NLS-1$

  /**
   * Fetches the {@link EC2ServiceCreator} from the GridModel and returns it. If
   * no {@link EC2ServiceCreator} could be found, <code>null</code> is returned.
   * 
   * @return an {@link EC2ServiceCreator} or <code>null</code> if the creator
   *         class could not be found
   */
  public static EC2ServiceCreator getEC2ServiceCreator() {
    List<IGridElementCreator> voCreators = GridModel.getCreatorRegistry()
      .getCreators();

    EC2ServiceCreator ec2ServiceCreator = null;
    for( IGridElementCreator gridElementCreator : voCreators ) {
      if( gridElementCreator instanceof EC2ServiceCreator ) {
        ec2ServiceCreator = ( EC2ServiceCreator )gridElementCreator;
        ec2ServiceCreator.setServiceName( EC2ServiceTestUtil.EC2_SERVICE_NAME );
        ec2ServiceCreator.setServiceURL( EC2ServiceTestUtil.EC2_URL );
      }
    }
    return ec2ServiceCreator;
  }

  /**
   * Creates a new {@link EC2Service}.
   * 
   * @return the new {@link EC2ServiceCreator}
   * @throws ProblemException when the service could not be created
   */
  public static EC2Service getEc2Service() throws ProblemException {
    EC2ServiceCreator ec2ServiceCreator = getEC2ServiceCreator();
    return ( EC2Service )ec2ServiceCreator.create( AWSVoTestUtil.getAwsVo() );
  }
}
