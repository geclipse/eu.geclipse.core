/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.jsdl.parametric.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.parametric.ParametricJsdlException;

/**
 * Context, which just calculate how many jsdl will be generated and ignore
 * parameters substitution and storing jsdl.
 */
class CounterGenerationContext implements IGenerationContext {
  private int iterations = 0;
  private Set<String> parameters = new TreeSet<String>();
  private IParametricJsdlHandler handler;

  
  public CounterGenerationContext( final IParametricJsdlHandler handler ) {
    super();
    this.handler = handler;
  }

  /*
   * (non-Javadoc)
   * @see
   * eu.geclipse.jsdl.parametric.IGenerationContext#setValue(java.lang.String,
   * javax.xml.xpath.XPathExpression, java.lang.String,
   * org.eclipse.core.runtime.SubMonitor)
   */
  public void setValue( final String xpathQuery,
                        final String value ) throws ParametricJsdlException
  {
    this.parameters.add( xpathQuery );
  }

  /*
   * (non-Javadoc)
   * @see
   * eu.geclipse.jsdl.parametric.IGenerationContext#storeGeneratedJsdl(java.
   * util.List, org.eclipse.core.runtime.SubMonitor)
   */
  public void storeGeneratedJsdl( final List<Integer> iterationsStack )
    throws ParametricJsdlException
  {
    this.iterations++;
  }

  @Override
  public IGenerationContext clone() {
    return this;
  }
  
  /**
   * @return how many jsdl will be generated for this parametric jsdl
   */
  public int getIterations() {
    return this.iterations;
  }
  
  List<String> getParameters() {
    List<String> list = new ArrayList<String>( this.parameters.size() );
    list.addAll( this.parameters );
    return list;
  }

  public IParametricJsdlHandler getHandler() {
    return this.handler;
  }
}
