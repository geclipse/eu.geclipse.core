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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3;

/**
 * This is an internal interface that holds some important definitions for S3.
 */
public interface IS3Constants {

  /** The scheme of the S3-file system implementation. */
  public static final String S3_SCHEME = "s3"; //$NON-NLS-1$

  /** The path separator used within S3 URIs. */
  public static final String S3_PATH_SEPARATOR = "/"; //$NON-NLS-1$

  /** The application tag used when accessing the S3 service. */
  public static final String APP_TAG = "g-Eclipse/1.0"; //$NON-NLS-1$

  // /** A root element for the S3 EFS implementation. */
  // public static final String S3_ROOT = "root"; //$NON-NLS-1$

}
