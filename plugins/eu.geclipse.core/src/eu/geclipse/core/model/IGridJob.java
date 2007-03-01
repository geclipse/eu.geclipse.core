package eu.geclipse.core.model;

import java.io.InputStream;

public interface IGridJob extends IGridContainer, IGridResource, IManageable {
  
  public void cancel();
  
  public IGridJobStatus getStatus();
  
  public String getID();

  public InputStream getStdOutStream();

  public InputStream getStdErrStream();
  
}
