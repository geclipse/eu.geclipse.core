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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.accesscontrol;

import eu.geclipse.core.reporting.ProblemException;


/**
 * A generic Access Control List (ACL) for any kind of resource
 * or object. An ACL consists of a list of entries, each defining
 * an access rule.
 * 
 * @author agarcia
 */
public interface IACL {
  
  /**
   * Returns the list of access control entries contained in this ACL.
   * 
   * @return an array of {@link IACLEntry}s
   */
  public IACLEntry[] getEntries();
  
  /**
   * Registers an additional entry in this ACL.
   * 
   * @param entry the ACL entry to set
   * @throws ProblemException if the provided entry is not compatible
   *         with the specific ACL implementation
   */
  public void addEntry( final IACLEntry entry ) throws ProblemException;
  
  /**
   * Removes an entry from this ACL.
   * 
   * @param entry the entry to delete
   * @throws ProblemException if the entry could not be deleted, for
   *         instance because such entry was not present in this ACL
   */
  public void deleteEntry( final IACLEntry entry ) throws ProblemException;
  
}
