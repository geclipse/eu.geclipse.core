package eu.geclipse.jsdl.parametric;



/**
 * This class gets parametric JSDL and generate a bunch of non-parametric JSDL.
 * Every generated JSDL has substituted value of parameters defined in sweep
 * extension of parametric JSDL
 */
public interface IParametricJsdlGenerator {
  /**
   * @param handler which allow to control generation process (e.g. serialization of generated JSDL, or tracking parameters substitution)  
   * @param monitor progress monitor updated during generation
   * @throws ParametricJsdlException thrown in case on error during generation
   * @throws ParametricGenerationCanceled thrown when generation was canceled by the handler
   */
  public void generate( IParametricJsdlHandler handler ) throws ParametricJsdlException, ParametricGenerationCanceled;
}