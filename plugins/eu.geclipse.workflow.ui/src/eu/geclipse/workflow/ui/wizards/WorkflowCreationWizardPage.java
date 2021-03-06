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


import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;

/**
 * @generated
 */
public class WorkflowCreationWizardPage extends WizardNewFolderMainPage {

  /**
   * @generated
   */
  private final String fileExtension;


  /**
   * @generated
   */
  public WorkflowCreationWizardPage( final String pageName,
                                     final IStructuredSelection selection,
                                     final String fileExtension )
  {
    super( pageName, selection );
    this.fileExtension = fileExtension;
  }

  /**
   * Override to create files with this extension.
   * 
   * @generated
   */
  protected String getExtension() {
    return this.fileExtension;
  }

  /**
   * @generated
   */
/*  public URI getURI() {
    return URI.createPlatformResourceURI( getFolderPath().toString(), false );
  }*/

  /**
   * @generated
   */
/*  protected IPath getFolderPath() {
    IPath path = getContainerFullPath();
    if( path == null ) {
      path = new Path( "" ); //$NON-NLS-1$
    }
    String fileName = getFileName();
    if( fileName != null ) {
      path = path.append( fileName );
    }
    return path;
  }*/

  /**
   * @generated
   */
/*  public void createControl( Composite parent ) {
    super.createControl( parent );
    setFileName( WorkflowDiagramEditorUtil.getUniqueFolderName( getContainerFullPath(),
                                                              getFileName(),
                                                              getExtension() ) );
    setPageComplete( validatePage() );
  }*/

  /**
   * @generated
   */
/*  protected boolean validatePage() {
    if( !super.validatePage() ) {
      return false;
    }
    String extension = getExtension();
    if( extension != null
        && !getFilePath().toString().endsWith( "." + extension ) ) //$NON-NLS-1$
    {
      setErrorMessage( NLS.bind( "File name should have ''{0}'' extension.", //$NON-NLS-1$
                                 extension ) );
      return false;
    }
    return true;
  }*/
}