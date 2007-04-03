/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * Temporary perspective for the g-Eclipse project. It can contain several views
 * that are contributed with the
 * <code>org.eclipse.ui.perspectiveExtensions</code> extension point.
 */
public class UserPerspectiveFactory
    implements IPerspectiveFactory {

  /*private static final String GECLIPSE_PERSPECTIVE_ID 
    =   Activator.getDefault().getBundle().getSymbolicName()
      + ".gEclipsePerspective"; //$NON-NLS-1$*/
  
  public UserPerspectiveFactory() {
    super();
  }

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
    
    IFolderLayout bottomFolder
      = layout.createFolder( "bottom", IPageLayout.BOTTOM, 0.75f, editorArea ); //$NON-NLS-1$
    bottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_CONNECTION_VIEW );
    bottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_AUTH_VIEW );
    bottomFolder.addView( eu.geclipse.ui.internal.Activator.ID_WEB_VIEW );
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
