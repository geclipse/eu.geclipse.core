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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
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

import eu.geclipse.core.model.GridModel;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.part.Messages;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * 
 * @author david
 */
public class WorkflowJobAfterCreateCommand extends Command {
  
  private IAdaptable adapter;
  private JSDLJobDescription jsdl;
  private IWorkflowJob newElement = null;
  private TransactionalEditingDomain domain;
  protected String[] dirs;
  protected IFileStore wfRootFileStore;
  private WorkflowEditPart diagram;
  private WorkflowJobEditPart newWorkflowJobEditPart;
  
  
  public WorkflowJobAfterCreateCommand(IAdaptable adapter, JSDLJobDescription jsdl, WorkflowEditPart diagram) {
    this.adapter = adapter;
    this.jsdl = jsdl;
    this.domain = diagram.getEditingDomain();
    this.diagram = diagram;
  }

  public void execute() {
    EObject newVisualElement = ( EObject ) this.adapter.getAdapter( EObject.class );
    Object o = newVisualElement.eCrossReferences().get( 0 );
    if ( o instanceof IWorkflowJob ) {
       this.newElement = ( IWorkflowJob )o;
       AbstractTransactionalCommand command = new AbstractTransactionalCommand( this.domain,
                                                                                Messages.getString( "GetJobDescriptionFromFileAction.updatingJobDescription" ), //$NON-NLS-1$
                                                                                null )
       {
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
           int numDirs = WorkflowJobAfterCreateCommand.this.dirs.length;
           java.net.URI targetUri = null;
           
           IFileStore fs = WorkflowJobAfterCreateCommand.this.wfRootFileStore;
           for ( int i = 3; i < numDirs - 1; i++ ) {
             String dir = WorkflowJobAfterCreateCommand.this.dirs[ i ];
             dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
             fs = fs.getChild( dir );
           }
           fs = fs.getChild( jsdlSource.getName() );
           targetUri =  fs.toURI();
           IFile jsdlTarget = ResourcesPlugin.getWorkspace()
             .getRoot()
             .findFilesForLocationURI( targetUri )[ 0 ];
           // now check if it exists in workflow directory, and copy it from
           // source if not
           try {
             // no need to copy if the file selected is in .workflow folder
             // anyway
             boolean isTargetSource = ! jsdlTarget.equals( jsdlSource );
             if ( isTargetSource ) {
               // deal with existing files somehow? change target to append [n]
               // to filename
               int numTarget = findJsdlTarget( jsdlTarget );
               if ( numTarget > 0 ) {
                 String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
                 String newTargetName = targetNamePart
                                        + "[" //$NON-NLS-1$
                                        + numTarget
                                        + "]" //$NON-NLS-1$
                                        + ".jsdl"; //$NON-NLS-1$
                 // make new jsdlTarget
                 numDirs = WorkflowJobAfterCreateCommand.this.dirs.length;
                 targetUri = null;
                 
                 fs = WorkflowJobAfterCreateCommand.this.wfRootFileStore;
                 for ( int i = 3; i < numDirs - 1; i++ ) {
                   String dir = WorkflowJobAfterCreateCommand.this.dirs[ i ];
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
               jobDescriptionInJSDL = jsdlTarget.getLocation().toString();
             }
           } catch ( CoreException ex ) {
             // ignore for now. naughty!
           }
           newElement.setJobDescription( jobDescriptionInJSDL );
           newElement.setName( jsdlTarget.getName().substring( 0, jsdlTarget.getName().indexOf( "." + jsdlTarget.getFileExtension() ) ) ); //$NON-NLS-1$
           return CommandResult.newOKCommandResult();
         }
       };
       try {
         OperationHistoryFactory.getOperationHistory()
           .execute( command, new NullProgressMonitor(), null );
       } catch ( ExecutionException eE ) {
         // ignore for now... very naughty!
       }
       
       CompoundCommand cmd = new CompoundCommand();
       EditPart part = this.diagram.findEditPart( this.diagram, this.newElement );
       if ( part instanceof WorkflowJobEditPart ) {
         this.newWorkflowJobEditPart = ( WorkflowJobEditPart ) part;
       }
       String stdInputUri = ""; //$NON-NLS-1$
       String stdOutputUri = ""; //$NON-NLS-1$
       String stdErrorUri = ""; //$NON-NLS-1$
       stdInputUri = this.jsdl.getStdInputFileName();
       stdOutputUri = this.jsdl.getStdOutputFileName();
       stdErrorUri = this.jsdl.getStdErrorFileName();
       if ( stdInputUri != null ) {
         cmd.add( createInputPortCommand( stdInputUri ) );
       }
       if ( stdOutputUri != null ) {
         cmd.add( createOutputPortCommand( stdOutputUri ) );
       }
       if ( stdErrorUri != null ) {
         cmd.add( createOutputPortCommand( stdErrorUri ) );
       }
       Map<String, String> m = this.jsdl.getDataStagingInStrings();
       Set<String> s = m.keySet();
       for ( Iterator< String > i = s.iterator(); i.hasNext(); ) {
         String filename = i.next();
         String uri = m.get( filename );
         cmd.add( createInputPortCommand( uri ) );
       }      
       m = this.jsdl.getDataStagingOutStrings();
       s = m.keySet();
       for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
         String filename = i.next();
         String uri = m.get( filename );
         cmd.add( createOutputPortCommand(uri) );
       }
       cmd.execute();       
    }
  }
  

  private int findJsdlTarget( IFile jsdlTarget ) {
    // search through the .workflow directory for the target name and target[n]
    // names to work out how many exist
    int numTarget = 0;
    while ( jsdlTarget.exists() ) {
      String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
      String newTargetName = ""; //$NON-NLS-1$
      if ( targetNamePart.endsWith( "[" + numTarget + "]" ) ) { //$NON-NLS-1$ //$NON-NLS-2$
        targetNamePart = targetNamePart.substring( 0,
                                                   ( targetNamePart.length() - ( "[" //$NON-NLS-1$
                                                                                 + numTarget + "]" ).length() ) ); //$NON-NLS-1$
      }
      newTargetName = targetNamePart + "[" + ++numTarget + "]" + ".jsdl"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      // make new jsdlTarget
      int numDirs = this.dirs.length;
      java.net.URI targetUri = null;
      
      IFileStore fs = WorkflowJobAfterCreateCommand.this.wfRootFileStore;
      for ( int i = 3; i < numDirs - 1; i++ ) {
        String dir = WorkflowJobAfterCreateCommand.this.dirs[ i ];
        dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
        fs = fs.getChild( dir );
      }
      fs = fs.getChild( newTargetName );
      targetUri = fs.toURI();
      jsdlTarget = ResourcesPlugin.getWorkspace()
        .getRoot()
        .findFilesForLocationURI( targetUri )[ 0 ];
    }
    return numTarget;
  }  
  
  private Command createInputPortCommand(String uri) {
    IElementType type = WorkflowElementTypes.IInputPort_2002;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.newWorkflowJobEditPart.getDiagramPreferencesHint() );
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest( viewDescriptor );
    Command cmd = this.newWorkflowJobEditPart.getCommand( createRequest );
    cmd = cmd.chain( new InputPortAfterCreateCommand( ( ( Collection< IAdaptable > )createRequest.getNewObject() ).iterator().next(), uri, this.newWorkflowJobEditPart.getEditingDomain() ) );
    return cmd;
  }
  
  private Command createOutputPortCommand(String uri) {
    IElementType type = WorkflowElementTypes.IOutputPort_2001;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.newWorkflowJobEditPart.getDiagramPreferencesHint() );
    
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest(viewDescriptor);
    Command cmd = this.newWorkflowJobEditPart.getCommand( createRequest );
    cmd = cmd.chain( new OutputPortAfterCreateCommand( ( ( Collection< IAdaptable > )createRequest.getNewObject() ).iterator().next(), uri, this.newWorkflowJobEditPart.getEditingDomain() ) );
    return cmd;
  }    
  
}
