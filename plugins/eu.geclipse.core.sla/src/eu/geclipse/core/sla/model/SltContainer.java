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
 * SltContainer is part of the simplistic SLT/SLA process model. The class
 * SltContainer holds information about one SLT like nam, content, ranking
 * value, ...
 * 
 * @author korn
 */
public class SltContainer implements Comparable<SltContainer> {

  private String name;
  private String content;
  private String evaluation;
  private double ranking;

  /**
   * @param inName
   * @param inContent
   * @param inEvaluation
   * @param inRanking
   */
  public SltContainer( final String inName,
                       final String inContent,
                       final String inEvaluation,
                       final double inRanking )
  {
    this.setName( inName );
    this.setContent( inContent );
    this.setEvaluation( inEvaluation );
    this.setRanking( inRanking );
  }

  /**
   * return the result of the evaluation of the SLT vs the SLT requirements
   * 
   * @return evaluation
   */
  public String getEvaluation() {
    return this.evaluation;
  }

  /**
   * set the evaluation string
   * 
   * @param evaluation
   */
  protected void setEvaluation( final String evaluation ) {
    this.evaluation = evaluation;
  }

  /**
   * get the ranking result
   * 
   * @return ranking
   */
  public double getRanking() {
    return this.ranking;
  }

  /**
   * set the ranking value
   * 
   * @param ranking
   */
  protected void setRanking( final double ranking ) {
    this.ranking = ranking;
  }

  /**
   * get the name of this SLT
   * 
   * @return sltName
   */
  public String getName() {
    return this.name;
  }

  /**
   * set the name of this SLT
   * 
   * @param name
   */
  protected void setName( final String name ) {
    this.name = name;
  }

  /**
   * get the content (XML)
   * 
   * @return content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * set the content to the XML
   * 
   * @param content
   */
  protected void setContent( final String content ) {
    this.content = content;
  }

  /**
   * part of the comparable interface
   */
  public int compareTo( final SltContainer other ) {
    int value = 0;
    if( this.ranking < other.ranking )
      value = -1;
    if( this.ranking > other.ranking )
      value = 1;
    return value;
  }
}