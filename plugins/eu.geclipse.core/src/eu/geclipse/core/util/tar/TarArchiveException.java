/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial implementation
 *****************************************************************************/

package eu.geclipse.core.util.tar;

import eu.geclipse.core.GridException;

/**
 * A subclass of {@link GridException} describing issues processing
 * a tar stream.
 * 
 * @author ariel
 */
public class TarArchiveException extends GridException {

  private static final long serialVersionUID = 7009702931714516945L;

  /**
   * Constructs a new TarArchiveException.
   * 
   * @param problemID The ID of the problem describing the issue.
   */
  public TarArchiveException( final int problemID ) {
    super( problemID );
  }

  /**
   * Constructs a new TarArchiveException.
   * 
   * @param problemID The ID of the problem describing the issue.
   * @param description A message string giving a more detailed
   * description of the problem.
   */
  public TarArchiveException( final int problemID, final String description ) {
    super( problemID, description );
  }

  /**
   * Constructs a new TarArchiveException.
   * 
   * @param problemID The ID of the problem describing the issue.
   * @param exc The exception that caused this problem.
   */
  public TarArchiveException( final int problemID, final Throwable exc ) {
    super( problemID, exc );
  }

}
