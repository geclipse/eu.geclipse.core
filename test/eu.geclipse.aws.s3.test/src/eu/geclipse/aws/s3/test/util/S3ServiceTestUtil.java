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

package eu.geclipse.aws.s3.test.util;

import java.util.List;

import eu.geclipse.aws.s3.service.S3AWSService;
import eu.geclipse.aws.s3.service.S3AWSServiceCreator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElementCreator;

/**
 * A utility class able to provide an {@link S3AWSServiceCreator} and an
 * {@link S3AWSService}.
 * 
 * @author Moritz Post
 */
public class S3ServiceTestUtil {

  /** A name used for the {@link S3AWSService}. */
  public static final String S3_SERVICE_NAME = "S3 Service"; //$NON-NLS-1$

  /** A default url for the Amazon AWS S3 service. */
  public static final String S3_URL = "https://s3.amazonaws.com/"; //$NON-NLS-1$

  /**
   * Fetches the {@link S3AWSServiceCreator} from the GridModel and returns it.
   * If no {@link S3AWSServiceCreator} could be found, <code>null</code> is
   * returned.
   * 
   * @return an {@link S3AWSServiceCreator} or <code>null</code> if the
   *         creator class could not be found
   */
  public static S3AWSServiceCreator getS3AWSServiceCreator() {
    List<IGridElementCreator> voCreators = GridModel.getElementCreators();

    S3AWSServiceCreator S3AWSServiceCreator = null;
    for( IGridElementCreator gridElementCreator : voCreators ) {
      if( gridElementCreator instanceof S3AWSServiceCreator ) {
        S3AWSServiceCreator = ( S3AWSServiceCreator )gridElementCreator;
        S3AWSServiceCreator.setServiceName( S3ServiceTestUtil.S3_SERVICE_NAME );
        S3AWSServiceCreator.setServiceURL( S3ServiceTestUtil.S3_URL );
      }
    }
    return S3AWSServiceCreator;
  }

  /**
   * Creates a new {@link S3AWSService}.
   * 
   * @return the new {@link S3AWSServiceCreator}
   * @throws GridModelException when the service could not be created
   */
  public static S3AWSService getS3AWSService() throws GridModelException {
    S3AWSServiceCreator S3AWSServiceCreator = S3ServiceTestUtil.getS3AWSServiceCreator();
    return ( S3AWSService )S3AWSServiceCreator.create( AWSVoTestUtil.getAwsVo() );
  }
}
