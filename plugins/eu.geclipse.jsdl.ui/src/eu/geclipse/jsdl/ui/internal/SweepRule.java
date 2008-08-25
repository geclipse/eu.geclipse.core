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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal;



public enum SweepRule {
  
  WITH("changes with"),
  FOR_EACH_CHANGE("changes for each change of"),
  INDEPENDENT("changes independently from"),
  NULL("");
 
  private final String alias;

  SweepRule( final String alias ) {
    this.alias = alias;
  }
  
  public String getAlias() {
    return this.alias;
  }
  
  public static SweepRule valueOfAlias( final String alias ) {
    SweepRule result = null;
    try {
      result = valueOf( alias );
    } catch( IllegalArgumentException iaExc ) {
      for( SweepRule element : SweepRule.values() ) {
        if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
          result = element;
        }
      }
      if( result == null ) {
        result = SweepRule.NULL;
      }
    }
    return result;
  }
  
}
