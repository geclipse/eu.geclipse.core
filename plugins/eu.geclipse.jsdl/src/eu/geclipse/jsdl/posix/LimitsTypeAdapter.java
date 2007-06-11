/******************************************************************************
  * Copyright (c) 2007 g-Eclipse consortium
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
package eu.geclipse.jsdl.posix;

import java.util.Hashtable;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;


/**
 * @author nickl
 *
 */
public class LimitsTypeAdapter {
  
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  private POSIXApplicationType parent = null;  

  /*
   * Class Constructor.
   */
  public LimitsTypeAdapter (final POSIXApplicationType lParent ){
    this.parent = lParent;
    getLimitsType(this.parent);
  }
  
  
  private void getLimitsType (final POSIXApplicationType posixParent){
  
    TreeIterator iterator = posixParent.eAllContents();
    
    while (iterator.hasNext()){
      
      EObject testType = (EObject) iterator.next();
      
      System.out.println(testType.eContainingFeature().toString());
      
      
      
    }
      
    
  }
  
}
