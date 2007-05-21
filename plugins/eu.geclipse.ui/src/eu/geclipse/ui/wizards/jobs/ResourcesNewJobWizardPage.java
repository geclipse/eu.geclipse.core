package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.ui.internal.wizards.jobs.JSDLExactValueTab;
import eu.geclipse.ui.internal.wizards.jobs.JSDLRangesTab;
import eu.geclipse.ui.internal.wizards.jobs.Range;
import eu.geclipse.ui.internal.wizards.jobs.ValueWithEpsilon;

/**
 * Page of New Job Wizard for job resources details
 */
public class ResourcesNewJobWizardPage extends WizardPage {

  
  private JSDLRangesTab tab;
  private JSDLRangesTab tabCPUCount;
  private JSDLRangesTab totalPhysicalMemory;
  private JSDLExactValueTab tabVal;
  private JSDLExactValueTab tabCPUCountValues;
  private JSDLExactValueTab totalPhysicalMemoryValues;
  private boolean isCreated = false;

  protected ResourcesNewJobWizardPage( final String pageName
                                        )
  {
    super( pageName );
    
    this.setTitle( Messages.getString("ResourcesNewJobWizardPage.resources_title") ); //$NON-NLS-1$
    this.setDescription( Messages.getString("ResourcesNewJobWizardPage.resources_description") ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    GridData layout = new GridData();
    
    Label cpuSpeedLabel = new Label( mainComp,
                                     GridData.HORIZONTAL_ALIGN_BEGINNING
                                         | GridData.VERTICAL_ALIGN_CENTER );
    cpuSpeedLabel.setText( Messages.getString("ResourcesNewJobWizardPage.individual_cpu_speed_label") ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    
    cpuSpeedLabel.setLayoutData( layout );
    ArrayList<String> temp = new ArrayList<String>();
    temp.add( Messages.getString("ResourcesNewJobWizardPage.range_start_collumn")); //$NON-NLS-1$
    temp.add( Messages.getString("ResourcesNewJobWizardPage.range_end_collumn")); //$NON-NLS-1$
    this.tab = new JSDLRangesTab( new RangeContentProvider(),
                                  new RangeLabelProvider(),
                                  temp,
                                  30,
                                  50 );
    this.tab.createControl( mainComp );
    
    ArrayList<String> temp1 = new ArrayList<String>();
    temp1.add( Messages.getString("ResourcesNewJobWizardPage.value_collumn")); //$NON-NLS-1$
    temp1.add( Messages.getString("ResourcesNewJobWizardPage.epsilon_collumn")); //$NON-NLS-1$
    this.tabVal = new JSDLExactValueTab( new ValueWithEpsilonContentProvider(),
                                  new ValueWithEpsilonLabelProvider(),
                                  temp1,
                                  30,
                                  50 );
    this.tabVal.createControl( mainComp );
    
    Label totalCPUCountLabel = new Label( mainComp,
                                          GridData.HORIZONTAL_ALIGN_BEGINNING
                                              | GridData.VERTICAL_ALIGN_CENTER );
    totalCPUCountLabel.setText( Messages.getString("ResourcesNewJobWizardPage.total_cpu_count_label") ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    totalCPUCountLabel.setLayoutData( layout );
    this.tabCPUCount = new JSDLRangesTab( new RangeContentProvider(),
                                          new RangeLabelProvider(),
                                          temp,
                                          30,
                                          50 );
    this.tabCPUCount.createControl( mainComp );
    
    this.tabCPUCountValues = new JSDLExactValueTab( new ValueWithEpsilonContentProvider(),
                                                    new ValueWithEpsilonLabelProvider(),
                                                    temp1,
                                                    30,
                                                    50 );
    this.tabCPUCountValues.createControl( mainComp );
    
    Label totalPhysicalMemoryLabel = new Label( mainComp,
                                          GridData.HORIZONTAL_ALIGN_BEGINNING
                                              | GridData.VERTICAL_ALIGN_CENTER );
    totalPhysicalMemoryLabel.setText( Messages.getString("ResourcesNewJobWizardPage.total_physical_memory_label") ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalIndent = 20;
    totalPhysicalMemoryLabel.setLayoutData( layout );
    
    this.totalPhysicalMemory = new JSDLRangesTab( new RangeContentProvider(),
                                          new RangeLabelProvider(),
                                          temp,
                                          30,
                                          50 );
    this.totalPhysicalMemory.createControl( mainComp );
    
    this.totalPhysicalMemoryValues = new JSDLExactValueTab( new ValueWithEpsilonContentProvider(),
                                                    new ValueWithEpsilonLabelProvider(),
                                                    temp1,
                                                    30,
                                                    50 );
    this.totalPhysicalMemoryValues.createControl( mainComp );
    
    this.isCreated = true;
    
    setControl( mainComp );
  }

  /**
   * Method to access ranges of indvidual CPU speed
   * @return ranges list of individual CPU speed
   */
  @SuppressWarnings("unchecked") 
  public ArrayList<Range> getIndividualCPUSpeedRanges() {
    return this.tab.getInput();
  }
  
  /**
   * Method to access ranges of total CPU count
   * @return ranges list of total CPU count
   */
  @SuppressWarnings("unchecked")
  public ArrayList<Range> getTotalCPUCount(){
    return this.tabCPUCount.getInput();
  }
  
  /**
   * Method to access ranges of total physical memory 
   * @return ranges list of total physical memory
   */
  @SuppressWarnings("unchecked")
  public ArrayList<Range> getTotalPhysicalMemory(){
    return this.totalPhysicalMemory.getInput();
  }
  
  /**
   * Method to access exact values of individual CPU speed
   * @return list of exact values of individual CPU speed
   */
  @SuppressWarnings("unchecked")
  public ArrayList<ValueWithEpsilon> getIndividualCPUSValues(){
    return this.tabVal.getInput();
  }
  
  /**
   * Method to access exact values of total CPU count
   * @return list of exact values of total CPU count
   */
  @SuppressWarnings("unchecked")
  public ArrayList<ValueWithEpsilon> getTotalCPUCountValues(){
    return this.tabCPUCountValues.getInput();
  }
  
  /**
   * Method to access exact values of total physical memory
   * @return list of exact values of total physical memory
   */
  @SuppressWarnings("unchecked")
  public ArrayList<ValueWithEpsilon> getTotalPhysicalMemoryValues(){
    return this.totalPhysicalMemoryValues.getInput();
  }
  
  protected class RangeContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      Range[] elements = new Range[ 0 ];
      Map<String, String> m = new HashMap<String, String>();
      if( !m.isEmpty() ) {
        elements = new Range[ m.size() ];
        String[] varStarts = new String[ m.size() ];
        m.keySet().toArray( varStarts );
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
                int result = -1;
                if( element1 == null ) {
                  result = -1;
                } else if( element2 == null ) {
                  result = 1;
                } 
                return result;
              }
            } );
          }
        }
      }
    }
  }
  class RangeLabelProvider extends LabelProvider implements ITableLabelProvider
  {

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        Range var = ( Range )element;
        switch( columnIndex ) {
          case 0: // type
            result = Double.valueOf( var.getStart() ).toString();
          break;
          case 1: // name
            result = Double.valueOf( var.getEnd() ).toString();
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
  
  //////providewrs for ValueWithEpsilon
  protected class ValueWithEpsilonContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      ValueWithEpsilon[] elements = new ValueWithEpsilon[ 0 ];
      Map<String, String> m = new HashMap<String, String>();
      if( !m.isEmpty() ) {
        elements = new ValueWithEpsilon[ m.size() ];
        String[] varStarts = new String[ m.size() ];
        m.keySet().toArray( varStarts );
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
                int result = -1;
                if( element1 == null ) {
                  result = -1;
                } else if( element2 == null ) {
                  result = 1;
                } // else {
                // result = ( ( Range )element1 ).getName()
                // .compareToIgnoreCase( ( ( Range )element2 ).getName() );
                // }
                return result;
              }
            } );
          }
        }
      }
    }
  }
  class ValueWithEpsilonLabelProvider extends LabelProvider implements ITableLabelProvider
  {

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        ValueWithEpsilon var = ( ValueWithEpsilon )element;
        switch( columnIndex ) {
          case 0: // type
            result = Double.valueOf( var.getValue() ).toString();
          break;
          case 1: // name
            result = Double.valueOf( var.getEpsilon() ).toString();
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
