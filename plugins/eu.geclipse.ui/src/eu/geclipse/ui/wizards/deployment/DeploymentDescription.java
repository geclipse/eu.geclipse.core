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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Yifan Zhou
 */
public class DeploymentDescription extends WizardPage {
  
  /**
   * Create a text for inputing a tag, the default was created by date.
   */
  private Text textOfTag;
  /**
   * Create a text for inputing a version, the default is v1.0.
   */
  private Text textOfVersion;
  /**
   * Product a tag by using the current system's time or its date.
   */
  private Calendar calendar;

  protected DeploymentDescription( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "Deployment.deployment_description_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "Deployment.deployment_description_title" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 4, false ) );
    GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 3;
    Label labelOfTag = new Label( composite, SWT.NONE );
    labelOfTag.setText( Messages.getString( "Deployment.deployment_description_tag_label" ) ); //$NON-NLS-1$
    this.textOfTag = new Text( composite, SWT.BORDER );
    this.textOfTag.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false, 1, 1 ) );
    Button byDateButton = new Button( composite, SWT.RADIO | SWT.FLAT );
    byDateButton.setText( Messages.getString( "Deployment.deployment_description_tag_by_date" ) ); //$NON-NLS-1$
    byDateButton.setSelection( true );
    byDateButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        getTextOfTag()
          .setText( Messages.getString( "Deployment.deployment_description_tag_name" ) + createDateTag() ); //$NON-NLS-1$
      }
    } );
    Button byTimeButton = new Button( composite, SWT.RADIO | SWT.FLAT );
    byTimeButton.setText( Messages.getString( "Deployment.deployment_description_tag_by_time" ) ); //$NON-NLS-1$
    byTimeButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        getTextOfTag()
          .setText( Messages.getString( "Deployment.deployment_description_tag_name" ) + createTimeTag() ); //$NON-NLS-1$
      }
    } );
    Label labelOfVersion = new Label( composite, SWT.NONE );
    labelOfVersion.setText( Messages.getString( "Deployment.deployment_description_version_label" ) ); //$NON-NLS-1$
    this.textOfVersion = new Text( composite, SWT.BORDER );
    this.textOfVersion.setLayoutData( gridData );
    this.setControl( composite );
  }

  @Override
  public void setVisible( final boolean visible )
  {
    if( visible ) {
      this.initContent();
    }
    super.setVisible( visible );
  }

  private void initContent() {
    this.textOfTag.
      setText( Messages.getString( "Deployment.deployment_description_tag_name" )  //$NON-NLS-1$
               + this.createDateTag() );
    this.textOfVersion
      .setText( Messages.getString( "Deployment.deployment_description_version_number" ) ); //$NON-NLS-1$
  }

  protected String createTimeTag() {
    this.calendar = new GregorianCalendar();
    return "" //$NON-NLS-1$
           + this.calendar.get( Calendar.HOUR_OF_DAY )
           + "_" //$NON-NLS-1$
           + this.calendar.get( Calendar.MINUTE )
           + "_" //$NON-NLS-1$
           + this.calendar.get( Calendar.SECOND );
  }

  protected String createDateTag() {
    DateFormatSymbols symbols = new DateFormatSymbols();
    this.calendar = new GregorianCalendar();
    return "" //$NON-NLS-1$
           + this.calendar.get( Calendar.DAY_OF_MONTH )
           + "_" //$NON-NLS-1$
           + symbols.getShortMonths()[ this.calendar.get( Calendar.MONTH ) ]
           + "_" //$NON-NLS-1$
           + this.calendar.get( Calendar.YEAR );
  }

  public Text getTextOfTag() {
    return this.textOfTag;
  }
  
  public Text getTextOfVersion() {
    return this.textOfVersion;
  }
  
}
