/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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
 *    Jie Tao - initial API and implementation
 *****************************************************************************/
 
package eu.geclipse.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.ui.wizards.deployment.ValidateWizard;


/**action for validating the installed software
 * @author tao-j
 *
 */
public class ValidateAction extends SelectionListenerAction {

  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;

  
  /**the deployment action
   * @param site
   */
  public ValidateAction( final IWorkbenchSite site ) {
    super( Messages.getString( "ValidateAction.action_name" ) ); //$NON-NLS-1$
    this.setEnabled( false );
    this.site = site;
  }

  @Override
  public void run() {
    //Shell shell = this.site.getWorkbenchWindow().getShell();
    Shell shell = this.site.getShell();
    ValidateWizard wizard = new ValidateWizard( this.getStructuredSelection() );
    WizardDialog dialog = new WizardDialog( shell, wizard );
    dialog.setBlockOnOpen( false );
    dialog.open();
  }

  @Override
  protected boolean updateSelection( final IStructuredSelection structuredSelection ) {
    this.setEnabled( true );
    boolean enabled = super.updateSelection( structuredSelection );
    boolean isGridApplicationFlag = false;
    
 
    Iterator< ? > iter = structuredSelection.iterator();
    while ( iter.hasNext() && enabled ) {
      Object element = iter.next();
      if( element instanceof IGridApplication) {
        String tag = ((IGridApplication) element).getName();
        if (tag.contains( "to-be-validated" ) || tag.contains( "aborted-validate" )) //$NON-NLS-1$ //$NON-NLS-2$
        isGridApplicationFlag = true;
      }
    }
      
    return enabled && isGridApplicationFlag;
  }

}
