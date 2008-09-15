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

import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class IterationsLProvider implements ITableLabelProvider {

  private Map<Integer, String> columnsMap;

  
  public void setColumnIndexMap( final Map<Integer, String> columnsMap ) {
    this.columnsMap = columnsMap;
  }

  public Image getColumnImage( final Object element, final int columnIndex ) {
    return null;
  }

  public String getColumnText( final Object element, final int columnIndex ) {
    String result = ""; //$NON-NLS-1$
    String columnName = this.columnsMap.get( new Integer( columnIndex ) );
    if( element instanceof Properties[] ) {
      Properties[] array = ( Properties[] )element;
      for( Properties currentProperty : array ) {
        if( currentProperty.getProperty( columnName ) != null ) {
          result = currentProperty.getProperty( columnName );
          break;
        }
      }
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
}
