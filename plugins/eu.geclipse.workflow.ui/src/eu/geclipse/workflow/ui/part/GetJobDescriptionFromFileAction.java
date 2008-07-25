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

import java.io.InputStream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.core.model.GridModel;
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
  private String jobDescriptionInJSDL = null;
  private String fileName = null;
  private IFileStore wfRootFileStore = null;
  private String[] dirs = null;

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
    FileDialog dialog = new FileDialog( this.myShell, SWT.OPEN );
	String[] exts = { "*.jsdl" };
	dialog.setFilterExtensions(exts);
	
    // this bit find the root directory of the workflow
    TransactionalEditingDomain domain = this.mySelectedElement.getEditingDomain();
    ResourceSet resourceSet = domain.getResourceSet();
    Resource res = resourceSet.getResources().get( 0 );
    URI wfRootUri = res.getURI();
    String wfRootPath = wfRootUri.path();
    dirs = wfRootPath.split( "/" ); //$NON-NLS-1$
    String projectName = dirs[2];
    wfRootFileStore = GridModel.getRoot().getFileStore().getChild( projectName );
	
	dialog.setFilterPath( wfRootFileStore.toString() );

    if ( dialog.open() != null ) { 
      String result = dialog.getFileName();
      if( ( result != null ) && ( result.length() > 0 ) ) {
    	  IFile jsdlSource = null;
    	  String filePath = dialog.getFilterPath() + "/" + result;
    	  //filePath = filePath.replace(' ', '+');
    	  java.net.URI filePathUri = null;
    	  filePathUri = URIUtil.toURI(filePath);
    	  IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( filePathUri );
    	  jsdlSource = files[0];
    	  java.net.URI targetUri = wfRootFileStore.getChild(dirs[3]).getChild(dirs[4]).getChild( result ).toURI();    	
	      IFile jsdlTarget = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( targetUri )[0];
	      // now check if it exists in workflow directory, and copy it from source if not	      
	      try {
	    	  // no need to copy if the file selected is in .workflow folder anyway
	    	if ( !jsdlTarget.equals(jsdlSource)) {
	    		// deal with existing files somehow? change target to append [n] to filename
	    		int numTarget = findJsdlTarget( jsdlTarget );
		        if ( numTarget > 0 ) {
		          String targetNamePart = jsdlTarget.getName().split("\\.")[0];
		          String newTargetName = targetNamePart + "[" + numTarget + "]" + ".jsdl";
		          // make new jsdlTarget
		          targetUri = wfRootFileStore.getChild(dirs[3]).getChild(dirs[4]).getChild( newTargetName ).toURI();    	
			      jsdlTarget = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( targetUri )[0];        		          		          		       
		        } 
		      jsdlSource.copy( jsdlTarget.getFullPath(), true, null );
		        
	    	}
	      } catch ( CoreException ex ) {
	        // ignore for now. naughty!
	      }
	      this.jobDescriptionInJSDL = jsdlTarget.getLocation().toString(); // is now the file path    
      	updateWorkflowJobDescription();
      }
    }
  }
  
  private int findJsdlTarget( IFile jsdlTarget ) {
	  // search through the .workflow directory for the target name and target[n] names to work out how many exist
	  int numTarget = 0;
	  while(jsdlTarget.exists()) {
          String targetNamePart = jsdlTarget.getName().split("\\.")[0];
          String newTargetName = "";
          if (targetNamePart.endsWith("[" + numTarget + "]")) {
        	  targetNamePart = targetNamePart.substring(0, (targetNamePart.length() - ("[" + numTarget + "]").length()));        	  
          }
          newTargetName = targetNamePart + "[" + ++numTarget + "]" + ".jsdl";         
		  java.net.URI targetUri = wfRootFileStore.getChild(dirs[3]).getChild(dirs[4]).getChild( newTargetName ).toURI();    	
	      jsdlTarget = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( targetUri )[0];		  
	  }
	  return numTarget;
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
