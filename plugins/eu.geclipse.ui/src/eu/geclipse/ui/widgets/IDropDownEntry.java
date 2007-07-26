/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.widgets;

/**
 * This interface has to be implemented by plugins which want to provide a
 * drop down entry.
 * @param <UserDataType> user data to pass when the entry gets selected.
 */
public interface IDropDownEntry<UserDataType> {
  
  /**
   * Element containing the drop down entries attributes.
   */
  public static final String EXT_DROP_DOWN_ENTRY = "dropDownEntry"; //$NON-NLS-1$

  /**
   * Attribute specifying the drop down entries class.
   */
  public static final String EXT_CLASS = "class"; //$NON-NLS-1$

  /**
   * Attribute specifying the drop down entries name.
   */
  public static final String EXT_LABEL = "label"; //$NON-NLS-1$

  /**
   * Attribute specifying the drop down entries icon.
   */
  public static final String EXT_ICON = "icon"; //$NON-NLS-1$

  /**
   * Called when the user selects the drop down entry.
   * @param userData the userdata of the entry.
   */
  public void action( final UserDataType userData );
}
