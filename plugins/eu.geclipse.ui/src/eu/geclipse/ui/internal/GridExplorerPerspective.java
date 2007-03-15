package eu.geclipse.ui.internal;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class GridExplorerPerspective
    implements IPerspectiveFactory {
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
   */
  public void createInitialLayout( final IPageLayout layout ) {
    
    String editorArea = layout.getEditorArea();

    IFolderLayout bottomLeftFolder
      = layout.createFolder( "bottomleft", IPageLayout.BOTTOM, 0.5f, editorArea ); //$NON-NLS-1$
    bottomLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW + ":left*" );
    
    IFolderLayout bottomRightFolder
      = layout.createFolder( "bottomright", IPageLayout.RIGHT, 0.5f, eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW ); //$NON-NLS-1$
    bottomRightFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW + ":right*" );
    
  }
  
}
