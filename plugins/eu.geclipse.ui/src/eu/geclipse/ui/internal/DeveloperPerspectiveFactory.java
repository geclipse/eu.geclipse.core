/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Mathias Stuempert - initial implementation
 *******************************************************************************/

package eu.geclipse.ui.internal;

import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.progress.IProgressConstants;

public class DeveloperPerspectiveFactory implements IPerspectiveFactory {
  
public void createInitialLayout( final IPageLayout layout ) {
    
    String editorArea = layout.getEditorArea();

    IFolderLayout leftFolder
      = layout.createFolder( "left", IPageLayout.LEFT, 0.25f, editorArea ); //$NON-NLS-1$
    leftFolder.addView( eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW );
    leftFolder.addPlaceholder( IPageLayout.ID_RES_NAV );
    
    IFolderLayout bottomFolder
      = layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.75f, editorArea ); //$NON-NLS-1$
    bottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW );
    bottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );
    bottomFolder.addPlaceholder( IPageLayout.ID_PROBLEM_VIEW );
    bottomFolder.addPlaceholder( NewSearchUI.SEARCH_VIEW_ID );
    bottomFolder.addPlaceholder( IPageLayout.ID_BOOKMARKS );
    bottomFolder.addPlaceholder( IProgressConstants.PROGRESS_VIEW_ID );
    
    layout.addShowViewShortcut( IPageLayout.ID_PROBLEM_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_RES_NAV );
    layout.addShowViewShortcut( IPageLayout.ID_TASK_LIST );
    layout.addShowViewShortcut( IProgressConstants.PROGRESS_VIEW_ID );

    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_PROJECT_WIZARD );
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_JOB_WIZARD );
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_CONNECTION_WIZARD );
    layout.addNewWizardShortcut( "org.eclipse.ui.wizards.new.folder" );//$NON-NLS-1$
    layout.addNewWizardShortcut( "org.eclipse.ui.wizards.new.file" );//$NON-NLS-1$
    layout.addNewWizardShortcut( "org.eclipse.ui.editors.wizards.UntitledTextFileWizard" );//$NON-NLS-1$
    
  }

}
