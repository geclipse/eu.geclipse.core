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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;

public class GridJobStatus implements IGridJobStatus {

  final static public String XML_ROOT = Messages.getString( "GridJobStatus.jobStatus" ); //$NON-NLS-1$
  final static public String XML_STATUSNAME = Messages.getString( "GridJobStatus.name" ); //$NON-NLS-1$
  final static public String XML_STATUSTYPE = Messages.getString( "GridJobStatus.type" ); //$NON-NLS-1$
  final static public String XML_STATUSREASON = Messages.getString( "GridJobStatus.reason" ); //$NON-NLS-1$
  final static public String XML_STATUSDATA = Messages.getString( "GridJobStatus.data" ); //$NON-NLS-1$
  final static public String XML_STATUSUPDATEDATE = Messages.getString( "GridJobStatus.updateDate" ); //$NON-NLS-1$
  final static public String XML_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$
  final static public GridJobStatus UNKNOWN_STATUS = new GridJobStatus();
  protected int type;
  protected String name = null;
  protected String reason = null;
  protected Date updateDate = null;
  private String data = null;

  public GridJobStatus() {
    this.name = Messages.getString( "GridJobStatus.unknown" ); //$NON-NLS-1$
    this.type = UNKNOWN;
    this.reason = ""; //$NON-NLS-1$
    this.data = null;
    this.updateDate = Calendar.getInstance().getTime();
  }

  public GridJobStatus( final String aReason ) {
    this.name = Messages.getString( "GridJobStatus.unknown" ); //$NON-NLS-1$
    this.type = UNKNOWN;
    this.reason = aReason;
    this.data = null;
  }

  public GridJobStatus( final String name, final int type ) {
    this.name = name;
    this.type = type;
    this.data = null;
  }

  public GridJobStatus( @SuppressWarnings("unused")
  final IGridJobID id )
  {
    // Empty implementation
  }

  public GridJobStatus( final Node statusNode ) {
    this();
    setXMLNode( statusNode );
  }

  public void setXMLNode( final Node statusNode ) {
    int i;
    Node node;
    NodeList childNodes = statusNode.getChildNodes();
    for( i = 0; i < childNodes.getLength(); i++ ) {
      node = childNodes.item( i );
      if( XML_STATUSNAME.equals( node.getNodeName() ) ) {
        this.name = node.getTextContent();
        if( this.name != null )
          this.name = this.name.trim();
      }
      if( XML_STATUSREASON.equals( node.getNodeName() ) ) {
        this.reason = node.getTextContent();
        if( this.reason != null )
          this.reason = this.reason.trim();
      }
      if( XML_STATUSUPDATEDATE.equals( node.getNodeName() ) ) {
        try {
          this.updateDate = getXmlDateFormatter().parse( node.getTextContent() );
        } catch( DOMException e ) {
          // empty implementation
        } catch( ParseException e ) {
          // empty implementation
        }
      }
      if( XML_STATUSTYPE.equals( node.getNodeName() ) ) {
        try {
          this.type = Integer.parseInt( node.getTextContent() );
        } catch( Exception e ) {
          this.type = 0;
        }
      }
      if( XML_STATUSDATA.equals( node.getNodeName() ) ) {
        setData( node.getTextContent() );
      }
    }
  }

  public boolean canChange() {
    boolean canChange = true;
    switch( this.type ) {
      case DONE:
      case PURGED:
      case ABORTED:
        canChange = false;
    }
    return canChange;
  }

  /**
   * Default behaviour. Can be overritten in subclasses.
   */
  public boolean isSuccessful() {
    return ( this.type == DONE );
  }

  public String getJobStatusData() {
    return this.data;
  }

  @SuppressWarnings("nls")
  public final String getXML() {
    String xml = "";
    xml += "<"
           + GridJobStatus.XML_ROOT
           + " "
           + XML_ATTRIBUTE_CLASS
           + "=\""
           + this.getClass().getName()
           + "\">";
    xml += "  <"
           + GridJobStatus.XML_STATUSNAME
           + ">"
           + this.name
           + "</"
           + GridJobStatus.XML_STATUSNAME
           + ">\n";
    xml += "  <"
           + GridJobStatus.XML_STATUSTYPE
           + ">"
           + this.type
           + "</"
           + GridJobStatus.XML_STATUSTYPE
           + ">\n";
    if( this.updateDate != null ) {
      xml += "  <"
             + GridJobStatus.XML_STATUSUPDATEDATE
             + ">"
             + getXmlDateFormatter().format( this.updateDate )
             + "</"
             + GridJobStatus.XML_STATUSUPDATEDATE
             + ">\n";
    }
    if( this.reason != null ) {
      xml += "  <"
             + GridJobStatus.XML_STATUSREASON
             + ">"
             + this.reason.replaceAll( "&", "" )
             + "</"
             + GridJobStatus.XML_STATUSREASON
             + ">\n";
    }
    xml += "<"
           + GridJobStatus.XML_STATUSDATA
           + "><![CDATA["
           + getData()
           + "]]></"
           + GridJobStatus.XML_STATUSDATA
           + ">";
    xml += "</" + GridJobStatus.XML_ROOT + ">\n";
    return xml;
  }

  private DateFormat getXmlDateFormatter() {
    DateFormat formatter = DateFormat.getDateTimeInstance( DateFormat.SHORT,
                                                           DateFormat.SHORT,
                                                           new Locale( "Locale.US" ) ); //$NON-NLS-1$
    if( formatter == null ) {
      formatter = DateFormat.getDateTimeInstance( DateFormat.SHORT,
                                                  DateFormat.SHORT );
    }
    return formatter;
  }

  public String getName() {
    return this.name;
  }

  public int getType() {
    return this.type;
  }

  public String getReason() {
    return this.reason;
  }

  public Date getLastUpdateTime() {
    return this.updateDate;
  }

  // This method should be overwritten by child classes
  protected String getData() {
    return null;
  }

  protected void setData( @SuppressWarnings("unused")
  final String data )
  {
    // Empty implementation
  }
}
