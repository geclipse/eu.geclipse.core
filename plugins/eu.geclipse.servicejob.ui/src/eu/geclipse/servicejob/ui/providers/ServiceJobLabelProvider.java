/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium 
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
package eu.geclipse.servicejob.ui.providers;

import java.text.DateFormat;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.core.model.IServiceJob;

/**
 * Label provider for Operator's Jobs view.
 */
public class ServiceJobLabelProvider implements ITableLabelProvider {

  private static final String DEFAULT_ERROR = Messages.getString("TestsLabelProvider.default_error"); //$NON-NLS-1$
  
  private static final String DEFAULT_N_A = Messages.getString("TestsLabelProvider.default_n_a"); //$NON-NLS-1$
  
  public Image getColumnImage( final Object element, final int columnIndex ) {
    Image result = null;
    return result;
  }

  public String getColumnText( final Object element, final int columnIndex ) {
    String result = DEFAULT_ERROR;
    if( element instanceof IServiceJob ) {
      IServiceJob test = ( IServiceJob )element;
      switch( columnIndex ) {
        case 0:
          result = test.getName();
        break;
        case 1:
          result = test.getProject().getName();
        break;
        case 2:
          result = test.getSummary().toString();
        break;
        case 3:
          if( test.getLastUpdate() != null ) {
            result = DateFormat.getDateTimeInstance()
              .format( test.getLastUpdate() );
          } else {
            result = DEFAULT_N_A;
          }
        break;
        case 4:
          result = test.getTestDescription();
        break;
      }
    }
    return result;
  }

  public void addListener( final ILabelProviderListener listener ) {
    // TODO Auto-generated method stub
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public boolean isLabelProperty( final Object element, final String property )
  {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    // TODO Auto-generated method stub
  }
}
