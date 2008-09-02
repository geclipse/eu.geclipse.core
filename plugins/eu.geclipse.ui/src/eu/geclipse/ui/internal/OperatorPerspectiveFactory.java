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

import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.progress.IProgressConstants;

/**
 * Perspective factory for the g-Eclipse operator perspective.
 */
public class OperatorPerspectiveFactory implements IPerspectiveFactory {

  public void createInitialLayout( final IPageLayout layout ) {

    String editorArea = layout.getEditorArea();

    IFolderLayout topLeftFolder
     = layout.createFolder( "topleft", IPageLayout.LEFT, 0.2f, editorArea ); //$NON-NLS-1$
    topLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW );
    topLeftFolder.addPlaceholder( IPageLayout.ID_RES_NAV );

    IFolderLayout bottomLeftFolder
     = layout.createFolder( "bottomleft", //$NON-NLS-1$
                            IPageLayout.BOTTOM, 0.5f, 
                            eu.geclipse.ui.internal.Activator.ID_GPROJECT_VIEW ); 
    bottomLeftFolder.addView( eu.geclipse.ui.internal.Activator.ID_GLUE_INFO_VIEW );

    IFolderLayout rightFolder
      = layout.createFolder( "right", IPageLayout.RIGHT,   //$NON-NLS-1$
                             0.5f, 
                             eu.geclipse.ui.internal.Activator.ID_TERMINAL_VIEW );
    rightFolder.addView( eu.geclipse.ui.internal.Activator.ID_TERMINAL_VIEW );
    rightFolder.addView( IPageLayout.ID_OUTLINE );

    IFolderLayout bottomFolder
      = layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.6f, editorArea ); //$NON-NLS-1$
    bottomFolder.addView( IPageLayout.ID_PROP_SHEET );
    // TODO This should have been the following identifier.
    // eu.geclipse.batch.ui.internal.Activator.ID_BATCH_JOB_VIEW
    // Due to batch.ui plugin location have to do this hack
    // Leave as Warning to remember
    bottomFolder.addView( "eu.geclipse.batch.ui.views.BatchJobView" ); //$NON-NLS-1$
    bottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );

    bottomFolder.addPlaceholder( IPageLayout.ID_PROP_SHEET );
    bottomFolder.addPlaceholder( IPageLayout.ID_PROBLEM_VIEW );
    bottomFolder.addPlaceholder( NewSearchUI.SEARCH_VIEW_ID );
    bottomFolder.addPlaceholder( IPageLayout.ID_BOOKMARKS );
    bottomFolder.addPlaceholder( IProgressConstants.PROGRESS_VIEW_ID );

    layout.addShowViewShortcut( "eu.geclipse.batch.ui.views.BatchJobView" ); //$NON-NLS-1$
    layout.addShowViewShortcut( IPageLayout.ID_PROP_SHEET );
    layout.addShowViewShortcut( IPageLayout.ID_PROBLEM_VIEW );
    layout.addShowViewShortcut( IPageLayout.ID_RES_NAV );
    layout.addShowViewShortcut( IPageLayout.ID_TASK_LIST );
    layout.addShowViewShortcut( IProgressConstants.PROGRESS_VIEW_ID );

    // TODO This should have been the following identifier.
    // eu.geclipse.batch.ui.internal.Activator.ID_BATCH_WIZARD
    // Due to batch.ui plugin location have to do this hack
    // Leave as Warning to remember
    layout.addNewWizardShortcut( "eu.geclipse.batch.ui.wizards.BatchCreationWizard" ); //$NON-NLS-1$
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_PROJECT_WIZARD );
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_CONNECTION_WIZARD );
    layout.addNewWizardShortcut( eu.geclipse.ui.internal.Activator.ID_WORKFLOW_WIZARD );
    layout.addNewWizardShortcut( "org.eclipse.ui.wizards.new.folder" );//$NON-NLS-1$
    layout.addNewWizardShortcut( "org.eclipse.ui.wizards.new.file" );//$NON-NLS-1$
    layout.addNewWizardShortcut( "org.eclipse.ui.editors.wizards.UntitledTextFileWizard" );//$NON-NLS-1$
    
    layout.addPerspectiveShortcut( Activator.ID_USER_PERSPECTIVE );
    layout.addPerspectiveShortcut( Activator.ID_DEVELOPER_PERSPECTIVE );
    layout.addPerspectiveShortcut( Activator.ID_EXPLORER_PERSPECTIVE );
    
  }

}
