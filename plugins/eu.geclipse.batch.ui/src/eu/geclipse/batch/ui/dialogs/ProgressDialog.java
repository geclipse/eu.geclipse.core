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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;

/**
 * Progress bar dialog, which are separated into major tasks. Within each major
 * task there are minor tasks. The driver progress the minor and major tasks.
 */
public class ProgressDialog extends Dialog {
  private volatile boolean isClosed = false;
  private String processMessage;
  private String shellTitle; 
  private Image[] processImages;  
  private Label processMessageLabel;
  private Button closeButton;
  private Composite cancelComposite;
  private Label lineLabel;
  private Composite progressBarComposite;
  private CLabel message;
  private ProgressBar progressBar;
  private Shell shell;
  private Display display;
  private int majorTasks;
  private int minorTasks;
  private int currentMajorTask;
  private int currentMinorTasks;
  private int currentMajorOffset;
  private double currentMinorOffset;
  private int progressTiks;
  private String[] taskDescriptions;
  
  /**
   * Default constructor
   * @param parent The parent shell
   */
  public ProgressDialog( final Shell parent ) {
    super( parent );
    
    this.currentMajorTask = 0;
    this.minorTasks = 0;
    this.majorTasks = 0;
    this.taskDescriptions = null;
    this.display = null;
    this.progressTiks = 100;
    this.processMessage = Messages.getString( "ProgressDialog.PleaseWait" ); //$NON-NLS-1$
    this.processImages = new Image[] { Activator.getDefault().getImageRegistry().get( Activator.IMG_BUSY_ARROW1 ),  
                                       Activator.getDefault().getImageRegistry().get( Activator.IMG_BUSY_ARROW2 ),
                                       Activator.getDefault().getImageRegistry().get( Activator.IMG_BUSY_ARROW3 ),
                                       Activator.getDefault().getImageRegistry().get( Activator.IMG_BUSY_ARROW4 ) }; 
  }

  /**
   * @param numMajorTasks How many major task this task bar should represent
   * @param descriptions Optional description of the major tasks 
   * @param dialogTitle The title of the dialog
   */
  public void initInformation( final int numMajorTasks, final String[] descriptions, final String dialogTitle ) {
    this.majorTasks = numMajorTasks;
    this.shellTitle = dialogTitle;

    this.taskDescriptions = new String[ this.majorTasks ];
    
    // If no description is given we create some
    if ( null == descriptions ) {
      for ( int i = 0; i < this.majorTasks; ++i )
        this.taskDescriptions[ i ] = Messages.getString( "ProgressDialog.WorkOnTask" ) + i; //$NON-NLS-1$ 
    } else {
      for ( int i = 0; i < this.majorTasks; ++i )
        this.taskDescriptions[ i ] = Messages.getString( "ProgressDialog.WorkOnTask" ) + descriptions[ i ]; //$NON-NLS-1$ 
    }
  }
  
  /**
   * Move the progress bar to the next major task
   * @param numMinTasks How many minor tasks are there in the next major task
   */
  public void moveNextMajorTask( final int numMinTasks ) {
    // The display is closed, so don't update the graphics
    if ( !this.isClosed ) {
      // Update the text
      this.display.syncExec( new Runnable() {
        @SuppressWarnings("synthetic-access")
        public void run() {
          ProgressDialog.this.processMessageLabel.setText( 
                         ProgressDialog.this.taskDescriptions[ ProgressDialog.this.currentMajorTask ] );
        }
      });
    }    
    this.minorTasks = numMinTasks;
    this.currentMinorTasks = 0;
    ++this.currentMajorTask;
    this.currentMajorOffset = ( this.progressTiks / this.majorTasks ) * ( this.currentMajorTask - 1 );
    this.currentMinorOffset = ( double )( this.progressTiks / this.majorTasks ) / this.minorTasks;
  }
  
  /**
   * Move the progress bar one minor step within the major task
   */
  public void moveNextMinor(  ) {
    ++this.currentMinorTasks;

    // The display is closed, so don't update the graphics
    if ( !this.isClosed ) {
      this.display.syncExec( new Runnable() {
        @SuppressWarnings("synthetic-access")
        public void run() {
          int selection = ProgressDialog.this.currentMajorOffset 
                          + ( int ) Math.floor( ( ProgressDialog.this.currentMinorTasks 
                                                  * ProgressDialog.this.currentMinorOffset ) );
          ProgressDialog.this.progressBar.setSelection( selection );
    
          // Update the image
          ProgressDialog.this.message.setImage( 
                         ProgressDialog.this.processImages[ ProgressDialog.this.currentMinorTasks % 4 ] );
        }
      }
      );
    }
  }
  
  /**
   * Opens the progress-bar dialog
   */
  public void open() {
    createContents(); 

    this.shell.open();
    this.shell.layout();
  }

  /**
   * Closes the progress-bar dialog 
   */
  public void close() {
    this.display.syncExec( new Runnable() {
      @SuppressWarnings("synthetic-access")
      public void run() {
        ProgressDialog.this.isClosed = true;
        ProgressDialog.this.shell.dispose();
      }
    });
  }
  
  /**
   * Draws the dialog and inits its contents 
   */
  protected void createContents() {
    Point dialogSize = new Point( 480, 180 );
    final GridLayout gridLayout = new GridLayout();
    gridLayout.verticalSpacing = 10;

    this.shell = new Shell( getParent(), SWT.TITLE | SWT.PRIMARY_MODAL );
    this.display = this.shell.getDisplay();

    this.shell.setLayout( gridLayout );
    this.shell.setSize( dialogSize );
    this.shell.setText( this.shellTitle );

    // Move the dialog to the center of the parent shell
    Rectangle shellBounds = getParent().getBounds();

    this.shell.setLocation(
         shellBounds.x + ( shellBounds.width - dialogSize.x) / 2,
         shellBounds.y + ( shellBounds.height - dialogSize.y) / 2 );

    final Composite composite = new Composite( this.shell, SWT.NONE );
    composite.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
    composite.setLayout( new GridLayout() );

    this.message = new CLabel( composite, SWT.NONE );
    this.message.setImage( this.processImages[ 0 ] );
    this.message.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
    this.message.setText( this.processMessage );

    this.progressBarComposite = new Composite( this.shell, SWT.NONE );
    this.progressBarComposite.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, false, false ) );
    this.progressBarComposite.setLayout( new FillLayout() );

    this.progressBar = new ProgressBar( this.progressBarComposite, SWT.SMOOTH );
    this.progressBar.setMaximum( this.progressTiks );

    this.processMessageLabel = new Label( this.shell, SWT.NONE );
    this.processMessageLabel.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, false, false ) );
    this.lineLabel = new Label( this.shell, SWT.HORIZONTAL | SWT.SEPARATOR );
    this.lineLabel.setLayoutData( new GridData(GridData.FILL, GridData.CENTER, false, false ) );

    this.cancelComposite = new Composite( this.shell, SWT.NONE );
    this.cancelComposite.setLayoutData( new GridData( GridData.END, GridData.CENTER, false, false ) );
    final GridLayout gridLayoutCancel = new GridLayout();
    gridLayoutCancel.numColumns = 2;
    this.cancelComposite.setLayout( gridLayoutCancel );

    this.closeButton = new Button( this.cancelComposite, SWT.NONE );
    this.closeButton.addSelectionListener( new SelectionAdapter() {
      @SuppressWarnings("synthetic-access")
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        ProgressDialog.this.isClosed = true;
        ProgressDialog.this.shell.dispose();
      }
    });
    this.closeButton.setLayoutData( new GridData( 80, SWT.DEFAULT ) );
    this.closeButton.setText( Messages.getString( "ProgressDialog.Close" ) ); //$NON-NLS-1$
    this.closeButton.setEnabled( true );
  }

}
