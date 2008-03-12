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

package eu.geclipse.workflow.ui.internal;

import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;

/**
 * The GEF figure for a Link
 */
public class LinkFigure extends PolylineConnectionEx {

  /**
   * Default Constructor
   */
  public LinkFigure() {
    setTargetDecoration( createTargetDecoration() );
  }

  /**
   * Creates the arrow decoration at the end of a link 
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