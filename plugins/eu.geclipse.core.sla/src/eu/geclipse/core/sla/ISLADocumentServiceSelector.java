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
 *     Harald Kornmayer, NEC Laboratories Europe 
 *
 *****************************************************************************/
package eu.geclipse.core.sla;

/**
 * The separation of graphical UI elements from the core implementation forced
 * the introduction of this interface. It is support by an extension point which
 * will be consumed by the UI components of the SLA core.
 * 
 * @author korn
 */
public interface ISLADocumentServiceSelector {

  /**
   * This method has to look in the extension registry for available
   * implementation and returns one of them.
   * 
   * @return Array of objects containing the Name of SLA service
   *         implementations.
   */
  public Object[] selectSLAImplementation();
}
