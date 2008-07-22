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
package eu.geclipse.servicejob.model;

/**
 * Enumeration class of Operator's Jobs statuses.
 */
public enum ServiceJobStates {
  /**
   * OK state.
   */
  OK("OK"), //$NON-NLS-1$
  /**
   * ERROR state (when Job was finished).
   */
  ERROR("ERROR"), //$NON-NLS-1$
  /**
   * WARRNING state (when Job was finished).
   */
  WARNING("WARNING"), //$NON-NLS-1$
  /**
   * CRITICAL state (when Job was finished).
   */
  CRITICAL("CRITICAL"), //$NON-NLS-1$
  /**
   * State not available.
   */
  NA("N/A"), //$NON-NLS-1$
  /**
   * Operator's Job is still running.
   */
  RUNNING("RUNNING"), //$NON-NLS-1$
  /**
   * null value
   */
  NULL("null"); //$NON-NLS-1$

  String alias;

  ServiceJobStates( final String alias ) {
    this.alias = alias;
  }

  /**
   * Returns enumeration value for given state alias.
   * 
   * @param alias text alias of Operator's Job state
   * @return {@link ServiceJobStates} object representing value of alias, or
   *         {@link ServiceJobStates#NULL} if there is no value for given alias.
   */
  public static ServiceJobStates valueOfAlias( final String alias ) {
    ServiceJobStates result = null;
    try {
      result = valueOf( alias );
    } catch( IllegalArgumentException iaExc ) {
      for( ServiceJobStates element : ServiceJobStates.values() ) {
        if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
          result = element;
        }
      }
      if( result == null ) {
        result = ServiceJobStates.NULL;
      }
    }
    return result;
  }

  /**
   * Method to access string alias of enumeration value.
   * 
   * @return string alias of enumeration value
   */
  public String getAlias() {
    return this.alias;
  }
}
