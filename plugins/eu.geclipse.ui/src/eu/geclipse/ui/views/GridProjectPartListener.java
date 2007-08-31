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

package eu.geclipse.ui.views;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import eu.geclipse.ui.internal.actions.EditorActions;

public class GridProjectPartListener implements IPartListener2 {
  
  private EditorActions editorActions;
  
  protected GridProjectPartListener( final EditorActions editorActions ) {
    this.editorActions = editorActions;
  }

  public void partActivated( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) instanceof IEditorPart ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }

  public void partBroughtToTop( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) == this.editorActions.getView() ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }

  public void partClosed( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partDeactivated( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partHidden( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partInputChanged( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partOpened( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) == this.editorActions.getView() ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }

  public void partVisible( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) == this.editorActions.getView() ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }
  
}
