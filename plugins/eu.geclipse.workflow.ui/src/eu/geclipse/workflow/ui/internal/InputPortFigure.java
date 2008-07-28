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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;


/**
 * A GEF figure for an InputPort.
 */
public class InputPortFigure extends NodeFigure {
  
  private static final PointList points = new PointList();

  static {
      points.addPoint(1, 11);
      points.addPoint(1, 1);
      points.addPoint(3, 4);
      points.addPoint(5, 7);
      points.addPoint(7, 10);
      points.addPoint(9, 7);
      points.addPoint(11, 4);
      points.addPoint(13, 1);
      points.addPoint(13, 11);
  }   

  private Dimension preferredSize;
  
  /**
   * Constructor
   */
  public InputPortFigure(){
    this.setBackgroundColor( ColorConstants.darkBlue );
    this.setSize( 13, 13 );
  }
  
  /**
   * Creates a new InputPortFigure
   */
  public InputPortFigure(final Dimension prefSize) {
      getBounds().width = prefSize.width; 
      getBounds().height = prefSize.height;
      this.preferredSize = new Dimension(prefSize);
  }

  /**
   * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
   */
  @Override
  public Dimension getPreferredSize(final int wHint, final int hHint) {
    if (this.preferredSize != null) {
      return new Dimension(this.preferredSize);
    }
    return new Dimension(13,13);
  }

  /**
   * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
   */
  @Override
  protected void paintFigure(final Graphics g) {
      final Rectangle r = getBounds().getCopy();
      
      final IMapMode mm = MapModeUtil.getMapMode(this);
      r.translate(mm.DPtoLP(2), mm.DPtoLP(2));
      r.setSize(mm.DPtoLP(11), mm.DPtoLP(9));
  
      //draw a line at the top
      //g.drawLine(r.x-1, r.y-1, r.right(), r.y-1);

      //draw figure
      g.translate(getLocation());
      final PointList outline = points.getCopy();
      mm.DPtoLP(outline);
      g.fillPolygon(outline);
      g.drawPolyline(outline);
      g.translate(getLocation().getNegated());
  }

}