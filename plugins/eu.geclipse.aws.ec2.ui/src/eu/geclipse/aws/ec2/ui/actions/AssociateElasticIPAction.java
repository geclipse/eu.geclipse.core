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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.ui.wizards.AssociateElasticIPWizard;

/**
 * The {@link IObjectActionDelegate} starts the {@link Job} to associate an EC2
 * instance with an elastic IP.
 * 
 * @author Moritz Post
 */
public class AssociateElasticIPAction extends AbstractAWSProjectAction {

  /** The list of selected {@link EC2Instance}s. */
  private List<EC2Instance> instanceList;

  /**
   * Creates a new instance of this associating action.
   */
  public AssociateElasticIPAction() {
    this.instanceList = new ArrayList<EC2Instance>();
  }

  @Override
  public void run( final IAction action ) {
    EC2Instance firstInstance = null;
    if( this.instanceList.size() > 0 ) {
      firstInstance = this.instanceList.get( 0 );

      extractAWSVoFromGridElement( firstInstance );

      AssociateElasticIPWizard associateElasticIPWizard = new AssociateElasticIPWizard( getAwsVo(),
                                                                                        this.instanceList.get( 0 ) );

      WizardDialog wizardDialog = new WizardDialog( getWorkbenchPart().getSite()
                                                      .getShell(),
                                                    associateElasticIPWizard );

      wizardDialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.instanceList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2Instance ) {
          EC2Instance ec2Instance = ( EC2Instance )element;
          this.instanceList.add( ec2Instance );
        }
      }
    }
    if( this.instanceList.size() > 0 ) {
      enable = true;
    }
    action.setEnabled( enable );
  }

}
