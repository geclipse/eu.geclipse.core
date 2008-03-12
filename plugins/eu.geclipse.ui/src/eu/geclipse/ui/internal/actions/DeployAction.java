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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.wizards.deployment.DeploymentWizard;

/**
 * @author Yifan Zhou
 */
public class DeployAction extends SelectionListenerAction {

  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  
  public DeployAction( final IWorkbenchSite site ) {
    super( Messages.getString( "DeployAction.deploy_action_name" ) ); //$NON-NLS-1$
    this.setEnabled( false );
    this.site = site;
  }

  @Override
  public void run() {
    Shell shell = this.site.getWorkbenchWindow().getShell();
    DeploymentWizard wizard = new DeploymentWizard( this.getStructuredSelection() );
    WizardDialog dialog = new WizardDialog( shell, wizard );
    dialog.open();
  }

  @Override
  protected boolean updateSelection( final IStructuredSelection structuredSelection ) {
    this.setEnabled( true );
    boolean enabled = super.updateSelection( structuredSelection );
    boolean isGridProjectFlag = false;
    if ( structuredSelection.size() == 1 ) {
      Object object = structuredSelection.getFirstElement();
      if ( isGridProject( object ) ) {
        isGridProjectFlag = ( ( IGridProject ) object ).isGridProject();
      }
    }
    return enabled & isGridProjectFlag;
  }

  private boolean isGridProject( final Object element ) {
    return element instanceof eu.geclipse.core.model.IGridProject;
  }

}