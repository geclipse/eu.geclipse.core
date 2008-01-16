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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardDialog;

import eu.geclipse.batch.BatchException;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.ui.dialogs.BatchJobInfoDialog;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.parts.ComputingElementEditPart;
import eu.geclipse.batch.ui.internal.parts.DiagramEditPart;
import eu.geclipse.batch.ui.internal.parts.QueueEditPart;
import eu.geclipse.batch.ui.internal.parts.WorkerNodeEditPart;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.wizards.MoveJobWizard;
import eu.geclipse.ui.dialogs.NewProblemDialog;

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
      if ( input instanceof BatchJobManager ) {
        List< IBatchJobInfo > jobs = ( ( BatchJobManager ) input ).getJobs();
        IBatchJobInfo[] jobArray = new IBatchJobInfo[ jobs.size() ];
        resultArray = jobs.toArray( jobArray ); 
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
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
      // empty implementation
    }
  }

  /**
   * This internal class is used to present the currently available authentication
   * tokens in a structured way to the user. The standard presentation uses a table.
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
   * This internal class is used to sort the batch jobs
   * according to different criteria.
   * 
   * @author agarcia
   */
  class BatchJobViewerComparator extends ViewerComparator {
    @Override
    public int compare( final Viewer viewer, final Object job1, final Object job2 ) {
      assert job1 instanceof IBatchJobInfo;
      assert job2 instanceof IBatchJobInfo;
      
      Table table = BatchJobView.this.jobTable;
      ITableLabelProvider labelProvider = (ITableLabelProvider) BatchJobView.this.jobList.getLabelProvider();
      
      int col;
      if ( table.getSortColumn() == null ) {
        col = 0;
      } else {
        col = table.indexOf( table.getSortColumn() );
      }
      
      String value1 = labelProvider.getColumnText( job1, col );
      String value2 = labelProvider.getColumnText( job2, col );
      
      int order = ( table.getSortDirection() == SWT.DOWN )
                    ? SWT.DOWN
                    : SWT.UP;
      
      int result;
      if ( order == SWT.UP ) {
        result = value1.compareTo( value2 );
      } else {
        result = value2.compareTo( value1 );
      }
      // If tokens compare equal, sort next by ascending ID
      if ( result == 0 ) {
        value1 = labelProvider.getColumnText( job1, 0 );
        value2 = labelProvider.getColumnText( job2, 0 );
        result = value1.compareTo( value2 );
      }
      
      return result;
    }
  }
  
  /**
   * This internal class is the listener for setting column sorting.
   * 
   * @author agarcia
   */
  class BatchJobColumnSelectionListener implements SelectionListener {
    
    public void widgetSelected( final SelectionEvent e ) {
      // This listener is only for the columns of the token table
      assert e.getSource() instanceof TableColumn;
      
      Table table = BatchJobView.this.jobTable;
      TableColumn clickedColumn = (TableColumn) e.getSource();
      TableColumn oldSortingColumn = table.getSortColumn();
      
      if ( clickedColumn == oldSortingColumn ) {
        table.setSortDirection( table.getSortDirection() == SWT.UP
                                  ? SWT.DOWN
                                  : SWT.UP );
      } else {
        table.setSortColumn( clickedColumn );
        table.setSortDirection( SWT.UP );
      }
      BatchJobView.this.jobList.refresh();
    }
  
    public void widgetDefaultSelected( final SelectionEvent e ) {
      // Empty implementation
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
    this.jobTable = buildAndLayoutTable( parent );

    BatchJobManager manager = BatchJobManager.getManager();
    this.jobList = new TableViewer( this.jobTable );
    
    this.jobList.setLabelProvider( new JobLabelProvider() );
    this.jobList.setContentProvider( new JobContentProvider() );
    this.jobList.setComparator( new BatchJobViewerComparator() );
    this.jobList.setInput( manager );

    this.jobList.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        updateActions();
      }
    });

    this.jobList.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent e ) {
        showSelectedJobInfo();
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
          StructuredSelection ss = (StructuredSelection) selection;
          List<StructuredSelection> sList = ss.toList();

          BatchJobManager innerManager = BatchJobManager.getManager();
        
          for ( Iterator<StructuredSelection> iterator = sList.iterator(); iterator.hasNext(); ) {
            Object obj = iterator.next();

            //See if it implements IAdaptable
            if ( obj instanceof QueueEditPart ) {
              // Clear old contents first
              innerManager.clear();
           
              QueueEditPart qEdit = (QueueEditPart)obj;
              Queue q = (Queue)qEdit.getModel();

              List<IBatchJobInfo> jobs = q.getJobs();

              if ( null != jobs ) {
                for ( IBatchJobInfo job : jobs ) {
                  if ( 0 == q.getQueneName().compareTo( job.getQueueName() ) ) {
                    innerManager.addJob( job );
                  }
                }
              }
              // Could list the jobs on this
            } else if ( obj instanceof ComputingElementEditPart ) {
              // Clear old contents first
              innerManager.clear();

              ComputingElementEditPart ceEdit = (ComputingElementEditPart)obj;
              ComputingElement ce = (ComputingElement)ceEdit.getModel();

              List<IBatchJobInfo> jobs = ce.getJobs();

              if ( null != jobs ) {
                for ( IBatchJobInfo job : jobs ) {
                  innerManager.addJob( job );
                }
              }
            } else if ( obj instanceof WorkerNodeEditPart ) {
              // Clear old contents first
              innerManager.clear();

              WorkerNodeEditPart wnEdit = (WorkerNodeEditPart)obj;
              WorkerNode wn = (WorkerNode)wnEdit.getModel();

              List< String > jobIds = wn.getJobIds();

              if ( null != jobIds ) {
                String na = Messages.getString( "BatchJobView.na" );  //$NON-NLS-1$
                for ( String jobId : jobIds ) {
                  innerManager.addJob( new BatchJobMinInfo( jobId, na, na, na, IBatchJobInfo.JobState.R, na ) );
                }
              }
            } else if ( obj instanceof DiagramEditPart ) {
              // clear all the content
              innerManager.clear();
            }
          }
        }
      }
    });
    
    manager.addContentChangeListener( this );
    
    createActions();
    createToolbar();
    createContextMenu();
  }

  /**
   * Creates the table and its layout for the view.
   * @param composite The composite were the table will be placed.
   * @return Returns the created <code>Table</code>.
   */
  private Table buildAndLayoutTable( final Composite composite ) {
    Table tbl = new Table( composite, /*SWT.CHECK |*/ SWT.MULTI | SWT.FULL_SELECTION );
    tbl.setHeaderVisible( true );
    tbl.setLinesVisible( true );
    
    BatchJobColumnSelectionListener columnListener = new BatchJobColumnSelectionListener();
    
    TableColumn idColumn = new TableColumn( tbl, SWT.NONE );
    idColumn.setText( Messages.getString( "BatchJobView.JobId" ) ); //$NON-NLS-1$
    idColumn.setWidth( 150 );    
    idColumn.setAlignment( SWT.LEFT );
    idColumn.addSelectionListener( columnListener );

    TableColumn nameColumn = new TableColumn( tbl, SWT.CENTER );
    nameColumn.setText( Messages.getString( "BatchJobView.JobName" ) ); //$NON-NLS-1$
    nameColumn.setWidth( 75 );    
    nameColumn.setAlignment( SWT.CENTER );
    nameColumn.addSelectionListener( columnListener );
    
    TableColumn queueColumn = new TableColumn( tbl, SWT.CENTER );
    queueColumn.setText( Messages.getString( "BatchJobView.Queue" ) ); //$NON-NLS-1$
    queueColumn.setWidth( 150 );    
    queueColumn.setAlignment( SWT.CENTER );
    queueColumn.addSelectionListener( columnListener );
    
    TableColumn userColumn = new TableColumn( tbl, SWT.CENTER );
    userColumn.setText( Messages.getString( "BatchJobView.UserAccount" ) ); //$NON-NLS-1$
    userColumn.setWidth( 150 );    
    userColumn.setAlignment( SWT.CENTER );
    userColumn.addSelectionListener( columnListener );
    
    TableColumn timeColumn = new TableColumn( tbl, SWT.CENTER );
    timeColumn.setText( Messages.getString( "BatchJobView.Time" ) ); //$NON-NLS-1$
    timeColumn.setWidth( 75 );    
    timeColumn.setAlignment( SWT.CENTER );
    timeColumn.addSelectionListener( columnListener );
    
    TableColumn statusColumn = new TableColumn( tbl, SWT.CENTER );
    statusColumn.setText( Messages.getString( "BatchJobView.Status" ) ); //$NON-NLS-1$
    statusColumn.setWidth( 75 );    
    statusColumn.setAlignment( SWT.CENTER );
    statusColumn.addSelectionListener( columnListener );
    
    return tbl;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#dispose()
   */
  @Override
  public void dispose() {
    BatchJobManager manager = BatchJobManager.getManager();
    manager.removeContentChangeListener( this );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.compare.IContentChangeListener#contentChanged(org.eclipse.compare.IContentChangeNotifier)
   */
  public void contentChanged( final IContentChangeNotifier source ) {
    BatchJobView.this.jobList.refresh();

    updateActions();
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
        BatchJobManager manager = BatchJobManager.getManager();
        for ( IBatchJobInfo job : jobs ) {
          try {
            manager.deleteJob( job );
          } catch( BatchException excp ) {
            // Action could not be performed
            NewProblemDialog.openProblem( getSite().getShell(),
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
//      boolean confirm = !MessageDialog.openConfirm( getSite().getShell(),
//                                         Messages.getString( "BatchJobView.confirm_delete_title" ), //$NON-NLS-1$
//                                         Messages.getString( "BatchJobView.confirm_delete_message" ) );  //$NON-NLS-1$
//      if ( !confirm ) {
        BatchJobManager manager = BatchJobManager.getManager();
        for ( IBatchJobInfo job : jobs ) {
          try {
            manager.holdJob( job );
          } catch( BatchException excp ) {
            // Action could not be performed
            NewProblemDialog.openProblem( getSite().getShell(),
                                          Messages.getString( "BatchJobView.error_hold_title" ), //$NON-NLS-1$
                                          Messages.getString( "BatchJobView.error_hold_message" ), //$NON-NLS-1$
                                          excp );      
          }
//        }
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
//      boolean confirm = !MessageDialog.openConfirm( getSite().getShell(),
//                                         Messages.getString( "BatchJobView.confirm_delete_title" ), //$NON-NLS-1$
//                                         Messages.getString( "BatchJobView.confirm_delete_message" ) );  //$NON-NLS-1$
//      if ( !confirm ) {
        BatchJobManager manager = BatchJobManager.getManager();
        for ( IBatchJobInfo job : jobs ) {
          try {
            manager.releaseJob( job );
          } catch( BatchException excp ) {
            // Action could not be performed
            NewProblemDialog.openProblem( getSite().getShell(),
                                          Messages.getString( "BatchJobView.error_release_title" ), //$NON-NLS-1$
                                          Messages.getString( "BatchJobView.error_release_message" ), //$NON-NLS-1$
                                          excp );      
          }
//        }
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
      BatchJobManager manager = BatchJobManager.getManager();

      MoveJobWizard wizard = new MoveJobWizard( manager, jobs );
      wizard.init( this.getSite().getWorkbenchWindow().getWorkbench(), null );
     
      WizardDialog dialog = new WizardDialog ( this.getSite().getShell(), wizard );
      dialog.create();
      dialog.open();
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
    
    for ( IBatchJobInfo job : jobs) {
      if ( !job.isDeletable() )
        deletable = false;
      if ( !job.isMovable() )
        movable = false;
      if ( !job.isHoldable() )
        holdable = false;
      if ( !job.isReleasable() )
        releasable = false;
    }
    
    this.deleteAction.setEnabled( selected && deletable );
    this.moveAction.setEnabled( selected && movable );
    this.holdAction.setEnabled( selected && holdable );
    this.releaseAction.setEnabled( selected && releasable );
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

