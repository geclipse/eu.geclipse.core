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

import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.aws.vo.AWSVoCreator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Utility Class to cover various repeating task in the setup process of the
 * Unit Tests.
 * 
 * @author Moritz Post
 */
public class AWSVoTestUtil {

  /** A default name for the Amazon AWS VO. */
  public static final String VO_NAME = "Amazon AWS VO"; //$NON-NLS-1$

  /** A dummy access Id for the Amazon EC2 services. */
  public static final String AWS_ACCESS_ID = "awsAccessId"; //$NON-NLS-1$

  /**
   * Fetches the {@link AWSVoCreator} from the GridModel and returns it. If not
   * {@link AWSVoCreator} could be found, <code>null</code> is returned.
   * 
   * @return an {@link AWSVoCreator} or <code>null</code> if the creator class
   *         could not be found
   */
  public static AWSVoCreator getAwsVoCreator() {
    List<IGridElementCreator> voCreators = GridModel.getVoCreators();

    AWSVoCreator awsVoCreator = null;
    for( IGridElementCreator gridElementCreator : voCreators ) {
      if( gridElementCreator instanceof AWSVoCreator ) {
        awsVoCreator = ( AWSVoCreator )gridElementCreator;
      }
    }
    if( awsVoCreator != null ) {
      awsVoCreator.setVoName( AWSVoTestUtil.VO_NAME );
      awsVoCreator.setAwsAccessId( AWSVoTestUtil.AWS_ACCESS_ID );
    }
    return awsVoCreator;
  }

  /**
   * Creates a new {@link AWSVirtualOrganization} with the property value
   * {@link #VO_NAME}}.
   * 
   * @return the new {@link AWSVirtualOrganization} with the default values
   * @throws ProblemException when the vo could not be created
   */
  public static AWSVirtualOrganization getAwsVo() throws ProblemException {
    AWSVoCreator awsVoCreator = AWSVoTestUtil.getAwsVoCreator();
    AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )awsVoCreator.create( null );
    awsVoCreator.setVoName( AWSVoTestUtil.VO_NAME );
    awsVoCreator.apply( awsVo );
    return awsVo;
  }

}
