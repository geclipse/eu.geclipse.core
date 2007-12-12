package eu.geclipse.ui.wizards;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.osgi.framework.Bundle;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ArrayTableLabelProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

public class GridProjectStructureWizardPage extends WizardPage {
  
  private static final int LABEL_COLUMN = 1;

  protected TableEditor editor;
  
  private Tree previewTree;
  
  private TreeItem rootItem;
  
  private Table table;
  
  private CheckboxTableViewer viewer;
  
  private Hashtable< String, Class< ? > > creatorTargets;
    
  public GridProjectStructureWizardPage() {
    super( "gridProjectFolders",
           "Grid Project Folders",
           null );
    setDescription( "Specify the folders that should be available for your Grid project" );
    initialize();
  }

  public void createControl( final Composite parent ) {

    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 3, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    mainComp.setLayoutData( gData );
    
    Composite previewComp = new Composite( mainComp, SWT.NONE );
    previewComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_VERTICAL );
    gData.grabExcessVerticalSpace = true;
    previewComp.setLayoutData( gData );
    
    Label previewLabel = new Label( previewComp, SWT.NONE );
    previewLabel.setText( "Preview:" );
    gData = new GridData();
    previewLabel.setLayoutData( gData );
    
    this.previewTree = new Tree( previewComp, SWT.BORDER );
    gData = new GridData( GridData.FILL_VERTICAL );
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 150;
    this.previewTree.setLayoutData( gData );
    
    this.rootItem = new TreeItem( this.previewTree, SWT.NONE );
    this.rootItem.setText( "My Grid Project" );
    this.rootItem.setImage(
        PlatformUI.getWorkbench()
        .getSharedImages()
        .getImage( IDE.SharedImages.IMG_OBJ_PROJECT )
    );
    
    Composite settingsComp = new Composite( mainComp, SWT.NONE );
    settingsComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessVerticalSpace = true;
    gData.grabExcessHorizontalSpace = true;
    settingsComp.setLayoutData( gData );
    
    Label settingsLabel = new Label( settingsComp, SWT.NONE );
    settingsLabel.setText( "Available Folders:" );
    gData = new GridData();
    settingsLabel.setLayoutData( gData );
    
    this.table = new Table( settingsComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK | SWT.FULL_SELECTION );
    this.table.setHeaderVisible( true );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.table.setLayoutData( gData );
    
    TableColumn nameColumn = new TableColumn( this.table, SWT.NONE );
    nameColumn.setText( "Folder ID" );
    nameColumn.setWidth( 150 );
    TableColumn labelColumn = new TableColumn( this.table, SWT.None );
    labelColumn.setText( "Folder Label" );
    labelColumn.setWidth( 100 );
    
    this.editor = new TableEditor( this.table );
    this.editor.grabHorizontal = true;
    this.editor.minimumWidth = 100;
    this.editor.horizontalAlignment = SWT.LEFT;
    
    this.table.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        startEditing( ( TableItem ) e.item );
      }
    } );
    
    this.viewer = new CheckboxTableViewer( this.table );
    this.viewer.setContentProvider( new ArrayContentProvider() );
    this.viewer.setLabelProvider( new ArrayTableLabelProvider() );
    
    List< String[] > input = new ArrayList< String[] >();
    List< String[] > checked = new ArrayList< String[] >();
    ExtensionManager extm = new ExtensionManager();
    List< IConfigurationElement > configurationElements
      = extm.getConfigurationElements( Extensions.PROJECT_FOLDER_POINT, Extensions.PROJECT_FOLDER_ELEMENT );
    for ( IConfigurationElement element : configurationElements ) {
      String id = element.getAttribute( Extensions.PROJECT_FOLDER_ID_ATTRIBUTE );
      String name = element.getAttribute( Extensions.PROJECT_FOLDER_NAME_ATTRIBUTE );
      String label = element.getAttribute( Extensions.PROJECT_FOLDER_LABEL_ATTRIBUTE );
      String cls = element.getAttribute( Extensions.PROJECT_FOLDER_ELEMENTCLASS_ATTRIBUTE );
      boolean preset = Boolean.parseBoolean( element.getAttribute( Extensions.PROJECT_FOLDER_PRESET_ATTRIBUTE ) );
      String[] row = new String[] { name, label, id, cls };
      input.add( row );
      if ( preset ) {
        checked.add( row );
      }
    }
    this.viewer.setInput( input );
    this.viewer.setCheckedElements( checked.toArray() );
    
    this.viewer.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent e ) {
        updateTree();
      }
    } );
    
    updateTree();
    
    setControl( mainComp );
    
  }
  
  public Hashtable< String, String > getProjectFolders() {
    
    Hashtable< String, String > result = new Hashtable< String, String >();
    
    List< String[] > input = ( List< String[] > ) this.viewer.getInput();
    TableItem[] items = this.table.getItems();
    
    for ( int i = 0 ; i < items.length ; i++ ) {
      if ( items[i].getChecked() ) {
        String id = input.get( i )[2];
        String label = items[i].getText( 1 );
        result.put( id, label );
      }
    }
    
    return result;
    
  }
  
  protected void updateTree() {
    
    this.rootItem.removeAll();
    
    Object[] checked = this.viewer.getCheckedElements();
    TableItem[] data = this.table.getItems();
    TreeItem[] treeItems = new TreeItem[ checked.length ];
    int index = 0;
    for ( TableItem row : data ) {
      if ( row.getChecked() ) {
        treeItems[ index ] = new TreeItem( this.rootItem, SWT.NONE );
        treeItems[ index ].setText( row.getText( LABEL_COLUMN ) );
        treeItems[ index ].setImage(
            PlatformUI.getWorkbench()
            .getSharedImages()
            .getImage( ISharedImages.IMG_OBJ_FOLDER )
        );
        index++;
      }
    }
    
    for ( String name : this.creatorTargets.keySet() ) {
      
      Class< ? > creatorTarget = this.creatorTargets.get( name );
      
      TreeItem parentItem = this.rootItem;
      for ( int i = 0 ; i < checked.length ; i++ ) {
        try {
          Class< ? > fcls = Class.forName( ( ( String[] ) checked[i] ) [ 3 ] );
          if ( fcls.isAssignableFrom( creatorTarget ) ) {
            parentItem = treeItems[ i ];
          }
        } catch ( ClassNotFoundException cnfExc ) {
          Activator.logException( cnfExc );
        }
      }
      TreeItem leafItem = new TreeItem( parentItem, SWT.NONE );
      leafItem.setText( name );
      leafItem.setImage(
          PlatformUI.getWorkbench()
          .getSharedImages()
          .getImage( ISharedImages.IMG_OBJ_FILE )
      );
      
    }
        
    this.rootItem.setExpanded( true );
    for ( TreeItem child : this.rootItem.getItems() ) {
      child.setExpanded( true );
    }
    
  }
  
  private void startEditing( final TableItem item ) {
    
    Control oldEditor = this.editor.getEditor();
    if ( oldEditor != null ) {
      oldEditor.dispose();
    }
    
    if ( item != null ) {
      
      Text text = new Text( this.table, SWT.NONE );
      text.setText( item.getText( LABEL_COLUMN ) );
      
      text.addModifyListener( new ModifyListener() {
        public void modifyText( final ModifyEvent e ) {
          Text t = ( Text ) GridProjectStructureWizardPage.this.editor.getEditor();
          GridProjectStructureWizardPage.this.editor.getItem().setText( LABEL_COLUMN, t.getText() );
          updateTree();
        }
      } );
      
      text.selectAll();
      text.setFocus();
      this.editor.setEditor( text, item, LABEL_COLUMN );
      
    }
    
  }
  
  private void initialize() {
    
    if ( this.creatorTargets == null ) {
      
      this.creatorTargets = new Hashtable< String, Class< ? > >();
    
      ExtensionManager extm = new ExtensionManager();
      List< IConfigurationElement > creators
        = extm.getConfigurationElements( Extensions.GRID_ELEMENT_CREATOR_POINT, Extensions.GRID_ELEMENT_CREATOR_ELEMENT );
      
      for ( IConfigurationElement creator : creators ) {
        IConfigurationElement[] targets
          = creator.getChildren( Extensions.GRID_ELEMENT_CREATOR_TARGET_ELEMENT );
        if ( targets != null ) {
          for ( IConfigurationElement target : targets ) {
            String name = target.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_NAME_ATTRIBUTE );
            String className = target.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_CLASS_ATTRIBUTE );
            String contributor = target.getContributor().getName();
            Bundle bundle = Platform.getBundle( contributor );
            if ( bundle != null ) {
              try {
                Class< ? > cls = bundle.loadClass( className );
                this.creatorTargets.put( name, cls );
              } catch ( ClassNotFoundException cnfExc ) {
                Activator.logException( cnfExc );
              }
            }
          }
        }
        
      }
      
    }
    
  }

}
