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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.Window;

import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.widgets.TabComponent;


/**
 * Table to keep exact values for ranges of JSDL resources
 */
@SuppressWarnings("unchecked") 
public class JSDLExactValueTab extends TabComponent {

  /**
   * Creates new instance of this class
   * @param contentProvider content provider for table viewer
   * @param labelProvider label provider for table viewer
   * @param columnsProperties headers for table
   * @param hight hight of this control
   * @param width width of collumn in table
   */
  public JSDLExactValueTab( final IStructuredContentProvider contentProvider, final ITableLabelProvider labelProvider, final List<String> columnsProperties, final int hight, final int width ) {
    super( contentProvider, labelProvider, columnsProperties, hight, width );
  }

  @Override
  protected void handleAddButtonSelected()
  {
    MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                          Messages.getString("JSDLExactValueTab.new_value") ); //$NON-NLS-1$
    dialog.addTextField( Messages.getString("JSDLExactValueTab.value"), null, false ); //$NON-NLS-1$
    dialog.addTextField( Messages.getString("JSDLExactValueTab.epsilon"), null, false ); //$NON-NLS-1$
    if( dialog.open() != Window.OK ) {
      return;
    }
    String val = dialog.getStringValue( Messages.getString("JSDLExactValueTab.value") ); //$NON-NLS-1$
    String eps = dialog.getStringValue( Messages.getString("JSDLExactValueTab.epsilon") ); //$NON-NLS-1$
    try{
    ValueWithEpsilon value = new ValueWithEpsilon( Double.valueOf( val ).doubleValue(), Double.valueOf( eps ).doubleValue() );
    addVariable( value );
    } catch (NumberFormatException exc){
      MessageDialog.openError( getShell(), Messages.getString("JSDLExactValueTab.bad_number_format"), Messages.getString("JSDLExactValueTab.bad_number_format")); //$NON-NLS-1$ //$NON-NLS-2$
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
                                                            Messages.getString("JSDLExactValueTab.edit_title") ); //$NON-NLS-1$
      ArrayList<String> comboData = new ArrayList<String>();
      for( FileType fileType : FileType.values() ) {
        comboData.add( fileType.toString() );
      }
      dialog.addTextField( Messages.getString("JSDLExactValueTab.value"), oldStart, false );  //$NON-NLS-1$
      dialog.addTextField( Messages.getString("JSDLExactValueTab.epsilon"), oldEnd, false ); //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        // do nothing;
      } else {
        try {
          String newStart = dialog.getStringValue( Messages.getString("JSDLExactValueTab.value") ); //$NON-NLS-1$
          String newEnd = dialog.getStringValue( Messages.getString("JSDLExactValueTab.epsilon") ); //$NON-NLS-1$
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
          MessageDialog.openError( getShell(), Messages.getString("JSDLExactValueTab.bad_number_format"), Messages.getString("JSDLExactValueTab.bad_number_format")); //$NON-NLS-1$ //$NON-NLS-2$
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
    this.addButton.setText( Messages.getString("JSDLExactValueTab.add_button") ); //$NON-NLS-1$
    this.editButton.setText( Messages.getString("JSDLExactValueTab.edit_button") ); //$NON-NLS-1$
    this.removeButton.setText( Messages.getString("JSDLExactValueTab.rmove_button") ); //$NON-NLS-1$
  }
}
