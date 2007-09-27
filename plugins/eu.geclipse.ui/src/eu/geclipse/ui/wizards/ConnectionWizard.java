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

import java.net.URI;
import java.net.URL;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.ui.internal.Activator;


public class ConnectionWizard
    extends Wizard
    implements INewWizard {
  
  protected static final String CONNECTION_PREFIX = "."; //$NON-NLS-1$
  
  protected static final String CONNECTION_SUFFIX = ".fs"; //$NON-NLS-1$
  
  IWizardPage firstPage;
  
  private boolean createGlobalConnection;
  
  private ISelection initialSelection;
    
  private ConnectionDefinitionWizardPage definitionPage;
  
  public ConnectionWizard() {
    this( false );
  }
  
  public ConnectionWizard( final boolean createGlobalConnection ) {
    URL imgURL = Activator.getDefault().getBundle().getResource( "icons/wizban/newconn_wiz.gif" );
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgURL ) );
    setNeedsProgressMonitor( true );
    this.createGlobalConnection = createGlobalConnection;
  }
  
  @Override
  public void addPages() {
    
    if ( this.createGlobalConnection ) {
      this.firstPage = new ConnectionNameWizardPage();
    } else {
      this.firstPage
        = new ConnectionLocationWizardPage( Messages.getString("ConnectionWizard.location_page_name"), //$NON-NLS-1$
                                            ( IStructuredSelection ) this.initialSelection );
      this.firstPage.setTitle( Messages.getString("ConnectionWizard.location_page_title") ); //$NON-NLS-1$
      this.firstPage.setDescription( Messages.getString("ConnectionWizard.location_page_description") ); //$NON-NLS-1$
    }
    addPage( this.firstPage );
    
    this.definitionPage = new ConnectionDefinitionWizardPage();
    addPage( this.definitionPage );
    
  }
  
  @Override
  public boolean canFinish() {
    
    IWizardContainer container = getContainer();
    IWizardPage currentPage = container.getCurrentPage();
    
    return ( currentPage != this.firstPage ) && super.canFinish();
    
  }

  @Override
  public String getWindowTitle() {
    return Messages.getString("ConnectionWizard.window_title"); //$NON-NLS-1$
  }
  
  @Override
  public boolean performFinish() {
    
    boolean result = false;
    URI uri = this.definitionPage.getURI();
    
    if ( uri != null ) {
    
      if ( this.createGlobalConnection ) {
        result = createGlobalConnection();
      } else {
        result = createLocalConnection();
      }
    
    }
    
    return result;
    
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selection ) {
    this.initialSelection = selection;
  }
  
  private boolean createGlobalConnection() {
    
    boolean result = false;
    
    ConnectionNameWizardPage page
      = ( ConnectionNameWizardPage ) this.firstPage;
    String name = page.getConnectionName();
    
    URI slaveURI = this.definitionPage.getURI();

    if ( slaveURI != null ) {
      try {
        GEclipseURI geclURI = new GEclipseURI( slaveURI );
        IGridPreferences preferences = GridModel.getPreferences();
        preferences.createGlobalConnection( name, geclURI.toMasterURI() );
        result = true;
      } catch ( GridModelException gmExc ) {
        page.setErrorMessage( gmExc.getMessage() );
      }
    }
    
    if ( ! result ) {
      setCurrentErrorMessage( page );
    }
    
    return result;
    
  }
  
  private boolean createLocalConnection() {
    
    boolean result = false;

    ConnectionLocationWizardPage page
      = ( ConnectionLocationWizardPage ) this.firstPage;
    
    URI slaveURI = this.definitionPage.getURI();
    
    if ( slaveURI != null ) {

      IPath path = page.getContainerFullPath();
      path = path.append( page.getFileName() );
      IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder( path );
      
      try {
        GEclipseURI geclURI = new GEclipseURI( slaveURI );
        folder.createLink( geclURI.toMasterURI(), IResource.ALLOW_MISSING_LOCAL, null );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
      
      result = ( folder != null ) && folder.exists();
      
    }
    
    if ( ! result ) {
      setCurrentErrorMessage( page );
    }
    
    return result;
    
  }
  
  private void setCurrentErrorMessage( final IWizardPage fromPage ) {
    String errorMessage = fromPage.getErrorMessage();
    WizardPage toPage = ( WizardPage ) getContainer().getCurrentPage();
    toPage.setErrorMessage( errorMessage );
  }
  
}
