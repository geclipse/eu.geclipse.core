/*******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 *     - David Johnson
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;

/**
 * @author athandava
 *
 */
public class GetJobDescriptionFromFileAction implements IObjectActionDelegate {

  /**
   * The WorkflowJobEditPart that has been selected.
   */
  protected WorkflowJobEditPart mySelectedElement;
  /**
   * 
   */
  private Shell myShell;
  String jobDescriptionInJSDL = null;
  String fileName = null;

  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    this.myShell = targetPart.getSite().getShell();
  }

  /**
   * Fires up a GridFileDialog and fetches the contents of a user-chosen JSDL
   * file.
   */
  public void run( IAction action ) {
    InputStream inStream = null;
    IStatus status = Status.OK_STATUS;
    GridFileDialog dialog = new GridFileDialog( this.myShell,
                                                GridFileDialog.STYLE_ALLOW_ONLY_LOCAL
                                                    | GridFileDialog.STYLE_ALLOW_ONLY_EXISTING );
    dialog.addFileTypeFilter( "jsdl",  //$NON-NLS-1$
                              Messages.getString( "GetJobDescriptionFromFileAction.filterTypeDesc" ) ); //$NON-NLS-1$
    
    if( dialog.open() == Window.OK ) {
      IFileStore[] result = dialog.getSelectedFileStores();
      if( ( result != null ) && ( result.length > 0 ) ) {
        try {
          System.out.println(result[0].toURI() + " ADDING REAL URI");
          this.fileName= result[0].toURI().toString();          
          
          inStream = result[ 0 ].openInputStream( EFS.NONE, null );
        } catch( CoreException cE ) {
          status = new Status( IStatus.ERROR,
                               WorkflowDiagramEditorPlugin.ID,
                               IStatus.OK,
                               Messages.getString( "GetJobDescriptionFromFileAction.errorOpeningInputStream" ), //$NON-NLS-1$
                               cE );
        }
        if( status.isOK() ) {
          // Read in contents of the JSDL file
          BufferedReader in = new BufferedReader( new InputStreamReader( inStream ) );
          StringBuffer buffer = new StringBuffer();
          String line = null;
          try {
            while( ( line = in.readLine() ) != null ) {
              buffer.append( line );
            }
          } catch( IOException iOE ) {
            status = new Status( IStatus.ERROR,
                                 WorkflowDiagramEditorPlugin.ID,
                                 IStatus.OK,
                                 Messages.getString( "GetJobDescriptionFromFileAction.errorReadingFromFile" ), //$NON-NLS-1$
                                 iOE );
          }
          this.jobDescriptionInJSDL = buffer.toString();
        }
        try {
          inStream.close();
        } catch( IOException iOE ) {
          status = new Status( IStatus.ERROR,
                               WorkflowDiagramEditorPlugin.ID,
                               IStatus.OK,
                               Messages.getString( "GetJobDescriptionFromFileAction.errorClosingInputStream" ), //$NON-NLS-1$
                               iOE );
        }
      }
    }
    updateWorkflowJobDescription();
  }

  /**
   * This method updates the JobDescription field in the WorkflowJob.
   * @return true or false
   */
  public boolean updateWorkflowJobDescription() {
    IStatus status = Status.OK_STATUS;
    TransactionalEditingDomain domain = this.mySelectedElement.getEditingDomain();
    ResourceSet resourceSet = domain.getResourceSet();
    AbstractTransactionalCommand command = new AbstractTransactionalCommand( domain,
                                                                             Messages.getString( "GetJobDescriptionFromFileAction.updatingJobDescription" ), //$NON-NLS-1$
                                                                             null )
    {

      @Override
      protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                                   IAdaptable info )
      {
        IWorkflowJob job = ( IWorkflowJob )GetJobDescriptionFromFileAction.this.mySelectedElement.resolveSemanticElement();
        job.setJobDescription( GetJobDescriptionFromFileAction.this.jobDescriptionInJSDL );
        job.setJobDescriptionFileName( GetJobDescriptionFromFileAction.this.fileName );
        return CommandResult.newOKCommandResult();
      }
    };
    try {
      OperationHistoryFactory.getOperationHistory()
        .execute( command, new NullProgressMonitor(), null );
    } catch( ExecutionException eE ) {
      status = new Status( IStatus.ERROR,
                           WorkflowDiagramEditorPlugin.ID,
                           IStatus.OK,
                           Messages.getString( "GetJobDescriptionFromFileAction.errorUpdatingJobDescription" ), //$NON-NLS-1$
                           eE );
      // WorkflowDiagramEditorPlugin.getInstance()
      // .logError(
      // Messages.getString("GetJobDescriptionFromFileAction.errorUpdatingJobDescription"),
      // e ); //$NON-NLS-1$
    }
    return true;
  }

  public void selectionChanged( IAction action, ISelection selection ) {
    this.mySelectedElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      if( structuredSelection.size() == 1
          && structuredSelection.getFirstElement() instanceof WorkflowJobEditPart )
      {
        this.mySelectedElement = ( WorkflowJobEditPart )structuredSelection.getFirstElement();
      }
    }
    action.setEnabled( isEnabled() );
  }

  private boolean isEnabled() {
    return this.mySelectedElement != null;
  }
}
