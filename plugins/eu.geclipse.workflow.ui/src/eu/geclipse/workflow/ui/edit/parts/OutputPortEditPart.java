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
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Rectangle;
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

    removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
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
    DefaultSizeNodeFigure result = new DefaultSizeNodeFigure( getMapMode().DPtoLP( 10 ),
                                                              getMapMode().DPtoLP( 10 ) );
    result.setDefaultSize( 10, 10 );
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
  
  
  
  /*
   * Copyright (c) 2006 Borland Software Corporation
   * 
   * All rights reserved. This program and the accompanying materials
   * are made available under the terms of the Eclipse Public License v1.0
   * which accompanies this distribution, and is available at
   * http://www.eclipse.org/legal/epl-v10.html
   *
   * Contributors:
   *    Michael Golubev (Borland) - initial API and implementation
   */
  public class OutputPortFigure extends Shape {
    private int[] myCachedPath = new int[8];
    
    public OutputPortFigure() {
 //     this.setBackgroundColor( ColorConstants.lightBlue );
 //     this.setSize( 1, 1 );
    }

    /**
     * @see Shape#fillShape(Graphics)
     */
    protected void fillShape(Graphics graphics) {
      Rectangle r = getBounds();

      int centerX = r.x + r.width / 2;
      int centerY = r.y + r.height / 2;

      setPathPoint(0, centerX, r.y);
      setPathPoint(1, r.x + r.width, centerY);
      setPathPoint(2, centerX, r.y + r.height);
      setPathPoint(2, r.x, centerY);

      graphics.fillRectangle(getBounds());
    }

    /**
     * @see Shape#outlineShape(Graphics)
     */
    protected void outlineShape(Graphics graphics) {
      Rectangle r = getBounds();

      int centerX = r.x + r.width / 2;
      int centerY = r.y + r.height / 2;

      graphics.drawLine(centerX, r.y, r.x + r.width, centerY);
      graphics.drawLine(centerX, r.y, r.x, centerY);
      graphics.drawLine(centerX, r.y + r.height, r.x + r.width, centerY);
      graphics.drawLine(centerX, r.y + r.height, r.x, centerY);
    }

    private void setPathPoint(int index, int x, int y){
      myCachedPath[index * 2] = x;
      myCachedPath[index * 2 + 1] = x;
    }

  }
  
//  
//  /**
//   * @generated
//   */
//  public class OutputPortFigure extends RectangleFigure {
//
//    /**
//     * @generated
//     */
//    public OutputPortFigure() {
//      this.setBackgroundColor( ColorConstants.darkBlue );
//      this.setSize( 1, 1 );
//    }
//    /**
//     * @generated
//     */
//    private boolean myUseLocalCoordinates = false;
//
//    /**
//     * @generated
//     */
//    @Override
//    protected boolean useLocalCoordinates() {
//      return myUseLocalCoordinates;
//    }
//
//    /**
//     * @generated
//     */
//    protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
//      myUseLocalCoordinates = useLocalCoordinates;
//    }
//  }
}
