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
 *      - Szymon Mueller
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.core.model.IServiceJobManager;

/**
 * Content provider for Operator's Jobs view.
 */
public class ServiceJobContentProvider implements ITreeContentProvider {

  public Object[] getChildren( final Object parentElement ) {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getParent( final Object element ) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean hasChildren( final Object element ) {
    // TODO Auto-generated method stub
    return false;
  }

  public Object[] getElements( final Object inputElement ) {
    Object[] serviceJobs = null;
    if( inputElement instanceof IServiceJobManager ) {
      serviceJobs = ( ( IServiceJobManager )inputElement ).getServiceJobs().toArray();
    }
    return serviceJobs != null
                        ? serviceJobs
                        : new Object[ 0 ];
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput )
  {
    // TODO Auto-generated method stub
  }
}
