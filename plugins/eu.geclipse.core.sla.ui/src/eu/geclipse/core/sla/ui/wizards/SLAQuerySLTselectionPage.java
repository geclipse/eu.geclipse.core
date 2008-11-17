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

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.sla.model.SltContainer;
import eu.geclipse.core.sla.model.SltContainerModel;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * @author korn
 */
public class SLAQuerySLTselectionPage extends WizardPage {

  private List listSLA;
  private ListViewer slaViewer;
  private Text slaContent;

  protected SLAQuerySLTselectionPage( String pageName ) {
    super( pageName );
    setTitle( "SLT templates" );
    setDescription( "Select one of the fitting SLT templates, which fullfill your requirements" );
  }

  /**
   * @see IDialogPage#createControl(Composite)
   */
  public void createControl( Composite parent ) {
    // create the composite to hold the widgets
    GridData gData;
    Composite composite = new Composite( parent, SWT.NONE );
    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 3;
    gl.numColumns = ncol;
    composite.setLayout( gl );
    // create the widgets. If the appearance of the widget is different from the
    // default,
    // create a GridData for it to set the alignment and define how much space
    // it will occupy
    this.listSLA = new List( composite, SWT.BORDER | SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_BOTH );
    this.listSLA.setLayoutData( gData );
    this.slaViewer = new ListViewer( this.listSLA );
    this.slaViewer.setContentProvider( new SltContentProvider() );
    this.slaViewer.setLabelProvider( new SltLabelProvider() );
    SltContainerModel sltModel = ( ( SLAQueryWizard )this.getWizard() ).getSltModel();
    this.slaViewer.setInput( sltModel );
    this.slaViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( SelectionChangedEvent event ) {
        IStructuredSelection selection = ( IStructuredSelection )event.getSelection();
        SltContainer slt = ( SltContainer )selection.getFirstElement();
        slaContent.setText( slt.getContent() );
        canFlipToNextPage();
        setPageComplete( true );
      }
    } );
    // add the text field to show the term request selected
    this.slaContent = new Text( composite, SWT.MULTI
                                           | SWT.BORDER
                                           | SWT.H_SCROLL
                                           | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_BOTH );
    this.slaContent.setLayoutData( gData );
    this.slaContent.setEditable( false );
    // set the composite as the control for this page
    setControl( composite );
  }

  @Override
  public IWizardPage getNextPage() {
    // get the selected slt document as String
    IStructuredSelection selection = ( IStructuredSelection )this.slaViewer.getSelection();
    ( ( SLAQueryWizard )this.getWizard() ).setSltDoc( ( ( SltContainer )selection.getFirstElement() ).getContent() );
    SLAQuerySLAPublication page = ( SLAQuerySLAPublication )this.getWizard()
      .getPage( "Publish the SLA" );
    try {
      page.onEnterPage();
      return page;
    } catch( ProblemException e ) {
      ProblemDialog.openProblem( this.getShell(),
                                 "SLA Problem report",
                                 "Missing SLA schema Document Service",
                                 e );
      // cancel the wizard
      this.getWizard().getContainer().getShell().close();
    }
    return null;
  }

  @Override
  public IWizardPage getPreviousPage() {
    ( ( SLAQueryWizard )this.getWizard() ).getSltModel().clear();
    return super.getPreviousPage();
  }

  public void onEnterPage() {
    this.slaViewer.setInput( ( ( SLAQueryWizard )this.getWizard() ).getSltModel() );
  }

  @Override
  public boolean canFlipToNextPage() {
    boolean value = false;
    IStructuredSelection selection = ( IStructuredSelection )this.slaViewer.getSelection();
    if( selection.getFirstElement() != null )
      value = true;
    return value;
  }
}
