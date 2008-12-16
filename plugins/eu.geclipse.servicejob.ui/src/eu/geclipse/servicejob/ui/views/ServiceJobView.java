/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *      - Szymon Mueller
 *****************************************************************************/
package eu.geclipse.servicejob.ui.views;

import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobStatusListener;
import eu.geclipse.servicejob.ui.Activator;
import eu.geclipse.servicejob.ui.actions.OpenServiceJobDialogAction;
import eu.geclipse.servicejob.ui.decorators.ServiceJobDecorator;
import eu.geclipse.servicejob.ui.providers.NewServiceJobLabelProvider;
import eu.geclipse.servicejob.ui.providers.ServiceJobContentProvider;
import eu.geclipse.servicejob.ui.providers.ServiceJobLabelProvider;
import eu.geclipse.servicejob.ui.wizard.JobSubmissionServiceWizard;
import eu.geclipse.ui.views.ElementManagerViewPart;

/**
 * View responsible for displaying managed Operator's Jobs.
 */
public class ServiceJobView extends ElementManagerViewPart
  implements IServiceJobStatusListener, ISelectionProvider,
  ISelectionChangedListener
{

  boolean refreshedFirstTime;
  private OpenServiceJobDialogAction wizardAction;
  private StructuredViewer myViewer;
  private Action runJobAction;

  @Override
  protected IGridElementManager getManager() {
    return GridModel.getServiceJobManager();
  }

  @Override
  protected boolean createTreeColumns( final Tree tree ) {
    TreeColumn nameColumn = new TreeColumn( tree, SWT.NONE );
    nameColumn.setText( "Name" ); //$NON-NLS-1$
    nameColumn.setAlignment( SWT.LEFT );
    nameColumn.setWidth( 160 );
    TreeColumn projectColumn = new TreeColumn( tree, SWT.NONE );
    projectColumn.setText( "Project" ); //$NON-NLS-1$
    projectColumn.setAlignment( SWT.LEFT );
    projectColumn.setWidth( 100 );
    TreeColumn statusColumn = new TreeColumn( tree, SWT.NONE );
    statusColumn.setText( Messages.getString( "ServiceJobsView.status" ) ); //$NON-NLS-1$
    statusColumn.setAlignment( SWT.LEFT );
    statusColumn.setWidth( 130 );
    TreeColumn dateColumn = new TreeColumn( tree, SWT.NONE );
    dateColumn.setText( Messages.getString( "ServiceJobsView.operator_job_date" ) ); //$NON-NLS-1$
    dateColumn.setAlignment( SWT.LEFT );
    dateColumn.setWidth( 130 );
    TreeColumn typeColumn = new TreeColumn( tree, SWT.NONE );
    typeColumn.setText( Messages.getString( "ServiceJobsView.type" ) ); //$NON-NLS-1$
    typeColumn.setAlignment( SWT.LEFT );
    typeColumn.setWidth( 60 );
    return true;
  }

  @Override
  public void gridModelChanged( final IGridModelEvent event ) {
    super.gridModelChanged( event );
    if( event.getType() == IGridModelEvent.ELEMENTS_CHANGED
        || event.getType() == IGridModelEvent.ELEMENTS_ADDED
        || event.getType() == IGridModelEvent.ELEMENTS_REMOVED )
    {
      IGridElement[] removedElements = event.getElements();
      Control control = getViewer().getControl();
      for( IGridElement elem : removedElements ) {
        if( elem instanceof IServiceJob ) {
          if( !control.isDisposed() ) {
            Display display = control.getDisplay();
            display.asyncExec( new Runnable() {

              public void run() {
                if( ServiceJobView.this.refreshedFirstTime ) {
                  ServiceJobView.this.getViewer().refresh( true );
                  ServiceJobView.this.refreshedFirstTime = false;
                }
                ServiceJobView.this.getViewer().refresh( true );
              }
            } );
            refreshViewer();
          }
        }
      }
    }
  }

  @Override
  protected IBaseLabelProvider createLabelProvider() {
    return new ServiceJobLabelProvider();
    // return super.createLabelProvider();
  }

  @Override
  protected IContentProvider createContentProvider() {
    return new ServiceJobContentProvider();
  }

  @Override
  public void init( final IViewSite site ) throws PartInitException {
    super.init( site );
    GridModel.getServiceJobManager().addServiceJobStatusListener( this );
    addSelectionChangedListener( this );
  }

  @Override
  protected void initViewer( final StructuredViewer viewer ) {
    super.initViewer( viewer );
    this.myViewer = viewer;
    this.myViewer.addSelectionChangedListener( this );
  }

  @Override
  public void createPartControl( final Composite parent ) {
    super.createPartControl( parent );
    createButtons();
    createMenu();
    getSite().setSelectionProvider( this.myViewer );
    this.refreshedFirstTime = true;
  }

  private void createButtons() {
    IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
    this.wizardAction = new OpenServiceJobDialogAction();
    this.wizardAction.setToolTipText( Messages.getString( "ServiceJobsView.new_operators_job_wizard" ) ); //$NON-NLS-1$
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    this.wizardAction.setImageDescriptor( sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
    URL runURL = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/obj16/debugt_obj.gif" ); //$NON-NLS-1$
    ImageDescriptor runDesc = ImageDescriptor.createFromURL( runURL );
    URL runDisabledURL = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/obj16/debugt_obj_disabled1.gif" ); //$NON-NLS-1$
    ImageDescriptor runDisabledDesc = ImageDescriptor.createFromURL( runDisabledURL );
    this.runJobAction = new Action() {

      @Override
      public void run() {
        ISelection sel = ServiceJobView.this.getSelection();
        if( !sel.isEmpty() ) {
          if( sel instanceof IStructuredSelection ) {
            IStructuredSelection sSel = ( IStructuredSelection )sel;
            for( Object obj : sSel.toList() ) {
              if( obj instanceof IServiceJob ) {
                IServiceJob serviceJob = ( IServiceJob )obj;
                if( serviceJob.needsSubmissionWizard() ) {
                  JobSubmissionServiceWizard serviceWizard = new JobSubmissionServiceWizard( serviceJob.getParent()
                    .getProject()
                    .getVO() );
                  WizardDialog dialog = new WizardDialog( PlatformUI.getWorkbench()
                                                            .getActiveWorkbenchWindow()
                                                            .getShell(),
                                                          serviceWizard );
                  if( dialog.open() == WizardDialog.OK ) {
                    serviceJob.setSubmissionService( serviceWizard.getSelectedService() );
                    serviceJob.run();
                  }
                } else {
                  serviceJob.run();
                }
              }
            }
          }
        }
        super.run();
      }
    };
    this.runJobAction.setImageDescriptor( runDesc );
    this.runJobAction.setDisabledImageDescriptor( runDisabledDesc );
    this.runJobAction.setEnabled( !getSelection().isEmpty() );
    mgr.add( this.runJobAction );
    mgr.add( this.wizardAction );
    refreshViewer();
  }

  private void createMenu() {
    // Empty
  }

  public void statusChanged( final IServiceJob serviceJob ) {
    ServiceJobDecorator decorator = ServiceJobDecorator.getDecorator();
    if( decorator != null ) {
      decorator.refresh( serviceJob );
    }
    refreshViewer( serviceJob );
  }

  public void addSelectionChangedListener( final ISelectionChangedListener listener )
  {
    // empty implementation
  }

  public ISelection getSelection() {
    return this.myViewer.getSelection();
  }

  public void removeSelectionChangedListener( final ISelectionChangedListener listener )
  {
    // empty implementation
  }

  public void setSelection( final ISelection selection ) {
    this.myViewer.setSelection( selection );
  }

  public void selectionChanged( final SelectionChangedEvent event ) {
    this.runJobAction.setEnabled( !getSelection().isEmpty() );
  }
}
