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
 * Abstraction of class that manages different connection types (contributet by
 * developers)
 * 
 * @author katis
 */
public abstract class AbstractConnectionTypeManager {

  /**
   * This method should be implementeted by contributors who wont't provide
   * wizard pages for their connection type. Those who will provide wizard pages
   * should return null here
   * 
   * @param host name of the host
   * @return instance of AbstractConnectionTypeManager dealing with transport
   *         layer, an implementation of a connection to a host using some kind
   *         of protocol
   */
  public abstract AbstractConnectionTypeManager getAbstractConnectionTypeManager( final String host );
}
