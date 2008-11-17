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
 * SltContainerModel is part of the simplistic SLA/SLT process model. It holds
 * many SltContainers and manages them.
 * 
 * @author korn
 */
public class SltContainerModel {

  private List<SltContainer> sltListe;

  /**
   * 
   */
  public SltContainerModel() {
    this.sltListe = new ArrayList<SltContainer>();
  }

  /**
   * adds a SLTContainer to the SLT model
   * 
   * @param slt
   */
  public void addSlt( final SltContainer slt ) {
    this.sltListe.add( slt );
  }

  /**
   * clears the SltContainerModel
   */
  public void clear() {
    this.sltListe.clear();
  }

  /**
   * gets a SltContainer from its name
   * 
   * @param name
   * @return sltContainer
   */
  public SltContainer getSlt( final String name ) {
    SltContainer temp = null;
    for( int i = 0; i < this.sltListe.size(); i++ ) {
      temp = this.sltListe.get( i );
      if( temp.getName().equalsIgnoreCase( name ) ) {
        break;
      }
    }
    return temp;
  }

  /**
   * gets the number of SltContainers in the model
   * 
   * @return nbSlts
   */
  public int getNbSlt() {
    return this.sltListe.size();
  }

  /**
   * returns the model containers as an Array of Objects
   * 
   * @return sltsArray
   */
  public Object[] getArray() {
    return this.sltListe.toArray();
  }
}
