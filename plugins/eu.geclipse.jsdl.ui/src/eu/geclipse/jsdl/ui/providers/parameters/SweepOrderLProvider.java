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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.LabelProvider;

import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepType;

/**
 * Label provider for tree table presenting order of sweeping of parameters.
 */
public class SweepOrderLProvider extends LabelProvider {

  @SuppressWarnings("unchecked")
  @Override
  public String getText( final Object element ) {
    String result = ""; //$NON-NLS-1$
    if( element instanceof SweepType ) {
      SweepType sweep = ( SweepType )element;
      EList list = sweep.getAssignment();
      for( int i = 0; i < list.size(); i++ ) {
        Object el = list.get( i );
        if( el instanceof AssignmentType ) {
          AssignmentType assignment = ( AssignmentType )el;
          EList paramList = assignment.getParameter();
          for( int j = 0; j < paramList.size(); j++ ) {
            String name = ( String )paramList.get( j );
            int index = name.lastIndexOf( ":" ); //$NON-NLS-1$
            if( index != -1 ) {
              name = name.substring( index + 1, name.length() );
            }
            result = result + ", " + name; //$NON-NLS-1$
          }
        }
      }
    }
    if( result.startsWith( "," ) ) { //$NON-NLS-1$
      result = result.substring( 2 );
    }
    return result;
  }
}
