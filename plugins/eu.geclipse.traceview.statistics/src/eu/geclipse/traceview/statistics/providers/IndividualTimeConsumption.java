/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.statistics.providers;

import org.eclipse.swt.graphics.Image;

import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.statistics.Activator;

public class IndividualTimeConsumption implements IStatistics {

  private ITrace trace;
  private double[][] y = null;

  public void setTrace( final ITrace trace ) {
    this.trace = trace;
  }

  public String getName() {
    return "Time Consumption";
  }

  public String getDescription() {
    return "Time consumption of communication and calculation.";
  }

  public String xAxis() {
    return "1";
  }

  public String yAxis() {
    return "2";
  }

  public String zAxis() {
    return "category";
  }

  public void initialize() {
    if( this.y == null ) {
      this.y = new double[ 2 ][];
      int runtime = 0;
      double[] calculation = new double[ this.trace.getNumberOfProcesses() ];
      double[] communication = new double[ this.trace.getNumberOfProcesses() ];
      try {
        for( int j = 0; j < this.trace.getNumberOfProcesses(); j++ ) {
          for( int i = 0; i <= this.trace.getProcess( j )
            .getMaximumLogicalClock(); i++ )
          {
            IPhysicalEvent event = ( IPhysicalEvent )this.trace.getProcess( j )
              .getEventByLogicalClock( i );
            if( i < this.trace.getProcess( j ).getMaximumLogicalClock()
                && event.getPhysicalStartClock() == ( ( IPhysicalEvent )this.trace.getProcess( j )
                  .getEventByLogicalClock( i + 1 ) ).getPhysicalStartClock() )
            {
              continue;
            }
            runtime += event.getPhysicalStopClock()
                       - event.getPhysicalStartClock();
          }
          communication[ j ] = runtime;
          calculation[ j ] = ( ( IPhysicalEvent )this.trace.getProcess( j )
            .getEventByLogicalClock( this.trace.getProcess( j )
              .getMaximumLogicalClock() ) ).getPhysicalStopClock()
                             - runtime;
          runtime = 0;
        }
      } catch( IndexOutOfBoundsException e1 ) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      this.y[ 0 ] = calculation;
      this.y[ 1 ] = communication;
    }
  }

  public Object getXSeries() {
    return null;
  }

  public Object getYSeries() {
    return this.y;
  }

  public Object getZSeries() {
    String[] names = {
      "calculation", "communication"
    };
    return names;
  }

  public String getTitle() {
    return getName();
  }
  
  public Image getImage() {
    // TODO dispose
    return Activator.getImageDescriptor( "icons/obj16/clock.gif" ).createImage(); //$NON-NLS-1$
  }
}
