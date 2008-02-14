/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *      - Nikolaos Tsioutsias
 *
 *****************************************************************************/

package eu.geclipse.info.views;


/**
 * The elements that will be shown as the top tree elements in the info view.
 * @author tnikos
 *
 */
public class GlueInfoTopTreeElement {
  
  private String glueObjectName;
  private String displayName;
  private String objectTableName;
  /**
   * @param glueObjectName String representing the name of the Glue Object 
   * such as "GlueSite", "GlueCE", "GlueSE" ... and is a AbstractGlueTable
   * @param displayName The name to display in the info view
   * @param objectTableName A string that defines the value that the parameter tableName of
   * the Glue Object
   */
  public GlueInfoTopTreeElement(final String glueObjectName,
                                final String displayName,
                                final String objectTableName)
  {
    if (glueObjectName == null)
      this.glueObjectName = ""; //$NON-NLS-1$
    else
      this.glueObjectName = glueObjectName;
    
    if (displayName == null)
      this.displayName = ""; //$NON-NLS-1$
    else
      this.displayName = displayName;
    
    if (objectTableName == null)
      this.objectTableName = ""; //$NON-NLS-1$
    else
      this.objectTableName = objectTableName;
  }
  
  /**
   * The name of the Glue Object 
   * @return A String with the Glue Object name or an empty string
   */
  public String getGlueObjectName() {
    return this.glueObjectName;
  }
  
  /**
   * The name to display in the info view
   * @return A String with the name to display in the info view or an empty string
   */
  public String getDisplayName() {
    return this.displayName;
  }
  
  /**
   * The value of the parameter tableName of the Glue Object
   * @return A string that defines the value that the parameter tableName of
   * the Glue Object or an empty string
   */
  public String getObjectTableName() {
    return this.objectTableName;
  }

  @Override
  // Automatically created by eclipse
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime
             * result
             + ( ( this.displayName == null )
                                        ? 0
                                        : this.displayName.hashCode() );
    result = prime
             * result
             + ( ( this.glueObjectName == null )
                                           ? 0
                                           : this.glueObjectName.hashCode() );
    result = prime
             * result
             + ( ( this.objectTableName == null )
                                            ? 0
                                            : this.objectTableName.hashCode() );
    return result;
  }

  @Override
  // Automatically created by eclipse
  public boolean equals( final Object obj ) {
    if( this == obj )
      return true;
    if( obj == null )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    final GlueInfoTopTreeElement other = ( GlueInfoTopTreeElement )obj;
    if( this.displayName == null ) {
      if( other.displayName != null )
        return false;
    } else if( !this.displayName.equals( other.displayName ) )
      return false;
    if( this.glueObjectName == null ) {
      if( other.glueObjectName != null )
        return false;
    } else if( !this.glueObjectName.equals( other.glueObjectName ) )
      return false;
    if( this.objectTableName == null ) {
      if( other.objectTableName != null )
        return false;
    } else if( !this.objectTableName.equals( other.objectTableName ) )
      return false;
    return true;
  }
  
  /**
   * Return the object as a three dimensional array of String
   * @return A three dimensional array of String
   */
  public String[] toArray()
  {
    String[] result = new String[3];
    result[0] = this.glueObjectName;
    result[1] = this.displayName;
    result[2] = this.objectTableName;
    
    return result; 
  }
}
