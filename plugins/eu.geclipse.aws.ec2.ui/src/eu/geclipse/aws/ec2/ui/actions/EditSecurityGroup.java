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
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;

import eu.geclipse.aws.ec2.EC2SecurityGroup;
import eu.geclipse.aws.ec2.ui.wizards.SecurityGroupWizard;

/**
 * This {@link IActionDelegate} is used to edit an EC2 security group.
 * 
 * @author Moritz Post
 */
public class EditSecurityGroup extends AbstractSecurityGroupAction {

  public void run( final IAction action ) {
    if( action.isEnabled() ) {
      EC2SecurityGroup securityGroup = getSecurityGroupList().get( 0 );
      if( securityGroup != null ) {
        SecurityGroupWizard securityGroupWizard = new SecurityGroupWizard( securityGroup );

        WizardDialog wizardDialog = new WizardDialog( getWorkbenchPart().getSite()
                                                        .getShell(),
                                                      securityGroupWizard );
        wizardDialog.open();
      }
    }
  }

  @Override
  protected int getEnablementCount() {
    return AbstractSecurityGroupAction.ENABLE_FOR_ONE;
  }
}
