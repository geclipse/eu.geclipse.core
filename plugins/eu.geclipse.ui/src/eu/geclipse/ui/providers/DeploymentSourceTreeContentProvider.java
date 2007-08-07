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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.deployment.DeploymentWizard;

/**
 * @author Yifan Zhou
 */
public class DeploymentSourceTreeContentProvider implements ITreeContentProvider {

  public Object[] getChildren( final Object parentElement ) {
    IGridElement[] children = null;
    try {
      if( parentElement instanceof IGridContainer ) {
        children = ( ( IGridContainer ) parentElement ).getChildren( null );
      }
    } catch( GridModelException e ) {
      Activator.logException( e );
    }
    return ( children == null ) ? new IGridElement[0] : children;
  }

  public Object getParent( final Object element ) {
    Object parent = null;
    if ( element instanceof IGridElement ) {
      parent = ( ( IGridElement ) element ).getParent();
    }
    return parent;
  }

  public boolean hasChildren( final Object element ) {
    return ( element instanceof IGridContainer ) ? this.getChildren( element ).length > 0 : false;
  }

  public Object[] getElements( final Object inputElement ) {
    return ( ( DeploymentWizard ) inputElement ).getReferencedProjects();
  }

  public void dispose() {
    // empty implementation
  }

  public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput ) {
    // empty implementation
  }
  
}
