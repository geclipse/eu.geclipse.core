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
 *     Mariusz Wojtysiak - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;


public class GridUIAdapterFactory implements IAdapterFactory {

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Object adaptableObject, final Class adapterType ) {
    Object result = null;
    if(adapterType.isAssignableFrom( IWorkbenchAdapter.class)){
      result = new GridJobAdapter();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList() {
    Class[] table = new Class[1];
    table[0]= IWorkbenchAdapter.class;
    return table;
  }
}
