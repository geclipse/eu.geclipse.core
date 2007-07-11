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
  private Text text;  
  private Button calendarButton;
  
  /**
   * @param parent
   * @param style
   */
  public DateTimeText( final Composite parent, final Style style ) {
    super();
    this.style = style;
    Composite composite = new Composite( parent, SWT.NULL );
    GridLayout layout = new GridLayout( 2, false );
    layout.horizontalSpacing = 0;
    layout.marginWidth = 0;
    layout.marginTop = 0;
    layout.marginBottom = 0;
    layout.verticalSpacing = 0;
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    composite.setLayout( layout );
    createText( composite );
    createButton( composite );
  }

  /**
   * @param date date, which will be visibled in widget
   */
  public void setDate( final Date date ) {
    String valueString = "";
    if( date != null ) {
      valueString = getFormatter( this.style ).format( date );
    }
    this.text.setText( valueString );
  }
  
  /**
   * @return Date entered in control, or null if date is empty or is invalid. To
   *         distinguish between empty and valid use {@link DateTimeText#isValid()}
   */
  public Date getDate() {
    Date date = null;
    try {
      date = internalGetDate();
    } catch( ParseException exception ) {
      // Just return null when exception occurs
    }
    return date;
  }
  
  /**
   * @return true if entered date is valid or if is empty. To show to the user
   *         proper date format, use {@link DateTimeText#getValidDateFormat()}
   */
  public boolean isValid() {
    boolean valid = false;
    try {
      internalGetDate();
      valid = true;
    } catch( ParseException exception ) {
      // ignore exception
    }
    return valid;
  }
  
  /**
   * @return String containing format in which date should be entered. Use it in
   *         error-messages shown for user, when {@link DateTimeText#isValid()}
   *         return false
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
    if( dateString.length() > 0 ) {
      try {
        date = formatter.parse( dateString );
      } catch( ParseException exception ) {
        // if declared parser doesn't work for entered data, try to use other
        // parsers (maybe user ommited some data parts)?
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
        DateTimeDialog dialog = new DateTimeDialog( parent.getShell() );        
        
        dialog.open();
      }
    } );
  }

  private Image getImage() {
    if( DateTimeText.image == null ) {
      // TODO mariusz change calendar icon, to this one for which we have license
      ImageDescriptor imageDescriptor = Activator.getDefault()
        .getImageRegistry()
        .getDescriptor( "calendar" ); //$NON-NLS-1$
      DateTimeText.image = imageDescriptor.createImage();
    }
    return DateTimeText.image;
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
      setDate( DateTimeText.this.getDate() );
      
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
      return Display.getCurrent().map( calendarButton.getParent(),
                                       null,
                                       calendarButton.getLocation() );
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
    
    private void closeWithSave() {
      DateTimeText.this.setDate( getDate() );
      close();
    }
  }

  public void setReadOnly( boolean readOnly ) {
    // TODO mariusz Auto-generated method stub
    
  }
}
