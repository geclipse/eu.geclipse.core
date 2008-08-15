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

  public abstract String getName();

  /**
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

  protected abstract void createChart();

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

  public abstract int minWidth();

  public abstract int minHeight();

  /**
   * Returns the built chart
   * 
   * @return Chart
   */
  public Chart getChart() {
    return this.chart;
  }
  
  public abstract Image getImage();
}
