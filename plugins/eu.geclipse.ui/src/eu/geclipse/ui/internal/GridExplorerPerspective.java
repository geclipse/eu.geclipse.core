/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

    IFolderLayout bottomLeftFolder
      = layout.createFolder( "bottomleft", IPageLayout.BOTTOM, 0.5f, editorArea ); //$NON-NLS-1$
    bottomLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW + ":left*" ); //$NON-NLS-1$
    
    IFolderLayout bottomRightFolder
      = layout.createFolder( "bottomright", IPageLayout.RIGHT, 0.5f, eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW ); //$NON-NLS-1$
    bottomRightFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW + ":right*" ); //$NON-NLS-1$
    
  }
  
}
