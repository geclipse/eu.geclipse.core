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
 *    Mateusz Pabis (PSNC) - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.views.gexplorer;

// FIXME: This class is never used!!!
// It should be removed since gExplorer relays on default 
// <code>TreeContentProvider</code> and GridProjectView on it's own
// architecture.
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Wrapper class for <code>ITreeContentProvider</code>
 * It's nowhere used and should be removed from repository soon.
 *
 * There is no functionality at all, right now.
 *
 * @author Mateusz Pabiœ
 *
 */
public class GExplorerContentProvider implements ITreeContentProvider {

  public Object[] getChildren( final Object parentElement ) {
    ResourceNode node = ( ResourceNode )parentElement;
    return node.getChildren();
  }

  public Object getParent( final Object element ) {
    ResourceNode node = ( ResourceNode )element;
    return node.getParent();
  }

  public boolean hasChildren( final Object element ) {
    ResourceNode node = ( ResourceNode )element;
    return node.hasChildren();
  }

  public Object[] getElements( final Object inputElement ) {
    ResourceNode node = ( ResourceNode )inputElement;
    return node.getChildren();
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput ) {
    // TODO Auto-generated method stub
  }
}
