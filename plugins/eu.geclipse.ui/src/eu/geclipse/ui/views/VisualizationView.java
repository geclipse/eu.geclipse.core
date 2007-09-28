package eu.geclipse.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.model.IGridVisualization;


/**
 * @author sgirtel
 *
 */
public class VisualizationView extends ViewPart {

  private CTabFolder cTabFolder;
  private IGridVisualization vtkPipeline = null;
  private String renderingSite;

  @Override
  public void createPartControl( Composite parent ) {

    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;
    gridData.verticalAlignment = GridData.FILL;
    this.cTabFolder = new CTabFolder( parent, SWT.FLAT | SWT.BOTTOM );
    this.cTabFolder.setLayoutData( gridData );
    this.cTabFolder.addFocusListener( new FocusAdapter() {

      @Override
      public void focusGained( final FocusEvent event ) {
        CTabFolder folder = ( CTabFolder )event.widget;
        CTabItem item = folder.getSelection();
        if( ( item != null ) && ( item.getControl() != null ) ) {
          item.getControl().setFocus();
        }
      }
    } );
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    if( this.cTabFolder != null )
      this.cTabFolder.setFocus();
  }

  public CTabItem getCTabItem() {
    CTabItem cTabItem = this.cTabFolder != 
      null ? this.cTabFolder.getSelection() : null;
    return cTabItem;
  }

  public CTabFolder getCTabFolder() {
    return this.cTabFolder;
  }

  /**
   * @param vtkPipeline 
   * @param renderingSite 
   */
  public void setPipeline( final IGridVisualization vtkPipeline, String renderingSite ) {
    //TODO: this could be a better place to do the validation of the pipeline as now it is 
    // really crucial whereas during the specification of the pipeline the user may want to 
    // save it even though it is not yet completed.
    if ( vtkPipeline.isValid() ) {
      this.vtkPipeline = vtkPipeline;
      this.renderingSite = renderingSite;
    }
    else {
      //TODO - display the problem dialog
    }
  }

  /**
   * Invokes the rendering process as described by the preset vtkPipeline.
   */
  public void render() {
    
    if ( this.vtkPipeline == null )
      return;
    
    if ( this.renderingSite.compareToIgnoreCase( "local" ) == 0 ) { //$NON-NLS-1$
      this.vtkPipeline.renderLocal();
    }
    else this.vtkPipeline.renderRemote();
  }
}
