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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.edit.policies.WorkflowJobItemSemanticEditPolicy;
import eu.geclipse.workflow.ui.part.Messages;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;


/**
 * @author david
 *
 */
public class UpdateJobPortsCommand extends AbstractTransactionalCommand {

  private GraphicalEditPart editPart;
  private JSDLJobDescription jsdl;
  private EObject domainElt;

  /**
   * @param editPart Edit part to update
   * @param jsdl Job description to copy into workflow
   */
  public UpdateJobPortsCommand( GraphicalEditPart editPart,
                                       JSDLJobDescription jsdl )
  {
    super( TransactionUtil.getEditingDomain( editPart.resolveSemanticElement() ),
           Messages.getString("UpdateJobPortsCommand_constructorMessage"), //$NON-NLS-1$
           null);
    this.editPart = editPart;
    this.domainElt = editPart.resolveSemanticElement();
    this.jsdl = jsdl;
  }

  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor arg0,
                                               IAdaptable arg1 )
    throws ExecutionException
  {
    if ( !(this.domainElt instanceof IWorkflowJob) | !(this.editPart instanceof WorkflowJobEditPart)) {
      return CommandResult.newErrorCommandResult( "Element is not a Workflow Job" ); //$NON-NLS-1$
    }
   
    /*
     * First, construct an IFile that points to the source JSDL
     */
    IResource jsdlRes = this.jsdl.getResource(); // the resource
    IPath jsdlResPath = jsdlRes.getLocation(); // the resource location as IPath
    String jsdlResPathString = jsdlResPath.toString(); // the resource location as String
    URI jsdlResPathUri = URIUtil.toURI( jsdlResPathString ); // the resource location as URI
    IFile jsdlFile = ResourcesPlugin.getWorkspace()
      .getRoot()
      .findFilesForLocationURI( jsdlResPathUri )[ 0 ]; // Finally, the resource location as IFile
    this.jsdl = new JSDLJobDescription(jsdlFile);
    
    CompoundCommand cmd = new CompoundCommand();
    // if we're dropping a new JSDL onto the job, clear the old ports before creating new ports that match
    // get WorkflowJobEditPart's children
    List childParts = this.editPart.getChildren();
    // find input ports and make a DestroyRequest for each one
    for (Iterator i1 = childParts.iterator(); i1.hasNext();) {
      Object child = i1.next();
      if( child instanceof InputPortEditPart ) {
        InputPortEditPart inputPortPart = ( InputPortEditPart )child;
        Command destroyCmd = destroyInputPortCommand(inputPortPart);
        if (destroyCmd!=null)
          cmd.add( destroyCmd );
      }
      if( child instanceof OutputPortEditPart ) {
        OutputPortEditPart outputPortPart = ( OutputPortEditPart )child;
        Command destroyCmd = destroyOutputPortCommand(outputPortPart);
        if (destroyCmd!=null)
          cmd.add( destroyCmd );
      }
    }
    
    Map<String, String> m = this.jsdl.getDataStagingInStrings();
    Set<String> s = m.keySet();    
    for ( Iterator< String > i = s.iterator(); i.hasNext(); ) {
      String filename = i.next();
      String uri = m.get( filename );
      cmd.add( createInputPortCommand( filename, uri ) );
    }      
    m = this.jsdl.getDataStagingOutStrings();
    s = m.keySet();
    for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
      String filename = i.next();
      String uri = m.get( filename );
      cmd.add( createOutputPortCommand( filename, uri ) );
    }
    cmd.execute(); 
    return CommandResult.newOKCommandResult();
  }
  
  @SuppressWarnings("unchecked")
  private org.eclipse.gef.commands.Command createInputPortCommand(String filename, String uri) {
    IElementType type = WorkflowElementTypes.IInputPort_2002;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.editPart.getDiagramPreferencesHint() );
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest( viewDescriptor );
    org.eclipse.gef.commands.Command cmd = this.editPart.getCommand( createRequest );
    cmd = cmd.chain( new InputPortAfterCreateCommand( ( ( Collection< IAdaptable > )createRequest.getNewObject() ).iterator().next(), filename, uri, this.editPart.getEditingDomain() ) );
    return cmd;
  }
  
  @SuppressWarnings("unchecked")
  private org.eclipse.gef.commands.Command createOutputPortCommand(String filename, String uri) {
    IElementType type = WorkflowElementTypes.IOutputPort_2001;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.editPart.getDiagramPreferencesHint() );
    
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest(viewDescriptor);
    org.eclipse.gef.commands.Command cmd = this.editPart.getCommand( createRequest );
    cmd = cmd.chain( new OutputPortAfterCreateCommand( ( ( Collection< IAdaptable > )createRequest.getNewObject() ).iterator().next(), filename, uri, this.editPart.getEditingDomain() ) );
    return cmd;
  }   
  
  /**
   * 
   * @param uri
   * @return the Command that destroys an OutputPort
   */
  private Command destroyOutputPortCommand( OutputPortEditPart outputPortPart )
  {
    WorkflowJobItemSemanticEditPolicy semanticEditPolicy = ( WorkflowJobItemSemanticEditPolicy )this.editPart.getEditPolicy( EditPolicyRoles.SEMANTIC_ROLE );
    Command cmd =  semanticEditPolicy.getDestroyElementCommand( new DestroyElementRequest(outputPortPart.resolveSemanticElement(), false) );
    return cmd;
  }

  /**
   * 
   * @param uri
   * @return the Command that destroys an InputPort
   */
  private Command destroyInputPortCommand( InputPortEditPart inputPortPart ) {
    WorkflowJobItemSemanticEditPolicy semanticEditPolicy = ( WorkflowJobItemSemanticEditPolicy )this.editPart.getEditPolicy( EditPolicyRoles.SEMANTIC_ROLE );
    Command cmd =  semanticEditPolicy.getDestroyElementCommand( new DestroyElementRequest(inputPortPart.resolveSemanticElement(), false) );
    return cmd;
  }  
  
}
