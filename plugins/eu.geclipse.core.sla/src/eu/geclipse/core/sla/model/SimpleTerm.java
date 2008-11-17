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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.model;

/**
 * Simple implementation of term definition to demonstrate the principle use
 * case of the SLA Query
 * 
 * @author korn
 */
public class SimpleTerm {

  private String name;
  private String parameterName;
  private String valueType;
  private String minValue;
  private String minCriteria;
  private String maxValue;
  private String maxCriteria;

  /**
   * standard constructor
   * 
   * @param inName
   * @param inParameterName
   * @param inValueType
   * @param minValue
   * @param minCriteria
   * @param maxValue
   * @param maxCriteria
   */
  public SimpleTerm( final String inName,
                     final String inParameterName,
                     final String inValueType,
                     final String minValue,
                     final String minCriteria,
                     final String maxValue,
                     final String maxCriteria )
  {
    this.setName( inName );
    this.setParameterName( inParameterName );
    this.setValueType( inValueType );
    this.setMinValue( minValue );
    this.setMinCriteria( minCriteria );
    this.setMaxCriteria( maxCriteria );
    this.setMaxValue( maxValue );
  }

  /**
   * return the Term name
   * 
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * set the Name of the Term
   * 
   * @param name
   */
  public void setName( final String name ) {
    this.name = name;
  }

  /**
   * return the term parameter name
   * 
   * @return parameterName
   */
  public String getParameterName() {
    return this.parameterName;
  }

  /**
   * set the term parameter name
   * 
   * @param parameterName
   */
  public void setParameterName( final String parameterName ) {
    this.parameterName = parameterName;
  }

  /**
   * return the upper bound value
   * 
   * @return maxValue
   */
  public String getMaxValue() {
    return this.maxValue;
  }

  /**
   * set the upper bound value
   * 
   * @param maxValue
   */
  public void setMaxValue( final String maxValue ) {
    this.maxValue = maxValue;
  }

  /**
   * get the upper bound criteria
   * 
   * @return maxCriteria
   */
  public String getMaxCriteria() {
    return this.maxCriteria;
  }

  /**
   * set the upper bound criteria
   * 
   * @param maxCriteria
   */
  public void setMaxCriteria( final String maxCriteria ) {
    this.maxCriteria = maxCriteria;
  }

  /**
   * return the lower bound criteria for the term
   * 
   * @return minCriteria
   */
  public String getMinCriteria() {
    return this.minCriteria;
  }

  /**
   * set the lower bound criteria
   * 
   * @param minCriteria
   */
  public void setMinCriteria( final String minCriteria ) {
    this.minCriteria = minCriteria;
  }

  /**
   * return the lower bound value
   * 
   * @return minValue
   */
  public String getMinValue() {
    return this.minValue;
  }

  /**
   * set the lower bound value
   * 
   * @param value
   */
  public void setMinValue( final String value ) {
    this.minValue = value;
  }

  /**
   * return the whole term string
   * 
   * @return termAsText
   */
  public String getText() {
    String result = this.getParameterName() + ": \n ( " //$NON-NLS-1$
                    + this.getMinValue()
                    + " " //$NON-NLS-1$
                    + this.getMinCriteria()
                    + "  Value  " //$NON-NLS-1$
                    + this.getMaxCriteria()
                    + " " //$NON-NLS-1$
                    + this.getMaxValue()
                    + " )"; //$NON-NLS-1$
    return result;
  }

  /**
   * sets the type of the term i.e. float, integer, ...
   * 
   * @param text
   */
  public void setValueType( final String text ) {
    this.valueType = text;
  }

  /**
   * returns the type of the Term
   * 
   * @return valueType ;
   */
  public String getValueType() {
    return this.valueType;
  }
}
