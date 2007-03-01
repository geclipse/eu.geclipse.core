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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.Preferences;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.AbstractGridContainer;

/**
 * This is the grid model class that represents any project in the
 * workspace. This has not to be necessarily a grid project. Use the
 * {@link #isGridProject()} method to determine if this is really a
 * grid project.
 */
public class GridProject
    extends AbstractGridContainer
    implements IGridProject {

  /**
   * Default constructor that constructs a grid project out of an
   * ordinary <code>IProject</code> object.
   *  
   * @param project The <code>IProject</code> that corresponds to this
   * grid project.
   */
  protected GridProject( final IProject project ) {
    super( project );
    loadProjectProperties( project );
  }
  
  @Override
  public IGridElement[] getChildren( final IProgressMonitor monitor ) {
    IGridElement[] children = super.getChildren( monitor );
    if ( !hasVo( children ) ) {
      loadProjectProperties( ( IProject ) getResource() );
      children = super.getChildren( monitor );
    }
    return children;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#isGridProject()
   */
  public boolean isGridProject() {
    // TODO mathias
    return false;
  }
  
  public boolean isLazy() {
    return false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isVirtual()
   */
  public boolean isVirtual() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#isOpen()
   */
  public boolean isOpen() {
    return ( ( IProject ) getResource() ).isOpen();
  }
  
  private boolean hasVo( final IGridElement[] children ) {
    boolean result = false;
    for ( IGridElement child : children ) {
      if ( child instanceof VoWrapper ) {
        result = true;
        break;
      }
    }
    return result;
  }
  
  private void loadProjectProperties( final IProject project ) {
    IScopeContext projectScope = new ProjectScope( project );
    Preferences projectNode = projectScope.getNode( "eu.geclipse.core" );
    String voName = projectNode.get( "vo", null );
    IVirtualOrganization vo = null;
    VoManager voManager = VoManager.getManager();
    if ( voName != null ) {
      vo = ( IVirtualOrganization ) voManager.findChild( voName );
      if ( vo != null ) {
        addElement( new VoWrapper( this, vo ) );
      }
    }
  }

}
