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

package eu.geclipse.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;

public class ConnectionLocationWizardPage
    extends WizardNewFileCreationPage {
  
  private URI initialContent;
  
  private IStructuredSelection initialSelection;

  public ConnectionLocationWizardPage( final String pageName,
                                       final IStructuredSelection selection ) {
    super( pageName, selection );
    this.initialSelection = selection;
  }
  
  /*@Override
  public IFile createNewFile() {
    String filename = getConnectionFilename();
    setFileName( filename );
    return super.createNewFile();
  }*/
  
  public String getConnectionFilename() {
    String filename = getFileName();
    return ConnectionWizard.CONNECTION_PREFIX + filename + ConnectionWizard.CONNECTION_SUFFIX;
  }
  
  @Override
  protected InputStream getInitialContents() {
    InputStream result = null;
    if ( this.initialContent != null ) {
      String string = this.initialContent.toString();
      byte[] bytes = string.getBytes();
      result = new ByteArrayInputStream( bytes );
    }
    return result;
  }
  
  @Override
  protected String getNewFileLabel() {
    return Messages.getString("ConnectionLocationWizardPage.connection_name"); //$NON-NLS-1$
  }
  
  @Override
  protected void initialPopulateContainerNameField() {
    
    IGridElement element = null;
    
    if ( ( this.initialSelection != null ) && ! this.initialSelection.isEmpty() ) {
      for ( Object o : this.initialSelection.toList() ) {
        if ( o instanceof IGridElement ) {
          IGridProject project = ( ( IGridElement ) o ).getProject();
          IGridElement mountDir = project.getProjectFolder( IGridConnection.class );
          for ( IGridElement e = ( IGridElement ) o ; e != null ; e = e.getParent() ) {
            if ( e == mountDir ) {
              element = ( IGridElement ) o;
              break;
            }
          }
          if ( element == null ) {
            element = mountDir;
          }
        }
      }
    }
    
    if ( element != null ) {
      setContainerFullPath( element.getPath() );
    } else {
      super.initialPopulateContainerNameField();
    }
    
    validatePage();
    
  }
  
  protected void setInitialContent( final URI uri ) {
    this.initialContent = uri;
  }
  
  @Override
  protected boolean validatePage() {
    
    boolean result = super.validatePage();
    
    if ( result ) {
    
      IPath path = getContainerFullPath(); 
      String file = getConnectionFilename();
      IPath filepath = path.append( file );
      
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      IWorkspaceRoot root = workspace.getRoot();
      if ( root.exists( filepath ) ) {
        result = false;
        setErrorMessage( Messages.getString("ConnectionLocationWizardPage.connection_already_exists") ); //$NON-NLS-1$
      }
      
    }
    
    return result;
    
  }
  
}
