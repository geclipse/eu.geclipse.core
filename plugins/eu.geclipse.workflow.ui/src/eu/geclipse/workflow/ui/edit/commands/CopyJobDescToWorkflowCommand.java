/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *     - David Johnson - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.commands;

import java.net.URI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.part.Messages;


/**
 * @author david
 *
 */
public class CopyJobDescToWorkflowCommand extends AbstractTransactionalCommand {

  private JSDLJobDescription jsdl;
  private EObject domainElt;

  /**
   * @param domainElt Domain element that determine's the context of the command
   * @param jsdl Job description to copy into workflow
   */
  public CopyJobDescToWorkflowCommand( EObject domainElt,
                                       JSDLJobDescription jsdl )
  {
    super( TransactionUtil.getEditingDomain( domainElt ),
           Messages.getString("CopyJobDescToWorkflowCommand_constructorMessage"), //$NON-NLS-1$
           null);
    this.domainElt = domainElt;
    this.jsdl = jsdl;
  }

  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor arg0,
                                               IAdaptable arg1 )
    throws ExecutionException
  {
    
    if (!(this.domainElt instanceof IWorkflowJob)) {
      return CommandResult.newErrorCommandResult( "Element is not a Workflow Job" ); //$NON-NLS-1$
    }
    
    /*
     * First, construct an IFile that points to the source JSDL
     */
    IResource jsdlRes = this.jsdl.getResource(); // the resource
    IPath jsdlResPath = jsdlRes.getLocation(); // the resource location as IPath
    String jsdlResPathString = jsdlResPath.toString(); // the resource location as String
    URI jsdlResPathUri = URIUtil.toURI( jsdlResPathString ); // the resource location as URI
    IFile jsdlSource = ResourcesPlugin.getWorkspace()
      .getRoot()
      .findFilesForLocationURI( jsdlResPathUri )[ 0 ]; // Finally, the resource location as IFile
    
    /*
     * Now, determine the root of the relevant .workflow folder according to our current context
     */
    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain( this.domainElt ); // the transactional editing domain
    ResourceSet resourceSet = domain.getResourceSet(); // the full resource set taken from the editing domain
    EList<Resource> resources = resourceSet.getResources(); // list of resources from resource set
    Resource workflowRes = resources.get( 0 ); // the first resource from list of resources, which is the workflow
    org.eclipse.emf.common.util.URI workflowRootUri = workflowRes.getURI(); // the URI related to the workflow itself
    String workflowRootPathString = workflowRootUri.path(); // path to workflow resource as a String
    String[] workflowDirectories = workflowRootPathString.split( "/" ); //$NON-NLS-1$ // Split path into individual the directories
    String projectName = workflowDirectories[ 2 ]; // Grid project name is third directory in path
    IGridRoot gridModelRoot = GridModel.getRoot(); // Grid Model root
    IFileStore gridModelRootFileStore = gridModelRoot.getFileStore(); // Grid Model root as IFileStore
    IFileStore workflowRootFileStore = gridModelRootFileStore.getChild( projectName ); // Workflow root as IFileStore
    
    /*
     * Construct the target to copy the Job Description to, which lies within the .workflow folder
     */    
    
    // The following little bit cleans up the directory name formatting
    int numDirsInWorkflowPath = workflowDirectories.length; // Number of directories in workflow path
    IFileStore fs = workflowRootFileStore; // temporary store for root file store of workflow
    // this loop goes through every directory in the path except the first 2 and last 1 replacing %20 with spaces
    for ( int i = 3; i < numDirsInWorkflowPath - 1; i++ ) {
      String d = workflowDirectories[ i ];
      d = d.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
      fs = fs.getChild( d );
    } 
    // end directory name cleanup
    
    String jsdlSourceName = jsdlSource.getName(); // source Job Description's file name only
    fs = fs.getChild( jsdlSourceName ); // uses the source file name to create IFileStore pointing to new location
    URI jsdlTargetUri = fs.toURI(); // Job Description target URI
    IWorkspace workspace = ResourcesPlugin.getWorkspace(); // workspace
    IWorkspaceRoot workspaceRoot = workspace.getRoot(); // workspace root
    IFile jsdlTarget = workspaceRoot.findFilesForLocationURI( jsdlTargetUri )[ 0 ]; // target URI returned within workspace
    
    /*
     * Now was have source and target IFiles, determine whether target already exists. If it doesn't
     * then we will copy the source file into the .workflow folder target. If it does, then we reformat
     * the target name by appending an index to it
     */
    try {
      // if target==source, reformat source
      if ( ! jsdlTarget.equals( jsdlSource ) ) {
        int numTarget = 0;
        // try append a number of end of target name. If the new target exists, increment the appended number
        while ( jsdlTarget.exists() ) {
          String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
          String newTargetName = ""; //$NON-NLS-1$
          if ( targetNamePart.endsWith( numTarget + "" ) ) { //$NON-NLS-1$
            targetNamePart = targetNamePart.substring( 0,
                               ( targetNamePart.length() - (
                                 + numTarget + "" ).length() ) ); //$NON-NLS-1$
          }
          newTargetName = targetNamePart + ++numTarget + ".jsdl"; //$NON-NLS-1$
          // make new jsdlTarget
          int numDirs = workflowDirectories.length;
          java.net.URI targetUri = null;
          
          IFileStore fs1 = workflowRootFileStore;
          for ( int i = 3; i < numDirs - 1; i++ ) {
            String dir = workflowDirectories[i];
            dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
            fs1 = fs1.getChild( dir );
          }
          fs1 = fs1.getChild( newTargetName );
          targetUri =  fs1.toURI();
          jsdlTarget = ResourcesPlugin.getWorkspace()
            .getRoot()
            .findFilesForLocationURI( targetUri )[ 0 ];
        }
        
        if ( numTarget > 0 ) {
          String targetNamePart = jsdlTarget.getName().split( numTarget + "\\." )[ 0 ]; //$NON-NLS-1$
          String newTargetName = targetNamePart
                                 + numTarget
                                 + ".jsdl"; //$NON-NLS-1$
          // make new jsdlTarget
          int numDirs = workflowDirectories.length;
          URI targetUri = null;
          
          fs = workflowRootFileStore;
          for ( int i = 3; i < numDirs - 1; i++ ) {
            String dir = workflowDirectories[ i ];
            dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
            fs = fs.getChild( dir );
          }
          fs = fs.getChild( newTargetName );
          targetUri =  fs.toURI();
          jsdlTarget = ResourcesPlugin.getWorkspace()
            .getRoot()
            .findFilesForLocationURI( targetUri )[ 0 ];
        }
        jsdlSource.copy( jsdlTarget.getFullPath(), true, null );
      }
    } catch ( CoreException ex ) {
      // ignore for now. naughty!
    }
    // set Job Description property of workflow job to point to the target JSDL
    IPath jsdlTargetPath = jsdlTarget.getLocation();
    String jsdlTargetPathString = jsdlTargetPath.toString();
    IWorkflowJob workflowJob = (IWorkflowJob) this.domainElt;
    workflowJob.setJobDescription( jsdlTargetPathString );
    
    // construct a name for the job out of the file name and set name property of JSDL as it
    String jsdlTargetName = jsdlTarget.getName();
    String jsdlTargetExtension = jsdlTarget.getFileExtension();
    String jsdlTargetSeparatorAndExtension = "." + jsdlTargetExtension; //$NON-NLS-1$
    int jsdlTargetLastPartIndex = jsdlTargetName.indexOf( jsdlTargetSeparatorAndExtension );
    workflowJob.setName( jsdlTarget.getName().substring( 0, jsdlTargetLastPartIndex ) );    
    return CommandResult.newOKCommandResult();
  }
}
