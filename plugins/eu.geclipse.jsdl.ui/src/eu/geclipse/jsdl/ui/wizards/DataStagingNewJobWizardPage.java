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
package eu.geclipse.jsdl.ui.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import eu.geclipse.jsdl.JSDLModelFacade;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.SourceTargetType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.wizards.FileType;
import eu.geclipse.jsdl.ui.widgets.DataStageInTable;
import eu.geclipse.jsdl.ui.widgets.DataStageOutTable;

/**
 * Wizard page used by {@link NewJobWizard}. Allows user to set input and
 * output files (but not stdIn or stdOut) for a job to be run on a Grid
 * 
 * @author katis
 */
public class DataStagingNewJobWizardPage extends WizardPage {

  /**
   * Special component to keep record of variables user provided
   */
  DataStageInTable copyFromTab;
  DataStageOutTable copyToTab;
  private boolean isCreated = false;
  private List<DataStagingType> initialStagingOut = new ArrayList<DataStagingType>();
  private List<DataStagingType> initialStagingIn = new ArrayList<DataStagingType>();

  /**
   * Method to create new instance of EnvNewJobWizardPage
   * 
   * @param pageName name that will describe this page
   */
  protected DataStagingNewJobWizardPage( final String pageName ) {
    super( pageName );
    setDescription( Messages.getString( "FilesOutputNewJobWizardPage.page_description" ) ); //$NON-NLS-1$
    setTitle( Messages.getString( "FilesOutputNewJobWizardPage.page_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    GridData gridData = new GridData();
    Group inFilesGroup = new Group( mainComp, SWT.NONE );
    // Data stage in group
    inFilesGroup.setText( "Stage in" ); //$NON-NLS-1$
    inFilesGroup.setLayout( new GridLayout( 1, false ) );
    gridData = new GridData( GridData.FILL_HORIZONTAL
                             | GridData.GRAB_HORIZONTAL );
    gridData.grabExcessHorizontalSpace = true;
    inFilesGroup.setLayoutData( gridData );
    FormToolkit toolkit = new FormToolkit( inFilesGroup.getDisplay() );
    toolkit.setBackground( inFilesGroup.getBackground() );
    Form inForm = toolkit.createForm( inFilesGroup );
    inForm.getBody().setLayout( new GridLayout( 2, false ) );
    Label inDescription = new Label( inForm.getBody(), SWT.NONE );
    inDescription.setLayoutData( gridData );
    inDescription.setText( Messages.getString( "DataStagingNewJobWizardPage.data_staging_in_description" ) ); //$NON-NLS-1$
    ImageHyperlink link = toolkit.createImageHyperlink( inForm.getBody(),
                                                        SWT.WRAP );
    link.setImage( Activator.getDefault().getImageRegistry().get( "helplink" ) ); //$NON-NLS-1$
    link.addHyperlinkListener( new HyperlinkAdapter() {

      @Override
      public void linkActivated( final HyperlinkEvent e ) {
        DataStagingNewJobWizardPage.this.performHelp();
      }
    } );
    this.copyFromTab = new DataStageInTable( inFilesGroup,
                                             this.initialStagingIn,
                                             DataStageInTable.BUTTONS_BOTTOM );
    // Data stage out group
    Group outFilesGroup = new Group( mainComp, SWT.NONE );
    outFilesGroup.setText( Messages.getString( "DataStagingNewJobWizardPage.stage_out_title" ) ); //$NON-NLS-1$
    outFilesGroup.setLayout( new GridLayout( 1, false ) );
    gridData = new GridData( GridData.FILL_HORIZONTAL
                             | GridData.GRAB_HORIZONTAL );
    gridData.grabExcessHorizontalSpace = true;
    outFilesGroup.setLayoutData( gridData );
    Form outForm = toolkit.createForm( outFilesGroup );
    outForm.getBody().setLayout( new GridLayout( 2, false ) );
    Label outDescription = new Label( outForm.getBody(), SWT.NONE );
    outDescription.setLayoutData( gridData );
    outDescription.setText( Messages.getString( "DataStagingNewJobWizardPage.data_staging_out_description" ) ); //$NON-NLS-1$
    ImageHyperlink link1 = toolkit.createImageHyperlink( outForm.getBody(),
                                                         SWT.WRAP );
    link1.setImage( Activator.getDefault().getImageRegistry().get( "helplink" ) ); //$NON-NLS-1$
    this.copyToTab = new DataStageOutTable( outFilesGroup,
                                            this.initialStagingOut,
                                            DataStageOutTable.BUTTONS_BOTTOM );
    this.isCreated = true;
    setControl( mainComp );
  }
  // public void createControl( final Composite parent ) {
  // Composite mainComp = new Composite( parent, SWT.NONE );
  // mainComp.setLayout( new GridLayout( 1, false ) );
  // GridData gridData = new GridData();
  // Group stdFilesGroup = new Group( mainComp, SWT.NONE );
  // stdFilesGroup.setText( "Stage in" ); //$NON-NLS-1$
  // stdFilesGroup.setLayout( new GridLayout( 1, false ) );
  // gridData = new GridData( GridData.FILL_HORIZONTAL );
  // gridData.grabExcessHorizontalSpace = true;
  // stdFilesGroup.setLayoutData( gridData );
  // FormToolkit toolkit = new FormToolkit( stdFilesGroup.getDisplay() );
  // toolkit.setBackground( stdFilesGroup.getBackground() );
  // Form form = toolkit.createForm( stdFilesGroup );
  // form.getBody().setLayout( new GridLayout( 2, false ) );
  // Label l = new Label( form.getBody(), SWT.NONE );
  // l.setLayoutData( gridData );
  // l.setText( "Files copied to computing element before execution of the job"
  // );
  // ImageHyperlink link = toolkit.createImageHyperlink( form.getBody(),
  // SWT.WRAP );
  // link.setImage( Activator.getDefault().getImageRegistry().get( "helplink" )
  // );
  // link.addHyperlinkListener( new HyperlinkAdapter() {
  //
  // public void linkActivated( HyperlinkEvent e ) {
  // DataStagingNewJobWizardPage.this.performHelp();
  // }
  // } );
  // // form.setText( "Stage in" );
  // // Section section = new Section( mainComp, Section.EXPANDED |
  // // Section.TWISTIE );
  // // section.setLayoutData( gridData );
  // // section.setText( "Details" );
  // //
  // // gridData = new GridData();
  // // gridData.horizontalIndent = 15;
  // // Label l = new Label( section, SWT.NONE );
  // // l.setLayoutData( gridData );
  // // l.setText( "Files to be transferred form storage element to file" );
  // // // section.setDescriptionControl( l );
  // // section.setClient( l );
  // ArrayList<String> map = new ArrayList<String>();
  // String message = Messages.getString(
  // "FilesOutputNewJobWizardPage.table_source_header" ); //$NON-NLS-1$
  // map.add( message );
  // message = Messages.getString(
  // "FilesOutputNewJobWizardPage.table_name_header" ); //$NON-NLS-1$
  // map.add( message );
  // // Composite tabComp = new Composite(mainComp, SWT.NONE);
  // // tabComp.setLayout( new GridLayout(1, false) );
  // // gridData = new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL |
  // // GridData.GRAB_VERTICAL);
  // // gridData.horizontalIndent = 15;
  // // tabComp.setLayoutData( gridData );
  // ArrayList<Integer> width = new ArrayList<Integer>();
  // width.add( Integer.valueOf( 240 ) );
  // width.add( Integer.valueOf( 100 ) );
  // StageOutContentProvider contentProvider = new StageOutContentProvider();
  // this.copyFromTab = new StageInTab( new StageOutContentProvider(),
  // new StageInLabelProvider(),
  //                                       
  // map,
  // this.initialStagingIn,
  // width );
  // this.copyFromTab.createControl( stdFilesGroup );
  // // stdFilesGroup.getChildren()[stdFilesGroup.getChildren().length -
  // // 1].setLayoutData( gridData );
  // // setPageComplete( true );
  // this.isCreated = true;
  // // Label horizontalSeparator = new Label( mainComp, SWT.SEPARATOR |
  // // SWT.HORIZONTAL );
  // // gridData = new GridData( GridData.FILL_BOTH );
  // // horizontalSeparator.setLayoutData( gridData );
  // Group stdFilesGroup1 = new Group( mainComp, SWT.NONE );
  // stdFilesGroup1.setText( "Stage out" );
  // stdFilesGroup1.setLayout( new GridLayout( 1, false ) );
  // gridData = new GridData( GridData.FILL_HORIZONTAL );
  // gridData.grabExcessHorizontalSpace = true;
  // // gridData.horizontalSpan = 3;
  // stdFilesGroup1.setLayoutData( gridData );
  // Form form1 = toolkit.createForm( stdFilesGroup1 );
  // form1.getBody().setLayout( new GridLayout( 2, false ) );
  // Label l1 = new Label( form1.getBody(), SWT.NONE );
  // // gridData.horizontalIndent = 15;
  // l1.setLayoutData( gridData );
  // l1.setText( "Files copied from computing element after execution of the
  // job" );
  // ImageHyperlink link1 = toolkit.createImageHyperlink( form1.getBody(),
  // SWT.WRAP );
  // link1.setImage( Activator.getDefault().getImageRegistry().get( "helplink" )
  // );
  // // form1.setText( "Stage out" );
  // // Composite tabComp1 = new Composite(stdFilesGroup1, SWT.NONE);
  // // tabComp1.setLayout( new GridLayout(1, false) );
  // // gridData = new GridData( GridData.FILL_BOTH );
  // // gridData.horizontalIndent = 15;
  // // tabComp1.setLayoutData( gridData );
  // map = new ArrayList<String>();
  // message = Messages.getString(
  // "FilesOutputNewJobWizardPage.table_name_header" ); //$NON-NLS-1$
  // map.add( message );
  // message = Messages.getString(
  // "FilesOutputNewJobWizardPage.table_target_header" ); //$NON-NLS-1$
  // map.add( message );
  // width = new ArrayList<Integer>();
  // width.add( Integer.valueOf( 100 ) );
  // width.add( Integer.valueOf( 240 ) );
  // this.copyToTab = new StageOutTab( new StageOutContentProvider(),
  // new StageOutLabelProvider(),
  // map,
  // this.initialStagingOut,
  // width );
  // this.copyToTab.createControl( stdFilesGroup1 );
  // // setPageComplete( true );
  // this.isCreated = true;
  // setControl( mainComp );
  // }
  // private String getSelectedElementDisplayName( IGridConnectionElement
  // element )
  // {
  // String result = element.getName();
  // try {
  // if( element.getConnectionFileStore()
  // .getFileSystem()
  // .getScheme()
  // .equalsIgnoreCase( "file" ) )
  // {
  // result = "file://" + result;
  // }
  // } catch( CoreException coreExc ) {
  // Activator.logException( coreExc );
  // }
  // return result;
  // }
//  protected class StageOutContentProvider implements IStructuredContentProvider
//  {
//
//    @SuppressWarnings("unchecked")
//    public Object[] getElements( final Object inputElement ) {
//      DataStaging[] elements = new DataStaging[ 0 ];
//      elements = ( DataStaging[] )( ( List )inputElement ).toArray( elements );
//      return elements;
//    }
//
//    public void dispose() {
//      // nothing
//    }
//
//    public void inputChanged( final Viewer viewer,
//                              final Object oldInput,
//                              final Object newInput )
//    {
//      if( newInput == null ) {
//        // do nothing
//      } else {
//        if( viewer instanceof TableViewer ) {
//          TableViewer tableViewer = ( TableViewer )viewer;
//          if( tableViewer.getTable().isDisposed() ) {
//            // do nothing
//          } else {
//            tableViewer.setSorter( new ViewerSorter() {
//
//              @Override
//              public int compare( final Viewer iviewer,
//                                  final Object element1,
//                                  final Object element2 )
//              {
//                int result;
//                if( element1 == null ) {
//                  result = -1;
//                } else if( element2 == null ) {
//                  result = 1;
//                } else {
//                  result = ( ( DataStaging )element1 ).getName()
//                    .compareToIgnoreCase( ( ( DataStaging )element2 ).getName() );
//                }
//                return result;
//              }
//            } );
//          }
//        }
//      }
//    }
//  }
//  class StageOutLabelProvider extends LabelProvider
//    implements ITableLabelProvider
//  {
//
//    public String getColumnText( final Object element, final int columnIndex ) {
//      String result = null;
//      if( element != null ) {
//        DataStaging var = ( DataStaging )element;
//        switch( columnIndex ) {
//          case 0: // name
//            result = var.getName();
//          break;
//          case 1: // target loaction
//            result = var.getTargetLocation();
//          break;
//        }
//      }
//      return result;
//    }
//
//    public Image getColumnImage( final Object element, final int columnIndex ) {
//      return null;
//    }
//  }

  /**
   * Method to access String values kept by table
   * 
   * @param type Type of a files which values should be extracted from table
   * @return Map with file names as a keys and their location as a value
   */
  public List<DataStagingType> getFiles( final FileType type ) {
    List<DataStagingType> result = new ArrayList<DataStagingType>();
    switch( type ) {
      case INPUT:
        if( this.copyFromTab != null ) {
          result = this.copyFromTab.getDataStagingType();
        }
        // for( DataStaging file : this.copyFromTab.getDataStagingInType() ) {
        // if( !file.getSourceLocation().equals( "" ) ) { //$NON-NLS-1$
        // result.put( file.getName(), file.getSourceLocation() );
        // }
        // }
      break;
      case OUTPUT:
        if( this.copyToTab != null ) {
          result = this.copyToTab.getDataStagingType();
        }
        // for( DataStaging file : this.copyToTab.getInput() ) {
        // if( !file.getTargetLocation().equals( "" ) ) { //$NON-NLS-1$
        // result.put( file.getName(), file.getTargetLocation() );
        // }
        // }
      break;
      case NULL:
        // do nothing
      break;
    }
    return result;
  }

  @Override
  public IWizardPage getNextPage() {
    // TODO Auto-generated method stub
    return super.getNextPage();
  }

  /**
   * Method to find out if this page was created
   * 
   * @return true if method
   *         {@link DataStagingNewJobWizardPage#createControl(Composite)} was
   *         invoked
   */
  public boolean isCreated() {
    return this.isCreated;
  }
//  class StageInLabelProvider extends LabelProvider
//    implements ITableLabelProvider
//  {
//
//    public String getColumnText( final Object element, final int columnIndex ) {
//      String result = null;
//      if( element != null ) {
//        DataStaging var = ( DataStaging )element;
//        switch( columnIndex ) {
//          case 0: // source location
//            result = var.getSourceLocation();
//          break;
//          case 1: // name
//            result = var.getName();
//          break;
//        }
//      }
//      return result;
//    }
//
//    public Image getColumnImage( final Object element, final int columnIndex ) {
//      return null;
//    }
//  }

  /**
   * Method to set initial contents of table holding DataStagingOut files, when
   * only their name and location are given (as a Strings).
   * 
   * @param initialOut map of DataStagingOut files' data with their names as a
   *            keys and locations as a values
   */
  public void setInitialStagingOut( final Map<String, String> initialOut ) {
    this.initialStagingOut = new ArrayList<DataStagingType>();
    if( initialOut != null ) {
      for( String name : initialOut.keySet() ) {
        try {
          DataStagingType newData = JSDLModelFacade.getDataStagingType();
          newData.setFileName( name );
          SourceTargetType sourceDataOut = JSDLModelFacade.getSourceTargetType();
          sourceDataOut.setURI( initialOut.get( name ) );
          newData.setTarget( sourceDataOut );
          this.initialStagingOut.add( newData );
        } catch( Exception e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    if( this.copyToTab != null ) {
      this.copyToTab.updateInput( this.initialStagingOut );
    }
  }

  /**
   * Method to set initial contents of table holding DataStagingIn files, when
   * only their name and location are given (as a Strings).
   * 
   * @param initialIn map of DataStagingOut files' data with their names as a
   *            keys and locations as a values
   */
  public void setInitialStagingIn( final Map<String, String> initialIn ) {
    this.initialStagingIn = new ArrayList<DataStagingType>();
    if( initialIn != null ) {
      for( String name : initialIn.keySet() ) {
        try {
          DataStagingType newData = JSDLModelFacade.getDataStagingType();
          newData.setFileName( name );
          SourceTargetType sourceDataOut = JSDLModelFacade.getSourceTargetType();
          sourceDataOut.setURI( initialIn.get( name ) );
          newData.setSource( sourceDataOut );
          this.initialStagingIn.add( newData );
        } catch( Exception e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    if( this.copyFromTab != null ) {
      this.copyFromTab.updateInput( this.initialStagingIn );
    }
  }

  @Override
  public IWizardPage getPreviousPage() {
    return super.getPreviousPage();
  }

  /**
   * Setter method for a field holding initial contents of table with DataStagingInFiles
   * @param files list of {@link DataStagingType} objects
   */
  public void setInitialStagingInModel( final List<DataStagingType> files ) {
    this.initialStagingIn = files;
    if( this.copyFromTab != null ) {
      this.copyFromTab.updateInput( this.initialStagingIn );
    }
  }

  /**
   * Setter method for a field holding initial contents of table with DataStagingOutFiles
   * @param files list of {@link DataStagingType} objects
   */
  public void setInitialStagingOutModel( final List<DataStagingType> files ) {
    this.initialStagingOut = files;
    if( this.copyToTab != null ) {
      this.copyToTab.updateInput( this.initialStagingOut );
    }
  }
}
