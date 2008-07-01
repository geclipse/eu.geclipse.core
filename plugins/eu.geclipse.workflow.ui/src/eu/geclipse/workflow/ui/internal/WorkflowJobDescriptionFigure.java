/**
 * 
 */
package eu.geclipse.workflow.ui.internal;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.RectangleFigure;


/**
 * @author ash
 *
 */
public class WorkflowJobDescriptionFigure extends RectangleFigure {
  
  private boolean myUseLocalCoordinates = false;

  /**
   * Default Constructor
   */
  public WorkflowJobDescriptionFigure() {
    this.setBackgroundColor( ColorConstants.green );
    this.setSize( 10, 10 );
  }

  protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
    this.myUseLocalCoordinates = useLocalCoordinates;
  }

  @Override
  protected boolean useLocalCoordinates() {
    return this.myUseLocalCoordinates;
  }
}
