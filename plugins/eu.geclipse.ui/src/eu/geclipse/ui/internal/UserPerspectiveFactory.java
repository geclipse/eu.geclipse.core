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
 *    Markus Knauer - initial implementation
 *    Mathias Stuempert - further development
 *******************************************************************************/

package eu.geclipse.ui.internal;

import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.progress.IProgressConstants;


/**
 * Perspective factory for the g-Eclipse user perspective. 
 */
public class UserPerspectiveFactory
    implements IPerspectiveFactory {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
   */
  public void createInitialLayout( final IPageLayout layout ) {
    
    String editorArea = layout.getEditorArea();

    IFolderLayout topLeftFolder
      = layout.createFolder( "topleft", IPageLayout.LEFT, 0.25f, editorArea ); //$NON-NLS-1$
    topLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW );
    topLeftFolder.addPlaceholder( IPageLayout.ID_RES_NAV );
    
    IFolderLayout bottomLeftFolder
      = layout.createFolder( "bottomleft", IPageLayout.BOTTOM, 0.5f, eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW ); //$NON-NLS-1$
    bottomLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_GLUE_INFO_VIEW );
    
    IFolderLayout leftBottomFolder
      = layout.createFolder( "leftbottom", IPageLayout.BOTTOM, 0.75f, editorArea ); //$NON-NLS-1$
    leftBottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW );
    leftBottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_JOBS_VIEW );
    leftBottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );
    leftBottomFolder.addView( IPageLayout.ID_PROP_SHEET);
    leftBottomFolder.addPlaceholder( eu.geclipse.ui.internal.Activator.ID_WEB_VIEW );
    leftBottomFolder.addPlaceholder( IPageLayout.ID_PROBLEM_VIEW );
    leftBottomFolder.addPlaceholder( NewSearchUI.SEARCH_VIEW_ID );
    leftBottomFolder.addPlaceholder( IPageLayout.ID_BOOKMARKS );
    leftBottomFolder.addPlaceholder( IProgressConstants.PROGRESS_VIEW_ID );

    IFolderLayout middleFolder
    = layout.createFolder( "middle", IPageLayout.BOTTOM, 0.5f, eu.geclipse.ui.internal.Activator.ID_JOBS_VIEW ); //$NON-NLS-1$
    middleFolder.addPlaceholder( eu.geclipse.ui.internal.Activator.ID_JOBDETAILS_VIEW );

//    IFolderLayout rightFolder
//    = layout.createFolder( "right", IPageLayout.RIGHT, 0.8f, editorArea ); //$NON-NLS-1$
    
    
    layout.addShowViewShortcut( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );
    layout.addShowViewShortcut( eu.geclipse.ui.internal.Activator.ID_JOBDETAILS_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_PROBLEM_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_PROP_SHEET );
    layout.addShowViewShortcut( IPageLayout.ID_RES_NAV );
    layout.addShowViewShortcut( IPageLayout.ID_TASK_LIST );
    layout.addShowViewShortcut( IProgressConstants.PROGRESS_VIEW_ID );

    layout.addNewWizardShortcut( "org.eclipse.ui.wizards.new.folder" );//$NON-NLS-1$
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_PROJECT_WIZARD );
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_CONNECTION_WIZARD );
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_WORKFLOW_WIZARD );
//    layout.addNewWizardShortcut( "org.eclipse.ui.wizards.new.file" );//$NON-NLS-1$
//    layout.addNewWizardShortcut( "org.eclipse.ui.editors.wizards.UntitledTextFileWizard" );//$NON-NLS-1$
    
    layout.addPerspectiveShortcut( Activator.ID_OPERATOR_PERSPECTIVE );
    layout.addPerspectiveShortcut( Activator.ID_DEVELOPER_PERSPECTIVE );
    layout.addPerspectiveShortcut( Activator.ID_EXPLORER_PERSPECTIVE );
    
  }

}
