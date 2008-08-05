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

import org.eclipse.core.runtime.IProgressMonitor;

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
   * @param monitor used to monitor progress, can be <code>null</code>.
   * @return an array of {@link IACLEntry}s.
   * @throws ProblemException if an error occurs while fetching the entries.
   */
  public IACLEntry[] getEntries( final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Registers an additional entry in this ACL. If {@link IACL#canSaveWholeACL()}
   * returns <code>false</code>, and the IProtectable object is remote,
   * the changes are immediately performed online.
   * 
   * @param entry the ACL entry to set.
   * @param monitor used to monitor progress, can be <code>null</code>.
   * @throws ProblemException if the provided entry is not compatible
   *         with the specific ACL implementation, or an error occurs
   *         while adding the entry.
   */
  public void addEntry( final IACLEntry entry, final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Removes an entry from this ACL. If {@link IACL#canSaveWholeACL()}
   * returns <code>false</code>, and the IProtectable object is remote,
   * the changes are immediately performed online.
   * 
   * @param entry the entry to delete.
   * @param monitor used to monitor progress, can be <code>null</code>.
   * @throws ProblemException if the entry can not be deleted, for
   *         instance because such entry was not present in this ACL,
   *         or an error occurs while deleting the entry.
   */
  public void deleteEntry( final IACLEntry entry, final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * Returns if the implementation is able to save changes to an ACL as
   * a whole or single {@link IACLEntry}'s must be stored independently.
   * 
   * @return true if the implementation allows to store the whole ACL,
   *         false otherwise.
   */
  public boolean canSaveWholeACL();
  
  /**
   * Saves the changes made to this ACL, if {@link IACL#canSaveWholeACL()}
   * returns <code>true</code>. Does nothing otherwise, as each {@link IACLEntry}
   * must be saved separately. For remote objects this is an online operation.
   * 
   * @param monitor used to monitor progress, can be <code>null</code>.
   * @throws ProblemException if an error occurs while saving the entry.
   */
  public void save( final IProgressMonitor monitor )
    throws ProblemException;
  
}
