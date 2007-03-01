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

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * Since there was a divergence between eclipse code and eclipse documentation
 * (see bugzilla bug #168226). We had to create our own comparator.
 * This class will be deprecated when that bug become FIXED.
 * 
 * @author Mateusz Pabiœ
 *
 */
public class GExplorerComparator extends ViewerComparator {
  /**
   * Describes type of element as an unknown type.
   */
  public static int TYPE_UNKOWN = 10000;
  
  /**
   * Describes type of element as a directory.
   */
  public static int TYPE_DIR = 10001;
  
  /**
   * Describes type of element as a file.
   */
  public static int TYPE_FILE = 10002;

  @Override
  public int category( final Object element )
  {
    int result = TYPE_UNKOWN;
    if( element != null ) {
      if( element instanceof ResourceNode ) {
        Object value = ( ( ResourceNode )element ).getValue();
        if( value instanceof IFileStore ) {
          IFileStore gf = ( IFileStore )value;
          if( gf.fetchInfo().isDirectory() ) {
            result = TYPE_DIR;
          }
          if( !gf.fetchInfo().isDirectory() ) {
            result = TYPE_FILE;
          }
        }
      }
    }
    return result;
  }

  @Override
  public int compare( final Viewer viewer,
                      final Object elementLeft,
                      final Object elementRight )
  {
    int result = 0;
    if( elementLeft != null
        && elementRight != null
        && elementLeft instanceof ResourceNode
        && elementRight instanceof ResourceNode )
    {
      result = category( elementLeft ) - category( elementRight );
      if( result == 0 ) {
        ResourceNode nodeLeft = ( ResourceNode )elementLeft;
        ResourceNode nodeRight = ( ResourceNode )elementRight;
        result = ( nodeLeft.toString().compareToIgnoreCase( nodeRight.toString() ) );
      }
    }
    return result;
  }
}
