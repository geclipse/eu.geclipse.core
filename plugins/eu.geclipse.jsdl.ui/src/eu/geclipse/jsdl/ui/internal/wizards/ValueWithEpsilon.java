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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.wizards;


/**
 * Numeric value with +/- epsilon (to represent exact numeric values for JSDL parameters)
 */
public class ValueWithEpsilon {
  
  double value;
  double epsilon;
  
  
  /**
   * Method to create new instance of the class
   * @param value exact value
   * @param epsilon epsilon value for the exact value
   */
  public ValueWithEpsilon( final double value, final double epsilon ) {
   this.value = value;
   this.epsilon = epsilon;
  }

  
  /**
   * Method to access epsilon value of this numeric value
   * @return epsilon value
   */
  public double getEpsilon() {
    return this.epsilon;
  }

  
  /**
   * Method to access exact value of this numeric value
   * @return exact value of this numeric value
   */
  public double getValue() {
    return this.value;
  }


  
  /**
   * Method to set epsilon value for exact value of this numeric value
   * @param epsilon epsilon to be set
   */
  public void setEpsilon( final double epsilon ) {
    this.epsilon = epsilon;
  }


  
  /**
   * Method to set exact value of this numeric value
   * @param value exact value to be set
   */
  public void setValue( final double value ) {
    this.value = value;
  }


  @Override
  public boolean equals( final Object obj )
  {
    boolean result = false;
    if (super.equals( obj )){
      result = true;
    } else {
      if (obj instanceof ValueWithEpsilon){
        ValueWithEpsilon val = (ValueWithEpsilon) obj;
        if ( this.value == val.getValue() && this.epsilon == val.getEpsilon() ) {
          result = true;
        }
      }
    }
    return result;
  }
  
  
  
}
