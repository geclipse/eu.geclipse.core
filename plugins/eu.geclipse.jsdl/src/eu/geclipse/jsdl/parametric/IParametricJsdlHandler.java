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
package eu.geclipse.jsdl.parametric;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;

import eu.geclipse.core.reporting.ProblemException;

/**
 * Handler passed to {@link IParametricJsdlGenerator}. Methods from that
 * interface are called during generation JSDLs for Parametric JSDL.<br>
 * It's useful to control generation process (e.g. serialization of generated
 * JSDL or tracking substitution of parameters).
 */
public interface IParametricJsdlHandler {

  /**
   * Called, when sweep jsdl is successfully validated and generation process is
   * starting
   * 
   * @param generatedJsdls number of JSDLs, which will be generated (number of
   *          iterations)
   * @throws ProblemException
   */
  void generationStarted( int generatedJsdls ) throws ProblemException;

  /**
   * Called after last JSDL was generated
   * 
   * @throws ProblemException
   */
  void generationFinished() throws ProblemException;

  /**
   * Called, when parameter value was changed in currently generating JSDL
   * 
   * @param paramName XPath query selecting nodes, which values are substituted
   *          in JSDL
   * @param newValue value set for node selected by XPath
   * @param monitor progress monitor
   * @throws ProblemException
   */
  void paramSubstituted( String paramName,
                         String newValue,
                         IProgressMonitor monitor ) throws ProblemException;

  /**
   * Called, when all parameters were substituted and new generated jsdl is
   * ready. Now you can store it on disk, show for user, submit etc
   * 
   * @param generatedJsdl new instance of jsdl
   * @param iterationsStack list of integer numbers describing, for which sweep
   *          iteration this jsdl was generated. E.g. If parametric jsdl
   *          contains nested sweeps (second sweep inside first), then this list
   *          will contain 2 Integer objects: first counting outer sweep
   *          iterations and the second for inner sweep iterations
   * @param monitor progress monitor
   * @throws ProblemException 
   */
  void newJsdlGenerated( Document generatedJsdl,
                         List<Integer> iterationsStack,
                         IProgressMonitor monitor ) throws ProblemException;
}
