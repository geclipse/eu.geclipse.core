/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Katarzyna Bylec - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl.ui.providers.parameters;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for sweep iterations table.
 */
public class IterationsLProvider implements ITableLabelProvider {

  private List<String> columns;

  /**
   * Sets list with column names which are then mapped to column indexes.
   * 
   * @param newColumns Lit with names of columns in table for which this label
   *          provider used.
   */
  public void setColumnNames( final List<String> newColumns ) {
    this.columns = newColumns;
  }

  public Image getColumnImage( final Object element, final int columnIndex ) {
    return null;
  }

  @SuppressWarnings("unchecked")
  public String getColumnText( final Object element, final int columnIndex ) {
    String result = ""; //$NON-NLS-1$
    if( element instanceof List ) {
      List<String> contentValues = ( List<String> )element;
      result = contentValues.get( columnIndex );
    }
    return result;
  }

  public void addListener( final ILabelProviderListener listener ) {
    // empty implementation
  }

  public void dispose() {
    // empty implementation
  }

  public boolean isLabelProperty( final Object element, final String property )
  {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    // empty implementation
  }

  /**
   * Method for accessing list of columns names set for this label provider.
   * 
   * @return list of column names from table for which this provider is used
   */
  public List<String> getColumnNames() {
    return this.columns;
  }
}
