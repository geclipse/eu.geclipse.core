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
 *           
 *****************************************************************************/
package eu.geclipse.ui.internal.wizards.jobs;

/**
 * Enumeration type holding names of elements that are children of {@link FirstLevelElements}
 * @author User
 *
 */
public enum ChildrenElements {
  /**
   * Enabled element (child of Text, List or TextFithFileChooser elements)
   */
  ENABLED("enabled"), //$NON-NLS-1$
  /**
   * Hint element (child of Text, List or TextFithFileChooser elements)
   */
  HINT("hint"), //$NON-NLS-1$
  /**
   * ParamName element (child of Text, List or TextFithFileChooser elements)
   */
  PARAM_NAME("paramName"), //$NON-NLS-1$
  /**
   * ParamPrefix element (child of Text, List or TextFithFileChooser elements)
   */
  PARAM_PREFIX("paramPrefix"), //$NON-NLS-1$
  /**
   * Value element (child of List elelemnt)
   */
  VALUE("value"), //$NON-NLS-1$
  /**
   * Writeable element (child of List element)
   */
  WRITEABLE("writeable"), //$NON-NLS-1$
  /**
   * Label element (child of Text, List or TextFithFileChooser elements)
   */
  LABEL("label"), //$NON-NLS-1$
  /**
   * Optional element (child of Text, List or TextWithFileChooser elements)
   */
  OPTIONAL("optional"), //$NON-NLS-1$
  /**
   * MinArgumentsCount element (child of multipleArguments element) 
   */
  MIN_ARGUMENTS_COUNT ("minArgumentsCount"), //$NON-NLS-1$
  /**
   * MaxArgumentsCount element (child of multipleArguments element) 
   */
  MAX_ARGUMENTS_COUNT ("maxArgumentsCount"), //$NON-NLS-1$
  /**
   * Value for any other value that is not a value of children elements
   * defined in this enum
   */
  NULL("null"); //$NON-NLS-1$

  private final String alias;

  ChildrenElements( final String alias ) {
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
  public static ChildrenElements valueOfAlias( final String alias ) {
    ChildrenElements result = null;
    try {
      result = valueOf( alias );
    } catch( IllegalArgumentException iaExc ) {
      for( ChildrenElements element : ChildrenElements.values() ) {
        if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
          result = element;
        }
      }
      if( result == null ) {
        result = ChildrenElements.NULL;
      }
    }
    return result;
  }
}