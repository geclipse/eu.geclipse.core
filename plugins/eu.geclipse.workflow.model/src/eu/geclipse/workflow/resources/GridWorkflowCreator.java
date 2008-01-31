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

import org.eclipse.core.resources.IFile;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridWorkflow;
import eu.geclipse.core.model.impl.AbstractFileElementCreator;

public class GridWorkflowCreator extends AbstractFileElementCreator {
  
  private static final String FILE_EXTENSION = "workflow"; //$NON-NLS-1$

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return IGridJobDescription.class.isAssignableFrom( elementType );
  }

  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    IFile file = ( IFile ) getObject();
    GridWorkflow workflow = new GridWorkflow( file );
    return workflow;
  }
  
  @Override
  protected boolean internalCanCreate( final String fileExtension ) {
    return FILE_EXTENSION.equals( fileExtension );
  }

}
