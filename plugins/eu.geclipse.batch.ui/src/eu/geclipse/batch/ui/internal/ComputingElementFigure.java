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

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;

import eu.geclipse.batch.ui.IComputingElementFigure;

/**
 * Holder of a GEF Figure for a Computing Element
 */
public class ComputingElementFigure extends Figure implements IComputingElementFigure {

  /**
   * The preferred size for this figure
   */
  private static final Dimension SIZE = new Dimension( 140, 90 );

  private TitleBarBorder border;
  private Label labType;
  private Label labQueues;
  private Label labWNs;
  private Label labJobs;

  /**
   * The default constructor.
   */
  public ComputingElementFigure( ) {
    setLayoutManager( new ToolbarLayout() );

    this.border = new TitleBarBorder(); 
    this.border.setBackgroundColor( ColorConstants.menuBackground );
    this.border.setTextColor( ColorConstants.menuForeground );

    CompoundBorder iBorder = new CompoundBorder( new SimpleRaisedBorder(), new LineBorder( ColorConstants.white ) );
    this.setBorder( new CompoundBorder( iBorder , this.border ) );    
    
    this.setOpaque( true );
    this.setBackgroundColor( ColorConstants.blue );
    this.setForegroundColor( ColorConstants.yellow );

    this.labType = new Label();
    add( this.labType );

    this.labQueues = new Label();
    add( this.labQueues );

    this.labWNs = new Label();
    add( this.labWNs );

    this.labJobs = new Label();
    add( this.labJobs );
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
   * Sets the name of the computing element.
   * @param name The name of the computing element.
   */
  public void setFQDN( final String name ) {

    // Update the border
    this.border.setLabel( name );
  }

  /**
   * Sets the type of the batch service.
   * @param type The batch service type.
   */
  public void setType( final String type ) {
    this.labType.setText( Messages.getString( "ComputingElementFigure.ServiceType" ) + type ); //$NON-NLS-1$
  }

  /**
   * Sets the number of queues, that this ce controls.
   * @param queues The number of queues.
   */
  public void setNumQueues( final int queues ) {
    this.labQueues.setText( Messages.getString( "ComputingElementFigure.NumOfQueues" ) + queues ); //$NON-NLS-1$
  }

  /**
   * Sets the number of worker nodes, that this ce controls.
   * @param wns The number of worker nodes.
   */
  public void setNumWNs( final int wns ) {
    this.labWNs.setText( Messages.getString( "ComputingElementFigure.NumOfWns" ) + wns ); //$NON-NLS-1$
  }

  /**
   * Sets the number of jobs, that this ce currently handles.
   * @param jobs The number of jobs.
   */
  public void setNumJobs( final int jobs ) {
    this.labJobs.setText( Messages.getString( "ComputingElementFigure.NumOfJobs" ) + jobs ); //$NON-NLS-1$
  }
}
