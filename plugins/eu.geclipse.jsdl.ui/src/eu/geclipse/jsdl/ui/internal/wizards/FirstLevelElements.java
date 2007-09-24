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
package eu.geclipse.jsdl.ui.internal.wizards;

import eu.geclipse.jsdl.ui.wizards.specific.IApplicationSpecificPage;

/**
 * Enumeration type holding names of the XML elements describing
 * {@link IApplicationSpecificPage}
 */
public enum FirstLevelElements {
  /**
   * Text element (child of specificInput element)
   */
  TEXT("text"), //$NON-NLS-1$
  /**
   * List element ((child of specificInput element)
   */
  LIST("list"), //$NON-NLS-1$
  /**
   * TextWithFileChooser element (child of specificInput element)
   */
  TEXT_WITH_FILE_CHOOSER("textWithFileChooser"), //$NON-NLS-1$
  /**
   * MultipleArguments element (child of specificInput element)
   */
  MULTIPLE_ARGUMENTS("multipleArguments"), //$NON-NLS-1$
  /**
   * Text field holding value of parameter that is local file on execution host,
   * but that needs to be transferred to or from remote location
   */
  TEXT_DATA_STAGING("textDataStaging"), //$NON-NLS-1$
  /**
   * Element to gather multiple element values. Those values are local files on
   * execution host, but need to be transferred to or from some remote host(s)
   */
  MULTIPLE_DATA_STAGING("multipleDataStaging"), //$NON-NLS-1$
  /**
   * Value for any other value than those defined above
   */
  NULL("null"); //$NON-NLS-1$

  private final String alias;

  FirstLevelElements( final String alias ) {
    this.alias = alias;
  }

  /**
   * Get alias of enumeration value
   * 
   * @return "text" for {@link FirstLevelElements#TEXT}<br>
   *         "list" for {@link FirstLevelElements#LIST}<br>
   *         "textWithFileChooser" for
   *         {@link FirstLevelElements#TEXT_WITH_FILE_CHOOSER}
   */
  public String getAlias() {
    return this.alias;
  }

  /**
   * Returns enumeration value for given alias (see
   * {@link FirstLevelElements#getAlias()}
   * 
   * @param alias alias of enumeration value
   * @return enumeration value for given alias or {@link FirstLevelElements#NULL} if
   *         there is no value with this alias
   */
  public static FirstLevelElements valueOfAlias( final String alias ) {
    FirstLevelElements result = null;
    try {
      result = valueOf( alias );
    } catch( IllegalArgumentException iaExc ) {
      for( FirstLevelElements element : FirstLevelElements.values() ) {
        if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
          result = element;
        }
      }
      if( result == null ) {
        result = FirstLevelElements.NULL;
      }
    }
    return result;
  }
}