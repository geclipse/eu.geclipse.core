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
 *      - Szymon Mueller    
 *****************************************************************************/
package eu.geclipse.core.model;

import java.util.List;


public interface IGridTestManager extends IGridElementManager {
  
  public List<IGridTest> getTests();
//  
//  public void addStrTest( final IGridTest test );
//  
  public List< IGridTest > getAvaliableTests( final Object resource );
  
  //TODO usunac
  public void addTest( IGridTest test );
//  
  public IGridTest getTest( final String name );
//  
//  public IGridTest getSimpleTest( final String name, final String parentTestName );
}
