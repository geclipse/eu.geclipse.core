/*******************************************************************************
 * Copyright (c) 2006-2009 g-Eclipse Consortium 
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
package eu.geclipse.workflow.ui.internal.actions;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.jsdl.ui.wizards.NewJobWizard;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;


/**
 * @author athandava
 *
 */
public class OpenWorkflowJobDescriptionAction implements IObjectActionDelegate {

  /**
   * The WorkflowJobEditPart that has been selected.
   */
  private WorkflowJobEditPart mySelectedElement;
  String fileName = null;
  
  public void setActivePart( final IAction action, final IWorkbenchPart targetPart ) {
    targetPart.getSite().getShell();
  }

  public void run( final IAction action ) {
    IWorkflowJob job = ( IWorkflowJob )OpenWorkflowJobDescriptionAction.this.mySelectedElement.resolveSemanticElement();    
    String wfFileString = job.getWorkflow().eResource().getURI().toPlatformString( true ); 
    IGridRoot gridModelRoot = GridModel.getRoot(); // Grid Model root
    IFileStore gridModelRootFileStore = gridModelRoot.getFileStore();
    String gridModelRootFileStoreString = gridModelRootFileStore.toString();
    URI uri = URIUtil.toURI(gridModelRootFileStoreString + wfFileString);
    IFile[] workflowIFile = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI( uri );
    String filename = job.getJobDescription();
    IResource res = workflowIFile[0].getParent().findMember( filename );
    if ( filename != null ) {
      if ( (!"".equals( filename )) ) { //$NON-NLS-1$
        IFile[] file = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation( res.getLocation() ); 
        try {
          if( (file.length != 0) && file[ 0 ].exists() ) {
            IDE.openEditor( WorkflowDiagramEditorPlugin.getDefault()
              .getWorkbench()
              .getActiveWorkbenchWindow()
              .getActivePage(), file[ 0 ], true );
          } else {
              // need to handle if file does not exist - indicates corrupted workflow file or missing JSDL
              // TODO implement problem reporting
          }
        } catch( PartInitException partInitException ) {
          WorkflowDiagramEditorPlugin.logException( partInitException );
        }
      } else {
        createNewJobWizard();
      }
    } else {
      createNewJobWizard();
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection ) {
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
  
  private void createNewJobWizard() {
    // if there is no entry in job description property, fire up NewJobWizard
    NewJobWizard newJobWizard = new NewJobWizard();       
    // this bit find the root directory of the workflow
    TransactionalEditingDomain domain = this.mySelectedElement.getEditingDomain();
    ResourceSet resourceSet = domain.getResourceSet();
    Resource res = resourceSet.getResources().get( 0 );
    org.eclipse.emf.common.util.URI wfRootUri = res.getURI(); 
    String wfRootPath = wfRootUri.path();
    String[] dirs = wfRootPath.split( "/" ); //$NON-NLS-1$
    String projectName = dirs[2];
    IFileStore wfRootFileStore = GridModel.getRoot().getFileStore().getChild( projectName ).getChild( "Workflows" ); //$NON-NLS-1$
    newJobWizard.init( PlatformUI.getWorkbench(), new StructuredSelection(wfRootFileStore) );        
    WizardDialog wizard = new WizardDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), newJobWizard);
    wizard.create();
    wizard.open();
  }  
  
}
