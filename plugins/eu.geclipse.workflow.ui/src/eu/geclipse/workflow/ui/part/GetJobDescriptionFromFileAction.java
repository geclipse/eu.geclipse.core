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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.action.IAction;
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
import eu.geclipse.workflow.ui.edit.commands.InputPortAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.OutputPortAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

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
    	  IFile jsdlSource = null;
    	  String filePath = dialog.getFilterPath() + "/" + result; //$NON-NLS-1$
    	  //filePath = filePath.replace(' ', '+');
    	  java.net.URI filePathUri = null;
    	  filePathUri = URIUtil.toURI(filePath);
    	  IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( filePathUri );
    	  jsdlSource = files[0];
    	  java.net.URI targetUri = this.wfRootFileStore.getChild(this.dirs[3]).getChild(this.dirs[4]).getChild( result ).toURI();    	
	      this.jsdlTarget = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( targetUri )[0];
	      // now check if it exists in workflow directory, and copy it from source if not	      
	      try {
	    	  // no need to copy if the file selected is in .workflow folder anyway
	    	if ( !this.jsdlTarget.equals(jsdlSource)) {
	    		// deal with existing files somehow? change target to append [n] to filename
	    		int numTarget = findJsdlTarget( this.jsdlTarget );
		        if ( numTarget > 0 ) {
		          String targetNamePart = this.jsdlTarget.getName().split("\\.")[0]; //$NON-NLS-1$
		          String newTargetName = targetNamePart + "[" + numTarget + "]" + ".jsdl";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		          // make new jsdlTarget
		          targetUri = this.wfRootFileStore.getChild(this.dirs[3]).getChild(this.dirs[4]).getChild( newTargetName ).toURI();    	
			      this.jsdlTarget = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( targetUri )[0];        		          		          		       
		        } 
		      jsdlSource.copy( this.jsdlTarget.getFullPath(), true, null );
		        
	    	}
	      } catch ( CoreException ex ) {
	        // ignore for now. naughty!
	      }
	      this.jobDescriptionInJSDL = this.jsdlTarget.getLocation().toString(); // is now the file path    
      	updateWorkflowJobDescription();
      }
    }
  }
  
  private int findJsdlTarget( IFile jsdlTargetToFind ) {
	  // search through the .workflow directory for the target name and target[n] names to work out how many exist
	  int numTarget = 0;
	  while(jsdlTargetToFind.exists()) {
          String targetNamePart = jsdlTargetToFind.getName().split("\\.")[0]; //$NON-NLS-1$
          String newTargetName = ""; //$NON-NLS-1$
          if (targetNamePart.endsWith("[" + numTarget + "]")) { //$NON-NLS-1$ //$NON-NLS-2$
        	  targetNamePart = targetNamePart.substring(0, (targetNamePart.length() - ("[" + numTarget + "]").length()));        	    //$NON-NLS-1$//$NON-NLS-2$
          }
          newTargetName = targetNamePart + "[" + ++numTarget + "]" + ".jsdl";          //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		  java.net.URI targetUri = this.wfRootFileStore.getChild(this.dirs[3]).getChild(this.dirs[4]).getChild( newTargetName ).toURI();    	
		  jsdlTargetToFind = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( targetUri )[0];		  
	  }
	  return numTarget;
  }

  /**
   * This method updates the JobDescription field in the WorkflowJob.
   * @return true or false
   */
  public boolean updateWorkflowJobDescription() {
    TransactionalEditingDomain domain = this.mySelectedElement.getEditingDomain();
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
        job.setName( GetJobDescriptionFromFileAction.this.jsdlTarget.getName().substring( 0, GetJobDescriptionFromFileAction.this.jsdlTarget.getName().indexOf( "." + GetJobDescriptionFromFileAction.this.jsdlTarget.getFileExtension() ) ) ); //$NON-NLS-1$
        return CommandResult.newOKCommandResult();
      }
    };
    try {
      OperationHistoryFactory.getOperationHistory()
        .execute( command, new NullProgressMonitor(), null );
    } catch( ExecutionException eE ) {
      // do nothing for now, but must fix this soon!
    }
    
    CompoundCommand cmd = new CompoundCommand();
    java.net.URI jsdlPathUri = URIUtil.toURI(  GetJobDescriptionFromFileAction.this.jobDescriptionInJSDL );
    IFile jsdlFile = ResourcesPlugin.getWorkspace()
    .getRoot()
    .findFilesForLocationURI( jsdlPathUri )[ 0 ];
    JSDLJobDescription jsdl = new JSDLJobDescription(jsdlFile);
    Map<String, String> m = jsdl.getDataStagingInStrings();
    Set<String> s = m.keySet();
    for ( Iterator< String > i = s.iterator(); i.hasNext(); ) {
      String filename = i.next();
      String uri = m.get( filename );
      cmd.add( createInputPortCommand( uri ) );
    }      
    m = jsdl.getDataStagingOutStrings();
    s = m.keySet();
    for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
      String filename = i.next();
      String uri = m.get( filename );
      cmd.add( createOutputPortCommand(uri) );
    }
    cmd.execute();   
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

  @SuppressWarnings("unchecked")
  private Command createInputPortCommand(String uri) {
    IElementType type = WorkflowElementTypes.IInputPort_2002;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.mySelectedElement.getDiagramPreferencesHint() );
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest( viewDescriptor );
    Command cmd = this.mySelectedElement.getCommand( createRequest );
    cmd = cmd.chain( new InputPortAfterCreateCommand( ( ( Collection< IAdaptable > )createRequest.getNewObject() ).iterator().next(), uri, this.mySelectedElement.getEditingDomain() ) );
    return cmd;
  }
  
  @SuppressWarnings("unchecked")
  private Command createOutputPortCommand(String uri) {
    IElementType type = WorkflowElementTypes.IOutputPort_2001;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.mySelectedElement.getDiagramPreferencesHint() );
    
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest(viewDescriptor);
    Command cmd = this.mySelectedElement.getCommand( createRequest );
    cmd = cmd.chain( new OutputPortAfterCreateCommand( ( ( Collection< IAdaptable > )createRequest.getNewObject() ).iterator().next(), uri, this.mySelectedElement.getEditingDomain() ) );
    return cmd;
  } 
  
}
