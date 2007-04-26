package eu.geclipse.ui.internal.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableItem;
import eu.geclipse.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.widgets.TabComponent;
import eu.geclipse.ui.wizards.jobs.Messages;

public class JSDLRangesTab extends TabComponent {

  public JSDLRangesTab( IStructuredContentProvider contentProvider,
                        ITableLabelProvider labelProvider,
                        HashMap propertiesVsHearders,
                        int hight,
                        int width )
  {
    super( contentProvider, labelProvider, propertiesVsHearders, hight, width );
  }

  @Override
  protected void handleAddButtonSelected()
  {
    MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                          "New value" );
    dialog.addTextField( "Range start", null, false );
    dialog.addTextField( "Range end", null, false );
    // dialog.addVariablesField(
    // Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field"),
    // null, true ); //$NON-NLS-1$
    if( dialog.open() != Window.OK ) {
      return;
    }
    String start = dialog.getStringValue( "Range start" );
    String end = dialog.getStringValue( "Range end" );
    try{
    Range range = new Range( Double.valueOf( start ).doubleValue(), Double.valueOf( end ).doubleValue() );
    addVariable( range );
    } catch (NumberFormatException exc){
      MessageDialog.openError( getShell(), "Bad range format", "Bad range format");
    }
  }

  @Override
  protected void handleEditButtonSelected()
  {
    IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
    Range var = ( Range )sel.getFirstElement();
    if( var == null ) {
      // do nothing;
    } else {
      String oldStart = Double.valueOf( var.getStart() ).toString();
      String oldEnd = Double.valueOf( var.getEnd() ).toString();
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            Messages.getString( "OutputFilesTab.edit_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
      ArrayList<String> comboData = new ArrayList<String>();
      for( FileType fileType : FileType.values() ) {
        comboData.add( fileType.toString() );
      }
      dialog.addTextField( "Range start", oldStart, false ); //$NON-NLS-1$
      dialog.addTextField( "Range end", oldEnd, false );
      if( dialog.open() != Window.OK ) {
        // do nothing;
      } else {
        try {
          String newStart = dialog.getStringValue( "Range start" );
          String newEnd = dialog.getStringValue( "Range end" );
          if( !oldStart.equals( newStart ) || !oldEnd.equals( newEnd ) ) {
            Range newRange = new Range( Double.valueOf( newStart ).doubleValue(),
                                        Double.valueOf( newEnd ).doubleValue() );
            if( addVariable( newRange ) ) {
              this.table.remove( var );
            }
          } else {
            var.setStart( Double.valueOf( newStart ).doubleValue() );
            var.setEnd( Double.valueOf( newEnd ).doubleValue() );
            this.table.update( var, null );
            updateLaunchConfigurationDialog();
          }
        } catch( NumberFormatException exc ) {
          MessageDialog.openError( getShell(), "Bad range format", "Bad range format");
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
      Range var = ( Range )i.next();
      this.table.remove( var );
    }
    this.table.getControl().setRedraw( true );
    // updateAppendReplace();
    updateLaunchConfigurationDialog();
  }

  @Override
  protected void setLabels()
  {
    this.addButton.setText( "Add" );
    this.editButton.setText( "Edit" );
    this.removeButton.setText( "Remove" );
  }

  protected boolean addVariable( final Range variable ) {
    boolean result = true;
    TableItem[] items = this.table.getTable().getItems();
    for( int i = 0; i < items.length; i++ ) {
      Range existingVariable = ( Range )items[ i ].getData();
      if( existingVariable.equals( variable ) ) {
        MessageDialog.openError( getShell(),
                                 "Range conflict",
                                 "Range you provided is in conflict with range ("
                                     + existingVariable.getStart()
                                     + ", "
                                     + existingVariable.getEnd()
                                     + ")" );
        result = false;
        break;
      } else {
        if( existingVariable.getStart() >= variable.getStart()
            && existingVariable.getStart() < variable.getEnd() )
        {
          MessageDialog.openError( getShell(),
                                   "Range conflict",
                                   "Range you provided is in conflict with range ("
                                       + existingVariable.getStart()
                                       + ", "
                                       + existingVariable.getEnd()
                                       + ")" );
          result = false;
          break;
        } else {
          if( variable.getStart() >= existingVariable.getStart()
              && variable.getStart() < existingVariable.getEnd() )
          {
            MessageDialog.openError( getShell(),
                                     "Range conflict",
                                     "Range you provided is in conflict with range ("
                                         + existingVariable.getStart()
                                         + ", "
                                         + existingVariable.getEnd()
                                         + ")" );
            result = false;
            break;
          }
        }
      }
    }
    if( result ) {
      this.table.add( variable );
    }
    return result;
  }
}