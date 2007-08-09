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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/


package eu.geclipse.jsdl.ui.providers;


/**
 * @author nickl
 */

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;

/**
 * This class provided a Label Provider for Structural Features of a JSDL document.
 *  
 *
 */
public class FeatureLabelProvider extends LabelProvider implements
    ITableLabelProvider {

  public Image getColumnImage(final Object element,final int columnIndex) {
    // Auto-generated method stub
    return null;
  }

  public String getColumnText(final Object element, final int columnIndex) {
    
    String text = null;
    Object[] array = null;
    
      
    if ( element instanceof ArgumentType ) {
      
      ArgumentType argumentType = ( ArgumentType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = argumentType.getFilesystemName();
          break;
        case 1:
          text = argumentType.getValue();
          break;
          default:
          break;
      }
      
    } 
    else if (element instanceof EnvironmentType) {
      
      EnvironmentType environmentType = ( EnvironmentType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = environmentType.getName();
          break;
        case 1:
          text = environmentType.getFilesystemName();
          break;
        case 2:
          text = environmentType.getValue();
          break;
          default:
          break;
          
      } // end switch
      
    } // end else_if EnvironmentType
    
    else if (element instanceof Object[]) {
      
      array = ( Object[] ) element;
      
    }
    
    else {
       
      switch ( columnIndex ) {
        case 0:
          text = element.toString();
          break;        
          default:
          break;
      } // end switch
      
    }
    
    if ( ( array != null ) && ( columnIndex < array.length ) ) {
      text = getText( array[ columnIndex ] );
    }
    return text;
    
  }// End String getColumnText()

} // End class
