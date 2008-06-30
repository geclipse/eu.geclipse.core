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
 * An access control entry in an ACL. This entry describes one
 * resource access rule, either allow or deny a single capability
 * on that resource for a single actor. A capability is either a
 * low level operation (read, write, ...) or a higher level role
 * (owner, manager, ...).
 * <p>
 * Also known as 'rule' in the XACML standard.
 * 
 * @author agarcia
 */
public interface IACLEntry {
  
  /**
   * The policy of the ACL, either 'allow' or 'deny'.
   * <p>
   * Also known as 'effect' in the XACML standard.
   */
  public static enum Policy {
    /** Allow access policy */
    ALLOW,
    /** Deny access policy */
    DENY
  }
  
  /**
   * Queries the policy of this entry.
   * 
   * @return the policy
   */
  public Policy getPolicy();
  
  /**
   * Sets the policy of this entry.
   * 
   * @param policy the value to set
   * @throws ProblemException if the provided policy is not allowed
   *         (for instance, some implementations only use "allow" ACLs,
   *         the default policy being "deny all to everybody")
   */
  public void setPolicy( final Policy policy ) throws ProblemException;
  
  /**
   * Queries the supported operations or roles which can be granted
   * or not to the actor of the ACL. The supported capabilities are
   * determined by the implementation.
   * 
   * @return an array of either operations or roles
   */
  public String[] getSupportedCapabilities();
  
  /**
   * Returns the capability which this ACL entry refers to.
   * <p>
   * Also known as 'target-action' in the XACML standard.
   * 
   * @return the capability name
   */
  public String getCapability();
  
  /**
   * Sets the capability to be (dis)allowed to the actor.
   * 
   * @param capability the capability to set
   * @throws ProblemException if the capability is <code>null</code> or invalid
   */
  public void setCapability( final String capability ) throws ProblemException;
  
  /**
   * Returns the actor of this entry.
   * 
   * @return the actor
   */
  public IACLActor getActor();
  
  /**
   * Sets the actor of this entry.
   * 
   * @param actor the actor to set
   */
  public void setActor( final IACLActor actor );
  
}
