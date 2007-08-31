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
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.actions.ActionGroup;

import eu.geclipse.ui.views.jobdetails.JobDetailsView;


/**
 * Actions for {@link JobDetailsView}
 */
public class JobDetailsViewActions extends ActionGroup {
  static final private String MEMENTOKEY_SHOW_EMPTY_VALUES = "ShowEmptyValues"; //$NON-NLS-1$
  private JobDetailsView view;
  private IAction showEmptyValuesAction;
  private UpdateJobStatusAction updateJobStatusAction;
  
  /**
   * @param view on which actions are placed
   */
  public JobDetailsViewActions( final JobDetailsView view ) {
    super();
    this.view = view;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    super.fillActionBars( actionBars );
    actionBars.getToolBarManager().add( getUpdateJobStatusAction() );
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    super.fillContextMenu( menu );
    menu.add( getShowEmptyValuesAction() );
    menu.add( getUpdateJobStatusAction() );
  }  
  
  /**
   * @return the showEmptyValuesAction
   */
  private IAction getShowEmptyValuesAction() {
    if( this.showEmptyValuesAction == null ) {
      this.showEmptyValuesAction = new Action( Messages.getString("JobDetailsViewActions.showEmptyValues"), IAction.AS_CHECK_BOX ) { //$NON-NLS-1$

        /* (non-Javadoc)
         * @see org.eclipse.jface.action.Action#run()
         */
        @Override
        public void run() {
          getView().refresh();
        }};
    }
    return this.showEmptyValuesAction;
  }

  /**
   * @return true if action "Show Empty Values" is set on 
   */
  public boolean isShowEmptyEnabled() {
    return getShowEmptyValuesAction().isChecked();
  }
  
  private boolean getMementoBoolean( final IMemento currentMemento, final String key ) {
    Integer integer = currentMemento.getInteger( key );
    return integer != null && integer.intValue() != 0;
  }

  /**
   * @param memento memento, from which actions state should be read
   */
  public void readState( final IMemento memento ) {
    getShowEmptyValuesAction().setChecked( getMementoBoolean( memento,
                                                              MEMENTOKEY_SHOW_EMPTY_VALUES ) );    
  }

  /**
   * @param memento to which actions state should be written
   */
  public void saveState( final IMemento memento ) {
    memento.putInteger( MEMENTOKEY_SHOW_EMPTY_VALUES, isShowEmptyEnabled() ? 1 : 0 );    
  }
  
  private UpdateJobStatusAction getUpdateJobStatusAction() {
    if( this.updateJobStatusAction == null ) {
      this.updateJobStatusAction = new UpdateJobStatusAction();
      this.view.getJobSelectionProvider().addSelectionChangedListener( this.updateJobStatusAction );
    }
    return this.updateJobStatusAction;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    this.view.getJobSelectionProvider().removeSelectionChangedListener( getUpdateJobStatusAction() );
    super.dispose();
  }

  
  /**
   * @return the view
   */
  JobDetailsView getView() {
    return this.view;
  }
  
}
