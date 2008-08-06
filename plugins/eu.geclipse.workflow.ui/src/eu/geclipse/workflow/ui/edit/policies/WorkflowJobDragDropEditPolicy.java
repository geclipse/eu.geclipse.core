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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.ui.edit.commands.JSDLDropCommand;
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
    for( Object o : objects ) {
      JSDLDropCommand dropCmd = null;
      JSDLJobDescription jsdl = null;
      if( o instanceof JSDLJobDescription ) {
        jsdl = ( JSDLJobDescription )o;
        this.selectedElement = ( WorkflowJobEditPart )getHost();
        dropCmd = new JSDLDropCommand( this.selectedElement.resolveSemanticElement(), ((EObject)this.selectedElement.getModel()).eResource(), jsdl);
      }
      
      Command cmd = new ICommandProxy(dropCmd);
      
      // this bit reads staging in and out to determine what in/out ports are needed
      // on a WF job    
      Map<String, String> m = jsdl.getDataStagingInStrings();
      Set<String> s = m.keySet();
      for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
        String filename = i.next();
        cmd = cmd.chain( createInputPortCommand() );
      }      
      m = jsdl.getDataStagingOutStrings();
      s = m.keySet();
      for (Iterator<String> i = s.iterator(); i.hasNext(); ) {
        String filename = i.next();
        cmd = cmd.chain( createOutputPortCommand() );
      }
//      if (jsdl.getParent() instanceof IGridWorkflow) {
//        System.out.println("All children jobs are...");
//        IGridWorkflow wf = ( IGridWorkflow )jsdl.getParent();
//        List<IGridWorkflowJob> jobs = wf.getChildrenJobs();
//        for (int i=0;i<jobs.size();i++) {
//          IGridWorkflowJob job = jobs.get( i );
//          System.out.println(job.getName() + "; " + job.getDescription());
//          // read in as JSDLJobDescription to get and build/compare semantic links??
//        }
//        System.out.println("so we can try match in/out stages to links now, right??");
//      }
        return cmd;
      }
    return super.getDropObjectsCommand( dropRequest );
  }
  
  private Command createInputPortCommand() {
    WorkflowJobEditPart selectedElement = ( WorkflowJobEditPart )getHost();
    IElementType type = WorkflowElementTypes.IInputPort_2002;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            selectedElement.getDiagramPreferencesHint() );
    
    Command cmd = selectedElement.getCommand( new CreateViewAndElementRequest(viewDescriptor) );
    return cmd;
  }
  
  private Command createOutputPortCommand() {
    WorkflowJobEditPart selectedElement = ( WorkflowJobEditPart )getHost();
    IElementType type = WorkflowElementTypes.IOutputPort_2001;
    ViewAndElementDescriptor viewDescriptor = new ViewAndElementDescriptor( new CreateElementRequestAdapter( new CreateElementRequest( type ) ),
                                                                            Node.class,
                                                                            ( ( IHintedType )type ).getSemanticHint(),
                                                                            selectedElement.getDiagramPreferencesHint() );
    
    Command cmd = selectedElement.getCommand( new CreateViewAndElementRequest(viewDescriptor) );
    return cmd;
  }  
  
}