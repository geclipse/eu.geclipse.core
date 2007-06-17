/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
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
 *     Pawel Wolniewicz - PSNC
 *****************************************************************************/
package eu.geclipse.core.jobs;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import eu.geclipse.core.model.IGridJobID;

public class GridJobID implements IGridJobID {

  final static public String XML_ROOT = "JobID";
  final static public String XML_NAMENODE = "Name";
  final static public String XML_DATANODE = "Data";
  final static public String UNKNOWN="Unknown";
  public static final String XML_ATTRIBUTE_CLASS = "class";
  protected String jobID = UNKNOWN;

  public GridJobID() {
  }

  public GridJobID( final Node jobIDNode ) {
    this();
    setXMLNode(jobIDNode);
  }
    
  public final void setXMLNode( final Node statusNode ) {
      int i;
    Node node;
    NodeList childNodes = statusNode.getChildNodes();
    for( i = 0; i < childNodes.getLength(); i++ ) {
      node = childNodes.item( i );
      if( XML_NAMENODE.equals( node.getNodeName() ) ) {
        jobID = node.getTextContent();
        if( jobID != null )
          jobID = jobID.trim();
      }
      if( XML_DATANODE.equals( node.getNodeName() ) ) {
        setData( node.getTextContent() );
      }
    }
  }

  public String getJobID() {
    return jobID;
  }

  protected void setData( final String data ) {
    System.out.println( data );
  }

  public final String getXML() {
    return "<"
           + XML_ROOT
           + " "
           + XML_ATTRIBUTE_CLASS
           + "=\""
           + this.getClass().getName()
           + "\"><Name>"
           + jobID
           + "</Name><Data><![CDATA["
           + getData()
           + "]]></Data></"
           + XML_ROOT
           + ">";
  }

  protected String getData() {
    return "<test>XML</test>";
  }
}
