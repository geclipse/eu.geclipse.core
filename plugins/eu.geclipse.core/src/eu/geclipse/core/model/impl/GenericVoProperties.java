package eu.geclipse.core.model.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IStorableElement;

public class GenericVoProperties extends AbstractGridElement implements
    IStorableElement {
  
  /**
   * Name to reference the properties.
   */
  public static final String NAME = ".generic_vo_properties"; //$NON-NLS-1$
  
  private GenericVirtualOrganization vo;
  
  GenericVoProperties( final GenericVirtualOrganization vo ) {
    this.vo = vo;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return NAME;
  }

  public IGridContainer getParent() {
    return this.vo;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }
  
  public void load() throws GridModelException {
    // TODO Auto-generated method stub

  }

  public void save() throws GridModelException {
    IFileStore fileStore = getFileStore();
    try {
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      OutputStreamWriter osWriter = new OutputStreamWriter( oStream );
      BufferedWriter bWriter = new BufferedWriter( osWriter );
      bWriter.write( this.vo.getName() );
      bWriter.close();
    } catch ( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_SAVE_FAILED, cExc );
    } catch ( IOException ioExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_SAVE_FAILED, ioExc );
    }
  }

}
