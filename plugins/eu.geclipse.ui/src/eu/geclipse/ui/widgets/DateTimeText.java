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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.widgets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.GridException;
import eu.geclipse.core.IProblem;
import eu.geclipse.ui.DateTimeSolutionRegistry;
import eu.geclipse.ui.UIProblems;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * Widgets, which allow to edit date and time
 */
public class DateTimeText {
  
  /**
   * Widget style
   */
  public enum Style {
    /**
     * Edit only date
     */
    DATE, 
    /**
     * Edit both date and time
     */
    DATETIME
  }

  private static Image image;
  protected Style style;
  protected Button calendarButton;  
  private Text text;  
  private Composite topComposite;
  private boolean allowEmpty;
  
  /**
   * @param parent
   * @param style
   * @param allowEmpty if false, then {@link DateTimeText#getDate()} throw an exception for empty date 
   */
  public DateTimeText( final Composite parent, final Style style, final boolean allowEmpty ) {
    super();
    this.style = style;
    this.allowEmpty = allowEmpty;
    this.topComposite = new Composite( parent, SWT.NULL );
    GridLayout layout = new GridLayout( 2, false );
    layout.horizontalSpacing = 0;
    layout.marginWidth = 0;
    layout.marginTop = 0;
    layout.marginBottom = 0;
    layout.verticalSpacing = 0;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    this.topComposite.setLayout( layout );
    createText( this.topComposite );
    createButton( this.topComposite );
  }

  /**
   * @param date date, which will be visibled in widget
   */
  public void setDate( final Date date ) {
    String valueString = ""; //$NON-NLS-1$
    if( date != null ) {
      valueString = getFormatter( this.style ).format( date );
    }
    this.text.setText( valueString );
  }
  
  /**
   * @return Date entered in control, or null if allowEmpty is true and entered date is empty
   * @throws GridException thrown when user entered date in wrong format
   * @see NewProblemDialog#openProblem(Shell, String, String, Throwable)
   */
  public Date getDate() throws GridException {
    Date date = null;
    try {
      date = internalGetDate();
    } catch( ParseException exception ) {
      GridException gridException = new GridException( UIProblems.DATETIME_PARSE_PROBLEM );
      IProblem problem = gridException.getProblem();
      problem.addSolution( DateTimeSolutionRegistry.getRegistry().findSolution( DateTimeSolutionRegistry.USE_CALENDAR_BUTTON, this ) );
      problem.addSolution( DateTimeSolutionRegistry.getRegistry().findSolution( DateTimeSolutionRegistry.APPLY_VALID_DATEFORMAT, this ) );
      if( this.allowEmpty ) {
        problem.addSolution( DateTimeSolutionRegistry.getRegistry().findSolution( DateTimeSolutionRegistry.DELETE_ENTERED_DATE, this ) );
      }
      throw gridException;
    }
    return date;
  }

  /**
   * @return String containing format in which date should be entered.
   */
  public String getValidDateFormat() {
    
    String validFormat = null;
    try {
      validFormat = ((SimpleDateFormat)getFormatter( this.style )).toLocalizedPattern();
    }
    catch( ClassCastException exception ) {
      // getFormatter() may return formatter other than SimpleDateFormat for rare localizations.
      // see java-doc for class DateFormat for details
    }
    return validFormat;
  }

  private DateFormat getFormatter( final Style forStyle ) {
    DateFormat formatter = null;
    switch( forStyle ) {
      case DATE:
        formatter = DateFormat.getDateInstance();
      break;
      case DATETIME:
      default:
        formatter = DateFormat.getDateTimeInstance();
      break;
    }
    
    formatter.setLenient( true );
    
    return formatter;
  }

  private void createText( final Composite parent ) {
    this.text = new Text( parent, SWT.SINGLE | SWT.BORDER );
    GridData gridData = new GridData();
    gridData.widthHint = getWidthHint();
    this.text.setLayoutData( gridData );
  }
  
  private int getWidthHint() {
    int width = SWT.DEFAULT;
    switch( this.style ) {
      case DATE:
        width = SWT.DEFAULT;
        break;
      
      case DATETIME:
      default:
        width = 110;
        break;
    }
    
    return width;
  }

  private Date internalGetDate() throws ParseException {
    Date date = null;
    DateFormat formatter = getFormatter( this.style );
    String dateString = this.text.getText();
    if( !this.allowEmpty
        || dateString.length() > 0 ) {
      try {
        date = formatter.parse( dateString );
      } catch( ParseException exception ) {
        // if declared parser doesn't work for entered data, try to use other
        // parsers (maybe user ommited some date parts)?
        date = parseOtherFormats( dateString );
        
        if( date == null ) {
          throw exception;
        }
      }
    }
    return date;
  }
  
  private Date parseOtherFormats( final String dateString ) {
    Date date = null;
    
    // formatters ordered since most detailed
    DateFormat[] formatters = new DateFormat[]{
      DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.MEDIUM ),
      DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.SHORT ),
      DateFormat.getDateInstance()      
    };
    
    for( int index = 0; index < formatters.length && date == null; index++ ) {
      try {
        date = formatters[index].parse( dateString );
      } catch( ParseException exception ) {
        // ignore exception
      }
    }
    
    return date;
  }

  private void createButton( final Composite parent ) {
    this.calendarButton = new Button( parent, SWT.PUSH );
    this.calendarButton.setImage( getImage() );
    this.calendarButton.addSelectionListener( new SelectionAdapter() {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        openCalendarDialog();
      }
    } );
  }

  private Image getImage() {
    if( DateTimeText.image == null ) {
      ImageDescriptor imageDescriptor = Activator.getDefault()
        .getImageRegistry()
        .getDescriptor( "calendar" ); //$NON-NLS-1$
      DateTimeText.image = imageDescriptor.createImage();
    }
    return DateTimeText.image;
  }

  /**
   * @return shell, on which control is placed 
   */
  public Shell getShell() {
    return this.topComposite.getShell();
  }
  
  /**
   * Opens popup dialog with calendar to easy selecting date and time
   */
  public void openCalendarDialog() {
    DateTimeDialog dialog = new DateTimeDialog( getShell() );
    dialog.open();
  }
  
  private class DateTimeDialog extends PopupDialog {

    private DateTime dateControl;
    private DateTime timeControl;

    protected DateTimeDialog( final Shell parentShell ) {
      super( parentShell,
             PopupDialog.INFOPOPUP_SHELLSTYLE,
             true,
             false,
             false,
             false,
             null,
             null );
    }

    private void setDate( final Date date ) {
      if( date != null ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        this.dateControl.setYear( calendar.get( Calendar.YEAR ) );
        this.dateControl.setMonth( calendar.get( Calendar.MONTH ) );
        this.dateControl.setDay( calendar.get( Calendar.DAY_OF_MONTH ) );
        if( this.timeControl != null ) {
          this.timeControl.setHours( calendar.get( Calendar.HOUR_OF_DAY ) );
          this.timeControl.setMinutes( calendar.get( Calendar.MINUTE ) );
          this.timeControl.setSeconds( calendar.get( Calendar.SECOND ) );
        }
      }
    }

    private Date getDate() {
      Calendar calendar = Calendar.getInstance();
      calendar.set( this.dateControl.getYear(),
                    this.dateControl.getMonth(),
                    this.dateControl.getDay(),
                    this.timeControl == null ? 0 : this.timeControl.getHours(),
                    this.timeControl == null ? 0 : this.timeControl.getMinutes(),
                    this.timeControl == null ? 0 : this.timeControl.getSeconds() );
      return calendar.getTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea( final Composite parent )
    {
      Composite composite = new Composite( parent, SWT.NONE );
      composite.setLayout( new GridLayout( 2, true ) );
      
      createCalendar( composite );
      
      if( DateTimeText.this.style == Style.DATETIME ) {
        createHour( composite );
      }

      createButtons( composite );

      try {
        setDate( DateTimeText.this.getDate() );
      } catch( GridException exception ) {
        // ignore exception
      }
      
      return composite;
    }
    
    private void createButtons( final Composite parent ) {
      Composite composite = new Composite( parent, SWT.NONE );
      GridLayout layout = new GridLayout( 2, false );
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      composite.setLayout( layout );
      
      GridData gridData = new GridData();
      gridData.horizontalAlignment = SWT.RIGHT;
      gridData.horizontalSpan = 2;
      composite.setLayoutData( gridData ); 
      
      createOkButton( composite );
      createCancelButton( composite );      
    }
    
    private void createCalendar( final Composite parent ) {
      this.dateControl = new DateTime( parent, SWT.CALENDAR );
      GridData layoutData = new GridData();
      layoutData.horizontalSpan = 2;
      this.dateControl.setLayoutData( layoutData );      
    }
    
    private void createHour( final Composite parent ) {
      Label label = new Label( parent, SWT.NONE );
      label.setText( Messages.getString("DateTimeText.labelHour") ); //$NON-NLS-1$
      GridData gridData = new GridData();
      gridData.horizontalAlignment = SWT.END;
      label.setLayoutData( gridData );
      
      this.timeControl = new DateTime( parent, SWT.TIME );
      gridData = new GridData();
      gridData.horizontalAlignment = SWT.RIGHT;
      this.timeControl.setLayoutData( gridData );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
    @Override
    protected Point getInitialLocation( final Point initialSize )
    {
      return Display.getCurrent().map( DateTimeText.this.calendarButton.getParent(),
                                       null,
                                       DateTimeText.this.calendarButton.getLocation() );
    }

    private void createOkButton( final Composite parent ) {
      Button button = new Button( parent, SWT.PUSH | SWT.FLAT );
      button.setText( IDialogConstants.OK_LABEL );
      
      GridData gridData = new GridData();
      gridData.horizontalAlignment = SWT.RIGHT;
      gridData.widthHint = IDialogConstants.BUTTON_WIDTH;
      
      button.setLayoutData( gridData );
      getShell().setDefaultButton( button );
      
      button.addSelectionListener( new SelectionAdapter(){

        /* (non-Javadoc)
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected( final SelectionEvent e )
        {
          closeWithSave();
        }} );
    }
    
    private void createCancelButton( final Composite parent ) {
      Button button = new Button( parent, SWT.PUSH | SWT.FLAT );
      button.setText( IDialogConstants.CANCEL_LABEL );
      GridData gridData = new GridData();
      gridData.horizontalAlignment = SWT.RIGHT;
      gridData.widthHint = IDialogConstants.BUTTON_WIDTH;
      button.setLayoutData( gridData );
      
      button.addSelectionListener( new SelectionAdapter(){

        /* (non-Javadoc)
         * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected( final SelectionEvent e )
        {
          close();
        }} );
    }    
    
    protected void closeWithSave() {
      DateTimeText.this.setDate( getDate() );
      close();
    }
  }

  /**
   * @param enabled true if widget should allow editing, false if should work in readonly mode  
   */
  public void setEnabled( final boolean enabled ) {
    this.text.setEnabled( enabled );
    this.calendarButton.setEnabled( enabled );    
  }

  /**
   * @return @see org.eclipse.swt.widgets.Text#setFocus()
   */
  public boolean setFocus() {
    return this.text.setFocus();
  }

}
