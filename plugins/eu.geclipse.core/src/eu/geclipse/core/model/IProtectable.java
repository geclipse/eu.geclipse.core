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
   * Returns the access control list attached to this grid object
   * (file, service, etc.).
   * 
   * @return an {@link IACL} object.
   * @throws ProblemException if an error occurs while querying the ACL.
   */
  public IACL getACL() throws ProblemException;
  
  /**
   * Modifies the access control list associated to this grid object.
   * 
   * @param acl the {@link IACL} to set on this element.
   * @throws ProblemException if an error occurs while changing the ACL.
   */
  public void setACL( final IACL acl ) throws ProblemException;

}
