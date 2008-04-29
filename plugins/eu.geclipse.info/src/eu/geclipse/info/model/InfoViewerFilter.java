/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Nikolaos Tsioutsias - initial API and implementation
 *****************************************************************************/

package eu.geclipse.info.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * This class is suppose to be extended in order to add filters in the info view. 
 * @author tnikos
 *
 */
public class InfoViewerFilter extends ViewerFilter{
  private String text;
  
  /**
   * Set the description of the info viewer filter
   * @param text the description in String  
   */
  public void setText(final String text) {
    this.text = text;
  }
  
  /**
   * Get the description of the info viewer filter
   * @return a String with the description or null
   */
  public String getText()
  {
    return this.text;
  }

  @Override
  public boolean select( final Viewer viewer, final Object parentElement, final Object element ) {
    return true; //
  }
  
}
