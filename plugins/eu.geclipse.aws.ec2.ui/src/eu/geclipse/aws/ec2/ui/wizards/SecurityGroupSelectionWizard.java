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

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

/**
 * This wizard allows to add and remove security groups from an ec2 launch
 * configuration.
 * 
 * @author Moritz Post
 */
public class SecurityGroupSelectionWizard extends Wizard {

  /** The page displaying the from elements. */
  private SecurityGroupSelectionWizardPage securityGroupSelectionWizardPage;

  /**
   * Create a new Wizard, instantiating the form elements.
   * 
   * @param awsAccessId
   */
  public SecurityGroupSelectionWizard( final String awsAccessId ) {
    setNeedsProgressMonitor( true );
    this.securityGroupSelectionWizardPage = new SecurityGroupSelectionWizardPage( awsAccessId );
  }

  @Override
  public void addPages() {
    addPage( this.securityGroupSelectionWizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "SecurityGroupSelectionWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    return true;
  }

  /**
   * Set the security groups to be selected by default.
   * 
   * @param securityGroups the security groups to set
   */
  public void setSecurityGroups( final List<String> securityGroups ) {
    this.securityGroupSelectionWizardPage.initialiseSelectedGroups( securityGroups );
  }

  /**
   * Get the security groups which have been selected in the wizard
   * 
   * @return the selected security groups
   */
  public List<String> getSecurityGroups() {
    return this.securityGroupSelectionWizardPage.getSelectedSecurityGroups();
  }

}
