/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ 
 *    Contributor(s): UCY (http://www.cs.ucy.ac.cy) 
 *        - Kyriakos Katsaris (kykatsar@gmail.com)
 ******************************************************************************/
package eu.geclipse.batch.ui.internal;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.geometry.Dimension;

import eu.geclipse.batch.ui.IBoxFigure;


public class BoxFigure extends Figure implements IBoxFigure {

  private static final Dimension SIZE = new Dimension( 250, 180 );
  private TitleBarBorder border;
  private Label labBox;

  public BoxFigure() {
    
    FlowLayout layout = new FlowLayout( FlowLayout.VERTICAL);
    layout.setHorizontal( FlowLayout.HORIZONTAL );
    this.setLayoutManager( layout );
    this.border = new TitleBarBorder(); // menuBackground
    this.border.setBackgroundColor( ColorConstants.gray );
    this.border.setTextColor( ColorConstants.menuForeground );
    CompoundBorder iBorder = new CompoundBorder( new SimpleRaisedBorder(),
                                                 new LineBorder( ColorConstants.darkGreen ) );
    
    this.setBorder( new CompoundBorder( iBorder, this.border ) );
    this.setOpaque( true );
    this.setBackgroundColor( ColorConstants.lightGray );
    this.labBox = new Label();
    add( this.labBox );
  }

  @Override
  public Dimension getPreferredSize( final int wHint, final int hHint ) {
    return SIZE;
  }

  public void setName( final String name ) {
    this.border.setLabel( name );
  }
}
