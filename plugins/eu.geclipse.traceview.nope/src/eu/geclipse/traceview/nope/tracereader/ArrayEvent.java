/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.nope.tracereader;

import java.util.Vector;

/**
 * Array Event
 */
public class ArrayEvent extends Event {

  private int size;
  private int noe;
  private int elemType;
  private int elemSize;
  private int numDims;
  private int numPADims;
  private Vector<Integer> arrSize;
  private Vector<Integer> paSize;
  private Vector<Integer> dist;
  
  /**
   * A nope array event
   * 
   * @param logicalClock
   * @param processCache
   */
  public ArrayEvent( final int logicalClock, final Process processCache ) {
    super(logicalClock, processCache);
  }
  /**
   * @return size
   */
  public int getSize() {
    return this.size;
  }

  /**
   * @param size
   */
  public void setSize( final int size ) {
    this.size = size;
  }

  /**
   * @return noe
   */
  public int getNoe() {
    return this.noe;
  }

  /**
   * @param noe
   */
  public void setNoe( final int noe ) {
    this.noe = noe;
  }

  /**
   * @return elemType
   */
  public int getElemType() {
    return this.elemType;
  }

  /**
   * @param elemType
   */
  public void setElemType( final int elemType ) {
    this.elemType = elemType;
  }

  /**
   * @return elemSize
   */
  public int getElemSize() {
    return this.elemSize;
  }

  /**
   * @param elemSize
   */
  public void setElemSize( final int elemSize ) {
    this.elemSize = elemSize;
  }

  /**
   * @return numDims
   */
  public int getNumDims() {
    return this.numDims;
  }

  /**
   * @param numDims
   */
  public void setNumDims( final int numDims ) {
    this.numDims = numDims;
  }

  /**
   * @return numPADims
   */
  public int getNumPADims() {
    return this.numPADims;
  }

  /**
   * @param numPADims
   */
  public void setNumPADims( final int numPADims ) {
    this.numPADims = numPADims;
  }

  /**
   * @return arrSize
   */
  public Vector<Integer> getArrSize() {
    return this.arrSize;
  }

  /**
   * @param arrSize
   */
  public void setArrSize( final Vector<Integer> arrSize ) {
    this.arrSize = arrSize;
  }

  /**
   * @return paSize
   */
  public Vector<Integer> getPaSize() {
    return this.paSize;
  }

  /**
   * @param paSize
   */
  public void setPaSize( final Vector<Integer> paSize ) {
    this.paSize = paSize;
  }

  /**
   * @return dist
   */
  public Vector<Integer> getDist() {
    return this.dist;
  }

  /**
   * @param dist
   */
  public void setDist( final Vector<Integer> dist ) {
    this.dist = dist;
  }
}
