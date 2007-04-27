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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import eu.geclipse.core.model.IGridJob;


/**
 * View showing details about submitted job
 */
public class JobDetailsView extends ViewPart {

  /**
   * View identifier
   */
  static final public String ID = "eu.geclipse.ui.views.jobdetails.JobDetailsView"; //$NON-NLS-1$
  private List<ISection<? extends IGridJob>> sectionsList;
  private FormToolkit formToolkit;
  private Composite topComposite;

  /**
   * 
   */
  public JobDetailsView() {
    super();
  }

  List<ISection<? extends IGridJob>> getSections() {
    if( this.sectionsList == null ) {
      this.sectionsList = new ArrayList<ISection<? extends IGridJob>>();
      this.sectionsList.add( new SectionGeneral() );
      this.sectionsList.add( new SectionDescription() );
    }
    return this.sectionsList;
  }

  /**
   * This method initializes formToolkit
   * 
   * @return org.eclipse.ui.forms.widgets.FormToolkit
   */
  private FormToolkit getFormToolkit() {
    if( this.formToolkit == null ) {
      this.formToolkit = new FormToolkit( Display.getCurrent() );
    }
    return this.formToolkit;
  }

  @Override
  public void createPartControl( final Composite parentComposite )
  {
    this.topComposite = getFormToolkit().createComposite( parentComposite );
    this.topComposite.setLayout( new GridLayout( 2, false ) );
    this.topComposite.setBackground( getFormToolkit().getColors()
      .getBackground() );
    for( ISection<? extends IGridJob> section : getSections() ) {
      section.createWidgets( this.topComposite, getFormToolkit() );
    }
    this.topComposite.layout();
  }

  @Override
  public void setFocus()
  {
    // TODO Auto-generated method stub
  }

  /**
   * @param gridJob object containg job data
   */
  public void setInputJob( final IGridJob gridJob ) {
    refresh( gridJob );
  }

  @SuppressWarnings("unchecked")
  private void refresh( final IGridJob gridJob )
  {
    setPartName( gridJob.getName() == null
                                          ? Messages.JobDetailsView_name
                                          : gridJob.getName() );
    for( ISection section : this.getSections() ) {
      section.refresh( gridJob );
    }
    if( this.topComposite != null ) {
      this.topComposite.layout();
    }
  }
}
