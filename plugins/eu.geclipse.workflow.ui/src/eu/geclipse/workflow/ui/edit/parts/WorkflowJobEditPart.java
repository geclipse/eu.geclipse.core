/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.parts;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LabeledContainer;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
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
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;
import eu.geclipse.workflow.ui.edit.policies.WorkflowJobCanonicalEditPolicy;
import eu.geclipse.workflow.ui.edit.policies.WorkflowJobItemSemanticEditPolicy;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowJobEditPart extends ShapeNodeEditPart {

  /**
   * @generated
   */
  public static final int VISUAL_ID = 1001;
  /**
   * @generated
   */
  protected IFigure contentPane;
  /**
   * @generated
   */
  protected IFigure primaryShape;

  /**
   * @generated
   */
  public WorkflowJobEditPart( View view ) {
    super( view );
  }

  /**
   * @generated
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
    // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
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
      protected Command createMoveChildCommand( EditPart child, EditPart after )
      {
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
   * @generated
   */
  protected IFigure createNodeShape() {
    WorkflowJobFigure figure = new WorkflowJobFigure();
    return primaryShape = figure;
  }

  /**
   * @generated
   */
  public WorkflowJobFigure getPrimaryShape() {
    return ( WorkflowJobFigure )primaryShape;
  }

  /**
   * @generated
   */
  protected boolean addFixedChild( EditPart childEditPart ) {
    if( childEditPart instanceof WorkflowJobNameEditPart ) {
      ( ( WorkflowJobNameEditPart )childEditPart ).setLabel( getPrimaryShape().getFigureWorkflowJobNameFigure() );
      return true;
    }
    return false;
  }

  /**
   * @generated
   */
  protected boolean removeFixedChild( EditPart childEditPart ) {
    return false;
  }

  /**
   * @generated
   */
  @Override
  protected void addChildVisual( EditPart childEditPart, int index ) {
    if( addFixedChild( childEditPart ) ) {
      return;
    }
    super.addChildVisual( childEditPart, -1 );
  }

  /**
   * @generated
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
    DefaultSizeNodeFigure result = new DefaultSizeNodeFigure( getMapMode().DPtoLP( 40 ),
                                                              getMapMode().DPtoLP( 40 ) );
    return result;
  }

  /**
   * Creates figure for this edit part.
   * 
   * Body of this method does not depend on settings in generation model
   * so you may safely remove <i>generated</i> tag and modify it.
   * 
   * @generated
   */
  @Override
  protected NodeFigure createNodeFigure() {
    NodeFigure figure = createNodePlate();
    figure.setLayoutManager( new StackLayout() );
    IFigure shape = createNodeShape();
    figure.add( shape );
    contentPane = setupContentPane( shape );
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
   * @generated
   */
  @Override
  public IFigure getContentPane() {
    if( contentPane != null ) {
      return contentPane;
    }
    return super.getContentPane();
  }

  /**
   * @generated
   */
  @Override
  public EditPart getPrimaryChildEditPart() {
    return getChildBySemanticHint( WorkflowVisualIDRegistry.getType( WorkflowJobNameEditPart.VISUAL_ID ) );
  }
  /**
   * @generated
   */
  public class WorkflowJobFigure extends RoundedRectangle {

    /**
     * @generated
     */
    private WrapLabel fFigureWorkflowJobNameFigure;
    /**
     * @generated
     */
    private WrapLabel fFigureWorkflowJobDescriptionFigure;

    /**
     * @generated
     */
    public WorkflowJobFigure() {
      FlowLayout layoutThis = new FlowLayout();
      layoutThis.setStretchMinorAxis( false );
      layoutThis.setMinorAlignment( FlowLayout.ALIGN_LEFTTOP );
      layoutThis.setMajorAlignment( FlowLayout.ALIGN_LEFTTOP );
      layoutThis.setMajorSpacing( 5 );
      layoutThis.setMinorSpacing( 5 );
      layoutThis.setHorizontal( false );
      this.setLayoutManager( layoutThis );
      this.setCornerDimensions( new Dimension( 20, 20 ) );
      this.setForegroundColor( THIS_FORE );
      createContents();
    }

    /**
     * @generated
     */
    private void createContents() {
      WrapLabel workflowJobNameFigure0 = new WrapLabel();
      workflowJobNameFigure0.setText( "<...>" );
      this.add( workflowJobNameFigure0 );
      fFigureWorkflowJobNameFigure = workflowJobNameFigure0;
      LabeledContainer jobDescriptionContainer0 = new LabeledContainer();
      this.add( jobDescriptionContainer0 );
      WrapLabel workflowJobDescriptionFigure1 = new WrapLabel();
      workflowJobDescriptionFigure1.setText( "<...>" );
      jobDescriptionContainer0.add( workflowJobDescriptionFigure1 );
      fFigureWorkflowJobDescriptionFigure = workflowJobDescriptionFigure1;
    }
    /**
     * @generated
     */
    private boolean myUseLocalCoordinates = false;

    /**
     * @generated
     */
    @Override
    protected boolean useLocalCoordinates() {
      return myUseLocalCoordinates;
    }

    /**
     * @generated
     */
    protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
      myUseLocalCoordinates = useLocalCoordinates;
    }

    /**
     * @generated
     */
    public WrapLabel getFigureWorkflowJobNameFigure() {
      return fFigureWorkflowJobNameFigure;
    }

    /**
     * @generated
     */
    public WrapLabel getFigureWorkflowJobDescriptionFigure() {
      return fFigureWorkflowJobDescriptionFigure;
    }
  }
  /**
   * @generated
   */
  static final Color THIS_FORE = new Color( null, 220, 220, 240 );
}
