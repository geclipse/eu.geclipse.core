/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.progress.IProgressConstants;


/**
 * Perspective factory for the g-Eclipse grid exploring perspective. 
 */
public class GridExplorerPerspective
    implements IPerspectiveFactory {
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
   */
  public void createInitialLayout( final IPageLayout layout ) {
    
    String editorArea = layout.getEditorArea();
    
    IPlaceholderFolderLayout leftFolder
      = layout.createPlaceholderFolder( "left", IPageLayout.LEFT, 0.2f, editorArea ); //$NON-NLS-1$
    leftFolder.addPlaceholder( eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW );
    leftFolder.addPlaceholder( IPageLayout.ID_RES_NAV );

    IFolderLayout bottomLeftFolder
      = layout.createFolder( "bottomleft", IPageLayout.BOTTOM, 0.5f, editorArea ); //$NON-NLS-1$
    bottomLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW + ":left*" ); //$NON-NLS-1$
    
    IFolderLayout bottomFolder
      = layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.7f, "bottomleft" ); //$NON-NLS-1$ //$NON-NLS-2$
    bottomFolder.addView( IProgressConstants.PROGRESS_VIEW_ID );
    bottomFolder.addView( LogExceptionSolution.LOG_VIEW_ID );
    bottomFolder.addPlaceholder( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );
    
    IFolderLayout bottomRightFolder
      = layout.createFolder( "bottomright", IPageLayout.RIGHT, 0.5f, "bottomleft" ); //$NON-NLS-1$ //$NON-NLS-2$
    bottomRightFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW + ":right*" ); //$NON-NLS-1$
    
    layout.addShowViewShortcut( eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW );
    layout.addShowViewShortcut( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_RES_NAV );
    layout.addShowViewShortcut( IProgressConstants.PROGRESS_VIEW_ID );
    layout.addShowViewShortcut( LogExceptionSolution.LOG_VIEW_ID );
    
    layout.addPerspectiveShortcut( Activator.ID_USER_PERSPECTIVE );
    layout.addPerspectiveShortcut( Activator.ID_OPERATOR_PERSPECTIVE );
    layout.addPerspectiveShortcut( Activator.ID_DEVELOPER_PERSPECTIVE );
    
  }
  
}
