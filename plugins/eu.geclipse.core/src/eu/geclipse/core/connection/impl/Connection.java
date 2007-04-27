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
package eu.geclipse.core.connection.impl;

import eu.geclipse.core.connection.AbstractConnection;
import eu.geclipse.core.connection.IConnectionDescription;

/**
 * Basic implementation of {@link AbstractConnection}
 * 
 * @author katis
 */
public class Connection extends AbstractConnection {

  /**
   * Creates connection from given {@link IConnectionDescription}
   * 
   * @param description description from which new connection should be created
   */
  public Connection( final IConnectionDescription description ) {
    super( description );
  }

  public void connect() {
    // TODO katis - is this method needed?
  }

  public void disconnect() {
    // TODO TODO katis - is this method needed?
  }
}
