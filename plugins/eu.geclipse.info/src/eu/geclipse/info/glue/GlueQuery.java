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
 *
 *****************************************************************************/

package eu.geclipse.info.glue;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueQuery {

  /**
   * This method returns a glue table. It is possible to choose certain elements from
   * that table using the objectTableName parameter. It is also possible to select only 
   * the elements that belong in a specific vo using the vo parameter.
   * @param glueObjectName A string for the Glue Object that will be returned. It can be a 
   * GlueCE, GlueSite ... or any other AbstractGlueTable.
   * @param objectTableName A string for the tableName parameter that the AbstractGlueTable has. 
   * This parameter is used in order to separate glue elements that belong in the same glue table.
   * An example for that is a GlueService that can have different values in the glueTable to 
   * distinguish if it is a glite service or a gria service.
   * @param vo A String containing a vo name. It can be <code>null</code> or <code>"none"</code>
   * @return An ArrayList containing the GlueObjects that were requested.
   * 
   * @see eu.geclipse.info.glue.AbstractGlueTable
   */
  public static ArrayList<AbstractGlueTable> getGlueTable(final String glueObjectName, 
                                                          final String objectTableName,
                                                          final String vo){
    ArrayList<AbstractGlueTable> inArray=GlueIndex.getInstance().getList(glueObjectName, objectTableName);
    ArrayList<AbstractGlueTable> outArray=new ArrayList<AbstractGlueTable>();
    ArrayList<AbstractGlueTable> result = new ArrayList<AbstractGlueTable>();
    if(vo==null || vo.equals("none")){ //$NON-NLS-1$
       result = inArray;
    }
    else {
      if(glueObjectName.equals("GlueCE")){ //$NON-NLS-1$
        for (AbstractGlueTable table : inArray) {
          GlueCE ce=(GlueCE) table;
          if(ceSupportsVO(ce, vo)){
            outArray.add(ce);
          }
        }
      }else if(glueObjectName.equals("GlueSE")){ //$NON-NLS-1$
        for (AbstractGlueTable table : inArray) {
          GlueSE se=(GlueSE) table;
          if(seSupportsVO(se, vo)){
            outArray.add(se);
          }
        }
      }else if(glueObjectName.equals("GlueService")){ //$NON-NLS-1$
        for (AbstractGlueTable table : inArray) {
          GlueService service=(GlueService) table;
          if(serviceSupportsVO(service, vo)){
            outArray.add(service);
          }
        }
      }else if(glueObjectName.equals("GlueSite")){ //$NON-NLS-1$
        for (AbstractGlueTable table : inArray) {
          GlueSite site=(GlueSite) table;
          if(siteSupportsVO(site,vo)){
            outArray.add(site);
          }
        }
      }else if(glueObjectName.equals("GlueSA")){ //$NON-NLS-1$
        for (AbstractGlueTable table : inArray) {
          GlueSA sa=(GlueSA) table;
          if(saSupportsVO(sa,vo)){
            outArray.add(sa);
          }
        }
      } else{
        outArray=inArray;
      }
      
      result = outArray;
    }
    
    return result;
  }

  /**
   * Check if the storage elements supports the vo
   * @param se The Storage Element
   * @param vo the name of the vo
   * @return true if it supports it or false otherwise.
   */
  public static boolean seSupportsVO(final GlueSE se, final String vo){
    boolean found=false;
    for (GlueSA sa : se.glueSAList) {
      for (GlueSAAccessControlBaseRule rule : sa.glueSAAccessControlBaseRuleList) {
        if(rule.Value.equals(vo)){
          found=true;
          break;
        }
      }
      if(found){
        break;
      }
    }
    return found;
  }

  /**
   * Chech if the computing elements supports the vo
   * @param ce the computing element
   * @param vo the name of the vo
   * @return true if it supports it or false otherwise.
   */
  public static boolean ceSupportsVO(final GlueCE ce, final String vo){
    boolean found=false;
    for (GlueCEAccessControlBaseRule rule : ce.glueCEAccessControlBaseRuleList) {
      if(rule.Value.equals("VO:"+vo)){ //$NON-NLS-1$
        found=true;
        break;
      }
    }
    return found;
  }

  /**
   * Check if the service supports the vo
   * @param service the service
   * @param vo the name of the vo
   * @return true if it supports it or false otherwise.
   */
  public static boolean serviceSupportsVO(final GlueService service, final String vo){
    boolean found=false;
    for (GlueServiceAccessControlRule rule : service.glueServiceAccessControlRuleList) {
      if(rule.value.equals(vo)){ 
        found=true;
        break;
      }
    }
    return found;
  }

  /**
   * Check if the storage access supports the vo
   * @param sa the storage access
   * @param vo the name of the vo
   * @return true if it supports it or false otherwise.
   */
  public static boolean saSupportsVO(final GlueSA sa, final String vo){
    boolean found=false;
    for (GlueSAAccessControlBaseRule rule : sa.glueSAAccessControlBaseRuleList) {
      if(rule.Value.equalsIgnoreCase(vo)){
        found=true;
        break;
      }
    }
    return found;
  }

  /**
   * Check if the sites supports the vo
   * @param site the site
   * @param vo the name of the vo
   * @return true if it supports it or false otherwise.
   */
  public static boolean siteSupportsVO(final GlueSite site, final String vo){
    boolean found=false;
    for (GlueSE se: site.glueSEList) {
      if(seSupportsVO(se, vo)){
        found=true;
        break;
      }
    }
    if(!found){
      for (GlueCluster gc: site.glueClusterList) {
        for (GlueCE ce : gc.glueCEList) {
          if(ceSupportsVO(ce, vo)){
            found=true;
            break;
          }
        }
        if(found){
          break;
        }
      }
    }
    return found;
  }

  /**
   * Get the storage elements
   * @param vo the name of the vo
   * @return an ArrayList of AbstractGlueTable
   */
  public static ArrayList<AbstractGlueTable> getStorageElements(final String vo){
    ArrayList<AbstractGlueTable> agtList=new ArrayList<AbstractGlueTable>();
    Enumeration<GlueSE> enSE= GlueIndex.getInstance().glueSE.elements();
    while(enSE.hasMoreElements()){
      GlueSE se=enSE.nextElement();
      agtList.add(se);
    }
    return agtList;
  }


}
