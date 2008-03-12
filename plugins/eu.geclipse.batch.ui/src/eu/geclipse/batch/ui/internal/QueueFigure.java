/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;

import eu.geclipse.batch.IQueueInfo.QueueRunState;
import eu.geclipse.batch.IQueueInfo.QueueState;
import eu.geclipse.batch.ui.IQueueFigure;

/**
 * Holder of a GEF Figure for a Queue
 */
public final class QueueFigure extends Figure implements IQueueFigure {
  /**
   * The preferred size for this figure
   */
  private static final Dimension SIZE = new Dimension( 100, 55 );

  private TitleBarBorder border;
  private Label labState;
  private Label labRunState;

  /**
   * The default constructor.
   */
  public QueueFigure(){
    setLayoutManager( new ToolbarLayout() );

    this.border = new TitleBarBorder(); 
    this.border.setBackgroundColor( ColorConstants.menuBackground );
    this.border.setTextColor( ColorConstants.menuForeground );

    CompoundBorder iBorder = new CompoundBorder( new SimpleRaisedBorder(), new LineBorder( ColorConstants.white ) );
    this.setBorder( new CompoundBorder( iBorder, this.border ) );
        
    this.setOpaque( true );
    this.setBackgroundColor( ColorConstants.green );
    this.setForegroundColor( ColorConstants.black );

    this.labState = new Label();
    add( this.labState );
    this.labRunState = new Label();
    add( this.labRunState );
  }

  /**
   * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
   * @param wHint width hint
   * @param hHint height hint
   * @return Returns the preferred dimensions.
   */
  @Override
  public Dimension getPreferredSize( final int wHint, final int hHint ) {
    return SIZE;
  }

  /**
   * Sets the name of the queue.
   * @param name The name of the queue.
   */
  public void setQueueName( final String name ) {
    this.border.setLabel( name );
  }

  /**
   * Sets the state of this queue.
   * @param state The state.
   */
  public void setState( final QueueState state, final QueueRunState runState ) {
    this.labState.setText( state.toString() ); 

    switch ( state ) {
      case E:
        if ( QueueRunState.R == runState ) 
          this.setBackgroundColor( ColorConstants.green );
        else
          this.setBackgroundColor( ColorConstants.yellow );
        break;
      case D:
        if ( QueueRunState.R == runState ) 
          this.setBackgroundColor( ColorConstants.orange );
        else
          this.setBackgroundColor( ColorConstants.red );
        break;
    }
  }

  /**
   * Sets the runState of this queue.
   * @param runState The state.
   */
  public void setRunState( final QueueRunState runState, final QueueState state ) {
    this.labRunState.setText( runState.toString() );  

    switch ( runState ) {
      case R:
        if ( QueueState.E == state ) 
          this.setBackgroundColor( ColorConstants.green );
        else
          this.setBackgroundColor( ColorConstants.orange );
        break;
      case S:
        if ( QueueState.E == state ) 
          this.setBackgroundColor( ColorConstants.yellow );
        else
          this.setBackgroundColor( ColorConstants.red );
        break;
    }
  }

}
