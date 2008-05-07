/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import eu.geclipse.batch.BatchQueueDescription;
import eu.geclipse.batch.BatchServiceManager;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.providers.BatchServiceTreeContentProvider;
import eu.geclipse.batch.ui.internal.providers.BatchServiceTreeLabelProvider;
import eu.geclipse.core.model.IGridBatchQueueDescription;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**
 * @author nloulloud
 * 
 * The Batch Service Selection Wizard Page.
 *
 */
public class BatchServiceSelectionWizardPage extends WizardPage {
  
  protected List<IGridBatchQueueDescription> queueDescList;
  protected CheckboxTreeViewer treeViewer;
  private Composite mainComp;   
  private int servicesCount = BatchServiceManager.getManager().getServiceCount();

  protected BatchServiceSelectionWizardPage( final String pageName ) {
    super( pageName );
    this.setTitle( Messages.getString( "BatchServiceSelectionDialog.title" ) ); //$NON-NLS-1$
    this.setDescription( Messages.getString("BatchServiceSelectionDialog.Descr")  ); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    GridData gd;
    
    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 1, false ) );
    
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;    
    gd.horizontalSpan = 1;
    gd.widthHint = 300;    
    
    /* Batch Services Tree Viewer */
    this.treeViewer = new CheckboxTreeViewer(this.mainComp);
    this.treeViewer.setContentProvider( new BatchServiceTreeContentProvider() );
    this.treeViewer.setLabelProvider( new BatchServiceTreeLabelProvider() );
    
    /* If one or more running Batch Service instances exist the load them TreeViewer input */
    if ( this.servicesCount > 0 ) {
      this.treeViewer.setInput( this.initialViewerInput() );
    }
    
    this.treeViewer.addCheckStateListener( new ICheckStateListener(){
      
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        Object element = event.getElement();        
        if (element instanceof String) {
          if (event.getChecked())
            BatchServiceSelectionWizardPage.this.treeViewer.setSubtreeChecked( element, true );
          else
            BatchServiceSelectionWizardPage.this.treeViewer.setSubtreeChecked( element, false );
         
        }
        /* Update the UI to enable Finish button if 1 or more elements are checked */
        updateUI();
      }
      
    });
    
    this.treeViewer.getControl().setLayoutData(gd);
    
    this.treeViewer.expandAll();
    
    setControl( this.mainComp );
  }
  
  /*
   * Get the Registered Batch Services through the Extension Points.
   */
  private List<String> initialViewerInput() {
    List<String> registeredServices = eu.geclipse.batch.Extensions.getRegisteredBatchServiceNames();
    
    return registeredServices;    
  }
  
  /**
   * Initial Data that will be used in the Wizard Page.
   * 
   * @param list
   */
  public void setInitialData(final List<IGridBatchQueueDescription> list){
    this.queueDescList = list;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    return super.isPageComplete() && isServiceSelectionValid() ;
  }
  
  
  private boolean isServiceSelectionValid(){
    boolean ret = false;
    
    if (this.treeViewer.getCheckedElements().length != 0) {      
      ret = true;
    }
    
    return ret;
  }
  
  protected void updateUI() {
    setPageComplete( isServiceSelectionValid() );
  }
  
  /**
   * @return TRUE if the Queue configuration has been committed to the selected batch services or
   * FALSE if not.
   * 
   */
  public boolean finish() {
    boolean result = true;

    if ( isServiceSelectionValid() ) { 
      final Object[] checkedElements = this.treeViewer.getCheckedElements();
      final IWizardContainer container = getContainer();
    
      try {
        container.run( true, true, new IRunnableWithProgress() {
          
        protected void testCanceled( final IProgressMonitor monitor ) {
          if ( monitor.isCanceled() ) {
            throw new OperationCanceledException();
          }
        }

        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException {
          IBatchService batchWrapper = null;    
          BatchQueueDescription batchQueue = null;
            
          SubMonitor betterMonitor = SubMonitor.convert( monitor, checkedElements.length );
            
          for( Object element : checkedElements ) {              
            testCanceled( betterMonitor );              

            if( element instanceof IBatchService ) {                
              betterMonitor.setTaskName( 
                        String.format( Messages.getString( "BatchServiceSelectionDialog.task.Service" ), //$NON-NLS-1$ 
                        ( ( IBatchService ) element).getName() ) ); 

              batchWrapper = (IBatchService) element;
              testCanceled( betterMonitor );
              for( IGridBatchQueueDescription queueDescription 
                      : BatchServiceSelectionWizardPage.this.queueDescList ) {
                batchQueue = (BatchQueueDescription) queueDescription;
//                try {
                  batchQueue.load(queueDescription.getResource().getFullPath().toString());
                  try {
                    betterMonitor.setTaskName( 
                              String.format( 
                                Messages.getString( "BatchServiceSelectionDialog.task.Configuration" ), //$NON-NLS-1$ 
                              queueDescription.getResource().getName() ) ); 
                    batchWrapper.createQueue( batchQueue.getRoot() );
                    testCanceled( betterMonitor ); 
                  } catch( ProblemException e ) {
                    ProblemDialog.openProblem( getShell(),
                                        Messages.getString( "AddQueueWizard.error_manipulate_title" ),  //$NON-NLS-1$
                                        Messages.getString( "AddQueueWizard.error_manipulate_message" ), //$NON-NLS-1$
                                        e );
                  } 
//                } catch( GridModelException e ) {
//                  Activator.logException( e );
//                }
              } // end for
            } // end if ( element instanceof IBatchService )
          }
          betterMonitor.worked( 1 );
        }
        } );
      } catch( InvocationTargetException itExc ) {
        ProblemDialog.openProblem( getShell(),
                         Messages.getString("BatchServiceSelectionDialog.QueueConfigurationFailed"), //$NON-NLS-1$
                         Messages.getString("BatchServiceSelectionDialog.QueueConfigurationFailed"), //$NON-NLS-1$
                         itExc.getCause() );
        result = false;
      } catch( InterruptedException intExc ) {
        ProblemDialog.openProblem( getShell(),
                         Messages.getString("BatchServiceSelectionDialog.QueueConfigurationInterupted"), //$NON-NLS-1$
                         Messages.getString("BatchServiceSelectionDialog.QueueConfigurationInterupted"), //$NON-NLS-1$
                         intExc );
        result = false;
      }
    } // end if ( isServiceSelectionValid() )
    return result;
  }
      
}
