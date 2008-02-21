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
 *     Mathias Stuempert - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.providers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.util.MasterMonitor;
import eu.geclipse.ui.internal.Activator;

public class FetchChildrenJob extends Job {
  
  private IGridContainer container;
  
  private IProgressMonitor externalMonitor;
  
  public FetchChildrenJob( final IGridContainer container ) {
    super( "Fetching Children of " + container.getName() );
    this.container = container;
  }
  
  public void setExternalMonitor( final IProgressMonitor monitor ) {
    this.externalMonitor = monitor;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {

    IStatus result = Status.CANCEL_STATUS;
    MasterMonitor mMonitor = new MasterMonitor( monitor, this.externalMonitor );
    
    try {
      this.container.setDirty();
      this.container.getChildren( mMonitor );
      result = Status.OK_STATUS;
    } catch ( GridModelException gmExc ) {
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Fetch Error", gmExc );
    }
    
    return result;
    
  }

}
