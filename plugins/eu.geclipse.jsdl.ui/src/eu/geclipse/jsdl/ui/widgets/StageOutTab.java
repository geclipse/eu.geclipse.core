package eu.geclipse.jsdl.ui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.jsdl.ui.internal.dataStaging.DataStaging;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.jsdl.ui.internal.wizards.FileType;
import eu.geclipse.jsdl.ui.wizards.Messages;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.widgets.TabComponent;


public class StageOutTab extends TabComponent<DataStaging> implements ICellModifier {

  /**
   * Creates StageOut composite
   * 
   * @param contentProvider content provider for table holding IOFiles
   * @param labelProvider label provider for file holding IOFiles
   * @param propertiesVsHearders map containing properties and headers of
   *          table columns
   */
  public StageOutTab( final IStructuredContentProvider contentProvider,
                      final ITableLabelProvider labelProvider,
                      final List<String> propertiesVsHearders,
                      final List<Integer> width )
  {
    super( contentProvider,
           labelProvider,
           propertiesVsHearders,
           150,
           width,
           SWT.BOTTOM );
  }

  @Override
  protected void handleAddButtonSelected()
  {
    handleAddButtonSelected( "", "" ); //$NON-NLS-1$ //$NON-NLS-2$
  }

  protected void handleAddButtonSelected( final String initName,
                                          final String initLocation )
  {
    {
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            Messages.getString( "OutputFilesTab.new_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
      ArrayList<String> comboData = new ArrayList<String>();
      for( FileType fileType : FileType.values() ) {
        comboData.add( fileType.toString() );
      }
      dialog.addTextField( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ), initName, false ); //$NON-NLS-1$
      dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ), initLocation, false, false ); //$NON-NLS-1$
      dialog.setConnectedFields( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ), //$NON-NLS-1$
                                 Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        return;
      }
      String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
      String target = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ) ); //$NON-NLS-1$
      if( name != null
          && target != null
          && name.length() > 0
          && target.length() > 0 )
      {
        DataStaging of;
        try {
          of = new DataStaging( name.trim(), target.trim(), null );
          addVariable( of );
        } catch( Exception e ) {
          MessageDialog.openError( getShell(),
                                   "No local files allowed",
                                   "Target location cannot be local!" );
          handleAddButtonSelected( name.trim(), target.trim() );
        }
      }
    }
  }

  @Override
  protected void handleEditButtonSelected()
  {
    IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
    DataStaging var = ( DataStaging )sel.getFirstElement();
    if( var == null ) {
      // do nothing;
    } else {
      String originalName = var.getName();
      String orginalTarget = var.getTargetLocation();
      String orginalSource = var.getSourceLocation();
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            Messages.getString( "OutputFilesTab.edit_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
      ArrayList<String> comboData = new ArrayList<String>();
      for( FileType fileType : FileType.values() ) {
        comboData.add( fileType.toString() );
      }
      dialog.addTextField( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ), originalName, false ); //$NON-NLS-1$
      dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ), orginalTarget, false, false ); //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        // do nothing;
      } else {
        String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
        orginalTarget = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ) ); //$NON-NLS-1$
        try {
          if( !originalName.equals( name ) ) {
            if( addVariable( new DataStaging( name,
                                              orginalTarget,
                                              orginalSource ) ) )
            {
              this.table.remove( var );
            }
          } else {
            var.setTargetLocation( orginalTarget );
            var.setSourceLocation( null );
            this.table.update( var, null );
            updateLaunchConfigurationDialog();
          }
        } catch( Exception e ) {
          MessageDialog.openError( getShell(),
                                   "No local files allowed",
                                   "Target location cannot be local!" );
          handleEditButtonSelected();
        }
      }
    }
  }

  @Override
  protected void handleRemoveButtonSelected()
  {
    IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
    this.table.getControl().setRedraw( false );
    for( Iterator<?> i = sel.iterator(); i.hasNext(); ) {
      DataStaging var = ( DataStaging )i.next();
      this.table.remove( var );
    }
    this.table.getControl().setRedraw( true );
    // updateAppendReplace();
    updateLaunchConfigurationDialog();
  }

  @Override
  protected void setLabels()
  {
    this.addButton.setText( Messages.getString( "OutputFilesTab.add_file_button" ) ); //$NON-NLS-1$
    this.editButton.setText( Messages.getString( "OutputFilesTab.replace_file_button" ) ); //$NON-NLS-1$
    this.removeButton.setText( Messages.getString( "OutputFilesTab.remove_file_button" ) ); //$NON-NLS-1$
  }

  public boolean canModify( final Object element, final String property ) {
    return true;
  }

  public Object getValue( final Object element, final String property ) {
    int columnIndex = -1;
    if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ) ) ) { //$NON-NLS-1$
      columnIndex = 0;
    }
    if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_target_header" ) ) ) { //$NON-NLS-1$
      columnIndex = 1;
    }
    Object result = null;
    DataStaging task = ( DataStaging )element;
    switch( columnIndex ) {
      case 0:
        result = task.getName();
      break;
      case 1:
        result = task.getTargetLocation();
      break;
      default:
        result = ""; //$NON-NLS-1$
    }
    return result;
  }

  public void modify( final Object element,
                      final String property,
                      final Object value )
  {
    int columnIndex = -1;
    if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ) ) ) { //$NON-NLS-1$
      columnIndex = 0;
    }
    if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_target_header" ) ) ) { //$NON-NLS-1$
      columnIndex = 1;
    }
    TableItem item = ( TableItem )element;
    DataStaging taskOld = ( DataStaging )item.getData();
    DataStaging task;
    try {
      task = new DataStaging( taskOld.getName(),
                              taskOld.getTargetLocation(),
                              taskOld.getSourceLocation() );
      switch( columnIndex ) {
        case 0:
          task.setName( ( String )value );
          if( !task.getName().equals( taskOld.getName() ) ) {
            if( addVariable( task ) ) {
              this.table.remove( taskOld );
            }
          } else {
            this.table.update( taskOld, null );
            updateLaunchConfigurationDialog();
          }
        break;
        case 1:
          task.setTargetLocation( ( String )value );
          if( !task.getName().equals( taskOld.getName() ) ) {
            if( addVariable( task ) ) {
              this.table.remove( taskOld );
            }
          } else {
            taskOld.setTargetLocation( task.getTargetLocation() );
            this.table.update( taskOld, null );
            updateLaunchConfigurationDialog();
          }
        break;
      }
    } catch( Exception e ) {
      MessageDialog.openError( getShell(),
                               "No local files allowed",
                               "Target location cannot be local!" );
    }
  }

  Shell getShellInner() {
    return super.getShell();
  }

  @Override
  protected void addEditors()
  {
    String[] filesTypesString = new String[ FileType.values().length ];
    int i = 0;
    for( FileType type : FileType.values() ) {
      filesTypesString[ i ] = type.getAlias();
      i++;
    }
    addEditor( new TextCellEditor() );
    addEditor( new DialogCellEditor() {

      @Override
      protected Object openDialogBox( final Control cellEditorWindow )
      {
        String filename = "";
        IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                           "Choose a file",
                                                                           null,
                                                                           true );
        if( connection != null ) {
          try {
            filename = connection.getConnectionFileStore().toString();
          } catch( CoreException cExc ) {
            NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
          }
        }
        return filename;
      }
    } );
    setCellModifier( this );
  }
}

