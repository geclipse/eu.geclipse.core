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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TableItem;

import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.widgets.TabComponent;


/**
 * Control used to keep multiple values of one parameter
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
                     final List<String> propertiesVsHearders )
  {
    super( contentProvider, labelProvider, propertiesVsHearders, 230, 200 );
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
                                                  Messages.getString("MultipleArgumentList.new_value") ); //$NON-NLS-1$
    dialog.addTextField( Messages.getString("MultipleArgumentList.new_value"), null, false ); //$NON-NLS-1$
    if( dialog.open() != Window.OK ) {
      return;
    }
    String name = dialog.getStringValue( Messages.getString("MultipleArgumentList.new_value") );     //$NON-NLS-1$
    if( name != null
        && name.length() > 0 )
    {
      addVariable( name.trim() );
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
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                 Messages.getString("MultipleArgumentList.edit_title") ); //$NON-NLS-1$
      dialog.addTextField( Messages.getString("MultipleArgumentList.new_value"), originalName, false );  //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        // do nothing;
      } else {
        String name = dialog.getStringValue( Messages.getString("MultipleArgumentList.new_value") );  //$NON-NLS-1$
        if( !originalName.equals( name ) ) {
          if( addVariable( name ) ) {
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
    updateLaunchConfigurationDialog();
  }

  @Override
  protected void setLabels()
  {
    this.addButton.setText( Messages.getString("MultipleArgumentList.add_button") );  //$NON-NLS-1$
    this.editButton.setText( Messages.getString("MultipleArgumentList.edit_button") ); //$NON-NLS-1$
    this.removeButton.setText( Messages.getString("MultipleArgumentList.remove_button") ); //$NON-NLS-1$
  }
}
