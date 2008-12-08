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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.views;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.ui.Activator;
import eu.geclipse.servicejob.ui.providers.DetailsContentProvider;
import eu.geclipse.servicejob.ui.providers.DetailsLabelProvider;

/**
 * View that is called "Operator's Job History". It displays detailed information
 * about single service job, such as: list of resources on which this service 
 * job was run, sub-jobs list and results for each sub-job and each resource.
 */
public class ServiceJobDetailsView extends ViewPart
  implements ISelectionListener, SelectionListener, IGridModelListener
{

  /**
   * Name of folder (in state location of this plug-in) where service jobs's 
   * result data is being serialized to files when needed (e.g. when this data
   * can be opened only with external - system - editor).
   */
  public static final String SERVICE_JOB_FILES_FOLDER = "./serviceJobFiles"; //$NON-NLS-1$
  protected Point pt = new Point( 0, 0 );
  int selectedColumnIndex;
  String selectedColumnText;
  TreeViewer viewer;
  IServiceJob rootElement;
  private DetailsContentProvider contentProvider;
  private DetailsLabelProvider labelProvider;
  private Composite parent;

  /**
   * Constructs instance of this view.
   */
  public ServiceJobDetailsView() {
    GridModel.addGridModelListener( this );
    this.contentProvider = new DetailsContentProvider( null );
    this.labelProvider = new DetailsLabelProvider();
  }

  @Override
  public void createPartControl( final Composite parent1 ) {
    this.parent = parent1;
    this.rootElement = findSelectedServiceJob();
    if( this.rootElement != null ) {
      updateViewer( this.rootElement );
    } else {
      Composite mainComp = new Composite( parent1, SWT.NONE );
      mainComp.setLayout( new GridLayout( 1, false ) );
      Label label = new Label( mainComp, SWT.LEAD );
      label.setText( Messages.getString( "ServiceJobsDetailsView.no_service_job_selected" ) ); //$NON-NLS-1$
      label.setLayoutData( new GridData() );
    }
    getSite().getPage().addSelectionListener( this );
  }

  private void updateViewer( final IServiceJob newInput ) {
    createViewer( this.parent );
    List<TreeColumn> columns = new ArrayList<TreeColumn>();
    TreeColumn nameColumn = new TreeColumn( this.viewer.getTree(), SWT.NONE );
    nameColumn.setText( "Resource" ); //$NON-NLS-1$
    nameColumn.setAlignment( SWT.LEFT );
    nameColumn.setWidth( 200 );
    if( newInput != null ) {
      String[] properties = new String[ newInput.getSingleServiceJobNames().size() + 1 ];
      properties[ 0 ] = Messages.getString( "ServiceJobsDetailsView.name" ); //$NON-NLS-1$
      int i = 0;
      for( String subServiceJob : newInput.getSingleServiceJobNames() ) {
        columns.add( new TreeColumn( this.viewer.getTree(), SWT.NONE ) );
        columns.get( i ).setText( subServiceJob );
        columns.get( i ).setAlignment( SWT.LEFT );
        columns.get( i ).setWidth( newInput.getColumnWidth( subServiceJob ) );
        properties[ i ] = subServiceJob;
        i++;
      }
    }
    ( ( DetailsContentProvider )this.viewer.getContentProvider() ).changeRoot( newInput );
    ( ( DetailsLabelProvider )this.viewer.getLabelProvider() ).changeRoot( newInput );
    this.viewer.setInput( newInput );
    this.viewer.refresh();
    this.parent.redraw();
    this.parent.update();
    addSelectionListenerForEditor();
    this.parent.layout();
  }

  private void addSelectionListenerForEditor() {
    this.viewer.addDoubleClickListener( new IDoubleClickListener() {

      @SuppressWarnings("unchecked")
      public void doubleClick( final DoubleClickEvent event ) {
        ISelection sel = event.getSelection();
        if( sel instanceof TreeSelection ) {
          TreeSelection structured = ( TreeSelection )sel;
          if( structured.getFirstElement() instanceof List
              && ServiceJobDetailsView.this.selectedColumnIndex != 0 )
          {
            List<IServiceJobResult> list = ( List<IServiceJobResult> )structured.getFirstElement();
            for( IServiceJobResult result : list ) {
              if( result.getSubServiceJobName()
                .equals( ServiceJobDetailsView.this.selectedColumnText )
                  && ServiceJobDetailsView.this.rootElement.getInputStreamForResult( result ) != null )
              {
                String extension = result.getResultType();
                if( extension == null || extension.equals( "" ) ) { //$NON-NLS-1$
                  extension = "txt"; //$NON-NLS-1$
                }
                IWorkbenchWindow window = PlatformUI.getWorkbench()
                  .getActiveWorkbenchWindow();
                IWorkbenchPage page = window.getActivePage();
                if( page != null ) {
                  try {
                    IEditorDescriptor desc = PlatformUI.getWorkbench()
                      .getEditorRegistry()
                      .getDefaultEditor( "name." + extension ); //$NON-NLS-1$
                    if( desc != null ) {
                      IStorage storage = new StreamStorage( ServiceJobDetailsView.this.rootElement.getInputStreamForResult( result ),
                                                            result.getSubServiceJobName()
                                                                + " - " //$NON-NLS-1$
                                                                + result.getResourceName() );
                      IStorageEditorInput editorInput = new StreamInput( storage,
                                                                         result );
                      page.openEditor( editorInput, desc.getId() );
                    } else {
                      File fileForEditor = null;
                      if( ( fileForEditor = Activator.getFileForServiceJobResult( result ) ) == null )
                      {
                        fileForEditor = serializeInputStream( ServiceJobDetailsView.this.rootElement.getInputStreamForResult( result ),
                                                              extension );
                        Activator.addFileForServiceJobResult( result, fileForEditor );
                      }
                      IFileStore fileStore = EFS.getLocalFileSystem()
                        .getStore( fileForEditor.toURI() );
                      IDE.openEditorOnFileStore( page, fileStore );
                    }
                  } catch( PartInitException e ) {
                    // TODO katis
                    Activator.logException( e );
                  }
                }
                break;
              }
            }
          }
        }
      }
    } );
    this.viewer.getTree().addListener( SWT.EraseItem, new Listener() {

      public void handleEvent( final Event event ) {
        event.detail &= ~SWT.HOT;
        if( ( event.detail & SWT.SELECTED ) != 0 ) {
          GC gc = event.gc;
          Rectangle area = ServiceJobDetailsView.this.viewer.getTree()
            .getClientArea();
          int columnCount = ServiceJobDetailsView.this.viewer.getTree()
            .getColumnCount();
          if( event.index == columnCount - 1 || columnCount == 0 ) {
            int width = area.x + area.width - event.x;
            if( width > 0 ) {
              Region region = new Region();
              gc.getClipping( region );
              region.add( event.x, event.y, width, event.height );
              gc.setClipping( region );
              region.dispose();
            }
          }
          gc.setAdvanced( true );
          if( gc.getAdvanced() ) {
            gc.setAlpha( 127 );
          }
          Rectangle rect = event.getBounds();
          TreeItem item = ServiceJobDetailsView.this.viewer.getTree()
            .getItem( ServiceJobDetailsView.this.pt );
          for( int i = 0; i < ServiceJobDetailsView.this.viewer.getTree()
            .getColumnCount(); i++ )
          {
            if( item != null ) {
              Rectangle rect1 = item.getBounds( i );
              if( rect1.contains( ServiceJobDetailsView.this.pt ) ) {
                rect = rect1;
              }
            }
          }
          gc.setForeground( Display.getCurrent()
            .getSystemColor( SWT.COLOR_LIST_SELECTION ) );
          gc.setBackground( Display.getCurrent()
            .getSystemColor( SWT.COLOR_LIST_SELECTION ) );
          gc.fillGradientRectangle( rect.x,
                                    rect.y,
                                    rect.width,
                                    rect.height,
                                    false );
          gc.setForeground( Display.getCurrent()
            .getSystemColor( SWT.COLOR_LIST_FOREGROUND ) );
          gc.setBackground( Display.getCurrent()
            .getSystemColor( SWT.COLOR_LIST_BACKGROUND ) );
          event.detail &= ~SWT.SELECTED;
        }
      }
    } );
  }
  class StringStorage extends PlatformObject implements IStorage {

    private String string;

    StringStorage( final String input ) {
      this.string = input;
    }

    public InputStream getContents() throws CoreException {
      return new ByteArrayInputStream( this.string.getBytes() );
    }

    public IPath getFullPath() {
      return null;
    }

    public String getName() {
      int len = Math.min( 5, this.string.length() );
      return this.string.substring( 0, len ).concat( "..." ); //$NON-NLS-1$
    }

    public boolean isReadOnly() {
      return true;
    }
  }
  class StringInput extends PlatformObject implements IStorageEditorInput {

    private IStorage storage;

    StringInput( final IStorage storage ) {
      this.storage = storage;
    }

    public boolean exists() {
      return true;
    }

    public ImageDescriptor getImageDescriptor() {
      return null;
    }

    public String getName() {
      return this.storage.getName();
    }

    public IPersistableElement getPersistable() {
      return null;
    }

    public IStorage getStorage() {
      return this.storage;
    }

    public String getToolTipText() {
      return Messages.getString( "ServiceJobsDetailsView.string_based_file" ) + ": " + this.storage.getName(); //$NON-NLS-1$ //$NON-NLS-2$
    }
  }

  private void createViewer( final Composite parent1 ) {
    TreeViewer tViewer = null;
    for( Control comp : parent1.getChildren() ) {
      comp.dispose();
    }
    tViewer = new TreeViewer( parent1, SWT.VIRTUAL
                                       | SWT.MULTI
                                       | SWT.H_SCROLL
                                       | SWT.V_SCROLL
                                       | SWT.FULL_SELECTION );
    Tree tree = tViewer.getTree();
    tree.setHeaderVisible( true );
    this.viewer = tViewer;
    this.viewer.setContentProvider( this.contentProvider );
    this.viewer.setLabelProvider( this.labelProvider );
    this.viewer.getTree().addListener( SWT.MouseDoubleClick, new Listener() {

      public void handleEvent( final Event event ) {
        Point point = new Point( event.x, event.y );
        TreeItem item = ServiceJobDetailsView.this.viewer.getTree().getItem( point );
        if( item != null ) {
          for( int i = 0; i < ServiceJobDetailsView.this.viewer.getTree()
            .getColumnCount(); i++ )
          {
            Rectangle rect = item.getBounds( i );
            if( rect.contains( point ) ) {
              if( !ServiceJobDetailsView.this.viewer.getTree()
                .getItem( point )
                .getText( i )
                .equals( "" ) ) //$NON-NLS-1$
              {
                ServiceJobDetailsView.this.selectedColumnIndex = i;
                ServiceJobDetailsView.this.selectedColumnText = ServiceJobDetailsView.this.viewer.getTree()
                  .getColumn( i )
                  .getText();
                break;
              } else {
                // TODO katis
                // do nothing
              }
            } else {
              ServiceJobDetailsView.this.selectedColumnText = ""; //$NON-NLS-1$
              ServiceJobDetailsView.this.selectedColumnIndex = -1;
            }
          }
        }
      }
    } );
  }

  File serializeInputStream( final InputStream inputStream, final String type )
  {
    File res = null;
    File serviceJobFolder = Activator.getDefault()
      .getStateLocation()
      .append( SERVICE_JOB_FILES_FOLDER )
      .toFile();
    if( !serviceJobFolder.exists() ) {
      serviceJobFolder.mkdir();
    }
    boolean fileCreated = false;
    String fileNameBase = "file"; //$NON-NLS-1$
    String fileNameSufix = ""; //$NON-NLS-1$
    int i = 0;
    while( !fileCreated ) {
      String fileName = fileNameBase + fileNameSufix + "." + type; //$NON-NLS-1$
      Path filePath = new Path( serviceJobFolder.getAbsolutePath() );
      if( filePath.append( fileName ).toFile().exists() ) {
        fileNameSufix = Integer.toString( i );
        i++;
        if( i == 100 ) {
          break;
        }
      } else {
        DataOutputStream out = null;
        try {
          File newFile = filePath.append( fileName ).toFile();
          fileCreated = true;
          if( newFile.createNewFile() ) {
            out = new DataOutputStream( new BufferedOutputStream( new FileOutputStream( newFile ) ) );
            int c;
            while( ( c = inputStream.read() ) != -1 ) {
              out.writeByte( c );
            }
            res = newFile;
          }
        } catch( IOException e ) {
          // TODO Auto-generated catch block
          Activator.logException( e );
        } finally {
          try {
            inputStream.close();
            if( out != null ) {
              out.close();
            }
          } catch( IOException e ) {
            //Ignore
          }
        }
      }
    }
    return res;
  }

  protected IServiceJob findSelectedServiceJob() {
    IServiceJob result = null;
    ISelection selection = getSite().getPage().getSelection();
    if( selection != null && selection instanceof IStructuredSelection ) {
      Object obj = ( ( IStructuredSelection )selection ).getFirstElement();
      if( obj != null && obj instanceof IServiceJob ) {
        result = ( IServiceJob )obj;
      }
    }
    return result;
  }

  @Override
  public void setFocus() {
    // TODO Auto-generated method stub
  }

  public void selectionChanged( final IWorkbenchPart part,
                                final ISelection selection )
  {
    if( selection instanceof IStructuredSelection ) {
      Object obj = ( ( IStructuredSelection )selection ).getFirstElement();
      if( obj != null && obj instanceof IServiceJob ) {
        this.rootElement = ( IServiceJob )obj;
        updateViewer( ( IServiceJob )obj );
        this.viewer.getTree().update();
        this.viewer.getTree().redraw();
        this.viewer.getTree().layout();
        this.parent.redraw();
        this.parent.update();
        this.parent.layout();
      }
    }
  }

  @Override
  public void dispose() {
    getSite().getPage().removeSelectionListener( this );
    super.dispose();
  }

  public void widgetDefaultSelected( final SelectionEvent e ) {
    // empty
  }

  public void widgetSelected( final SelectionEvent e ) {
    // empty
  }

  public void gridModelChanged( final IGridModelEvent event ) {
    if( event.getType() == IGridModelEvent.ELEMENTS_CHANGED ) {
      IGridElement[] removedElements = event.getElements();
      if( this.viewer != null ) {
        Control control = this.viewer.getControl();
        for( IGridElement elem : removedElements ) {
          if( elem instanceof IServiceJob ) {
            if( !control.isDisposed() ) {
              Display display = control.getDisplay();
              display.asyncExec( new Runnable() {

                public void run() {
                  ServiceJobDetailsView.this.viewer.refresh( true );
                }
              } );
            }
          }
        }
      }
    }
  }
  class StreamStorage extends PlatformObject implements IStorage {

    private InputStream stream;
    private String name;

    StreamStorage( final InputStream input, final String name ) {
      this.stream = input;
      this.name = name;
    }

    public InputStream getContents() throws CoreException {
      return this.stream;
    }

    public IPath getFullPath() {
      return null;
    }

    public String getName() {
      return this.name;
    }

    public boolean isReadOnly() {
      return true;
    }
  }
  class StreamInput extends PlatformObject implements IStorageEditorInput {

    private IStorage storage;
    private IServiceJobResult result;

    StreamInput( final IStorage sto, final IServiceJobResult result ) {
      this.storage = sto;
      this.result = result;
    }

    public boolean exists() {
      return true;
    }

    public ImageDescriptor getImageDescriptor() {
      return null;
    }

    public String getName() {
      return this.storage.getName();
    }

    public IPersistableElement getPersistable() {
      return null;
    }

    public IStorage getStorage() {
      return this.storage;
    }

    public String getToolTipText() {
      return Messages.getString( "ServiceJobsDetailsView.results" ) + ":" + this.storage.getName(); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public boolean equals( final Object obj ) {
      boolean res = super.equals( obj );
      if( !res && obj instanceof StreamInput ) {
        res = this.result.equals( ( ( StreamInput )obj ).result );
      }
      return res;
    }
    
    @Override
    public int hashCode() {
      return this.result.hashCode();
    }
    
  }
}
