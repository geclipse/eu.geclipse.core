/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.LogExceptionSolution;
import eu.geclipse.ui.internal.MailToSolution;

public class ProblemDialog extends ErrorDialog {
  
  /**
   * Return code determining that a solution was chosen.
   */
  public static final int SOLVE = 2;
  
  private Throwable exc;
  
  private ProblemDialog( final Shell parentShell,
                         final String dialogTitle,
                         final String message,
                         final Throwable exc ) {
    super( parentShell, dialogTitle, message, getStatus( exc ),
           IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR );
    this.exc = exc;
  }
  
  public static int openProblem( final Shell parent,
                                 final String dialogTitle,
                                 final String message,
                                 final Throwable exc ) {
    
    Shell shell = parent;
    if ( shell == null ) {
      IWorkbench workbench = PlatformUI.getWorkbench();
      IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
      if ( window != null ) { 
        shell = window.getShell();
      }
    }
    
    final ProblemDialog dialog
      = new ProblemDialog( shell, dialogTitle, message, exc );
    
    if ( shell != null ) {
      shell.getDisplay().syncExec( new Runnable() {
        public void run() {
          dialog.open();
        }
      } );
    }
    
    return dialog.getReturnCode();
    
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
      // TODO mathias addAccessibleListeners missing
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
    
    IProblem problem = null;
    if ( ( this.exc != null ) && ( this.exc instanceof ProblemException ) ) {
      problem = ( ( ProblemException ) this.exc ).getProblem();
    }
    
    if ( problem != null ) {
      
      String[] reasons = problem.getReasons();
        
      if ( ( reasons != null ) && ( reasons.length >0 ) ) {
        
        Composite reasonComposite = new Composite( composite, SWT.NONE );
        reasonComposite.setLayout( new GridLayout( 1, false ) );
        
        Label reasonLabel = new Label( reasonComposite, SWT.NONE );
        reasonLabel.setText( Messages.getString("NewProblemDialog.further_reasons") ); //$NON-NLS-1$
        gData = new GridData();
        gData.horizontalAlignment = GridData.BEGINNING;
        reasonLabel.setLayoutData( gData );
        
        for ( String reason : reasons ) {
          
          Label label = new Label( reasonComposite, SWT.NONE );
          label.setText( "- " + reason ); //$NON-NLS-1$
          gData = new GridData();
          gData.horizontalAlignment = GridData.BEGINNING;
          label.setLayoutData( gData );
          
        }
        
      }
      
    }
    
    List< ISolution > solutions = new ArrayList< ISolution >();
    
    if ( problem != null ) {
      List< ISolution > list = Arrays.asList( problem.getSolutions() );
      solutions.addAll( list );
    }
    
    if ( this.exc != null ) {
      solutions.add( new LogExceptionSolution( this.exc ) );
    }
    
    if ( ( problem != null ) && ( problem.getMailTo() != null ) ) {
      solutions.add( new MailToSolution( problem ) );
    }
    
    if ( solutions.size() != 0 ) {
      
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
      
      for ( final ISolution solution : solutions ) {
        
        Label imgLabel = new Label( solutionComposite, SWT.NONE );
        imgLabel.setImage( solutionImage );
        gData = new GridData();
        gData.horizontalAlignment = GridData.CENTER;
        gData.verticalAlignment = GridData.CENTER;
        imgLabel.setLayoutData( gData );
        
        Link link = new Link( solutionComposite, SWT.NONE );
        String text = solution.getDescription();
        if ( solution.isActive() ) {
          link.setText( "<a>" + text + "</a>" ); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
          link.setText( text );
        }
        gData = new GridData();
        gData.horizontalAlignment = GridData.BEGINNING;
        link.setLayoutData( gData );
        link.addSelectionListener( new SelectionAdapter() {
          @SuppressWarnings("synthetic-access")
          @Override
          public void widgetSelected( final SelectionEvent e ) {
            setReturnCode( SOLVE );
            close();
            try {
              solution.solve();
            } catch ( InvocationTargetException itExc ) {
              Throwable cause = itExc.getCause();
              Activator.logException( cause );
            }
          }
        } );
        
      }
      
    }
      
    return composite;
    
  }
  
  private static IStatus getStatus( final Throwable throwable ) {
    
    IStatus result = null;
    
    if ( throwable instanceof CoreException ) {
      result = ( ( CoreException ) throwable ).getStatus();
    }
    
    else {
      String message = throwable.getMessage();
      if ( message == null ) {
        message = "No further information available";
      }
      result = new Status( IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        IStatus.OK,
                        message,
                        throwable );
    }
    
    return result;
    
  }

}
