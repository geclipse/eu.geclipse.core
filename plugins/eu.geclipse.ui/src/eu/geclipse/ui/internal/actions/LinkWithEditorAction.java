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

package eu.geclipse.ui.internal.actions;

import java.net.URL;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.ResourceUtil;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Links an editor with a viewer.
 */
public class LinkWithEditorAction extends Action {

  private GridModelViewPart view;

  protected LinkWithEditorAction( final GridModelViewPart view ) {
    super( Messages.getString("LinkWithEditorAction.link_with_editor_action_text"), IAction.AS_CHECK_BOX ); //$NON-NLS-1$
    this.view = view;
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/elcl16/synced.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  @Override
  public void run() {
    if ( isChecked() ) {
      IWorkbenchPage workbenchPage = this.view.getViewSite().getPage();
      if ( workbenchPage.isPartVisible( this.view ) ) {
        IEditorPart editor = workbenchPage.getActiveEditor();
        if ( editor != null ) {
          IEditorInput input = editor.getEditorInput();
          IFile file = ResourceUtil.getFile(input);
          if (file != null) {
            IGridRoot gridRoot = GridModel.getRoot();
            IGridElement gridElement = gridRoot.findElement( file );
            if ( gridElement != null ) {
              StructuredViewer viewer = this.view.getViewer();
              ISelection selection = new StructuredSelection( gridElement );
              viewer.setSelection( selection );
            }
          }
        }
      }
    }
  }
}
