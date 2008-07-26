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

package eu.geclipse.core.model;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.accesscontrol.IACL;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Grid elements that implement this interface have access permissions
 * which can be managed via the associated Access Control List (ACL).
 * 
 * @author agarcia
 */
public interface IProtectable {
  
  /**
   * Fetches the access control list attached to this grid object
   * (file, service, etc.). For remote objects this is an online operation.
   * 
   * @param monitor a progress monitor, or null if progress reporting is not desired.
   * @return an {@link IACL} object.
   * @throws ProblemException if an error occurs while querying the ACL.
   */
  public IACL fetchACL( IProgressMonitor monitor ) throws ProblemException;
  
  /**
   * Writes the access control list associated with this grid object.
   * Only when this method is called changes to the object's access
   * permissions are really made effective.
   * 
   * @param acl the {@link IACL} to set on this element.
   * @param monitor a progress monitor, or null if progress reporting is not desired.
   * @throws ProblemException if an error occurs while changing the ACL.
   */
  public void putACL( final IACL acl, IProgressMonitor monitor ) throws ProblemException;

}
