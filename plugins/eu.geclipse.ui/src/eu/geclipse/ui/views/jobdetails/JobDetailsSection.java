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

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import eu.geclipse.core.model.IGridJob;

/**
 *
 */
public class JobDetailsSection implements IJobDetailsSection {

  private String name;
  private int order;
  private Section sectionWidget;
  private List<IJobDetail> details = new LinkedList<IJobDetail>();
  private IViewConfiguration viewConfiguration;

  /**
   * @param name section name
   * @param order value used to sort sections before creation
   * @param viewConfiguration the configuration of view
   */
  public JobDetailsSection( final String name, final int order, final IViewConfiguration viewConfiguration ) {
    super();
    this.name = name;
    this.order = order;
    this.viewConfiguration = viewConfiguration;
  }

  public int getOrder() {
    return this.order;
  }

  public void refresh( final IGridJob gridJob,
                       final Composite parent )
  {
    boolean atLeastOneDetailSpecified = false;
    if( !isWidgetCreated() ) {
      createWidgets( parent, this.viewConfiguration.getFormToolkit() );
    }
    for( IJobDetail detail : this.details ) {
      atLeastOneDetailSpecified |= detail.refresh( gridJob,
                                                   ( Composite )this.sectionWidget.getClient(),
                                                   this.viewConfiguration );
    }
    setVisible( this.viewConfiguration.isShowEmptyEnabled()
                || atLeastOneDetailSpecified );
  }

  private void createWidgets( final Composite parent,
                              final FormToolkit formToolkit )
  {
    this.sectionWidget = formToolkit.createSection( parent,
                                                    ExpandableComposite.TWISTIE
                                                        | ExpandableComposite.TITLE_BAR );
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.verticalAlignment = SWT.TOP;
    this.sectionWidget.setLayoutData( gridData );
    this.sectionWidget.setText( this.name );
    this.sectionWidget.setExpanded( true );
    Composite clientComposite = formToolkit.createComposite( this.sectionWidget );
    clientComposite.setLayout( new GridLayout( 2, false ) );
    this.sectionWidget.setClient( clientComposite );
  }

  private boolean isWidgetCreated() {
    return this.sectionWidget != null && !this.sectionWidget.isDisposed();
  }

  public void addDetail( final IJobDetail detail ) {
    IJobDetail oldDetail = null;
    ListIterator<IJobDetail> iterator = this.details.listIterator();
    while( iterator.hasNext() && oldDetail == null ) {
      IJobDetail curDetail = iterator.next();
      if( curDetail.getId().equals( detail.getId() )
          && curDetail.getClass().equals( detail.getClass() ) )
      {
        oldDetail = curDetail;
      }
    }
    if( oldDetail == null ) {
      this.details.add( detail );
    } else {
      detail.reuseWidgets( oldDetail );
      iterator.set( detail );
    }
  }

  public void removeDetail( final IJobDetail detail ) {
    this.details.remove( detail );
  }

  public void dispose() {
    if( isWidgetCreated() ) {
      this.sectionWidget.dispose();
      this.sectionWidget = null;
    }
  }

  private void setVisible( final boolean visible ) {
    this.sectionWidget.setVisible( visible );
    GridData gridData = ( GridData )this.sectionWidget.getLayoutData();
    gridData.exclude = !visible;
  }

  public List<IJobDetail> getDetails() {
    return this.details;
  }

  public IGridJob getInputJob() {
    return this.viewConfiguration.getInputJob();
  }
}
