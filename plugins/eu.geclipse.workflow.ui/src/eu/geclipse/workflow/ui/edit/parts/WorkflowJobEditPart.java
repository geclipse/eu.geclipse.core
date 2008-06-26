/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

import eu.geclipse.workflow.ui.internal.WorkflowJobFigure;
import eu.geclipse.workflow.ui.edit.policies.WorkflowJobCanonicalEditPolicy;
import eu.geclipse.workflow.ui.edit.policies.WorkflowJobItemSemanticEditPolicy;

/**
 * The class that connects the Figure and Model of the WorkflowJob
 */
public class WorkflowJobEditPart extends ShapeNodeEditPart {

  /**
   * @generated
   */
  public static final int VISUAL_ID = 1001;

  protected IFigure contentPane;

  protected IFigure primaryShape;

  /**
   * Constructor
   */
  public WorkflowJobEditPart( View view ) {
    super( view );
  }

  /**
   * Creates the edit policies for this EditPart
   */
  @Override
  protected void createDefaultEditPolicies() {
    installEditPolicy( EditPolicyRoles.CREATION_ROLE, new CreationEditPolicy() );
    super.createDefaultEditPolicies();
    installEditPolicy( EditPolicyRoles.SEMANTIC_ROLE,
                       new WorkflowJobItemSemanticEditPolicy() );
    installEditPolicy( EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy() );
    installEditPolicy( EditPolicyRoles.CANONICAL_ROLE,
                       new WorkflowJobCanonicalEditPolicy() );
    installEditPolicy( EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy() );
    // XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
    removeEditPolicy( EditPolicyRoles.CONNECTION_HANDLES_ROLE);
  }

  /**
   * @generated
   */
  protected LayoutEditPolicy createLayoutEditPolicy() {
    FlowLayoutEditPolicy lep = new FlowLayoutEditPolicy() {

      @Override
      protected Command createAddCommand( EditPart child, EditPart after ) {
        return null;
      }

      @Override
      protected Command createMoveChildCommand( EditPart child, EditPart after ) {
        return null;
      }

      @Override
      protected Command getCreateCommand( CreateRequest request ) {
        return null;
      }
    };
    return lep;
  }

  /**
   * Creates the shape of the node
   */
  protected IFigure createNodeShape() {
    WorkflowJobFigure figure = new WorkflowJobFigure();
    return this.primaryShape = figure;
  }

  /**
   * Returns the shape of this figure
   */
  public WorkflowJobFigure getPrimaryShape() {
    return ( WorkflowJobFigure )this.primaryShape;
  }

  /**
   * Add children to this edit part 
   */
  protected boolean addFixedChild( EditPart childEditPart ) {
    if( childEditPart instanceof WorkflowJobNameEditPart ) {
      ( ( WorkflowJobNameEditPart )childEditPart ).setLabel( getPrimaryShape().getFigureWorkflowJobNameFigure() );
      return true;
    }
    return false;
  }

  /**
   * Remove a fixed child
   */
  protected boolean removeFixedChild( EditPart childEditPart ) {
    return false;
  }

  /**
   * Adds the child's visual
   */
  @Override
  protected void addChildVisual( EditPart childEditPart, int index ) {
    if( addFixedChild( childEditPart ) ) {
      return;
    }
    super.addChildVisual( childEditPart, 1 );

//    if ( addChild( childEditPart )) {
//      return;
//    }
//    super.addChildVisual( childEditPart, -1 );

  }

  /**
   * Removes the child's visual
   */
  @Override
  protected void removeChildVisual( EditPart childEditPart ) {
    if( removeFixedChild( childEditPart ) ) {
      return;
    }
    super.removeChildVisual( childEditPart );
  }

  /**
   * @generated
   */
  @Override
  protected IFigure getContentPaneFor( IGraphicalEditPart editPart ) {
    return super.getContentPaneFor( editPart );
  }

  /**
   * @generated
   */
  protected NodeFigure createNodePlate() {
    DefaultSizeNodeFigure result = new DefaultSizeNodeFigure( getMapMode().DPtoLP( 100 ),
                                                              getMapMode().DPtoLP( 50 ) );
    return result;
  }

  /**
   * Creates figure for this edit part.
   *
   */
  @Override
  protected NodeFigure createNodeFigure() {
    NodeFigure figure = createNodePlate();
    figure.setLayoutManager( new StackLayout() );
    IFigure shape = createNodeShape();
    figure.add( shape );
    this.contentPane = setupContentPane( shape );
    return figure;
  }

  /**
   * Default implementation treats passed figure as content pane.
   * Respects layout one may have set for generated figure.
   * @param nodeShape instance of generated figure class
   * @generated
   */
  protected IFigure setupContentPane( IFigure nodeShape ) {
    if( nodeShape.getLayoutManager() == null ) {
      ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
      layout.setSpacing( getMapMode().DPtoLP( 5 ) );
      nodeShape.setLayoutManager( layout );
    }
    return nodeShape; // use nodeShape itself as contentPane
  }

  /**
   * Returns the content pane
   */
  @Override
  public IFigure getContentPane() {
    if( this.contentPane != null ) {
      return this.contentPane;
    }
    return super.getContentPane();
  }


//  @Override
//  public EditPart getPrimaryChildEditPart() {
//    return getChildBySemanticHint( WorkflowVisualIDRegistry.getType( WorkflowJobDescriptionEditPart.VISUAL_ID ) );
//  }

  

}
