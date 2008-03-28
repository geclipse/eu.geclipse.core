/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.pbs;

import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IBatchServiceDescription;
import eu.geclipse.batch.internal.Messages;
import eu.geclipse.core.reporting.ProblemException;

/**
 * A batch service description that is dedicated to pbs. 
 * 
 * @author harald
 */
public class PBSBatchServiceDescription implements IBatchServiceDescription {

  /**
   * Create a {@link PBSBatchService} from this description.
   *
   * @param name batch service name, i.e. the configuration file that were 
   *             used to instantiate this service
   * @return A pbs batch service that can safely be casted to {@link PBSBatchService}.
   */
  public IBatchService createService( final String name ) throws ProblemException {
    return new PBSBatchService( this, name );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.batch.IBatchServiceDescription#getServiceTypeName()
   */
  public String getServiceTypeName() {
    return Messages.getString( "PBSBatchServiceDescription.serviceTypeName" ); //$NON-NLS-1$
  }

  public boolean supportsService( final String service ) {
    return ( 0 == Messages.getString( "PBSBatchServiceDescription.serviceTypeName" ).compareTo( service ) ) ; //$NON-NLS-1$
  }
}
