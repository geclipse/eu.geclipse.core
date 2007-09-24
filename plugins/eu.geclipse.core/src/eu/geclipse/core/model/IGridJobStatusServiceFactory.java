/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Pawel Wolniewicz
 *****************************************************************************/

package eu.geclipse.core.model;

import java.net.URI;



/**
 * This class is the factory for GridJobStatus services. The factory is responsible
 * for creation of GridJobStatus service for the given IGridJobID or location URI
 *
 */
public interface IGridJobStatusServiceFactory {

/**
 * Creates GridJobStatusService that can be used to ask about the specific jobId
 * @param id - The IGridJobID for which service shuold be creater
 * @return GridJobStatusService
 */
public IGridJobStatusService getGridJobStatusService(IGridJobID id);
  
/**
 * Creates GridJobStatusService from remote service location URI.
 * @param uri that represents the remote service
 * @return GridJobStatusService
 */
public IGridJobStatusService getGridJobStatusService(URI uri);
}
