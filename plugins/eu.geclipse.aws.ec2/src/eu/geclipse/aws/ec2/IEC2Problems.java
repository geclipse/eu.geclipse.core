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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2;

import eu.geclipse.core.reporting.IProblem;

/**
 * This interfaces houses a set of {@link IProblem} IDs related to the AWS
 * system, which can be used throughout the project.
 * 
 * @author Moritz Post
 */
public interface IEC2Problems {

  /*
   * Please keep the list sorted by extensionID!
   */
  /** Authentication with the EC2 Service failed. */
  public static final String EC2_AUTH_FAILED = "eu.geclipse.aws.ec2.problem.auth.authFailed"; //$NON-NLS-1$

  /** Problem interfacing with the EC2 services */
  public static final String EC2_INTERACTION = "eu.geclipse.aws.ec2.problem.net.ec2"; //$NON-NLS-1$

}
