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

public interface IStatistics {

  public void setTrace( ITrace trace );

  public void initialize();
  
  public String getName();

  public String getDescription();
  
  public Object getXSeries();
  public Object getYSeries();
  public Object getZSeries();

  public String xAxis();

  public String yAxis();

  public String zAxis();
  
  public String getTitle();
  
  public Image getImage();
}
