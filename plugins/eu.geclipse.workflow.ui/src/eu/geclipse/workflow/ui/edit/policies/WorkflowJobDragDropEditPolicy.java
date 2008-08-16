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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.ui.edit.commands.InputPortAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.JSDLDropCommand;
import eu.geclipse.workflow.ui.edit.commands.OutputPortAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * @author david
 */
public class WorkflowJobDragDropEditPolicy extends DragDropEditPolicy {

  private WorkflowJobEditPart selectedElement = null;

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy
   * #getDropObjectsCommand
   * (org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Command getDropObjectsCommand( DropObjectsRequest dropRequest ) {
    List objects = dropRequest.getObjects();
    CompoundCommand cmd = new CompoundCommand();
    JSDLDropCommand dropCmd = null;
    JSDLJobDescription jsdl = null;

    for( Object o : objects ) {
      
      if( o instanceof JSDLJobDescription ) {
        jsdl = ( JSDLJobDescription )o;
        this.selectedElement = ( WorkflowJobEditPart )getHost();
        dropCmd = new JSDLDropCommand( this.selectedElement.resolveSemanticElement(), jsdl);
        cmd.add( new ICommandProxy(dropCmd) );

        // if we're dropping a new JSDL onto the job, clear the old ports before creating new ports that match
        // get WorkflowJobEditPart's children
        List childParts = this.selectedElement.getChildren();
        // find input ports and make a DestroyRequest for each one
        for (Iterator i = childParts.iterator(); i.hasNext();) {
          Object child = i.next();
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

        // this bit reads staging in and out to determine what in/out ports are needed
        // on a WF job    
        Map<String, String> m = jsdl.getDataStagingInStrings();
        Set<String> s = m.keySet();
        for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
          String filename = i.next();
          String uri = m.get( filename );
          cmd.add( createInputPortCommand(uri) );
        }      
        m = jsdl.getDataStagingOutStrings();
        s = m.keySet();
        for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
          String filename = i.next();
          String uri = m.get( filename );
          cmd.add( createOutputPortCommand(uri) );
        }



//      if (jsdl.getParent() instanceof IGridWorkflow) {
//      System.out.println("All children jobs are...");
//      IGridWorkflow wf = ( IGridWorkflow )jsdl.getParent();
//      List<IGridWorkflowJob> jobs = wf.getChildrenJobs();
//      for (int i=0;i<jobs.size();i++) {
//      IGridWorkflowJob job = jobs.get( i );
//      System.out.println(job.getName() + "; " + job.getDescription());
//      // read in as JSDLJobDescription to get and build/compare semantic links??
//      }
//      System.out.println("so we can try match in/out stages to links now, right??");
//      }
        
      }

      return cmd;
    }
    return super.getDropObjectsCommand( dropRequest );
  }
  
  /**
   * 
   * @param uri
   * @return the Command that destroys an OutputPort
   */
  private Command destroyOutputPortCommand( OutputPortEditPart outputPortPart )
  {
    WorkflowJobItemSemanticEditPolicy semanticEditPolicy = ( WorkflowJobItemSemanticEditPolicy )this.selectedElement.getEditPolicy( EditPolicyRoles.SEMANTIC_ROLE );
    Command cmd =  semanticEditPolicy.getDestroyElementCommand( new DestroyElementRequest(outputPortPart.resolveSemanticElement(), false) );
    return cmd;
  }

  /**
   * 
   * @param uri
   * @return the Command that destroys an InputPort
   */
  private Command destroyInputPortCommand( InputPortEditPart inputPortPart ) {
    WorkflowJobItemSemanticEditPolicy semanticEditPolicy = ( WorkflowJobItemSemanticEditPolicy )this.selectedElement.getEditPolicy( EditPolicyRoles.SEMANTIC_ROLE );
    Command cmd =  semanticEditPolicy.getDestroyElementCommand( new DestroyElementRequest(inputPortPart.resolveSemanticElement(), false) );
    return cmd;
  }

  /**
   * 
   * @param uri
   * @return the Command that creates an InputPort
   */
  private Command createInputPortCommand(String uri) {
    IElementType type = WorkflowElementTypes.IInputPort_2002;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.selectedElement.getDiagramPreferencesHint() );
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest(viewDescriptor);
    Command cmd = this.selectedElement.getCommand( createRequest );
    cmd = cmd.chain( new InputPortAfterCreateCommand(((Collection<IAdaptable>)createRequest.getNewObject()).iterator().next(), uri, this.selectedElement.getEditingDomain()) );
    return cmd;
  }
  
  /**
   * 
   * @param uri
   * @return the Command that creates an OutputPort
   */
  private Command createOutputPortCommand(String uri) {
    IElementType type = WorkflowElementTypes.IOutputPort_2001;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            this.selectedElement.getDiagramPreferencesHint() );
    
    CreateViewAndElementRequest createRequest = new CreateViewAndElementRequest(viewDescriptor);
    Command cmd = this.selectedElement.getCommand( createRequest );
    cmd = cmd.chain( new OutputPortAfterCreateCommand(((Collection<IAdaptable>)createRequest.getNewObject()).iterator().next(), uri, this.selectedElement.getEditingDomain()) );    
    return cmd;
  }  
  
}