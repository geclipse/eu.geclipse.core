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
 * Problem IDs for AWS problems.
 */
public interface IS3Problems {

  /**
   * ID for the service creation failed problem.
   */
  public static final String REST_SERVICE_CREATION_FAILED = "eu.geclipse.aws.s3.problem.service.rest.creationFailed"; //$NON-NLS-1$

  /**
   * ID for the bucket creation failed problem.
   */
  public static final String S3_BUCKET_CREATION_FAILED = "eu.geclipse.aws.s3.problem.bucketCreateFailed"; //$NON-NLS-1$

  /**
   * ID for the bucket creation failed problem.
   */
  public static final String S3_BUCKET_IN_BUCKET_FAILED = "eu.geclipse.aws.s3.problem.bucketInBucketFailed"; //$NON-NLS-1$

  /**
   * ID for the list failed problem.
   */
  public static final String S3_LIST_FAILED = "eu.geclipse.aws.s3.problem.listFailed"; //$NON-NLS-1$

  /**
   * ID for the input failed problem.
   */
  public static final String S3_INPUT_FAILED = "eu.geclipse.aws.s3.problem.inputFailed"; //$NON-NLS-1$

  /**
   * ID for the output failed problem.
   */
  public static final String S3_OUTPUT_FAILED = "eu.geclipse.aws.s3.problem.outputFailed"; //$NON-NLS-1$

  /**
   * ID for the object load failed problem.
   */
  public static final String S3_OBJECT_LOAD_FAILED = "eu.geclipse.aws.s3.problem.objectLoadFailed"; //$NON-NLS-1$

  /**
   * ID for the delete failed problem.
   */
  public static final String S3_DELETE_FAILED = "eu.geclipse.aws.s3.problem.deleteFailed"; //$NON-NLS-1$

  /**
   * ID for general interaction failure.
   */
  public static final String S3_INTERACTION = "eu.geclipse.aws.s3.problem.interactionFailed"; //$NON-NLS-1$

}
