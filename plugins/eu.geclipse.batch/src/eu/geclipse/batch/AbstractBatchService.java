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
package eu.geclipse.batch;

import eu.geclipse.batch.internal.SSHConnection;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Common methods for interacting with a batch service. 
 * @author harald
 */
public abstract class AbstractBatchService implements IBatchService {
  /**
   * The holder of the ssh-connection to the PBS server.
   */
  protected SSHConnection connection;

  /**
   * The service description that was used to initialize this service.
   */
  private IBatchServiceDescription description;

  /**
   * The batch service name, i.e. the configuration file that were 
   * used to instantiate this service
   */
  private String name;
  
  /**
   * Construct a new <code>AbstractBatchService</code> from the
   * specified {@link IBatchServiceDescription}.
   * 
   * @param description The {@link IBatchServiceDescription} from which
   *                    this service should be created.
   * @param name batch service name, i.e. the configuration file that were 
   *             used to instantiate this service
   */
  public AbstractBatchService( final IBatchServiceDescription description, final String name ) {
    this.connection = new SSHConnection();
    this.description = description;
    this.name = name;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.batch.IBatchService#getDescription()
   */
  public IBatchServiceDescription getDescription() {
    return this.description;
  }

  /**
   * Get the batch service name, i.e. the configuration file that were used to instantiate this service.
   * 
   * @return The batch service name.
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Establishes a ssh-connection to the server running the PBS service.
   *
   * @param sshConnectionInfo Holding the information needed to establish a ssh
   * connection with the PBS server.
   * @return Returns <code>true</code> if the connection is established,
   *  <code>false</code> otherwise.
   * @throws ProblemException If the ssh connection cannot be established
   */
  public synchronized boolean connectToServer( final ISSHConnectionInfo sshConnectionInfo )  throws ProblemException {
    this.connection.createSession( sshConnectionInfo, null );
    return this.connection.isSessionActive();
  }
 
  /**
   * Tears down an already established ssh-connection to the server.
   */
  public synchronized void disconnectFromServer( ) {
    this.connection.endSession();
  }
}
