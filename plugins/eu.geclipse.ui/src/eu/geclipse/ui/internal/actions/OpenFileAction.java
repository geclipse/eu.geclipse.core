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

package eu.geclipse.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * This action provides support for opening connections with progress monitoring
 * and cancelation.
 */
public class OpenFileAction
    extends SelectionListenerAction {

  /**
   * File size limit for which to show a confirmation dialog.
   */
  private static long SIZE_LIMIT_MB = 10;
  
  /**
   * The workbench page.
   */
  protected IWorkbenchPage workbenchPage;
  
  /**
   * Optional editor descriptor. 
   */
  private IEditorDescriptor editorDescriptor;
  
  /**
   * Create a new action without a dedicated editor descriptor.
   * 
   * @param page The workbench page.
   */
  public OpenFileAction( final IWorkbenchPage page ) {
    this( page, null );
  }

  /**
   * Create a new action with the specified editor descriptor.
   * 
   * @param page The workbench page.
   * @param descriptor The editor descriptor.
   */
  public OpenFileAction( final IWorkbenchPage page, final IEditorDescriptor descriptor ) {
    super( Messages.getString("OpenFileAction.action_label") ); //$NON-NLS-1$
    this.workbenchPage = page;
    this.editorDescriptor = descriptor;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Iterator< ? > iter = getStructuredSelection().iterator();
    while ( iter.hasNext() ) {
      Object next = iter.next();
      if ( next instanceof IGridConnectionElement ) {
        try {
          openConnection( ( IGridConnectionElement ) next );
        } catch ( CoreException cExc ) {
          ProblemDialog.openProblem(
              this.workbenchPage.getWorkbenchWindow().getShell(),
              Messages.getString("OpenFileAction.open_failed_title"), //$NON-NLS-1$
              String.format( Messages.getString("OpenFileAction.open_failed_text"), ( ( IGridConnectionElement ) next ).getName() ), //$NON-NLS-1$
              cExc
          );
        }
      } else if ( next instanceof IAdaptable ) {
        IResource resource = ( IResource ) ( ( IAdaptable ) next ).getAdapter( IResource.class );
        if ( resource instanceof IFile ) {
          openFile( ( IFile ) resource );
        }
      }
    }
  }
  
  /**
   * Open the specified file.
   * 
   * @param file The file to open.
   */
  protected void openFile( final IFile file ) {
    try {
      boolean activate = OpenStrategy.activateOnOpen();
      if ( this.editorDescriptor == null ) {
        IDE.openEditor( this.workbenchPage, file, activate );
      } else {
        this.workbenchPage.openEditor(
            new FileEditorInput(file),
            this.editorDescriptor.getId(),
            activate
        );
      }
    } catch ( PartInitException piExc ) {
      ProblemDialog.openProblem(
          this.workbenchPage.getWorkbenchWindow().getShell(),
          Messages.getString("OpenFileAction.open_failed_title"), //$NON-NLS-1$
          String.format( Messages.getString("OpenFileAction.open_failed_text"), file.getName() ), //$NON-NLS-1$
          piExc
      );
    }
  }
  
  /**
   * Open the specified connection.
   * 
   * @param element The connection to be opened.
   * @throws CoreException If opening fails.
   */
  protected void openConnection( final IGridConnectionElement element )
      throws CoreException {
    
    boolean confirm = true;
    long lengthMB = element.getConnectionFileInfo().getLength()/(1024*1024);
    
    if ( lengthMB > SIZE_LIMIT_MB ) {
      confirm = MessageDialog.openConfirm(
          this.workbenchPage.getWorkbenchWindow().getShell(),
          Messages.getString("OpenFileAction.open_confirm_title"), //$NON-NLS-1$
          String.format( Messages.getString("OpenFileAction.open_confirm_text"), //$NON-NLS-1$
              Long.valueOf( lengthMB ),
              Long.valueOf( SIZE_LIMIT_MB ) )
      );
    }
    
    if ( confirm ) {
    
      Job job = new Job( String.format( Messages.getString("OpenFileAction.opening_progress"), element.getName() ) ) { //$NON-NLS-1$
        @Override
        protected IStatus run( final IProgressMonitor monitor ) {
          SubMonitor sMonitor = SubMonitor.convert( monitor, String.format( Messages.getString("OpenFileAction.opening_progress"), element.getName() ), 10 ); //$NON-NLS-1$
          try {
            sMonitor.subTask( Messages.getString("OpenFileAction.connecting_progress") ); //$NON-NLS-1$
            IFileStore cfs = element.getCachedConnectionFileStore( sMonitor.newChild( 9 ) );
            sMonitor.subTask( Messages.getString("OpenFileAction.opening_editor_progress") ); //$NON-NLS-1$
            try {
              if ( ! sMonitor.isCanceled() ) {
                OpenFileAction.this.workbenchPage.getWorkbenchWindow().getShell().getDisplay().syncExec( new Runnable() {
                  public void run() {
                    openFile( ( IFile ) element.getResource() );
                  }
                } );
                sMonitor.worked( 1 );
              }
            } finally {
              element.releaseCachedConnectionFileStore( cfs );
            }
          } catch ( CoreException cExc ) {
            Activator.logException( cExc );
          } finally {
            sMonitor.done();
          }
          return Status.OK_STATUS;
        }
      };
      job.setUser( true );
      job.schedule();
      
    }
    
  }

}
