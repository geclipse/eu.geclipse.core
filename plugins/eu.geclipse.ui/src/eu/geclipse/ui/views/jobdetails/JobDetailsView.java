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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatusListener;
import eu.geclipse.ui.internal.actions.JobDetailsViewActions;


/**
 *
 */
public class JobDetailsView extends ViewPart
implements ISelectionListener, IViewConfiguration, IGridJobStatusListener
 {
  /**
   * View identifier
   */
  static final public String ID = "eu.geclipse.ui.views.jobdetails.JobDetailsView"; //$NON-NLS-1$
 
  private ScrolledForm topScrolledForm;
  private FormToolkit formToolkit;
  private JobDetailSectionsManager sectionsManager;  
  private IMemento memento;
  private JobDetailsViewActions actions;  
  private JobSelectionProvider jobSelectionProvider;
  private Label emptyJobDescription;
  private IGridJob inputJob;

  /**
   * 
   */
  public JobDetailsView() {
    // empty implementation
  }

  /**
   * This method initializes formToolkit
   * 
   * @return org.eclipse.ui.forms.widgets.FormToolkit
   */
  public FormToolkit getFormToolkit() {
    if( this.formToolkit == null ) {
      this.formToolkit = new FormToolkit( Display.getCurrent() );
    }
    return this.formToolkit;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl( final Composite parent ) {
    fillActionBars();
    fillContextMenu();
    readState();
    this.topScrolledForm = getFormToolkit().createScrolledForm( parent );
    
    // bug #210764
    // Fix system default font for this control, to avoid 
    // checking by FormToolkit system fonts during creating children of this control 
    this.topScrolledForm.getBody().setFont( this.topScrolledForm.getBody().getFont() );
    
    this.topScrolledForm.getBody().setLayout( new GridLayout( 2, false ) );
    this.topScrolledForm.setBackground( getFormToolkit().getColors()
      .getBackground() );
    getSite().getPage().addSelectionListener( this );
    setInputJob( findSelectedJob() );    
    GridModel.getJobManager().addJobStatusListener( this );
  }

  @Override
  public void dispose() {
    getSite().getPage().removeSelectionListener( this );    
    GridModel.getJobManager().removeJobStatusListener( this );
    super.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    this.topScrolledForm.setFocus();
  }

  /**
   * Set job, which should be shown in view
   * 
   * @param gridJob
   */
  protected void setInputJob( final IGridJob gridJob ) {
    this.inputJob = gridJob;        
    refresh();
    getJobSelectionProvider().fireSelectionChanged();
  }

  /**
   * Refresh whole view using current input job
   */
  public void refresh() {
    refreshEmptyJobDescription();
    getSectionsManager().refresh( this.inputJob );    
    this.topScrolledForm.reflow( true );
  }

  private IGridJob findSelectedJob() {
    IGridJob selectedJob = null;
    ISelection selection = getSite().getPage().getSelection();
    if( selection != null && selection instanceof IStructuredSelection ) {
      Object selectedObj = ( ( IStructuredSelection )selection ).getFirstElement();
      if( selectedObj != null && selectedObj instanceof IGridJob ) {
        selectedJob = ( IGridJob )selectedObj;
      }
    }
    return selectedJob;
  }

  public void selectionChanged( final IWorkbenchPart part,
                                final ISelection selection )
  {
    if( selection != null && selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      if( !structuredSelection.isEmpty() ) {
        Object selectedObject = structuredSelection.getFirstElement();
        if( selectedObject instanceof IGridJob ) {
          IGridJob selectedJob = ( IGridJob )selectedObject;
          setInputJob( selectedJob ); 
          
        }
      }
    }    
  }

  public boolean isShowEmptyEnabled() {
    return getActions().isShowEmptyEnabled();
  }

  public JobDetailSectionsManager getSectionsManager() {
    if( this.sectionsManager == null ) {
      this.sectionsManager = new JobDetailSectionsManager( this.topScrolledForm.getBody(), this );
    }
    return this.sectionsManager;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite,
   *      org.eclipse.ui.IMemento)
   */
  @Override
  public void init( final IViewSite site, final IMemento currentMemento )
    throws PartInitException
  {
    this.memento = currentMemento;
    super.init( site, currentMemento );
  }

  private void readState() {
    if( this.memento != null ) {
      getActions().readState( this.memento );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
   */
  @Override
  public void saveState( final IMemento currentMemento ) {
    super.saveState( currentMemento );
    
    getActions().saveState( currentMemento );
  }

  /**
   * Refresh view - may be called from any thread
   * 
   */
  private void scheduleRefresh() {
    IWorkbenchPartSite site = this.getSite();
    if( site != null ) {
      Shell shell = site.getShell();
      if( shell != null ) {
        Display display = shell.getDisplay();
        if( display != null && !display.isDisposed() ) {
          display.asyncExec( new Runnable() {

            public void run() {
              refresh();
            }
          } );
        }
      }
    }
  }  
  
  public void statusChanged( final IGridJob job ) {
    if( this.inputJob == job ) {
      scheduleRefresh();
    }
  }
  
  public void statusUpdated( final IGridJob job ) {
    if( this.inputJob == job ) {
      scheduleRefresh();
    }
  }


  private JobDetailsViewActions getActions() {
    if( this.actions == null ) {
      this.actions = new JobDetailsViewActions( this );
    }
    return this.actions;
  }

  private void fillActionBars() {
    IActionBars actionBars = getViewSite().getActionBars();
    getActions().fillActionBars( actionBars );
    
  }

  private void fillContextMenu() {
    IMenuManager manager = getViewSite().getActionBars().getMenuManager();
    getActions().fillContextMenu( manager );    
  }
  
  /**
   * Selection provider handling currently shown job in this view
   */
  public class JobSelectionProvider implements ISelectionProvider {

    private List<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

    public void addSelectionChangedListener( final ISelectionChangedListener listener )
    {
      if( !this.selectionChangedListeners.contains( listener ) ) {
        this.selectionChangedListeners.add( listener );
      }
    }

    public ISelection getSelection() {
      return getInputJob() == null
                             ? new StructuredSelection()
                             : new StructuredSelection( getInputJob() );
    }

    public void removeSelectionChangedListener( final ISelectionChangedListener listener )
    {
      this.selectionChangedListeners.remove( listener );
    }

    public void setSelection( final ISelection selection ) {
      // empty implementation
    }

    void fireSelectionChanged() {
      SelectionChangedEvent event = new SelectionChangedEvent( this,
                                                               getSelection() );
      for( ISelectionChangedListener listener : this.selectionChangedListeners )
      {
        listener.selectionChanged( event );
      }
    }
  }  
  
  /**
   * @return selection provides currently shown job
   */
  public JobSelectionProvider getJobSelectionProvider() {
    if( this.jobSelectionProvider == null ) {
      this.jobSelectionProvider = new JobSelectionProvider();
    }
    return this.jobSelectionProvider;
  }
  
  private void refreshEmptyJobDescription() {
    if( this.inputJob == null ) {
      if( this.emptyJobDescription == null ) {
        this.emptyJobDescription = getFormToolkit().createLabel( this.topScrolledForm.getBody(), Messages.JobDetailsView_emptyJobDescription );
      }
    }
    else {
      if( this.emptyJobDescription != null ) {
        this.emptyJobDescription.dispose();
        this.emptyJobDescription = null;
      }
    }
  }

  
  /**
   * @return the inputJob
   */
  public IGridJob getInputJob() {
    return this.inputJob;
  }  
}
