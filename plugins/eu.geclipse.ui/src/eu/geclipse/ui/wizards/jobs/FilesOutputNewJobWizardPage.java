/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Katarzyna Bylec - initial API and implementation
 ******************************************************************************/
package eu.geclipse.ui.wizards.jobs;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
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
  StageInTab CopyFromTab;
  StageOutTab CopyToTab;
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
    mainComp.setLayout( new GridLayout( 1, false ) );
    GridData gridData = new GridData();
    Group stdFilesGroup = new Group( mainComp, SWT.NONE );
    stdFilesGroup.setText( "Stage in" ); //$NON-NLS-1$
    stdFilesGroup.setLayout( new GridLayout( 1, false ) );
    gridData = new GridData( GridData.FILL_HORIZONTAL );
    gridData.grabExcessHorizontalSpace = true;
    stdFilesGroup.setLayoutData( gridData );
    FormToolkit toolkit = new FormToolkit( stdFilesGroup.getDisplay() );
    toolkit.setBackground( stdFilesGroup.getBackground() );
    Form form = toolkit.createForm( stdFilesGroup );
    form.getBody().setLayout( new GridLayout( 2, false ) );
    Label l = new Label( form.getBody(), SWT.NONE );
    l.setLayoutData( gridData );
    l.setText( "Files copied to computing element before execution of the job" );
    ImageHyperlink link = toolkit.createImageHyperlink( form.getBody(),
                                                        SWT.WRAP );
    link.setImage( Activator.getDefault().getImageRegistry().get( "helplink" ) );
    link.addHyperlinkListener( new HyperlinkAdapter() {

      public void linkActivated( HyperlinkEvent e ) {
        FilesOutputNewJobWizardPage.this.performHelp();
      }
    } );
    // form.setText( "Stage in" );
    // Section section = new Section( mainComp, Section.EXPANDED |
    // Section.TWISTIE );
    // section.setLayoutData( gridData );
    // section.setText( "Details" );
    //  
    // gridData = new GridData();
    // gridData.horizontalIndent = 15;
    // Label l = new Label( section, SWT.NONE );
    // l.setLayoutData( gridData );
    // l.setText( "Files to be transferred form storage element to file" );
    // // section.setDescriptionControl( l );
    // section.setClient( l );
    ArrayList<String> map = new ArrayList<String>();
    String message = Messages.getString( "FilesOutputNewJobWizardPage.table_source_header" ); //$NON-NLS-1$
    map.add( message );
    message = Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ); //$NON-NLS-1$
    map.add( message );
    // Composite tabComp = new Composite(mainComp, SWT.NONE);
    // tabComp.setLayout( new GridLayout(1, false) );
    // gridData = new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL |
    // GridData.GRAB_VERTICAL);
    // gridData.horizontalIndent = 15;
    // tabComp.setLayoutData( gridData );
    ArrayList<Integer> width = new ArrayList<Integer>();
    width.add( Integer.valueOf( 240 ));
    width.add( Integer.valueOf( 100 ));
    this.CopyFromTab = new StageInTab( new StageOutContentProvider(),
                                       new StageInLabelProvider(),
                                       map, width );
    this.CopyFromTab.createControl( stdFilesGroup );
    // stdFilesGroup.getChildren()[stdFilesGroup.getChildren().length -
    // 1].setLayoutData( gridData );
    // setPageComplete( true );
    this.isCreated = true;
    // Label horizontalSeparator = new Label( mainComp, SWT.SEPARATOR |
    // SWT.HORIZONTAL );
    // gridData = new GridData( GridData.FILL_BOTH );
    // horizontalSeparator.setLayoutData( gridData );
    Group stdFilesGroup1 = new Group( mainComp, SWT.NONE );
    stdFilesGroup1.setText( "Stage out" );
    stdFilesGroup1.setLayout( new GridLayout( 1, false ) );
    gridData = new GridData( GridData.FILL_HORIZONTAL );
    gridData.grabExcessHorizontalSpace = true;
    // gridData.horizontalSpan = 3;
    stdFilesGroup1.setLayoutData( gridData );
    Form form1 = toolkit.createForm( stdFilesGroup1 );
    form1.getBody().setLayout( new GridLayout( 2, false ) );
    Label l1 = new Label( form1.getBody(), SWT.NONE );
    // gridData.horizontalIndent = 15;
    l1.setLayoutData( gridData );
    l1.setText( "Files copied from computing element after execution of the job" );
    ImageHyperlink link1 = toolkit.createImageHyperlink( form1.getBody(),
                                                         SWT.WRAP );
    link1.setImage( Activator.getDefault().getImageRegistry().get( "helplink" ) );
    // form1.setText( "Stage out" );
    // Composite tabComp1 = new Composite(stdFilesGroup1, SWT.NONE);
    // tabComp1.setLayout( new GridLayout(1, false) );
    // gridData = new GridData( GridData.FILL_BOTH );
    // gridData.horizontalIndent = 15;
    // tabComp1.setLayoutData( gridData );
    map = new ArrayList<String>();
    message = Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ); //$NON-NLS-1$
    map.add( message );
    message = Messages.getString( "FilesOutputNewJobWizardPage.table_target_header" ); //$NON-NLS-1$
    map.add( message );
    width = new ArrayList<Integer>();
    width.add( Integer.valueOf( 100 ));
    width.add( Integer.valueOf( 240 ));
    this.CopyToTab = new StageOutTab( new StageOutContentProvider(),
                                      new StageOutLabelProvider(),
                                      map, width );
    this.CopyToTab.createControl( stdFilesGroup1 );
    // setPageComplete( true );
    this.isCreated = true;
    setControl( mainComp );
  }
  class StageInTab extends TabComponent<DataStaging> implements ICellModifier {

    /**
     * Creates IOFilesTab composite
     * 
     * @param contentProvider content provider for table holding IOFiles
     * @param labelProvider label provider for file holding IOFiles
     * @param propertiesVsHearders map containing properties and headers of
     *          table columns
     */
    public StageInTab( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final List<String> propertiesVsHearders,
                       final List<Integer> columnsWidth)
    {
      super( contentProvider,
             labelProvider,
             propertiesVsHearders,
             150,
             columnsWidth,
             SWT.BOTTOM );
    }

    @Override
    protected void handleAddButtonSelected()
    {
      handleAddButtonSelected( "", "" );
    }

    protected void handleAddButtonSelected( final String initName,
                                            final String initSource )
    {
      {
        MultipleInputDialog dialog = new MultipleInputDialog( getShell(),
                                                              Messages.getString( "OutputFilesTab.new_output_file_settings_dialog_title" ) ); //$NON-NLS-1$
        ArrayList<String> comboData = new ArrayList<String>();
        for( FileType fileType : FileType.values() ) {
          comboData.add( fileType.toString() );
        }
        dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ), initSource, false ); //$NON-NLS-1$
        dialog.addTextField( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ), initName, false ); //$NON-NLS-1$
        dialog.setConnectedFields( Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ), Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        if( dialog.open() != Window.OK ) {
          return;
        }
        String source = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ) ); //$NON-NLS-1$
        String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
        // FileType type = FileType.valueOf( ( dialog.getStringValue(
        // Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ) )
        // ).toUpperCase() ); //$NON-NLS-1$
        if( name != null
        // && target != null
            && name.length() > 0
        // && target.length() > 0 )
        )
        {
          DataStaging of;
          try {
            of = new DataStaging( name.trim(), null, source.trim() );
            addVariable( of );
          } catch( Exception e ) {
            MessageDialog.openError( getShell(),
                                     "No local files allowed",
                                     "Target location cannot be local!" );
            handleAddButtonSelected( name.trim(), source.trim() );
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
        dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ), orginalSource, false ); //$NON-NLS-1$
        dialog.addTextField( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ), originalName, false ); //$NON-NLS-1$
        if( dialog.open() != Window.OK ) {
          // do nothing;
        } else {
          String source = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ) ); //$NON-NLS-1$
          String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
          try {
            if( !originalName.equals( name ) ) {
              if( addVariable( new DataStaging( name,
                                                orginalTarget,
                                                orginalSource ) ) )
              {
                this.table.remove( var );
              }
            } else {
              if( orginalTarget != null ) {
                var.setTargetLocation( orginalTarget );
              }
              var.setSourceLocation( source );
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
      if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_source_header" ) ) ) { //$NON-NLS-1$
        columnIndex = 0;
      }
      if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ) ) ) { //$NON-NLS-1$
        columnIndex = 1;
      }
      // if( property.equals( Messages.getString(
      // "FilesOutputNewJobWizardPage.table_target_header" ) ) ) { //$NON-NLS-1$
      // columnIndex = 2;
      // }
      Object result = null;
      DataStaging task = ( DataStaging )element;
      switch( columnIndex ) {
        case 0:
          result = task.getSourceLocation();
        break;
        case 1:
          result = task.getName();
        break;
        // case 2:
        // result = task.getTargetLocation();
        // break;
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
      if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_source_header" ) ) ) { //$NON-NLS-1$
        columnIndex = 0;
      }
      if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ) ) ) { //$NON-NLS-1$
        columnIndex = 1;
      }
      // if( property.equals( Messages.getString(
      // "FilesOutputNewJobWizardPage.table_target_header" ) ) ) { //$NON-NLS-1$
      // columnIndex = 2;
      // }
      TableItem item = ( TableItem )element;
      DataStaging taskOld = ( DataStaging )item.getData();
      DataStaging task = null;
      try {
        task = new DataStaging( taskOld.getName(),
                                taskOld.getTargetLocation(),
                                taskOld.getSourceLocation() );
      } catch( Exception e ) {
        MessageDialog.openError( getShell(),
                                 "No local files allowed",
                                 "Target location cannot be local!" );
      }
      if( task != null ) {
        switch( columnIndex ) {
          case 0:
            task.setSourceLocation( ( String )value );
            if( !task.getName().equals( taskOld.getName() ) ) {
              if( addVariable( task ) ) {
                this.table.remove( taskOld );
              }
            } else {
              taskOld.setSourceLocation( task.getSourceLocation() );
              this.table.update( taskOld, null );
              updateLaunchConfigurationDialog();
            }
          break;
          case 1:
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
          // case 2:
          // task.setTargetLocation( ( String )value );
          // if( !task.getName().equals( taskOld.getName() ) ) {
          // if( addVariable( task ) ) {
          // this.table.remove( taskOld );
          // }
          // } else {
          // taskOld.setTargetLocation( task.getTargetLocation() );
          // this.table.update( taskOld, null );
          // updateLaunchConfigurationDialog();
          // }
          // break;
        }
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
      addEditor( new DialogCellEditor() {

        @Override
        protected Object openDialogBox( final Control cellEditorWindow )
        {
          String filename = "";
          IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                             "Choose a file",
                                                                             null, true );
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
      addEditor( new TextCellEditor() );
      // addEditor( new DialogCellEditor() {
      //
      // @Override
      // protected Object openDialogBox( final Control cellEditorWindow )
      // {
      // String filename = "";
      // IGridConnectionElement connection = GridFileDialog.openFileDialog(
      // getShell(),
      // "Choose a file",
      // null );
      // if( connection != null ) {
      // try {
      // filename = connection.getConnectionFileStore().toString();
      // } catch( CoreException cExc ) {
      // NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
      // }
      // }
      // return filename;
      // }
      // } );
      setCellModifier( this );
    }
  }
  class StageOutTab extends TabComponent<DataStaging> implements ICellModifier {

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
                        final List<Integer> width)
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
      handleAddButtonSelected( "", "" );
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
        dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ), initLocation, false ); //$NON-NLS-1$
        dialog.setConnectedFields( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ), Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) );
        if( dialog.open() != Window.OK ) {
          return;
        }
        String name = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_name_label" ) ); //$NON-NLS-1$
        String target = dialog.getStringValue( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ) ); //$NON-NLS-1$
        // FileType type = FileType.valueOf( ( dialog.getStringValue(
        // Messages.getString( "OutputFilesTab.new_dialog_file_source_label" ) )
        // ).toUpperCase() ); //$NON-NLS-1$
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
        dialog.addBrowseField( Messages.getString( "OutputFilesTab.new_dialog_file_target_label" ), orginalTarget, false ); //$NON-NLS-1$
        
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
      // if( property.equals( Messages.getString(
      // "FilesOutputNewJobWizardPage.table_source_header" ) ) ) { //$NON-NLS-1$
      // columnIndex = 0;
      // }
      if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_name_header" ) ) ) { //$NON-NLS-1$
        columnIndex = 0;
      }
      if( property.equals( Messages.getString( "FilesOutputNewJobWizardPage.table_target_header" ) ) ) { //$NON-NLS-1$
        columnIndex = 1;
      }
      Object result = null;
      DataStaging task = ( DataStaging )element;
      switch( columnIndex ) {
        // case 0:
        // result = task.getSourceLocation();
        // break;
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
      // if( property.equals( Messages.getString(
      // "FilesOutputNewJobWizardPage.table_source_header" ) ) ) { //$NON-NLS-1$
      // columnIndex = 0;
      // }
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
          // case 0:
          // task.setSourceLocation( ( String )value );
          // if( !task.getName().equals( taskOld.getName() ) ) {
          // if( addVariable( task ) ) {
          // this.table.remove( taskOld );
          // }
          // } else {
          // taskOld.setSourceLocation( task.getSourceLocation() );
          // this.table.update( taskOld, null );
          // updateLaunchConfigurationDialog();
          // }
          // break;
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
      // addEditor( new DialogCellEditor() {
      //
      // @Override
      // protected Object openDialogBox( final Control cellEditorWindow )
      // {
      // String filename = "";
      // IGridConnectionElement connection = GridFileDialog.openFileDialog(
      // getShell(),
      // "Choose a file",
      // null );
      // if( connection != null ) {
      // try {
      // filename = connection.getConnectionFileStore().toString();
      // } catch( CoreException cExc ) {
      // NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
      // }
      // }
      // return filename;
      // }
      // } );
      addEditor( new TextCellEditor() );
      addEditor( new DialogCellEditor() {

        @Override
        protected Object openDialogBox( final Control cellEditorWindow )
        {
          String filename = "";
          IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                             "Choose a file",
                                                                             null, true );
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
  protected class StageOutContentProvider implements IStructuredContentProvider
  {

    public Object[] getElements( final Object inputElement ) {
      DataStaging[] elements = new DataStaging[ 0 ];
      Map<String, String> m = new HashMap<String, String>();
      if( !m.isEmpty() ) {
        elements = new DataStaging[ m.size() ];
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
                  result = ( ( DataStaging )element1 ).getName()
                    .compareToIgnoreCase( ( ( DataStaging )element2 ).getName() );
                }
                return result;
              }
            } );
          }
        }
      }
    }
  }
  class StageOutLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        DataStaging var = ( DataStaging )element;
        switch( columnIndex ) {
          case 0: // name
            result = var.getName();
          break;
          case 1: // target loaction
            result = var.getTargetLocation();
          break;
          // case 2: // value
          // result = var.getTargetLocation();
          // break;
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
    if( this.CopyFromTab != null ) {
      switch( type ) {
        case INPUT:
          for( DataStaging file : this.CopyFromTab.getInput() ) {
            if( !file.getSourceLocation().equals( "" ) ) { //$NON-NLS-1$
              result.put( file.getName(), file.getSourceLocation() );
            }
          }
        break;
        case OUTPUT:
          for( DataStaging file : this.CopyToTab.getInput() ) {
            if( !file.getTargetLocation().equals( "" ) ) { //$NON-NLS-1$
              result.put( file.getName(), file.getTargetLocation() );
            }
          }
        break;
        case NULL:
          // do nothing
        break;
      }
    }
    return result;
  }
  class DataStaging {

    private String sourceLocation;
    private String name;
    private String targetLocation;

    /**
     * Creates new instance of IOFile
     * 
     * @param name name of the IOFile
     * @param targetLocation target remote location of file (data stage out)
     * @param sourceLocation source remote location of file (data stage in)
     * @throws Exception exception is thrown when target location is local or
     *           not absolute
     */
    public DataStaging( final String name,
                        final String targetLocation,
                        final String sourceLocation ) throws Exception
    {
      this.name = name;
      this.sourceLocation = sourceLocation;
      if( targetLocation == null ) {
        this.targetLocation = targetLocation;
      } else {
        setTargetLocation( targetLocation );
      }
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
     * Getter method to access the source location of data staging in
     * 
     * @return source loaction
     */
    public String getSourceLocation() {
      return this.sourceLocation;
    }

    /**
     * Setter method to set source location of data staging in
     * 
     * @param location source location of data staging in
     */
    public void setSourceLocation( final String location ) {
      this.sourceLocation = location;
    }

    /**
     * Getter method to access the target location of data staging out
     * 
     * @return type of IOFile
     */
    public String getTargetLocation() {
      return this.targetLocation;
    }

    /**
     * Setter method to set target location of data staging out
     * 
     * @param target location
     */
    public void setTargetLocation( final String target ) throws Exception {
      URI uri = new URI( target );
      if( uri.isAbsolute() ) {
        this.targetLocation = target;
      } else {
        throw new Exception();
      }
    }

    @Override
    public boolean equals( final Object argument )
    {
      boolean result = false;
      if( super.equals( argument ) ) {
        result = true;
      } else {
        if( argument instanceof DataStaging ) {
          DataStaging of = ( DataStaging )argument;
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
  class StageInLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        DataStaging var = ( DataStaging )element;
        switch( columnIndex ) {
          case 0: // source location
            result = var.getSourceLocation();
          break;
          case 1: // name
            result = var.getName();
          break;
          // case 2: // value
          // result = var.getTargetLocation();
          // break;
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
}
