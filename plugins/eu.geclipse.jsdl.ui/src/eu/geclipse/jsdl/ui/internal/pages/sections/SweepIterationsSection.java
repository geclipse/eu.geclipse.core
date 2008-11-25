/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Katarzyna Bylec - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepType;
import eu.geclipse.jsdl.parametric.IParametricJsdlGenerator;
import eu.geclipse.jsdl.parametric.ParametricJsdlGeneratorFactory;
import eu.geclipse.jsdl.ui.adapters.jsdl.ParametricJobAdapter;
import eu.geclipse.jsdl.ui.internal.EditorParametricJsdlHandler;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.providers.parameters.IterationsLProvider;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Section of "Parameters" page in JSDL multi-page editor. This section displays
 * values of each parameter for every iteration of parameters sweep.
 */
public class SweepIterationsSection extends JsdlFormPageSection {

  private ParametricJobAdapter adapter;
  private Shell shell;
  private Table table;
  private JobDefinitionType jobDefinitionType;
  private FormToolkit formToolkit;
  private TableViewer viewer;
  private IterationsLProvider labelProvider;
  private JSDLJobDescription jsdlJobDescription;
  private Composite client;
  private ArrayList<SweepType> sweepType;

  /**
   * Constructor.
   * 
   * @param parent parent composite of this section
   * @param toolkit forms toolkit to use
   * @param adapter adapter handling operations on parametric part of JSDL
   */
  public SweepIterationsSection( final Composite parent,
                                 final FormToolkit toolkit,
                                 final ParametricJobAdapter adapter )
  {
    this.adapter = adapter;
    createSection( parent, toolkit );
  }

  private void createSection( final Composite parent, final FormToolkit toolkit )
  {
    this.shell = parent.getShell();
    String sectionDescription = "Values of each parameter for every iteration of parameters sweep."; //$NON-NLS-1$
    this.formToolkit = toolkit;
    this.client = FormSectionFactory.createExpandableSection( toolkit,
                                                              parent,
                                                              "Iterations preview", //$NON-NLS-1$
                                                              sectionDescription,
                                                              1,
                                                              false );
    Hyperlink link = toolkit.createHyperlink( this.client,
                                              "Calculate iterations (refresh)", //$NON-NLS-1$
                                              SWT.LEAD );
    link.addMouseListener( new MouseListener() {

      public void mouseDoubleClick( final MouseEvent e ) {
        // TODO Auto-generated method stub
      }

      public void mouseDown( final MouseEvent e ) {
        updateTable();
      }

      public void mouseUp( final MouseEvent e ) {
        // TODO Auto-generated method stub
      }
    } );
    renewTableViewer( new EditorParametricJsdlHandler( this ) ); // dummy
    // handler
  }

  protected void updateTable() {
    final EditorParametricJsdlHandler handler = new EditorParametricJsdlHandler( this );
    renewTableViewer( handler );
    this.viewer.setInput( handler );
    this.viewer.setContentProvider( handler );
    final JSDLJobDescription jsdl = this.jsdlJobDescription;
    final SweepIterationsSection section = this;
    Job job = new Job( "Generating sweep for iterations" ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        IParametricJsdlGenerator generator = ParametricJsdlGeneratorFactory.getGenerator( jsdl );
        try {
          generator.generate( handler, monitor );
        } catch( ProblemException exception ) {
          final ProblemException pexc = exception;
          final Shell fShell = shell;
          fShell.getDisplay().asyncExec( new Runnable() {

            public void run() {
              ProblemDialog.openProblem( fShell,
                                         "JSDLs preview", //$NON-NLS-1$
                                         "Generation JSDLs for preview failed", //$NON-NLS-1$
                                         pexc );
            }
          } );
        }
        return Status.OK_STATUS;
      }
    };
    job.setUser( true );
    job.schedule();
  }

  private void renewTableViewer( final EditorParametricJsdlHandler handler ) {
    List<Integer> widths = Collections.emptyList();
    if( this.viewer != null ) {
      TableColumn[] columns = this.viewer.getTable().getColumns();
      if( columns.length > 1 ) {
        widths = new ArrayList<Integer>( columns.length );
        for( TableColumn column : columns ) {
          widths.add( Integer.valueOf( column.getWidth() ) );
        }
      }
      this.viewer.getTable().dispose();
    }
    this.viewer = null;
    this.viewer = new TableViewer( this.client, SWT.BORDER | SWT.FULL_SELECTION );
    TableWrapData tableData = new TableWrapData( TableWrapData.FILL_GRAB );
    tableData.heightHint = 300;
    this.table = this.viewer.getTable();
    this.table.setLayoutData( tableData );
    this.labelProvider = new IterationsLProvider();
    this.labelProvider.setColumnNames( getColumnNamesForTable() );
    this.viewer.setLabelProvider( this.labelProvider );
    this.viewer.setContentProvider( handler );
    this.table.setHeaderVisible( true );
    List<String> columnNames = getColumnNamesForTable();
    int colIndex = 0;
    for( String name : columnNames ) {
      TableColumn column = new TableColumn( this.table, SWT.NONE );
      column.setText( name );
      column.setWidth( colIndex < widths.size()
                                               ? widths.get( colIndex )
                                                 .intValue()
                                               : 90 );
      colIndex++;
    }
    this.client.layout();
  }

  /**
   * @param jobDefinition
   * @param jsdlJobDescr
   */
  public void setInput( final JobDefinitionType jobDefinition,
                        final JSDLJobDescription jsdlJobDescr )
  {
    this.adapterRefreshed = true;
    if( null != jobDefinition ) {
      this.jobDefinitionType = jobDefinition;
    }
    this.jsdlJobDescription = jsdlJobDescr;
  }

  public void iterationGenerated( final List<String> newRow ) {
    final TableViewer viewerF = this.viewer;
    PlatformUI.getWorkbench().getDisplay().syncExec( new Runnable() {

      public void run() {
        viewerF.refresh( false );
      }
    } );
  }

  private List<String> getColumnNamesForTable() {
    List<String> columnNames = new ArrayList<String>();
    columnNames.add( "Iteration" ); //$NON-NLS-1$
    for( String paramName : getInerSweepNames( getInnerList() ) ) {
      columnNames.add( paramName );
    }
    return columnNames;
  }

  @Override
  protected void contentChanged() {
    this.labelProvider.setColumnNames( getColumnNamesForTable() );
    super.contentChanged();
  }

  private List<SweepType> getInnerList() {
    List<SweepType> inerSweepList = new ArrayList<SweepType>();
    this.sweepType = new ArrayList<SweepType>();
    if( null != this.jobDefinitionType ) {
      TreeIterator<EObject> iterator = this.jobDefinitionType.eAllContents();
      while( iterator.hasNext() ) {
        EObject testType = iterator.next();
        if( testType instanceof SweepType
            && !( testType.eContainer() instanceof SweepType ) )
        {
          SweepType type = ( ( SweepType )testType );
          type.eAdapters().add( this );
          this.sweepType.add( type );
        }
      }
      if( !this.sweepType.isEmpty() ) {
        for( SweepType type : this.sweepType ) {
          TreeIterator<EObject> it = type.eAllContents();
          while( it.hasNext() ) {
            EObject testObject = it.next();
            if( testObject instanceof SweepType ) {
              inerSweepList.add( ( SweepType )testObject );
            }
          }
        }
        inerSweepList.addAll( this.sweepType );
      }
    }
    return inerSweepList;
  }

  private List<String> getInerSweepNames( final List<SweepType> inerSweepList )
  {
    List<String> result = new ArrayList<String>();
    for( SweepType sweep : inerSweepList ) {
      EList list = sweep.getAssignment();
      for( int i = 0; i < list.size(); i++ ) {
        Object el = list.get( i );
        if( el instanceof AssignmentType ) {
          AssignmentType assignment = ( AssignmentType )el;
          EList paramList = assignment.getParameter();
          for( int j = 0; j < paramList.size(); j++ ) {
            result.add( ( String )paramList.get( j ) );
          }
        }
      }
    }
    return result;
  }

  /**
   * @return the Label Provider.
   */
  public IterationsLProvider getLabelProvider() {
    return this.labelProvider;
  }
}
