/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
package eu.geclipse.jsdl.ui.providers.parameters;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.jsdl.model.sweep.SweepType;

public class SweepOrderCProvider implements ITreeContentProvider {

  public Object[] getChildren( final Object parentElement ) {
    Object[] result = null;
    if( parentElement instanceof SweepType ) {
      SweepType sweep = ( SweepType )parentElement;
      if( sweep.getSweep() != null && sweep.getSweep().size() != 0 ) {
        result = new Object[ sweep.getAssignment().size() ];
        result = sweep.getSweep().toArray( result );
      }
    }
    return result;
  }

  public Object getParent( final Object element ) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean hasChildren( final Object element ) {
    boolean result = false;
    if( element instanceof SweepType ) {
      SweepType sweep = ( SweepType )element;
      if( sweep.getSweep() != null && sweep.getSweep().size() != 0 ) {
        result = true;
      }
    }
    return result;
  }

  public Object[] getElements( final Object inputElement ) {
    Object[] result = null;
    if( inputElement instanceof SweepType[] ) {
      
      result = new Object[((Object[])inputElement).length];
      result = ( Object[] )inputElement;
    } else if (inputElement instanceof SweepType){
      SweepType sweep = (SweepType) inputElement;
      result = new Object[1];
      result[0] = sweep;
//      ??
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
    viewer.refresh();
  }
}
