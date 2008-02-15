/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IStorableElement;


/**
 * Properties for the {@link GenericVirtualOrganization}.
 */
public class GenericVoProperties
    extends AbstractGridElement
    implements IStorableElement {
  
  /**
   * Name to reference the properties.
   */
  public static final String NAME = ".generic_vo_properties"; //$NON-NLS-1$
  
  private GenericVirtualOrganization vo;
  
  GenericVoProperties( final GenericVirtualOrganization vo ) {
    this.vo = vo;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return NAME;
  }

  public IGridContainer getParent() {
    return this.vo;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }
  
  public void load() throws GridModelException {
    // TODO Auto-generated method stub

  }

  public void save() throws GridModelException {
    IFileStore fileStore = getFileStore();
    try {
      OutputStream oStream = fileStore.openOutputStream( EFS.NONE, null );
      OutputStreamWriter osWriter = new OutputStreamWriter( oStream );
      BufferedWriter bWriter = new BufferedWriter( osWriter );
      bWriter.write( this.vo.getName() );
      bWriter.close();
    } catch ( CoreException cExc ) {
      throw new GridModelException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                    cExc,
                                    Activator.PLUGIN_ID );
    } catch ( IOException ioExc ) {
      throw new GridModelException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                    ioExc,
                                    Activator.PLUGIN_ID );
    }
  }

}
