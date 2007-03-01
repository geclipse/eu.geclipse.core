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

import java.net.URI;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * First page of a {@link ConnectionWizard}. Allows user to choose a connection
 * type. Pages that {@link ConnectionWizard} will show to user depend on user's
 * choise on this page
 * 
 * @author katis
 */
public class WizardGridProjectChooserPage extends WizardPage
  implements IConnectionWizardPage, Listener
{

  private TreeViewer newViewer;

  protected WizardGridProjectChooserPage( final String pageName ) {
    super( pageName );
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 15;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    this.newViewer = new TreeViewer( mainComp, SWT.VIRTUAL
                                               | SWT.MULTI
                                               | SWT.V_SCROLL
                                               | SWT.H_SCROLL );
    this.newViewer.setContentProvider( new WorkbenchContentProvider() );
    this.newViewer.setLabelProvider( new WorkbenchLabelProvider() );
    this.newViewer.setInput( ResourcesPlugin.getWorkspace().getRoot() );
    GridData gridData = new GridData( GridData.FILL_BOTH
                                      | SWT.H_SCROLL
                                      | SWT.V_SCROLL );
    gridData.horizontalSpan = 2;
    this.newViewer.getTree().setLayoutData( gridData );
    this.newViewer.getTree().addListener( SWT.Selection, this );
    setControl( mainComp );
  }

  private IResource extractSelection( final ISelection sel ) {
    IResource result = null;
    if( !( sel instanceof IStructuredSelection ) ) {
      // do nothing
    } else {
      IStructuredSelection ss = ( IStructuredSelection )sel;
      Object element = ss.getFirstElement();
      if( element instanceof IResource ) {
        result = ( IResource )element;
      } else {
        if( !( element instanceof IAdaptable ) ) {
          // do nothing
        } else {
          IAdaptable adaptable = ( IAdaptable )element;
          Object adapter = adaptable.getAdapter( IResource.class );
          result = ( IResource )adapter;
        }
      }
    }
    return result;
  }

//   public IPath getProjectPath() {
//   IPath result = null;
//   IResource selection = extractSelection( this.newViewer.getSelection() );
//   if (selection != null){
//   result = selection.getFullPath();
//   }
//   return result;
//   }
   
  @Override
  public boolean canFlipToNextPage()
  {
    String message = null;
    boolean result = false;
    IResource resource = extractSelection( this.newViewer.getSelection() );
    if( resource == null ) {
      message = Messages.getString( "ConnectionWizard.req_container_path" ); //$NON-NLS-1$
    } else {
      if( ( resource.getType() != IResource.FOLDER )
          && ( resource.getType() != IResource.PROJECT ) )
      {
        message = Messages.getString( "ConnectionWizard.req_not_directory_path" ); //$NON-NLS-1$
      }
    }
    setErrorMessage( message );
    return result;
  }

  public URI finish() {
    return null;
  }

  public boolean isLastPage() {
    return false;
  }

  public void handleEvent( final Event event ) {
    getContainer().updateButtons();
  }
}
