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

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleTermModel is part of the simplistic model for SLAs. The SimpleTermModel
 * holds all Terms specified for a SLA.
 * 
 * @author korn
 */
public class SimpleTermModel {

  private String serviceType;
  private List<SimpleTerm> termList;

  /**
   * 
   */
  public SimpleTermModel() {
    this.serviceType = null;
    this.termList = new ArrayList<SimpleTerm>();
  }

  /**
   * @param serviceTypeIn
   */
  public SimpleTermModel( final String serviceTypeIn ) {
    this.setServiceType( serviceTypeIn );
    this.termList = new ArrayList<SimpleTerm>();
  }

  /**
   * set service type
   * 
   * @param serviceTypeIn
   */
  public void setServiceType( final String serviceTypeIn ) {
    this.serviceType = serviceTypeIn;
  }

  /**
   * get service type
   * 
   * @return serviceType
   */
  public String getServiceType() {
    return this.serviceType;
  }

  /**
   * add a Term description
   * 
   * @param inTerm
   */
  public void addTerm( final SimpleTerm inTerm ) {
    this.termList.add( inTerm );
  }

  /**
   * returns a term descriptions
   * 
   * @param name
   * @return SimpleTerm
   */
  public SimpleTerm getTerm( final String name ) {
    SimpleTerm temp = null;
    for( int i = 0; i < this.termList.size(); i++ ) {
      temp = this.termList.get( i );
      if( temp.getName().equalsIgnoreCase( name ) ) {
        break;
      }
    }
    return temp;
  }

  /**
   * removes a term description from the model
   * 
   * @param name
   */
  public void removeTerm( final String name ) {
    SimpleTerm temp = null;
    for( int i = 0; i < this.termList.size(); i++ ) {
      temp = this.termList.get( i );
      if( temp.getName().equalsIgnoreCase( name ) ) {
        this.termList.remove( i );
        break;
      }
    }
  }

  /**
   * returns the number of terms in the model
   * 
   * @return nbTerms
   */
  public int getNbTerms() {
    return this.termList.size();
  }

  /**
   * returns all terms as an Array
   * 
   * @return termArray
   */
  public Object[] getArray() {
    return this.termList.toArray();
  }

  /**
   * The main method was just used for testing
   * 
   * @param args
   */
  @SuppressWarnings("nls")
  public static void main( final String[] args ) {
    SimpleTermModel model = new SimpleTermModel();
    SimpleTerm term1 = new SimpleTerm( "bandwidth-1",
                                       "Bandwidth",
                                       "",
                                       "",
                                       "",
                                       "1000",
                                       "<=" );
    model.addTerm( term1 );
    SimpleTerm term2 = new SimpleTerm( "bandwidth-2",
                                       "Bandwidth",
                                       "",
                                       "",
                                       "",
                                       "1000",
                                       "<=" );
    model.addTerm( term2 );
    SimpleTerm term3 = new SimpleTerm( "performance-1",
                                       "SPECINT",
                                       "",
                                       "300",
                                       "<=",
                                       "1000",
                                       "<=" );
    model.addTerm( term3 );
    SimpleTerm term4 = new SimpleTerm( "latency-1",
                                       "latency",
                                       "",
                                       "200",
                                       "<",
                                       "1000",
                                       "<=" );
    model.addTerm( term4 );
    SimpleTerm result = model.getTerm( "performance-1" );
    System.out.println (result.getText() );
    model.removeTerm( "performance-1" ); 
    model.addTerm( result );
    System.out.println (result.getText() );
  }
}
