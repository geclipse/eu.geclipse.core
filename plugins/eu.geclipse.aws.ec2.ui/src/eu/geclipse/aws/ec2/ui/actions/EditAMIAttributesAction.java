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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;

import eu.geclipse.aws.ec2.EC2AMIImage;
import eu.geclipse.aws.ec2.ui.wizards.EditAMIAttributesWizard;

/**
 * This {@link IObjectActionDelegate} opens an {@link EditAMIAttributesWizard},
 * which allows to set the attribute values.
 * 
 * @author Moritz Post
 */
public class EditAMIAttributesAction extends AbstractAWSProjectAction {

  /** The list of selected AMIs. This list should contain only one element. */
  private List<EC2AMIImage> amiList;

  /**
   * This constructor initializes the objects fields.
   */
  public EditAMIAttributesAction() {
    this.amiList = new ArrayList<EC2AMIImage>();
  }

  @Override
  public void run( final IAction action ) {
    if( getAwsVo() != null && this.amiList.size() == 1 ) {
      EditAMIAttributesWizard wizard = new EditAMIAttributesWizard( getAwsVo(),
                                                                    this.amiList.get( 0 ) );

      WizardDialog wizardDialog = new WizardDialog( getWorkbenchPart().getSite()
                                                      .getShell(),
                                                    wizard );
      wizardDialog.open();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.amiList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2AMIImage ) {
          EC2AMIImage ami = ( EC2AMIImage )element;
          this.amiList.add( ami );
        }
      }
    }
    if( this.amiList.size() == 1 ) {
      enable = true;
      extractAWSVoFromGridElement( this.amiList.get( 0 ) );
    }
    action.setEnabled( enable );

  }

}
