/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Sylva Girtelschmid GUP, JKU - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.visualisation;

import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.IGridVisualisation;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;


/**
 * @author JKU
 *
 */
public class GridVisualisationResource extends ResourceGridContainer
  implements IGridVisualisation
{

  protected GridVisualisationResource( final IResource resource ) {
    super( resource );
    // TODO Auto-generated constructor stub
  }

  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getResourceFileNameExtension() {
    // TODO Auto-generated method stub
    return null;
  }

  public void render( final String fileExtention ) {
    // TODO Auto-generated method stub
  }

  public void validate() throws ProblemException {
    // TODO Auto-generated method stub
  }
}
