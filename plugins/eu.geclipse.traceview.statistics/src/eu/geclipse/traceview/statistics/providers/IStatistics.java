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

import eu.geclipse.traceview.ITrace;

/**
 * Interface for a Statistic
 */
public interface IStatistics {

  /**
   * Returns the name of the statistic
   * 
   * @return name
   */
  public String getName();

  /**
   * Returns the description of the statistic
   * 
   * @return description
   */
  public String getDescription();

  /**
   * Returns the title shown in the chart
   * 
   * @return title
   */
  public String getTitle();

  /**
   * Returns the image used as icon in the drop down menu
   * 
   * @return image
   */
  public Image getImage();

  /**
   * Sets the trace used to calculate the statistics
   * 
   * @param trace
   */
  public void setTrace( ITrace trace );

  /**
   * Called after the trace was set to perform initialization and calculation of
   * the statistics
   */
  public void initialize();

  /**
   * Returns the type of the provided x series data
   * 
   * @return label
   */
  public String xAxis();

  /**
   * Returns the type of the provided y series data
   * 
   * @return label
   */
  public String yAxis();

  /**
   * Returns the type of the provided z series data
   * 
   * @return label
   */
  public String zAxis();

  /**
   * Returns the x series data
   * 
   * @return x series data
   */
  public Object getXSeries();

  /**
   * Returns the y series data
   * 
   * @return y series data
   */
  public Object getYSeries();

  /**
   * Returns the z series data
   * 
   * @return z series data
   */
  public Object getZSeries();
}
