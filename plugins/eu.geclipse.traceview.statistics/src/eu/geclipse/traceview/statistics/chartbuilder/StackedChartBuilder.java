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

import java.util.Calendar;

import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.Palette;
import org.eclipse.birt.chart.model.attribute.RiserType;
import org.eclipse.birt.chart.model.attribute.impl.PaletteImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.DataSet;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.DateTimeDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.traceview.statistics.Activator;

/**
 * 
 */
public class StackedChartBuilder extends AbstractChartBuilder {
  
  private static Image image = Activator.getImageDescriptor( "icons/obj16/stackedbarchart.gif" ).createImage(); //$NON-NLS-1$
  protected Axis xAxis = null;
  protected Axis yAxis = null;
  private double[][] ySeries = null;
  private String[] names = null;
  private Calendar[] times = null;
  private String[] zSeries = null;

  @Override
  public String getName() {
    return "Stacked Chart";
  }

  @Override
  public void setXSeries( final Object object ) {
    if( object instanceof String[] ) {
      this.names = ( String[] )object;
    } else if( object instanceof Calendar[] ) {
      this.times = ( Calendar[] )object;
    }
  }

  @Override
  public void setYSeries( final Object object ) {
    this.ySeries = ( double[][] )object;
  }

  @Override
  public void setZSeries( final Object object ) {
    if( object instanceof String[] ) {
      this.zSeries = ( String[] )object;
    }
  }

  @Override
  protected void createChart() {
    this.chart = ChartWithAxesImpl.create();
    this.chart.setDimension( ChartDimension.TWO_DIMENSIONAL_LITERAL );
    ( ( ChartWithAxes )this.chart ).setUnitSpacing( 30 );
  }

  @Override
  protected void buildLegend() {
    Legend lg = this.chart.getLegend();
    lg.getText().getFont().setSize( 8 );
  }

  @Override
  protected void buildXAxis() {
    this.xAxis = ( ( ChartWithAxes )this.chart ).getPrimaryBaseAxes()[ 0 ];
    if( this.times != null ) {
      this.xAxis.setType( AxisType.DATE_TIME_LITERAL );
    } else {
      this.xAxis.setType( AxisType.TEXT_LITERAL );
    }
    this.xAxis.getTitle().getCaption().setValue( this.xTitle );
    this.xAxis.getTitle().setVisible( true );
  }

  @Override
  protected void buildYAxis() {
    this.yAxis = ( ( ChartWithAxes )this.chart ).getPrimaryOrthogonalAxis( this.xAxis );
    this.yAxis.getTitle().getCaption().setValue( this.yTitle );
    this.yAxis.getTitle().setVisible( true );
    this.yAxis.getScale().setStepNumber( 8 );
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void buildXSeries() {
    DataSet xDataSet = null;
    if( this.times != null ) {
      DateTimeDataSetImpl.create( this.times );
    } else {
      if( this.names == null ) {
        this.names = new String[ this.ySeries[ 0 ].length ];
        for( int i = 0; i < this.ySeries[ 0 ].length; i++ )
          this.names[ i ] = Integer.toString( i );
      }
      xDataSet = TextDataSetImpl.create( this.names );
    }
    Series xSeries = SeriesImpl.create();
    xSeries.setDataSet( xDataSet );
    SeriesDefinition xSeriesDefinition = SeriesDefinitionImpl.create();
    xSeriesDefinition.getSeries().add( xSeries );
    this.xAxis.getSeriesDefinitions().add( xSeriesDefinition );
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void buildYSeries() {
    SeriesDefinition ySeriesDefinition = SeriesDefinitionImpl.create();
    Palette palette = PaletteImpl.create( 0, false );
    ySeriesDefinition.setSeriesPalette( palette );
    for( int i = 0; i < this.zSeries.length; i++ ) {
      NumberDataSet yDataSet = NumberDataSetImpl.create( this.ySeries[ i ] );
      BarSeries barSeries = ( BarSeries )BarSeriesImpl.create();
      barSeries.setSeriesIdentifier( this.zSeries[ i ] );
      barSeries.setDataSet( yDataSet );
      barSeries.setRiser( RiserType.RECTANGLE_LITERAL );
      barSeries.setStacked( true );
      ySeriesDefinition.getSeries().add( barSeries );
    }
    this.yAxis.getSeriesDefinitions().add( ySeriesDefinition );
  }

  @Override
  public int minHeight() {
    return 100;
  }

  @Override
  public int minWidth() {
    return 20*this.ySeries[0].length;
  }
  
  @Override
  public Image getImage() {
    return image;
  }
}
