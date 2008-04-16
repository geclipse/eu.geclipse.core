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
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import eu.geclipse.batch.ui.IWorkerNodeFigure;
import eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState;

/**
 * Holder of a GEF Figure for a Worker Node
 */
public final class WorkerNodeFigure extends Figure implements IWorkerNodeFigure {

  /**
   * The preferred size for this figure
   */
  private static final Dimension SIZE = new Dimension( 100, 40 );

  //private FrameBorder border;
  private TitleBarBorder border;
  
 
  private Label labState;
 
  /**
   * The default constructor.
   */
  public WorkerNodeFigure(){
   setLayoutManager( new ToolbarLayout() );
    
    this.border = new TitleBarBorder();
    this.border.setBackgroundColor( ColorConstants.menuBackground  ); 
    this.border.setTextColor( ColorConstants.menuForeground );
    CompoundBorder iBorder = new CompoundBorder( new SimpleRaisedBorder(), new LineBorder( ColorConstants.white ) );
    this.setBorder( new CompoundBorder( iBorder, this.border ) );
    this.setOpaque( true );
    this.setBackgroundColor( ColorConstants.green );
    this.setForegroundColor( ColorConstants.black );
    this.labState = new Label();
   
    add( this.labState );
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
   * Sets the name of the worker node.
   * @param name The name of the computing element.
   */
  public void setFQDN( final String name ) {
    int idx = name.indexOf( '.' );
    if (-1 != idx)
      this.border.setLabel( name.substring( 0, idx ) );
    else
      this.border.setLabel( name );
  }

  /**
   * Sets the state of this worker node.
   * @param state The state.
   * @param numJobs How many jobs are currently executing on this wn
   */
  public void setState( final WorkerNodeState state, final int numJobs ) {
    this.labState.setText( state.toString() );

    switch (state) {
      case free:
        // Indicate that some of this WNs cpus are being used
        if ( numJobs > 0 )
          this.setBackgroundColor( ColorConstants.darkGreen );
        else
          this.setBackgroundColor( ColorConstants.green );
        break;
      case job_exclusive:
      case busy:
//        this.labState.setText( Messages.getString( "WorkerNodeFigure.Busy" ) ); //$NON-NLS-1$
        this.setBackgroundColor( ColorConstants.yellow );
        break;
      case down:
      case offline:
      case unknown:
//        this.labState.setText( Messages.getString( "WorkerNodeFigure.Down" ) ); //$NON-NLS-1$
        this.setBackgroundColor( ColorConstants.red );
        break;
    }
  }
}
