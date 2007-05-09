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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.ui.views.jobdetails.jsdl.SectionRequirements;

/**
 * View showing details about submitted job
 */
public class JobDetailsView extends ViewPart implements IViewConfiguration {

  /**
   * View identifier
   */
  static final public String ID = "eu.geclipse.ui.views.jobdetails.JobDetailsView"; //$NON-NLS-1$
  protected IGridJob gridJob;
  private List<ISection> sectionsList;
  private FormToolkit formToolkit;
  private Composite topComposite;
  private IAction showEmptyValuesAction;
  private Label emptyJobDescLabel;

  /**
   * 
   */
  public JobDetailsView() {
    super();
  }

  List<ISection> getSections() {
    if( this.sectionsList == null ) {
      this.sectionsList = new ArrayList<ISection>();
      this.sectionsList.add( new SectionGeneral( this ) );
      this.sectionsList.add( new SectionDescription( this ) );
      this.sectionsList.add( new SectionRequirements( this ) );
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
    registerContextMenu();
    this.topComposite = getFormToolkit().createComposite( parentComposite );
    this.topComposite.setLayout( new GridLayout( 2, false ) );
    this.topComposite.setBackground( getFormToolkit().getColors()
      .getBackground() );
    for( ISection section : getSections() ) {
      section.createWidgets( this.topComposite, getFormToolkit() );
    }
    setInputJob( findJob() );
  }

  @Override
  public void setFocus()
  {
    // empty implementation
  }

  /**
   * @param job object containg job data
   */
  public void setInputJob( final IGridJob job ) {
    this.gridJob = job;
    refresh();
  }

  protected void refresh() {
    refreshEmptyJobDesc();
    setPartName( this.gridJob == null || this.gridJob.getName() == null
                                                                       ? Messages.JobDetailsView_name
                                                                       : this.gridJob.getName() );
    for( ISection section : this.getSections() ) {
      section.refresh( this.gridJob );
    }
    if( this.topComposite != null ) {
      this.topComposite.layout();
    }
  }

  private void registerContextMenu() {
    IMenuManager manager = getViewSite().getActionBars().getMenuManager();
    manager.add( createShowEmptyAction() );
  }

  IAction createShowEmptyAction() {
    this.showEmptyValuesAction = new Action( Messages.JobDetailsView_actionShowEmptyVals,
                                             IAction.AS_CHECK_BOX )
    {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.action.Action#run()
       */
      @Override
      public void run()
      {
        refresh();
      }
    };
    return this.showEmptyValuesAction;
  }

  public boolean isShowEmptyEnabled() {
    return this.showEmptyValuesAction.isChecked();
  }

  private void refreshEmptyJobDesc() {
    if( this.gridJob == null && this.emptyJobDescLabel == null ) {
      this.emptyJobDescLabel = getFormToolkit().createLabel( this.topComposite,
                                                             Messages.JobDetailsView_EmptyJobDesc );
    } else if( this.gridJob != null && this.emptyJobDescLabel != null ) {
      this.emptyJobDescLabel.dispose();
      this.emptyJobDescLabel = null;
    }
  }

  private IGridJob findJob() {
    IGridJob foundJob = null;
    IViewSite viewSite = getViewSite();
    if( viewSite != null ) {
      String jobPathString = viewSite.getSecondaryId();
      if( jobPathString != null ) {
        IPath path = new Path( jobPathString );
        IGridElement element = GridModel.getRoot().findElement( path );
        if( element != null ) {
          if( element instanceof IGridJob ) {
            foundJob = ( IGridJob )element;
          }
        }
      }
    }
    return foundJob;
  }
}
