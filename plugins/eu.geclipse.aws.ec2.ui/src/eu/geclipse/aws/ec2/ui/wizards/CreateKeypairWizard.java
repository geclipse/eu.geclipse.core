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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.xerox.amazonws.ec2.KeyPairInfo;

import eu.geclipse.aws.ec2.EC2ProblemException;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.IEC2Problems;
import eu.geclipse.aws.ec2.op.EC2OpCreateKeypair;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This {@link Wizard} allows to create a new keypair within the account denoted
 * by the provided aws access id contained in the {@link AWSVirtualOrganization}
 * .
 * 
 * @author Moritz Post
 */
public class CreateKeypairWizard extends Wizard {

  /**
   * The {@link AWSVirtualOrganization} holding the aws access id to identify
   * the current user.
   */
  private AWSVirtualOrganization awsVo;

  /** The wizard page providing the form elements. */
  private CreateKeypairWizardPage wizardPage;

  /**
   * Creates a new {@link WizardPage} with the aws access id in the aws vo.
   * 
   * @param awsVo the container of the aws access id
   */
  public CreateKeypairWizard( final AWSVirtualOrganization awsVo ) {
    this.awsVo = awsVo;
    setNeedsProgressMonitor( true );
    setHelpAvailable( false );
  }

  @Override
  public void addPages() {
    this.wizardPage = new CreateKeypairWizardPage( this.awsVo );
    addPage( this.wizardPage );
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString( "CreateKeypairWizard.wizard_title" ); //$NON-NLS-1$
  }

  /**
   * Creates the new security group.
   */
  @Override
  public boolean performFinish() {
    if( this.wizardPage.isPageComplete() ) {
      try {

        getContainer().run( false, false, new IRunnableWithProgress() {

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            final String keypairName = CreateKeypairWizard.this.wizardPage.getKeypairName();
            monitor.beginTask( Messages.getString( "CreateKeypairWizard.task_title" ) + keypairName, IProgressMonitor.UNKNOWN ); //$NON-NLS-1$

            EC2OpCreateKeypair op = createKeypairOnEC2( keypairName );

            if( op.getException() == null ) {
              KeyPairInfo keypairInfo = op.getResult();
              String keyMaterial = keypairInfo.getKeyMaterial();

              writeKeymaterialToDisc( keyMaterial );
            } else {
              throw new InvocationTargetException( op.getException() );
            }
            monitor.done();
          }

        } );
      } catch( Exception ex ) {
        Activator.log( "A problem occured while creating a keypair", ex ); //$NON-NLS-1$
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
                                       Messages.getString( "CreateKeypairWizard.error_ec2_title" ), //$NON-NLS-1$
                                       Messages.getString( "CreateKeypairWizard.error_ec2_description" ) //$NON-NLS-1$
                                           + CreateKeypairWizard.this.wizardPage.getKeypairName(),
                                       exception );
          }
        } );
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Writes the given keymaterial to the path specified in the wizard page.
   * 
   * @param keyMaterial the key hash to write
   * @throws InvocationTargetException the possible target exception
   */
  private void writeKeymaterialToDisc( final String keyMaterial )
    throws InvocationTargetException
  {
    FileWriter fileWriter = null;
    try {
      File keyFile = new File( CreateKeypairWizard.this.wizardPage.getKeypairPath() );
      if( !keyFile.exists() ) {
        File parent = keyFile.getParentFile();
        if( parent != null && !parent.exists() ) {
          parent.mkdirs();
        }
        keyFile.createNewFile();
      }

      fileWriter = new FileWriter( keyFile );
      fileWriter.write( keyMaterial );

    } catch( IOException ioEx ) {
      Activator.log( "Could not create keypair file", ioEx ); //$NON-NLS-1$
      throw new InvocationTargetException( ioEx,
                                           "Could not create keypair file" ); //$NON-NLS-1$
    } finally {
      if( fileWriter != null ) {
        try {
          fileWriter.close();
        } catch( IOException ioEx ) {
          Activator.log( "Could not close keypair file", ioEx ); //$NON-NLS-1$
          throw new InvocationTargetException( ioEx,
                                               "Could not close keypair file" ); //$NON-NLS-1$
        }
      }
    }
  }

  /**
   * @param keypairName
   * @return
   */
  private EC2OpCreateKeypair createKeypairOnEC2( final String keypairName ) {
    EC2Registry registry = EC2Registry.getRegistry();
    IEC2 ec2 = null;
    try {
      ec2 = registry.getEC2( CreateKeypairWizard.this.awsVo );
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not obtain the aws vo properties", problemEx ); //$NON-NLS-1$
    }
    EC2OpCreateKeypair op = new EC2OpCreateKeypair( ec2, keypairName );

    new OperationExecuter().execOp( op );
    return op;
  }
}
