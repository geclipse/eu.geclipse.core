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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.ui.edit.policies.OutputPortItemSemanticEditPolicy;

/**
 * @generated
 */
public class OutputPortEditPart extends ShapeNodeEditPart {

  /**
   * @generated
   */
  public static final int VISUAL_ID = 2001;
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
  public OutputPortEditPart( View view ) {
    super( view );
  }

  /**
   * @generated
   */
  @Override
  protected void createDefaultEditPolicies() {
    super.createDefaultEditPolicies();
    installEditPolicy( EditPolicyRoles.SEMANTIC_ROLE,
                       new OutputPortItemSemanticEditPolicy() );
    installEditPolicy( EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy() );
    // XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
    // removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
  }

  /**
   * @generated
   */
  protected LayoutEditPolicy createLayoutEditPolicy() {
    LayoutEditPolicy lep = new LayoutEditPolicy() {

      @Override
      protected EditPolicy createChildEditPolicy( EditPart child ) {
        EditPolicy result = child.getEditPolicy( EditPolicy.PRIMARY_DRAG_ROLE );
        if( result == null ) {
          result = new NonResizableEditPolicy();
        }
        return result;
      }

      @Override
      protected Command getMoveChildrenCommand( Request request ) {
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
    OutputPortFigure figure = new OutputPortFigure();
    return primaryShape = figure;
  }

  /**
   * @generated
   */
  public OutputPortFigure getPrimaryShape() {
    return ( OutputPortFigure )primaryShape;
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
  public class OutputPortFigure extends RectangleFigure {

    /**
     * @generated
     */
    public OutputPortFigure() {
      this.setBackgroundColor( ColorConstants.darkBlue );
      this.setSize( 1, 1 );
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
  }
}
