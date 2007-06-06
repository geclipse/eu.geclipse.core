package eu.geclipse.ui.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class ConnectionLocationWizardPage
    extends WizardNewFileCreationPage {

  public ConnectionLocationWizardPage( final String pageName,
                                       final IStructuredSelection selection ) {
    super( pageName, selection );
  }
  
  protected  void createAdvancedControls( final Composite parent ) {
    // We want no linked resources, therefore this is an empty implementation
  }
  
  protected IStatus validateLinkedResource() {
    // We want no linked resources, therefore just return OK
    return Status.OK_STATUS;
  }
  
}
