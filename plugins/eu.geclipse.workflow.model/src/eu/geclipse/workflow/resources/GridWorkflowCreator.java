/*******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

import org.eclipse.core.resources.IFolder;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.workflow.IGridWorkflowDescription;

/**
 * 
 * @author ash
 */
public class GridWorkflowCreator extends AbstractGridElementCreator {

  private static final String FILE_EXTENSION = "workflow"; //$NON-NLS-1$

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return elementType.isAssignableFrom( IGridWorkflowDescription.class );
  }

  public IGridElement create( final IGridContainer parent )
    throws ProblemException
  {
    IFolder folder = ( IFolder )getObject();
    GridWorkflow workflow = new GridWorkflow( folder );
    return workflow;
  }

  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    boolean result = false;
    if( fromObject instanceof IFolder ) {
      String extension = ( ( IFolder )fromObject ).getFileExtension();
      if( FILE_EXTENSION.equalsIgnoreCase( extension ) ) {
        result = true;
      }
    }
    return result;
  }
}
