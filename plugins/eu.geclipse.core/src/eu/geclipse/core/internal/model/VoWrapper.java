package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;

public class VoWrapper
    extends AbstractGridContainer
    implements IVirtualOrganization, IWrappedElement {
  
  private IGridProject project;
  
  private IVirtualOrganization vo;
  
  protected VoWrapper( final IGridProject project,
                       final IVirtualOrganization vo ) {
    super( null );
    this.project = project;
    this.vo = vo;
    addElement( new VoResourceContainer( this, vo, VoResourceContainer.ResourceType.Service ) );
    addElement( new VoResourceContainer( this, vo, VoResourceContainer.ResourceType.Computing ) );
    addElement( new VoResourceContainer( this, vo, VoResourceContainer.ResourceType.Storage ) );
  }

  public String getTypeName() {
    return this.vo.getTypeName();
  }

  @Override
  public void dispose() {
    this.vo.dispose();
  }

  @Override
  public IFileStore getFileStore() {
    return getProject().getFileStore().getChild( getName() );
  }
  
  public IGridInfoService getInfoService() {
    return this.vo.getInfoService();
  }

  @Override
  public String getName() {
    return this.vo.getName();
  }

  @Override
  public IGridContainer getParent() {
    return this.project;
  }

  @Override
  public IPath getPath() {
    return this.project.getPath().append( getName() );
  }

  @Override
  public IGridProject getProject() {
    return this.project;
  }
  
  public IGridService[] getServices() {
    return this.vo.getServices();
  }
  
  public IGridElement getWrappedElement() {
    return this.vo;
  }
  
  public boolean isLazy() {
    return true;
  }

  public boolean isLocal() {
    return true;
  }

  public boolean isVirtual() {
    return true;
  }

  @Override
  public Object getAdapter( final Class adapter ) {
    return null;
  }

  public void load() throws GridModelException {
    // TODO mathias
  }

  public void save() throws GridModelException {
    // TODO mathias
  }
  
  @Override
  public void setDirty() {
    // empty implementation 
  }

}
