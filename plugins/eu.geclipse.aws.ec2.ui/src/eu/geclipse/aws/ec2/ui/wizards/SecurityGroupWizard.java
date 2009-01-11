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

package eu.geclipse.aws.ec2.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.aws.ec2.EC2SecurityGroup;

/**
 * This {@link Wizard} lets you manage the security permissions of an EC2
 * security group.
 * 
 * @author Moritz Post
 */
public class SecurityGroupWizard extends Wizard {

  /** The security group to edit. */
  private EC2SecurityGroup securityGroup;

  /**
   * Create a new Wizard, editing the passed security group.
   * 
   * @param securityGroup the group to modify
   * @param awsAccessId the access id to identify the current user
   */
  public SecurityGroupWizard( final EC2SecurityGroup securityGroup ) {
    this.securityGroup = securityGroup;
    setNeedsProgressMonitor( true );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "SecurityGroupWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public void addPages() {
    addPage( new SecurityGroupWizardPage( this.securityGroup ) );
  }

  @Override
  public boolean performFinish() {
    return true;
  }

}
