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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui;

import java.util.List;

import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;

/**
 * Abstract implementation of the {@link IAuthTokenUIFactory} that provides methods
 * for finding extensions of the authentication ui factory mechanism.
 * 
 * @author stuempert-m
 */
public abstract class AbstractAuthTokenUIFactory implements IAuthTokenUIFactory {
  
  /**
   * Find an {@link IAuthTokenUIFactory} that is able to provide UI elements for the
   * specified authentication token.
   * 
   * @param token The token to provide UI elements for.
   * @return An authentication token manager that is capable to provide UI elements
   * for the specified token.
   */
  public static IAuthTokenUIFactory findFactory( final IAuthenticationToken token ) {
    IAuthenticationTokenDescription description = token.getDescription();
    return findFactory( description );
  }
  
  /**
   * Find an {@link IAuthTokenUIFactory} that is able to provide UI elements for
   * tokens described by the specified authentication token description.
   * 
   * @param description The token description that describes tokens to provide UI elements for.
   * @return An authentication token manager that is capable to provide UI elements
   * for tokens describes by the the specified token description.
   */
  public static IAuthTokenUIFactory findFactory( final IAuthenticationTokenDescription description ) {
    IAuthTokenUIFactory resultFactory = null;
    List< IAuthTokenUIFactory > factories = Extensions.getRegisteredAuthTokenUIFactories();
    for ( IAuthTokenUIFactory factory : factories ) {
      IAuthenticationTokenDescription desc = factory.getSupportedDescription();
      if ( desc.getClass().equals( description.getClass() ) ) {
        resultFactory = factory;
        break;
      }
    }
    return resultFactory;
  }
  
}
