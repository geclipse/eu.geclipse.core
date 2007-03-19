/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ Contributor(s): PSNC - Mariusz
 * Wojtysiak
 ******************************************************************************/
package eu.geclipse.ui.editors;

import java.text.DateFormat;
import java.util.Calendar;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IJobManager;

/**
 * @author Mariusz Wojtysiak Viewer for submitted jobs developed as multitab
 *         editor
 */
public class JobEditor extends MultiPageEditorPart {

  private Page[] pages;

  @Override
  protected void createPages()
  {
    int index = 0;
    pages = new Page[ 2 ];
    pages[ index++ ] = new PageGeneral();
    pages[ index++ ] = new PageDescription();
    refresh();
  }

  @Override
  public void doSave( final IProgressMonitor monitor )
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void doSaveAs()
  {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean isSaveAsAllowed()
  {
    return false;
  }
  abstract class Page {

    private Composite pageComposite;
    private Composite contentComposite; // contains controls showing job-data on
                                        // page

    protected Page() {
      super();
      pageComposite = new Composite( getContainer(), SWT.NULL );
      pageComposite.setLayout( new FillLayout( SWT.VERTICAL ) );
      contentComposite = createContent( pageComposite );
      int pageIndex = addPage( pageComposite );
      setPageText( pageIndex, getPageName() );
    }

    abstract protected Composite createContent( final Composite parentComposite );

    abstract protected String getPageName();

    /**
     * Called when xml file with job was changed and page have to be refreshed
     * 
     * @param gridJob
     */
    abstract void refresh( final IGridJob gridJob );
  };
  class PageGeneral extends Page {

    private Text jobNameText;
    private Text jobIdText;
    private Text jobStatusText;
    private Text jobStatusRefreshedText;

    /**
     * Class collecting all controls for first page - general
     */
    public PageGeneral() {
      super();
    }

    @Override
    protected Composite createContent( final Composite parentComposite )
    {
      Composite composite = new Composite( parentComposite, SWT.NULL );
      composite.setLayout( new GridLayout( 2, false ) );
      jobNameText = createControlPair( composite,
                                       Messages.getString( "JobEditor.label_jobname" ) ); //$NON-NLS-1$
      jobIdText = createControlPair( composite,
                                     Messages.getString( "JobEditor.label_id" ) ); //$NON-NLS-1$
      jobStatusText = createControlPair( composite,
                                         Messages.getString( "JobEditor.label_status" ) ); //$NON-NLS-1$
      jobStatusRefreshedText = createControlPair( composite,
                                                  Messages.getString( "JobEditor.label_status_refresed" ) ); //$NON-NLS-1$
      return composite;
    }

    private Text createControlPair( final Composite parentComposite,
                                    final String labelString )
    {
      Label label = new Label( parentComposite, SWT.NULL );
      label.setText( labelString );
      return new Text( parentComposite, SWT.READ_ONLY );
    }

    @Override
    protected String getPageName()
    {
      return Messages.getString( "JobEditor.page_general" ); //$NON-NLS-1$;
    }

    @Override
    void refresh( final IGridJob gridJob )
    {
      setValue( jobNameText, gridJob.getName() );
      setValue( jobIdText, gridJob.getID().getJobID() );
      IGridJobStatus status = gridJob.getStatus();
      if( status != null ) {
        setValue( jobStatusText, gridJob.getStatus().getName() );
      } else {
        setValue( jobStatusText, "" ); //$NON-NLS-1$;    
      }
      jobStatusRefreshedText.setText( DateFormat.getDateTimeInstance()
        .format( Calendar.getInstance().getTime() ) ); // TODO read from
                                                        // IStatus
    }

    private void setValue( final Text controlText, final String valueString ) {
      controlText.setText( valueString != null
                                              ? valueString
                                              : "" ); //$NON-NLS-1$;
    }
  }
  /**
   * Class collecting all controls for second page - description
   */
  class PageDescription extends Page {

    Table descriptionTable;

    protected PageDescription() {
      super();
    }

    @Override
    protected Composite createContent( final Composite parentComposite )
    {
      Composite contentComposite = new Composite( parentComposite, SWT.NONE );
      contentComposite.setLayout( new FillLayout() );
      descriptionTable = new Table( contentComposite, SWT.BORDER
                                                      | SWT.FULL_SELECTION
                                                      | SWT.V_SCROLL );
      descriptionTable.setLinesVisible( true );
      descriptionTable.setHeaderVisible( true );
      TableColumn nameColumn = new TableColumn( descriptionTable, SWT.NONE );
      nameColumn.setText( "Property" );
      TableColumn valueColumn = new TableColumn( descriptionTable, SWT.NONE );
      valueColumn.setText( "Value" );
      return contentComposite;
    }

    @Override
    protected String getPageName()
    {
      return Messages.getString( "JobEditor.page_description" ); //$NON-NLS-1$
    }

    @Override
    void refresh( final IGridJob gridJob )
    {
      // TODO Auto-generated method stub
    }
  }

  private void refresh() {
    IEditorInput editorInput = getEditorInput();
    if( editorInput instanceof IFileEditorInput ) {
      setPartName( editorInput.getName() );
      IFileEditorInput fileEditorInput = ( IFileEditorInput )editorInput;
      IGridJob gridJob = getGridJob( fileEditorInput );
      if( gridJob != null ) {
        for( Page page : pages ) {
          page.refresh( gridJob );
        }
      }
    }
  }

  private IGridJob getGridJob( final IFileEditorInput fileEditorInput ) {
    IJobManager jobManager = GridModel.getJobManager();
    IGridJob gridJob = ( IGridJob )jobManager.findChild( fileEditorInput.getFile()
      .getName() );
    if( gridJob == null ) { //TODO add job-loading
    }
    return gridJob;
  }
}
