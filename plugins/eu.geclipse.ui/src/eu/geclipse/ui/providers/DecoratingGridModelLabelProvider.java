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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Decorating label provider that forwards the {@link ITableLabelProvider}
 * functionalities to the {@link IStyledLabelProvider} if this is an instance
 * of {@link ITableLabelProvider}.
 */
public class DecoratingGridModelLabelProvider
    extends DecoratingStyledCellLabelProvider
    implements ITableLabelProvider {

  /**
   * Create a new label provider from the specified settings.
   * 
   * @param labelProvider The {@link IStyledLabelProvider}.
   * @param decorator The {@link ILabelDecorator}.
   */
  public DecoratingGridModelLabelProvider( final IStyledLabelProvider labelProvider,
                                           final ILabelDecorator decorator ) {
    super( labelProvider, decorator, null );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
   */
  public Image getColumnImage( final Object element, final int columnIndex ) {
    Image result = null;
    IStyledLabelProvider provider = getStyledStringProvider();
    if ( ( provider instanceof ITableLabelProvider )
        && ( columnIndex > 0 ) ) {
      result = ( ( ITableLabelProvider ) provider ).getColumnImage( element, columnIndex );
    } else if ( columnIndex == 0 ) {
      result = super.getImage( element );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
   */
  public String getColumnText( final Object element, final int columnIndex ) {
    String result = ""; //$NON-NLS-1$
    IStyledLabelProvider provider = getStyledStringProvider();
    if ( ( provider instanceof ITableLabelProvider )
        && ( columnIndex > 0 ) ) {
      result = ( ( ITableLabelProvider ) provider ).getColumnText( element, columnIndex );
    } else if ( columnIndex == 0 ) {
      result = provider.getStyledText( element ).getString();
    }
    return result;
  }
  
}
