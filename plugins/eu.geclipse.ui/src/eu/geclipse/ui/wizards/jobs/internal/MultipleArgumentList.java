package eu.geclipse.ui.wizards.jobs.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TableItem;
import eu.geclipse.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.widgets.TabComponent;


/**
 * Control used to keep multiple values of one parameter
 * @author katis
 *
 */
public class MultipleArgumentList extends TabComponent<String> {

  /**
   * Creates IOFilesTab composite
   * 
   * @param contentProvider content provider for table holding IOFiles
   * @param labelProvider label provider for file holding IOFiles
   * @param propertiesVsHearders map containing properties and headers of
   *          table columns
   */
  public MultipleArgumentList( final IStructuredContentProvider contentProvider,
                     final ITableLabelProvider labelProvider,
                     final HashMap<String, String> propertiesVsHearders )
  {
    super( contentProvider, labelProvider, propertiesVsHearders );
  }

  /**
   * Returns values describing variables kept in {@link MultipleArgumentList}
   * @return Map containing variable's name as a key and it's value as a map's value
   */
  public List<String> getValues() {
    ArrayList<String> result = new ArrayList<String>();
    for ( TableItem item: this.table.getTable().getItems() ){
      result.add( ( String )item.getData() );
    }
    return result;
  }

  @Override
  protected void handleAddButtonSelected()
  {
    MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                          "New value" );
    dialog.addTextField( "New value", null, false );
//    dialog.addVariablesField( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field"), null, true ); //$NON-NLS-1$
    if( dialog.open() != Window.OK ) {
      return;
    }
    String name = dialog.getStringValue( "New value" );    
    if( name != null
        && name.length() > 0 )
    {
      addVariable( new String( name.trim() ) );
    }
  }

  @Override
  protected void handleEditButtonSelected()
  {
    IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
    String var = ( String )sel.getFirstElement();
    getValues();
    if( var == null ) {
      // do nothing;
    } else {
      String originalName = var;
//      String value = var.getValue();
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            "Editing..." );
      dialog.addTextField( "New value", originalName, false ); 
//      dialog.addVariablesField( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field"), value, true ); //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        // do nothing;
      } else {
        String name = dialog.getStringValue( "New value" ); 
//        String value = dialog.getStringValue( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field") ); //$NON-NLS-1$
        if( !originalName.equals( name ) ) {
          if( addVariable( new String( name ) ) ) {
            this.table.remove( var );
          }
        } else {
          var = name;
          this.table.update( var, null );
          updateLaunchConfigurationDialog();
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
      String var = ( String )i.next();
      this.table.remove( var );
    }
    this.table.getControl().setRedraw( true );
    // updateAppendReplace();
    updateLaunchConfigurationDialog();
  }

  @Override
  protected void setLabels()
  {
    this.addButton.setText( "Add..." ); 
    this.editButton.setText( "Edit..." );
    this.removeButton.setText( "Remove" );
  }
}
