package eu.geclipse.info.glue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import eu.geclipse.info.IGlueInfoStore;

public class GlueQuery {
	
	public static ArrayList<AbstractGlueTable> getGlueTable(String tableName,String vo){
		ArrayList<AbstractGlueTable> inArray=GlueIndex.getInstance().getList(tableName);
		ArrayList<AbstractGlueTable> outArray=new ArrayList<AbstractGlueTable>();
		if(vo==null || vo.equals("none")){
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

	public static boolean seSupportsVO(GlueSE se,String vo){
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
	
	public static boolean ceSupportsVO(GlueCE ce, String vo){
		boolean found=false;
		for (GlueCEAccessControlBaseRule rule : ce.glueCEAccessControlBaseRuleList) {
			if(rule.Value.equals("VO:"+vo)){ //$NON-NLS-1$
				found=true;
				break;
			}
		}
		return found;
	}
    public static boolean saSupportsVO(GlueSA sa, String vo){
        boolean found=false;
        for (GlueSAAccessControlBaseRule rule : sa.glueSAAccessControlBaseRuleList) {
            if(rule.Value.equals(vo)){ //$NON-NLS-1$
                found=true;
                break;
            }
        }
        return found;
    }
    
	public static boolean siteSupportsVO(GlueSite site, String vo){
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
	
	public static ArrayList<AbstractGlueTable> getStorageElements(String vo){
		ArrayList<AbstractGlueTable> agtList=new ArrayList<AbstractGlueTable>();
		Enumeration<GlueSE> enSE= GlueIndex.getInstance().glueSE.elements();
		while(enSE.hasMoreElements()){
			GlueSE se=enSE.nextElement();
			agtList.add(se);
		}
		return agtList;
	}
	
	
}
