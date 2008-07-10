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
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.net.URI;
import java.net.URL;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.internal.Activator;


/**
 * Wizard for the creation of new connections. Can both create local
 * and global connections.
 */
public class ConnectionWizard
    extends Wizard
    implements INewWizard {
  
  protected static final String CONNECTION_PREFIX = "."; //$NON-NLS-1$
  
  protected static final String CONNECTION_SUFFIX = ".fs"; //$NON-NLS-1$
  
  IWizardPage firstPage;
  
  private boolean createGlobalConnection;
  
  private ISelection initialSelection;
  
  private URI initialURI;
  
  private String initialName;
    
  private ConnectionDefinitionWizardPage definitionPage;
  
  /**
   * Create a new connection wizard for local connections.
   */
  public ConnectionWizard() {
    this( null, null, false );
  }
  
  public ConnectionWizard( final URI initialURI,
                           final String initialName ) {
    this( initialURI, initialName, false );
  }
  
  /**
   * Create a new connection wizard.
   * 
   * @param createGlobalConnection If true a connection wizard for
   * the creation of global connections will be initialized.
   */
  public ConnectionWizard( final boolean createGlobalConnection ) {
    this( null, null, createGlobalConnection );
  }
  
  public ConnectionWizard( final URI initialURI,
                           final String initialName,
                           final boolean createGlobalConnection ) {
    URL imgURL = Activator.getDefault().getBundle().getResource( "icons/wizban/newconn_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgURL ) );
    setNeedsProgressMonitor( true );
    this.createGlobalConnection = createGlobalConnection;
    this.initialURI = initialURI;
    this.initialName = initialName;
  }
  
  @Override
  public void addPages() {
    
    if ( this.initialSelection == null ) {
      this.initialSelection = StructuredSelection.EMPTY;
    }
    
    if ( this.createGlobalConnection ) {
      this.firstPage = new ConnectionNameWizardPage( this.initialName );
    } else {
      this.firstPage
        = new ConnectionLocationWizardPage( Messages.getString("ConnectionWizard.location_page_name"), //$NON-NLS-1$
                                            ( IStructuredSelection ) this.initialSelection );
      this.firstPage.setTitle( Messages.getString("ConnectionWizard.location_page_title") ); //$NON-NLS-1$
      this.firstPage.setDescription( Messages.getString("ConnectionWizard.location_page_description") ); //$NON-NLS-1$
      if ( this.initialName != null ) {
        ( ( ConnectionLocationWizardPage ) this.firstPage ).setFileName( this.initialName );
      }
    }
    addPage( this.firstPage );
    
    this.definitionPage = new ConnectionDefinitionWizardPage( this.initialURI );
    addPage( this.definitionPage );
    
  }
  
  @Override
  public boolean canFinish() {
    
    IWizardContainer container = getContainer();
    IWizardPage currentPage = container.getCurrentPage();
    
    return ( ( currentPage != this.firstPage ) || ( this.initialURI != null ) ) && super.canFinish();
    
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
      } catch ( ProblemException pExc ) {
        page.setErrorMessage( pExc.getMessage() );
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
      
      try {
        
        GEclipseURI geclURI = new GEclipseURI( slaveURI );
        URI masterURI = geclURI.toMasterURI();
        IFileStore fileStore = EFS.getStore( masterURI );
        IFileInfo fileInfo = fileStore.fetchInfo();
        
        if ( fileInfo.isDirectory() ) {
          IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder( path );
          folder.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, null );
        } else {
          IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
          file.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, null );
        }

        result = true;
        
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
      
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
