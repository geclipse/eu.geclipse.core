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
   * @param tableName
   * @param vo
   * @return ArrayList<AbstractGlueTable>
   */
  public static ArrayList<AbstractGlueTable> getGlueTable(final String tableName, final String vo){
    ArrayList<AbstractGlueTable> inArray=GlueIndex.getInstance().getList(tableName);
    ArrayList<AbstractGlueTable> outArray=new ArrayList<AbstractGlueTable>();
    if(vo==null || vo.equals("none")){ //$NON-NLS-1$
      return inArray;
    }

    if(tableName.equals("GlueCE")){ //$NON-NLS-1$
      for (AbstractGlueTable table : inArray) {
        GlueCE ce=(GlueCE) table;
        if(ceSupportsVO(ce, vo)){
          outArray.add(ce);
        }
      }
    }else if(tableName.equals("GlueSE")){ //$NON-NLS-1$
      for (AbstractGlueTable table : inArray) {
        GlueSE se=(GlueSE) table;
        if(seSupportsVO(se, vo)){
          outArray.add(se);
        }
      }
    }else if(tableName.equals("GlueService")){ //$NON-NLS-1$
      for (AbstractGlueTable table : inArray) {
        GlueService service=(GlueService) table;
        if(serviceSupportsVO(service, vo)){
          outArray.add(service);
        }
      }
    }else if(tableName.equals("GlueSite")){ //$NON-NLS-1$
      for (AbstractGlueTable table : inArray) {
        GlueSite site=(GlueSite) table;
        if(siteSupportsVO(site,vo)){
          outArray.add(site);
        }
      }
    }else if(tableName.equals("GlueSA")){ //$NON-NLS-1$
      for (AbstractGlueTable table : inArray) {
        GlueSA sa=(GlueSA) table;
        if(saSupportsVO(sa,vo)){
          outArray.add(sa);
        }
      }
    }else{
      outArray=inArray;
    }
    return outArray;
  }

  /**
   * @param se
   * @param vo
   * @return boolean
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
   * @param ce
   * @param vo
   * @return boolean
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
   * @param service
   * @param vo
   * @return boolean
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
   * @param sa
   * @param vo
   * @return booelan
   */
  public static boolean saSupportsVO(final GlueSA sa, final String vo){
    boolean found=false;
    for (GlueSAAccessControlBaseRule rule : sa.glueSAAccessControlBaseRuleList) {
      if(rule.Value.equals(vo)){
        found=true;
        break;
      }
    }
    return found;
  }

  /**
   * @param site
   * @param vo
   * @return booelan
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
   * @param vo
   * @return ArrayList<AbstractGlueTable>
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
