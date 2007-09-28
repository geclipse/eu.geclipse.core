/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.ui.wizards;

import eu.geclipse.ui.Extensions;

public interface IConnectionTokenValidator {
  
  public static final String URI_TOKEN = Extensions.EFS_URI_ATT;
  
  public static final String SCHEME_SPEC_TOKEN = Extensions.EFS_SCHEME_SPEC_PART_ATT;
  
  public static final String AUTHORITY_TOKEN = Extensions.EFS_AUTHORITY_ATT;
  
  public static final String USER_INFO_TOKEN = Extensions.EFS_USER_INFO_ATT;
  
  public static final String HOST_TOKEN = Extensions.EFS_HOST_ATT;
  
  public static final String PORT_TOKEN = Extensions.EFS_PORT_ATT;
  
  public static final String PATH_TOKEN = Extensions.EFS_PATH_ATT;
  
  public static final String QUERY_TOKEN = Extensions.EFS_QUERY_ATT;
  
  public static final String FRAGMENT_TOKEN = Extensions.EFS_FRAGMENT_ATT;

  public String validateToken( final String tokenID, final String tokenValue );
  
}
