/*******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 *     - David Johnson
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import java.net.URI;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;


/**
 * @author athandava
 *
 */
public class OpenWorkflowJobWithJSDLEditor implements IObjectActionDelegate {

  /**
   * The WorkflowJobEditPart that has been selected.
   */
  private WorkflowJobEditPart mySelectedElement;
  
  /**
   * 
   */
  String fileName = null;
  
  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    targetPart.getSite().getShell();
  }

  public void run( IAction action ) {
    IWorkflowJob job = ( IWorkflowJob )OpenWorkflowJobWithJSDLEditor.this.mySelectedElement.resolveSemanticElement();
    String filename = job.getJobDescription();
    if ( filename != null ) {
      URI fileNameURI = URIUtil.toURI( filename );
      IFile[] file = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( fileNameURI ); 
      try {
        if( (file.length != 0) && file[ 0 ].exists() ) {
          IDE.openEditor( WorkflowDiagramEditorPlugin.getDefault()
            .getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage(), file[ 0 ], true );
        } else {
            // need to handle if file does not exist
        }
      } catch( PartInitException partInitException ) {
        WorkflowDiagramEditorPlugin.logException( partInitException );
      }
    }
  }

  public void selectionChanged( IAction action, ISelection selection ) {
    this.mySelectedElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      if( structuredSelection.size() == 1
          && structuredSelection.getFirstElement() instanceof WorkflowJobEditPart )
      {
        this.mySelectedElement = ( WorkflowJobEditPart )structuredSelection.getFirstElement();
      }
    }
    action.setEnabled( isEnabled() );
  }

  private boolean isEnabled() {
    return this.mySelectedElement != null;
  }
  
}
