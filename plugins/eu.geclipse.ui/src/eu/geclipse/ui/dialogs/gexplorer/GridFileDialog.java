/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/

package eu.geclipse.ui.dialogs.gexplorer;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.views.navigator.ResourcePatternFilter;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.connection.FileSystemsProvider;
import eu.geclipse.ui.views.gexplorer.GExplorerComparator;
import eu.geclipse.ui.views.gexplorer.GExplorerLabelProvider;
import eu.geclipse.ui.views.gexplorer.ResourceNode;
import eu.geclipse.ui.widgets.StoredCombo;
import eu.geclipse.ui.wizards.connection.ConnectionWizard;

/**
 * Dialog that allows user to choose resources from local or remopte filesystems
 * 
 * @author katis
 */
public class GridFileDialog extends Dialog implements Listener{

  private static String EXTENSIONS_ID = "fileDialog_extensions"; //$NON-NLS-1$
  String result = null;
  // IFile filesystemsFile;
  TreeViewer newViewer;
  Text fileName;
  StoredCombo fileExtension;
  Shell shell;
  private Display display;

  /**
   * GridFileDialog constructor
   * 
   * @param parent parent's Shell
   */
  public GridFileDialog( final Shell parent ) {
    super( parent );
  }

  /**
   * Method used to show GridFileDialog. GridFileDialog will remain open until
   * user closes its window
   * 
   * @return URIs to files chosen by user (URI in form of String)
   */
  public String open() {
    Shell parent = getParent();
    // this.filesystemsFile = newFileHandle;
    this.shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL );
    this.shell.setText( Messages.getString( "GridFileDialog.dialog_title" ) ); //$NON-NLS-1$
    createControl( this.shell );
    this.shell.setSize( 450, 500 );
    this.shell.open();
    this.display = parent.getDisplay();
    while( !this.shell.isDisposed() ) {
      if( !this.display.readAndDispatch() ) {
        this.display.sleep();
      }
    }
    return this.result;
  }

  /**
   * Method that creates all widgets and controls used by GridFileDialog
   * 
   * @param parent Parent composite
   */
  private void createControl( final Composite parent ) {
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 15;
    gLayout.verticalSpacing = 12;
    parent.setLayout( gLayout );
    this.newViewer = new TreeViewer( parent, SWT.VIRTUAL
                                             | SWT.MULTI
                                             | SWT.V_SCROLL
                                             | SWT.H_SCROLL );
//    this.newViewer.setContentProvider( new GridModelContentProvider() );
//    this.newViewer.setLabelProvider( new GridModelLabelProvider() );
//    
//    this.newViewer.setInput( GridModel.getRoot() );
//    this.newViewer.setComparator( new GridModelComparator() );
    this.newViewer.setContentProvider( new TreeNodeContentProvider() );
    this.newViewer.setLabelProvider( new GExplorerLabelProvider() );
    this.newViewer.setComparator( new GExplorerComparator() );
    ResourcePatternFilter filter = new ResourcePatternFilter();
    this.newViewer.addFilter( filter );
    GridData gridData = new GridData( GridData.FILL_BOTH
                                      | SWT.H_SCROLL
                                      | SWT.V_SCROLL );
    gridData.horizontalSpan = 2;
    this.newViewer.getTree().setLayoutData( gridData );
    refresh();
    Button newConnectionButton = new Button( parent, SWT.NONE );
    gridData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    newConnectionButton.setText( Messages.getString( "GridFileDialog.new_connection" ) ); //$NON-NLS-1$
    newConnectionButton.setLayoutData( gridData );
    newConnectionButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        WizardDialog wizardDialog = new WizardDialog( Display.getCurrent()
          .getActiveShell(), new ConnectionWizard() );
        wizardDialog.open();
        GridFileDialog.this.refresh();
      }
    } );
    Label fileNameLabel = new Label( parent, SWT.LEFT );
    fileNameLabel.setText( Messages.getString( "GridFileDialog.file_label" ) ); //$NON-NLS-1$
    this.fileName = new Text( parent, SWT.LEFT | SWT.BORDER );
    gridData = new GridData( GridData.FILL_HORIZONTAL
                             | GridData.GRAB_HORIZONTAL );
    this.fileName.setLayoutData( gridData );
    Button openFileButton = new Button( parent, SWT.NONE );
    gridData = new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING
                             | GridData.HORIZONTAL_ALIGN_FILL );
    openFileButton.setLayoutData( gridData );
    openFileButton.setText( Messages.getString( "GridFileDialog.open_button" ) ); //$NON-NLS-1$
    openFileButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog.this.result = GridFileDialog.this.fileName.getText();
        GridFileDialog.this.shell.dispose();
      }
    } );
    Label fileExtensionLabel = new Label( parent, SWT.LEFT );
    fileExtensionLabel.setText( Messages.getString( "GridFileDialog.extension_label" ) ); //$NON-NLS-1$
    // TODO add filter to a tree
    this.fileExtension = new StoredCombo( parent, SWT.LEFT | SWT.BORDER );
    IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
    this.fileExtension.setPreferences( prefs, EXTENSIONS_ID );
    this.fileExtension.setText( "*" ); //$NON-NLS-1$
    gridData = new GridData( GridData.FILL_HORIZONTAL
                             | GridData.GRAB_HORIZONTAL );
    this.fileExtension.setLayoutData( gridData );
    this.fileExtension.addListener( SWT.KeyDown, this );
//    fileExtension.addModifyListener( this );
    Button cancelButton = new Button( parent, SWT.NONE );
    gridData = new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING
                             | GridData.HORIZONTAL_ALIGN_FILL );
    cancelButton.setLayoutData( gridData );
    cancelButton.setText( Messages.getString( "GridFileDialog.cancel_label" ) ); //$NON-NLS-1$
    cancelButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog.this.shell.dispose();
      }
    } );
    this.newViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      @SuppressWarnings("unchecked")
      public void selectionChanged( final SelectionChangedEvent event )
      {
        // if the selection is empty clear the label
        if( event.getSelection().isEmpty() ) {
          GridFileDialog.this.fileName.setText( "" ); //$NON-NLS-1$
          return;
        }
        if( event.getSelection() instanceof IStructuredSelection ) {
          IStructuredSelection selection = ( IStructuredSelection )event.getSelection();
          StringBuilder toShow = new StringBuilder();
          // for (ResourceNode domain: (ResourceNode[])(selection.toArray())){
          // String value = ( ( GExplorerLabelProvider
          // )GridFileDialog.this.newViewer.getLabelProvider() ).getText( domain
          // );
          // IFileStore fileStore = ( IFileStore )domain.getValue();
          // value = fileStore.toURI().toString();
          // toShow.append( value );
          // }
          for( Iterator iterator = selection.iterator(); iterator.hasNext(); ) {
            ResourceNode domain = ( ResourceNode )iterator.next();
            String value = ( ( GExplorerLabelProvider )GridFileDialog.this.newViewer.getLabelProvider() ).getText( domain );
            IFileStore fileStore = ( IFileStore )domain.getValue();
            value = fileStore.toURI().toString();
            toShow.append( value );
          }
          if( toShow.length() > 0 ) {
            toShow.setLength( toShow.length() );
          }
          GridFileDialog.this.fileName.setText( toShow.toString() );
        }
      }
    } );
    this.newViewer.addFilter( new PatternFilter() );
//    this.newViewer.addFilter( new ViewerFilter(){
//
//      @Override
//        public boolean select( Viewer viewer, Object parentElement, Object element )
//      {
//        boolean result = true;
//        if( !fileExtension.getText().equals( "*" ) ) {
//          result = false;
//          ResourceNode el = ( ResourceNode )element;
//          if( el.hasChildren() ) {
//            for (TreeNode node: el.getChildren()){
//            FileStore file = ( FileStore )el.getValue();
//            String name = file.fetchInfo().getName();
//            if( name.endsWith( "." + fileExtension.getText() ) ) {
//              result = true;
//              PatternFilter a = new PatternFilter();
//              a.setPattern( patternString )
//              newViewer.addFilter( a );
//            }
//          }
//          }
//        }
//        return result;
//      }
//    }
//    );
  }

  /**
   * Refreshes GridFileDialog's tree viewer in order to show new file systems
   */
  void refresh() {
    ArrayList<ResourceNode> nodes = new ArrayList<ResourceNode>();
    for (ViewerFilter filter: this.newViewer.getFilters()){
      if (filter instanceof ResourcePatternFilter){
        String extension = null;
        if (this.fileExtension == null){
          extension = "*";
        } else {
          extension = this.fileExtension.getText();
        }
        String[] patterns = { "*." + extension};
        ((ResourcePatternFilter)filter).setPatterns( patterns );
      }
    }
    ResourcePatternFilter a;
    nodes = FileSystemsProvider.getFileSystems();
    if( this.newViewer.getInput() == null ) {
      ResourceNode[] c = new ResourceNode[ nodes.size() ];
      c = nodes.toArray( c );
      this.newViewer.setInput( c );
    } else {
      ResourceNode[] c = new ResourceNode[ nodes.size() ];
      c = nodes.toArray( c );
      this.newViewer.setInput( c );
    }
    this.newViewer.refresh();
  }

  public void handleEvent( final Event event ) {
    refresh();
  }

  
}
