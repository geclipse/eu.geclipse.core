/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *****************************************************************************/

package eu.geclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import eu.geclipse.ui.internal.Activator;

/**
 * This dialog class extends the jface <code>ErrorDialog</code> and gives the 
 * programmer the possibility to specify solutions for the error that is
 * presented in the dialog. Solutions are therefore defined with the help of
 * the {@link eu.geclipse.ui.dialogs.Solution} class.
 * 
 * @author stuempert-m
 */
public class ProblemDialog extends ErrorDialog {
  
  /**
   * Return code determining that a solution was chosen.
   */
  public static final int SOLVE = 2;
  
  /**
   * The list of currently available solutions.
   */
  private List< Solution > solutions = new ArrayList< Solution >();

  /**
   * Construct a new <code>ProblemDialog</code>. See <code>org.eclipse.jface.dialogs.ErrorDialog</code>
   * for details.
   * 
   * @param parentShell The parent shell of this dialog.
   * @param dialogTitle The dialog title.
   * @param message A message that is presented to the user.
   * @param status A status that is used to display further information.
   */
  public ProblemDialog( final Shell parentShell,
                        final String dialogTitle,
                        final String message,
                        final IStatus status ) {
    super( parentShell, dialogTitle, message, status,
           IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR );
  }
  
  /**
   * Add a solution to this dialog. The solutions will be displayed in the order they
   * were added to the dialog.
   * 
   * @param solution The {@link eu.geclipse.ui.dialogs.Solution} that should be added.
   */
  public void addSolution( final Solution solution ) {
    this.solutions.add( solution );
  }
  
  /**
   * Static method for opening a problem dialog.
   * 
   * @param parent The parent shell.
   * @param dialogTitle The title of this dialog.
   * @param message A message that will be displayed to the user.
   * @param status A status that will be used to display further information.
   * @param solutions A list of solutions.
   * @return The return code. This can be either Dialog.OK, Dialog.CANCEL or ProblemDialog.SOLVE.
   */
  public static int openProblem( final Shell parent,
                                 final String dialogTitle,
                                 final String message,
                                 final IStatus status,
                                 final Solution[] solutions ) {
    ProblemDialog dialog
      = new ProblemDialog( parent, dialogTitle, message, status );
    if ( solutions != null ) {
      for ( int i = 0 ; i < solutions.length ; i++ ) {
        dialog.addSolution( solutions[i] );
      }
    }
    return dialog.open();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IconAndMessageDialog#createMessageArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createMessageArea( final Composite parent ) {
    
    GridData gData;
    
    Image image = getImage();
    if ( image != null ) {
      this.imageLabel = new Label( parent, SWT.NULL );
      image.setBackground( this.imageLabel.getBackground() );
      this.imageLabel.setImage( image );
      // XXX addAccessibleListeners missing
      this.imageLabel.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_CENTER
                                                   | GridData.VERTICAL_ALIGN_BEGINNING) );
    }
    
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayoutData( new GridData( GridData.GRAB_HORIZONTAL
                                           | GridData.HORIZONTAL_ALIGN_FILL
                                           | GridData.VERTICAL_ALIGN_BEGINNING ) );
    composite.setLayout( new GridLayout( 1, false ) );

    if (this.message != null) {
      this.messageLabel = new Label( composite, getMessageLabelStyle() );
      this.messageLabel.setText( this.message );
      gData = new GridData( GridData.GRAB_HORIZONTAL
                            | GridData.HORIZONTAL_ALIGN_FILL
                            | GridData.VERTICAL_ALIGN_BEGINNING);
      gData.widthHint = convertHorizontalDLUsToPixels( IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH );
      this.messageLabel.setLayoutData( gData );
    }
    
    if ( !this.solutions.isEmpty() ) {
      
      ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
      Image solutionImage = imgReg.get( "solution" ); //$NON-NLS-1$
      
      Composite solutionComposite = new Composite( composite, SWT.NONE );
      solutionComposite.setLayout( new GridLayout( 2, false ) );
      
      Label solutionLabel = new Label( solutionComposite, SWT.NONE );
      solutionLabel.setText( Messages.getString( "ProblemDialog.solutions_label" ) ); //$NON-NLS-1$
      gData = new GridData();
      gData.horizontalAlignment = GridData.BEGINNING;
      gData.horizontalSpan = 2;
      solutionLabel.setLayoutData( gData );
      
      for ( final Solution solution : this.solutions ) {
        
        Label imgLabel = new Label( solutionComposite, SWT.NONE );
        imgLabel.setImage( solutionImage );
        gData = new GridData();
        gData.horizontalAlignment = GridData.CENTER;
        gData.verticalAlignment = GridData.CENTER;
        imgLabel.setLayoutData( gData );
        
        Link link = solution.createLink( solutionComposite );
        gData = new GridData();
        gData.horizontalAlignment = GridData.BEGINNING;
        link.setLayoutData( gData );
        link.addSelectionListener( new SelectionAdapter() {
          @SuppressWarnings("synthetic-access")
          @Override
          public void widgetSelected( final SelectionEvent e ) {
            setReturnCode( SOLVE );
            close();
            solution.solve();
          }
        } );
        
      }
      
    }
    
    return composite;
    
  }
  
}
