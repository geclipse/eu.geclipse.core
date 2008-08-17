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
import org.eclipse.draw2d.geometry.Point;
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
 * 
 * @author nloulloud
 */
public class WorkflowDiagramDragDropEditPolicy
  extends DiagramDragDropEditPolicy
{

  /*
   * (non-Javadoc)
   * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy
   *        #getDropObjectsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Command getDropObjectsCommand( DropObjectsRequest dropRequest ) {
    List objects = dropRequest.getObjects();
    CompoundCommand cmd = new CompoundCommand();
    Point dropLocation = dropRequest.getLocation();
    for ( Object o : objects ) {
      if ( ( o instanceof JSDLJobDescription ) ) {
        JSDLJobDescription jsdl = ( JSDLJobDescription ) o;
        WorkflowEditPart selectedElement = ( WorkflowEditPart ) getHost();
        IElementType type = WorkflowElementTypes.IWorkflowJob_1001;
        ViewAndElementDescriptor viewDescriptor
          = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                          Node.class,
                                          ( ( IHintedType )type ).getSemanticHint(),
                                          selectedElement.getDiagramPreferencesHint() );
        CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest( viewDescriptor );
        createRequest.setLocation( dropLocation );
        // translate each subsequent drop slightly, maybe find a more intelligent way of doing this?
        dropLocation.translate( 25, 25 );
        cmd.add( selectedElement.getCommand( createRequest ) );
        WorkflowJobAfterCreateCommand afterCreateCmd
          = new WorkflowJobAfterCreateCommand( ( ( Collection< IAdaptable > ) createRequest.getNewObject() )
                                                 .iterator().next(),
                                               jsdl,
                                               selectedElement );
        cmd.add( afterCreateCmd );
      }
    }
    return cmd;
//  return super.getDropObjectsCommand( dropRequest );
  }
  
  /**
   * @generated
   */
  protected final Command getGEFWrapper( ICommand cmd ) {
    return new ICommandProxy( cmd );
  }

}
