/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.model.tests.access;

import eu.geclipse.servicejob.model.AbstractServiceJob;

/**
 * Abstract implementation of base class for all tests that test some resources
 * by contacting them directly (e.g. through SSL connection) or accessing test
 * results from remote services (portals, databases, etc.).
 */
public abstract class GridAccessServiceJob extends AbstractServiceJob {

  public boolean isLocal() {
    return true;
  }

  
  
}
