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

package eu.geclipse.aws.s3.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;

import eu.geclipse.aws.s3.ui.wizards.CreateBucketWizard;

/**
 * This action open the {@link CreateBucketWizard} to create a new Bucket on S3.
 * 
 * @author Moritz Post
 */
public class CreateBucketAction extends AbstractAWSProjectAction {

  /**
   * Default constructor.
   */
  public CreateBucketAction() {
    // nothing to do here
  }

  @Override
  public void run( final IAction action ) {
    if( getAwsVo() != null ) {
      CreateBucketWizard createbucketWizard = new CreateBucketWizard( getAwsVo() );

      WizardDialog wizardDialog = new WizardDialog( getWorkbenchPart().getSite()
                                                      .getShell(),
                                                    createbucketWizard );

      wizardDialog.open();
    }

  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    extractAWSVoFromCategory( selection );
  }

}
