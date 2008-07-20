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


/**
 * A policy to be used in the ACL entries.
 * The supported policies are determined by the implementation.
 * The simplest case would have only the <b>allow</b> policy. Other
 * examples would have for instance <b>allow</b> and <b>deny</b> policies.
 * The implementation determines how different policies are prioritized
 * and evaluated. The recommended implementation is an <code>enum</code>
 * containing all the policies allowed by the middleware.
 * <p>
 * Also known as 'effect' in the XACML standard.
 * 
 * @author agarcia
 */
public interface IACLPolicy {
  
  /**
   * Returns the name of this policy, for instance "Allow".
   * 
   * @return a user friendly string.
   */
  public String toString();
  
  /**
   * Returns a description of this policy, for instance to be used
   * in tooltips.
   * 
   * @return a user friendly string explaining the policy.
   */
  public String getDescription();

}
