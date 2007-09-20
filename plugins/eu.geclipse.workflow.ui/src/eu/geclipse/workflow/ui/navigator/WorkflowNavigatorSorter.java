/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.navigator;

import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @generated
 */
public class WorkflowNavigatorSorter extends ViewerSorter {

  /**
   * @generated
   */
  private static final int GROUP_CATEGORY = 3003;

  /**
   * @generated
   */
  @Override
  public int category( Object element ) {
    if( element instanceof WorkflowNavigatorItem ) {
      WorkflowNavigatorItem item = ( WorkflowNavigatorItem )element;
      return WorkflowVisualIDRegistry.getVisualID( item.getView() );
    }
    return GROUP_CATEGORY;
  }
}
