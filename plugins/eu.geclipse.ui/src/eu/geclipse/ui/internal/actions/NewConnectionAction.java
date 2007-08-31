/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.net.URL;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.ConnectionWizard;

/**
 * Action for creating new connections using the {@link ConnectionWizard}.
 */
public class NewConnectionAction
    extends BaseSelectionListenerAction {
  
  /**
   * The workbench window for which to create this action.
   */
  private IWorkbenchWindow workbenchWindow;
  
  /**
   * Construct this action for the specified workbench window.
   * 
   * @param workbenchWindow The {@link IWorkbenchWindow} for which to
   * create this action.
   */
  public NewConnectionAction( final IWorkbenchWindow workbenchWindow ) {
    super( Messages.getString("NewConnectionAction.new_connection_action_text") ); //$NON-NLS-1$
    this.workbenchWindow = workbenchWindow;
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/etool16/newconn_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {

    IWorkbench workbench = this.workbenchWindow.getWorkbench();
    IStructuredSelection selection = getStructuredSelection();
    Shell shell = this.workbenchWindow.getShell();
    
    ConnectionWizard wizard = new ConnectionWizard( selection.isEmpty() );
    wizard.init( workbench, selection );
    WizardDialog dialog = new WizardDialog( shell, wizard );
    
    dialog.open();
    
  }
  
}
