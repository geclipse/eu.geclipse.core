package eu.geclipse.core.config;

import java.util.Set;

import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.reporting.ProblemException;

public interface IConfiguration {
  
  public Set< String > getKeys();
  
  public String getParameter( final String key );
  
  public String[] getParameters( final String key );
  
  public void loadFromXML( final IFileStore store ) throws ProblemException;
  
  public void storeToXML( final IFileStore store ) throws ProblemException;
  
}
