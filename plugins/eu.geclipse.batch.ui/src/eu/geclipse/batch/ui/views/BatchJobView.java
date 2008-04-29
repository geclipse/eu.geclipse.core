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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.ui.dialogs.BatchJobInfoDialog;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.parts.BatchTreeEditPart;
import eu.geclipse.batch.ui.internal.parts.ComputingElementEditPart;
import eu.geclipse.batch.ui.internal.parts.ConnectionEditPart;
import eu.geclipse.batch.ui.internal.parts.DiagramEditPart;
import eu.geclipse.batch.ui.internal.parts.QueueEditPart;
import eu.geclipse.batch.ui.internal.parts.WorkerNodeEditPart;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.wizards.MoveJobWizard;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.comparators.TableColumnComparator;
import eu.geclipse.ui.listeners.TableColumnListener;

/**
 * Viewer of Batch jobs that are either placed in a Worker Node,
 * Computing Element, or Queue.
 */
public class BatchJobView extends ViewPart implements IContentChangeListener {

  /**
   * This internal class is used to present the currently available jobs
   * in a structured way to the user. The standard presentation uses a table.
   * 
   * @see JobLabelProvider
   */
  class JobContentProvider implements IStructuredContentProvider {
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements( final Object input ) {
      Object[] resultArray = null;

      if ( input instanceof Queue ) {
        Queue q = ( Queue )input;
        List< IBatchJobInfo > jobs = q.getJobManager().getJobs( q.getQueneName() );
        IBatchJobInfo[] jobArray = new IBatchJobInfo[ jobs.size() ];
        resultArray = jobs.toArray( jobArray ); 
      } else if ( input instanceof ComputingElement ) {
        ComputingElement ce = ( ComputingElement ) input;
        List< IBatchJobInfo > jobs = ce.getJobManager().getJobs( );
        IBatchJobInfo[] jobArray = new IBatchJobInfo[ jobs.size() ];
        resultArray = jobs.toArray( jobArray ); 
      } else if ( input instanceof WorkerNode ) {
        WorkerNode wn = ( WorkerNode ) input;
        List< String > jobIds = wn.getJobIds();
        List< IBatchJobInfo > jobs = wn.getJobManager().getJobs(jobIds );
        IBatchJobInfo[] jobArray = new IBatchJobInfo[ jobs.size() ];
        resultArray = jobs.toArray( jobArray ); 
      } else if ( input instanceof DiagramEditPart || input instanceof ConnectionEditPart ) {
        IBatchJobInfo[] jobArray = new IBatchJobInfo[ 0 ];
        resultArray = jobArray;
      }
      
      return resultArray;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged
     * (org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput) {
      // empty implementation
     /* if ( oldInput instanceof BatchJobManager ) {
        ( ( BatchJobManager ) oldInput).clear();
      }
*/
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
      // empty implementation
    }
  }

  /**
   * This internal class is used to present the currently available batch jobs
   * in a structured way to the user. The standard presentation uses a table.
   * 
   * @author harald
   * @see JobContentProvider
   */
  class JobLabelProvider extends LabelProvider implements ITableLabelProvider, ITableFontProvider {
    
    /**
     * The font that is used to draw activated tokens.
     */
    private Font boldFont;
    
    /**
     * Construct and initialize a new label provider.
     */
    public JobLabelProvider() {
      Font font = JFaceResources.getDefaultFont();
      Device device = font.getDevice();
      FontData[] fontData = font.getFontData();
      for ( int i = 0 ; i < fontData.length ; i++ ) {
        int style = fontData[i].getStyle();
        fontData[i].setStyle( style | SWT.BOLD );
      }
      this.boldFont = new Font( device, fontData );
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.LabelProvider#dispose()
     */
    @Override
    public void dispose() {
      super.dispose();
      this.boldFont.dispose();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage( final Object element,
                                 final int columnIndex ) {
      // no images supported at the moment
      return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText( final Object element,
                                 final int columnIndex ) {
      String columnText = element.toString();
      if ( element instanceof IBatchJobInfo ) {
        IBatchJobInfo job = ( IBatchJobInfo ) element;
        switch ( columnIndex ) {
          case 0:
            columnText = job.getJobId();
            break;
          case 1:
            columnText = job.getJobName();
            break;
          case 2:
            columnText = job.getQueueName();
            break;
          case 3:
            columnText = job.getUserAccount();
            break;
          case 4:
            columnText = job.getTimeUse();
            break;
          case 5:
            columnText = job.getStatus().toString(); 
            break;
          default:
            assert false;
        }
      }
      return columnText; // Just for debugging
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableFontProvider#getFont(java.lang.Object, int)
     */
    public Font getFont( final Object element, final int columnIndex ) {
      Font resultFont = null;
      if ( element instanceof IBatchJobInfo ) {
        IBatchJobInfo job = ( IBatchJobInfo ) element;
        if ( job.getStatus() == IBatchJobInfo.JobState.R ) {
          resultFont = this.boldFont;
        }
      }
      return resultFont;
    }
    
  }
  
  /**
   * The table used to present the jobs.
   */
  protected Table jobTable;
  
  /**
   * A <code>TableViewer</code> that helps to present the jobs
   * in a more elegant way.
   */
  protected TableViewer jobList;

  /**
   * This is the object that are currently selected and which jobs are listed 
   */
  protected BatchJobManager jobManager;
  
  /**
   * Action for deleting jobs.
   */
  private Action deleteAction;

  /**
   * Action for moving a job.
   */
  private Action moveAction;

  /**
   * Action for refreshing the token list.
   */
  private Action refreshAction;

  /**
   * Action for refreshing the token list.
   */
  private Action reRunAction;

  /**
   * Action for holding a job.
   */
  private Action holdAction;
  
  /**
   * Action for releasing a job.
   */
  private Action releaseAction;
  
  /**
   * Created the table viewer for batch jobs.
   * @param parent The composite that will contain the table.
   */
  @Override
  public void createPartControl( final Composite parent )
  {
    this.jobTable = new Table( parent, SWT.MULTI | SWT.FULL_SELECTION );
    this.jobTable.setHeaderVisible( true );
    this.jobTable.setLinesVisible( true );    
    
    this.jobList = new TableViewer( this.jobTable );
    this.jobList.setLabelProvider( new JobLabelProvider() );
    this.jobList.setContentProvider( new JobContentProvider() );

    TableColumnListener columnListener = new TableColumnListener( this.jobList );

    TableColumn idColumn = new TableColumn( this.jobTable, SWT.NONE );
    idColumn.setText( Messages.getString( "BatchJobView.JobId" ) ); //$NON-NLS-1$
    idColumn.setWidth( 150 );    
    idColumn.setAlignment( SWT.LEFT );
    idColumn.addSelectionListener( columnListener );

    TableColumn nameColumn = new TableColumn( this.jobTable, SWT.CENTER );
    nameColumn.setText( Messages.getString( "BatchJobView.JobName" ) ); //$NON-NLS-1$
    nameColumn.setWidth( 75 );    
    nameColumn.setAlignment( SWT.CENTER );
    nameColumn.addSelectionListener( columnListener );
    
    TableColumn queueColumn = new TableColumn( this.jobTable, SWT.CENTER );
    queueColumn.setText( Messages.getString( "BatchJobView.Queue" ) ); //$NON-NLS-1$
    queueColumn.setWidth( 150 );    
    queueColumn.setAlignment( SWT.CENTER );
    queueColumn.addSelectionListener( columnListener );
    
    TableColumn userColumn = new TableColumn( this.jobTable, SWT.CENTER );
    userColumn.setText( Messages.getString( "BatchJobView.UserAccount" ) ); //$NON-NLS-1$
    userColumn.setWidth( 150 );    
    userColumn.setAlignment( SWT.CENTER );
    userColumn.addSelectionListener( columnListener );
    
    TableColumn timeColumn = new TableColumn( this.jobTable, SWT.CENTER );
    timeColumn.setText( Messages.getString( "BatchJobView.Time" ) ); //$NON-NLS-1$
    timeColumn.setWidth( 75 );    
    timeColumn.setAlignment( SWT.CENTER );
    timeColumn.addSelectionListener( columnListener );
    
    TableColumn statusColumn = new TableColumn( this.jobTable, SWT.CENTER );
    statusColumn.setText( Messages.getString( "BatchJobView.Status" ) ); //$NON-NLS-1$
    statusColumn.setWidth( 75 );    
    statusColumn.setAlignment( SWT.CENTER );
    statusColumn.addSelectionListener( columnListener );
    
    // Initially we sort the jobs by ID, ascending
    this.jobTable.setSortColumn( idColumn );
    this.jobTable.setSortDirection( SWT.UP );

    this.jobList.setComparator( new TableColumnComparator( idColumn ) );
    this.jobList.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent e ) {
        showSelectedJobInfo();
      }
    });

    this.jobList.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        updateActions();
      }
    });
    
    this.jobTable.addKeyListener( new KeyAdapter() {
      @Override
      public void keyPressed( final KeyEvent event ) {
        if ( event.character == SWT.DEL && event.stateMask == 0 ) {
          removeSelectedJobs();
        }
      }
    });
    
    getSite().getWorkbenchWindow().getSelectionService().addSelectionListener( new ISelectionListener() {
      @SuppressWarnings("unchecked")
      public void selectionChanged( final IWorkbenchPart part, final ISelection selection ) {
        if ( selection instanceof StructuredSelection ) {
          StructuredSelection ss = ( StructuredSelection ) selection;
          List< StructuredSelection > sList = ss.toList();

          //BatchJobManager innerManager = BatchJobManager.getManager();
        
          for ( Iterator<StructuredSelection> iterator = sList.iterator(); iterator.hasNext(); ) {
            Object obj = iterator.next();

            //See if it implements IAdaptable
            if ( obj instanceof QueueEditPart ) {
              QueueEditPart qEdit = ( QueueEditPart )obj;
              Queue q = ( Queue )qEdit.getModel();

              if ( null == BatchJobView.this.jobManager ) {
                BatchJobView.this.jobManager = q.getJobManager();
                BatchJobView.this.jobManager.addContentChangeListener( BatchJobView.this );
              }
              
              BatchJobView.this.jobList.setInput( q );
            } else if ( obj instanceof ComputingElementEditPart ) {
              ComputingElementEditPart ceEdit = ( ComputingElementEditPart )obj;
              ComputingElement ce = ( ComputingElement )ceEdit.getModel();

              if ( null == BatchJobView.this.jobManager ) {
                BatchJobView.this.jobManager = ce.getJobManager();
                BatchJobView.this.jobManager.addContentChangeListener( BatchJobView.this );
              }

              BatchJobView.this.jobList.setInput( ce );
            } else if ( obj instanceof WorkerNodeEditPart ) {
              WorkerNodeEditPart wnEdit = ( WorkerNodeEditPart )obj;
              WorkerNode wn = ( WorkerNode )wnEdit.getModel();

              if ( null == BatchJobView.this.jobManager ) {
                BatchJobView.this.jobManager = wn.getJobManager();
                BatchJobView.this.jobManager.addContentChangeListener( BatchJobView.this );
              }

              BatchJobView.this.jobList.setInput( wn );
            } else if ( obj instanceof DiagramEditPart || obj instanceof ConnectionEditPart ) {
              // clear all the content
              if ( null != BatchJobView.this.jobManager ) {
                BatchJobView.this.jobManager.removeContentChangeListener( BatchJobView.this );
                BatchJobView.this.jobManager = null;
              }

              BatchJobView.this.jobList.setInput( obj );
            } else if ( obj instanceof BatchTreeEditPart ) {
              BatchTreeEditPart treeEdit = ( BatchTreeEditPart )obj;
              Object obj2 = treeEdit.getModel();

              if ( obj2 instanceof ComputingElement ) {
                ComputingElement ce = ( ComputingElement )obj2;
              
                if ( null == BatchJobView.this.jobManager ) {
                  BatchJobView.this.jobManager =ce.getJobManager();
                  BatchJobView.this.jobManager.addContentChangeListener( BatchJobView.this );
                }

                BatchJobView.this.jobList.setInput( ce );
              } else if ( obj2 instanceof Queue ) {
                Queue q = ( Queue )obj2;
                
                if ( null == BatchJobView.this.jobManager ) {
                  BatchJobView.this.jobManager = q.getJobManager();
                  BatchJobView.this.jobManager.addContentChangeListener( BatchJobView.this );
                }

                BatchJobView.this.jobList.setInput( q );
              } else if ( obj2 instanceof WorkerNode ) {
                WorkerNode wn = ( WorkerNode )obj2;
                
                if ( null == BatchJobView.this.jobManager ) {
                  BatchJobView.this.jobManager = wn.getJobManager();
                  BatchJobView.this.jobManager.addContentChangeListener( BatchJobView.this );
                }

                BatchJobView.this.jobList.setInput( wn );
              }
            }
          }
        }
      }
    });
    
    createActions();
    createToolbar();
    createContextMenu();
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#dispose()
   */
  @Override
  public void dispose() {
    if ( null != this.jobManager ) {
      this.jobManager.removeContentChangeListener( this );
      this.jobManager = null;
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeListener#contentChanged(org.eclipse.compare.IContentChangeNotifier)
   */
  public void contentChanged( final IContentChangeNotifier source ) {

    getSite().getWorkbenchWindow().getShell().getDisplay().asyncExec( new Runnable() {  
      public void run() {  
        BatchJobView.this.jobList.refresh(); 
        updateActions();
      }
    });
  }
  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    this.jobTable.setFocus();
  }

  /**
   * Get the currently selected job. If there are more jobs selected get the first
   * job in the list of selected jobs.
   * 
   * @return The first job in the list of selected jobs.
   */
  public IBatchJobInfo getSelectedJob() {
    IBatchJobInfo resultJob = null;
    IStructuredSelection selection
      = ( IStructuredSelection ) this.jobList.getSelection();
    Object o = selection.getFirstElement();
    if ( o instanceof  IBatchJobInfo ) {
      resultJob = ( IBatchJobInfo ) o;
    }
    return resultJob;
  }
  
  /**
   * Get a list of all currently selected tokens.
   * 
   * @return A list containing all currently selected tokens.
   */
  public List< IBatchJobInfo > getSelectedJobs() {
    IStructuredSelection selection 
      = ( IStructuredSelection ) this.jobList.getSelection();
    List< ? > selectionList = selection.toList();
    List< IBatchJobInfo > result = new ArrayList< IBatchJobInfo >();
    for( Object element : selectionList ) {
      if ( element instanceof IBatchJobInfo ) {
        IBatchJobInfo token = ( IBatchJobInfo ) element;
        result.add( token );
      }
    }
    return result;
  }
 
  /**
   * Removes the selected jobs from the table and deletes them using the batch job manager.
   * 
   * @see #getSelectedJobs()
   */
  protected void removeSelectedJobs() {
    List< IBatchJobInfo > jobs = getSelectedJobs();
    if ( !jobs.isEmpty() ) {
      boolean confirm = !MessageDialog.openConfirm( getSite().getShell(),
                                         Messages.getString( "BatchJobView.confirm_delete_title" ), //$NON-NLS-1$
                                         Messages.getString( "BatchJobView.confirm_delete_message" ) );  //$NON-NLS-1$
      if ( !confirm ) {
        //BatchJobManager manager = BatchJobManager.getManager();
        for ( IBatchJobInfo job : jobs ) {
          try {
            if ( null != this.jobManager ) {
              this.jobManager.deleteJob( job );
              this.jobList.refresh(); 
            }
          } catch( ProblemException excp ) {
            // Action could not be performed
            ProblemDialog.openProblem( getSite().getShell(),
                                       Messages.getString( "BatchJobView.error_delete_title" ), //$NON-NLS-1$
                                       Messages.getString( "BatchJobView.error_delete_message" ), //$NON-NLS-1$
                                       excp );      
          }
        }
        updateActions();
      }
    }
  }

  /**
   * Changes the state of the job to be held using the batch job manager.
   * 
   * @see #getSelectedJobs()
   */
  protected void holdSelectedJobs() {
    List< IBatchJobInfo > jobs = getSelectedJobs();
    if ( !jobs.isEmpty() ) {
      for ( IBatchJobInfo job : jobs ) {
        try {
          if ( null != this.jobManager ) {
            this.jobManager.holdJob( job );
            this.jobList.refresh(); 
          }
        } catch( ProblemException excp ) {
          // Action could not be performed
          ProblemDialog.openProblem( getSite().getShell(),
                                     Messages.getString( "BatchJobView.error_hold_title" ), //$NON-NLS-1$
                                     Messages.getString( "BatchJobView.error_hold_message" ), //$NON-NLS-1$
                                      excp );      
        }

        updateActions();
      }
    }
  }

  /**
   * Changes the state of the job to be held using the batch job manager.
   * 
   * @see #getSelectedJobs()
   */
  protected void releaseSelectedJobs() {
    List< IBatchJobInfo > jobs = getSelectedJobs();
    if ( !jobs.isEmpty() ) {
      for ( IBatchJobInfo job : jobs ) {
        try {
          if ( null != this.jobManager ) {
            this.jobManager.releaseJob( job );
            this.jobList.refresh(); 
          }
        } catch( ProblemException excp ) {
          // Action could not be performed
          ProblemDialog.openProblem( getSite().getShell(),
                                     Messages.getString( "BatchJobView.error_release_title" ), //$NON-NLS-1$
                                     Messages.getString( "BatchJobView.error_release_message" ), //$NON-NLS-1$
                                     excp );      
        }

        updateActions();
      }
    }
  }
  
  /**
   * Moves the selected jobs using the batch job manager to another queue or batch service.
   * 
   * @see #getSelectedJobs()
   */
  protected void moveSelectedJobs() {
    List< IBatchJobInfo > jobs = getSelectedJobs();
    if ( !jobs.isEmpty() ) {
      if ( null != this.jobManager ) {
        MoveJobWizard wizard = new MoveJobWizard( this.jobManager, jobs );
        wizard.init( this.getSite().getWorkbenchWindow().getWorkbench(), null );
     
        WizardDialog dialog = new WizardDialog ( this.getSite().getShell(), wizard );
        dialog.create();
        dialog.open();
      }
    }
  }
  
  /**
   * Show an info dialog for the currently selected job.
   * 
   * @see #getSelectedJob()
   */
  protected void showSelectedJobInfo() {
    IBatchJobInfo job = getSelectedJob();
    if ( job != null ) {
      BatchJobInfoDialog infoDialog = new BatchJobInfoDialog( job, getSite().getShell() );
      infoDialog.open();
/*      IBatchJobUIFactory factory = AbstractBatchJobUIFactory.findFactory( job );
      if ( factory != null ) {
        BatchJobInfoDialog infoDialog = factory.getInfoDialog( job, getSite().getShell() );
        infoDialog.open();
      }
*/
    }
  }
  
  /**
   * Re-run the currently selected job.
   * 
   * @see #getSelectedJob()
   */
  protected void reRunJobs() {
    List< IBatchJobInfo > jobs = getSelectedJobs();
    if ( !jobs.isEmpty() ) {
      for ( IBatchJobInfo job : jobs ) {
        try {
          if ( null != this.jobManager ) {
            this.jobManager.reRunJob( job );
            //this.jobList.refresh(); 
          }
        } catch( ProblemException excp ) {
          // Action could not be performed
          ProblemDialog.openProblem( getSite().getShell(),
                                     Messages.getString( "BatchJobView.error_rerun_title" ), //$NON-NLS-1$
                                     Messages.getString( "BatchJobView.error_rerun_message" ), //$NON-NLS-1$
                                     excp );      
        }

        updateActions();
      }
    }
  }

  /**
   * Update the enabled state of the actions according to the current content of this view and
   * the content's state.
   */
  @SuppressWarnings("null")
  protected void updateActions() {
    List< IBatchJobInfo > jobs = getSelectedJobs(); 
    boolean selected = jobs.size() > 0;
    boolean deletable = true;
    boolean movable = true;
    boolean holdable = true;
    boolean releasable = true;
    boolean reRunnable = true;
    
    for ( IBatchJobInfo job : jobs) {
      if ( !job.isDeletable() )
        deletable = false;
      if ( !job.isMovable() )
        movable = false;
      if ( !job.isHoldable() )
        holdable = false;
      if ( !job.isReleasable() )
        releasable = false;
      if ( !job.isReRunnable() )
        reRunnable = false;
    }
    
    this.deleteAction.setEnabled( selected && deletable );
    this.moveAction.setEnabled( selected && movable );
    this.holdAction.setEnabled( selected && holdable );
    this.releaseAction.setEnabled( selected && releasable );
    this.reRunAction.setEnabled( selected && reRunnable );
  }
  
  /**
   * Fill the context menu belonging to the token table.
   * 
   * @param mgr The manager that takes responsible for the context menu.
   */
  protected void fillContextMenu( final IMenuManager mgr ) {
    if ( this.deleteAction.isEnabled() ) 
      mgr.add( this.deleteAction );

    if ( this.moveAction.isEnabled() )
      mgr.add( this.moveAction );

    if ( this.holdAction.isEnabled() )
      mgr.add( this.holdAction );

    if ( this.releaseAction.isEnabled() )
      mgr.add( this.releaseAction );

    if ( this.reRunAction.isEnabled() )
      mgr.add( this.reRunAction );
      
    if ( this.refreshAction.isEnabled() ) {
      mgr.add( new Separator() );
      mgr.add( this.refreshAction );
    }

    mgr.add( new GroupMarker( IWorkbenchActionConstants.MB_ADDITIONS ) );
  }
  
  /**
   * Create the actions of this view.
   */
  private void createActions() {
    
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
    
    ImageDescriptor deleteImage 
      = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE );

    Image image = imgReg.get( Activator.IMG_REFRESH ); 
    ImageDescriptor refreshImage = ImageDescriptor.createFromImage( image );

    image = imgReg.get( Activator.IMG_MOVEJOB ); 
    ImageDescriptor moveImage = ImageDescriptor.createFromImage( image );

    image = imgReg.get( Activator.IMG_HOLDJOB ); 
    ImageDescriptor holdImage = ImageDescriptor.createFromImage( image );

    image = imgReg.get( Activator.IMG_RELEASEJOB ); 
    ImageDescriptor releaseImage = ImageDescriptor.createFromImage( image );

    image = imgReg.get( Activator.IMG_BUSY_ARROW2 ); 
    ImageDescriptor reRunImage = ImageDescriptor.createFromImage( image );
    
    this.refreshAction = new Action() {
      @Override
      public void run() {
        BatchJobView.this.jobList.refresh();
      }
    };
    this.refreshAction.setText( Messages.getString( "BatchJobView.refresh_text" ) ); //$NON-NLS-1$
    this.refreshAction.setToolTipText( Messages.getString( "BatchJobView.refresh_tooltip" ) ); //$NON-NLS-1$
    this.refreshAction.setImageDescriptor( refreshImage );
    
    this.deleteAction = new Action() {
      @Override
      public void run() {
        removeSelectedJobs();
      }
    };
    this.deleteAction.setText( Messages.getString( "BatchJobView.delete_text" ) ); //$NON-NLS-1$
    this.deleteAction.setToolTipText( Messages.getString( "BatchJobView.delete_tooltip" ) ); //$NON-NLS-1$
    this.deleteAction.setImageDescriptor( deleteImage );

    this.moveAction = new Action() {
      
      @Override
      public void run() {
        moveSelectedJobs();
      }
    };

    this.moveAction.setText( Messages.getString( "BatchJobView.move_text" ) ); //$NON-NLS-1$
    this.moveAction.setToolTipText( Messages.getString( "BatchJobView.move_tooltip" ) ); //$NON-NLS-1$
    this.moveAction.setImageDescriptor( moveImage );

    this.holdAction = new Action() {
      
      @Override
      public void run() {
        holdSelectedJobs();
      }
    };

    this.holdAction.setText( Messages.getString( "BatchJobView.hold_text" ) ); //$NON-NLS-1$
    this.holdAction.setToolTipText( Messages.getString( "BatchJobView.hold_tooltip" ) ); //$NON-NLS-1$
    this.holdAction.setImageDescriptor( holdImage );

    this.releaseAction = new Action() {
      
      @Override
      public void run() {
        releaseSelectedJobs();
      }
    };

    this.releaseAction.setText( Messages.getString( "BatchJobView.release_text" ) ); //$NON-NLS-1$
    this.releaseAction.setToolTipText( Messages.getString( "BatchJobView.release_tooltip" ) ); //$NON-NLS-1$
    this.releaseAction.setImageDescriptor( releaseImage );
    
    this.reRunAction = new Action() {
      @Override
      public void run() {
        reRunJobs();
      }
    };

    this.reRunAction.setText( Messages.getString( "BatchJobView.reRun_text" ) ); //$NON-NLS-1$
    this.reRunAction.setToolTipText( Messages.getString( "BatchJobView.reRun_tooltip" ) ); //$NON-NLS-1$
    this.reRunAction.setImageDescriptor( reRunImage );
    
    updateActions();
  }
  
  /**
   * Create the toolbar of this view. 
   */
  private void createToolbar() {
    IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
    mgr.add( new Separator() );
    mgr.add( this.deleteAction );
    mgr.add( this.moveAction );
    mgr.add( this.holdAction );
    mgr.add( this.releaseAction );
    mgr.add( this.reRunAction );
    mgr.add( new Separator() );
    mgr.add( this.refreshAction );
  }

  /**
   * Create the context menu for the token table.
   */
  private void createContextMenu() {
    MenuManager manager = new MenuManager();
    manager.setRemoveAllWhenShown( true );
    manager.addMenuListener( new IMenuListener() {
      public void menuAboutToShow( final IMenuManager mgr ) {
        fillContextMenu( mgr );
      }
    } );

    Menu menu = manager.createContextMenu( this.jobTable );
    this.jobTable.setMenu(menu);
    getSite().registerContextMenu( manager, getSite().getSelectionProvider() );
  }
}

