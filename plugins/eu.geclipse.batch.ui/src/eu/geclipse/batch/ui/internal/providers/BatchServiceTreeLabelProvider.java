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

import org.eclipse.jface.viewers.LabelProvider;

import eu.geclipse.batch.IBatchService;


/**
 * @author nloulloud
 * 
 * The Batch Service Selection  Tree Label Provider.
 *
 */
public class BatchServiceTreeLabelProvider extends LabelProvider {
  
  @Override
  public String getText(final Object element) {
    
    String ret = null;
    
    if (element instanceof String) {
      ret = (String) element;
    } 
    else if (element instanceof IBatchService) { 
      ret = ((IBatchService) element).getName();
    }
    else {
        throw unknownElement(element);
    }
    return ret;
    
  }
    
    protected RuntimeException unknownElement(final Object element) {
      return new RuntimeException("Unknown type of element in tree of type " + element.getClass().getName()); //$NON-NLS-1$
  }
  
}
