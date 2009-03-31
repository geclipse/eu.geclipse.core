/******************************************************************************
 * Copyright (c) 2008-2009 g-Eclipse consortium 
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
package eu.geclipse.workflow.resources;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.workflow.IGridWorkflowJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;

/**
 * Wrapper to emf implementation
 */
public class GridWorkflowJobDescription implements IGridWorkflowJobDescription {

  private IWorkflowJob jobImpl;

  GridWorkflowJobDescription( final IWorkflowJob jobImpl ) {
    super();
    this.jobImpl = jobImpl;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridWorkflowJob#getDescription()
   */
  public Path getDescriptionPath() {
    Path path = null;
    
    String wfFileString = this.jobImpl.getWorkflow().eResource().getURI().toPlatformString( true ); 
    IGridRoot gridModelRoot = GridModel.getRoot(); // Grid Model root
    IFileStore gridModelRootFileStore = gridModelRoot.getFileStore();
    String gridModelRootFileStoreString = gridModelRootFileStore.toString();
    URI uri = URIUtil.toURI(gridModelRootFileStoreString + wfFileString);
    IFile[] workflowIFile = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( uri );
    String filename = this.jobImpl.getJobDescription();
    IResource res = workflowIFile[0].getParent().findMember( filename );
    String pathString = res.getLocation().toOSString();
    if( pathString != null ) {
      path = new Path( pathString );
    }
    
    return path;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridWorkflowJob#getName()
   */
  public String getName() {
    return this.jobImpl.getName();
  }
}
