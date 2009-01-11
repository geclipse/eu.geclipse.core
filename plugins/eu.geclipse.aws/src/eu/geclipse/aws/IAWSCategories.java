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

package eu.geclipse.aws;

/**
 * A container interface to hold category ids as specified in the
 * <code>plugin.xml</code>.
 * 
 * @author Moritz Post
 */
public interface IAWSCategories {

  /** A category for computing elements. */
  public static final String CATEGORY_AWS_COMPUTING = "eu.geclipse.aws.computing"; //$NON-NLS-1$

  /** A category for service elements. */
  public static final String CATEGORY_AWS_SERVICE = "eu.geclipse.aws.service"; //$NON-NLS-1$

  /** A category for service storage. */
  public static final String CATEGORY_AWS_STORAGE = "eu.geclipse.aws.ec2.storage"; //$NON-NLS-1$

}
