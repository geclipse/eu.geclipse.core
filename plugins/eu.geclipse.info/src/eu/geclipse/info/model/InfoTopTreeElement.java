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

import java.util.ArrayList;


/**
 * The elements that will be shown as the top tree elements in the info view.
 * @author tnikos
 *
 */
public class InfoTopTreeElement {
  
  private String displayName;
  private ArrayList<InfoTopTreeCategory> glueInfoTopTreeCategory;
  
  /**
   * 
   * @param glueInfoTopTreeCategory a GlueInfoTopTreeCategory
   * @param displayName The name to display in the info view
   */
  public InfoTopTreeElement(final ArrayList<InfoTopTreeCategory> glueInfoTopTreeCategory,
                                final String displayName)
  { 
    if (glueInfoTopTreeCategory == null)
      this.glueInfoTopTreeCategory = new ArrayList<InfoTopTreeCategory>();
    else
      this.glueInfoTopTreeCategory = glueInfoTopTreeCategory;
    
    if (displayName == null)
      this.displayName = ""; //$NON-NLS-1$
    else
      this.displayName = displayName;
    
  }
  
  /**
   * Get the GlueInfoTopTreeCategory.
   * @return a GlueInfoTopTreeCategory or null
   */
  public ArrayList<InfoTopTreeCategory> getGlueInfoTopTreeCategory() {
    return this.glueInfoTopTreeCategory;
  }
  
  /**
   * The name to display in the info view
   * @return A String with the name to display in the info view or an empty string
   */
  public String getDisplayName() {
    return this.displayName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime
             * result
             + ( ( this.displayName == null )
                                        ? 0
                                        : this.displayName.hashCode() );
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
    final InfoTopTreeElement other = ( InfoTopTreeElement )obj;
    if( this.displayName == null ) {
      if( other.displayName != null )
        return false;
    } else if( !this.displayName.equals( other.displayName ) )
      return false;
    return true;
  }
  
  
}
