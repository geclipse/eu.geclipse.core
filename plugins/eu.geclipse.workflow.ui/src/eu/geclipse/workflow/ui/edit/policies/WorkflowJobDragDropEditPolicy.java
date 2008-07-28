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
package eu.geclipse.workflow.ui.edit.policies;

import java.net.URI;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * @author david
 *
 */
public class WorkflowJobDragDropEditPolicy extends DragDropEditPolicy {

  private IFileStore wfRootFileStore = null;
  private String[] dirs = null;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy#getDropObjectsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Command getDropObjectsCommand( DropObjectsRequest dropRequest ) {
    List objects = dropRequest.getObjects();
    for( Object o : objects ) {
      if( ( o instanceof JSDLJobDescription ) ) {
        JSDLJobDescription jsdl = ( JSDLJobDescription )o;
        WorkflowJobEditPart selectedElement = ( WorkflowJobEditPart )getHost();
        IElementType type = WorkflowElementTypes.IWorkflowJob_1001;
        ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                                Node.class,
                                                                                ( ( IHintedType )type ).getSemanticHint(),
                                                                                selectedElement.getDiagramPreferencesHint() );
        CreateViewAndElementRequest req = new CreateViewAndElementRequest( viewDescriptor );
        CompoundCommand cmd = new CompoundCommand( "Create 1 metaDataLink" );
        Object obj = selectedElement.resolveSemanticElement();
        if( obj instanceof IWorkflowJob ) {
          // must make sure it's in .workflow folder, and/or copy if necessary.
          IWorkflowJob wfJob = ( IWorkflowJob )obj;
          URI jsdlPathUri = jsdl.getFileStore().toURI();
          IFile jsdlSource = ResourcesPlugin.getWorkspace()
            .getRoot()
            .findFilesForLocationURI( jsdlPathUri )[ 0 ];
          // ResourceSet resourceSet =
          // selectedElement.getEditingDomain().getResourceSet();
          // Resource res = resourceSet.getResources().get( 0 );
          // org.eclipse.emf.common.util.URI wfRootUri = res.getURI();
          // String wfRootPath = wfRootUri.path();
          // dirs = wfRootPath.split( "/" ); //$NON-NLS-1$
          // String projectName = dirs[2];
          // wfRootFileStore = GridModel.getRoot().getFileStore().getChild(
          // projectName );
          // java.net.URI targetUri =
          // wfRootFileStore.getChild(dirs[3]).getChild(dirs[4]).getChild(
          // jsdlSource.getName() ).toURI();
          // IFile jsdlTarget =
          // ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(
          // targetUri )[0];
          // // now check if it exists in workflow directory, and copy it from
          // source if not
          // try {
          // // no need to copy if the file selected is in .workflow folder
          // anyway
          // if ( !jsdlTarget.equals(jsdlSource)) {
          // // deal with existing files somehow? change target to append [n] to
          // filename
          // int numTarget = findJsdlTarget( jsdlTarget );
          // if ( numTarget > 0 ) {
          // String targetNamePart = jsdlTarget.getName().split("\\.")[0];
          // String newTargetName = targetNamePart + "[" + numTarget + "]" +
          // ".jsdl";
          // // make new jsdlTarget
          // targetUri =
          // wfRootFileStore.getChild(dirs[3]).getChild(dirs[4]).getChild(
          // newTargetName ).toURI();
          // jsdlTarget =
          // ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(
          // targetUri )[0];
          // }
          // jsdlSource.copy( jsdlTarget.getFullPath(), true, null );
          // }
          // } catch ( CoreException ex ) {
          // // ignore for now. naughty!
          // }
          // wfJob.setJobDescription(jsdlTarget.toString());
          wfJob.setJobDescription( jsdl.getFileStore().toString() );
        }
        cmd.add( selectedElement.getCommand( req ) );
        return cmd;
      }
    }
    return super.getDropObjectsCommand( dropRequest );
  }

  private int findJsdlTarget( IFile jsdlTarget ) {
    // search through the .workflow directory for the target name and target[n]
    // names to work out how many exist
    int numTarget = 0;
    while( jsdlTarget.exists() ) {
      String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ];
      String newTargetName = "";
      if( targetNamePart.endsWith( "[" + numTarget + "]" ) ) {
        targetNamePart = targetNamePart.substring( 0,
                                                   ( targetNamePart.length() - ( "["
                                                                                 + numTarget + "]" ).length() ) );
      }
      newTargetName = targetNamePart + "[" + ++numTarget + "]" + ".jsdl";
      java.net.URI targetUri = wfRootFileStore.getChild( dirs[ 3 ] )
        .getChild( dirs[ 4 ] )
        .getChild( newTargetName )
        .toURI();
      jsdlTarget = ResourcesPlugin.getWorkspace()
        .getRoot()
        .findFilesForLocationURI( targetUri )[ 0 ];
    }
    return numTarget;
  }
}
