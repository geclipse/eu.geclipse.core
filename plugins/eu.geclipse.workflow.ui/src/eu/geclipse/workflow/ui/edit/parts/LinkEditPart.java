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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.ui.edit.policies.LinkItemSemanticEditPolicy;

/**
 * @generated
 */
public class LinkEditPart extends ConnectionNodeEditPart {

  /**
   * @generated
   */
  public static final int VISUAL_ID = 3001;

  /**
   * @generated
   */
  public LinkEditPart( View view ) {
    super( view );
  }

  /**
   * @generated
   */
  @Override
  protected void createDefaultEditPolicies() {
    super.createDefaultEditPolicies();
    installEditPolicy( EditPolicyRoles.SEMANTIC_ROLE,
                       new LinkItemSemanticEditPolicy() );
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
  protected Connection createConnectionFigure() {
    return new LinkFigure();
  }
  /**
   * @generated
   */
  public class LinkFigure extends PolylineConnectionEx {

    /**
     * @generated
     */
    public LinkFigure() {
      setTargetDecoration( createTargetDecoration() );
    }

    /**
     * @generated
     */
    private RotatableDecoration createTargetDecoration() {
      PolylineDecoration df = new PolylineDecoration();
      PointList pl = new PointList();
      pl.addPoint( -1, 1 );
      pl.addPoint( 0, 0 );
      pl.addPoint( -1, -1 );
      df.setTemplate( pl );
      df.setScale( 7, 3 );
      return df;
    }
  }
}
