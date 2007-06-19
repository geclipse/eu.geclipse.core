/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ 
 * Contributors:
 * Mathias Stumpert - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.internal.model;

import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.IGridWorkflow;
import eu.geclipse.core.model.impl.ResourceGridContainer;

public class GridWorkflow
    extends ResourceGridContainer
    implements IGridWorkflow {

  protected GridWorkflow( final IResource resource ) {
    super( resource );
  }

}
