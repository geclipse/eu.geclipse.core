/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
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
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.ui.wizards.datavisualization;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


/**
 * @author sgirtel
 *
 */
public class DataVisualizationSetupWizardPage extends WizardPage
  implements Listener
{

  private PipelineWizPage composite = null;
  
  /**
   * @param pageName
   */
  public DataVisualizationSetupWizardPage( String pageName ) {
    super( pageName );
  }

  /**
   * @param pageName
   * @param title
   * @param titleImage
   */
  public DataVisualizationSetupWizardPage( String pageName,
                                           String title,
                                           ImageDescriptor titleImage )
  {
    super( pageName, title, titleImage );
  }


  public void handleEvent( Event event ) {
    validateInput();
    getWizard().getContainer().updateButtons();
  }

  private void validateInput() {
    // TODO
  }

  public void createControl( Composite parent ) {
    if( super.getName()
      .compareToIgnoreCase( Messages.getString( "ContourFilterWizard.pageName" ) ) == 0 ) { //$NON-NLS-1$
      this.composite = new CreatePipelineWizPageContent( parent, SWT.NONE, this );
    } else if( super.getName()
      .compareToIgnoreCase( Messages.getString( "SmoothFilterWizard.pageName" ) ) == 0 ) { //$NON-NLS-1$
      this.composite = new EditPipelineWizPageContent( parent, SWT.NONE, this );
    }
    setPageComplete( false );
    setControl( this.composite );
    validateInput();
  }

  protected void applyToStatusLine( final IStatus status ) {
    String message = status.getMessage();
    if ( message.length() == 0 ) message = null;
    switch ( status.getSeverity() ) {
      case IStatus.OK:
        setErrorMessage( null );
        setMessage( message );
        break;
      case IStatus.WARNING:
        setErrorMessage( null );
        setMessage( message, IMessageProvider.WARNING );
        break;
      case IStatus.INFO:
        setErrorMessage( null );
        setMessage( message, IMessageProvider.INFORMATION );
        break;
      default:
        setErrorMessage( message );
        setMessage( null );
        break;
    }
  }

  public void setInitData( String currentSelection ) {
    // TODO Auto-generated method stub
    
  }
}
