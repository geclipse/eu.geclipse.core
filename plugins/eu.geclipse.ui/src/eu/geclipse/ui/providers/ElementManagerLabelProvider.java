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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridProject;

/**
 * Label provider implementation for Grid model views that have
 * an {@link IGridElementManager} as their root element.
 */
public abstract class ElementManagerLabelProvider
    extends GridModelLabelProvider
    implements ITableLabelProvider {
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
   */
  public Image getColumnImage( final Object element,
                               final int columnIndex ) {
    Image image = null;
    if ( columnIndex == 0 ) {
      image = super.getImage( element );
    }
    return image;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
   */
  public String getColumnText( final Object element,
                               final int columnIndex ) {
    String text = ""; //$NON-NLS-1$
    if ( columnIndex == 0 ) {
      text = super.getText( element );
    } else if ( element instanceof IGridElement ) {
      IGridElement gridElement = ( IGridElement ) element;
      switch ( columnIndex ) {
        case 0:
          break;
        case 1:
          IGridProject project = gridElement.getProject();
          if ( project != null ) {
            text = project.getName();
          }
          break;
        default:
          text = getColumnText( gridElement, columnIndex );
          break;
      }
    }
    return text;
  }
  
  protected abstract String getColumnText( final IGridElement element,
                                           final int columnIndex );
  
}
