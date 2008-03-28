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
package eu.geclipse.batch.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.batch.BatchQueueDescription;
import eu.geclipse.batch.ui.wizards.BatchServiceSelectionWizard;
import eu.geclipse.core.model.IGridBatchQueueDescription;


/**
 * @author nloulloud
 *
 */
public class ApplyQueueConfigurationAction implements IObjectActionDelegate {

  private IWorkbenchPart workbench;  
  private List<IGridBatchQueueDescription> queueDescrList;
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
   */
  public void setActivePart( final IAction action, final IWorkbenchPart targetPart ) {
    this.workbench = targetPart;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
   */
  public void run( final IAction action ) {

    BatchServiceSelectionWizard wizard = new BatchServiceSelectionWizard(this.queueDescrList);
    WizardDialog dialog = new WizardDialog( this.workbench.getSite().getShell(), wizard );
    dialog.open();
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
   */
  public void selectionChanged( final IAction action, final ISelection selection ) {
    
    action.setEnabled( true );
    this.queueDescrList = new ArrayList<IGridBatchQueueDescription>();
    if( selection instanceof IStructuredSelection ) {
      
      IStructuredSelection sselection = ( IStructuredSelection )selection;  
      Iterator<?> it = sselection.iterator();
      
      while ( it.hasNext() ) {
        
        Object element = it.next();

        if( element instanceof BatchQueueDescription ) {
          this.queueDescrList.add( (BatchQueueDescription ) element );
        
        }
        
      }
      
    }
    
  } //end void selectionChanged()
  
  
} // end ApplyQueueConfigurationActionClass
