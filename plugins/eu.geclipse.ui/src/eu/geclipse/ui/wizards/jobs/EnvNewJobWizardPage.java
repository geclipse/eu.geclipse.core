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
 *****************************************************************************/

package eu.geclipse.ui.wizards.jobs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import eu.geclipse.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.widgets.TabComponent;
import eu.geclipse.ui.wizards.jobs.Messages;

/**
 * This is the page for eclipse wizards that helps to gather information about
 * any environment variables
 * 
 * @author katis
 */
public class EnvNewJobWizardPage extends WizardPage {

  /**
   * Special component to keep record of variables user provided
   */
  EnvironmentVariablesTab tab;

  /**
   * Method to create new instance of EnvNewJobWizardPage
   * 
   * @param pageName name that will describe this page
   */
  protected EnvNewJobWizardPage( final String pageName ) {
    super( pageName );
    setDescription( Messages.getString( "EnvNewJobWizardPage.page_description" ) ); //$NON-NLS-1$
    setTitle( Messages.getString( "EnvNewJobWizardPage.page_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout() );
    HashMap<String, String> map = new HashMap<String, String>();
    String message = Messages.getString( "EnvNewJobWizardPage.table_name_header" ); //$NON-NLS-1$
    map.put( message, message );
    message = Messages.getString( "EnvNewJobWizardPage.table_value_header" ); //$NON-NLS-1$
    map.put( message, message );
    this.tab = new EnvironmentVariablesTab(new VariableContentProvider(), new VariableLabelProvider(), map);
    this.tab.createControl( mainComp );
    setControl( mainComp );
  }

  // public String getVariables() {
  // String result = ""; //$NON-NLS-1$
  // HashMap<String, String> vars = new HashMap<String, String>();
  // vars = this.tab.getVariables();
  // for( String name : vars.keySet() ) {
  // result = result
  // + "<variable name=\"" //$NON-NLS-1$
  // + name
  // + "\">" //$NON-NLS-1$
  // + vars.get( name )
  // + "</variable>\n"; //$NON-NLS-1$
  // }
  // return result;
  // }
  /**
   * Method to acces variables stored in {@link EnvNewJobWizardPage#tab}
   * 
   * @return map containing variables' names as a key set and their values as a
   *         map's value set
   */
  public HashMap<String, String> getVariables() {
    HashMap<String, String> result = new HashMap<String, String>();
    result = this.tab.getVariables();
    return result;
  }
  
  class EnvironmentVariablesTab extends TabComponent<EnvironmentVariable> {

    /**
     * Creates IOFilesTab composite
     * 
     * @param contentProvider content provider for table holding IOFiles
     * @param labelProvider label provider for file holding IOFiles
     * @param propertiesVsHearders map containing properties and headers of
     *          table columns
     */
    public EnvironmentVariablesTab( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final HashMap<String, String> propertiesVsHearders )
    {
      super( contentProvider, labelProvider, null, 50, 30 );
    }

    /**
     * Returns values describing variables kept in {@link EnvironmentVariablesTab}
     * @return Map containing variable's name as a key and it's value as a map's value
     */
    public HashMap<String, String> getVariables() {
      HashMap<String, String> result = new HashMap<String, String>();
      EnvironmentVariable var;
      for ( TableItem item: this.table.getTable().getItems() ){
        var = ( EnvironmentVariable )item.getData();
        result.put( var.getName(), var.getValue() );
      }
      return result;
    }

    @Override
    protected void handleAddButtonSelected()
    {
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            Messages.getString("EnvNewJobWizardPage.new_var_dialog_title") ); //$NON-NLS-1$
      dialog.addTextField( Messages.getString("EnvNewJobWizardPage.new_env_dialog_name_field"), null, false ); //$NON-NLS-1$
      dialog.addVariablesField( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field"), null, true ); //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        return;
      }
      String name = dialog.getStringValue( Messages.getString("EnvNewJobWizardPage.new_env_dialog_name_field") ); //$NON-NLS-1$
      String value = dialog.getStringValue( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field") ); //$NON-NLS-1$
      if( name != null
          && value != null
          && name.length() > 0
          && value.length() > 0 )
      {
        addVariable( new EnvironmentVariable( name.trim(), value.trim() ) );
      }
    }

    @Override
    protected void handleEditButtonSelected()
    {
      IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
      EnvironmentVariable var = ( EnvironmentVariable )sel.getFirstElement();
      getVariables();
      if( var == null ) {
        // do nothing;
      } else {
        String originalName = var.getName();
        String value = var.getValue();
        MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                              Messages.getString("EnvNewJobWizardPage.edit_var_dialog_title") ); //$NON-NLS-1$
        dialog.addTextField( Messages.getString("EnvNewJobWizardPage.new_env_dialog_name_field"), originalName, false ); //$NON-NLS-1$
        dialog.addVariablesField( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field"), value, true ); //$NON-NLS-1$
        if( dialog.open() != Window.OK ) {
          // do nothing;
        } else {
          String name = dialog.getStringValue( Messages.getString("EnvNewJobWizardPage.new_env_dialog_name_field") ); //$NON-NLS-1$
          value = dialog.getStringValue( Messages.getString("EnvNewJobWizardPage.new_env_dialog_value_field") ); //$NON-NLS-1$
          if( !originalName.equals( name ) ) {
            if( addVariable( new EnvironmentVariable( name, value ) ) ) {
              this.table.remove( var );
            }
          } else {
            var.setValue( value );
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
        EnvironmentVariable var = ( EnvironmentVariable )i.next();
        this.table.remove( var );
      }
      this.table.getControl().setRedraw( true );
      // updateAppendReplace();
      updateLaunchConfigurationDialog();
    }

    @Override
    protected void setLabels()
    {
      this.addButton.setText( Messages.getString( "EnvNewJobWizardPage.add_var_button_label" ) ); //$NON-NLS-1$
      this.editButton.setText( Messages.getString( "EnvNewJobWizardPage.edit_var_button_label" ) ); //$NON-NLS-1$
      this.removeButton.setText( Messages.getString( "EnvNewJobWizardPage.delete_var_button_label" ) ); //$NON-NLS-1$
    }
  }
  
  protected class VariableContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      EnvironmentVariable[] elements = new EnvironmentVariable[ 0 ];
      Map<String, String> m = new HashMap<String, String>();
      if( !m.isEmpty() ) {
        elements = new EnvironmentVariable[ m.size() ];
        String[] varNames = new String[ m.size() ];
        m.keySet().toArray( varNames );
        for( int i = 0; i < m.size(); i++ ) {
          // elements[ i ] = new OutputFile( varNames[ i ],
          // m.get( varNames[ i ] ) );
        }
      }
      return elements;
    }

    public void dispose() {
      // nothing
    }

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput )
    {
      if( newInput == null ) {
        // do nothing
      } else {
        if( viewer instanceof TableViewer ) {
          TableViewer tableViewer = ( TableViewer )viewer;
          if( tableViewer.getTable().isDisposed() ) {
            // do nothing
          } else {
            tableViewer.setSorter( new ViewerSorter() {

              @Override
              public int compare( final Viewer iviewer,
                                  final Object element1,
                                  final Object element2 )
              {
                int result;
                if( element1 == null ) {
                  result = -1;
                } else if( element2 == null ) {
                  result = 1;
                } else {
                  result = ( ( EnvironmentVariable )element1 ).getName()
                    .compareToIgnoreCase( ( ( EnvironmentVariable )element2 ).getName() );
                }
                return result;
              }
            } );
          }
        }
      }
    }
  }
  class VariableLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        EnvironmentVariable var = ( EnvironmentVariable )element;
        switch( columnIndex ) {
          case 0: // type
            result = var.getName().toString();
          break;
          case 1: // name
            result = var.getValue();
          break;
        }
      }
      return result;
    }

    public Image getColumnImage( final Object element, final int columnIndex ) {
      if( columnIndex == 0 ) {
        // return
        // DebugPluginImages.getImage(IDebugUIConstants.IMG_OBJS_ENV_VAR);
      }
      return null;
    }
  }
  
  /**
   * Internal class to store variable
   * 
   * @author katis
   */
  private class EnvironmentVariable {

    private String value;
    private String name;

    /**
     * Creates new {@link EnvironmentVariable}'s instance
     * @param name name of the variable
     * @param value value of the variable
     */
    public EnvironmentVariable( final String name, final String value ) {
      this.name = name;
      this.value = value;
    }

    /**
     * Getter method to access variable's name
     * @return name of the variable
     */
    public String getName() {
      return this.name;
    }

    /**
     * Setter method to set variable's name
     * @param name variable's name to be set
     */
    public void setName( final String name ) {
      this.name = name;
    }

    /**
     * Getter method to access variable's value
     * @return value of the variable
     */
    public String getValue() {
      return this.value;
    }

    /**
     * Setter method to set value of the variables
     * @param value variable's value to be set
     */
    public void setValue( final String value ) {
      this.value = value;
    }

    @Override
    public boolean equals( final Object obj )
    {
      boolean result = false;
      if ( super.equals( obj )){
        result = true;
      } else {
        if (obj instanceof EnvironmentVariable){
          result = this.getName().equals( (( EnvironmentVariable )obj).getName());
        }
      }
      return result;
    }
    
    
  }
}
