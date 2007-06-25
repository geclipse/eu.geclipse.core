/******************************************************************************
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     UCY (http://www.ucy.cs.ac.cy)
  *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
  *            
  *****************************************************************************/

package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nickl
 */

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


/**
 * This class provides a ContentProvider for Structural Features of a JSDL 
 * document.
 *
 */
public class FeatureContentProvider implements IStructuredContentProvider {

  @SuppressWarnings("unchecked")
  public Object[] getElements(Object inputElement) {
    Object[] result = null;

      EList<Object>list = (EList) inputElement;
      result = list.toArray( new Object[ list.size() ] );
    
    return result;
  }

  public void dispose() {
    // Auto-generated method stub

  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // Auto-generated method stub

  }

}
