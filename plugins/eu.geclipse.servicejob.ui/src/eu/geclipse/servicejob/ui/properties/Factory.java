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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import eu.geclipse.core.model.IServiceJob;

/**
 * Adapter factory implementation for showing properties of Operator's Jobs.
 */
public class Factory implements IAdapterFactory {

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Object adaptableObject,
                            final Class adapterType )
  {
    Object result = null;
    if( adaptableObject instanceof IServiceJob ) {
      result = new ServiceJobResultProperties( ( IServiceJob )adaptableObject );
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList() {
    return new Class[]{
      IPropertySource.class
    };
  }
}
