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
   * The name that will be shown on the tree in the glue info view.
   */
  public String displayName = null;
  
  /**
   * 
   */
  public boolean byRefOnly = true;
  /**
   * The date and time that this object was retrieved and stored
   */
  public String retrievalTime = ""; //$NON-NLS-1$
  protected String keyName;
  private String key;
  private String voType = ""; //$NON-NLS-1$
  
  
  
  /**
   * Set the type of the vo that this glue table belongs to.
   * @param voType a string of the VO type, like gria or glite
   */
  public void setVoType(final String voType)
  {
    if (voType != null)
      this.voType = voType;
  }
  
  /**
   * Get the type of the vo that this glue table belongs to.
   * @return the type of the vo or an empty string.
   */
  public String getVotype()
  {
    return this.voType;
  }
  /**
   * @return The unique identified for this Glue entry
   */
  public String getID(){
    return this.getKey();
  }

  /**
   * 
   * @return the display name for this Glue entry
   */
  public String getDisplayName() {
    String result = ""; //$NON-NLS-1$
    
    if (this.displayName == null || this.displayName.equals( "" )) //$NON-NLS-1$
        result = this.getID();
    else
      result = this.displayName;
    
    return result;
  }
  /**
   * Update the value of an attribute of the Glue object. If there is no 
   * attribute with the provided name and there is an attribute named fieldName<b>List</b> 
   * the provided value is appended to that list.
   * @param fieldName the name of the field to modify
   * @param value 
   * @return the value of the updated field, null in the case of a list
   * @throws RuntimeException
   * @throws IllegalAccessException
   */

  @SuppressWarnings({
    "boxing", "unchecked"
  })
  public Object setFieldByName( final String fieldName, final Object value  )
  throws RuntimeException, IllegalAccessException//, NoSuchFieldException
  {
    Object ret = null;
    Object valueToset = value;
    Field f;
    try {
      f = this.getClass().getField( fieldName );
      if( f.getGenericType().equals( Integer.class ) ) {
        valueToset = new Integer( ( String )value );
      } else if( f.getGenericType().equals( Long.class ) ) {
        try {
          valueToset = new Long( ( String )value );
        } catch (NumberFormatException ex)
        {
          valueToset = 0;
        }
      } else if( f.getGenericType().equals( Double.class ) ) {
        try {
          valueToset = new Double( ( String )value );
        } catch (NumberFormatException ex)
        {
          valueToset = 0.0;
        }
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
/**
 * Set the key 
 * @param key
 */
  public void setKey( final String key ) {
    this.key = key;
  }

  /**
   * Get the key
   * @return They key as String or null
   */
  public String getKey() {
    return this.key;
  }

  // Get the date time that this object was retrieved and stored
  public String getRetrievalTime() {
    return this.retrievalTime;
  }

  // Set the date time that this object was retrieved and stored
  public void setRetrievalTime( final String retrievalTime ) {
    this.retrievalTime = retrievalTime;
  }
  
}
