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
package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.IGridBatchQueueDescription;


/**
 * @author nloulloud
 *
 * Action dedicated for Applying Queue Configuration from a QDL to a specific site.
 */
public class ApplyQueueConfigurationAction extends SelectionListenerAction {
  
  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  
  private ArrayList<IGridBatchQueueDescription> batchQueueDescList;

  protected ApplyQueueConfigurationAction( final IWorkbenchSite site ) {
    super( Messages.getString( "ApplyQueueConfiguration.title" ) ); //$NON-NLS-1$
    this.site = site;
    // TODO Auto-generated constructor stub
  }
  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.batchQueueDescList = new ArrayList< IGridBatchQueueDescription >();
    boolean enabled = super.updateSelection( selection );
    Iterator< ? > iter = selection.iterator();
    while( iter.hasNext() && enabled ) {
      Object element = iter.next();
      boolean isBatchDescriptionFile = isBatchQueueDescription( element );
      enabled &= isBatchDescriptionFile;
      if( isBatchDescriptionFile ) {
        this.batchQueueDescList.add( ( IGridBatchQueueDescription )element );
      }
    }
    return enabled;
  }
  
  
  protected boolean isBatchQueueDescription( final Object element ) {
    return element instanceof IGridBatchQueueDescription;
  }
  
    
  
  @Override
  public void run() {
      System.out.println("RUNNED");
  }
  
  
} //End ApplyQueueConfigurationAction() class
