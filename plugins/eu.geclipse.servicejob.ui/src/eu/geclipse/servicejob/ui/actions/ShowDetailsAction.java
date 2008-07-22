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
package eu.geclipse.servicejob.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.servicejob.ui.Activator;

/**
 * Action for opening Operator's Job details view from Operator's Job's context
 * menu.
 * 
 * @author Katarzyna Bylec
 */
public class ShowDetailsAction implements IObjectActionDelegate {

  /**
   * Simple constructor.
   */
  public ShowDetailsAction() {
    // do nothing
  }

  public void setActivePart( final IAction action,
                             final IWorkbenchPart targetPart )
  {
    // do nothing
  }

  public void run( final IAction action ) {
    try {
      PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow()
        .getActivePage()
        .showView( "eu.geclipse.servicejob.views.testDetailsView", //$NON-NLS-1$
                   null,
                   IWorkbenchPage.VIEW_ACTIVATE );
    } catch( PartInitException e ) {
      Activator.logException( e );
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    // do nothing
  }
}
