/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.wizards;

import org.eclipse.core.runtime.Preferences;
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

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.sla.DocumentExtensions;
import eu.geclipse.core.sla.ISLADocumentService;

/**
 * @author korn
 */
public class SLAQuerySLAPublication extends WizardPage {

  private Button agreeSLA;
  private Text finalSLA;

  protected SLAQuerySLAPublication( String pageName ) {
    super( pageName );
    this.setTitle( "Publish SLA" );
    this.setDescription( "Publish the final SLA in the SLA registry" );
  }

  public void createControl( Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    GridData gData;
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    composite.setLayout( gLayout );
    Label labelSlaName = new Label( composite, SWT.LEAD );
    labelSlaName.setText( "Please verify the final SLA before publishing it\n If you click finish, you make an electronic contract!" );
    this.agreeSLA = new Button( composite, SWT.CHECK );
    this.agreeSLA.setSelection( false );
    this.agreeSLA.setText( "I agree to the SLA Contract" );
    this.agreeSLA.addSelectionListener( new SelectionAdapter() {

      public void widgetSelected( SelectionEvent e ) {
        boolean test = agreeSLA.getSelection();
        updatePageComplete();
      }
    } );
    this.finalSLA = new Text( composite, SWT.BORDER
                                         | SWT.MULTI
                                         | SWT.H_SCROLL
                                         | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_BOTH );
    this.finalSLA.setLayoutData( gData );
    this.finalSLA.setEditable( false );
    this.finalSLA.setText( "" );
    setControl( composite );
  }

  protected void updatePageComplete() {
    this.setPageComplete( this.agreeSLA.getSelection() );
  }

  public void onEnterPage() throws ProblemException {
    // include the preferences
    Preferences prefs = eu.geclipse.core.sla.Activator.getDefault()
      .getPluginPreferences();
    // when this page of the wiards is entered,
    // 1. look for implementations of the SLADocumentService interface
    // 2. Add the consumer preferences to the document
    // 3. show the resulting document to the user
    ISLADocumentService service = DocumentExtensions.getSlaServiceImpl();
    String sltDoc = ( ( SLAQueryWizard )this.getWizard() ).getSltDoc();
    String finalDoc = service.addConsumerData( sltDoc );
    this.finalSLA.setText( finalDoc );
  }

  /*
   * Sets the completed field on the wizard class when all the information is
   * entered and the wizard can be completed
   */
  @Override
  public boolean isPageComplete() {
    return this.agreeSLA.getSelection();
  }

  /**
   * @return
   */
  public String getSLA() {
    return this.finalSLA.getText();
  }
}
