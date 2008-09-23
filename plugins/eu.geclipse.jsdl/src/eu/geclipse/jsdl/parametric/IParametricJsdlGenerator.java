package eu.geclipse.jsdl.parametric;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.reporting.ProblemException;


/**
 * This class gets parametric JSDL and generate a bunch of non-parametric JSDL.
 * Every generated JSDL has substituted value of parameters defined in sweep
 * extension of parametric JSDL
 */
public interface IParametricJsdlGenerator {
  /**
   * @param handler which allow to control generation process (e.g. serialization of generated JSDL, or tracking parameters substitution)  
   * @param monitor progress monitor updated during generation
   * @throws ProblemException
   */
  public void generate( IParametricJsdlHandler handler,
                        final IProgressMonitor monitor ) throws ProblemException;
}