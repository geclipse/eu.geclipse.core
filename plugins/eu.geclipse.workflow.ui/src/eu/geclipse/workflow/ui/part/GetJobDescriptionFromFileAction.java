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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.commands.CopyJobDescToWorkflowCommand;
import eu.geclipse.workflow.ui.edit.commands.UpdateJobPortsCommand;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;

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
  private IFileStore wfRootFileStore = null;
  private String[] dirs = null;

  protected IFile jsdlTarget;

  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    this.myShell = targetPart.getSite().getShell();
  }

  /**
   * Fires up a GridFileDialog and fetches the contents of a user-chosen JSDL
   * file.
   */
  public void run( IAction action ) {
    FileDialog dialog = new FileDialog( this.myShell, SWT.OPEN );
	String[] exts = { "*.jsdl" }; //$NON-NLS-1$
	dialog.setFilterExtensions(exts);
	
    // this bit find the root directory of the workflow
    TransactionalEditingDomain domain = this.mySelectedElement.getEditingDomain();
    ResourceSet resourceSet = domain.getResourceSet();
    Resource res = resourceSet.getResources().get( 0 );
    URI wfRootUri = res.getURI();
    String wfRootPath = wfRootUri.path();
    this.dirs = wfRootPath.split( "/" ); //$NON-NLS-1$
    String projectName = this.dirs[2];
    this.wfRootFileStore = GridModel.getRoot().getFileStore().getChild( projectName );
	
	dialog.setFilterPath( this.wfRootFileStore.toString() );

    if ( dialog.open() != null ) { 
      String result = dialog.getFileName();
      if( ( result != null ) && ( result.length() > 0 ) ) {
    	  String filePath = dialog.getFilterPath() + "/" + result; //$NON-NLS-1$
    	  //filePath = filePath.replace(' ', '+');
    	  java.net.URI filePathUri = null;
    	  filePathUri = URIUtil.toURI(filePath);
          IFile jsdlFile = ResourcesPlugin.getWorkspace()
          .getRoot()
          .findFilesForLocationURI( filePathUri )[ 0 ];
          IWorkflowJob selectedJob = (IWorkflowJob)this.mySelectedElement.resolveSemanticElement();
          if (!(selectedJob.getName()==null && selectedJob.getJobDescription()==null)) {
            MessageDialog confirmDialog = new MessageDialog( null,
                 Messages.getString("WorkflowJobDragDropEditPolicy_confirmationTitle"), //$NON-NLS-1$
                 null,
                 Messages.getString( "WorkflowJobDragDropEditPolicy_userPrompt" ), //$NON-NLS-1$
                 true ? MessageDialog.QUESTION : MessageDialog.WARNING ,
                 new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL },
                 0 );
            int confirmResult = confirmDialog.open();
            if (confirmResult==0) {
          	JSDLJobDescription jsdl = new JSDLJobDescription(jsdlFile); 
          	  AbstractTransactionalCommand copyCommand = new CopyJobDescToWorkflowCommand(this.mySelectedElement.resolveSemanticElement(), jsdl);
              AbstractTransactionalCommand updatePortsCommand = new UpdateJobPortsCommand(GetJobDescriptionFromFileAction.this.mySelectedElement, jsdl);  	    
              try {
                OperationHistoryFactory.getOperationHistory()
                .execute( copyCommand, new NullProgressMonitor(), null );  	    
                OperationHistoryFactory.getOperationHistory()
                .execute( updatePortsCommand, new NullProgressMonitor(), null );
        	    } catch( ExecutionException eE ) {
        	      eE.printStackTrace();
        	    }
            }
          }
      }
    }
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
