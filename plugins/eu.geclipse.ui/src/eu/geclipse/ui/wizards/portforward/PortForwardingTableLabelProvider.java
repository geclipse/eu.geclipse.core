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

package eu.geclipse.ui.wizards.portforward;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

class PortForwardingTableLabelProvider implements ITableLabelProvider {
  public Image getColumnImage( final Object element, final int columnIndex ) {
    return null;
  }

  public String getColumnText( final Object element, final int columnIndex ) {
    IForwardTableEntry forward = (IForwardTableEntry) element;
    String result = "undefined"; //$NON-NLS-1$
    switch( columnIndex ) {
      case 0:
        result = forward.getType().toString();
        break;
      case 1:
        result = Integer.valueOf( forward.getBindPort() ).toString();
        break;
      case 2:
        result = forward.getHostname();
        break;
      case 3:
        result = Integer.valueOf( forward.getPort() ).toString();
        break;
      default:
        break;
    }
    return result;
  }

  public void addListener( final ILabelProviderListener listener ) {
    // TODO Auto-generated method stub
  }

  public boolean isLabelProperty( final Object element, final String property ) {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    // TODO Auto-generated method stub
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }
}
