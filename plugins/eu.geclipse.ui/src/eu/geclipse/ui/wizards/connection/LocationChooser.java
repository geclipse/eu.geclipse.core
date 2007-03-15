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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import eu.geclipse.core.connection.ConnectionManager;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;

/**
 * @author katis
 */
public class LocationChooser extends WizardNewFileCreationPage
  implements Listener, IConnectionFirstPage
{

  private IStructuredSelection iniSelection;
  private String iniText;
  private boolean validate = false;

  /**
   * Method to create new {@link LocationChooser} page
   * 
   * @param pageName name of the page
   * @param selection selection passed to this method
   */
  public LocationChooser( final String pageName,
                          final IStructuredSelection selection )
  {
    super( pageName, selection );
    this.setDescription( Messages.getString( "LocationChooser.location_chooser_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "LocationChooser.location_chooser_title" ) ); //$NON-NLS-1$
    this.iniSelection = selection;
  }

  @Override
  public boolean canFlipToNextPage()
  {
    return this.isPageComplete();
  }

  @Override
  protected void initialPopulateContainerNameField()
  {
    if( isInGridProjectView() ) {
      Object obj = this.iniSelection.getFirstElement();
      if( obj instanceof IGridElement ) {
        IGridElement element = ( IGridElement ) obj;
        IGridProject project = element.getProject();
        if ( project != null ) {
          IGridElement connections = project.findChild( IGridProject.DIR_MOUNTS );
          if ( connections != null ) {
            IPath cPath = connections.getPath();
            IPath ePath = element.getPath();
            if ( !cPath.isPrefixOf( ePath ) ) {
              element = connections;
            }
          }
        }
        super.setContainerFullPath( element.getPath() );
      } else {
        super.initialPopulateContainerNameField();
      }
    } else {
      // do nothing
    }
  }

  @Override
  protected String getNewFileLabel()
  {
    // TODO Auto-generated method stub
    return Messages.getString( "LocalizationChooser.new_connection_label" ); //$NON-NLS-1$
  }

  @Override
  public void createControl( final Composite parent )
  {
    Composite mostTopLevel = new Composite( parent, SWT.NONE );
    mostTopLevel.setLayout( new GridLayout() );
    mostTopLevel.setLayoutData( new GridData( GridData.VERTICAL_ALIGN_FILL
                                              | GridData.HORIZONTAL_ALIGN_FILL ) );
    mostTopLevel.setFont( parent.getFont() );
    Label locationTypeLabel = new Label( mostTopLevel, SWT.NONE );
    locationTypeLabel.setText( Messages.getString( "LocationChooser.location_type_label" ) ); //$NON-NLS-1$
    Composite bottomTopLevel = new Composite( mostTopLevel, SWT.NONE );
    bottomTopLevel.setLayout( new GridLayout() );
    bottomTopLevel.setLayoutData( new GridData( GridData.VERTICAL_ALIGN_FILL
                                                | GridData.HORIZONTAL_ALIGN_FILL ) );
    bottomTopLevel.setFont( parent.getFont() );
    super.createControl( bottomTopLevel );
    
    initialPopulateContainerNameField();
    
    setControl( mostTopLevel );
    
    this.validate = true;
  }

  @Override
  protected boolean validatePage()
  {
    boolean result = false;
    
    String message = null;
    if( !isInGridProjectView() ) {
      if( this.getFileName().equals( "" ) ) { //$NON-NLS-1$
        message = Messages.getString( "LocationChooser.connection_name_empty" ); //$NON-NLS-1$
      } else {
        if( ConnectionManager.getManager()
          .getConnectionNames()
          .contains( this.getFileName() ) )
        {
          message = Messages.getString( "LocationChooser.connection_already_exists" ); //$NON-NLS-1$
        } else {
          result = true;
        }
      }
    } else {
      result = super.validatePage();
    
      if( this.getFileName().equalsIgnoreCase( "" ) ) { //$NON-NLS-1$
        result = false;
        message = Messages.getString( "LocationChooser.connection_name_empty" ); //$NON-NLS-1$
      }
      if( result ) {
        IFile file = ResourcesPlugin.getWorkspace()
          .getRoot()
          .getFile( getContainerFullPath().append( "." + getFileName() + ".fs" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        if( file.exists() ) {
          message = Messages.getString( "LocationChooser.connection_already_exists" ); //$NON-NLS-1$
          result = false;
        } else {
          result = true;
        }
      }
    
    }
    this.setErrorMessage( message );
    
    
    return result;
  }

  @Override
  public void handleEvent( final Event event )
  {
//    validate = true;
    if ( this.validate ){
      super.handleEvent( event );
    }
  }

  /**
   * Method for setting <code>super.iniText</code> only in specific
   * circumstances
   * 
   * @param string initial text to set
   */
  public void setInitialText( final String string ) {
    this.iniText = string;
  }

  @Override
  protected InputStream getInitialContents()
  {
    return new ByteArrayInputStream( this.iniText.getBytes() );
  }

  // @Override
  // protected void createAdvancedControls( final Composite parent )
  // {
  // //empty method
  // }
  //
  // @Override
  // protected IStatus validateLinkedResource()
  // {
  // return new Status(IStatus.OK,
  // eu.geclipse.ui.internal.Activator.getDefault().getBundle()
  // .getSymbolicName(), IStatus.OK, "", null); //$NON-NLS-1$
  // }
  private boolean isInGridProjectView() {
    return ( this.iniSelection instanceof TreeSelection );
  }

  public String getConnectionName() {
    return getFileName();
  }
}
