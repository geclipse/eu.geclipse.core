/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IConnectionManager;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.dialogs.Solution;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

/**
 * Implementation of a wizard to create new connection to a file system.
 * Connections may be of different types. Different connections are provided by
 * contributors with wizard pages to create those connections. Those wizard
 * pages are used to create ConnectionWizard.
 * 
 * @author katis
 */
public class ConnectionWizard extends Wizard implements INewWizard {
  
  /**
   * Name of a file in which connections' data are stored
   */
  public static String ID_FILESYSTEMS = ".filesystems"; //$NON-NLS-1$
  protected IFile propertiesFile;
  IConnectionFirstPage firstPage;
  IStructuredSelection selection;
  private ExtPointWizardSelectionListPage startPage;
  
  
  /**
   * Creates new wizard
   */
  public ConnectionWizard() {
    super();
    // this.propertiesFile = properties;
    setForcePreviousAndNextButtons( true );
    setNeedsProgressMonitor( true );
    setWindowTitle( Messages.getString( "ConnectionWizard.window_title" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newconn_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  public void init( final IWorkbench workbench,
                    final IStructuredSelection selectionS ) {
    this.selection = selectionS;
    // if( selection != null ) {
    // Object obj = selection.getFirstElement();
    // if ( !( obj instanceof IResource ) && ( obj instanceof IAdaptable ) ) {
    // obj = ( ( IAdaptable ) obj ).getAdapter( IResource.class );
    // }
    // if( obj instanceof IFile ) {
    // IFile file = ( IFile )obj;
    // if( !file.getName().equals( ID_FILESYSTEMS ) ) {
    // obj = file.getParent();
    // } else {
    // this.propertiesFile = file;
    // }
    // }
    // if( obj instanceof IContainer ) {
    // IContainer container = ( IContainer )obj;
    // IPath path = new Path( ID_FILESYSTEMS );
    // this.propertiesFile = container.getFile( path );
    // }
    // }
  }

  boolean isInGridProjectView( final IStructuredSelection structurdSelection ) {
    return ( structurdSelection instanceof TreeSelection );
  }
  
  @Override
  public void addPages() {
    //locationPage is different when Wizard is started from Grid Poject View or from gExplorer View
    this.firstPage = null;
    if ( isInGridProjectView( this.selection ) ) {
      this.firstPage = new LocationChooser( Messages.getString( "ConnectionWizard.location_page_name" ), this.selection ); //$NON-NLS-1$
    } else {
      this.firstPage = new ConnectionNameChooser( Messages.getString( "ConnectionWizard.name_page_name" )); //$NON-NLS-1$
    }
    addPage( this.firstPage );
    this.startPage = new ExtPointWizardSelectionListPage(
        Messages.getString( "ConnectionWizard.start_page_name" ), //$NON-NLS-1$
        Extensions.CONNECTION_WIZARD_POINT, 
        Messages.getString( "ConnectionWizard.start_page_title" ), //$NON-NLS-1$
        Messages.getString( "ConnectionWizard.start_page_description" ) ); //$NON-NLS-1$
    this.startPage.setInitData( this );
    addPage( this.startPage );
  }

  @Override
  public boolean performFinish() {
    return false;
  }
  
  /**
   * Operations for connection creation common to all connection types.
   * @param fileSystemUri URI of the new connection
   * @return true if successful, false otherwise
   */
  public boolean commonPerformFinish( final URI fileSystemUri ) {
    boolean result = true;
    try {
      getContainer().run( false, false, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException  {
          if( isInGridProjectView(ConnectionWizard.this.selection) ) {
            ConnectionWizard.this.firstPage.setFileName(
                "." + ConnectionWizard.this.firstPage.getConnectionName() + ".fs" ); //$NON-NLS-1$ //$NON-NLS-2$
            ConnectionWizard.this.firstPage.setInitialText(
                fileSystemUri.toString() );
            ConnectionWizard.this.firstPage.createNewFile();
            // File cont =
            // ConnectionWizard.this.localizationPage.getContainerFullPath().toFile();
            // ConnectionWizard.this.propertiesFile = cont;
            // manager.createAndSaveConnection( desc,
            // ConnectionWizard.this.propertiesFile,
            // ConnectionWizard.this.localizationPage.getFileName() );
          } else {
            /*ConnectionDescription desc = new ConnectionDescription( fileSystemUri, null );
            ConnectionManager manager = ConnectionManager.getManager();
            manager.createAndSaveConnection( desc,
                                             ConnectionWizard.this.propertiesFile,
                                             ConnectionWizard.this.firstPage.getConnectionName() );
                                             */
            
            String filename
              = "." + ConnectionWizard.this.firstPage.getConnectionName() + ".fs";
            
            IConnectionManager connectionManager = GridModel.getConnectionManager();
            IFileStore fileStore = connectionManager.getFileStore();
            IFileStore child = fileStore.getChild( filename );
            
            try {
              OutputStream oStream = child.openOutputStream( EFS.NONE, null );
              oStream.write( fileSystemUri.toString().getBytes() );
              oStream.close();
            } catch ( CoreException cExc ) {
              throw new InvocationTargetException( cExc );
            } catch( IOException ioExc ) {
              throw new InvocationTargetException( ioExc );
            }
            
            List< IGridElementCreator > standardCreators = GridModel.getStandardCreators();
            IGridElementCreator connectionCreator = null;
            
            for ( IGridElementCreator creator : standardCreators ) {
              if ( creator.canCreate( child ) ) {
                connectionCreator = creator;
                break;
              }
            }
            
            if ( connectionCreator != null ) {
              try {
                connectionManager.create( connectionCreator );
              } catch( GridModelException gmExc ) {
                throw new InvocationTargetException( gmExc );
              }
            }
            
            else {
              // TODO mathias proper error handling
            }
            
          }
        }
      } );
    } catch( InvocationTargetException itExc ) {
      eu.geclipse.ui.internal.Activator.logException( itExc );
    } catch( InterruptedException intExc ) {
      // do nothing just log
      eu.geclipse.ui.internal.Activator.logException( intExc );
    }
    return result;
  } 
}
