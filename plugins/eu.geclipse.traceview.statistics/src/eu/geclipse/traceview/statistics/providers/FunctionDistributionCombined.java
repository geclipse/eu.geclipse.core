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

import java.util.Hashtable;

import org.eclipse.swt.graphics.Image;

import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.statistics.Activator;

/**
 * Statistic provider
 */
public class FunctionDistributionCombined implements IStatistics {

  ITrace trace;
  Hashtable<String, Double> functions;

  public String getName() {
    return "Function Distribution";
  }

  public void setTrace( final ITrace trace ) {
    this.trace = trace;
  }

  public String getDescription() {
    return "Distribution of the called functions.";
  }

  public String xAxis() {
    return "1"; //$NON-NLS-1$
  }

  public String yAxis() {
    return "category"; //$NON-NLS-1$
  }

  public String zAxis() {
    return null;
  }

  public void initialize() {
    this.functions = new Hashtable<String, Double>();
    for( int i = 0; i < this.trace.getNumberOfProcesses(); i++ ) {
      IProcess process = this.trace.getProcess( i );
      for( int j = 0; j <= process.getMaximumLogicalClock(); j++ ) {
        String name = process.getEventByLogicalClock( j ).getName();
        Double value = this.functions.get( name );
        if( value == null ) {
          this.functions.put( name, Double.valueOf( 1 ) );
        } else {
          this.functions.put( name, Double.valueOf( value.intValue() + 1 ) );
        }
      }
    }
  }

  public Object getXSeries() {
    String[] result = this.functions.keySet().toArray( new String[ 0 ] );
    return result;
  }

  public Object getYSeries() {
    Double[] values = this.functions.values().toArray( new Double[ 0 ] );
    double[] dValues = new double[ values.length ];
    // TODO better way
    for( int i = 0; i < values.length; i++ ) {
      dValues[ i ] = values[ i ].doubleValue();
    }
    return dValues;
  }

  public Object getZSeries() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getTitle() {
    return getName();
  }
  
  public Image getImage() {
    // TODO dispose
    return Activator.getImageDescriptor( "icons/obj16/distribution.gif" ).createImage(); //$NON-NLS-1$
  }
}
