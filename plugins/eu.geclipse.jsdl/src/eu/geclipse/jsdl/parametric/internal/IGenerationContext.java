package eu.geclipse.jsdl.parametric.internal;

import java.util.List;

import org.w3c.dom.Document;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.parametric.ParametricJsdlException;

/**
 * Interface for objects, containing JSDL {@link Document}, which nodes are
 * changed by values defined in sweep extension.
 */
interface IGenerationContext {

  /**
   * Substitute text of nodes described by paramName with new value
   * @param xpathQuery query selecting XML nodes, to which new value should be set
   * @param value new value for parameter
   * @param subMonitor
   * @param paramXPath XPath expression, which selects XML nodes, which
   *          text Value should be substituted
   * @throws ParametricJsdlException 
   * @throws ProblemException
   */
  public abstract void setValue( final String xpathQuery,
                                 final String value )
    throws ParametricJsdlException;

  /**
   * Called, when all parameters were substituted for current iteration, and new jsdl can be stored
   * @param iterationsStack
   * @param subMonitor
   * @throws ParametricJsdlException 
   * @throws ProblemException
   */
  public abstract void storeGeneratedJsdl( final List<Integer> iterationsStack )
    throws ParametricJsdlException;

  public abstract IGenerationContext clone();
  
  public IParametricJsdlHandler getHandler();  
}