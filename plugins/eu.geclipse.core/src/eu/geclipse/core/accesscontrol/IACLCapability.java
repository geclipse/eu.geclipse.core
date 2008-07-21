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
 * A capability to be used in the ACL entries.
 * A capability is a permission or role to be granted or denied to an
 * actor ({@link IACLActor}) on the protected resource. The supported
 * capabilities are determined by the implementation and are also often
 * resource specific (for instance, folders might support two different
 * capabilities "addFiles" and "removeFiles", while files support simply
 * "write").
 * <p>
 * Also known as 'target-action' in the XACML standard.
 * 
 * @author agarcia
 */
public interface IACLCapability {
  
  /**
   * Returns the name of this capability, for instance "Write".
   * 
   * @return a user friendly string.
   */
  public String getName();
  
  /**
   * Returns a user friendly description of this capability, for instance
   * to be used in tooltips.
   * 
   * @return a string explaining the capability.
   */
  public String getDescription();

}
