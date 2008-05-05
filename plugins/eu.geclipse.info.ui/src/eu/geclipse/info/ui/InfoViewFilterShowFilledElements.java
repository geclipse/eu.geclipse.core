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
 *   - University of Cyprus
 *      Nikolaos Tsioutsias - initial API and implementation
 *****************************************************************************/

package eu.geclipse.info.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.info.views.GlueInfoTopTreeElement;
import eu.geclipse.info.ui.GlueInfoViewer.TreeObject;
import eu.geclipse.info.ui.GlueInfoViewer.TreeParent;

/**
 * This filter allows only elements that are filled by the information system
 * @author tnikos
 */

public class InfoViewFilterShowFilledElements extends ViewerFilter {

  @Override
  public boolean select( final Viewer viewer, final Object parentElement, final Object element ) {
    
    boolean result = false;
    
    if ( element instanceof TreeParent )
    {
      TreeParent treeParent = ((TreeParent)element);
      GlueInfoTopTreeElement topTreeElement = treeParent.getQuery();
      
      // Show the top level tree elements
      if ( topTreeElement != null && topTreeElement.getDisplayName() != null)
      {
          result = true;
      }
      else if ( topTreeElement == null)
      {
        if (treeParent.getChildren().length > 0 )
          result = true;
        else
          result = false;
      }
    }
    else if (element instanceof TreeObject 
            && !((TreeObject)element).toString().contains( "Not available from the information service" ))
      result = true;
    
    return result;
  }
}
