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

import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.impl.PaletteImpl;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.traceview.statistics.Activator;

/**
 * Pie Chart Builder
 */
public class PieChartBuilder extends AbstractChartBuilder {

  private static Image image = Activator.getImageDescriptor( "icons/obj16/piechart.gif" ).createImage(); //$NON-NLS-1$
  private String[] names = null;
  private double[] ySeries = null;

  @Override
  public String getName() {
    return "Pie Chart";
  }

  @Override
  public void setYSeries( final Object object ) {
    if( object instanceof Double[] || object instanceof double[] ) {
      this.ySeries = ( double[] )object;
    }
  }

  @Override
  public void setXSeries( final Object object ) {
    if( object instanceof String[] ) {
      this.names = ( String[] )object;
    }
  }

  @Override
  protected void createChart() {
    this.chart = ChartWithoutAxesImpl.create();
    this.chart.setDimension( ChartDimension.TWO_DIMENSIONAL_LITERAL );
  }

  @Override
  protected void buildPlot() {
    // empty
  }

  @Override
  protected void buildLegend() {
    Legend lg = this.chart.getLegend();
    lg.setPosition( Position.RIGHT_LITERAL );
    lg.getText().getFont().setSize( 8 );
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void buildXSeries() {
    SeriesDefinition seriesDefinitionX = null;
    TextDataSet categoryValues = TextDataSetImpl.create( this.names );
    Series seCategory = SeriesImpl.create();
    seCategory.setDataSet( categoryValues );
    seriesDefinitionX = SeriesDefinitionImpl.create();
    Palette palette = PaletteImpl.create( 0, false );
    seriesDefinitionX.setSeriesPalette( palette );
    ( ( ChartWithoutAxes )( this.chart ) ).getSeriesDefinitions()
      .add( seriesDefinitionX );
    seriesDefinitionX.getSeries().add( seCategory );
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void buildYSeries() {
    SeriesDefinition seriesDefinitionY = null;
    seriesDefinitionY = SeriesDefinitionImpl.create();
    SeriesDefinition seriesDefinition = ( SeriesDefinition )( ( ChartWithoutAxes )this.chart ).getSeriesDefinitions()
      .get( 0 );
    seriesDefinition.getSeriesDefinitions().add( seriesDefinitionY );
    NumberDataSet orthoValuesDataSet = NumberDataSetImpl.create( this.ySeries );
    PieSeries pieSeries = ( PieSeries )PieSeriesImpl.create();
    pieSeries.setDataSet( orthoValuesDataSet );
    pieSeries.setExplosion( 5 );
    seriesDefinitionY.getSeries().add( pieSeries );
  }

  @Override
  public void setZSeries( final Object object ) {
    // emtpy
  }

  @Override
  public int minHeight() {
    return 200;
  }

  @Override
  public int minWidth() {
    // TODO Auto-generated method stub
    return 400;
  }
  
  @Override
  public Image getImage() {
    return image;
  }
}
