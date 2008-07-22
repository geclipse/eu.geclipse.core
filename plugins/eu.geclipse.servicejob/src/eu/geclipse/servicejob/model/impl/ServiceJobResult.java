/******************************************************************************
 * Copyright (c) 2007 - 2008 g-Eclipse consortium 
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
package eu.geclipse.servicejob.model.impl;

import java.util.Date;

import eu.geclipse.core.model.IServiceJobResult;

/**
 * Implementation of {@link IServiceJobResult}. Clients should use it as it is.
 * This class can be seen as a simple java bean and in comparison to interface
 * it introduces also simple class constructor.
 */
public class ServiceJobResult implements IServiceJobResult {

  private Date runDate;
  private String resource;
  private String subTest;
  private String resultType;
  private String resutlRawData;
  private String resultSummary;
  private String resultEnum;

  /**
   * Simple constructor.
   * 
   * @param runDate when this result was created
   * @param resource name of tested resource
   * @param subTest name of performed sub-test
   * @param resultRawData content of Output > Result > ResultData GTDL element
   * @param resultSummary textual interpretation of test's result
   * @param resultType type describing kind of detailed job's result - usually a
   *            file extension of a file that can contain such a result
   * @param resultEnum short string describing state of Operator's Job
   */
  public ServiceJobResult( final Date runDate,
                         final String resource,
                         final String subTest,
                         final String resultRawData,
                         final String resultSummary,
                         final String resultType,
                         final String resultEnum )
  {
    this.runDate = runDate;
    this.resource = resource;
    this.subTest = subTest;
    this.resutlRawData = resultRawData;
    this.resultSummary = resultSummary;
    this.resultType = resultType;
    this.resultEnum = resultEnum;
  }

  public String getResourceName() {
    return this.resource;
  }

  public String getResultRawData() {
    return this.resutlRawData;
  }

  public String getResultSummary() {
    return this.resultSummary;
  }

  public String getResultType() {
    return this.resultType;
  }

  public Date getRunDate() {
    return this.runDate;
  }

  public String getSubTestName() {
    return this.subTest;
  }

  public String getResultEnum() {
    return this.resultEnum;
  }

  /**
   * Simple setter.
   * 
   * @param runDate date of job run
   */
  public void setRunDate( final Date runDate ) {
    this.runDate = runDate;
  }

  /**
   * Simple setter.
   * 
   * @param resultSummary
   */
  public void setResultSummary( final String resultSummary ) {
    this.resultSummary = resultSummary;
  }

  /**
   * Simple setter.
   * 
   * @param resultType
   */
  public void setResultType( final String resultType ) {
    this.resultType = resultType;
  }

  /**
   * Simple setter.
   * 
   * @param resultEnum
   */
  public void setResultEnum( final String resultEnum ) {
    this.resultEnum = resultEnum;
  }

  /**
   * Simple getter.
   * 
   * @return raw data of test (the same as written in "input" node of GTDL file)
   */
  public String getResutlRawData() {
    return this.resutlRawData;
  }

  /**
   * Simple setter.
   * 
   * @param resutlRawData
   */
  public void setResutlRawData( final String resutlRawData ) {
    this.resutlRawData = resutlRawData;
  }
}
