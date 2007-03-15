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
 *    Pawel Wolniewicz
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import java.io.InputStream;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridProject;


public abstract class AbstractGridJob  extends AbstractGridContainer implements IGridJob {

  
  private IGridJobID jobID;
  private IGridJobDescription jobDescription;
  private IGridJobStatus jobStatus;
  
  protected AbstractGridJob( final IFile jobFile ) {
    super( );
    
  }

  
  public void cancel() {
    // TODO Auto-generated method stub
  }

  public IGridJobID getID() {
    return jobID;
  }

  public IGridJobDescription getJobDescription() {
    return jobDescription;
  }

  public IGridJobStatus getStatus() {
    return jobStatus;
  }

//  public InputStream getStdErrStream() {
//    // TODO Auto-generated method stub
//    return null;
//  }
//
//  public InputStream getStdOutStream() {
//    // TODO Auto-generated method stub
//    return null;
//  }

  public boolean isLazy() {
    return false;
  }

  public boolean isLocal() {
    return true;
  }

  public boolean isVirtual() {
    return true;
  }

  public IGridElementManager getManager() {
    return GridModel.getJobManager();
  }
  
}
