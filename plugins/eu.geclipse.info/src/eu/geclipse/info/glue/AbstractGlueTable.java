/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.info.glue;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author George Tsouloupas
 *
 */
public abstract class AbstractGlueTable implements Serializable{

  /**
   * 
   */
  public String tableName;
  /**
   * 
   */
  public boolean byRefOnly = true;
  
  protected String key;
  protected String keyName;
  /**
   * @return The unique identified for this Glue entry
   */
  public String getID(){
    return this.key;
  }
  
//  public void addReference( AbstractGlueTable agt ) {
//    String referencedTableName = agt.getClass().getSimpleName();
//    System.out.println( referencedTableName + " " + agt.keyName ); //$NON-NLS-1$
//  }

  /**
   * Update the value of an attribute of the Glue object. If there is no 
   * attribute with the provided name and there is an attribute named fieldName<b>List</b> 
   * the provided value is appended to that list.
   * @param fieldName the name of the field to modify
   * @param value 
   * @return the value of the updated field, null in the case of a list
   * @throws RuntimeException
   * @throws IllegalAccessException
   * @throws NoSuchFieldException
   */

  public Object setFieldByName( final String fieldName, final Object value  )
  throws RuntimeException, IllegalAccessException, NoSuchFieldException
  {
    Object ret = null;
    Object valueToset = value;
    Field f;
    try {
      f = this.getClass().getField( fieldName );
      if( f.getGenericType().equals( Integer.class ) ) {
        valueToset = new Integer( ( String )value );
      } else if( f.getGenericType().equals( Long.class ) ) {
        valueToset = new Long( ( String )value );
      } else if( f.getGenericType().equals( Double.class ) ) {
        valueToset = new Double( ( String )value );
      }
      f.set( this, valueToset );
      ret = this.getClass().getField( fieldName ).get( this );
    } catch( NoSuchFieldException nsfe ) {// check for a list
      String listFieldName = fieldName.substring( 0, 1 ).toLowerCase()
                             + fieldName.substring( 1 );
      try {
        f = this.getClass().getField( listFieldName + "List" ); //$NON-NLS-1$
        ArrayList<AbstractGlueTable> list = ( ArrayList<AbstractGlueTable> )f.get( this );
        String sValue = value.toString();
        AbstractGlueTable agt = GlueIndex.getInstance().get( fieldName,
                                                             sValue,
                                                             false );
        list.add( agt );
      } catch( NoSuchFieldException nsfe2 ) {//Ignore this field
      }
    }
    return ret;
  }

  /**
   * @param fieldName the name of the field to return
   * @return the value of the specified field
   * @throws RuntimeException
   * @throws IllegalAccessException
   * @throws NoSuchFieldException
   */
  public Object getFieldByName( final String fieldName )
    throws RuntimeException, IllegalAccessException, NoSuchFieldException
  {
    Object ret = null;
    ret = this.getClass().getField( fieldName ).get( this );
    return ret;
  }
  
}
