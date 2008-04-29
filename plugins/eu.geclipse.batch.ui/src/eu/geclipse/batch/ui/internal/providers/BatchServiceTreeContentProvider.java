/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.batch.BatchServiceManager;
import eu.geclipse.batch.IBatchService;


/**
 * @author nloulloud
 *
 *
 * The Batch Service Selection Tree Content Provider.
 */
public class BatchServiceTreeContentProvider
  implements IStructuredContentProvider, ITreeContentProvider
{

  private static Object[] EMPTY_ARRAY = new Object[0];
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
   */
  public Object[] getElements( final Object inputElement ) {
    return getChildren( inputElement );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  public void dispose() {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput ) {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public Object[] getChildren( final Object parentElement ) {
    
    Object[] ret = null;    
    List<IBatchService> servicesList = null;
    
    if (parentElement instanceof ArrayList) {
      List<String> registeredServices = new ArrayList<String>();
      registeredServices = (ArrayList<String>)parentElement;

      String[] serviceArray
      = registeredServices.toArray( new String[ registeredServices.size() ] );
      ret = serviceArray;

    }
    else if (parentElement instanceof String) {    
      servicesList = BatchServiceManager.getManager().getServices((String) parentElement);
      Object[] serviceArray
      = servicesList.toArray( new Object[ servicesList.size() ] );
      ret = serviceArray;   
    }
    else{
      ret = EMPTY_ARRAY;
    }
    
    return ret;  
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
   */
  public Object getParent( final Object element ) {
    Object ret = null;
    if(element instanceof IBatchService) {
      ret =  ((IBatchService)element).getDescription().getServiceTypeName();
    }
    return ret;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
   */
  public boolean hasChildren( final Object element ) {
    boolean ret = false;
    if (getChildren( element ) != EMPTY_ARRAY ){
      ret = true;
    }
  return ret;
  }
}
