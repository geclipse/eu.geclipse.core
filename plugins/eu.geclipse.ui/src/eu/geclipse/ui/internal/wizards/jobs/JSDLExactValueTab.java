package eu.geclipse.ui.internal.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.Window;
import eu.geclipse.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.widgets.TabComponent;
import eu.geclipse.ui.wizards.jobs.Messages;


public class JSDLExactValueTab extends TabComponent {

  public JSDLExactValueTab( IStructuredContentProvider contentProvider, ITableLabelProvider labelProvider, HashMap propertiesVsHearders, int hight, int width ) {
    super( contentProvider, labelProvider, propertiesVsHearders, hight, width );
  }

  @Override
  protected void handleAddButtonSelected()
  {
    MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                          "New value" );
    dialog.addTextField( "Value", null, false );
    dialog.addTextField( "Epsilon", null, false );
    // dialog.addVariablesField(
    // Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field"),
    // null, true ); //$NON-NLS-1$
    if( dialog.open() != Window.OK ) {
      return;
    }
    String val = dialog.getStringValue( "Value" );
    String eps = dialog.getStringValue( "Epsilon" );
    try{
    ValueWithEpsilon value = new ValueWithEpsilon( Double.valueOf( val ).doubleValue(), Double.valueOf( eps ).doubleValue() );
    addVariable( value );
    } catch (NumberFormatException exc){
      MessageDialog.openError( getShell(), "Bad number format", "Bad number format");
    }
  }

  @Override
  protected void handleEditButtonSelected()
  {
    IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
    ValueWithEpsilon var = ( ValueWithEpsilon )sel.getFirstElement();
    if( var == null ) {
      // do nothing;
    } else {
      String oldStart = Double.valueOf( var.getValue() ).toString();
      String oldEnd = Double.valueOf( var.getEpsilon() ).toString();
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            Messages.getString( "OutputFilesTab.edit_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
      ArrayList<String> comboData = new ArrayList<String>();
      for( FileType fileType : FileType.values() ) {
        comboData.add( fileType.toString() );
      }
      dialog.addTextField( "Value", oldStart, false ); //$NON-NLS-1$
      dialog.addTextField( "Epsilon", oldEnd, false );
      if( dialog.open() != Window.OK ) {
        // do nothing;
      } else {
        try {
          String newStart = dialog.getStringValue( "Value" );
          String newEnd = dialog.getStringValue( "Epsilon" );
          if( !oldStart.equals( newStart ) || !oldEnd.equals( newEnd ) ) {
            ValueWithEpsilon newRange = new ValueWithEpsilon( Double.valueOf( newStart ).doubleValue(),
                                        Double.valueOf( newEnd ).doubleValue() );
            if( addVariable( newRange ) ) {
              this.table.remove( var );
            }
          } else {
            var.setValue( Double.valueOf( newStart ).doubleValue() );
            var.setEpsilon( Double.valueOf( newEnd ).doubleValue() );
            this.table.update( var, null );
            updateLaunchConfigurationDialog();
          }
        } catch( NumberFormatException exc ) {
          MessageDialog.openError( getShell(), "Bad number format", "Bad number format");
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
      ValueWithEpsilon var = ( ValueWithEpsilon )i.next();
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
}
