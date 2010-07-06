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

/** Call Graph Node
 * 
 *
 */
public class Node {

  private Node parent;
  private Map<Integer, Node> children;
  private int functionId;
  private int count;
  private long timeAccumulated;
  private long timeEntered;
  private String name;
  private static OTFDefinitionReader otfDefinitionReader;
  public int depth;
  
  public Node( OTFDefinitionReader otfDefinitionReader, int functionId ) {
    this.otfDefinitionReader = otfDefinitionReader;
    this.children = new HashMap<Integer, Node>();
    this.parent = parent;
    this.functionId = functionId;
    this.count = 1;
    this.timeAccumulated = 0;
    this.depth = 0;
  }
  
  public Node( final Node parent, int functionId, long time ) {
    this.timeEntered = time;
    this.children = new HashMap<Integer, Node>();
    this.parent = parent;
    this.functionId = functionId;
    this.count = 1;
    this.timeAccumulated = 0;
    this.depth = 0;
  }

  @SuppressWarnings("boxing")
  public Node enter( final int functionId, long time ) {
    Node child = this.children.get( functionId );
    if( child == null ) {
      child = new Node( this, functionId, time );
      child.depth = depth+1;
      this.children.put( functionId, child );
    } else {
      child.increasecCount();
    }
    return child;
  }

  public Node leave( final long time ) {
    long abc = time - this.timeEntered;
    this.timeAccumulated +=  abc;
    return this.parent;
  }

  private void increasecCount() {
    this.count++;
  }

  public int getFunctionId() {
    return this.functionId;
  }
  
  public String getFunctionName(){
    return null;
  }
  
  public Node[] getChildren() {
    return children.values().toArray( new Node[0] );
  }
  
  public String getName() {
    return otfDefinitionReader.getFunctionName( this.functionId );
  }
 
  public int getCount() {
    return this.count;
  }
  
  public long getTime() {
    return timeAccumulated;
  }
}