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

package eu.geclipse.aws.s3.ui.internal;

import eu.geclipse.ui.wizards.GenericConnectionTokenValidator;

/**
 * Validator for the bucket path
 * 
 * @author Moritz Post
 */
public class S3TokenValidator extends GenericConnectionTokenValidator {

  /** The path prefix. */
  public static final String PATH_PREFIX = "/"; //$NON-NLS-1$

  @Override
  protected String validatePath( final String path ) {
    String error = super.validatePath( path );
    if( error == null ) {
      if( !path.startsWith( S3TokenValidator.PATH_PREFIX ) ) {
        error = String.format( Messages.getString( "S3TokenValidator.prefix_missing_error" ), S3TokenValidator.PATH_PREFIX ); //$NON-NLS-1$
      }
    }
    return error;
  }

}
