/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 ******************************************************************************/
package eu.geclipse.workflow.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import eu.geclipse.workflow.ui.editor.WorkflowDiagramEditorUtil;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * @generated
 */
public class WorkflowCreationWizard extends Wizard implements INewWizard {

  /**
   * @generated
   */
  private IWorkbench workbench;
  /**
   * @generated
   */
  protected IStructuredSelection selection;
  /**
   * @generated
   */
  protected WorkflowCreationWizardPage diagramModelFilePage;
  /**
   * @generated
   */
  protected Resource diagram;
  /**
   * @generated
   */
  private boolean openNewlyCreatedDiagramEditor = true;

  /**
   * @generated
   */
  public IWorkbench getWorkbench() {
    return this.workbench;
  }

  /**
   * @generated
   */
  public IStructuredSelection getSelection() {
    return this.selection;
  }

  /**
   * @generated
   */
  public final Resource getDiagram() {
    return this.diagram;
  }

  /**
   * @generated
   */
  public final boolean isOpenNewlyCreatedDiagramEditor() {
    return this.openNewlyCreatedDiagramEditor;
  }

  /**
   * @generated
   */
  public void setOpenNewlyCreatedDiagramEditor( final boolean openNewlyCreatedDiagramEditor )
  {
    this.openNewlyCreatedDiagramEditor = openNewlyCreatedDiagramEditor;
  }

  /**
   * @generated
   */
  public void init( final IWorkbench workbench1, final IStructuredSelection selection1 ) {
    this.workbench = workbench1;
    this.selection = selection1;
    setWindowTitle( Messages.getString("WorkflowCreationWizard_WindowTitle") ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( WorkflowDiagramEditorPlugin.getBundledImageDescriptor( "icons/wizban/NewWorkflowWizard.gif" ) );
    setNeedsProgressMonitor( true );
  }

  /**
   * @generated
   */
  @Override
  public void addPages() {
    this.diagramModelFilePage = new WorkflowCreationWizardPage( Messages.getString( "WorkflowCreationWizard_PageName" ),  //$NON-NLS-1$
                                                                getSelection(), 
                                                                "workflow" );  //$NON-NLS-1$
    this.diagramModelFilePage.setTitle( Messages.getString( "WorkflowCreationWizard_Title" ) ); //$NON-NLS-1$
    this.diagramModelFilePage.setDescription( Messages.getString( "WorkflowCreationWizard_Description" ) ); //$NON-NLS-1$
    addPage( this.diagramModelFilePage );
  }

  /**
   * @generated
   */
  @Override
  public boolean performFinish() {
    IRunnableWithProgress op = new WorkspaceModifyOperation( null ) {

      @Override
      protected void execute( IProgressMonitor monitor )
        throws CoreException, InterruptedException
      {
        IFolder folder = WorkflowCreationWizard.this.diagramModelFilePage.createNewFolder();
        IFile file = folder.getFile( folder.getName() );
        URI uri = URI.createPlatformResourceURI( file.getFullPath().toString(), false );
        WorkflowCreationWizard.this.diagram = WorkflowDiagramEditorUtil.createDiagram( uri,
                                                                                       monitor );
        if( isOpenNewlyCreatedDiagramEditor() && WorkflowCreationWizard.this.diagram != null ) {
          try {
            WorkflowDiagramEditorUtil.openDiagram( WorkflowCreationWizard.this.diagram );
          } catch( PartInitException e ) {
            ErrorDialog.openError( getContainer().getShell(),
                                   Messages.getString( "WorkflowCreationWizard_ErrorOpeningDiagramEditor" ), //$NON-NLS-1$
                                   null,
                                   e.getStatus() );
          }
        }
      }
    };
    try {
      getContainer().run( false, true, op );
    } catch( InterruptedException e ) {
      return false;
    } catch( InvocationTargetException e ) {
      if( e.getTargetException() instanceof CoreException ) {
        ErrorDialog.openError( getContainer().getShell(),
                               Messages.getString( "WorkflowCreationWizard_CreationProblems" ), //$NON-NLS-1$
                               null,
                               ( ( CoreException )e.getTargetException() ).getStatus() );
      } else {
        WorkflowDiagramEditorPlugin.getInstance()
          .logError( Messages.getString( "WorkflowCreationWizard_ErrorCreatingDiagram" ), e.getTargetException() ); //$NON-NLS-1$
      }
      return false;
    }
    return this.diagram != null;
  }
}
