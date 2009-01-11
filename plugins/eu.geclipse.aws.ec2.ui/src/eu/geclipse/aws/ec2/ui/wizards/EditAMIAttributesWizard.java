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
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.vo.AWSVirtualOrganization;

/**
 * This {@link Wizard} allows to edit the attributes of an AMI. Among these
 * attributes are the launch permissions and the product code configuration.
 * 
 * @author Moritz Post
 */
public class EditAMIAttributesWizard extends Wizard {

  /** The aws VO to hold the access id. */
  private AWSVirtualOrganization awsVo;

  /** The AMI which attributes should be edited. */
  private EC2AMIImage ami;

  /** The {@link WizardPage} holding the form elements. */
  private EditAMIAttributesWizardPage editAMIAttributesWizardPage;

  /**
   * Create a new Wizard with the vo and data source.
   * 
   * @param awsVo the aws VO holding the access id
   * @param ami the AMI to edit
   */
  public EditAMIAttributesWizard( final AWSVirtualOrganization awsVo,
                                  final EC2AMIImage ami )
  {
    this.awsVo = awsVo;
    this.ami = ami;
    setNeedsProgressMonitor( true );
  }

  @Override
  public void addPages() {
    this.editAMIAttributesWizardPage = new EditAMIAttributesWizardPage( this.awsVo,
                                                                        this.ami );

    addPage( this.editAMIAttributesWizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString("EditAMIAttributesWizard.wizard_title"); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    return true;
  }

}
