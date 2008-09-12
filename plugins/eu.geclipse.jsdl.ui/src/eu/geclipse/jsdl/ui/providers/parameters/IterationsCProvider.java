/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Katarzyna Bylec - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl.ui.providers.parameters;

import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class IterationsCProvider implements IStructuredContentProvider {

  public Object[] getElements( final Object inputElement ) {
    Properties[][] result = null;
    if( inputElement instanceof Map ) {
      Map<String, Properties> map = ( Map<String, Properties> )inputElement;
      result = new Properties[ map.keySet().size() ][];
      int i = 0;
      for( String iterationId : map.keySet() ) {
        result[ i ] = new Properties[ map.get( iterationId ).size() + 1 ];
        result[ i ][ 0 ] = new Properties();
        result[ i ][ 0 ].put( "Iteration", iterationId );
        int j = 1;
        for( Object paramName : map.get( iterationId ).keySet() ) {
          result[ i ][ j ] = new Properties();
          result[ i ][ j ].put( paramName, map.get( iterationId )
            .getProperty( (String)paramName ) );
          j++;
        }
        i++;
      }
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
    // empty implementation
  }
}
