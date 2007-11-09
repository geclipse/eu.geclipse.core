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
 *    Nikolaos Tsioutsias - initial API and implementation
 *    
 *****************************************************************************/
package eu.geclipse.info.glue;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;


/**
 * @author nickl
 *
 */
public class GlueUtility {
  
  /**
   * @param attribute
   * @param attributes
   * @return
   */
  public static String getStringAttribute( final String attribute,
                                           final Attributes attributes )
  {
    String result = ""; //$NON-NLS-1$
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  attr.get().toString();
      } catch( NamingException e ) {
        // ignore missing fields
      }
    }
    return result;
  }

  /**
   * @param attribute
   * @param attributes
   * @return
   */
  public static int getIntegerAttribute( final String attribute,
                                   final Attributes attributes )
  {
    int result = -1;
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  Integer.parseInt( attr.get().toString() );
      } catch( NamingException e ) {
        // ignore missing fields
      }
    }
    return result;
  }

  /**
   * @param attribute
   * @param attributes
   * @return
   */
  public static long getLongAttribute( final String attribute,
                                       final Attributes attributes )
  {
    long result = -1;
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  Long.parseLong( attr.get().toString() );
      } catch( NamingException e ) {
        // ignore missing fields
      }
    }
    return result;
  }
}
