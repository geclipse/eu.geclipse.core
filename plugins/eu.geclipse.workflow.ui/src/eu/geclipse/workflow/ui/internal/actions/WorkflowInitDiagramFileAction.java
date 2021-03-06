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
 ******************************************************************************/
package eu.geclipse.workflow.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.editor.WorkflowDiagramEditorUtil;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.Messages;
import eu.geclipse.workflow.ui.wizards.WorkflowNewDiagramFileWizard;

/**
 * @generated
 */
public class WorkflowInitDiagramFileAction implements IObjectActionDelegate {

  /**
   * @generated
   */
  private IWorkbenchPart targetPart;
  /**
   * @generated
   */
  private URI domainModelURI;

  /**
   * @generated
   */
  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    this.targetPart = targetPart;
  }

  /**
   * @generated
   */
  public void selectionChanged( IAction action, ISelection selection ) {
    domainModelURI = null;
    action.setEnabled( false );
    if( selection instanceof IStructuredSelection == false || selection.isEmpty() )
    {
      return;
    }
    IFile file = ( IFile )( ( IStructuredSelection )selection ).getFirstElement();
    domainModelURI = URI.createPlatformResourceURI( file.getFullPath().toString(), true );
    action.setEnabled( true );
  }

  /**
   * @generated
   */
  private Shell getShell() {
    return targetPart.getSite().getShell();
  }

  /**
   * @generated NOT
   */
  public void run( IAction action ) {
    TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();
    ResourceSet resourceSet = new ResourceSetImpl();
    EObject diagramRoot = null;
    try {
      Resource resource = resourceSet.getResource( domainModelURI, true );
      diagramRoot = resource.getContents().get( 0 );
    } catch( WrappedException ex ) {
      WorkflowDiagramEditorPlugin.getInstance().logError( "Unable to load resource: " + domainModelURI, ex ); //$NON-NLS-1$
    }
    if( diagramRoot == null ) {
      ProblemDialog.openProblem( getShell(),
          Messages.getString("WorkflowInitDiagramFileAction_InitDiagramFileResourceErrorDialogTitle"), //$NON-NLS-1$
          Messages.getString("WorkflowInitDiagramFileAction_InitDiagramFileResourceErrorDialogMessage"), //$NON-NLS-1$
          null );
      return;
    }
    Wizard wizard = new WorkflowNewDiagramFileWizard( domainModelURI,
                                                      diagramRoot,
                                                      editingDomain );
    wizard.setWindowTitle( NLS.bind( Messages.getString("WorkflowInitDiagramFileAction_InitDiagramFileWizardTitle"), //$NON-NLS-1$
                                     WorkflowEditPart.MODEL_ID ) );
    WorkflowDiagramEditorUtil.runWizard( getShell(), wizard, "InitDiagramFile" ); //$NON-NLS-1$
  }
}
