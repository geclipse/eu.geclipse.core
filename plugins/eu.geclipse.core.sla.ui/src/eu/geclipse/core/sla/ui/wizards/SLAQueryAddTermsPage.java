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
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.sla.Extensions;
import eu.geclipse.core.sla.ISLAService;
import eu.geclipse.core.sla.model.SimpleTerm;
import eu.geclipse.core.sla.model.SimpleTermModel;
import eu.geclipse.core.sla.model.SltContainer;
import eu.geclipse.core.sla.ui.dialogs.SlaTermInputDialog;
import eu.geclipse.ui.dialogs.ProblemDialog;

public class SLAQueryAddTermsPage extends WizardPage implements Listener {

  private final String ServiceTypes[] = {
    "http://www.it.neclab.eu/sla/service/Sudoku",
    "http://www.it.neclab.eu/sla/service/WebMail",
    "http://www.it.neclab.eu/sla/service/CardReader",
    "http://www.it.neclab.eu/sla/service/Wiki",
    "http://www.it.neclab.eu/sla/service/BigBangSimulation"}; //$NON-NLS-5$
  SimpleTermModel model = new SimpleTermModel();
  // widgets on this page
  Combo serviceType;
  List termList;
  ListViewer termViewer;
  Button addButton;
  Button editButton;
  Button removeButton;
  Button demoButton;
  Text termText;

  /**
   * Constructor for PlanePage.
   */
  protected SLAQueryAddTermsPage( String pageName ) {
    super( pageName );
    setTitle( "SLA Query terms" );
    setDescription( "Specify the SLA term requirments to the service" );
  }

  /**
   * @see IDialogPage#createControl(Composite)
   */
  public void createControl( final Composite parent ) {
    // create the composite to hold the widgets
    GridData gData;
    Composite composite = new Composite( parent, SWT.NONE );
    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 3;
    gl.numColumns = ncol;
    composite.setLayout( gl );
    Label serviceLabel = new Label( composite, SWT.NONE );
    gData = new GridData( GridData.HORIZONTAL_ALIGN_END );
    serviceLabel.setText( "Service Type" );
    this.serviceType = new Combo( composite, SWT.NONE );
    gData = new GridData( GridData.BEGINNING | GridData.FILL_HORIZONTAL );
    gData.horizontalSpan = 2;
    this.serviceType.setLayoutData( gData );
    this.serviceType.setItems( this.ServiceTypes );
    this.serviceType.addModifyListener( new ModifyListener() {

      public void modifyText( ModifyEvent e ) {
        model.setServiceType( serviceType.getText() );
      }
    } );
    // create the widgets. If the appearance of the widget is different from
    // the
    // default,
    // create a GridData for it to set the alignment and define how much
    // space
    // it will occupy
    this.termList = new List( composite, SWT.BORDER | SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_BOTH );
    this.termList.setLayoutData( gData );
    this.termViewer = new ListViewer( this.termList );
    this.termViewer.setContentProvider( new TermContentProvider() );
    this.termViewer.setLabelProvider( new TermLabelProvider() );
    this.termViewer.setInput( this.model );
    this.termViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( SelectionChangedEvent event ) {
        IStructuredSelection selection = ( IStructuredSelection )event.getSelection();
        SimpleTerm term = ( SimpleTerm )selection.getFirstElement();
        termText.setText( term.getText() );
        updateButtons();
      }
    } );
    Composite buttonComposite = new Composite( composite, SWT.NULL );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    gData.horizontalSpan = 1;
    buttonComposite.setLayoutData( gData );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    buttonComposite.setLayout( gLayout );
    // add button
    this.addButton = new Button( buttonComposite, SWT.PUSH );
    this.addButton.setText( "Add" );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addButton.setLayoutData( gData );
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        addTerm();
      }
    } );
    // edit button
    this.editButton = new Button( buttonComposite, SWT.PUSH );
    this.editButton.setText( "Edit" );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.editButton.setLayoutData( gData );
    this.editButton.addSelectionListener( new SelectionAdapter() {

      public void widgetSelected( final SelectionEvent e ) {
        editTerm();
      }
    } );
    // remove button
    this.removeButton = new Button( buttonComposite, SWT.PUSH );
    this.removeButton.setText( "Remove" );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.removeButton.setLayoutData( gData );
    this.removeButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        removeTerm();
      }
    } );
    // demo button
    this.demoButton = new Button( buttonComposite, SWT.PUSH );
    this.demoButton.setText( "Demo" );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.demoButton.setLayoutData( gData );
    this.demoButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        demoTerms();
      }
    } );
    // add the text field to show the term request selected
    termText = new Text( composite, SWT.BORDER | SWT.MULTI );
    gData = new GridData( GridData.FILL_BOTH );
    termText.setLayoutData( gData );
    termText.setEditable( false );
    // set the composite as the control for this page
    setControl( composite );
    setPageComplete( true );
    this.updateButtons();
  }

  private void updateButtons() {
    IStructuredSelection selection = ( IStructuredSelection )this.termViewer.getSelection();
    Object element = selection.getFirstElement();
    if( element == null ) {
      this.termText.setText( "" );
      this.editButton.setEnabled( false );
      this.removeButton.setEnabled( false );
    } else {
      this.editButton.setEnabled( true );
      this.removeButton.setEnabled( true );
    }
  }

  public boolean canFlipToNextPage() {
    // no next page for this path through the wizard
    return true;
  }

  /*
   * Process the events: when the user has entered all information the wizard
   * can be finished
   */
  public void handleEvent( Event e ) {
  }

  @Override
  public IWizardPage getNextPage() {
    try {
      ISLAService service = Extensions.getSlaServiceImpl();
      // here we transform the Terms to the registration schema.
      String requirements = service.getRequirements( this.model );
      Object[] queryResult = service.queryRegistry( requirements );
      for( int i = 0; i < queryResult.length; i++ ) {
        ( ( SLAQueryWizard )this.getWizard() ).getSltModel()
          .addSlt( ( SltContainer )queryResult[ i ] );
      }
    } catch( ProblemException e ) {
      ProblemDialog.openProblem( this.getShell(),
                                 "SLA service missing",
                                 "No implementation for ISLAService found",
                                 e );
      this.getContainer().getShell().close();
    }
    SLAQuerySLTselectionPage page = ( SLAQuerySLTselectionPage )this.getWizard()
      .getPage( "Select a SLT" );
    page.onEnterPage();
    return page;
  }

  void onEnterPage() {
    // Gets the model
    System.out.println( "load the data from model if neccessary" );
  }

  private void addTerm() {
    SlaTermInputDialog dia1 = new SlaTermInputDialog( getShell(), null );
    if( dia1.open() == Window.OK ) {
      this.model.addTerm( dia1.getValue() );
    }
    this.termViewer.setInput( this.model );
    updateButtons();
  }

  private void editTerm() {
    IStructuredSelection selection = ( IStructuredSelection )this.termViewer.getSelection();
    SimpleTerm element = ( SimpleTerm )selection.getFirstElement();
    SlaTermInputDialog dia1 = new SlaTermInputDialog( getShell(), element );
    if( dia1.open() == Window.OK ) {
      // and so???
    }
    this.termViewer.setInput( this.model );
    updateButtons();
  }

  private void removeTerm() {
    IStructuredSelection selection = ( IStructuredSelection )this.termViewer.getSelection();
    SimpleTerm element = ( SimpleTerm )selection.getFirstElement();
    this.model.removeTerm( element.getName() );
    this.termViewer.setInput( this.model );
    this.updateButtons();
  }

  protected void demoTerms() {
    this.model.addTerm( new SimpleTerm( "NumberAccounts", //$NON-NLS-1$
                                        "http://www.it.neclab.eu/sla/terms/webmail/MaxNumAccounts", //$NON-NLS-1$
                                        "integer",
                                        "100",
                                        "<=",
                                        "",
                                        "" ) );
    this.model.addTerm( new SimpleTerm( "Availability",
                                        "http://www.it.neclab.eu/sla/terms/service/Availability",
                                        "float",
                                        "99.5",
                                        "<=",
                                        "",
                                        "" ) );
    this.model.addTerm( new SimpleTerm( "ResponseTime",
                                        "http://www.it.neclab.eu/sla/terms/service/ResponseTime",
                                        "float",
                                        "",
                                        "",
                                        "50.",
                                        "<=" ) );
    this.model.addTerm( new SimpleTerm( "Payment",
                                        "http://www.it.neclab.eu/sla/terms/service/Payment/Yen",
                                        "float",
                                        "",
                                        "",
                                        "1000.",
                                        "<" ) );
    this.termViewer.setInput( this.model );
    this.updateButtons();
  }
}
