/**
 * 
 */
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
    String filename = job.getJobDescriptionFileName();
    URI fileNameURI = URIUtil.toURI( filename );
    
    //IPath location = new Path("/Users/ash/Documents/work/g-Eclipse/runtime-g-Eclipse-S3Test/AnotherPrject/Job%2520Descriptions/ps.jsdl");
    IFile[] file = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( fileNameURI );
      //.getRoot().findFilesForLocation( location );
    System.out.print( file.length );
    
    try {
      if( (file.length != 0) && file[ 0 ].exists() ) {
        IDE.openEditor( WorkflowDiagramEditorPlugin.getDefault()
          .getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage(), file[ 0 ], true );
      }
    } catch( PartInitException partInitException ) {
      WorkflowDiagramEditorPlugin.logException( partInitException );
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
