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
 *   nloulloud
 *****************************************************************************/
package eu.geclipse.info.ui;

import org.eclipse.jface.viewers.IElementComparer;

import eu.geclipse.info.ui.GlueInfoViewer.TreeParent;

/**
 * This class is used to sort the elements in the GlueInfoView.
 * The TreeParent obhects are shown first as the considered to 
 * be folders  
 * @author tnikos
 *
 */
public class GlueInfoComparator implements IElementComparer
{

  public boolean equals( final Object a, final Object b ) {
    
    boolean result = false;
    
    if (a != null && a instanceof TreeParent 
        && b!= null && b instanceof TreeParent 
        && ((TreeParent)a).getName().equals( ((TreeParent )b).getName()))
      result = true;
    else if (a != null)
      result =  a.equals( b );
    
    return result;
  }

  public int hashCode( final Object element ) {
    
    return 0;
  }
  
}