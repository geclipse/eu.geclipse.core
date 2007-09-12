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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.GridException;
import eu.geclipse.core.model.IGridJobID;

/**
 * Class representing handle returned from grid to submitted job 
 */
public class GridJobID implements IGridJobID {

  final static String UNKNOWN="Unknown"; //$NON-NLS-1$
  final static String XML_ROOT = "JobID"; //$NON-NLS-1$
  final static String XML_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$
  final static private String XML_NAMENODE = "Name"; //$NON-NLS-1$
  final static private String XML_DATANODE = "Data"; //$NON-NLS-1$
  protected String jobID = UNKNOWN;

  /**
   * 
   */
  public GridJobID() {
    // default constructor
  }

  /**
   * @param jobIDNode
   */
  public GridJobID( final Node jobIDNode ) {
    this();
    setXMLNode(jobIDNode);
  }
    
  final void setXMLNode( final Node statusNode ) {
      int i;
    Node node;
    NodeList childNodes = statusNode.getChildNodes();
    for( i = 0; i < childNodes.getLength(); i++ ) {
      node = childNodes.item( i );
      if( XML_NAMENODE.equals( node.getNodeName() ) ) {
        this.jobID = node.getTextContent();
        if( this.jobID != null )
          this.jobID = this.jobID.trim();
      }
      if( XML_DATANODE.equals( node.getNodeName() ) ) {
        setData( node.getTextContent() );
      }
    }
  }

  public String getJobID() {
    return this.jobID;
  }

  protected void setData( final String data ) {
//    System.out.println( data );
  }

  public final String getXML() {
    return "<"
           + XML_ROOT
           + " "
           + XML_ATTRIBUTE_CLASS
           + "=\""
           + this.getClass().getName()
           + "\"><Name>"
           + this.jobID
           + "</Name><Data><![CDATA["
           + getData()
           + "]]></Data></"
           + XML_ROOT
           + ">";
  }

  protected String getData() {
    return "<test>XML</test>";
  }

  public IStatus downloadOutputs(final IFolder jobFolder, final IProgressMonitor monitor) throws GridException {
    return new Status( IStatus.WARNING, eu.geclipse.core.jobs.internal.Activator.PLUGIN_ID, Messages.getString("GridJobID.errNotImplemented") ); //$NON-NLS-1$
  }
}
