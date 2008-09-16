/******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse consortium
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

/**
 * Class representing handle returned from grid to submitted job 
 */
public class GridJobID implements IGridJobID {

  final static String UNKNOWN="Unknown"; //$NON-NLS-1$
  final static String XML_ROOT = "JobID"; //$NON-NLS-1$
  final static String XML_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$
  final static private String XML_NAMENODE = "Name"; //$NON-NLS-1$
  final static private String XML_DATANODE = "Data"; //$NON-NLS-1$
  final static private String XML_VONODE = "VO"; //$NON-NLS-1$
  private String jobID = UNKNOWN;
  private String VO = UNKNOWN;
  private GridJob job;  

  /**
   * Empty constructor for JobId created in past g-Eclipse sessions
   */
  public GridJobID() {
    // default constructor
  }

  /**
   * Empty constructor for JobId created in past g-Eclipse sessions
   */
  public GridJobID(String _jobID, String _VO) {
    this.jobID=_jobID;
    this.VO=_VO;
  }

  /**
   * @param jobIDNode xml node, from which data about job should be read
   */
  public GridJobID( final Node jobIDNode ) {
    this();
    setXMLNode( jobIDNode );
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
      if( XML_VONODE.equals( node.getNodeName() ) ) {
        this.VO = node.getTextContent();
        if( this.VO != null )
          this.VO = this.VO.trim();
      }
      if( XML_DATANODE.equals( node.getNodeName() ) ) {
        setData( node.getTextContent() );
      }
    }
  }

  public String getJobID() {
    return this.jobID;
  }

  protected void setData( @SuppressWarnings("unused")
                          final String data ) {
    // Empty implementation, subclasses override
  }

  final String getXML() {
    return "<"                        //$NON-NLS-1$
           + XML_ROOT
           + " "                      //$NON-NLS-1$
           + XML_ATTRIBUTE_CLASS
           + "=\""                    //$NON-NLS-1$
           + this.getClass().getName()
           + "\"><Name>"              //$NON-NLS-1$
           + this.jobID
           + "</Name><VO>"
           + this.VO
           + "</VO><Data><![CDATA[" //$NON-NLS-1$
           + getData()
           + "]]></Data></"           //$NON-NLS-1$
           + XML_ROOT
           + ">";                     //$NON-NLS-1$
  }

  protected String getData() {
    return ""; //$NON-NLS-1$
  }
  
  /**
   * @param job created in local workspace for after submission
   */
  public void setJob( final GridJob job ) {
    this.job = job;
  }
  
  /**
   * @return job created within the workspace<br>
   * May be <b>null</b> if GridJob wasn't created for submitted job
   */
  public GridJob getJob() {
    return this.job;
  }

  public String getVO() {
    return this.VO;
  }
}
