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

import eu.geclipse.workflow.ui.internal.InputPortFigure;
import eu.geclipse.workflow.ui.edit.policies.InputPortItemSemanticEditPolicy;

/**
 * The class that connects the Figure and Model of the InputPort.
 */
public class InputPortEditPart extends ShapeNodeEditPart {

  /**
   * @generated
   */
  public static final int VISUAL_ID = 2002;
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
  public InputPortEditPart( View view ) {
    super( view );
  }

  /**
   * Creates the edit policies for this editpart
   */
  @Override
  protected void createDefaultEditPolicies() {
    super.createDefaultEditPolicies();
    installEditPolicy( EditPolicyRoles.SEMANTIC_ROLE,
                       new InputPortItemSemanticEditPolicy() );
    installEditPolicy( EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy() );

    removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);

//    installEditPolicy( EditPolicy.PRIMARY_DRAG_ROLE,
//                       new NonResizableEditPolicy() {
//
//                         @Override
//                         public Command getCommand( Request request )
//                         {
//                           return null;
//                         }
//
//                         @Override
//                         public boolean understandsRequest( Request request )
//                         {
//                           return false;
//                         }
//                       } );  
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
    return primaryShape = new InputPortFigure();
  }

  /**
   * @generated
   */
  public InputPortFigure getPrimaryShape() {
    return ( InputPortFigure )primaryShape;
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

}
