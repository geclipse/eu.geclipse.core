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

package eu.geclipse.info.model;

/**
 * A category of a GlueInfoTopTreeElement
 * @author tnikos
 *
 */
public class InfoTopTreeCategory {
  String glueObjectName = ""; //$NON-NLS-1$
  String objectTableName = ""; //$NON-NLS-1$
 
  /**
   * Get the GLUE object name like GlueService or GlueSite
   * @return a String with the GLUE object name or null
   */
  public String getGlueObjectName() {
    return this.glueObjectName;
  }
  
  /**
   * Set the GLUE object name. Valid glue elements should be used like GlueService or GlueSite
   * @param glueObjectName a String with the GLUE table object name
   */
  public void setGlueObjectName( final String glueObjectName ) {
    this.glueObjectName = glueObjectName;
  }

  /**
   * Get the glue table name. It is used to distinguish entries with the same GLUE object name.
   * @return A String with the glue table name or null.
   */
  public String getObjectTableName() {
    return this.objectTableName;
  }

  /**
   * Set the GLUE object table.
   * @param objectTableName A string with the GLUE object table name.
   */
  public void setObjectTableName( final String objectTableName ) {
    this.objectTableName = objectTableName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
  public boolean equals( final Object obj ) {
    if( this == obj )
      return true;
    if( obj == null )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    final InfoTopTreeCategory other = ( InfoTopTreeCategory )obj;
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
  
  
}
