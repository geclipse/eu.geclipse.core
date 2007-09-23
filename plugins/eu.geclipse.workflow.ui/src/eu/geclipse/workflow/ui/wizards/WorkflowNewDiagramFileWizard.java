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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.editor.WorkflowDiagramEditorUtil;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.Messages;
import eu.geclipse.workflow.ui.part.ModelElementSelectionPage;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowNewDiagramFileWizard extends Wizard {

  /**
   * @generated
   */
  private WizardNewFileCreationPage myFileCreationPage;
  /**
   * @generated
   */
  private ModelElementSelectionPage diagramRootElementSelectionPage;
  /**
   * @generated
   */
  private TransactionalEditingDomain myEditingDomain;

  /**
   * @generated NOT
   */
  public WorkflowNewDiagramFileWizard( URI domainModelURI,
                                       EObject diagramRoot,
                                       TransactionalEditingDomain editingDomain )
  {
    assert domainModelURI != null : "Domain model uri must be specified"; //$NON-NLS-1$
    assert diagramRoot != null : "Doagram root element must be specified"; //$NON-NLS-1$
    assert editingDomain != null : "Editing domain must be specified"; //$NON-NLS-1$
    myFileCreationPage = new WizardNewFileCreationPage( Messages.getString("WorkflowNewDiagramFileWizard_CreationPageName"), //$NON-NLS-1$
                                                        StructuredSelection.EMPTY );
    myFileCreationPage.setTitle( Messages.getString("WorkflowNewDiagramFileWizard_CreationPageTitle") ); //$NON-NLS-1$
    myFileCreationPage.setDescription( NLS.bind( Messages.getString("WorkflowNewDiagramFileWizard_CreationPageDescription"), //$NON-NLS-1$
                                                 WorkflowEditPart.MODEL_ID ) );
    IPath filePath;
    String fileName = domainModelURI.trimFileExtension().lastSegment();
    if( domainModelURI.isPlatformResource() ) {
      filePath = new Path( domainModelURI.trimSegments( 1 )
        .toPlatformString( true ) );
    } else if( domainModelURI.isFile() ) {
      filePath = new Path( domainModelURI.trimSegments( 1 ).toFileString() );
    } else {
      // TODO : use some default path
      throw new IllegalArgumentException( "Unsupported URI: " + domainModelURI ); //$NON-NLS-1$
    }
    myFileCreationPage.setContainerFullPath( filePath );
    myFileCreationPage.setFileName( WorkflowDiagramEditorUtil.getUniqueFileName( filePath,
                                                                                 fileName,
                                                                                 "workflow" ) ); //$NON-NLS-1$
    diagramRootElementSelectionPage = new DiagramRootElementSelectionPage( Messages.getString("WorkflowNewDiagramFileWizard_RootSelectionPageName") ); //$NON-NLS-1$
    diagramRootElementSelectionPage.setTitle( Messages.getString("WorkflowNewDiagramFileWizard_RootSelectionPageTitle") ); //$NON-NLS-1$
    diagramRootElementSelectionPage.setDescription( Messages.getString("WorkflowNewDiagramFileWizard_RootSelectionPageDescription") ); //$NON-NLS-1$
    diagramRootElementSelectionPage.setModelElement( diagramRoot );
    myEditingDomain = editingDomain;
  }

  /**
   * @generated
   */
  public void addPages() {
    addPage( myFileCreationPage );
    addPage( diagramRootElementSelectionPage );
  }

  /**
   * @generated NOT
   */
  public boolean performFinish() {
    List affectedFiles = new LinkedList();
    IFile diagramFile = myFileCreationPage.createNewFile();
    WorkflowDiagramEditorUtil.setCharset( diagramFile );
    affectedFiles.add( diagramFile );
    URI diagramModelURI = URI.createPlatformResourceURI( diagramFile.getFullPath()
                                                           .toString(),
                                                         true );
    ResourceSet resourceSet = myEditingDomain.getResourceSet();
    final Resource diagramResource = resourceSet.createResource( diagramModelURI );
    AbstractTransactionalCommand command = new AbstractTransactionalCommand( myEditingDomain,
                                                                             Messages.getString("WorkflowNewDiagramFileWizard_InitDiagramCommand"), //$NON-NLS-1$
                                                                             affectedFiles )
    {

      protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                                   IAdaptable info )
        throws ExecutionException
      {
        int diagramVID = WorkflowVisualIDRegistry.getDiagramVisualID( diagramRootElementSelectionPage.getModelElement() );
        if( diagramVID != WorkflowEditPart.VISUAL_ID ) {
          return CommandResult.newErrorCommandResult( Messages.getString("WorkflowNewDiagramFileWizard_IncorrectRootError") ); //$NON-NLS-1$
        }
        Diagram diagram = ViewService.createDiagram( diagramRootElementSelectionPage.getModelElement(),
                                                     WorkflowEditPart.MODEL_ID,
                                                     WorkflowDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT );
        diagramResource.getContents().add( diagram );
        diagramResource.getContents().add( diagram.getElement() );
        return CommandResult.newOKCommandResult();
      }
    };
    try {
      OperationHistoryFactory.getOperationHistory()
        .execute( command, new NullProgressMonitor(), null );
      diagramResource.save( WorkflowDiagramEditorUtil.getSaveOptions() );
      WorkflowDiagramEditorUtil.openDiagram( diagramResource );
    } catch( ExecutionException e ) {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( Messages.getString("WorkflowInitDiagramFileAction_UnableToCreateModelAndDiagram"), e ); //$NON-NLS-1$
    } catch( IOException ex ) {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( Messages.getString("WorkflowInitDiagramFileAction_SaveOperationFailedFor") + diagramModelURI, ex ); //$NON-NLS-1$
    } catch( PartInitException ex ) {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( Messages.getString("WorkflowInitDiagramFileAction_UnableToOpenEditor"), ex ); //$NON-NLS-1$
    }
    return true;
  }
  /**
   * @generated
   */
  private static class DiagramRootElementSelectionPage
    extends ModelElementSelectionPage
  {

    /**
     * @generated
     */
    protected DiagramRootElementSelectionPage( String pageName ) {
      super( pageName );
    }

    /**
     * @generated NOT
     */
    protected String getSelectionTitle() {
      return Messages.getString("WorkflowNewDiagramFileWizard_RootSelectionPageSelectionTitle"); //$NON-NLS-1$
    }

    /**
     * @generated NOT
     */
    protected boolean validatePage() {
      if( selectedModelElement == null ) {
        setErrorMessage( Messages.getString("WorkflowNewDiagramFileWizard_RootSelectionPageNoSelectionMessage") ); //$NON-NLS-1$
        return false;
      }
      boolean result = ViewService.getInstance()
        .provides( new CreateDiagramViewOperation( new EObjectAdapter( selectedModelElement ),
                                                   WorkflowEditPart.MODEL_ID,
                                                   WorkflowDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT ) );
      setErrorMessage( result
                             ? null
                             : Messages.getString("WorkflowNewDiagramFileWizard_RootSelectionPageInvalidSelectionMessage") ); //$NON-NLS-1$
      return result;
    }
  }
}
