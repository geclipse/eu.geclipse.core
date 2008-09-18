package eu.geclipse.jsdl.parametric.internal;

import java.util.List;

import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Document;

import eu.geclipse.core.reporting.ProblemException;

/**
 * Interface for objects, containing JSDL {@link Document}, which nodes are
 * changed by values defined in sweep extension.
 */
interface IGenerationContext {

  /**
   * Substitute text of nodes described by paramName with new value
   * @param paramName String representation of XPath query
   * @param paramXPath XPath expression, which selects XML nodes, which
   *          text Value should be substituted
   * @param value new value for parameter
   * @param subMonitor
   * @throws ProblemException
   */
  public abstract void setValue( final String paramName,
                                 final String xpathQuery,
                                 final String value,
                                 final SubMonitor subMonitor )
    throws ProblemException;

  /**
   * Called, when all parameters were substituted for current iteration, and new jsdl can be stored
   * @param iterationsStack
   * @param subMonitor
   * @throws ProblemException
   */
  public abstract void storeGeneratedJsdl( final List<Integer> iterationsStack,
                                           final SubMonitor subMonitor )
    throws ProblemException;

  public abstract IGenerationContext clone();
}