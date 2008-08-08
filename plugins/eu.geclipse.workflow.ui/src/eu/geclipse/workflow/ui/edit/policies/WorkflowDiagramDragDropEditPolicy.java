/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *     RUR (http://acet.reading.ac.uk)
 *      - David Johnson - got node creation to work
 *
 *****************************************************************************/
package eu.geclipse.workflow.ui.edit.policies;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.ui.edit.commands.WorkflowJobAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;


/**
 * @author nloulloud
 *
 */
public class WorkflowDiagramDragDropEditPolicy
  extends DiagramDragDropEditPolicy
{

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
        WorkflowEditPart selectedElement = ( WorkflowEditPart )getHost();
        IElementType type = WorkflowElementTypes.IWorkflowJob_1001;
        ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                                Node.class,
                                                                                ( ( IHintedType )type ).getSemanticHint(),
                                                                                selectedElement.getDiagramPreferencesHint() );
//        Command cmd = selectedElement.getCommand( new CreateViewAndElementRequest( viewDescriptor ) );
        CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest(viewDescriptor);
        CompoundCommand cmd = new CompoundCommand();
        cmd.add( selectedElement.getCommand( createRequest ) );
        TransactionalEditingDomain domain = selectedElement.getEditingDomain();
        WorkflowJobAfterCreateCommand afterCreateCmd = new WorkflowJobAfterCreateCommand(((Collection<IAdaptable>)createRequest.getNewObject()).iterator().next(), jsdl, domain);
        cmd.add( afterCreateCmd );
        

        // NOTE: the following won't work until we can figure out how to get the
        // newly created editpart 
        // this bit reads stdin/out and staging in and out to determine what
        // in/out ports are needed on a WF job
        // Map<String, String> m = jsdl.getDataStagingInStrings();
        // Set<String> s = m.keySet();
        // for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
        // String filename = i.next();
        // cmd = cmd.chain( createInputPortCommand() );
        // }
        // m = jsdl.getDataStagingOutStrings();
        // s = m.keySet();
        // for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
        // String filename = i.next();
        // cmd = cmd.chain( createOutputPortCommand() );
        // }
        return cmd;
      }
    }
    return super.getDropObjectsCommand( dropRequest );
  }

  //  
  // private Command createInputPortCommand() {
  // WorkflowJobEditPart selectedElement = ( WorkflowJobEditPart )getHost();
  // IElementType type = WorkflowElementTypes.IInputPort_2002;
  // ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new
  // CreateElementRequestAdapter( new CreateElementRequest( type ) ),
  // Node.class,
  // ( ( IHintedType )type ).getSemanticHint(),
  // selectedElement.getDiagramPreferencesHint() );
  //    
  // Command cmd = selectedElement.getCommand( new
  // CreateViewAndElementRequest(viewDescriptor) );
  // return cmd;
  // }
  //  
  // private Command createOutputPortCommand() {
  // WorkflowJobEditPart selectedElement = ( WorkflowJobEditPart )getHost();
  // IElementType type = WorkflowElementTypes.IOutputPort_2001;
  // ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new
  // CreateElementRequestAdapter( new CreateElementRequest( type ) ),
  // Node.class,
  // ( ( IHintedType )type ).getSemanticHint(),
  // selectedElement.getDiagramPreferencesHint() );
  //    
  // Command cmd = selectedElement.getCommand( new
  // CreateViewAndElementRequest(viewDescriptor) );
  //    return cmd;
  //  }    
  /**
   * @generated
   */
  protected final Command getGEFWrapper( ICommand cmd ) {
    return new ICommandProxy( cmd );
  }
}
