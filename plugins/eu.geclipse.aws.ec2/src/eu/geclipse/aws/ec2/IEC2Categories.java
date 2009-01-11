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

/**
 * A container interface to hold category ids as specified in the
 * <code>plugin.xml</code>.
 * 
 * @author Moritz Post
 */
public interface IEC2Categories {

  /** A category for EC2 images. */
  public static final String CATEGORY_EC2_IMAGES = "eu.geclipse.aws.ec2.images"; //$NON-NLS-1$

  /** A category for EC2 AMIs belonging to the current user. */
  public static final String CATEGORY_EC2_IMAGES_MY_OWNED = "eu.geclipse.aws.ec2.images.myOwned"; //$NON-NLS-1$

  /** A category for EC2 AMIs to which the user has execution rights. */
  public static final String CATEGORY_EC2_IMAGES_MY_ACCESSIBLE = "eu.geclipse.aws.ec2.images.myAccessible"; //$NON-NLS-1$

  /** A category for EC2 AMIs belonging to any user (all public AMIs). */
  public static final String CATEGORY_EC2_IMAGES_ALL = "eu.geclipse.aws.ec2.images.allImages"; //$NON-NLS-1$

  /** A category to contain security groups. */
  public static final String CATEGORY_EC2_SECURITY_GROUPS = "eu.geclipse.aws.ec2.securityGroups"; //$NON-NLS-1$

  /** A category to contain keypairs. */
  public static final String CATEGORY_EC2_KEYPAIRS = "eu.geclipse.aws.ec2.keypairs"; //$NON-NLS-1$

  /** A category to contain elastic IPs. */
  public static final String CATEGORY_EC2_ELASTIC_IP = "eu.geclipse.aws.ec2.elasticIP"; //$NON-NLS-1$

}
