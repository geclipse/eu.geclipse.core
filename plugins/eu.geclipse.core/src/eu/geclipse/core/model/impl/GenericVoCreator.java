package eu.geclipse.core.model.impl;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IStorableElementCreator;

public class GenericVoCreator
    implements IStorableElementCreator {
  
  /**
   * The creators extension ID.
   */
  private static final String EXTENSION_ID = "eu.geclipse.core.genericVoCreator"; //$NON-NLS-1$
  
  /**
   * The object from which to create the VO.
   */
  private Object object;
  
  private String voName;
  
  /**
   * Apply this creators settings to the specified VO.
   * 
   * @param vo The {@link GenericVirtualOrganization} to which to
   * apply this creators settings.
   * @throws GridModelException If an error occurs during the operation.
   */
  public void apply( final GenericVirtualOrganization vo )
      throws GridModelException {
    // currently empty implementation
  }

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return elementType.isAssignableFrom( GenericVirtualOrganization.class );
  }

  public boolean canCreate( final Object fromObject ) {
    // TODO mathias
    return false;
  }
  
  public boolean canCreate( final IFileStore fromFileStore ) {
    IFileStore propertiesStore = fromFileStore.getChild( GenericVoProperties.NAME );
    IFileInfo propertiesInfo = propertiesStore.fetchInfo();
    boolean result = propertiesInfo.exists();
    if ( result ) {
      this.object = fromFileStore;
    }
    return result;
  }

  public IGridElement create( final IGridContainer parent ) throws GridModelException {
    GenericVirtualOrganization vo = null;
    if ( this.object == null ) {
      vo = new GenericVirtualOrganization( this );
    } else if ( this.object instanceof IFileStore ) {
      IFileStore fileStore = ( IFileStore ) this.object;
      vo = new GenericVirtualOrganization( fileStore );
    }
    return vo;
  }
  
  public String getExtensionID() {
    return EXTENSION_ID;
  }

  public Object getObject() {
    return this.object;
  }
  
  /**
   * Get the VO's name.
   * 
   * @return The unique name of the VO.
   */
  public String getVoName() {
    return this.voName;
  }
  
  /**
   * Set the VO's name.
   * 
   * @param name The unique name of the VO.
   */
  public void setVoName( final String name ) {
    this.voName = name;
  }

}
