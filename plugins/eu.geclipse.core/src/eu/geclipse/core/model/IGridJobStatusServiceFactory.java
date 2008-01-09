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




/**
 * This class is the factory for GridJobStatus services. The factory is responsible
 * for creation of GridJobStatus service for the given job
 *
 */
public interface IGridJobStatusServiceFactory {


/**
 * @param job the job, for which status service should be returned
 * @return service, which can ba asked about current job status
 */
public IGridJobStatusService getGridJobStatusService( final IGridJob job );
}
