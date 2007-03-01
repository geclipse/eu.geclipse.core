package eu.geclipse.ui.internal.actions;

import java.util.List;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionGroup;
import eu.geclipse.ui.views.GridProjectView;

public class GridProjectActions extends ActionGroupManager {
  
  public GridProjectActions( final GridProjectView view ) {
    
    super();
    
    IWorkbenchPartSite site = view.getSite();
    IWorkbenchWindow window = site.getWorkbenchWindow();
    
    addGroup( new NewWizardActions( window ) );
    addGroup( new OpenActions( site ) );
    addGroup( new FileActions( site ) );
    addGroup( new SubmitJobActions( site ) );
    addGroup( new MountActions( site ) );
    addGroup( new ProjectActions( site ) );
    addGroup( new BuildActions( site ) );

  }
  
  public void delegateOpenEvent( final OpenEvent event ) {
    getOpenActions().delegateOpenEvent( event );
  }
  
  protected OpenActions getOpenActions() {
    OpenActions result = null;
    List< ActionGroup > groups = getGroups();
    for ( ActionGroup group : groups ) {
      if ( group instanceof OpenActions ) {
        result = ( OpenActions ) group;
        break;
      }
    }
    return result;
  }
  
}
