/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;

import eu.geclipse.aws.ec2.ui.wizards.CreateSecurityGroupWizard;

/**
 * This Action creates opens a wizard to create a new security group.
 * 
 * @author Moritz Post
 */
public class CreateSecurityGroupAction extends AbstractAWSProjectAction {

  @Override
  public void run( final IAction action ) {
    if( getAwsVo() != null ) {
      CreateSecurityGroupWizard createSecurityGroupWizard = new CreateSecurityGroupWizard( getAwsVo() );

      WizardDialog wizardDialog = new WizardDialog( getWorkbenchPart().getSite()
                                                      .getShell(),
                                                    createSecurityGroupWizard );

      wizardDialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    extractAWSVoFromCategory( selection );
  }

}
