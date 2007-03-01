package eu.geclipse.ui.dialogs;

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
import eu.geclipse.core.GridException;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.SolutionRegistry;
import eu.geclipse.ui.UISolutionRegistry;
import eu.geclipse.ui.internal.Activator;

public class NewProblemDialog extends ErrorDialog {
  
  /**
   * Return code determining that a solution was chosen.
   */
  public static final int SOLVE = 2;
  
  final IProblem problem;
  
  private SolutionRegistry solutionRegistry;
  
  public NewProblemDialog( final Shell parentShell,
                           final String dialogTitle,
                           final String message,
                           final GridException gExc,
                           final SolutionRegistry solutionRegistry ) {
    super( parentShell, dialogTitle, message, gExc.getStatus(),
           IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR );
    this.problem = gExc.getProblem();
    this.solutionRegistry = solutionRegistry;
    if ( this.solutionRegistry == null ) {
      this.solutionRegistry = UISolutionRegistry.getRegistry( this.getShell() );
    }
  }
  
  public static int openProblem( final Shell parent,
                                 final String dialogTitle,
                                 final String message,
                                 final GridException gExc ) {
    return openProblem( parent, dialogTitle, message, gExc, null );
  }
  
  public static int openProblem( final Shell parent,
                                 final String dialogTitle,
                                 final String message,
                                 final GridException gExc,
                                 final SolutionRegistry solutionRegistry ) {
    NewProblemDialog dialog
      = new NewProblemDialog( parent, dialogTitle, message, gExc, solutionRegistry );
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
    
    List< ISolution > solutions
      = this.problem.getSolutions( this.solutionRegistry );
    
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
    
    return composite;
    
  }
  
}
