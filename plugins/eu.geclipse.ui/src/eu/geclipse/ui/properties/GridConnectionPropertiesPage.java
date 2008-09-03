/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

package eu.geclipse.ui.properties;

import java.net.URI;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.widgets.GridConnectionDefinitionComposite;

/**
 * A property page for modifying already existing Grid connections.
 */
public class GridConnectionPropertiesPage extends PropertyPage {
  
  /**
   * The Grid connection composite used inside this property page.
   */
  private GridConnectionDefinitionComposite connectionDefinitionComp;

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
  @Override
  public boolean performOk() {
    
    boolean result = false;
    
    IFolder folder = ( IFolder ) getElement().getAdapter( IFolder.class );
    
    if ( folder != null ) {
      URI slaveURI = this.connectionDefinitionComp.getURI();
      GEclipseURI geclURI = new GEclipseURI( slaveURI );
      try {
        folder.delete( true, null );
        folder.createLink( geclURI.toMasterURI(), IResource.ALLOW_MISSING_LOCAL, null );
        result = true;
      } catch ( CoreException cExc ) {
        ProblemDialog.openProblem(
            getShell(),
            Messages.getString("GridConnectionPropertiesPage.create_link_error_title"), //$NON-NLS-1$
            Messages.getString("GridConnectionPropertiesPage.create_link_error_text"), //$NON-NLS-1$
            cExc );
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.dialogs.PropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
   */
  @Override
  public void setElement( final IAdaptable element ) {
    super.setElement( element );
    updateConnectionDefinitionComposite();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    // TODO mathias correct for the mount point
    this.connectionDefinitionComp = new GridConnectionDefinitionComposite( null, parent, SWT.NULL );
    updateConnectionDefinitionComposite();
    return this.connectionDefinitionComp;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
   */
  @Override
  protected void performDefaults() {
    updateConnectionDefinitionComposite();
    super.performDefaults();
  }
  
  /**
   * Update the Grid connection composite if it is already
   * available, i.e. set the URI of the selected element.
   */
  private void updateConnectionDefinitionComposite() {
    // TODO mathias correct for the mount point
    if ( this.connectionDefinitionComp != null ) {
      IFolder folder = ( IFolder ) getElement().getAdapter( IFolder.class );
      if ( folder != null ) {
        IGridRoot root = GridModel.getRoot();
        IGridElement element = root.findElement( folder );
        if ( ( element != null ) && ( element instanceof IGridConnection ) ) {
          URI uri = ( ( IGridConnection ) element ).getURI();
          this.connectionDefinitionComp.setURI( uri );
          this.connectionDefinitionComp.setEnabled( true );
        } else {
          this.connectionDefinitionComp.setEnabled( false );
        }
      } else {
        this.connectionDefinitionComp.setEnabled( false );
      }
    }
  }
  
}
