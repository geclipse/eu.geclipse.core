package eu.geclipse.ui.simpleTest;

import java.util.List;

import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;
import eu.geclipse.core.simpleTest.ISimpleTestDescription;
import eu.geclipse.ui.AbstractSimpleTestUIFactory;
import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;

/**
 * This UI factory is dedicated to port scan and provides the ui elements
 * that are dealing with this test.
 * 
 * @author cs05ce1
 *
 */
public class PortScanUIFactory extends AbstractSimpleTestUIFactory {

  public AbstractSimpleTestDialog getSimpleTestDialog( final ISimpleTest test, 
                                                       final List< IGridResource > resources, 
                                                       final Shell parentShell ) {
    PortScanDialog dialog = new PortScanDialog( test, resources, parentShell );
    return dialog;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.ISimpleTestUIFactory#getSupportedDescription()
   */
  public ISimpleTestDescription getSupportedDescription() {
    return new PortScanDescription();
  }

}
