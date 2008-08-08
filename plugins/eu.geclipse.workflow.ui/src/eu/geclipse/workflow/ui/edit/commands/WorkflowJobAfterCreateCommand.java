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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.GetJobDescriptionFromFileAction;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * 
 * @author david
 *
 */
public class WorkflowJobAfterCreateCommand extends Command {
  
  private IAdaptable adapter;
  private JSDLJobDescription jsdl;
  private IWorkflowJob newElement = null;
  private TransactionalEditingDomain domain;
  protected String[] dirs;
  protected IFileStore wfRootFileStore;
  
  public WorkflowJobAfterCreateCommand(IAdaptable adapter, JSDLJobDescription jsdl, TransactionalEditingDomain domain) {
    this.adapter = adapter;
    this.jsdl = jsdl;
    this.domain = domain;
  }

  public void execute() {
    EObject newVisualElement = (EObject)adapter.getAdapter(EObject.class);
    Object o = newVisualElement.eCrossReferences().get( 0 );
    if (o instanceof IWorkflowJob) {
       newElement = ( IWorkflowJob )o;
       IStatus status = Status.OK_STATUS;
       AbstractTransactionalCommand command = new AbstractTransactionalCommand( domain,
                                                                                Messages.getString( "GetJobDescriptionFromFileAction.updatingJobDescription" ), //$NON-NLS-1$
                                                                                null )
       {
         IStatus status = Status.OK_STATUS;
         @Override
         protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                                      IAdaptable info )
         {
           URI jsdlPathUri = null;
           jsdlPathUri = URIUtil.toURI( WorkflowJobAfterCreateCommand.this.jsdl.getResource().getLocation().toString());
           IFile jsdlSource = ResourcesPlugin.getWorkspace()
             .getRoot()
             .findFilesForLocationURI( jsdlPathUri )[ 0 ];
           String jobDescriptionInJSDL = jsdlSource.getLocation().toString();
           ResourceSet resourceSet = domain.getResourceSet();
           Resource res = resourceSet.getResources().get( 0 );
           org.eclipse.emf.common.util.URI wfRootUri = res.getURI();
           String wfRootPath = wfRootUri.path();
           WorkflowJobAfterCreateCommand.this.dirs = wfRootPath.split( "/" ); //$NON-NLS-1$
           String projectName = WorkflowJobAfterCreateCommand.this.dirs[ 2 ];
           WorkflowJobAfterCreateCommand.this.wfRootFileStore = GridModel.getRoot()
             .getFileStore()
             .getChild( projectName );
           java.net.URI targetUri = WorkflowJobAfterCreateCommand.this.wfRootFileStore.getChild( WorkflowJobAfterCreateCommand.this.dirs[ 3 ] )
             .getChild( WorkflowJobAfterCreateCommand.this.dirs[ 4 ] )
             .getChild( jsdlSource.getName() )
             .toURI();
           IFile jsdlTarget = ResourcesPlugin.getWorkspace()
             .getRoot()
             .findFilesForLocationURI( targetUri )[ 0 ];
           // now check if it exists in workflow directory, and copy it from
           // source if not
           try {
             // no need to copy if the file selected is in .workflow folder
             // anyway
             if( !jsdlTarget.equals( jsdlSource ) ) {
               // deal with existing files somehow? change target to append [n]
               // to filename
               int numTarget = findJsdlTarget( jsdlTarget );
               if( numTarget > 0 ) {
                 String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
                 String newTargetName = targetNamePart
                                        + "[" //$NON-NLS-1$
                                        + numTarget
                                        + "]" //$NON-NLS-1$
                                        + ".jsdl"; //$NON-NLS-1$
                 // make new jsdlTarget
                 targetUri = WorkflowJobAfterCreateCommand.this.wfRootFileStore.getChild( WorkflowJobAfterCreateCommand.this.dirs[ 3 ] )
                   .getChild( WorkflowJobAfterCreateCommand.this.dirs[ 4 ] )
                   .getChild( newTargetName )
                   .toURI();
                 jsdlTarget = ResourcesPlugin.getWorkspace()
                   .getRoot()
                   .findFilesForLocationURI( targetUri )[ 0 ];
               }
               jsdlSource.copy( jsdlTarget.getFullPath(), true, null );
               jobDescriptionInJSDL = jsdlTarget.getLocation().toString();
             }
           } catch( CoreException ex ) {
             // ignore for now. naughty!
           }
           newElement.setJobDescription( jobDescriptionInJSDL );
           if (newElement.getName()==null) {
             newElement.setName( jsdlTarget.getName().substring( 0, jsdlTarget.getName().indexOf( "." + jsdlTarget.getFileExtension() ) ) );
           }
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
       }
    }
  }
  

  private int findJsdlTarget( IFile jsdlTarget ) {
    // search through the .workflow directory for the target name and target[n]
    // names to work out how many exist
    int numTarget = 0;
    while( jsdlTarget.exists() ) {
      String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
      String newTargetName = ""; //$NON-NLS-1$
      if( targetNamePart.endsWith( "[" + numTarget + "]" ) ) { //$NON-NLS-1$ //$NON-NLS-2$
        targetNamePart = targetNamePart.substring( 0,
                                                   ( targetNamePart.length() - ( "[" //$NON-NLS-1$
                                                                                 + numTarget + "]" ).length() ) ); //$NON-NLS-1$
      }
      newTargetName = targetNamePart + "[" + ++numTarget + "]" + ".jsdl"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      java.net.URI targetUri = this.wfRootFileStore.getChild( this.dirs[ 3 ] )
        .getChild( this.dirs[ 4 ] )
        .getChild( newTargetName )
        .toURI();
      jsdlTarget = ResourcesPlugin.getWorkspace()
        .getRoot()
        .findFilesForLocationURI( targetUri )[ 0 ];
    }
    return numTarget;
  }  
  
}
