package eu.geclipse.jsdl.ui.internal.pages.sections;

import org.eclipse.ui.forms.editor.FormPage;

import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;

public class JsdlFormPageSection extends JsdlAdaptersFactory {

  protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
  protected FormPage parentPage;
  protected boolean isNotifyAllowed = true;
  protected boolean adapterRefreshed = false;

  public JsdlFormPageSection() {
    super();
  }

  protected void contentChanged() {
    
    if ( this.isNotifyAllowed ){
      fireNotifyChanged( null );
    }
    
  }
}