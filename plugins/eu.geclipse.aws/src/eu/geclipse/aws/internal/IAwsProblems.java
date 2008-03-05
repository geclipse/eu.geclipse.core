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

package eu.geclipse.aws.internal;

/**
 * Problem IDs for AWS problems.
 */
public interface IAwsProblems {
  
  /**
   * ID for the service creation failed problem.
   */
  public static final String REST_SERVICE_CREATION_FAILED
    = "eu.geclipse.aws.problem.service.s3.rest.creationFailed"; //$NON-NLS-1$
  
  /**
   * ID for the list failed problem.
   */
  public static final String S3_LIST_FAILED =
    "eu.geclipse.aws.problem.s3.listFailed"; //$NON-NLS-1$
  
  /**
   * ID for the input failed problem.
   */
  public static final String S3_INPUT_FAILED =
    "eu.geclipse.aws.problem.s3.inputFailed"; //$NON-NLS-1$
  
  /**
   * ID for the output failed problem.
   */
  public static final String S3_OUTPUT_FAILED =
    "eu.geclipse.aws.problem.s3.outputFailed"; //$NON-NLS-1$
  
  /**
   * ID for the object load failed problem.
   */
  public static final String S3_OBJECT_LOAD_FAILED =
    "eu.geclipse.aws.problem.s3.objectLoadFailed"; //$NON-NLS-1$
  
  /**
   * ID for the delete failed problem.
   */
  public static final String S3_DELETE_FAILED =
    "eu.geclipse.aws.problem.s3.deleteFailed"; //$NON-NLS-1$

}