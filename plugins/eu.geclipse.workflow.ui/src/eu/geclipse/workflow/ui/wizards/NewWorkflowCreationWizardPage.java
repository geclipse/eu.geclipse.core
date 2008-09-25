/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFolderOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.misc.ResourceAndContainerGroup;

import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.Messages;


/**
 * Main page for a wizard that creates a workflow resource.
 * 
 * @author athandava
 *
 * Many bits of code in this class have been reused from
 * org.eclipse.ui.dialogs.WizardNewFileCreationPage and
 * org.eclipse.ui.dialogs.WizardNewFolderMainPage
 * 
 */
public class NewWorkflowCreationWizardPage extends WizardPage implements Listener {

  private static final int SIZING_CONTAINER_GROUP_HEIGHT = 250; 
  
  private String extension = "workflow";
  private String initialName = "new";
  
  // link target location
  private URI linkTargetPath;
  
  private IStructuredSelection currentSelection;
  
  private IPath initialContainerFullPath;
    
  private ResourceAndContainerGroup resourceGroup;
  
  private IFolder newFolder;
 
  
  /**
   * Constructor which takes the pagename and current selection as arguments.
   *  
   * @param pageName
   * @param selection
   */
  protected NewWorkflowCreationWizardPage( String pageName, 
                                           IStructuredSelection selection ) {
    super( pageName );
    
    URL imgUrl = WorkflowDiagramEditorPlugin.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/NewWorkflowWizard.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  
    this.currentSelection = selection;
    
    setPageComplete(false);    
  }

  /**
   * The method that creates the controls on the page.
   */
  public void createControl( Composite parent ) {
    initializeDialogUnits( parent );
    
    //top level group
    Composite topLevel = new Composite( parent, SWT.NONE );
    topLevel.setFont( parent.getFont() );
    topLevel.setLayout( new GridLayout() );
    topLevel.setLayoutData( new GridData( GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL ) );
    
    PlatformUI.getWorkbench().getHelpSystem().setHelp( topLevel, WorkflowDiagramEditorPlugin.ID + "new_workflow_wizard_page_context" );
   
    resourceGroup = new ResourceAndContainerGroup( topLevel,
                                                   this,
                                                   Messages.getString( "NewWorkflowCreationWizardPage_WorkflowName" ),
                                                   Messages.getString( "NewWorkflowCreationWizardPage_WorkflowNameLabel" ),
                                                   false,
                                                   SIZING_CONTAINER_GROUP_HEIGHT );
    resourceGroup.setAllowExistingResources( false );

    initializePage();
    
    validatePage();
    
    // Show description on opening
    setErrorMessage(null);
    setMessage(null);
    setControl(topLevel);
  }
  
  /**
   * Creates a folder resource handle for the folder with the given workspace
   * path. This method does not create the folder resource; this is the
   * responsibility of <code>createNewFolder</code>.
   * 
   * @param folderPath the path of the folder resource to create a handle for
   * @return the new folder resource handle
   * @see #createNewFolder
   */
  protected IFolder createFolderHandle( IPath folderPath ) {
    return IDEWorkbenchPlugin.getPluginWorkspace()
      .getRoot()
      .getFolder( folderPath );
  }
  
  /**
   * Creates a new folder resource in the selected container and with the
   * selected name. Creates any missing resource containers along the path;
   * does nothing if the container resources already exist.
   * <p>
   * In normal usage, this method is invoked after the user has pressed Finish
   * on the wizard; the enablement of the Finish button implies that all
   * controls on this page currently contain valid values.
   * </p>
   * <p>
   * Note that this page caches the new folder once it has been successfully
   * created; subsequent invocations of this method will answer the same
   * folder resource without attempting to create it again.
   * </p>
   * <p>
   * This method should be called within a workspace modify operation since it
   * creates resources.
   * </p>
   * 
   * @return the created folder resource, or <code>null</code> if the folder
   *         was not created
   */
  public IFolder createNewFolder() {
    if( newFolder != null ) {
      return newFolder;
    }
    
    // create the new folder and cache it if successful
    final IPath containerPath = resourceGroup.getContainerFullPath();
    IPath newFolderPath = containerPath.append( resourceGroup.getResource() );
    final IFolder newFolderHandle = createFolderHandle( newFolderPath );
    IRunnableWithProgress op = new IRunnableWithProgress() {

      public void run( IProgressMonitor monitor ) {
        CreateFolderOperation op = new CreateFolderOperation( newFolderHandle,
                                                              linkTargetPath,
                                                              IDEWorkbenchMessages.WizardNewFolderCreationPage_title );
        try {
          PlatformUI.getWorkbench()
            .getOperationSupport()
            .getOperationHistory()
            .execute( op,
                      monitor,
                      WorkspaceUndoUtil.getUIInfoAdapter( getShell() ) );
        } catch( final ExecutionException e ) {
          getContainer().getShell().getDisplay().syncExec( new Runnable() {

            public void run() {
              if( e.getCause() instanceof CoreException ) {
                ErrorDialog.openError( getContainer().getShell(), // Was
                                                                  // Utilities.
                                                                  // getFocusShell
                                                                  // ()
                                       IDEWorkbenchMessages.WizardNewFolderCreationPage_errorTitle,
                                       null, // no special message
                                       ( ( CoreException )e.getCause() ).getStatus() );
              } else {
                IDEWorkbenchPlugin.log( getClass(),
                                        "createNewFolder()", e.getCause() ); //$NON-NLS-1$
                MessageDialog.openError( getContainer().getShell(),
                                         IDEWorkbenchMessages.WizardNewFolderCreationPage_internalErrorTitle,
                                         NLS.bind( IDEWorkbenchMessages.WizardNewFolder_internalError,
                                                   e.getCause().getMessage() ) );
              }
            }
          } );
        }
      }
    };
    try {
      getContainer().run( true, true, op );
    } catch( InterruptedException e ) {
      return null;
    } catch( InvocationTargetException e ) {
      // ExecutionExceptions are handled above, but unexpected runtime
      // exceptions and errors may still occur.
      IDEWorkbenchPlugin.log( getClass(),
                              "createNewFolder()", e.getTargetException() ); //$NON-NLS-1$
      MessageDialog.openError( getContainer().getShell(),
                               IDEWorkbenchMessages.WizardNewFolderCreationPage_internalErrorTitle,
                               NLS.bind( IDEWorkbenchMessages.WizardNewFolder_internalError,
                                         e.getTargetException().getMessage() ) );
      return null;
    }
    newFolder = newFolderHandle;
    return newFolder;
  }
  
  /**
   * Returns the current full path of the containing resource as entered or
   * selected by the user, or its anticipated initial value.
   * 
   * @return the container's full path, anticipated initial value, or
   *         <code>null</code> if no path is known
   */
  public IPath getContainerFullPath() {
      return resourceGroup.getContainerFullPath();
  }

  /**
   * 
   * @return foldername
   */
  public String getFolderName() {
    if (resourceGroup == null) {
        return initialName;
    }

    return resourceGroup.getResource();
  }
  
  
  /**
   * 
   * @return
   */
  protected IPath getFolderPath() {
    IPath path = getContainerFullPath();
    if( path == null ) {
      path = new Path( "" ); //$NON-NLS-1$
    }
    String fileName = getFolderName();
    if( fileName != null ) {
      path = path.append( fileName );
    }
    return path;
  }
  
  /**
   * The <code>NewWorkflowCreationWizardPage</code> implementation of this
   * <code>Listener</code> method handles all events and enablements for
   * controls on this page.
   */
  public void handleEvent( Event event ) {
    setPageComplete( validatePage() );
  }
  
  /**
   * Initializes this page's controls.
   */
  protected void initializePage() {
      Iterator it = currentSelection.iterator();
      if (it.hasNext()) {
          Object next = it.next();
          IResource selectedResource = null;
          if (next instanceof IResource) {
              selectedResource = (IResource) next;
          } else if (next instanceof IAdaptable) {
              selectedResource = (IResource) ((IAdaptable) next)
                      .getAdapter(IResource.class);
          }
          if (selectedResource != null) {
              if (selectedResource.getType() == IResource.FILE) {
                  selectedResource = selectedResource.getParent();
              }
              if (selectedResource.isAccessible()) {
                  resourceGroup.setContainerFullPath(selectedResource
                          .getFullPath());
              }
          }
      }

      setPageComplete(false);
  }
  
  
  /**
   * Sets the value of this page's container name field, or stores it for
   * future use if this page's controls do not exist yet.
   * 
   * @param path
   *            the full path to the container
   */
  public void setContainerFullPath(IPath path) {
      if (resourceGroup == null) {
          initialContainerFullPath = path;
      } else {
          resourceGroup.setContainerFullPath(path);
      }
  }
  
  /**
   * Set the only file extension allowed for this page's file name field.
   * If this page's controls do not exist yet, store it for future use.
   * <br><br>
   * If a file extension is specified, then it will always be 
   * appended with a '.' to the text from the file name field for 
   * validation when the following conditions are met:
   * <br><br>
   * (1) File extension length is greater than 0
   * <br>
   * (2) File name field text length is greater than 0
   * <br>
   * (3) File name field text does not already end with a '.' and the file 
   *     extension specified (case sensitive)
   * <br><br>
   * The file extension will not be reflected in the actual file
   * name field until the file name field loses focus.
   * 
   * @param value
   *             The file extension without the '.' prefix 
   *             (e.g. 'java', 'xml') 
   * @since 3.3
   */
  public void setFileExtension(String value) {
      if (resourceGroup == null) {
          extension = value;
      } else {
          resourceGroup.setResourceExtension(value);
      }
  }
  
  /**
   * Sets the value of this page's file name field, or stores it for future
   * use if this page's controls do not exist yet.
   * 
   * @param value
   *            new file name
   */
  public void setFileName(String value) {
      if (resourceGroup == null) {
          initialName = value;
      } else {
          resourceGroup.setResource(value);
      }
  }


  
  
  /**
   * Checks and returns whether this page's controls currently all contain valid values.
   * 
   * @return <code>true</code> if all controls are valid, and
   *         <code>false</code> if at least one is invalid
   */
  protected boolean validatePage() {
      boolean valid = true;

      if (!resourceGroup.areAllValuesValid()) {
          // if blank name then fail silently
          if (resourceGroup.getProblemType() == ResourceAndContainerGroup.PROBLEM_RESOURCE_EMPTY
                  || resourceGroup.getProblemType() == ResourceAndContainerGroup.PROBLEM_CONTAINER_EMPTY) {
              setMessage(resourceGroup.getProblemMessage());
              setErrorMessage(null);
          } else {
              setErrorMessage(resourceGroup.getProblemMessage());
          }
          valid = false;
      }
      
      String resourceName = resourceGroup.getResource();
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      IStatus result = workspace.validateName(resourceName, IResource.FILE);
      if (!result.isOK()) {
          setErrorMessage(result.getMessage());
          return false;
      }

      IStatus linkedResourceStatus = null;
      
      // validateLinkedResource sets messages itself
      if (valid
              && (linkedResourceStatus == null || linkedResourceStatus.isOK())) {
          setMessage(null);
          setErrorMessage(null);
          
          //perform "resource exists" check if it was skipped in ResourceAndContainerGroup
          if (resourceGroup.getAllowExistingResources()) {
              String problemMessage = NLS.bind(IDEWorkbenchMessages.ResourceGroup_nameExists, getFolderName());
              IPath resourcePath = getContainerFullPath().append(getFolderName());
              if (workspace.getRoot().getFolder(resourcePath).exists()) {
                  setErrorMessage(problemMessage);
                  valid = false;
              }
              if (workspace.getRoot().getFile(resourcePath).exists()) {
                  setMessage(problemMessage, IMessageProvider.WARNING);
              }
          }
      }
      
      if( extension != null
          && !getFolderPath().toString().endsWith( "." + extension ) ) //$NON-NLS-1$
      {
        setErrorMessage( NLS.bind( "Folder name should have ''{0}'' extension.", //$NON-NLS-1$
                                   extension ) );
        valid = false;
      }
      
      return valid;
  }

}
