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
 * <p>
 * The supported policies are determined by the implementation. The simplest
 * case would have the <b>allow</b> policy only. Other examples would have
 * <b>allow</b> and <b>deny</b> policies, for instance. The implementation
 * determines how different policies are prioritized and evaluated.
 * <p>
 * The recommended implementation is an <code>enum</code> containing all
 * the policies allowed by the middleware.
 * <p>
 * Also known as 'effect' in the XACML standard.
 * 
 * @author agarcia
 */
public interface IACLPolicy {
  
  /** Flag for a policy which essentially allows the actor access on the resource */
  public static short TYPE_ALLOW = 1;
  /** Flag for a policy which essentially denies the actor access on the resource */
  public static short TYPE_DENY = -1;
  /** Flag for a policy whose effect is not so directly 'allow' or 'deny' */
  public static short TYPE_OTHER = 0;
  
  /**
   * Returns the name of this policy, for instance "Allow".
   * 
   * @return a user friendly string. Must be non empty.
   */
  public String toString();
  
  /**
   * Returns a description of this policy, for instance to be used
   * in tool-tips.
   * 
   * @return a user friendly string explaining the policy.
   */
  public String getDescription();
  
  /**
   * Returns the policy type, to be used for sorting and grouping
   * similar rules.
   * 
   * @return a flag, one of {@link IACLPolicy.TYPE_ALLOW},
   *         {@link IACLPolicy.TYPE_DENY}, {@link IACLPolicy.TYPE_OTHER}.
   */
  public short getType();

}
