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

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpAssociateAddress;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * The Wizard to choose an Elastic IP to associate it with an ec2 instance.
 * 
 * @author Moritz Post
 */
public class AssociateElasticIPWizard extends Wizard {

  /** The {@link EC2Instance} for which to choose an elastic ip. */
  private EC2Instance ec2Instance;

  /** The {@link AWSVirtualOrganization} to provide the access credentials. */
  private AWSVirtualOrganization awsVo;

  /** The {@link WizardPage} to provide the UI form elements. */
  private SelectElasticIPWizardPage selectElasticIPWizardPage;

  /**
   * Create a new Wizard with the credentials contained within the awsVo. The
   * passed instance parameter defines the ec2 instance to which the an elastic
   * IP should be bound.
   * 
   * @param awsVo the source for access credentials
   * @param instance the entity to bind an elastic ip to
   */
  public AssociateElasticIPWizard( final AWSVirtualOrganization awsVo,
                                   final EC2Instance instance )
  {
    this.awsVo = awsVo;
    this.ec2Instance = instance;
    setNeedsProgressMonitor( true );
  }

  @Override
  public void addPages() {
    this.selectElasticIPWizardPage = new SelectElasticIPWizardPage( this.awsVo,
                                                                    this.ec2Instance );
    addPage( this.selectElasticIPWizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "AssociateElasticIPWizard.wizard_title" ); //$NON-NLS-1$
  }

  @Override
  public boolean performFinish() {
    if( this.selectElasticIPWizardPage.isPageComplete() ) {
      try {

        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            try {
              monitor.beginTask( MessageFormat.format( Messages.getString( "AssociateElasticIPWizard.monitor_title" ), //$NON-NLS-1$
                                                       AssociateElasticIPWizard.this.ec2Instance.getInstanceId(),
                                                       AssociateElasticIPWizard.this.selectElasticIPWizardPage.getSelectedElasticIP() ),
                                 2 );
              EC2Registry registry = EC2Registry.getRegistry();
              IEC2 ec2;
              try {
                ec2 = registry.getEC2( AssociateElasticIPWizard.this.awsVo );
              } catch( ProblemException problemEx ) {
                throw new InvocationTargetException( problemEx );
              }
              monitor.worked( 1 );
              EC2OpAssociateAddress op = new EC2OpAssociateAddress( ec2,
                                                                    AssociateElasticIPWizard.this.selectElasticIPWizardPage.getSelectedElasticIP(),
                                                                    AssociateElasticIPWizard.this.ec2Instance.getInstanceId() );

              new OperationExecuter().execOp( op );

              if( op.getException() != null ) {
                throw new InvocationTargetException( op.getException() );
              }
            } finally {
              monitor.done();
            }
          }
        } );
      } catch( Exception ex ) {
        Activator.log( "An problem occured while associating the Elastic IP", ex ); //$NON-NLS-1$
        // process any errors
        final EC2ProblemException exception = new EC2ProblemException( IEC2Problems.EC2_INTERACTION,
                                                                       ex.getCause()
                                                                         .getLocalizedMessage(),
                                                                       ex,
                                                                       Activator.PLUGIN_ID );

        Display display = PlatformUI.getWorkbench().getDisplay();
        display.asyncExec( new Runnable() {

          public void run() {
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
              .getActiveWorkbenchWindow();
            ProblemDialog.openProblem( workbenchWindow.getShell(),
                                       Messages.getString( "AssociateElasticIPWizard.problem_title" ), //$NON-NLS-1$
                                       Messages.getString( "AssociateElasticIPWizard.problem_description" ), //$NON-NLS-1$
                                       exception );
          }
        } );
        return false;
      }
      return true;
    }
    return false;
  }
}
