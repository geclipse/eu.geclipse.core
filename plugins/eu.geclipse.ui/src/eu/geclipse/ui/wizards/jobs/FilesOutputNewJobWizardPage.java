/******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse consortium 
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

package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
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
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import eu.geclipse.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.ui.internal.wizards.jobs.FileType;
import eu.geclipse.ui.widgets.TabComponent;

/**
 * Wizard page used by {@link NewJobWizard}. Allows user to set input and
 * output files (but not stdIn or stdOut) for a job to be run on a Grid
 * 
 * @author katis
 */
public class FilesOutputNewJobWizardPage extends WizardPage {

  /**
   * Special component to keep record of variables user provided
   */
  IOFilesTab tab;
  private boolean isCreated = false;

  /**
   * Method to create new instance of EnvNewJobWizardPage
   * 
   * @param pageName name that will describe this page
   */
  protected FilesOutputNewJobWizardPage( final String pageName ) {
    super( pageName );
    setDescription( Messages.getString( "FilesOutputNewJobWizardPage.page_description" ) ); //$NON-NLS-1$
    setTitle( Messages.getString( "FilesOutputNewJobWizardPage.page_title" ) ); //$NON-NLS-1$
    // setPageComplete( false );
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout() );
    HashMap<String, String> map = new HashMap<String, String>();
    String message = Messages.getString( "FilesOutputNewJobWizardPage.table_type_header" ); //$NON-NLS-1$
    map.put( message, message );
    message = Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ); //$NON-NLS-1$
    map.put( message, message );
    message = Messages.getString( "FilesOutputNewJobWizardPage.table_location_header" ); //$NON-NLS-1$
    map.put( message, message );
    this.tab = new IOFilesTab( new IOFileContentProvider(),
                               new IOFileLabelProvider(),
                               map );
    this.tab.createControl( mainComp );
    // setPageComplete( true );
    this.isCreated = true;
    setControl( mainComp );
  }
  class IOFilesTab extends TabComponent<OutputFile> {

    /**
     * Creates IOFilesTab composite
     * 
     * @param contentProvider content provider for table holding IOFiles
     * @param labelProvider label provider for file holding IOFiles
     * @param propertiesVsHearders map containing properties and headers of
     *          table columns
     */
    public IOFilesTab( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final HashMap<String, String> propertiesVsHearders )
    {
      super( contentProvider, labelProvider, propertiesVsHearders, 350, 100 );
    }

    @Override
    protected void handleAddButtonSelected()
    {
      MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                            Messages.getString( "OutputFilesTab.new_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
      ArrayList<String> comboData = new ArrayList<String>();
      for( FileType fileType : FileType.values() ) {
        comboData.add( fileType.toString() );
      }
      dialog.addTextField( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ), null, false ); //$NON-NLS-1$
      dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_location_label" ), null, true ); //$NON-NLS-1$
      dialog.addComboField( Messages.getString( "OutputFilesTab.new_dialog_file_type_label" ), comboData, null ); //$NON-NLS-1$
      if( dialog.open() != Window.OK ) {
        return;
      }
      String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
      String value = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_location_label" ) ); //$NON-NLS-1$
      FileType type = FileType.valueOf( ( dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_type_label" ) ) ).toUpperCase() ); //$NON-NLS-1$
      if( name != null
          && value != null
          && name.length() > 0
          && value.length() > 0 )
      {
        OutputFile of = new OutputFile( name.trim(), value.trim(), type );
        addVariable( of );
      }
    }

    @Override
    protected void handleEditButtonSelected()
    {
      IStructuredSelection sel = ( IStructuredSelection )this.table.getSelection();
      OutputFile var = ( OutputFile )sel.getFirstElement();
      if( var == null ) {
        // do nothing;
      } else {
        String originalName = var.getName();
        String value = var.getLocation();
        FileType originalType = var.getType();
        MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                              Messages.getString( "OutputFilesTab.edit_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
        ArrayList<String> comboData = new ArrayList<String>();
        for( FileType fileType : FileType.values() ) {
          comboData.add( fileType.toString() );
        }
        dialog.addTextField( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ), originalName, false ); //$NON-NLS-1$
        dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_location_label" ), value, true ); //$NON-NLS-1$
        dialog.addComboField( Messages.getString( "OutputFilesTab.new_dialog_file_type_label" ), comboData, originalType.name() ); //$NON-NLS-1$
        if( dialog.open() != Window.OK ) {
          // do nothing;
        } else {
          String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
          value = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_location_label" ) ); //$NON-NLS-1$
          FileType type = FileType.valueOf( dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_type_label" ) ) ); //$NON-NLS-1$
          if( !originalName.equals( name ) ) {
            if( addVariable( new OutputFile( name, value, type ) ) ) {
              this.table.remove( var );
            }
          } else {
            var.setLocation( value );
            var.setType( type );
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
        OutputFile var = ( OutputFile )i.next();
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
  }
  protected class IOFileContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      OutputFile[] elements = new OutputFile[ 0 ];
      Map<String, String> m = new HashMap<String, String>();
      if( !m.isEmpty() ) {
        elements = new OutputFile[ m.size() ];
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
                  result = ( ( OutputFile )element1 ).getName()
                    .compareToIgnoreCase( ( ( OutputFile )element2 ).getName() );
                }
                return result;
              }
            } );
          }
        }
      }
    }
  }
  class IOFileLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        OutputFile var = ( OutputFile )element;
        switch( columnIndex ) {
          case 0: // type
            result = var.getType().toString();
          break;
          case 1: // name
            result = var.getName();
          break;
          case 2: // value
            result = var.getLocation();
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
   * Method to access String values kept by table
   * 
   * @param type Type of a files wchich values should be extracted from table
   * @return Map with file names as a keys and their location as a value
   */
  public HashMap<String, String> getFiles( final FileType type ) {
    HashMap<String, String> result = new HashMap<String, String>();
    if( this.tab != null ) {
      for( OutputFile file : this.tab.getInput() ) {
        if( file.getType().equals( type ) ) {
          result.put( file.getName(), file.getLocation() );
        }
      }
    }
    return result;
  }
  class OutputFile {

    private String location;
    private String name;
    private FileType type;

    /**
     * Creates new instance of IOFile
     * 
     * @param name name of the IOFile
     * @param location location of the IOFile (String in form of URI)
     * @param type type of the IOFile
     */
    public OutputFile( final String name,
                       final String location,
                       final FileType type )
    {
      this.name = name;
      this.location = location;
      this.type = type;
    }

    /**
     * Getter method to access the name of the IOFile
     * 
     * @return name of the IOFile
     */
    public String getName() {
      return this.name;
    }

    /**
     * Setter method to set name of the IOFile
     * 
     * @param name name of the IOFile
     */
    public void setName( final String name ) {
      this.name = name;
    }

    /**
     * Getter method to access the location of IOFile
     * 
     * @return location of IOFile (String in form of URI)
     */
    public String getLocation() {
      return this.location;
    }

    /**
     * Setter method to set location of IOFile
     * 
     * @param location of IOFile (String in form of URI)
     */
    public void setLocation( final String location ) {
      this.location = location;
    }

    /**
     * Getter method to access type of IOFile
     * 
     * @return type of IOFile
     */
    public FileType getType() {
      return this.type;
    }

    /**
     * Setter method to set type of IOFile
     * 
     * @param type type of IOFile
     */
    public void setType( final FileType type ) {
      this.type = type;
    }

    @Override
    public boolean equals( final Object argument )
    {
      boolean result = false;
      if( super.equals( argument ) ) {
        result = true;
      } else {
        if( argument instanceof OutputFile ) {
          OutputFile of = ( OutputFile )argument;
          if( of.getName().equals( this.getName() ) ) {
            result = true;
          }
        }
      }
      return result;
    }
  }

  @Override
  public IWizardPage getNextPage()
  {
    // TODO Auto-generated method stub
    return super.getNextPage();
  }

  /**
   * Method to fnd out if this page was created
   * 
   * @return true if method
   *         {@link FilesOutputNewJobWizardPage#createControl(Composite)} was
   *         invoked
   */
  public boolean isCreated() {
    return this.isCreated;
  }
}
