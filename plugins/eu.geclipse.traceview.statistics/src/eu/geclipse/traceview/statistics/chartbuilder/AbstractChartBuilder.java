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

package eu.geclipse.traceview.statistics.chartbuilder;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.swt.graphics.Image;

/**
 * Abstract Chart Builder
 */
public abstract class AbstractChartBuilder {

  private final static String emptyString = ""; //$NON-NLS-1$
  protected Chart chart = null;
  protected String chartTitle = emptyString;
  protected String xTitle = emptyString;
  protected String yTitle = emptyString;

  /**
   * Returns the Name of the Chart
   * 
   * @return name
   */
  public abstract String getName();

  /**
   * Returns the icon for the chart
   * 
   * @return image
   */
  public abstract Image getImage();

  /**
   * Returns the built chart
   * 
   * @return Chart
   */
  public Chart getChart() {
    return this.chart;
  }

  /**
   * Returns the minimum with to display the chart correctly
   * 
   * @return minimum width
   */
  public abstract int minWidth();

  /**
   * Returns the minimum height to display the chart correctly
   * 
   * @return minimum height
   */
  public abstract int minHeight();

  protected abstract void createChart();

  /**
   * Sets the title of the chart
   * 
   * @param title
   */
  public void setTitle( final String title ) {
    this.chartTitle = title;
  }

  /**
   * Sets the title for the X axis.
   * 
   * @param title
   */
  public void setXAxisTitle( final String title ) {
    this.xTitle = title;
  }

  /**
   * Sets the title for the y axis.
   * 
   * @param title
   */
  public void setYAxisTitle( final String title ) {
    this.yTitle = title;
  }

  /**
   * Set the X Series
   * 
   * @param object - an array of values
   */
  public abstract void setXSeries( final Object object );

  /**
   * Set the Y Series
   * 
   * @param object - an array of values
   */
  public abstract void setYSeries( final Object object );

  /**
   * Set the Z Series
   * 
   * @param object - an array of values
   */
  public abstract void setZSeries( final Object object );

  /**
   * Build the Chart
   */
  public void build() {
    createChart();
    buildPlot();
    buildLegend();
    buildTitle();
    buildXAxis();
    buildYAxis();
    buildXSeries();
    buildYSeries();
  }

  protected void buildPlot() {
    // empty
  }

  protected void buildXAxis() {
    // empty
  }

  protected void buildYAxis() {
    // empty
  }

  protected void buildXSeries() {
    // empty
  }

  protected void buildYSeries() {
    // empty
  }

  protected void buildLegend() {
    // empty
  }

  protected void buildTitle() {
    this.chart.getTitle().getLabel().getCaption().setValue( this.chartTitle );
    this.chart.getTitle().getLabel().getCaption().getFont().setSize( 16 );
  }
}
