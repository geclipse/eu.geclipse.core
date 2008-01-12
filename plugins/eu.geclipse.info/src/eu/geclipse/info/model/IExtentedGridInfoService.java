package eu.geclipse.info.model;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.IGlueInfoStore;


public interface IExtentedGridInfoService extends IGridInfoService {

  public IGlueInfoStore getStore();
  
  public void scheduleFetch( final IProgressMonitor monitor );
  
  public void setVO( final IVirtualOrganization vo );
  
  public String getVoType();
}
