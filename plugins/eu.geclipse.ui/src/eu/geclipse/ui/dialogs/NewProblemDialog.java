/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import eu.geclipse.core.GridException;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.SolutionRegistry;
import eu.geclipse.ui.LogExceptionSolution;
import eu.geclipse.ui.UISolutionRegistry;
import eu.geclipse.ui.internal.Activator;

/**
 * The <code>ProblemDialog</code> is the graphical front-end for the
 * g-Eclipse problem reporting mechanism. It essentially behaves like
 * the Eclipse {@link ErrorDialog} but offers the user solutions that
 * come with a specified problem. Nevertheless the
 * <code>ProblemDialog</code> may also be used with ordinary
 * {@link CoreException}s, so it may completely replace the
 * {@link ErrorDialog}.
 */
public class NewProblemDialog extends ErrorDialog {
  
  /**
   * Return code determining that a solution was chosen.
   */
  public static final int SOLVE = 2;

  /**
   * The result type of the dialog execution.
   */
  static int result;
  
  /**
   * The problem that should be displayed.
   */
  final IProblem problem;
  
  /**
   * The solution registry used to map solution IDs.
   */
  private SolutionRegistry solutionRegistry;
  
  private Throwable throwable;
  
  /**
   * Create a new problem dialog from the specfied parameters.
   * 
   * @param parentShell The dialog's parent {@link Shell}.
   * @param dialogTitle The title of the dialog.
   * @param message The message that will be primarily displayed
   * to the user. 
   * @param throwable Any type of exception to be shown to the
   * user. If this is a {@link GridException} the associated
   * {@link IProblem} will be used to build up the dialog. If
   * this is a {@link CoreException} the associated {@link IStatus}
   * will be used to build up the dialog.
   * @param solutionRegistry The {@link SolutionRegistry} that is
   * used to map solution IDs to solutions. If this is <code>null</code>
   * the {@link UISolutionRegistry} is taken as default.
   */
  private NewProblemDialog( final Shell parentShell,
                            final String dialogTitle,
                            final String message,
                            final Throwable throwable,
                            final SolutionRegistry solutionRegistry ) {
    super( parentShell, dialogTitle, message, getStatus( throwable ),
           IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR );
    this.problem
      = ( throwable != null ) && ( throwable instanceof GridException )
      ? ( ( GridException ) throwable ).getProblem()
      : null;
    this.throwable = throwable;
    this.solutionRegistry = solutionRegistry;
    if ( this.solutionRegistry == null ) {
      this.solutionRegistry = UISolutionRegistry.getRegistry( this.getShell() );
    }
  }
  
  /**
   * Static helper method to open a <code>ProblemDialog</code>.
   * 
   * @param parent The dialog's parent {@link Shell}.
   * @param dialogTitle The title of the dialog.
   * @param message The message that will be primarily displayed
   * to the user. 
   * @param throwable Any type of exception to be shown to the
   * user. If this is a {@link GridException} the associated
   * {@link IProblem} will be used to build up the dialog. If
   * this is a {@link CoreException} the associated {@link IStatus}
   * will be used to build up the dialog.
   * @return The return code of the dialog. This may be one of the
   * {@link ErrorDialog}'s standard return codes or {@link #SOLVE}
   * if the dialog was closed with the action of a solution. 
   */
  public static int openProblem( final Shell parent,
                                 final String dialogTitle,
                                 final String message,
                                 final Throwable throwable ) {
    final NewProblemDialog dialog
      = new NewProblemDialog( parent, dialogTitle, message, throwable, null );
    
    Shell execShell = ( parent == null ) ? Display.getDefault().getShells()[0] : parent;
    execShell.getDisplay().syncExec( new Runnable() {
      public void run() {
        result = dialog.open();
      }
    } );
    return result;
  }
  
  /**
   * Static helper method to open a <code>ProblemDialog</code>.
   * 
   * @param parent The dialog's parent {@link Shell}.
   * @param dialogTitle The title of the dialog.
   * @param message The message that will be primarily displayed
   * to the user. 
   * @param gExc A {@link GridException} that will be used to build
   * up the dialog.
   * @param solutionRegistry The {@link SolutionRegistry} that is
   * used to map solution IDs to solutions. If this is <code>null</code>
   * the {@link UISolutionRegistry} is taken as default.
   * @return The return code of the dialog. This may be one of the
   * {@link ErrorDialog}'s standard return codes or {@link #SOLVE}
   * if the dialog was closed with the action of a solution. 
   */
  public static int openProblem( final Shell parent,
                                 final String dialogTitle,
                                 final String message,
                                 final GridException gExc,
                                 final SolutionRegistry solutionRegistry ) {
    final NewProblemDialog dialog
      = new NewProblemDialog( parent, dialogTitle, message, gExc, solutionRegistry );
    Shell execShell = ( parent == null ) ? Display.getDefault().getShells()[0] : parent;
    execShell.getDisplay().syncExec( new Runnable() {
      public void run() {
        result = dialog.open();
      }
    } );
    return result;
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
    
    if ( ( this.problem != null ) || ( this.throwable != null )) {
      
      List< String > reasons
        = this.problem != null
        ? this.problem.getReasons()
        : null;
        
      if ( ( reasons != null ) && ! reasons.isEmpty() ) {
        
        Composite reasonComposite = new Composite( composite, SWT.NONE );
        reasonComposite.setLayout( new GridLayout( 1, false ) );
        
        Label reasonLabel = new Label( reasonComposite, SWT.NONE );
        reasonLabel.setText( "Reasons:" );
        gData = new GridData();
        gData.horizontalAlignment = GridData.BEGINNING;
        reasonLabel.setLayoutData( gData );
        
        for ( String reason : reasons ) {
          
          Label label = new Label( reasonComposite, SWT.NONE );
          label.setText( "- " + reason );
          gData = new GridData();
          gData.horizontalAlignment = GridData.BEGINNING;
          label.setLayoutData( gData );
          
        }
        
      }
    
      List< ISolution > solutions
        = this.problem != null
        ? this.problem.getSolutions( this.solutionRegistry )
        : new ArrayList< ISolution >();
      
      Throwable exc
        = this.problem != null
        ? this.problem.getException()
        : this.throwable;
        
      if ( exc != null ) {
        solutions.add( new LogExceptionSolution( exc ) );
      }
      
      if ( ( solutions != null ) && ( solutions.size() != 0) ) {
        
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
          String text = solution.getText();
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
              solution.solve();
            }
          } );
          
        }
        
      }
      
    }
    
    return composite;
    
  }
  
  private static IStatus getStatus( final Throwable throwable ) {
    IStatus res = null;
    if ( throwable instanceof CoreException ) {
      res = ( ( CoreException ) throwable ).getStatus();
    } else {
      String message = throwable.getMessage();
      if ( message == null ) {
        message = Messages.getString("NewProblemDialog.no_further_info"); //$NON-NLS-1$
      }
      res = new Status( IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        IStatus.OK,
                        message,
                        throwable );
    }
    return res;
  }
  
}
