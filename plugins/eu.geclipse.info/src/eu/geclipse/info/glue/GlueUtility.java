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
 * @author Nikolaos Tsioutsias
 *
 */
public class GlueUtility {
  
  /**
   * 
   * @param attribute The name of the attribute
   * @param attributes The list of attributes
   * @return returns an empty String if a wrong attribute name is passed or
   * a String with the value of the attribute.
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
   * @param attribute The name of the attribute
   * @param attributes The list of attributes
   * @return returns an Integer object with value of -1 if the wrong name is passed or the
   * value of the attribute.
   */
  public static Integer getIntegerAttribute( final String attribute,
                                   final Attributes attributes )
  {
    Integer result = new Integer(-1);
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  new Integer(Integer.parseInt( attr.get().toString() ));
      } catch( NamingException e ) {
        // ignore missing fields
      } catch( NumberFormatException e ) {
        // Ignore Exception
      }
    }
    return result;
  }

  /**
   * @param attribute The name of the attribute
   * @param attributes The list of attributes
   * @return returns a Long object with value of -1 if the wrong name is passed or the
   * value of the attribute.
   */
  public static Long getLongAttribute( final String attribute,
                                       final Attributes attributes )
  {
    Long result = new Long(-1);
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  new Long(Long.parseLong( attr.get().toString() ));
      } catch( NamingException e ) {
        // ignore missing fields
      } catch( NumberFormatException e ) {
        // Ignore Exception
      }
    }
    return result;
  }
}
