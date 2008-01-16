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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal.providers;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;



/**
 * @author nloulloud
 *
 */
public class QueueContentProvider implements IStructuredContentProvider {

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
   */
  public Object[] getElements( final Object inputElement ) {

    Object[] result = null;

    
    if (inputElement instanceof AllowedVirtualOrganizationsType){
      AllowedVirtualOrganizationsType allowedVOs = (AllowedVirtualOrganizationsType) inputElement;
      
      EList<String>list = allowedVOs.getVOName();
      result = list.toArray( new Object[ list.size() ] );
  
    }
    else if (inputElement instanceof Object[]) {     
      result = (Object[]) inputElement;
    }
    else if (inputElement instanceof Collection) {    
      result = ((Collection) inputElement).toArray();
    }
    else {      
     result = null;
    }
      
    
    return result;
  }
      
  

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  public void dispose() {
    // Empty Implementation
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  public void inputChanged( final Viewer viewer, 
                            final Object oldInput, 
                            final Object newInput ) {
    // Auto-generated method stub
  }
}
