/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     FZK (http://www.fzk.de)
 *     - Mathias Stumpert - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.resources;

import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IResource;

import eu.geclipse.core.GridException;
import eu.geclipse.core.model.IGridWorkflow;
import eu.geclipse.core.model.impl.ResourceGridContainer;

public class GridWorkflow
    extends ResourceGridContainer
    implements IGridWorkflow {

  protected GridWorkflow( final IResource resource ) {
    super( resource );
  }

  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getExecutable() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<String> getExecutableArguments() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getStdErrorFileName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getStdErrorUri() throws GridException {
    // TODO Auto-generated method stub
    return null;
  }

  public String getStdInputFileName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getStdInputUri() throws GridException {
    // TODO Auto-generated method stub
    return null;
  }

  public String getStdOutputFileName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getStdOutputUri() throws GridException {
    // TODO Auto-generated method stub
    return null;
  }

}
