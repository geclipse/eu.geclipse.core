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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.portforward;

import eu.geclipse.core.portforward.Forward;
import eu.geclipse.core.portforward.ForwardType;

class ForwardTableEntry extends Forward implements IForwardTableEntry {
  private boolean removeable;

  ForwardTableEntry( final ForwardType type,
                final int bindPort, final String hostname,
                final int port, final boolean removeable ) {
    super( type, bindPort, hostname, port );
    this.removeable = removeable;
  }

  public boolean isRemoveable() {
    return this.removeable;
  }
}
