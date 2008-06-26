/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridSiteConfig;


/**
 * @author nloulloud
 *
 */
public class QueueLocationWizard extends WizardNewFileCreationPage {
  private static final String DEFAULT_EXTENSION = ".qdl"; //$NON-NLS-1$
  private IStructuredSelection iniSelection;

  /**
   * Create a new wizard page instance.
   *
   * @param selection the current object selection
   * @see AddQueueWizard#init(IWorkbench, IStructuredSelection)
   */
  public QueueLocationWizard( final IStructuredSelection selection )
  {
    super( Messages.getString( "NewQueueWizard.FirstPage_Name" ), selection );  //$NON-NLS-1$
    setTitle(  Messages.getString( "NewQueueWizard.FirstPage_Title" ) ); //$NON-NLS-1$
    setDescription( Messages.getString( "NewQueueWizard.FirstPage_Description" ) ); //$NON-NLS-1$
    this.iniSelection = selection;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createControl( final Composite parent ) {
    super.createControl( parent );
    setFileName( Messages.getString( "NewQueueWizard.Default_New_Filename" ) + DEFAULT_EXTENSION ); //$NON-NLS-1$
    setPageComplete( validatePage() );
  }

  /**
   * This method will be invoked, when the "Finish" button is pressed.
   *
   * @see AddQueueWizard#performFinish()
   */
  protected IFile createFile() {
    return createNewFile();
  }
    
  /**
   * Return true, if the file name entered in this page is valid.
   */
  private boolean validateFilename() {
    boolean ret = false;
    if ( null != getFileName() && getFileName().endsWith( DEFAULT_EXTENSION ) )
      ret = true;
    else
      setErrorMessage( Messages.getString( "NewQueueWizard.Default_New_Filename" ) + DEFAULT_EXTENSION ); //$NON-NLS-1$
    return ret;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
   */
  @Override
  protected boolean validatePage() {
    return super.validatePage() && this.validateFilename();

  }
  
  @Override
  protected void initialPopulateContainerNameField() {
    Object obj = this.iniSelection.getFirstElement();
    
    if ( obj instanceof IGridContainer ) {
      IGridElement element = ( IGridElement )obj;
      IGridProject project = element.getProject();
      if( project != null ) {
        IGridElement descriptions = project.getProjectFolder( IGridSiteConfig.class );
        if( descriptions != null ) {
          IPath cPath = descriptions.getPath();
          IPath ePath = element.getPath();
          if( !cPath.isPrefixOf( ePath ) ) {
            element = descriptions;
          }
        }
      }
      super.setContainerFullPath( element.getPath() );
    } else {
      super.initialPopulateContainerNameField();
    }
  }
}
