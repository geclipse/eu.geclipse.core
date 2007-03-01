package eu.geclipse.core.model;


public interface IGridElementCreator {
  
  public boolean canCreate( final Class< ? extends IGridElement > elementType );
  
  public boolean canCreate( final Object fromObject );
  
  public IGridElement create( final IGridContainer parent ) throws GridModelException;
  
  public Object getObject();
  
}
