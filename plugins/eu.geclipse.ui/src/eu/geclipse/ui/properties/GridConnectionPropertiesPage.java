package eu.geclipse.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import eu.geclipse.ui.widgets.GridConnectionDefinitionComposite;

public class GridConnectionPropertiesPage extends PropertyPage {
  
  private GridConnectionDefinitionComposite connectionDefinitionComp;

  @Override
  protected Control createContents( final Composite parent ) {
    this.connectionDefinitionComp = new GridConnectionDefinitionComposite( parent, SWT.NULL );
    return this.connectionDefinitionComp;
  }

}
