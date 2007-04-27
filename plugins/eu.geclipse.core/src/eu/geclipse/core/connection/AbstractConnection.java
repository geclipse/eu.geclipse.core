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
 *    Katarzyna Bylec - initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.connection;

/**
 * Abstract implementation of {@link IConnection}
 * 
 * @author katis
 */
public abstract class AbstractConnection implements IConnection {

  /**
   * The description that was used to create this connection.
   */
  private IConnectionDescription description;

  /**
   * Construct a new <code>AbstractConnection</code> from the specified
   * {@link IConnectionDescription}.
   * 
   * @param description {@link IConnectionDescription} to create this
   *          connection.
   */
  public AbstractConnection( final IConnectionDescription description ) {
    this.description = description;
  }

  public final IConnectionDescription getDescription() {
    return this.description;
  }
}
