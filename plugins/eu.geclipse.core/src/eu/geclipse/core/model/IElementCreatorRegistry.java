package eu.geclipse.core.model;

import java.util.List;

public interface IElementCreatorRegistry {
  
  /**
   * Get a list of all element creators that are defined as extensions
   * of the <code>eu.geclipse.core.gridElementCreator</code> extension
   * point.
   * 
   * @return All defined element creators.
   */
  public List< IGridElementCreator > getCreators();
  
  /**
   * Get an {@link IGridElementCreator} that is able to create an element of the
   * specified type from the specified object. If such a creator could be found
   * and a source object was specified the element's source object will be
   * initialized by calling {@link IGridElementCreator#setSource(Object)}.
   * 
   * @param source The object from which to create the element.
   * @param target The type of the element to be created.
   * @return An appropriate element creator or <code>null</code> if no
   * such creator is currently registered.
   */
  public IGridElementCreator getCreator( final Object source,
                                         final Class< ? extends IGridElement > target );
  
}
