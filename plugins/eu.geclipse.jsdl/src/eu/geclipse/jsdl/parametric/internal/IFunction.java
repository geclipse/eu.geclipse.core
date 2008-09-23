package eu.geclipse.jsdl.parametric.internal;

/**
 * Function, which generate values for parameter during generating JSLDs from parametric JSDL
 */
interface IFunction extends Iterable<String> {
  // now methods from iterator are enough, but it's better to stay with our own
  // interface, to mark that not every iterator may be used as function in
  // parametric jsdl
  
}