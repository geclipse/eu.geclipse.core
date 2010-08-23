/*****************************************************************************
 * Copyright (c) 2010 g-Eclipse Consortium 
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
 *    Christof Klausecker MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf.util;

import java.util.HashMap;
import java.util.Map;

import eu.geclipse.eventgraph.tracereader.otf.OTFDefinitionReader;

/**
 * Call Graph Node
 */
public class Node {

  private OTFDefinitionReader reader;
  private Node parent;
  private Map<Integer, Node> children;
  private int functionId;
  private int count;
  private long timeAccumulated;
  private long timeEntered;
  private int depth;

  /**
   * Constucts a new Node
   * 
   * @param otfDefinitionReader
   * @param functionId
   */
  public Node( final OTFDefinitionReader otfDefinitionReader, final int functionId ) {
    this.parent = null;
    this.reader = otfDefinitionReader;
    this.children = new HashMap<Integer, Node>();
    this.functionId = functionId;
    this.count = 1;
    this.timeAccumulated = 0;
    this.depth = 0;
  }

  private Node( final Node parent, final int functionId, final long time ) {
    this.timeEntered = time;
    this.children = new HashMap<Integer, Node>();
    this.parent = parent;
    this.functionId = functionId;
    this.count = 1;
    this.depth = parent.depth + 1;
    this.reader = parent.reader;
    this.timeAccumulated = 0;
  }

  /**
   * Enter the child nod
   * 
   * @param enterFunctionId
   * @param time
   * @return Child node
   */
  @SuppressWarnings("boxing")
  public Node enter( final int enterFunctionId, final long time ) {
    Node child = this.children.get( enterFunctionId );
    if( child == null ) {
      child = new Node( this, enterFunctionId, time );
      this.children.put( enterFunctionId, child );
    } else {
      child.timeEntered = time;
      child.count++;
    }
    return child;
  }

  /**
   * Leaves the function and returns the parent
   * 
   * @param time
   * @return the parent function
   */
  public Node leave( final long time ) {
    this.timeAccumulated += time - this.timeEntered;
    return this.parent;
  }

  /**
   * Returns the function id
   * 
   * @return function id
   */
  public int getFunctionId() {
    return this.functionId;
  }

  /**
   * Returns the name of the function
   * 
   * @return function name
   */
  public String getFunctionName() {
    return this.reader.getFunctionName( this.functionId );
  }

  /**
   * Returns the node's children
   * 
   * @return children
   */
  public Node[] getChildren() {
    return this.children.values().toArray( new Node[ 0 ] );
  }

  /**
   * Returns the times the function was called at this position in the call graph
   * 
   * @return number of function calls
   */
  public int getCount() {
    return this.count;
  }

  /**
   * Returns the time spend in this function at this position in the call graph
   * 
   * @return time spent in this function
   */
  public long getTime() {
    return this.timeAccumulated;
  }
}