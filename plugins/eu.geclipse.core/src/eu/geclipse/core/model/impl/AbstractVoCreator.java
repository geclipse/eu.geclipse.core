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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import eu.geclipse.core.config.IConfiguration;
import eu.geclipse.core.model.IConfigurableElementCreator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Abstract implementation for VO creators adding the possibility to create VOs
 * from an {@link IConfiguration}.
 */
public abstract class AbstractVoCreator
    extends AbstractGridElementCreator
    implements IConfigurableElementCreator {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IConfigurableElementCreator#create(eu.geclipse.core.model.IGridContainer, eu.geclipse.core.config.IConfiguration)
   */
  public IGridElement create( final IGridContainer parent,
                              final IConfiguration configuration )
      throws ProblemException {
    setConfiguration( configuration );
    return create( parent );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IConfigurableElementCreator#getConfiguration()
   */
  public IConfiguration getConfiguration() {
    Object source = getSource();
    return source instanceof IConfiguration
      ? ( IConfiguration ) source
      : null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IConfigurableElementCreator#setConfiguration(eu.geclipse.core.config.IConfiguration)
   */
  public void setConfiguration( final IConfiguration configuration ) {
    setSource( configuration );
  }

}
