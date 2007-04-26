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
package eu.geclipse.ui.internal.wizards.jobs;


/**
 * Numbers range (to represent ranges of numeric values for JSDL parameters)
 */
public class Range{
  private double start;
  private double end;
  
   /**
   * Creates instnace of this class
   * @param start start of the range
   * @param end end of the rnage
   * @throws IllegalArgumentException when start is after end
   */
  public Range(final double start, final double end)throws IllegalArgumentException{
     if (start >= end){
       throw new IllegalArgumentException();
     }
    this.start = start;
    this.end = end;
  }

  
  /**
   * Method to access value of the end of range
   * @return end value
   */
  public double getEnd() {
    return this.end;
  }

  
  /**
   * Method to set value of end of the range
   * @param end value to be set as an end of the range
   */
  public void setEnd( final double end ) {
    this.end = end;
  }

  
  /**
   * Method to access value of the start of the range
   * @return start of the range
   */
  public double getStart() {
    return this.start;
  }

  
  /**
   * Method to set value of the start of the range
   * @param start value to be set as a start of the range
   */
  public void setStart( final double start ) {
    this.start = start;
  }


  @Override
  public boolean equals( final Object obj )
  {
    boolean result = false;
    if (super.equals( obj )){
      result = true;
    } else {
      if (obj instanceof Range){
        Range rangeToCompare = (Range) obj;
        if ( this.start == rangeToCompare.getStart() && this.end == rangeToCompare.getEnd()){
          result = true;
        }
      }
    }
    return result;
  }
  
  
  
}
