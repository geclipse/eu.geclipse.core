/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *      - Nikolaos Tsioutsias
 *****************************************************************************/

package eu.geclipse.info.glue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.directory.Attributes;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueCESEBind extends AbstractGlueTable
  implements java.io.Serializable
{
  
  private static final long serialVersionUID = 1L;


  /**
   * 
   */
  //public GlueCE glueCE; // GlueCEUniqueID

  /**
   * 
   */
  //public GlueSE glueSE; // GlueSEUniqueID

  /**
   * 
   */
  public String Accesspoint;
  
  /**
   * 
   */
  public String MountInfo;

  /**
   * 
   */
  public Long Weight;

  /**
   * 
   */
  //public Date MeasurementDate;

  /**
   * 
   */
  //public Date MeasurementTime;

  /**
   * 
   */
  public GlueIndex glueIndex;

  /**
   * Set the unique id
   * @param id
   */
  public void setID( final String id ) {
    this.setKey( id );
  }
  
  /**
   * Fills tha values of a GlueCESEBind
   * @param attributes the attributes to process
   */
  public void processGlueRecord(final Attributes attributes)
  {
    final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss"; //$NON-NLS-1$
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    
    this.byRefOnly = false;
    this.Accesspoint = GlueUtility.getStringAttribute( "GlueCESEBindCEAccesspoint", attributes ); //$NON-NLS-1$
    this.setKey( GlueUtility.getStringAttribute( "GlueCESEBindSEUniqueID", attributes ) ); //$NON-NLS-1$
    this.keyName = GlueUtility.getStringAttribute( "GlueCESEBindSEUniqueID", attributes ); //$NON-NLS-1$
    this.MountInfo = GlueUtility.getStringAttribute( "GlueCESEBindMountInfo", attributes ); //$NON-NLS-1$
    this.tableName = "GlueCESEBind"; //$NON-NLS-1$
    this.Weight = GlueUtility.getLongAttribute( "GlueCESEBindWeight", attributes );//$NON-NLS-1$
    this.setVoType( "glite" ); //$NON-NLS-1$
    this.setRetrievalTime( sdf.format(cal.getTime()) );
  }
}
