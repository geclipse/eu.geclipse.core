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
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.ui.internal.Activator;

/**
 * Action for {@link TreeViewer}s that causes a collapse all.
 */
public class CollapseAllAction extends Action {
  
  /**
   * The viewer that is the target of this action.
   */
  TreeViewer viewer;
  
  /**
   * Construct a new collapse all action for the specified tree viewer.
   * 
   * @param viewer The tree viewer that is the target of this action. 
   */
  public CollapseAllAction( final TreeViewer viewer ) {
    super( Messages.getString("CollapseAllAction.collapse_all_action_text") ); //$NON-NLS-1$
    this.viewer = viewer;
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/elcl16/collapseall.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Display display = this.viewer.getControl().getDisplay();
    display.asyncExec( new Runnable() {
      public void run() {
        CollapseAllAction.this.viewer.collapseAll();
      }
    } );
  }
  
}
