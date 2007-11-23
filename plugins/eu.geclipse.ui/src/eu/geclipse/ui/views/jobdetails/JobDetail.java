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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.jobdetails;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.core.model.IGridJob;

/**
 * Abstract implementation of job detail
 */
abstract public class JobDetail implements IJobDetail {

  private IJobDetailsSection section;

  /**
   * @param section in which this detail should be placed
   */
  public JobDetail( final IJobDetailsSection section ) {
    super();
    this.section = section;
  }

  public IJobDetailsSection getSection() {
    return this.section;
  }

  /**
   * @return false if widget wasn't created yet
   */
  abstract protected boolean isWidgetCreated();

  /**
   * Creates widgets
   * 
   * @param parent
   */
  abstract protected void createWidgets( final Composite parent,
                                         final FormToolkit formToolkit );

  /**
   * Refresh detail data in widget using passed job
   * 
   * @param gridJob job, from which data should be obtained for detail
   * @param viewConfiguration
   */
  abstract protected boolean refresh( final IGridJob gridJob,
                                      IViewConfiguration viewConfiguration );

  public boolean refresh( final IGridJob gridJob,
                          final Composite parent,
                          final IViewConfiguration viewConfiguration )
  {
    if( !isWidgetCreated() ) {
      createWidgets( parent, viewConfiguration.getFormToolkit() );
    }
    return refresh( gridJob, viewConfiguration );
  }

  protected void setVisible( final Control control, final boolean visible ) {
    control.setVisible( visible );
    GridData gridData = ( GridData )control.getLayoutData();
    if( gridData == null ) {
      gridData = new GridData();
      control.setLayoutData( gridData );
    }
    gridData.exclude = !visible;
  }
  
  protected IGridJob getInputJob() {
    return this.section.getInputJob();
  }
}
