/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Yifan Zhou - initial API and implementation
 ******************************************************************************/
package eu.geclipse.ui.wizards.deployment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * @author Yifan Zhou
 */
public class DeploymentDescription extends WizardPage
  implements ModifyListener, SelectionListener {

  private Combo date;
  private Button custom;
  private Button automatic;
  private Button createdByDate;
  private Button createdByTime;
  private Button createdByVersion;
  private Button createdByName;
  private Text nameText;
  private Text tagText;
  private String time;
  private final String DEFAULTPROJECTNAME = "geclipse"; //$NON-NLS-1$
  private final String DEFAULTVERSION = "v1.0"; //$NON-NLS-1$

  protected DeploymentDescription( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "Deployment.deployment_description_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "Deployment.deployment_description_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 2, false ) );
    Group methodFor = new Group( composite, SWT.BORDER );
    methodFor.setText( Messages.getString( "Deployment.deployment_description_group_method_for" ) ); //$NON-NLS-1$
    methodFor.setLayout( new GridLayout() );
    GridData gridData = new GridData( GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL );
    methodFor.setLayoutData( gridData );
    this.custom = new Button( methodFor, SWT.RADIO );
    this.custom.setText( Messages.getString( "Deployment.deployment_description_method_for_custom" ) ); //$NON-NLS-1$
    this.custom.addSelectionListener( this );
    this.automatic = new Button( methodFor, SWT.RADIO );
    this.automatic.setText( Messages.getString( "Deployment.deployment_description_method_for_automatic" ) ); //$NON-NLS-1$
    this.automatic.setSelection( true );
    this.automatic.addSelectionListener( this );
    Group tag = new Group( composite, SWT.BORDER );
    tag.setText( Messages.getString( "Deployment.deployment_description_group_created_by" ) ); //$NON-NLS-1$
    tag.setLayout( new GridLayout( 2, false ) );
    tag.setLayoutData( gridData );
    this.createdByName = new Button( tag, SWT.CHECK );
    this.createdByName.setText( Messages.getString( "Deployment.deployment_description_tag_by_name" ) ); //$NON-NLS-1$
    this.createdByName.addSelectionListener( this );
    this.nameText = new Text( tag, SWT.BORDER );
    this.nameText.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    this.nameText.addModifyListener( this );
    this.createdByDate = new Button( tag, SWT.CHECK );
    this.createdByDate.setText( Messages.getString( "Deployment.deployment_description_tag_by_date" ) ); //$NON-NLS-1$
    this.createdByDate.addSelectionListener( this );
    this.date = new Combo( tag, SWT.BORDER | SWT.READ_ONLY );
    this.date.setLayoutData( new GridData( GridData.CENTER | GridData.GRAB_HORIZONTAL ) );
    initDateCombo();
    this.date.addSelectionListener( this );
    this.createdByTime = new Button( tag, SWT.CHECK );
    this.createdByTime.setText( Messages.getString( "Deployment.deployment_description_tag_by_time" ) ); //$NON-NLS-1$
    this.createdByTime.addSelectionListener( this );
    gridData = new GridData( GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 2;
    this.createdByTime.setLayoutData( gridData );
    this.createdByVersion = new Button( tag, SWT.CHECK );
    this.createdByVersion.setText( Messages.getString( "Deployment.deployment_description_tag_by_version" ) ); //$NON-NLS-1$
    this.createdByVersion.addSelectionListener( this );
    this.tagText = new Text( composite, SWT.BORDER );
    gridData = new GridData( GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 2;
    this.tagText.setLayoutData( gridData );
    this.tagText.setEnabled( false );
    this.tagText.addModifyListener( this );
    this.setControl( composite );
  }

  private void initDateCombo() {
    DateFormatSymbols symbols = new DateFormatSymbols();
    Calendar calendar = new GregorianCalendar();
    int year = calendar.get( Calendar.YEAR );
    int month = calendar.get( Calendar.MONTH );
    int day = calendar.get( Calendar.DAY_OF_MONTH );
    int hour = calendar.get( Calendar.HOUR_OF_DAY );
    int minute = calendar.get( Calendar.MINUTE );
    int second =calendar.get( Calendar.SECOND );
    String sMonth = symbols.getShortMonths()[ calendar.get( Calendar.MONTH ) ];
    String dashMark = "-"; //$NON-NLS-1$
    String colon = ":"; //$NON-NLS-1$
    this.date.add( sMonth + dashMark + day + dashMark + year );
    this.date.add( year + dashMark + sMonth + dashMark + day );
    this.date.add( month + dashMark + day + dashMark + year );
    this.date.add( year + dashMark + month + dashMark + day );
    this.date.select( 0 );
    this.time = hour + colon + minute + colon + second;
  }

  public void modifyText( final ModifyEvent e ) {
    setMessages();
    if ( e.getSource().equals( this.nameText ) ) {
      generateTag();
    }
  }

  public void widgetDefaultSelected( final SelectionEvent e ) {
    // empty implementation
  }

  public void widgetSelected( final SelectionEvent e ) {
    setMessages();
    if( e.getSource().equals( this.custom ) && this.custom.getSelection() ) {
      setWidgets( false );
    }
    if( e.getSource().equals( this.automatic ) && this.automatic.getSelection() ) {
      setWidgets( true );
    }
    if( e.getSource().equals( this.createdByName )
        || e.getSource().equals( this.createdByDate ) 
        || e.getSource().equals( this.createdByTime ) 
        || e.getSource().equals( this.createdByVersion )
        || e.getSource().equals( this.date ) ) {
      this.tagText.setText( generateTag() );
    }
  }
  
  public String getTag() {
    return ( this.tagText.getText().equals( "" ) ) ? this.DEFAULTPROJECTNAME : this.tagText.getText(); //$NON-NLS-1$
  }
  
  private void setWidgets( final boolean b ) {
    this.createdByName.setEnabled( b );
    this.createdByDate.setEnabled( b );
    this.createdByTime.setEnabled( b );
    this.createdByVersion.setEnabled( b );
    this.date.setEnabled( b );
    this.nameText.setEnabled( b );
    this.tagText.setEnabled( !b );
  }
  
  private String generateTag() {
    String blank = ""; //$NON-NLS-1$
    if ( this.createdByName.getSelection() ) { blank += this.nameText.getText(); }
    if ( this.createdByDate.getSelection() ) { blank += " " + this.date.getText(); } //$NON-NLS-1$
    if ( this.createdByTime.getSelection() ) { blank += " " + this.time; } //$NON-NLS-1$
    if ( this.createdByVersion.getSelection() ) { blank += " " + this.DEFAULTVERSION; } //$NON-NLS-1$
    return blank;
  }
  
  private void setMessages() {
    if( this.tagText.getText().equals( "" ) ) { //$NON-NLS-1$
      this.setErrorMessage( Messages.getString( "Deployment.deployment_description_error_message" ) ); //$NON-NLS-1$
    } else {
      this.setErrorMessage( null );
    }
  }
  
}
