package eu.geclipse.info.model;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.IGlueInfoStore;


public interface IExtentedGridInfoService extends IGridInfoService {

  public IGlueInfoStore getStore();
  
  public void scheduleFetch(final IProgressMonitor monitor);
  
  public void setVO(IVirtualOrganization vo);
  
  public String getVoType();
}
