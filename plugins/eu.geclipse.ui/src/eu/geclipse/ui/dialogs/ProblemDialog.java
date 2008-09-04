/*****************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse Consortium
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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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
import eu.geclipse.ui.internal.ReportProblemSolution;
import eu.geclipse.ui.internal.layout.PackData;
import eu.geclipse.ui.internal.layout.PackLayout;


/**
 * The ProblemDialog is the user-friendly way of g-Eclipse of reporting
 * problems to the user. It displays not only the description of the problem
 * but also the registered solutions that could potentially solve the problem.
 * The active solutions are clickable and perform some action, like generating
 * a problem report or logging the exception.
 */
public class ProblemDialog extends ErrorDialog {

  /**
   * Return code determining that a solution was chosen.
   */
  public static final int SOLVE = 2;

  private final Throwable exc;
  
  private ProblemDialog( final Shell parentShell,
                         final String dialogTitle,
                         final String message,
                         final Throwable exc ) {
    super( parentShell, dialogTitle, message, getStatus( exc ),
           IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR );
    this.message = message;
    this.exc = exc;
  }

  /**
   * Convenience static method for opening a {@link ProblemDialog}. This
   * method has to be called from the UI thread. The parent {@link Shell}
   * may be <code>null</code>. In this case a default {@link Shell} is
   * chosen. Nevertheless it is recommended to not call this method
   * without a valid {@link Shell}. 
   * 
   * @param parent The parent {@link Shell} of the dialog.
   * @param dialogTitle The dialog's title.
   * @param message A short message displayed as description of the problem.
   * @param exc An optional exception. If this is a {@link ProblemException}
   * the corresponding reasons and solutions are displayed.
   * @return The dialog's return status.
   */
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
    composite.setLayout( new PackLayout( SWT.VERTICAL ) );
    
    createLabelArea( composite );
    createReasonArea( composite );
    createSolutionArea( composite );

    return composite;
    
  }
  
  protected void createLabelArea( final Composite parent ) {
    
    if ( this.message != null ) {
      
      int wHint = convertHorizontalDLUsToPixels( IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH );
      
      final ScrolledComposite sComp = createListArea( "Message:", parent, 100 );
      
      final Composite comp = new Composite( sComp, SWT.NONE );
      sComp.setContent( comp );
      comp.setLayout( new GridLayout( 1, false ) );
      
      Label label = new Label( comp, SWT.WRAP );
      label.setText( this.message );
      GridData gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.grabExcessHorizontalSpace = true;
      gData.widthHint = wHint;
      label.setLayoutData( gData );
      
      Point size = comp.computeSize( wHint, SWT.DEFAULT );
      comp.setSize( size );
      
      sComp.addControlListener( new ControlAdapter() {
        @Override
        public void controlResized( final ControlEvent e ) {
          Rectangle r = sComp.getClientArea();
          Point cSize = comp.computeSize( r.width, SWT.DEFAULT );
          comp.setSize( cSize );
        }
      } );
      
    }
    
  }
  
  protected void createReasonArea( final Composite parent ) {
    
    String[] reasons = getReasons();
    
    if ( ( reasons != null ) && ( reasons.length > 0 ) ) {
      
      ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
      Image reasonImage = imgReg.get( "reason" ); //$NON-NLS-1$
      
      final ScrolledComposite sComp = createListArea( "Reasons:", parent, 200 );
      
      final Composite reasonList = new Composite( sComp, SWT.NONE );
      sComp.setContent( reasonList );
      reasonList.setLayout( new GridLayout( 2, false ) );
      
      for ( String reason : reasons ) {
        
        Label imgLabel = new Label( reasonList, SWT.NONE );
        imgLabel.setImage( reasonImage );
        GridData gData = new GridData();
        gData.horizontalAlignment = GridData.CENTER;
        gData.verticalAlignment = GridData.BEGINNING;
        imgLabel.setLayoutData( gData );
        
        Label label = new Label( reasonList, SWT.WRAP );
        label.setText( reason );
        gData = new GridData( GridData.FILL_HORIZONTAL );
        gData.grabExcessHorizontalSpace = true;
        gData.horizontalAlignment = GridData.BEGINNING;
        label.setLayoutData( gData );
        
      }
      
      int wHint = convertHorizontalDLUsToPixels( IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH );
      reasonList.setSize( reasonList.computeSize( wHint, SWT.DEFAULT ) );
      
      sComp.addControlListener( new ControlAdapter() {
        @Override
        public void controlResized( final ControlEvent e ) {
          Rectangle r = sComp.getClientArea();
          Point cSize = reasonList.computeSize( r.width, SWT.DEFAULT );
          reasonList.setSize( cSize );
        }
      } );
      
    }
    
  }
  
  protected void createSolutionArea( final Composite parent ) {
    
    ISolution[] solutions = getSolutions();
    
    if ( ( solutions != null ) && ( solutions.length > 0 ) ) {
      
      ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
      Image solutionImage = imgReg.get( "solution" ); //$NON-NLS-1$
      
      final ScrolledComposite sComp = createListArea(  Messages.getString( "ProblemDialog.solutions_label" ), parent, 200 ); //$NON-NLS-1$
      
      final Composite solutionList = new Composite( sComp, SWT.NONE );
      sComp.setContent( solutionList );
      solutionList.setLayout( new GridLayout( 2, false ) );
      
      for ( final ISolution solution : solutions ) {
        
        Label imgLabel = new Label( solutionList, SWT.NONE );
        imgLabel.setImage( solutionImage );
        GridData gData = new GridData();
        gData.horizontalAlignment = GridData.CENTER;
        gData.verticalAlignment = GridData.BEGINNING;
        imgLabel.setLayoutData( gData );

        Link link = new Link( solutionList, SWT.WRAP );
        String text = solution.getDescription();
        if ( solution.isActive() ) {
          link.setText( "<a>" + text + "</a>" ); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
          link.setText( text );
        }
        gData = new GridData( GridData.FILL_HORIZONTAL);
        gData.grabExcessHorizontalSpace = true;
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
      
      int wHint = convertHorizontalDLUsToPixels( IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH );
      solutionList.setSize( solutionList.computeSize( wHint, SWT.DEFAULT ) );
      
      sComp.addControlListener( new ControlAdapter() {
        @Override
        public void controlResized( final ControlEvent e ) {
          Rectangle r = sComp.getClientArea();
          Point cSize = solutionList.computeSize( r.width, SWT.DEFAULT );
          solutionList.setSize( cSize );
        }
      } );
      
    }
    
  }
  
  protected IProblem getProblem() {
    
    IProblem result = null;
    
    if ( ( this.exc != null ) && ( this.exc instanceof ProblemException ) ) {
      result = ( ( ProblemException ) this.exc ).getProblem();
    }
    
    return result;
    
  }
  
  protected String[] getReasons() {
    
     List< String > resultList = new ArrayList< String >();
     
     IStatus status = getStatus();
     if ( status != null ) {
       resultList.add( status.getMessage() );
     }
     
     IProblem problem = getProblem();
     if ( problem != null ) {
       String[] reasons = problem.getReasons();
       if ( reasons != null ) {
         for ( String reason : reasons ) {
           resultList.add( reason );
         }
       }
     }
     
     return resultList.isEmpty() ? null : resultList.toArray( new String[ resultList.size() ] );
     
  }
  
  protected ISolution[] getSolutions() {
    
    List< ISolution > resultList = new ArrayList< ISolution >();

    IProblem problem = getProblem();
    if ( problem != null ) {
      List< ISolution > list = Arrays.asList( problem.getSolutions() );
      resultList.addAll( list );
    }

    if ( this.exc != null ) {
      resultList.add( new LogExceptionSolution( this.exc ) );
    }

    resultList.add( new ReportProblemSolution( this.exc ) );
    
    return resultList.toArray( new ISolution[ resultList.size() ] );
    
  }
  
  protected IStatus getStatus() {
    return getStatus( this.exc );
  }
  
  private ScrolledComposite createListArea( final String title, final Composite parent, final int maxScrollableHeight ) {
    
    Composite comp = new Composite( parent, SWT.NONE );
    PackLayout layout = new PackLayout( SWT.VERTICAL );
    comp.setLayout( layout );
    PackData pData = new PackData();
    comp.setLayoutData( pData );
    
    Label label = new Label( comp, SWT.NONE );
    label.setText( title );
    
    final ScrolledComposite sComp = new ScrolledComposite( comp, SWT.V_SCROLL );
    pData = new PackData();
    pData.minHeight = 10;
    pData.maxHeight = maxScrollableHeight;
    sComp.setLayoutData( pData );
    
    return sComp;
    
  }

  private static IStatus getStatus( final Throwable throwable ) {

    IStatus result = null;

    if ( ( throwable != null ) && ( throwable instanceof CoreException ) ) {
      result = ( ( CoreException ) throwable ).getStatus();
    }

    else {
      String message = null;
      if ( throwable != null ) {
        message = throwable.getMessage();
      } 
      if ( message == null ) {
        message = Messages.getString( "ProblemDialog.no_further_info" ); //$NON-NLS-1$
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
