package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;

/**
 * Wrapper of a VO in order to map the VO from the manager to a
 * project.
 */
public class VoWrapper
    extends AbstractGridContainer
    implements IVirtualOrganization, IWrappedElement {
  
  private IGridProject project;
  
  private IVirtualOrganization vo;
  
  protected VoWrapper( final IGridProject project,
                       final IVirtualOrganization vo ) {
    super();
    this.project = project;
    this.vo = vo;
    try {
      addElement( new VoResourceContainer( this, vo, VoResourceContainer.ResourceType.Service ) );
      addElement( new VoResourceContainer( this, vo, VoResourceContainer.ResourceType.Computing ) );
      addElement( new VoResourceContainer( this, vo, VoResourceContainer.ResourceType.Storage ) );
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof VoResourceContainer;
  }

  public String getTypeName() {
    return this.vo.getTypeName();
  }

  @Override
  public void dispose() {
    this.vo.dispose();
  }

  public IFileStore getFileStore() {
    return getProject().getFileStore().getChild( getName() );
  }
  
  public IGridInfoService getInfoService()
      throws GridModelException {
    return this.vo.getInfoService();
  }

  public String getName() {
    return this.vo.getName();
  }

  public IGridContainer getParent() {
    return this.project;
  }

  public IPath getPath() {
    return this.project.getPath().append( getName() );
  }

  @Override
  public IGridProject getProject() {
    return this.project;
  }
  
  public IResource getResource() {
    return null;
  }
  
  public IGridService[] getServices()
      throws GridModelException {
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

  @SuppressWarnings("unchecked")
  @Override
  public Object getAdapter( final Class adapter ) {
    return this.vo.getAdapter( adapter );
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
