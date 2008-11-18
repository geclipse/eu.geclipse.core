package eu.geclipse.core.config;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import eu.geclipse.core.reporting.ProblemException;

public interface IConfigurator {
  
  public IStatus configure( final IProgressMonitor monitor ) throws ProblemException;
  
}
