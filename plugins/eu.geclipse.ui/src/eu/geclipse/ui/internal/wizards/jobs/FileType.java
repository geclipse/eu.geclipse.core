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
 *     PSNC - Katarzyna Bylec
 *****************************************************************************/
package eu.geclipse.ui.internal.wizards.jobs;

/**
 * Enumeration type to store type of files that can be send on Grid and be ued
 * as a parameters in jsdl
 * 
 */
public enum FileType {
  
  /**
   * Input file type 
   */
  INPUT ("in"), //$NON-NLS-1$
  
  /**
   * Output file type
   */
  OUTPUT ("out"), //$NON-NLS-1$
  /**
   * Value for any other value that is not a value of children elements defined
   * in this enum
   */
  NULL("null"); //$NON-NLS-1$
  
  private final String alias;
  
  private FileType(final String alias) {
    this.alias = alias;
  }
  
  /**
   * Get alias ov enum value
   * 
   * @return alias of a given enum value
   */
  public String getAlias() {
    return this.alias;
  }
  
  /**
   * Returns enum value for given alias
   * 
   * @param alias alias of enum value
   * @return enum value for given alias or {@link ChildrenElements#NULL} if
   *         there is no value with this alias
   */
  public static FileType valueOfAlias( final String alias ) {
    FileType result = null;
    try {
      result = valueOf( alias );
    } catch( IllegalArgumentException iaExc ) {
      for( FileType element : FileType.values() ) {
        if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
          result = element;
        }
      }
      if( result == null ) {
        result = FileType.NULL;
      }
    }
    return result;
  }
  
}
