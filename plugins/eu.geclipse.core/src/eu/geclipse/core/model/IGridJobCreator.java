package eu.geclipse.core.model;


public interface IGridJobCreator extends IGridElementCreator {
  
  public boolean canCreate( final IGridJobDescription description );
  
  public String getJobLabel();

  public IGridJobID submitJob( final IGridJobDescription parent ) throws GridModelException;
//FIXME pawelw - czhange to grid exception
  public IGridJobID submitJob( final IGridJobDescription parent, final String destination ) throws GridModelException;
  
  public IGridElement create( final IGridContainer parent, IGridJobID id ) throws GridModelException;

}
