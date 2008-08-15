/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import eu.geclipse.traceview.ISourceLocation;

/**
 * 
 *
 */
public class GotoSourceAction extends Action implements IActionDelegate {

  private Object selectedObj;

  public void run( final IAction action ) {
    try {
      if( this.selectedObj instanceof ISourceLocation ) {
        ISourceLocation sourceLocation = ( ISourceLocation )this.selectedObj;
        if( sourceLocation.getSourceFilename() != null ) {
          IPath path = findPath( sourceLocation.getSourceFilename(),
                                 ResourcesPlugin.getWorkspace().getRoot() );
          if (path == null) {
            ErrorDialog.openError( Display.getDefault().getActiveShell(),
                                   Messages.getString("GotoSourceAction.errorTitle"), Messages.getString("GotoSourceAction.fileNotInWorkspace"),  //$NON-NLS-1$ //$NON-NLS-2$
                                   new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.getString("GotoSourceAction.fileNotInWorkspace"))); //$NON-NLS-1$
          } else {
            IFile file = FileBuffers.getWorkspaceFileAtLocation( path );
            IWorkbenchWindow window = PlatformUI.getWorkbench()
              .getActiveWorkbenchWindow();
            IWorkbenchPage page = window.getActivePage();
            IEditorPart editorPart = IDE.openEditor( page, file );
            if( editorPart instanceof ITextEditor ) {
              ITextEditor textEditor = ( ITextEditor )editorPart;
              IEditorInput input = editorPart.getEditorInput();
              IDocument document = textEditor.getDocumentProvider()
                .getDocument( input );
              int offset = document.getLineInformation( sourceLocation.getSourceLineNumber() - 1 )
                .getOffset();
              textEditor.getSelectionProvider()
                .setSelection( new TextSelection( offset, 0 ) );
            }
          }
        } else {
          ErrorDialog.openError( Display.getDefault().getActiveShell(),
                                 Messages.getString("GotoSourceAction.errorTitle"), Messages.getString("GotoSourceAction.noSourceLocation"),  //$NON-NLS-1$ //$NON-NLS-2$
                                 new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.getString("GotoSourceAction.noSourceLocation"))); //$NON-NLS-1$
        }
      }
    } catch( PartInitException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch( CoreException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch( BadLocationException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private IPath findPath( final String filename, final IResource searchPath )
    throws CoreException
  {
    IPath result = null;
    if( searchPath instanceof IContainer && ((IContainer)searchPath).isAccessible() ) {
      for( IResource resource : ( ( IContainer )searchPath ).members() ) {
        if( resource.getName().equals( filename ) ) {
          result = resource.getLocation();
        } else {
          result = findPath( filename, resource );
        }
        if (result != null) break;
      }
    }
    return result;
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    if( selection instanceof StructuredSelection ) {
      StructuredSelection structuredSelection = ( StructuredSelection )selection;
      this.selectedObj = structuredSelection.getFirstElement();
    }
  }
}
