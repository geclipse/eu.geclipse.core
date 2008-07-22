/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.core.model.IServiceJob;

/**
 * Content provider for Operator's Job details view.
 */
public class DetailsContentProvider implements ITreeContentProvider {

  private IServiceJob root;

  /**
   * Simple constructor.
   * 
   * @param root Operator's Job for which details will be provider by this
   *            content provider.
   */
  public DetailsContentProvider( final IServiceJob root ) {
    this.root = root;
  }

  public Object[] getChildren( final Object parentElement ) {
    Object[] result = null;
    if( parentElement instanceof IServiceJob ) {
      result = ( ( IServiceJob )parentElement ).getTestedResourcesNames()
        .toArray();
    } else if( parentElement instanceof String ) {
      result = this.root.getTestResultsForResourceForDate( ( String )parentElement )
        .toArray();
    }
    return result;
  }

  public Object getParent( final Object element ) {
    return this.root;
  }

  public boolean hasChildren( final Object element ) {
    boolean result = false;
    if( element instanceof IServiceJob || element instanceof String ) {
      result = true;
    }
    return result;
  }

  public Object[] getElements( final Object inputElement ) {
    Object[] result = null;
    if( inputElement instanceof IServiceJob ) {
      result = ( ( IServiceJob )inputElement ).getTestedResourcesNames()
        .toArray();
    }
    return result;
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput )
  {
    this.root = ( IServiceJob )newInput;
  }

  /**
   * Changes source of details which are provider by this content provider.
   * @param newInput  Operator's Job for which details will be provider by this
   *            content provider.
   */
  public void changeRoot( final IServiceJob newInput ) {
    this.root = newInput;
  }
}
