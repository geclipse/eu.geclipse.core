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

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
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

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.actions.MountAction;
import eu.geclipse.ui.dialogs.ProblemDialog;
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
    
    IGridContainer mountPoint = getMountPoint();
    this.definitionPage = new ConnectionDefinitionWizardPage( mountPoint, this.initialURI );
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
    
    boolean result = true;
    URI uri = this.definitionPage.getURI();
    
    if ( uri != null ) {
      try {
        if ( ConnectionWizard.this.createGlobalConnection ) {
          result = createGlobalConnection( uri );
        } else {
          result = createLocalConnection( uri );
        }
      } catch ( ProblemException pExc ) {
        ProblemDialog.openProblem(
            getShell(),
            "Mount failed",
            "Unable to create connection",
            pExc );
        result = false;
      }
    }
    
    return result;
    
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selection ) {
    this.initialSelection = selection;
  }
  
  protected boolean createGlobalConnection( final URI uri )
      throws ProblemException {
    
    boolean result = true;
    
    ConnectionNameWizardPage page
      = ( ConnectionNameWizardPage ) this.firstPage;
    final String name = page.getConnectionName();
    
    try {
      getContainer().run( true, true, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException ,InterruptedException {
          try {
            MountAction.createGlobalMount( uri, name, monitor );
          } catch ( CoreException pExc ) {
            throw new InvocationTargetException( pExc );
          } finally {
            monitor.done();
          }
        }
      } );
    } catch ( InvocationTargetException itExc ) {
      Throwable t = itExc.getCause();
      if ( t instanceof ProblemException ) {
        throw ( ProblemException ) t;
      }
      if ( t == null ) {
        t = itExc;
      }
      throw new ProblemException(
          ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
          "Unable to create connection",
          t,
          Activator.PLUGIN_ID );
    } catch ( InterruptedException intExc ) {
      result = false;
    }
    
    return result;
    
  }
  
  protected boolean createLocalConnection( final URI uri )
      throws ProblemException {
    
    boolean result = true;
    
    ConnectionLocationWizardPage page
      = ( ConnectionLocationWizardPage ) this.firstPage;
    final IPath path = page.getContainerFullPath().append( page.getFileName() );
    
    try {
      getContainer().run( true, true, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {
          try {
            MountAction.createLocalMount( uri, path, monitor );
          } catch ( CoreException cExc ) {
            throw new InvocationTargetException( cExc );
          }
        }
      } );
    } catch ( InvocationTargetException itExc ) {
      Throwable t = itExc.getCause();
      if ( t instanceof ProblemException ) {
        throw ( ProblemException ) t;
      }
      if ( t == null ) {
        t = itExc;
      }
      throw new ProblemException(
          ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
          "Unable to create connection",
          t,
          Activator.PLUGIN_ID );
    } catch ( InterruptedException intExc ) {
      result = false;
    }
    
    return result;

  }
  
  private IGridContainer getMountPoint() {
    
    IGridContainer result = null;
    
    if ( ( this.initialSelection != null ) && ( this.initialSelection instanceof StructuredSelection ) ) {
      StructuredSelection sSelection = ( StructuredSelection ) this.initialSelection;
      Object object = sSelection.getFirstElement();
      if ( object instanceof IGridContainer ) {
        result = ( IGridContainer ) object;
      } else if ( object instanceof IResource ) {
        IGridElement element = GridModel.getRoot().findElement( ( IResource ) object );
        if ( ( element != null ) && ( element instanceof IGridContainer ) ) {
          result = ( IGridContainer ) element;
        }
      } else if ( object instanceof IAdaptable ) {
        result = ( IGridContainer ) ( ( IAdaptable ) object ).getAdapter( IGridContainer.class );
      }
    }
    
    return result;
    
  }
  
  private void setCurrentErrorMessage( final IWizardPage fromPage ) {
    String errorMessage = fromPage.getErrorMessage();
    WizardPage toPage = ( WizardPage ) getContainer().getCurrentPage();
    toPage.setErrorMessage( errorMessage );
  }
  
}
